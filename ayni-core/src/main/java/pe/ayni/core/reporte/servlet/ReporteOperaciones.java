package pe.ayni.core.reporte.servlet;

import java.io.IOException;
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

import pe.ayni.core.operacion.constraint.OperacionConstraint.TipoOperacion;
import pe.ayni.core.reporte.constraint.ReporteConstraint;
import pe.ayni.core.reporte.service.ReporteOperacionService;
import pe.ayni.core.reporte.utils.ReporteConfig;
import pe.ayni.core.reporte.utils.SheetUtils;

@Controller
@RequestMapping("/reportes/operaciones")
public class ReporteOperaciones extends ReporteSheetServlet{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	ReporteOperacionService reporteOperacionService; 
	
	@Autowired
	ReporteConfig reporteConfig;
	
	@Override
	protected void onAuthorization(HttpServletRequest req, HttpServletResponse resp, 
			AuthorizationCodeRequestUrl authorizationUrl) throws ServletException, IOException {
	    authorizationUrl.setState("operaciones");
	    super.onAuthorization(req, resp, authorizationUrl);
	}
	
	@GetMapping(path="", params= {"tipoOperacion", "desde", "hasta"})
	public void getReporteCarteraCreditos(@RequestParam("tipoOperacion") String tipoOperacionReq,
			@RequestParam("desde") String desde, @RequestParam("hasta") String hasta,
			HttpServletRequest req, HttpServletResponse resp, Principal principal) 
			throws ServletException, IOException, GeneralSecurityException{
		if (principal != null) {
			super.service(req, resp);	
		}
		TipoOperacion tipoOperacion = null;
		if (tipoOperacionReq != null && !tipoOperacionReq.equals("")) {
			tipoOperacion = TipoOperacion.valueOf(tipoOperacionReq);
		}
		
		if (req.getAttribute("validated") != null && (boolean)req.getAttribute("validated")) {
			String url = generateReporteOperaciones(tipoOperacion, desde, hasta);
			String glosa = "Reporte de Operaciones";
			showLinkReporte(url, resp, glosa);
			
			
		} else {
			// TODO: Show specific message to the USER
		}
	
	}
	
	private String generateReporteOperaciones(TipoOperacion tipoOperacion, String desde, String hasta) throws IOException, GeneralSecurityException {

		String title = ReporteConstraint.Reporte.OPERACIONES.toString();
		Spreadsheet requestBody = new Spreadsheet().setProperties(new SpreadsheetProperties().setTitle(title));
	   
	    Credential credential = getCredential();
	    Sheets sheetsService = SheetUtils.createSheetsService(credential);
		Sheets.Spreadsheets.Create request = sheetsService.spreadsheets().create(requestBody);

	    Spreadsheet response = request.execute();
	    
	    // COPY
	    // The ID of the spreadsheet containing the sheet to copy.
	    String spreadsheetId = reporteConfig.getSpreadSheetId(ReporteConstraint.Reporte.OPERACIONES);

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
	    List<List<Object>> values = reporteOperacionService.getOperaciones(tipoOperacion, desde, hasta);
	    int initialRow = 3;
	    int lastRow = initialRow + values.size() - 1;
	    ValueRange body = new ValueRange().setValues(values);
	    //final String range = "Copy of Sheet1!A3:S4";
	    final String range = sheetName + "!A" + initialRow + ":M" + lastRow;
	    UpdateValuesResponse result = sheetsService.spreadsheets().values().update(response.getSpreadsheetId(), range, body)
	                    .setValueInputOption("RAW")
	                    .execute();
	    System.out.printf("%d cells updated.", result.getUpdatedCells());
	    
	    return response.getSpreadsheetUrl() + "#gid=" + responseNew.getSheetId();

	}
}
