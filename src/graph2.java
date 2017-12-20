import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.PrintStream;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class graph2 extends Frame
	implements ComponentListener, ActionListener, ItemListener {

	int matrix[][];
	String names[];
	int selectedind[];
	tools t;
	Frame saveframe;
	Checkbox cb1;
	Checkbox cb2;
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
	Image backim;
	Graphics offGraphics;
	filehandler fh;
	actionhandler ah;
	List li;
	int inframeymin;
	int inframeymax;
	String title;
	boolean clusterdata;
	paintpanel p2;
	int ytemp;
	Button sh3d;
	analyse a;
	Frame fl;
	cluster cl;
	String yneglabels[];
	String yposlabels[];
	JPanel jp;
	boolean dontpaintscale;
	int xmax;
	int ymax;
	int ymin;

	public graph2(gen gen1, analyse analyse1, filehandler filehandler1, cluster cluster1) {
		t = new tools();
		ah = new actionhandler(this);
		li = new List(10);
		inframeymin = 9999;
		inframeymax = -9999;
		title = "";
		p2 = new paintpanel(0);
		ytemp = 0;
		sh3d = new Button("3D");
		jp = new JPanel(new BorderLayout());
		dontpaintscale = false;
		xmax = 0;
		ymax = 0;
		ymin = 0;
		cl = cluster1;
		li.setMultipleMode(true);
		li.addItemListener(this);
		clusterdata = true;
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		setIconImage(toolkit.getImage("im/curv.gif"));
		a = analyse1;
		setTitle("Gene graph");
		trunk = gen1;
		countgenes(gen1);
		matrix = new int[matrixheight][analyse1.data[0].length];
		names = new String[matrixheight];
		makematrix(gen1, analyse1.data);
		mkgui();
		selectedind = li.getSelectedIndexes();
		repaint();
	}

	public graph2(int ai[][], String as[], String s, cluster cluster1) {
		t = new tools();
		ah = new actionhandler(this);
		li = new List(10);
		inframeymin = 9999;
		inframeymax = -9999;
		title = "";
		p2 = new paintpanel(0);
		ytemp = 0;
		sh3d = new Button("3D");
		jp = new JPanel(new BorderLayout());
		dontpaintscale = false;
		xmax = 0;
		ymax = 0;
		ymin = 0;
		cl = cluster1;
		li.setMultipleMode(true);
		li.addItemListener(this);
		if (as == null)
			System.out.print("names er null!");
		clusterdata = false;
		names = as;
		setTitle(s);
		matrix = ai;
		mkgui();
		setlist();
		selectedind = li.getSelectedIndexes();
		repaint();
	}

	public graph2(int ai[][], String as[], String s, cluster cluster1, int i) {
		t = new tools();
		ah = new actionhandler(this);
		li = new List(10);
		inframeymin = 9999;
		inframeymax = -9999;
		title = "";
		p2 = new paintpanel(0);
		ytemp = 0;
		sh3d = new Button("3D");
		jp = new JPanel(new BorderLayout());
		dontpaintscale = false;
		xmax = 0;
		ymax = 0;
		ymin = 0;
		cl = cluster1;
		dontpaintscale = true;
		li.setMultipleMode(true);
		li.addItemListener(this);
		if (as == null)
			System.out.print("names er null!");
		clusterdata = false;
		names = as;
		setTitle(s);
		matrix = ai;
		mkgui2();
		setlist();
		selectedind = li.getSelectedIndexes();
		repaint();
	}

	public void actionPerformed(ActionEvent actionevent) {
		som2 som2_1;
		prof prof1;
		graph3d graph3d1;
		if (actionevent.getActionCommand() == "Close") {
			if (fl != null)
				fl.dispose();
			dispose();
		} else
		if (actionevent.getActionCommand() == "OK") {
			if (cb1.getState())
				fh.saveimage(backim, 0, 0);
			else
				fh.savegenes(names, matrix);
			saveframe.dispose();
		} else
		if (actionevent.getActionCommand() == "Save")
			savechose();
		else
		if (actionevent.getActionCommand() == "SOM")
			som2_1 = new som2(matrix, names, 1, cl);
		else
		if (actionevent.getActionCommand() == "Profiler")
			prof1 = new prof(matrix, names, cl);
		else
		if (actionevent.getActionCommand() == "3D")
			graph3d1 = new graph3d(matrix, names, "3D version");
		else
		if (actionevent.getActionCommand() == "List")
			showlist();
		else
		if (actionevent.getActionCommand() == "Refresh") {
			selectedind = li.getSelectedIndexes();
			repaint();
		} else
		if (actionevent.getActionCommand() == "ALL") {
			for (int i = 0; i < li.getItemCount(); i++)
				li.select(i);

			selectedind = li.getSelectedIndexes();
			repaint();
		} else
		if (actionevent.getActionCommand() == "NONE") {
			for (int j = 0; j < li.getItemCount(); j++)
				li.deselect(j);

			selectedind = li.getSelectedIndexes();
			repaint();
		} else {
			selectedind = li.getSelectedIndexes();
		}
		repaint();
	}

	public void componentHidden(ComponentEvent componentevent) {
	}

	public void componentMoved(ComponentEvent componentevent) {
	}

	public void componentResized(ComponentEvent componentevent) {
		Dimension dimension = p2.getSize();
		frameheight = dimension.height;
		framewidth = dimension.width;
		repaint();
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

	public void drawPolyline(int ai[], int ai1[], int ai2[], Graphics g, int i, int j, int k) {
		boolean flag = false;
		if (cl != null && cl.a != null) {
			i = cl.a.minvalue;
			j = cl.a.maxvalue;
		}
		for (int j1 = 1; j1 < ai.length; j1++)
			if (k != 0) {
				if (ai2[j1] < 0) {
					int l = Math.abs(60 + (int)(((double)ai2[j1] / (double)i) * 180D));
					if (l > 250 || l < 0)
						l = 0;
					g.setColor(new Color(0, l, 0));
				} else {
					int i1 = Math.abs(60 + (int)(((double)ai2[j1] / (double)j) * 180D));
					if (i1 > 250 || i1 < 0)
						i1 = 0;
					g.setColor(new Color(i1, 0, 0));
				}
				if (dontpaintscale) {
					g.setColor(Color.blue);
					g.drawRect(ai[j1 - 1] - 2, ai1[j1 - 1] - 2, 4, 4);
					g.setColor(Color.black);
				}
				g.drawLine(ai[j1 - 1], ai1[j1 - 1], ai[j1], ai1[j1]);
			} else {
				g.setColor(new Color(200, 170, 10));
			}

		if (dontpaintscale && k != 0) {
			g.setColor(Color.blue);
			g.drawRect(ai[ai.length - 1] - 2, ai1[ai.length - 1] - 2, 4, 4);
		}
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
		selectedind = li.getSelectedIndexes();
		repaint();
	}

	public static void main(String args[]) {
		String args1[] = {
			"test1", "test2", "test3", "test4", "test5"
		};
		int ai[][] = new int[5][5];
		ai[2][3] = 5;
		ai[2][4] = -1;
		ai[0][0] = 1;
		graph2 graph2_1 = new graph2(ai, args1, "test", new cluster());
	}

	public void makelabely() {
		double d = Math.max(Math.abs(ymax), Math.abs(ymin));
		d = d;
		d /= 5D;
		yneglabels = new String[(int)((double)(frameheight / 2) / 5D)];
		yposlabels = new String[(int)((double)(frameheight / 2) / 5D)];
		for (int i = 0; i < yneglabels.length; i++) {
			yneglabels[i] = String.valueOf((d * (double)i) / 1000D);
			yposlabels[i] = String.valueOf((-d * (double)i) / 1000D);
			if (yneglabels[i].length() > 7)
				yneglabels[i] = yneglabels[i].substring(0, 7);
			if (yposlabels[i].length() > 7)
				yposlabels[i] = yposlabels[i].substring(0, 7);
		}

	}

	public void makematrix(gen gen1, int ai[][]) {
		if (gen1.merged) {
			makematrix(gen1.right, ai);
			makematrix(gen1.left, ai);
		} else {
			li.add(a.names[t.intval(gen1.name)]);
			li.select(li.getItemCount() - 1);
			names[matrixindex] = a.names[t.intval(gen1.name)];
			for (int i = 0; i < ai[0].length; i++)
				matrix[matrixindex][i] = ai[t.intval(gen1.name)][i];

			matrixindex++;
		}
	}

	public void mkgui() {
		frameheight = 220;
		framewidth = 250;
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		setIconImage(toolkit.getImage("im/curv.gif"));
		addWindowListener(ah);
		fh = cl.fh;
		setBackground(Color.white);
		addComponentListener(this);
		setSize(framewidth, frameheight);
		setLayout(new BorderLayout());
		Panel panel = new Panel(new BorderLayout());
		Panel panel1 = new Panel(new GridLayout(2, 2));
		JButton jbutton = new JButton("Close");
		JButton jbutton1 = new JButton("Save");
		JButton jbutton2 = new JButton("SOM");
		JButton jbutton3 = new JButton("Profiler");
		JButton jbutton4 = new JButton("List");
		JButton jbutton5 = new JButton("3D");
		jbutton.addActionListener(this);
		jbutton1.addActionListener(this);
		jbutton2.addActionListener(this);
		jbutton3.addActionListener(this);
		jbutton4.addActionListener(this);
		jbutton5.addActionListener(this);
		jbutton.setBackground(new Color(201, 201, 240));
		jbutton1.setBackground(new Color(201, 201, 240));
		jbutton2.setBackground(new Color(201, 201, 240));
		jbutton3.setBackground(new Color(201, 201, 240));
		jbutton4.setBackground(new Color(201, 201, 240));
		jbutton5.setBackground(new Color(201, 201, 240));
		jp.setBorder(new EtchedBorder());
		jp.add("Center", p2);
		panel1.add(jbutton5);
		panel1.add(jbutton2);
		panel1.add(jbutton4);
		panel1.add(jbutton1);
		panel1.add(jbutton);
		panel1.add(jbutton3);
		add("Center", jp);
		add("South", panel1);
		setLocation(cl.getLocation().x + 590, cl.getLocation().y + 230);
		setVisible(true);
		selectedind = li.getSelectedIndexes();
		repaint();
	}

	public void mkgui2() {
		frameheight = 220;
		framewidth = 250;
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		setIconImage(toolkit.getImage("im/curv.gif"));
		addWindowListener(ah);
		fh = cl.fh;
		setBackground(Color.white);
		addComponentListener(this);
		setSize(framewidth, frameheight);
		setLayout(new BorderLayout());
		Panel panel = new Panel(new BorderLayout());
		Panel panel1 = new Panel(new GridLayout(1, 2));
		JButton jbutton = new JButton("Close");
		JButton jbutton1 = new JButton("Save");
		JButton jbutton2 = new JButton("SOM");
		JButton jbutton3 = new JButton("Profiler");
		JButton jbutton4 = new JButton("List");
		JButton jbutton5 = new JButton("3D");
		jbutton.addActionListener(this);
		jbutton1.addActionListener(this);
		jbutton2.addActionListener(this);
		jbutton3.addActionListener(this);
		jbutton4.addActionListener(this);
		jbutton5.addActionListener(this);
		jbutton.setBackground(new Color(201, 201, 240));
		jbutton1.setBackground(new Color(201, 201, 240));
		jbutton2.setBackground(new Color(201, 201, 240));
		jbutton3.setBackground(new Color(201, 201, 240));
		jbutton4.setBackground(new Color(201, 201, 240));
		jbutton5.setBackground(new Color(201, 201, 240));
		jp.setBorder(new EtchedBorder());
		jp.add("Center", p2);
		panel1.add(jbutton1);
		panel1.add(jbutton);
		add("Center", jp);
		add("South", panel1);
		setLocation(cl.getLocation().x + 590, cl.getLocation().y + 230);
		setVisible(true);
		selectedind = li.getSelectedIndexes();
		repaint();
	}

	public void paint(Graphics g) {
		update(g);
	}

	public void savechose() {
		if (saveframe != null)
			saveframe.dispose();
		saveframe = new Frame("Save");
		JButton jbutton = new JButton("OK");
		jbutton.addActionListener(this);
		jbutton.setBackground(new Color(201, 201, 240));
		CheckboxGroup checkboxgroup = new CheckboxGroup();
		cb1 = new Checkbox("Graph image", checkboxgroup, true);
		cb2 = new Checkbox("Gene Identifiers and expression values", checkboxgroup, false);
		cb1.setBackground(new Color(200, 210, 200));
		cb2.setBackground(new Color(200, 210, 200));
		Panel panel = new Panel(new GridLayout(2, 1));
		panel.setBackground(new Color(200, 210, 200));
		panel.add(cb1);
		panel.add(cb2);
		Label label = new Label("Choose what to save");
		label.setBackground(new Color(200, 210, 200));
		saveframe.add("North", label);
		saveframe.add("Center", panel);
		saveframe.add("South", jbutton);
		saveframe.pack();
		saveframe.setLocation(getLocation().x + 100, getLocation().y + 150);
		actionhandler actionhandler1 = new actionhandler(saveframe);
		saveframe.addWindowListener(actionhandler1);
		saveframe.setVisible(true);
	}

	public void setlist() {
		for (int i = 0; i < matrix.length; i++) {
			li.add(names[i]);
			li.select(li.getItemCount() - 1);
		}

	}

	public void showlist() {
		li.setMultipleMode(true);
		fl = new Frame("List");
		actionhandler actionhandler1 = new actionhandler(fl);
		li.addActionListener(this);
		JButton jbutton = new JButton("ALL");
		JButton jbutton1 = new JButton("NONE");
		Panel panel = new Panel(new GridLayout(1, 2));
		jbutton.setBackground(new Color(201, 201, 240));
		jbutton.addActionListener(this);
		jbutton1.setBackground(new Color(201, 201, 240));
		jbutton1.addActionListener(this);
		panel.add(jbutton);
		panel.add(jbutton1);
		fl.add("Center", li);
		fl.add("South", panel);
		fl.setSize(150, 220);
		fl.setLocation(getLocation().x + 250, getLocation().y);
		fl.setVisible(true);
		fl.addWindowListener(actionhandler1);
	}

	public void update(Graphics g) {
		inframeymin = 0x1869f;
		inframeymax = 0xfffe7961;
		boolean flag = false;
		Dimension dimension = p2.getSize();
		frameheight = dimension.height;
		framewidth = dimension.width;
		double d1 = (double)frameheight / 2D;
		int i = t.idivideint(framewidth, 2);
		int j = t.idivideint(frameheight, 2);
		try {
			if (offImage != null) {
				offGraphics.setColor(getBackground());
				offGraphics.fillRect(0, 0, framewidth, frameheight);
			}
			offImage = createImage(framewidth, frameheight);
			offGraphics = offImage.getGraphics();
			offGraphics.setColor(new Color(80, 10, 50));
			offGraphics.drawLine(50, j, framewidth - 10, j);
			offGraphics.drawLine(50, 0, 50, frameheight);
			offGraphics.setColor(new Color(200, 250, 190));
			xmax = matrix[0].length;
			for (int k = 0; k < matrix.length; k++) {
				for (int l = 0; l < xmax; l++) {
					if (matrix[k][l] < ymin)
						ymin = matrix[k][l];
					if (matrix[k][l] > ymax)
						ymax = matrix[k][l];
				}

			}

			yvals = new int[xmax];
			yraw = new int[xmax];
			xaxis = new int[xmax];
			ymean = new int[xmax];
			yspan = ymax - ymin;
			for (int i1 = 0; i1 < matrix[0].length; i1++) {
				xaxis[i1] = 50 + i1 * t.idivideint(framewidth - 50, xmax);
				ytemp = i1 * t.idivideint(frameheight, xmax);
				offGraphics.setColor(new Color(220, 220, 220));
				if (i1 > 0)
					offGraphics.drawLine(xaxis[i1], 0, xaxis[i1], frameheight);
			}

			double d = Math.max(Math.abs(ymax), Math.abs(ymin));
			makelabely();
			offGraphics.setFont(new Font("Arial", 0, 9));
			for (int j1 = 1; j1 < yneglabels.length; j1++) {
				offGraphics.setColor(Color.black);
				offGraphics.drawString(yposlabels[j1], 10, (int)(d1 + 2D + ((d1 - 10D) / 5D) * (double)j1));
				offGraphics.drawString(yneglabels[j1], 10, (int)((d1 + 2D) - ((d1 - 10D) / 5D) * (double)j1));
				offGraphics.setColor(new Color(200, 200, 200));
				offGraphics.drawLine(51, (int)(d1 - ((d1 - 10D) / 5D) * (double)j1), framewidth - 10, (int)(d1 - ((d1 - 10D) / 5D) * (double)j1));
				offGraphics.drawLine(51, (int)(d1 + ((d1 - 10D) / 5D) * (double)j1), framewidth - 10, (int)(d1 + ((d1 - 10D) / 5D) * (double)j1));
			}

			for (int k1 = 0; k1 < matrix.length; k1++) {
				for (int l1 = 0; l1 < matrix[k1].length; l1++) {
					yraw[l1] = matrix[k1][l1];
					yvals[l1] = (int)Math.round(d1 - ((double)matrix[k1][l1] / d) * (d1 - 10D));
					if (yvals[l1] < inframeymin)
						inframeymin = yvals[l1];
					if (yvals[l1] > inframeymax)
						inframeymax = yvals[l1];
					ymean[l1] = ymean[l1] + yvals[l1];
				}

				if (isselected(k1))
					drawPolyline(xaxis, yvals, yraw, offGraphics, ymin, ymax, 1);
				else
					drawPolyline(xaxis, yvals, yraw, offGraphics, ymin, ymax, 0);
			}

			for (int i2 = 0; i2 < ymean.length; i2++)
				ymean[i2] = t.idivideint(ymean[i2], matrixheight);

			offGraphics.setColor(Color.blue);
			offGraphics.drawPolyline(xaxis, ymean, xaxis.length);
			offGraphics.setColor(Color.black);
			offGraphics.drawString("0", 10, j + 2);
			offGraphics.drawLine(46, inframeymin, 54, inframeymin);
			offGraphics.drawLine(46, inframeymax, 54, inframeymax);
			boolean flag1 = false;
			int k2 = 0;
			int l2 = 0;
			if (cl != null && cl.a != null) {
				l2 = cl.a.minvalue;
				k2 = cl.a.maxvalue;
			}
			if (k2 == 0)
				k2 = ymax;
			if (!dontpaintscale) {
				for (int i3 = 0; i3 < 10; i3++) {
					int j2 = (int)Math.abs(60D + ((((((d1 - 10D) / 10D) * (double)i3) / (d1 - 10D)) * (double)ymin) / (double)l2) * 180D);
					if (j2 > 250 || j2 < 0)
						offGraphics.setColor(new Color(0, 0, 0));
					else
						offGraphics.setColor(new Color(0, j2, 0));
					offGraphics.fillRect(0, (int)Math.round(d1 + (double)i3 * (d1 / 10D)), 5, 1 + (int)Math.round(d1 / 10D));
					j2 = (int)Math.abs(60D + (((((d1 / 10D) * (double)i3) / d1) * (double)ymax) / (double)k2) * 180D);
					if (j2 > 250 || j2 < 0)
						offGraphics.setColor(new Color(0, 0, 0));
					else
						offGraphics.setColor(new Color(j2, 0, 0));
					offGraphics.fillRect(0, (int)Math.round(d1 - d1 / 10D - (double)i3 * (d1 / 10D)), 5, 1 + (int)Math.round(d1 / 10D));
				}

			}
			backim = offImage;
			p2.setimage(offImage);
			jp.repaint();
		}
		catch (NullPointerException _ex) {
			System.out.print("Graph 0-0P");
		}
	}
}
