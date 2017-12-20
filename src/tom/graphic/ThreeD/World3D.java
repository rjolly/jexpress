package tom.graphic.ThreeD;

import java.awt.Graphics;

// Referenced classes of package tom.graphic.ThreeD:
//			Object3D, Matrix3D, View3D, Point3D

public class World3D extends Object3D {

	public boolean containsTexture;

	public World3D() {
		containsTexture = false;
	}

	public int addObj(Object3D object3d) {
		return addChild(object3d);
	}

	public void paint(View3D view3d, Graphics g) {
		super.mat.unit();
		super.mat.mult(view3d.amat);
		super.mat.scale(view3d.globFac, -view3d.globFac, view3d.globFac);
		super.transformed = false;
		super.mat.translate(view3d.pos.x, view3d.pos.y, view3d.pos.z);
		computeZ();
		super.curView = view3d;
		paint(g);
	}

	public void remAllObj() {
		remAllChilds();
	}

	public void remObj(Object3D object3d) {
		remChild(object3d);
	}
}
