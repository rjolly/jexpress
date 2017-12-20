package tom.graphic.ThreeD;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StreamTokenizer;

// Referenced classes of package tom.graphic.ThreeD:
//			ObjReader, Object3D, Point3D, TextureMap, 
//			Face, Elem3D

public class ObjReaderRWX
	implements ObjReader {

	float curFac;

	public ObjReaderRWX() {
		curFac = 1.0F;
	}

	public boolean checkIfTypeIsKnow(InputStream inputstream, String s) {
		return s.toUpperCase().endsWith("RWX");
	}

	public void constructFromStream(Object3D object3d, InputStream inputstream, String s) throws IOException {
		Color color = Color.white;
		TextureMap texturemap = null;
		StreamTokenizer streamtokenizer = new StreamTokenizer(inputstream);
		streamtokenizer.eolIsSignificant(true);
		streamtokenizer.slashSlashComments(false);
		streamtokenizer.ordinaryChar(47);
		streamtokenizer.commentChar(35);
		do
			switch (streamtokenizer.nextToken()) {
			case -3: 
				String s1 = streamtokenizer.sval.toUpperCase();
				if ("TRANSFORMBEGIN".equals(s1)) {
					Object3D object3d1 = new Object3D();
					object3d.addChild(object3d1);
					constructFromStream(object3d1, inputstream, s);
				} else
				if ("TRANSFORM".equals(s1)) {
					float af[][] = new float[4][4];
					for (int i = 0; i < 4; i++) {
						for (int l = 0; l < 4; l++) {
							streamtokenizer.nextToken();
							af[i][l] = (float)streamtokenizer.nval;
						}

					}

					object3d.pos.x = af[3][0];
					object3d.pos.y = af[3][1];
					object3d.pos.z = af[3][2];
					if (af[3][3] != 0.0F)
						curFac = af[3][3];
					System.out.println(curFac);
				} else {
					if ("TRANSFORMEND".equals(s1))
						return;
					if (!"TEXTUREMODE".equals(s1))
						if ("TEXTURE".equals(s1)) {
							streamtokenizer.nextToken();
							String s2 = streamtokenizer.sval;
							String s3 = new String("NULL");
							if (!s2.toUpperCase().equals(s3)) {
								s2 = s2 + ".jpg";
								texturemap = new TextureMap(s2);
							}
						} else
						if ("VERTEX".equals(s1) || "VERTEXEXT".equals(s1)) {
							double d = 0.0D;
							double d2 = 0.0D;
							double d4 = 0.0D;
							if (streamtokenizer.nextToken() == -2) {
								d = getFloat(streamtokenizer) * curFac;
								if (streamtokenizer.nextToken() == -2) {
									d2 = getFloat(streamtokenizer) * curFac;
									if (streamtokenizer.nextToken() == -2)
										d4 = getFloat(streamtokenizer) * curFac;
								}
							}
							object3d.addVert((float)d, (float)d2, (float)d4);
							while (streamtokenizer.ttype != 10 && streamtokenizer.ttype != -1) 
								streamtokenizer.nextToken();
						} else
						if ("COLOR".equals(s1)) {
							double d1 = 0.0D;
							double d3 = 0.0D;
							double d5 = 0.0D;
							if (streamtokenizer.nextToken() == -2) {
								d1 = streamtokenizer.nval;
								if (streamtokenizer.nextToken() == -2) {
									d3 = streamtokenizer.nval;
									if (streamtokenizer.nextToken() == -2)
										d5 = streamtokenizer.nval;
								}
							}
							color = new Color((float)d1, (float)d3, (float)d5);
							while (streamtokenizer.ttype != 10 && streamtokenizer.ttype != -1) 
								streamtokenizer.nextToken();
						} else
						if ("QUAD".equals(s1)) {
							Face face = new Face();
							if (streamtokenizer.nextToken() == -2) {
								for (int j = 0; j < 4; j++) {
									face.addPt((int)streamtokenizer.nval - 1);
									streamtokenizer.nextToken();
								}

								face.col = color;
								object3d.addElem(face);
								if (texturemap != null)
									face.setTex(texturemap);
							}
						} else
						if ("TRIANGLE".equals(streamtokenizer.sval.toUpperCase())) {
							Face face1 = new Face();
							if (streamtokenizer.nextToken() == -2) {
								for (int k = 0; k < 3; k++) {
									face1.addPt((int)streamtokenizer.nval - 1);
									streamtokenizer.nextToken();
								}

								face1.col = color;
								object3d.addElem(face1);
								if (texturemap != null)
									face1.setTex(texturemap);
							}
						} else
						if ("POLYGON".equals(streamtokenizer.sval.toUpperCase())) {
							Face face2 = new Face();
							if (streamtokenizer.nextToken() == -2) {
								do {
									streamtokenizer.nextToken();
									if (streamtokenizer.ttype == -2)
										face2.addPt((int)streamtokenizer.nval - 1);
								} while (streamtokenizer.ttype == -2);
								face2.col = color;
								object3d.addElem(face2);
								if (texturemap != null)
									face2.setTex(texturemap);
							}
						} else {
							while (streamtokenizer.nextToken() != 10 && streamtokenizer.ttype != -1) ;
						}
				}
				break;

			default:
				inputstream.close();
				return;

			case 10: // '\n'
				break;
			}
		while (true);
	}

	float getFloat(StreamTokenizer streamtokenizer) throws IOException {
		float f = (float)streamtokenizer.nval;
		streamtokenizer.nextToken();
		if (streamtokenizer.ttype == -3 && streamtokenizer.sval.charAt(0) == 'e') {
			String s = streamtokenizer.sval.substring(1);
			f = (float)Math.pow(f, Integer.parseInt(s));
			f = 0.0F;
		} else {
			streamtokenizer.pushBack();
		}
		return f;
	}
}
