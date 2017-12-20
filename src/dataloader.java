import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Point;
import java.awt.Scrollbar;
import java.awt.TextComponent;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class dataloader extends Frame
	implements ActionListener, MouseListener, AdjustmentListener {

	Label status;
	int stat;
	int mode;
	int mouseclickx;
	int mouseclicky;
	int horslide;
	int vertslide;
	tools t;
	File f;
	Checkbox cb1;
	Checkbox cb2;
	Checkbox nullstoav;
	String screen[][];
	String screen2[][];
	int minvalue;
	int maxvalue;
	int clusters;
	int lengde;
	int bredde;
	int breddefraextend;
	String clusvector[];
	boolean dataloaded;
	Scrollbar rangerup;
	Scrollbar rangerhor;
	int returnrow;
	int returncol;
	int endrow;
	int endcol;
	int namecol;
	int delim;
	Vector nulls;
	Math m;
	Button b2;
	Button b1;
	TextField tf1;
	TextField tf2;
	int matrix[][];
	String names[];
	Image offImage;
	Image offImage2;
	Image startimage;
	Graphics offGraphics;
	Graphics offGraphics2;
	JPanel p;
	JPanel p5;
	JPanel p2;
	JButton jb1;
	JButton jb2;
	int firetre;
	int ti;
	int rows;
	int cols;
	cluster cl;
	int antallx;
	int antally;
	int wdth;
	int hgth;
	paintpanel pp;
	analyse a;
	int error;
	Label status2;
	actionhandler ah;

	public dataloader(Label label, cluster cluster1) {
		status = new Label("Choose file                               ");
		t = new tools();
		dataloaded = false;
		returnrow = 1;
		nulls = new Vector();
		jb1 = new JButton("Ok");
		jb2 = new JButton("Set dataend");
		firetre = 43;
		ti = 10;
		error = 0;
		ah = new actionhandler(this);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		setIconImage(toolkit.getImage("im/loads.gif"));
		startimage = toolkit.getImage("im/file.jpg");
		setTitle("Gene data loader");
		addWindowListener(ah);
		status2 = label;
		setBackground(Color.lightGray);
		cl = cluster1;
		p = new JPanel();
		JButton jbutton = new JButton("Set namecolumn");
		JButton jbutton1 = new JButton("Choose file");
		JButton jbutton2 = new JButton("Get data");
		p2 = new JPanel(new BorderLayout());
		p.setLayout(new GridLayout(15, 1));
		b1 = new Button("Ok");
		b2 = new Button("Set dataend");
		Button button = new Button("Set namecolumn");
		Button button1 = new Button("Choose file");
		Button button2 = new Button("Get data");
		jb2.addActionListener(this);
		jbutton.addActionListener(this);
		jbutton1.addActionListener(this);
		jbutton2.addActionListener(this);
		CheckboxGroup checkboxgroup = new CheckboxGroup();
		cb1 = new Checkbox("Tab delimited", checkboxgroup, true);
		cb2 = new Checkbox("Space delimited", checkboxgroup, false);
		p.add(jbutton1);
		p.add(new Label(""));
		p.add(new Label("Data delimiter:"));
		p.add(cb1);
		p.add(cb2);
		p.add(jbutton2);
		p.add(jb2);
		p.add(jbutton);
		p.add(jb1);
		jb1.setBackground(new Color(201, 201, 240));
		jb2.setBackground(new Color(201, 201, 240));
		jbutton.setBackground(new Color(201, 201, 240));
		jbutton1.setBackground(new Color(201, 201, 240));
		jbutton2.setBackground(new Color(201, 201, 240));
		tf1 = new TextField(10);
		tf2 = new TextField(10);
		p.add(new Label("NullValues:"));
		p.add(tf1);
		p.add(new Label("Set to:"));
		p.add(tf2);
		nullstoav = new Checkbox("NULLS to average", false);
		p.add(nullstoav);
		p2.add(status, "Center");
		p2.setBackground(Color.lightGray);
		status.setBackground(Color.lightGray);
		setLayout(new BorderLayout());
		rangerup = new Scrollbar(1, 0, 10, 0, 35);
		rangerhor = new Scrollbar(0, 0, 10, 0, 40);
		rangerup.addAdjustmentListener(this);
		rangerhor.addAdjustmentListener(this);
		add(p2, "North");
		pp = new paintpanel(rangerhor, rangerup);
		pp.addMouseListener(this);
		add(pp, "Center");
		p5 = new JPanel(new FlowLayout());
		p5.setBorder(new EtchedBorder());
		add(p5, "West");
		p5.add(p);
		p.setBorder(new EtchedBorder());
		p2.setBorder(new EtchedBorder());
		pp.setimage(startimage);
		setLocation(cluster1.getLocation().x, cluster1.getLocation().y);
		setSize(642, 535);
	}

	public void actionPerformed(ActionEvent actionevent) {
		stat = 0;
		boolean flag = false;
		if (actionevent.getActionCommand() == "Set datastart" && dataloaded) {
			mode = 0;
			jb2.setText("Set dataend");
			status.setText("Select upper left cell");
			stat = 2;
			rangerup.setValue(rangerup.getMinimum());
			rangerhor.setValue(rangerhor.getMinimum());
			horslide = rangerup.getValue();
			vertslide = rangerhor.getValue();
			repaint();
		} else
		if (actionevent.getActionCommand() == "Set dataend" && dataloaded) {
			mode = 1;
			jb2.setText("Set datastart");
			status.setText("Select lower right cell");
			stat = 1;
			rangerup.setValue(screen.length);
			rangerhor.setValue(rangerhor.getMaximum());
			horslide = rangerup.getValue();
			vertslide = rangerhor.getValue();
			repaint();
		} else
		if (actionevent.getActionCommand() == "Set namecolumn" && dataloaded) {
			status.setText("Select namecolumn");
			rangerup.setValue(rangerup.getMinimum());
			rangerhor.setValue(rangerhor.getMinimum());
			horslide = rangerup.getValue();
			vertslide = rangerhor.getValue();
			mode = 2;
			repaint();
		} else
		if (actionevent.getActionCommand() == "Choose file") {
			FileDialog filedialog = new FileDialog(new Frame("Select file"));
			filedialog.setMode(0);
			if (cl.lastpath != null)
				filedialog.setDirectory(cl.lastpath);
			filedialog.show();
			f = new File(filedialog.getDirectory() + filedialog.getFile());
			cl.lastpath = filedialog.getDirectory();
			cl.setTitle("J-EXPRESS : " + filedialog.getFile());
			status.setText("Get the data");
		} else
		if (actionevent.getActionCommand() == "Get data") {
			status.setText("Importing data.");
			setindata();
			dataloaded = true;
			status.setText("Select upper left cell");
		} else
		if (actionevent.getActionCommand() == "Ok" && dataloaded)
			makematrix();
	}

	public void adjustmentValueChanged(AdjustmentEvent adjustmentevent) {
		boolean flag = false;
		if (adjustmentevent.getAdjustable() == rangerup)
			horslide = adjustmentevent.getValue();
		else
			vertslide = adjustmentevent.getValue();
		repaint();
	}

	public String clean(String s, String s1, String s2) {
		String s3 = null;
		int i = s.indexOf(s1);
		if (i != -1) {
			s3 = s.substring(0, i);
			s3 = s3 + s2;
			s3 = s3 + s.substring(i + s1.length(), s.length());
		}
		if (s.indexOf(s1) != -1)
			s3 = clean(s3, s1, s2);
		else
			s3 = s;
		return s3;
	}

	public byte[] extendnulls(byte abyte0[]) {
		int i = 0;
		int j = 0;
		int k = 0;
		boolean flag = false;
		int l = 0;
		for (int i1 = 0; i1 < abyte0.length; i1++)
			if (abyte0[i1] == 13)
				l++;

		byte abyte1[] = new byte[abyte0.length - l];
		l = 0;
		for (int j1 = 0; j1 < abyte0.length; j1++)
			if (abyte0[j1] != 13) {
				abyte1[l] = abyte0[j1];
				l++;
			}

		abyte0 = abyte1;
		abyte1 = null;
		for (int k1 = 0; k1 < abyte0.length; k1++) {
			if (abyte0[k1] == delim && i == delim || abyte0[k1] == 10 && i == delim || abyte0[k1] == delim && i == 10)
				j += 4;
			i = abyte0[k1];
		}

		abyte1 = new byte[abyte0.length + j];
		i = 0;
		for (int l1 = 0; l1 < abyte0.length; l1++) {
			if (abyte0[l1] == delim && i == delim || abyte0[l1] == 10 && i == delim || abyte0[l1] == delim && i == 10) {
				abyte1[l1 + k] = 78;
				abyte1[l1 + k + 1] = 85;
				abyte1[l1 + k + 2] = 76;
				abyte1[l1 + k + 3] = 76;
				k += 4;
			}
			abyte1[l1 + k] = abyte0[l1];
			i = abyte0[l1];
		}

		return abyte1;
	}

	public int getclickedcol(int i) {
		int j = 0;
		j = i / firetre;
		return j;
	}

	public int getclickedrow(int i) {
		int j = 0;
		j = i / ti;
		return j;
	}

	public double intdubval(String s, int i, int j, String s1) {
		double d = 0.0D;
		Double double1 = null;
		Object obj = null;
		if (s == null)
			s = "0";
		if (s.equals("NULL"))
			if (s1 != "") {
				s = s1;
				nulls.addElement(new Point(i, j));
			} else {
				s = "0";
				nulls.addElement(new Point(i, j));
			}
		try {
			double1 = new Double(s);
		}
		catch (NumberFormatException _ex) {
			double1 = new Double(0.0D);
			status2.setText("Some values has automaticly been set to 0.0!");
		}
		if (double1 != null)
			d = double1.doubleValue();
		return d * 1000D;
	}

	public static void main(String args[]) {
	}

	public void makematrix() {
		boolean flag = false;
		boolean flag1 = false;
		String s = null;
		String s1 = tf1.getText();
		String s2 = tf2.getText();
		matrix = new int[(endrow - returnrow) + 1][(endcol - returncol) + 1];
		status.setText("loading data");
		error = 0;
		try {
			for (int i = 0; i <= endrow - returnrow; i++) {
				for (int k = 0; k <= endcol - returncol; k++) {
					s = screen[i + returnrow][k + returncol];
					if (s.equals(s1) && s2 != "")
						matrix[i][k] = (int)intdubval(s2, i, k, s2);
					else
						matrix[i][k] = (int)intdubval(s, i, k, s2);
					if (minvalue > matrix[i][k])
						minvalue = matrix[i][k];
					if (maxvalue < matrix[i][k])
						maxvalue = matrix[i][k];
					if (error == 1)
						break;
				}

				if (error == 1)
					break;
			}

		}
		catch (NumberFormatException _ex) {
			status.setText("Dont know how to convert \"" + s + "\" to a number");
			error = 1;
		}
		catch (NullPointerException _ex) {
			status.setText("Cannot load data, check marked area");
			error = 1;
		}
		catch (Exception _ex) {
			status.setText("Cannot load data, check marked area");
			error = 1;
		}
		if (error == 0) {
			names = new String[(endrow - returnrow) + 1];
			for (int j = 0; j <= endrow - returnrow; j++)
				names[j] = screen[j + returnrow][namecol];

			lengde = (endrow - returnrow) + 1;
			bredde = (endcol - returncol) + 1;
			status.setText("data loaded");
			status2.setText(String.valueOf((endrow - returnrow) + 1) + " genes loaded");
		}
		if (nullstoav.getState())
			nulltoaverage();
		cl.nullvals = nulls;
	}

	public void mouseClicked(MouseEvent mouseevent) {
	}

	public void mouseEntered(MouseEvent mouseevent) {
	}

	public void mouseExited(MouseEvent mouseevent) {
	}

	public void mousePressed(MouseEvent mouseevent) {
		mouseclickx = mouseevent.getX();
		mouseclicky = mouseevent.getY();
		if (mode == 0) {
			returnrow = getclickedrow(mouseclicky) + horslide;
			returncol = getclickedcol(mouseclickx) + vertslide;
		} else
		if (mode == 1) {
			endrow = getclickedrow(mouseclicky) + horslide;
			endcol = getclickedcol(mouseclickx) + vertslide;
		} else
		if (mode == 2)
			namecol = getclickedcol(mouseclickx) + vertslide;
		if (namecol >= returncol && namecol <= endcol)
			status.setText("Namecolumn is defined as a datacolumn");
		else
		if (mode == 0)
			status.setText("Select upper left cell");
		else
		if (mode == 1)
			status.setText("Select lower right cell");
		else
		if (mode == 2)
			status.setText("Select name column");
		repaint();
	}

	public void mouseReleased(MouseEvent mouseevent) {
	}

	public void nulltoaverage() {
		Vector vector = new Vector();
		boolean flag = false;
		double d = 0.0D;
		boolean flag1 = false;
		boolean flag2 = false;
		for (int k = 0; k < nulls.size(); k++) {
			vector.removeAllElements();
			int j = 0;
			double d1 = 0.0D;
			Point point = (Point)nulls.elementAt(k);
			vector.addElement(point);
			boolean flag3 = false;
			int i = point.x;
			if (nulls.size() > k + 1) {
				for (Point point1 = (Point)nulls.elementAt(k + 1); !flag3 && point1.x == point.x;) {
					vector.addElement(nulls.elementAt(k + 1));
					k++;
					if (nulls.size() > k + 1)
						point1 = (Point)nulls.elementAt(k + 1);
					else
						flag3 = true;
				}

			}
			for (int l = 0; l < matrix[0].length; l++) {
				boolean flag4 = false;
				for (int i1 = 0; i1 < vector.size(); i1++) {
					Point point2 = (Point)vector.elementAt(i1);
					if (point2.y == l)
						flag4 = true;
				}

				if (!flag4) {
					d1 += matrix[i][l];
					j++;
				}
			}

			for (int j1 = 0; j1 < matrix[0].length; j1++) {
				for (int k1 = 0; k1 < vector.size(); k1++) {
					Point point3 = (Point)vector.elementAt(k1);
					if (point3.y == j1)
						matrix[i][j1] = (int)Math.round(d1 / (double)j);
				}

			}

		}

	}

	public void paint(Graphics g) {
		update(g);
	}

	public void setindata() {
		String s = "";
		boolean flag = false;
		boolean flag1 = false;
		int j = 0;
		int k = 0;
		boolean flag2 = false;
		boolean flag3 = false;
		boolean flag4 = false;
		if (cb1.getState()) {
			boolean flag5 = true;
			delim = 9;
		} else {
			byte byte0 = 2;
			delim = 32;
		}
		try {
			FileInputStream fileinputstream = new FileInputStream(f);
			int i = fileinputstream.available();
			byte abyte0[] = new byte[i];
			BufferedInputStream bufferedinputstream = new BufferedInputStream(fileinputstream);
			DataInputStream datainputstream = new DataInputStream(bufferedinputstream);
			datainputstream.read(abyte0);
			s = new String(extendnulls(abyte0));
			if (cb1.getState()) {
				for (int l1 = 0; l1 < abyte0.length; l1++) {
					if (abyte0[l1] == 10)
						j++;
					if (abyte0[l1] == 9 && j == 0)
						k++;
				}

			} else {
				for (int i2 = 0; i2 < abyte0.length; i2++) {
					if (abyte0[i2] == 10)
						j++;
					if (abyte0[i2] == 32 && j == 0)
						k++;
				}

			}
			k = width(s);
			screen = new String[j + 1][k + 1];
			screen2 = new String[j + 1][k + 1];
			fileinputstream.close();
		}
		catch (IOException ioexception) {
			System.out.print(ioexception.toString());
		}
		rangerup.setMaximum(screen.length);
		rangerhor.setMaximum(screen[0].length);
		rangerup.setValue(rangerup.getMinimum());
		rangerhor.setValue(rangerhor.getMinimum());
		horslide = rangerup.getValue();
		vertslide = rangerhor.getValue();
		endrow = j;
		endcol = k - 1;
		returnrow = 1;
		returncol = 1;
		String as[] = new String[j + 1];
		lengde = j;
		bredde = k + 1;
		clusters = lengde;
		clusvector = new String[lengde];
		for (int j1 = 0; j1 < lengde; j1++)
			clusvector[j1] = String.valueOf(j1);

		int l = 0;
		for (StringTokenizer stringtokenizer = new StringTokenizer(s, "\n"); stringtokenizer.hasMoreTokens();) {
			as[l] = stringtokenizer.nextToken();
			l++;
		}

		for (int k1 = 0; k1 < as.length; k1++) {
			int i1 = 0;
			if (as[k1] != null && as[k1] != " " && as[k1] != "\n") {
				StringTokenizer stringtokenizer1;
				if (cb1.getState())
					stringtokenizer1 = new StringTokenizer(as[k1], "\t");
				else
					stringtokenizer1 = new StringTokenizer(as[k1], " ");
				while (stringtokenizer1.hasMoreTokens())  {
					screen[k1][i1] = stringtokenizer1.nextToken();
					if (screen[k1][i1].length() > 7)
						screen2[k1][i1] = screen[k1][i1].substring(1, 7);
					else
						screen2[k1][i1] = screen[k1][i1];
					i1++;
				}
			}
		}

		repaint();
	}

	public void update(Graphics g) {
		wdth = pp.wdth();
		hgth = pp.hgth();
		rows = hgth / ti;
		cols = wdth / firetre;
		if (offImage == null)
			offImage = createImage(wdth, hgth);
		else
		if (offImage.getHeight(this) != hgth || offImage.getWidth(this) != wdth)
			offImage = createImage(wdth, hgth);
		offGraphics = offImage.getGraphics();
		offGraphics.setColor(getBackground());
		offGraphics.fillRect(0, 0, wdth, hgth);
		offGraphics.setColor(new Color(230, 230, 230));
		offGraphics.fillRect(0, 0, wdth, hgth);
		if (dataloaded) {
			offGraphics.setColor(new Color(230, 230, 130));
			offGraphics.fillRect(Math.max(0, (returncol - vertslide) * firetre), Math.max((returnrow - horslide) * ti, 0), (wdth / firetre) * firetre - Math.max(0, (returncol - vertslide) * firetre) - Math.max(0, ((cols - 1 - endcol) + vertslide) * firetre), (hgth / ti) * ti - Math.max((returnrow - horslide) * ti, 0) - Math.max(0, ((rows - 1 - endrow) + horslide) * ti));
			if (namecol < returncol || namecol > endcol)
				offGraphics.setColor(new Color(230, 130, 230));
			else
				offGraphics.setColor(new Color(250, 0, 0));
			if (namecol >= vertslide && namecol < vertslide + 8)
				offGraphics.fillRect(Math.max(0, (namecol - vertslide) * firetre), Math.max((returnrow - horslide) * ti, 0), firetre, (hgth / ti) * ti - Math.max((returnrow - horslide) * ti, 0) - Math.max(0, ((rows - 1 - endrow) + horslide) * ti));
		}
		offGraphics.setColor(new Color(120, 200, 120));
		offGraphics.drawRect(0, 0, 430, 360);
		for (int i = 0; i < rows; i++)
			offGraphics.drawLine(0, i * ti, wdth, i * ti);

		for (int j = 0; j < cols; j++)
			offGraphics.drawLine(j * firetre, 0, j * firetre, hgth);

		if (dataloaded) {
			offGraphics.setColor(Color.black);
			offGraphics.setFont(new Font("Times", 0, 8));
			for (int k = 0; k < cols; k++) {
				for (int l = 0; l < rows; l++)
					try {
						if (screen[l + horslide][k + vertslide] != null) {
							offGraphics.setColor(Color.black);
							if (screen2[l + horslide][k + vertslide].equals("NULL"))
								offGraphics.setColor(Color.red);
							offGraphics.drawString(screen2[l + horslide][k + vertslide], 2 + k * firetre, 10 + l * ti);
							pp.setimage(offImage);
						}
					}
					catch (ArrayIndexOutOfBoundsException _ex) { }
					catch (NullPointerException _ex) { }

			}

		}
		p5.repaint();
		p.repaint();
		p2.repaint();
	}

	public int width(String s) {
		int i = 0;
		int j = 0;
		byte abyte0[] = s.getBytes();
		for (int k = 0; k < abyte0.length; k++)
			if (abyte0[k] == 10) {
				if (j > i)
					i = j;
				j = 0;
			} else
			if (abyte0[k] == delim)
				j++;

		return i + 1;
	}
}
