import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class textwriter extends Frame
	implements ActionListener {

	tools t;
	double matrix[][];
	double xmax;
	double ymin;
	double ymax;
	gen trunk;
	gen maintrunk;
	int matrixheight;
	int matrixindex;
	String names[];
	String selectednames[];
	int namecounter;
	FileOutputStream output;
	BufferedOutputStream buffer;
	DataOutputStream dataout;
	actionhandler ah;
	Label status;
	Checkbox cb1;
	Checkbox cb2;

	public textwriter(gen gen1, gen gen2, Label label, String as[]) {
		t = new tools();
		ah = new actionhandler(this);
		addWindowListener(ah);
		setLayout(new GridLayout(4, 1));
		setTitle("Cluster");
		CheckboxGroup checkboxgroup = new CheckboxGroup();
		cb1 = new Checkbox("Complete set", checkboxgroup, true);
		cb2 = new Checkbox("Selected cluster", checkboxgroup, false);
		add(new Label("Save textrepresentation for:"));
		add(cb1);
		add(cb2);
		Button button = new Button("OK");
		button.addActionListener(this);
		add(button);
		status = label;
		names = as;
		trunk = gen1;
		maintrunk = gen2;
		setBackground(Color.lightGray);
		pack();
		show();
	}

	public void actionPerformed(ActionEvent actionevent) {
		if (cb1.getState())
			write(maintrunk);
		else
			write(trunk);
		dispose();
	}

	public int intvalue(String s) {
		Integer integer = new Integer(s);
		return integer.intValue();
	}

	public static void main(String args[]) {
	}

	public void write(gen gen1) {
		if (gen1 == null) {
			status.setText("There are no cluster to save");
		} else {
			double d = ymax - ymin;
			double d1 = 3D;
			FileDialog filedialog = new FileDialog(new Frame("Enter filename"));
			filedialog.setFile("textrep.txt");
			filedialog.setMode(1);
			filedialog.show();
			File file = new File(filedialog.getDirectory() + filedialog.getFile());
			try {
				FileOutputStream fileoutputstream = new FileOutputStream(file);
				BufferedOutputStream bufferedoutputstream = new BufferedOutputStream(fileoutputstream);
				DataOutputStream dataoutputstream = new DataOutputStream(bufferedoutputstream);
				dataoutputstream = new DataOutputStream(fileoutputstream);
				writegene(gen1, dataoutputstream);
				bufferedoutputstream.flush();
				fileoutputstream.close();
				status.setText("done saving");
			}
			catch (IOException _ex) { }
		}
	}

	public void writegene(gen gen1, DataOutputStream dataoutputstream) {
		try {
			if (gen1.merged) {
				dataoutputstream.writeBytes("(");
				writegene(gen1.right, dataoutputstream);
				dataoutputstream.writeBytes(",");
				writegene(gen1.left, dataoutputstream);
				dataoutputstream.writeBytes(")");
			} else {
				dataoutputstream.writeBytes(names[intvalue(gen1.name)]);
			}
		}
		catch (IOException _ex) {
			t.println("outfeil");
		}
	}
}
