package co.edu.unbosque.mensajeria.repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.mensajeria.entity.PaqueteCarta;
import co.edu.unbosque.mensajeria.entity.PaqueteNoAlimenticio;

public interface PaqueteCartaRepository extends CrudRepository<PaqueteCarta, Long> {
	
	public Optional<List<PaqueteCarta>>findByPrecioEnvio(int precioEnvio);	
	public Optional<List<PaqueteCarta>>findByDireccionDestino(String direccionDestino);
	public Optional<List<PaqueteCarta>>findByTamanio(String tamanio);
	public Optional<List<PaqueteCarta>>findByFechaCreacionPedido(LocalDateTime fechaCreacionPedido);
	public Optional<List<PaqueteCarta>>findByFechaEstimadaEntrega(LocalDateTime fechaEstimadaEntrega);
	public Optional<List<PaqueteCarta>>findByTipoCarta(String tipoCarta);
	
	public Optional<List<PaqueteCarta>>findByTamanioYTipoCarta(boolean esFragil, String tipoCarta);
	
}