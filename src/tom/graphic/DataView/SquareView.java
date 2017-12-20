package tom.graphic.DataView;

import java.awt.Color;
import java.io.PrintStream;
import tom.graphic.ThreeD.Elem3D;
import tom.graphic.ThreeD.Face;
import tom.graphic.ThreeD.Object3D;

// Referenced classes of package tom.graphic.DataView:
//			ArrayChangeListener, Array, ArrayChangeEvent

public class SquareView extends Object3D
	implements ArrayChangeListener {

	Array arr;

	public SquareView(Array array) {
		arr = array;
		arr.addChangeListener(this);
		float f = arr.getMin();
		float f1 = arr.getMax();
		for (int i = 0; i < arr.i; i++) {
			for (int k = 0; k < arr.j; k++) {
				addVert(((i - arr.i / 2) * 200) / arr.i - 5, ((k - arr.j / 2) * 200) / arr.j - 5, ((arr.getIJ(i, k) - f) * 80F) / (f1 - f));
				addVert(((i - arr.i / 2) * 200) / arr.i - 5, ((k - arr.j / 2) * 200) / arr.j + 5, ((arr.getIJ(i, k) - f) * 80F) / (f1 - f));
				addVert(((i - arr.i / 2) * 200) / arr.i + 5, ((k - arr.j / 2) * 200) / arr.j + 5, ((arr.getIJ(i, k) - f) * 80F) / (f1 - f));
				addVert(((i - arr.i / 2) * 200) / arr.i + 5, ((k - arr.j / 2) * 200) / arr.j - 5, ((arr.getIJ(i, k) - f) * 80F) / (f1 - f));
			}

		}

		for (int j = 0; j < arr.i - 1; j++) {
			for (int l = 0; l < arr.j - 1; l++) {
				Face face = new Face((j * arr.j + l) * 4, (j * arr.j + l) * 4 + 1, (j * arr.j + l) * 4 + 2, (j * arr.j + l) * 4 + 3, Color.red);
				addElem(face);
				int i1 = (int)(((double)(arr.getIJ(j, l) - f) * 200D) / (double)(f1 - f));
				i1 = (i1 + 55) % 256;
				if (i1 < 0)
					i1 += 256;
				face.col = new Color(i1, i1, i1);
				face.setColOp(new Color(i1, 255 - i1, i1 / 2));
			}

		}

	}

	public void elementChanged(ArrayChangeEvent arraychangeevent) {
		System.out.println("Element changed...");
	}
}
