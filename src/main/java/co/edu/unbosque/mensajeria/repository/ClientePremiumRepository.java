package co.edu.unbosque.mensajeria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.mensajeria.entity.ClientePremium;

public interface ClientePremiumRepository extends CrudRepository<ClientePremium, Long> {


	public Optional<List<ClientePremium>> findByNombre(String nombre);
	public Optional<List<ClientePremium>> findByCedula(String cedula);
	public Optional<List<ClientePremium>> findByCorreo(String correo);	
	public Optional<List<ClientePremium>> findByTelefono(String telefono);		
	public Optional<List<ClientePremium>> findByMetodoPago(String metodoPago);	
	public Optional<List<ClientePremium>> findByTipoPedido(String tipoPedido);	
	
	
	public Optional<List<ClientePremium>> findByNombreAndCedula(String nombre, String cedula);	
	public Optional<List<ClientePremium>> findByTipoPedidoAndMetodoPago(String tipoPedido, String metodoPago);
	
	public boolean existByCedula(String cedula);
}