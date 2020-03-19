package es.infouned.solicitudesInformacionBBDD;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Stack;
import java.util.Map.Entry;

import es.infouned.baseDeDatos.ConexionBaseDeDatos;
import es.infouned.baseDeDatos.ConexionMySQL;
import es.infouned.baseDeDatos.InstruccionSelect;
import es.infouned.principal.Configuracion;

public abstract class SolicitudInformacion {
	protected ConexionBaseDeDatos conexionBaseDeDatos;
	protected InstruccionSelect instruccionSelect;
	protected HashMap<String, String> sustitucionesConsultaSQL;
	
	protected SolicitudInformacion(){
		conexionBaseDeDatos = new ConexionMySQL();
		instruccionSelect = new InstruccionSelect(conexionBaseDeDatos);
		sustitucionesConsultaSQL = new HashMap<String, String>();
	}
	
	protected ResultSet generarResultSetConsultaSQL(String identificadorConsulta) {
		String cadenaTextoConsultaSQL = Configuracion.obtenerConsultaSQL(identificadorConsulta);
    	for(Entry<String, String> entradaDelHashMap : sustitucionesConsultaSQL.entrySet()) {
    		cadenaTextoConsultaSQL = cadenaTextoConsultaSQL.replaceAll(entradaDelHashMap.getKey(), entradaDelHashMap.getValue());
    	}
    	System.out.println(cadenaTextoConsultaSQL);
		instruccionSelect.ejecutarConsulta(cadenaTextoConsultaSQL);
		ResultSet resultSet = instruccionSelect.getResultSet();
		return resultSet;
	}
	
	protected String transcribirTextoCriteriosASQL(Stack<String> criteriosConsultaSQL) {
		if(!criteriosConsultaSQL.isEmpty()) {
			String cadenaTextoCriteriosConsultaSQL = "AND ";
			for(String elementoPila : criteriosConsultaSQL) {
	    		cadenaTextoCriteriosConsultaSQL = cadenaTextoCriteriosConsultaSQL + elementoPila + " AND ";
	    	}
			String cadenaTextoCriteriosConsultaSQLSinUltimosCincoCaracteres = cadenaTextoCriteriosConsultaSQL.substring(0, cadenaTextoCriteriosConsultaSQL.length() - 5);
			return cadenaTextoCriteriosConsultaSQLSinUltimosCincoCaracteres;
		}else{
			return "";
		}
	}
	
	protected String transcribirTextoOrdenamientoASQL(String ordenamiento) {
		if (ordenamiento.equals("mayores")) {
			return "DESC";
		}else{
			return "ASC";
		}
	}
	
	public abstract String generarCadenaRespuesta(String saltoDeLinea);
}
