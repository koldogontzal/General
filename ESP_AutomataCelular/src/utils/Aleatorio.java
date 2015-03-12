package utils;

public class Aleatorio {
	
	public static int valor(int min, int max) {
		return (int)(min + Math.random() * (max - min + 1));
	}

}
