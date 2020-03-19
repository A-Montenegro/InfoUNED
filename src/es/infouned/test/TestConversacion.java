package es.infouned.test;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import es.infouned.conversacion.Conversacion;
import es.infouned.principal.Configuracion;

public class TestConversacion {

	@BeforeAll
	private static void inicializarPropiedades() {
		String rutaFicheroConfiguracion = "/config.properties";
		Configuracion.establecerPropiedadesConfiguracionAPartirDeFichero(rutaFicheroConfiguracion);
	}
	
	@Test
    public void testNaiveBayes() throws Exception{		
    	Conversacion conversacion = new Conversacion("123456789", "Telegram");
    	conversacion.procesarTextoRecibido("Hola que tal");
    	System.out.println(conversacion.obtenerRespuestaActual());
	}
	
}
