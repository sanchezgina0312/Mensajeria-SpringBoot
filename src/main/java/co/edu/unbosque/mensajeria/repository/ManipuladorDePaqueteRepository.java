package co.edu.unbosque.mensajeria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.mensajeria.entity.ManipuladorDePaquete;

public interface ManipuladorDePaqueteRepository extends CrudRepository<ManipuladorDePaquete, Long> {

	public Optional<List<ManipuladorDePaquete>> findByNombre(String nombre);
	public Optional<List<ManipuladorDePaquete>> findByCedula(String cedula);
	public Optional<List<ManipuladorDePaquete>> findByCorreo(String correo);	
	public Optional<List<ManipuladorDePaquete>> findByTelefono(String telefono);		
	public Optional<List<ManipuladorDePaquete>> findByTipoManipulador(String tipoManipulador);
	
	public Optional<List<ManipuladorDePaquete>> findByNombreAndCedula(String nombre, String cedula);	

	public boolean existsByCedula(String cedula);
}
