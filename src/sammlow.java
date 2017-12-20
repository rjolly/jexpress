import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Point;
import java.awt.TextComponent;
import java.awt.TextField;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

public class sammlow extends Frame
	implements ComponentListener, ActionListener, MouseListener, MouseMotionListener {

	Frame f1;
	Frame f2;
	Panel south;
	TextField t1;
	TextField t2;
	TextField t3;
	TextField t4;
	Button b;
	Button prev;
	Button next;
	Image offImage;
	Graphics offGraphics;
	paintpanel pp;
	LowPointDataset lp;
	int neurons;
	actionhandler ah;
	node net[][];
	Image mov[];
	Image points;
	Graphics pnts;
	boolean resized;
	int imgnr;
	node clicked;
	int clickx;
	int clicky;
	int startx;
	int starty;
	int endx;
	int endy;
	double dataset[][];
	cluster cl;
	boolean isneurons;
	String names[];
	int frameheight;
	int framewidth;
	double mxmax;
	double mxmin;
	double mymax;
	double mymin;
	int totx;
	int toty;

	public sammlow(double ad[][], String as[], cluster cluster1) {
		south = new Panel(new GridLayout(1, 2));
		t1 = new TextField(20);
		t2 = new TextField(20);
		t3 = new TextField(20);
		t4 = new TextField(20);
		b = new Button("Resize");
		prev = new Button("<");
		next = new Button(">");
		pp = new paintpanel(1);
		ah = new actionhandler(this);
		resized = true;
		imgnr = -1;
		clickx = -1;
		clicky = -1;
		isneurons = true;
		frameheight = 0;
		framewidth = 0;
		mxmax = -99999.899999999994D;
		mxmin = 99999.899999999994D;
		mymax = -99999.899999999994D;
		mymin = 99999.899999999994D;
		totx = 0;
		toty = 0;
		addWindowListener(ah);
		cl = cluster1;
		isneurons = false;
		names = as;
		dataset = ad;
		setTitle("Karhunen Loeve transposition");
		pp.addMouseMotionListener(this);
		pp.addMouseListener(this);
		setBackground(Color.lightGray);
		lp = new LowPointDataset(ad);
		lp.vals();
		lay();
		redraw();
	}

	public sammlow(double ad[][], String as[], node anode[][], cluster cluster1) {
		south = new Panel(new GridLayout(1, 2));
		t1 = new TextField(20);
		t2 = new TextField(20);
		t3 = new TextField(20);
		t4 = new TextField(20);
		b = new Button("Resize");
		prev = new Button("<");
		next = new Button(">");
		pp = new paintpanel(1);
		ah = new actionhandler(this);
		resized = true;
		imgnr = -1;
		clickx = -1;
		clicky = -1;
		isneurons = true;
		frameheight = 0;
		framewidth = 0;
		mxmax = -99999.899999999994D;
		mxmin = 99999.899999999994D;
		mymax = -99999.899999999994D;
		mymin = 99999.899999999994D;
		totx = 0;
		toty = 0;
		addWindowListener(ah);
		cl = cluster1;
		names = as;
		dataset = ad;
		setTitle("2D SOM Window");
		pp.addMouseMotionListener(this);
		pp.addMouseListener(this);
		setBackground(Color.lightGray);
		net = anode;
		lp = new LowPointDataset(ad);
		lp.vals();
		lay();
		update(ad, anode);
	}

	public void actionPerformed(ActionEvent actionevent) {
		Object obj = null;
		double d = 0.0D;
		boolean flag = false;
		String s = t1.getText();
		if (actionevent.getActionCommand() == "<" && imgnr > 0)
			imgnr--;
		else
		if (actionevent.getActionCommand() == ">" && imgnr < mov.length - 1)
			imgnr++;
		else
		if (actionevent.getActionCommand() == "Resize") {
			pp.repaint();
			redraw();
		}
		repaint();
	}

	public void addtomov(Image image) {
		if (mov == null) {
			mov = new Image[1];
			mov[0] = image;
		} else {
			Image aimage[] = new Image[mov.length + 1];
			for (int i = 0; i < mov.length; i++)
				aimage[i] = mov[i];

			aimage[mov.length] = image;
			mov = aimage;
		}
	}

	public void clickednode(node node1) {
		clicked = node1;
		redraw();
	}

	public void componentHidden(ComponentEvent componentevent) {
	}

	public void componentMoved(ComponentEvent componentevent) {
	}

	public void componentResized(ComponentEvent componentevent) {
		Dimension dimension = pp.getSize();
		offImage = createImage(dimension.width, dimension.height);
		offGraphics = offImage.getGraphics();
		offGraphics.setColor(Color.lightGray);
		offGraphics.fillRect(0, 0, dimension.width, dimension.height);
		pp.validate();
		repaint();
	}

	public void componentShown(ComponentEvent componentevent) {
	}

	public int[] intvals(double ad[]) {
		int ai[] = new int[ad.length];
		for (int i = 0; i < ad.length; i++)
			ai[i] = (int)ad[i];

		return ai;
	}

	public int[][] intvals(double ad[][]) {
		int ai[][] = new int[ad.length][ad[0].length];
		for (int i = 0; i < ad.length; i++) {
			for (int j = 0; j < ad[0].length; j++)
				ai[i][j] = (int)ad[i][j];

		}

		return ai;
	}

	public void lay() {
		addComponentListener(this);
		setBackground(Color.lightGray);
		pp.setBackground(Color.lightGray);
		b.addActionListener(this);
		prev.addActionListener(this);
		next.addActionListener(this);
		f1 = new Frame();
		f2 = new Frame();
		setSize(400, 400);
		add("Center", pp);
		add("South", south);
		setLocation(cl.getLocation().x + 200, cl.getLocation().y + 200);
		setVisible(true);
	}

	public void mouseClicked(MouseEvent mouseevent) {
	}

	public void mouseDragged(MouseEvent mouseevent) {
		endx = mouseevent.getX();
		endy = mouseevent.getY();
		redraw();
	}

	public void mouseEntered(MouseEvent mouseevent) {
	}

	public void mouseExited(MouseEvent mouseevent) {
	}

	public void mouseMoved(MouseEvent mouseevent) {
	}

	public void mousePressed(MouseEvent mouseevent) {
		startx = mouseevent.getX();
		starty = mouseevent.getY();
	}

	public void mouseReleased(MouseEvent mouseevent) {
		sweep();
	}

	public void paint(Graphics g) {
		update(g);
	}

	public void redraw() {
		int i = 0;
		if (isneurons)
			i = (int)Math.sqrt(neurons);
		int ai[][] = new int[i][i];
		int ai1[][] = new int[i][i];
		boolean flag = false;
		boolean flag1 = false;
		boolean flag2 = false;
		boolean flag3 = false;
		boolean flag4 = false;
		boolean flag5 = false;
		boolean flag6 = false;
		boolean flag7 = false;
		Dimension dimension = pp.getSize();
		frameheight = dimension.height;
		framewidth = dimension.width;
		if (offImage == null) {
			offImage = createImage(framewidth, frameheight);
			offGraphics = offImage.getGraphics();
			offGraphics.setColor(Color.lightGray);
			offGraphics.fillRect(0, 0, framewidth, frameheight);
		} else {
			offGraphics = offImage.getGraphics();
			offGraphics.setColor(Color.lightGray);
			offGraphics.fillRect(0, 0, framewidth, frameheight);
		}
		if (resized) {
			for (int l2 = 0; l2 < lp.nrPoints; l2++) {
				if (mxmax < lp.XElementAt(l2))
					mxmax = lp.XElementAt(l2);
				if (mxmin > lp.XElementAt(l2))
					mxmin = lp.XElementAt(l2);
				if (mymax < lp.YElementAt(l2))
					mymax = lp.YElementAt(l2);
				if (mymin > lp.YElementAt(l2))
					mymin = lp.YElementAt(l2);
			}

			if (totx == 0) {
				totx = (int)Math.ceil(mxmax - mxmin);
				toty = (int)Math.ceil(mymax - mymin);
			}
			for (int j3 = 0; j3 < lp.nrPoints; j3++) {
				offGraphics.setColor(Color.blue);
				offGraphics.fillRect(20 + (int)(((lp.XElementAt(j3) - mxmin) / (double)totx) * ((double)framewidth - 50D)), 20 + (int)(((lp.YElementAt(j3) - mymin) / (double)toty) * ((double)frameheight - 50D)), 2, 2);
			}

		}
		if (resized) {
			pnts = offGraphics.create();
		} else {
			offGraphics = pnts;
			resized = false;
		}
		offGraphics.setColor(Color.green);
		if (isneurons && net != null) {
			double ad[][] = new double[net.length * net.length][net[0][0].vector.length];
			int i3 = 0;
			for (int k3 = 0; k3 < net.length; k3++) {
				for (int l3 = 0; l3 < net.length; l3++) {
					ad[i3] = net[k3][l3].vector;
					i3++;
				}

			}

			double ad1[][] = lp.getlowvalues(ad);
			i3 = 0;
			for (int i4 = 0; i4 < net.length; i4++) {
				for (int j4 = 0; j4 < net.length; j4++) {
					net[i4][j4].mapx = (int)ad1[i3][0];
					net[i4][j4].mapy = (int)ad1[i3][1];
					i3++;
				}

			}

			for (int k4 = 0; k4 < net.length - 1; k4++) {
				for (int l4 = 0; l4 < net.length; l4++) {
					int j = net[k4][l4].mapx;
					int i1 = net[k4][l4].mapy;
					int l1 = net[k4 + 1][l4].mapx;
					int j2 = net[k4 + 1][l4].mapy;
					j = 20 + (int)((((double)j - mxmin) / (double)totx) * ((double)framewidth - 50D));
					i1 = 20 + (int)((((double)i1 - mymin) / (double)toty) * ((double)frameheight - 50D));
					l1 = 20 + (int)((((double)l1 - mxmin) / (double)totx) * ((double)framewidth - 50D));
					j2 = 20 + (int)((((double)j2 - mymin) / (double)toty) * ((double)frameheight - 50D));
					offGraphics.drawLine(j, i1, l1, j2);
				}

			}

			for (int i5 = 0; i5 < net.length; i5++) {
				for (int j5 = 0; j5 < net.length - 1; j5++) {
					int k = net[i5][j5].mapx;
					int j1 = net[i5][j5].mapy;
					int i2 = net[i5][j5 + 1].mapx;
					int k2 = net[i5][j5 + 1].mapy;
					k = 20 + (int)((((double)k - mxmin) / (double)totx) * ((double)framewidth - 50D));
					j1 = 20 + (int)((((double)j1 - mymin) / (double)toty) * ((double)frameheight - 50D));
					i2 = 20 + (int)((((double)i2 - mxmin) / (double)totx) * ((double)framewidth - 50D));
					k2 = 20 + (int)((((double)k2 - mymin) / (double)toty) * ((double)frameheight - 50D));
					offGraphics.setColor(Color.cyan);
					offGraphics.drawLine(k, j1, i2, k2);
				}

			}

			for (int k5 = 0; k5 < net.length; k5++) {
				for (int l5 = 0; l5 < net.length; l5++) {
					int l = net[k5][l5].mapx;
					int k1 = net[k5][l5].mapy;
					l = 20 + (int)((((double)l - mxmin) / (double)totx) * ((double)framewidth - 50D));
					k1 = 20 + (int)((((double)k1 - mymin) / (double)toty) * ((double)frameheight - 50D));
					if (clickx == k5 && clicky == l5) {
						offGraphics.setColor(Color.yellow);
						offGraphics.drawRect(l, k1, 1, 1);
						offGraphics.drawRect(l - 2, k1 - 2, 5, 5);
						offGraphics.drawRect(l - 6, k1 - 6, 13, 13);
					} else {
						offGraphics.setColor(Color.red);
						offGraphics.drawRect(l, k1, 1, 1);
					}
				}

			}

		}
		offGraphics.setColor(Color.yellow);
		if (startx != endx && starty != endy)
			offGraphics.drawRect(startx, starty, endx - startx, endy - starty);
		if (isneurons) {
			offGraphics.setColor(Color.black);
			offGraphics.drawString("Step: " + imgnr, 5, 10);
		}
		pp.setimage(offImage);
		pp.repaint();
	}

	public void sweep() {
		boolean flag = false;
		boolean flag1 = false;
		int k = 0;
		Vector vector = new Vector();
		Vector vector1 = new Vector();
		for (int l = 0; l < lp.nrPoints; l++) {
			int i = 20 + (int)(((lp.XElementAt(l) - mxmin) / (double)totx) * ((double)framewidth - 50D));
			int j = 20 + (int)(((lp.YElementAt(l) - mymin) / (double)toty) * ((double)frameheight - 50D));
			if (i > startx && i < endx && j > starty && j < endy) {
				k++;
				vector.addElement(intvals(dataset[l]));
				vector1.addElement(cl.a.names[l]);
			}
		}

		if (vector.size() > 0) {
			int ai[][] = new int[vector.size()][dataset[0].length];
			String as[] = new String[vector.size()];
			for (int i1 = 0; i1 < vector.size(); i1++) {
				ai[i1] = (int[])vector.elementAt(i1);
				as[i1] = (String)vector1.elementAt(i1);
			}

			graph2 graph2_1 = new graph2(ai, as, "K.L Trans.", cl);
			graph2_1.setVisible(true);
		}
	}

	public void update(Graphics g) {
		redraw();
	}

	public void update(double ad[][], node anode[][]) {
		boolean flag = false;
		double d = 9999D;
		net = anode;
		imgnr++;
		redraw();
		repaint();
	}

	public void update(double ad[][], node anode[][], boolean flag) {
		boolean flag1 = false;
		double d = 9999D;
		net = anode;
		imgnr++;
		redraw();
	}

	public void update(node anode[][]) {
		boolean flag = false;
		double d = 9999D;
		net = anode;
		imgnr++;
		redraw();
	}
}
