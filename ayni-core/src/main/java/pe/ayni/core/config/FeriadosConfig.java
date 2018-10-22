package pe.ayni.core.config;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeriadosConfig {
	
	@Bean
	public Map<String, int[]> feriados() { 
		
		Map<String, int[]> feriados = new HashMap<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		// Feriados Regulares
		int[] diasFeriados = new int[] {
		    LocalDate.parse("2018-01-01", formatter).getDayOfYear(),
		    LocalDate.parse("2018-05-01", formatter).getDayOfYear(),
			LocalDate.parse("2018-06-29", formatter).getDayOfYear(),
			LocalDate.parse("2018-07-27", formatter).getDayOfYear(),
			LocalDate.parse("2018-07-28", formatter).getDayOfYear(),
			LocalDate.parse("2018-08-30", formatter).getDayOfYear(),
			LocalDate.parse("2018-10-08", formatter).getDayOfYear(),
			LocalDate.parse("2018-11-01", formatter).getDayOfYear(),
			LocalDate.parse("2018-12-08", formatter).getDayOfYear(),
			LocalDate.parse("2018-12-25", formatter).getDayOfYear(),
		};
		
		feriados.put("REGULAR", diasFeriados);
		// Feriados No Regulares
		feriados.put("2018", new int[] {
				LocalDate.parse("2019-03-29", formatter).getDayOfYear(),
				LocalDate.parse("2019-03-30", formatter).getDayOfYear()
		});
		feriados.put("2019", new int[] {
				LocalDate.parse("2019-04-18", formatter).getDayOfYear(),
				LocalDate.parse("2019-04-19", formatter).getDayOfYear()
		});
		feriados.put("2020", new int[] {
				LocalDate.parse("2020-04-09", formatter).getDayOfYear(),
				LocalDate.parse("2020-04-10", formatter).getDayOfYear()
		});
		
		return feriados;
		
		
	}

}
