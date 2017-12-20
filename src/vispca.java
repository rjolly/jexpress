import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.Point;
import java.awt.TextComponent;
import java.awt.TextField;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class vispca extends Frame
	implements ComponentListener, ActionListener, MouseListener, MouseMotionListener, ItemListener {

	Frame f1;
	Frame f2;
	Panel south;
	TextField t1;
	TextField t2;
	TextField t3;
	TextField t4;
	List li1;
	List li2;
	JButton b1;
	JButton b2;
	JButton b3;
	JButton b4;
	JButton b5;
	JButton b6;
	Choice ch1;
	Choice ch2;
	Button b;
	Button prev;
	Label l1;
	Label l2;
	Label l3;
	Label l4;
	Label l5;
	Label l6;
	Label l7;
	JCheckBox cb1;
	Button next;
	Image offImage;
	Graphics offGraphics;
	paintpanel pp;
	PcaII PCA;
	int pcax;
	int pcay;
	int pcaz;
	int neurons;
	actionhandler ah;
	node net[][];
	Image mov[];
	Image points;
	Graphics pnts;
	boolean resized;
	boolean paintstats;
	int imgnr;
	node clicked;
	int clickx;
	int clicky;
	int startx;
	int starty;
	int endx;
	int endy;
	double dataset[][];
	Panel eastpnl;
	JPanel southpnl;
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

	public vispca(double ad[][], String as[], cluster cluster1) {
		south = new Panel(new GridLayout(1, 2));
		t1 = new TextField(20);
		t2 = new TextField(20);
		t3 = new TextField(20);
		t4 = new TextField(20);
		b = new Button("Resize");
		prev = new Button("<");
		cb1 = new JCheckBox("Stats");
		next = new Button(">");
		pp = new paintpanel(1);
		pcax = 0;
		pcay = 1;
		pcaz = 2;
		ah = new actionhandler(this);
		resized = true;
		paintstats = false;
		imgnr = -1;
		clickx = -1;
		clicky = -1;
		eastpnl = new Panel(new GridLayout(5, 1));
		southpnl = new JPanel(new BorderLayout());
		isneurons = true;
		frameheight = 0;
		framewidth = 0;
		mxmax = -99999.899999999994D;
		mxmin = 99999.899999999994D;
		mymax = -99999.899999999994D;
		mymin = 99999.899999999994D;
		totx = 0;
		toty = 0;
		cb1.addActionListener(this);
		addWindowListener(ah);
		cl = cluster1;
		isneurons = false;
		names = as;
		dataset = ad;
		setTitle("Principal component analysis");
		pp.addMouseMotionListener(this);
		pp.addMouseListener(this);
		setBackground(Color.lightGray);
		PCA = new PcaII(ad);
		lay();
		redraw();
	}

	public vispca(double ad[][], String as[], node anode[][], cluster cluster1) {
		south = new Panel(new GridLayout(1, 2));
		t1 = new TextField(20);
		t2 = new TextField(20);
		t3 = new TextField(20);
		t4 = new TextField(20);
		b = new Button("Resize");
		prev = new Button("<");
		cb1 = new JCheckBox("Stats");
		next = new Button(">");
		pp = new paintpanel(1);
		pcax = 0;
		pcay = 1;
		pcaz = 2;
		ah = new actionhandler(this);
		resized = true;
		paintstats = false;
		imgnr = -1;
		clickx = -1;
		clicky = -1;
		eastpnl = new Panel(new GridLayout(5, 1));
		southpnl = new JPanel(new BorderLayout());
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
		cb1.addActionListener(this);
		names = as;
		dataset = ad;
		setTitle("2D SOM Window");
		pp.addMouseMotionListener(this);
		pp.addMouseListener(this);
		setBackground(Color.lightGray);
		net = anode;
		PCA = new PcaII(ad);
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
		if (actionevent.getActionCommand() == "Components") {
			String as[] = new String[PCA.eigenvalues.length];
			for (int i = 0; i < PCA.eigenvalues.length; i++)
				as[i] = "Component " + String.valueOf(i + 1);

			graph2 graph2_1 = new graph2(intvals(PCA.gettransposedeigen(), 0.0001D), as, "Princ. Components", cl, 1);
			graph2_1.setVisible(true);
			for (int k = 1; k < PCA.eigenvalues.length; k++)
				graph2_1.li.deselect(k);

			graph2_1.li.select(0);
			graph2_1.selectedind = graph2_1.li.getSelectedIndexes();
			graph2_1.repaint();
			graph2_1.showlist();
		} else
		if (actionevent.getActionCommand() == "Variance/comp") {
			String as1[] = new String[PCA.eigenvalues.length];
			for (int j = 0; j < PCA.eigenvalues.length; j++)
				as1[j] = "Component " + String.valueOf(j + 1);

			double ad[][] = new double[1][PCA.eigenvectors.length];
			String as2[] = new String[1];
			as2[0] = "Eigenvalues";
			for (int l = 0; l < ad[0].length; l++)
				ad[0][l] = (PCA.eigenvalues[l] / PCA.totalvariance()) * 100D;

			graph2 graph2_2 = new graph2(intvals(ad, 0.001D), as2, "Variance/Components (%)", cl, 1);
			graph2_2.setVisible(true);
			graph2_2.selectedind = graph2_2.li.getSelectedIndexes();
			graph2_2.repaint();
		} else
		if (actionevent.getActionCommand() == "Resize") {
			pp.repaint();
			redraw();
		} else
		if (actionevent.getActionCommand() == "Save PCA window") {
			pp.repaint();
			redraw();
			filehandler filehandler1 = cl.fh;
			filehandler1.saveimage(offImage, 0, 0);
		}
		if (cb1.isSelected())
			paintstats = true;
		else
			paintstats = false;
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

	public int[][] intvals(double ad[][], double d) {
		int ai[][] = new int[ad.length][ad[0].length];
		for (int i = 0; i < ad.length; i++) {
			for (int j = 0; j < ad[0].length; j++)
				ai[i][j] = (int)(ad[i][j] / d);

		}

		return ai;
	}

	public void itemStateChanged(ItemEvent itemevent) {
		pcax = ch1.getSelectedIndex();
		pcay = ch2.getSelectedIndex();
		resized = true;
		redraw();
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
		setSize(480, 500);
		add("Center", pp);
		Panel panel = new Panel(new GridLayout(5, 1));
		Panel panel1 = new Panel(new GridLayout(5, 1));
		ch1 = new Choice();
		ch2 = new Choice();
		for (int i = 0; i < PCA.eigenvalues.length; i++) {
			ch1.addItem("Principal Component nr. " + String.valueOf(i + 1) + " - " + PCA.varianceastr(i) + "% var.");
			ch2.addItem("Principal Component nr. " + String.valueOf(i + 1) + " - " + PCA.varianceastr(i) + "% var.");
		}

		ch1.addItemListener(this);
		ch2.addItemListener(this);
		ch2.select(1);
		panel1.add(new Label("Axis"));
		panel.add(new Label("Principal Component"));
		panel1.add(new Label("X"));
		panel.add(ch1);
		panel1.add(new Label("Y"));
		panel.add(ch2);
		panel1.add(new Label(""));
		l6 = new Label("Total variance retained:");
		panel.add(l6);
		b1 = new JButton("Repaint");
		b2 = new JButton("Save PCA window");
		b3 = new JButton("Close");
		b4 = new JButton("Components");
		b5 = new JButton("Variance/comp");
		b1.setBackground(new Color(201, 201, 240));
		b2.setBackground(new Color(201, 201, 240));
		b3.setBackground(new Color(201, 201, 240));
		b4.setBackground(new Color(201, 201, 240));
		b5.setBackground(new Color(201, 201, 240));
		b2.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		Panel panel2 = new Panel(new GridLayout(1, 2));
		panel1.add(cb1);
		panel2.add(b4);
		panel2.add(b5);
		panel.add(panel2);
		l1 = new Label("    Variance");
		l2 = new Label("");
		l3 = new Label("");
		l4 = new Label("");
		eastpnl.add(l1);
		eastpnl.add(l2);
		eastpnl.add(l3);
		eastpnl.add(l4);
		eastpnl.add(b2);
		southpnl.setBackground(new Color(200, 210, 200));
		southpnl.add("West", panel1);
		southpnl.add("Center", panel);
		southpnl.add("East", eastpnl);
		southpnl.setBorder(new EtchedBorder());
		add("South", southpnl);
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
		double ad2[][] = new double[1][dataset[0].length];
		for (int j = 0; j < ad2[0].length; j++)
			ad2[0][j] = 0.0D;

		double d = 0.0D;
		double d1 = 0.0D;
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
		boolean flag8 = false;
		boolean flag9 = false;
		boolean flag10 = false;
		boolean flag11 = false;
		frameheight = 0;
		framewidth = 0;
		mxmax = -99999.899999999994D;
		mxmin = 99999.899999999994D;
		mymax = -99999.899999999994D;
		mymin = 99999.899999999994D;
		totx = 0;
		toty = 0;
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
			for (int k3 = 0; (double)k3 < PCA.nrPoints(); k3++) {
				if (mxmax < PCA.ElementAt(k3, pcax))
					mxmax = PCA.ElementAt(k3, pcax);
				if (mxmin > PCA.ElementAt(k3, pcax))
					mxmin = PCA.ElementAt(k3, pcax);
				if (mymax < PCA.ElementAt(k3, pcay))
					mymax = PCA.ElementAt(k3, pcay);
				if (mymin > PCA.ElementAt(k3, pcay))
					mymin = PCA.ElementAt(k3, pcay);
			}

			if (totx == 0) {
				totx = (int)Math.ceil(mxmax - mxmin);
				toty = (int)Math.ceil(mymax - mymin);
			}
			for (int j4 = 0; (double)j4 < PCA.nrPoints(); j4++) {
				offGraphics.setColor(Color.blue);
				offGraphics.fillRect(20 + (int)(((PCA.ElementAt(j4, pcax) - mxmin) / (double)totx) * ((double)framewidth - 50D)), pp.getSize().height - (20 + (int)(((PCA.ElementAt(j4, pcay) - mymin) / (double)toty) * ((double)frameheight - 50D))), 2, 2);
			}

		}
		if (paintstats) {
			offGraphics.setColor(new Color(150, 150, 140));
			offGraphics.drawString("Variance x: " + PCA.varianceastr(pcax) + "%", 4, 25);
			offGraphics.drawString("Variance y: " + PCA.varianceastr(pcay) + "%", 4, 40);
			if (String.valueOf(PCA.varianceaccounted(pcax) + PCA.varianceaccounted(pcay)).length() < 5)
				offGraphics.drawString("Total variance: " + String.valueOf(PCA.varianceaccounted(pcax) + PCA.varianceaccounted(pcay)) + "%", 4, 55);
			else
				offGraphics.drawString("Total variance: " + String.valueOf(PCA.varianceaccounted(pcax) + PCA.varianceaccounted(pcay)).substring(0, 5) + "%", 4, 55);
		}
		l2.setText("    " + String.valueOf(PCA.varianceastr(pcax)) + "%");
		l3.setText("    " + String.valueOf(PCA.varianceastr(pcay)) + "%");
		if (pcax != pcay) {
			if (String.valueOf(PCA.varianceaccounted(pcax) + PCA.varianceaccounted(pcay)).length() < 5)
				l4.setText("    " + String.valueOf(PCA.varianceaccounted(pcax) + PCA.varianceaccounted(pcay)) + "%");
			else
				l4.setText("    " + String.valueOf(PCA.varianceaccounted(pcax) + PCA.varianceaccounted(pcay)).substring(0, 5) + "%");
		} else
		if (String.valueOf(PCA.varianceaccounted(pcax) + PCA.varianceaccounted(pcay)).length() < 5)
			l4.setText("    " + String.valueOf(PCA.varianceaccounted(pcax)) + "%");
		else
			l4.setText("    " + String.valueOf(PCA.varianceaccounted(pcax)).substring(0, 5) + "%");
		if (resized) {
			pnts = offGraphics.create();
		} else {
			offGraphics = pnts;
			resized = false;
		}
		ad2 = PCA.getlow(ad2);
		d = ad2[0][pcax];
		d1 = ad2[0][pcay];
		d = 20 + (int)(((d - mxmin) / (double)totx) * ((double)framewidth - 50D));
		d1 = 20 + (int)(((d1 - mymin) / (double)toty) * ((double)frameheight - 50D));
		offGraphics.setColor(new Color(200, 220, 200));
		offGraphics.drawLine((int)d, 0, (int)d, pp.getSize().height);
		offGraphics.drawLine(0, pp.getSize().height - (int)d1, pp.getSize().width, pp.getSize().height - (int)d1);
		offGraphics.setColor(Color.green);
		if (isneurons && net != null) {
			double ad[][] = new double[net.length * net.length][net[0][0].vector.length];
			int i4 = 0;
			for (int k4 = 0; k4 < net.length; k4++) {
				for (int i5 = 0; i5 < net.length; i5++) {
					ad[i4] = net[k4][i5].vector;
					i4++;
				}

			}

			double ad1[][] = PCA.getlow(ad);
			i4 = 0;
			for (int j5 = 0; j5 < net.length; j5++) {
				for (int k5 = 0; k5 < net.length; k5++) {
					net[j5][k5].mapx = (int)ad1[i4][pcax];
					net[j5][k5].mapy = (int)ad1[i4][pcay];
					i4++;
				}

			}

			for (int i6 = 0; i6 < net.length - 1; i6++) {
				for (int j6 = 0; j6 < net.length; j6++) {
					int k = net[i6][j6].mapx;
					int i1 = net[i6][j6].mapy;
					int k1 = net[i6 + 1][j6].mapx;
					int i2 = net[i6 + 1][j6].mapy;
					k = 20 + (int)((((double)k - mxmin) / (double)totx) * ((double)framewidth - 50D));
					i1 = 20 + (int)((((double)i1 - mymin) / (double)toty) * ((double)frameheight - 50D));
					k1 = 20 + (int)((((double)k1 - mxmin) / (double)totx) * ((double)framewidth - 50D));
					i2 = 20 + (int)((((double)i2 - mymin) / (double)toty) * ((double)frameheight - 50D));
					offGraphics.drawLine(k, pp.getSize().height - i1, k1, pp.getSize().height - i2);
				}

			}

			for (int k6 = 0; k6 < net.length; k6++) {
				for (int i7 = 0; i7 < net.length - 1; i7++) {
					int j2 = 20 + (int)((((double)net[k6][i7].mapx - mxmin) / (double)totx) * ((double)framewidth - 50D));
					int k2 = 20 + (int)((((double)net[k6][i7].mapy - mymin) / (double)toty) * ((double)frameheight - 50D));
					int i3 = 20 + (int)((((double)net[k6][i7 + 1].mapx - mxmin) / (double)totx) * ((double)framewidth - 50D));
					int j3 = 20 + (int)((((double)net[k6][i7 + 1].mapy - mymin) / (double)toty) * ((double)frameheight - 50D));
					offGraphics.setColor(Color.cyan);
					offGraphics.drawLine(j2, pp.getSize().height - k2, i3, pp.getSize().height - j3);
				}

			}

			for (int j7 = 0; j7 < net.length; j7++) {
				for (int k7 = 0; k7 < net.length; k7++) {
					int l = net[j7][k7].mapx;
					int j1 = net[j7][k7].mapy;
					l = 20 + (int)((((double)l - mxmin) / (double)totx) * ((double)framewidth - 50D));
					j1 = 20 + (int)((((double)j1 - mymin) / (double)toty) * ((double)frameheight - 50D));
					if (clickx == j7 && clicky == k7) {
						offGraphics.setColor(Color.yellow);
						offGraphics.drawRect(l, pp.getSize().height - j1, 1, 1);
						offGraphics.drawRect(l - 2, pp.getSize().height - j1 - 2, 5, 5);
						offGraphics.drawRect(l - 6, pp.getSize().height - j1 - 6, 13, 13);
					} else {
						offGraphics.setColor(Color.red);
						offGraphics.drawRect(l, pp.getSize().height - j1, 1, 1);
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
		southpnl.repaint();
	}

	public void sweep() {
		boolean flag = false;
		boolean flag1 = false;
		int k = 0;
		java.util.Vector vector = new java.util.Vector();
		java.util.Vector vector1 = new java.util.Vector();
		for (int l = 0; (double)l < PCA.nrPoints(); l++) {
			int i = 20 + (int)(((PCA.ElementAt(l, pcax) - mxmin) / (double)totx) * ((double)framewidth - 50D));
			int j = pp.getSize().height - (20 + (int)(((PCA.ElementAt(l, pcay) - mymin) / (double)toty) * ((double)frameheight - 50D)));
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
