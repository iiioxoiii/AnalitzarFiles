package analitzar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *   _ _ _                _ _ _ 
 *  (_|_|_) _____  _____ (_|_|_)
 *  | | | |/ _ \ \/ / _ \| | | |
 *  | | | | (_) >  < (_) | | | |
 *  |_|_|_|\___/_/\_\___/|_|_|_|
 *  
 * L'script crea un arxiu de text al lloc on s'executa
 * i mostra arxius filtrats per extensions png,doc i jpg 
 * d'un directori concret. El directori es passa com argument
 * en l'execució de l'scrip.
 * 
 * ex: >java analitzar.AnalitzarFiles /home/paco/documents/
 * 
 * */


public class AnalitzarFiles {
	
	
	//Array de caracters que serveixen per la funció de mapeig de 
	//pes del arxius amb nom getAsString
	private static final String[] Q = new String[]{"", "K", "M", "G", "T", "P", "E"};


	public static void main(String [] args) throws IOException {
		
		File rr = new File(args[0]);
		llistaFilesFiltrats(rr);
	}
	
	
	public static void llistaFilesFiltrats(File ruta) throws IOException {
		
		//Creació de l'arxiu on s'escriu el llistat d'arxius filtrats
		File f = new File("continguts.txt");
		
		//Es crea la instancia per manipular fitxers i el buffer.
		FileWriter fff = new FileWriter(f.getName());
		BufferedWriter wr = new BufferedWriter(fff);
		
		//Validació si la ruta es un directori
        if (ruta.isDirectory()) {
        	
        	//Es realitza un bucle per cada element del directori
        	for (File element : ruta.listFiles()) {
        		
        		//Si es un directori
    	        if (element.isDirectory()) {
    	        
    	        //Si es un fitxer	
    	        } else {
    	        	
    	        	//s'avalua el filtre
    	        	 FileNameExtensionFilter filter = new FileNameExtensionFilter("primerFiltre", "doc", "png","jpg");
    	        	
    	        	 //si el filtre es acceptat
    	        	 if (filter.accept(element)) {
    	        		
    	        		//Es demana el nom del fitxer...
    	        		String nom = element.getName();
    	        		
    	        		//Es demanen els segons passats de la darrera modificació
    	        		long tt = element.lastModified();
    	        		//S'instancien en un objecte per poder-los manipular
    	        		Date d = new Date(tt);
    	        		//i es formata la instancia en un altre string
    	        		String dataModificacio = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(d);
    	        		
    	        		//Es demana el pes del fitxer i formata amb la funció getAsString
    	        		String pesFitxer = getAsString(element.length());
    	        		
    	        		//S'escriu la línia amb salt de línia al final 
    	                wr.write(nom+" "+pesFitxer+" "+dataModificacio+"\n");
    	        		
    	        	} 	        
    	        }
    	    }
        }
		
        //es tanca el buffer
	    wr.close();
	}
	
	
	public static String getAsString(long bb) {
		for (int i = 6; i > 0; i--){
	        double step = Math.pow(1024, i);
	        if (bb > step) {
	        	return String.format("%3.1f %s", bb / step, Q[i]);
	        }
	    }
	    return Long.toString(bb);
	}

}
