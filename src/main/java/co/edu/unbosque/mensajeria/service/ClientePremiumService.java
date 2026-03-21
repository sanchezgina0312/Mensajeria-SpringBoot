package co.edu.unbosque.mensajeria.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.mensajeria.dto.ClientePremiumDTO;
import co.edu.unbosque.mensajeria.entity.ClientePremium;
import co.edu.unbosque.mensajeria.repository.ClientePremiumRepository;

@Service
public class ClientePremiumService implements CRUDOperation<ClientePremiumDTO> {

	@Autowired
	private ClientePremiumRepository clientepremiumRep;

	@Autowired
	private ModelMapper mapper;

	@Override
	public int create(ClientePremiumDTO data) {
		ClientePremium entity = mapper.map(data, ClientePremium.class);
		clientepremiumRep.save(entity);
		return 0;
	}

	@Override
	public List<ClientePremiumDTO> getAll() {
		List<ClientePremium> entityList = (List<ClientePremium>) clientepremiumRep.findAll();
		List<ClientePremiumDTO> dtoList = new ArrayList<>();

		entityList.forEach((entity) -> {
			ClientePremiumDTO dto = mapper.map(entity, ClientePremiumDTO.class);
			dtoList.add(dto);
		});

		return dtoList;
	}

	@Override
	public int deleteById(Long id) {

		Optional<ClientePremium> encontrado = clientepremiumRep.findById(id);

		if (encontrado.isPresent()) {

			clientepremiumRep.delete(encontrado.get());
			return 0;
		}
		return 1;

	}

	@Override
	public int updateById(Long id, ClientePremiumDTO data) {

		Optional<ClientePremium> encontrado = clientepremiumRep.findById(id);

		if (encontrado.isPresent()) {

			ClientePremium temp = encontrado.get();

			temp.setNombre(data.getNombre());
			temp.setCedula(data.getCedula());
			temp.setCorreo(data.getCorreo());
			temp.setTelefono(data.getTelefono());
			temp.setTipoPedido(data.getTipoPedido());
			temp.setMetodoPago(data.getMetodoPago());
			temp.setTarifaPremium(data.getTarifaPremium());

			clientepremiumRep.save(temp);
			return 0;

		}

		return 1;
	}

	@Override
	public long count() {
		return clientepremiumRep.count();
	}

	@Override
	public boolean exist(Long id) {
		return clientepremiumRep.existsById(id) ? true : false;
	}


}