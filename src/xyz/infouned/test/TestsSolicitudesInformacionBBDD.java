package xyz.infouned.test;

import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Stack;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import xyz.infouned.baseDeDatos.ConexionBaseDeDatos;
import xyz.infouned.baseDeDatos.ConexionMySQL;
import xyz.infouned.baseDeDatos.InstruccionSelect;
import xyz.infouned.configuracion.PropiedadesConfiguracion;
import xyz.infouned.configuracion.PropiedadesListaDeConsultasSQL;
import xyz.infouned.conversacion.Conversacion;
import xyz.infouned.estudios.Asignatura;
import xyz.infouned.estudios.Titulacion;
import xyz.infouned.solicitudesInformacionBBDD.FactoriaDeSolicitudInformacion;
import xyz.infouned.solicitudesInformacionBBDD.SolicitudInformacion;


public class TestsSolicitudesInformacionBBDD {
	
	@BeforeAll
	private static void inicializarPropiedades() {
		String rutaFicheroConfiguracion = "/config.properties";
		PropiedadesConfiguracion.establecerPropiedadesConfiguracionAPartirDeFichero(rutaFicheroConfiguracion);
		String rutaFicheroConsultasSQL = "/consultasSQL.properties";
	    PropiedadesListaDeConsultasSQL.establecerPropiedadesDeListaDeConsultasSQLAPartirDeFichero(rutaFicheroConsultasSQL);
	}
	
	@Test
	public void testConsultaSQL() {
		ConexionBaseDeDatos conexionBaseDeDatos = new ConexionMySQL();
		conexionBaseDeDatos.abrirConexion();
		InstruccionSelect instruccionSelect = new InstruccionSelect(conexionBaseDeDatos);
		HashMap<String, String> sustituciones = new HashMap<String, String>();
		sustituciones.put("idTitulacionObjetivo", "6103");
		String cadenaTextoConsultaSQL = PropiedadesListaDeConsultasSQL.obtenerConsultaSQL("SolicitudPreciosTitulacionPorIdTitulacion");
    	for(Entry<String, String> entradaDelHashMap : sustituciones.entrySet()) {
    		cadenaTextoConsultaSQL = cadenaTextoConsultaSQL.replaceAll(entradaDelHashMap.getKey(), entradaDelHashMap.getValue());
    	}
		instruccionSelect.ejecutarConsulta(cadenaTextoConsultaSQL);
		ResultSet resultSet = instruccionSelect.getResultSet();
		try {
			while(resultSet.next()) {
				Float precioMatricula1 = resultSet.getFloat(1);
				Float precioMatricula2 = resultSet.getFloat(2);
				Float precioMatricula3 = resultSet.getFloat(3);
				Float precioMatricula4 = resultSet.getFloat(4);
				assertTrue(precioMatricula1 == 20.48f);
				assertTrue(precioMatricula2 == 30.6f);
				assertTrue(precioMatricula3 == 67.32f);
				assertTrue(precioMatricula4 == 92.82f);
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conexionBaseDeDatos.cerrarConexion();
	}
	
	/**
	@Test
	public void SalidaPorConsolaSolicitudInformacionAleatoria(){
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("aleatoria");
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		System.out.println("\n\n\n" + respuesta);
	} 
	**/
	
	@Test
	public void testMensajeConversacionSobrePrecios(){
		Titulacion titulacion = new Titulacion(6103,"GRADO EN QU�MICA","GRADO");
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("preciosTitulacion", titulacion);
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("El precio de las asignaturas de la titulaci�n GRADO EN QU�MICA es de:\n" + 
				"-20,48� por cada cr�dito ECTS la primera vez que el alumno se matricula de la asignatura.\n" + 
				"-30,60� por cada cr�dito ECTS la segunda vez que el alumno se matricula de la asignatura.\n" + 
				"-67,32� por cada cr�dito ECTS la tercera vez que el alumno se matricula de la asignatura.\n" + 
				"-92,82� por cada cr�dito ECTS la cuarta y las sucesivas veces que el alumno se matricule de la asignatura.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreMatriculadosTitulacion(){
		Titulacion titulacion = new Titulacion(7102,"GRADO EN INGENIER�A EN TECNOLOG�AS DE LA INFORMACI�N","GRADO");
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("matriculadosTitulacion", titulacion);
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("El n�mero de matriculados en la titulaci�n GRADO EN INGENIER�A EN TECNOLOG�AS DE LA INFORMACI�N en los �ltimos a�os son los siguientes:\n" + 
				"-Curso 2017 - 2018: 970 matriculados.\n" + 
				"-Curso 2016 - 2017: 1073 matriculados.\n" + 
				"-Curso 2015 - 2016: 1169 matriculados.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreMatriculadosAsignatura(){
		Titulacion titulacion = new Titulacion(7101,"GRADO EN INGENIER�A INFORM�TICA","GRADO");
		Asignatura asignatura = new Asignatura("7101102-","FUNDAMENTOS MATEM�TICOS DE LA INFORM�TICA",6);
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("matriculadosAsignatura", titulacion, asignatura);
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("El n�mero de matriculados en la asignatura FUNDAMENTOS MATEM�TICOS DE LA INFORM�TICA de la titulaci�n GRADO EN INGENIER�A INFORM�TICA en los �ltimos a�os son los siguientes:\n" + 
				"-Curso 2018 - 2019: 991 matriculados.\n" + 
				"-Curso 2017 - 2018: 758 matriculados.\n" + 
				"-Curso 2016 - 2017: 766 matriculados.\n" + 
				"-Curso 2015 - 2016: 847 matriculados.\n" + 
				"-Curso 2014 - 2015: 829 matriculados.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreValoracionEstudiantilTitulacion(){
		Titulacion titulacion = new Titulacion(7101,"GRADO EN INGENIER�A INFORM�TICA","GRADO");
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("valoracionEstudiantilTitulacion", titulacion);
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Las calificaciones que ha obtenido la titulaci�n GRADO EN INGENIER�A INFORM�TICA en los �ltimos a�os seg�n los cuestionarios de satisfacci�n de los estudiantes son las siguientes:\n" + 
				"-Curso 2017 - 2018: 67,11 puntos sobre 100.\n" + 
				"-Curso 2016 - 2017: 64,82 puntos sobre 100.\n" + 
				"-Curso 2015 - 2016: 73,98 puntos sobre 100.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreTopValoracionesEstudiantiles(){
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("valoracionEstudiantilTopTitulacion", "grado", "menores");
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Durante el �ltimo curso acad�mico registrado (2017 - 2018), estas fueron los estudios de GRADO que menores calificaciones obtuvieron seg�n las encuestas de los estudiantes:\n" + 
				"-La titulaci�n GRADO EN INGENIER�A EN TECNOLOG�AS INDUSTRIALES obtuvo una calificaci�n de 56,25 sobre 100.\n" + 
				"-La titulaci�n GRADO EN INGENIER�A MEC�NICA obtuvo una calificaci�n de 62,33 sobre 100.\n" + 
				"-La titulaci�n GRADO EN CRIMINOLOG�A obtuvo una calificaci�n de 62,43 sobre 100.\n" + 
				"-La titulaci�n GRADO EN F�SICA obtuvo una calificaci�n de 64,31 sobre 100.\n" + 
				"-La titulaci�n GRADO EN CIENCIA POL�TICA Y DE LA ADMINISTRACI�N obtuvo una calificaci�n de 64,31 sobre 100.\n" + 
				"-La titulaci�n GRADO EN ING. EN  ELECTR�NICA INDUSTRIAL Y AUTOM�TICA obtuvo una calificaci�n de 64,75 sobre 100.\n" + 
				"-La titulaci�n GRADO EN ADMINISTRACI�N Y DIRECCI�N DE EMPRESAS obtuvo una calificaci�n de 65,34 sobre 100.\n" + 
				"-La titulaci�n GRADO EN SOCIOLOG�A obtuvo una calificaci�n de 66,02 sobre 100.\n" + 
				"-La titulaci�n GRADO EN CIENCIAS AMBIENTALES obtuvo una calificaci�n de 66,20 sobre 100.\n" + 
				"-La titulaci�n GRADO EN MATEM�TICAS obtuvo una calificaci�n de 66,30 sobre 100.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreEstadisticaRendimientoTitulacion(){
		Titulacion titulacion = new Titulacion(7101,"GRADO EN INGENIER�A INFORM�TICA","GRADO");
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("estadisticaRendimientoTitulacion", titulacion, "TASA_EXITOS");
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Las estad�sticas de TASA_EXITOS para la titulaci�n GRADO EN INGENIER�A INFORM�TICA en los �ltimos a�os son las siguientes:\n" + 
				"-Curso 2017 - 2018: 80,17.\n" + 
				"-Curso 2016 - 2017: 78,16.\n" + 
				"-Curso 2015 - 2016: 78,47.\n"));
	}

	@Test
	public void testMensajeConversacionSobreTopEstadisticaRendimientoTitulacion(){
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("estadisticaRendimientoTopTitulacion", "grado", "TASA_EXITOS", "mayores");
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Durante el �ltimo curso acad�mico registrado (2017 - 2018), estas fueron los estudios de GRADO que obtuvieron mayores resultados en cuanto a TASA_EXITOS :\n" + 
				"-La titulaci�n GRADO EN FILOSOF�A obtuvo unos resultados de 92,00.\n" + 
				"-La titulaci�n GRADO EN HISTORIA DEL ARTE obtuvo unos resultados de 91,88.\n" + 
				"-La titulaci�n GRADO EN ANTROPOLOG�A SOCIAL Y CULTURAL obtuvo unos resultados de 90,15.\n" + 
				"-La titulaci�n GRADO EN GEOGRAF�A E HISTORIA obtuvo unos resultados de 90,15.\n" + 
				"-La titulaci�n GRADO EN ESTUDIOS INGLESES: LENGUA, LITERATURA Y CULTURA obtuvo unos resultados de 90,12.\n" + 
				"-La titulaci�n GRADO EN SOCIOLOG�A obtuvo unos resultados de 89,71.\n" + 
				"-La titulaci�n GRADO EN CC. JUR�DICAS DE LAS ADMINISTRACIONES P�BLICAS obtuvo unos resultados de 89,65.\n" + 
				"-La titulaci�n GRADO EN PEDAGOG�A obtuvo unos resultados de 89,62.\n" + 
				"-La titulaci�n GRADO EN EDUCACI�N SOCIAL obtuvo unos resultados de 89,45.\n" + 
				"-La titulaci�n GRADO EN TRABAJO SOCIAL obtuvo unos resultados de 88,29.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreEstadisticaRendimientoAsignatura(){
		Titulacion titulacion = new Titulacion(6201,"GRADO EN PSICOLOG�A","GRADO");
		Asignatura asignatura = new Asignatura("62011014","FUNDAMENTOS DE PSICOBIOLOG�A",6);
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("estadisticaRendimientoAsignatura", titulacion, asignatura, "PORCENTAJE_TASA_SOBRESALIENTES");
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Las estad�sticas de PORCENTAJE_TASA_SOBRESALIENTES para la asignatura FUNDAMENTOS DE PSICOBIOLOG�A de la titulaci�n GRADO EN PSICOLOG�A en los �ltimos a�os son las siguientes:\n" + 
				"-Curso 2017-2018: 0,69.\n" + 
				"-Curso 2016-2017: 0,74.\n" + 
				"-Curso 2015-2016: 0,85.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreTopEstadisticaRendimientoAsignatura(){
		Titulacion titulacion = new Titulacion(7101,"GRADO EN INGENIER�A INFORM�TICA","GRADO");
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("estadisticaRendimientoTopAsignatura", titulacion, "PORCENTAJE_TASA_EXITO", "menores", new Stack<String>());
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Durante el �ltimo curso acad�mico registrado (2017-2018), estas fueron las asignaturas de la titulaci�n GRADO EN INGENIER�A INFORM�TICA que obtuvieron menores resultados en las estad�sticas de PORCENTAJE_TASA_EXITO:\n" + 
				"-La asignatura FUNDAMENTOS F�SICOS DE LA INFORM�TICA obtuvo unos resultados de 38,36.\n" + 
				"-La asignatura DISE�O DEL SOFTWARE obtuvo unos resultados de 53,24.\n" + 
				"-La asignatura FUNDAMENTOS MATEM�TICOS DE LA INFORM�TICA obtuvo unos resultados de 55,95.\n" + 
				"-La asignatura FUNDAMENTOS DE SISTEMAS DIGITALES obtuvo unos resultados de 59,39.\n" + 
				"-La asignatura INGENIER�A DE COMPUTADORES I obtuvo unos resultados de 66,67.\n" + 
				"-La asignatura BASES DE DATOS obtuvo unos resultados de 71,43.\n" + 
				"-La asignatura SISTEMAS DISTRIBUIDOS obtuvo unos resultados de 74,73.\n" + 
				"-La asignatura FUNDAMENTOS DE PROGRAMACI�N obtuvo unos resultados de 75,23.\n" + 
				"-La asignatura TEOR�A DE LOS LENGUAJES DE PROGRAMACI�N obtuvo unos resultados de 77,27.\n" + 
				"-La asignatura FUNDAMENTOS DE INTELIGENCIA ARTIFICIAL obtuvo unos resultados de 77,30.\n"));
	}
	
	@Test
	public void testMensajesGenericosFacebook(){
		Conversacion conversacion = new Conversacion("1234","Facebook");
		System.out.println(conversacion.obtenerMensajeGenericoFacebook("Hola"));
		System.out.println(conversacion.obtenerMensajeGenericoFacebook("Quiero saber cu�ntos alumnos est�n matriculados de la asignatura Fundamentos matem�ticos de la inform�tica en el grado en ingenier�a inform�tica."));
		System.out.println(conversacion.obtenerMensajeGenericoFacebook("Ok, gracias."));
		System.out.println(conversacion.obtenerMensajeGenericoFacebook("�cual es la asignatura m�s dif�cil del grado en derecho?"));
		
	}
	
}
