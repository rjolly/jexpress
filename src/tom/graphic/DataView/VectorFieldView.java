package tom.graphic.DataView;

import java.awt.Color;
import tom.graphic.ThreeD.Line3D;
import tom.graphic.ThreeD.Object3D;

// Referenced classes of package tom.graphic.DataView:
//			Array

public class VectorFieldView extends Object3D {

	Array arr;

	public VectorFieldView(Array array) {
		arr = array;
		arr.getMin();
		arr.getMax();
		for (int i = 0; i < arr.i; i++) {
			for (int j = 0; j < arr.j; j++) {
				int k = addVert(((i - arr.i / 2) * 200) / arr.i, ((j - arr.j / 2) * 200) / arr.j, 0.0F);
				float f = arr.getIJ(i, j);
				float f1 = (float)(8D * Math.cos(f));
				float f2 = (float)(8D * Math.sin(f));
				int l = addVert((float)(((i - arr.i / 2) * 200) / arr.i) + f1, (float)(((j - arr.j / 2) * 200) / arr.j) + f2, 0.0F);
				int i1 = addVert(((float)(((i - arr.i / 2) * 200) / arr.i) + f1) - f2 / 4F - f1 / 4F, ((float)(((j - arr.j / 2) * 200) / arr.j) + f2 + f1 / 4F) - f2 / 4F, 0.0F);
				int j1 = addVert(((float)(((i - arr.i / 2) * 200) / arr.i) + f1 + f2 / 4F) - f1 / 4F, ((float)(((j - arr.j / 2) * 200) / arr.j) + f2) - f1 / 4F - f2 / 4F, 0.0F);
				Line3D line3d = new Line3D(k, l, Color.white);
				Line3D line3d1 = new Line3D(l, i1, Color.white);
				Line3D line3d2 = new Line3D(l, j1, Color.white);
				addElem(line3d);
				addElem(line3d1);
				addElem(line3d2);
			}

		}

	}
}
