package listaCircular;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ListaCircular<String> lista =new ListaCircular<String> (14);
		
		String kk = "Hola";
		for (int i = 0; i < 20; i++) {
			lista.addClone(kk + i);
		}
		
		System.out.println("Esto da: " + (-56 % 9));
		
		for (int i=0; i<20; i++) {
			System.out.println(lista.actual());
			lista.anterior();
			
		}
		
		for (int i = 0; i < 10; i++) {
			lista.addClone(kk + i + " 2a. vuelta");
		}
		
		System.out.println();
		
		for (int i=0; i<20; i++) {
			System.out.println(lista.actual());
			lista.anterior();
			
		}
		
		System.out.println();
		
		for (int i=0; i<20; i++) {
			System.out.println(lista.siguiente());
		
		}
	}
}
