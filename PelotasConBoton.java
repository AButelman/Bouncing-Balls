import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class PelotasConBoton implements ActionListener, WindowListener {
	public static int MAX_PELOTAS = 12;
	
	private Frame ventana;
	private Panel panelDeBotones;
	private Panel panelDePelotas;
	private Button boton1;
	private Button boton2;
	private Label maximo;
	private Color color;
	private Pelota[] pelotas = new Pelota[MAX_PELOTAS];
	private Thread[] procesos = new Thread[MAX_PELOTAS];
	private int indice = 0;
	private Font fuente;
	private String etiqueta;

	public PelotasConBoton(){
		ventana = new Frame("Pelotas");	// Instanciamos la ventana
		ventana.addWindowListener(this);
		
		panelDeBotones = new Panel();
		panelDePelotas = new Panel();
		
		boton1 = new Button("Nueva Pelota");
		boton2 = new Button("Reiniciar");
		
// Etiqueta de cantidad de pelotas m�xima
		etiqueta = "�No quedan m�s pelotas!";
		maximo = new Label(etiqueta);
		fuente = new Font("TimesRoman", Font.PLAIN, 24);
		maximo.setFont(fuente);
		maximo.setSize(400, 50);
		//maximo.setLocation(330, 740); 
		maximo.setVisible(false);
	}

		
	public void actionPerformed(ActionEvent e) {	// Cuando se apreta un bot�n
		if (e.getActionCommand().equals("pelota")) {
			
			if (indice >= MAX_PELOTAS ) { // Para no pasarse de la m�xima cantidad de pelotas
				maximo.setVisible(true);
				return; 
			}
			
			do {	// Color aleatorio, mientras que no sea el mismo que el fondo de la ventana
				color = new Color ((int) (Math.random() * 255), (int) (Math.random() * 255), 
					(int) (Math.random() * 255));	
			} while ( (color.equals(panelDePelotas.getBackground())) );
			
			
			int diametro = (int) (Math.random() * 60 + 15);
			int velocidadX = (int) (Math.random() * 35) - (int) (Math.random() * 35);
			int velocidadY = (int) (Math.random() * 35) - (int) (Math.random() * 35);
		
			pelotas[indice] = new Pelota(panelDePelotas, color, diametro, velocidadX, velocidadY);
			procesos[indice] = new Thread(pelotas[indice]);
			procesos[indice].start();
			indice++;
		}
		
		if (e.getActionCommand().equals("reiniciar")) {
			reiniciar();
		}
	}
	
	public void reiniciar(){
		for (int i = 0; i < indice; i++){
			pelotas[i].setAndando(false);	// Salen del loop y se terminan los threads
			pelotas[i] = null;	// Reemplazamos con nada
			procesos[i] = null;
			Pelota.pelotas[i] = null;	// Borramos lo que hay en el detector de colisiones
		}
		indice = 0;	// Reiniciamos el �ndice
		Pelota.numeroPelotas = 0;	// Tambi�n dentro del detector de colisiones
		maximo.setVisible(false);	// Sacamos el cartel
	}
	
	public void inicia(){
	
//Le sacamos el layout por defecto para poder setear la localizaci�n deseada
		//ventana.setSize(1000, 800);	// Tama�o de la ventana
		
		boton1.setBackground(Color.GREEN);
		boton1.setActionCommand("pelota");
		boton1.addActionListener(this);
		panelDeBotones.add(boton1);
		
		boton2.setBackground(Color.RED);
		boton2.setActionCommand("reiniciar");
		boton2.addActionListener(this);
		panelDeBotones.add(boton2);
		
		panelDeBotones.setBackground(Color.GRAY);
				
		panelDePelotas.setLayout(null);	
		panelDePelotas.setSize(1000, 800);
		panelDePelotas.setBackground(Color.LIGHT_GRAY);
		
		maximo.setLocation(panelDePelotas.getWidth() / 2 - 160, 0);
		panelDePelotas.add(maximo);
		
		ventana.add(panelDeBotones, BorderLayout.NORTH);
		ventana.add(panelDePelotas, BorderLayout.CENTER);
		
		ventana.setLocation(0, 0);
		ventana.pack();
		ventana.setVisible(true);	// Lo mostramos
	}
			
	public static void main(String[] args) {
		PelotasConBoton m = new PelotasConBoton();	// Instanciamos un objeto de �sta clase
		m.inicia();	//Llamamos al m�todo
	}

	public void windowOpened(WindowEvent e) {	}
	public void windowClosing(WindowEvent e) {	// Se cerr� la ventana, fin del programa
		reiniciar();
		ventana.setVisible(false);
		System.exit(0);
	}
	public void windowClosed(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
}

