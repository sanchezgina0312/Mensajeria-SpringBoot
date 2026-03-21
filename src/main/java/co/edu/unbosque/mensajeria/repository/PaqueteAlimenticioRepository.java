package co.edu.unbosque.mensajeria.repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.mensajeria.entity.PaqueteAlimenticio;
import co.edu.unbosque.mensajeria.entity.PaqueteNoAlimenticio;

public interface PaqueteAlimenticioRepository extends CrudRepository<PaqueteAlimenticio, Long> {
	
	public Optional<List<PaqueteAlimenticio>>findByPrecioEnvio(int precioEnvio);	
	public Optional<List<PaqueteAlimenticio>>findByDireccionDestino(String direccionDestino);
	public Optional<List<PaqueteAlimenticio>>findByTamanio(String tamanio);
	public Optional<List<PaqueteAlimenticio>>findByFechaCreacionPedido(LocalDateTime fechaCreacionPedido);
	public Optional<List<PaqueteAlimenticio>>findByFechaEstimadaEntrega(LocalDateTime fechaEstimadaEntrega);
	public Optional<List<PaqueteAlimenticio>>findBySeEnviaHoy(boolean seEnviaHoy);	
	public Optional<List<PaqueteAlimenticio>>findByTipoDeAlimento(String tipoDeAlimento);
	
	public Optional<List<PaqueteAlimenticio>>findByTamanioYTipoDeAlimento(boolean esFragil, String tipoDeAlimento);
}