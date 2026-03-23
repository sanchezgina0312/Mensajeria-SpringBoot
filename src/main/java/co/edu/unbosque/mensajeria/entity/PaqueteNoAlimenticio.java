package co.edu.unbosque.mensajeria.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "paquetenoalimenticio")
public class PaqueteNoAlimenticio extends Paquete {

	private boolean esFragil;

	public PaqueteNoAlimenticio() {

	}

	public PaqueteNoAlimenticio(boolean esFragil) {
		super();
		this.esFragil = esFragil;
	}

	public PaqueteNoAlimenticio(long id, int precioEnvio, String direccionDestino, String tamanio,
			LocalDateTime fechaCreacionPedido, LocalDateTime fechaEstimadaEntrega, String ciudadDestino,
			String estadoPedido, boolean esPrioritario, double precioFinal) {
		super(id, precioEnvio, direccionDestino, tamanio, fechaCreacionPedido, fechaEstimadaEntrega, ciudadDestino,
				estadoPedido, esPrioritario, precioFinal);
	}

	public PaqueteNoAlimenticio(long id, int precioEnvio, String direccionDestino, String tamanio,
			LocalDateTime fechaCreacionPedido, LocalDateTime fechaEstimadaEntrega, String ciudadDestino,
			String estadoPedido, boolean esPrioritario, double precioFinal, boolean esFragil) {
		super(id, precioEnvio, direccionDestino, tamanio, fechaCreacionPedido, fechaEstimadaEntrega, ciudadDestino,
				estadoPedido, esPrioritario, precioFinal);
		this.esFragil = esFragil;
	}

	public boolean isEsFragil() {
		return esFragil;
	}

	public void setEsFragil(boolean esFragil) {
		this.esFragil = esFragil;
	}

	@Override
	public String toString() {
		return super.toString() + "\n PaqueteNoAlimenticio: \n ¿Es Frágil?:" + esFragil + ".";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(esFragil);
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
		PaqueteNoAlimenticio other = (PaqueteNoAlimenticio) obj;
		return esFragil == other.esFragil;
	}

}