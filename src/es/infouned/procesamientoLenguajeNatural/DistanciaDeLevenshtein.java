package es.infouned.procesamientoLenguajeNatural;

import java.util.Arrays;
import java.util.Stack;
import java.util.stream.IntStream;

/**
 * Clase diseñada para ofrecer el método estático 'calcularDistanciaDeLevenshtein', que nos devolverá un número entero
 * que simboliza la diferencia entre las dos cádenas de caracteres que se le pasen como parámetro.
 * @author Alberto Martínez Montenegro
 *
 */
public class DistanciaDeLevenshtein {
	
	/**
	 * Este método devuelve un número entero que simboliza la diferencia entre las dos cádenas de caracteres que se le pasen como parámetro.
	 * @param primerString
	 * @param segundoString
	 * @return
	 */
	public static int calcularDistanciaDeLevenshtein(String primerString, String segundoString) {
	    int[][] matrizDeCaracteres = new int[primerString.length() + 1][segundoString.length() + 1];
	 
	    for (int numeroFilaMatriz = 0; numeroFilaMatriz <= primerString.length(); numeroFilaMatriz++) {
	        for (int numeroColumnaMatriz = 0; numeroColumnaMatriz <= segundoString.length(); numeroColumnaMatriz++) {
	        	
	            if (numeroFilaMatriz == 0) {
	                matrizDeCaracteres[numeroFilaMatriz][numeroColumnaMatriz] = numeroColumnaMatriz;
	            }
	            else if (numeroColumnaMatriz == 0) {
	                matrizDeCaracteres[numeroFilaMatriz][numeroColumnaMatriz] = numeroFilaMatriz;
	            }
	            else {
	                matrizDeCaracteres[numeroFilaMatriz][numeroColumnaMatriz] = 
                		minimo(
							matrizDeCaracteres[numeroFilaMatriz - 1][numeroColumnaMatriz - 1] + costeDeSustitucion(primerString.charAt(numeroFilaMatriz - 1),
							segundoString.charAt(numeroColumnaMatriz - 1)),
	                		matrizDeCaracteres[numeroFilaMatriz - 1][numeroColumnaMatriz] + 1,
	                		matrizDeCaracteres[numeroFilaMatriz][numeroColumnaMatriz - 1] + 1
		                );
	            }
	        }
	    }
	    return matrizDeCaracteres[primerString.length()][segundoString.length()];
	}
	
    private static int costeDeSustitucion(char primerCaracterComparacion, char segundoCaracterComparacion) {
    	if (primerCaracterComparacion == segundoCaracterComparacion) {
    		return 0;
    	}
    	else {
    		return 1;
    	}
    }
    
    private static int minimo(int... numerosEnteros) {
    	IntStream streamNumerosEnteros = Arrays.stream(numerosEnteros);
    	int numeroEnteroMinimo = streamNumerosEnteros.min().orElse(Integer.MAX_VALUE);
        return numeroEnteroMinimo;
    }
    
    public static Stack<String> construirVariantes(String palabraOriginal){
    	Stack<String> variantes = new Stack<String>();
    	String alfabeto = obtenerAlfabeto() ;
    	
    	//Eliminación de caracteres
    	for (int indice = 0; indice < palabraOriginal.length(); indice++) {
    		StringBuilder variante = new StringBuilder(palabraOriginal);
    		variante.deleteCharAt(indice);
    		variantes.push(variante.toString());
    	}
    	
    	//Sustitución de caracteres
    	for (int indice = 0; indice < palabraOriginal.length(); indice++) {
    		for (int indiceAlfabeto = 0; indiceAlfabeto < alfabeto.length(); indiceAlfabeto++) {
	    		StringBuilder variante = new StringBuilder(palabraOriginal);
	    		char caracter = alfabeto.charAt(indiceAlfabeto);
	    		variante.setCharAt(indice, caracter);
	    		variantes.push(variante.toString());
    		}
    	}
    	
    	//Inserción de caracteres
    	for (int indice = 0; indice <= palabraOriginal.length(); indice++) {
    		for (int indiceAlfabeto = 0; indiceAlfabeto < alfabeto.length(); indiceAlfabeto++) {
	    		StringBuilder variante = new StringBuilder(palabraOriginal);
	    		char caracter = alfabeto.charAt(indiceAlfabeto);
	    		variante.insert(indice, caracter);
	    		variantes.push(variante.toString());
    		}
    	}
    	return variantes;
    	
    }
    
    private static String obtenerAlfabeto() {
    	StringBuilder alfabeto = new StringBuilder();
    	for (char c = 'a'; c <= 'z'; c++) {
    		alfabeto.append(c);
        }
    	return alfabeto.toString();
    }

}
