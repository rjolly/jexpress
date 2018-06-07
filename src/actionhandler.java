import java.awt.Frame;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class actionhandler
	implements WindowListener {

	Frame tobeclosed;
	int exitjava;

	public actionhandler(Frame frame) {
		this(frame, 0);
	}

	public actionhandler(Frame frame, int i) {
		exitjava = i;
		tobeclosed = frame;
	}

	public static void main(String args[]) {
	}

	public void windowActivated(WindowEvent windowevent) {
	}

	public void windowClosed(WindowEvent windowevent) {
	}

	public void windowClosing(WindowEvent windowevent) {
		if (exitjava == 1)
			System.exit(1);
		else
			tobeclosed.dispose();
	}

	public void windowDeactivated(WindowEvent windowevent) {
	}

	public void windowDeiconified(WindowEvent windowevent) {
	}

	public void windowIconified(WindowEvent windowevent) {
	}

	public void windowOpened(WindowEvent windowevent) {
	}
}
