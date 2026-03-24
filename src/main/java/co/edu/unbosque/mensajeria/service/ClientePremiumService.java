package co.edu.unbosque.mensajeria.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.mensajeria.dto.ClientePremiumDTO;
import co.edu.unbosque.mensajeria.entity.ClientePremium;
import co.edu.unbosque.mensajeria.repository.ClienteNormalRepository;
import co.edu.unbosque.mensajeria.repository.ClientePremiumRepository;
import co.edu.unbosque.mensajeria.util.LanzadorDeException;

@Service
public class ClientePremiumService implements CRUDOperation<ClientePremiumDTO> {

	@Autowired
	private ClientePremiumRepository clientePremiumRep;

	@Autowired
	private ModelMapper mapper;

	@Override
	public int create(ClientePremiumDTO data) {
		LanzadorDeException.verificarCedula(data.getCedula());
		LanzadorDeException.verificarNombre(data.getNombre());
		LanzadorDeException.verificarCorreoElectronico(data.getCorreo());
		LanzadorDeException.verificarTelefono(data.getTelefono());
		LanzadorDeException.verificarMetodoPago(data.getMetodoPago());
		LanzadorDeException.verificarTipoPedido(data.getTipoPedido());

		LanzadorDeException.verificarDuplicado(clientePremiumRep.existsByCedula(data.getCedula()), "La cédula " + data.getCedula() + " ya se encuentra registrada para un cliente premium.");

		if (clientePremiumRep.existsByCedula(data.getCedula())) {
			return 1;
		} else {
			ClientePremium entity = mapper.map(data, ClientePremium.class);
			clientePremiumRep.save(entity);
			return 0;
		}
	}

	@Override
	public List<ClientePremiumDTO> getAll() {
		List<ClientePremium> entityList = (List<ClientePremium>) clientePremiumRep.findAll();
		List<ClientePremiumDTO> dtoList = new ArrayList<>();
		entityList.forEach(entity -> {
			dtoList.add(mapper.map(entity, ClientePremiumDTO.class));
		});
		return dtoList;
	}

	@Override
	public int deleteById(Long id) {
		LanzadorDeException.verificarId(id);
		Optional<ClientePremium> encontrado = clientePremiumRep.findById(id);
		if (encontrado.isPresent()) {
			clientePremiumRep.delete(encontrado.get());
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int updateById(Long id, ClientePremiumDTO data) {
		LanzadorDeException.verificarId(id);
		LanzadorDeException.verificarCedula(data.getCedula());
		LanzadorDeException.verificarNombre(data.getNombre());
		LanzadorDeException.verificarCorreoElectronico(data.getCorreo());
		LanzadorDeException.verificarTelefono(data.getTelefono());
		LanzadorDeException.verificarMetodoPago(data.getMetodoPago());
		LanzadorDeException.verificarTipoPedido(data.getTipoPedido());

		Optional<ClientePremium> encontrado = clientePremiumRep.findById(id);

		if (encontrado.isPresent()) {
			ClientePremium temp = encontrado.get();

			if (!temp.getCedula().equals(data.getCedula())) {
				LanzadorDeException.verificarDuplicado(clientePremiumRep.existsByCedula(data.getCedula()), "No se puede actualizar: la cédula " + data.getCedula() + " ya pertenece a otro cliente premium.");
				if (clientePremiumRep.existsByCedula(data.getCedula())) {
					return 1;
				}
			}

			temp.setNombre(data.getNombre());
			temp.setCedula(data.getCedula());
			temp.setCorreo(data.getCorreo());
			temp.setTelefono(data.getTelefono());
			temp.setMetodoPago(data.getMetodoPago());
			temp.setTipoPedido(data.getTipoPedido());

			clientePremiumRep.save(temp);
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public long count() {
		return clientePremiumRep.count();
	}

	@Override
	public boolean exist(Long id) {
		LanzadorDeException.verificarId(id);
		return clientePremiumRep.existsById(id);
	}

	public List<ClientePremiumDTO> findByNombre(String nombre) {
		LanzadorDeException.verificarNombre(nombre);
		Optional<List<ClientePremium>> encontrados = clientePremiumRep.findByNombre(nombre);
		List<ClientePremiumDTO> dtoList = new ArrayList<>();
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach(e -> dtoList.add(mapper.map(e, ClientePremiumDTO.class)));
		}
		return dtoList;
	}

	public List<ClientePremiumDTO> findByCedula(String cedula) {
		LanzadorDeException.verificarCedula(cedula);
		Optional<List<ClientePremium>> encontrados = clientePremiumRep.findByCedula(cedula);
		List<ClientePremiumDTO> dtoList = new ArrayList<>();
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach(e -> dtoList.add(mapper.map(e, ClientePremiumDTO.class)));
		}
		return dtoList;
	}

	public List<ClientePremiumDTO> findByCorreo(String correo) {
		LanzadorDeException.verificarCorreoElectronico(correo);
		Optional<List<ClientePremium>> encontrados = clientePremiumRep.findByCorreo(correo);
		List<ClientePremiumDTO> dtoList = new ArrayList<>();
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach(e -> dtoList.add(mapper.map(e, ClientePremiumDTO.class)));
		}
		return dtoList;
	}

	public List<ClientePremiumDTO> findByTelefono(String telefono) {
		LanzadorDeException.verificarTelefono(telefono);
		Optional<List<ClientePremium>> encontrados = clientePremiumRep.findByTelefono(telefono);
		List<ClientePremiumDTO> dtoList = new ArrayList<>();
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach(e -> dtoList.add(mapper.map(e, ClientePremiumDTO.class)));
		}
		return dtoList;
	}

	public List<ClientePremiumDTO> findByMetodoPago(String metodoPago) {
		LanzadorDeException.verificarMetodoPago(metodoPago);
		Optional<List<ClientePremium>> encontrados = clientePremiumRep.findByMetodoPago(metodoPago);
		List<ClientePremiumDTO> dtoList = new ArrayList<>();
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach(e -> dtoList.add(mapper.map(e, ClientePremiumDTO.class)));
		}
		return dtoList;
	}

	public List<ClientePremiumDTO> findByTipoPedido(String tipoPedido) {
		LanzadorDeException.verificarTipoPedido(tipoPedido);
		Optional<List<ClientePremium>> encontrados = clientePremiumRep.findByTipoPedido(tipoPedido);
		List<ClientePremiumDTO> dtoList = new ArrayList<>();
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach(e -> dtoList.add(mapper.map(e, ClientePremiumDTO.class)));
		}
		return dtoList;
	}

	public List<ClientePremiumDTO> findByNombreAndCedula(String nombre, String cedula) {
		LanzadorDeException.verificarNombre(nombre);
		LanzadorDeException.verificarCedula(cedula);
		Optional<List<ClientePremium>> encontrados = clientePremiumRep.findByNombreAndCedula(nombre, cedula);
		List<ClientePremiumDTO> dtoList = new ArrayList<>();
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach(e -> dtoList.add(mapper.map(e, ClientePremiumDTO.class)));
		}
		return dtoList;
	}

	public List<ClientePremiumDTO> findByTipoPedidoAndMetodoPago(String tipoPedido, String metodoPago) {
		LanzadorDeException.verificarTipoPedido(tipoPedido);
		LanzadorDeException.verificarMetodoPago(metodoPago);
		Optional<List<ClientePremium>> encontrados = clientePremiumRep.findByTipoPedidoAndMetodoPago(tipoPedido,
				metodoPago);
		List<ClientePremiumDTO> dtoList = new ArrayList<>();
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach(e -> dtoList.add(mapper.map(e, ClientePremiumDTO.class)));
		}
		return dtoList;
	}
	
	public void setClientePremiumRep(ClientePremiumRepository repo) {
	    this.clientePremiumRep= repo;
	}

	public void setMapper(ModelMapper mapper) {
	    this.mapper = mapper;
	}
}