package tom.graphic.ThreeD;

import java.awt.Color;
import java.awt.Graphics;

// Referenced classes of package tom.graphic.ThreeD:
//			Elem3D, Point3D, Object3D, Matrix3D, 
//			View3D, World3D, Graphics3D, TextureMap

public class Face extends Elem3D {

	public int pt[];
	TextureMap tex;
	Color colOp;
	int x[];
	int y[];
	int n1;
	int n2;
	Point3D normal;
	boolean side;
	public int nbPt;

	public Face() {
		pt = new int[4];
		tex = null;
		colOp = null;
		x = new int[4];
		y = new int[4];
		normal = null;
		super.col = Color.white;
		nbPt = 0;
	}

	public Face(int i, int j, int k, int l, Color color) {
		pt = new int[4];
		tex = null;
		colOp = null;
		x = new int[4];
		y = new int[4];
		normal = null;
		pt[0] = i;
		pt[1] = j;
		pt[2] = k;
		pt[3] = l;
		if (k == l)
			nbPt = 3;
		else
			nbPt = 4;
		super.col = color;
	}

	public void addPt(int i) {
		if (nbPt >= pt.length) {
			int ai[] = new int[nbPt + 4];
			System.arraycopy(pt, 0, ai, 0, pt.length);
			pt = ai;
			x = new int[nbPt + 4];
			y = new int[nbPt + 4];
		}
		pt[nbPt] = i;
		nbPt++;
	}

	public void computeNormal() {
		normal = new Point3D();
		normal.prodVect(Point3D.sub(super.parent.vert[pt[1]], super.parent.vert[pt[0]]), Point3D.sub(super.parent.vert[pt[1]], super.parent.vert[pt[2]]));
		normal.normalize();
		normal.add(super.parent.vert[pt[1]]);
	}

	public Elem3D isInside(int i, int j) {
		Face face = null;
		boolean flag = false;
		if (super.posZ == -1000F)
			return null;
		int ai[] = new int[nbPt];
		int ai1[] = new int[nbPt];
		for (int k = 0; k < nbPt; k++) {
			int j1 = k;
			ai[k] = super.parent.pos2d[pt[j1] << 1];
			ai1[k] = super.parent.pos2d[(pt[j1] << 1) + 1];
		}

		int l = 0;
		for (int i1 = nbPt - 1; l < nbPt; i1 = l++) {
			int k1 = pt[l] << 1;
			int l1 = pt[i1] << 1;
			if ((super.parent.pos2d[k1 + 1] <= j && j < super.parent.pos2d[l1 + 1] || super.parent.pos2d[l1 + 1] <= j && j < super.parent.pos2d[k1 + 1]) && i < ((super.parent.pos2d[l1] - super.parent.pos2d[k1]) * (j - super.parent.pos2d[k1 + 1])) / (super.parent.pos2d[l1 + 1] - super.parent.pos2d[k1 + 1]) + super.parent.pos2d[k1])
				flag = !flag;
		}

		if (flag)
			face = this;
		return face;
	}

	public void paint(Graphics g) {
		for (int i = 0; i < nbPt; i++) {
			int j = pt[i] << 1;
			x[i] = super.parent.pos2d[j];
			y[i] = super.parent.pos2d[j + 1];
		}

		if (super.parent.flgShaded) {
			if (normal == null)
				computeNormal();
			Point3D point3d = new Point3D();
			super.parent.mat.transform(normal, point3d);
			point3d.sub(super.parent.tvert[pt[1]]);
			point3d.normalize();
			Color color;
			if (side || colOp == null)
				color = super.col;
			else
				color = colOp;
			if (color != null && point3d.z != 0.0F) {
				float f = Math.abs(point3d.z);
				if (f < 0.0F)
					f = 0.0F;
				float f1 = (float)color.getRed() * f;
				float f2 = (float)color.getGreen() * f;
				float f3 = (float)color.getBlue() * f;
				g.setColor(super.parent.curView.colorFog(f1, f2, f3, super.posZ));
			}
		} else
		if (side || colOp == null)
			g.setColor(super.parent.curView.colorFog(super.col, super.posZ));
		else
			g.setColor(super.parent.curView.colorFog(colOp, super.posZ));
		if (!super.parent.flgWireFrame) {
			if (tex != null)
				super.parent.curView.world().containsTexture = true;
			if (tex != null && (g instanceof Graphics3D) && side) {
				Graphics3D graphics3d = (Graphics3D)g;
				graphics3d.fillTextPolygon(x, y, nbPt, tex);
			} else {
				g.fillPolygon(x, y, nbPt);
			}
		}
		if (super.parent.flgOutline || super.parent.flgWireFrame) {
			if (super.parent.flgWireFrame)
				g.setColor(super.parent.curView.colorFog(super.parent.colWireFrame, super.posZ));
			else
				g.setColor(super.parent.curView.colorFog(super.parent.colOutline, super.posZ));
			g.drawPolygon(x, y, nbPt);
		}
	}

	public void setColOp(Color color) {
		colOp = color;
	}

	public void setTex(TextureMap texturemap) {
		tex = texturemap;
	}

	public float setZ() {
		int i = pt[0] * 2;
		int k = pt[1] * 2;
		int i1 = pt[2] * 2;
		boolean flag = true;
		if (!super.parent.flgWireFrame) {
			int k1 = super.parent.pos2d[i] - super.parent.pos2d[k];
			int l1 = super.parent.pos2d[i + 1] - super.parent.pos2d[k + 1];
			int i2 = super.parent.pos2d[i1] - super.parent.pos2d[k];
			int j2 = super.parent.pos2d[i1 + 1] - super.parent.pos2d[k + 1];
			int k2 = k1 * j2 - i2 * l1;
			if (k2 >= 0) {
				if (colOp == null)
					flag = false;
				side = false;
			} else {
				side = true;
			}
			if (super.parent.curView.backFace)
				flag = true;
		} else {
			flag = true;
		}
		if (flag) {
			int j = pt[0];
			int l = pt[1];
			int j1 = pt[2];
			super.posZ = (super.parent.tvert[j].z + super.parent.tvert[l].z + super.parent.tvert[j1].z) / 3F;
		} else {
			super.posZ = -1000F;
		}
		return super.posZ;
	}
}
