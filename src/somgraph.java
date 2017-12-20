import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.List;
import java.awt.Panel;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.PrintStream;

public class somgraph extends Panel
	implements ComponentListener, MouseListener {

	int matrix[][];
	String names[];
	int selectedind[];
	tools t;
	int xaxis[];
	int yvals[];
	int yraw[];
	int ymean[];
	boolean justmean;
	vispca pca;
	som3d s3d;
	int xinframe;
	int yinframe;
	cluster cl;
	int yspan;
	Math m;
	gen trunk;
	int matrixheight;
	int matrixindex;
	int frameheight;
	int framewidth;
	Image offImage;
	Graphics offGraphics;
	List li;
	int inframeymin;
	int inframeymax;
	String title;
	boolean clusterdata;
	Frame f;

	public somgraph(int ai[][], String s, cluster cluster1, String as[]) {
		t = new tools();
		justmean = true;
		li = new List(10);
		inframeymin = 9999;
		inframeymax = -9999;
		title = "";
		cl = cluster1;
		names = as;
		clusterdata = false;
		setSize(framewidth, frameheight);
		setBackground(new Color(25, 25, 25));
		matrix = ai;
		addMouseListener(this);
		addComponentListener(this);
		setBackground(new Color(140, 135, 135));
	}

	public somgraph(int ai[][], String as[], String s, int i, int j, cluster cluster1) {
		t = new tools();
		justmean = true;
		li = new List(10);
		inframeymin = 9999;
		inframeymax = -9999;
		title = "";
		cl = cluster1;
		names = as;
		if (as == null)
			System.out.print("Names er null i somgraph");
		clusterdata = false;
		frameheight = i;
		framewidth = j;
		setSize(framewidth, frameheight);
		matrix = ai;
		addMouseListener(this);
		addComponentListener(this);
		setBackground(new Color(140, 135, 135));
	}

	public void componentHidden(ComponentEvent componentevent) {
	}

	public void componentMoved(ComponentEvent componentevent) {
	}

	public void componentResized(ComponentEvent componentevent) {
		Dimension dimension = getSize();
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
			} else
			if (k == 1)
				g.setColor(new Color(200, 170, 10));
			else
			if (k == 2)
				g.setColor(Color.blue);
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

	public void mouseClicked(MouseEvent mouseevent) {
		if (pca != null) {
			pca.clickx = xinframe;
			pca.clicky = yinframe;
			pca.redraw();
			pca.repaint();
		}
		if (s3d != null) {
			s3d.clickx = xinframe;
			s3d.clicky = yinframe;
			s3d.remake();
		}
		graph2 graph2_1 = new graph2(matrix, names, "somgraph", cl);
		graph2_1.setVisible(true);
	}

	public void mouseEntered(MouseEvent mouseevent) {
	}

	public void mouseExited(MouseEvent mouseevent) {
	}

	public void mousePressed(MouseEvent mouseevent) {
	}

	public void mouseReleased(MouseEvent mouseevent) {
	}

	public void paint(Graphics g) {
		update(g);
	}

	public void pop() {
		f = new Frame("Somgraph");
		f.add(this);
		f.setVisible(true);
	}

	public void setsz(int i, int j) {
		setSize(i, j);
		frameheight = i;
		framewidth = j;
		repaint();
	}

	public void update(Graphics g) {
		inframeymin = 0x1869f;
		inframeymax = 0xfffe7961;
		boolean flag = false;
		boolean flag1 = false;
		int j = 0;
		int k = 0;
		int l = t.idivideint(framewidth, 2);
		int i1 = t.idivideint(frameheight, 2);
		try {
			if (offImage != null) {
				offGraphics.setColor(getBackground());
				offGraphics.fillRect(0, 0, framewidth, frameheight);
			}
			offImage = createImage(framewidth, frameheight);
			offGraphics = offImage.getGraphics();
			offGraphics.setColor(new Color(80, 10, 50));
			offGraphics.drawLine(3, i1, framewidth, i1);
			offGraphics.drawLine(3, 10, 3, frameheight);
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
				xaxis[l1] = 3 + l1 * t.idivideint(framewidth, i);

			for (int i2 = 0; i2 < matrix.length; i2++) {
				for (int j2 = 0; j2 < matrix[i2].length; j2++) {
					yraw[j2] = matrix[i2][j2];
					yvals[j2] = i1 - t.norm(matrix[i2][j2], t.idivideint(frameheight, 2), yspan);
					if (yvals[j2] < inframeymin)
						inframeymin = yvals[j2];
					if (yvals[j2] > inframeymax)
						inframeymax = yvals[j2];
					ymean[j2] = ymean[j2] + yvals[j2];
				}

				if (!justmean)
					if (isselected(i2))
						drawPolyline(xaxis, yvals, yraw, offGraphics, k, j, 1);
					else
						drawPolyline(xaxis, yvals, yraw, offGraphics, k, j, 0);
			}

			if (justmean) {
				for (int k2 = 0; k2 < ymean.length; k2++)
					ymean[k2] = t.idivideint(ymean[k2], matrix.length);

				drawPolyline(xaxis, ymean, yraw, offGraphics, k, j, 2);
			}
			offGraphics.setColor(Color.black);
			offGraphics.setFont(new Font("arial", 0, 8));
			if (framewidth > 30 && frameheight > 30)
				offGraphics.drawString("n=" + matrix.length, 10, frameheight);
			g.drawImage(offImage, 0, 0, this);
		}
		catch (NullPointerException _ex) {
			System.out.print("Graph 0-0P");
		}
	}
}
