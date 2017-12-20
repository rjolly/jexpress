package tom.graphic.ThreeD;

import java.awt.Color;
import java.awt.Graphics;

// Referenced classes of package tom.graphic.ThreeD:
//			Face, Elem3D, Object3D, Point3D

public class NonPlanarFace extends Face {

	public NonPlanarFace() {
	}

	public NonPlanarFace(int i, int j, int k, int l, Color color) {
		super(i, j, k, l, color);
	}

	public void paint(Graphics g) {
		for (int j = 0; j < 2; j++) {
			for (int i = 0; i < 3; i++) {
				int k = super.pt[(i + 2 * j) % 4] << 1;
				super.x[i] = super.parent.pos2d[k];
				super.y[i] = super.parent.pos2d[k + 1];
			}

			if (super.side)
				g.setColor(super.col);
			else
				g.setColor(super.colOp);
			if (!super.parent.flgWireFrame)
				g.fillPolygon(super.x, super.y, 3);
			if (super.parent.flgOutline || super.parent.flgWireFrame) {
				g.setColor(Color.black);
				g.drawPolygon(super.x, super.y, 3);
			}
		}

	}

	public void setColOp(Color color) {
		super.colOp = color;
	}

	public float setZ() {
		int i = super.pt[0] * 2;
		int k = super.pt[1] * 2;
		int i1 = super.pt[2] * 2;
		boolean flag = true;
		int k1 = super.parent.pos2d[i] - super.parent.pos2d[k];
		int l1 = super.parent.pos2d[i + 1] - super.parent.pos2d[k + 1];
		int i2 = super.parent.pos2d[i1] - super.parent.pos2d[k];
		int j2 = super.parent.pos2d[i1 + 1] - super.parent.pos2d[k + 1];
		int k2 = k1 * j2 - i2 * l1;
		if (k2 >= 0) {
			if (super.colOp == null)
				flag = false;
			super.side = false;
		} else {
			super.side = true;
		}
		if (flag) {
			int j = super.pt[0];
			int l = super.pt[1];
			int j1 = super.pt[2];
			super.posZ = (super.parent.tvert[j].z + super.parent.tvert[l].z + super.parent.tvert[j1].z) / 3F;
		} else {
			super.posZ = -1000F;
		}
		return super.posZ;
	}
}
