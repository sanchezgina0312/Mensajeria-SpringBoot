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

	public Paquete() {

	}

	public Paquete(int precioEnvio, String direccionDestino, String tamanio, LocalDateTime fechaCreacionPedido,
			LocalDateTime fechaEstimadaEntrega) {
		super();
		this.precioEnvio = precioEnvio;
		this.direccionDestino = direccionDestino;
		this.tamanio = tamanio;
		this.fechaCreacionPedido = fechaCreacionPedido;
		this.fechaEstimadaEntrega = fechaEstimadaEntrega;
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

	@Override
	public String toString() {
		return "Paquete:\n Id:" + id + "\n Precio Envío:" + precioEnvio + "\n Dirección Destino:" + direccionDestino
				+ "\n Tamaño:" + tamanio + "\n Fecha Creación Pedido:" + fechaCreacionPedido + "\n Fecha Estimada Entrega:"
				+ fechaEstimadaEntrega + ".";
	}

	@Override
	public int hashCode() {
		return Objects.hash(direccionDestino, fechaCreacionPedido, fechaEstimadaEntrega, id, precioEnvio, tamanio);
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
		return Objects.equals(direccionDestino, other.direccionDestino)
				&& Objects.equals(fechaCreacionPedido, other.fechaCreacionPedido)
				&& Objects.equals(fechaEstimadaEntrega, other.fechaEstimadaEntrega) && id == other.id
				&& precioEnvio == other.precioEnvio && Objects.equals(tamanio, other.tamanio);
	}

}
