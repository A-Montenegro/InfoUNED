package xyz.infouned.estudios;

public class Asignatura extends Estudio{
	private String idAsignatura;
	private String nombreAsignatura;
	private String nombreAsignaturaSinAcentos;
	private float creditos;
	
	public Asignatura(String idAsignatura, String nombreAsignatura) {
		this.idAsignatura = idAsignatura;
		this.nombreAsignatura = nombreAsignatura;
		this.nombreAsignaturaSinAcentos = quitarAcentos(nombreAsignatura);
	}

	public Asignatura(String idAsignatura, String nombreAsignatura, float creditos) {
		this.idAsignatura = idAsignatura;
		this.nombreAsignatura = nombreAsignatura;
		this.nombreAsignaturaSinAcentos = quitarAcentos(nombreAsignatura);
		this.creditos = creditos;
	}
	
	public String getIdAsignatura() {
		return idAsignatura;
	}
	
	public String getNombreAsignatura() {
		return nombreAsignatura;
	}
	
	public String getNombreAsignaturaSinAcentos() {
		return nombreAsignaturaSinAcentos;
	}	
	
	public float getCreditos() {
		return creditos;
	}
	
	public void setCreditos(float creditos) {
		this.creditos = creditos;
	}
}