import java.awt.Color;
import java.awt.Graphics;
import java.awt.Panel;

// ESTO ES SIMPLEMENTE UN COMENTARIO PARA PROBAR GIT

@SuppressWarnings("serial")
public class Circulo extends Panel{	// Panel con el m�todo paint sobreescrito para dibujar un c�rculo
	private int diametro;
	private Color color;
	
	public Circulo(int diametro, Color color){
		super();
		this.diametro = diametro;
		this.color = color;
	}
	
	public void paint(Graphics g){	// Dibujamos cuando agregamos el panel a la ventan
		g.setColor(color);		// Color de la brocha
// Dibujamos un c�rculo con el diam�tro definido por la constante		
		g.fillOval(0, 0, diametro, diametro);
	}
}
