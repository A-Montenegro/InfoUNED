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
    	conversacion.procesarTextoRecibido("¿Cuanto cuesta el grado en psicología?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("El precio de las asignaturas de la titulación GRADO EN PSICOLOGÍA es de:\n" + 
    			"-15,95€ por cada crédito ECTS la primera vez que el alumno se matricula de la asignatura.\n" + 
    			"-22,95€ por cada crédito ECTS la segunda vez que el alumno se matricula de la asignatura.\n" + 
    			"-50,49€ por cada crédito ECTS la tercera vez que el alumno se matricula de la asignatura.\n" + 
    			"-69,62€ por cada crédito ECTS la cuarta y las sucesivas veces que el alumno se matricule de la asignatura.\n"));
    	conversacion.procesarTextoRecibido("¿Cuanto cuesta estudiar en la uned?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Si desea información sobre los precios de una titulación, debe especificar claramente el nombre de la misma.\n"));
    	conversacion.procesarTextoRecibido("¿Cuanto cuestan las asignaturas del grado en geografía e historia?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("El precio de las asignaturas de la titulación GRADO EN GEOGRAFÍA E HISTORIA es de:\n" + 
    			"-13,00€ por cada crédito ECTS la primera vez que el alumno se matricula de la asignatura.\n" + 
    			"-18,97€ por cada crédito ECTS la segunda vez que el alumno se matricula de la asignatura.\n" + 
    			"-41,74€ por cada crédito ECTS la tercera vez que el alumno se matricula de la asignatura.\n" + 
    			"-57,55€ por cada crédito ECTS la cuarta y las sucesivas veces que el alumno se matricule de la asignatura.\n"));
	}

	@Test
    public void testConversacionValoracionEstudiantil(){		
    	Conversacion conversacion = new Conversacion("123456789", "Telegram");
    	conversacion.procesarTextoRecibido("¿Está bien valorada la asignatura ética y legislación en el grado en ingeniería informática?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Las calificaciones que ha obtenido la asignatura ÉTICA Y LEGISLACIÓN de la titulación GRADO EN INGENIERÍA INFORMÁTICA en los últimos años según los cuestionarios de satisfacción de los estudiantes son las siguientes:\n" + 
    			"-Curso 2018-2019: 53,09 puntos sobre 100.\n" + 
    			"-Curso 2017-2018: 54,89 puntos sobre 100.\n" + 
    			"-Curso 2016-2017: 41,49 puntos sobre 100.\n" + 
    			"-Curso 2015-2016: 50,60 puntos sobre 100.\n"));
    	conversacion.procesarTextoRecibido("¿Está bien valorada la asignatura fundamentos de programación?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("GRADO EN INGENIERÍA INFORMÁTICA\n" + 
    			"GRADO EN INGENIERÍA EN TECNOLOGÍAS DE LA INFORMACIÓN\n" + 
    			"Mi consulta no estaba relacionada con eso\n"));
    	conversacion.procesarTextoRecibido("GRADO EN INGENIERÍA INFORMÁTICA");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Las calificaciones que ha obtenido la asignatura FUNDAMENTOS DE PROGRAMACIÓN de la titulación GRADO EN INGENIERÍA INFORMÁTICA en los últimos años según los cuestionarios de satisfacción de los estudiantes son las siguientes:\n" + 
    			"-Curso 2018-2019: 65,83 puntos sobre 100.\n" + 
    			"-Curso 2017-2018: 68,47 puntos sobre 100.\n" + 
    			"-Curso 2016-2017: 62,07 puntos sobre 100.\n" + 
    			"-Curso 2015-2016: 57,88 puntos sobre 100.\n"));
    	conversacion.procesarTextoRecibido("¿Los alumnos están descontentos con la titulación de grado en psicología");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Las calificaciones que ha obtenido la titulación GRADO EN PSICOLOGÍA en los últimos años según los cuestionarios de satisfacción de los estudiantes son las siguientes:\n" + 
    			"-Curso 2018 - 2019: 69,35 puntos sobre 100.\n" + 
    			"-Curso 2017 - 2018: 67,76 puntos sobre 100.\n" + 
    			"-Curso 2016 - 2017: 65,73 puntos sobre 100.\n" + 
    			"-Curso 2015 - 2016: 78,69 puntos sobre 100.\n"));
    	
	}

	@Test
    public void testConversacionEstadisticaRendimiento(){		
    	Conversacion conversacion = new Conversacion("123456789", "Telegram");
    	conversacion.procesarTextoRecibido("¿Cual es la nota media de grado en ingeniería informática?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Las estadísticas de NOTA MEDIA para la titulación GRADO EN INGENIERÍA INFORMÁTICA en los últimos años son las siguientes:\n" + 
    			"-Curso 2018 - 2019: 7,05.\n" + 
    			"-Curso 2017 - 2018: 7,03.\n" + 
    			"-Curso 2016 - 2017: 7,08.\n" + 
    			"-Curso 2015 - 2016: 7,04.\n"));
    	conversacion.procesarTextoRecibido("¿Cual es la nota media de programación orientada a objetos?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("GRADO EN INGENIERÍA INFORMÁTICA\n" + 
    			"GRADO EN INGENIERÍA EN TECNOLOGÍAS DE LA INFORMACIÓN\n" + 
    			"Mi consulta no estaba relacionada con eso\n"));
    	conversacion.procesarTextoRecibido("GRADO EN INGENIERÍA EN TECNOLOGÍAS DE LA INFORMACIÓN");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Las estadísticas de NOTA MEDIA para la asignatura PROGRAMACIÓN ORIENTADA A OBJETOS de la titulación GRADO EN INGENIERÍA EN TECNOLOGÍAS DE LA INFORMACIÓN en los últimos años son las siguientes:\n" + 
    			"-Curso 2018-2019: 6,60.\n" + 
    			"-Curso 2017-2018: 6,18.\n" + 
    			"-Curso 2016-2017: 6,20.\n" + 
    			"-Curso 2015-2016: 6,11.\n"));
    	conversacion.procesarTextoRecibido("¿Cual es la nota media de HISTORIA DE LA ALTA EDAD MODERNA perteneciente al GRADO EN GEOGRAFÍA E HISTORIA?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Las estadísticas de NOTA MEDIA para la asignatura HISTORIA DE LA ALTA EDAD MODERNA de la titulación GRADO EN GEOGRAFÍA E HISTORIA en los últimos años son las siguientes:\n" + 
    			"-Curso 2018-2019: 6,73.\n" + 
    			"-Curso 2017-2018: 6,63.\n" + 
    			"-Curso 2016-2017: 6,98.\n" + 
    			"-Curso 2015-2016: 7,08.\n"));
	}
	
	@Test
    public void testConversacionEstadisticaRendimientoTop(){		
    	Conversacion conversacion = new Conversacion("123456789", "Telegram");
    	conversacion.procesarTextoRecibido("¿Cual es la asignatura más fácil del grado en derecho?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Durante el último curso académico registrado (2018-2019), estas fueron las asignaturas de la titulación GRADO EN DERECHO que obtuvieron mayores resultados en las estadísticas de TASA DE ÉXITO:\n" + 
    			"-La asignatura TRABAJO FIN DE GRADO (DERECHO) obtuvo unos resultados de 99,89.\n" + 
    			"-La asignatura DERECHO DEL CONSUMO obtuvo unos resultados de 98,21.\n" + 
    			"-La asignatura DERECHO PROCESAL I.2: ESPECIAL obtuvo unos resultados de 94,32.\n" + 
    			"-La asignatura DERECHO PROCESAL I.1: GENERAL obtuvo unos resultados de 94,18.\n" + 
    			"-La asignatura FILOSOFÍA DEL DERECHO obtuvo unos resultados de 93,98.\n" + 
    			"-La asignatura DERECHO MERCANTIL IV: TÍTULOS-VALORES, INSTRUMENTOS DE PAGO Y CRÉDITO Y DERECHO CONCURSAL obtuvo unos resultados de 93,71.\n" + 
    			"-La asignatura TEORÍA DEL ESTADO CONSTITUCIONAL obtuvo unos resultados de 92,86.\n" + 
    			"-La asignatura DERECHO CIVIL III: DERECHOS REALES E HIPOTECARIO obtuvo unos resultados de 92,65.\n" + 
    			"-La asignatura DERECHO ADMINISTRATIVO IV obtuvo unos resultados de 92,25.\n" + 
    			"-La asignatura DERECHO CIVIL IV: DERECHO DE SUCESIONES obtuvo unos resultados de 90,26.\n"));
    	conversacion.procesarTextoRecibido("¿Cual es la asignatura más dificil de cuarto curso del grado en ingenieria electrica?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Durante el último curso académico registrado (2018-2019), estas fueron las asignaturas de la titulación GRADO EN INGENIERÍA ELÉCTRICA que cumplen el criterio de  PERTENECER AL CUARTO CURSO que obtuvieron mayores resultados en las estadísticas de TASA DE SUSPENSOS:\n" + 
    			"-La asignatura ACCIONAMIENTO Y CONTROL DE MÁQUINAS ELÉCTRICAS obtuvo unos resultados de 28,57.\n" + 
    			"-La asignatura INGENIERÍA DEL MEDIO AMBIENTE obtuvo unos resultados de 25,00.\n" + 
    			"-La asignatura SISTEMAS FOTOVOLTAICOS obtuvo unos resultados de 20,00.\n" + 
    			"-La asignatura OFICINA TÉCNICA Y PROYECTOS (I. ELÉCTRICA) obtuvo unos resultados de 16,67.\n" + 
    			"-La asignatura GENERACIÓN DE ENERGÍA ELÉCTRICA obtuvo unos resultados de 11,11.\n" + 
    			"-La asignatura ANÁLISIS Y OPERACIÓN DE SISTEMAS ELÉCTRICOS obtuvo unos resultados de 8,33.\n" + 
    			"-La asignatura ANÁLISIS DEL ENTORNO Y ADMINISTRACIÓN DE EMPRESAS obtuvo unos resultados de 0,00.\n" + 
    			"-La asignatura AUTOMÓVILES Y FERROCARRILES obtuvo unos resultados de 0,00.\n" + 
    			"-La asignatura CONSTRUCCIÓN Y ARQUITECTURA INDUSTRIAL obtuvo unos resultados de 0,00.\n" + 
    			"-La asignatura CONTROL AVANZADO DE SISTEMAS ELÉCTRICOS obtuvo unos resultados de 0,00.\n"));
    	conversacion.procesarTextoRecibido("¿Cual es la carrera mas dificil?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Durante el último curso académico registrado (2018 - 2019), estas fueron los estudios de GRADO que obtuvieron mayores resultados en cuanto a PORCENTAJE DE SUSPENSOS :\n" + 
    			"-La titulación GRADO EN INGENIERÍA EN TECNOLOGÍAS INDUSTRIALES obtuvo unos resultados de 32,40.\n" + 
    			"-La titulación GRADO EN INGENIERÍA ELÉCTRICA obtuvo unos resultados de 31,02.\n" + 
    			"-La titulación GRADO EN ING. EN  ELECTRÓNICA INDUSTRIAL Y AUTOMÁTICA obtuvo unos resultados de 30,79.\n" + 
    			"-La titulación GRADO EN INGENIERÍA MECÁNICA obtuvo unos resultados de 28,07.\n" + 
    			"-La titulación GRADO EN ADMINISTRACIÓN Y DIRECCIÓN DE EMPRESAS obtuvo unos resultados de 26,13.\n" + 
    			"-La titulación GRADO EN FÍSICA obtuvo unos resultados de 25,41.\n" + 
    			"-La titulación GRADO EN QUÍMICA obtuvo unos resultados de 24,59.\n" + 
    			"-La titulación GRADO EN PSICOLOGÍA obtuvo unos resultados de 24,43.\n" + 
    			"-La titulación GRADO EN INGENIERÍA INFORMÁTICA obtuvo unos resultados de 24,22.\n" + 
    			"-La titulación GRADO EN MATEMÁTICAS obtuvo unos resultados de 23,74.\n"));
	}
	
	@Test
    public void testConversacionMatriculados(){		
    	Conversacion conversacion = new Conversacion("123456789", "Telegram");
    	conversacion.procesarTextoRecibido("¿Cuanta gente se matricula de fundamentos de psicobiología en el grado en psicología?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("El número de matriculados en la asignatura FUNDAMENTOS DE PSICOBIOLOGÍA de la titulación GRADO EN PSICOLOGÍA en los últimos años son los siguientes:\n" + 
    			"-Curso 2019 - 2020: 7934 matriculados.\n" + 
    			"-Curso 2018 - 2019: 7233 matriculados.\n" + 
    			"-Curso 2017 - 2018: 7002 matriculados.\n" + 
    			"-Curso 2016 - 2017: 7281 matriculados.\n" + 
    			"-Curso 2015 - 2016: 8302 matriculados.\n")); 
    	conversacion.procesarTextoRecibido("¿Cuanta gente se matricula de fundamentos de psicobiología en el grado en ingeniería informática?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("La asignatura FUNDAMENTOS DE PSICOBIOLOGÍA no forma parte de la titulación GRADO EN INGENIERÍA INFORMÁTICA.\n"
    			+ "Por favor, intente expresar su consulta de otra forma, prestando especial atención al nombre de los estudios de los cuales desea información.\n")); 
    	conversacion.procesarTextoRecibido("¿Cuantos hacen la matrícula de inglés");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("El número de matriculados en la titulación INGLÉS en los últimos años son los siguientes:\n" + 
    			"-Curso 2019 - 2020: 6861 matriculados.\n" + 
    			"-Curso 2018 - 2019: 7690 matriculados.\n" + 
    			"-Curso 2017 - 2018: 9750 matriculados.\n" + 
    			"-Curso 2016 - 2017: 10629 matriculados.\n" + 
    			"-Curso 2015 - 2016: 12588 matriculados.\n")); 
    	conversacion.procesarTextoRecibido("¿Cuanta gente se matricula de química, matemáticas y esas cosas?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("El número de matriculados en la asignatura QUÍMICA (CURSO DE ACCESO) de la titulación ACCESO A LA UNIVERSIDAD PARA MAYORES DE 25 Y 45 AÑOS en los últimos años son los siguientes:\n" + 
    			"-Curso 2019 - 2020: 68 matriculados.\n" + 
    			"-Curso 2018 - 2019: 88 matriculados.\n" + 
    			"-Curso 2017 - 2018: 86 matriculados.\n" + 
    			"-Curso 2016 - 2017: 94 matriculados.\n" + 
    			"-Curso 2015 - 2016: 104 matriculados.\n"));
    	conversacion.procesarTextoRecibido("¿Cuantas personas se matriculan de fundamentos de psicobiología");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("El número de matriculados en la asignatura FUNDAMENTOS DE PSICOBIOLOGÍA de la titulación GRADO EN PSICOLOGÍA en los últimos años son los siguientes:\n" + 
    			"-Curso 2019 - 2020: 7934 matriculados.\n" + 
    			"-Curso 2018 - 2019: 7233 matriculados.\n" + 
    			"-Curso 2017 - 2018: 7002 matriculados.\n" + 
    			"-Curso 2016 - 2017: 7281 matriculados.\n" + 
    			"-Curso 2015 - 2016: 8302 matriculados.\n"));
    	conversacion.procesarTextoRecibido("¿Cuanta gente se matricula de fundamentos de psicobiología en el grado en psicología?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("El número de matriculados en la asignatura FUNDAMENTOS DE PSICOBIOLOGÍA de la titulación GRADO EN PSICOLOGÍA en los últimos años son los siguientes:\n" + 
    			"-Curso 2019 - 2020: 7934 matriculados.\n" + 
    			"-Curso 2018 - 2019: 7233 matriculados.\n" + 
    			"-Curso 2017 - 2018: 7002 matriculados.\n" + 
    			"-Curso 2016 - 2017: 7281 matriculados.\n" + 
    			"-Curso 2015 - 2016: 8302 matriculados.\n"));
    	conversacion.procesarTextoRecibido("¿Cuanta gente se matricula en grado en ingeniería informática, grado en psicología, fundamentos de psicobiología en el grado en ingeniería informática?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Si desea información sobre el número de matriculados de una determinada titulación o asignatura, debe nombrar sólo la titulación y la asignatura sobre las que quiere esa información.\n"));
    	conversacion.procesarTextoRecibido("¿Cuanta gente se matricula de fundamentos de psicobiología en el grado en ingeniería informática?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("La asignatura FUNDAMENTOS DE PSICOBIOLOGÍA no forma parte de la titulación GRADO EN INGENIERÍA INFORMÁTICA.\n" + 
    			"Por favor, intente expresar su consulta de otra forma, prestando especial atención al nombre de los estudios de los cuales desea información.\n"));
	}
	
	@Test
    public void testConversacionCallBack(){		
    	Conversacion conversacion = new Conversacion("123456789", "Telegram");
    	conversacion.procesarTextoRecibido("Cuanta gente se matricula de programacion y estructuras de datos avanzadas grado en ingeniería informática");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("El número de matriculados en la asignatura PROGRAMACIÓN Y ESTRUCTURAS DE DATOS AVANZADAS de la titulación GRADO EN INGENIERÍA INFORMÁTICA en los últimos años son los siguientes:\n" + 
    			"-Curso 2019 - 2020: 289 matriculados.\n" + 
    			"-Curso 2018 - 2019: 273 matriculados.\n" + 
    			"-Curso 2017 - 2018: 275 matriculados.\n" + 
    			"-Curso 2016 - 2017: 260 matriculados.\n" + 
    			"-Curso 2015 - 2016: 323 matriculados.\n")); 
    	conversacion.procesarTextoRecibido("Cuanta gente se matricula de estrategias de programacion y estructuras de datos");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("GRADO EN INGENIERÍA INFORMÁTICA\n" + 
    			"GRADO EN INGENIERÍA EN TECNOLOGÍAS DE LA INFORMACIÓN\n" + 
    			"Mi consulta no estaba relacionada con eso\n")); 
    	conversacion.procesarTextoRecibido("Cuanta gente se matricula de estrategias de programacion y estructuras de datos");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("GRADO EN INGENIERÍA INFORMÁTICA\n" + 
    			"GRADO EN INGENIERÍA EN TECNOLOGÍAS DE LA INFORMACIÓN\n" + 
    			"Mi consulta no estaba relacionada con eso\n")); 
    	conversacion.procesarTextoRecibido("Mi consulta no estaba relacionada con eso");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Siento no haberle entendido correctamente, ¿Podría repetir su consulta?\n")); 
    	conversacion.procesarTextoRecibido("Cuanta gente se matricula de estrategias de programacion y estructuras de datos");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("GRADO EN INGENIERÍA INFORMÁTICA\n" + 
    			"GRADO EN INGENIERÍA EN TECNOLOGÍAS DE LA INFORMACIÓN\n" + 
    			"Mi consulta no estaba relacionada con eso\n")); 
    	conversacion.procesarTextoRecibido("GRADO EN INGENIERÍA INFORMÁTICA");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("El número de matriculados en la asignatura ESTRATEGIAS DE PROGRAMACIÓN Y ESTRUCTURAS DE DATOS de la titulación GRADO EN INGENIERÍA INFORMÁTICA en los últimos años son los siguientes:\n" + 
    			"-Curso 2019 - 2020: 856 matriculados.\n" + 
    			"-Curso 2018 - 2019: 842 matriculados.\n" + 
    			"-Curso 2017 - 2018: 782 matriculados.\n" + 
    			"-Curso 2016 - 2017: 837 matriculados.\n" + 
    			"-Curso 2015 - 2016: 942 matriculados.\n")); 
	}
	
}
