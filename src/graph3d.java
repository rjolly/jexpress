import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.List;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import tom.graphic.DataView.Array;
import tom.graphic.DataView.DataView;
import tom.graphic.ThreeD.Line3D;
import tom.graphic.ThreeD.Object3D;
import tom.graphic.ThreeD.Sphere3D;
import tom.graphic.ThreeD.View3DCanvas;
import tom.graphic.ThreeD.View3DCommon;

public class graph3d extends Frame
	implements ComponentListener, ActionListener, ItemListener {

	int matrix[][];
	String names[];
	int selectedind[];
	tools t;
	int xaxis[];
	int yvals[];
	int yraw[];
	int ymean[];
	int yspan;
	Math m;
	gen trunk;
	int matrixheight;
	int matrixindex;
	int frameheight;
	int framewidth;
	Image offImage;
	Graphics offGraphics;
	filehandler fh;
	actionhandler ah;
	int inframeymin;
	int inframeymax;
	String title;
	boolean clusterdata;
	paintpanel p2;
	int ytemp;
	View3DCanvas v3;
	int dept;
	Object3D thegraph[];
	CheckboxGroup cbg;
	Checkbox c1;
	Checkbox c2;
	DataView dv;
	List li;
	JButton jb10;
	JButton jb11;
	Array arr;

	public graph3d(gen gen1, int ai[][], String as[], filehandler filehandler1) {
		t = new tools();
		frameheight = 130;
		framewidth = 150;
		ah = new actionhandler(this);
		inframeymin = 9999;
		inframeymax = -9999;
		title = "";
		p2 = new paintpanel(0);
		ytemp = 0;
		cbg = new CheckboxGroup();
		c1 = new Checkbox("Bar view", cbg, false);
		c2 = new Checkbox("Sqare view", cbg, true);
		dv = new DataView();
		li = new List(10);
		jb10 = new JButton("Raise all");
		jb11 = new JButton("Null all");
		frameheight = 130;
		framewidth = 160;
		clusterdata = true;
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		setIconImage(toolkit.getImage("im/curv.gif"));
		names = as;
		addWindowListener(ah);
		fh = filehandler1;
		setBackground(Color.white);
		setTitle("Gene graph");
		addComponentListener(this);
		setSize(frameheight, framewidth);
		trunk = gen1;
		countgenes(gen1);
		matrix = new int[matrixheight][ai[0].length];
		makematrix(gen1, ai);
		setLayout(new BorderLayout());
		Panel panel = new Panel(new GridLayout(1, 3));
		Button button = new Button("Close");
		button.addActionListener(this);
		button.setBackground(Color.lightGray);
		Button button1 = new Button("Save");
		button1.addActionListener(this);
		button1.setBackground(Color.lightGray);
		Button button2 = new Button("List");
		button2.addActionListener(this);
		panel.add(button);
		panel.add(button2);
		panel.add(button1);
		add("Center", p2);
		add("South", panel);
		setVisible(true);
	}

	public graph3d(int ai[][], String as[], String s) {
		t = new tools();
		frameheight = 130;
		framewidth = 150;
		ah = new actionhandler(this);
		inframeymin = 9999;
		inframeymax = -9999;
		title = "";
		p2 = new paintpanel(0);
		ytemp = 0;
		cbg = new CheckboxGroup();
		c1 = new Checkbox("Bar view", cbg, false);
		c2 = new Checkbox("Sqare view", cbg, true);
		dv = new DataView();
		li = new List(10);
		jb10 = new JButton("Raise all");
		jb11 = new JButton("Null all");
		names = as;
		v3 = new View3DCanvas(false);
		clusterdata = false;
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		fh = new filehandler();
		frameheight = 320;
		framewidth = 250;
		setIconImage(toolkit.getImage("im/curv.gif"));
		addWindowListener(ah);
		fh = fh;
		setTitle(s);
		addComponentListener(this);
		setSize(frameheight, framewidth);
		setBackground(Color.white);
		addComponentListener(this);
		setSize(frameheight, framewidth);
		matrix = ai;
		setLayout(new BorderLayout());
		Panel panel = new Panel(new GridLayout(1, 2));
		Panel panel1 = new Panel(new BorderLayout());
		Panel panel2 = new Panel(new GridLayout(1, 2));
		Button button = new Button("Close");
		button.addActionListener(this);
		button.setBackground(Color.lightGray);
		Button button1 = new Button("Save");
		button1.addActionListener(this);
		button1.setBackground(Color.lightGray);
		jb10.addActionListener(this);
		jb11.addActionListener(this);
		jb10.setBackground(new Color(201, 201, 240));
		jb11.setBackground(new Color(201, 201, 240));
		c1.addItemListener(this);
		c2.addItemListener(this);
		c1.setBackground(Color.lightGray);
		c2.setBackground(Color.lightGray);
		panel2.add(jb10);
		panel2.add(jb11);
		panel1.add("Center", li);
		panel1.add("South", panel2);
		panel.add(c1);
		panel.add(c2);
		add("East", panel1);
		li.setBackground(Color.lightGray);
		li.setMultipleMode(true);
		li.addItemListener(this);
		float af[] = new float[matrix.length * matrix[0].length];
		int i = 0;
		if (as == null)
			li.addItem("Vector " + String.valueOf(0));
		else
			li.addItem(as[as.length - 1]);
		li.select(0);
		for (int j = 1; j < matrix.length; j++) {
			if (as == null)
				li.addItem("Vector " + String.valueOf(j));
			else
				li.addItem(as[as.length - 1 - j]);
			li.select(j);
			for (int k = 0; k < matrix[0].length; k++) {
				af[i] = matrix[j][k];
				i++;
			}

		}

		arr = new Array(matrix.length + 1, matrix[0].length + 1);
		for (int l = 0; l < matrix.length; l++) {
			arr.setIJ(l, matrix[0].length, 0.0F);
			for (int i1 = 0; i1 < matrix[l].length; i1++) {
				arr.setIJ(matrix.length, i1, 0.0F);
				arr.setIJ(l, i1, matrix[l][i1]);
			}

		}

		dv.setArray(arr);
		dv.setAngleX(78F);
		dv.setAngleY(-115F);
		dv.setAngleZ(-10F);
		dv.setPosY(21F);
		dv.setRepr(2);
		dv.setDisplayAxes(true);
		add("Center", dv);
		add("South", panel);
		paintgraph();
		setVisible(true);
	}

	public void actionPerformed(ActionEvent actionevent) {
		if (actionevent.getActionCommand() == "Close")
			dispose();
		else
		if (actionevent.getActionCommand() != "Save")
			if (actionevent.getActionCommand() == "List")
				showlist();
			else
			if (actionevent.getActionCommand() != "Refresh")
				if (actionevent.getActionCommand() == "Raice all") {
					for (int i = 0; i < li.getItemCount(); i++)
						li.select(i);

					updatelist();
				} else
				if (actionevent.getActionCommand() == "Null all") {
					for (int j = 0; j < li.getItemCount(); j++)
						li.deselect(j);

					updatelist();
				}
	}

	public void componentHidden(ComponentEvent componentevent) {
	}

	public void componentMoved(ComponentEvent componentevent) {
	}

	public void componentResized(ComponentEvent componentevent) {
		Dimension dimension = p2.getSize();
		frameheight = dimension.height;
		framewidth = dimension.width;
	}

	public void componentShown(ComponentEvent componentevent) {
	}

	public void countgenes(gen gen1) {
		if (gen1.merged) {
			countgenes(gen1.right);
			countgenes(gen1.left);
		} else {
			matrixheight++;
		}
	}

	public void drawPolyline(int ai[], int ai1[], int ai2[], int i, int j, int k, int l) {
		boolean flag = false;
		if (i == 0)
			i = 1;
		if (j == 0)
			j = 1;
		thegraph[l] = new Object3D();
		for (int k1 = 0; k1 < ai.length; k1++)
			thegraph[l].addVert((float)ai[k1] - (float)(ai[ai.length - 1] / 2), ai1[k1], (float)(dept * (ai[ai.length - 1] - ai[ai.length - 2])) - (float)((matrix.length * (ai[ai.length - 1] - ai[ai.length - 2])) / 2));

		for (int l1 = 0; l1 < ai.length - 1; l1++) {
			Color color;
			if (k == 0) {
				if (ai2[l1] < 0) {
					int i1 = Math.abs(60 + (int)(((double)ai2[l1] / (double)i) * 180D));
					color = new Color(0, i1, 0);
				} else {
					int j1 = Math.abs(60 + (int)(((double)ai2[l1] / (double)j) * 180D));
					color = new Color(j1, 0, 0);
				}
			} else {
				color = new Color(200, 170, 10);
			}
			Line3D line3d = new Line3D(l1, l1 + 1, color);
			thegraph[l].addElem(line3d);
			thegraph[l].addVert((float)ai[0] - (float)(ai[ai.length - 1] / 2), 0.0F, (float)(dept * (ai[ai.length - 1] - ai[ai.length - 2])) - (float)((matrix.length * (ai[ai.length - 1] - ai[ai.length - 2])) / 2));
			thegraph[l].addVert((float)ai[ai.length - 1] - (float)(ai[ai.length - 1] / 2), 0.0F, (float)(dept * (ai[ai.length - 1] - ai[ai.length - 2])) - (float)((matrix.length * (ai[ai.length - 1] - ai[ai.length - 2])) / 2));
			line3d = new Line3D(ai.length, ai.length + 1, new Color(200, 200, 200));
			thegraph[l].addElem(line3d);
		}

		dept++;
	}

	public boolean isselected(int i) {
		boolean flag = false;
		if (selectedind != null) {
			for (int j = 0; j < selectedind.length; j++)
				if (i == selectedind[j])
					flag = true;

		}
		return flag;
	}

	public void itemStateChanged(ItemEvent itemevent) {
		if (cbg.getSelectedCheckbox() == c1) {
			dv.setRepr(3);
			dv.repaint();
		} else
		if (cbg.getSelectedCheckbox() == c2) {
			dv.setRepr(2);
			dv.repaint();
		}
		if (itemevent.getItem() != c1 && itemevent.getItem() != c2)
			updatelist();
	}

	public void makematrix(gen gen1, int ai[][]) {
		if (gen1.merged) {
			makematrix(gen1.right, ai);
			makematrix(gen1.left, ai);
		} else {
			li.add(names[t.intval(gen1.name)]);
			for (int i = 0; i < ai[0].length; i++)
				matrix[matrixindex][i] = ai[t.intval(gen1.name)][i];

			matrixindex++;
		}
	}

	public void paintgraph() {
		inframeymin = 0x1869f;
		inframeymax = 0xfffe7961;
		boolean flag = false;
		int i = 0;
		int j = 0;
		int k = 0;
		int l = 0;
		int i1 = 0;
		int j1 = 0;
		int k1 = 0x1869f;
		int l1 = 0xfffe7961;
		Dimension dimension = getSize();
		frameheight = dimension.height;
		framewidth = dimension.width;
		int i2 = t.idivideint(framewidth, 2);
		int j2 = t.idivideint(frameheight, 2);
		i = matrix[0].length;
		for (int k2 = 0; k2 < matrix.length; k2++) {
			for (int l2 = 0; l2 < i; l2++) {
				if (matrix[k2][l2] < k)
					k = matrix[k2][l2];
				if (matrix[k2][l2] > j)
					j = matrix[k2][l2];
			}

		}

		yvals = new int[i];
		yraw = new int[i];
		xaxis = new int[i];
		ymean = new int[i];
		yspan = j - k;
		thegraph = new Object3D[matrix.length];
		Object3D object3d = new Object3D();
		Object3D object3d1 = new Object3D();
		Object3D object3d2 = new Object3D();
		for (int i3 = 0; i3 < matrix[0].length; i3++) {
			xaxis[i3] = 30 + i3 * t.idivideint(framewidth - 30, i);
			ytemp = i3 * t.idivideint(frameheight, i);
		}

		for (int j3 = 0; j3 < matrix.length; j3++) {
			for (int k3 = 0; k3 < matrix[j3].length; k3++) {
				yraw[k3] = matrix[j3][k3];
				yvals[k3] = t.norm(matrix[j3][k3], t.idivideint(frameheight, 2), yspan);
				if (yvals[k3] < k1)
					k1 = yvals[k3];
				if (yvals[k3] > l1)
					l1 = yvals[k3];
				ymean[k3] = ymean[k3] + yvals[k3];
			}

			if (isselected(j3))
				drawPolyline(xaxis, yvals, yraw, k, j, 1, j3);
			else
				drawPolyline(xaxis, yvals, yraw, k, j, 0, j3);
			object3d.addChild(thegraph[j3]);
		}

		l = (xaxis[xaxis.length - 1] - xaxis[0]) / 2;
		i1 = (l1 - k1) / 2;
		j1 = (int)((double)dept * (double)xaxis[xaxis.length - 1]) / 2;
		object3d2.addVert((float)xaxis[0] - (float)(xaxis[xaxis.length - 1] / 2), l1, (float)(dept * (xaxis[xaxis.length - 1] - xaxis[xaxis.length - 2])) - (float)((matrix.length * (xaxis[xaxis.length - 1] - xaxis[xaxis.length - 2])) / 2));
		object3d2.addVert((float)xaxis[xaxis.length - 1] - (float)(xaxis[xaxis.length - 1] / 2), l1, (float)(dept * (xaxis[xaxis.length - 1] - xaxis[xaxis.length - 2])) - (float)((matrix.length * (xaxis[xaxis.length - 1] - xaxis[xaxis.length - 2])) / 2));
		object3d2.addVert((float)xaxis[0] - (float)(xaxis[xaxis.length - 1] / 2), k1, (float)(dept * (xaxis[xaxis.length - 1] - xaxis[xaxis.length - 2])) - (float)((matrix.length * (xaxis[xaxis.length - 1] - xaxis[xaxis.length - 2])) / 2));
		object3d2.addVert((float)xaxis[xaxis.length - 1] - (float)(xaxis[xaxis.length - 1] / 2), k1, (float)(dept * (xaxis[xaxis.length - 1] - xaxis[xaxis.length - 2])) - (float)((matrix.length * (xaxis[xaxis.length - 1] - xaxis[xaxis.length - 2])) / 2));
		object3d2.addVert((float)xaxis[0] - (float)(xaxis[xaxis.length - 1] / 2), l1, -(float)((matrix.length * (xaxis[xaxis.length - 1] - xaxis[xaxis.length - 2])) / 2));
		object3d2.addVert((float)xaxis[xaxis.length - 1] - (float)(xaxis[xaxis.length - 1] / 2), l1, -(float)((matrix.length * (xaxis[xaxis.length - 1] - xaxis[xaxis.length - 2])) / 2));
		object3d2.addVert((float)xaxis[0] - (float)(xaxis[xaxis.length - 1] / 2), k1, -(float)((matrix.length * (xaxis[xaxis.length - 1] - xaxis[xaxis.length - 2])) / 2));
		object3d2.addVert((float)xaxis[xaxis.length - 1] - (float)(xaxis[xaxis.length - 1] / 2), k1, -(float)((matrix.length * (xaxis[xaxis.length - 1] - xaxis[xaxis.length - 2])) / 2));
		Line3D line3d = new Line3D(0, 1, new Color(200, 200, 200));
		object3d2.addElem(line3d);
		line3d = new Line3D(1, 3, new Color(200, 200, 200));
		object3d2.addElem(line3d);
		line3d = new Line3D(3, 2, new Color(200, 200, 200));
		object3d2.addElem(line3d);
		line3d = new Line3D(2, 0, new Color(200, 200, 200));
		object3d2.addElem(line3d);
		line3d = new Line3D(4, 5, new Color(200, 200, 200));
		object3d2.addElem(line3d);
		line3d = new Line3D(5, 7, new Color(200, 200, 200));
		object3d2.addElem(line3d);
		line3d = new Line3D(7, 6, new Color(200, 200, 200));
		object3d2.addElem(line3d);
		line3d = new Line3D(6, 4, new Color(200, 200, 200));
		object3d2.addElem(line3d);
		line3d = new Line3D(0, 4, new Color(200, 200, 200));
		object3d2.addElem(line3d);
		line3d = new Line3D(1, 5, new Color(200, 200, 200));
		object3d2.addElem(line3d);
		line3d = new Line3D(3, 7, new Color(200, 200, 200));
		object3d2.addElem(line3d);
		line3d = new Line3D(2, 6, new Color(200, 200, 200));
		object3d2.addElem(line3d);
		object3d1.addVert(0.0F, 0.0F, 0.0F);
		Sphere3D sphere3d = new Sphere3D(0, 1.0F, Color.red);
		object3d1.addElem(sphere3d);
		object3d.addChild(object3d2);
	}

	public void showlist() {
		li.setMultipleMode(true);
		Frame frame = new Frame("List");
		actionhandler actionhandler1 = new actionhandler(frame);
		li.addActionListener(this);
		Button button = new Button("Refresh");
		button.addActionListener(this);
		frame.add("North", li);
		frame.add("South", button);
		frame.pack();
		frame.setVisible(true);
		frame.addWindowListener(actionhandler1);
	}

	public void updatelist() {
		arr = new Array(matrix.length + 1, matrix[0].length + 1);
		for (int i = 0; i < matrix.length; i++) {
			arr.setIJ(i, matrix[0].length, 0.0F);
			for (int j = 0; j < matrix[i].length; j++) {
				arr.setIJ(matrix.length, j, 0.0F);
				if (li.isIndexSelected(li.getItemCount() - i - 1))
					arr.setIJ(i, j, matrix[i][j]);
				else
					arr.setIJ(i, j, 0.0F);
			}

		}

		dv.setArray(arr);
	}
}
