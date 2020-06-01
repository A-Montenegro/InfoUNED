package es.infouned.estudios;

/**
 * Clase que simboliza una asignatura común.
 * @author Alberto Martínez Montenegro
 * 
 */
public class Asignatura extends Estudio{
	private String idAsignatura;
	private float creditos;
	
	public Asignatura(String idAsignatura, String nombreAsignatura) {
		super(nombreAsignatura);
		this.idAsignatura = idAsignatura;
	}

	public Asignatura(String idAsignatura, String nombreAsignatura, float creditos) {
		super(nombreAsignatura);
		this.idAsignatura = idAsignatura;
		this.creditos = creditos;
	}

	public String getIdAsignatura() {
		return idAsignatura;
	}
	
	public float getCreditos() {
		return creditos;
	}
	
	public void setCreditos(float creditos) {
		this.creditos = creditos;
	}
}