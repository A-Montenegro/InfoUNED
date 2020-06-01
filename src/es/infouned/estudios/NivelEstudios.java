package es.infouned.estudios;

import java.util.Stack;

/**
 * Clase que simboliza el nivel de estudios de una determinada titulación.
 * @author Alberto Martínez Montenegro
 * 
 */
public class NivelEstudios {
	private Stack<String> nomenclaturas;
	private NombreNivelEstudios nombreNivelEstudios;
	private String literal;
	
	public NivelEstudios(NombreNivelEstudios nombreNivelEstudios, Stack<String> nomenclaturas) {
		this.nombreNivelEstudios = nombreNivelEstudios;
		this.nomenclaturas = nomenclaturas;
		this.literal = generarLiteral(nombreNivelEstudios);
	}

	private String generarLiteral(NombreNivelEstudios nombreNivelEstudios) {
		switch(nombreNivelEstudios) {
		case MASTER:
			return "MÁSTER";
		case GRADO:
			return "GRADO";
		default:
			throw new IllegalArgumentException("Se ha intentado crear un objeto NivelEstudios pasándo un parámetro de tipo NombreNivelEstudios incorrecto.");
		}
	}
	
	public NombreNivelEstudios getNombreNivelEstudios() {
		return nombreNivelEstudios;
	}
	
	public String getLiteral() {
		return literal;
	}
	
	public Stack<String> getNomenclaturas() {
		return nomenclaturas;
	}

	public enum NombreNivelEstudios{
		MASTER,
		GRADO
	}
}

