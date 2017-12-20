import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextComponent;
import java.awt.TextField;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;

public class kmeans extends Frame
	implements ActionListener {

	Label l;
	int step;
	somframe sf;
	cluster cl;
	String names[];
	String selectednames[][];
	int input[][];
	Frame f;
	TextField tf;

	public kmeans(int ai[][], cluster cluster, String as[]) {
		l = new Label("test");
		step = 0;
		f = new Frame("K-Means");
		tf = new TextField("9");
		cl = cluster;
		names = as;
		input = ai;
		Label label = new Label("Enter number of clusters");
		JButton jbutton = new JButton("OK");
		JButton jbutton1 = new JButton("Cancel");
		Panel panel = new Panel(new GridLayout(1, 2));
		f.setLayout(new BorderLayout());
		f.add("North", label);
		f.add("Center", tf);
		jbutton.addActionListener(this);
		jbutton1.addActionListener(this);
		jbutton.setBackground(new Color(201, 201, 240));
		jbutton1.setBackground(new Color(201, 201, 240));
		panel.add(jbutton);
		panel.add(jbutton1);
		f.add("South", panel);
		f.pack();
		f.setBackground(Color.lightGray);
		panel.setBackground(Color.lightGray);
		label.setBackground(Color.lightGray);
		f.setVisible(true);
	}

	public void actionPerformed(ActionEvent actionevent) {
		int i = 0;
		if (actionevent.getActionCommand() == "OK") {
			try {
				i = intvalue(tf.getText());
			}
			catch (Exception _ex) { }
			if (i > 0) {
				kmeanit(input, i, cl, names);
				f.dispose();
			}
		} else {
			f.dispose();
			dispose();
		}
	}

	public void display(int ai[][][]) {
		somgraph asomgraph[][] = new somgraph[(int)Math.ceil(Math.sqrt(ai.length))][(int)Math.ceil(Math.sqrt(ai.length))];
		int ai1[][] = new int[1][ai[0][0].length];
		for (int i = 0; i < (int)Math.ceil(Math.sqrt(ai.length)); i++) {
			for (int j = 0; j < (int)Math.ceil(Math.sqrt(ai.length)); j++)
				try {
					asomgraph[i][j] = new somgraph(ai[i + j * (int)Math.ceil(Math.sqrt(ai.length))], "K-Means", cl, selectednames[i + j * (int)Math.ceil(Math.sqrt(ai.length))]);
				}
				catch (Exception _ex) {
					asomgraph[i][j] = null;
				}

		}

		sf = new somframe(asomgraph, new TextField(""), new Button(""), null);
	}

	public double dist(double ad[], int ai[]) {
		double d = 0.0D;
		double d1 = 0.0D;
		double d2 = 0.0D;
		for (int i = 0; i < ad.length; i++) {
			double d3 = ad[i] - (double)ai[i];
			d1 += d3 * d3;
		}

		d = Math.sqrt(d1);
		return d;
	}

	public int intvalue(String s) {
		Integer integer = new Integer(s);
		return integer.intValue();
	}

	public void iterate(double ad[][], int ai[][], int ai1[]) {
		int i = -1;
		boolean flag = false;
		double d = 999999D;
		boolean flag1 = false;
		boolean flag2 = true;
		for (int k = 0; k < ai.length; k++) {
			double d1 = 9999999D;
			for (int i1 = 0; i1 < ad.length; i1++)
				if (dist(ad[i1], ai[k]) < d1) {
					d1 = dist(ad[i1], ai[k]);
					i = i1;
				}

			ai1[k] = i;
		}

		flag = true;
		for (int j1 = 0; j1 < ad.length; j1++) {
			int j = 0;
			double ad1[] = new double[ad[0].length];
			for (int k1 = 0; k1 < ai1.length; k1++)
				if (ai1[k1] == j1) {
					j++;
					for (int l1 = 0; l1 < ad[j1].length; l1++)
						ad1[l1] = ad1[l1] + (double)ai[k1][l1];

				}

			for (int i2 = 0; i2 < ad[j1].length; i2++)
				if (j > 0)
					ad1[i2] = ad1[i2] / (double)j;

			for (int j2 = 0; j2 < ad[j1].length; j2++)
				if (ad[j1][j2] != ad1[j2]) {
					flag = false;
					ad[j1][j2] = ad1[j2];
				}

		}

		if (step > 10) {
			boolean flag3 = false;
			flag = true;
		}
		if (!flag) {
			step++;
			iterate(ad, ai, ai1);
		}
	}

	public void kmeanit(int ai[][], int i, cluster cluster, String as[]) {
		boolean flag = false;
		int ai2[] = new int[ai.length];
		double ad[][] = new double[i][ai[0].length];
		for (int k = 0; k < ai2.length; k++)
			ai2[k] = -1;

		for (int i1 = 0; i1 < i; i1++) {
			int j = (int)(Math.random() * (double)ai.length);
			for (int j1 = 0; j1 < ai[0].length; j1++)
				ad[i1][j1] = ai[j][j1];

		}

		iterate(ad, ai, ai2);
		int ai1[][][] = sweep(ai, ai2, i);
		display(ai1);
	}

	public static void main(String args[]) {
	}

	public int[][][] sweep(int ai[][], int ai1[], int i) {
		boolean flag = false;
		boolean flag1 = false;
		int ai2[][][] = new int[i][][];
		selectednames = new String[i][];
		for (int i1 = 0; i1 < i; i1++) {
			int j = 0;
			for (int j1 = 0; j1 < ai1.length; j1++)
				if (ai1[j1] == i1)
					j++;

			ai2[i1] = new int[j][ai[0].length];
			selectednames[i1] = new String[j];
		}

		for (int k1 = 0; k1 < ai2.length; k1++) {
			int k = 0;
			for (int l1 = 0; l1 < ai1.length; l1++)
				if (ai1[l1] == k1) {
					ai2[k1][k] = ai[l1];
					selectednames[k1][k] = names[l1];
					k++;
				}

		}

		return ai2;
	}
}
