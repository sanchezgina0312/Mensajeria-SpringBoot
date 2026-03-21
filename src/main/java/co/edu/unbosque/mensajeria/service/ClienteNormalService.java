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

@Service
public class ClienteNormalService implements CRUDOperation<ClienteNormalDTO> {

	@Autowired
	private ClienteNormalRepository clientenormalRep;

	@Autowired
	private ModelMapper mapper;

	@Override
	public int create(ClienteNormalDTO data) {
		ClienteNormal entity = mapper.map(data, ClienteNormal.class);
		clientenormalRep.save(entity);
		return 0;
	}

	@Override
	public List<ClienteNormalDTO> getAll() {
		List<ClienteNormal> entityList = (List<ClienteNormal>) clientenormalRep.findAll();
		List<ClienteNormalDTO> dtoList = new ArrayList<>();

		entityList.forEach((entity) -> {
			ClienteNormalDTO dto = mapper.map(entity, ClienteNormalDTO.class);
			dtoList.add(dto);
		});

		return dtoList;

	}

	@Override
	public int deleteById(Long id) {
		Optional<ClienteNormal> encontrado = clientenormalRep.findById(id);

		if (encontrado.isPresent()) {
			clientenormalRep.delete(encontrado.get());
			return 0;
		}
		return 1;
	}

	@Override
	public int updateById(Long id, ClienteNormalDTO data) {
		Optional<ClienteNormal> encontrado = clientenormalRep.findById(id);

		if (encontrado.isPresent()) {

			ClienteNormal temp = encontrado.get();

			temp.setNombre(data.getNombre());
			temp.setCedula(data.getCedula());
			temp.setCorreo(data.getCorreo());
			temp.setTelefono(data.getTelefono());
			temp.setTipoPedido(data.getTipoPedido());
			temp.setMetodoPago(data.getMetodoPago());
			temp.setTarifaNormal(data.getTarifaNormal());

			clientenormalRep.save(temp);
			return 0;
		}

		return 1;
	}

	@Override
	public long count() {
		return clientenormalRep.count();
	}

	@Override
	public boolean exist(Long id) {
		return clientenormalRep.existsById(id) ? true : false;
	}

}