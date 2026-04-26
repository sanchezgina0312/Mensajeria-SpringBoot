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

/**
 * Servicio encargado de gestionar las operaciones CRUD de los paquetes de tipo carta.
 * <p>
 * Permite crear, consultar, actualizar y eliminar paquetes de carta, así como
 * realizar cálculos de precios por tamaño, gestionar el estado del envío y 
 * procesar tiempos de entrega estimados.
 * Utiliza ModelMapper para la conversión entre entidades y DTOs.
 * </p>
 */
@Service
public class PaqueteCartaService implements CRUDOperation<PaqueteCartaDTO> {

	@Autowired
	private PaqueteCartaRepository paqueteCartaRep;

	@Autowired
	private ModelMapper mapper;

	/**
	 * Constructor por defecto.
	 */
	public PaqueteCartaService() {

	}

	/**
	 * Crea un nuevo paquete de carta.
	 * * @param data datos del paquete de carta
	 * @return 1 si se crea correctamente
	 */
	@Override
	public int create(PaqueteCartaDTO data) {
		LanzadorDeException.verificarDireccion(data.getDireccionDestino());
		LanzadorDeException.verificarTipoCarta(data.getTipoCarta());
		LanzadorDeException.verificarTamanoPaquete(data.getTamanio());
		LanzadorDeException.verificarCiudad(data.getCiudadDestino());

		PaqueteCarta entity = mapper.map(data, PaqueteCarta.class);
		entity.setIdCliente(data.getIdCliente());
		entity.setFechaCreacionPedido(LocalDateTime.now());
		entity.setFechaEstimadaEntrega(LocalDateTime.now().plusDays(2));
		entity.setEstadoPedido("EN_PROCESO");
		entity.setEsPrioritario(data.isEsPrioritario());
		
		double precioBase = 5000;
		double precioFinal = calcularPrecioPorTamaño(precioBase, data.getTamanio());
		entity.setPrecioFinal(precioFinal);

		paqueteCartaRep.save(entity);
		return 1;
	}

	/**
	 * Obtiene todos los paquetes de carta.
	 * * @return lista de paquetes de carta
	 */
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

	/**
	 * Elimina un paquete de carta por ID.
	 * * @param id identificador del paquete
	 * @return 0 si se elimina, 1 si no existe
	 */
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

	/**
	 * Actualiza un paquete de carta existente.
	 * * @param id identificador del paquete
	 * @param data nuevos datos
	 * @return 1 si se actualiza correctamente, 0 si hay error
	 */
	@Override
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

	/**
	 * Cuenta los paquetes de carta registrados.
	 * * @return total de paquetes
	 */
	@Override
	public long count() {
		return paqueteCartaRep.count();
	}

	/**
	 * Verifica si existe un paquete de carta por ID.
	 * * @param id identificador
	 * @return true si existe, false si no
	 */
	@Override
	public boolean exist(Long id) {
		return paqueteCartaRep.existsById(id) ? true : false;
	}

	/**
	 * Busca paquetes de carta por tamaño.
	 */
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

	/**
	 * Busca paquetes de carta por tipo de carta.
	 */
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

	/**
	 * Busca paquetes de carta por tamaño y tipo de carta.
	 */
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

	/**
	 * Busca un paquete de carta por ID.
	 */
	public PaqueteCartaDTO findById(Long id) {
		Optional<PaqueteCarta> encontrado = paqueteCartaRep.findById(id);
		if (encontrado.isPresent()) {
			PaqueteCartaDTO dto = mapper.map(encontrado.get(), PaqueteCartaDTO.class);
			procesarEstadoYTiempoDTO(dto);
			return dto;
		}
		return null;
	}

	/**
	 * Busca paquetes de carta por dirección y ciudad de destino.
	 */
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
	
	public List<PaqueteCartaDTO> findByIdCliente(long idCliente) {
	    Optional<List<PaqueteCarta>> encontrados = paqueteCartaRep.findByIdCliente(idCliente);
	    List<PaqueteCartaDTO> dtoList = new ArrayList<>();
	    
	    if(encontrados.isPresent()) {
	        for(PaqueteCarta p : encontrados.get()) {
	            PaqueteCartaDTO dto = mapper.map(p, PaqueteCartaDTO.class);
	            // LLAMADA AL MOTOR DE TIEMPO
	            procesarEstadoYTiempoDTO(dto); 
	            dtoList.add(dto);
	        }
	    }
	    return dtoList;
	}

	/**
	 * Calcula el precio basado en el tamaño del paquete de carta.
	 */
	public double calcularPrecioPorTamaño(double base, String tamaño) {
		if (tamaño.equalsIgnoreCase("MEDIANO"))
			return base + 2000;
		if (tamaño.equalsIgnoreCase("GRANDE"))
			return base + 4000;
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

	/**
	 * Registra un plazo de entrega de 72 horas.
	 */
	public int registrarPlazo72Horas(PaqueteCartaDTO data) {
		data.setFechaEstimadaEntrega(data.getFechaCreacionPedido().plusHours(72));
		return 0;
	}

	/**
	 * Permite inyectar el repositorio manualmente (testing).
	 */
	public void setPaqueteCartaRep(PaqueteCartaRepository repo) {
		this.paqueteCartaRep = repo;
	}

	/**
	 * Permite inyectar el mapper manualmente (testing).
	 */
	public void setMapper(ModelMapper mapper) {
		this.mapper = mapper;
	}

}