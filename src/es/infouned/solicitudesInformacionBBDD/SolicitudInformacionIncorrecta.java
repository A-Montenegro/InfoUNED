package es.infouned.solicitudesInformacionBBDD;


import es.infouned.estudios.Asignatura;
import es.infouned.estudios.AsignaturaBorrosa;
import es.infouned.estudios.Titulacion;

/**
 * Clase que se encarga de generar las cadenas de texto finales que componen las respuestas del chatbot cuando la consulta del usuario se considera que est� mal planteada.
 * @author Alberto Mart�nez Montenegro
 * 
 */
public class SolicitudInformacionIncorrecta extends SolicitudInformacion{
	private String tipoIncorreccion;
	private Titulacion titulacion;
	private Asignatura asignatura;
	@SuppressWarnings("unused")
	private AsignaturaBorrosa asignaturaBorrosa;
	private final int prioridad = 0;

	public SolicitudInformacionIncorrecta(String tipoIncorreccion, Titulacion titulacion, Asignatura asignatura){
		super();
		this.tipoIncorreccion = tipoIncorreccion;
		this.titulacion = titulacion;
		this.asignatura = asignatura;
		this.asignaturaBorrosa = null;
	}

	public SolicitudInformacionIncorrecta(String tipoIncorreccion, Titulacion titulacion, AsignaturaBorrosa asignaturaBorrosa){
		super();
		this.tipoIncorreccion = tipoIncorreccion;
		this.titulacion = titulacion;
		this.asignatura = null;
		this.asignaturaBorrosa = asignaturaBorrosa;
	}
	
	public String generarCadenaRespuesta(String saltoDeLinea){
		String cadenaRespuesta = new String("");
		switch(tipoIncorreccion) {
		case "asignaturaNoPerteneceATitulacion":
			if(asignatura != null) {
				cadenaRespuesta = "La asignatura " + asignatura.getNombre() + " no forma parte de la titulaci�n " + titulacion.getNombre() + "." + saltoDeLinea  +
				"Por favor, intenta expresar tu consulta de otra forma, prestando especial atenci�n al nombre de los estudios de los cuales deseas informaci�n." + saltoDeLinea;
			}
			else {
				cadenaRespuesta = "Seg�n mis datos, la asignatura que has nombrado en tu mensaje, no pertenece a la titulaci�n " + titulacion.getNombre() + "." + saltoDeLinea  +
				"Por favor, intenta expresar tu consulta de otra forma, prestando especial atenci�n al nombre de los estudios de los cuales deseas informaci�n." + saltoDeLinea;
			}
			break;
		case "asignaturaBorrosaNoPerteneceATitulacion":
			cadenaRespuesta = "Seg�n mis datos, la asignatura que has nombrado en tu mensaje, no pertenece a la titulaci�n " + titulacion.getNombre() + "." + saltoDeLinea  +
			"Por favor, intenta expresar tu consulta de otra forma, prestando especial atenci�n al nombre de los estudios de los cuales deseas informaci�n." + saltoDeLinea;
			break;
		}
		
		return cadenaRespuesta;
	}
	
	public int getPrioridad() {
		return prioridad;
	}


}