package es.infouned.estudios;


public class Titulacion extends Estudio{
	private int idTitulacion;
	private String nombreTitulacion;
	private String nombreTitulacionSinAcentos;
	private String nivelEstudios;
	
	public Titulacion(int idTitulacion, String nombreTitulacion, String nivelEstudios) {
		this.idTitulacion = idTitulacion;
		this.nombreTitulacion = nombreTitulacion;
		this.nombreTitulacionSinAcentos = quitarAcentos(nombreTitulacion);
		this.nivelEstudios = nivelEstudios;
	}
	
	public int getIdTitulacion() {
		return idTitulacion;
	}
	
	public String getNombreTitulacion() {
		return nombreTitulacion;
	}
	
	public String getNombreTitulacionSinAcentos() {
		return nombreTitulacionSinAcentos;
	}
	
	public String getNivelEstudios() {
		return nivelEstudios;
	}
}
