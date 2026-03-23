package co.edu.unbosque.mensajeria.dto;

import java.time.LocalDateTime;
import java.util.Objects;

import co.edu.unbosque.mensajeria.entity.Paquete;

public class PaqueteAlimenticioDTO extends Paquete {

	private boolean seEnviaHoy;
	private String tipoDeAlimento;

	public PaqueteAlimenticioDTO() {

	}

	public PaqueteAlimenticioDTO(boolean seEnviaHoy, String tipoDeAlimento) {
		super();
		this.seEnviaHoy = seEnviaHoy;
		this.tipoDeAlimento = tipoDeAlimento;
	}

	public PaqueteAlimenticioDTO(long id, int precioEnvio, String direccionDestino, String tamanio,
			LocalDateTime fechaCreacionPedido, LocalDateTime fechaEstimadaEntrega, String ciudadDestino,
			String estadoPedido, boolean esPrioritario, double precioFinal, boolean seEnviaHoy, String tipoDeAlimento) {
		super(id, precioEnvio, direccionDestino, tamanio, fechaCreacionPedido, fechaEstimadaEntrega, ciudadDestino,
				estadoPedido, esPrioritario, precioFinal);
		this.seEnviaHoy = seEnviaHoy;
		this.tipoDeAlimento = tipoDeAlimento;
	}

	public PaqueteAlimenticioDTO(long id, int precioEnvio, String direccionDestino, String tamanio,
			LocalDateTime fechaCreacionPedido, LocalDateTime fechaEstimadaEntrega, String ciudadDestino,
			String estadoPedido, boolean esPrioritario, double precioFinal) {
		super(id, precioEnvio, direccionDestino, tamanio, fechaCreacionPedido, fechaEstimadaEntrega, ciudadDestino,
				estadoPedido, esPrioritario, precioFinal);
		// TODO Auto-generated constructor stub
	}

	public boolean isSeEnviaHoy() {
		return seEnviaHoy;
	}

	public void setSeEnviaHoy(boolean seEnviaHoy) {
		this.seEnviaHoy = seEnviaHoy;
	}

	public String getTipoDeAlimento() {
		return tipoDeAlimento;
	}

	public void setTipoDeAlimento(String tipoDeAlimento) {
		this.tipoDeAlimento = tipoDeAlimento;
	}

	@Override
	public String toString() {
		return super.toString() + "\n Paquete Alimenticio:\n ¿Se Envía Hoy?:" + seEnviaHoy + "\n Tipo De Alimento:"
				+ tipoDeAlimento + ".";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(seEnviaHoy, tipoDeAlimento);
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
		PaqueteAlimenticioDTO other = (PaqueteAlimenticioDTO) obj;
		return seEnviaHoy == other.seEnviaHoy && Objects.equals(tipoDeAlimento, other.tipoDeAlimento);
	}

}