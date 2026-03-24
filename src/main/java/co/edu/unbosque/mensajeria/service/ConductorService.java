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

/**
 * Servicio encargado de gestionar las operaciones CRUD de los conductores.
 * <p>
 * Permite crear, consultar, actualizar y eliminar conductores, así como
 * realizar búsquedas por diferentes atributos como nombre, cédula, correo,
 * teléfono y placa del vehículo.
 * Utiliza ModelMapper para la conversión entre entidades y DTOs.
 * </p>
 * 
 * @author Angie Villarreal
 */
@Service
public class ConductorService implements CRUDOperation<ConductorDTO> {

	@Autowired
	private ConductorRepository conductorRep;

	@Autowired
	private ModelMapper mapper;

	/**
	 * Constructor por defecto.
	 */
	public ConductorService() {
	}

	/**
	 * Crea un nuevo conductor.
	 * 
	 * @param data datos del conductor
	 * @return 0 si se crea correctamente, 1 si ya existe
	 */
	@Override
	public int create(ConductorDTO data) {
		LanzadorDeException.verificarCedula(data.getCedula());
		LanzadorDeException.verificarNombre(data.getNombre());
		LanzadorDeException.verificarCorreoElectronico(data.getCorreo());
		LanzadorDeException.verificarTelefono(data.getTelefono());
		LanzadorDeException.verificarPlaca(data.getPlacaVehiculo());

		LanzadorDeException.verificarDuplicado(
				conductorRep.existsByCedula(data.getCedula()),
				"La cédula " + data.getCedula() + " ya se encuentra registrada para un conductor.");

		if (conductorRep.existsByCedula(data.getCedula())) {
			return 1;
		} else {
			Conductor entity = mapper.map(data, Conductor.class);
			conductorRep.save(entity);
			return 0;
		}
	}

	/**
	 * Obtiene todos los conductores.
	 * 
	 * @return lista de conductores
	 */
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

	/**
	 * Elimina un conductor por ID.
	 * 
	 * @param id identificador del conductor
	 * @return 0 si se elimina, 1 si no existe
	 */
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

	/**
	 * Actualiza un conductor existente.
	 * 
	 * @param id identificador del conductor
	 * @param data nuevos datos
	 * @return 0 si se actualiza correctamente, 1 si hay error
	 */
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
				LanzadorDeException.verificarDuplicado(
						conductorRep.existsByCedula(data.getCedula()),
						"No se puede actualizar: la cédula " + data.getCedula()
								+ " ya pertenece a otro conductor.");

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

	/**
	 * Cuenta los conductores registrados.
	 * 
	 * @return total de conductores
	 */
	@Override
	public long count() {
		return conductorRep.count();
	}

	/**
	 * Verifica si existe un conductor por ID.
	 * 
	 * @param id identificador
	 * @return true si existe, false si no
	 */
	@Override
	public boolean exist(Long id) {
		LanzadorDeException.verificarId(id);
		return conductorRep.existsById(id);
	}

	/**
	 * Busca conductores por nombre.
	 */
	public List<ConductorDTO> findByNombre(String nombre) {
		LanzadorDeException.verificarNombre(nombre);
		Optional<List<Conductor>> encontrados = conductorRep.findByNombre(nombre);
		List<ConductorDTO> dtoList = new ArrayList<>();
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> dtoList.add(mapper.map(entity, ConductorDTO.class)));
		}
		return dtoList;
	}

	/**
	 * Busca conductores por cédula.
	 */
	public List<ConductorDTO> findByCedula(String cedula) {
		LanzadorDeException.verificarCedula(cedula);
		Optional<List<Conductor>> encontrados = conductorRep.findByCedula(cedula);
		List<ConductorDTO> dtoList = new ArrayList<>();
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> dtoList.add(mapper.map(entity, ConductorDTO.class)));
		}
		return dtoList;
	}

	/**
	 * Busca conductores por correo.
	 */
	public List<ConductorDTO> findByCorreo(String correo) {
		LanzadorDeException.verificarCorreoElectronico(correo);
		Optional<List<Conductor>> encontrados = conductorRep.findByCorreo(correo);
		List<ConductorDTO> dtoList = new ArrayList<>();
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> dtoList.add(mapper.map(entity, ConductorDTO.class)));
		}
		return dtoList;
	}

	/**
	 * Busca conductores por teléfono.
	 */
	public List<ConductorDTO> findByTelefono(String telefono) {
		LanzadorDeException.verificarTelefono(telefono);
		Optional<List<Conductor>> encontrados = conductorRep.findByTelefono(telefono);
		List<ConductorDTO> dtoList = new ArrayList<>();
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> dtoList.add(mapper.map(entity, ConductorDTO.class)));
		}
		return dtoList;
	}

	/**
	 * Busca conductores por placa del vehículo.
	 */
	public List<ConductorDTO> findByPlacaVehiculo(String placa) {
		LanzadorDeException.verificarPlaca(placa);
		Optional<List<Conductor>> encontrados = conductorRep.findByPlacaVehiculo(placa);
		List<ConductorDTO> dtoList = new ArrayList<>();
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> dtoList.add(mapper.map(entity, ConductorDTO.class)));
		}
		return dtoList;
	}

	/**
	 * Busca conductores por nombre y cédula.
	 */
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

	/**
	 * Permite inyectar el repositorio manualmente (testing).
	 */
	public void setConductorRep(ConductorRepository repo) {
		this.conductorRep = repo;
	}

	/**
	 * Permite inyectar el mapper manualmente (testing).
	 */
	public void setMapper(ModelMapper mapper) {
		this.mapper = mapper;
	}
}