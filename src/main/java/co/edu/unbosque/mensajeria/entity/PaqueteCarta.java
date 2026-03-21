package co.edu.unbosque.mensajeria.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "paquetecarta")
public class PaqueteCarta extends Paquete {

	private String tipoCarta;

	public PaqueteCarta() {

	}

	public PaqueteCarta(String tipoCarta) {
		super();
		this.tipoCarta = tipoCarta;
	}

	public PaqueteCarta(int precioEnvio, String direccionDestino, String tamanio, LocalDateTime fechaCreacionPedido,
			LocalDateTime fechaEstimadaEntrega, String tipoCarta) {
		super(precioEnvio, direccionDestino, tamanio, fechaCreacionPedido, fechaEstimadaEntrega);
		this.tipoCarta = tipoCarta;
	}

	public String getTipoCarta() {
		return tipoCarta;
	}

	public void setTipoCarta(String tipoCarta) {
		this.tipoCarta = tipoCarta;
	}

	@Override
	public String toString() {
		return super.toString() + "\n PaqueteCarta:\n Tipo Carta:" + tipoCarta + ".";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(tipoCarta);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PaqueteCarta other = (PaqueteCarta) obj;
		return Objects.equals(tipoCarta, other.tipoCarta);
	}

}