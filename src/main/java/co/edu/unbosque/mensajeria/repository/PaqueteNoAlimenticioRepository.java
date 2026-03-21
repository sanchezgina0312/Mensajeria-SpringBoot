package co.edu.unbosque.mensajeria.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.mensajeria.entity.PaqueteCarta;
import co.edu.unbosque.mensajeria.entity.PaqueteNoAlimenticio;

public interface PaqueteNoAlimenticioRepository extends CrudRepository<PaqueteNoAlimenticio, Long> {

	public Optional<List<PaqueteNoAlimenticio>>findByPrecioEnvio(int precioEnvio);	
	public Optional<List<PaqueteNoAlimenticio>>findByDireccionDestino(String direccionDestino);
	public Optional<List<PaqueteNoAlimenticio>>findByTamanio(String tamanio);
	public Optional<List<PaqueteNoAlimenticio>>findByFechaCreacionPedido(LocalDateTime fechaCreacionPedido);
	public Optional<List<PaqueteNoAlimenticio>>findByFechaEstimadaEntrega(LocalDateTime fechaEstimadaEntrega);
	public Optional<List<PaqueteNoAlimenticio>> findByEsFragil(boolean esFragil);
	
	public Optional<List<PaqueteNoAlimenticio>>findByTamanioYEsFragil(boolean esFragil, String tamanio);
	
}
