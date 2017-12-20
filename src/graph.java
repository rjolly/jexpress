import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
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
import java.io.PrintStream;

public class graph extends Frame
	implements ComponentListener, ActionListener {

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
	List li;
	int inframeymin;
	int inframeymax;
	String title;
	boolean clusterdata;
	cluster cl;

	public graph(gen gen1, int ai[][], String as[], filehandler filehandler1, cluster cluster) {
		t = new tools();
		frameheight = 130;
		framewidth = 150;
		ah = new actionhandler(this);
		li = new List(10);
		inframeymin = 9999;
		inframeymax = -9999;
		title = "";
		cl = cluster;
		clusterdata = true;
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		setIconImage(toolkit.getImage("im/curv.gif"));
		names = as;
		addWindowListener(ah);
		fh = filehandler1;
		setBackground(Color.white);
		setTitle("Gene graph");
		addComponentListener(this);
		setSize(frameheight + 20, framewidth + 20);
		trunk = gen1;
		countgenes(gen1);
		matrix = new int[matrixheight][ai[0].length];
		makematrix(gen1, ai);
		repaint();
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
		add("South", panel);
		setVisible(true);
	}

	public graph(int ai[][], String s, cluster cluster) {
		t = new tools();
		frameheight = 130;
		framewidth = 150;
		ah = new actionhandler(this);
		li = new List(10);
		inframeymin = 9999;
		inframeymax = -9999;
		title = "";
		cl = cluster;
		title = s;
		clusterdata = false;
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		fh = new filehandler();
		frameheight = 130;
		framewidth = 160;
		setIconImage(toolkit.getImage("im/curv.gif"));
		addWindowListener(ah);
		fh = fh;
		setTitle("Base");
		addComponentListener(this);
		setSize(frameheight, framewidth);
		setBackground(Color.white);
		addComponentListener(this);
		setSize(frameheight, framewidth);
		matrix = ai;
		repaint();
		setLayout(new BorderLayout());
		Panel panel = new Panel(new GridLayout(1, 2));
		Button button = new Button("Close");
		button.addActionListener(this);
		button.setBackground(Color.lightGray);
		Button button1 = new Button("Save");
		button1.addActionListener(this);
		button1.setBackground(Color.lightGray);
		panel.add(button);
		panel.add(button1);
		add("South", panel);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent actionevent) {
		if (actionevent.getActionCommand() == "Close")
			dispose();
		else
		if (actionevent.getActionCommand() == "Save")
			fh.saveimage(offImage, 0, 0);
		else
		if (actionevent.getActionCommand() == "List")
			showlist();
		else
		if (actionevent.getActionCommand() == "Refresh") {
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
		Dimension dimension = getSize();
		frameheight = dimension.height - 50;
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
		if (i == 0)
			i = 1;
		if (j == 0)
			j = 1;
		for (int j1 = 1; j1 < ai.length; j1++) {
			if (k == 0) {
				if (ai2[j1] < 0) {
					int l = Math.abs(60 + (int)(((double)ai2[j1] / (double)i) * 180D));
					g.setColor(new Color(0, l, 0));
				} else {
					int i1 = Math.abs(60 + (int)(((double)ai2[j1] / (double)j) * 180D));
					g.setColor(new Color(i1, 0, 0));
				}
			} else {
				g.setColor(new Color(200, 170, 10));
			}
			g.drawLine(ai[j1 - 1], ai1[j1 - 1], ai[j1], ai1[j1]);
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

	public void paint(Graphics g) {
		update(g);
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

	public void update(Graphics g) {
		inframeymin = 0x1869f;
		inframeymax = 0xfffe7961;
		boolean flag = false;
		boolean flag1 = false;
		int j = 0;
		int k = 0;
		int l = t.idivideint(framewidth, 2) + 20;
		int i1 = t.idivideint(frameheight, 2) + 20;
		try {
			if (offImage != null) {
				offGraphics.setColor(getBackground());
				offGraphics.fillRect(0, 0, framewidth, frameheight);
			}
			offImage = createImage(framewidth, frameheight);
			offGraphics = offImage.getGraphics();
			offGraphics.setColor(new Color(80, 10, 50));
			offGraphics.drawLine(30, i1, framewidth, i1);
			offGraphics.drawLine(30, 10, 30, frameheight + 50);
			offGraphics.setColor(new Color(200, 250, 190));
			int i = matrix[0].length;
			for (int j1 = 0; j1 < matrix.length; j1++) {
				for (int k1 = 0; k1 < i; k1++) {
					if (matrix[j1][k1] < k)
						k = matrix[j1][k1];
					if (matrix[j1][k1] > j)
						j = matrix[j1][k1];
				}

			}

			yvals = new int[i];
			yraw = new int[i];
			xaxis = new int[i];
			ymean = new int[i];
			yspan = j - k;
			for (int l1 = 0; l1 < matrix[0].length; l1++)
				xaxis[l1] = 30 + l1 * t.idivideint(framewidth - 40, i);

			for (int i2 = 0; i2 < matrix.length; i2++) {
				for (int j2 = 0; j2 < matrix[i2].length; j2++) {
					yraw[j2] = matrix[i2][j2];
					yvals[j2] = i1 - t.norm(matrix[i2][j2], t.idivideint(frameheight - 30, 2), yspan);
					if (yvals[j2] < inframeymin)
						inframeymin = yvals[j2];
					if (yvals[j2] > inframeymax)
						inframeymax = yvals[j2];
					ymean[j2] = ymean[j2] + yvals[j2];
				}

				if (isselected(i2))
					drawPolyline(xaxis, yvals, yraw, offGraphics, k, j, 1);
				else
					drawPolyline(xaxis, yvals, yraw, offGraphics, k, j, 0);
			}

			for (int k2 = 0; k2 < ymean.length; k2++)
				ymean[k2] = t.idivideint(ymean[k2], matrixheight);

			offGraphics.setColor(Color.blue);
			offGraphics.drawPolyline(xaxis, ymean, xaxis.length);
			offGraphics.setColor(Color.black);
			offGraphics.setFont(new Font("Arial", 0, 9));
			offGraphics.drawString("0", 20, i1 + 3);
			offGraphics.drawLine(28, inframeymin, 32, inframeymin);
			offGraphics.drawLine(28, inframeymax, 32, inframeymax);
			if (clusterdata) {
				if (String.valueOf((double)j / 1000D).length() > 4)
					offGraphics.drawString(String.valueOf((double)j / 1000D).substring(0, 4), 10, inframeymin + 3);
				else
					offGraphics.drawString(String.valueOf((double)j / 1000D), 10, inframeymin + 3);
				if (String.valueOf((double)k / 1000D).length() > 4)
					offGraphics.drawString(String.valueOf((double)k / 1000D).substring(0, 4), 10, inframeymax + 3);
				else
					offGraphics.drawString(String.valueOf((double)k / 1000D), 10, inframeymax + 3);
			} else {
				offGraphics.drawString(title, 33, 30);
				if (String.valueOf(j).length() > 4)
					offGraphics.drawString(String.valueOf(j).substring(0, 4), 10, inframeymin + 3);
				else
					offGraphics.drawString(String.valueOf(j), 10, inframeymin + 3);
				if (String.valueOf(k).length() > 4)
					offGraphics.drawString(String.valueOf(k).substring(0, 4), 10, inframeymax + 3);
				else
					offGraphics.drawString(String.valueOf(k), 10, inframeymax + 3);
			}
			g.drawImage(offImage, 0, 0, this);
		}
		catch (NullPointerException _ex) {
			System.out.print("Graph 0-0P");
		}
	}
}
