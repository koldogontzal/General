package prueba;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.mortennobel.imagescaling.ResampleOp;

public class Resize {

	public static void main(String[] args) throws IOException {
		File archivo = new File("D:\\ImágenesPersonales\\2013-01-11 10.52.03.jpg");
		BufferedImage tomato = ImageIO.read(archivo);
		ResampleOp resampleOp = new ResampleOp(500, 333);
		BufferedImage rescaledTomato = resampleOp.filter(tomato, null);
		ImageIO.write(rescaledTomato, "PNG", new File("D:\\ImágenesPersonales\\Reduccion.png"));
		
	}
}
