package co.edu.unbosque.mensajeria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.mensajeria.entity.Conductor;

public interface ConductorRepository extends CrudRepository<Conductor, Long> {
	
	public Optional<List<Conductor>> findByNombre(String nombre);
	public Optional<List<Conductor>> findByCedula(String cedula);
	public Optional<List<Conductor>> findByCorreo(String correo);	
	public Optional<List<Conductor>> findByTelefono(String telefono);		
	public Optional<List<Conductor>> findByPlacaVehiculo(String placaVehiculo);
	
	public Optional<List<Conductor>> findByNombreAndCedula(String nombre, String cedula);	
	public Optional<List<Conductor>> findByPlacaVehiculoAndNombre(String placaVehiculo, String nombre);
	
}