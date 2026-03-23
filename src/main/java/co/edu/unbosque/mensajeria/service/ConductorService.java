package co.edu.unbosque.mensajeria.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.mensajeria.dto.ConductorDTO;
import co.edu.unbosque.mensajeria.entity.Conductor;
import co.edu.unbosque.mensajeria.repository.ConductorRepository;
import co.edu.unbosque.mensajeria.util.LanzadorDeException;

@Service
public class ConductorService implements CRUDOperation<ConductorDTO> {

	@Autowired
	private ConductorRepository conductorRep;

	@Autowired
	private ModelMapper mapper;

	public ConductorService() {
	}

	@Override
	public int create(ConductorDTO data) {
		LanzadorDeException.verificarCedula(data.getCedula());
		LanzadorDeException.verificarNombre(data.getNombre());
		LanzadorDeException.verificarCorreoElectronico(data.getCorreo());
		LanzadorDeException.verificarTelefono(data.getTelefono());
		LanzadorDeException.verificarPlaca(data.getPlacaVehiculo());

		LanzadorDeException.verificarDuplicado(conductorRep.existsByCedula(data.getCedula()), "La cédula " + data.getCedula() + " ya se encuentra registrada para un conductor.");

		if (conductorRep.existsByCedula(data.getCedula())) {
			return 1;
		} else {
			Conductor entity = mapper.map(data, Conductor.class);
			conductorRep.save(entity);
			return 0;
		}
	}

	@Override
	public List<ConductorDTO> getAll() {
		List<Conductor> entityList = (List<Conductor>) conductorRep.findAll();
		List<ConductorDTO> dtoList = new ArrayList<>();
		entityList.forEach((entity) -> {
			ConductorDTO dto = mapper.map(entity, ConductorDTO.class);
			dtoList.add(dto);
		});
		return dtoList;
	}

	@Override
	public int deleteById(Long id) {
		LanzadorDeException.verificarId(id);
		Optional<Conductor> encontrado = conductorRep.findById(id);

		if (encontrado.isPresent()) {
			conductorRep.delete(encontrado.get());
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int updateById(Long id, ConductorDTO data) {
		LanzadorDeException.verificarId(id);
		LanzadorDeException.verificarNombre(data.getNombre());
		LanzadorDeException.verificarCedula(data.getCedula());
		LanzadorDeException.verificarCorreoElectronico(data.getCorreo());
		LanzadorDeException.verificarTelefono(data.getTelefono());
		LanzadorDeException.verificarPlaca(data.getPlacaVehiculo());

		Optional<Conductor> encontrado = conductorRep.findById(id);

		if (encontrado.isPresent()) {
			Conductor temp = encontrado.get();

			if (!temp.getCedula().equals(data.getCedula())) {
				LanzadorDeException.verificarDuplicado(conductorRep.existsByCedula(data.getCedula()),"No se puede actualizar: la cédula " + data.getCedula() + " ya pertenece a otro conductor.");
				if (conductorRep.existsByCedula(data.getCedula())) {
					return 1;
				}
			}

			temp.setNombre(data.getNombre());
			temp.setCedula(data.getCedula());
			temp.setCorreo(data.getCorreo());
			temp.setTelefono(data.getTelefono());
			temp.setPlacaVehiculo(data.getPlacaVehiculo());

			conductorRep.save(temp);
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public long count() {
		return conductorRep.count();
	}

	@Override
	public boolean exist(Long id) {
		LanzadorDeException.verificarId(id);
		return conductorRep.existsById(id);
	}

	public List<ConductorDTO> findByNombre(String nombre) {
		LanzadorDeException.verificarNombre(nombre);
		Optional<List<Conductor>> encontrados = conductorRep.findByNombre(nombre);
		List<ConductorDTO> dtoList = new ArrayList<>();
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> dtoList.add(mapper.map(entity, ConductorDTO.class)));
		}
		return dtoList;
	}

	public List<ConductorDTO> findByCedula(String cedula) {
		LanzadorDeException.verificarCedula(cedula);
		Optional<List<Conductor>> encontrados = conductorRep.findByCedula(cedula);
		List<ConductorDTO> dtoList = new ArrayList<>();
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> dtoList.add(mapper.map(entity, ConductorDTO.class)));
		}
		return dtoList;
	}

	public List<ConductorDTO> findByCorreo(String correo) {
		LanzadorDeException.verificarCorreoElectronico(correo);
		Optional<List<Conductor>> encontrados = conductorRep.findByCorreo(correo);
		List<ConductorDTO> dtoList = new ArrayList<>();
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> dtoList.add(mapper.map(entity, ConductorDTO.class)));
		}
		return dtoList;
	}

	public List<ConductorDTO> findByTelefono(String telefono) {
		LanzadorDeException.verificarTelefono(telefono);
		Optional<List<Conductor>> encontrados = conductorRep.findByTelefono(telefono);
		List<ConductorDTO> dtoList = new ArrayList<>();
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> dtoList.add(mapper.map(entity, ConductorDTO.class)));
		}
		return dtoList;
	}

	public List<ConductorDTO> findByPlacaVehiculo(String placa) {
		LanzadorDeException.verificarPlaca(placa);
		Optional<List<Conductor>> encontrados = conductorRep.findByPlacaVehiculo(placa);
		List<ConductorDTO> dtoList = new ArrayList<>();
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> dtoList.add(mapper.map(entity, ConductorDTO.class)));
		}
		return dtoList;
	}

	public List<ConductorDTO> findByNombreAndCedula(String nombre, String cedula) {
		LanzadorDeException.verificarNombre(nombre);
		LanzadorDeException.verificarCedula(cedula);
		Optional<List<Conductor>> encontrados = conductorRep.findByNombreAndCedula(nombre, cedula);
		List<ConductorDTO> dtoList = new ArrayList<>();
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> dtoList.add(mapper.map(entity, ConductorDTO.class)));
		}
		return dtoList;
	}


}