import java.awt.*;

public class Pelota implements Runnable {
	public static int numeroPelotas = 0;
	public static Pelota[] pelotas = new Pelota[PelotasConBoton.MAX_PELOTAS];
	
	private int estaPelota;
	private boolean andando = true;
	
	private int diametro;
	private int x;
	private int y;
	private int velocidadX;
	private int velocidadY;
	private Panel ventana;
	private Circulo circulo;
	private Color color;
	boolean derecha;
	boolean izquierda;
	boolean arriba;
	boolean abajo;
	
	public Pelota(Panel ventana, Color color, int diametro, int velocidadX, int velocidadY){
		this.ventana = ventana;
		this.color = color;
		this.diametro = diametro;
		this.velocidadX = velocidadX;
		this.velocidadY = velocidadY;
		this.color = color;
		
		circulo = new Circulo(diametro, color);
		circulo.setSize(diametro, diametro);
		
		// Que empiece en alg�n lado aleatorio dentro de la ventana
		y = (int) (Math.random() * ventana.getHeight());
		if (y < 0) { y = 1; }	// Borde superior
		if ((y + diametro) > ventana.getHeight()) { y = ventana.getHeight() - diametro-1; } // Borde inferior
		x = (int) (Math.random() * ventana.getWidth());
		if (x < 0) { x = 1; }	// Borde izquierdo
		if ((x + diametro) > ventana.getWidth()) { x = ventana.getWidth() - diametro-1; } // Borde derecho	
		
		estaPelota = numeroPelotas++;
		pelotas[estaPelota] = this; 
		
		System.out.println("Pelota: " + estaPelota + ". Posici�n inicial X: " + x + " Y: " + y 
				+ ". VelocidadX: " + velocidadX + ", velocidadY: " + velocidadY + ".");
	}
	
	public void setAndando(boolean andando) {this.andando = andando; }
	
	public int getX() {return x;}
	public int getY() {return y;}
	public int getDiametro() {return diametro;}
	
	public int getVelocidadX() {return velocidadX;}
	public int getVelocidadY() {return velocidadY;}
	
	public boolean getAbajo() {return abajo;}
	public boolean getArriba() {return arriba;}
	public boolean getDerecha() {return derecha;}
	public boolean getIzquierda() {return izquierda;}
	
	@Override
	public void run(){
		ventana.add(circulo);
		
		circulo.setLocation(x, y);
		circulo.setVisible(true);
						
		while (andando){
			// Rebotes entre pelotas
			for (int i = 0; i < numeroPelotas; i++){
				if (pelotas[i].equals(this)) {continue;}	// Para que no se compare consigo misma
				
				// Tomamos la velocidad de la pelota comparada para no hacerlo cuando hay choque y que 
				// pueda ser inconsistente, porque ambas estar�n chocando y reaccionando a la vez.
				int otraVelocidadX = pelotas[i].getVelocidadX();
				int otraVelocidadY = pelotas[i].getVelocidadY();
				
				if ( (x > pelotas[i].getX()) && (x <=  pelotas[i].getX() + pelotas[i].getDiametro()) ){
					izquierda = true;
				} else { izquierda = false; }
					
						
				if ( (x + diametro >= pelotas[i].getX() ) && ( x < pelotas[i].getX()) ) { // Por la izqu 
					  derecha = true;
				} else { derecha = false; }
			
				if ( (y > pelotas[i].getY()) && (y <= pelotas[i].getY() + pelotas[i].getDiametro()) ) {
					arriba = true;
				} else { arriba = false; }
					
				if ( (y + diametro >= pelotas[i].getY()) && ( y < pelotas[i].getY()) ) { 
					abajo = true;
				} else { abajo = false; }
			
				if (abajo && izquierda) {
					if ((velocidadY > 0) && (velocidadX > 0)) {	// Si bajaba hacia la derecha 
						velocidadY = velocidadY - velocidadY * 2;	// subo
					} else if ((velocidadY < 0 ) && (velocidadX > 0)) { // Si sub�a hacia la derecha
						velocidadY--;	// Voy m�s r�pido
						velocidadX++;
					} else if ((velocidadY > 0) && (velocidadX < 0)) { // Si bajaba hacia la izquierda
						velocidadY = velocidadY - velocidadY * 2;	// Reboto
						velocidadX = velocidadX - velocidadX * 2;
					} else if ((velocidadY < 0) && (velocidadX < 0)) { // Si subia hacia la izquierda
						velocidadX = velocidadX - velocidadX * 2;	// Cambio de sentido
					} else if ((velocidadY == 0) && (velocidadX > 0)) { // Si iba a la derecha
						velocidadY = velocidadY - pelotas[i].getVelocidadY(); // Subo con la fuerza de la otra
					} else if ((velocidadY == 0) && (velocidadX < 0)) { // Si iba a la izquierda
						velocidadX = velocidadX - velocidadX * 2;	// Cambio de sentido
						velocidadY = velocidadY - pelotas[i].getVelocidadY(); // Subo con la fuerza de la otra
					} else if ((velocidadY > 0) && (velocidadX == 0)) { // Si bajaba
						velocidadY = velocidadY - velocidadY * 2;	// Cambio de sentido
						velocidadX = otraVelocidadX; // A la derecha con la fuerza de la otra
					} else if ((velocidadY < 0) && (velocidadX == 0)) { // Si subia
						velocidadX = otraVelocidadX; // A la derecha con la fuerza de la otra
					} else if ((velocidadY == 0) && (velocidadX == 0)) {	// Si estaba quieta
						velocidadX = otraVelocidadX;	// Me muevo como la que me choc�
						velocidadY = otraVelocidadY;
					}
				}
				
				if (abajo && derecha) {
					if ((velocidadY > 0) && (velocidadX > 0)) {	// Si bajaba hacia la derecha 
						velocidadY = velocidadY - velocidadY * 2;	// Reboto
						velocidadX = velocidadX - velocidadX * 2;
					} else if ((velocidadY < 0 ) && (velocidadX > 0)) { // Si sub�a hacia la derecha
						velocidadX = velocidadX - velocidadX * 2;	// Cambio de sentido
					} else if ((velocidadY > 0) && (velocidadX < 0)) { // Si bajaba hacia la izquierda
						velocidadY = velocidadY - velocidadY * 2;	// Subo
					} else if ((velocidadY < 0) && (velocidadX < 0)) { // Si subia hacia la izquierda
						velocidadY--;	// Voy m�s r�pido
						velocidadX--; 
					} else if ((velocidadY == 0) && (velocidadX > 0)) { // Si iba a la derecha
						velocidadX = velocidadX - velocidadX * 2;	// Cambio de sentido
						velocidadY = velocidadY - otraVelocidadY; // Subo con la fuerza de la otra
					} else if ((velocidadY == 0) && (velocidadX < 0)) { // Si iba a la izquierda
						velocidadY = velocidadY - otraVelocidadY; // Subo con la fuerza de la otra
					} else if ((velocidadY > 0) && (velocidadX == 0)) { // Si bajaba
						velocidadY = velocidadY - velocidadY * 2;	// Cambio de sentido
						velocidadX = otraVelocidadX; // A la izquierda con la fuerza de la otra
					} else if ((velocidadY < 0) && (velocidadX == 0)) { // Si subia
						velocidadX = otraVelocidadX; // A la izquierda con la fuerza de la otra
					} else if ((velocidadY == 0) && (velocidadX == 0)) {	// Si estaba quieta
						velocidadX = otraVelocidadX;	// Me muevo como la que me choc�
						velocidadY = otraVelocidadY;
					}
				}
				
				if (arriba && izquierda) {
					if ((velocidadY > 0) && (velocidadX > 0)) {	// Si bajaba hacia la derecha 
						velocidadY++;	// Voy m�s r�pido
						velocidadX++; 
					} else if ((velocidadY < 0 ) && (velocidadX > 0)) { // Si sub�a hacia la derecha
						velocidadY = velocidadY - velocidadY * 2;	// Cambio de sentido
					} else if ((velocidadY > 0) && (velocidadX < 0)) { // Si bajaba hacia la izquierda
						velocidadX = velocidadX - velocidadX * 2;   // Cambio de sentido
					} else if ((velocidadY < 0) && (velocidadX < 0)) { // Si subia hacia la izquierda
						velocidadY = velocidadY - velocidadY * 2;	// Reboto
						velocidadX = velocidadX - velocidadX * 2;
					} else if ((velocidadY == 0) && (velocidadX > 0)) { // Si iba a la derecha
						velocidadY = pelotas[i].getVelocidadY(); // Bajo con la fuerza de la otra
					} else if ((velocidadY == 0) && (velocidadX < 0)) { // Si iba a la izquierda
						velocidadX = velocidadX - velocidadX * 2;	// Cambio de sentido
						velocidadY = otraVelocidadY; // Bajo con la fuerza de la otra
					} else if ((velocidadY > 0) && (velocidadX == 0)) { // Si bajaba
						velocidadX = otraVelocidadX; // A la derecha con la fuerza de la otra
					} else if ((velocidadY < 0) && (velocidadX == 0)) { // Si subia
						velocidadY = velocidadY - velocidadY * 2;	// Cambio de sentido
						velocidadX = otraVelocidadX; // A la derecha con la fuerza de la otra
					} else if ((velocidadY == 0) && (velocidadX == 0)) {	// Si estaba quieta
						velocidadX = otraVelocidadX;	// Me muevo como la que me choc�
						velocidadY = otraVelocidadY;
					}
				}
				

				if (arriba && derecha) {
					if ((velocidadY > 0) && (velocidadX > 0)) {	// Si bajaba hacia la derecha 
						velocidadX = velocidadX - velocidadX * 2;	// Cambio de sentido
					} else if ((velocidadY < 0 ) && (velocidadX > 0)) { // Si sub�a hacia la derecha
						velocidadY = velocidadY - velocidadY * 2;	// Reboto
						velocidadX = velocidadX - velocidadX * 2;
					} else if ((velocidadY > 0) && (velocidadX < 0)) { // Si bajaba hacia la izquierda
						velocidadY++;	// Voy m�s r�pido
						velocidadX--; 
					} else if ((velocidadY < 0) && (velocidadX < 0)) { // Si subia hacia la izquierda
						velocidadY = velocidadY - velocidadY * 2;	// Cambio de sentido
					} else if ((velocidadY == 0) && (velocidadX > 0)) { // Si iba a la derecha
						velocidadY = otraVelocidadY; // Bajo con la fuerza de la otra
						velocidadX = velocidadX - velocidadX * 2;	// Cambio de sentido
					} else if ((velocidadY == 0) && (velocidadX < 0)) { // Si iba a la izquierda
						velocidadY = otraVelocidadY; // Bajo con la fuerza de la otra
					} else if ((velocidadY > 0) && (velocidadX == 0)) { // Si bajaba
						velocidadX = 0 - otraVelocidadX; // A la izquierda con la fuerza de la otra
					} else if ((velocidadY < 0) && (velocidadX == 0)) { // Si subia
						velocidadY = velocidadY - velocidadY * 2;	// Cambio de sentido
						velocidadX = 0 - otraVelocidadX; // A la izquierda con la fuerza de la otra
					} else if ((velocidadY == 0) && (velocidadX == 0)) {	// Si estaba quieta
						velocidadX = otraVelocidadX;	// Me muevo como la que me choc�
						velocidadY = otraVelocidadY;
					}
				}
				/* boolean otraAbajo = pelotas[i].getAbajo();
				boolean otraArriba = pelotas[i].getArriba();
				boolean otraDerecha = pelotas[i].getDerecha();
				boolean otraIzquierda = pelotas[i].getIzquierda(); */
				
			}		
			
			x = x + velocidadX;
			y = y + velocidadY;
			
			System.out.println("Pelota: " + estaPelota + " X: " + x + " Y: " + y);
			if (x < 0) { // Limite izquierdo
				x = 0;
				velocidadX = velocidadX - velocidadX * 2;
				new Sonido().choquePared();;
			}
			
			if (x + diametro > ventana.getWidth()){	// L�mite derecho
				x = ventana.getWidth() - diametro;
				velocidadX = velocidadX - velocidadX * 2;
				new Sonido().choquePared();;
			}
			
			if (y < 0) { // L�mite superior
				y = 0;
				velocidadY = velocidadY - velocidadY * 2;
				new Sonido().choquePared();;
			}	
			
			if (y + diametro > ventana.getHeight() ) { // L�mite inferior
				y = ventana.getHeight() - diametro;
				velocidadY = velocidadY - velocidadY * 2;
				new Sonido().choquePared();;
			}
			
			circulo.setLocation(x, y);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {}
		}
		circulo.setVisible(false); // Cuando salimos del loop borramos la pelota y termina el thread
		
	}
}


