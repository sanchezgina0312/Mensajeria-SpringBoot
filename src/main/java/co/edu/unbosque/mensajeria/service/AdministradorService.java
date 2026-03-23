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
		LanzadorDeException.verificarCedula(data.getCedula());
		LanzadorDeException.verificarNombre(data.getNombre());
		LanzadorDeException.verificarCorreoElectronico(data.getCorreo());
		LanzadorDeException.verificarTelefono(data.getTelefono());
		LanzadorDeException.verificarTurno(data.getTurno());

		LanzadorDeException.verificarDuplicado(administradorRep.existsByCedula(data.getCedula()), "La cédula " + data.getCedula() + " ya se encuentra registrada.");

		if (administradorRep.existsByCedula(data.getCedula())) {
			return 1;
		} else {
			Administrador entity = mapper.map(data, Administrador.class);
			administradorRep.save(entity);
			return 0;
		}
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
		} else {
			return 1;
		}
	}

	@Override
	public int updateById(Long id, AdministradorDTO data) {
		LanzadorDeException.verificarId(id);
		LanzadorDeException.verificarNombre(data.getNombre());
		LanzadorDeException.verificarCedula(data.getCedula());
		LanzadorDeException.verificarCorreoElectronico(data.getCorreo());
		LanzadorDeException.verificarTelefono(data.getTelefono());
		LanzadorDeException.verificarTurno(data.getTurno());

		Optional<Administrador> encontrado = administradorRep.findById(id);

		if (encontrado.isPresent()) {
			Administrador adminActual = encontrado.get();
			if (!adminActual.getCedula().equals(data.getCedula())) {
				LanzadorDeException.verificarDuplicado(administradorRep.existsByCedula(data.getCedula()), "No se puede actualizar: la cédula " + data.getCedula() + " ya pertenece a otro administrador.");

				if (administradorRep.existsByCedula(data.getCedula())) {
					return 1;
				}
			}

			adminActual.setNombre(data.getNombre());
			adminActual.setCedula(data.getCedula());
			adminActual.setCorreo(data.getCorreo());
			adminActual.setTelefono(data.getTelefono());
			adminActual.setTurno(data.getTurno());
			adminActual.setUsuario(data.getUsuario());
			adminActual.setContrasenia(data.getContrasenia());
			administradorRep.save(adminActual);
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public long count() {
		return administradorRep.count();
	}

	@Override
	public boolean exist(Long id) {
		LanzadorDeException.verificarId(id);
		if (administradorRep.existsById(id)) {
			return true;
		} else {
			return false;
		}
	}

	public List<AdministradorDTO> findByNombre(String nombre) {
		LanzadorDeException.verificarNombre(nombre);
		Optional<List<Administrador>> encontrados = administradorRep.findByNombre(nombre);
		List<AdministradorDTO> dtoList = new ArrayList<>();

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> {
				dtoList.add(mapper.map(entity, AdministradorDTO.class));
			});
			return dtoList;
		} else {
			return new ArrayList<AdministradorDTO>();
		}
	}

	public List<AdministradorDTO> findByCedula(String cedula) {
		LanzadorDeException.verificarCedula(cedula);
		Optional<List<Administrador>> encontrados = administradorRep.findByCedula(cedula);
		List<AdministradorDTO> dtoList = new ArrayList<>();

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> {
				dtoList.add(mapper.map(entity, AdministradorDTO.class));
			});
			return dtoList;
		} else {
			return new ArrayList<AdministradorDTO>();
		}
	}

	public List<AdministradorDTO> findByCorreo(String correo) {
		LanzadorDeException.verificarCorreoElectronico(correo);
		Optional<List<Administrador>> encontrados = administradorRep.findByCorreo(correo);
		List<AdministradorDTO> dtoList = new ArrayList<>();

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> {
				dtoList.add(mapper.map(entity, AdministradorDTO.class));
			});
			return dtoList;
		} else {
			return new ArrayList<AdministradorDTO>();
		}
	}

	public List<AdministradorDTO> findByTelefono(String telefono) {
		LanzadorDeException.verificarTelefono(telefono);
		Optional<List<Administrador>> encontrados = administradorRep.findByTelefono(telefono);
		List<AdministradorDTO> dtoList = new ArrayList<>();

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> {
				dtoList.add(mapper.map(entity, AdministradorDTO.class));
			});
			return dtoList;
		} else {
			return new ArrayList<AdministradorDTO>();
		}
	}

	public List<AdministradorDTO> findByUsuario(String usuario) {
		Optional<List<Administrador>> encontrados = administradorRep.findByUsuario(usuario);
		List<AdministradorDTO> dtoList = new ArrayList<>();

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> {
				dtoList.add(mapper.map(entity, AdministradorDTO.class));
			});
			return dtoList;
		} else {
			return new ArrayList<AdministradorDTO>();
		}
	}

	public List<AdministradorDTO> findByContrasenia(String contrasenia) {
		Optional<List<Administrador>> encontrados = administradorRep.findByContrasenia(contrasenia);
		List<AdministradorDTO> dtoList = new ArrayList<>();

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> {
				dtoList.add(mapper.map(entity, AdministradorDTO.class));
			});
			return dtoList;
		} else {
			return new ArrayList<AdministradorDTO>();
		}
	}

	public List<AdministradorDTO> findByNombreAndCedula(String nombre, String cedula) {
		LanzadorDeException.verificarNombre(nombre);
		LanzadorDeException.verificarCedula(cedula);
		Optional<List<Administrador>> encontrados = administradorRep.findByNombreAndCedula(nombre, cedula);
		List<AdministradorDTO> dtoList = new ArrayList<>();

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> {
				dtoList.add(mapper.map(entity, AdministradorDTO.class));
			});
			return dtoList;
		} else {
			return new ArrayList<AdministradorDTO>();
		}
	}

	public List<AdministradorDTO> findByUsuarioAndContrasenia(String usuario, String contrasenia) {
		Optional<List<Administrador>> encontrados = administradorRep.findByUsuarioAndContrasenia(usuario, contrasenia);
		List<AdministradorDTO> dtoList = new ArrayList<>();

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> {
				dtoList.add(mapper.map(entity, AdministradorDTO.class));
			});
			return dtoList;
		} else {
			return new ArrayList<AdministradorDTO>();
		}
	}
}