package utils.tratimagen.filtros;

import utils.ColorMod;

import com.jhlabs.image.PointFilter;

public class InvertirColorFilter extends PointFilter {
	
	public InvertirColorFilter() {
		super();
		canFilterIndexColorModel = false;
	}

	@Override
	public int filterRGB(int x, int y, int rgb) {
		ColorMod c = new ColorMod(rgb);
		float hue = c.getHue() + 180f;
		float val = c.getValue();
		float sat = c.getSaturation();
		
		ColorMod ret = ColorMod.getColorFromHSV(hue, sat, val);
			
		return ret.getARGB();
	}

}
