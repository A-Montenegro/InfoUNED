package es.infouned.test;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import es.infouned.aprendizajeAutomatico.Clasificador;
import es.infouned.aprendizajeAutomatico.ClasificadorNaiveBayes;
import es.infouned.principal.Configuracion;
import es.infouned.utilidades.ProcesamientoDeTexto;

public class TestAprendizajeAutomatico{

	private static Clasificador clasificador;
	
	@BeforeAll
	private static void inicializarPropiedades() {
		String rutaFicheroConfiguracion = "/config.properties";
		Configuracion.establecerPropiedadesConfiguracionAPartirDeFichero(rutaFicheroConfiguracion);
		clasificador = Configuracion.getClasificador();
	}
	
	@AfterAll
	private static void eliminarConfiguracion() {
		Configuracion.eliminarTodo();
	}	
	
	@Test
    public void testNaiveBayes(){		
		assertTrue(clasificador instanceof ClasificadorNaiveBayes);
		assertTrue(esClasificacionCorrecta("Cuanta gente se matricula de","solicitudMatriculados"));
		assertTrue(esClasificacionCorrecta("cual es la asignatura mas dificil de cuarto curso de","solicitudEstadisticaRendimiento"));
		assertTrue(esClasificacionCorrecta("cual es el email para hablar con vosotros","informacionContacto"));
		assertTrue(esClasificacionCorrecta("como hay que hacer para hacer la matricula online","informacionMatricula"));
		assertTrue(esClasificacionCorrecta("la gente suele dejar la carrera","solicitudEstadisticaRendimiento"));
		assertTrue(esClasificacionCorrecta("los estudiantes de la asignatura estan satisfechos","solicitudEstadisticaRendimiento"));
		assertTrue(esClasificacionCorrecta("¿Cuanto cuestan las asignaturas del?","solicitudPreciosTitulacion"));
		assertTrue(esClasificacionCorrecta("¿Cual es la nota media de perteneciente al?","solicitudEstadisticaRendimiento"));
		assertTrue(esClasificacionCorrecta("¿Cuantos hacen la matrícula de?","solicitudMatriculados"));
		assertTrue(esClasificacionCorrecta("Necesito información general sobre","solicitudGuia"));
		assertTrue(esClasificacionCorrecta("los alumnos están descontentos con la titulación de", "solicitudEstadisticaRendimiento"));
	}
	
	private boolean esClasificacionCorrecta(String textoAClasificar, String clasificacionEsperada) {
		if (clasificador.clasificarInstancia(ProcesamientoDeTexto.normalizarTexto(textoAClasificar)).equals(clasificacionEsperada)) return true;
		return false;
	}
}