import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Event;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import tom.graphic.ThreeD.Line3D;
import tom.graphic.ThreeD.Object3D;
import tom.graphic.ThreeD.Sphere3D;
import tom.graphic.ThreeD.View3D;
import tom.graphic.ThreeD.View3DCanvas;
import tom.graphic.ThreeD.View3DCommon;
import tom.graphic.ThreeD.World3D;

public class som3d extends Frame
	implements MouseMotionListener, MouseListener {

	int matrix[][];
	View3DCanvas v3;
	float zval;
	LowPointDataset lp;
	PcaII PCA;
	Object3D neuronnet;
	node nt[][];
	int clickx;
	int clicky;
	actionhandler ah;
	boolean pressed;
	int prevx;
	int prevy;
	int prevz;
	int nowx;
	int nowy;
	int nowz;
	float closestz;
	float prevmvx;
	float prevmvy;
	float nowmvx;
	float nowmvy;
	int pcax;
	int pcay;
	int pcaz;
	float xmin;
	float xmax;
	float ymin;
	float ymax;
	float zmin;
	float zmax;
	float zoomlimit;

	public som3d(PcaII pcaii) {
		clickx = -1;
		clicky = -1;
		ah = new actionhandler(this);
		pressed = false;
		pcax = 0;
		pcay = 1;
		pcaz = 2;
		xmin = 99999F;
		xmax = -99999F;
		ymin = 99999F;
		ymax = -99999F;
		zmin = 99999F;
		zmax = -99999F;
		zoomlimit = -9999F;
		PCA = pcaii;
		init();
	}

	public som3d(double ad[][]) {
		clickx = -1;
		clicky = -1;
		ah = new actionhandler(this);
		pressed = false;
		pcax = 0;
		pcay = 1;
		pcaz = 2;
		xmin = 99999F;
		xmax = -99999F;
		ymin = 99999F;
		ymax = -99999F;
		zmin = 99999F;
		zmax = -99999F;
		zoomlimit = -9999F;
		PCA = new PcaII(ad);
		init();
	}

	public void addnet(node anode[][]) {
		nt = anode;
		neuronnet = new Object3D();
		double ad[][] = new double[anode.length * anode.length][anode[0][0].vector.length];
		int i = 0;
		for (int j = 0; j < anode.length; j++) {
			for (int k = 0; k < anode.length; k++) {
				ad[i] = anode[j][k].vector;
				i++;
			}

		}

		double ad1[][] = PCA.getlow(ad);
		i = 0;
		for (int l = 0; l < anode.length; l++) {
			for (int i1 = 0; i1 < anode.length; i1++) {
				anode[l][i1].mapx = (int)ad1[i][0];
				anode[l][i1].mapy = (int)ad1[i][1];
				anode[l][i1].mapz = (int)ad1[i][2];
				i++;
			}

		}

		for (int j1 = 0; j1 < anode.length; j1++) {
			for (int k1 = 0; k1 < anode.length; k1++)
				neuronnet.addVert(anode[j1][k1].mapx, anode[j1][k1].mapy, anode[j1][k1].mapz);

		}

		for (int l1 = 0; l1 < anode.length; l1++) {
			for (int i2 = 0; i2 < anode.length - 1; i2++) {
				Line3D line3d = new Line3D(l1 * anode.length + i2, l1 * anode.length + i2 + 1, Color.green);
				neuronnet.addElem(line3d);
				line3d = new Line3D(i2 * anode.length + l1, i2 * anode.length + l1 + anode.length, Color.green);
				neuronnet.addElem(line3d);
			}

		}

		Object obj = null;
		for (int j2 = 0; j2 < anode.length; j2++) {
			for (int k2 = 0; k2 < anode.length; k2++) {
				Sphere3D sphere3d = new Sphere3D(j2 * anode.length + k2, 10F, Color.red);
				neuronnet.addElem(sphere3d);
			}

		}

		v3.view().world().addObj(neuronnet);
	}

	public void init() {
		addWindowListener(ah);
		enableEvents(16L);
		enableEvents(32L);
		setTitle("3D SOM Window");
		float f = 0.0F;
		float f2 = 0.0F;
		float f4 = 0.0F;
		v3 = new View3DCanvas(false);
		v3.addMouseMotionListener(this);
		byte byte0 = 100;
		byte byte1 = 100;
		byte byte2 = 100;
		Object3D aobject3d[] = new Object3D[(int)PCA.nrPoints()];
		for (int i = 0; (double)i < PCA.nrPoints(); i++) {
			aobject3d[i] = new Object3D();
			float f1 = (float)PCA.ElementAt(i, pcax);
			float f3 = (float)PCA.ElementAt(i, pcay);
			float f5 = (float)PCA.ElementAt(i, pcaz);
			if (f1 < xmin)
				xmin = f1;
			if (f1 > xmax)
				xmax = f1;
			if (f3 < ymin)
				ymin = f3;
			if (f3 > ymax)
				ymax = f3;
			if (f5 < zmin)
				zmin = f5;
			if (f5 > zmax)
				zmax = f5;
			aobject3d[i].addVert(f1 - 1.0F, f3, f5);
			aobject3d[i].addVert(f1 + 1.0F, f3, f5);
			aobject3d[i].addVert(f1, f3 - 1.0F, f5);
			aobject3d[i].addVert(f1, f3 + 1.0F, f5);
			aobject3d[i].addVert(f1, f3, f5 - 1.0F);
			aobject3d[i].addVert(f1, f3, f5 + 1.0F);
			Line3D line3d = new Line3D(0, 1, Color.blue);
			aobject3d[i].addElem(line3d);
			line3d = new Line3D(2, 3, Color.blue);
			aobject3d[i].addElem(line3d);
			line3d = new Line3D(4, 5, Color.blue);
			aobject3d[i].addElem(line3d);
			v3.view().world().addObj(aobject3d[i]);
		}

		Object3D object3d = new Object3D();
		object3d.addVert(xmin - 10F, ymin - 10F, zmin - 10F);
		object3d.addVert(xmax + 10F, ymin - 10F, zmin - 10F);
		object3d.addVert(xmax + 10F, ymin - 10F, zmax + 10F);
		object3d.addVert(xmin - 10F, ymin - 10F, zmax + 10F);
		object3d.addVert(xmin - 10F, ymax + 10F, zmin - 10F);
		object3d.addVert(xmax + 10F, ymax + 10F, zmin - 10F);
		object3d.addVert(xmax + 10F, ymax + 10F, zmax + 10F);
		object3d.addVert(xmin - 10F, ymax + 10F, zmax + 10F);
		Color color = new Color(200, 200, 200);
		Line3D line3d1 = new Line3D(0, 1, color);
		object3d.addElem(line3d1);
		line3d1 = new Line3D(1, 2, color);
		object3d.addElem(line3d1);
		line3d1 = new Line3D(2, 3, color);
		object3d.addElem(line3d1);
		line3d1 = new Line3D(3, 0, color);
		object3d.addElem(line3d1);
		line3d1 = new Line3D(4, 5, color);
		object3d.addElem(line3d1);
		line3d1 = new Line3D(5, 6, color);
		object3d.addElem(line3d1);
		line3d1 = new Line3D(6, 7, color);
		object3d.addElem(line3d1);
		line3d1 = new Line3D(7, 4, color);
		object3d.addElem(line3d1);
		line3d1 = new Line3D(0, 4, color);
		object3d.addElem(line3d1);
		line3d1 = new Line3D(1, 5, color);
		object3d.addElem(line3d1);
		line3d1 = new Line3D(2, 6, color);
		object3d.addElem(line3d1);
		line3d1 = new Line3D(3, 7, color);
		object3d.addElem(line3d1);
		v3.view().world().addObj(object3d);
		v3.view().flgFog = false;
		v3.view().setWireFrame(true);
		add("Center", v3);
		setSize(300, 300);
		setVisible(true);
	}

	public void mouseClicked(MouseEvent mouseevent) {
	}

	public void mouseDragged(MouseEvent mouseevent) {
		int i = 0;
		int j = 0;
		nowmvx = mouseevent.getX();
		nowmvy = mouseevent.getY();
		if (nowmvx < prevmvx)
			i++;
		if (nowmvx > prevmvx)
			i--;
		if (nowmvy < prevmvy)
			j--;
		if (nowmvy > prevmvy)
			j++;
		if (mouseevent.getModifiers() == 4) {
			v3.setPosX(v3.getPosX() - (float)i);
			v3.setPosY(v3.getPosY() + (float)j);
		} else {
			v3.setAngleX(v3.getAngleX() + (float)(j * 4));
			v3.setAngleY(v3.getAngleY() + (float)(i * 4));
		}
		prevmvy = mouseevent.getY();
		prevmvx = mouseevent.getX();
	}

	public void mouseEntered(MouseEvent mouseevent) {
	}

	public void mouseExited(MouseEvent mouseevent) {
	}

	public void mouseMoved(MouseEvent mouseevent) {
	}

	public void mousePressed(MouseEvent mouseevent) {
	}

	public void mouseReleased(MouseEvent mouseevent) {
	}

	public void nearest(float f, float f1, float f2) {
		float f3 = 0.0F;
		if ((float)Math.sqrt(Math.pow(f, 2D) + Math.pow(f1, 2D) + Math.pow(f2, 2D)) > zoomlimit)
			zoomlimit = (float)Math.sqrt(Math.pow(f, 2D) + Math.pow(f1, 2D) + Math.pow(f2, 2D));
	}

	public void remake() {
		node anode[][] = nt;
		v3.view().world().remObj(neuronnet);
		neuronnet = new Object3D();
		double ad[][] = new double[anode.length * anode.length][anode[0][0].vector.length];
		int i = 0;
		for (int j = 0; j < anode.length; j++) {
			for (int k = 0; k < anode.length; k++) {
				ad[i] = anode[j][k].vector;
				i++;
			}

		}

		double ad1[][] = PCA.getlow(ad);
		i = 0;
		for (int l = 0; l < anode.length; l++) {
			for (int i1 = 0; i1 < anode.length; i1++) {
				anode[l][i1].mapx = (int)ad1[i][0];
				anode[l][i1].mapy = (int)ad1[i][1];
				anode[l][i1].mapz = (int)ad1[i][2];
				i++;
			}

		}

		for (int j1 = 0; j1 < anode.length; j1++) {
			for (int k1 = 0; k1 < anode.length; k1++)
				neuronnet.addVert(anode[j1][k1].mapx, anode[j1][k1].mapy, anode[j1][k1].mapz);

		}

		Object obj = null;
		for (int l1 = 0; l1 < anode.length; l1++) {
			for (int i2 = 0; i2 < anode.length - 1; i2++) {
				Line3D line3d = new Line3D(l1 * anode.length + i2, l1 * anode.length + i2 + 1, Color.green);
				neuronnet.addElem(line3d);
				line3d = new Line3D(i2 * anode.length + l1, i2 * anode.length + l1 + anode.length, Color.green);
				neuronnet.addElem(line3d);
			}

		}

		for (int j2 = 0; j2 < anode.length; j2++) {
			for (int k2 = 0; k2 < anode.length; k2++)
				if (j2 == clickx && k2 == clicky) {
					Sphere3D sphere3d = new Sphere3D(j2 * anode.length + k2, 16F, Color.yellow);
					neuronnet.addElem(sphere3d);
				} else {
					Sphere3D sphere3d1 = new Sphere3D(j2 * anode.length + k2, 10F, Color.red);
					neuronnet.addElem(sphere3d1);
				}

		}

		v3.view().world().addObj(neuronnet);
		v3.repaint();
	}

	public void updatenet(node anode[][]) {
		nt = anode;
		v3.view().world().remObj(neuronnet);
		neuronnet = new Object3D();
		double ad[][] = new double[anode.length * anode.length][anode[0][0].vector.length];
		int i = 0;
		for (int j = 0; j < anode.length; j++) {
			for (int k = 0; k < anode.length; k++) {
				ad[i] = anode[j][k].vector;
				i++;
			}

		}

		double ad1[][] = PCA.getlow(ad);
		i = 0;
		for (int l = 0; l < anode.length; l++) {
			for (int i1 = 0; i1 < anode.length; i1++) {
				anode[l][i1].mapx = (int)ad1[i][0];
				anode[l][i1].mapy = (int)ad1[i][1];
				anode[l][i1].mapz = (int)ad1[i][2];
				i++;
			}

		}

		for (int j1 = 0; j1 < anode.length; j1++) {
			for (int k1 = 0; k1 < anode.length; k1++)
				neuronnet.addVert(anode[j1][k1].mapx, anode[j1][k1].mapy, anode[j1][k1].mapz);

		}

		Object obj = null;
		for (int l1 = 0; l1 < anode.length; l1++) {
			for (int i2 = 0; i2 < anode.length - 1; i2++) {
				Line3D line3d = new Line3D(l1 * anode.length + i2, l1 * anode.length + i2 + 1, Color.green);
				neuronnet.addElem(line3d);
				line3d = new Line3D(i2 * anode.length + l1, i2 * anode.length + l1 + anode.length, Color.green);
				neuronnet.addElem(line3d);
			}

		}

		for (int j2 = 0; j2 < anode.length; j2++) {
			for (int k2 = 0; k2 < anode.length; k2++)
				if (j2 == clickx && k2 == clicky) {
					Sphere3D sphere3d = new Sphere3D(j2 * anode.length + k2, 16F, Color.yellow);
					neuronnet.addElem(sphere3d);
				} else {
					Sphere3D sphere3d1 = new Sphere3D(j2 * anode.length + k2, 10F, Color.red);
					neuronnet.addElem(sphere3d1);
				}

		}

		v3.view().world().addObj(neuronnet);
		v3.repaint();
	}
}
