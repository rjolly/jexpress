import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.TextArea;
import java.awt.Window;
import java.io.PrintStream;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class tsg extends Frame
	implements ChangeListener {

	int numclusters;
	int numobjects;
	int numvalues;
	int noisevectors;
	int noisedev;
	int devdecrease;
	int devvert;
	int span;
	int numreps;
	int noiseregularity;
	int clusterregularity;
	double signh;
	actionhandler ah;
	int ret[][];
	String names[];
	int max;
	int min;
	int length;
	int width;
	boolean vert;
	boolean hor;
	graph g;
	cluster c;
	Frame statwin;
	int totfrags;
	int fragmentedclusters;
	ButtonGroup bg;
	GridBagLayout gridBagLayout1;
	GridBagLayout gridBagLayout2;
	JLabel jLabel1;
	JLabel jLabel2;
	JLabel jLabel3;
	JLabel jLabel4;
	JLabel jLabel5;
	JSlider sc3;
	JSlider sc4;
	JSlider sc5;
	JSlider sc101;
	JSlider sc200;
	JSlider sc6;
	JLabel tf3;
	JLabel tf4;
	JLabel tf5;
	JLabel tf101;
	JLabel tf200;
	JLabel tf201;
	JLabel tf6;
	JCheckBox jCheckBox1;
	JCheckBox cb;
	JSlider sc2;
	JSlider sc1;
	JLabel jLabel11;
	JLabel jLabel12;
	JLabel tf1;
	JLabel tf2;
	JButton b;
	JButton b2;
	JSlider sc10;
	JSlider sc11;
	JLabel jLabel6;
	JLabel jLabel7;
	JLabel tf10;
	JLabel tf11;
	JPanel jPanel1;
	JPanel jPanel2;
	JRadioButton jRadioButton1;
	JRadioButton jRadioButton2;
	JRadioButton jRadioButton3;

	public tsg(cluster cluster1) {
		numclusters = 1;
		numobjects = 10;
		numvalues = 20;
		noisevectors = 50;
		noisedev = 10;
		devdecrease = 0;
		devvert = 10;
		span = 100;
		numreps = 1;
		noiseregularity = 0;
		clusterregularity = 5;
		signh = 1.0D;
		ah = new actionhandler(this);
		vert = true;
		hor = true;
		bg = new ButtonGroup();
		gridBagLayout1 = new GridBagLayout();
		gridBagLayout2 = new GridBagLayout();
		jLabel1 = new JLabel();
		jLabel2 = new JLabel();
		jLabel3 = new JLabel();
		jLabel4 = new JLabel();
		jLabel5 = new JLabel();
		sc3 = new JSlider(1, 200, 20);
		sc4 = new JSlider(0, 6000, 50);
		sc5 = new JSlider(0, 300, 10);
		sc101 = new JSlider(0, 300, 10);
		sc200 = new JSlider(1, 300, 1);
		sc6 = new JSlider(0, 3000, 1000);
		tf3 = new JLabel();
		tf4 = new JLabel();
		tf5 = new JLabel();
		tf101 = new JLabel();
		tf200 = new JLabel();
		tf201 = new JLabel();
		tf6 = new JLabel();
		jCheckBox1 = new JCheckBox();
		cb = new JCheckBox();
		sc2 = new JSlider(1, 1000, 10);
		sc1 = new JSlider(1, 1000, 1);
		jLabel11 = new JLabel();
		jLabel12 = new JLabel();
		tf1 = new JLabel();
		tf2 = new JLabel();
		b = new JButton();
		b2 = new JButton();
		sc10 = new JSlider(0, 4, 0);
		sc11 = new JSlider(0, 5, 5);
		jLabel6 = new JLabel();
		jLabel7 = new JLabel();
		tf10 = new JLabel();
		tf11 = new JLabel();
		jPanel1 = new JPanel();
		jPanel2 = new JPanel();
		jRadioButton1 = new JRadioButton("Single linkage");
		jRadioButton2 = new JRadioButton("Average linkage", true);
		jRadioButton3 = new JRadioButton("Complete linkage");
		jPanel2.setLayout(new GridLayout(3, 1));
		c = cluster1;
		jRadioButton1.setBackground(Color.lightGray);
		jRadioButton2.setBackground(Color.lightGray);
		jRadioButton3.setBackground(Color.lightGray);
		jRadioButton1.setForeground(new Color(90, 50, 150));
		jRadioButton2.setForeground(new Color(90, 50, 150));
		jRadioButton3.setForeground(new Color(90, 50, 150));
		b.setBackground(new Color(201, 201, 240));
		b2.setBackground(new Color(201, 201, 240));
		addWindowListener(ah);
		jPanel1.setBackground(Color.lightGray);
		jPanel1.setBorder(new EtchedBorder());
		jPanel2.setBackground(Color.lightGray);
		jPanel2.setBorder(new EtchedBorder());
		setBackground(Color.lightGray);
		jLabel1.setText("Number of values for each cluster");
		jLabel1.setBackground(Color.lightGray);
		jLabel2.setText("Noise vectors");
		jLabel2.setBackground(Color.lightGray);
		jLabel3.setText("Cluster deviation");
		jLabel3.setBackground(Color.lightGray);
		jLabel4.setText("Noise deviation");
		jLabel4.setBackground(Color.lightGray);
		jLabel5.setText("Value span");
		jLabel5.setBackground(Color.lightGray);
		tf200.setText("Number of repeats");
		tf200.setBackground(Color.lightGray);
		tf201.setText("1       ");
		tf201.setBackground(Color.lightGray);
		sc200.setBackground(Color.lightGray);
		sc3.setBackground(Color.lightGray);
		sc4.setBackground(Color.lightGray);
		sc5.setBackground(Color.lightGray);
		sc101.setBackground(Color.lightGray);
		sc6.setBackground(Color.lightGray);
		tf3.setText("1        ");
		tf3.setBackground(Color.lightGray);
		tf4.setText("0        ");
		tf4.setBackground(Color.lightGray);
		tf5.setText("10%         ");
		tf5.setBackground(Color.lightGray);
		tf101.setText("10%         ");
		tf101.setBackground(Color.lightGray);
		tf6.setText("10        ");
		tf6.setBackground(Color.lightGray);
		jLabel6.setText("Noise regularity");
		jLabel6.setBackground(Color.lightGray);
		jLabel7.setText("Cluster regularity");
		jLabel7.setBackground(Color.lightGray);
		tf10.setBackground(Color.lightGray);
		tf11.setBackground(Color.lightGray);
		jPanel1.setLayout(gridBagLayout2);
		jPanel2.setBackground(Color.lightGray);
		jCheckBox1.setText("jCheckBox1");
		jCheckBox1.setBackground(Color.lightGray);
		cb.setText("Show cluster base");
		cb.setBackground(Color.lightGray);
		sc2.setBackground(Color.lightGray);
		sc1.setBackground(Color.lightGray);
		jLabel11.setText("Number of clusters");
		jLabel11.setBackground(Color.lightGray);
		jLabel12.setText("Number of objects in cluster");
		jLabel12.setBackground(Color.lightGray);
		tf1.setText("1        ");
		tf1.setBackground(Color.lightGray);
		tf2.setText("1        ");
		tf2.setBackground(Color.lightGray);
		b.setText("Generate");
		b2.setText("Reset history");
		sc10.setBackground(Color.lightGray);
		sc11.setBackground(Color.lightGray);
		bg.add(jRadioButton1);
		bg.add(jRadioButton2);
		bg.add(jRadioButton3);
		jPanel2.add(jRadioButton1);
		jPanel2.add(jRadioButton2);
		jPanel2.add(jRadioButton3);
		setLayout(gridBagLayout1);
		add(jLabel1, new GridBagConstraints(0, 2, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
		add(jLabel2, new GridBagConstraints(0, 3, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
		add(jLabel3, new GridBagConstraints(0, 4, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
		add(jLabel4, new GridBagConstraints(0, 5, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
		add(jLabel5, new GridBagConstraints(0, 6, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
		add(sc3, new GridBagConstraints(1, 2, 1, 1, 0.0D, 0.0D, 17, 2, new Insets(0, 0, 0, 0), 0, 0));
		add(sc4, new GridBagConstraints(1, 3, 1, 1, 0.0D, 0.0D, 17, 1, new Insets(0, 0, 0, 0), 0, 0));
		add(sc5, new GridBagConstraints(1, 4, 1, 1, 0.0D, 0.0D, 17, 2, new Insets(0, 0, 0, 0), 0, 0));
		add(sc101, new GridBagConstraints(1, 5, 1, 1, 0.0D, 0.0D, 17, 2, new Insets(0, 0, 0, 0), 0, 0));
		add(sc6, new GridBagConstraints(1, 6, 1, 1, 0.0D, 0.0D, 17, 2, new Insets(0, 0, 0, 0), 0, 0));
		add(tf3, new GridBagConstraints(2, 2, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 8, 0, 0), 0, 0));
		add(tf4, new GridBagConstraints(2, 3, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 8, 0, 0), 0, 0));
		add(tf5, new GridBagConstraints(2, 4, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 8, 0, 0), 0, 0));
		add(tf101, new GridBagConstraints(2, 5, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 8, 0, 0), 0, 0));
		add(tf6, new GridBagConstraints(2, 6, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 8, 0, 0), 0, 0));
		add(sc2, new GridBagConstraints(1, 1, 1, 1, 0.0D, 0.0D, 17, 2, new Insets(0, 0, 0, 0), 0, 0));
		add(sc1, new GridBagConstraints(1, 0, 1, 1, 0.0D, 0.0D, 17, 2, new Insets(0, 0, 0, 0), 0, 0));
		add(jLabel11, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
		add(jLabel12, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
		add(tf1, new GridBagConstraints(2, 0, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 8, 0, 0), 0, 0));
		add(tf2, new GridBagConstraints(2, 1, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 8, 0, 0), 0, 0));
		add(b, new GridBagConstraints(1, 11, 1, 1, 0.0D, 0.0D, 13, 1, new Insets(5, 4, 13, 3), 0, 0));
		add(b2, new GridBagConstraints(2, 11, 1, 1, 0.0D, 0.0D, 13, 1, new Insets(5, 4, 13, 3), 0, 0));
		add(sc10, new GridBagConstraints(1, 7, 1, 1, 0.0D, 0.0D, 17, 1, new Insets(0, 0, 0, 0), 0, 0));
		add(sc11, new GridBagConstraints(1, 8, 1, 1, 0.0D, 0.0D, 17, 1, new Insets(0, 0, 0, 0), 0, 0));
		add(jLabel6, new GridBagConstraints(0, 7, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
		add(jLabel7, new GridBagConstraints(0, 8, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
		add(tf10, new GridBagConstraints(2, 7, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 8, 0, 1), 0, 0));
		add(tf11, new GridBagConstraints(2, 8, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 8, 0, 0), 0, 0));
		add(tf200, new GridBagConstraints(0, 9, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
		add(sc200, new GridBagConstraints(1, 9, 1, 1, 0.0D, 0.0D, 17, 1, new Insets(0, 0, 0, 0), 0, 0));
		add(tf201, new GridBagConstraints(2, 9, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 8, 0, 0), 0, 0));
		add(jPanel1, new GridBagConstraints(1, 10, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(5, 4, 13, 3), 63, 10));
		jPanel1.add(cb, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 0, 0), 0, 1));
		add(jPanel2, new GridBagConstraints(0, 10, 1, 2, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 13, 0), 0, 0));
		b.setActionCommand("simulate");
		b2.setActionCommand("reset");
		sc1.addChangeListener(this);
		sc2.addChangeListener(this);
		sc3.addChangeListener(this);
		sc4.addChangeListener(this);
		sc5.addChangeListener(this);
		sc6.addChangeListener(this);
		sc10.addChangeListener(this);
		sc101.addChangeListener(this);
		sc11.addChangeListener(this);
		sc200.addChangeListener(this);
		tf1.setText(String.valueOf(numclusters) + "             ");
		tf2.setText(String.valueOf(numobjects) + "             ");
		tf3.setText(String.valueOf(numvalues) + "             ");
		tf6.setText(String.valueOf(span) + "             ");
		tf4.setText(String.valueOf(noisevectors) + "             ");
		tf10.setText("random                          ");
		tf11.setText("Sine                          ");
		pack();
		setVisible(true);
	}

	public int[] crap(int ai[], int i) {
		int ai1[] = new int[ai.length];
		for (int j = 0; j < ai.length; j++) {
			double d = 0.0D;
			double d1 = ((double)span * (double)i) / 100D;
			d = d1 * (Math.random() * 2D - 1.0D);
			ai1[j] = ai[j] - (int)d;
		}

		return ai1;
	}

	public int dist(int i, int j) {
		boolean flag = false;
		if (i < j) {
			int k = i;
			i = j;
			j = k;
		}
		return i - j;
	}

	public int[][] generate2() {
		names = new String[numobjects * numclusters + noisevectors + 1];
		int ai2[][] = new int[numobjects * numclusters][numvalues];
		int i = (int)((double)numvalues / 4D);
		int j = (int)(((double)numvalues / 4D) * 2D);
		int k = (int)(((double)numvalues / 4D) * 3D);
		int l = (int)(((double)numvalues / 4D) * 4D);
		int i1 = 0;
		for (int j1 = 0; j1 < numclusters; j1++) {
			int k1 = ran();
			int l1 = ran();
			int i2 = ran();
			int j2 = ran();
			int ai[][] = new int[1][numvalues];
			ai[0] = regulate(clusterregularity, 0);
			if (cb.isSelected())
				new graph(ai, "Cluster profile", c);
			int ai1[] = ai[0];
			for (int k2 = 0; k2 < numobjects; k2++) {
				names[i1] = "cluster " + j1;
				ai2[i1] = crap(ai1, devvert);
				i1++;
			}

		}

		ai2 = noise(ai2, noisevectors);
		setproperties(ai2);
		return ai2;
	}

	public int imid(double d, double d1) {
		double d2 = 0.0D;
		double d3 = signh + (double)noiseregularity;
		if (d3 != 0.0D) {
			d2 = d * signh + d1 * (double)noiseregularity;
			d2 /= d3;
		} else {
			d2 = ran();
		}
		return intval(d2);
	}

	public int[] interpolate(int i, int j, int k, int l, int i1) {
		boolean flag = false;
		if (i > j) {
			int j1 = i;
			i = j;
			j = j1;
			j1 = k;
			k = l;
			l = j1;
		}
		double d = i;
		double d1 = j;
		double d2 = k;
		double d3 = l;
		int ai[] = new int[i1];
		double d4 = 0.0D;
		for (int k1 = 0; k1 < i1; k1++) {
			double d5 = (((double)k1 - d1) / (d - d1)) * d2;
			d5 += (((double)k1 - d) / (d1 - d)) * d3;
			ai[k1] = (int)d5;
		}

		return ai;
	}

	public int[] interpolate(int i, int j, int k, int l, int i1, int j1, int k1) {
		boolean flag = false;
		if (i > j) {
			int l1 = i;
			i = j;
			j = l1;
			l1 = l;
			l = i1;
			i1 = l1;
		}
		if (i > k) {
			int i2 = i;
			i = k;
			k = i2;
			i2 = l;
			l = j1;
			j1 = i2;
		}
		if (j > k) {
			int j2 = j;
			j = k;
			k = j2;
			j2 = i1;
			i1 = j1;
			j1 = j2;
		}
		double d = i;
		double d1 = j;
		double d2 = k;
		double d3 = l;
		double d4 = i1;
		double d5 = j1;
		int ai[] = new int[k1];
		double d6 = 0.0D;
		for (int k2 = 0; k2 < k1; k2++) {
			double d7 = ((((double)k2 - d1) * ((double)k2 - d2)) / ((d - d1) * (d - d2))) * d3;
			d7 += ((((double)k2 - d) * ((double)k2 - d2)) / ((d1 - d) * (d1 - d2))) * d4;
			d7 += ((((double)k2 - d) * ((double)k2 - d1)) / ((d2 - d) * (d2 - d1))) * d5;
			ai[k2] = (int)d7;
		}

		return ai;
	}

	public int[] interpolate(int i, int j, int k, int l, int i1, int j1, int k1, 
			int l1, int i2) {
		int ai[][] = sort(i, j, k, l, i1, j1, k1, l1);
		int ai1[] = new int[i2];
		double d = 0.0D;
		double d2 = ai[0][0];
		double d3 = ai[1][0];
		double d4 = ai[2][0];
		double d5 = ai[3][0];
		double d6 = ai[0][1];
		double d7 = ai[1][1];
		double d8 = ai[2][1];
		double d9 = ai[3][1];
		for (int j2 = 0; j2 < i2; j2++) {
			double d1 = ((((double)j2 - d3) * ((double)j2 - d4) * ((double)j2 - d5)) / ((d2 - d3) * (d2 - d4) * (d2 - d5))) * d6;
			d1 += ((((double)j2 - d2) * ((double)j2 - d4) * ((double)j2 - d5)) / ((d3 - d2) * (d3 - d4) * (d3 - d5))) * d7;
			d1 += ((((double)j2 - d2) * ((double)j2 - d3) * ((double)j2 - d5)) / ((d4 - d2) * (d4 - d3) * (d4 - d5))) * d8;
			d1 += ((((double)j2 - d2) * ((double)j2 - d3) * ((double)j2 - d4)) / ((d5 - d2) * (d5 - d3) * (d5 - d4))) * d9;
			ai1[j2] = (int)d1;
		}

		return ai1;
	}

	public int intval(double d) {
		Long long1 = new Long(Math.round(d));
		return long1.intValue();
	}

	public static void main(String args[]) {
		tsg tsg1 = new tsg(new cluster());
	}

	public int[][] noise(int ai[][], int i) {
		int j = 0;
		int ai1[][] = new int[ai.length + i][ai[0].length];
		for (int k = 0; k < ai.length; k++) {
			for (int l = 0; l < ai[0].length; l++)
				ai1[j][l] = ai[k][l];

			j++;
		}

		for (int i1 = 0; i1 < i; i1++)
			switch (noiseregularity) {
			default:
				break;

			case 0: // '\0'
				for (int j1 = 0; j1 < ai[0].length; j1++)
					ai1[j][j1] = ran();

				names[j] = "Random noise";
				j++;
				break;

			case 1: // '\001'
				ai1[j] = randomregulate(1);
				names[j] = "Random reg. noise ";
				j++;
				break;

			case 2: // '\002'
				int k1 = ran();
				int l1 = ran();
				int i2 = 0;
				int j2 = numvalues;
				ai1[j] = crap(interpolate(i2, j2, k1, l1, numvalues), noisedev);
				names[j] = "Linear noise";
				j++;
				break;

			case 3: // '\003'
				int k2 = ran();
				int l2 = ran();
				int i3 = ran();
				int j3 = (int)((double)numvalues / 3D);
				int k3 = (int)(((double)numvalues / 3D) * 2D);
				int l3 = (int)(((double)numvalues / 3D) * 3D);
				ai1[j] = crap(interpolate(j3, k3, l3, k2, l2, i3, numvalues), noisedev);
				names[j] = "n^2 noise";
				j++;
				break;

			case 4: // '\004'
				int i4 = ran();
				int j4 = ran();
				int k4 = ran();
				int l4 = ran();
				int i5 = (int)((double)numvalues / 4D);
				int j5 = (int)(((double)numvalues / 4D) * 2D);
				int k5 = (int)(((double)numvalues / 4D) * 3D);
				int l5 = (int)(((double)numvalues / 4D) * 4D);
				ai1[j] = crap(interpolate(i5, j5, k5, l5, i4, j4, k4, l4, numvalues), noisedev);
				names[j] = "n^3 noise";
				j++;
				break;
			}

		return ai1;
	}

	public int numfrags(int ai[]) {
		int i = 0;
		for (int j = 1; j < ai.length; j++)
			if (ai[j] != ai[j - 1] + 1)
				i++;

		return i;
	}

	public int ran() {
		return (int)((Math.random() * 2D - 1.0D) * (double)span);
	}

	public int[] randomregulate(int i) {
		int ai[] = new int[numvalues];
		int j = (int)(Math.random() * 4.9900000000000002D);
		ai = regulate(j, i);
		return ai;
	}

	public int[] regulate(int i, int j) {
		int ai[] = new int[numvalues];
		switch (i) {
		case 0: // '\0'
			for (int k = 0; k < numvalues; k++)
				ai[k] = ran();

			break;

		case 1: // '\001'
			ai = randomregulate(j);
			break;

		case 2: // '\002'
			int l = ran();
			int i1 = ran();
			int j1 = 0;
			int k1 = numvalues;
			if (j == 1)
				ai = crap(interpolate(j1, k1, l, i1, numvalues), noisedev);
			else
				ai = interpolate(j1, k1, l, i1, numvalues);
			break;

		case 3: // '\003'
			int l1 = ran();
			int i2 = ran();
			int j2 = ran();
			int k2 = (int)((double)numvalues / 3D);
			int l2 = (int)(((double)numvalues / 3D) * 2D);
			int i3 = (int)(((double)numvalues / 3D) * 3D);
			if (j == 1)
				ai = crap(interpolate(k2, l2, i3, l1, i2, j2, numvalues), noisedev);
			else
				ai = interpolate(k2, l2, i3, l1, i2, j2, numvalues);
			break;

		case 4: // '\004'
			int j3 = ran();
			int k3 = ran();
			int l3 = ran();
			int i4 = ran();
			int j4 = (int)((double)numvalues / 4D);
			int k4 = (int)(((double)numvalues / 4D) * 2D);
			int l4 = (int)(((double)numvalues / 4D) * 3D);
			int i5 = (int)(((double)numvalues / 4D) * 4D);
			if (j == 1)
				ai = crap(interpolate(j4, k4, l4, i5, j3, k3, l3, i4, numvalues), noisedev);
			else
				ai = interpolate(j4, k4, l4, i5, j3, k3, l3, i4, numvalues);
			break;

		case 5: // '\005'
			double d = Math.random();
			double d1 = Math.random() * 2D;
			if (j == 1)
				ai = crap(sincurv(d, d1, numvalues), noisedev);
			else
				ai = sincurv(d, d1, numvalues);
			break;

		default:
			System.out.print(i + " ");
			break;
		}
		return ai;
	}

	public void score() {
		totfrags = 0;
		fragmentedclusters = 0;
		TextArea textarea = new TextArea(6, 30);
		boolean flag = false;
		if (statwin != null)
			statwin.dispose();
		statwin = new Frame("Resu  lts");
		statwin.add("Center", textarea);
		textarea.append("Clusters :" + numclusters);
		for (int j = 0; j < numclusters; j++) {
			c.wherepos = null;
			c.findfragments(c.root, "cluster " + j);
			int i = numfrags(c.wherepos);
			if (i != 0)
				fragmentedclusters++;
			textarea.append("Cluster " + j + ":\n");
			textarea.append("Fragments: " + String.valueOf(i) + "\n\n");
			totfrags = totfrags + i;
		}

	}

	public void setproperties(int ai[][]) {
		min = max = 0;
		length = ai.length;
		width = ai[0].length;
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				if (min > ai[i][j])
					min = ai[i][j];
				if (max < ai[i][j])
					max = ai[i][j];
			}

		}

	}

	public int[] sincurv(double d, double d1, int i) {
		boolean flag = false;
		int ai[] = new int[i];
		double d2 = 0.0D;
		for (int j = 0; j < i; j++) {
			double d3 = Math.sin((double)j / (d1 * 5D) + d * 10D) * (double)span;
			ai[j] = (int)d3;
		}

		return ai;
	}

	public int[][] sort(int i, int j, int k, int l, int i1, int j1, int k1, 
			int l1) {
		int ai[][] = new int[4][2];
		int ai1[][] = new int[4][2];
		ai[0][0] = i;
		ai[1][0] = j;
		ai[2][0] = k;
		ai[3][0] = l;
		ai[0][1] = i1;
		ai[1][1] = j1;
		ai[2][1] = k1;
		ai[3][1] = l1;
		int i2 = 0;
		int j2 = 0x1869f;
		int l2 = 0;
		for (int i3 = 0; i3 < 4; i3++) {
			int k2 = 0x1869f;
			for (int j3 = 0; j3 < 4; j3++)
				if (k2 > ai[j3][0]) {
					k2 = ai[j3][0];
					i2 = ai[j3][1];
					l2 = j3;
				}

			ai1[l2][0] = k2;
			ai1[l2][1] = i2;
			ai[l2][0] = 0x1869f;
		}

		return ai1;
	}

	public void stateChanged(ChangeEvent changeevent) {
		numclusters = sc1.getValue();
		numobjects = sc2.getValue();
		numvalues = sc3.getValue();
		noisevectors = sc4.getValue();
		devvert = sc5.getValue();
		span = sc6.getValue();
		noisedev = sc101.getValue();
		noiseregularity = sc10.getValue();
		clusterregularity = sc11.getValue();
		numreps = sc200.getValue();
		tf1.setText(String.valueOf(numclusters));
		tf2.setText(String.valueOf(numobjects));
		tf3.setText(String.valueOf(numvalues));
		tf6.setText(String.valueOf(span));
		tf4.setText(String.valueOf(noisevectors));
		tf5.setText(String.valueOf(devvert) + "%");
		tf101.setText(String.valueOf(noisedev) + "%");
		tf201.setText(String.valueOf(numreps));
		switch (clusterregularity) {
		case 0: // '\0'
			tf11.setText("random           ");
			break;

		case 1: // '\001'
			tf11.setText("random regularity");
			break;

		case 2: // '\002'
			tf11.setText("Linear");
			break;

		case 3: // '\003'
			tf11.setText("n^2");
			break;

		case 4: // '\004'
			tf11.setText("n^3");
			break;

		case 5: // '\005'
			tf11.setText("Sine");
			break;
		}
		switch (noiseregularity) {
		case 0: // '\0'
			tf10.setText("random           ");
			break;

		case 1: // '\001'
			tf10.setText("random regularity");
			break;

		case 2: // '\002'
			tf10.setText("Linear");
			break;

		case 3: // '\003'
			tf10.setText("n^2");
			break;

		case 4: // '\004'
			tf10.setText("n^3");
			break;
		}
		vert = hor = true;
	}
}
