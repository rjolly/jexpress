package tom.graphic.ThreeD;

import java.awt.Color;
import java.awt.Graphics;

// Referenced classes of package tom.graphic.ThreeD:
//			Elem3D, Point3D, Object3D, Matrix3D, 
//			View3D

public class Sphere3D extends Elem3D {

	public int pt;
	public float size;

	public Sphere3D() {
		size = 1.0F;
	}

	public Sphere3D(int i, float f, Color color) {
		size = 1.0F;
		pt = i;
		size = f;
		super.col = color;
	}

	public int getSize() {
		int ai[] = new int[2];
		Point3D point3d = new Point3D();
		Point3D point3d1 = new Point3D();
		point3d.x = super.parent.vert[pt].x + size;
		point3d.y = super.parent.vert[pt].y;
		point3d.z = super.parent.vert[pt].z;
		super.parent.mat.transform(point3d, point3d1);
		point3d1.sub(super.parent.tvert[pt]);
		float f = point3d1.getLength();
		point3d.x = f;
		point3d.y = f;
		point3d.z = super.parent.tvert[pt].z;
		super.parent.computeOnePoint(point3d, ai, false);
		float f1 = ai[0];
		if (f1 < 0.0F)
			f1 = -f1;
		return (int)f1;
	}

	public Elem3D isInside(int i, int j) {
		int k = pt << 1;
		Sphere3D sphere3d = null;
		int l = i - super.parent.pos2d[k];
		int i1 = j - super.parent.pos2d[k + 1];
		int j1 = getSize() / 2;
		if (Math.abs(l) < j1 && Math.abs(i1) < j1 && l * l + i1 * i1 <= j1 * j1)
			sphere3d = this;
		return sphere3d;
	}

	public void paint(Graphics g) {
		int i = pt << 1;
		int j = getSize();
		g.setColor(super.parent.curView.colorFog(super.col, super.posZ));
		if (super.parent.flgWireFrame) {
			g.drawOval(super.parent.pos2d[i] - j / 2, super.parent.pos2d[i + 1] - j / 2, j, j);
		} else {
			g.fillOval(super.parent.pos2d[i] - j / 2, super.parent.pos2d[i + 1] - j / 2, j, j);
			if (super.parent.flgOutline) {
				g.setColor(super.parent.curView.colorFog(super.parent.colOutline, super.posZ));
				g.drawOval(super.parent.pos2d[i] - j / 2, super.parent.pos2d[i + 1] - j / 2, j, j);
			}
		}
	}

	public float setZ() {
		super.posZ = super.parent.tvert[pt].z;
		return super.posZ;
	}
}
