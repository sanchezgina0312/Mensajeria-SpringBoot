package co.edu.unbosque.mensajeria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.mensajeria.entity.ClienteNormal;



public interface ClienteNormalRepository extends CrudRepository<ClienteNormal, Long> {
	
	public Optional<List<ClienteNormal>> findByNombre(String nombre);
	public Optional<List<ClienteNormal>> findByCedula(String cedula);
	public Optional<List<ClienteNormal>> findByCorreo(String correo);	
	public Optional<List<ClienteNormal>> findByTelefono(String telefono);		
	public Optional<List<ClienteNormal>> findByMetodoPago(String metodoPago);	
	public Optional<List<ClienteNormal>> findByTipoPedido(String tipoPedido);	
	public Optional<List<ClienteNormal>> findByNombreAndCedula(String nombre, String cedula);	
	public Optional<List<ClienteNormal>> findByTipoPedidoAndMetodoPago(String tipoPedido, String metodoPago);

	public boolean existsByCedula(String cedula);

}