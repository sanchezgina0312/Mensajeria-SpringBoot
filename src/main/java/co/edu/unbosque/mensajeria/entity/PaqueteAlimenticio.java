package co.edu.unbosque.mensajeria.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "paquetealimenticio")
public class PaqueteAlimenticio extends Paquete {

	private boolean seEnviaHoy;
	private String tipoDeAlimento;

	public PaqueteAlimenticio() {

	}

	public PaqueteAlimenticio(boolean seEnviaHoy, String tipoDeAlimento) {
		super();
		this.seEnviaHoy = seEnviaHoy;
		this.tipoDeAlimento = tipoDeAlimento;
	}

	public PaqueteAlimenticio(int precioEnvio, String direccionDestino, String tamanio,
			LocalDateTime fechaCreacionPedido, LocalDateTime fechaEstimadaEntrega, boolean seEnviaHoy,
			String tipoDeAlimento) {
		super(precioEnvio, direccionDestino, tamanio, fechaCreacionPedido, fechaEstimadaEntrega);
		this.seEnviaHoy = seEnviaHoy;
		this.tipoDeAlimento = tipoDeAlimento;
	}

	public PaqueteAlimenticio(int precioEnvio, String direccionDestino, String tamanio,
			LocalDateTime fechaCreacionPedido, LocalDateTime fechaEstimadaEntrega) {
		super(precioEnvio, direccionDestino, tamanio, fechaCreacionPedido, fechaEstimadaEntrega);
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
		PaqueteAlimenticio other = (PaqueteAlimenticio) obj;
		return seEnviaHoy == other.seEnviaHoy && Objects.equals(tipoDeAlimento, other.tipoDeAlimento);
	}

}
