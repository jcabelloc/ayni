package pe.ayni.core.proveedor.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.ayni.core.proveedor.dao.ProveedorDao;
import pe.ayni.core.proveedor.dto.ProveedorDto;
import pe.ayni.core.proveedor.entity.Proveedor;

@Service
public class ProveedorServiceImpl implements ProveedorService {
	
	@Autowired
	ProveedorDao proveedorDao;
	
	@Override
	@Transactional
	public List<ProveedorDto> findProveedoresBy(String by, String input) {
		List<ProveedorDto> proveedoresDto = new ArrayList<>();
		List<Proveedor> proveedores = proveedorDao.findBy(by, input);
		for (Proveedor proveedor : proveedores) {
			ProveedorDto proveedorDto = buildDtoFrom(proveedor);
			proveedoresDto.add(proveedorDto);
		}
		return proveedoresDto;
	}

	private ProveedorDto buildDtoFrom(Proveedor proveedor) {
		return new ProveedorDto(proveedor);
	}

	@Override
	@Transactional
	public ProveedorDto findProveedorById(Integer id) {
		Proveedor proveedor = proveedorDao.findById(id);
		return buildDtoFrom(proveedor);
	}

}
