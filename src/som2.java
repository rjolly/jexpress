import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextComponent;
import java.awt.TextField;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.PrintStream;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;

public class som2 extends Component
	implements ActionListener, Runnable, MouseListener {

	int numneurons;
	double alunder;
	double alover;
	double matrix[][];
	int neurons[][];
	int iteration;
	int iterations;
	node net[][];
	int netwidth;
	double closestvalue;
	double disttolerance;
	double minval;
	double maxval;
	JButton jb1;
	double relpull;
	analyse a;
	cluster cl;
	public Thread animator;
	public boolean please_stop;
	int sleep;
	int beforepaint;
	double rmat[][];
	double flatnode[];
	public node gn[];
	int typesom;
	int globali;
	int globalj;
	int teller;
	som3d s3d;
	int taken[];
	int neuronsassigned;
	boolean d3D;
	boolean d2D;
	Checkbox c1;
	Checkbox c2;
	double smalldist[][];
	double dist[][][];
	double sammatrix[][];
	TextField f1;
	TextField f2;
	TextField f3;
	TextField f4;
	TextField f5;
	TextField f6;
	TextField f7;
	TextField f8;
	somframe sf;
	TextField tf;
	Button b;
	vispca pca;
	Frame f;
	double theta;
	double phi;
	double momentum;
	double phitolerance;
	Label maxl;
	Label minl;
	Checkbox cb;
	String names[];

	public som2(analyse analyse1, cluster cluster) {
		alunder = 10D;
		alover = 10D;
		iteration = 0;
		iterations = 0;
		netwidth = 4;
		disttolerance = 2.2000000000000002D;
		animator = null;
		please_stop = false;
		sleep = 30;
		beforepaint = 5;
		typesom = 0;
		neuronsassigned = 0;
		d3D = false;
		d2D = true;
		tf = new TextField(5);
		b = new Button("review");
		theta = 0.5D;
		phi = 0.40000000000000002D;
		momentum = 0.998D;
		phitolerance = 0.10000000000000001D;
		maxl = new Label("Max: ");
		minl = new Label("Min: ");
		cb = new Checkbox("Step");
		cl = cluster;
		a = analyse1;
		int i = max(analyse1.data);
		b.addActionListener(this);
		addMouseListener(this);
		matrix = reduce(analyse1.data, 1.0D);
		getvalues();
		minval = min(matrix);
		maxval = max(matrix);
		maxl.setText("Max: " + String.valueOf(maxval / 1000D));
		minl.setText("Min: " + String.valueOf(minval / 1000D));
	}

	public som2(analyse analyse1, int ai[][], cluster cluster) {
		alunder = 10D;
		alover = 10D;
		iteration = 0;
		iterations = 0;
		netwidth = 4;
		disttolerance = 2.2000000000000002D;
		animator = null;
		please_stop = false;
		sleep = 30;
		beforepaint = 5;
		typesom = 0;
		neuronsassigned = 0;
		d3D = false;
		d2D = true;
		tf = new TextField(5);
		b = new Button("review");
		theta = 0.5D;
		phi = 0.40000000000000002D;
		momentum = 0.998D;
		phitolerance = 0.10000000000000001D;
		maxl = new Label("Max: ");
		minl = new Label("Min: ");
		cb = new Checkbox("Step");
		cl = cluster;
		a = analyse1;
		int i = max(ai);
		b.addActionListener(this);
		addMouseListener(this);
		matrix = reduce(ai, 1.0D);
		getvalues();
		minval = min(matrix);
		maxval = max(matrix);
		maxl.setText("Max: " + String.valueOf(maxval / 1000D));
		minl.setText("Min: " + String.valueOf(minval / 1000D));
	}

	public som2(int ai[][], String as[], int i, cluster cluster) {
		alunder = 10D;
		alover = 10D;
		iteration = 0;
		iterations = 0;
		netwidth = 4;
		disttolerance = 2.2000000000000002D;
		animator = null;
		please_stop = false;
		sleep = 30;
		beforepaint = 5;
		typesom = 0;
		neuronsassigned = 0;
		d3D = false;
		d2D = true;
		tf = new TextField(5);
		b = new Button("review");
		theta = 0.5D;
		phi = 0.40000000000000002D;
		momentum = 0.998D;
		phitolerance = 0.10000000000000001D;
		maxl = new Label("Max: ");
		minl = new Label("Min: ");
		cb = new Checkbox("Step");
		cl = cluster;
		typesom = i;
		names = as;
		int j = max(ai);
		b.addActionListener(this);
		addMouseListener(this);
		matrix = reduce(ai, 1.0D);
		getvalues();
		minval = min(matrix);
		maxval = max(matrix);
		maxl.setText("Max: " + String.valueOf(maxval / 1000D));
		minl.setText("Min: " + String.valueOf(minval / 1000D));
	}

	public void actionPerformed(ActionEvent actionevent) {
		boolean flag = false;
		if (actionevent.getActionCommand() == "Sweep" && net != null) {
			disttolerance = dubval(f4.getText()) * 1000D;
			for (int i = 0; i < net.length; i++) {
				for (int j = 0; j < net[0].length; j++)
					net[i][j].clear();

			}

			sweep();
			makegraphs();
		} else
		if (actionevent.getActionCommand() == "Cancel") {
			f.dispose();
			if (pca != null)
				pca.dispose();
			if (s3d != null)
				s3d.dispose();
		} else
		if (actionevent.getActionCommand() == "Stop") {
			jb1.setLabel("Run");
			please_stop = true;
			stop();
		} else
		if (actionevent.getActionCommand() == "Reset") {
			theta = 0.5D;
			phi = 0.40000000000000002D;
			momentum = 0.998D;
			phitolerance = 0.10000000000000001D;
			f1.setText(String.valueOf(theta));
			f2.setText(String.valueOf(phi));
			f3.setText(String.valueOf(momentum));
			f4.setText(String.valueOf(disttolerance / 1000D));
			f5.setText(String.valueOf(netwidth));
			f6.setText(String.valueOf(phitolerance));
		} else
		if (actionevent.getActionCommand() == "OK") {
			f5.setEditable(false);
			jb1.setLabel("Run");
			disttolerance = dubval(f4.getText()) * 1000D;
			phitolerance = dubval(f6.getText());
			theta = dubval(f1.getText());
			phi = dubval(f2.getText());
			beforepaint = (int)dubval(f8.getText());
			momentum = dubval(f3.getText());
			d3D = c2.getState();
			d2D = c1.getState();
			netwidth = intval(f5.getText());
			dist = new double[netwidth][netwidth][matrix.length];
			net = makenet(netwidth, minval, maxval, matrix[0].length);
			rmat = new double[netwidth * netwidth][netwidth * netwidth];
			if (d2D)
				if (a != null)
					pca = new vispca(matrix, a.names, net, cl);
				else
					pca = new vispca(matrix, names, net, cl);
			if (d3D) {
				if (d2D)
					s3d = new som3d(pca.PCA);
				else
					s3d = new som3d(matrix);
				s3d.addnet(net);
			}
		} else
		if (actionevent.getActionCommand() == "Run") {
			please_stop = false;
			jb1.setLabel("Stop");
			start();
		}
	}

	public void calcdist() {
		double d = 0.0D;
		double d2 = 0.0D;
		for (int i = 0; i < net.length; i++) {
			for (int j = 0; j < net[i].length; j++) {
				for (int k = 0; k < matrix.length; k++) {
					double d1 = 0.0D;
					double d3 = 0.0D;
					for (int l = 0; l < matrix[k].length; l++) {
						double d4 = matrix[k][l] - net[i][j].vector[l];
						d1 += d4 * d4;
					}

					d1 = Math.sqrt(d1);
					dist[i][j][k] = d1;
				}

			}

		}

	}

	public void closen(node node1, node node2) {
		double d = 0.0D;
		for (int i = 0; i < node1.vector.length; i++);
	}

	public void closenode(double ad[], node node1) {
		node1.additions++;
		for (int i = 0; i < ad.length; i++)
			node1.addedvector[i] = node1.addedvector[i] + ad[i];

	}

	public double dubval(String s) {
		Double double1 = new Double(s);
		return double1.doubleValue();
	}

	public node findclosest(double ad[]) {
		double d = 21474833647D;
		node node1 = null;
		double d1 = 0.10000000000000001D;
		for (int i = 0; i < netwidth; i++) {
			for (int j = 0; j < netwidth; j++) {
				for (int k = 0; k < net[i][j].vector.length; k++)
					d1 += Math.pow(net[i][j].vector[k] - ad[k], 2D);

				d1 = Math.sqrt(d1);
				if (d1 < d) {
					d = d1;
					node1 = net[i][j];
				}
				d1 = 0.0D;
			}

		}

		return node1;
	}

	public node findclosestneuron(double ad[], int i) {
		double d = 21474833647D;
		node node1 = null;
		for (int j = 0; j < netwidth; j++) {
			for (int k = 0; k < netwidth; k++) {
				if (net[j][k] == null)
					System.out.print("En node i nettet er null!");
				if (dist[j][k][i] < d) {
					node1 = net[j][k];
					d = dist[j][k][i];
					globali = j;
					globalj = k;
				}
			}

		}

		if (node1 == null) {
			System.out.print("Nermeste node er null!");
			node1 = net[0][0];
		}
		return node1;
	}

	public node findclosestneuron(int ai[], int i) {
		double d = 0.0D;
		double d2 = 99999D;
		node node1 = null;
		double d3 = 0.0D;
		for (int j = 0; j < net.length; j++) {
			for (int k = 0; k < net[j].length; k++) {
				double d1 = 0.0D;
				for (int l = 0; l < net[j][k].vector.length; l++)
					d1 += Math.pow(Math.max(ai[l], net[j][k].vector[l]) - Math.min(ai[l], net[j][k].vector[l]), 2D);

				d1 = Math.sqrt(d1);
				if (d1 < d2) {
					d2 = d1;
					closestvalue = d1;
					node1 = net[j][k];
				}
			}

		}

		d3 = maxval - minval;
		return node1;
	}

	public double getrelpull(node node1) {
		return node1.additions / relpull;
	}

	public void getvalues() {
		jb1 = new JButton("OK");
		JButton jbutton = new JButton("Cancel");
		JButton jbutton1 = new JButton("Sweep");
		JButton jbutton2 = new JButton("Reset");
		jb1.addActionListener(this);
		jbutton.addActionListener(this);
		jbutton1.addActionListener(this);
		jbutton2.addActionListener(this);
		jb1.setBackground(new Color(201, 201, 240));
		jbutton.setBackground(new Color(201, 201, 240));
		jbutton1.setBackground(new Color(201, 201, 240));
		jbutton2.setBackground(new Color(201, 201, 240));
		c1 = new Checkbox("2D view", true);
		c2 = new Checkbox("3D view", false);
		f1 = new TextField(String.valueOf(theta), 6);
		f2 = new TextField(String.valueOf(phi), 6);
		f3 = new TextField(String.valueOf(momentum), 6);
		f4 = new TextField(String.valueOf(disttolerance), 6);
		f5 = new TextField(String.valueOf(netwidth), 6);
		f6 = new TextField(String.valueOf(phitolerance), 6);
		f7 = new TextField(String.valueOf(sleep), 6);
		f8 = new TextField(String.valueOf(beforepaint), 6);
		f = new Frame("Som Properties");
		f.setLayout(new GridLayout(12, 2));
		maxl.setBackground(new Color(200, 200, 220));
		minl.setBackground(new Color(200, 200, 220));
		f.add(maxl);
		f.add(minl);
		f.add(new Label("Theta: "));
		f.add(f1);
		f.add(new Label("Phi: "));
		f.add(f2);
		f.add(new Label("Momentum: "));
		f.add(f3);
		f.add(new Label("Tolerance: "));
		f.add(f4);
		f.add(new Label("nxn neurons: "));
		f.add(f5);
		f.add(new Label("Phi toerance: "));
		f.add(f6);
		f.add(new Label("Pause: "));
		f.add(f7);
		f.add(new Label("Updates before repaint: "));
		f.add(f8);
		f.add(c1);
		f.add(c2);
		f.add(jb1);
		f.add(jbutton);
		f.add(jbutton1);
		f.add(jbutton2);
		f.setBackground(Color.lightGray);
		f.pack();
		actionhandler actionhandler1 = new actionhandler(f);
		f.addWindowListener(actionhandler1);
		f.setVisible(true);
	}

	public int intval(String s) {
		Integer integer = new Integer(s);
		return integer.intValue();
	}

	public void makeR(node node1, double ad[]) {
		double d = 0.0D;
		double d2 = 0.0D;
		for (int i = 0; i < netwidth; i++) {
			for (int j = 0; j < netwidth; j++) {
				double d3 = Math.pow(net[i][j].xcor - node1.xcor, 2D) + Math.pow(net[i][j].ycor - node1.ycor, 2D);
				d3 = Math.sqrt(d3);
				double d1 = Math.exp((-1D * (d3 * d3)) / (2D * theta * theta));
				for (int k = 0; k < net[i][j].vector.length; k++)
					net[i][j].vector[k] += phi * d1 * (ad[k] - net[i][j].vector[k]);

			}

		}

	}

	public void makegraphs() {
		somgraph asomgraph[][] = new somgraph[net.length][net[0].length];
		String as1[] = new String[1];
		as1[0] = "No members in this node";
		int ai[][] = new int[1][matrix.length];
		for (int i = 0; i < net.length; i++)
			ai[0][i] = 0;

		for (int j = 0; j < net.length; j++) {
			for (int k = 0; k < net[j].length; k++)
				if (net[j][k].memberindexes != null) {
					int ai1[][] = new int[net[j][k].memberindexes.length][matrix[0].length];
					String as[] = new String[net[j][k].memberindexes.length];
					for (int l = 0; l < net[j][k].memberindexes.length; l++)
						if (typesom == 0) {
							if (maxval > 1000D)
								ai1[l] = toint(matrix[net[j][k].memberindexes[l]], 1);
							else
								ai1[l] = toint(matrix[net[j][k].memberindexes[l]], 1);
							as[l] = a.names[net[j][k].memberindexes[l]];
						} else {
							if (maxval > 1000D)
								ai1[l] = toint(matrix[net[j][k].memberindexes[l]], 1);
							else
								ai1[l] = toint(matrix[net[j][k].memberindexes[l]], 1);
							as[l] = names[net[j][k].memberindexes[l]];
						}

					asomgraph[j][k] = new somgraph(ai1, as, "node [" + j + "][" + k + "]", 200 / net.length, 200 / net[0].length, cl);
					asomgraph[j][k].pca = pca;
					asomgraph[j][k].s3d = s3d;
				} else {
					asomgraph[j][k] = new somgraph(ai, as1, "node [" + j + "][" + k + "]", 200 / net.length, 200 / net[0].length, cl);
					asomgraph[j][k].pca = pca;
					asomgraph[j][k].s3d = s3d;
				}

		}

		sf = new somframe(asomgraph, tf, b, pca);
	}

	public node[][] makenet(int i, double d, double d1, int j) {
		node anode[][] = new node[i][i];
		for (int k = 0; k < i; k++) {
			for (int l = 0; l < i; l++) {
				anode[k][l] = new node(makeneuron(), (double)k / (double)netwidth, (double)l / (double)netwidth);
				if (anode[k][l] == null)
					System.out.print("Krise. neuron er null!");
			}

		}

		anode[0][0] = new node(matrix[0], 0.0D, 0.0D);
		for (int i1 = 1; i1 < i; i1++) {
			anode[0][i1].down = anode[1][i1];
			anode[i1][0].right = anode[i1][1];
			anode[i1][i - 1].left = anode[i1][i - 2];
			anode[i - 1][i1].up = anode[i - 2][i1];
		}

		for (int j1 = 1; j1 < i - 1; j1++) {
			for (int k1 = 1; k1 < i - 1; k1++) {
				anode[j1][k1].down = anode[j1 + 1][k1];
				anode[j1][k1].right = anode[j1][k1 + 1];
				anode[j1][k1].left = anode[j1][k1 - 1];
				anode[j1][k1].up = anode[j1 - 1][k1];
			}

		}

		return anode;
	}

	public double[] makeneuron() {
		int i = -1;
		boolean flag = false;
		boolean flag1 = false;
		if (netwidth * netwidth < matrix.length) {
			if (taken == null) {
				taken = new int[netwidth * netwidth];
				for (int j = 0; j < taken.length; j++)
					taken[j] = -1;

			}
			while (!flag)  {
				i = (int)Math.round(Math.random() * (double)(matrix.length - 1));
				boolean flag2 = false;
				for (int k = 0; k < taken.length; k++)
					if (i == taken[k])
						flag2 = true;

				if (!flag2) {
					flag = true;
					taken[neuronsassigned] = i;
					neuronsassigned++;
				}
			}
		} else {
			i = (int)Math.round(Math.random() * (double)(matrix.length - 1));
		}
		double ad[] = matrix[i];
		return ad;
	}

	public double[] makeneuron(double d, double d1, int i) {
		double d2 = 0.0D;
		double ad[] = new double[i];
		for (int j = 0; j < i; j++) {
			double d3 = Math.random() * (d1 - d) + d;
			ad[j] = d3;
		}

		return ad;
	}

	public void makesmalldist() {
		double d = 0.0D;
		double d1 = -9999999D;
		smalldist = new double[net.length][net[0].length];
		for (int i = 0; i < net.length; i++) {
			for (int j = 0; j < net[0].length; j++) {
				for (int l = 0; l < net[i][j].vector.length; l++)
					d += Math.pow(net[i][j].vector[l] - net[i][j].addedvector[l], 2D);

				smalldist[i][j] = Math.sqrt(d);
				if (d1 < smalldist[i][j])
					d1 = smalldist[i][j];
			}

		}

		for (int k = 0; k < net.length; k++) {
			for (int i1 = 0; i1 < net[0].length; i1++)
				smalldist[k][i1] = smalldist[k][i1] / d1;

		}

	}

	public double max(double ad[][]) {
		double d = -100000D;
		for (int i = 0; i < ad.length; i++) {
			for (int j = 0; j < ad[i].length; j++)
				if (d < ad[i][j])
					d = ad[i][j];

		}

		return d;
	}

	public int max(int ai[][]) {
		int i = 0xfffe7960;
		for (int j = 0; j < ai.length; j++) {
			for (int k = 0; k < ai[j].length; k++)
				if (i < ai[j][k])
					i = ai[j][k];

		}

		return i;
	}

	public double min(double ad[][]) {
		double d = 100000D;
		for (int i = 0; i < ad.length; i++) {
			for (int j = 0; j < ad[i].length; j++)
				if (d > ad[i][j])
					d = ad[i][j];

		}

		return d;
	}

	public void mouseClicked(MouseEvent mouseevent) {
		please_stop = true;
	}

	public void mouseEntered(MouseEvent mouseevent) {
	}

	public void mouseExited(MouseEvent mouseevent) {
	}

	public void mousePressed(MouseEvent mouseevent) {
	}

	public void mouseReleased(MouseEvent mouseevent) {
	}

	public double neurondist(node node1, node node2) {
		double d = 0.0D;
		double d1 = 0.0D;
		double d2 = 0.0D;
		for (int i = 0; i < node1.vector.length; i++) {
			double d3 = node1.vector[i] - node2.vector[i];
			d += d3 * d3;
		}

		d1 = Math.sqrt(d);
		return d1;
	}

	public double[][] reduce(int ai[][], double d) {
		double ad[][] = new double[ai.length][ai[0].length];
		for (int i = 0; i < ai.length; i++) {
			for (int j = 0; j < ai[i].length; j++)
				ad[i][j] = (double)ai[i][j] / d;

		}

		return ad;
	}

	public void run() {
		int i = 0;
		disttolerance = dubval(f4.getText()) * 1000D;
		phitolerance = dubval(f6.getText());
		theta = dubval(f1.getText());
		phi = dubval(f2.getText());
		sleep = (int)dubval(f7.getText());
		momentum = dubval(f3.getText());
		beforepaint = (int)dubval(f8.getText());
		while (!please_stop)  {
			if (phi < phitolerance)
				stop();
			int j = (int)Math.floor(Math.random() * (double)(matrix.length - 1));
			double ad[] = matrix[j];
			node node1 = findclosest(ad);
			makeR(node1, ad);
			if (i == beforepaint) {
				if (d2D)
					pca.update(net);
				if (d3D)
					s3d.updatenet(net);
				i = 0;
			} else {
				i++;
			}
			phi = phi * momentum;
			theta = theta * momentum;
			f1.setText(String.valueOf(theta));
			f2.setText(String.valueOf(phi));
			f3.setText(String.valueOf(momentum));
			f4.setText(String.valueOf(disttolerance / 1000D));
			f5.setText(String.valueOf(netwidth));
			f6.setText(String.valueOf(phitolerance));
			f7.setText(String.valueOf(sleep));
			f8.setText(String.valueOf(beforepaint));
			try {
				Thread.sleep(sleep);
			}
			catch (InterruptedException _ex) { }
		}
		animator = null;
	}

	public void setrelpull() {
		relpull = -999999D;
		for (int i = 0; i < net.length; i++) {
			for (int j = 0; j < net[0].length; j++)
				if (net[i][j].additions > relpull)
					relpull = net[i][j].additions;

		}

	}

	public void start() {
		animator = new Thread(this);
		animator.start();
	}

	public void stop() {
		if (animator != null)
			animator.stop();
		animator = null;
	}

	public void sweep() {
		double d = 0.0D;
		double d2 = 0.0D;
		boolean flag = false;
		boolean flag1 = false;
		boolean flag2 = false;
		double d4 = 99999999D;
		for (int i = 0; i < net.length; i++) {
			for (int j = 0; j < net[i].length; j++) {
				for (int k = 0; k < matrix.length; k++) {
					double d1 = 0.0D;
					for (int l = 0; l < matrix[k].length; l++) {
						double d3 = matrix[k][l] - net[i][j].vector[l];
						d1 += d3 * d3;
					}

					d1 = Math.sqrt(d1);
					if (d1 < disttolerance) {
						net[i][j].newmember(k);
						boolean flag3 = true;
					}
				}

			}

		}

	}

	public int[] toint(double ad[], int i) {
		int ai[] = new int[ad.length];
		for (int j = 0; j < ad.length; j++)
			ai[j] = Math.round((long)ad[j] * (long)i);

		return ai;
	}

	public int[][] toint(double ad[][], int i) {
		int ai[][] = new int[ad.length][ad[0].length];
		for (int j = 0; j < ad.length; j++) {
			for (int k = 0; k < ad[j].length; k++)
				ai[j][k] = Math.round((long)ad[j][k] * (long)i);

		}

		return ai;
	}

	public void train() {
		Object obj = null;
		for (int i = 0; i < matrix.length; i++) {
			node node1 = findclosestneuron(matrix[i], i);
			closenode(matrix[i], node1);
			if (node1.left != null)
				closenode(matrix[i], node1.left);
			if (node1.right != null)
				closenode(matrix[i], node1.right);
			if (node1.up != null)
				closenode(matrix[i], node1.up);
			if (node1.down != null)
				closenode(matrix[i], node1.down);
		}

	}
}
