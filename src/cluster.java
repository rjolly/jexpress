import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Scrollbar;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class cluster extends Frame
	implements AdjustmentListener, ActionListener, ComponentListener, MouseMotionListener, MouseListener {

	int values[][];
	gen gendata[];
	boolean last;
	int currentgennr;
	int counter;
	boolean stepovergen[];
	analyse a;
	int numgenes;
	int totgenes;
	int numobjects;
	Math m;
	gen trunk;
	gen clickedgen;
	gen maintrunk;
	gen root;
	int rightlim;
	int leftlim;
	int lastmid;
	int lastlastmid;
	int lastdown;
	int step;
	int highest;
	int temp;
	int deepest;
	int hordist;
	int leftstart;
	int span;
	int rightfinalend;
	int absspan;
	int offimageheight;
	int offimagewidth;
	int lasttemp;
	filehandler fh;
	double rightend;
	boolean greens;
	Vector nullvals;
	String lastpath;
	actionhandler ah;
	int clustermethod;
	int scright;
	int scdown;
	boolean end;
	boolean painted;
	boolean gfxready;
	Scrollbar rangerup;
	Scrollbar rangerhor;
	tools t;
	MenuBar mb;
	Label status;
	Image offImage;
	Image blank;
	Image offImage2;
	Graphics offGraphics;
	Graphics offGraphics2;
	int mousedragx;
	int mousedragy;
	int mouseclickx;
	int mouseclicky;
	zoomed z;
	int vertlocation;
	JPanel p;
	JPanel p2frame;
	paintpanel p2;
	gen lastvisited;
	gen visit;
	clusterselection cs;
	genefinder gf;
	Dialog d;
	int sortpan[][];
	int wherepos[];

	public cluster() {
		last = false;
		counter = 0;
		a = new analyse(this);
		step = 3;
		hordist = 3;
		rightfinalend = 400;
		fh = new filehandler();
		greens = false;
		ah = new actionhandler(this, 1);
		end = false;
		painted = false;
		gfxready = false;
		t = new tools();
		status = new Label("Waiting for user");
		vertlocation = 0;
		p2 = new paintpanel();
		fh.cl = this;
		setBackground(Color.lightGray);
		setTitle("Cluster");
		addWindowListener(ah);
		addMouseMotionListener(this);
		addMouseListener(this);
		rangerup = new Scrollbar(1, 0, 64, 0, totgenes * step);
		rangerhor = new Scrollbar(0, 0, 64, 0, 500);
		add(new Label(""), "East");
		add(new Label(""), "South");
		add(status, "North");
		setMenu();
		p = new JPanel();
		p.setPreferredSize(new Dimension(80, 400));
		p.setBorder(new EtchedBorder());
		p.setLayout(new GridLayout(8, 1, 5, 5));
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("im/icon.gif");
		JButton jbutton = new JButton("", new ImageIcon(toolkit.getImage("im/load.gif")));
		JButton jbutton1 = new JButton("", new ImageIcon(toolkit.getImage("im/distances.gif")));
		JButton jbutton2 = new JButton("", new ImageIcon(toolkit.getImage("im/cluster.gif")));
		JButton jbutton3 = new JButton("", new ImageIcon(toolkit.getImage("im/vrml.gif")));
		JButton jbutton4 = new JButton("", new ImageIcon(toolkit.getImage("im/find.gif")));
		JButton jbutton5 = new JButton("", new ImageIcon(toolkit.getImage("im/cluster.gif")));
		JButton jbutton6 = new JButton("", new ImageIcon(toolkit.getImage("im/som.gif")));
		JButton jbutton7 = new JButton("", new ImageIcon(toolkit.getImage("im/pc.gif")));
		JButton jbutton8 = new JButton("", new ImageIcon(toolkit.getImage("im/kmeans.gif")));
		setIconImage(image);
		jbutton.setToolTipText("Load gendata");
		jbutton.setActionCommand("Load gendata");
		jbutton.addActionListener(this);
		jbutton2.setToolTipText("Cluster the genes");
		jbutton2.setActionCommand("cluster");
		jbutton2.addActionListener(this);
		jbutton3.setToolTipText("Export to VRML");
		jbutton3.setActionCommand("Make vrmloutput");
		jbutton3.addActionListener(this);
		jbutton4.setToolTipText("Find gene");
		jbutton4.setActionCommand("Find gene");
		jbutton4.addActionListener(this);
		jbutton6.setToolTipText("Apply Self-organising map");
		jbutton6.setActionCommand("Apply Som");
		jbutton6.addActionListener(this);
		jbutton7.setToolTipText("Principal Components Analysis");
		jbutton7.setActionCommand("Principal Components Analysis");
		jbutton7.addActionListener(this);
		jbutton8.setToolTipText("K-Means algorithm");
		jbutton8.setActionCommand("K-Means");
		jbutton8.addActionListener(this);
		p.add(jbutton);
		p.add(jbutton2);
		p.add(jbutton6);
		p.add(jbutton7);
		p.add(jbutton8);
		p.add(jbutton3);
		p.add(jbutton4);
		add(p, "West");
		p2.addMouseListener(this);
		p2.addComponentListener(this);
		p2frame = new JPanel();
		p2frame.setLayout(new BorderLayout());
		p2frame.setBorder(new EtchedBorder());
		p2frame.add(p2, "Center");
		add(p2frame, "Center");
	}

	public void actionPerformed(ActionEvent actionevent) {
		if (actionevent.getActionCommand() == "About J-Express") {
			Frame frame = new Frame("J-Express");
			frame.setSize(400, 300);
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Image image = toolkit.getImage("im/ab.jpg");
			paintpanel paintpanel1 = new paintpanel(0);
			paintpanel1.image = image;
			frame.add(paintpanel1);
			paintpanel1.repaint();
			frame.setVisible(true);
			actionhandler actionhandler1 = new actionhandler(frame);
			frame.addWindowListener(actionhandler1);
		}
		if (actionevent.getActionCommand() == "Simulate")
			a.simulate(status, this);
		if (actionevent.getActionCommand() == "Load gendata")
			a.getdata(status);
		Object obj;
		if (actionevent.getActionCommand() == "Apply Som" && a.data != null)
			obj = new som2(a, this);
		if (actionevent.getActionCommand() == "Karhunen Loeve transposition" && a.data != null)
			obj = new sammlow(reduce(a.data, 1.0D), a.names, this);
		if (actionevent.getActionCommand() == "Principal Components Analysis" && a.data != null)
			obj = new vispca(reduce(a.data, 1.0D), a.names, this);
		if (actionevent.getActionCommand() == "Sammons Mapping" && a.data != null)
			obj = new samm(reduce(a.data, 1.0D), this, a.names);
		if (actionevent.getActionCommand() == "Calc distances")
			if (a.data != null) {
				a.calcdist(status);
				status.setText("Distances calculated");
			} else {
				status.setText("load data first");
			}
		if (actionevent.getActionCommand() == "Save clusterimage")
			fh.saveimage(offImage, offimagewidth, offimageheight);
		if (actionevent.getActionCommand() == "Save zoomimage")
			fh.saveimage(z.offImage, z.offimagewidth, z.offimageheight);
		if (actionevent.getActionCommand() == "Load clusterlist") {
			loadlist();
			for (int i = 0; i < gendata.length; i++)
				if (!stepovergen[i]) {
					trunk = gendata[i];
					maintrunk = gendata[i];
				}

			gfxready = true;
			initgfx();
			resetscbar();
			repaint();
		}
		if (actionevent.getActionCommand() == "Save clusterlist")
			savelist();
		if (actionevent.getActionCommand() == "+ hor. dist") {
			hordist = hordist + 2;
			painted = false;
			gfxready = true;
			initgfx();
			resetscbar();
			repaint();
		}
		if (actionevent.getActionCommand() == "- hor. dist") {
			hordist = hordist - 2;
			gfxready = true;
			painted = false;
			initgfx();
			resetscbar();
			repaint();
		}
		if (actionevent.getActionCommand() == "Make clustertree") {
			if (a.data != null) {
				a.calcdist(status);
				status.setText("Distances calculated");
			} else {
				status.setText("load data first");
			}
			makeclusters(true);
		}
		if (actionevent.getActionCommand() == "Make vrmloutput")
			if (clickedgen == null)
				status.setText("Select a cluster first");
			else
				i = new vrmlmodul(clickedgen, a.data, a.names, this);
		if (actionevent.getActionCommand() == "cluster") {
			if (a.data != null) {
				a.calcdist(status);
				status.setText("Distances calculated");
			} else {
				status.setText("load data first");
			}
			if (a.dist != null) {
				if (cs == null) {
					cs = new clusterselection(this);
					cs.jb.addActionListener(this);
				}
				cs.setVisible(true);
			} else {
				status.setText("There are no dataset to cluster, load data first");
			}
		}
		if (actionevent.getActionCommand() == "clusterok") {
			if (cs.cb1.getState())
				clustermethod = 0;
			else
			if (cs.cb2.getState())
				clustermethod = 1;
			else
				clustermethod = 2;
			cs.setVisible(false);
			makeclusters(true);
		}
		if (actionevent.getActionCommand() == "Find gene" && a.data != null) {
			if (gf == null) {
				gf = new genefinder(this);
				gf.jb1.addActionListener(this);
			}
			gf.setVisible(true);
		}
		if (actionevent.getActionCommand() == "K-Means" && a.data != null)
			i = new kmeans(a.data, this, a.names);
		if (actionevent.getActionCommand() == "Find")
			if (trunk != null) {
				vertlocation = 0;
				offGraphics.setColor(Color.white);
				offGraphics.fillRect(rightfinalend + 1 + 3 * a.bredde, 0, rightfinalend + 10 + 3 * a.bredde, offimageheight);
				markgene(maintrunk, gf.getname());
				repaint();
			} else {
				status.setText("There are no genes in the dataset, load and cluster data first");
			}
		if (actionevent.getActionCommand() == "Save textrepresentation")
			i = new textwriter(clickedgen, trunk, status, a.names);
	}

	public void adjustmentValueChanged(AdjustmentEvent adjustmentevent) {
		if (adjustmentevent.getAdjustable() == rangerup)
			scright = -adjustmentevent.getValue();
		else
			scdown = -adjustmentevent.getValue();
		repaint();
	}

	public gen[] buildmatrix(int ai[][]) {
		gen agen[] = new gen[ai.length];
		stepovergen = new boolean[ai.length];
		numgenes = ai.length;
		for (int i = 0; i < ai.length; i++)
			agen[i] = new gen(a.genvalues[i], i);

		sortpan = new int[ai.length][2];
		return agen;
	}

	public void clear(Graphics g) {
		Dimension dimension = getSize();
		g.setColor(getBackground());
		g.fillRect(0, 0, dimension.width, dimension.height);
	}

	public void componentHidden(ComponentEvent componentevent) {
	}

	public void componentMoved(ComponentEvent componentevent) {
	}

	public void componentResized(ComponentEvent componentevent) {
		Dimension dimension = getSize();
		if (p2 != null)
			p2.resetscbar();
		offImage2 = createImage(dimension.width, dimension.height);
	}

	public void componentShown(ComponentEvent componentevent) {
	}

	public double dist(double d1, double d2) {
		double d3 = 0.0D;
		double d5 = 0.0D;
		if (d1 < d2) {
			double d4 = d1;
			d1 = d2;
			d2 = d4;
		}
		d5 = d1 - d2;
		return d5;
	}

	public void findfragments(gen gen1, String s) {
		if (gen1.merged) {
			findfragments(gen1.right, s);
			findfragments(gen1.left, s);
		} else {
			vertlocation++;
			if (a.names[intvalue(gen1.name)].indexOf(s) != -1) {
				int ai[];
				if (wherepos == null) {
					ai = new int[1];
					ai[0] = vertlocation;
				} else {
					ai = new int[wherepos.length + 1];
					for (int i = 0; i < wherepos.length; i++)
						ai[i] = wherepos[i];

					ai[wherepos.length] = vertlocation;
				}
				wherepos = ai;
			}
		}
	}

	public gen findgen(gen gen1, int i, int j) {
		gen gen2 = null;
		byte byte0 = 4;
		if (gen1.xcor > i - byte0 && gen1.xcor < i + byte0 && gen1.ycor > j - byte0 && gen1.ycor < j + byte0)
			gen2 = gen1;
		else
		if (gen1.merged) {
			gen2 = findgen(gen1.right, i, j);
			if (gen2 == null)
				gen2 = findgen(gen1.left, i, j);
		}
		return gen2;
	}

	public void findlowest(gen gen1, int i, double d1) {
		boolean flag = false;
		numobjects++;
		if (gen1.merged) {
			findlowest(gen1.right, i + 1, d1 + t.abs(t.trunkdist(gen1.getval(), gen1.right.getval())));
			findlowest(gen1.left, i + 1, d1 + t.trunkdist(gen1.getval(), gen1.left.getval()));
		}
		if (deepest < i)
			deepest = i;
		if (rightend < d1)
			rightend = d1;
	}

	public void initgfx() {
		scright = 0;
		scdown = 0;
		deepest = 0;
		rightend = 0.0D;
		leftstart = 0;
		Dimension dimension = getSize();
		findlowest(trunk, 0, 10D);
		offimageheight = step * totgenes + 100;
		offimagewidth = rightfinalend + 4 * a.bredde + 100;
		offImage = createImage(offimagewidth, offimageheight);
		offImage2 = createImage(dimension.width, dimension.height);
	}

	public int intvalue(String s) {
		Integer integer = new Integer(s);
		return integer.intValue();
	}

	private void lay(Component component, GridBagLayout gridbaglayout, GridBagConstraints gridbagconstraints) {
		gridbaglayout.setConstraints(component, gridbagconstraints);
		p.add(component);
	}

	public void loadlist() {
		fh.read();
		a.data = fh.adata;
		a.genvalues = fh.agenvalues;
		values = fh.values;
		a.minvalue = fh.minval;
		a.maxvalue = fh.maxval;
		gendata = fh.gendata;
		stepovergen = fh.stepovergen;
		totgenes = a.data.length;
		a.bredde = a.data[1].length;
		a.lengde = totgenes;
		t.println("done loading");
	}

	public static void main(String args[]) {
		cluster cluster1 = new cluster();
		cluster1.setSize(630, 620);
		cluster1.setVisible(true);
	}

	public void makeclusters(boolean flag) {
		painted = false;
		values = a.dist;
		gendata = buildmatrix(values);
		totgenes = numgenes;
		makeminarray();
		while (numgenes > 1)  {
			gendata = makeclusters(gendata);
			if ((double)numgenes / (double)totgenes > 0.90000000000000002D)
				status.setText("Clustering");
			else
			if ((double)numgenes / (double)totgenes > 0.69999999999999996D && (double)numgenes / (double)totgenes < 0.80000000000000004D)
				status.setText("Clustering 25% done");
			else
			if ((double)numgenes / (double)totgenes > 0.45000000000000001D && (double)numgenes / (double)totgenes < 0.5D)
				status.setText("Clustering 50% done");
			else
			if ((double)numgenes / (double)totgenes > 0.20000000000000001D && (double)numgenes / (double)totgenes < 0.25D)
				status.setText("Clustering 75% done");
			else
			if ((double)numgenes / (double)totgenes < 0.10000000000000001D)
				status.setText("Clustering 100% done, Drawing...");
		}
		values = null;
		a.dist = null;
		for (int i = 0; i < gendata.length; i++)
			if (!stepovergen[i]) {
				trunk = gendata[i];
				root = gendata[i];
				maintrunk = gendata[i];
			}

		status.setText("Clustering done");
		if (flag) {
			gfxready = true;
			initgfx();
			resetscbar();
			repaint();
			p2.clearpanel = true;
			p2.repaint();
		}
	}

	public gen[] makeclusters(gen agen[]) {
		boolean flag = false;
		boolean flag1 = false;
		boolean flag2 = false;
		boolean flag3 = false;
		boolean flag4 = false;
		int i = 10;
		int j = 10;
		int k = 0x98967f;
		int l = 0x98967f;
		double d1 = 0.0D;
		int j1 = 0x98967f;
		for (int k1 = 0; k1 < sortpan.length; k1++)
			if (!stepovergen[k1] && !stepovergen[sortpan[k1][0]] && sortpan[k1][1] < j1) {
				j1 = sortpan[k1][1];
				i = k1;
				j = sortpan[k1][0];
			}

		stepovergen[i] = true;
		numgenes--;
		agen[j] = new gen(values[i][j] * 1000, agen[j], agen[i]);
		a.genvalues[j] = (int)t.mid(a.genvalues[i], a.genvalues[j]);
		for (int l1 = 0; l1 < values[j].length; l1++) {
			if (!stepovergen[l1])
				if (clustermethod == 2)
					values[j][l1] = Math.max(values[j][l1], values[i][l1]);
				else
				if (clustermethod == 0)
					values[j][l1] = Math.min(values[j][l1], values[i][l1]);
				else
				if (clustermethod == 1)
					values[j][l1] = t.imid(values[j][l1], values[i][l1]);
			if (values[j][l1] < k) {
				int i1 = l1;
				k = values[j][l1];
			}
		}

		updateminarray(j);
		for (int i2 = j + 1; i2 < values.length - j; i2++)
			if (!stepovergen[i2]) {
				if (i2 < i) {
					if (clustermethod == 2)
						values[i2][j] = Math.max(values[i2][j], values[i][i2]);
					else
					if (clustermethod == 0)
						values[i2][j] = Math.min(values[i2][j], values[i][i2]);
					else
					if (clustermethod == 1)
						values[i2][j] = t.imid(values[i2][j], values[i][i2]);
				} else
				if (i2 > i)
					if (clustermethod == 2)
						values[i2][j] = Math.max(values[i2][j], values[i2][i]);
					else
					if (clustermethod == 0)
						values[i2][j] = Math.min(values[i2][j], values[i2][i]);
					else
					if (clustermethod == 1)
						values[i2][j] = t.imid(values[i2][j], values[i2][i]);
				updateminarray(i2, j);
			}

		return agen;
	}

	public void makeminarray() {
		int i = 0xf423f;
		int k = 0;
		for (int l = 0; l < values.length; l++) {
			int j = 0xf423f;
			for (int i1 = 0; i1 < l; i1++)
				if (!stepovergen[l] && !stepovergen[i1] && values[l][i1] < j) {
					j = values[l][i1];
					k = i1;
				}

			sortpan[l][0] = k;
			sortpan[l][1] = j;
		}

	}

	public void markgene(gen gen1, String s) {
		if (gen1.merged) {
			markgene(gen1.right, s);
			markgene(gen1.left, s);
		} else {
			vertlocation++;
			if (a.names[intvalue(gen1.name)].indexOf(s) != -1) {
				offGraphics.setColor(Color.red);
				offGraphics.drawLine(rightfinalend + 1 + 3 * a.bredde, offimageheight - (48 + vertlocation * 3), rightfinalend + 10 + 3 * a.bredde, offimageheight - (48 + vertlocation * 3));
			}
		}
	}

	public void mouseClicked(MouseEvent mouseevent) {
	}

	public void mouseDragged(MouseEvent mouseevent) {
	}

	public void mouseEntered(MouseEvent mouseevent) {
	}

	public void mouseExited(MouseEvent mouseevent) {
	}

	public void mouseMoved(MouseEvent mouseevent) {
	}

	public void mousePressed(MouseEvent mouseevent) {
		mouseclickx = mouseevent.getX() - p2.scdown;
		mouseclicky = mouseevent.getY() - p2.scright;
		if (gfxready) {
			gen gen1 = findgen(root, mouseclickx, mouseclicky);
			visit = gen1;
			if (z == null && gen1 != null)
				z = new zoomed(gen1, totgenes, a);
			if (gen1 != null) {
				clickedgen = gen1;
				z.settrunk(gen1);
				z.setVisible(true);
				graph2 graph2_1 = new graph2(gen1, a, fh, this);
			}
		}
	}

	public void mouseReleased(MouseEvent mouseevent) {
	}

	public void paint(Graphics g) {
		update(g);
	}

	public int painttree(gen gen1, Graphics g, int i, int j, Color color) {
		boolean flag = false;
		boolean flag1 = false;
		boolean flag2 = false;
		boolean flag3 = false;
		boolean flag4 = false;
		boolean flag5 = false;
		boolean flag6 = false;
		boolean flag7 = false;
		boolean flag8 = false;
		if (gen1.merged) {
			int j2 = (int)t.abs(t.trunkdist(gen1.getval(), gen1.right.getval()));
			j2 = t.norm(j2, absspan, span);
			int k1 = j2;
			lastdown = j;
			int k = painttree(gen1.right, g, i + t.idivideint(step, 2), j + j2, color);
			int i1 = lastmid;
			j2 = (int)t.abs(t.trunkdist(gen1.getval(), gen1.left.getval()));
			j2 = t.norm(j2, absspan, span);
			lastdown = j;
			int l1 = j2;
			int l = painttree(gen1.left, g, i - t.idivideint(step, 2), j + j2, color);
			lastlastmid = k + 14;
			int j1 = lastmid;
			int i2 = j + j2;
			if (k > highest)
				highest = k;
			g.setColor(color);
			if (gen1.left.name != null && gen1.right.name != null)
				g.drawLine(j, rightlim + step, j, rightlim + step * 2);
			else
			if (gen1.left.name != null && gen1.right.name == null) {
				g.drawLine(j, l + step, j, lastmid);
				g.drawLine(j, lastmid, j + k1, lastmid);
			} else
			if (gen1.left.name == null && gen1.right.name != null) {
				g.drawLine(j, lastmid, j, k + step);
				g.drawLine(j, lastmid, j + l1, lastmid);
			} else
			if (gen1.left.name == null && gen1.right.name == null) {
				g.drawLine(j, j1, j, i1);
				g.drawLine(j, j1, j + l1, lastmid);
				g.drawLine(j, i1, j + k1, i1);
				gen1.xcor = j;
				gen1.ycor = t.idivideint(j1 + i1, 2);
				g.setColor(Color.green);
				g.drawRect(gen1.xcor - 1, gen1.ycor - 1, 2, 2);
			}
			lastmid = t.idivideint(l + k + step * 2, 2);
		} else {
			g.setColor(color);
			g.drawLine(lastdown, rightlim, rightfinalend, rightlim);
			a.paintvalues(intvalue(gen1.name), g, rightfinalend, rightlim - 2);
			rightlim = rightlim - step;
		}
		return rightlim;
	}

	public double[][] reduce(int ai[][], double d1) {
		double ad[][] = new double[ai.length][ai[0].length];
		for (int i = 0; i < ai.length; i++) {
			for (int j = 0; j < ai[i].length; j++)
				ad[i][j] = (double)ai[i][j] / d1;

		}

		return ad;
	}

	public void resetscbar() {
	}

	public void savelist() {
		filehandler filehandler1 = new filehandler();
		filehandler1.write(a.data, a.genvalues, values, status, a.minvalue, a.maxvalue, gendata, stepovergen);
	}

	public void setMenu() {
		addComponentListener(this);
		Menu menu = new Menu("File");
		MenuItem menuitem = new MenuItem("Load gendata");
		MenuItem menuitem1 = new MenuItem("Simulate");
		MenuItem menuitem2 = new MenuItem("Save textrepresentation");
		MenuItem menuitem3 = new MenuItem("Load clusterlist");
		MenuItem menuitem4 = new MenuItem("Save clusterimage");
		MenuItem menuitem5 = new MenuItem("Save zoomimage");
		menuitem.addActionListener(this);
		menuitem1.addActionListener(this);
		menuitem2.addActionListener(this);
		menuitem3.addActionListener(this);
		menuitem4.addActionListener(this);
		menuitem5.addActionListener(this);
		menu.add(menuitem);
		menu.add(menuitem1);
		menu.add(menuitem2);
		menu.addSeparator();
		menu.add(menuitem4);
		menu.add(menuitem5);
		Menu menu1 = new Menu("Process");
		MenuItem menuitem6 = new MenuItem("Calc distances");
		MenuItem menuitem7 = new MenuItem("Make clustertree");
		MenuItem menuitem8 = new MenuItem("Make vrmloutput");
		MenuItem menuitem9 = new MenuItem("Find gene");
		MenuItem menuitem10 = new MenuItem("Apply Som");
		MenuItem menuitem11 = new MenuItem("Principal Components Analysis");
		MenuItem menuitem12 = new MenuItem("Sammons Mapping");
		MenuItem menuitem13 = new MenuItem("K-Means");
		MenuItem menuitem14 = new MenuItem("Karhunen Loeve transposition");
		menuitem6.addActionListener(this);
		menuitem7.addActionListener(this);
		menuitem8.addActionListener(this);
		menuitem9.addActionListener(this);
		menuitem10.addActionListener(this);
		menuitem11.addActionListener(this);
		menuitem12.addActionListener(this);
		menuitem13.addActionListener(this);
		menuitem14.addActionListener(this);
		menu1.add(menuitem6);
		menu1.add(menuitem7);
		menu1.add(menuitem8);
		menu1.add(menuitem9);
		menu1.add(menuitem10);
		menu1.add(menuitem11);
		menu1.add(menuitem14);
		menu1.add(menuitem12);
		menu1.add(menuitem13);
		Menu menu2 = new Menu("About");
		MenuItem menuitem15 = new MenuItem("About J-Express");
		menuitem15.addActionListener(this);
		menu2.add(menuitem15);
		mb = new MenuBar();
		mb.add(menu);
		mb.add(menu1);
		mb.add(menu2);
		setMenuBar(mb);
		repaint();
	}

	public void update(Graphics g) {
		Dimension dimension = getSize();
		boolean flag = false;
		if (gfxready) {
			offGraphics = offImage.getGraphics();
			offGraphics2 = offImage2.getGraphics();
			rightlim = step * totgenes + 50;
			if (!painted) {
				status.setText("Generating tree");
				offGraphics.setColor(Color.white);
				offGraphics.fillRect(0, 0, offimagewidth, offimageheight);
				leftstart = 10;
				absspan = rightfinalend - 10;
				span = (int)rightend - leftstart;
				offGraphics.setColor(Color.black);
				painttree(trunk, offGraphics, 0, 10, Color.black);
				span = 1;
				absspan = 1;
				painted = true;
				status.setText("Done generating tree");
			}
			offGraphics2.setColor(Color.white);
			offGraphics2.fillRect(105, 100, dimension.width - 141, dimension.height - 140);
			p2.setimage(null);
			p2.setimage(offImage);
			p2.resetscbar();
		}
		g.setColor(Color.yellow);
		p.repaint();
		p2frame.repaint();
	}

	public void updateminarray(int i) {
		int j = 0x98967f;
		int k = 0;
		j = 0x98967f;
		for (int l = 0; l < i; l++)
			if (!stepovergen[l] && values[i][l] < j) {
				j = values[i][l];
				k = l;
			}

		sortpan[i][0] = k;
		sortpan[i][1] = j;
	}

	public void updateminarray(int i, int j) {
		boolean flag = false;
		if (stepovergen[sortpan[i][0]])
			updateminarray(i);
		else
		if (values[i][j] < sortpan[i][1]) {
			int k = j;
			sortpan[i][0] = k;
			sortpan[i][1] = values[i][j];
		}
	}
}
