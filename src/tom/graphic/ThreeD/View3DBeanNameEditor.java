package tom.graphic.ThreeD;

import java.awt.Component;
import java.beans.PropertyEditorSupport;
import java.io.PrintStream;

// Referenced classes of package tom.graphic.ThreeD:
//			FileDiagButton

public class View3DBeanNameEditor extends PropertyEditorSupport {

	public View3DBeanNameEditor() {
		System.out.println("View3D bean name editor constructor");
	}

	public Component getCustomEditor() {
		System.out.println("get custom editor");
		return new FileDiagButton(this);
	}

	public boolean supportsCustomEditor() {
		System.out.println("Support custom editor");
		return true;
	}
}
