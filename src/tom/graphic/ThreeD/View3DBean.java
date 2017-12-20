package tom.graphic.ThreeD;

import java.awt.Component;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.PrintStream;
import java.io.Serializable;

// Referenced classes of package tom.graphic.ThreeD:
//			View3DCommon

public class View3DBean extends View3DCommon
	implements Serializable, MouseMotionListener, MouseListener {

	public View3DBean() {
		addMouseMotionListener(this);
		addMouseListener(this);
	}

	public View3DBean(boolean flag) {
		super(flag);
		addMouseMotionListener(this);
		addMouseListener(this);
	}

	public void mouseClicked(MouseEvent mouseevent) {
	}

	public void mouseDragged(MouseEvent mouseevent) {
		commonMouseDrag(mouseevent.getX(), mouseevent.getY(), mouseevent.getModifiers());
	}

	public void mouseEntered(MouseEvent mouseevent) {
	}

	public void mouseExited(MouseEvent mouseevent) {
	}

	public void mouseMoved(MouseEvent mouseevent) {
	}

	public void mousePressed(MouseEvent mouseevent) {
		System.out.println("NEW mouse down");
		commonMouseDown(mouseevent.getX(), mouseevent.getY());
	}

	public void mouseReleased(MouseEvent mouseevent) {
		commonMouseUp(mouseevent.getX(), mouseevent.getY());
	}
}
