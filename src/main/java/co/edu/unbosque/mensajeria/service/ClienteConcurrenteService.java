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

		Optional<ClienteConcurrente> encontrado = clienteConcurrenteRep.findById(id);

		if (encontrado.isPresent()) {

			clienteConcurrenteRep.delete(encontrado.get());
			return 0;
		}
		return 1;

	}

	@Override
	public int updateById(Long id, ClienteConcurrenteDTO data) {

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

}