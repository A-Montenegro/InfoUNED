package es.infouned.test;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Comparator;
import org.junit.jupiter.api.Test;
import es.infouned.estructurasDeDatos.MonticuloDeMinimos;
import es.infouned.utilidades.Quicksort;

/**
 * Clase de pruebas unitarias destinadas a probar el correcto funcionamento del paquete de utilidades.
 * @author Alberto Martínez Montenegro
 *  
 */
public class TestUtilidades {
	
	@Test
	public void testMonticulos() {
		MonticuloDeMinimos<Integer> monticuloDeMinimos= new MonticuloDeMinimos<Integer>(5000, new IntComparator());
		monticuloDeMinimos.insertar(5);
		monticuloDeMinimos.insertar(17);
		monticuloDeMinimos.insertar(10);
		monticuloDeMinimos.insertar(84);
		monticuloDeMinimos.insertar(19);
		monticuloDeMinimos.insertar(3);
		monticuloDeMinimos.insertar(6);
		monticuloDeMinimos.insertar(22);
		monticuloDeMinimos.insertar(2);
		monticuloDeMinimos.insertar(9);
        assertTrue(monticuloDeMinimos.consultarCima() == 2); 
        Integer[] vectorElementos = {5,17,10,84,19,3,6,22,2,9};
        MonticuloDeMinimos<Integer> monticuloDeMinimosVector= new MonticuloDeMinimos<Integer>(5000, new IntComparator(), vectorElementos);
        assertTrue(monticuloDeMinimosVector.consultarCima() == 2);  
        ArrayList<Integer> listaElementos = new ArrayList<Integer>();
        listaElementos.add(5);
        listaElementos.add(17);
        listaElementos.add(10);
        listaElementos.add(84);
        listaElementos.add(5);
        listaElementos.add(5);
        listaElementos.add(5);
        listaElementos.add(2);
        listaElementos.add(5);
        listaElementos.add(-5);
        MonticuloDeMinimos<Integer> monticuloDeMinimosLista= new MonticuloDeMinimos<Integer>(5000, new IntComparator(), listaElementos);
        assertTrue(monticuloDeMinimosLista.consultarCima() == -5);
	}
	
	@Test
	public void testQuicksort() {
		Quicksort<Integer> quicksort = new Quicksort<Integer>(new IntComparator());
		Integer[] arr = {999,5,17,10,84,19,2,54,654,654,54,545,45,45,45,46,465,456,456,45,4,498,765,413,168,43,103,16,16,5163,18,1653,16,46,4321634,81,216,468,413521,3,6,22,2,9};
		quicksort.ordenar(arr);
		assertTrue(arr[9] == 16);
		ArrayList<Integer> listaElementos = new ArrayList<Integer>();
        listaElementos.add(5);
        listaElementos.add(17);
        listaElementos.add(10);
        listaElementos.add(84);
        listaElementos.add(5);
        listaElementos.add(5);
        listaElementos.add(5);
        listaElementos.add(2);
        listaElementos.add(5);
        listaElementos.add(-5);
        quicksort.ordenar(listaElementos);
        assertTrue(listaElementos.get(7) == 10);
	}
	
	class IntComparator implements Comparator<Integer> {
	     public int compare (Integer a, Integer b) {
	    	 return a < b ? -1 : a > b ? +1 : 0;
	     }
	}
}
