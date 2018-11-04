package pe.ayni.core.gasto.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.ayni.core.gasto.constraint.GastoContraint.TipoComprobante;
import pe.ayni.core.gasto.dao.GastoDao;
import pe.ayni.core.gasto.dto.GastoDto;
import pe.ayni.core.gasto.entity.Gasto;
import pe.ayni.core.operacion.entity.Operacion;
import pe.ayni.core.proveedor.entity.Proveedor;
import pe.ayni.core.seguridad.entity.Usuario;


@Service
public class GastoServiceImpl implements GastoService {
	
	@Autowired
	GastoDao gastoDao;
	
	
	@Override
	@Transactional
	public GastoDto createGasto(GastoDto gastoDto) {
		
		Gasto gasto = buildEntityFrom(gastoDto);
		gastoDao.save(gasto);
		gastoDto = buildDtoFrom(gasto);
		
		return gastoDto;
	}


	private GastoDto buildDtoFrom(Gasto gasto) {
		ModelMapper modelMapper = new ModelMapper();
		GastoDto gastoDto = modelMapper.map(gasto, GastoDto.class);	
		gastoDto.setAutorizador(gasto.getAutorizador().getUsuario());
		return gastoDto;
	}


	private Gasto buildEntityFrom(GastoDto gastoDto) {
		Gasto gasto = new Gasto();
		gasto.setAutorizador(new Usuario(gastoDto.getAutorizador()));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		gasto.setFecha(LocalDate.parse(gastoDto.getFecha(), formatter));
		gasto.setNroComprobante(gastoDto.getNroComprobante());
		gasto.setTipoComprobante(TipoComprobante.valueOf(gastoDto.getTipoComprobante()));
		gasto.setOperacion(new Operacion(gastoDto.getIdOperacion()));
		gasto.setProveedor(new Proveedor(gastoDto.getIdProveedor()));
		
		return gasto;
	}


	@Override
	@Transactional
	public GastoDto findGastoById(Integer id) {
		Gasto gasto = gastoDao.findById(id);
		GastoDto gastoDto = buildDtoFrom(gasto);
		return gastoDto;
	}

}
