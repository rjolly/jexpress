package tom.graphic.ThreeD;

import java.awt.Component;
import java.awt.Event;

// Referenced classes of package tom.graphic.ThreeD:
//			View3DCommon

public class View3DCanvas extends View3DCommon {

	public View3DCanvas() {
	}

	public View3DCanvas(boolean flag) {
		super(flag);
	}

	public boolean mouseDown(Event event, int i, int j) {
		commonMouseDown(i, j);
		return super.mouseDown(event, i, j);
	}

	public boolean mouseDrag(Event event, int i, int j) {
		super.mouseDrag(event, i, j);
		return commonMouseDrag(i, j, event.modifiers);
	}

	public boolean mouseUp(Event event, int i, int j) {
		commonMouseUp(i, j);
		return super.mouseUp(event, i, j);
	}
}
