package co.edu.unbosque.mensajeria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.mensajeria.entity.Administrador;

public interface AdministradorRepository extends CrudRepository<Administrador, Long> {
	
	public Optional<List<Administrador>> findByNombre(String nombre);
	public Optional<List<Administrador>> findByCedula(String cedula);
	public Optional<List<Administrador>> findByCorreo(String correo);	
	public Optional<List<Administrador>> findByTelefono(String telefono);		
	public Optional<List<Administrador>> findByUsuario(String usuario);		
	public Optional<List<Administrador>> findByContrasenia(String contrasenia);		
	
	public Optional<List<Administrador>> findByNombreAndCedula(String nombre, String cedula);	
	public Optional<List<Administrador>> findByUsuarioAndContrasenia(String usuario, String contrasenia);
	
	public boolean existsByCedula(String cedula);
}
