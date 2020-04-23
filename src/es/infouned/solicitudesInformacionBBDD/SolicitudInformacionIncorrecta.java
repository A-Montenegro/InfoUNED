package es.infouned.solicitudesInformacionBBDD;


import es.infouned.estudios.Asignatura;
import es.infouned.estudios.Titulacion;

public class SolicitudInformacionIncorrecta extends SolicitudInformacion{
	private String tipoIncorreccion;
	private Titulacion titulacion;
	private Asignatura asignatura;
	
	public SolicitudInformacionIncorrecta(String tipoIncorreccion, Titulacion titulacion, Asignatura asignatura){
		super();
		this.tipoIncorreccion = tipoIncorreccion;
		this.titulacion = titulacion;
		this.asignatura = asignatura;
	}
	
	public String generarCadenaRespuesta(String saltoDeLinea){
		String cadenaRespuesta = new String("");
		switch(tipoIncorreccion) {
		case "asignaturaNoPerteneceATitulacion":
			cadenaRespuesta = "La asignatura " + asignatura.getNombre() + " no forma parte de la titulaci�n " + titulacion.getNombre() + "." + saltoDeLinea  +
			"Por favor, intente expresar su consulta de otra forma, prestando especial atenci�n al nombre de los estudios de los cuales desea informaci�n." + saltoDeLinea;
		}
		return cadenaRespuesta;
	}

}