package pe.ayni.core.reporte.servlet;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeServlet;
import com.google.api.client.http.GenericUrl;

import pe.ayni.core.reporte.utils.SheetUtils;


public abstract class ReporteSheetServlet extends AbstractAuthorizationCodeServlet {
	
	private static final long serialVersionUID = 1L;


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setAttribute("validated", true);
	}
	
    
	@Override
	protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
	    GenericUrl url = new GenericUrl(req.getRequestURL().toString());
	    url.setRawPath("/reportes/oauth2callback");
	    return url.build();
	}

	@Override
	protected String getUserId(HttpServletRequest req) throws ServletException, IOException {
		Principal principal = req.getUserPrincipal();
		return principal.getName();
	}

	@Override
	protected AuthorizationCodeFlow initializeFlow() throws ServletException, IOException {
	    return SheetUtils.initializeFlow();
	}
	
	
	

}
