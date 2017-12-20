import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Point;
import java.awt.Window;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;

public class clusterselection extends Frame {

	int mode;
	Checkbox cb1;
	Checkbox cb2;
	Checkbox cb3;
	JButton jb;
	cluster cl;

	public clusterselection(cluster cluster) {
		jb = new JButton("OK");
		cl = cluster;
		setLocation(cluster.getLocation().x + 200, cluster.getLocation().y + 250);
		setBackground(Color.lightGray);
		setLayout(new GridLayout(5, 1));
		setTitle("Cluster");
		CheckboxGroup checkboxgroup = new CheckboxGroup();
		cb1 = new Checkbox("Single linkage", checkboxgroup, true);
		cb2 = new Checkbox("Average linkage", checkboxgroup, false);
		cb3 = new Checkbox("Complete linkage", checkboxgroup, false);
		add(new Label("Select method"));
		add(cb1);
		add(cb2);
		add(cb3);
		jb.setActionCommand("clusterok");
		jb.setBackground(new Color(201, 201, 240));
		add(jb);
		pack();
	}
}
