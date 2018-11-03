package pe.ayni.core.reporte.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
	
	protected void showLinkReporte(String url, HttpServletResponse resp, String glosa) throws IOException {
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
	    PrintWriter writer = resp.getWriter();
	    writer.println("<!doctype html><html><head>");
	    writer.println("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">");
	    writer.println("<title>" + glosa + "</title>");
	    writer.println("</head><body>");
	    writer.println("<div> "
	    	+ " <h1> " + glosa + " </h1> "
	    	+ " <hr> "
	        + "<a href=\"" + url + "\"" 
	        + "><h3>Acceder...</h3></a>"
	        );
	    writer.println("</div>");
	    writer.println("</body></html>");
		
	}
	
	

}
