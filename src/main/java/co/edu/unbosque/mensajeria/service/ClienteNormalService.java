package co.edu.unbosque.mensajeria.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.mensajeria.dto.ClienteNormalDTO;
import co.edu.unbosque.mensajeria.entity.ClienteNormal;
import co.edu.unbosque.mensajeria.repository.ClienteNormalRepository;
import co.edu.unbosque.mensajeria.util.LanzadorDeException;

/**
 * Servicio encargado de gestionar las operaciones CRUD de la entidad ClienteNormal.
 */
@Service
public class ClienteNormalService implements CRUDOperation<ClienteNormalDTO> {

	@Autowired
	private ClienteNormalRepository clienteNormalRep;

	@Autowired
	private ModelMapper mapper;

	/**
	 * MÉTODO AGREGADO: Valida las credenciales para el inicio de sesión.
	 * * @param cedula Cédula ingresada.
	 * @param contrasenia Contraseña ingresada.
	 * @return ClienteNormalDTO si es válido, null en caso contrario.
	 */
	public ClienteNormalDTO validarLogin(String cedula, String contrasenia) {
		// Buscamos en el repositorio por cédula
		Optional<List<ClienteNormal>> encontrados = clienteNormalRep.findByCedula(cedula);

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			ClienteNormal cliente = encontrados.get().get(0); // Tomamos el primer resultado
			
			// Verificamos si la contraseña coincide
			if (cliente.getContrasenia().equals(contrasenia)) {
				return mapper.map(cliente, ClienteNormalDTO.class);
			}
		}
		return null; // Credenciales inválidas
	}

	/**
	 * Crea un nuevo cliente normal.
	 */
	@Override
	public int create(ClienteNormalDTO data) {
		LanzadorDeException.verificarCedula(data.getCedula());
		LanzadorDeException.verificarNombre(data.getNombre());
		LanzadorDeException.verificarCorreoElectronico(data.getCorreo());
		LanzadorDeException.verificarTelefono(data.getTelefono());
		LanzadorDeException.verificarContrasena(data.getContrasenia());

		if (clienteNormalRep.existsByCedula(data.getCedula())) {
			return 1;
		} else {
			ClienteNormal entity = mapper.map(data, ClienteNormal.class);
			// Valores por defecto
			entity.setMetodoPago("Tarjeta De Crédito");
			clienteNormalRep.save(entity);
			return 0;
		}
	}

	@Override
	public List<ClienteNormalDTO> getAll() {
		List<ClienteNormal> entityList = (List<ClienteNormal>) clienteNormalRep.findAll();
		List<ClienteNormalDTO> dtoList = new ArrayList<>();
		entityList.forEach(entity -> dtoList.add(mapper.map(entity, ClienteNormalDTO.class)));
		return dtoList;
	}

	@Override
	public int deleteById(Long id) {
		LanzadorDeException.verificarId(id);
		Optional<ClienteNormal> encontrado = clienteNormalRep.findById(id);
		if (encontrado.isPresent()) {
			clienteNormalRep.delete(encontrado.get());
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int updateById(Long id, ClienteNormalDTO data) {
		LanzadorDeException.verificarId(id);
		Optional<ClienteNormal> encontrado = clienteNormalRep.findById(id);

		if (encontrado.isPresent()) {
			ClienteNormal temp = encontrado.get();
			temp.setNombre(data.getNombre());
			temp.setCedula(data.getCedula());
			temp.setCorreo(data.getCorreo());
			temp.setTelefono(data.getTelefono());
			temp.setMetodoPago(data.getMetodoPago());
			temp.setContrasenia(data.getContrasenia());

			clienteNormalRep.save(temp);
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public long count() {
		return clienteNormalRep.count();
	}

	@Override
	public boolean exist(Long id) {
		LanzadorDeException.verificarId(id);
		return clienteNormalRep.existsById(id);
	}

	public List<ClienteNormalDTO> findByNombre(String nombre) {
		Optional<List<ClienteNormal>> encontrados = clienteNormalRep.findByNombre(nombre);
		List<ClienteNormalDTO> dtoList = new ArrayList<>();
		encontrados.ifPresent(list -> list.forEach(e -> dtoList.add(mapper.map(e, ClienteNormalDTO.class))));
		return dtoList;
	}

	public List<ClienteNormalDTO> findByCedula(String cedula) {
		Optional<List<ClienteNormal>> encontrados = clienteNormalRep.findByCedula(cedula);
		List<ClienteNormalDTO> dtoList = new ArrayList<>();
		encontrados.ifPresent(list -> list.forEach(e -> dtoList.add(mapper.map(e, ClienteNormalDTO.class))));
		return dtoList;
	}

	public List<ClienteNormalDTO> findByCorreo(String correo) {
		Optional<List<ClienteNormal>> encontrados = clienteNormalRep.findByCorreo(correo);
		List<ClienteNormalDTO> dtoList = new ArrayList<>();
		encontrados.ifPresent(list -> list.forEach(e -> dtoList.add(mapper.map(e, ClienteNormalDTO.class))));
		return dtoList;
	}

	public List<ClienteNormalDTO> findByTelefono(String telefono) {
		Optional<List<ClienteNormal>> encontrados = clienteNormalRep.findByTelefono(telefono);
		List<ClienteNormalDTO> dtoList = new ArrayList<>();
		encontrados.ifPresent(list -> list.forEach(e -> dtoList.add(mapper.map(e, ClienteNormalDTO.class))));
		return dtoList;
	}

	public List<ClienteNormalDTO> findByMetodoPago(String metodoPago) {
		Optional<List<ClienteNormal>> encontrados = clienteNormalRep.findByMetodoPago(metodoPago);
		List<ClienteNormalDTO> dtoList = new ArrayList<>();
		encontrados.ifPresent(list -> list.forEach(e -> dtoList.add(mapper.map(e, ClienteNormalDTO.class))));
		return dtoList;
	}

	public List<ClienteNormalDTO> findByNombreAndCedula(String nombre, String cedula) {
		Optional<List<ClienteNormal>> encontrados = clienteNormalRep.findByNombreAndCedula(nombre, cedula);
		List<ClienteNormalDTO> dtoList = new ArrayList<>();
		encontrados.ifPresent(list -> list.forEach(e -> dtoList.add(mapper.map(e, ClienteNormalDTO.class))));
		return dtoList;
	}

	public void setClienteNormalRep(ClienteNormalRepository repo) {
		this.clienteNormalRep = repo;
	}

	public void setMapper(ModelMapper mapper) {
		this.mapper = mapper;
	}
}