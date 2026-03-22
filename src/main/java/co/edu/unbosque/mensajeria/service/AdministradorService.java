package co.edu.unbosque.mensajeria.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unbosque.mensajeria.dto.AdministradorDTO;
import co.edu.unbosque.mensajeria.entity.Administrador;
import co.edu.unbosque.mensajeria.repository.AdministradorRepository;
import co.edu.unbosque.mensajeria.util.LanzadorDeException;

@Service
public class AdministradorService implements CRUDOperation<AdministradorDTO> {

	@Autowired
	private AdministradorRepository administradorRep;

	@Autowired
	private ModelMapper mapper;

	public AdministradorService() {
		
	}

	@Override
    public int create(AdministradorDTO data) {

        LanzadorDeException.verificarNombre(data.getNombre());
        LanzadorDeException.verificarCedula(data.getCedula());
        LanzadorDeException.verificarCorreoElectronico(data.getCorreo());
        LanzadorDeException.verificarTelefono(data.getTelefono());
        LanzadorDeException.verificarTurno(data.getTurno());

        Administrador entity = mapper.map(data, Administrador.class);
        administradorRep.save(entity);

        return 0;
    }

	@Override
	public List<AdministradorDTO> getAll() {
		List<Administrador> entityList = (List<Administrador>) administradorRep.findAll();
		List<AdministradorDTO> dtoList = new ArrayList<>();
		entityList.forEach((entity) -> {
			AdministradorDTO dto = mapper.map(entity, AdministradorDTO.class);
			dtoList.add(dto);
		});
		return dtoList;
	}

	@Override
	public int deleteById(Long id) {
		
		LanzadorDeException.verificarId(id);
		Optional<Administrador> encontrado = administradorRep.findById(id);
		if (encontrado.isPresent()) {
			administradorRep.delete(encontrado.get());
			return 0;
		}
		return 1;
	}

	@Override
	public int updateById(Long id, AdministradorDTO data) {
		Optional<Administrador> encontrado = administradorRep.findById(id);
		if (encontrado.isPresent()) {
			Administrador temp = encontrado.get();
			temp.setNombre(data.getNombre());
			temp.setCedula(data.getCedula());
			temp.setCorreo(data.getCorreo());
			temp.setTelefono(data.getTelefono());
			temp.setTurno(data.getTurno());
			temp.setUsuario(data.getUsuario());
			temp.setContrasenia(data.getContrasenia());
			administradorRep.save(temp);
			return 0;
		}
		return 1;
	}

	@Override
	public long count() {
		return administradorRep.count();
	}

	@Override
	public boolean exist(Long id) {
		return administradorRep.existsById(id) ? true : false;
	}
	
	
	public List<AdministradorDTO> findByNombre(String nombre) {
		Optional<List<Administrador>> encontrados = administradorRep.findByNombre(nombre);
		List<Administrador> entityList = encontrados.get();
		List<AdministradorDTO> dtoList = new ArrayList<>();
		
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			entityList.forEach((entity) -> {
				AdministradorDTO dto = mapper.map(entity, AdministradorDTO.class);
				dtoList.add(dto);
			});
			return dtoList;
		} else {
			return new ArrayList<AdministradorDTO>(); 
		}
	}

	public List<AdministradorDTO> findByCedula(String cedula) {
		Optional<List<Administrador>> encontrados = administradorRep.findByCedula(cedula);
		List<Administrador> entityList = encontrados.get();
		List<AdministradorDTO> dtoList = new ArrayList<>();
		
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			entityList.forEach((entity) -> {
				AdministradorDTO dto = mapper.map(entity, AdministradorDTO.class);
				dtoList.add(dto);
			});
			return dtoList;
		} else {
			return new ArrayList<AdministradorDTO>(); 
		}
	}

	public List<AdministradorDTO> findByCorreo(String correo) {
		Optional<List<Administrador>> encontrados = administradorRep.findByCorreo(correo);
		List<Administrador> entityList = encontrados.get();
		List<AdministradorDTO> dtoList = new ArrayList<>();
		
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			entityList.forEach((entity) -> {
				AdministradorDTO dto = mapper.map(entity, AdministradorDTO.class);
				dtoList.add(dto);
			});
			return dtoList;
		} else {
			return new ArrayList<AdministradorDTO>();
		}
	}

	public List<AdministradorDTO> findByTelefono(String telefono) {
		Optional<List<Administrador>> encontrados = administradorRep.findByTelefono(telefono);
		List<Administrador> entityList = encontrados.get();
		List<AdministradorDTO> dtoList = new ArrayList<>();
		
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			entityList.forEach((entity) -> {
				AdministradorDTO dto = mapper.map(entity, AdministradorDTO.class);
				dtoList.add(dto);
			});
			return dtoList;
		} else {
			return new ArrayList<AdministradorDTO>(); 
		}
	}

	public List<AdministradorDTO> findByUsuario(String usuario) {
		Optional<List<Administrador>> encontrados = administradorRep.findByUsuario(usuario);
		List<Administrador> entityList = encontrados.get();
		List<AdministradorDTO> dtoList = new ArrayList<>();
		
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			entityList.forEach((entity) -> {
				AdministradorDTO dto = mapper.map(entity, AdministradorDTO.class);
				dtoList.add(dto);
			});
			return dtoList;
		} else {
			return new ArrayList<AdministradorDTO>(); 
		}
	}

	public List<AdministradorDTO> findByContrasenia(String contrasenia) {
		Optional<List<Administrador>> encontrados = administradorRep.findByContrasenia(contrasenia);
		List<Administrador> entityList = encontrados.get();
		List<AdministradorDTO> dtoList = new ArrayList<>();
		
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			entityList.forEach((entity) -> {
				AdministradorDTO dto = mapper.map(entity, AdministradorDTO.class);
				dtoList.add(dto);
			});
			return dtoList;
		} else {
			return new ArrayList<AdministradorDTO>(); 
		}
	}



	public List<AdministradorDTO> findByNombreAndCedula(String nombre, String cedula) {
		Optional<List<Administrador>> encontrados = administradorRep.findByNombreAndCedula(nombre, cedula);
		List<Administrador> entityList = encontrados.get();
		List<AdministradorDTO> dtoList = new ArrayList<>();
		
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			entityList.forEach((entity) -> {
				AdministradorDTO dto = mapper.map(entity, AdministradorDTO.class);
				dtoList.add(dto);
			});
			return dtoList;
		} else {
			return new ArrayList<AdministradorDTO>();
		}
	}

	public List<AdministradorDTO> findByUsuarioAndContrasenia(String usuario, String contrasenia) {
		Optional<List<Administrador>> encontrados = administradorRep.findByUsuarioAndContrasenia(usuario, contrasenia);
		List<Administrador> entityList = encontrados.get();
		List<AdministradorDTO> dtoList = new ArrayList<>();
		
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			entityList.forEach((entity) -> {
				AdministradorDTO dto = mapper.map(entity, AdministradorDTO.class);
				dtoList.add(dto);
			});
			return dtoList;
		} else {
			return new ArrayList<AdministradorDTO>(); 
		}
	}

}
