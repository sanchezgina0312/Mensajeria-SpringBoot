package co.edu.unbosque.mensajeria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.mensajeria.entity.ClienteConcurrente;

public interface ClienteConcurrenteRepository extends CrudRepository<ClienteConcurrente, Long> {
	
	public Optional<List<ClienteConcurrente>> findByNombre(String nombre);
	public Optional<List<ClienteConcurrente>> findByCedula(String cedula);
	public Optional<List<ClienteConcurrente>> findByCorreo(String correo);	
	public Optional<List<ClienteConcurrente>> findByTelefono(String telefono);		
	public Optional<List<ClienteConcurrente>> findByMetodoPago(String metodoPago);	
	public Optional<List<ClienteConcurrente>> findByTipoPedido(String tipoPedido);	
	

	public Optional<List<ClienteConcurrente>> findByNombreAndCedula(String nombre, String cedula);	
	public Optional<List<ClienteConcurrente>> findByTipoPedidoAndMetodoPago(String tipoPedido, String metodoPago);
	
}