package es.infouned.estudios;

import java.text.Normalizer;

public abstract class Estudio {
	private String nombre;
	
	protected Estudio(String nombreEstudio) {
		this.nombre = nombreEstudio;
	}
	
	protected String quitarAcentos(String nombreTitulacion) 
	{
		String nombreTitulacionSinAcentos = Normalizer.normalize(nombreTitulacion, Normalizer.Form.NFD);
		nombreTitulacionSinAcentos = nombreTitulacionSinAcentos.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
	    return nombreTitulacionSinAcentos;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public enum TipoEstudio {
		  TITULACION,
		  ASIGNATURA,
		  AMBIGUO
	}
}
