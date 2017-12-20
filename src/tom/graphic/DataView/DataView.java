package tom.graphic.DataView;

import java.awt.Color;
import java.awt.Component;
import tom.graphic.ThreeD.Line3D;
import tom.graphic.ThreeD.Object3D;
import tom.graphic.ThreeD.View3D;
import tom.graphic.ThreeD.View3DCanvas;
import tom.graphic.ThreeD.View3DCommon;
import tom.graphic.ThreeD.World3D;

// Referenced classes of package tom.graphic.DataView:
//			Array, FieldView, VectorFieldView, SquareView, 
//			BuildingView

public class DataView extends View3DCanvas {

	Array arr;
	private int currentRepr;
	boolean displayAxes;
	Object3D obj;
	public static final int REPR_3DSOLID = 0;
	public static final int REPR_VECTORFIELD = 1;
	public static final int REPR_SQUARE = 2;
	public static final int REPR_BUILDING = 3;

	public DataView() {
		super(false);
		currentRepr = 0;
		super.view.setOutline(true);
		arr = new Array(10, 10);
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++)
				arr.setIJ(i, j, (float)Math.random());

		}

		obj = getCurrentRepr(arr);
		view().computeXFac();
		super.view.world().addObj(obj);
	}

	public Object3D getCurrentRepr(Array array) {
		Object obj1;
		switch (currentRepr) {
		case 0: // '\0'
		default:
			obj1 = new FieldView(array);
			break;

		case 1: // '\001'
			obj1 = new VectorFieldView(array);
			break;

		case 2: // '\002'
			obj1 = new SquareView(array);
			break;

		case 3: // '\003'
			obj1 = new BuildingView(array);
			break;
		}
		if (displayAxes) {
			int i = ((Object3D) (obj1)).addVert(-100F, -100F, 0.0F);
			int j = ((Object3D) (obj1)).addVert(-100F, -100F, 50F);
			((Object3D) (obj1)).addElem(new Line3D(i, j, Color.red));
			j = ((Object3D) (obj1)).addVert(100F, -100F, 0.0F);
			((Object3D) (obj1)).addElem(new Line3D(i, j, Color.red));
			j = ((Object3D) (obj1)).addVert(-100F, 100F, 0.0F);
			((Object3D) (obj1)).addElem(new Line3D(i, j, Color.red));
		}
		return ((Object3D) (obj1));
	}

	public boolean getDisplayAxes() {
		return displayAxes;
	}

	public int getRepr() {
		return currentRepr;
	}

	public void set2DGrid(int i, int j, float af[]) {
		arr = new Array(i, j);
		for (int k = 0; k < i; k++) {
			for (int l = 0; l < j; l++)
				arr.setIJ(k, l, af[k * j + l]);

		}

		super.view.world().remObj(obj);
		obj = getCurrentRepr(arr);
		super.view.world().addObj(obj);
		repaint();
	}

	public void setArray(Array array) {
		arr = array;
		super.view.world().remObj(obj);
		obj = getCurrentRepr(arr);
		super.view.world().addObj(obj);
		repaint();
	}

	public void setDisplayAxes(boolean flag) {
		displayAxes = flag;
		super.view.world().remObj(obj);
		obj = getCurrentRepr(arr);
		super.view.world().addObj(obj);
		repaint();
	}

	public void setRepr(int i) {
		currentRepr = i;
		super.view.world().remObj(obj);
		obj = getCurrentRepr(arr);
		super.view.world().addObj(obj);
		repaint();
	}
}
