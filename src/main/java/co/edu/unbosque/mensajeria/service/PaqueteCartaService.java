package co.edu.unbosque.mensajeria.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.mensajeria.dto.PaqueteCartaDTO;
import co.edu.unbosque.mensajeria.entity.PaqueteCarta;
import co.edu.unbosque.mensajeria.repository.PaqueteCartaRepository;

@Service
public class PaqueteCartaService implements CRUDOperation<PaqueteCartaDTO> {

	@Autowired
	private PaqueteCartaRepository paqueteCartaRep;

	@Autowired
	private ModelMapper mapper;

	public PaqueteCartaService() {

	}

	@Override
	public int create(PaqueteCartaDTO data) {
		PaqueteCarta entity = mapper.map(data, PaqueteCarta.class);
		paqueteCartaRep.save(entity);
		return 0;
	}

	@Override
	public List<PaqueteCartaDTO> getAll() {
		List<PaqueteCarta> entityList = (List<PaqueteCarta>) paqueteCartaRep.findAll();
		List<PaqueteCartaDTO> dtoList = new ArrayList<>();
		entityList.forEach((entity) -> {
			PaqueteCartaDTO dto = mapper.map(entity, PaqueteCartaDTO.class);
			dtoList.add(dto);
		});

		return dtoList;
	}

	@Override
	public int deleteById(Long id) {
		Optional<PaqueteCarta> encontrado = paqueteCartaRep.findById(id);
		if (encontrado.isPresent()) {
			paqueteCartaRep.delete(encontrado.get());
			return 0;
		}

		return 1;
	}

	@Override
	public int updateById(Long id, PaqueteCartaDTO data) {
		Optional<PaqueteCarta> encontrado = paqueteCartaRep.findById(id);
		if (encontrado.isPresent()) {
			PaqueteCarta temp = encontrado.get();
			temp.setPrecioEnvio(data.getPrecioEnvio());
			temp.setDireccionDestino(data.getDireccionDestino());
			temp.setTamanio(data.getTamanio());
			temp.setFechaCreacionPedido(data.getFechaCreacionPedido());
			temp.setFechaEstimadaEntrega(data.getFechaEstimadaEntrega());
			temp.setTipoCarta(data.getTipoCarta());
			paqueteCartaRep.save(temp);
			return 0;
		}

		return 1;
	}

	@Override
	public long count() {
		return paqueteCartaRep.count();
	}

	@Override
	public boolean exist(Long id) {
		return paqueteCartaRep.existsById(id) ? true : false;
	}

	public int registrarConPlazo72Horas(PaqueteCartaDTO data) {
		if (data.getFechaCreacionPedido() == null) {
			data.setFechaCreacionPedido(LocalDateTime.now());
		}

		data.setFechaEstimadaEntrega(data.getFechaCreacionPedido().plusHours(72));

		PaqueteCarta entity = mapper.map(data, PaqueteCarta.class);
		paqueteCartaRep.save(entity);
		return 0;
	}

	public int registrarPlazo72Horas(PaqueteCartaDTO data) {
		if (data.getFechaCreacionPedido() == null) {
			data.setFechaCreacionPedido(LocalDateTime.now());
		}
		data.setFechaEstimadaEntrega(data.getFechaCreacionPedido().plusHours(72));
		return 0;
	}

}