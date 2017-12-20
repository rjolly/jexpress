package tom.graphic.ThreeD;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.io.StringBufferInputStream;
import java.util.Vector;

// Referenced classes of package tom.graphic.ThreeD:
//			Point3D, View3D, Object3D, World3D, 
//			Graphics3D, View3DEvent, View3DListener, Elem3D

public class View3DCommon extends Canvas
	implements Serializable {

	protected World3D world;
	protected View3D view;
	private boolean doubleBuffer;
	private boolean eventDispatching;
	private Image buf;
	private Graphics gBuf;
	private boolean painted;
	protected int prevx;
	protected int prevy;
	protected String mdname;
	protected String message;
	protected Image backgIma;
	protected Image backgBuff;
	protected Image nullBuff;
	public static final int BACKG_NONE = 0;
	public static final int BACKG_CENTERED = 1;
	public static final int BACKG_TILED = 2;
	public static final int BACKG_STRETCHED = 3;
	public static final int BACKG_DIRECTION = 4;
	protected int backgMode;
	protected boolean flgFastMove;
	protected boolean flgModeEdit;
	protected boolean currentMove;
	private Vector Listeners;
	public boolean textureEnabled;
	static String defaultObj = "NAME:  DIRIGABLE\nPoints:\n\t-40,40,0,-40,20,40,-40,-20,40,\n\t-40,-40,0,-40,-20,-40,-40,20,-40\n\t40,40,0,40,20,40,40,-20,40,\n\t40,-40,0,40,-20,-40,40,20,-40\n\t-70,0,0,70,0,0\n\n\t10,-30,60,10,-40,70,10,-50,60,10,-40,50\n\t40,-30,60,40,-40,70,40,-50,60,40,-40,50\n\t\n\t10,-30,-60,10,-40,-70,10,-50,-60,10,-40,-50\n\t40,-30,-60,40,-40,-70,40,-50,-60,40,-40,-50\n\t\n\t45,-20,50,45,-20,70,45,-60,50,45,-60,70\n\t45,-20,-50,45,-20,-70,45,-60,-50,45,-60,-70\n\n\t20,-20,-40,20,-33,-57,20,-37,-54,20,-30,-20\n\t35,-20,-40,35,-33,-57,35,-37,-54,35,-30,-20\n\t20,-20,40,20,-33,57,20,-37,54,20,-30,20\n\t35,-20,40,35,-33,57,35,-37,54,35,-30,20\n\n\t45,-40,60\n\t45,-40,-60\nLines:\n\t38,39,30,       40,41,30\n\t42,43,30,       44,45,30\n\t46,47,30,       48,49,30\n\t50,51,30,       52,53,30\nFaces:\n\n\t0,1,7,6,        1,                     \n\t1,2,8,7,        1,    \n\t2,3,9,8,        15,   \n\n\t0,12,1,1,       20,             \n\t1,12,2,2,       15,   \n\t2,12,3,3,       20,   \n\t3,12,4,4,       15,   \n\t4,12,5,5,       20,     \n\t5,12,0,0,       15,     \n\n\t14,15,19,18,    18,             \n\t15,16,20,19,    17,     \n\t14,17,16,15,    15,     \n\n\t23,22,26,27,    15,             \n\t24,23,27,28,    17,     \n\t22,23,24,25,    18,     \n\n\t30,31,54,54,    10,10,    \n\t32,33,54,54,    10,10,\n\t34,35,55,55,    10,10,\n\t36,37,55,55,    10,10,    \n\n\t3,4,10,9,        20     \n\t4,5,11,10,15,\n\t5,0,6,11, 20,\n\n\t11,13,10,10,20, \n\t10,13,9,9,  15,\n\t9,13,8,8,   20,\n\t8,13,7,7,   15,\n\t7,13,6,6,   20,\n\t6,13,11,11, 15,\n\n\t16,17,21,20,18, \n\t17,14,18,21,17,\n\t18,19,20,21,15,\n\n\t25,24,28,29,18, \n\t22,25,29,26,17,\n\t29,28,27,26,15\n";
	private boolean rotX;
	private boolean rotY;
	private boolean rotZ;
	private boolean moveX;
	private boolean moveY;
	private boolean moveZ;

	public View3DCommon() {
		world = null;
		view = null;
		doubleBuffer = true;
		eventDispatching = false;
		painted = true;
		message = null;
		backgIma = null;
		backgBuff = null;
		nullBuff = null;
		backgMode = 0;
		flgFastMove = false;
		flgModeEdit = true;
		Listeners = new Vector();
		textureEnabled = false;
		rotX = true;
		rotY = true;
		rotZ = true;
		moveX = true;
		moveY = true;
		moveZ = true;
		init(null, true);
	}

	public View3DCommon(boolean flag) {
		world = null;
		view = null;
		doubleBuffer = true;
		eventDispatching = false;
		painted = true;
		message = null;
		backgIma = null;
		backgBuff = null;
		nullBuff = null;
		backgMode = 0;
		flgFastMove = false;
		flgModeEdit = true;
		Listeners = new Vector();
		textureEnabled = false;
		rotX = true;
		rotY = true;
		rotZ = true;
		moveX = true;
		moveY = true;
		moveZ = true;
		init(null, flag);
	}

	public synchronized void addView3DListener(View3DListener view3dlistener) {
		if (Listeners == null)
			Listeners = new Vector();
		Listeners.addElement(view3dlistener);
		System.out.println("Add listener");
	}

	private void affDebug(String s) {
		System.out.println(s);
	}

	float checkAngle(float f) {
		for (; f > 360F; f -= 360F);
		for (; f < 0.0F; f += 360F);
		return f;
	}

	void checkAngle(Point3D point3d) {
		point3d.x = checkAngle(point3d.x);
		point3d.y = checkAngle(point3d.y);
		point3d.z = checkAngle(point3d.z);
	}

	protected void commonMouseDown(int i, int j) {
		prevx = i;
		prevy = j;
		if (flgFastMove) {
			currentMove = view.flgWireFrame;
			view.flgWireFrame = true;
		}
		notifyView3D(world.isInside(i, j));
	}

	protected boolean commonMouseDrag(int i, int j, int k) {
		if (flgModeEdit) {
			if ((k & 1) == 1) {
				float f = 0.0F;
				float f4 = 0.0F;
				if (moveX)
					f = prevx - i;
				if (moveY)
					f4 = prevy - j;
				if (f != 0.0F || f4 != 0.0F) {
					view.deplOf(-f, -f4, 0.0F);
					repaint();
				}
			} else
			if ((k & 4) == 4) {
				if (moveZ) {
					float f1 = prevy - j;
					view.deplOf(0.0F, 0.0F, f1);
				}
				if (view.pos.z < -17F)
					view.pos.z = -17F;
				if (rotZ) {
					float f2 = ((float)(i - prevx) * 360F) / (float)size().height;
					view.rotateOf(0.0F, 0.0F, f2);
				}
				repaint();
			} else {
				float f3 = 0.0F;
				float f5 = 0.0F;
				if (rotX)
					f3 = ((float)(prevy - j) * 360F) / (float)size().width;
				if (rotY)
					f5 = ((float)(i - prevx) * 360F) / (float)size().height;
				if (f3 != 0.0F || f5 != 0.0F) {
					view.rotateOf(f3, -f5, 0.0F);
					if (painted) {
						painted = false;
						repaint();
					}
				}
			}
			prevx = i;
			prevy = j;
		}
		return true;
	}

	protected void commonMouseUp(int i, int j) {
		if (flgFastMove) {
			view.flgWireFrame = currentMove;
			repaint();
		}
	}

	public void destroy() {
		if (doubleBuffer)
			gBuf.dispose();
	}

	public void doLayout() {
		layout();
	}

	private synchronized void drawImageChecked(Graphics g, Image image, int i, int j, int k, int l) {
		prepareImage(image, k, l, null);
		while ((checkImage(image, k, l, null) & 0x20) != 32)  {
			System.out.println(checkImage(image, k, l, null));
			try {
				wait(100L);
			}
			catch (Exception _ex) { }
		}
		g.drawImage(image, i, j, k, l, null);
	}

	public float getAngleX() {
		return view.angle.x;
	}

	public float getAngleY() {
		return view.angle.y;
	}

	public float getAngleZ() {
		return view.angle.z;
	}

	public boolean getBackFace() {
		return view.backFace;
	}

	public Image getBackgIma() {
		if (backgIma == null) {
			if (nullBuff == null)
				nullBuff = createImage(1, 1);
			return nullBuff;
		} else {
			return backgIma;
		}
	}

	public int getBackgMode() {
		return backgMode;
	}

	public boolean getDoubleBuffer() {
		return doubleBuffer;
	}

	public boolean getEdit() {
		return flgModeEdit;
	}

	public boolean getEventDispatching() {
		return eventDispatching;
	}

	public boolean getFastMove() {
		return flgFastMove;
	}

	public boolean getFog() {
		return view.flgFog;
	}

	public Dimension getMinimumSize() {
		return minimumSize();
	}

	public String getObjFromFile() {
		return mdname;
	}

	public boolean getOutline() {
		return view.flgOutline;
	}

	public float getPosX() {
		return view.pos.x;
	}

	public float getPosY() {
		return view.pos.y;
	}

	public float getPosZ() {
		return view.pos.z;
	}

	public boolean getShaded() {
		return view.getShaded();
	}

	public boolean getTextureEnabled() {
		return textureEnabled;
	}

	public boolean getWireFrame() {
		return view.flgWireFrame;
	}

	public void init(String s, boolean flag) {
		world = new World3D();
		view = new View3D(world);
		mdname = s;
		if (flag) {
			if (mdname == null)
				mdname = "defaultObj.m3d";
			StringBufferInputStream stringbufferinputstream = new StringBufferInputStream(defaultObj);
			setObj(stringbufferinputstream, mdname);
			resize(size().width > 20 ? size().width : 400, size().height > 20 ? size().height : 400);
		}
	}

	public void layout() {
		if (isValid()) {
			super.layout();
			view.width = size().width;
			view.height = size().height;
			view.computeXFac();
			if (buf != null) {
				buf = null;
				gBuf.dispose();
			}
			if (doubleBuffer) {
				Dimension dimension = size();
				buf = createImage(dimension.width, dimension.height);
				if (textureEnabled)
					gBuf = new Graphics3D(buf);
				else
					gBuf = buf.getGraphics();
			}
			if (backgBuff == null && backgMode != 0) {
				Dimension dimension1 = size();
				backgBuff = createImage(dimension1.width, dimension1.height);
				Graphics g = backgBuff.getGraphics();
				g.clipRect(0, 0, dimension1.width, dimension1.height);
				switch (backgMode) {
				default:
					break;

				case 1: // '\001'
					g.drawImage(backgIma, 0, 0, null);
					break;

				case 3: // '\003'
					drawImageChecked(g, backgIma, 0, 0, dimension1.width, dimension1.height);
					break;

				case 2: // '\002'
					float f = size().width / backgIma.getWidth(null);
					float f1 = size().height / backgIma.getHeight(null);
					for (int i = 0; i < (int)f1 + 1; i++) {
						for (int j = 0; j < (int)f + 1; j++)
							g.drawImage(backgIma, j * backgIma.getWidth(null), i * backgIma.getHeight(null), null);

					}

					break;

				case 4: // '\004'
					drawImageChecked(g, backgIma, 0, 0, dimension1.width, dimension1.height);
					break;
				}
			}
			repaint();
		}
	}

	public Dimension minimumSize() {
		return new Dimension(20, 20);
	}

	private void notifyView3D(Elem3D elem3d) {
		if (elem3d != null) {
			Vector vector;
			synchronized (this) {
				vector = (Vector)Listeners.clone();
			}
			if (Listeners != null) {
				new String(Integer.toString(Listeners.size()));
				View3DEvent view3devent = new View3DEvent(this, elem3d);
				for (int i = 0; i < vector.size(); i++) {
					View3DListener view3dlistener = (View3DListener)vector.elementAt(i);
					view3dlistener.elemSelected(view3devent);
				}

			}
		}
	}

	public void paint(Graphics g) {
		if (!isValid())
			return;
		if (buf != null) {
			paintCanvas(gBuf);
			if (gBuf instanceof Graphics3D)
				((Graphics3D)gBuf).updateBuf();
			g.drawImage(buf, 0, 0, this);
		} else {
			paintCanvas(g);
		}
	}

	public void paintCanvas(Graphics g) {
		if (size().width != view.width || size().height != view.height) {
			backgBuff = null;
			layout();
		}
		if (backgBuff == null || backgMode == 0) {
			g.setColor(getBackground());
			view.size(size().width, size().height);
			g.fillRect(0, 0, size().width, size().height);
		} else
		if (backgMode == 4) {
			checkAngle(view.angle);
			int i = ((int)view.angle.y * backgBuff.getWidth(null)) / 360;
			int j = ((int)view.angle.x * backgBuff.getHeight(null)) / 360;
			g.drawImage(backgBuff, i, j, null);
			g.drawImage(backgBuff, i - backgBuff.getWidth(null), j - backgBuff.getHeight(null), null);
			g.drawImage(backgBuff, i - backgBuff.getWidth(null), j, null);
			g.drawImage(backgBuff, i, j - backgBuff.getHeight(null), null);
		} else {
			g.drawImage(backgBuff, 0, 0, null);
		}
		view.paint(g);
		setPainted();
		boolean flag = textureEnabled;
		if (world.containsTexture)
			flag = true;
		else
			flag = false;
		if (flag != textureEnabled) {
			System.out.println("Texture mode change:" + flag);
			textureEnabled = flag;
			layout();
		}
	}

	public synchronized void removeView3DListener(View3DListener view3dlistener) {
		Listeners.removeElement(view3dlistener);
		System.out.println("Remove listener");
	}

	public void setAllowedMove(boolean flag, boolean flag1, boolean flag2, boolean flag3, boolean flag4, boolean flag5) {
		rotX = flag;
		rotY = flag1;
		rotZ = flag2;
		moveX = moveX;
		moveY = moveY;
		moveZ = moveZ;
	}

	public void setAngleX(float f) {
		view.angle.x = f;
		repaint();
	}

	public void setAngleY(float f) {
		view.angle.y = f;
		repaint();
	}

	public void setAngleZ(float f) {
		view.angle.z = f;
		repaint();
	}

	public void setBackFace(boolean flag) {
		view.backFace = flag;
		layout();
	}

	public void setBackgIma(Image image) {
		backgBuff = null;
		if (image == nullBuff)
			backgIma = null;
		else
			backgIma = image;
		layout();
	}

	public void setBackgMode(int i) {
		backgBuff = null;
		backgMode = i;
		layout();
	}

	public void setDoubleBuffer(boolean flag) {
		doubleBuffer = flag;
		layout();
	}

	public void setEdit(boolean flag) {
		flgModeEdit = flag;
	}

	public void setEventDispatching(boolean flag) {
		eventDispatching = flag;
	}

	public void setFastMove(boolean flag) {
		flgFastMove = flag;
	}

	public void setFog(boolean flag) {
		view.flgFog = flag;
		view.fogColor = getBackground();
		layout();
	}

	public Object3D setObj(InputStream inputstream, String s) {
		return setObj(inputstream, s, null);
	}

	public Object3D setObj(InputStream inputstream, String s, String s1) {
		world.remAllObj();
		try {
			if (isValid()) {
				Graphics g = getGraphics();
				g.setColor(getBackground());
				g.fillRect(0, 0, size().width, size().height);
				g.setColor(getForeground());
				g.drawString("Loading...", 10, size().height - 10);
			}
			world.containsTexture = false;
			Object3D object3d = Object3D.constructFromStream(inputstream, s, s1);
			world.addObj(object3d);
			view().computeXFac();
			repaint();
			return object3d;
		}
		catch (Exception exception) {
			System.out.println(exception);
		}
		return null;
	}

	public Object3D setObjFromFile(String s) {
		mdname = s;
		try {
			FileInputStream fileinputstream = new FileInputStream(s);
			return setObj(fileinputstream, s);
		}
		catch (Exception exception) {
			System.out.println("Error while opening file:" + s + " exception:" + exception);
		}
		return null;
	}

	public void setOutline(boolean flag) {
		view.flgOutline = flag;
		layout();
	}

	private synchronized void setPainted() {
		painted = true;
		notifyAll();
	}

	public void setPosX(float f) {
		view.pos.x = f;
		repaint();
	}

	public void setPosY(float f) {
		view.pos.y = f;
		repaint();
	}

	public void setPosZ(float f) {
		view.pos.z = f;
		repaint();
	}

	public void setShaded(boolean flag) {
		view.flgShaded = flag;
		repaint();
	}

	public void setTextureEnabled(boolean flag) {
		textureEnabled = flag;
		layout();
	}

	public void setWireFrame(boolean flag) {
		view.flgWireFrame = flag;
		layout();
	}

	public void update(Graphics g) {
		paint(g);
	}

	public View3D view() {
		return view;
	}

}
