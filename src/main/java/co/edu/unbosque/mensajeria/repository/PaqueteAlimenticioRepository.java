package co.edu.unbosque.mensajeria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.mensajeria.entity.PaqueteAlimenticio;

public interface PaqueteAlimenticioRepository extends CrudRepository<PaqueteAlimenticio, Long> {

	public Optional<List<PaqueteAlimenticio>> findByTamanio(String tamanio);
	public Optional<List<PaqueteAlimenticio>> findBySeEnviaHoy(boolean seEnviaHoy);
	public Optional<List<PaqueteAlimenticio>> findByTipoDeAlimento(String tipoDeAlimento);
	public Optional<List<PaqueteAlimenticio>> findByTamanioAndTipoDeAlimento(String tamanio, String tipoDeAlimento);
	public Optional<List<PaqueteAlimenticio>> findByDireccionDestinoAndCiudadDestino(String direccionDestino, String ciudadDestino);

}