package co.edu.unbosque.mensajeria.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "paquete")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Paquete {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;
	private int precioEnvio;
	private String direccionDestino;
	private String tamanio;
	private LocalDateTime fechaCreacionPedido;
	private LocalDateTime fechaEstimadaEntrega;
	private String ciudadDestino;
	private String estadoPedido;
	private boolean esPrioritario = false;
	private double precioFinal;

	public Paquete() {

	}
	
	public Paquete(long id, int precioEnvio, String direccionDestino, String tamanio, LocalDateTime fechaCreacionPedido,
			LocalDateTime fechaEstimadaEntrega, String ciudadDestino, String estadoPedido, boolean esPrioritario,
			double precioFinal) {
		super();
		this.id = id;
		this.precioEnvio = precioEnvio;
		this.direccionDestino = direccionDestino;
		this.tamanio = tamanio;
		this.fechaCreacionPedido = fechaCreacionPedido;
		this.fechaEstimadaEntrega = fechaEstimadaEntrega;
		this.ciudadDestino = ciudadDestino;
		this.estadoPedido = estadoPedido;
		this.esPrioritario = esPrioritario;
		this.precioFinal = precioFinal;
	}
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getPrecioEnvio() {
		return precioEnvio;
	}

	public void setPrecioEnvio(int precioEnvio) {
		this.precioEnvio = precioEnvio;
	}

	public String getDireccionDestino() {
		return direccionDestino;
	}

	public void setDireccionDestino(String direccionDestino) {
		this.direccionDestino = direccionDestino;
	}

	public String getTamanio() {
		return tamanio;
	}

	public void setTamanio(String tamanio) {
		this.tamanio = tamanio;
	}

	public LocalDateTime getFechaCreacionPedido() {
		return fechaCreacionPedido;
	}

	public void setFechaCreacionPedido(LocalDateTime fechaCreacionPedido) {
		this.fechaCreacionPedido = fechaCreacionPedido;
	}

	public LocalDateTime getFechaEstimadaEntrega() {
		return fechaEstimadaEntrega;
	}

	public void setFechaEstimadaEntrega(LocalDateTime fechaEstimadaEntrega) {
		this.fechaEstimadaEntrega = fechaEstimadaEntrega;
	}

	public String getCiudadDestino() {
		return ciudadDestino;
	}

	public void setCiudadDestino(String ciudadDestino) {
		this.ciudadDestino = ciudadDestino;
	}

	public String getEstadoPedido() {
		return estadoPedido;
	}

	public void setEstadoPedido(String estadoPedido) {
		this.estadoPedido = estadoPedido;
	}

	public boolean isEsPrioritario() {
		return esPrioritario;
	}

	public void setEsPrioritario(boolean esPrioritario) {
		this.esPrioritario = esPrioritario;
	}

	public double getPrecioFinal() {
		return precioFinal;
	}

	public void setPrecioFinal(double precioFinal) {
		this.precioFinal = precioFinal;
	}

	@Override
	public String toString() {
		return "Paquete:\n Id:" + id + "\n Precio Envío:" + precioEnvio + "\n Dirección Destino:" + direccionDestino
				+ "\n Tamaño:" + tamanio + "\n Fecha Creación Pedido:" + fechaCreacionPedido
				+ "\n Fecha Estimada Entrega:" + fechaEstimadaEntrega + "\n Ciudad Destino:" + ciudadDestino
				+ "\n Estado Pedido:" + estadoPedido + "\n Prioridad:" + esPrioritario + "\n Precio Final:" + precioFinal
				+ ".";
	}

	@Override
	public int hashCode() {
		return Objects.hash(ciudadDestino, direccionDestino, esPrioritario, estadoPedido, fechaCreacionPedido,
				fechaEstimadaEntrega, id, precioEnvio, precioFinal, tamanio);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Paquete other = (Paquete) obj;
		return Objects.equals(ciudadDestino, other.ciudadDestino)
				&& Objects.equals(direccionDestino, other.direccionDestino) && esPrioritario == other.esPrioritario
				&& Objects.equals(estadoPedido, other.estadoPedido)
				&& Objects.equals(fechaCreacionPedido, other.fechaCreacionPedido)
				&& Objects.equals(fechaEstimadaEntrega, other.fechaEstimadaEntrega) && id == other.id
				&& precioEnvio == other.precioEnvio
				&& Double.doubleToLongBits(precioFinal) == Double.doubleToLongBits(other.precioFinal)
				&& Objects.equals(tamanio, other.tamanio);
	}

}
