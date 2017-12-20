package tom.graphic.DataView;

import java.awt.Color;
import tom.graphic.ThreeD.Face;
import tom.graphic.ThreeD.Object3D;

// Referenced classes of package tom.graphic.DataView:
//			Array

public class BuildingView extends Object3D {

	Array arr;

	public BuildingView(Array array) {
		arr = array;
		float f = arr.getMin();
		float f1 = arr.getMax();
		for (int i = 0; i < arr.i; i++) {
			for (int k = 0; k < arr.j; k++) {
				addVert(((i - arr.i / 2) * 200) / arr.i - 5, ((k - arr.j / 2) * 200) / arr.j - 5, ((arr.getIJ(i, k) - f) * 80F) / (f1 - f));
				addVert(((i - arr.i / 2) * 200) / arr.i - 5, ((k - arr.j / 2) * 200) / arr.j + 5, ((arr.getIJ(i, k) - f) * 80F) / (f1 - f));
				addVert(((i - arr.i / 2) * 200) / arr.i + 5, ((k - arr.j / 2) * 200) / arr.j + 5, ((arr.getIJ(i, k) - f) * 80F) / (f1 - f));
				addVert(((i - arr.i / 2) * 200) / arr.i + 5, ((k - arr.j / 2) * 200) / arr.j - 5, ((arr.getIJ(i, k) - f) * 80F) / (f1 - f));
				addVert(((i - arr.i / 2) * 200) / arr.i - 5, ((k - arr.j / 2) * 200) / arr.j - 5, 0.0F);
				addVert(((i - arr.i / 2) * 200) / arr.i - 5, ((k - arr.j / 2) * 200) / arr.j + 5, 0.0F);
				addVert(((i - arr.i / 2) * 200) / arr.i + 5, ((k - arr.j / 2) * 200) / arr.j + 5, 0.0F);
				addVert(((i - arr.i / 2) * 200) / arr.i + 5, ((k - arr.j / 2) * 200) / arr.j - 5, 0.0F);
			}

		}

		for (int j = 0; j < arr.i - 1; j++) {
			for (int l = 0; l < arr.j - 1; l++) {
				int i1 = (int)(((double)(arr.getIJ(j, l) - f) * 200D) / (double)(f1 - f));
				i1 = (i1 + 55) % 256;
				if (i1 < 0)
					i1 += 256;
				new Color(i1, i1, i1);
				Color color = new Color(i1, 255 - i1, i1 / 2);
				Face face = new Face((j * arr.j + l) * 8 + 3, (j * arr.j + l) * 8 + 2, (j * arr.j + l) * 8 + 1, (j * arr.j + l) * 8 + 0, color);
				addElem(face);
				face = new Face((j * arr.j + l) * 8 + 4, (j * arr.j + l) * 8 + 5, (j * arr.j + l) * 8 + 6, (j * arr.j + l) * 8 + 7, color);
				addElem(face);
				face = new Face((j * arr.j + l) * 8, (j * arr.j + l) * 8 + 1, (j * arr.j + l) * 8 + 1 + 4, (j * arr.j + l) * 8 + 4, color);
				addElem(face);
				face = new Face((j * arr.j + l) * 8 + 1, (j * arr.j + l) * 8 + 2, (j * arr.j + l) * 8 + 2 + 4, (j * arr.j + l) * 8 + 1 + 4, color);
				addElem(face);
				face = new Face((j * arr.j + l) * 8 + 2, (j * arr.j + l) * 8 + 3, (j * arr.j + l) * 8 + 3 + 4, (j * arr.j + l) * 8 + 2 + 4, color);
				addElem(face);
				face = new Face((j * arr.j + l) * 8 + 3, (j * arr.j + l) * 8, (j * arr.j + l) * 8 + 4, (j * arr.j + l) * 8 + 3 + 4, color);
				addElem(face);
			}

		}

	}
}
