package es.infouned.estudios;

/**
 * Clase que simboliza una titulación.
 * @author Alberto Martínez Montenegro
 * 
 */
public class Titulacion extends Estudio{
	private int idTitulacion;
	private String nivelEstudios;
	
	public Titulacion(int idTitulacion, String nombreTitulacion, String nivelEstudios) {
		super(nombreTitulacion);
		this.idTitulacion = idTitulacion;
		this.nivelEstudios = nivelEstudios;
	}
	
	public int getIdTitulacion() {
		return idTitulacion;
	}
	
	public String getNivelEstudios() {
		return nivelEstudios;
	}
}
