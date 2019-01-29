package pe.ayni.core.reporte.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesResponse;
import com.google.api.services.sheets.v4.model.CopySheetToAnotherSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.SheetProperties;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.api.services.sheets.v4.model.ValueRange;

import pe.ayni.core.cliente.dto.ClienteDto;
import pe.ayni.core.cliente.service.ClienteService;
import pe.ayni.core.credito.dto.CreditoDto;
import pe.ayni.core.reporte.constraint.ReporteConstraint;
import pe.ayni.core.reporte.service.ReporteCreditoService;
import pe.ayni.core.reporte.utils.ReporteConfig;
import pe.ayni.core.reporte.utils.SheetUtils;

@Controller
@RequestMapping("/reportes/solicitud-credito")
public class FormatoSolicitudCredito extends ReporteSheetServlet{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	ReporteCreditoService reporteCreditoService; 
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	ReporteConfig reporteConfig;

	@Override
	protected void onAuthorization(HttpServletRequest req, HttpServletResponse resp, 
			AuthorizationCodeRequestUrl authorizationUrl) throws ServletException, IOException {
	    authorizationUrl.setState("solicitud-credito");
	    super.onAuthorization(req, resp, authorizationUrl);
	}
	
	@GetMapping(path="", params= {"monto", "fechaDesembolso", "idCliente", "frecuencia", "tem", "fechaPrimeraCuota", "nroCuotas"})
	public void getFormatoSolicitudCredito(@RequestParam("monto") BigDecimal monto, 
			@RequestParam("fechaDesembolso") String fechaDesembolso, @RequestParam("idCliente") Integer idCliente,
			@RequestParam("frecuencia") String frecuencia, @RequestParam("tem") BigDecimal tem,
			@RequestParam("fechaPrimeraCuota") String fechaPrimeraCuota, @RequestParam("nroCuotas") Integer nroCuotas,
			HttpServletRequest req, HttpServletResponse resp, Principal principal)
			throws ServletException, IOException, GeneralSecurityException{
	    
		CreditoDto creditoDto = new CreditoDto(monto ,frecuencia, tem, nroCuotas, fechaDesembolso, fechaPrimeraCuota);
		
		if (principal != null) {
			super.service(req, resp);	
		}
		if (req.getAttribute("validated") != null && (boolean)req.getAttribute("validated")) {
			
			String url = generateFormatoSolicitudCredito(idCliente, creditoDto);
			
			String glosa = "Formato de Solicitud de Credito";
			showLinkReporte(url, resp, glosa);
			
		} else {
			// TODO: Show specific message to the USER
		}
	}
	
	private String generateFormatoSolicitudCredito(Integer idCliente, CreditoDto creditoDto) throws IOException, GeneralSecurityException {

		String title = ReporteConstraint.Reporte.SOLICITUD_CREDITO.toString();
		Spreadsheet requestBody = new Spreadsheet().setProperties(new SpreadsheetProperties().setTitle(title));
	    
	    Credential credential = getCredential();
	    Sheets sheetsService = SheetUtils.createSheetsService(credential);
		Sheets.Spreadsheets.Create request = sheetsService.spreadsheets().create(requestBody);

	    Spreadsheet response = request.execute();
	    
	    // COPY
	    // The ID of the spreadsheet containing the sheet to copy.
	    String spreadsheetId = reporteConfig.getSpreadSheetId(ReporteConstraint.Reporte.SOLICITUD_CREDITO);

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

	    // TODO: Change code below to process the `response` object:
	    System.out.println(responseNew);
	    System.out.println(responseNew.getSheetId());
    
	    
	    // UPDATE
	    List<List<Object>> values = reporteCreditoService.calculateCuotas(creditoDto);
	    ClienteDto clienteDto = clienteService.findClienteById(idCliente);
	    
	    //Cuotas
	    int initialRow = 15;
	    int lastRow = initialRow + values.size() - 1;
	    final String range = sheetName + "!B" + initialRow + ":G" + lastRow;
	    
	    //Cliente
	    final String rangeCliente = sheetName + "!C5:C6";
	    List<List<Object>> valuesCliente = Arrays.asList(Arrays.asList(clienteDto.getNroIdentificacion()), Arrays.asList(clienteDto.getNombre()));
	    
	    //Credito Col C and Col F
	    final String rangeCredColC = sheetName + "!C9:C11";
	    final String rangeCredColF = sheetName + "!F9:F10";
	    String fechaDesembolso = reporteCreditoService.formatStringDate(creditoDto.getFechaDesembolso());
	    List<List<Object>> valuesCredColC = Arrays.asList(
	    		Arrays.asList(creditoDto.getMontoDesembolso()), 
	    		Arrays.asList(creditoDto.getTem()), 
	    		Arrays.asList(fechaDesembolso));
	    List<List<Object>> valuesCredColF = Arrays.asList(Arrays.asList(creditoDto.getFrecuencia()), Arrays.asList(creditoDto.getNroCuotas()));

	    List<ValueRange> data = new ArrayList<ValueRange>();
	    data.add(new ValueRange().setRange(range).setValues(values));
	    data.add(new ValueRange().setRange(rangeCliente).setValues(valuesCliente));
	    data.add(new ValueRange().setRange(rangeCredColC).setValues(valuesCredColC));
	    data.add(new ValueRange().setRange(rangeCredColF).setValues(valuesCredColF));

	    
	    // Additional ranges to update ...
	    BatchUpdateValuesRequest body = new BatchUpdateValuesRequest()
	            .setValueInputOption("RAW")
	            .setData(data);
	    BatchUpdateValuesResponse result = sheetsService.spreadsheets().values().batchUpdate(response.getSpreadsheetId(), body).execute();
	    System.out.printf("%d cells updated.", result.getTotalUpdatedCells());
	    
	    return response.getSpreadsheetUrl() + "#gid=" + responseNew.getSheetId();
	    

	
	}
	
	
}
