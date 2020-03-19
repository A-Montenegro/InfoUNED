package es.infouned.solicitudesInformacionBBDD;

import  static org.junit.Assert.assertFalse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import es.infouned.baseDeDatos.ConexionBaseDeDatos;
import es.infouned.baseDeDatos.ConexionMySQL;
import es.infouned.baseDeDatos.InstruccionSelect;
import es.infouned.estudios.Asignatura;
import es.infouned.estudios.Titulacion;
import es.infouned.principal.Configuracion;

import java.util.Random;
import java.util.Stack;

public class SolicitudInformacionAleatoria{
	private  ConexionBaseDeDatos conexionBaseDeDatos = new ConexionMySQL();
	
	public  SolicitudInformacion obtenerSolicitudInformacionAleatoria(){
		conexionBaseDeDatos.abrirConexion();
		SolicitudInformacion solicitudInformacion = null;
		Titulacion titulacion;
		Asignatura asignatura;
		String nivelEstudios = new String();
		String ordenamiento = new String();
		String parametroEstadisticoTitulacion = new String();
		String parametroEstadisticoAsignatura = new String();
		Stack <String> criteriosConsultaSQL = new Stack<String>();
		int numeroAleatorioEntre1y11 = obtenerNumeroAleatorio(1,11);
		switch(numeroAleatorioEntre1y11) {
			case 1:
				titulacion = obtenerTitulacionAleatoria();
				solicitudInformacion = new SolicitudPreciosTitulacion(titulacion);
				break;
			case 2:
				titulacion = obtenerTitulacionAleatoria();
				solicitudInformacion = new SolicitudMatriculadosTitulacion(titulacion);
				break;
			case 3:
				titulacion = obtenerTitulacionAleatoria();
				solicitudInformacion = new SolicitudValoracionEstudiantilTitulacion(titulacion);
				break;
			case 4:
				nivelEstudios = obtenerNivelEstudiosAleatorio();
				ordenamiento = obtenerOrdenamientoAleatorio();
				solicitudInformacion = new SolicitudValoracionEstudiantilTopTitulacion(nivelEstudios, ordenamiento);
				break;
			case 5:
				titulacion = obtenerTitulacionAleatoria();
				parametroEstadisticoTitulacion = obtenerParametroEstadisticoRendimientoTitulacionAleatorio();
				solicitudInformacion = new SolicitudEstadisticaRendimientoTitulacion(titulacion, parametroEstadisticoTitulacion);
				break;
			case 6:
				titulacion = obtenerTitulacionAleatoria();
				asignatura = obtenerAsignaturaAleatoriaDeTitulacion(titulacion.getIdTitulacion());
				solicitudInformacion = new SolicitudMatriculadosAsignatura(titulacion, asignatura);
				break;
			case 7:
				titulacion = obtenerTitulacionAleatoria();
				asignatura = obtenerAsignaturaAleatoriaDeTitulacion(titulacion.getIdTitulacion());
				solicitudInformacion = new SolicitudValoracionEstudiantilAsignatura(titulacion, asignatura);
				break;
			case 8:
				titulacion = obtenerTitulacionAleatoria();
				ordenamiento = obtenerOrdenamientoAleatorio();
				criteriosConsultaSQL.push("CURSO='4º'");
				solicitudInformacion = new SolicitudValoracionEstudiantilTopAsignatura(titulacion, ordenamiento, criteriosConsultaSQL);
				break;
			case 9:
				titulacion = obtenerTitulacionAleatoria();
				asignatura = obtenerAsignaturaAleatoriaDeTitulacion(titulacion.getIdTitulacion());
				parametroEstadisticoAsignatura = obtenerParametroEstadisticoRendimientoAsignaturaAleatorio();
				solicitudInformacion = new SolicitudEstadisticaRendimientoAsignatura(titulacion, asignatura, parametroEstadisticoAsignatura);
				break;
			case 10:
				nivelEstudios = obtenerNivelEstudiosAleatorio();
				parametroEstadisticoTitulacion = obtenerParametroEstadisticoRendimientoTitulacionAleatorio();
				ordenamiento = obtenerOrdenamientoAleatorio();
				solicitudInformacion = new SolicitudEstadisticaRendimientoTopTitulacion(nivelEstudios, parametroEstadisticoTitulacion, ordenamiento);
				break;
			case 11:
				titulacion = obtenerTitulacionAleatoria();
				parametroEstadisticoAsignatura = obtenerParametroEstadisticoRendimientoAsignaturaAleatorio();
				ordenamiento = obtenerOrdenamientoAleatorio();
				criteriosConsultaSQL.push("CURSO='4º'");
				solicitudInformacion = new SolicitudEstadisticaRendimientoTopAsignatura(titulacion, parametroEstadisticoAsignatura, ordenamiento, criteriosConsultaSQL);
				break;
		}
		assertFalse(solicitudInformacion == null);
		conexionBaseDeDatos.cerrarConexion();
		return solicitudInformacion;
	}
	
	private  int obtenerNumeroAleatorio(int minimo, int maximo) {
		assertFalse(minimo >= maximo);
		Random random = new Random();
		int numeroAleatorioDeSalida = random.nextInt((maximo - minimo) + 1) + minimo;
		return numeroAleatorioDeSalida;
	}
	
	private Titulacion obtenerTitulacionAleatoria() {
		int idTitulacion = obtenerIdTitulacionAleatoria();
		HashMap<String, String> sustitucionesSQL = new HashMap<String, String>();
		sustitucionesSQL.put("idTitulacionObjetivo", String.valueOf(idTitulacion));
		ResultSet resultSet = generarResultSetConsultaSQL("SolicitudDatosTitulacion", sustitucionesSQL);
		Titulacion titulacionAleatoria = null;
		try {
			if(resultSet.next()) {
				String nombreTitulacion = resultSet.getString(1);
				String nivelEstudiosTitulacion = resultSet.getString(2);
				titulacionAleatoria = new Titulacion(idTitulacion, nombreTitulacion, nivelEstudiosTitulacion);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertFalse(titulacionAleatoria == null);
		return titulacionAleatoria;
	}
	
	private int obtenerIdTitulacionAleatoria() {
		ResultSet resultSet = generarResultSetConsultaSQL("SolicitudTotalIdTitulacionConDatosRendimiento", new HashMap<String, String>());
		ArrayList<Integer> totalIdTitulaciones = new  ArrayList<Integer>(); 
		try {
			while (resultSet.next()){
				totalIdTitulaciones.add(resultSet.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int numeroAleatorioEntre1ytotalIdTitulaciones = obtenerNumeroAleatorio(1, totalIdTitulaciones.size());
		int IdTitulacionAleatoria = totalIdTitulaciones.get(numeroAleatorioEntre1ytotalIdTitulaciones-1);
		return IdTitulacionAleatoria;
	}
	
	private  String obtenerNivelEstudiosAleatorio() {
		int numeroAleatorioEntre0y1 = obtenerNumeroAleatorio(0,1); 
		if (numeroAleatorioEntre0y1 == 0) {
			return "máster universitario";
		}else {
			return "grado";
		}
	}
	
	private  String obtenerOrdenamientoAleatorio() {
		int numeroAleatorioEntre0y1 = obtenerNumeroAleatorio(0,1); 
		if (numeroAleatorioEntre0y1 == 0) {
			return "mayores";
		}else {
			return "menores";
		}
	}
	
	private  String obtenerParametroEstadisticoRendimientoTitulacionAleatorio() {	
		ResultSet resultSet = generarResultSetConsultaSQL("SolicitudNombreColumnasTablaRendimientoTitulaciones", new HashMap<String, String>());
		ArrayList<String> totalParametroEstadisticosRendimientoTitulacion = new  ArrayList<String>(); 
		try {
				for (int indiceColumna=5;indiceColumna<=resultSet.getMetaData().getColumnCount();indiceColumna++) {
					totalParametroEstadisticosRendimientoTitulacion.add(resultSet.getMetaData().getColumnName(indiceColumna));
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int numeroAleatorioEntre0ytotalParametroEstadisticosRendimientoTitulacion = obtenerNumeroAleatorio(0,totalParametroEstadisticosRendimientoTitulacion.size() - 1); 
		String ParametroEstadisticoRendimientoAsignaturaAleatorio = totalParametroEstadisticosRendimientoTitulacion.get(numeroAleatorioEntre0ytotalParametroEstadisticosRendimientoTitulacion);
		return ParametroEstadisticoRendimientoAsignaturaAleatorio;
	}
	
	private Asignatura obtenerAsignaturaAleatoriaDeTitulacion(int idTitulacion) {
		String idAsignatura = obtenerIdAsignaturaAleatoriaDeTitulacion(idTitulacion);
		HashMap<String, String> sustitucionesConsultaSQL = new HashMap<String, String>();
		sustitucionesConsultaSQL.put("idAsignaturaObjetivo", idAsignatura);
		ResultSet resultSet = generarResultSetConsultaSQL("SolicitudDatosAsignatura", sustitucionesConsultaSQL);
		Asignatura asignaturaAleatoria = null;
		try {
			if(resultSet.next()) {
				String nombreAsignatura = resultSet.getString(1);
				float creditosAsignatura = resultSet.getFloat(2);
				asignaturaAleatoria = new Asignatura(idAsignatura, nombreAsignatura, creditosAsignatura);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertFalse(asignaturaAleatoria == null);
		return asignaturaAleatoria;
	}
	
	private  String obtenerIdAsignaturaAleatoriaDeTitulacion(int idTitulacion) {
		HashMap<String, String> sustitucionesConsultaSQL = new HashMap<String, String>();
		sustitucionesConsultaSQL.put("idTitulacionObjetivo", String.valueOf(idTitulacion));
		ResultSet resultSet = generarResultSetConsultaSQL("SolicitudTotalIdAsignaturaDeTitulacion", sustitucionesConsultaSQL);
		ArrayList<String> totalIdAsignaturas = new  ArrayList<String>(); 
		try {
			while (resultSet.next()){
				totalIdAsignaturas.add(resultSet.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int numeroAleatorioEntre1ytotalIdAsignaturas = obtenerNumeroAleatorio(1, totalIdAsignaturas.size());
		String idAsignaturaAleatoria = totalIdAsignaturas.get(numeroAleatorioEntre1ytotalIdAsignaturas - 1);
		return String.valueOf(idAsignaturaAleatoria);
	}
	
	private  String obtenerParametroEstadisticoRendimientoAsignaturaAleatorio() {	
		ResultSet resultSet = generarResultSetConsultaSQL("SolicitudNombreColumnasTablaRendimientoAsignaturas", new HashMap<String,String>());
		ArrayList<String> totalParametroEstadisticosRendimientoAsignatura = new  ArrayList<String>(); 
		try {
				for (int indiceColumna=8;indiceColumna<=resultSet.getMetaData().getColumnCount();indiceColumna++) {
					totalParametroEstadisticosRendimientoAsignatura.add(resultSet.getMetaData().getColumnName(indiceColumna));
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int numeroAleatorioEntre0ytotalParametroEstadisticosRendimientoAsignatura = obtenerNumeroAleatorio(0,totalParametroEstadisticosRendimientoAsignatura.size() - 1); 
		String ParametroEstadisticoRendimientoAsignaturaAleatorio = totalParametroEstadisticosRendimientoAsignatura.get(numeroAleatorioEntre0ytotalParametroEstadisticosRendimientoAsignatura);
		return ParametroEstadisticoRendimientoAsignaturaAleatorio;
	}
	
	private  ResultSet generarResultSetConsultaSQL(String identificadorConsulta, HashMap<String, String> sustitucionesConsultaSQL) {
		InstruccionSelect instruccionSelect =  new InstruccionSelect(conexionBaseDeDatos);
		String cadenaTextoConsultaSQL = Configuracion.obtenerConsultaSQL(identificadorConsulta);
		for(Entry<String, String> entradaDelHashMap : sustitucionesConsultaSQL.entrySet()) {
    		cadenaTextoConsultaSQL = cadenaTextoConsultaSQL.replaceAll(entradaDelHashMap.getKey(), entradaDelHashMap.getValue());
    	}
		instruccionSelect.ejecutarConsulta(cadenaTextoConsultaSQL);
		ResultSet resultSet = instruccionSelect.getResultSet();
		return resultSet;
	}	
}
