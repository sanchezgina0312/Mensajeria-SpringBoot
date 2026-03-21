package co.edu.unbosque.mensajeria.dto;

import java.util.Objects;

import co.edu.unbosque.mensajeria.entity.Trabajador;

public class AdministradorDTO extends Trabajador {

	private String usuario = "admin";
	private String contrasenia = "1234";

	public AdministradorDTO() {

	}

	public AdministradorDTO(String usuario, String contrasenia) {
		super();
		this.usuario = usuario;
		this.contrasenia = contrasenia;
	}

	public AdministradorDTO(char turno, String usuario, String contrasenia) {
		super(turno);
		this.usuario = usuario;
		this.contrasenia = contrasenia;
	}

	public AdministradorDTO(String nombre, String cedula, String correo, String telefono, char turno, String usuario,
			String contrasenia) {
		super(nombre, cedula, correo, telefono, turno);
		this.usuario = usuario;
		this.contrasenia = contrasenia;
	}

	public AdministradorDTO(String nombre, String cedula, String correo, String telefono, String usuario,
			String contrasenia) {
		super(nombre, cedula, correo, telefono);
		this.usuario = usuario;
		this.contrasenia = contrasenia;
	}

	public AdministradorDTO(char turno) {
		super(turno);

	}

	public AdministradorDTO(String nombre, String cedula, String correo, String telefono, char turno) {
		super(nombre, cedula, correo, telefono, turno);

	}

	public AdministradorDTO(String nombre, String cedula, String correo, String telefono) {
		super(nombre, cedula, correo, telefono);

	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	@Override
	public String toString() {
		return super.toString() + "\n Administrador:\n Usuario:" + usuario + "\n Contraseña:" + contrasenia + ".";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(contrasenia, usuario);
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
		AdministradorDTO other = (AdministradorDTO) obj;
		return Objects.equals(contrasenia, other.contrasenia) && Objects.equals(usuario, other.usuario);
	}

}