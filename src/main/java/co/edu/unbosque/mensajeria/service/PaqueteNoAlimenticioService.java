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
	    LanzadorDeException.verificarCiudad(data.getCiudadDestino());

	    data.setEstadoPedido("EN_PROCESO");
	    
	    if (data.getFechaCreacionPedido() == null) {
	        data.setFechaCreacionPedido(LocalDateTime.now());
	    }
	    
	    if (data.getFechaEstimadaEntrega() == null) {
	        data.setFechaEstimadaEntrega(data.getFechaCreacionPedido().plusDays(5));
	    }

	    double precioBaseConRecargo = calcularPrecioPorTamaño(15000, data.getTamanio());
	    double precioConDescuento = aplicarDescuentoPorCliente(precioBaseConRecargo, "NORMAL");

	    data.setPrecioEnvio((int) precioBaseConRecargo);
	    data.setPrecioFinal(precioConDescuento);
	    data.setEsPrioritario(false);

	    registrarPlazo24Horas(data);
	    procesarEstadoYTiempoDTO(data);

	    try {
	        PaqueteNoAlimenticio entity = mapper.map(data, PaqueteNoAlimenticio.class);
	        paqueteNoAlimenticioRep.save(entity);
	        return 1;
	    } catch (Exception e) {
	        e.getMessage(); 
	        return 0;
	    }
	}

	@Override
	public List<PaqueteNoAlimenticioDTO> getAll() {
		List<PaqueteNoAlimenticio> entities = (List<PaqueteNoAlimenticio>) paqueteNoAlimenticioRep.findAll();
		List<PaqueteNoAlimenticioDTO> dtoList = new ArrayList<>();

		for (PaqueteNoAlimenticio e : entities) {
			PaqueteNoAlimenticioDTO dto = mapper.map(e, PaqueteNoAlimenticioDTO.class);
			String estadoOriginal = dto.getEstadoPedido();

			procesarEstadoYTiempoDTO(dto);

			if (!estadoOriginal.equals(dto.getEstadoPedido())) {
				PaqueteNoAlimenticio actualizado = mapper.map(dto, PaqueteNoAlimenticio.class);
				paqueteNoAlimenticioRep.save(actualizado);
			}
			dtoList.add(dto);
		}
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

		LanzadorDeException.verificarDireccion(data.getDireccionDestino());
		LanzadorDeException.verificarTamanoPaquete(data.getTamanio());
		LanzadorDeException.verificarId(id);

		Optional<PaqueteNoAlimenticio> encontrado = paqueteNoAlimenticioRep.findById(id);
		if (encontrado.isPresent()) {
			PaqueteNoAlimenticio temp = encontrado.get();
			temp.setPrecioEnvio(data.getPrecioEnvio());
			temp.setDireccionDestino(data.getDireccionDestino());
			temp.setTamanio(data.getTamanio());
			temp.setFechaCreacionPedido(data.getFechaCreacionPedido());
			temp.setFechaEstimadaEntrega(data.getFechaEstimadaEntrega());
			temp.setEsFragil(data.isEsFragil());
			LanzadorDeException.verificarCiudad(data.getCiudadDestino());
			LanzadorDeException.verificarId(id);

			PaqueteNoAlimenticioDTO dtoTemp = mapper.map(temp, PaqueteNoAlimenticioDTO.class);
			procesarEstadoYTiempoDTO(dtoTemp);

			paqueteNoAlimenticioRep.save(mapper.map(dtoTemp, PaqueteNoAlimenticio.class));
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

	public List<PaqueteNoAlimenticioDTO> findByTamanio(String tamanio) {
		LanzadorDeException.verificarTamanoPaquete(tamanio);
		Optional<List<PaqueteNoAlimenticio>> encontrados = paqueteNoAlimenticioRep.findByTamanio(tamanio);
		List<PaqueteNoAlimenticioDTO> dtoList = new ArrayList<>();

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach((entity) -> {
				PaqueteNoAlimenticioDTO dto = mapper.map(entity, PaqueteNoAlimenticioDTO.class);
				procesarEstadoYTiempoDTO(dto);
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
				procesarEstadoYTiempoDTO(dto);
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
				procesarEstadoYTiempoDTO(dto);
				dtoList.add(dto);
			});
			return dtoList;
		} else {
			return new ArrayList<PaqueteNoAlimenticioDTO>();
		}
	}

	public PaqueteNoAlimenticioDTO findById(Long id) {
		Optional<PaqueteNoAlimenticio> encontrado = paqueteNoAlimenticioRep.findById(id);
		if (encontrado.isPresent()) {
			PaqueteNoAlimenticioDTO dto = mapper.map(encontrado.get(), PaqueteNoAlimenticioDTO.class);
			procesarEstadoYTiempoDTO(dto);
			return dto;
		}
		return null;
	}

	public List<PaqueteNoAlimenticioDTO> findByDireccionDestinoAndCiudadDestino(String direccion, String ciudad) {

		LanzadorDeException.verificarDireccion(direccion);
		LanzadorDeException.verificarCiudad(ciudad);

		Optional<List<PaqueteNoAlimenticio>> encontrados = paqueteNoAlimenticioRep
				.findByDireccionDestinoAndCiudadDestino(direccion, ciudad);
		List<PaqueteNoAlimenticioDTO> dtoList = new ArrayList<>();

		if (encontrados.isPresent()) {
			for (PaqueteNoAlimenticio p : encontrados.get()) {
				PaqueteNoAlimenticioDTO dto = mapper.map(p, PaqueteNoAlimenticioDTO.class);
				procesarEstadoYTiempoDTO(dto);
				dtoList.add(dto);
			}
		}
		return dtoList;
	}

	public double calcularPrecioPorTamaño(double base, String tamaño) {
		if (tamaño.equalsIgnoreCase("MEDIANO"))
			return base + 7000;
		if (tamaño.equalsIgnoreCase("GRANDE"))
			return base + 12000;
		return base;
	}

	public double aplicarDescuentoPorCliente(double precioActual, String tipoCliente) {
		if (tipoCliente.equalsIgnoreCase("CONCURRENTE"))
			return precioActual * 0.90;
		if (tipoCliente.equalsIgnoreCase("PREMIUM"))
			return precioActual * 0.75;
		return precioActual;
	}

	public void procesarEstadoYTiempoDTO(PaqueteNoAlimenticioDTO dto) {
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

	public int registrarPlazo24Horas(PaqueteNoAlimenticioDTO data) {
		data.setFechaEstimadaEntrega(data.getFechaCreacionPedido().plusHours(24));
		return 0;
	}

}