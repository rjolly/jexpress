package tom.graphic.ThreeD;

import java.util.EventObject;

// Referenced classes of package tom.graphic.ThreeD:
//			Elem3D, Object3D

public class View3DEvent extends EventObject {

	private Elem3D elem;

	View3DEvent(Object obj, Elem3D elem3d) {
		super(obj);
		elem = null;
		elem = elem3d;
	}

	public String getAsString() {
		if (elem != null)
			return elem.getLastEvtAsString();
		else
			return "";
	}

	public Elem3D getElem() {
		return elem;
	}

	public String getObjectName() {
		if (elem != null && elem.parent != null)
			return elem.parent.getLastEvtAsString();
		else
			return "";
	}

	public String toString() {
		String s = getAsString();
		if (s.equals(""))
			s = getObjectName();
		return s;
	}
}
