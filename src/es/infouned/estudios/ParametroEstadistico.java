package es.infouned.estudios;

import java.util.Stack;

import es.infouned.estudios.Estudio.TipoEstudio;

/**
 * Clase que simboliza un parámetro estadístico detectable. Se usará para consultas específicas a la base de datos.
 * @author Alberto Martínez Montenegro
 * 
 */
public class ParametroEstadistico {
	private TipoEstudio tipoEstudio;
	private String literal;
	private String nombre;
	private Stack<String> nomenclaturas;
	
	public ParametroEstadistico(TipoEstudio tipoEstudio, String literal, String nombre, Stack<String> nomenclaturas) {
		this.tipoEstudio = tipoEstudio;
		this.literal = literal;
		this.nombre = nombre;
		this.nomenclaturas = nomenclaturas;
	}

	public String getNombre() {
		return nombre;
	}

	public TipoEstudio getTipoEstudio() {
		return tipoEstudio;
	}

	public String getLiteral() {
		return literal;
	}

	public Stack<String> getNomenclaturas() {
		return nomenclaturas;
	}
}

