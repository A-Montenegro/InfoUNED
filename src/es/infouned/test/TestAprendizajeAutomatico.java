package es.infouned.test;

import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import es.infouned.aprendizajeAutomatico.Clasificador;
import es.infouned.principal.Configuracion;

public class TestAprendizajeAutomatico{

	@BeforeAll
	private static void inicializarPropiedades() {
		String rutaFicheroConfiguracion = "/config.properties";
		Configuracion.establecerPropiedadesConfiguracionAPartirDeFichero(rutaFicheroConfiguracion);
	}
	
	@Test
    public void testNaiveBayes() throws Exception{		
    	String rutaFicheroModeloAprendizajeAutomaticoEntrenado = Configuracion.getPropiedad("rutaFicheroModeloAprendizajeAutomaticoEntrenado");
    	String rutaFicheroEstructuraDataSet = Configuracion.getPropiedad("rutaFicheroEstructuraDataSet");
    	Configuracion.establecerClasificador("naiveBayes", rutaFicheroModeloAprendizajeAutomaticoEntrenado, rutaFicheroEstructuraDataSet);
		Clasificador clasificadorNaiveBayes = Configuracion.getClasificador();
		String clasificacionInstancia = clasificadorNaiveBayes.clasificarInstancia("Cuanto cuesta estudiar ingenería informática");
		assertTrue(clasificacionInstancia.contentEquals("solicitudPreciosTitulacion"));
	}
	
}