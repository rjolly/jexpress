import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class prof extends Frame
	implements ActionListener, ChangeListener {

	int slider1;
	JSlider sc1;
	JSlider sc2;
	JSlider sc3;
	JSlider sc4;
	JSlider sc5;
	JSlider sc6;
	GridLayout gridLayout1;
	Label label1;
	Label label2;
	Label label3;
	Label label4;
	Label label5;
	Label label6;
	TextField textField1;
	TextField textField2;
	TextField textField3;
	TextField textField4;
	TextField textField5;
	TextField textField6;
	GridLayout gridLayout2;
	JButton button1;
	JButton button2;
	JButton button3;
	JButton button4;
	JButton button5;
	BorderLayout borderLayout1;
	profiler p;
	cluster cl;

	public prof(int ai[][], String as[], cluster cluster) {
		sc1 = new JSlider(-500, 500, 0);
		sc2 = new JSlider(-500, 500, 0);
		sc3 = new JSlider(-50, 50, 0);
		sc4 = new JSlider(1, 200, 20);
		sc5 = new JSlider(1, 200, 20);
		sc6 = new JSlider(1, 200, 20);
		gridLayout1 = new GridLayout();
		label1 = new Label();
		label2 = new Label();
		label3 = new Label();
		label4 = new Label();
		label5 = new Label();
		label6 = new Label();
		textField1 = new TextField();
		textField2 = new TextField();
		textField3 = new TextField();
		textField4 = new TextField();
		textField5 = new TextField();
		textField6 = new TextField();
		gridLayout2 = new GridLayout();
		button1 = new JButton("Search");
		button2 = new JButton("Load Profile");
		button3 = new JButton("Save Profile");
		button4 = new JButton("New Profile");
		button5 = new JButton("Close");
		borderLayout1 = new BorderLayout();
		cl = cluster;
		double ad[][] = dubval(ai);
		p = new profiler(ad, as, cluster);
		actionhandler actionhandler1 = new actionhandler(this);
		addWindowListener(actionhandler1);
		jbInit();
		pack();
		setVisible(true);
		setTitle("Profiler");
		setResizable(false);
	}

	public void actionPerformed(ActionEvent actionevent) {
		if (actionevent.getActionCommand() == "Search")
			p.getthem();
		else
		if (actionevent.getActionCommand() == "Save Profile")
			p.write();
		else
		if (actionevent.getActionCommand() == "Load Profile")
			p.read();
		else
		if (actionevent.getActionCommand() == "New Profile")
			p.newprof();
	}

	public double[][] dubval(int ai[][]) {
		double ad[][] = new double[ai.length][ai[0].length];
		for (int i = 0; i < ai.length; i++) {
			for (int j = 0; j < ai[0].length; j++)
				ad[i][j] = ai[i][j];

		}

		return ad;
	}

	private void jbInit() {
		JPanel jpanel = new JPanel(new GridLayout(2, 4));
		JPanel jpanel1 = new JPanel(new BorderLayout());
		JPanel jpanel2 = new JPanel(new GridLayout(10, 1));
		jpanel.setBorder(new EtchedBorder());
		button1.setBackground(new Color(201, 201, 240));
		button2.setBackground(new Color(201, 201, 240));
		button3.setBackground(new Color(201, 201, 240));
		button4.setBackground(new Color(201, 201, 240));
		button5.setBackground(new Color(201, 201, 240));
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		jpanel.add(button1);
		jpanel.add(button2);
		jpanel.add(button3);
		jpanel.add(button4);
		sc1.addChangeListener(this);
		sc2.addChangeListener(this);
		sc3.addChangeListener(this);
		sc4.addChangeListener(this);
		sc5.addChangeListener(this);
		jpanel2.add(new Label("Upper"));
		jpanel2.add(sc1);
		jpanel2.add(new Label("Lower"));
		jpanel2.add(sc2);
		jpanel2.add(new Label("Cycle"));
		jpanel2.add(sc3);
		jpanel1.add("Center", p);
		jpanel1.add("West", jpanel2);
		add("North", jpanel1);
		add("Center", jpanel);
	}

	public void stateChanged(ChangeEvent changeevent) {
		p.addupp(sc1.getValue());
		p.addlo(sc2.getValue());
		p.cycle(sc3.getValue());
	}
}
