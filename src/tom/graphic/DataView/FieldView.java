package tom.graphic.DataView;

import java.awt.Color;
import java.io.PrintStream;
import tom.graphic.ThreeD.Elem3D;
import tom.graphic.ThreeD.Face;
import tom.graphic.ThreeD.NonPlanarFace;
import tom.graphic.ThreeD.Object3D;
import tom.graphic.ThreeD.Point3D;

// Referenced classes of package tom.graphic.DataView:
//			ArrayChangeListener, Array, ArrayChangeEvent

public class FieldView extends Object3D
	implements ArrayChangeListener {

	Array arr;
	float min;
	float max;

	public FieldView(Array array) {
		min = -1F;
		max = 1.0F;
		arr = array;
		arr.addChangeListener(this);
		min = arr.getMin();
		max = arr.getMax();
		if (min == max)
			if (min == 0.0F) {
				min = -1F;
				max = 1.0F;
			} else {
				min = (min * 9F) / 10F;
				max = (max * 11F) / 10F;
			}
		for (int i = 0; i < arr.i; i++) {
			for (int k = 0; k < arr.j; k++)
				addVert(((i - arr.i / 2) * 200) / arr.i, ((k - arr.j / 2) * 200) / arr.j, ((arr.getIJ(i, k) - min) * 80F) / (max - min));

		}

		for (int j = 0; j < arr.i - 1; j++) {
			for (int l = 0; l < arr.j - 1; l++) {
				NonPlanarFace nonplanarface = new NonPlanarFace(j * arr.j + l, j * arr.j + l + 1, (j + 1) * arr.j + l + 1, (j + 1) * arr.j + l, Color.red);
				addElem(nonplanarface);
				int i1 = (int)(((double)(arr.getIJ(j, l) - min) * 200D) / (double)(max - min));
				i1 = (i1 + 55) % 256;
				if (i1 < 0)
					i1 += 256;
				nonplanarface.col = new Color(i1, i1, i1);
				nonplanarface.setColOp(new Color(i1, 255 - i1, i1 / 2));
			}

		}

	}

	public void elementChanged(ArrayChangeEvent arraychangeevent) {
		System.out.println("Element changed...");
		int i = arraychangeevent.pos[0] * arr.j + arraychangeevent.pos[1];
		super.vert[i].z = ((arraychangeevent.theValue - min) * 80F) / (max - min);
	}
}
