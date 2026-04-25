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

/**
 * Servicio encargado de gestionar las operaciones CRUD de los paquetes alimenticios.
 * <p>
 * Permite crear, consultar, actualizar y eliminar paquetes alimenticios, así como
 * realizar cálculos de precios por tamaño, aplicar descuentos por tipo de cliente
 * y procesar estados de entrega en tiempo real.
 * Utiliza ModelMapper para la conversión entre entidades y DTOs.
 * </p>
 */
@Service
public class PaqueteAlimenticioService implements CRUDOperation<PaqueteAlimenticioDTO> {

	@Autowired
	private PaqueteAlimenticioRepository paqueteAlimenticioRep;

	@Autowired
	private ModelMapper mapper;

	/**
	 * Constructor por defecto.
	 */
	public PaqueteAlimenticioService() {

	}

	/**
	 * Crea un nuevo paquete alimenticio.
	 * * @param data datos del paquete alimenticio
	 * @return 1 si se crea correctamente
	 */
	@Override
	public int create(PaqueteAlimenticioDTO data) {
		LanzadorDeException.verificarDireccion(data.getDireccionDestino());
		LanzadorDeException.verificarTamanoPaquete(data.getTamanio());
		LanzadorDeException.verificarTipoAlimento(data.getTipoDeAlimento());
		LanzadorDeException.verificarCiudad(data.getCiudadDestino());

		PaqueteAlimenticio entity = mapper.map(data, PaqueteAlimenticio.class);
		entity.setFechaCreacionPedido(LocalDateTime.now());
		entity.setFechaEstimadaEntrega(LocalDateTime.now().plusDays(1));
		entity.setEstadoPedido("EN_PROCESO");

		double precioBase = 15000;
		double precioFinal = calcularPrecioPorTamaño(precioBase, data.getTamanio());
		entity.setPrecioFinal(precioFinal);

		paqueteAlimenticioRep.save(entity);
		return 1;
	}

	/**
	 * Obtiene todos los paquetes alimenticios.
	 * * @return lista de paquetes alimenticios
	 */
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

	/**
	 * Elimina un paquete alimenticio por ID.
	 * * @param id identificador del paquete
	 * @return 0 si se elimina, 1 si no existe
	 */
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

	/**
	 * Actualiza un paquete alimenticio existente.
	 * * @param id identificador del paquete
	 * @param data nuevos datos
	 * @return 1 si se actualiza correctamente, 0 si no existe
	 */
	@Override
	public int updateById(Long id, PaqueteAlimenticioDTO data) {
		Optional<PaqueteAlimenticio> encontrado = paqueteAlimenticioRep.findById(id);
		if (encontrado.isPresent()) {
			PaqueteAlimenticio entity = encontrado.get();

			LanzadorDeException.verificarDireccion(data.getDireccionDestino());
			LanzadorDeException.verificarCiudad(data.getCiudadDestino());
			LanzadorDeException.verificarTamanoPaquete(data.getTamanio());
			LanzadorDeException.verificarTipoAlimento(data.getTipoDeAlimento());

			boolean valorPrevio = entity.isSeEnviaHoy();

			entity.setDireccionDestino(data.getDireccionDestino());
			entity.setCiudadDestino(data.getCiudadDestino());
			entity.setTamanio(data.getTamanio());
			entity.setTipoDeAlimento(data.getTipoDeAlimento());
			entity.setSeEnviaHoy(valorPrevio);

			double precioBase = 15000;
			double precioFinal = calcularPrecioPorTamaño(precioBase, data.getTamanio());
			entity.setPrecioFinal(precioFinal);

			paqueteAlimenticioRep.save(entity);
			return 1;
		}
		return 0;
	}

	/**
	 * Cuenta los paquetes alimenticios registrados.
	 * * @return total de paquetes
	 */
	@Override
	public long count() {
		return paqueteAlimenticioRep.count();
	}

	/**
	 * Verifica si existe un paquete por ID.
	 * * @param id identificador
	 * @return true si existe, false si no
	 */
	@Override
	public boolean exist(Long id) {
		return paqueteAlimenticioRep.existsById(id);
	}

	/**
	 * Busca paquetes por tamaño.
	 */
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

	/**
	 * Busca paquetes que se envían hoy.
	 */
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

	/**
	 * Busca paquetes por tipo de alimento.
	 */
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

	/**
	 * Busca paquetes por tamaño y tipo de alimento.
	 */
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

	/**
	 * Busca un paquete por ID.
	 */
	public PaqueteAlimenticioDTO findById(Long id) {
		Optional<PaqueteAlimenticio> encontrado = paqueteAlimenticioRep.findById(id);
		if (encontrado.isPresent()) {
			PaqueteAlimenticioDTO dto = mapper.map(encontrado.get(), PaqueteAlimenticioDTO.class);
			procesarEstadoYTiempoDTO(dto);
			return dto;
		}
		return null;
	}

	/**
	 * Busca paquetes por dirección y ciudad de destino.
	 */
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

	public List<PaqueteAlimenticioDTO> findByIdCLiente(long idCliente) {
		Optional<List<PaqueteAlimenticio>> encontrados = paqueteAlimenticioRep.findByIdCliente(idCliente);
		List<PaqueteAlimenticioDTO> dtoList = new ArrayList<>();
		
		if(encontrados.isPresent()) {
			for(PaqueteAlimenticio p : encontrados.get()) {//no requiere lista aparte
				PaqueteAlimenticioDTO dto = mapper.map(p, PaqueteAlimenticioDTO.class);
				dtoList.add(dto);
			}
		}
		
        return dtoList;
    }
	
	/**
	 * Calcula el precio basado en el tamaño del paquete.
	 */
	public double calcularPrecioPorTamaño(double base, String tamaño) {
		if (tamaño.equalsIgnoreCase("MEDIANO"))
			return base + 5000;
		if (tamaño.equalsIgnoreCase("GRANDE"))
			return base + 10000;
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
	 * Procesa el estado del pedido y verifica la prioridad según el tiempo restante.
	 */
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

	/**
	 * Registra un plazo de entrega de 6 horas.
	 */
	public int registrarPlazo6Horas(PaqueteAlimenticioDTO data) {
		data.setFechaEstimadaEntrega(data.getFechaCreacionPedido().plusHours(6));
		return 0;
	}

	/**
	 * Permite inyectar el repositorio manualmente (testing).
	 */
	public void setPaqueteAlimenticioRep(PaqueteAlimenticioRepository repo) {
		this.paqueteAlimenticioRep = repo;
	}

	/**
	 * Permite inyectar el mapper manualmente (testing).
	 */
	public void setMapper(ModelMapper mapper) {
		this.mapper = mapper;
	}
	
}