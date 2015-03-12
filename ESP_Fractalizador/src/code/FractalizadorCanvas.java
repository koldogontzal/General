package code;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import utils.Punto;
import utils.VectorFrac;

public class FractalizadorCanvas extends Canvas {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8293646469803305072L;

	private ArrayList<VectorFrac> lista;
	
	private double xMax;
	private double xMin;
	private double yMax;
	private double yMin;
	
	private int pantX;
	private int pantY;
	
	private Color fractalColor;
	private int ejes;
	
	public FractalizadorCanvas(Color fractalColor, ArrayList<VectorFrac> lista, Punto infIzq, Punto supDrch, int ejes) {
        this.fractalColor = fractalColor;
        this.ejes = ejes;
        this.lista = lista;
        
        this.xMax = supDrch.getRectangularesX();
        this.yMax = supDrch.getRectangularesY();
        this.xMin = infIzq.getRectangularesX();
        this.yMin = infIzq.getRectangularesY();
        
    }

    public Dimension getPreferredSize() {
        return new Dimension(1200, 940);
    }
    
    private int convX (double x) {
    	return (int)(this.pantX * (x - this.xMin) / (this.xMax - this.xMin));
    }
    private int convY (double y) {
    	return (int)(this.pantY * (this.yMax - y) / (this.yMax - this.yMin));
    }

   /*
    * Paint when the AWT tells us to...
    */
    public void paint(Graphics g) {
        // Calcula el tamaño de la pantalla en pixeles
        Dimension size = getSize();
        this.pantX = size.width;
        this.pantY = size.height;
        
        // Recalcula los limites de la rejilla para que las direcciones X e Y sean isótropas /en pixeles)
        double pixelsUnidadX = this.pantX / (this.xMax - this.xMin);
        double pixelsUnidadY = this.pantY / (this.yMax - this.yMin);
        
        if (pixelsUnidadX > pixelsUnidadY) {
        	double n = ((this.pantX / pixelsUnidadY) - this.xMax + this.xMin) / 2;
        	this.xMax = this.xMax + n;
        	this.xMin = this.xMin - n;
        } else {
        	double n = ((this.pantY / pixelsUnidadX) - this.yMax + this.yMin) / 2;
        	this.yMax = this.yMax + n;
        	this.yMin = this.yMin - n;
        }
        
        
        // Dibuja los ejes
		if (this.ejes != 0) {
			for (int i = -10; i < 10; i++) {
				if (i == 0) {
					g.setColor(Color.gray);
				} else {
					g.setColor(Color.cyan);
				}
				g.drawLine(this.convX(i), this.convY(-10), this.convX(i), this
						.convY(10));
				g.drawLine(this.convX(-10), this.convY(i), this.convX(10), this
						.convY(i));
			}
		}
        g.setColor(this.fractalColor);
        
        // Recorremos la lista de vectores y los vamos dibujando
        for (int i = 0; i < this.lista.size(); i++) {
        	VectorFrac vector = this.lista.get(i);
        	int xOrig = this.convX(vector.getPuntoOrigen().getRectangularesX());
        	int yOrig = this.convY(vector.getPuntoOrigen().getRectangularesY());
        	int xExtr = this.convX(vector.getPuntoExtremo().getRectangularesX());
        	int yExtr = this.convY(vector.getPuntoExtremo().getRectangularesY());
        	
        	g.drawLine(xOrig, yOrig, xExtr, yExtr);
        	
        }
    } 
}
