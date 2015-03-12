package utils.tratimagen.utils.geometria;

import java.awt.image.BufferedImage;

public class Punto {

	private int x;
	private int y;

	public Punto(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}

	public float distanceTo(Punto p) {
		int varX = x - p.x;
		int varY = y - p.y;
		return (float) (Math.sqrt(varX * varX + varY * varY));
	}
	
	@Override
	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}
	
	public void dibujaCruz(BufferedImage img) {
		for (int i = Math.max(0, x - 3); i <= Math.min(x + 3,
				img.getWidth() - 1); i++) {
			int color = img.getRGB(i, y);
			color = color ^ 0xFFFFFF;
			img.setRGB(i, y, color);
		}
		for (int j = Math.max(0, y - 3); j <= Math.min(y + 3,
				img.getHeight() - 1); j++) {
			if (j != y) {
				int color = img.getRGB(x, j);
				color = color ^ 0xFFFFFF;
				img.setRGB(x, j, color);
			}
		}
	}
	
	public static Punto max(Punto p1, Punto p2) {
		return new Punto(Math.max(p1.getX(), p2.getX()), Math.max(p1.getY(), p2.getY()));
	}

	public static Punto min(Punto p1, Punto p2) {
		return new Punto(Math.min(p1.getX(), p2.getX()), Math.min(p1.getY(), p2.getY()));
	}

}
