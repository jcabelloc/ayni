package pe.ayni.core.reporte.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.appengine.datastore.AppEngineDataStoreFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Preconditions;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;

public class SheetUtils {
	
	private static final AppEngineDataStoreFactory DATA_STORE_FACTORY = AppEngineDataStoreFactory.getDefaultInstance();
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FILE_PATH = "/client_secrets.json";

	private static GoogleClientSecrets clientSecrets = null;

	static GoogleClientSecrets getClientCredential() throws IOException {
		if (clientSecrets == null) {
	        InputStream in = SheetUtils.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
	        clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
			
			Preconditions.checkArgument(!clientSecrets.getDetails().getClientId().startsWith("Enter ")
	          && !clientSecrets.getDetails().getClientSecret().startsWith("Enter "),
	          "Download client_secrets.json file from https://code.google.com/apis/console/"
	          + "?api=sheets into ...");
	    }
	    return clientSecrets;
	}
	
	public static AuthorizationCodeFlow initializeFlow() throws IOException {
	    return new GoogleAuthorizationCodeFlow.Builder(
	            new NetHttpTransport(), JSON_FACTORY, 
	            getClientCredential(),
	            Collections.singleton(SheetsScopes.SPREADSHEETS)).setDataStoreFactory(
	            DATA_STORE_FACTORY).setAccessType("offline").setApprovalPrompt("force").build();
	}
	
	public static Sheets createSheetsService(Credential credential) throws GeneralSecurityException, IOException  {
	    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
	    return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
	        .setApplicationName("Google-SheetsSample/0.1")
	        .build();
	}

}
