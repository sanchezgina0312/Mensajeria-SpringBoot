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

@Service
public class ClienteNormalService implements CRUDOperation<ClienteNormalDTO> {

	@Autowired
	private ClienteNormalRepository clienteNormalRep;

	@Autowired
	private ModelMapper mapper;

	@Override
	public int create(ClienteNormalDTO data) {
		LanzadorDeException.verificarNombre(data.getNombre());
		LanzadorDeException.verificarCedula(data.getCedula());
		LanzadorDeException.verificarCorreoElectronico(data.getCorreo());
		LanzadorDeException.verificarTelefono(data.getTelefono());
		LanzadorDeException.verificarMetodoPago(data.getMetodoPago());
		LanzadorDeException.verificarTipoPedido(data.getTipoPedido());
		ClienteNormal entity = mapper.map(data, ClienteNormal.class);
		clienteNormalRep.save(entity);
		return 0;
	}

	@Override
	public List<ClienteNormalDTO> getAll() {
		List<ClienteNormal> entityList = (List<ClienteNormal>) clienteNormalRep.findAll();
		List<ClienteNormalDTO> dtoList = new ArrayList<>();

		entityList.forEach((entity) -> {
			ClienteNormalDTO dto = mapper.map(entity, ClienteNormalDTO.class);
			dtoList.add(dto);
		});

		return dtoList;

	}

	@Override
	public int deleteById(Long id) {
		LanzadorDeException.verificarId(id);
		Optional<ClienteNormal> encontrado = clienteNormalRep.findById(id);

		if (encontrado.isPresent()) {
			clienteNormalRep.delete(encontrado.get());
			return 0;
		}
		return 1;
	}

	@Override
	public int updateById(Long id, ClienteNormalDTO data) {
		Optional<ClienteNormal> encontrado = clienteNormalRep.findById(id);

		if (encontrado.isPresent()) {

			ClienteNormal temp = encontrado.get();

			temp.setNombre(data.getNombre());
			temp.setCedula(data.getCedula());
			temp.setCorreo(data.getCorreo());
			temp.setTelefono(data.getTelefono());
			temp.setTipoPedido(data.getTipoPedido());
			temp.setMetodoPago(data.getMetodoPago());
			temp.setTarifaNormal(data.getTarifaNormal());

			clienteNormalRep.save(temp);
			return 0;
		}

		return 1;
	}

	@Override
	public long count() {
		return clienteNormalRep.count();
	}

	@Override
	public boolean exist(Long id) {
		return clienteNormalRep.existsById(id) ? true : false;
	}

	public List<ClienteNormalDTO> findByNombre(String nombre) {

		Optional<List<ClienteNormal>> encontrados = clienteNormalRep.findByNombre(nombre);

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {

			List<ClienteNormal> entityList = encontrados.get();
			List<ClienteNormalDTO> dtoList = new ArrayList<>();

			entityList.forEach((entity) -> {
				ClienteNormalDTO dto = mapper.map(entity, ClienteNormalDTO.class);
				dtoList.add(dto);
			});

			return dtoList;
		} else {

			return new ArrayList<ClienteNormalDTO>();

		}

	}

	public List<ClienteNormalDTO> findByCedula(String cedula) {

		Optional<List<ClienteNormal>> encontrados = clienteNormalRep.findByCedula(cedula);

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {

			List<ClienteNormal> entityList = encontrados.get();
			List<ClienteNormalDTO> dtoList = new ArrayList<>();

			entityList.forEach((entity) -> {
				ClienteNormalDTO dto = mapper.map(entity, ClienteNormalDTO.class);
				dtoList.add(dto);
			});

			return dtoList;

		} else {
			return new ArrayList<ClienteNormalDTO>();
		}

	}

	public List<ClienteNormalDTO> findByCorreo(String correo) {

		Optional<List<ClienteNormal>> encontrados = clienteNormalRep.findByCorreo(correo);

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {

			List<ClienteNormal> entityList = encontrados.get();
			List<ClienteNormalDTO> dtoList = new ArrayList<>();

			entityList.forEach((entity) -> {
				ClienteNormalDTO dto = mapper.map(entity, ClienteNormalDTO.class);
				dtoList.add(dto);
			});

			return dtoList;

		} else {
			return new ArrayList<ClienteNormalDTO>();
		}
	}

	public List<ClienteNormalDTO> findByTelefono(String telefono) {

		Optional<List<ClienteNormal>> encontrados = clienteNormalRep.findByTelefono(telefono);

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {

			List<ClienteNormal> entityList = encontrados.get();
			List<ClienteNormalDTO> dtoList = new ArrayList<>();

			entityList.forEach((entity) -> {
				ClienteNormalDTO dto = mapper.map(entity, ClienteNormalDTO.class);
				dtoList.add(dto);
			});

			return dtoList;

		} else {
			return new ArrayList<ClienteNormalDTO>();
		}
	}

	public List<ClienteNormalDTO> findByMetodoPago(String metodoPago) {

		Optional<List<ClienteNormal>> encontrados = clienteNormalRep.findByMetodoPago(metodoPago);

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {

			List<ClienteNormal> entityList = encontrados.get();
			List<ClienteNormalDTO> dtoList = new ArrayList<>();

			entityList.forEach((entity) -> {
				ClienteNormalDTO dto = mapper.map(entity, ClienteNormalDTO.class);
				dtoList.add(dto);
			});

			return dtoList;

		} else {
			return new ArrayList<ClienteNormalDTO>();
		}
	}

	public List<ClienteNormalDTO> findByTipoPedido(String tipoPedido) {

		Optional<List<ClienteNormal>> encontrados = clienteNormalRep.findByTipoPedido(tipoPedido);

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			List<ClienteNormal> entityList = encontrados.get();
			List<ClienteNormalDTO> dtoList = new ArrayList<>();

			entityList.forEach((entity) -> {
				ClienteNormalDTO dto = mapper.map(entity, ClienteNormalDTO.class);
				dtoList.add(dto);
			});

			return dtoList;

		} else {
			return new ArrayList<ClienteNormalDTO>();
		}
	}
	
	public List<ClienteNormalDTO> findByNombreAndCedula(String nombre, String cedula) {

		Optional<List<ClienteNormal>> encontrados = clienteNormalRep.findByNombreAndCedula(nombre, cedula);

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {

			List<ClienteNormal> entityList = encontrados.get();
			List<ClienteNormalDTO> dtoList = new ArrayList<>();

			entityList.forEach((entity) -> {
				ClienteNormalDTO dto = mapper.map(entity, ClienteNormalDTO.class);
				dtoList.add(dto);
			});

			return dtoList;

		} else {
			return new ArrayList<ClienteNormalDTO>();
		}
	}
	
	public List<ClienteNormalDTO> findByTipoPedidoAndMetodoPago(String tipoPedido, String metodoPago) {

		Optional<List<ClienteNormal>> encontrados = clienteNormalRep.findByTipoPedidoAndMetodoPago(tipoPedido, metodoPago);

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {

			List<ClienteNormal> entityList = encontrados.get();
			List<ClienteNormalDTO> dtoList = new ArrayList<>();

			entityList.forEach((entity) -> {
				ClienteNormalDTO dto = mapper.map(entity, ClienteNormalDTO.class);
				dtoList.add(dto);
			});

			return dtoList;

		} else {
			return new ArrayList<ClienteNormalDTO>();
		}
	}
	
	

}