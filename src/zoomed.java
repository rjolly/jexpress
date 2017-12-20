import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Scrollbar;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class zoomed extends Frame
	implements AdjustmentListener, ComponentListener {

	int totgenes;
	gen trunk;
	actionhandler ah;
	int rightlim;
	int leftlim;
	int lastmid;
	int lastdown;
	int rightfinalend;
	int lastlastmid;
	int step;
	int highest;
	int deepest;
	int hordist;
	int span;
	int absspan;
	int leftstart;
	int offimageheight;
	int offimagewidth;
	int mouseclickx;
	int mousedragx;
	int mousedragy;
	int mouseclicky;
	double rightend;
	int scright;
	int scdown;
	int numobjects;
	Image offImage;
	Image offImage2;
	Image blank;
	Graphics offGraphics;
	Graphics offGraphics2;
	Label status;
	analyse a;
	boolean painted;
	boolean gfxready;
	tools t;
	Scrollbar rangerup;
	Scrollbar rangerhor;
	Scrollbar rangerhor2;
	int genes;
	boolean clearscreen;
	int genenumber;
	int toti;

	public zoomed(gen gen1, int i, analyse analyse1) {
		ah = new actionhandler(this);
		rightfinalend = 300;
		step = 8;
		hordist = 8;
		status = new Label("wait");
		painted = false;
		gfxready = false;
		t = new tools();
		genes = 0;
		clearscreen = true;
		genenumber = 0;
		toti = 210;
		rangerup = new Scrollbar(1, 0, 50, 0, i * 8);
		rangerhor = new Scrollbar(0, 0, 50, 0, i * 8);
		rangerhor2 = new Scrollbar(0, 0, 50, -200, 200);
		rangerup.setBackground(Color.lightGray);
		rangerup.addAdjustmentListener(this);
		rangerhor.setBackground(Color.lightGray);
		rangerhor.addAdjustmentListener(this);
		rangerhor2.setBackground(Color.lightGray);
		rangerhor2.addAdjustmentListener(this);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		setIconImage(toolkit.getImage("im/magn.gif"));
		addComponentListener(this);
		addWindowListener(ah);
		setBackground(Color.white);
		setTitle("Zoomed");
		totgenes = i;
		a = analyse1;
		trunk = gen1;
		initgfx();
		setSize(550, 200);
		Panel panel = new Panel(new BorderLayout());
		panel.add("North", rangerhor2);
		panel.add("South", rangerhor);
		add(rangerup, "East");
		add(rangerhor, "South");
		setLocation(analyse1.c.getLocation().x + 200, analyse1.c.getLocation().y + 450);
		setVisible(true);
		repaint();
	}

	public void adjustmentValueChanged(AdjustmentEvent adjustmentevent) {
		repaint();
	}

	public void clear(Graphics g) {
		Dimension dimension = getSize();
		g.setColor(Color.white);
		g.fillRect(0, 0, dimension.width, dimension.height);
	}

	public void componentHidden(ComponentEvent componentevent) {
	}

	public void componentMoved(ComponentEvent componentevent) {
	}

	public void componentResized(ComponentEvent componentevent) {
		Dimension dimension = getSize();
		resetscbar();
		repaint();
	}

	public void componentShown(ComponentEvent componentevent) {
	}

	public void countgenes(gen gen1) {
		if (gen1.merged) {
			countgenes(gen1.right);
			countgenes(gen1.left);
		} else {
			genenumber++;
		}
	}

	public void findlowest(gen gen1, int i, double d) {
		boolean flag = false;
		numobjects++;
		if (gen1.merged) {
			findlowest(gen1.right, i + 1, d + t.abs(t.trunkdist(gen1.getval(), gen1.right.getval())));
			findlowest(gen1.left, i + 1, d + t.trunkdist(gen1.getval(), gen1.left.getval()));
		} else {
			totgenes++;
			genes++;
		}
		if (deepest < i)
			deepest = i;
		if (rightend < d)
			rightend = d;
	}

	public void initgfx() {
		totgenes = 0;
		deepest = 0;
		rightend = 0.0D;
		leftstart = 0;
		totgenes = 0;
		findlowest(trunk, 0, 10D);
		rangerup.setMaximum(genes * 8);
		rangerup.setValue(0);
		scdown = 0;
		painted = false;
		gfxready = true;
		if (totgenes < 300) {
			offimageheight = step * totgenes + 100;
			offimagewidth = rightfinalend + 140 + 8 * a.bredde;
		} else {
			offimageheight = 200;
			offimagewidth = 400;
		}
		offImage = createImage(offimagewidth, offimageheight);
		resetscbar();
	}

	public int intvalue(String s) {
		Integer integer = new Integer(s);
		return integer.intValue();
	}

	public void paint(Graphics g) {
		status.setText("Waiting for user");
		g.setColor(Color.white);
		Dimension dimension = getSize();
		g.fillRect(0, 0, dimension.height, dimension.width);
		update(g);
	}

	public int painttree(gen gen1, Graphics g, int i, int j) {
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
			int k = painttree(gen1.right, g, i + t.idivideint(step, 2), j + j2);
			int i1 = lastmid;
			j2 = (int)t.abs(t.trunkdist(gen1.getval(), gen1.left.getval()));
			j2 = t.norm(j2, absspan, span);
			lastdown = j;
			int l1 = j2;
			int l = painttree(gen1.left, g, i - t.idivideint(step, 2), j + j2);
			lastlastmid = k + 14;
			int j1 = lastmid;
			int i2 = j + j2;
			if (k > highest)
				highest = k;
			if (gen1.left.name != null && gen1.right.name != null)
				g.drawLine(toti + j, rightlim + step, toti + j, rightlim + step * 2);
			else
			if (gen1.left.name != null && gen1.right.name == null) {
				g.drawLine(toti + j, l + step, toti + j, lastmid);
				g.drawLine(toti + j, lastmid, toti + j + k1, lastmid);
			} else
			if (gen1.left.name == null && gen1.right.name != null) {
				g.drawLine(toti + j, lastmid, toti + j, k + step);
				g.drawLine(toti + j, lastmid, toti + j + l1, lastmid);
			} else
			if (gen1.left.name == null && gen1.right.name == null) {
				g.drawLine(toti + j, j1, toti + j, i1);
				g.drawLine(toti + j, j1, toti + j + l1, lastmid);
				g.drawLine(toti + j, i1, toti + j + k1, i1);
			}
			lastmid = t.idivideint(l + k + step * 2, 2);
		} else {
			g.drawLine(toti + lastdown, rightlim, rightfinalend + 25, rightlim);
			g.setFont(new Font("Times", 0, 9));
			a.paintvalues2(intvalue(gen1.name), g, rightfinalend + 25, rightlim - 2);
			rightlim = rightlim - step;
		}
		return rightlim;
	}

	public void resetscbar() {
		Dimension dimension = getSize();
		rangerup.setMaximum((offimageheight - dimension.height) + 50);
		repaint();
	}

	public void settrunk(gen gen1) {
		trunk = gen1;
		painted = false;
		genenumber = 0;
		countgenes(gen1);
		initgfx();
		toFront();
		repaint();
	}

	public void update(Graphics g) {
		rightfinalend = 200 - rangerup.getValue();
		boolean flag = false;
		if (gfxready) {
			if (offImage != null) {
				offGraphics = offImage.getGraphics();
			} else {
				if (totgenes < 300) {
					offimageheight = step * totgenes + 100;
					offimagewidth = rightfinalend + 200 + 8 * a.bredde;
				} else {
					offimageheight = 200;
					offimagewidth = 400;
				}
				offImage = createImage(offimagewidth, offimageheight);
				offGraphics = offImage.getGraphics();
			}
			rightlim = step * totgenes + 50;
			if (!painted) {
				Dimension dimension = getSize();
				leftstart = 10;
				absspan = rightfinalend - 10;
				span = (int)rightend - leftstart;
				offGraphics.setColor(Color.black);
				if (totgenes > 299) {
					offGraphics.drawString("Too many genes!", 270, 100);
				} else {
					rangerup.setValue(0);
					resetscbar();
					scdown = 0;
					scright = 0;
					clearscreen = true;
					painttree(trunk, offGraphics, 0, -200);
					repaint();
				}
				span = 1;
				absspan = 1;
				painted = true;
			}
		}
		if (clearscreen) {
			clear(g);
			clearscreen = false;
		}
		g.drawImage(offImage, -rangerhor.getValue(), -rangerup.getValue() - 10, this);
		rangerhor.setMaximum((offImage.getWidth(this) - getSize().width) + 20);
		rangerup.setMaximum((offImage.getHeight(this) - getSize().height) + 20);
	}
}
