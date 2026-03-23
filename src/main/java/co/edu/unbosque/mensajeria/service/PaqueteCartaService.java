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

		data.setEstadoPedido("RECIBIDO");
		if (data.getFechaCreacionPedido() == null)
			data.setFechaCreacionPedido(LocalDateTime.now());

		registrarPlazo72Horas(data);

		double baseConTamaño = calcularPrecioPorTamaño(5000, data.getTamanio());
		double totalFinal = aplicarDescuentoPorCliente(baseConTamaño, "NORMAL");

		data.setPrecioEnvio((int) baseConTamaño);
		data.setPrecioFinal(totalFinal);

		procesarEstadoYTiempoDTO(data);

		PaqueteCarta entity = mapper.map(data, PaqueteCarta.class);
		paqueteCartaRep.save(entity);
		return 0;
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
			LanzadorDeException.verificarCiudad(data.getCiudadDestino());
			LanzadorDeException.verificarId(id);
			
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

	public List<PaqueteCartaDTO> findByDireccionDestinoAndCiudadDestino(String direccion, String ciudad) {
		LanzadorDeException.verificarDireccion(direccion);
		LanzadorDeException.verificarCiudad(ciudad);

		Optional<List<PaqueteCarta>> encontrados = paqueteCartaRep.findByDireccionDestinoAndCiudadDestino(direccion,ciudad);
		List<PaqueteCartaDTO> dtoList = new ArrayList<>();

		if (encontrados.isPresent()) {
			for (PaqueteCarta p : encontrados.get()) {
				dtoList.add(mapper.map(p, PaqueteCartaDTO.class));
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

}