package es.infouned.estructurasDeDatos;

import java.util.Comparator;

/**
* 
* Clase que implementa un montículo de mínimos.
* Se ha construído a partir del pseudocódigo del libro base, por lo tanto cualquier información sobre su funcionamiento puede consularse en él.
* @author Alberto Martínez Montenegro
*/
public class MonticuloDeMinimos<T>{
	private Node<T>[] nodos;
	private int numeroDeNodos;
	private int numeroMaximoDeNodos;
	private Comparator<T> comparador;
	
	@SuppressWarnings("unchecked")
	public MonticuloDeMinimos(int numeroMaximoDeNodos, Comparator<T> comparador) {
		this.numeroDeNodos = 0;
		this.numeroMaximoDeNodos = numeroMaximoDeNodos + 1;
		this.nodos = new Node[numeroMaximoDeNodos];
		this.comparador = comparador;
	}
	
	@SuppressWarnings("unchecked")
	public MonticuloDeMinimos(int numeroMaximoDeNodos, Comparator<T> comparador, T[] vectorElementos) {
		this.numeroDeNodos = 0;
		this.numeroMaximoDeNodos = numeroMaximoDeNodos + 1;
		this.nodos = new Node[numeroMaximoDeNodos];
		this.comparador = comparador;
		for(T elemento: vectorElementos) { //No conseguí implementarlo de una forma más eficiente, pero según el libro "PROGRAMACIÓN Y ESTRUCTURAS DE DATOS AVANZADAS se puede implementar con coste O(n) usando hundir()"
			insertar(elemento);
		}
	}
	
	@SuppressWarnings("unchecked")
	public MonticuloDeMinimos(int numeroMaximoDeNodos, Comparator<T> comparador, Iterable<T> vectorElementos) {
		this.numeroDeNodos = 0;
		this.numeroMaximoDeNodos = numeroMaximoDeNodos + 1;
		this.nodos = new Node[numeroMaximoDeNodos];
		this.comparador = comparador;
		for(T elemento: vectorElementos) { //No conseguí implementarlo de una forma más eficiente, pero según el libro "PROGRAMACIÓN Y ESTRUCTURAS DE DATOS AVANZADAS se puede implementar con coste O(n) usando hundir()"
			insertar(elemento);
		}
	}
	
	public boolean esVacio() {
		if(numeroDeNodos==0) return true;
		return false;
	}
	
	public T consultarCima() {
		if(numeroDeNodos==0) return null;
		return nodos[1].getValue();
	}
	
	public T extreaerCima() {
		if(numeroDeNodos!=0) {
			Node<T> nodo = nodos[1];
			nodos[1] = nodos[numeroDeNodos];
			numeroDeNodos--;
			hundir(1);
			return nodo.getValue();
		}
		else {
			return null;
		}
	}
	
	public void insertar(T objeto) {
		Node<T> e = new Node<T>(objeto);
		if(numeroDeNodos == numeroMaximoDeNodos) {
			System.out.println(numeroDeNodos);
			System.out.println("Error de desbordamiento al ejecutar el proceso");
			System.exit(0);
		}
		else {
			numeroDeNodos++;
			nodos[numeroDeNodos] = e;
			flotar(numeroDeNodos);		
		}
	}
	
	private void flotar(int i) {
		int padre = i/2;
		while(i > 1 && comparador.compare(nodos[padre].getValue(), nodos[i].getValue()) >= 0) {
			intercambiar(padre, i);
			i=padre;
			padre=i/2;
		}
	}
	
	private void hundir(int i){
		int hi;
		int hd;
		int p;
		do{
			hi=2*i;
			hd=2*i+1;
			p=i;
			if(hd<=numeroDeNodos) {
				if(comparador.compare(nodos[hd].getValue(), nodos[i].getValue()) <= 0) {
					i = hd;	
				}
			}
			if(hi<=numeroDeNodos ) {
				if(comparador.compare(nodos[hi].getValue(), nodos[i].getValue()) <= 0) {
					i = hi;
				}
			}
			intercambiar(p, i);
		}while(p!=i);
	}
	
	private void intercambiar(int indiceNodoA, int indiceNodoB) {
		Node<T> nodo = nodos[indiceNodoA];
		nodos[indiceNodoA] = nodos[indiceNodoB];
		nodos[indiceNodoB] = nodo;
	}
}