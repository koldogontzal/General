package utils.tratimagen.filtros;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import utils.ColorMod;

import com.jhlabs.image.PointFilter;

public class HighlightColorFilter extends PointFilter {
	
	private float hueCentral;
	private float amplitud;
	
	private float hueMinCero;
	private float hueMinUno;
	private float hueMaxUno;
	private float hueMaxCero;
	
	private static final float porcentajePendiente = 0.70f;
	private float anchoPendienteHue;
	
	public HighlightColorFilter(float hueCentral, float amplitud) {
		// Filtro que dependiendo del HUE del color del pixel dado, modifica la saturación
		// para filtrar todos los colores menos los cercanos al HUE, según un factor
		// dado por amplitud
		
		this.hueCentral = hueCentral % 360; // HUE del color central que se mantendrá igual
		this.amplitud = Math.max(0f, Math.min(amplitud, 1f)); // Amplitud de la variación de color (HUE) permitida en % (entre 0.0 y 1.0)
		
		this.hueMinCero = this.hueCentral - 360f * this.amplitud * (0.5f + porcentajePendiente / 2f) ;
		this.hueMinUno = this.hueCentral - 360f * this.amplitud * (0.5f - porcentajePendiente / 2f);
		this.hueMaxUno = this.hueCentral + 360f * this.amplitud * (0.5f - porcentajePendiente / 2f);
		this.hueMaxCero = this.hueCentral + 360f * this.amplitud * (0.5f + porcentajePendiente / 2f);
		
		this.anchoPendienteHue = this.hueMinUno - this.hueMinCero;
/*		
System.out.println(hueCentral + " " + amplitud);
System.out.println(hueMinCero + " " + hueMinUno + " " + hueMaxUno + " " + hueMaxCero);
System.out.println(anchoPendienteHue);
BufferedImage ft = this.getImgFuncionTransferenciaHue();
try {
	ImageIO.write(ft, "PNG", new File("C:\\Documents and Settings\\lcastellano\\Escritorio\\2012_04_28, La Virgen de la Cabeza\\FuncTransf.png"));
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
*/

		
		canFilterIndexColorModel = false;
		
	}

	@Override
	public int filterRGB(int x, int y, int rgb) {
		ColorMod c = new ColorMod(rgb);
		float hue = c.getHue();
		float val = c.getValue();
		float sat = c.getSaturation() * this.funcTransferSat(hue);
		
		ColorMod ret = ColorMod.getColorFromHSV(hue, sat, val);
			
		return ret.getARGB();
	}
	
	private float funcTransferSat(float hue) {
		// Hue varía entre 0 y 360. Como es periódico, hay que mirar también en hue-360 y en hue+360
		// La función de transferencia devuelve valores entre 0.0 y 1.0

/* 
 System.out.println(this.funcTransferSatSencilla(hue - 360)
 
						+ " " + this.funcTransferSatSencilla(hue)
						+ " " +this.funcTransferSatSencilla(hue + 360));		
*/		
		return Math.min(
				this.funcTransferSatSencilla(hue - 360f)
						+ this.funcTransferSatSencilla(hue)
						+ this.funcTransferSatSencilla(hue + 360f), 1f);
	}
	
	private float funcTransferSatSencilla(float hue) {
		if ((hue <= this.hueMinCero) || (hue >= this.hueMaxCero)) {
			return 0f;
		} else if ((hue >= this.hueMinUno) && (hue <= this.hueMaxUno)) {
			return 1f;
		} else if (hue < this.hueMinUno) {
			return (hue - this.hueMinCero) / this.anchoPendienteHue;
		} else {
			return (1f - (hue - this.hueMaxUno) / this.anchoPendienteHue);
		}
	}
	

	public BufferedImage getImgFuncionTransferenciaHue() {		
		BufferedImage ret = new BufferedImage(360, 120, BufferedImage.TYPE_INT_ARGB);
		
		// Rellena el fondo blanco
		Graphics2D g = ret.createGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, 360, 120);
		
		//Dibuja una barra para cada valor de HUE
		g.setColor(Color.black);
		for (int f = 0; f < 360; f++) {
			g.drawLine(f, 120, f, 20 + (int)(100f * (1f - this.funcTransferSat(f))));

		}
		g.dispose();
		
		return ret;
		
	}
	
	/*
	public static void main(String[] args) throws IOException {
		BufferedImage im = ImageIO.read(new File("C:\\Documents and Settings\\lcastellano\\Escritorio\\2012_04_28, La Virgen de la Cabeza\\20120430150342_IMG_0701.JPG"));
		HighlightColorFilter filtro = new HighlightColorFilter(335, 0.2f);
		BufferedImage im2 = filtro.filter(im, null);
		ImageIO.write(im2, "PNG", new File("C:\\Documents and Settings\\lcastellano\\Escritorio\\2012_04_28, La Virgen de la Cabeza\\Salida.png"));
	}
	*/
}
