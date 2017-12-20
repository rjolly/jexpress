package tom.graphic.ThreeD;

import java.awt.Button;
import java.awt.Event;
import java.io.PrintStream;

// Referenced classes of package tom.graphic.ThreeD:
//			View3DBeanNameEditor

class FileDiagButton extends Button {

	View3DBeanNameEditor myParent;

	public FileDiagButton(View3DBeanNameEditor view3dbeannameeditor) {
		myParent = view3dbeannameeditor;
	}

	public boolean action(Event event, Object obj) {
		System.out.println("Action");
		return true;
	}
}
