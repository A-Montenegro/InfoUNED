package es.infouned.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import es.infouned.estudios.CriterioConsultaSQL;
import es.infouned.estudios.IndicadorOrdenamiento;
import es.infouned.estudios.ParametroEstadistico;
import es.infouned.principal.Configuracion;

public class TestConfiguracion {

		@BeforeAll
		private static void inicializarPropiedades() {
			String rutaFicheroConfiguracion = "/config.properties";
			Configuracion.establecerPropiedadesConfiguracionAPartirDeFichero(rutaFicheroConfiguracion);
			//Configuracion.iniciarProcesadorLenguajeNatural();
		}
		
		@AfterAll
		private static void eliminarConfiguracion() {
			Configuracion.eliminarTodo();
		}	
		
		@Test
	    public void testConversacionBlabla(){		
	    	for(ParametroEstadistico parametroEstadistico: Configuracion.getParametrosEstadisticos()) {
	    		System.out.println(parametroEstadistico.getLiteral() + " " + parametroEstadistico.getNombre() + " " + parametroEstadistico.getTipoEstudio());
	    		for(String nomenclatura: parametroEstadistico.getNomenclaturas()) {
	    			System.out.println(nomenclatura);
	    		}
	    		System.out.println();
	    	}
	    	
	    	for(IndicadorOrdenamiento indicadorOrdenamiento: Configuracion.getIndicadoresOrdenamiento()) {
	    		System.out.println(indicadorOrdenamiento.getNombre());
	    		for(String nomenclatura: indicadorOrdenamiento.getNomenclaturas()) {
	    			System.out.println(nomenclatura);
	    		}
	    		System.out.println();
	    	}
	    	
	    	for(CriterioConsultaSQL criterioConsultaSQL: Configuracion.getCriteriosConsultaSQL()) {
	    		System.out.println(criterioConsultaSQL.getLiteral() + " " + criterioConsultaSQL.getNombre());
	    		for(String nomenclatura: criterioConsultaSQL.getNomenclaturas()) {
	    			System.out.println(nomenclatura);
	    		}
	    		System.out.println();
	    	}
		}
		
	
}
