package lienzo;

import interfaz.PlanoRealDibujable;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;

import com.koldogontzal.geometria2d.Punto;

import conversor.ConversorPlanorealPixels;

import utils.Pixel;

public class LienzoEnPlanoReal extends Canvas {

	private static final long serialVersionUID = -7847721594435654437L;
	
	private static final int UNIDAD_EJES = 10;
	
	private ConversorPlanorealPixels conversor;
	
	// Resto
	private PlanoRealDibujable objeto;
	private Color color;
	private boolean ejes;
	
	
	public LienzoEnPlanoReal(PlanoRealDibujable objetoADibujar, Color color, 
			Punto infIzq, Punto supDrch, boolean ejes) {
        
		this.objeto = objetoADibujar;
		this.color = color;
        this.ejes = ejes;
        
       this.conversor = new ConversorPlanorealPixels(infIzq, supDrch);        
    }

    public Dimension getPreferredSize() {
    	// Tamaño preferido de la ventana (inicial)
        return new Dimension(1200, 740);
    }
    
    private int convX(double x) {
    	// Se introduce una coordenada en el eje X real y se devuelve el pixel al que corresponde
    	return this.conversor.convX(x);
    }
    
    private int convY(double y) {
    	// Se introduce una coordenada en el eje Y real y se devuelve el pixel al que corresponde
    	return this.conversor.convY(y);
    }
    
    private Punto convPunto(Punto p) {
    	// Se introduce un punto en el plano real y se devuelve el pixel al que corresponde
    	return new Punto(this.convX(p.getX()), this.convY(p.getY()));
    }
 
   /*
    * Paint when the AWT tells us to...
    */
    public void paint(Graphics g) {
    	// Recalcular valores para el nuevo tamaño de la ventana
    	Dimension size = getSize();
        this.conversor.CambiarTamagnoPixels(size.width, size.height);
        
        // Dibuja los ejes
		if (this.ejes) {
			this.dibujaEjes(g);			
		}
		
		// Fija el color para la figura
        g.setColor(this.color);
        
        // Dibuja el objeto incrustado
        this.objeto.dibujar(this, g);
    }
    
    
    private void dibujaEjes(Graphics g) {
    	double xMin = this.conversor.getPuntoInfIzq().getX();
    	double yMin = this.conversor.getPuntoInfIzq().getY();
    	double xMax = this.conversor.getPuntoSupDrch().getX();
    	double yMax = this.conversor.getPuntoSupDrch().getY();
    	
    	// Ejes verticales
    	for (int i = UNIDAD_EJES * (int)(xMin / UNIDAD_EJES); i <= UNIDAD_EJES * (int)(xMax / UNIDAD_EJES); i = i + UNIDAD_EJES) {
			if (i == 0) {
				g.setColor(Color.gray);
			} else {
				g.setColor(Color.cyan);
			}
			
			this.dibujaLinea(g, new Punto(i, yMin), new Punto(i, yMax));
		}
    	
    	// Ejes horizontales
    	for (int i = UNIDAD_EJES * (int)(yMin / UNIDAD_EJES); i <= UNIDAD_EJES * (int)(yMax / UNIDAD_EJES); i = i + UNIDAD_EJES) {
			if (i == 0) {
				g.setColor(Color.gray);
			} else {
				g.setColor(Color.cyan);
			}
			
			this.dibujaLinea(g, new Punto(xMin, i), new Punto(xMax, i));
		}
    	
    }
    
    public void dibujaPunto(Graphics g, Punto p) {
    	Punto pCn = this.convPunto(p);
    	g.drawLine((int)pCn.getX(), (int)pCn.getY(), (int)pCn.getX(), (int)pCn.getY());
    }
    
    public void dibujaCruz(Graphics g, Punto p) {
    	Punto pCn = this.convPunto(p);
    	g.drawLine((int)pCn.getX() - 2, (int)pCn.getY(), (int)pCn.getX() + 2, (int)pCn.getY());
    	g.drawLine((int)pCn.getX(), (int)pCn.getY() - 2, (int)pCn.getX(), (int)pCn.getY() + 2);
    }
    
    public void dibujaCabezaFlechaUp(Graphics g, Punto p) {
    	Punto pCn = this.convPunto(p);
    	g.drawLine((int)pCn.getX(), (int)pCn.getY(), (int)pCn.getX(), (int)pCn.getY());
    	g.drawLine((int)pCn.getX() - 1, (int)pCn.getY() + 1, (int)pCn.getX() + 1, (int)pCn.getY() + 1);
    	g.drawLine((int)pCn.getX() - 2, (int)pCn.getY() + 2, (int)pCn.getX() + 2, (int)pCn.getY() + 2);
    	g.drawLine((int)pCn.getX() - 3, (int)pCn.getY() + 3, (int)pCn.getX() - 3, (int)pCn.getY() + 3);
    	g.drawLine((int)pCn.getX() + 3, (int)pCn.getY() + 3, (int)pCn.getX() + 3, (int)pCn.getY() + 3);
    }
    
    public void dibujaLinea(Graphics g, Punto ini, Punto fin) {
    	Punto inCn = this.convPunto(ini);
    	Punto fnCn = this.convPunto(fin);
 Graphics2D g2 = (Graphics2D)g;
  g2.drawLine((int)inCn.getX(), (int)inCn.getY(), (int)fnCn.getX(), (int)fnCn.getY());
    }
    
    public void dibujaArco(Graphics g, Punto centro, double diamX, double diamY, double ang0, double arco) {
    	this.dibujaArcoPrivado(g, centro, diamX, diamY, ang0, arco, false);
    }
    
    public void rellenaArco(Graphics g, Punto centro, double diamX, double diamY, double ang0, double arco) {
    	this.dibujaArcoPrivado(g, centro, diamX, diamY, ang0, arco, true);
    }
    
    
    private void dibujaArcoPrivado(Graphics g, Punto centro, double diamX, double diamY, double ang0, double arco, boolean relleno) {
    	double xMin = this.conversor.getPuntoInfIzq().getX();
    	double yMax = this.conversor.getPuntoSupDrch().getY();
    	
    	Punto traslacion = new Punto(- diamX / 2.0, diamY / 2.0);
    	traslacion.sumar(centro);
    	Punto cnCn = this.convPunto(traslacion);    	
    	int diamIntX = this.convX(xMin + diamX);
    	int diamIntY = this.convY(yMax - diamY);
    	int intAng0 = (int)Math.round(ang0 * 180.0 / Math.PI);
    	int intArco = (int)Math.round(arco * 180.0 / Math.PI);
 
 Graphics2D g2 = (Graphics2D)g;
 g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    	g2.drawArc((int)cnCn.getX(), (int)cnCn.getY(), 
    			diamIntX, diamIntY, 
    			intAng0, intArco);
    	if (relleno) {
    		g2.fillArc((int)cnCn.getX(), (int)cnCn.getY(), 
    			diamIntX, diamIntY, 
    			intAng0, intArco);
    	}
    }
   
    public void rellenaTrapecio(Graphics g, Punto i0, Punto f0, Punto i1, Punto f1) {    	
    	// Trasforma a Pixels
    	Pixel pxi0 = this.conversor.convertir(i0);
    	Pixel pxf0 = this.conversor.convertir(f0);
    	Pixel pxi1 = this.conversor.convertir(i1);
    	Pixel pxf1 = this.conversor.convertir(f1);
    	
    	// Calcula el polígono
    	Polygon p = new Polygon();
    	p.addPoint(pxi0.getX(), pxi0.getY());
    	p.addPoint(pxf0.getX(), pxf0.getY());
    	p.addPoint(pxf1.getX(), pxf1.getY());
    	p.addPoint(pxi1.getX(), pxi1.getY());
    	
    	// Dibuja el trapecio
Graphics2D g2 = (Graphics2D)g;
g2.fillPolygon(p);		
    }
    
    
    public double getXMinReal() {
    	return this.conversor.getPuntoInfIzq().getX();
    }
    
    public double getXMaxReal() {
    	return this.conversor.getPuntoSupDrch().getX();
    }
    
    public int getAnchoPixels() {
    	return this.conversor.getPixelsX();
    }
    
}
