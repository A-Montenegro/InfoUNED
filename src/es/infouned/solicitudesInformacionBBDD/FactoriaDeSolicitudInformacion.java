package es.infouned.solicitudesInformacionBBDD;

import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import es.infouned.conversacion.CallBack.TipoCallBack;
import es.infouned.conversacion.Conversacion.OrigenConversacion;
import es.infouned.estudios.Asignatura;
import es.infouned.estudios.AsignaturaBorrosa;
import es.infouned.estudios.CriterioConsultaSQL;
import es.infouned.estudios.IndicadorOrdenamiento;
import es.infouned.estudios.NivelEstudios;
import es.infouned.estudios.ParametroEstadistico;
import es.infouned.estudios.Titulacion;

/**
 * Clase que se encarga de la creación de instancias de tipo SolicitudInformacion mediante métodos estáticos.
 * @author Alberto Martínez Montenegro
 * 
 */
public class FactoriaDeSolicitudInformacion {

	
	@SuppressWarnings("unchecked")
	public static SolicitudInformacion obtenerSolicitudInformacion(TipoSolicitud tipoSolicitud, HashMap<NombreParametro,Object> parametros){
		SolicitudInformacion solicitudInformacion = null;
		Titulacion titulacion;
		Asignatura asignatura;
		AsignaturaBorrosa asignaturaBorrosa;
		IndicadorOrdenamiento ordenamiento;
		Stack<CriterioConsultaSQL> criteriosConsultaSQL;
		ParametroEstadistico parametroEstadisticoTitulacion;
		ParametroEstadistico parametroEstadisticoAsignatura;
		NivelEstudios nivelEstudios;
		switch(tipoSolicitud) {
		case ALEATORIA:
			assert(parametros.isEmpty());
			SolicitudInformacionAleatoria solicitudInformacionAleatoria = new SolicitudInformacionAleatoria();
			solicitudInformacion = solicitudInformacionAleatoria.obtenerSolicitudInformacionAleatoria();
		break;
		case INFORMACIONGENERICA:
			assert(parametros.size() == 1);
			assert(parametros.containsKey(NombreParametro.IDINFORMACIONGENERICA));
			String idInformacionGenerica = (String) parametros.get(NombreParametro.IDINFORMACIONGENERICA);
			solicitudInformacion = new SolicitudInformacionGenerica(idInformacionGenerica);
		break;
		case PRECIOSTITULACION:
			assert(parametros.size() == 1);
			assert(parametros.containsKey(NombreParametro.TITULACION));
			titulacion = (Titulacion) parametros.get(NombreParametro.TITULACION);
			solicitudInformacion = new SolicitudPreciosTitulacion(titulacion);
		break;
		case MATRICULADOSTITULACION:
			assert(parametros.size() == 1);
			assert(parametros.containsKey(NombreParametro.TITULACION));
			titulacion = (Titulacion) parametros.get(NombreParametro.TITULACION);
			solicitudInformacion = new SolicitudMatriculadosTitulacion(titulacion);
		break;
		case MATRICULADOSASIGNATURA:
			assert(parametros.size() == 2);
			assert(parametros.containsKey(NombreParametro.TITULACION) && parametros.containsKey(NombreParametro.ASIGNATURA));
			titulacion = (Titulacion) parametros.get(NombreParametro.TITULACION);
			asignatura = (Asignatura) parametros.get(NombreParametro.ASIGNATURA);
			solicitudInformacion = new SolicitudMatriculadosAsignatura(titulacion, asignatura);
		break;
		case ESTADISTICARENDIMIENTOTITULACION:
			assert(parametros.size() == 2);
			assert(parametros.containsKey(NombreParametro.TITULACION) && parametros.containsKey(NombreParametro.PARAMETROESTADISTICOTITULACION));
			titulacion = (Titulacion) parametros.get(NombreParametro.TITULACION);
			parametroEstadisticoTitulacion = (ParametroEstadistico) parametros.get(NombreParametro.PARAMETROESTADISTICOTITULACION);
			solicitudInformacion = new SolicitudEstadisticaRendimientoTitulacion(titulacion, parametroEstadisticoTitulacion);
		break;
		case ESTADISTICARENDIMIENTOTOPTITULACION:
			assert(parametros.size() == 3);
			assert(parametros.containsKey(NombreParametro.NIVELESTUDIOS) && parametros.containsKey(NombreParametro.PARAMETROESTADISTICOTITULACION) && parametros.containsKey(NombreParametro.ORDENAMIENTO));
			nivelEstudios = (NivelEstudios) parametros.get(NombreParametro.NIVELESTUDIOS);
			parametroEstadisticoTitulacion = (ParametroEstadistico) parametros.get(NombreParametro.PARAMETROESTADISTICOTITULACION);
			ordenamiento = (IndicadorOrdenamiento) parametros.get(NombreParametro.ORDENAMIENTO);
			solicitudInformacion = new SolicitudEstadisticaRendimientoTopTitulacion(nivelEstudios.getNombreNivelEstudios().toString(), parametroEstadisticoTitulacion, ordenamiento.getLiteral());
		break;
		case ESTADISTICARENDIMIENTOTOPASIGNATURA:
			assert(parametros.size() == 4);
			assert(parametros.containsKey(NombreParametro.TITULACION) && parametros.containsKey(NombreParametro.PARAMETROESTADISTICOASIGNATURA) && parametros.containsKey(NombreParametro.ORDENAMIENTO) && parametros.containsKey(NombreParametro.CRITERIOSCONSULTASQL));
			titulacion = (Titulacion) parametros.get(NombreParametro.TITULACION);
			parametroEstadisticoAsignatura = (ParametroEstadistico) parametros.get(NombreParametro.PARAMETROESTADISTICOASIGNATURA);
			IndicadorOrdenamiento indicadorOrdenamiento = (IndicadorOrdenamiento) parametros.get(NombreParametro.ORDENAMIENTO);
			criteriosConsultaSQL = (Stack<CriterioConsultaSQL>) parametros.get(NombreParametro.CRITERIOSCONSULTASQL);
			solicitudInformacion = new SolicitudEstadisticaRendimientoTopAsignatura(titulacion, parametroEstadisticoAsignatura, indicadorOrdenamiento.getLiteral(), criteriosConsultaSQL);
		break;
		case ESTADISTICARENDIMIENTOASIGNATURA:
			assert(parametros.size() == 3);
			assert(parametros.containsKey(NombreParametro.TITULACION) && parametros.containsKey(NombreParametro.ASIGNATURA) && parametros.containsKey(NombreParametro.PARAMETROESTADISTICOASIGNATURA));
			titulacion = (Titulacion) parametros.get(NombreParametro.TITULACION);
			asignatura = (Asignatura) parametros.get(NombreParametro.ASIGNATURA);
			parametroEstadisticoAsignatura = (ParametroEstadistico) parametros.get(NombreParametro.PARAMETROESTADISTICOASIGNATURA);
			solicitudInformacion = new SolicitudEstadisticaRendimientoAsignatura(titulacion, asignatura, parametroEstadisticoAsignatura);
		break;
		case GUIAASIGNATURA:
			assert(parametros.size() == 1 || parametros.size() == 2);
			assert(parametros.containsKey(NombreParametro.TITULACION));
			titulacion = (Titulacion) parametros.get(NombreParametro.TITULACION);
			asignatura = (Asignatura) parametros.get(NombreParametro.ASIGNATURA);
			solicitudInformacion = new SolicitudGuia(titulacion, asignatura);
		break;
		case ASIGNATURANOPERTENECIENTEATITULACION:
			assert(parametros.size() == 2);
			assert(parametros.containsKey(NombreParametro.TITULACION) && (parametros.containsKey(NombreParametro.ASIGNATURA) || parametros.containsKey(NombreParametro.ASIGNATURABORROSA)));
			titulacion = (Titulacion) parametros.get(NombreParametro.TITULACION);
			if(parametros.containsKey(NombreParametro.ASIGNATURA)) {
				asignatura = (Asignatura) parametros.get(NombreParametro.ASIGNATURA);
				solicitudInformacion = new SolicitudInformacionIncorrecta("asignaturaNoPerteneceATitulacion", titulacion, asignatura);
			}
			else {
				asignaturaBorrosa = (AsignaturaBorrosa) parametros.get(NombreParametro.ASIGNATURABORROSA);
				solicitudInformacion = new SolicitudInformacionIncorrecta("asignaturaBorrosaNoPerteneceATitulacion", titulacion, asignaturaBorrosa);
			}
		break;
		case CALLBACK:
			assert(parametros.size() == 4);
			assert(parametros.containsKey(NombreParametro.ORIGENCONVERSACION) && parametros.containsKey(NombreParametro.OPCIONES) && parametros.containsKey(NombreParametro.TIPOCALLBACK) && parametros.containsKey(NombreParametro.PARAMETROSCALLBACK));
			TipoCallBack tipoCallBack = (TipoCallBack) parametros.get(NombreParametro.TIPOCALLBACK);
			HashMap<NombreParametro,Object> parametrosCallBack = (HashMap<NombreParametro,Object>) parametros.get(NombreParametro.PARAMETROSCALLBACK);
			OrigenConversacion origenConversacion = (OrigenConversacion) parametros.get(NombreParametro.ORIGENCONVERSACION);
			ArrayList<String> opciones = (ArrayList<String>) parametros.get(NombreParametro.OPCIONES);
			solicitudInformacion = new SolicitudInformacionCallBack(tipoCallBack, parametrosCallBack, origenConversacion, opciones);
		break;
		default:
			throw new IllegalArgumentException("La tipo de solicitud de información no encaja con ninguna de las opciones codificadas.");
		}
		assertFalse(solicitudInformacion == null);
		return solicitudInformacion;
	}
	
	public enum TipoSolicitud {
		ALEATORIA,
		INFORMACIONGENERICA,
		PRECIOSTITULACION,
		MATRICULADOSTITULACION,
		MATRICULADOSASIGNATURA,
		GUIAASIGNATURA,
		ESTADISTICARENDIMIENTOTITULACION,
		ESTADISTICARENDIMIENTOTOPTITULACION,
		ESTADISTICARENDIMIENTOTOPASIGNATURA,
		ESTADISTICARENDIMIENTOASIGNATURA,
		ASIGNATURANOPERTENECIENTEATITULACION,
		CALLBACK
	}
	
	public enum NombreParametro {
		TITULACION,
		ASIGNATURA,
		ASIGNATURABORROSA,
		ORIGENCONVERSACION,
		OPCIONES,
		VALORACIONESTUDIANTILTITULACION,
		PARAMETROESTADISTICOASIGNATURA,
		PARAMETROESTADISTICOTITULACION,
		MATRICULADOSASIGNATURA,
		IDINFORMACIONGENERICA,	
		ORDENAMIENTO,
		CRITERIOSCONSULTASQL,
		NIVELESTUDIOS,
		TIPOCALLBACK,
		PARAMETROSCALLBACK
	}
}