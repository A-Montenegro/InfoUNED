package es.infouned.utilidades;


import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Quicksort<T> {
	private Comparator<T> comparador;
	private Random rand;
	
	public Quicksort(Comparator<T> comparador){
		rand = new Random();
		this.comparador = comparador;
	}
	
	public void ordenar(T vectorElementos[]) {
		ordenarRecursivo(vectorElementos, 0, vectorElementos.length-1);
	}

	public void ordenar(List<T> listaElementos) {
		@SuppressWarnings("unchecked")
		T[] vectorElementos = listaElementos.toArray((T[]) new Object[listaElementos.size()]);
		ordenarRecursivo(vectorElementos, 0, vectorElementos.length-1);
		listaElementos.clear();
		listaElementos.addAll((List<T>) Arrays.asList(vectorElementos));
	}
	
	private void ordenarRecursivo(T[] vectorElementos, int principio, int fin) {
	    if (principio < fin) {
	        int indicePivote = principio + rand.nextInt(fin - principio + 1);
	        intercambiar(vectorElementos, indicePivote, fin);
	        int indiceParticion = pivotar(vectorElementos, principio, fin);
	        ordenarRecursivo(vectorElementos, principio, indiceParticion-1);
	        ordenarRecursivo(vectorElementos, indiceParticion+1, fin);
	    }
	}
	
	private int pivotar(T vectorElementos[], int principio, int fin) {
	    T pivot = vectorElementos[fin];
	    int i = (principio-1);
	    for (int j = principio; j < fin; j++) {
	        if (comparador.compare(vectorElementos[j], pivot) < 1){
	            i++;
	            T swapTemp = vectorElementos[i];
	            vectorElementos[i] = vectorElementos[j];
	            vectorElementos[j] = swapTemp;
	        }
	    }
	    intercambiar(vectorElementos, i+1, fin);
	    return i+1;
	}
	
    private void intercambiar(T vectorElementos[], int elemento1, int elemento2) 
    {
        T temporal = vectorElementos[elemento1];
        vectorElementos[elemento1] = vectorElementos[elemento2];
        vectorElementos[elemento2] = temporal;
    }
}
