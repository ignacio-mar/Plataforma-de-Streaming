package model;

public class Usuario {
	private int id;
	private String nombreUsuario;
	private String email;
	private String contrasenia;
	private int dniPersona;

	public Usuario(int id, String nombreUsuario, String email, String contrasenia, int dniPersona) {
		this.id = id;
		this.nombreUsuario = nombreUsuario;
		this.email = email;
		this.contrasenia = contrasenia;
		this.dniPersona = dniPersona;
	}

	public Usuario(String nombreUsuario, String email, String contrasenia, int dniPersona) {
		this(0, nombreUsuario, email, contrasenia, dniPersona); // id 0 para nuevos usuarios
	}

	public int getId() {
		return id;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public String getEmail() {
		return email;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public int getDniPersona() {
		return dniPersona;
	}

	@Override public String toString() {
		return "Usuario{id=" + id + ", nombreUsuario='" + nombreUsuario + '\'' +
		       ", email='" + email + '\'' + ", contrasenia='" + contrasenia + '\'' +
		       ", dniPersona=" + dniPersona + '}';
	}
}
