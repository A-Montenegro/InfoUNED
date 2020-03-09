package xyz.infouned.solicitudesInformacionBBDD;

import static org.junit.Assert.assertFalse;

import java.util.Stack;

import xyz.infouned.estudios.Asignatura;
import xyz.infouned.estudios.Titulacion;



public class FactoriaDeSolicitudInformacion {

	public static SolicitudInformacion obtenerSolicitudInformacion(String tipo){
		SolicitudInformacion solicitudInformacion = null;
		switch(tipo) {
		case "aleatoria":
			SolicitudInformacionAleatoria solicitudInformacionAleatoria = new SolicitudInformacionAleatoria();
			solicitudInformacion = solicitudInformacionAleatoria.obtenerSolicitudInformacionAleatoria();
		break;
		}
		assertFalse(solicitudInformacion == null);
		return solicitudInformacion;
	}
	
	public static SolicitudInformacion obtenerSolicitudInformacion(String tipo, String idInformacionGenerica){
		SolicitudInformacion solicitudInformacion = null;
		switch(tipo) {
		case "informacionGenerica":
			solicitudInformacion = new SolicitudInformacionGenerica(idInformacionGenerica);
		break;
		}
		assertFalse(solicitudInformacion == null);
		return solicitudInformacion;
	}
	
	public static SolicitudInformacion obtenerSolicitudInformacion(String tipoSolicitud, Titulacion titulacion){
		SolicitudInformacion solicitudInformacion = null;
		switch(tipoSolicitud) {
		case "preciosTitulacion":
			solicitudInformacion = new SolicitudPreciosTitulacion(titulacion);
		break;
		case "matriculadosTitulacion":
			solicitudInformacion = new SolicitudMatriculadosTitulacion(titulacion);
		break;
		case "valoracionEstudiantilTitulacion":
			solicitudInformacion = new SolicitudValoracionEstudiantilTitulacion(titulacion);
		break;
		}
		assertFalse(solicitudInformacion == null);
		return solicitudInformacion;
	}
	
	public static SolicitudInformacion obtenerSolicitudInformacion(String tipoSolicitud, Titulacion titulacion, Asignatura asignatura){
		SolicitudInformacion solicitudInformacion = null;
		switch(tipoSolicitud) {
		case "matriculadosAsignatura":
			solicitudInformacion = new SolicitudMatriculadosAsignatura(titulacion, asignatura);
		break;
		case "valoracionEstudiantilAsignatura":
			solicitudInformacion = new SolicitudValoracionEstudiantilAsignatura(titulacion, asignatura);
		break;
		}
		assertFalse(solicitudInformacion == null);
		return solicitudInformacion;
	}
	
	public static SolicitudInformacion obtenerSolicitudInformacion(String tipoSolicitud, Titulacion titulacion, String ordenamiento, Stack<String> criteriosConsultaSQL){
		SolicitudInformacion solicitudInformacion = null;
		switch(tipoSolicitud) {
		case "valoracionEstudiantilTopAsignatura":
			solicitudInformacion = new SolicitudValoracionEstudiantilTopAsignatura(titulacion, ordenamiento, criteriosConsultaSQL);
		break;
		}
		assertFalse(solicitudInformacion == null);
		return solicitudInformacion;
	}
	
	public static SolicitudInformacion obtenerSolicitudInformacion(String tipoSolicitud, Titulacion titulacion, String parametroEstadisticoTitulacion){
		SolicitudInformacion solicitudInformacion = null;
		switch(tipoSolicitud) {
		case "estadisticaRendimientoTitulacion":
			solicitudInformacion = new SolicitudEstadisticaRendimientoTitulacion(titulacion, parametroEstadisticoTitulacion);
		break;
		}
		assertFalse(solicitudInformacion == null);
		return solicitudInformacion;
	}
	
	public static SolicitudInformacion obtenerSolicitudInformacion(String tipoSolicitud, String parametro1, String parametro2){
		SolicitudInformacion solicitudInformacion = null;
		switch(tipoSolicitud) {
		case "valoracionEstudiantilTopTitulacion":
			String nivelEstudios = parametro1;
			String ordenamiento = parametro2;
			solicitudInformacion = new SolicitudValoracionEstudiantilTopTitulacion(nivelEstudios, ordenamiento);
		break;

		}
		assertFalse(solicitudInformacion == null);
		return solicitudInformacion;
	}
	
	public static SolicitudInformacion obtenerSolicitudInformacion(String tipoSolicitud, String nivelEstudios, String parametroEstadisticoTitulacion, String ordenamiento){
		SolicitudInformacion solicitudInformacion = null;
		switch(tipoSolicitud) {
		case "estadisticaRendimientoTopTitulacion":
			solicitudInformacion = new SolicitudEstadisticaRendimientoTopTitulacion(nivelEstudios, parametroEstadisticoTitulacion, ordenamiento);
		break;
		}
		assertFalse(solicitudInformacion == null);
		return solicitudInformacion;
	}
	
	public static SolicitudInformacion obtenerSolicitudInformacion(String tipoSolicitud, Titulacion titulacion, String parametroEstadisticoAsignatura, String ordenamiento, Stack<String> criteriosConsultaSQL){
		SolicitudInformacion solicitudInformacion = null;
		switch(tipoSolicitud) {
		case "estadisticaRendimientoTopAsignatura":
			solicitudInformacion = new SolicitudEstadisticaRendimientoTopAsignatura(titulacion, parametroEstadisticoAsignatura, ordenamiento, criteriosConsultaSQL);
		break;
		}
		assertFalse(solicitudInformacion == null);
		return solicitudInformacion;
	}
	
	public static SolicitudInformacion obtenerSolicitudInformacion(String tipoSolicitud, Titulacion titulacion, Asignatura asignatura, String parametroEstadisticoAsignatura){
		SolicitudInformacion solicitudInformacion = null;
		switch(tipoSolicitud) {
		case "estadisticaRendimientoAsignatura":
			solicitudInformacion = new SolicitudEstadisticaRendimientoAsignatura(titulacion, asignatura, parametroEstadisticoAsignatura);
		break;
		}
		assertFalse(solicitudInformacion == null);
		return solicitudInformacion;
	}
}
