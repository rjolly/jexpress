import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Label;
import java.awt.Point;
import java.awt.TextComponent;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.Window;
import javax.swing.JButton;
import javax.swing.JComponent;

public class genefinder extends Frame {

	TextField tf;
	JButton jb1;
	actionhandler ah;
	Image magn;

	public genefinder(cluster cluster) {
		tf = new TextField(15);
		jb1 = new JButton("Find");
		ah = new actionhandler(this);
		jb1.setBackground(new Color(201, 201, 240));
		setTitle("Gene finder");
		setBackground(Color.lightGray);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		magn = toolkit.getImage(getClass().getResource("find.gif"));
		setIconImage(toolkit.getImage(getClass().getResource("magn.gif")));
		setLocation(cluster.getLocation().x + 100, cluster.getLocation().y + 450);
		addWindowListener(ah);
		setLayout(new FlowLayout());
		setSize(160, 120);
		add(new Label("Name or part of name:"), "North");
		add(tf, "Center");
		add(jb1, "South");
		repaint();
	}

	public String getname() {
		return tf.getText();
	}

	public static void main(String args[]) {
	}
}
