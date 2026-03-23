package co.edu.unbosque.mensajeria.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.mensajeria.dto.PaqueteAlimenticioDTO;
import co.edu.unbosque.mensajeria.dto.PaqueteCartaDTO;
import co.edu.unbosque.mensajeria.entity.PaqueteAlimenticio;
import co.edu.unbosque.mensajeria.entity.PaqueteCarta;
import co.edu.unbosque.mensajeria.repository.PaqueteCartaRepository;
import co.edu.unbosque.mensajeria.util.LanzadorDeException;

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

		LanzadorDeException.verificarDireccion(data.getDireccionDestino());
		LanzadorDeException.verificarTipoCarta(data.getTipoCarta());
		LanzadorDeException.verificarTamanoPaquete(data.getTamanio());

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

		LanzadorDeException.verificarId(id);
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

	public List<PaqueteCartaDTO> findByTamanio(String tamanio) {
		LanzadorDeException.verificarTamanoPaquete(tamanio);
		Optional<List<PaqueteCarta>> encontrados = paqueteCartaRep.findByTamanio(tamanio);
		List<PaqueteCartaDTO> dtoList = new ArrayList<>();

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> {
				PaqueteCartaDTO dto = mapper.map(entity, PaqueteCartaDTO.class);
				dtoList.add(dto);
			});
			return dtoList;
		} else {
			return new ArrayList<PaqueteCartaDTO>();
		}
	}

	public List<PaqueteCartaDTO> findByTipoCarta(String tipoCarta) {
		LanzadorDeException.verificarTipoCarta(tipoCarta);
		Optional<List<PaqueteCarta>> encontrados = paqueteCartaRep.findByTipoCarta(tipoCarta);
		List<PaqueteCartaDTO> dtoList = new ArrayList<>();

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> {
				PaqueteCartaDTO dto = mapper.map(entity, PaqueteCartaDTO.class);
				dtoList.add(dto);
			});
			return dtoList;
		} else {
			return new ArrayList<PaqueteCartaDTO>();
		}
	}

	public List<PaqueteCartaDTO> findByTamanioAndTipoCarta(String tamanio, String tipoCarta) {
		LanzadorDeException.verificarTamanoPaquete(tamanio);
		LanzadorDeException.verificarTipoCarta(tipoCarta);
		Optional<List<PaqueteCarta>> encontrados = paqueteCartaRep.findByTamanioAndTipoCarta(tamanio, tipoCarta);
		List<PaqueteCartaDTO> dtoList = new ArrayList<>();

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> {
				PaqueteCartaDTO dto = mapper.map(entity, PaqueteCartaDTO.class);
				dtoList.add(dto);
			});
			return dtoList;
		} else {
			return new ArrayList<PaqueteCartaDTO>();
		}
	}

	public PaqueteCartaDTO findById(Long id) {
		Optional<PaqueteCarta> encontrado = paqueteCartaRep.findById(id);
		if (encontrado.isPresent()) {
			return mapper.map(encontrado.get(), PaqueteCartaDTO.class);
		}
		return null;
	}

	public List<PaqueteCartaDTO> findByDireccionDestino(String direccion) {
		LanzadorDeException.verificarDireccion(direccion);

		Optional<List<PaqueteCarta>> encontrados = paqueteCartaRep.findByDireccionDestino(direccion);
		List<PaqueteCartaDTO> dtoList = new ArrayList<>();

		if (encontrados.isPresent()) {
			for (PaqueteCarta p : encontrados.get()) {
				dtoList.add(mapper.map(p, PaqueteCartaDTO.class));
			}
		}
		return dtoList;
	}

}