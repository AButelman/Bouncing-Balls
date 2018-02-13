import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

public class Sonido {
	private static URL urlPared = Sonido.class.getResource("boing.wav"); 
	private AudioClip sonidoPared = Applet.newAudioClip(urlPared);
		
	public void choquePared() {
		sonidoPared.play();
	}
}
