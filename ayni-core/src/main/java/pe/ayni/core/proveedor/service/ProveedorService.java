package pe.ayni.core.proveedor.service;

import java.util.List;

import pe.ayni.core.proveedor.dto.ProveedorDto;

public interface ProveedorService {

	List<ProveedorDto> findProveedoresBy(String by, String input);

}
