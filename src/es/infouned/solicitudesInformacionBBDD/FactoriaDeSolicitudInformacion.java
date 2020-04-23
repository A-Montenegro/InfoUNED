package es.infouned.solicitudesInformacionBBDD;

import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import es.infouned.estudios.Asignatura;
import es.infouned.estudios.CriterioConsultaSQL;
import es.infouned.estudios.IndicadorOrdenamiento;
import es.infouned.estudios.NivelEstudios;
import es.infouned.estudios.ParametroEstadistico;
import es.infouned.estudios.Titulacion;

public class FactoriaDeSolicitudInformacion {

	
	@SuppressWarnings("unchecked")
	public static SolicitudInformacion obtenerSolicitudInformacion(TipoSolicitud tipoSolicitud, HashMap<NombreParametro,Object> parametros){
		SolicitudInformacion solicitudInformacion = null;
		Titulacion titulacion;
		Asignatura asignatura;
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
		case VALORACIONESTUDIANTILTITULACION:
			assert(parametros.size() == 1);
			assert(parametros.containsKey(NombreParametro.TITULACION));
			titulacion = (Titulacion) parametros.get(NombreParametro.TITULACION);
			solicitudInformacion = new SolicitudValoracionEstudiantilTitulacion(titulacion);
		break;
		case MATRICULADOSASIGNATURA:
			assert(parametros.size() == 2);
			assert(parametros.containsKey(NombreParametro.TITULACION) && parametros.containsKey(NombreParametro.ASIGNATURA));
			titulacion = (Titulacion) parametros.get(NombreParametro.TITULACION);
			asignatura = (Asignatura) parametros.get(NombreParametro.ASIGNATURA);
			solicitudInformacion = new SolicitudMatriculadosAsignatura(titulacion, asignatura);
		break;
		case VALORACIONESTUDIANTILASIGNATURA:
			assert(parametros.size() == 2);
			assert(parametros.containsKey(NombreParametro.TITULACION) && parametros.containsKey(NombreParametro.ASIGNATURA));
			titulacion = (Titulacion) parametros.get(NombreParametro.TITULACION);
			asignatura = (Asignatura) parametros.get(NombreParametro.ASIGNATURA);
			solicitudInformacion = new SolicitudValoracionEstudiantilAsignatura(titulacion, asignatura);
		break;
		case VALORACIONESTUDIANTILTOPASIGNATURA:
			assert(parametros.size() == 3);
			assert(parametros.containsKey(NombreParametro.TITULACION) && parametros.containsKey(NombreParametro.ORDENAMIENTO) && parametros.containsKey(NombreParametro.CRITERIOSCONSULTASQL));
			titulacion = (Titulacion) parametros.get(NombreParametro.TITULACION);
			ordenamiento = (IndicadorOrdenamiento) parametros.get(NombreParametro.ORDENAMIENTO);
			criteriosConsultaSQL = (Stack<CriterioConsultaSQL>) parametros.get(NombreParametro.CRITERIOSCONSULTASQL);
			solicitudInformacion = new SolicitudValoracionEstudiantilTopAsignatura(titulacion, ordenamiento.getLiteral(), criteriosConsultaSQL);
		break;
		case ESTADISTICARENDIMIENTOTITULACION:
			assert(parametros.size() == 2);
			assert(parametros.containsKey(NombreParametro.TITULACION) && parametros.containsKey(NombreParametro.PARAMETROESTADISTICOTITULACION));
			titulacion = (Titulacion) parametros.get(NombreParametro.TITULACION);
			parametroEstadisticoTitulacion = (ParametroEstadistico) parametros.get(NombreParametro.PARAMETROESTADISTICOTITULACION);
			solicitudInformacion = new SolicitudEstadisticaRendimientoTitulacion(titulacion, parametroEstadisticoTitulacion);
		break;
		case VALORACIONESTUDIANTILTOPTITULACION:
			assert(parametros.size() == 2);
			assert(parametros.containsKey(NombreParametro.NIVELESTUDIOS) && parametros.containsKey(NombreParametro.ORDENAMIENTO));
			nivelEstudios = (NivelEstudios) parametros.get(NombreParametro.NIVELESTUDIOS);
			ordenamiento = (IndicadorOrdenamiento) parametros.get(NombreParametro.ORDENAMIENTO);
			solicitudInformacion = new SolicitudValoracionEstudiantilTopTitulacion(nivelEstudios.getNombreNivelEstudios().toString(), ordenamiento.getLiteral());
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
		case ASIGNATURANOPERTENECIENTEATITULACION:
			assert(parametros.size() == 2);
			assert(parametros.containsKey(NombreParametro.TITULACION) && parametros.containsKey(NombreParametro.ASIGNATURA));
			titulacion = (Titulacion) parametros.get(NombreParametro.TITULACION);
			asignatura = (Asignatura) parametros.get(NombreParametro.ASIGNATURA);
			solicitudInformacion = new SolicitudInformacionIncorrecta("asignaturaNoPerteneceATitulacion", titulacion, asignatura);
		break;
		case CALLBACK:
			assert(parametros.size() == 2);
			assert(parametros.containsKey(NombreParametro.ORIGENCONVERSACION) && parametros.containsKey(NombreParametro.OPCIONES));
			String origenConversacion = (String) parametros.get(NombreParametro.ORIGENCONVERSACION);
			ArrayList<String> opciones = (ArrayList<String>) parametros.get(NombreParametro.OPCIONES);
			solicitudInformacion = new SolicitudInformacionCallBack(origenConversacion, opciones);
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
		VALORACIONESTUDIANTILTITULACION,
		MATRICULADOSASIGNATURA,
		VALORACIONESTUDIANTILASIGNATURA,
		VALORACIONESTUDIANTILTOPASIGNATURA,
		ESTADISTICARENDIMIENTOTITULACION,
		VALORACIONESTUDIANTILTOPTITULACION,
		ESTADISTICARENDIMIENTOTOPTITULACION,
		ESTADISTICARENDIMIENTOTOPASIGNATURA,
		ESTADISTICARENDIMIENTOASIGNATURA,
		ASIGNATURANOPERTENECIENTEATITULACION,
		CALLBACK
	}
	
	public enum NombreParametro {
		TITULACION,
		ASIGNATURA,
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
	}
}