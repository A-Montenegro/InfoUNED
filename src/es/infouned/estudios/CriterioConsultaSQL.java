package es.infouned.estudios;

import java.util.Stack;

import es.infouned.estudios.Estudio.TipoEstudio;

public class CriterioConsultaSQL {
	private TipoEstudio tipoEstudio;
	private String literal;
	private String nombre;
	private Stack<String> nomenclaturas;
	
	public CriterioConsultaSQL(String literal, String nombre, Stack<String> nomenclaturas) {
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