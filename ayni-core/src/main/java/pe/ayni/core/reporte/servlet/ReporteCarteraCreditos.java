package pe.ayni.core.reporte.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.CopySheetToAnotherSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.SheetProperties;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import pe.ayni.core.credito.constraint.CreditoConstraint.EstadoCredito;
import pe.ayni.core.reporte.constraint.ReporteConstraint;
import pe.ayni.core.reporte.service.ReporteCreditoService;
import pe.ayni.core.reporte.utils.ReporteConfig;
import pe.ayni.core.reporte.utils.SheetUtils;

@Controller
@RequestMapping("/reportes/cartera-creditos")
public class ReporteCarteraCreditos extends ReporteSheetServlet {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	ReporteCreditoService reporteCreditoService; 
	
	@Autowired
	ReporteConfig reporteConfig;

	@Override
	protected void onAuthorization(HttpServletRequest req, HttpServletResponse resp, 
			AuthorizationCodeRequestUrl authorizationUrl) throws ServletException, IOException {
	    authorizationUrl.setState("cartera-creditos");
	    super.onAuthorization(req, resp, authorizationUrl);
	}
	
	@GetMapping(path="", params= {"estado"})
	public void getReporteCarteraCreditos(@RequestParam("estado") String estado,
			HttpServletRequest req, HttpServletResponse resp, Principal principal) 
			throws ServletException, IOException, GeneralSecurityException{
		if (principal != null) {
			super.service(req, resp);	
		}
		EstadoCredito estadoCred = null;
		if (estado != null && !estado.equals("")) {
			estadoCred = EstadoCredito.valueOf(estado);
		}
		if (req.getAttribute("validated") != null && (boolean)req.getAttribute("validated")) {
			String url = generateReporteCarteraCreditos(estadoCred);
			showLinkReporteCarteraCreditos(url, resp);
			
			
		} else {
			// TODO: Show specific message to the USER
		}
	
	}

	private void showLinkReporteCarteraCreditos(String url, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
	    PrintWriter writer = resp.getWriter();
	    writer.println("<!doctype html><html><head>");
	    writer.println("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">");
	    writer.println("<title>" + "Reporte de Cartera de Creditos" + "</title>");
	    writer.println("</head><body>");
	    writer.println("<div> "
	    	+ " <h1> Reporte de Cartera de Creditos </h1> "
	    	+ " <hr> "
	        + "<a href=\"" + url + "\"" 
	        + "><h3>Acceder al Reporte</h3></a>"
	        );
	    writer.println("</div>");
	    writer.println("</body></html>");
		
	}

	private String generateReporteCarteraCreditos(EstadoCredito estado) throws IOException, GeneralSecurityException {
		
		String title = ReporteConstraint.Reporte.CARTERA_CREDITOS.toString();
		Spreadsheet requestBody = new Spreadsheet().setProperties(new SpreadsheetProperties().setTitle(title));
	    
	    Credential credential = getCredential();
		Sheets sheetsService = SheetUtils.createSheetsService(credential);
		Sheets.Spreadsheets.Create request = sheetsService.spreadsheets().create(requestBody);

	    Spreadsheet response = request.execute();
	    
	    // COPY
	    // The ID of the spreadsheet containing the sheet to copy.
	    String spreadsheetId = reporteConfig.getSpreadSheetId(ReporteConstraint.Reporte.CARTERA_CREDITOS);

	    // The ID of the sheet to copy.
	    int sheetId = 0; // TODO: Update placeholder value.

	    // The ID of the spreadsheet to copy the sheet to.
	    String destinationSpreadsheetId = response.getSpreadsheetId(); // TODO: Update placeholder value.

	    // TODO: Assign values to desired fields of `requestBody`:
	    CopySheetToAnotherSpreadsheetRequest requestBodyNew = new CopySheetToAnotherSpreadsheetRequest();
	    requestBodyNew.setDestinationSpreadsheetId(destinationSpreadsheetId);

	    //Sheets sheetsService = createSheetsService();
	    Sheets.Spreadsheets.SheetsOperations.CopyTo requestNew =
	        sheetsService.spreadsheets().sheets().copyTo(spreadsheetId, sheetId, requestBodyNew);
	    
	    SheetProperties responseNew = requestNew.execute();
	    String sheetName = responseNew.getTitle(); 

	    // UPDATE
	    List<List<Object>> values = reporteCreditoService.getCarteraCreditos(estado);
	    int initialRow = 3;
	    int lastRow = initialRow + values.size() - 1;
	    ValueRange body = new ValueRange().setValues(values);
	    final String range = sheetName + "!A" + initialRow + ":X" + lastRow;
	    UpdateValuesResponse result = sheetsService.spreadsheets().values().update(response.getSpreadsheetId(), range, body)
	                    .setValueInputOption("RAW")
	                    .execute();
	    System.out.printf("%d cells updated.", result.getUpdatedCells());
	    
	    
	    return response.getSpreadsheetUrl() + "#gid=" + responseNew.getSheetId();
	    

	
	}
	
	
}
