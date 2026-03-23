package co.edu.unbosque.mensajeria.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.mensajeria.dto.PaqueteAlimenticioDTO;
import co.edu.unbosque.mensajeria.entity.PaqueteAlimenticio;
import co.edu.unbosque.mensajeria.repository.PaqueteAlimenticioRepository;
import co.edu.unbosque.mensajeria.util.LanzadorDeException;

@Service
public class PaqueteAlimenticioService implements CRUDOperation<PaqueteAlimenticioDTO> {

	@Autowired
	private PaqueteAlimenticioRepository paqueteAlimenticioRep;

	@Autowired
	private ModelMapper mapper;

	public PaqueteAlimenticioService() {

	}

	@Override
	public int create(PaqueteAlimenticioDTO data) {
		LanzadorDeException.verificarDireccion(data.getDireccionDestino());
		LanzadorDeException.verificarTamanoPaquete(data.getTamanio());
		LanzadorDeException.verificarTipoAlimento(data.getTipoDeAlimento());

		PaqueteAlimenticio entity = mapper.map(data, PaqueteAlimenticio.class);
		paqueteAlimenticioRep.save(entity);

		return 0;
	}

	@Override
	public List<PaqueteAlimenticioDTO> getAll() {
		List<PaqueteAlimenticio> entityList = (List<PaqueteAlimenticio>) paqueteAlimenticioRep.findAll();
		List<PaqueteAlimenticioDTO> dtoList = new ArrayList<>();
		entityList.forEach((entity) -> {
			PaqueteAlimenticioDTO dto = mapper.map(entity, PaqueteAlimenticioDTO.class);
			dtoList.add(dto);
		});

		return dtoList;
	}

	@Override
	public int deleteById(Long id) {
		LanzadorDeException.verificarId(id);
		Optional<PaqueteAlimenticio> encontrado = paqueteAlimenticioRep.findById(id);
		if (encontrado.isPresent()) {
			paqueteAlimenticioRep.delete(encontrado.get());
			return 0;
		}

		return 1;
	}

	@Override
	public int updateById(Long id, PaqueteAlimenticioDTO data) {
		
		LanzadorDeException.verificarDireccion(data.getDireccionDestino());
		LanzadorDeException.verificarTamanoPaquete(data.getTamanio());
		LanzadorDeException.verificarTipoAlimento(data.getTipoDeAlimento());
		LanzadorDeException.verificarId(id);

		
		Optional<PaqueteAlimenticio> encontrado = paqueteAlimenticioRep.findById(id);
		if (encontrado.isPresent()) {
			PaqueteAlimenticio temp = encontrado.get();
			temp.setPrecioEnvio(data.getPrecioEnvio());
			temp.setDireccionDestino(data.getDireccionDestino());
			temp.setTamanio(data.getTamanio());
			temp.setFechaCreacionPedido(data.getFechaCreacionPedido());
			temp.setFechaEstimadaEntrega(data.getFechaEstimadaEntrega());
			temp.setSeEnviaHoy(data.isSeEnviaHoy());
			temp.setTipoDeAlimento(data.getTipoDeAlimento());
			paqueteAlimenticioRep.save(temp);
			return 0;
		}

		return 1;
	}

	@Override
	public long count() {
		return paqueteAlimenticioRep.count();
	}

	@Override
	public boolean exist(Long id) {
		return paqueteAlimenticioRep.existsById(id) ? true : false;
	}

	public int registrarYValidarHorasEntrega(PaqueteAlimenticioDTO data) {
		if (data.getFechaCreacionPedido() == null) {
			data.setFechaCreacionPedido(LocalDateTime.now());
		}

		LocalDateTime fechaCreacion = data.getFechaCreacionPedido();
		LocalDateTime fechaEstimada = fechaCreacion.plusHours(6);
		data.setFechaEstimadaEntrega(fechaEstimada);

		if (fechaEstimada.toLocalDate().isAfter(fechaCreacion.toLocalDate())) {
			return 2;
		}

		return 0;
	}

	public List<PaqueteAlimenticioDTO> findByTamanio(String tamanio) {
		LanzadorDeException.verificarTamanoPaquete(tamanio);
		Optional<List<PaqueteAlimenticio>> encontrados = paqueteAlimenticioRep.findByTamanio(tamanio);
		List<PaqueteAlimenticioDTO> dtoList = new ArrayList<>();

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> {
				PaqueteAlimenticioDTO dto = mapper.map(entity, PaqueteAlimenticioDTO.class);
				dtoList.add(dto);
			});
			return dtoList;
		} else {
			return new ArrayList<PaqueteAlimenticioDTO>();
		}
	}

	public List<PaqueteAlimenticioDTO> findBySeEnviaHoy(boolean seEnviaHoy) {
		Optional<List<PaqueteAlimenticio>> encontrados = paqueteAlimenticioRep.findBySeEnviaHoy(seEnviaHoy);
		List<PaqueteAlimenticioDTO> dtoList = new ArrayList<>();

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> {
				PaqueteAlimenticioDTO dto = mapper.map(entity, PaqueteAlimenticioDTO.class);
				dtoList.add(dto);
			});
			return dtoList;
		} else {
			return new ArrayList<PaqueteAlimenticioDTO>();
		}
	}

	public List<PaqueteAlimenticioDTO> findByTipoDeAlimento(String tipoDeAlimento) {
		LanzadorDeException.verificarTipoAlimento(tipoDeAlimento);
		Optional<List<PaqueteAlimenticio>> encontrados = paqueteAlimenticioRep.findByTipoDeAlimento(tipoDeAlimento);
		List<PaqueteAlimenticioDTO> dtoList = new ArrayList<>();

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> {
				PaqueteAlimenticioDTO dto = mapper.map(entity, PaqueteAlimenticioDTO.class);
				dtoList.add(dto);
			});
			return dtoList;
		} else {
			return new ArrayList<PaqueteAlimenticioDTO>();
		}
	}

	public List<PaqueteAlimenticioDTO> findByTamanioAndTipoDeAlimento(String tamanio, String tipoDeAlimento) {
		LanzadorDeException.verificarTamanoPaquete(tamanio);
		LanzadorDeException.verificarTipoAlimento(tipoDeAlimento);
		Optional<List<PaqueteAlimenticio>> encontrados = paqueteAlimenticioRep.findByTamanioAndTipoDeAlimento(tamanio,
				tipoDeAlimento);
		List<PaqueteAlimenticioDTO> dtoList = new ArrayList<>();

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> {
				PaqueteAlimenticioDTO dto = mapper.map(entity, PaqueteAlimenticioDTO.class);
				dtoList.add(dto);
			});
			return dtoList;
		} else {
			return new ArrayList<PaqueteAlimenticioDTO>();
		}
	}
	
	public PaqueteAlimenticioDTO findById(Long id) {
		Optional<PaqueteAlimenticio> encontrado = paqueteAlimenticioRep.findById(id);
		if (encontrado.isPresent()) {
			return mapper.map(encontrado.get(), PaqueteAlimenticioDTO.class);
		}
		return null;
	}

	public List<PaqueteAlimenticioDTO> findByDireccionDestino(String direccion) {
		LanzadorDeException.verificarDireccion(direccion);

		Optional<List<PaqueteAlimenticio>> encontrados = paqueteAlimenticioRep.findByDireccionDestino(direccion);
		List<PaqueteAlimenticioDTO> dtoList = new ArrayList<>();

		if (encontrados.isPresent()) {
			for (PaqueteAlimenticio p : encontrados.get()) {
				dtoList.add(mapper.map(p, PaqueteAlimenticioDTO.class));
			}
		}
		return dtoList;
	}
}