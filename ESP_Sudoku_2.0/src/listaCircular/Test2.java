package listaCircular;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Test2 {
	public static void main(String[] args) {
		List<String> lista = new ArrayList<String>(15);

		for (int i = 0; i < 14; i++) {
			lista.add("Numero " + (i + 1));
		}

		for (int i = 5; i < 10; i++) {
			lista.remove("Numero " + (i + 1));
		}
		
		for (int i = 5; i < 10; i++) {
			lista.add("Final");
		}
		
		for (int i = 10; i < 13; i++) {
			lista.remove("Numero " + (i + 1));
		}

		for (int i = 10; i < 13; i++) {
			lista.add("Final");
		}

		mostrarLista(lista);
	}

	private static void mostrarLista(List<String> lista) {
		Iterator it = lista.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}
}
