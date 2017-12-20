import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintStream;
import java.util.Stack;
import java.util.Vector;
import javax.swing.AbstractButton;

public class analyse
	implements ActionListener {

	int lengde;
	int bredde;
	int clusters;
	String clusvector[];
	String names[];
	File f;
	filehandler fh;
	int minvalue;
	int maxvalue;
	Stack nullturn;
	Label status;
	int data[][];
	int dist[][];
	int genvalues[];
	dataloader dl;
	tsg ts;
	cluster c;
	Frame ret;
	TextArea ta;
	int noise[][];
	graph gnoise;

	public analyse(cluster cluster1) {
		lengde = 200;
		bredde = 15;
		clusters = 0;
		fh = new filehandler();
		nullturn = new Stack();
		c = cluster1;
	}

	public void actionPerformed(ActionEvent actionevent) {
		boolean flag = false;
		boolean flag1 = false;
		int i = 0;
		int j = 0;
		if (actionevent.getActionCommand() == "reset") {
			if (noise != null)
				noise = null;
		} else
		if (actionevent.getActionCommand() == "simulate") {
			for (int k = 0; k < ts.numreps; k++) {
				data = ts.generate2();
				minvalue = ts.min;
				maxvalue = ts.max;
				lengde = ts.length;
				bredde = ts.width;
				names = ts.names;
				clusters = lengde - 1;
				calcdist(new Label("Calculating distances"));
				c.clustermethod = 1;
				if (ts.jRadioButton1.isSelected())
					c.clustermethod = 0;
				else
				if (ts.jRadioButton2.isSelected())
					c.clustermethod = 1;
				else
					c.clustermethod = 2;
				if (ts.numreps > 1)
					c.makeclusters(false);
				else
					c.makeclusters(true);
				ts.score();
				i += ts.totfrags;
				j += ts.fragmentedclusters;
			}

			if (ret == null) {
				ret = new Frame("Simulation results");
				ta = new TextArea(9, 50);
				ret.add(ta);
				actionhandler actionhandler1 = new actionhandler(ret);
				ret.addWindowListener(actionhandler1);
			}
			ta.append("Made " + ts.numreps + " simulations\n");
			ta.append(ts.numclusters + " clusters in each simulation.\n");
			ta.append(ts.numclusters * ts.numobjects + " clusterobjects in each simulation.\n");
			ta.append("total clusters =" + ts.numclusters * ts.numreps + "\n");
			ta.append("total vectors =" + ts.numclusters * ts.numreps * ts.numobjects + "\n");
			ta.append(j + " of the clusters where fragmented into total " + i + " fragments");
			ta.append("\nfragments/clusters=" + ((double)i / (double)ts.numclusters) * 20D + "\n");
			ta.append("---------------------------------------------------------\n");
			ret.pack();
			ret.setVisible(true);
			noise = addtoarr(noise, i);
			if (gnoise != null) {
				gnoise.matrix = noise;
				gnoise.repaint();
			} else
			if (noise[0].length > 1)
				gnoise = new graph(noise, "fragments", c);
		} else
		if (dl != null) {
			dl.makematrix();
			if (dl.error == 0) {
				dl.screen = null;
				data = dl.matrix;
				minvalue = dl.minvalue;
				maxvalue = dl.maxvalue;
				lengde = dl.lengde;
				bredde = dl.bredde;
				names = dl.names;
				clusters = lengde - 1;
				dl.setVisible(false);
				dl.dispose();
			}
		}
	}

	public double[][] addtoarr(double ad[][], double d) {
		double ad1[][];
		if (ad != null) {
			ad1 = new double[1][ad.length + 1];
			for (int i = 0; i < ad.length; i++)
				ad1[0][i] = ad[0][i];

			ad1[0][ad[0].length] = d;
		} else {
			ad1 = new double[0][1];
			ad1[0][0] = d;
		}
		return ad1;
	}

	public int[][] addtoarr(int ai[][], int i) {
		int ai1[][];
		if (ai != null) {
			ai1 = new int[1][ai[0].length + 1];
			for (int j = 0; j < ai[0].length; j++)
				ai1[0][j] = ai[0][j];

			ai1[0][ai[0].length] = i;
		} else {
			ai1 = new int[1][1];
			ai1[0][0] = i;
		}
		return ai1;
	}

	public void calcdist(Label label) {
		status = label;
		dist = calcdistance(data, label);
	}

	public int[][] calcdistance(int ai[][], Label label) {
		int ai1[][] = makematrix();
		genvalues = new int[lengde];
		int i = 0;
		for (int j = 0; j < lengde; j++) {
			if ((double)j / (double)lengde < 0.10000000000000001D)
				label.setText("Calculating distances");
			if ((double)j / (double)lengde > 0.20000000000000001D && (double)j / (double)lengde < 0.25D)
				label.setText("25% of distance matrix calculated");
			if ((double)j / (double)lengde > 0.5D && (double)j / (double)lengde < 0.59999999999999998D)
				label.setText("50% of distance matrix calculated");
			if ((double)j / (double)lengde > 0.69999999999999996D && (double)j / (double)lengde < 8D)
				label.setText("75% of distance matrix calculated");
			if ((double)j / (double)lengde == 1.0D)
				label.setText("100% of distance matrix calculated");
			for (int k = 0; k < j; k++) {
				for (int l = 0; l < bredde; l++) {
					ai1[j][k] = ai1[j][k] + calcvectordistance(ai[j][l], ai[k][l]);
					genvalues[j] = genvalues[j] + ai[j][l];
				}

				ai1[j][k] = (int)Math.sqrt(ai1[j][k]);
				i++;
			}

		}

		return ai1;
	}

	public int calcvectordistance(int i, int j) {
		boolean flag = false;
		int l = 0;
		if (i < j) {
			int k = i;
			i = j;
			j = k;
		}
		l = i - j;
		return l * l;
	}

	public void getdata(Label label) {
		dl = new dataloader(label, c);
		dl.jb1.addActionListener(this);
		dl.setVisible(true);
	}

	public int intval(String s) {
		Integer integer = new Integer(s);
		return integer.intValue();
	}

	public int intvalue(String s) {
		Integer integer = new Integer(s);
		return integer.intValue();
	}

	public int largest(int i, int j) {
		int k = 0;
		if (i > j)
			k = i;
		else
			k = j;
		return k;
	}

	public int largest(int i, int j, int k, int l) {
		int i1 = 0;
		i1 = largest(largest(i, j), largest(k, l));
		return i1;
	}

	public int[][] makematrix() {
		int ai[][] = new int[lengde][];
		for (int i = 0; i < lengde; i++)
			ai[i] = new int[i];

		return ai;
	}

	public void paintvalues(int i, Graphics g, int j, int k) {
		boolean flag = false;
		if (minvalue == 0)
			minvalue = 1;
		if (maxvalue == 0)
			maxvalue = 1;
		Color color = g.getColor();
		for (int j1 = 0; j1 < bredde; j1++) {
			if (data[i][j1] < 0) {
				int l = Math.abs(70 + (int)(((double)data[i][j1] / (double)minvalue) * 180D));
				g.setColor(new Color(0, l, 0));
			} else {
				int i1 = Math.abs(70 + (int)(((double)data[i][j1] / (double)maxvalue) * 180D));
				g.setColor(new Color(i1, 0, 0));
			}
			g.fillRect(j + j1 * 3, k, 4, 3);
		}

		for (int k1 = 0; k1 < c.nullvals.size(); k1++) {
			Point point = (Point)c.nullvals.elementAt(k1);
			if (point.x > i)
				break;
			if (point.x == i) {
				g.setColor(Color.blue);
				g.fillRect(j + point.y * 3, k, 4, 3);
			}
		}

		g.setColor(Color.black);
		g.setColor(color);
	}

	public void paintvalues2(int i, Graphics g, int j, int k) {
		boolean flag = false;
		if (minvalue == 0)
			minvalue = 1;
		if (maxvalue == 0)
			maxvalue = 1;
		for (int j1 = 0; j1 < bredde; j1++) {
			if (data[i][j1] < 0) {
				int l = Math.abs(70 + (int)(((double)data[i][j1] / (double)minvalue) * 180D));
				g.setColor(new Color(0, l, 0));
			} else {
				int i1 = Math.abs(70 + (int)(((double)data[i][j1] / (double)maxvalue) * 180D));
				g.setColor(new Color(i1, 0, 0));
			}
			g.fillRect(j + j1 * 8, k - 2, 9, 8);
			g.setColor(Color.black);
			g.drawString(names[i], (j + bredde * 8 + 4) - 2, k + 4);
		}

		for (int k1 = 0; k1 < c.nullvals.size(); k1++) {
			Point point = (Point)c.nullvals.elementAt(k1);
			if (point.x > i)
				break;
			if (point.x == i) {
				g.setColor(Color.blue);
				g.fillRect(j + point.y * 8, k - 2, 9, 8);
			}
		}

		g.setColor(Color.black);
	}

	public void print(int i) {
		System.out.print(String.valueOf(i));
	}

	public void print(String s) {
		System.out.print(s);
	}

	public void println(int i) {
		System.out.print(String.valueOf(i) + "\n");
	}

	public void println(String s) {
		System.out.print(s + "\n");
	}

	public void simulate(Label label, cluster cluster1) {
		c = cluster1;
		ts = new tsg(cluster1);
		ts.b.addActionListener(this);
		ts.b2.addActionListener(this);
	}
}
