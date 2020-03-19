package es.infouned.test;

import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Stack;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import es.infouned.baseDeDatos.ConexionBaseDeDatos;
import es.infouned.baseDeDatos.ConexionMySQL;
import es.infouned.baseDeDatos.InstruccionSelect;
import es.infouned.estudios.Asignatura;
import es.infouned.estudios.Titulacion;
import es.infouned.principal.Configuracion;
import es.infouned.solicitudesInformacionBBDD.FactoriaDeSolicitudInformacion;
import es.infouned.solicitudesInformacionBBDD.SolicitudInformacion;


public class TestsSolicitudesInformacionBBDD {
	
	@BeforeAll
	private static void inicializarPropiedades() {
		String rutaFicheroConfiguracion = "/config.properties";
		Configuracion.establecerPropiedadesConfiguracionAPartirDeFichero(rutaFicheroConfiguracion);
	}
	
	@Test
	public void testConsultaSQL() {
		ConexionBaseDeDatos conexionBaseDeDatos = new ConexionMySQL();
		conexionBaseDeDatos.abrirConexion();
		InstruccionSelect instruccionSelect = new InstruccionSelect(conexionBaseDeDatos);
		HashMap<String, String> sustituciones = new HashMap<String, String>();
		sustituciones.put("idTitulacionObjetivo", "6103");
		String cadenaTextoConsultaSQL = Configuracion.obtenerConsultaSQL("SolicitudPreciosTitulacionPorIdTitulacion");
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
				"-Curso 2019 - 2020: 958 matriculados.\n" + 
				"-Curso 2018 - 2019: 1009 matriculados.\n" + 
				"-Curso 2017 - 2018: 971 matriculados.\n" + 
				"-Curso 2016 - 2017: 1073 matriculados.\n" + 
				"-Curso 2015 - 2016: 1165 matriculados.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreMatriculadosAsignatura(){
		Titulacion titulacion = new Titulacion(7101,"GRADO EN INGENIER�A INFORM�TICA","GRADO");
		Asignatura asignatura = new Asignatura("7101102-","FUNDAMENTOS MATEM�TICOS DE LA INFORM�TICA",6);
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("matriculadosAsignatura", titulacion, asignatura);
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("El n�mero de matriculados en la asignatura FUNDAMENTOS MATEM�TICOS DE LA INFORM�TICA de la titulaci�n GRADO EN INGENIER�A INFORM�TICA en los �ltimos a�os son los siguientes:\n" + 
				"-Curso 2019 - 2020: 872 matriculados.\n" + 
				"-Curso 2018 - 2019: 799 matriculados.\n" + 
				"-Curso 2017 - 2018: 754 matriculados.\n" + 
				"-Curso 2016 - 2017: 765 matriculados.\n" + 
				"-Curso 2015 - 2016: 846 matriculados.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreValoracionEstudiantilTitulacion(){
		Titulacion titulacion = new Titulacion(7101,"GRADO EN INGENIER�A INFORM�TICA","GRADO");
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("valoracionEstudiantilTitulacion", titulacion);
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Las calificaciones que ha obtenido la titulaci�n GRADO EN INGENIER�A INFORM�TICA en los �ltimos a�os seg�n los cuestionarios de satisfacci�n de los estudiantes son las siguientes:\n" + 
				"-Curso 2018 - 2019: 66,53 puntos sobre 100.\n" + 
				"-Curso 2017 - 2018: 67,11 puntos sobre 100.\n" + 
				"-Curso 2016 - 2017: 64,82 puntos sobre 100.\n" + 
				"-Curso 2015 - 2016: 73,98 puntos sobre 100.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreTopValoracionesEstudiantiles(){
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("valoracionEstudiantilTopTitulacion", "grado", "menores");
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Durante el �ltimo curso acad�mico registrado (2018 - 2019), estas fueron los estudios de GRADO que menores calificaciones obtuvieron seg�n las encuestas de los estudiantes:\n" + 
				"-La titulaci�n GRADO EN INGENIER�A EN TECNOLOG�AS INDUSTRIALES obtuvo una calificaci�n de 58,45 sobre 100.\n" + 
				"-La titulaci�n GRADO EN INGENIER�A MEC�NICA obtuvo una calificaci�n de 59,53 sobre 100.\n" + 
				"-La titulaci�n GRADO EN ING. EN  ELECTR�NICA INDUSTRIAL Y AUTOM�TICA obtuvo una calificaci�n de 59,94 sobre 100.\n" + 
				"-La titulaci�n GRADO EN INGENIER�A EL�CTRICA obtuvo una calificaci�n de 60,74 sobre 100.\n" + 
				"-La titulaci�n GRADO EN F�SICA obtuvo una calificaci�n de 64,51 sobre 100.\n" + 
				"-La titulaci�n GRADO EN MATEM�TICAS obtuvo una calificaci�n de 64,92 sobre 100.\n" + 
				"-La titulaci�n GRADO EN INGENIER�A EN TECNOLOG�AS DE LA INFORMACI�N obtuvo una calificaci�n de 65,15 sobre 100.\n" + 
				"-La titulaci�n GRADO EN QU�MICA obtuvo una calificaci�n de 65,59 sobre 100.\n" + 
				"-La titulaci�n GRADO EN CIENCIA POL�TICA Y DE LA ADMINISTRACI�N obtuvo una calificaci�n de 65,62 sobre 100.\n" + 
				"-La titulaci�n GRADO EN TRABAJO SOCIAL obtuvo una calificaci�n de 65,89 sobre 100.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreEstadisticaRendimientoTitulacion(){
		Titulacion titulacion = new Titulacion(7101,"GRADO EN INGENIER�A INFORM�TICA","GRADO");
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("estadisticaRendimientoTitulacion", titulacion, "TASA_EXITOS");
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Las estad�sticas de TASA_EXITOS para la titulaci�n GRADO EN INGENIER�A INFORM�TICA en los �ltimos a�os son las siguientes:\n" + 
				"-Curso 2018 - 2019: 78,19.\n" + 
				"-Curso 2017 - 2018: 80,17.\n" + 
				"-Curso 2016 - 2017: 78,16.\n" + 
				"-Curso 2015 - 2016: 78,47.\n"));
	}

	@Test
	public void testMensajeConversacionSobreTopEstadisticaRendimientoTitulacion(){
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("estadisticaRendimientoTopTitulacion", "grado", "TASA_EXITOS", "mayores");
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Durante el �ltimo curso acad�mico registrado (2018 - 2019), estas fueron los estudios de GRADO que obtuvieron mayores resultados en cuanto a TASA_EXITOS :\n" + 
				"-La titulaci�n GRADO EN FILOSOF�A obtuvo unos resultados de 92,84.\n" + 
				"-La titulaci�n GRADO EN ANTROPOLOG�A SOCIAL Y CULTURAL obtuvo unos resultados de 92,19.\n" + 
				"-La titulaci�n GRADO EN HISTORIA DEL ARTE obtuvo unos resultados de 92,08.\n" + 
				"-La titulaci�n GRADO EN LENGUA Y LITERATURA ESPA�OLAS obtuvo unos resultados de 91,11.\n" + 
				"-La titulaci�n GRADO EN GEOGRAF�A E HISTORIA obtuvo unos resultados de 90,56.\n" + 
				"-La titulaci�n GRADO EN CC. JUR�DICAS DE LAS ADMINISTRACIONES P�BLICAS obtuvo unos resultados de 90,33.\n" + 
				"-La titulaci�n GRADO EN ESTUDIOS INGLESES: LENGUA, LITERATURA Y CULTURA obtuvo unos resultados de 89,39.\n" + 
				"-La titulaci�n GRADO EN EDUCACI�N SOCIAL obtuvo unos resultados de 89,37.\n" + 
				"-La titulaci�n GRADO EN CIENCIA POL�TICA Y DE LA ADMINISTRACI�N obtuvo unos resultados de 88,76.\n" + 
				"-La titulaci�n GRADO EN PEDAGOG�A obtuvo unos resultados de 88,67.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreEstadisticaRendimientoAsignatura(){
		Titulacion titulacion = new Titulacion(6201,"GRADO EN PSICOLOG�A","GRADO");
		Asignatura asignatura = new Asignatura("62011014","FUNDAMENTOS DE PSICOBIOLOG�A",6);
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("estadisticaRendimientoAsignatura", titulacion, asignatura, "PORCENTAJE_TASA_SOBRESALIENTES");
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Las estad�sticas de PORCENTAJE_TASA_SOBRESALIENTES para la asignatura FUNDAMENTOS DE PSICOBIOLOG�A de la titulaci�n GRADO EN PSICOLOG�A en los �ltimos a�os son las siguientes:\n" + 
				"-Curso 2018-2019: 0,36.\n" + 
				"-Curso 2017-2018: 0,69.\n" + 
				"-Curso 2016-2017: 0,74.\n" + 
				"-Curso 2015-2016: 0,85.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreTopEstadisticaRendimientoAsignatura(){
		Titulacion titulacion = new Titulacion(7101,"GRADO EN INGENIER�A INFORM�TICA","GRADO");
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("estadisticaRendimientoTopAsignatura", titulacion, "PORCENTAJE_TASA_EXITO", "menores", new Stack<String>());
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Durante el �ltimo curso acad�mico registrado (2018-2019), estas fueron las asignaturas de la titulaci�n GRADO EN INGENIER�A INFORM�TICA que obtuvieron menores resultados en las estad�sticas de PORCENTAJE_TASA_EXITO:\n" + 
				"-La asignatura FUNDAMENTOS DE CONTROL AUTOM�TICO obtuvo unos resultados de 0,00.\n" + 
				"-La asignatura TRATAMIENTO DIGITAL DE SE�ALES obtuvo unos resultados de 0,00.\n" + 
				"-La asignatura FUNDAMENTOS F�SICOS DE LA INFORM�TICA obtuvo unos resultados de 37,34.\n" + 
				"-La asignatura REDES DE COMPUTADORES obtuvo unos resultados de 50,00.\n" + 
				"-La asignatura INGENIER�A DE COMPUTADORES I obtuvo unos resultados de 55,95.\n" + 
				"-La asignatura INGENIER�A DE COMPUTADORES II obtuvo unos resultados de 61,24.\n" + 
				"-La asignatura BASES DE DATOS obtuvo unos resultados de 63,20.\n" + 
				"-La asignatura PROGRAMACI�N Y ESTRUCTURAS DE DATOS AVANZADAS obtuvo unos resultados de 66,12.\n" + 
				"-La asignatura FUNDAMENTOS DE PROGRAMACI�N obtuvo unos resultados de 68,68.\n" + 
				"-La asignatura FUNDAMENTOS MATEM�TICOS DE LA INFORM�TICA obtuvo unos resultados de 70,65.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreInformacionGenerica(){
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("informacionGenerica","matriculaAdmisionPorInternet");
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Para matricularse de cualquier estudio impartido en la UNED, puede realizar el tr�mite a trav�s de Internet siguiendo el siguiente enlace:\r\n" + 
				"https://app.uned.es/portal/admision-matricula-por-internet"));
	}
	
}
