package co.edu.unbosque.mensajeria.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.mensajeria.dto.ClienteConcurrenteDTO;
import co.edu.unbosque.mensajeria.entity.ClienteConcurrente;
import co.edu.unbosque.mensajeria.repository.ClienteConcurrenteRepository;
import co.edu.unbosque.mensajeria.util.LanzadorDeException;

@Service
public class ClienteConcurrenteService implements CRUDOperation<ClienteConcurrenteDTO> {

	@Autowired
	private ClienteConcurrenteRepository clienteConcurrenteRep;

	@Autowired
	private ModelMapper mapper;

	public ClienteConcurrenteService() {

	}

	@Override
	public int create(ClienteConcurrenteDTO data) {
		
		LanzadorDeException.verificarCedula(data.getCedula());
		if(clienteConcurrenteRep.existByCedula(data.getCedula())) {
			return 1;
		}
		
		LanzadorDeException.verificarNombre(data.getNombre());
		LanzadorDeException.verificarCorreoElectronico(data.getCorreo());
		LanzadorDeException.verificarTelefono(data.getTelefono());
		LanzadorDeException.verificarMetodoPago(data.getMetodoPago());
		LanzadorDeException.verificarTipoPedido(data.getTipoPedido());
		
		ClienteConcurrente entity = mapper.map(data, ClienteConcurrente.class);
		clienteConcurrenteRep.save(entity);
		return 0;
	}

	@Override
	public List<ClienteConcurrenteDTO> getAll() {
		List<ClienteConcurrente> entityList = (List<ClienteConcurrente>) clienteConcurrenteRep.findAll();
		List<ClienteConcurrenteDTO> dtoList = new ArrayList<>();

		entityList.forEach((entity) -> {
			ClienteConcurrenteDTO dto = mapper.map(entity, ClienteConcurrenteDTO.class);
			dtoList.add(dto);
		});

		return dtoList;
	}

	@Override
	public int deleteById(Long id) {
		LanzadorDeException.verificarId(id);
		Optional<ClienteConcurrente> encontrado = clienteConcurrenteRep.findById(id);

		if (encontrado.isPresent()) {

			clienteConcurrenteRep.delete(encontrado.get());
			return 0;
		}
		return 1;

	}

	@Override
	public int updateById(Long id, ClienteConcurrenteDTO data) {

		LanzadorDeException.verificarNombre(data.getNombre());
		LanzadorDeException.verificarCedula(data.getCedula());
		LanzadorDeException.verificarCorreoElectronico(data.getCorreo());
		LanzadorDeException.verificarTelefono(data.getTelefono());
		LanzadorDeException.verificarMetodoPago(data.getMetodoPago());
		LanzadorDeException.verificarTipoPedido(data.getTipoPedido());
		LanzadorDeException.verificarId(id);
		
		Optional<ClienteConcurrente> encontrado = clienteConcurrenteRep.findById(id);

		if (encontrado.isPresent()) {

			ClienteConcurrente temp = encontrado.get();

			temp.setNombre(data.getNombre());
			temp.setCedula(data.getCedula());
			temp.setCorreo(data.getCorreo());
			temp.setTelefono(data.getTelefono());
			temp.setTipoPedido(data.getTipoPedido());
			temp.setMetodoPago(data.getMetodoPago());
			temp.setTarifaConcurrente(data.getTarifaConcurrente());

			clienteConcurrenteRep.save(temp);
			return 0;

		}

		return 1;
	}

	@Override
	public long count() {
		return clienteConcurrenteRep.count();
	}

	@Override
	public boolean exist(Long id) {
		return clienteConcurrenteRep.existsById(id) ? true : false;
	}

	public List<ClienteConcurrenteDTO> findByNombre(String nombre) {

		Optional<List<ClienteConcurrente>> encontrados = clienteConcurrenteRep.findByNombre(nombre);

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {

			List<ClienteConcurrente> entityList = encontrados.get();
			List<ClienteConcurrenteDTO> dtoList = new ArrayList<>();

			entityList.forEach(entity -> {
				dtoList.add(mapper.map(entity, ClienteConcurrenteDTO.class));
			});

			return dtoList;

		} else {
			return new ArrayList<ClienteConcurrenteDTO>();
		}
	}

	public List<ClienteConcurrenteDTO> findByCedula(String cedula) {

		Optional<List<ClienteConcurrente>> encontrados = clienteConcurrenteRep.findByCedula(cedula);

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {

			List<ClienteConcurrente> entityList = encontrados.get();
			List<ClienteConcurrenteDTO> dtoList = new ArrayList<>();

			entityList.forEach(entity -> {
				dtoList.add(mapper.map(entity, ClienteConcurrenteDTO.class));
			});

			return dtoList;

		} else {
			return new ArrayList<ClienteConcurrenteDTO>();
		}
	}

	public List<ClienteConcurrenteDTO> findByCorreo(String correo) {

		Optional<List<ClienteConcurrente>> encontrados = clienteConcurrenteRep.findByCorreo(correo);

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {

			List<ClienteConcurrente> entityList = encontrados.get();
			List<ClienteConcurrenteDTO> dtoList = new ArrayList<>();

			entityList.forEach(entity -> {
				dtoList.add(mapper.map(entity, ClienteConcurrenteDTO.class));
			});

			return dtoList;

		} else {
			return new ArrayList<ClienteConcurrenteDTO>();
		}
	}

	public List<ClienteConcurrenteDTO> findByTelefono(String telefono) {

		Optional<List<ClienteConcurrente>> encontrados = clienteConcurrenteRep.findByTelefono(telefono);

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {

			List<ClienteConcurrente> entityList = encontrados.get();
			List<ClienteConcurrenteDTO> dtoList = new ArrayList<>();

			entityList.forEach(entity -> {
				dtoList.add(mapper.map(entity, ClienteConcurrenteDTO.class));
			});

			return dtoList;

		} else {
			return new ArrayList<ClienteConcurrenteDTO>();
		}
	}

	public List<ClienteConcurrenteDTO> findByMetodoPago(String metodoPago) {

		Optional<List<ClienteConcurrente>> encontrados = clienteConcurrenteRep.findByMetodoPago(metodoPago);

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {

			List<ClienteConcurrente> entityList = encontrados.get();
			List<ClienteConcurrenteDTO> dtoList = new ArrayList<>();

			entityList.forEach(entity -> {
				dtoList.add(mapper.map(entity, ClienteConcurrenteDTO.class));
			});

			return dtoList;

		} else {
			return new ArrayList<ClienteConcurrenteDTO>();
		}
	}

	public List<ClienteConcurrenteDTO> findByTipoPedido(String tipoPedido) {

		Optional<List<ClienteConcurrente>> encontrados = clienteConcurrenteRep.findByTipoPedido(tipoPedido);

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {

			List<ClienteConcurrente> entityList = encontrados.get();
			List<ClienteConcurrenteDTO> dtoList = new ArrayList<>();

			entityList.forEach(entity -> {
				dtoList.add(mapper.map(entity, ClienteConcurrenteDTO.class));
			});

			return dtoList;

		} else {
			return new ArrayList<ClienteConcurrenteDTO>();
		}
	}

	public List<ClienteConcurrenteDTO> findByNombreAndCedula(String nombre, String cedula) {

		Optional<List<ClienteConcurrente>> encontrados = clienteConcurrenteRep.findByNombreAndCedula(nombre, cedula);

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {

			List<ClienteConcurrente> entityList = encontrados.get();
			List<ClienteConcurrenteDTO> dtoList = new ArrayList<>();

			entityList.forEach(entity -> {
				dtoList.add(mapper.map(entity, ClienteConcurrenteDTO.class));
			});

			return dtoList;

		} else {
			return new ArrayList<ClienteConcurrenteDTO>();
		}

	}

	public List<ClienteConcurrenteDTO> findByTipoPedidoAndMetodoPago(String tipoPedido, String metodoPago) {

		Optional<List<ClienteConcurrente>> encontrados = clienteConcurrenteRep.findByTipoPedidoAndMetodoPago(tipoPedido,
				metodoPago);

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {

			List<ClienteConcurrente> entityList = encontrados.get();
			List<ClienteConcurrenteDTO> dtoList = new ArrayList<>();

			entityList.forEach(entity -> {
				dtoList.add(mapper.map(entity, ClienteConcurrenteDTO.class));
			});

			return dtoList;

		} else {
			return new ArrayList<ClienteConcurrenteDTO>();
		}
	}
}