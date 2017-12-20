package tom.graphic.ThreeD;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.StreamTokenizer;

// Referenced classes of package tom.graphic.ThreeD:
//			ObjReader, Object3D, Line3D, Face, 
//			Elem3D, Sphere3D, TextureMap

class ObjReaderM3D
	implements ObjReader {

	static Color tabColor[] = new Color[256];
	static boolean init = false;
	static int Tab_Source[][] = {
		{
			160, 160, 160
		}, {
			255, 0, 0
		}, {
			0, 0, 255
		}, {
			0, 255, 0
		}, {
			255, 255, 0
		}, {
			255, 0, 255
		}, {
			0, 255, 255
		}, {
			255, 255, 100
		}, {
			255, 200, 150
		}, {
			200, 250, 150
		}, {
			200, 150, 255
		}, {
			255, 128, 0
		}, {
			150, 200, 255
		}, {
			255, 0, 128
		}, {
			255, 128, 255
		}, {
			128, 128, 0
		}
	};

	public ObjReaderM3D() {
		if (!init) {
			for (int j = 0; j < 15; j++) {
				for (int i = 0; i < 16; i++) {
					int k;
					int l;
					int i1;
					if (i <= 10) {
						int j1 = 55 + i * 20;
						k = (Tab_Source[j][0] * j1) / 256;
						l = (Tab_Source[j][1] * j1) / 256;
						i1 = (Tab_Source[j][2] * j1) / 256;
						if (i == 0 && j == 0) {
							k = 0;
							l = 0;
							i1 = 0;
						}
					} else {
						int k1 = i - 10;
						k = Tab_Source[j][0] + ((255 - Tab_Source[j][0]) * k1) / 5;
						l = Tab_Source[j][1] + ((255 - Tab_Source[j][1]) * k1) / 5;
						i1 = Tab_Source[j][2] + ((255 - Tab_Source[j][2]) * k1) / 5;
					}
					tabColor[j * 16 + i] = new Color(k, l, i1);
				}

			}

		}
	}

	public boolean checkIfTypeIsKnow(InputStream inputstream, String s) {
		return s.toUpperCase().endsWith("M3D");
	}

	public void constructFromStream(Object3D object3d, InputStream inputstream, String s) throws IOException {
		StreamTokenizer streamtokenizer = new StreamTokenizer(inputstream);
		streamtokenizer.eolIsSignificant(false);
		streamtokenizer.ordinaryChar(58);
		streamtokenizer.commentChar(35);
		int i = 0;
		do
			switch (streamtokenizer.nextToken()) {
			case 10: // '\n'
			default:
				continue;

			case -2: 
				switch (i) {
				case 1: // '\001'
					double d = 0.0D;
					double d1 = 0.0D;
					double d2 = 0.0D;
					d = streamtokenizer.nval;
					streamtokenizer.nextToken();
					if (streamtokenizer.nextToken() == -2)
						d1 = streamtokenizer.nval;
					streamtokenizer.nextToken();
					if (streamtokenizer.nextToken() == -2)
						d2 = streamtokenizer.nval;
					object3d.addVert((float)d, (float)d1, (float)d2);
					break;

				case 2: // '\002'
					int j = 0;
					int k = 0;
					j = (int)streamtokenizer.nval;
					streamtokenizer.nextToken();
					if (streamtokenizer.nextToken() == -2)
						k = (int)streamtokenizer.nval;
					Color color = getColor(streamtokenizer);
					Line3D line3d = new Line3D(j, k, color);
					object3d.addElem(line3d);
					break;

				case 3: // '\003'
					Face face = new Face();
					for (int l = 0; l < 4; l++) {
						face.addPt((int)streamtokenizer.nval);
						streamtokenizer.nextToken();
						streamtokenizer.nextToken();
					}

					streamtokenizer.pushBack();
					face.col = getColor(streamtokenizer);
					Color color1 = getColor(streamtokenizer);
					if (color1 != null)
						face.colOp = color1;
					TextureMap texturemap = getMapped(streamtokenizer);
					if (texturemap != null)
						face.setTex(texturemap);
					object3d.addElem(face);
					while (streamtokenizer.ttype != 10) 
						streamtokenizer.nextToken();
					break;

				case 4: // '\004'
					int i1 = 0;
					int k1 = 0;
					i1 = (int)streamtokenizer.nval;
					streamtokenizer.nextToken();
					if (streamtokenizer.nextToken() == -2)
						k1 = (int)streamtokenizer.nval;
					Color color2 = getColor(streamtokenizer);
					Sphere3D sphere3d = new Sphere3D(i1, (float)k1 * 2.0F, color2);
					object3d.addElem(sphere3d);
					break;

				case 5: // '\005'
					int j1 = (int)streamtokenizer.nval;
					streamtokenizer.nextToken();
					int l1;
					if (streamtokenizer.nextToken() == -2)
						l1 = (int)streamtokenizer.nval;
					streamtokenizer.nextToken();
					if (streamtokenizer.nextToken() == -2)
						l1 = (int)streamtokenizer.nval;
					streamtokenizer.nextToken();
					if (streamtokenizer.nextToken() == -2)
						l1 = (int)streamtokenizer.nval;
					Color color3 = getColor(streamtokenizer);
					color3 = getColor(streamtokenizer);
					break;
				}
				continue;

			case -3: 
				if ("Points".equalsIgnoreCase(streamtokenizer.sval)) {
					i = 1;
					continue;
				}
				if (!"End".equalsIgnoreCase(streamtokenizer.sval)) {
					if ("Lines".equalsIgnoreCase(streamtokenizer.sval))
						i = 2;
					else
					if ("Faces".equalsIgnoreCase(streamtokenizer.sval)) {
						i = 3;
						streamtokenizer.eolIsSignificant(true);
					} else
					if ("Spheres".equalsIgnoreCase(streamtokenizer.sval)) {
						i = 4;
						streamtokenizer.eolIsSignificant(true);
					} else
					if ("Circles".equalsIgnoreCase(streamtokenizer.sval)) {
						i = 5;
						streamtokenizer.eolIsSignificant(true);
					} else
					if ("Mapped".equalsIgnoreCase(streamtokenizer.sval)) {
						streamtokenizer.eolIsSignificant(true);
						do
							streamtokenizer.nextToken();
						while (streamtokenizer.ttype != 10);
					}
					continue;
				}
				// fall through

			case -1: 
				inputstream.close();
				return;
			}
		while (true);
	}

	Color getColor(StreamTokenizer streamtokenizer) throws IOException {
		boolean flag = true;
		Color color = null;
		do {
			streamtokenizer.nextToken();
			switch (streamtokenizer.ttype) {
			case -2: 
				int i = (int)(streamtokenizer.nval - 1.0D) % 16;
				if (i < 0)
					i = 0;
				color = tabColor[16 * i + 10];
				flag = false;
				break;

			case -3: 
				if ("RGB".equalsIgnoreCase(streamtokenizer.sval)) {
					int j = 0;
					int k = 0;
					int l = 0;
					streamtokenizer.nextToken();
					if (streamtokenizer.nextToken() == -2) {
						j = (int)streamtokenizer.nval;
						if (j > 255)
							j = 255;
					}
					streamtokenizer.nextToken();
					if (streamtokenizer.nextToken() == -2) {
						k = (int)streamtokenizer.nval;
						if (k > 255)
							k = 255;
					}
					streamtokenizer.nextToken();
					if (streamtokenizer.nextToken() == -2) {
						l = (int)streamtokenizer.nval;
						if (l > 255)
							l = 255;
					}
					color = new Color(j, k, l);
				} else {
					streamtokenizer.pushBack();
				}
				flag = false;
				break;

			case -1: 
			case 10: // '\n'
				streamtokenizer.pushBack();
				flag = false;
				break;
			}
		} while (flag);
		return color;
	}

	TextureMap getMapped(StreamTokenizer streamtokenizer) throws IOException {
		TextureMap texturemap = null;
		streamtokenizer.quoteChar(34);
		streamtokenizer.nextToken();
		if (streamtokenizer.ttype == -3) {
			if ("MAPPED".equalsIgnoreCase(streamtokenizer.sval)) {
				streamtokenizer.nextToken();
				streamtokenizer.nextToken();
				texturemap = new TextureMap(streamtokenizer.sval);
				if (texturemap.pixImage == null)
					texturemap = null;
				streamtokenizer.nextToken();
			}
		} else {
			streamtokenizer.pushBack();
		}
		return texturemap;
	}

}
