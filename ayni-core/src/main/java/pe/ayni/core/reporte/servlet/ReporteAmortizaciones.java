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

import pe.ayni.core.reporte.constraint.ReporteConstraint;
import pe.ayni.core.reporte.service.ReporteCreditoService;
import pe.ayni.core.reporte.utils.ReporteConfig;
import pe.ayni.core.reporte.utils.SheetUtils;

@Controller
@RequestMapping("/reportes/amortizaciones")
public class ReporteAmortizaciones extends ReporteSheetServlet {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	ReporteCreditoService reporteCreditoService; 
	
	@Autowired
	ReporteConfig reporteConfig;

	@Override
	protected void onAuthorization(HttpServletRequest req, HttpServletResponse resp, 
			AuthorizationCodeRequestUrl authorizationUrl) throws ServletException, IOException {
	    authorizationUrl.setState("amortizaciones");
	    super.onAuthorization(req, resp, authorizationUrl);
	}

	@GetMapping(path="", params= {"month", "year"})
	public void getReporteAmortizaciones(@RequestParam("month") Integer month, @RequestParam("year") Integer year, 
			HttpServletRequest req, HttpServletResponse resp, Principal principal) 
			throws ServletException, IOException, GeneralSecurityException{
		if (principal != null) {
			super.service(req, resp);	
		}
		if (req.getAttribute("validated") != null && (boolean)req.getAttribute("validated")) {
			
			String url = generateReporteAmortizaciones(month, year);
			String glosa = "Reporte de Amortizaciones";
			showLinkReporte(url, resp, glosa);
			
			
		} else {
			// TODO: Show specific message to the USER
		}
	
	}

	private String generateReporteAmortizaciones(int month, int year) throws IOException, GeneralSecurityException {

		String title = ReporteConstraint.Reporte.AMORTIZACIONES.toString();
		Spreadsheet requestBody = new Spreadsheet().setProperties(new SpreadsheetProperties().setTitle(title));
	   
	    Credential credential = getCredential();
	    Sheets sheetsService = SheetUtils.createSheetsService(credential);
		Sheets.Spreadsheets.Create request = sheetsService.spreadsheets().create(requestBody);

	    Spreadsheet response = request.execute();
	    
	    // COPY
	    // The ID of the spreadsheet containing the sheet to copy.
	    String spreadsheetId = reporteConfig.getSpreadSheetId(ReporteConstraint.Reporte.AMORTIZACIONES);

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
	    List<List<Object>> values = reporteCreditoService.getAmortizaciones(month, year);
	    int initialRow = 3;
	    int lastRow = initialRow + values.size() - 1;
	    ValueRange body = new ValueRange().setValues(values);
	    //final String range = "Copy of Sheet1!A3:S4";
	    final String range = sheetName + "!A" + initialRow + ":L" + lastRow;
	    UpdateValuesResponse result = sheetsService.spreadsheets().values().update(response.getSpreadsheetId(), range, body)
	                    .setValueInputOption("RAW")
	                    .execute();
	    System.out.printf("%d cells updated.", result.getUpdatedCells());
	    
	    return response.getSpreadsheetUrl() + "#gid=" + responseNew.getSheetId();

	}
	
}
