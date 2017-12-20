import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextComponent;
import java.awt.TextField;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Vector;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;

public class samm extends Frame
	implements ActionListener, MouseListener, MouseMotionListener {

	String names[];
	cluster cl;
	int startx;
	int starty;
	int endx;
	int endy;
	Frame f1;
	Frame f2;
	Panel south;
	TextField t1;
	TextField t2;
	TextField t3;
	TextField t4;
	Button b;
	JButton jb1;
	JButton jb2;
	Image offImage;
	Graphics offGraphics;
	paintpanel pp;
	HiPointDataset hp;
	int neurons;
	actionhandler ah;
	double mxmax;
	double mxmin;
	double mymax;
	double mymin;
	int frameheight;
	int framewidth;
	double dataset[][];
	int totx;
	int toty;

	public samm(String s) {
		south = new Panel(new GridLayout(3, 2));
		t1 = new TextField(20);
		t2 = new TextField(20);
		t3 = new TextField(20);
		t4 = new TextField(20);
		b = new Button("Refine");
		jb1 = new JButton("Refine");
		jb2 = new JButton("Save");
		pp = new paintpanel();
		ah = new actionhandler(this);
		mxmax = -99999.899999999994D;
		mxmin = 99999.899999999994D;
		mymax = -99999.899999999994D;
		mymin = 99999.899999999994D;
		frameheight = 0;
		framewidth = 0;
		totx = 0;
		toty = 0;
		try {
			hp = new HiPointDataset(s);
		}
		catch (IOException _ex) {
			System.out.print("IOexception!");
		}
		catch (IllegalFormatException _ex) {
			System.out.print("illegalformat!");
		}
		lay();
	}

	public samm(double ad[][], cluster cluster1, String as[]) {
		south = new Panel(new GridLayout(3, 2));
		t1 = new TextField(20);
		t2 = new TextField(20);
		t3 = new TextField(20);
		t4 = new TextField(20);
		b = new Button("Refine");
		jb1 = new JButton("Refine");
		jb2 = new JButton("Save");
		pp = new paintpanel();
		ah = new actionhandler(this);
		mxmax = -99999.899999999994D;
		mxmin = 99999.899999999994D;
		mymax = -99999.899999999994D;
		mymin = 99999.899999999994D;
		frameheight = 0;
		framewidth = 0;
		totx = 0;
		toty = 0;
		dataset = ad;
		names = as;
		cl = cluster1;
		addWindowListener(ah);
		try {
			hp = new HiPointDataset(ad);
		}
		catch (IOException _ex) {
			System.out.print("IOexception!");
		}
		catch (IllegalFormatException _ex) {
			System.out.print("illegalformat!");
		}
		lay();
	}

	public samm(int ai[][], cluster cluster1, String as[]) {
		south = new Panel(new GridLayout(3, 2));
		t1 = new TextField(20);
		t2 = new TextField(20);
		t3 = new TextField(20);
		t4 = new TextField(20);
		b = new Button("Refine");
		jb1 = new JButton("Refine");
		jb2 = new JButton("Save");
		pp = new paintpanel();
		ah = new actionhandler(this);
		mxmax = -99999.899999999994D;
		mxmin = 99999.899999999994D;
		mymax = -99999.899999999994D;
		mymin = 99999.899999999994D;
		frameheight = 0;
		framewidth = 0;
		totx = 0;
		toty = 0;
		names = as;
		cl = cluster1;
		try {
			hp = new HiPointDataset(ai);
		}
		catch (IOException _ex) {
			System.out.print("IOexception!");
		}
		catch (IllegalFormatException _ex) {
			System.out.print("illegalformat!");
		}
		lay();
	}

	public void actionPerformed(ActionEvent actionevent) {
		if (actionevent.getActionCommand() == "Save") {
			filehandler filehandler1 = new filehandler();
			filehandler1.saveimage(offImage, 0, 0);
		} else {
			Object obj = null;
			double d = 0.0D;
			int i = 0;
			String s = t1.getText();
			if (s.length() > 0) {
				Double double1 = new Double(s);
				for (d = hp.iterate_sammon(t1, t2, t3, t4); d > double1.doubleValue() && i < 10; t2.setText(String.valueOf(d))) {
					i++;
					d = hp.iterate_sammon(t1, t2, t3, t4);
				}

			} else {
				d = hp.iterate_sammon(t1, t2, t3, t4);
			}
			t2.setText(String.valueOf(d));
			repaint();
		}
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
		setBackground(Color.lightGray);
		jb1.addActionListener(this);
		jb1.setBackground(new Color(201, 201, 240));
		jb2.addActionListener(this);
		jb2.setBackground(new Color(201, 201, 240));
		south.add(new Label("Error tolerance"));
		south.add(t1);
		south.add(new Label("Actual error"));
		south.add(t2);
		t2.setText("??");
		south.add(jb1);
		south.add(jb2);
		setSize(400, 400);
		add("Center", pp);
		add("South", south);
		setTitle("Sammons mapping");
		setVisible(true);
		pp.addMouseMotionListener(this);
		pp.addMouseListener(this);
	}

	public static void main(String args[]) {
		samm samm1 = new samm(args[0]);
	}

	public void mouseClicked(MouseEvent mouseevent) {
	}

	public void mouseDragged(MouseEvent mouseevent) {
		endx = mouseevent.getX();
		endy = mouseevent.getY();
		repaint();
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

	public void sweep() {
		boolean flag = false;
		boolean flag1 = false;
		int k = 0;
		Vector vector = new Vector();
		Vector vector1 = new Vector();
		for (int l = 0; l < hp.nrPoints; l++) {
			int i = 15 + (int)(((hp.myElementAt(l).y - mxmin) / (double)totx) * ((double)framewidth - 50D));
			int j = 15 + (int)(((hp.myElementAt(l).x - mymin) / (double)toty) * ((double)frameheight - 50D));
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

			graph2 graph2_1 = new graph2(ai, as, "Princ. comp. analysis", cl);
			graph2_1.setVisible(true);
		}
	}

	public void update(Graphics g) {
		Dimension dimension = pp.getSize();
		frameheight = dimension.height;
		framewidth = dimension.width;
		offImage = createImage(framewidth, frameheight);
		offGraphics = offImage.getGraphics();
		offGraphics.setColor(Color.lightGray);
		offGraphics.fillRect(0, 0, framewidth, frameheight);
		for (int i = 0; i < hp.nrPoints; i++) {
			if (mxmax < hp.myElementAt(i).y)
				mxmax = hp.myElementAt(i).y;
			if (mxmin > hp.myElementAt(i).y)
				mxmin = hp.myElementAt(i).y;
			if (mymax < hp.myElementAt(i).x)
				mymax = hp.myElementAt(i).x;
			if (mymin > hp.myElementAt(i).x)
				mymin = hp.myElementAt(i).x;
		}

		totx = (int)Math.ceil(mxmax - mxmin);
		toty = (int)Math.ceil(mymax - mymin);
		for (int j = 0; j < hp.nrPoints; j++)
			if (j < neurons) {
				offGraphics.setColor(Color.red);
				offGraphics.drawRect(15 + (int)(((hp.myElementAt(j).y - mxmin) / (double)totx) * ((double)framewidth - 50D)), 15 + (int)(((hp.myElementAt(j).x - mymin) / (double)toty) * ((double)frameheight - 50D)), 2, 2);
			} else {
				offGraphics.setColor(Color.blue);
				offGraphics.fillRect(15 + (int)(((hp.myElementAt(j).y - mxmin) / (double)totx) * ((double)framewidth - 50D)), 15 + (int)(((hp.myElementAt(j).x - mymin) / (double)toty) * ((double)frameheight - 50D)), 2, 2);
			}

		offGraphics.setColor(Color.yellow);
		if (startx != endx && starty != endy)
			offGraphics.drawRect(startx, starty, endx - startx, endy - starty);
		pp.setimage(offImage);
		pp.repaint();
	}

	public void update(double ad[][]) {
		int i = 0;
		double d = 9999D;
		try {
			hp = new HiPointDataset(ad);
		}
		catch (IOException _ex) {
			System.out.print("IOexception!");
		}
		catch (IllegalFormatException _ex) {
			System.out.print("illegalformat!");
		}
		for (; d > 0.5D && i < 10; i++) {
			d = hp.iterate_sammon(t1, t2, t3, t4);
			t2.setText(String.valueOf(d));
		}

		repaint();
	}
}
