package co.edu.unbosque.mensajeria.dto;

import java.time.LocalDateTime;
import java.util.Objects;

import co.edu.unbosque.mensajeria.entity.Paquete;

public class PaqueteCartaDTO extends Paquete {

	private String tipoCarta;

	public PaqueteCartaDTO() {

	}

	public PaqueteCartaDTO(String tipoCarta) {
		super();
		this.tipoCarta = tipoCarta;
	}

	public PaqueteCartaDTO(int precioEnvio, String direccionDestino, String tamanio, LocalDateTime fechaCreacionPedido,
			LocalDateTime fechaEstimadaEntrega, String tipoCarta) {
		super(precioEnvio, direccionDestino, tamanio, fechaCreacionPedido, fechaEstimadaEntrega);
		this.tipoCarta = tipoCarta;
	}

	public PaqueteCartaDTO(int precioEnvio, String direccionDestino, String tamanio, LocalDateTime fechaCreacionPedido,
			LocalDateTime fechaEstimadaEntrega) {
		super(precioEnvio, direccionDestino, tamanio, fechaCreacionPedido, fechaEstimadaEntrega);
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
		PaqueteCartaDTO other = (PaqueteCartaDTO) obj;
		return Objects.equals(tipoCarta, other.tipoCarta);
	}

}
