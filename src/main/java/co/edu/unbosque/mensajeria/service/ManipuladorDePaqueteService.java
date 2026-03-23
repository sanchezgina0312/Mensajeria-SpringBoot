package co.edu.unbosque.mensajeria.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unbosque.mensajeria.dto.ManipuladorDePaqueteDTO;
import co.edu.unbosque.mensajeria.entity.ManipuladorDePaquete;
import co.edu.unbosque.mensajeria.repository.ManipuladorDePaqueteRepository;
import co.edu.unbosque.mensajeria.util.LanzadorDeException;

@Service
public class ManipuladorDePaqueteService implements CRUDOperation<ManipuladorDePaqueteDTO> {

	@Autowired
	private ManipuladorDePaqueteRepository manipuladorRep;

	@Autowired
	private ModelMapper mapper;

	public ManipuladorDePaqueteService() {
	}

	@Override
	public int create(ManipuladorDePaqueteDTO data) {
		LanzadorDeException.verificarCedula(data.getCedula());
		LanzadorDeException.verificarNombre(data.getNombre());
		LanzadorDeException.verificarCorreoElectronico(data.getCorreo());
		LanzadorDeException.verificarTelefono(data.getTelefono());
		LanzadorDeException.verificarTipoManipulador(data.getTipoManipulador());

		LanzadorDeException.verificarDuplicado(manipuladorRep.existsByCedula(data.getCedula()), "La cédula " + data.getCedula() + " ya se encuentra registrada para un manipulador.");

		if (manipuladorRep.existsByCedula(data.getCedula())) {
			return 1;
		} else {
			ManipuladorDePaquete entity = mapper.map(data, ManipuladorDePaquete.class);
			manipuladorRep.save(entity);
			return 0;
		}
	}

	@Override
	public List<ManipuladorDePaqueteDTO> getAll() {
		List<ManipuladorDePaquete> entityList = (List<ManipuladorDePaquete>) manipuladorRep.findAll();
		List<ManipuladorDePaqueteDTO> dtoList = new ArrayList<>();
		entityList.forEach((entity) -> {
			dtoList.add(mapper.map(entity, ManipuladorDePaqueteDTO.class));
		});
		return dtoList;
	}

	@Override
	public int deleteById(Long id) {
		LanzadorDeException.verificarId(id);
		Optional<ManipuladorDePaquete> encontrado = manipuladorRep.findById(id);

		if (encontrado.isPresent()) {
			manipuladorRep.delete(encontrado.get());
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int updateById(Long id, ManipuladorDePaqueteDTO data) {
		LanzadorDeException.verificarId(id);
		LanzadorDeException.verificarNombre(data.getNombre());
		LanzadorDeException.verificarCedula(data.getCedula());
		LanzadorDeException.verificarCorreoElectronico(data.getCorreo());
		LanzadorDeException.verificarTelefono(data.getTelefono());
		LanzadorDeException.verificarTipoManipulador(data.getTipoManipulador());

		Optional<ManipuladorDePaquete> encontrado = manipuladorRep.findById(id);

		if (encontrado.isPresent()) {
			ManipuladorDePaquete temp = encontrado.get();

			if (!temp.getCedula().equals(data.getCedula())) {
				LanzadorDeException.verificarDuplicado(manipuladorRep.existsByCedula(data.getCedula()), "No se puede actualizar: la cédula " + data.getCedula() + " ya pertenece a otro manipulador.");
				if (manipuladorRep.existsByCedula(data.getCedula())) {
					return 1;
				}
			}

			temp.setNombre(data.getNombre());
			temp.setCedula(data.getCedula());
			temp.setCorreo(data.getCorreo());
			temp.setTelefono(data.getTelefono());
			temp.setTipoManipulador(data.getTipoManipulador());

			manipuladorRep.save(temp);
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public long count() {
		return manipuladorRep.count();
	}

	@Override
	public boolean exist(Long id) {
		LanzadorDeException.verificarId(id);
		return manipuladorRep.existsById(id);
	}

	public List<ManipuladorDePaqueteDTO> findByNombre(String nombre) {
		LanzadorDeException.verificarNombre(nombre);
		Optional<List<ManipuladorDePaquete>> encontrados = manipuladorRep.findByNombre(nombre);
		List<ManipuladorDePaqueteDTO> dtoList = new ArrayList<>();
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> dtoList.add(mapper.map(entity, ManipuladorDePaqueteDTO.class)));
		}
		return dtoList;
	}

	public List<ManipuladorDePaqueteDTO> findByCedula(String cedula) {
		LanzadorDeException.verificarCedula(cedula);
		Optional<List<ManipuladorDePaquete>> encontrados = manipuladorRep.findByCedula(cedula);
		List<ManipuladorDePaqueteDTO> dtoList = new ArrayList<>();
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> dtoList.add(mapper.map(entity, ManipuladorDePaqueteDTO.class)));
		}
		return dtoList;
	}

	public List<ManipuladorDePaqueteDTO> findByCorreo(String correo) {
		LanzadorDeException.verificarCorreoElectronico(correo);
		Optional<List<ManipuladorDePaquete>> encontrados = manipuladorRep.findByCorreo(correo);
		List<ManipuladorDePaqueteDTO> dtoList = new ArrayList<>();
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> dtoList.add(mapper.map(entity, ManipuladorDePaqueteDTO.class)));
		}
		return dtoList;
	}

	public List<ManipuladorDePaqueteDTO> findByTelefono(String telefono) {
		LanzadorDeException.verificarTelefono(telefono);
		Optional<List<ManipuladorDePaquete>> encontrados = manipuladorRep.findByTelefono(telefono);
		List<ManipuladorDePaqueteDTO> dtoList = new ArrayList<>();
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> dtoList.add(mapper.map(entity, ManipuladorDePaqueteDTO.class)));
		}
		return dtoList;
	}

	public List<ManipuladorDePaqueteDTO> findByTipoManipulador(String tipoManipulador) {
		LanzadorDeException.verificarTipoManipulador(tipoManipulador);
		Optional<List<ManipuladorDePaquete>> encontrados = manipuladorRep.findByTipoManipulador(tipoManipulador);
		List<ManipuladorDePaqueteDTO> dtoList = new ArrayList<>();
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> dtoList.add(mapper.map(entity, ManipuladorDePaqueteDTO.class)));
		}
		return dtoList;
	}

	public List<ManipuladorDePaqueteDTO> findByNombreAndCedula(String nombre, String cedula) {
		LanzadorDeException.verificarNombre(nombre);
		LanzadorDeException.verificarCedula(cedula);
		Optional<List<ManipuladorDePaquete>> encontrados = manipuladorRep.findByNombreAndCedula(nombre, cedula);
		List<ManipuladorDePaqueteDTO> dtoList = new ArrayList<>();
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> dtoList.add(mapper.map(entity, ManipuladorDePaqueteDTO.class)));
		}
		return dtoList;
	}
}