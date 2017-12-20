package tom.graphic.ThreeD;

import java.awt.Color;
import java.awt.Graphics;

// Referenced classes of package tom.graphic.ThreeD:
//			Elem3D, Object3D, Point3D

public class TextF3D extends Elem3D {

	public int pt;
	public String text;

	public TextF3D() {
		super.col = Color.white;
		text = "...";
	}

	public TextF3D(int i, String s, Color color) {
		pt = i;
		text = s;
		super.col = color;
	}

	public void paint(Graphics g) {
		int i = pt << 1;
		g.setColor(super.col);
		g.drawString(text, super.parent.pos2d[i], super.parent.pos2d[i + 1]);
	}

	public float setZ() {
		super.posZ = super.parent.tvert[pt].z;
		return super.posZ;
	}
}
