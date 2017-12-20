package tom.graphic.ThreeD;

import java.awt.Color;
import java.awt.Graphics;

// Referenced classes of package tom.graphic.ThreeD:
//			Elem3D, Object3D, View3D, Point3D

public class Line3D extends Elem3D {

	int pt[];

	public Line3D(int i, int j, Color color) {
		pt = new int[2];
		pt[0] = i;
		pt[1] = j;
		super.col = color;
	}

	public void paint(Graphics g) {
		int i = pt[0] << 1;
		int j = pt[1] << 1;
		g.setColor(super.parent.curView.colorFog(super.col, super.posZ));
		g.drawLine(super.parent.pos2d[i], super.parent.pos2d[i + 1], super.parent.pos2d[j], super.parent.pos2d[j + 1]);
	}

	public float setZ() {
		int i = pt[0];
		int j = pt[1];
		super.posZ = (super.parent.tvert[i].z + super.parent.tvert[j].z) / 2.0F;
		return super.posZ;
	}
}
