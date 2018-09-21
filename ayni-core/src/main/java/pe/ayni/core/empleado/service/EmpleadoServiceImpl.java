package pe.ayni.core.empleado.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.ayni.core.empleado.dao.EmpleadoDao;
import pe.ayni.core.empleado.entity.Empleado;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {
	
	@Autowired
	EmpleadoDao empleadoDao;
	
	@Override
	@Transactional
	public void createEmpleado(Empleado empleado) {
		empleadoDao.create(empleado);
	}

}
