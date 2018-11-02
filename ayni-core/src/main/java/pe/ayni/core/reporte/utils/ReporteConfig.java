package pe.ayni.core.reporte.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import pe.ayni.core.reporte.constraint.ReporteConstraint.Reporte;

@Configuration
@PropertySource({ "classpath:plantillas-reportes-sheets.properties" })
public class ReporteConfig {
	
	@Autowired
	private Environment env;
	
	
	public String getSpreadSheetId(Reporte nombreReporte) {
		return env.getProperty(nombreReporte.toString());
	}

}
