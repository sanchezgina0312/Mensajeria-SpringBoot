package co.edu.unbosque.mensajeria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.mensajeria.entity.PaqueteCarta;

public interface PaqueteCartaRepository extends CrudRepository<PaqueteCarta, Long> {

	public Optional<List<PaqueteCarta>> findByTamanio(String tamanio);
	public Optional<List<PaqueteCarta>> findByTipoCarta(String tipoCarta);
	public Optional<List<PaqueteCarta>> findByTamanioAndTipoCarta(String tamanio, String tipoCarta);
	public Optional<List<PaqueteCarta>> findByDireccionDestino(String direccionDestino);

}