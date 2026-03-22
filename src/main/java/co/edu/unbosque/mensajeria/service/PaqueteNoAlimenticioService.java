package co.edu.unbosque.mensajeria.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.mensajeria.dto.PaqueteNoAlimenticioDTO;
import co.edu.unbosque.mensajeria.entity.PaqueteNoAlimenticio;
import co.edu.unbosque.mensajeria.repository.PaqueteNoAlimenticioRepository;
import co.edu.unbosque.mensajeria.util.LanzadorDeException;

@Service
public class PaqueteNoAlimenticioService implements CRUDOperation<PaqueteNoAlimenticioDTO> {

	@Autowired
	private PaqueteNoAlimenticioRepository paqueteNoAlimenticioRep;

	@Autowired
	private ModelMapper mapper;

	public PaqueteNoAlimenticioService() {

	}

	@Override
	public int create(PaqueteNoAlimenticioDTO data) {

		LanzadorDeException.verificarDireccion(data.getDireccionDestino());
		LanzadorDeException.verificarTamanoPaquete(data.getTamanio());

		PaqueteNoAlimenticio entity = mapper.map(data, PaqueteNoAlimenticio.class);
		paqueteNoAlimenticioRep.save(entity);

		return 0;
	}

	@Override
	public List<PaqueteNoAlimenticioDTO> getAll() {
		List<PaqueteNoAlimenticio> entityList = (List<PaqueteNoAlimenticio>) paqueteNoAlimenticioRep.findAll();
		List<PaqueteNoAlimenticioDTO> dtoList = new ArrayList<>();
		entityList.forEach((entity) -> {
			PaqueteNoAlimenticioDTO dto = mapper.map(entity, PaqueteNoAlimenticioDTO.class);
			dtoList.add(dto);
		});

		return dtoList;
	}

	@Override
	public int deleteById(Long id) {
		LanzadorDeException.verificarId(id);
		Optional<PaqueteNoAlimenticio> encontrado = paqueteNoAlimenticioRep.findById(id);
		if (encontrado.isPresent()) {
			paqueteNoAlimenticioRep.delete(encontrado.get());
			return 0;
		}

		return 1;
	}

	@Override
	public int updateById(Long id, PaqueteNoAlimenticioDTO data) {
		Optional<PaqueteNoAlimenticio> encontrado = paqueteNoAlimenticioRep.findById(id);
		if (encontrado.isPresent()) {
			PaqueteNoAlimenticio temp = encontrado.get();
			temp.setPrecioEnvio(data.getPrecioEnvio());
			temp.setDireccionDestino(data.getDireccionDestino());
			temp.setTamanio(data.getTamanio());
			temp.setFechaCreacionPedido(data.getFechaCreacionPedido());
			temp.setFechaEstimadaEntrega(data.getFechaEstimadaEntrega());
			temp.setEsFragil(data.isEsFragil());
			paqueteNoAlimenticioRep.save(temp);
			return 0;
		}

		return 1;
	}

	@Override
	public long count() {
		return paqueteNoAlimenticioRep.count();
	}

	@Override
	public boolean exist(Long id) {
		return paqueteNoAlimenticioRep.existsById(id) ? true : false;
	}

	public int registrarPlazo24Horas(PaqueteNoAlimenticioDTO data) {
		if (data.getFechaCreacionPedido() == null) {
			data.setFechaCreacionPedido(LocalDateTime.now());
		}
		data.setFechaEstimadaEntrega(data.getFechaCreacionPedido().plusHours(24));
		return 0;
	}

	public List<PaqueteNoAlimenticioDTO> findByTamanio(String tamanio) {
		LanzadorDeException.verificarTamanoPaquete(tamanio);
		Optional<List<PaqueteNoAlimenticio>> encontrados = paqueteNoAlimenticioRep.findByTamanio(tamanio);
		List<PaqueteNoAlimenticioDTO> dtoList = new ArrayList<>();

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> {
				PaqueteNoAlimenticioDTO dto = mapper.map(entity, PaqueteNoAlimenticioDTO.class);
				dtoList.add(dto);
			});
			return dtoList;
		} else {
			return new ArrayList<PaqueteNoAlimenticioDTO>();
		}
	}

	public List<PaqueteNoAlimenticioDTO> findByEsFragil(boolean esFragil) {
		Optional<List<PaqueteNoAlimenticio>> encontrados = paqueteNoAlimenticioRep.findByEsFragil(esFragil);
		List<PaqueteNoAlimenticioDTO> dtoList = new ArrayList<>();

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> {
				PaqueteNoAlimenticioDTO dto = mapper.map(entity, PaqueteNoAlimenticioDTO.class);
				dtoList.add(dto);
			});
			return dtoList;
		} else {
			return new ArrayList<PaqueteNoAlimenticioDTO>();
		}
	}

	public List<PaqueteNoAlimenticioDTO> findByTamanioAndEsFragil(String tamanio, boolean esFragil) {
		LanzadorDeException.verificarTamanoPaquete(tamanio);
		Optional<List<PaqueteNoAlimenticio>> encontrados = paqueteNoAlimenticioRep.findByTamanioAndEsFragil(tamanio,
				esFragil);
		List<PaqueteNoAlimenticioDTO> dtoList = new ArrayList<>();

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> {
				PaqueteNoAlimenticioDTO dto = mapper.map(entity, PaqueteNoAlimenticioDTO.class);
				dtoList.add(dto);
			});
			return dtoList;
		} else {
			return new ArrayList<PaqueteNoAlimenticioDTO>();
		}
	}
}