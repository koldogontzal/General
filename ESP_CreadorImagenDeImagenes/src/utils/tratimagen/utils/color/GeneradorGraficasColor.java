package utils.tratimagen.utils.color;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import utils.ColorMod;

public class GeneradorGraficasColor {
	
	public static void main(String[] args) {
		
		BufferedImage img;
		
		for (int s = 0; s <= 100; s = s + 25) {
			for (int v = 0; v <= 100; v = v + 25) {
				img = new BufferedImage(360, 255, BufferedImage.TYPE_INT_BGR);
				Graphics2D g = img.createGraphics();
				g.setBackground(ColorMod.white);
				g.setColor(ColorMod.white);
				g.fillRect(0, 0, 360, 255);
				for (int h = 0; h < 360; h++) {
					ColorMod c = ColorMod.getColorFromHSV(h, s, v);
					int lum = c.getLuminance();
					g.setColor(c);
					g.drawLine(h, 255, h, 255 - lum);
				}
				g.dispose();
				String sS = "000" + s;
				sS = sS.substring(sS.length() - 3);
				String sV = "000" + v;
				sV = sV.substring(sV.length() - 3);
				File fichero = new File("C:\\Documents and Settings\\lcastellano\\Escritorio\\PruebasColor" +
						"\\S" + sS + "V" + sV + ".png");
				try {
					ImageIO.write(img, "PNG", fichero);
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("Creago fichero " + fichero.getPath());
			}
		}
		
	}

}
