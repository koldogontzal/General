package shell;


import java.util.Scanner;
import java.util.StringTokenizer;

/**
* Clase que implementa a shell de comandos.
*/
public class Shell {
	String [] comandos;
	int [] numParametros;
	String [] descripcion;
	
/**
* Constructor, se le pasan varios parámetros para describir los comandos de la shell. El último de los comandos ha de ser el de ayuda y no tener parámetros.
* @param comandos Array de String con los comandos aceptados por la Shell
* @param numParametros Array de int con el número de parámetros que acepta cada uno de los comandos anteriores.
* @param descripcion Array de String con una descripción d elo sque hace cada comando.
*/
	public Shell (String [] comandos, int [] numParametros, String [] descripcion) {
		this.comandos = comandos;
		this.numParametros = numParametros;
		this.descripcion = descripcion;
	}
	
/**
* Método que muestra en pantalla el menú de comandos
*/
	public void muestraComandos () {
		System.out.println();
		System.out.println("Ayuda de Sudoku: Listado de comandos:");
		System.out.println("-------------------------------------");
		//System.out.println();
		System.out.println("\tCOMANDO\t#PARAM.\tDESCRIPCION");
		
		for (int i=0; i<comandos.length; i++) {
			System.out.print("\t  " + comandos[i] + "\t  ");
			if (numParametros[i] != 0) {
				System.out.print(numParametros[i]);
			} else {
				System.out.print("-");			
			}
			System.out.println(	"\t" + descripcion[i]);
		}
		System.out.println();
	}
/**
* Método que devuelve una array de enteros con el comando y los parametros metidos por teclado
* @return int[] Array de enteros, cuya primera posición es el comando escrito por el judaor y si tiene parametros,  estos van en las siguientes posiciones del array
*/
	public int [] leeComando () {
		Scanner s = new Scanner(System.in).useDelimiter("\r\n");
		int [] devolver = null;
		
		System.out.print("> ");
		StringTokenizer lectura = new StringTokenizer (s.next(), ", ()=");
		
		if (lectura.hasMoreTokens()) {
			String comando = lectura.nextToken();
			for (int i=0; i<comandos.length; i++) {
				if (comando.compareTo(comandos[i]) == 0) {
					devolver = new int [numParametros[i] + 1];
					devolver[0] = i;
				}
			}
			if (devolver == null) {
				devolver = new int [1];
				devolver[0] = comandos.length-1;
			}
		} else {
			devolver = new int [1];
			devolver[0] = comandos.length-1;
		}
		
		// Lee los parametros, que han de ser siempre enteros entre 1 y 9, ambos inclusive
		for (int i=1; i<devolver.length; i++) {
			if (lectura.hasMoreTokens()) {
				devolver[i] = 1 + ((Integer.parseInt(lectura.nextToken()) - 1) % 9) ;			
			} else {
				// Numero de parametros erroneo
				devolver = new int [1];
				devolver[0] = comandos.length-1;				
			}
		}		
		return devolver;
	}
	
}