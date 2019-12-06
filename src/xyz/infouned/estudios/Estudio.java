package xyz.infouned.estudios;

import java.text.Normalizer;

public abstract class Estudio {
	protected String quitarAcentos(String nombreTitulacion) 
	{
		String nombreTitulacionSinAcentos = Normalizer.normalize(nombreTitulacion, Normalizer.Form.NFD);
		nombreTitulacionSinAcentos = nombreTitulacionSinAcentos.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
	    return nombreTitulacionSinAcentos;
	}
}
