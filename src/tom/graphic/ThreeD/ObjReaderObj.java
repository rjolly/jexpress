package tom.graphic.ThreeD;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.StreamTokenizer;

// Referenced classes of package tom.graphic.ThreeD:
//			ObjReader, Object3D, Face, Elem3D, 
//			Line3D

class ObjReaderObj
	implements ObjReader {

	public ObjReaderObj() {
	}

	public boolean checkIfTypeIsKnow(InputStream inputstream, String s) {
		return s.toUpperCase().endsWith("OBJ");
	}

	public void constructFromStream(Object3D object3d, InputStream inputstream, String s) throws IOException {
		StreamTokenizer streamtokenizer = new StreamTokenizer(inputstream);
		streamtokenizer.eolIsSignificant(true);
		streamtokenizer.slashSlashComments(false);
		streamtokenizer.ordinaryChar(47);
		streamtokenizer.commentChar(35);
label0:
		do {
label1:
			{
label2:
				do {
label3:
					{
label4:
						do {
label5:
							do
								switch (streamtokenizer.nextToken()) {
								default:
									break label4;

								case 10: // '\n'
									break;

								case -3: 
									if (!"v".equals(streamtokenizer.sval))
										break label5;
									double d = 0.0D;
									double d1 = 0.0D;
									double d2 = 0.0D;
									if (streamtokenizer.nextToken() == -2) {
										d = streamtokenizer.nval;
										if (streamtokenizer.nextToken() == -2) {
											d1 = streamtokenizer.nval;
											if (streamtokenizer.nextToken() == -2)
												d2 = streamtokenizer.nval;
										}
									}
									object3d.addVert((float)d, (float)d1, (float)d2);
									while (streamtokenizer.ttype != 10 && streamtokenizer.ttype != -1) 
										streamtokenizer.nextToken();
									break;
								}
							while (true);
							if (!"f".equals(streamtokenizer.sval) && !"fo".equals(streamtokenizer.sval))
								break label3;
							Face face = new Face();
							do {
								int j;
								for (; streamtokenizer.nextToken() == -2; face.addPt(j - 1))
									j = (int)streamtokenizer.nval;

								if (streamtokenizer.ttype != 47)
									break;
								streamtokenizer.nextToken();
								if (streamtokenizer.ttype != 47)
									streamtokenizer.nextToken();
								streamtokenizer.nextToken();
							} while (true);
							face.col = Color.green;
							object3d.addElem(face);
						} while (streamtokenizer.ttype == 10);
						break label2;
					}
					if (!"l".equals(streamtokenizer.sval))
						break label1;
					int i = -1;
					int k = -1;
					byte byte0 = -1;
					do {
						while (streamtokenizer.nextToken() == -2)  {
							int l = (int)streamtokenizer.nval;
							if (k >= 0)
								new Line3D(k - 1, l - 1, Color.green);
							if (i < 0)
								i = l;
							k = l;
						}
						if (streamtokenizer.ttype != 47)
							break;
						streamtokenizer.nextToken();
					} while (true);
					if (i >= 0) {
						Line3D line3d = new Line3D(i - 1, k - 1, Color.green);
						object3d.addElem(line3d);
					}
				} while (streamtokenizer.ttype == 10);
				break label0;
			}
			while (streamtokenizer.nextToken() != 10 && streamtokenizer.ttype != -1) ;
		} while (true);
		inputstream.close();
	}
}
