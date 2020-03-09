package es.infouned.procesamientoLenguajeNatural;

import java.util.Arrays;
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
    
}
