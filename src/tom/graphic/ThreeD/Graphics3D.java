package tom.graphic.ThreeD;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.MemoryImageSource;
import java.io.PrintStream;
import java.util.Hashtable;

// Referenced classes of package tom.graphic.ThreeD:
//			PixImage, EdgeScan, Point2D, polInfo, 
//			TextureMap

public class Graphics3D extends Graphics {

	Graphics g;
	Rectangle clipRect;
	Image buff;
	int w;
	int h;
	int pix[];
	PixImage pixIma;
	int curCol;
	ColorModel cm;
	Color colCurC;
	int red;
	int green;
	int blue;
	long d05;
	private PixImage fontData;
	private int fontW;
	private int fontH;
	private Rectangle buffRect1;
	private Rectangle r;
	private Hashtable cacheImage;
	int DestY;
	int MaxVert;
	int NumVerts;
	static TextureMap res = null;

	public Graphics3D(Image image) {
		g = null;
		clipRect = null;
		curCol = 123;
		d05 = 0L;
		fontData = null;
		fontW = 8;
		fontH = 8;
		buffRect1 = new Rectangle();
		r = new Rectangle();
		cacheImage = new Hashtable();
		buff = image;
		w = buff.getWidth(null);
		h = buff.getHeight(null);
		if (image instanceof PixImage) {
			pixIma = (PixImage)image;
		} else {
			pixIma = new PixImage(w, h);
			g = image.getGraphics();
		}
		pix = pixIma.pix;
		cm = ColorModel.getRGBdefault();
		clipRect = new Rectangle(0, 0, w, h);
		d05 = DOUBLE_TO_FIXED(0.5D);
	}

	public void clearRect(int i, int j, int k, int l) {
		fillRect(i, j, k, l);
	}

	public void clipRect(int i, int j, int k, int l) {
		if (i < 0)
			i = 0;
		if (j < 0)
			j = 0;
		if (k > w)
			k = w;
		if (l > h)
			l = h;
		clipRect.setBounds(i, j, k, l);
	}

	public void copyArea(int i, int j, int k, int l, int i1, int j1) {
	}

	public Graphics create() {
		return null;
	}

	public void dispose() {
	}

	long DOUBLE_TO_FIXED(double d) {
		return (long)(d * 65536D + 0.5D);
	}

	public void drawArc(int i, int j, int k, int l, int i1, int j1) {
	}

	public void drawBytes(byte abyte0[], int i, int j, int k, int l) {
		drawString(new String(abyte0, 0, i, j), k, l);
	}

	public void drawChars(char ac[], int i, int j, int k, int l) {
		drawString(new String(ac, i, j), k, l);
	}

	public boolean drawImage(Image image, int i, int j, int k, int l, int i1, int j1, 
			int k1, int l1, Color color, ImageObserver imageobserver) {
		PixImage piximage = null;
		if (image instanceof PixImage) {
			piximage = (PixImage)image;
		} else {
			piximage = (PixImage)cacheImage.get(image);
			if (piximage == null) {
				piximage = new PixImage(image);
				cacheImage.put(image, piximage);
			}
		}
		int i2 = getIntFromColor(color);
		if (k - i == k1 - i1 && l - j == l1 - j1 && i >= 0 && j >= 0 && k <= w && l <= h && i1 >= 0 && j1 >= 0 && k1 <= piximage.width && l1 <= piximage.height) {
			if (i < clipRect.x) {
				int j2 = clipRect.x - i;
				i = clipRect.x;
				i1 += j2;
			}
			if (k > clipRect.width + clipRect.x) {
				int k2 = k - (clipRect.width + clipRect.x);
				k -= k2;
				k1 -= k2;
			}
			if (j < clipRect.y) {
				int l2 = clipRect.y - j;
				j = clipRect.y;
				j1 += l2;
			}
			if (l > clipRect.height + clipRect.y) {
				int i3 = l - (clipRect.height + clipRect.y);
				l -= i3;
				l1 -= i3;
			}
			int j3 = j1 * piximage.width;
			int k3 = j * w;
			for (int l3 = j; l3 < l; l3++) {
				for (int i4 = i; i4 < k; i4++) {
					int j4 = piximage.pix[((j3 + i4) - i) + i1];
					if (j4 != i2)
						pix[k3 + i4] = j4;
				}

				j3 += piximage.width;
				k3 += w;
			}

		}
		return true;
	}

	public boolean drawImage(Image image, int i, int j, int k, int l, int i1, int j1, 
			int k1, int l1, ImageObserver imageobserver) {
		return true;
	}

	public boolean drawImage(Image image, int i, int j, int k, int l, Color color, ImageObserver imageobserver) {
		return true;
	}

	public boolean drawImage(Image image, int i, int j, int k, int l, ImageObserver imageobserver) {
		return true;
	}

	public boolean drawImage(Image image, int i, int j, Color color, ImageObserver imageobserver) {
		return true;
	}

	public boolean drawImage(Image image, int i, int j, ImageObserver imageobserver) {
		drawImage(image, i, j, image.getWidth(null), image.getHeight(null), i, j, image.getWidth(null), image.getHeight(null), Color.black, null);
		return true;
	}

	public void drawLine(int i, int j, int k, int l) {
		int i1;
		int j1;
		int k1;
		int l1;
		if (l < j) {
			i1 = k;
			j1 = l;
			k1 = i;
			l1 = j;
		} else {
			i1 = i;
			j1 = j;
			k1 = k;
			l1 = l;
		}
		if (j1 < clipRect.height + clipRect.y && l1 >= clipRect.y && (i1 < clipRect.width + clipRect.x && k1 >= clipRect.x || k1 < clipRect.width + clipRect.x && i1 >= clipRect.x)) {
			if (i1 == k1) {
				LineVert(i1, j1, l1);
				return;
			}
			if (l1 < h && j1 >= 0 && j1 < h && l1 >= 0 && i1 < w && i1 >= 0 && k1 < w && k1 >= 0)
				if (j1 == l1) {
					LineHoriz(i1, k1, j1);
				} else {
					int k3 = -1;
					int l3 = 0;
					int i2 = l1 - j1;
					int j2 = k1 - i1;
					byte byte0 = ((byte)(j2 <= 0 ? -1 : 1));
					if (j2 < 0)
						j2 = -j2;
					int i3 = 0;
					int k2 = i1;
					int j3 = j1;
					l3 = j3 * w + k2;
					if (i2 > j2) {
						i3 += i2 / 2;
						for (j3 = j1; j3 <= l1; j3++) {
							if ((k3 & 1) != 0) {
								pix[l3] = curCol;
								k3 = k3 >> 1 | 0x8000;
							} else {
								k3 >>= 1;
							}
							l3 += w;
							if (i3 <= 0) {
								i3 += i2 - j2;
								k2 += byte0;
								l3 += byte0;
							} else {
								i3 -= j2;
							}
						}

					} else {
						i3 += (j2 - i2) / 2;
						for (int l2 = i1; l2 != k1; l2 += byte0) {
							if ((k3 & 1) != 0) {
								pix[l3] = curCol;
								k3 = k3 >> 1 | 0x8000;
							} else {
								k3 >>= 1;
							}
							l3 += byte0;
							if (i3 <= 0) {
								i3 += j2 - i2;
								j3++;
								l3 += w;
							} else {
								i3 -= i2;
							}
						}

					}
				}
		}
	}

	public void drawOval(int i, int j, int k, int l) {
	}

	public void drawPolygon(int ai[], int ai1[], int i) {
		for (int j = 0; j < i - 1; j++)
			drawLine(ai[j], ai1[j], ai[j + 1], ai1[j + 1]);

	}

	public void drawPolyline(int ai[], int ai1[], int i) {
		for (int j = 0; j < i - 1; j++)
			drawLine(ai[j], ai1[j], ai[j + 1], ai1[j + 1]);

	}

	public void drawRoundRect(int i, int j, int k, int l, int i1, int j1) {
	}

	public void drawString(String s, int i, int j) {
		if (fontData != null) {
			int k = fontData.width / fontW;
			byte abyte0[] = s.getBytes();
			for (int l = 0; l < abyte0.length; l++) {
				byte byte0 = abyte0[l];
				byte0 = (byte)((char)byte0 - 32);
				if (byte0 >= 0 && byte0 < 96) {
					int i1 = (byte0 % k) * fontW;
					int j1 = (byte0 / k) * fontH;
					drawImage(fontData, i + l * fontW, j, i + (l + 1) * fontW, j + fontH, i1, j1, i1 + fontW, j1 + fontH, Color.white, null);
				}
			}

		} else {
			System.out.println("you must provide a font with setFont(PixImage,int,int) before using drawString");
		}
	}

	void DrawTexturedPolygon(Point2D apoint2d[], TextureMap texturemap) {
		EdgeScan edgescan = new EdgeScan();
		EdgeScan edgescan1 = new EdgeScan();
		NumVerts = apoint2d.length;
		if (apoint2d.length < 3)
			return;
		int i = 32767;
		int j = -32768;
		int k = 0;
		for (int l = 0; l < apoint2d.length; l++) {
			if (apoint2d[l].y < i) {
				i = apoint2d[l].y;
				k = l;
			}
			if (apoint2d[l].y > j) {
				j = apoint2d[l].y;
				MaxVert = l;
			}
		}

		if (i >= j)
			return;
		DestY = i;
		long l1 = (long)DestY * (long)w;
		edgescan.Direction = -1;
		SetUpEdge(edgescan, k, apoint2d, texturemap);
		edgescan1.Direction = 1;
		SetUpEdge(edgescan1, k, apoint2d, texturemap);
		do {
			if (DestY >= h)
				return;
			if (DestY >= 0)
				ScanOutLine(edgescan, edgescan1, texturemap, l1);
			if (StepEdge(edgescan, apoint2d, texturemap) && StepEdge(edgescan1, apoint2d, texturemap)) {
				DestY++;
				l1 += w;
			} else {
				return;
			}
		} while (true);
	}

	public void drawTextureSlice(int i, int j, int k, int l, PixImage piximage) {
		try {
			int j2 = l;
			if (j > k) {
				int k1 = k;
				k = j;
				j = k1;
			}
			if (j < h && k >= 0 && i >= 0 && i < w) {
				int k2 = k - j;
				int l2 = 0;
				if (k2 < 0)
					return;
				if (k2 > 0x10000)
					return;
				if (j < 0) {
					l2 = -j;
					j = 0;
				}
				if (k >= h)
					k = h - 1;
				int j1 = k - j;
				int i1 = j * w + i;
				int i3 = (piximage.height << 8) / k2;
				int j3 = l2 * i3;
				int k3 = piximage.getShiftWidth();
				if (k3 == 0) {
					for (int l1 = 0; l1 < j1; l1++) {
						pix[i1] = piximage.pix[j2 + piximage.width * (j3 >> 8)];
						i1 += w;
						j3 += i3;
					}

				} else {
					for (int i2 = 0; i2 < j1; i2++) {
						pix[i1] = piximage.pix[j2 + ((j3 >> 8) << k3)];
						i1 += w;
						j3 += i3;
					}

				}
			}
		}
		catch (Exception _ex) {
			System.out.println("FIXME...");
		}
	}

	public void drawTextureSliceTransp(int i, int j, int k, int l, PixImage piximage, int i1) {
		try {
			int k2 = (l * piximage.width) / 128;
			if (j > k) {
				int l1 = k;
				k = j;
				j = l1;
			}
			if (j < h && k >= 0 && i >= 0 && i < w) {
				int l2 = k - j;
				int i3 = 0;
				if (l2 < 0)
					return;
				if (l2 > 0x10000)
					return;
				if (j < 0) {
					i3 = -j;
					j = 0;
				}
				if (k >= h)
					k = h - 1;
				int j1 = j * w + i;
				int k1 = k - j;
				int j3 = (piximage.height << 8) / l2;
				int k3 = i3 * j3;
				int l3 = piximage.getShiftWidth();
				if (l3 == 0) {
					for (int i2 = 0; i2 < k1; i2++) {
						int i4 = piximage.pix[k2 + piximage.width * (k3 >> 8)];
						if (i4 != i1)
							pix[j1] = i4;
						j1 += w;
						k3 += j3;
					}

				} else {
					for (int j2 = 0; j2 < k1; j2++) {
						int j4 = piximage.pix[k2 + ((k3 >> 8) << l3)];
						if (j4 != i1)
							pix[j1] = j4;
						j1 += w;
						k3 += j3;
					}

				}
			}
		}
		catch (Exception _ex) {
			System.out.println("FIXME...");
		}
	}

	public void fill3DRect(int i, int j, int k, int l, boolean flag) {
		fillRect(i, j, k, l);
	}

	public void fillArc(int i, int j, int k, int l, int i1, int j1) {
	}

	public void fillOval(int i, int j, int k, int l) {
	}

	public void fillPolygon(int ai[], int ai1[], int i) {
		int k2 = 0;
		polInfo polinfo = new polInfo();
		int j1 = 32000;
		int k1 = 0;
		int i2 = j1;
		int j2 = k1;
		for (int j = 0; j < i; j++) {
			if (j1 > ai1[j]) {
				j1 = ai1[j];
				k2 = j;
			}
			if (k1 < ai1[j])
				k1 = ai1[j];
			if (i2 > ai[j])
				i2 = ai[j];
			if (j2 < ai[j])
				j2 = ai[j];
		}

		polinfo.NB_PT_POLY = i;
		polinfo.x = ai;
		polinfo.y = ai1;
		polinfo.oldPt = 0;
		if (j1 < h && k1 >= 0 && i2 < w && j2 >= 0) {
			if (k1 >= h)
				k1 = h - 1;
			int l1 = k1 - j1;
			long l = j1 * w;
			if (l1 != 0) {
				int k3 = k2 + 1;
				int i4 = (i + k2) - 1;
				polinfo.numPt = i4;
				polinfo.oldPt = k2;
				polinfo.findNext();
				i4 = polinfo.numPt;
				long l2 = polinfo.Dx;
				long l4 = polinfo.Dy;
				long l6 = polinfo.X;
				polinfo.numPt = k3;
				polinfo.oldPt = k2;
				polinfo.findNext();
				k3 = polinfo.numPt;
				long l3 = polinfo.Dx;
				long l5 = polinfo.Dy;
				long l7 = polinfo.X;
				for (int i1 = j1; i1 < k1; i1++) {
					if (i1 >= 0) {
						int i5 = (int)(l6 >> 8);
						int j5 = (int)(l7 >> 8);
						if (i5 > j5) {
							int k4 = j5;
							j5 = i5;
							i5 = k4;
						}
						if (i5 < 0)
							i5 = 0;
						if (j5 >= w)
							j5 = w - 1;
						int k5 = (int)(l + (long)i5);
						int j4 = j5 - i5;
						for (int k = 0; k <= j4; k++)
							pix[k5++] = curCol;

					}
					l6 += l2;
					l7 += l3;
					l4--;
					if (l4 == 0L) {
						int i3 = i4;
						i4 += i - 1;
						polinfo.numPt = i4;
						polinfo.oldPt = i3;
						polinfo.findNext();
						i4 = polinfo.numPt;
						l2 = polinfo.Dx;
						l4 = polinfo.Dy;
						l6 = polinfo.X;
					}
					l5--;
					if (l5 == 0L) {
						int j3 = k3;
						k3++;
						polinfo.numPt = k3;
						polinfo.oldPt = j3;
						polinfo.findNext();
						k3 = polinfo.numPt;
						l3 = polinfo.Dx;
						l5 = polinfo.Dy;
						l7 = polinfo.X;
					}
					l += w;
				}

			}
		}
	}

	public void fillPolygon(Polygon polygon) {
	}

	public void fillRect(int i, int j, int k, int l) {
		buffRect1.x = i;
		buffRect1.y = j;
		buffRect1.width = k;
		buffRect1.height = l;
		r = buffRect1.intersection(clipRect);
		int i1 = r.y * w + r.x;
		for (; r.height > 0; r.height--) {
			for (int j1 = 0; j1 < r.width; j1++)
				pix[i1 + j1] = curCol;

			i1 += w;
		}

	}

	public void fillRoundRect(int i, int j, int k, int l, int i1, int j1) {
	}

	public void fillTextPolygon(int ai[], int ai1[], int i, TextureMap texturemap) {
		try {
			Point2D apoint2d[] = new Point2D[i];
			for (int j = 0; j < i; j++) {
				apoint2d[j] = new Point2D();
				apoint2d[j].x = ai[j];
				apoint2d[j].y = ai1[j];
			}

			DrawTexturedPolygon(apoint2d, texturemap);
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public void finalize() {
		dispose();
	}

	double FIXED_TO_DOUBLE(long l) {
		return (double)l / 65536D;
	}

	int FIXED_TO_INT(long l) {
		return (int)(l >> 16);
	}

	long FixedDiv(long l, long l1) {
		return l / (l1 >> 8) << 8;
	}

	long FixedMul(long l, long l1) {
		return (l >> 8) * (l1 >> 8);
	}

	public Shape getClip() {
		Shape shape = null;
		return shape;
	}

	public Rectangle getClipBounds() {
		Rectangle rectangle = null;
		return rectangle;
	}

	public Rectangle getClipRect() {
		return new Rectangle(w, h);
	}

	public Color getColor() {
		return colCurC;
	}

	static TextureMap getFake() {
		if (res != null)
			return res;
		res = new TextureMap();
		res.pixImage = new PixImage(128, 128);
		for (int i = 0; i < 128; i++) {
			for (int j = 0; j < 128; j++)
				res.pixImage.pix[i * 128 + j] = i + j | 0xff000000;

		}

		res.pt = new Point2D[4];
		res.pt[0] = new Point2D();
		res.pt[1] = new Point2D();
		res.pt[2] = new Point2D();
		res.pt[3] = new Point2D();
		res.pt[0].x = 0;
		res.pt[0].y = 0;
		res.pt[1].x = 127;
		res.pt[1].y = 0;
		res.pt[2].x = 127;
		res.pt[2].y = 127;
		res.pt[3].x = 0;
		res.pt[3].y = 127;
		return res;
	}

	public Font getFont() {
		return null;
	}

	public FontMetrics getFontMetrics(Font font) {
		return null;
	}

	public int getIntFromColor(Color color) {
		red = color.getRed();
		blue = color.getBlue();
		green = color.getGreen();
		return 0xff000000 + (red << 16) + (green << 8) + blue;
	}

	public PixImage getPixImage() {
		return pixIma;
	}

	long INT_TO_FIXED(int i) {
		return (long)i << 16;
	}

	void LineHoriz(int i, int j, int k) {
		if (i > j) {
			int j1 = j;
			j = i;
			i = j1;
		}
		j++;
		if (i < clipRect.width + clipRect.x && j >= clipRect.x && k >= clipRect.y && k < clipRect.height + clipRect.y) {
			if (i < clipRect.x)
				i = clipRect.x;
			if (j >= clipRect.width + clipRect.x)
				j = (clipRect.width + clipRect.x) - 1;
			int l = k * w + i;
			int i1 = j - i;
			for (int k1 = 0; k1 < i1; k1++)
				pix[l++] = curCol;

		}
	}

	void LineVert(int i, int j, int k) {
		if (j > k) {
			int j1 = k;
			k = j;
			j = j1;
		}
		if (j < h && k >= 0 && i >= 0 && i < w) {
			if (j < 0)
				j = 0;
			if (k >= w)
				k = h - 1;
			int l = j * w + i;
			int i1 = k - j;
			for (int k1 = 0; k1 < i1; k1++) {
				pix[l] = curCol;
				l += w;
			}

		}
	}

	void ScanOutLine(EdgeScan edgescan, EdgeScan edgescan1, TextureMap texturemap, long l) {
		int j = edgescan.DestX;
		int k = edgescan1.DestX;
		if (k <= 0 || j >= w)
			return;
		if (k - j <= 0)
			return;
		long l1 = edgescan.SourceX + d05;
		long l2 = edgescan.SourceY + d05;
		long l3 = k - j << 16;
		long l4 = (edgescan1.SourceX - l1) / (l3 >> 8) << 8;
		long l5 = (edgescan1.SourceY - l2) / (l3 >> 8) << 8;
		l1 += l4 >> 1;
		l2 += l5 >> 1;
		if (k > w)
			k = w;
		if (j < 0) {
			l1 += (l4 >> 8) * (long)(-j << 8);
			l2 += (l5 >> 8) * (long)(-j << 8);
			j = 0;
		}
		int i = j;
		int i1 = (int)(l + (long)i);
		int ai[] = texturemap.pixImage.pix;
		int j1 = texturemap.pixImage.width;
		int k1 = k - j;
		if (texturemap.pixImage.width != 256)
			while (k1-- > 0)  {
				pix[i1++] = ai[(int)((l2 >> 16) * (long)j1 + (l1 >> 16))];
				l1 += l4;
				l2 += l5;
			}
		else
			while (k1-- > 0)  {
				pix[i1++] = ai[(int)(((l2 >> 16) << 8) + (l1 >> 16))];
				l1 += l4;
				l2 += l5;
			}
	}

	public void setClip(int i, int j, int k, int l) {
		clipRect(i, j, k, l);
	}

	public void setClip(Shape shape) {
	}

	public void setColor(Color color) {
		colCurC = color;
		red = color.getRed();
		blue = color.getBlue();
		green = color.getGreen();
		curCol = 0xff000000 + (red << 16) + (green << 8) + blue;
	}

	public void setFont(Font font) {
	}

	public void setFont(PixImage piximage, int i, int j) {
		fontData = piximage;
		fontW = i;
		fontH = j;
	}

	public void setPaintMode() {
	}

	boolean SetUpEdge(EdgeScan edgescan, int i, Point2D apoint2d[], TextureMap texturemap) {
		do {
			if (i == MaxVert)
				return false;
			int j = i + edgescan.Direction;
			if (j >= apoint2d.length)
				j = 0;
			else
			if (j < 0)
				j = apoint2d.length - 1;
			if (apoint2d[j].y < apoint2d[i].y)
				apoint2d[j].y = apoint2d[i].y;
			if ((edgescan.RemainingScans = apoint2d[j].y - apoint2d[i].y) != 0) {
				long l = (long)edgescan.RemainingScans << 16;
				edgescan.CurrentEnd = j;
				edgescan.SourceX = (long)texturemap.pt[i].x << 16;
				edgescan.SourceY = (long)texturemap.pt[i].y << 16;
				edgescan.SourceStepX = (((long)texturemap.pt[j].x << 16) - edgescan.SourceX) / (l >> 8) << 8;
				edgescan.SourceStepY = (((long)texturemap.pt[j].y << 16) - edgescan.SourceY) / (l >> 8) << 8;
				edgescan.DestX = apoint2d[i].x;
				int k;
				if ((k = apoint2d[j].x - apoint2d[i].x) < 0) {
					edgescan.DestXDirection = -1;
					k = -k;
					edgescan.DestXErrTerm = 1 - edgescan.RemainingScans;
					edgescan.DestXIntStep = -(k / edgescan.RemainingScans);
				} else {
					edgescan.DestXDirection = 1;
					edgescan.DestXErrTerm = 0;
					edgescan.DestXIntStep = k / edgescan.RemainingScans;
				}
				edgescan.DestXAdjUp = k % edgescan.RemainingScans;
				edgescan.DestXAdjDown = edgescan.RemainingScans;
				return true;
			}
			i = j;
		} while (true);
	}

	public void setXORMode(Color color) {
	}

	boolean StepEdge(EdgeScan edgescan, Point2D apoint2d[], TextureMap texturemap) {
		if (--edgescan.RemainingScans == 0)
			return SetUpEdge(edgescan, edgescan.CurrentEnd, apoint2d, texturemap);
		edgescan.SourceX += edgescan.SourceStepX;
		edgescan.SourceY += edgescan.SourceStepY;
		edgescan.DestX += edgescan.DestXIntStep;
		if ((edgescan.DestXErrTerm += edgescan.DestXAdjUp) > 0) {
			edgescan.DestX += edgescan.DestXDirection;
			edgescan.DestXErrTerm -= edgescan.DestXAdjDown;
		}
		return true;
	}

	public String toString() {
		return getClass().getName() + "[font=" + getFont() + ",color=" + getColor() + "]";
	}

	public void translate(int i, int j) {
	}

	public void updateBuf() {
		if (g != null) {
			g.clearRect(0, 0, w, h);
			Image image = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(w, h, cm, pix, 0, w));
			g.drawImage(image, 0, 0, null);
			image.flush();
			image = null;
		}
	}

}
