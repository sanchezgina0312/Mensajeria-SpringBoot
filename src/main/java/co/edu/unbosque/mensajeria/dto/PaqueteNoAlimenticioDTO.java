package co.edu.unbosque.mensajeria.dto;

import java.time.LocalDateTime;
import java.util.Objects;

import co.edu.unbosque.mensajeria.entity.Paquete;

public class PaqueteNoAlimenticioDTO extends Paquete {

	private boolean esFragil;

	public PaqueteNoAlimenticioDTO() {

	}

	public PaqueteNoAlimenticioDTO(boolean esFragil) {
		super();
		this.esFragil = esFragil;
	}

	public PaqueteNoAlimenticioDTO(int precioEnvio, String direccionDestino, String tamanio,
			LocalDateTime fechaCreacionPedido, LocalDateTime fechaEstimadaEntrega, boolean esFragil) {
		super(precioEnvio, direccionDestino, tamanio, fechaCreacionPedido, fechaEstimadaEntrega);
		this.esFragil = esFragil;
	}

	public PaqueteNoAlimenticioDTO(int precioEnvio, String direccionDestino, String tamanio,
			LocalDateTime fechaCreacionPedido, LocalDateTime fechaEstimadaEntrega) {
		super(precioEnvio, direccionDestino, tamanio, fechaCreacionPedido, fechaEstimadaEntrega);
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
		PaqueteNoAlimenticioDTO other = (PaqueteNoAlimenticioDTO) obj;
		return esFragil == other.esFragil;
	}

}
