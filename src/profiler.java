import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.Vector;

public class profiler extends Canvas
	implements MouseListener, MouseMotionListener {

	Image offImage;
	Graphics offGraphics;
	int profheight;
	int profwidth;
	int firstupper[];
	int firstlower[];
	int firstprof[];
	int upper[];
	int lower[];
	int prof[];
	int xvals[];
	int points;
	int clicked;
	int clickedline;
	int yclick;
	int upperdist;
	int lowerdist;
	int cyclval;
	int upperval;
	int lowerval;
	int alldistlower;
	int alldistupper;
	int meany;
	int framemid;
	double globstep;
	double dupper[];
	double dlower[];
	double max;
	double min;
	int addupper[];
	int addlower[];
	int sumupper[];
	int sumlower[];
	String yneglabels[];
	String yposlabels[];
	String names[];
	double forhold;
	cluster cl;

	public profiler(int i, cluster cluster1) {
		profheight = 250;
		profwidth = 300;
		upperdist = 0;
		lowerdist = 0;
		alldistlower = 50;
		alldistupper = 50;
		globstep = 0.0D;
		max = 100D;
		min = -50D;
		cl = cluster1;
		setSize(profwidth, profheight);
		points = i;
		framemid = profheight / 2;
		initpoints();
		makelabely();
		addMouseMotionListener(this);
		addMouseListener(this);
		repaint();
	}

	public profiler(double ad[], cluster cluster1) {
		profheight = 250;
		profwidth = 300;
		upperdist = 0;
		lowerdist = 0;
		alldistlower = 50;
		alldistupper = 50;
		globstep = 0.0D;
		max = 100D;
		min = -50D;
		cl = cluster1;
		setSize(profwidth, profheight);
		points = ad.length;
		framemid = profheight / 2;
		initpoints(ad);
		makelabely();
		addMouseMotionListener(this);
		addMouseListener(this);
		repaint();
	}

	public profiler(double ad[][], String as[], cluster cluster1) {
		profheight = 250;
		profwidth = 300;
		upperdist = 0;
		lowerdist = 0;
		alldistlower = 50;
		alldistupper = 50;
		globstep = 0.0D;
		max = 100D;
		min = -50D;
		cl = cluster1;
		names = as;
		setSize(profwidth, profheight);
		points = ad[0].length;
		framemid = profheight / 2;
		initpoints(ad);
		makelabely();
		addMouseMotionListener(this);
		addMouseListener(this);
		repaint();
	}

	public void addlo(int i) {
		for (int j = 0; j < points; j++)
			if (lowerval < i) {
				if (lower[j] - 4 > prof[j])
					lower[j] = lower[j] - 4;
			} else
			if (lowerval > i && upper[j] + 4 < profheight)
				lower[j] = lower[j] + 4;

		lowerval = i;
		repaint();
	}

	public void addupp(int i) {
		for (int j = 0; j < points; j++)
			if (upperval > i) {
				if (upper[j] + 4 < prof[j])
					upper[j] = upper[j] + 4;
			} else
			if (upperval < i && upper[j] - 4 > 0)
				upper[j] = upper[j] - 4;

		upperval = i;
		repaint();
	}

	public void cycle(int i) {
		boolean flag = false;
		boolean flag1 = false;
		boolean flag2 = false;
		if (cyclval < i) {
			int j = upper[0];
			int l = lower[0];
			int j1 = prof[0];
			for (int l1 = 0; l1 < points - 1; l1++) {
				upper[l1] = upper[l1 + 1];
				lower[l1] = lower[l1 + 1];
				prof[l1] = prof[l1 + 1];
			}

			upper[points - 1] = j;
			lower[points - 1] = l;
			prof[points - 1] = j1;
		} else
		if (cyclval > i) {
			int k = upper[points - 1];
			int i1 = lower[points - 1];
			int k1 = prof[points - 1];
			for (int i2 = points - 1; i2 > 0; i2--) {
				upper[i2] = upper[i2 - 1];
				lower[i2] = lower[i2 - 1];
				prof[i2] = prof[i2 - 1];
			}

			upper[0] = k;
			lower[0] = i1;
			prof[0] = k1;
		}
		cyclval = i;
		repaint();
	}

	public void getthem() {
		Vector vector = new Vector();
		Vector vector1 = new Vector();
		boolean flag = false;
		double ad[] = new double[points];
		double ad1[] = new double[points];
		double d = 1.0D;
		double d1 = 0.0D;
		double d2 = Math.max(Math.abs(max), Math.abs(min));
		for (int i = 0; i < points; i++) {
			ad[i] = (((double)framemid - (double)sumupper[i]) + 1.0D) * (d2 / ((double)framemid - 50D));
			ad1[i] = ((double)framemid - (double)sumlower[i] - 1.0D) * (d2 / ((double)framemid - 50D));
		}

		for (int j = 0; j < cl.a.data.length; j++) {
			boolean flag1 = true;
			for (int k = 0; k < points; k++) {
				if (cl.a.data[j][k] >= (int)(ad1[k] * d) && cl.a.data[j][k] <= (int)(ad[k] * d))
					continue;
				flag1 = false;
				break;
			}

			if (flag1) {
				vector.addElement(cl.a.data[j]);
				vector1.addElement(cl.a.names[j]);
			}
		}

		if (vector.size() > 0) {
			int ai[][] = new int[vector.size()][points];
			names = new String[vector.size()];
			for (int l = 0; l < vector.size(); l++) {
				ai[l] = (int[])vector.elementAt(l);
				names[l] = (String)vector1.elementAt(l);
			}

			graph2 graph2_1 = new graph2(ai, names, "profilesearch", cl);
			graph2_1.setVisible(true);
		}
	}

	public void initpoints() {
		upper = new int[points];
		prof = new int[points];
		lower = new int[points];
		for (int i = 0; i < points; i++) {
			upper[i] = framemid - alldistupper;
			prof[i] = framemid;
			lower[i] = framemid + alldistlower;
		}

	}

	public void initpoints(double ad[]) {
		double d = 0.0D;
		double d1 = 0.0D;
		upper = new int[points];
		prof = new int[points];
		lower = new int[points];
		max = -99999D;
		min = 999999D;
		for (int i = 0; i < points; i++) {
			if (min > ad[i])
				min = ad[i];
			if (max < ad[i])
				max = ad[i];
		}

		d1 = Math.max(Math.abs(max), Math.abs(min));
		for (int j = 0; j < points; j++) {
			prof[j] = (int)Math.round((double)framemid - (ad[j] / d1) * ((double)framemid - 50D));
			lower[j] = prof[j] + alldistlower;
			upper[j] = prof[j] - alldistupper;
		}

		forhold = d1 / (double)framemid;
	}

	public void initpoints(double ad[][]) {
		double d = 0.0D;
		double d1 = 0.0D;
		dupper = new double[points];
		dlower = new double[points];
		double d2 = 0.0D;
		double d4 = 0.0D;
		double d6 = 0.0D;
		upper = new int[points];
		prof = new int[points];
		lower = new int[points];
		firstupper = new int[points];
		firstprof = new int[points];
		firstlower = new int[points];
		max = -99999D;
		min = 999999D;
		addupper = new int[points];
		addlower = new int[points];
		sumupper = new int[points];
		sumlower = new int[points];
		for (int i = 0; i < ad.length; i++) {
			for (int j = 0; j < points; j++) {
				if (min > ad[i][j])
					min = ad[i][j];
				if (max < ad[i][j])
					max = ad[i][j];
			}

		}

		d1 = Math.max(Math.abs(max), Math.abs(min));
		for (int k = 0; k < points; k++) {
			double d3 = -999999D;
			double d5 = 9999999D;
			double d7 = 0.0D;
			for (int l = 0; l < ad.length; l++) {
				if (d3 < ad[l][k])
					d3 = ad[l][k];
				if (d5 > ad[l][k])
					d5 = ad[l][k];
				d7 += ad[l][k];
			}

			d7 /= ad.length;
			prof[k] = (int)Math.round((double)framemid - (d7 / d1) * ((double)framemid - 50D));
			lower[k] = (int)Math.round((double)framemid - (d5 / d1) * ((double)framemid - 50D));
			upper[k] = (int)Math.round((double)framemid - (d3 / d1) * ((double)framemid - 50D));
			dlower[k] = Math.round((double)framemid - (d5 / d1) * ((double)framemid - 50D));
			dupper[k] = Math.round((double)framemid - (d3 / d1) * ((double)framemid - 50D));
			firstupper[k] = upper[k];
			firstlower[k] = lower[k];
			firstprof[k] = prof[k];
		}

		forhold = d1 / (double)framemid;
	}

	public void makelabely() {
		double d = Math.max(Math.abs(max), Math.abs(min));
		d = d;
		d /= 5D;
		globstep = d;
		yneglabels = new String[(int)((double)profheight / 5D)];
		yposlabels = new String[(int)((double)profheight / 5D)];
		for (int i = 0; i < yneglabels.length; i++) {
			yneglabels[i] = String.valueOf((d * (double)i) / 1000D);
			yposlabels[i] = String.valueOf((-d * (double)i) / 1000D);
			if (yneglabels[i].length() > 7)
				yneglabels[i] = yneglabels[i].substring(0, 7);
			if (yposlabels[i].length() > 7)
				yposlabels[i] = yposlabels[i].substring(0, 7);
		}

	}

	public void mouseClicked(MouseEvent mouseevent) {
	}

	public void mouseDragged(MouseEvent mouseevent) {
		if (clicked != -1 && clickedline == 0 && upper[clicked] < prof[clicked] - 4)
			upper[clicked] = mouseevent.getY();
		else
		if (clicked != -1 && clickedline == 0 && upper[clicked] > mouseevent.getY())
			upper[clicked] = mouseevent.getY();
		else
		if (clicked != -1 && clickedline == 2 && lower[clicked] > prof[clicked] + 4)
			lower[clicked] = mouseevent.getY();
		else
		if (clicked != -1 && clickedline == 2 && lower[clicked] < mouseevent.getY())
			lower[clicked] = mouseevent.getY();
		if (clicked != -1 && clickedline == 1) {
			prof[clicked] = mouseevent.getY();
			upper[clicked] = prof[clicked] - upperdist;
			lower[clicked] = prof[clicked] - lowerdist;
		}
		repaint();
	}

	public void mouseEntered(MouseEvent mouseevent) {
	}

	public void mouseExited(MouseEvent mouseevent) {
	}

	public void mouseMoved(MouseEvent mouseevent) {
	}

	public void mousePressed(MouseEvent mouseevent) {
		clicked = -1;
		yclick = mouseevent.getY();
		for (int i = 0; i < points; i++) {
			if (mouseevent.getX() >= xvals[i] - 2 && mouseevent.getX() <= xvals[i] + 2 && mouseevent.getY() >= sumupper[i] - 2 && mouseevent.getY() <= sumupper[i] + 2) {
				clicked = i;
				clickedline = 0;
			}
			if (mouseevent.getX() >= xvals[i] - 2 && mouseevent.getX() <= xvals[i] + 2 && mouseevent.getY() >= prof[i] - 2 && mouseevent.getY() <= prof[i] + 2) {
				clicked = i;
				clickedline = 1;
				upperdist = prof[clicked] - upper[clicked];
				lowerdist = prof[clicked] - lower[clicked];
			}
			if (mouseevent.getX() >= xvals[i] - 2 && mouseevent.getX() <= xvals[i] + 2 && mouseevent.getY() >= sumlower[i] - 2 && mouseevent.getY() <= sumlower[i] + 2) {
				clicked = i;
				clickedline = 2;
			}
		}

	}

	public void mouseReleased(MouseEvent mouseevent) {
	}

	public void newprof() {
		for (int i = 0; i < points; i++) {
			upper[i] = framemid - 30;
			lower[i] = framemid + 30;
			prof[i] = framemid;
		}

		repaint();
	}

	public void paint(Graphics g) {
		update(g);
	}

	public void read() {
		try {
			FileDialog filedialog = new FileDialog(new Frame("Enter filename"));
			filedialog.setFile(".prf");
			filedialog.setMode(0);
			if (cl != null && cl.lastpath != null)
				filedialog.setDirectory(cl.lastpath);
			filedialog.show();
			File file = new File(filedialog.getDirectory() + filedialog.getFile());
			if (cl != null && cl.lastpath != null)
				cl.lastpath = filedialog.getDirectory();
			FileInputStream fileinputstream = new FileInputStream(file);
			ObjectInputStream objectinputstream = new ObjectInputStream(fileinputstream);
			upper = (int[])objectinputstream.readObject();
			lower = (int[])objectinputstream.readObject();
			prof = (int[])objectinputstream.readObject();
			fileinputstream.close();
		}
		catch (Exception _ex) {
			System.out.println("IO Exception:");
		}
		repaint();
	}

	public void sub1() {
		for (int i = 0; i < points; i++) {
			lower[i]++;
			upper[i]--;
		}

		repaint();
	}

	public void update(Graphics g) {
		xvals = new int[points];
		for (int i = 0; i < points; i++)
			xvals[i] = 50 + ((profwidth - 60) / points) * i;

		if (offImage == null) {
			offImage = createImage(profwidth, profheight);
			offGraphics = offImage.getGraphics();
			offGraphics.setColor(Color.lightGray);
			offGraphics.fillRect(0, 0, profheight, profwidth);
		} else {
			offGraphics = offImage.getGraphics();
			offGraphics.setColor(Color.lightGray);
			offGraphics.fillRect(0, 0, profwidth, profheight);
		}
		offGraphics.setFont(new Font("Times", 0, 9));
		offGraphics.setColor(Color.black);
		offGraphics.drawString("0.0", 5, framemid + 2);
		for (int j = 1; j < yneglabels.length; j++) {
			offGraphics.setColor(Color.black);
			offGraphics.drawString(yposlabels[j], 5, framemid + 2 + ((framemid - 50) / 5) * j);
			offGraphics.drawString(yneglabels[j], 5, (framemid + 2) - ((framemid - 50) / 5) * j);
			offGraphics.setColor(new Color(200, 200, 200));
			offGraphics.drawLine(51, framemid - ((framemid - 50) / 5) * j, profwidth - 10, framemid - ((framemid - 50) / 5) * j);
			offGraphics.drawLine(51, framemid + ((framemid - 50) / 5) * j, profwidth - 10, framemid + ((framemid - 50) / 5) * j);
		}

		for (int k = 0; k < points; k++) {
			sumupper[k] = addupper[k] + upper[k];
			sumlower[k] = addlower[k] + lower[k];
		}

		offGraphics.setColor(new Color(170, 170, 170));
		offGraphics.drawPolyline(xvals, firstupper, points);
		offGraphics.drawPolyline(xvals, firstlower, points);
		offGraphics.drawPolyline(xvals, firstprof, points);
		offGraphics.setColor(new Color(100, 100, 100));
		offGraphics.drawLine(50, 10, 50, profheight - 10);
		offGraphics.drawLine(50, framemid, profwidth - 10, framemid);
		offGraphics.setColor(Color.red);
		offGraphics.drawPolyline(xvals, sumupper, points);
		offGraphics.drawPolyline(xvals, sumlower, points);
		offGraphics.setColor(Color.blue);
		offGraphics.drawPolyline(xvals, prof, points);
		for (int l = 0; l < points; l++) {
			offGraphics.setColor(Color.green);
			offGraphics.drawRect(xvals[l] - 1, sumupper[l] - 1, 3, 3);
			offGraphics.drawRect(xvals[l] - 1, sumlower[l] - 1, 3, 3);
			offGraphics.drawRect(xvals[l] - 1, prof[l] - 1, 3, 3);
		}

		g.drawImage(offImage, 0, 0, this);
	}

	public void write() {
		try {
			FileDialog filedialog = new FileDialog(new Frame("Enter filename"));
			filedialog.setFile(".prf");
			filedialog.setMode(1);
			if (cl != null && cl.lastpath != null)
				filedialog.setDirectory(cl.lastpath);
			filedialog.show();
			File file = new File(filedialog.getDirectory() + filedialog.getFile());
			if (cl != null && cl.lastpath != null)
				cl.lastpath = filedialog.getDirectory();
			if (!file.getName().endsWith("null")) {
				FileOutputStream fileoutputstream = new FileOutputStream(file);
				ObjectOutputStream objectoutputstream = new ObjectOutputStream(fileoutputstream);
				objectoutputstream.writeObject(upper);
				objectoutputstream.writeObject(lower);
				objectoutputstream.writeObject(prof);
				objectoutputstream.flush();
				fileoutputstream.close();
			}
		}
		catch (IOException _ex) {
			System.out.println("IO Exception:");
		}
	}
}
