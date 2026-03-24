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
		LanzadorDeException.verificarCiudad(data.getCiudadDestino());

		PaqueteCarta entity = mapper.map(data, PaqueteCarta.class);
		entity.setFechaCreacionPedido(LocalDateTime.now());
		entity.setFechaEstimadaEntrega(LocalDateTime.now().plusDays(2));
		entity.setEstadoPedido("EN_PROCESO");

		double precioBase = 5000;
		double precioFinal = calcularPrecioPorTamaño(precioBase, data.getTamanio());
		entity.setPrecioFinal(precioFinal);

		paqueteCartaRep.save(entity);
		return 1;
	}

	@Override
	public List<PaqueteCartaDTO> getAll() {
		List<PaqueteCarta> entities = (List<PaqueteCarta>) paqueteCartaRep.findAll();
		List<PaqueteCartaDTO> dtoList = new ArrayList<>();

		for (PaqueteCarta c : entities) {
			PaqueteCartaDTO dto = mapper.map(c, PaqueteCartaDTO.class);
			String estadoPre = dto.getEstadoPedido();

			procesarEstadoYTiempoDTO(dto);

			if (!estadoPre.equals(dto.getEstadoPedido())) {
				PaqueteCarta actualizado = mapper.map(dto, PaqueteCarta.class);
				paqueteCartaRep.save(actualizado);
			}
			dtoList.add(dto);
		}
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

	public int updateById(Long id, PaqueteCartaDTO data) {
		Optional<PaqueteCarta> encontrado = paqueteCartaRep.findById(id);
		if (encontrado.isPresent()) {
			PaqueteCarta entity = encontrado.get();

			LanzadorDeException.verificarDireccion(data.getDireccionDestino());
			LanzadorDeException.verificarCiudad(data.getCiudadDestino());
			LanzadorDeException.verificarTamanoPaquete(data.getTamanio());
			LanzadorDeException.verificarTipoCarta(data.getTipoCarta());

			boolean priorPrevio = entity.isEsPrioritario();

			entity.setDireccionDestino(data.getDireccionDestino());
			entity.setCiudadDestino(data.getCiudadDestino());
			entity.setTamanio(data.getTamanio());
			entity.setTipoCarta(data.getTipoCarta());
			entity.setEsPrioritario(priorPrevio);

			double precioBase = 5000;
			double precioFinal = calcularPrecioPorTamaño(precioBase, data.getTamanio());
			entity.setPrecioFinal(precioFinal);

			paqueteCartaRep.save(entity);
			return 1;
		}
		return 0;
	}

	@Override
	public long count() {
		return paqueteCartaRep.count();
	}

	@Override
	public boolean exist(Long id) {
		return paqueteCartaRep.existsById(id) ? true : false;
	}

	public List<PaqueteCartaDTO> findByTamanio(String tamanio) {
		LanzadorDeException.verificarTamanoPaquete(tamanio);
		Optional<List<PaqueteCarta>> encontrados = paqueteCartaRep.findByTamanio(tamanio);
		List<PaqueteCartaDTO> dtoList = new ArrayList<>();

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> {
				PaqueteCartaDTO dto = mapper.map(entity, PaqueteCartaDTO.class);
				procesarEstadoYTiempoDTO(dto);
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
				procesarEstadoYTiempoDTO(dto);
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
				procesarEstadoYTiempoDTO(dto);
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
			PaqueteCartaDTO dto = mapper.map(encontrado.get(), PaqueteCartaDTO.class);
			procesarEstadoYTiempoDTO(dto);
			return dto;
		}
		return null;
	}

	public List<PaqueteCartaDTO> findByDireccionDestinoAndCiudadDestino(String direccion, String ciudad) {
		LanzadorDeException.verificarDireccion(direccion);
		LanzadorDeException.verificarCiudad(ciudad);

		Optional<List<PaqueteCarta>> encontrados = paqueteCartaRep.findByDireccionDestinoAndCiudadDestino(direccion,
				ciudad);
		List<PaqueteCartaDTO> dtoList = new ArrayList<>();

		if (encontrados.isPresent()) {
			for (PaqueteCarta p : encontrados.get()) {
				PaqueteCartaDTO dto = mapper.map(p, PaqueteCartaDTO.class);
				procesarEstadoYTiempoDTO(dto);
				dtoList.add(dto);
			}
		}
		return dtoList;
	}

	public double calcularPrecioPorTamaño(double base, String tamaño) {
		if (tamaño.equalsIgnoreCase("MEDIANO"))
			return base + 2000;
		if (tamaño.equalsIgnoreCase("GRANDE"))
			return base + 4000;
		return base;
	}

	public double aplicarDescuentoPorCliente(double precioActual, String tipoCliente) {
		if (tipoCliente.equalsIgnoreCase("CONCURRENTE"))
			return precioActual * 0.90;
		if (tipoCliente.equalsIgnoreCase("PREMIUM"))
			return precioActual * 0.75;
		return precioActual;
	}

	public void procesarEstadoYTiempoDTO(PaqueteCartaDTO dto) {
		LocalDateTime ahora = LocalDateTime.now();
		if (dto.getFechaEstimadaEntrega() == null)
			return;
		long horas = java.time.Duration.between(ahora, dto.getFechaEstimadaEntrega()).toHours();
		if (ahora.isAfter(dto.getFechaEstimadaEntrega())) {
			dto.setEstadoPedido("ENTREGADO");
			dto.setEsPrioritario(false);
		} else if (horas <= 3 && horas >= 0) {
			dto.setEsPrioritario(true);
		} else {
			dto.setEsPrioritario(false);
		}
	}

	public int registrarPlazo72Horas(PaqueteCartaDTO data) {
		data.setFechaEstimadaEntrega(data.getFechaCreacionPedido().plusHours(72));
		return 0;
	}

	public void setPaqueteCartaRep(PaqueteCartaRepository repo) {
		this.paqueteCartaRep = repo;
	}

	public void setMapper(ModelMapper mapper) {
		this.mapper = mapper;
	}

}