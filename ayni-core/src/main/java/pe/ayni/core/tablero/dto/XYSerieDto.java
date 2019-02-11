package pe.ayni.core.tablero.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class XYSerieDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	String[] xSerie;
	BigDecimal[] ySerie;
	
	public XYSerieDto() {
		
	}
	
	public XYSerieDto(String[] xSerie, BigDecimal[] ySerie) {
		this.xSerie = xSerie;
		this.ySerie = ySerie;
		
	}

	public String[] getxSerie() {
		return xSerie;
	}

	public void setxSerie(String[] xSerie) {
		this.xSerie = xSerie;
	}

	public BigDecimal[] getySerie() {
		return ySerie;
	}

	public void setySerie(BigDecimal[] ySerie) {
		this.ySerie = ySerie;
	}
	
	
}
