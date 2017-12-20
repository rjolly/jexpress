package tom.graphic.ThreeD;

import java.awt.Graphics;

// Referenced classes of package tom.graphic.ThreeD:
//			Face, Object3D, Elem3D, Graphics3D, 
//			TextureMap

public class Face3DText extends Face {

	TextureMap tex;

	public Face3DText(Face face, TextureMap tex) {
		super.pt = face.pt;
		super.nbPt = face.nbPt;
		super.col = ((Elem3D) (face)).col;
		super.colOp = face.colOp;
		setTex(tex);
	}

	public void setTex(TextureMap tex) {
		this.tex = tex;
	}

	public void paint(Graphics g) {
		for (int i = 0; i < super.nbPt; i++) {
			int num = super.pt[i] << 1;
			super.x[i] = super.parent.pos2d[num];
			super.y[i] = super.parent.pos2d[num + 1];
		}

		if (g instanceof Graphics3D) {
			Graphics3D g3 = (Graphics3D)g;
			g3.fillTextPolygon(super.x, super.y, super.nbPt, tex);
		} else {
			super.paint(g);
		}
	}
}
