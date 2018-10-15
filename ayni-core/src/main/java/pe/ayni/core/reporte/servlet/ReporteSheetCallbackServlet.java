package pe.ayni.core.reporte.servlet;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeCallbackServlet;
import com.google.api.client.http.GenericUrl;

import pe.ayni.core.reporte.utils.SheetUtils;

@Controller
@RequestMapping("/reportes/oauth2callback")
public class ReporteSheetCallbackServlet extends AbstractAuthorizationCodeCallbackServlet {
	private static final long serialVersionUID = 1L;
    
	@GetMapping("")
	public void callDoGet(HttpServletRequest req, HttpServletResponse resp, Principal principal) throws ServletException, IOException{
		if (principal != null) {
			super.doGet(req, resp);	
		}
	}
    
    
	@Override
	protected void onSuccess(HttpServletRequest req, HttpServletResponse resp, Credential credential)
		throws ServletException, IOException {
		String state = req.getParameter("state");
		resp.sendRedirect("/reportes/" + state);
	}
	
	@Override
	protected void onError( HttpServletRequest req, HttpServletResponse resp, AuthorizationCodeResponseUrl errorResponse)
	throws ServletException, IOException {
		// handle error
	}
	
	@Override
	protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
	    GenericUrl url = new GenericUrl(req.getRequestURL().toString());
	    url.setRawPath("/reportes/oauth2callback");
	    return url.build();
	}

	@Override
	protected String getUserId(HttpServletRequest req) throws ServletException, IOException {
		return req.getUserPrincipal().getName();

	}

	@Override
	protected AuthorizationCodeFlow initializeFlow() throws ServletException, IOException {
		return SheetUtils.initializeFlow();
	}

}
