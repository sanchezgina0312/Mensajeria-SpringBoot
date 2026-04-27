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

/**
 * Servicio encargado de gestionar las operaciones CRUD de los paquetes no
 * alimenticios.
 * <p>
 * Permite crear, consultar, actualizar y eliminar paquetes no alimenticios, así
 * como realizar cálculos de precios por tamaño, gestionar la fragilidad del
 * envío y procesar tiempos de entrega estimados. Utiliza ModelMapper para la
 * conversión entre entidades y DTOs.
 * </p>
 */
@Service
public class PaqueteNoAlimenticioService implements CRUDOperation<PaqueteNoAlimenticioDTO> {

	@Autowired
	private PaqueteNoAlimenticioRepository paqueteNoAlimenticioRep;

	@Autowired
	private ModelMapper mapper;

	/**
	 * Constructor por defecto.
	 */
	public PaqueteNoAlimenticioService() {

	}

	/**
	 * Crea un nuevo paquete no alimenticio. * @param data datos del paquete no
	 * alimenticio
	 * 
	 * @return 1 si se crea correctamente
	 */
	@Override
	public int create(PaqueteNoAlimenticioDTO data) {
		LanzadorDeException.verificarDireccion(data.getDireccionDestino());
		LanzadorDeException.verificarTamanoPaquete(data.getTamanio());
		LanzadorDeException.verificarCiudad(data.getCiudadDestino());

		PaqueteNoAlimenticio entity = mapper.map(data, PaqueteNoAlimenticio.class);
		entity.setIdCliente(data.getIdCliente());
		entity.setFechaCreacionPedido(LocalDateTime.now());
		entity.setFechaEstimadaEntrega(LocalDateTime.now().plusDays(3));
		entity.setEstadoPedido("EN_PROCESO");

		double precioBase = 20000;
		double precioFinal = calcularPrecioPorTamaño(precioBase, data.getTamanio());
		entity.setPrecioFinal(precioFinal);

		paqueteNoAlimenticioRep.save(entity);
		return 1;
	}

	/**
	 * Obtiene todos los paquetes no alimenticios. * @return lista de paquetes no
	 * alimenticios
	 */
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

	/**
	 * Elimina un paquete no alimenticio por ID. * @param id identificador del
	 * paquete
	 * 
	 * @return 0 si se elimina, 1 si no existe
	 */
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

	/**
	 * Actualiza un paquete no alimenticio existente. * @param id identificador del
	 * paquete
	 * 
	 * @param data nuevos datos
	 * @return 1 si se actualiza correctamente, 0 si no existe
	 */
	@Override
	public int updateById(Long id, PaqueteNoAlimenticioDTO data) {
		Optional<PaqueteNoAlimenticio> encontrado = paqueteNoAlimenticioRep.findById(id);
		if (encontrado.isPresent()) {
			PaqueteNoAlimenticio entity = encontrado.get();

			LanzadorDeException.verificarDireccion(data.getDireccionDestino());
			LanzadorDeException.verificarCiudad(data.getCiudadDestino());
			LanzadorDeException.verificarTamanoPaquete(data.getTamanio());

			boolean fragilPrevio = entity.isEsFragil();

			entity.setDireccionDestino(data.getDireccionDestino());
			entity.setCiudadDestino(data.getCiudadDestino());
			entity.setTamanio(data.getTamanio());
			entity.setEsFragil(fragilPrevio);

			double precioBase = 20000;
			double precioFinal = calcularPrecioPorTamaño(precioBase, data.getTamanio());
			entity.setPrecioFinal(precioFinal);

			paqueteNoAlimenticioRep.save(entity);
			return 1;
		}
		return 0;
	}

	/**
	 * Cuenta los paquetes no alimenticios registrados. * @return total de paquetes
	 */
	@Override
	public long count() {
		return paqueteNoAlimenticioRep.count();
	}

	/**
	 * Verifica si existe un paquete no alimenticio por ID. * @param id
	 * identificador
	 * 
	 * @return true si existe, false si no
	 */
	@Override
	public boolean exist(Long id) {
		return paqueteNoAlimenticioRep.existsById(id) ? true : false;
	}

	/**
	 * Busca paquetes no alimenticios por tamaño.
	 */
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

	/**
	 * Busca paquetes no alimenticios por fragilidad.
	 */
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

	/**
	 * Busca paquetes no alimenticios por tamaño y fragilidad.
	 */
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

	/**
	 * Busca un paquete no alimenticio por ID.
	 */
	public PaqueteNoAlimenticioDTO findById(Long id) {
		Optional<PaqueteNoAlimenticio> encontrado = paqueteNoAlimenticioRep.findById(id);
		if (encontrado.isPresent()) {
			PaqueteNoAlimenticioDTO dto = mapper.map(encontrado.get(), PaqueteNoAlimenticioDTO.class);
			procesarEstadoYTiempoDTO(dto);
			return dto;
		}
		return null;
	}

	/**
	 * Busca paquetes no alimenticios por dirección y ciudad de destino.
	 */
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

	public List<PaqueteNoAlimenticioDTO> findByIdCliente(long idCliente) {
		Optional<List<PaqueteNoAlimenticio>> encontrados = paqueteNoAlimenticioRep.findByIdCliente(idCliente);
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

	/**
	 * Calcula el precio basado en el tamaño del paquete no alimenticio.
	 */
	public double calcularPrecioPorTamaño(double base, String tamaño) {
		if (tamaño.equalsIgnoreCase("MEDIANO"))
			return base + 7000;
		if (tamaño.equalsIgnoreCase("GRANDE"))
			return base + 12000;
		return base;
	}

	/**
	 * Aplica descuentos según el tipo de cliente.
	 */
	public double aplicarDescuentoPorCliente(double precioActual, String tipoCliente) {
		if (tipoCliente.equalsIgnoreCase("CONCURRENTE"))
			return precioActual * 0.90;
		if (tipoCliente.equalsIgnoreCase("PREMIUM"))
			return precioActual * 0.75;
		return precioActual;
	}

	/**
	 * Procesa el estado del pedido y verifica la prioridad según el tiempo
	 * restante.
	 */
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

	/**
	 * Registra un plazo de entrega de 24 horas.
	 */
	public int registrarPlazo24Horas(PaqueteNoAlimenticioDTO data) {
		data.setFechaEstimadaEntrega(data.getFechaCreacionPedido().plusHours(24));
		return 0;
	}

	/**
	 * Permite inyectar el repositorio manualmente (testing).
	 */
	public void setPaqueteNoAlimenticioRep(PaqueteNoAlimenticioRepository repo) {
		this.paqueteNoAlimenticioRep = repo;
	}

	/**
	 * Permite inyectar el mapper manualmente (testing).
	 */
	public void setMapper(ModelMapper mapper) {
		this.mapper = mapper;
	}

}