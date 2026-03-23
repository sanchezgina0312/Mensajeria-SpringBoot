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
		LanzadorDeException.verificarCiudad(data.getCiudadDestino());

		data.setEstadoPedido("RECIBIDO");
		if (data.getFechaCreacionPedido() == null) {
			data.setFechaCreacionPedido(LocalDateTime.now());
		}

		registrarPlazo6Horas(data);

		double montoConRecargo = calcularPrecioPorTamaño(10000, data.getTamanio());
		double montoFinal = aplicarDescuentoPorCliente(montoConRecargo, "CONCURRENTE");

		data.setPrecioEnvio((int) montoConRecargo);
		data.setPrecioFinal(montoFinal);

		procesarEstadoYTiempoDTO(data);

		PaqueteAlimenticio entity = mapper.map(data, PaqueteAlimenticio.class);
		paqueteAlimenticioRep.save(entity);
		return 0;
	}

	@Override
	public List<PaqueteAlimenticioDTO> getAll() {
		List<PaqueteAlimenticio> listaEntidad = (List<PaqueteAlimenticio>) paqueteAlimenticioRep.findAll();
		List<PaqueteAlimenticioDTO> dtoList = new ArrayList<>();
		for (PaqueteAlimenticio p : listaEntidad) { 
			PaqueteAlimenticioDTO dto = mapper.map(p, PaqueteAlimenticioDTO.class);
			procesarEstadoYTiempoDTO(dto);
			dtoList.add(dto);
		}
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
		LanzadorDeException.verificarCiudad(data.getCiudadDestino());
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
			
			PaqueteAlimenticioDTO dtoParaActualizar = mapper.map(temp, PaqueteAlimenticioDTO.class);
			procesarEstadoYTiempoDTO(dtoParaActualizar);
			
			paqueteAlimenticioRep.save(mapper.map(dtoParaActualizar, PaqueteAlimenticio.class));
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

	public List<PaqueteAlimenticioDTO> findByTamanio(String tamanio) {
		LanzadorDeException.verificarTamanoPaquete(tamanio);
		Optional<List<PaqueteAlimenticio>> encontrados = paqueteAlimenticioRep.findByTamanio(tamanio);
		List<PaqueteAlimenticioDTO> dtoList = new ArrayList<>();

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> {
				PaqueteAlimenticioDTO dto = mapper.map(entity, PaqueteAlimenticioDTO.class);
				procesarEstadoYTiempoDTO(dto);
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
				procesarEstadoYTiempoDTO(dto);
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
				procesarEstadoYTiempoDTO(dto);
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
				procesarEstadoYTiempoDTO(dto);
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
			PaqueteAlimenticioDTO dto = mapper.map(encontrado.get(), PaqueteAlimenticioDTO.class);
			procesarEstadoYTiempoDTO(dto);
			return dto;
		}
		return null;
	}

	public List<PaqueteAlimenticioDTO> findByDireccionDestinoAndCiudadDestino(String direccion, String ciudad) {

		LanzadorDeException.verificarDireccion(direccion);
		LanzadorDeException.verificarCiudad(ciudad);

		Optional<List<PaqueteAlimenticio>> encontrados = paqueteAlimenticioRep
				.findByDireccionDestinoAndCiudadDestino(direccion, ciudad);
		List<PaqueteAlimenticioDTO> dtoList = new ArrayList<>();

		if (encontrados.isPresent()) {
			for (PaqueteAlimenticio p : encontrados.get()) {
				PaqueteAlimenticioDTO dto = mapper.map(p, PaqueteAlimenticioDTO.class);
				procesarEstadoYTiempoDTO(dto);
				dtoList.add(dto);
			}
		}
		return dtoList;
	}

	public double calcularPrecioPorTamaño(double base, String tamaño) {
		if (tamaño.equalsIgnoreCase("MEDIANO"))
			return base + 5000;
		if (tamaño.equalsIgnoreCase("GRANDE"))
			return base + 10000;
		return base;
	}

	public double aplicarDescuentoPorCliente(double precioActual, String tipoCliente) {
		if (tipoCliente.equalsIgnoreCase("CONCURRENTE"))
			return precioActual * 0.90;
		if (tipoCliente.equalsIgnoreCase("PREMIUM"))
			return precioActual * 0.75;
		return precioActual;
	}

	public void procesarEstadoYTiempoDTO(PaqueteAlimenticioDTO dto) {
		LocalDateTime ahora = LocalDateTime.now();
		if (dto.getFechaEstimadaEntrega() == null)
			return;

		long horasRestantes = java.time.Duration.between(ahora, dto.getFechaEstimadaEntrega()).toHours();

		if (ahora.isAfter(dto.getFechaEstimadaEntrega())) {
			dto.setEstadoPedido("ENTREGADO");
			dto.setEsPrioritario(false);
		} else if (horasRestantes <= 3 && horasRestantes >= 0) {
			dto.setEsPrioritario(true);
		} else {
			dto.setEsPrioritario(false);
		}
	}

	public int registrarPlazo6Horas(PaqueteAlimenticioDTO data) {
		data.setFechaEstimadaEntrega(data.getFechaCreacionPedido().plusHours(6));
		return 0;
	}

}