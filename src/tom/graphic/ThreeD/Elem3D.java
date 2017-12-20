package tom.graphic.ThreeD;

import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.io.PrintStream;

// Referenced classes of package tom.graphic.ThreeD:
//			Comparator, Object3D

public class Elem3D
	implements Comparator {

	public static final int ELEM_NOT_VISIBLE = -1000;
	protected float posZ;
	protected Object3D parent;
	public Color col;

	Elem3D() {
		col = Color.black;
	}

	public int compare(Object obj, Object obj1) {
		byte byte0 = 0;
		if (((Elem3D)obj).posZ > ((Elem3D)obj1).posZ)
			byte0 = 1;
		else
		if (((Elem3D)obj).posZ < ((Elem3D)obj1).posZ)
			byte0 = -1;
		return byte0;
	}

	public boolean equals(Object obj) {
		return this == obj;
	}

	public String getLastEvtAsString() {
		return "";
	}

	public boolean handleEvent(Event event) {
		return false;
	}

	public Elem3D isInside(int i, int j) {
		return null;
	}

	public void paint(Graphics g) {
		System.out.println("Paint Elem 3D");
	}

	public float setZ() {
		return posZ;
	}
}
