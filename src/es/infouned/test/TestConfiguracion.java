package es.infouned.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import es.infouned.estudios.CriterioConsultaSQL;
import es.infouned.estudios.IndicadorOrdenamiento;
import es.infouned.estudios.NivelEstudios;
import es.infouned.estudios.ParametroEstadistico;
import es.infouned.principal.Configuracion;

/**
 * Clase de pruebas unitarias destinadas a probar el correcto funcionamento de la clase Configuracion.
 * @author Alberto Martínez Montenegro
 * 
 */
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
			System.out.println("----------- Parámetros estadísticos cargados: -------------\n");
	    	for(ParametroEstadistico parametroEstadistico: Configuracion.getParametrosEstadisticos()) {
	    		System.out.println("Nombre: " + parametroEstadistico.getNombre() + "\nLiteral: " + parametroEstadistico.getLiteral() + "\nTipo de estudio: " + parametroEstadistico.getTipoEstudio()+ "\nNomenclarutas:" );
	    		for(String nomenclatura: parametroEstadistico.getNomenclaturas()) {
	    			System.out.println(nomenclatura);
	    		}
	    		System.out.println();
	    	}
	    	
	    	
	    	System.out.println("\n----------- Inidicadores de ordenamiento cargados: -------------\n");
	    	for(IndicadorOrdenamiento indicadorOrdenamiento: Configuracion.getIndicadoresOrdenamiento()) {
	    		System.out.println("Nombre: " + indicadorOrdenamiento.getNombre() + "\nNomenclarutas:" );
	    		for(String nomenclatura: indicadorOrdenamiento.getNomenclaturas()) {
	    			System.out.println(nomenclatura);
	    		}
	    		System.out.println();
	    	}
	    	
	    	System.out.println("\n----------- Niveles de estudios: -------------\n");
	    	for(NivelEstudios nivelEstudios: Configuracion.getNivelesEstudios()) {
	    		System.out.println("Nombre: " + nivelEstudios.getLiteral() + "\nNomenclarutas:" );
	    		for(String nomenclatura: nivelEstudios.getNomenclaturas()) {
	    			System.out.println(nomenclatura);
	    		}
	    		System.out.println();
	    	}
	    	
	    	System.out.println("\n----------- Criterios de consulta SQL cargados: -------------\n");
	    	for(CriterioConsultaSQL criterioConsultaSQL: Configuracion.getCriteriosConsultaSQL()) {
	    		System.out.println("Nombre: " + criterioConsultaSQL.getNombre() + "\nLiteral: " + criterioConsultaSQL.getLiteral() + "\nNomenclarutas: " );
	    		for(String nomenclatura: criterioConsultaSQL.getNomenclaturas()) {
	    			System.out.println(nomenclatura);
	    		}
	    		System.out.println();
	    	}
		}
		
	
}
