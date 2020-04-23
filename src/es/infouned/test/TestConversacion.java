package es.infouned.test;


import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import es.infouned.conversacion.Conversacion;
import es.infouned.principal.Configuracion;

public class TestConversacion {

	@BeforeAll
	private static void inicializarPropiedades() {
		String rutaFicheroConfiguracion = "/config.properties";
		Configuracion.establecerPropiedadesConfiguracionAPartirDeFichero(rutaFicheroConfiguracion);
		Configuracion.iniciarProcesadorLenguajeNatural();
	}
	
	@AfterAll
	private static void eliminarConfiguracion() {
		Configuracion.eliminarTodo();
	}
	
	@Test
    public void testConversacionPrecios(){		
    	Conversacion conversacion = new Conversacion("123456789", "Telegram");
    	conversacion.procesarTextoRecibido("�Cuanto cuesta el grado en psicolog�a?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("El precio de las asignaturas de la titulaci�n GRADO EN PSICOLOG�A es de:\n" + 
    			"-15,95� por cada cr�dito ECTS la primera vez que el alumno se matricula de la asignatura.\n" + 
    			"-22,95� por cada cr�dito ECTS la segunda vez que el alumno se matricula de la asignatura.\n" + 
    			"-50,49� por cada cr�dito ECTS la tercera vez que el alumno se matricula de la asignatura.\n" + 
    			"-69,62� por cada cr�dito ECTS la cuarta y las sucesivas veces que el alumno se matricule de la asignatura.\n"));
    	conversacion.procesarTextoRecibido("�Cuanto cuesta estudiar en la uned?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Si desea informaci�n sobre los precios de una titulaci�n, debe especificar claramente el nombre de la misma.\n"));
    	conversacion.procesarTextoRecibido("�Cuanto cuestan las asignaturas del grado en geograf�a e historia?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("El precio de las asignaturas de la titulaci�n GRADO EN GEOGRAF�A E HISTORIA es de:\n" + 
    			"-13,00� por cada cr�dito ECTS la primera vez que el alumno se matricula de la asignatura.\n" + 
    			"-18,97� por cada cr�dito ECTS la segunda vez que el alumno se matricula de la asignatura.\n" + 
    			"-41,74� por cada cr�dito ECTS la tercera vez que el alumno se matricula de la asignatura.\n" + 
    			"-57,55� por cada cr�dito ECTS la cuarta y las sucesivas veces que el alumno se matricule de la asignatura.\n"));
	}

	@Test
    public void testConversacionValoracionEstudiantil(){		
    	Conversacion conversacion = new Conversacion("123456789", "Telegram");
    	conversacion.procesarTextoRecibido("�Est� bien valorada la asignatura �tica y legislaci�n en el grado en ingenier�a inform�tica?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Las calificaciones que ha obtenido la asignatura �TICA Y LEGISLACI�N de la titulaci�n GRADO EN INGENIER�A INFORM�TICA en los �ltimos a�os seg�n los cuestionarios de satisfacci�n de los estudiantes son las siguientes:\n" + 
    			"-Curso 2018-2019: 53,09 puntos sobre 100.\n" + 
    			"-Curso 2017-2018: 54,89 puntos sobre 100.\n" + 
    			"-Curso 2016-2017: 41,49 puntos sobre 100.\n" + 
    			"-Curso 2015-2016: 50,60 puntos sobre 100.\n"));
    	conversacion.procesarTextoRecibido("�Est� bien valorada la asignatura fundamentos de programaci�n?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("GRADO EN INGENIER�A INFORM�TICA\n" + 
    			"GRADO EN INGENIER�A EN TECNOLOG�AS DE LA INFORMACI�N\n" + 
    			"Mi consulta no estaba relacionada con eso\n"));
    	conversacion.procesarTextoRecibido("GRADO EN INGENIER�A INFORM�TICA");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Las calificaciones que ha obtenido la asignatura FUNDAMENTOS DE PROGRAMACI�N de la titulaci�n GRADO EN INGENIER�A INFORM�TICA en los �ltimos a�os seg�n los cuestionarios de satisfacci�n de los estudiantes son las siguientes:\n" + 
    			"-Curso 2018-2019: 65,83 puntos sobre 100.\n" + 
    			"-Curso 2017-2018: 68,47 puntos sobre 100.\n" + 
    			"-Curso 2016-2017: 62,07 puntos sobre 100.\n" + 
    			"-Curso 2015-2016: 57,88 puntos sobre 100.\n"));
    	conversacion.procesarTextoRecibido("�Los alumnos est�n descontentos con la titulaci�n de grado en psicolog�a");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Las calificaciones que ha obtenido la titulaci�n GRADO EN PSICOLOG�A en los �ltimos a�os seg�n los cuestionarios de satisfacci�n de los estudiantes son las siguientes:\n" + 
    			"-Curso 2018 - 2019: 69,35 puntos sobre 100.\n" + 
    			"-Curso 2017 - 2018: 67,76 puntos sobre 100.\n" + 
    			"-Curso 2016 - 2017: 65,73 puntos sobre 100.\n" + 
    			"-Curso 2015 - 2016: 78,69 puntos sobre 100.\n"));
    	
	}

	@Test
    public void testConversacionEstadisticaRendimiento(){		
    	Conversacion conversacion = new Conversacion("123456789", "Telegram");
    	conversacion.procesarTextoRecibido("�Cual es la nota media de grado en ingenier�a inform�tica?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Las estad�sticas de NOTA MEDIA para la titulaci�n GRADO EN INGENIER�A INFORM�TICA en los �ltimos a�os son las siguientes:\n" + 
    			"-Curso 2018 - 2019: 7,05.\n" + 
    			"-Curso 2017 - 2018: 7,03.\n" + 
    			"-Curso 2016 - 2017: 7,08.\n" + 
    			"-Curso 2015 - 2016: 7,04.\n"));
    	conversacion.procesarTextoRecibido("�Cual es la nota media de programaci�n orientada a objetos?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("GRADO EN INGENIER�A INFORM�TICA\n" + 
    			"GRADO EN INGENIER�A EN TECNOLOG�AS DE LA INFORMACI�N\n" + 
    			"Mi consulta no estaba relacionada con eso\n"));
    	conversacion.procesarTextoRecibido("GRADO EN INGENIER�A EN TECNOLOG�AS DE LA INFORMACI�N");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Las estad�sticas de NOTA MEDIA para la asignatura PROGRAMACI�N ORIENTADA A OBJETOS de la titulaci�n GRADO EN INGENIER�A EN TECNOLOG�AS DE LA INFORMACI�N en los �ltimos a�os son las siguientes:\n" + 
    			"-Curso 2018-2019: 6,60.\n" + 
    			"-Curso 2017-2018: 6,18.\n" + 
    			"-Curso 2016-2017: 6,20.\n" + 
    			"-Curso 2015-2016: 6,11.\n"));
    	conversacion.procesarTextoRecibido("�Cual es la nota media de HISTORIA DE LA ALTA EDAD MODERNA perteneciente al GRADO EN GEOGRAF�A E HISTORIA?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Las estad�sticas de NOTA MEDIA para la asignatura HISTORIA DE LA ALTA EDAD MODERNA de la titulaci�n GRADO EN GEOGRAF�A E HISTORIA en los �ltimos a�os son las siguientes:\n" + 
    			"-Curso 2018-2019: 6,73.\n" + 
    			"-Curso 2017-2018: 6,63.\n" + 
    			"-Curso 2016-2017: 6,98.\n" + 
    			"-Curso 2015-2016: 7,08.\n"));
	}
	
	@Test
    public void testConversacionEstadisticaRendimientoTop(){		
    	Conversacion conversacion = new Conversacion("123456789", "Telegram");
    	conversacion.procesarTextoRecibido("�Cual es la asignatura m�s f�cil del grado en derecho?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Durante el �ltimo curso acad�mico registrado (2018-2019), estas fueron las asignaturas de la titulaci�n GRADO EN DERECHO que obtuvieron mayores resultados en las estad�sticas de TASA DE �XITO:\n" + 
    			"-La asignatura TRABAJO FIN DE GRADO (DERECHO) obtuvo unos resultados de 99,89.\n" + 
    			"-La asignatura DERECHO DEL CONSUMO obtuvo unos resultados de 98,21.\n" + 
    			"-La asignatura DERECHO PROCESAL I.2: ESPECIAL obtuvo unos resultados de 94,32.\n" + 
    			"-La asignatura DERECHO PROCESAL I.1: GENERAL obtuvo unos resultados de 94,18.\n" + 
    			"-La asignatura FILOSOF�A DEL DERECHO obtuvo unos resultados de 93,98.\n" + 
    			"-La asignatura DERECHO MERCANTIL IV: T�TULOS-VALORES, INSTRUMENTOS DE PAGO Y CR�DITO Y DERECHO CONCURSAL obtuvo unos resultados de 93,71.\n" + 
    			"-La asignatura TEOR�A DEL ESTADO CONSTITUCIONAL obtuvo unos resultados de 92,86.\n" + 
    			"-La asignatura DERECHO CIVIL III: DERECHOS REALES E HIPOTECARIO obtuvo unos resultados de 92,65.\n" + 
    			"-La asignatura DERECHO ADMINISTRATIVO IV obtuvo unos resultados de 92,25.\n" + 
    			"-La asignatura DERECHO CIVIL IV: DERECHO DE SUCESIONES obtuvo unos resultados de 90,26.\n"));
    	conversacion.procesarTextoRecibido("�Cual es la asignatura m�s dificil de cuarto curso del grado en ingenieria electrica?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Durante el �ltimo curso acad�mico registrado (2018-2019), estas fueron las asignaturas de la titulaci�n GRADO EN INGENIER�A EL�CTRICA que cumplen el criterio de  PERTENECER AL CUARTO CURSO que obtuvieron mayores resultados en las estad�sticas de TASA DE SUSPENSOS:\n" + 
    			"-La asignatura ACCIONAMIENTO Y CONTROL DE M�QUINAS EL�CTRICAS obtuvo unos resultados de 28,57.\n" + 
    			"-La asignatura INGENIER�A DEL MEDIO AMBIENTE obtuvo unos resultados de 25,00.\n" + 
    			"-La asignatura SISTEMAS FOTOVOLTAICOS obtuvo unos resultados de 20,00.\n" + 
    			"-La asignatura OFICINA T�CNICA Y PROYECTOS (I. EL�CTRICA) obtuvo unos resultados de 16,67.\n" + 
    			"-La asignatura GENERACI�N DE ENERG�A EL�CTRICA obtuvo unos resultados de 11,11.\n" + 
    			"-La asignatura AN�LISIS Y OPERACI�N DE SISTEMAS EL�CTRICOS obtuvo unos resultados de 8,33.\n" + 
    			"-La asignatura AN�LISIS DEL ENTORNO Y ADMINISTRACI�N DE EMPRESAS obtuvo unos resultados de 0,00.\n" + 
    			"-La asignatura AUTOM�VILES Y FERROCARRILES obtuvo unos resultados de 0,00.\n" + 
    			"-La asignatura CONSTRUCCI�N Y ARQUITECTURA INDUSTRIAL obtuvo unos resultados de 0,00.\n" + 
    			"-La asignatura CONTROL AVANZADO DE SISTEMAS EL�CTRICOS obtuvo unos resultados de 0,00.\n"));
    	conversacion.procesarTextoRecibido("�Cual es la carrera mas dificil?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Durante el �ltimo curso acad�mico registrado (2018 - 2019), estas fueron los estudios de GRADO que obtuvieron mayores resultados en cuanto a PORCENTAJE DE SUSPENSOS :\n" + 
    			"-La titulaci�n GRADO EN INGENIER�A EN TECNOLOG�AS INDUSTRIALES obtuvo unos resultados de 32,40.\n" + 
    			"-La titulaci�n GRADO EN INGENIER�A EL�CTRICA obtuvo unos resultados de 31,02.\n" + 
    			"-La titulaci�n GRADO EN ING. EN  ELECTR�NICA INDUSTRIAL Y AUTOM�TICA obtuvo unos resultados de 30,79.\n" + 
    			"-La titulaci�n GRADO EN INGENIER�A MEC�NICA obtuvo unos resultados de 28,07.\n" + 
    			"-La titulaci�n GRADO EN ADMINISTRACI�N Y DIRECCI�N DE EMPRESAS obtuvo unos resultados de 26,13.\n" + 
    			"-La titulaci�n GRADO EN F�SICA obtuvo unos resultados de 25,41.\n" + 
    			"-La titulaci�n GRADO EN QU�MICA obtuvo unos resultados de 24,59.\n" + 
    			"-La titulaci�n GRADO EN PSICOLOG�A obtuvo unos resultados de 24,43.\n" + 
    			"-La titulaci�n GRADO EN INGENIER�A INFORM�TICA obtuvo unos resultados de 24,22.\n" + 
    			"-La titulaci�n GRADO EN MATEM�TICAS obtuvo unos resultados de 23,74.\n"));
	}
	
	@Test
    public void testConversacionMatriculados(){		
    	Conversacion conversacion = new Conversacion("123456789", "Telegram");
    	conversacion.procesarTextoRecibido("�Cuanta gente se matricula de fundamentos de psicobiolog�a en el grado en psicolog�a?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("El n�mero de matriculados en la asignatura FUNDAMENTOS DE PSICOBIOLOG�A de la titulaci�n GRADO EN PSICOLOG�A en los �ltimos a�os son los siguientes:\n" + 
    			"-Curso 2019 - 2020: 7934 matriculados.\n" + 
    			"-Curso 2018 - 2019: 7233 matriculados.\n" + 
    			"-Curso 2017 - 2018: 7002 matriculados.\n" + 
    			"-Curso 2016 - 2017: 7281 matriculados.\n" + 
    			"-Curso 2015 - 2016: 8302 matriculados.\n")); 
    	conversacion.procesarTextoRecibido("�Cuanta gente se matricula de fundamentos de psicobiolog�a en el grado en ingenier�a inform�tica?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("La asignatura FUNDAMENTOS DE PSICOBIOLOG�A no forma parte de la titulaci�n GRADO EN INGENIER�A INFORM�TICA.\n"
    			+ "Por favor, intente expresar su consulta de otra forma, prestando especial atenci�n al nombre de los estudios de los cuales desea informaci�n.\n")); 
    	conversacion.procesarTextoRecibido("�Cuantos hacen la matr�cula de ingl�s");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("El n�mero de matriculados en la titulaci�n INGL�S en los �ltimos a�os son los siguientes:\n" + 
    			"-Curso 2019 - 2020: 6861 matriculados.\n" + 
    			"-Curso 2018 - 2019: 7690 matriculados.\n" + 
    			"-Curso 2017 - 2018: 9750 matriculados.\n" + 
    			"-Curso 2016 - 2017: 10629 matriculados.\n" + 
    			"-Curso 2015 - 2016: 12588 matriculados.\n")); 
    	conversacion.procesarTextoRecibido("�Cuanta gente se matricula de qu�mica, matem�ticas y esas cosas?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("El n�mero de matriculados en la asignatura QU�MICA (CURSO DE ACCESO) de la titulaci�n ACCESO A LA UNIVERSIDAD PARA MAYORES DE 25 Y 45 A�OS en los �ltimos a�os son los siguientes:\n" + 
    			"-Curso 2019 - 2020: 68 matriculados.\n" + 
    			"-Curso 2018 - 2019: 88 matriculados.\n" + 
    			"-Curso 2017 - 2018: 86 matriculados.\n" + 
    			"-Curso 2016 - 2017: 94 matriculados.\n" + 
    			"-Curso 2015 - 2016: 104 matriculados.\n"));
    	conversacion.procesarTextoRecibido("�Cuantas personas se matriculan de fundamentos de psicobiolog�a");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("El n�mero de matriculados en la asignatura FUNDAMENTOS DE PSICOBIOLOG�A de la titulaci�n GRADO EN PSICOLOG�A en los �ltimos a�os son los siguientes:\n" + 
    			"-Curso 2019 - 2020: 7934 matriculados.\n" + 
    			"-Curso 2018 - 2019: 7233 matriculados.\n" + 
    			"-Curso 2017 - 2018: 7002 matriculados.\n" + 
    			"-Curso 2016 - 2017: 7281 matriculados.\n" + 
    			"-Curso 2015 - 2016: 8302 matriculados.\n"));
    	conversacion.procesarTextoRecibido("�Cuanta gente se matricula de fundamentos de psicobiolog�a en el grado en psicolog�a?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("El n�mero de matriculados en la asignatura FUNDAMENTOS DE PSICOBIOLOG�A de la titulaci�n GRADO EN PSICOLOG�A en los �ltimos a�os son los siguientes:\n" + 
    			"-Curso 2019 - 2020: 7934 matriculados.\n" + 
    			"-Curso 2018 - 2019: 7233 matriculados.\n" + 
    			"-Curso 2017 - 2018: 7002 matriculados.\n" + 
    			"-Curso 2016 - 2017: 7281 matriculados.\n" + 
    			"-Curso 2015 - 2016: 8302 matriculados.\n"));
    	conversacion.procesarTextoRecibido("�Cuanta gente se matricula en grado en ingenier�a inform�tica, grado en psicolog�a, fundamentos de psicobiolog�a en el grado en ingenier�a inform�tica?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Si desea informaci�n sobre el n�mero de matriculados de una determinada titulaci�n o asignatura, debe nombrar s�lo la titulaci�n y la asignatura sobre las que quiere esa informaci�n.\n"));
    	conversacion.procesarTextoRecibido("�Cuanta gente se matricula de fundamentos de psicobiolog�a en el grado en ingenier�a inform�tica?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("La asignatura FUNDAMENTOS DE PSICOBIOLOG�A no forma parte de la titulaci�n GRADO EN INGENIER�A INFORM�TICA.\n" + 
    			"Por favor, intente expresar su consulta de otra forma, prestando especial atenci�n al nombre de los estudios de los cuales desea informaci�n.\n"));
	}
	
	@Test
    public void testConversacionCallBack(){		
    	Conversacion conversacion = new Conversacion("123456789", "Telegram");
    	conversacion.procesarTextoRecibido("Cuanta gente se matricula de programacion y estructuras de datos avanzadas grado en ingenier�a inform�tica");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("El n�mero de matriculados en la asignatura PROGRAMACI�N Y ESTRUCTURAS DE DATOS AVANZADAS de la titulaci�n GRADO EN INGENIER�A INFORM�TICA en los �ltimos a�os son los siguientes:\n" + 
    			"-Curso 2019 - 2020: 289 matriculados.\n" + 
    			"-Curso 2018 - 2019: 273 matriculados.\n" + 
    			"-Curso 2017 - 2018: 275 matriculados.\n" + 
    			"-Curso 2016 - 2017: 260 matriculados.\n" + 
    			"-Curso 2015 - 2016: 323 matriculados.\n")); 
    	conversacion.procesarTextoRecibido("Cuanta gente se matricula de estrategias de programacion y estructuras de datos");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("GRADO EN INGENIER�A INFORM�TICA\n" + 
    			"GRADO EN INGENIER�A EN TECNOLOG�AS DE LA INFORMACI�N\n" + 
    			"Mi consulta no estaba relacionada con eso\n")); 
    	conversacion.procesarTextoRecibido("Cuanta gente se matricula de estrategias de programacion y estructuras de datos");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("GRADO EN INGENIER�A INFORM�TICA\n" + 
    			"GRADO EN INGENIER�A EN TECNOLOG�AS DE LA INFORMACI�N\n" + 
    			"Mi consulta no estaba relacionada con eso\n")); 
    	conversacion.procesarTextoRecibido("Mi consulta no estaba relacionada con eso");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Siento no haberle entendido correctamente, �Podr�a repetir su consulta?\n")); 
    	conversacion.procesarTextoRecibido("Cuanta gente se matricula de estrategias de programacion y estructuras de datos");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("GRADO EN INGENIER�A INFORM�TICA\n" + 
    			"GRADO EN INGENIER�A EN TECNOLOG�AS DE LA INFORMACI�N\n" + 
    			"Mi consulta no estaba relacionada con eso\n")); 
    	conversacion.procesarTextoRecibido("GRADO EN INGENIER�A INFORM�TICA");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("El n�mero de matriculados en la asignatura ESTRATEGIAS DE PROGRAMACI�N Y ESTRUCTURAS DE DATOS de la titulaci�n GRADO EN INGENIER�A INFORM�TICA en los �ltimos a�os son los siguientes:\n" + 
    			"-Curso 2019 - 2020: 856 matriculados.\n" + 
    			"-Curso 2018 - 2019: 842 matriculados.\n" + 
    			"-Curso 2017 - 2018: 782 matriculados.\n" + 
    			"-Curso 2016 - 2017: 837 matriculados.\n" + 
    			"-Curso 2015 - 2016: 942 matriculados.\n")); 
	}
	
}
