import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;

public class somframe extends Frame
	implements ComponentListener, ActionListener {

	int height;
	int width;
	somgraph gr[][];
	actionhandler ah;
	Panel mainpanel;
	Panel buttonpanel;
	JButton jb1;
	JButton jb2;

	public somframe(somgraph asomgraph[][], TextField textfield, Button button, vispca vispca) {
		height = 220;
		width = 200;
		ah = new actionhandler(this);
		jb1 = new JButton("Complete");
		jb2 = new JButton("Save");
		mainpanel = new Panel(new GridLayout(asomgraph.length, asomgraph[0].length));
		buttonpanel = new Panel(new GridLayout(1, 1));
		if (vispca != null)
			setTitle("SOM neuron graphs");
		else
			setTitle("K-Means graphs");
		jb1.setBackground(new Color(201, 201, 240));
		jb2.setBackground(new Color(201, 201, 240));
		buttonpanel.add(jb1);
		addWindowListener(ah);
		jb1.addActionListener(this);
		jb2.addActionListener(this);
		gr = asomgraph;
		setBackground(new Color(140, 135, 135));
		for (int i = 0; i < asomgraph.length; i++) {
			for (int j = 0; j < asomgraph[0].length; j++)
				if (asomgraph[i][j] != null) {
					asomgraph[i][j].setsz(width / asomgraph.length, height / asomgraph.length);
					asomgraph[i][j].xinframe = i;
					asomgraph[i][j].yinframe = j;
					mainpanel.add(asomgraph[i][j]);
				}

		}

		add("Center", mainpanel);
		add("South", buttonpanel);
		setSize(width, height);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent actionevent) {
		if (actionevent.getActionCommand() == "Complete") {
			jb1.setLabel("Profile");
			for (int i = 0; i < gr.length; i++) {
				for (int k = 0; k < gr[0].length; k++)
					if (gr[i][k] != null) {
						gr[i][k].justmean = false;
						gr[i][k].repaint();
					}

			}

		}
		if (actionevent.getActionCommand() == "Profile") {
			jb1.setLabel("Complete");
			for (int j = 0; j < gr.length; j++) {
				for (int l = 0; l < gr[0].length; l++)
					if (gr[j][l] != null) {
						gr[j][l].justmean = true;
						gr[j][l].repaint();
					}

			}

		}
	}

	public void componentHidden(ComponentEvent componentevent) {
	}

	public void componentMoved(ComponentEvent componentevent) {
	}

	public void componentResized(ComponentEvent componentevent) {
		Dimension dimension = getSize();
		height = dimension.height;
		width = dimension.width;
		for (int i = 0; i < gr.length; i++) {
			for (int j = 0; j < gr[0].length; j++)
				if (gr[i][j] != null) {
					gr[i][j].setsz(width / gr.length, height / gr.length);
					gr[i][j].repaint();
					add(gr[i][j]);
				}

		}

		repaint();
	}

	public void componentShown(ComponentEvent componentevent) {
	}

	public void regraph(somgraph asomgraph[][]) {
		for (int i = 0; i < gr.length; i++) {
			for (int j = 0; j < gr[0].length; j++)
				if (gr[i][j] != null)
					mainpanel.remove(gr[i][j]);

		}

		gr = asomgraph;
		for (int k = 0; k < gr.length; k++) {
			for (int l = 0; l < gr[0].length; l++)
				if (gr[k][l] != null) {
					gr[k][l].setsz(width / gr.length, height / gr.length);
					mainpanel.add(gr[k][l]);
				}

		}

		add("Center", mainpanel);
		add("South", buttonpanel);
		setSize(width, height);
		doLayout();
		repaint();
	}
}
