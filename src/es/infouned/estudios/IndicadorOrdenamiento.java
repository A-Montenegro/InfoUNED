package es.infouned.estudios;

import java.util.Stack;

public class IndicadorOrdenamiento {
	private String literal;
	private String nombre;
	private Stack<String> nomenclaturas;
	
	public IndicadorOrdenamiento(String literal, String nombre, Stack<String> nomenclaturas) {
		this.literal = literal;
		this.nombre = nombre;
		this.nomenclaturas = nomenclaturas;
	}

	public String getLiteral() {
		return literal;
	}
	
	public String getNombre() {
		return nombre;
	}

	public Stack<String> getNomenclaturas() {
		return nomenclaturas;
	}

}

