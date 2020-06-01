package es.infouned.estructurasDeDatos;

/**
 * Clase que simboliza una pareja de objetos de cualquier clase.
 * @author Alberto Martínez Montenegro
 *
 */
public class Par <TipoObjeto1, TipoObjeto2> {
    private TipoObjeto1 objeto1;
    private TipoObjeto2 objeto2;
 
    public Par(TipoObjeto1 objeto1, TipoObjeto2 objeto2) {
    	this.objeto1 = objeto1;
    	this.objeto2 = objeto2;
    }
    
    public TipoObjeto1 getObjeto1() {
    	return objeto1;
    }
    
    public TipoObjeto2 getObjeto2() {
    	return objeto2;
    }
}