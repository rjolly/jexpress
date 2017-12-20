package tom.graphic.ThreeD;

import java.awt.Color;
import java.awt.Graphics;

// Referenced classes of package tom.graphic.ThreeD:
//			Point3D, Matrix3D, World3D, Object3D

public class View3D {

	float globFac;
	int width;
	int height;
	public Point3D pos;
	public boolean flgOutline;
	public boolean flgWireFrame;
	public boolean flgShaded;
	public boolean backFace;
	public boolean flgFog;
	protected Color fogColor;
	public Point3D angle;
	World3D world;
	Matrix3D amat;
	Matrix3D tmat;
	float e;
	protected float xfac;
	protected float scalefudge;

	public View3D() {
		globFac = 1.0F;
		width = 320;
		height = 200;
		pos = new Point3D();
		flgOutline = false;
		flgWireFrame = false;
		flgShaded = true;
		backFace = false;
		flgFog = false;
		fogColor = Color.white;
		angle = new Point3D();
		amat = new Matrix3D();
		tmat = new Matrix3D();
		e = 100F;
		scalefudge = 1.0F;
		world = new World3D();
		pos.z = 200F;
	}

	public View3D(World3D world3d) {
		globFac = 1.0F;
		width = 320;
		height = 200;
		pos = new Point3D();
		flgOutline = false;
		flgWireFrame = false;
		flgShaded = true;
		backFace = false;
		flgFog = false;
		fogColor = Color.white;
		angle = new Point3D();
		amat = new Matrix3D();
		tmat = new Matrix3D();
		e = 100F;
		scalefudge = 1.0F;
		world = world3d;
		pos.z = 200F;
	}

	public Color colorFog(float f, float f1, float f2, float f3) {
		if (flgFog) {
			float f4 = f3 / 1000F;
			if (f4 > 1.0F)
				f4 = 1.0F;
			float f5 = fogColor.getRed();
			float f6 = fogColor.getGreen();
			float f7 = fogColor.getBlue();
			f += (f5 - f) * f4;
			f1 += (f6 - f1) * f4;
			f2 += (f7 - f2) * f4;
		}
		return new Color((int)f, (int)f1, (int)f2);
	}

	public Color colorFog(Color color, float f) {
		if (flgFog)
			return colorFog(color.getRed(), color.getGreen(), color.getBlue(), f);
		else
			return color;
	}

	public void computeXFac() {
		world.findBB();
		float f = ((Object3D) (world)).xmax - ((Object3D) (world)).xmin;
		float f1 = ((Object3D) (world)).ymax - ((Object3D) (world)).ymin;
		float f2 = ((Object3D) (world)).zmax - ((Object3D) (world)).zmin;
		if (f1 > f)
			f = f1;
		if (f2 > f)
			f = f2;
		xfac = 100F / f;
		setGlobFac(xfac);
		float f3 = (float)width / 320F;
		float f4 = (float)height / 200F;
		e = 400F * scalefudge * (f3 >= f4 ? f4 : f3);
	}

	public void deplOf(float f, float f1, float f2) {
		pos.x += f;
		pos.y += f1;
		pos.z += f2;
	}

	public Point3D getAngle() {
		return angle;
	}

	public boolean getShaded() {
		return flgShaded;
	}

	public float getXAngle() {
		return angle.x;
	}

	public void paint(Graphics g) {
		amat.unit();
		amat.xrot(angle.x);
		amat.yrot(angle.y);
		amat.zrot(angle.z);
		if (world != null)
			world.paint(this, g);
	}

	public void rotateOf(float f, float f1, float f2) {
		angle.x += f;
		angle.y += f1;
		angle.z += f2;
	}

	public void setAngle(Point3D point3d) {
		angle = point3d;
	}

	public void setGlobFac(float f) {
		globFac = f;
	}

	public void setOutline(boolean flag) {
		flgOutline = flag;
	}

	public void setShaded(boolean flag) {
		flgShaded = flag;
	}

	public void setWireFrame(boolean flag) {
		flgWireFrame = flag;
	}

	public void setXAngle(float f) {
		angle.x = f;
	}

	public void size(int i, int j) {
		width = i;
		height = j;
	}

	public World3D world() {
		return world;
	}
}
