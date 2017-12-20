package tom.graphic.ThreeD;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Vector;

// Referenced classes of package tom.graphic.ThreeD:
//			Elem3D, Point3D, Matrix3D, ObjReader, 
//			View3D, Face, ObjReaderM3D, ObjReaderObj, 
//			ObjReaderRWX

public class Object3D extends Elem3D {

	public Point3D vert[];
	protected Point3D tvert[];
	public int nvert;
	private int maxvert;
	public Elem3D tFace[];
	public int nFace;
	private int maxFace;
	protected boolean transformed;
	public Matrix3D mat;
	public int pos2d[];
	private long startTime;
	public View3D curView;
	protected Color colOutline;
	protected Color colWireFrame;
	protected boolean flgOutline;
	protected boolean flgWireFrame;
	protected boolean flgShaded;
	public Point3D angle;
	public Point3D pos;
	public Point3D spRot;
	public Point3D spTrans;
	public String name;
	public float xmin;
	public float xmax;
	public float ymin;
	public float ymax;
	public float zmin;
	public float zmax;
	static Vector listOfDecoder = new Vector();
	static boolean defaultDecoder = false;
	protected Object3D objList[];
	protected int nbObj;
	private int maxObj;
	private int freeSlot;

	public Object3D() {
		nFace = 0;
		maxFace = 0;
		colOutline = Color.black;
		colWireFrame = Color.white;
		flgShaded = true;
		angle = new Point3D();
		pos = new Point3D();
		spRot = new Point3D();
		spTrans = new Point3D();
		objList = null;
		nbObj = 0;
		maxObj = 0;
		freeSlot = 0;
		mat = new Matrix3D();
	}

	public Object3D(InputStream inputstream, ObjReader objreader) throws IOException {
		this();
		objreader.constructFromStream(this, inputstream, "unknown");
		findBB();
	}

	public int addChild(Object3D object3d) {
		if (object3d != null) {
			int i = nbObj;
			addElem(object3d);
			for (int j = 0; j < nbObj; j++)
				if (objList[j] == null) {
					objList[j] = object3d;
					return j;
				}

			if (i >= maxObj)
				if (objList == null) {
					maxObj = 100;
					objList = new Object3D[maxObj];
				} else {
					maxObj *= 2;
					Object3D aobject3d[] = new Object3D[maxObj];
					System.arraycopy(objList, 0, aobject3d, 0, objList.length);
					objList = aobject3d;
				}
			objList[i] = object3d;
			nbObj++;
		}
		return nbObj;
	}

	public int addElem(Elem3D elem3d) {
		int i = nFace;
		elem3d.parent = this;
		if (freeSlot > 0) {
			for (int j = 0; j < maxFace; j++)
				if (tFace[j] == null) {
					freeSlot--;
					tFace[j] = elem3d;
					return j;
				}

		}
		if (i >= maxFace)
			if (tFace == null) {
				maxFace = 100;
				tFace = new Elem3D[maxFace];
			} else {
				maxFace *= 2;
				Elem3D aelem3d[] = new Elem3D[maxFace];
				System.arraycopy(tFace, 0, aelem3d, 0, tFace.length);
				tFace = aelem3d;
			}
		tFace[i] = elem3d;
		return nFace++;
	}

	public static void addReader(ObjReader objreader) {
		listOfDecoder.addElement(objreader);
	}

	public int addVert(float f, float f1, float f2) {
		Point3D point3d = new Point3D();
		point3d.x = f;
		point3d.y = f1;
		point3d.z = f2;
		int i = nvert;
		if (i >= maxvert)
			if (vert == null) {
				maxvert = 100;
				vert = new Point3D[maxvert * 3];
			} else {
				maxvert *= 2;
				Point3D apoint3d[] = new Point3D[maxvert * 3];
				System.arraycopy(vert, 0, apoint3d, 0, vert.length);
				vert = apoint3d;
			}
		vert[i] = point3d;
		pos2d = null;
		pos2d = new int[(nvert + 1) * 2];
		nvert++;
		return i;
	}

	public int addVert(Point3D point3d) {
		return addVert(point3d.x, point3d.y, point3d.z);
	}

	void computeAllPoints(View3D view3d) {
		int i = (nvert - 1) * 2;
		for (int j = nvert - 1; j >= 0; j--) {
			float f = tvert[j].x;
			float f1 = tvert[j].y;
			float f2 = tvert[j].z;
			pos2d[i] = (int)((float)(view3d.width / 2) + (f * view3d.e) / f2);
			pos2d[i + 1] = (int)((float)(view3d.height / 2) + (f1 * view3d.e) / f2);
			i -= 2;
		}

	}

	public void computeOnePoint(Point3D point3d, int ai[], boolean flag) {
		float f = point3d.x;
		float f1 = point3d.y;
		float f2 = point3d.z;
		ai[0] = (int)((f * curView.e) / f2);
		ai[1] = (int)((f1 * curView.e) / f2);
		if (flag) {
			ai[0] += curView.width / 2;
			ai[1] += curView.height / 2;
		}
	}

	protected void computeZ() {
		for (int i = 0; i < nFace; i++)
			if (tFace[i] != null)
				tFace[i].setZ();

	}

	public static Object3D constructFromStream(InputStream inputstream, String s) {
		return constructFromStream(inputstream, s, null);
	}

	public static Object3D constructFromStream(InputStream inputstream, String s, String s1) {
		Object3D object3d = null;
		inputstream = new BufferedInputStream(inputstream);
		ObjReader objreader = null;
		if (s1 != null) {
			objreader = loadReader(s1);
			if (objreader == null)
				System.out.println("Error, could not find class loader " + s1);
		}
		if (objreader == null)
			objreader = getReader(inputstream, s);
		if (objreader != null)
			try {
				object3d = new Object3D(inputstream, objreader);
				object3d.setName(s);
			}
			catch (Exception exception) {
				System.out.println("Construct from stream:" + exception);
			}
		return object3d;
	}

	public void findBB() {
		float f = 0.0F;
		float f1 = f;
		float f2 = f;
		float f3 = f;
		float f4 = f;
		float f5 = f;
		boolean flag = false;
		for (int i = 0; i < nbObj; i++) {
			Object3D object3d = objList[i];
			if (object3d != null) {
				object3d.findBB();
				if (!flag) {
					f = object3d.xmin;
					f1 = object3d.xmax;
					f2 = object3d.ymin;
					f3 = object3d.ymax;
					f4 = object3d.zmin;
					f5 = object3d.zmax;
					flag = true;
				} else {
					if (object3d.xmin < f)
						f = object3d.xmin;
					if (object3d.xmax > f1)
						f1 = object3d.xmax;
					if (object3d.ymin < f)
						f2 = object3d.ymin;
					if (object3d.ymax > f1)
						f3 = object3d.ymax;
					if (object3d.zmin < f)
						f4 = object3d.zmin;
					if (object3d.zmax > f1)
						f5 = object3d.zmax;
				}
			}
		}

		if (nvert > 0) {
			Point3D apoint3d[] = vert;
			if (!flag) {
				f = apoint3d[0].x;
				f1 = f;
				f2 = apoint3d[0].y;
				f3 = f2;
				f4 = apoint3d[0].z;
				f5 = f4;
			}
			for (int j = nvert - 1; j >= 0; j--) {
				float f6 = apoint3d[j].x;
				if (f6 < f)
					f = f6;
				if (f6 > f1)
					f1 = f6;
				float f7 = apoint3d[j].y;
				if (f7 < f2)
					f2 = f7;
				if (f7 > f3)
					f3 = f7;
				float f8 = apoint3d[j].z;
				if (f8 < f4)
					f4 = f8;
				if (f8 > f5)
					f5 = f8;
			}

		}
		xmax = f1;
		xmin = f;
		ymax = f3;
		ymin = f2;
		zmax = f5;
		zmin = f4;
	}

	public Object3D findByName(String s) {
		if (name.equals(s))
			return this;
		for (int i = 0; i < nbObj; i++)
			if (objList[i] != null) {
				Object3D object3d = findByName(s);
				if (object3d != null)
					return object3d;
			}

		return null;
	}

	public Object3D[] getChilds() {
		return objList;
	}

	public static Object3D getCube(float f, float f1, float f2, Color color) {
		Object3D object3d = new Object3D();
		object3d.addVert(f, f1, f2);
		object3d.addVert(-f, f1, f2);
		object3d.addVert(-f, -f1, f2);
		object3d.addVert(f, -f1, f2);
		object3d.addVert(f, f1, -f2);
		object3d.addVert(-f, f1, -f2);
		object3d.addVert(-f, -f1, -f2);
		object3d.addVert(f, -f1, -f2);
		Face face = new Face(0, 1, 2, 3, color);
		object3d.addElem(face);
		face = new Face(7, 6, 5, 4, color);
		object3d.addElem(face);
		face = new Face(0, 4, 5, 1, color);
		object3d.addElem(face);
		face = new Face(1, 5, 6, 2, color);
		object3d.addElem(face);
		face = new Face(2, 6, 7, 3, color);
		object3d.addElem(face);
		face = new Face(3, 7, 4, 0, color);
		object3d.addElem(face);
		return object3d;
	}

	public String getLastEvtAsString() {
		return getName();
	}

	public String getName() {
		return name;
	}

	public int getNbChilds() {
		return nbObj;
	}

	public static ObjReader getReader(InputStream inputstream, String s) {
		ObjReader objreader = null;
		if (!defaultDecoder) {
			defaultDecoder = true;
			try {
				addReader(new ObjReaderM3D());
			}
			catch (Exception _ex) {
				System.out.println("Default Obj reader not found");
			}
			try {
				addReader(new ObjReaderObj());
			}
			catch (Exception _ex) {
				System.out.println("Default Obj reader not found");
			}
			try {
				addReader(new ObjReaderRWX());
			}
			catch (Exception _ex) {
				System.out.println("Default RWX reader not found");
			}
		}
		boolean flag = false;
		for (Enumeration enumeration = listOfDecoder.elements(); enumeration.hasMoreElements() && !flag;) {
			objreader = (ObjReader)enumeration.nextElement();
			if (objreader.checkIfTypeIsKnow(inputstream, s))
				flag = true;
			else
				objreader = null;
		}

		if (objreader == null) {
			int i = s.length();
			int j = s.lastIndexOf('.');
			if (j == -1)
				j = i - 3;
			String s1 = "tom.graphic.xThreeD.ObjReader" + s.toUpperCase().substring(j + 1, i);
			objreader = loadReader(s1);
		}
		return objreader;
	}

	public Elem3D isInside(int i, int j) {
		Elem3D elem3d = null;
		for (int k = 0; k < nFace; k++)
			if (tFace[k] != null) {
				Elem3D elem3d1 = tFace[k].isInside(i, j);
				if (elem3d1 != null && (elem3d == null || elem3d1.posZ < elem3d.posZ))
					elem3d = elem3d1;
			}

		return elem3d;
	}

	public static ObjReader loadReader(String s) {
		ObjReader objreader = null;
		System.out.println(" Suffix:" + s);
		try {
			Class class1 = Class.forName(s);
			if (class1 != null)
				objreader = (ObjReader)class1.newInstance();
		}
		catch (Exception exception) {
			System.out.println(exception);
		}
		return objreader;
	}

	public void move(Point3D point3d) {
		if (point3d.x != 0.0F || point3d.y != 0.0F || point3d.z != 0.0F) {
			for (int i = 0; i < nvert; i++) {
				vert[i].x += point3d.x;
				vert[i].y += point3d.y;
				vert[i].z += point3d.z;
			}

		}
		for (int j = 0; j < nbObj; j++) {
			Object3D object3d = objList[j];
			if (object3d != null)
				object3d.move(point3d);
		}

	}

	public void paint(Graphics g) {
		try {
			if (curView == null)
				if (super.parent != null) {
					curView = super.parent.curView;
				} else {
					System.out.println("Error with parent");
					return;
				}
			updateSpeed();
			startTime = System.currentTimeMillis();
			transform();
			flgOutline = curView.flgOutline;
			flgWireFrame = curView.flgWireFrame;
			flgShaded = curView.flgShaded;
			computeAllPoints(curView);
			Point3D[] _tmp = tvert;
			if (nvert < 0)
				return;
			computeZ();
			sort(tFace, nFace);
			for (int i = 0; i < nFace; i++)
				if (tFace[i] != null && tFace[i].posZ > -1000F)
					tFace[i].paint(g);

		}
		catch (NullPointerException nullpointerexception) {
			System.out.println("Object3D.paint:" + nullpointerexception);
		}
	}

	private void QuickSort(Elem3D aelem3d[], int i, int j) {
		int k = i;
		int l = j;
		if (j > i) {
			float f = aelem3d[(i + j) / 2].posZ;
			while (k <= l)  {
				while (k < j && aelem3d[k].posZ > f) 
					k++;
				for (; l > i && aelem3d[l].posZ < f; l--);
				if (k <= l) {
					swap(aelem3d, k, l);
					k++;
					l--;
				}
			}
			if (i < l)
				QuickSort(aelem3d, i, l);
			if (k < j)
				QuickSort(aelem3d, k, j);
		}
	}

	public void remAllChilds() {
		for (int i = 0; i < nbObj; i++)
			if (objList[i] != null)
				remElem(objList[i]);

		objList = null;
		maxObj = 0;
		nbObj = 0;
	}

	public void remChild(Object3D object3d) {
		for (int i = 0; i < nbObj; i++)
			if (objList[i] == object3d)
				objList[i] = null;

		remElem(object3d);
	}

	public Elem3D remElem(Elem3D elem3d) {
		for (int i = 0; i < nFace; i++)
			if (tFace[i] == elem3d) {
				tFace[i] = null;
				freeSlot++;
				return elem3d;
			}

		return null;
	}

	public void scale(Point3D point3d) {
		if (point3d.x != 1.0F || point3d.y != 1.0F || point3d.z != 1.0F) {
			for (int i = 0; i < nvert; i++) {
				vert[i].x *= point3d.x;
				vert[i].y *= point3d.y;
				vert[i].z *= point3d.z;
			}

		}
		pos.x *= point3d.x;
		pos.y *= point3d.y;
		pos.z *= point3d.z;
		for (int j = 0; j < nbObj; j++) {
			Object3D object3d = objList[j];
			if (object3d != null)
				object3d.scale(point3d);
		}

	}

	public void setName(String s) {
		name = s;
	}

	public float setZ() {
		transformed = false;
		mat.unit();
		mat.rotMat(angle);
		mat.translate(pos);
		if (super.parent != null)
			mat.mult(super.parent.mat);
		Point3D point3d = new Point3D();
		Point3D point3d1 = new Point3D();
		mat.transform(point3d, point3d1);
		super.posZ = point3d1.z;
		return super.posZ;
	}

	private void slowSort(Elem3D aelem3d[], int i) {
		for (int j = i; --j >= 0;) {
			for (int k = 0; k < j; k++)
				if (aelem3d[k].posZ < aelem3d[k + 1].posZ) {
					Elem3D elem3d = aelem3d[k];
					aelem3d[k] = aelem3d[k + 1];
					aelem3d[k + 1] = elem3d;
				}

		}

	}

	public void sort(Elem3D aelem3d[], int i) {
		QuickSort(aelem3d, 0, i - 1);
	}

	private void swap(Elem3D aelem3d[], int i, int j) {
		Elem3D elem3d = aelem3d[i];
		aelem3d[i] = aelem3d[j];
		aelem3d[j] = elem3d;
	}

	private void transform() {
		if (transformed || nvert <= 0)
			return;
		if (tvert == null || tvert.length < nvert) {
			tvert = new Point3D[nvert];
			for (int i = 0; i < nvert; i++)
				tvert[i] = new Point3D();

		}
		mat.transform(vert, tvert, nvert);
		transformed = true;
	}

	public void updateSpeed() {
		angle.add(spRot);
		pos.add(spTrans);
	}

}
