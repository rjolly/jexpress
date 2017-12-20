import Acme.JPM.Encoders.GifEncoder;
import Acme.JPM.Encoders.ImageEncoder;
import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Label;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.StringTokenizer;

public class filehandler {

	DataOutputStream dataout;
	FileOutputStream ostream;
	FileOutputStream ostream2;
	FileInputStream istream;
	FileInputStream istream2;
	DataInputStream datain;
	Label Status;
	int adata[][];
	int values[][];
	int agenvalues[];
	gen gendata[];
	int minval;
	int maxval;
	boolean stepovergen[];
	cluster cl;
	int minvalue;
	int maxvalue;
	int clusters;
	int lengde;
	int bredde;
	String clusvector[];

	public filehandler() {
	}

	public double intdubval(String s) {
		double d = 0.0D;
		if (s == null)
			s = "0";
		Double double1 = new Double(s);
		d = double1.doubleValue();
		return d * 1000D;
	}

	public static void main(String args[]) {
	}

	public void read() {
		Object obj = null;
		try {
			File file = new File("out");
			File file1 = new File("out2");
			istream = new FileInputStream(file);
			istream2 = new FileInputStream(file1);
			datain = new DataInputStream(istream);
			readmeta();
			minval = datain.readInt();
			maxval = datain.readInt();
			istream.close();
			istream2.close();
		}
		catch (Exception exception) {
			System.out.print(exception.toString());
		}
	}

	public int[][] readdata() {
		String s = "";
		boolean flag = false;
		boolean flag1 = false;
		int j = 0;
		int k = 0;
		try {
			FileDialog filedialog = new FileDialog(new Frame());
			filedialog.setMode(0);
			filedialog.show();
			File file = new File(filedialog.getDirectory() + filedialog.getFile());
			FileInputStream fileinputstream = new FileInputStream(file);
			int i = fileinputstream.available();
			byte abyte0[] = new byte[i];
			BufferedInputStream bufferedinputstream = new BufferedInputStream(fileinputstream);
			DataInputStream datainputstream = new DataInputStream(bufferedinputstream);
			datainputstream.read(abyte0);
			s = new String(abyte0);
			for (int k1 = 0; k1 < abyte0.length; k1++) {
				if (abyte0[k1] == 10)
					j++;
				if (abyte0[k1] == 32 && j == 0)
					k++;
			}

			fileinputstream.close();
		}
		catch (IOException ioexception) {
			System.out.print(ioexception.toString());
		}
		System.out.print("\n lines :" + String.valueOf(j) + "\n");
		System.out.print("\n tabs  :" + String.valueOf(k) + "\n");
		int ai[][] = new int[j + 1][k + 1];
		String as[] = new String[j + 1];
		lengde = j;
		bredde = k + 1;
		clusters = lengde;
		clusvector = new String[lengde];
		for (int l = 0; l < lengde; l++)
			clusvector[l] = String.valueOf(l);

		int i1 = 0;
		for (StringTokenizer stringtokenizer = new StringTokenizer(s, "\n"); stringtokenizer.hasMoreTokens();) {
			as[i1] = stringtokenizer.nextToken();
			i1++;
		}

		for (int l1 = 1; l1 < as.length - 1; l1++) {
			int j1 = 0;
			StringTokenizer stringtokenizer1 = new StringTokenizer(as[l1], " ");
			stringtokenizer1.nextToken();
			while (stringtokenizer1.hasMoreTokens())  {
				ai[l1][j1] = (int)intdubval(stringtokenizer1.nextToken());
				if (minvalue > ai[l1][j1])
					minvalue = ai[l1][j1];
				if (maxvalue < ai[l1][j1])
					maxvalue = ai[l1][j1];
				j1++;
			}
		}

		return ai;
	}

	public gen readgen() {
		gen gen1 = null;
		try {
			String s = datain.readUTF();
			boolean flag = datain.readBoolean();
			boolean flag1 = datain.readBoolean();
			boolean flag2 = datain.readBoolean();
			double d = datain.readDouble();
			int i = datain.readInt();
			gen1 = new gen(s);
			gen1.hasleft = flag;
			gen1.hasright = flag1;
			gen1.merged = flag2;
			gen1.value = d;
			gen1.nme = i;
			if (gen1.hasright)
				gen1.right = readgen();
			if (gen1.hasleft)
				gen1.left = readgen();
		}
		catch (Exception exception) {
			System.out.print(exception.toString());
		}
		return gen1;
	}

	public void readmeta() {
		try {
			ObjectInputStream objectinputstream = new ObjectInputStream(istream2);
			adata = (int[][])objectinputstream.readObject();
			agenvalues = (int[])objectinputstream.readObject();
			values = (int[][])objectinputstream.readObject();
			gendata = (gen[])objectinputstream.readObject();
			stepovergen = (boolean[])objectinputstream.readObject();
		}
		catch (Exception exception) {
			System.out.print(exception.toString());
		}
	}

	public void savegenes(String as[], int ai[][]) {
		boolean flag = false;
		double d = 0.0D;
		FileDialog filedialog = new FileDialog(new Frame());
		filedialog.setMode(1);
		filedialog.show();
		try {
			File file = new File(filedialog.getDirectory() + filedialog.getFile());
			FileOutputStream fileoutputstream = new FileOutputStream(file);
			BufferedOutputStream bufferedoutputstream = new BufferedOutputStream(fileoutputstream);
			DataOutputStream dataoutputstream = new DataOutputStream(bufferedoutputstream);
			for (int i = 0; i < as.length; i++) {
				dataoutputstream.writeBytes(as[i]);
				for (int j = 0; j < ai[0].length; j++) {
					dataoutputstream.writeBytes("\t");
					double d1 = (double)ai[i][j] / 1000D;
					dataoutputstream.writeBytes(String.valueOf(d1));
				}

				dataoutputstream.writeBytes("\n");
			}

			bufferedoutputstream.flush();
			fileoutputstream.close();
		}
		catch (IOException _ex) { }
	}

	public void saveimage(Image image, int i, int j) {
		FileDialog filedialog = new FileDialog(new Frame("Enter filename"));
		filedialog.setFile(".gif");
		filedialog.setMode(1);
		if (cl != null && cl.lastpath != null)
			filedialog.setDirectory(cl.lastpath);
		filedialog.show();
		File file = new File(filedialog.getDirectory() + filedialog.getFile());
		if (cl != null && cl.lastpath != null)
			cl.lastpath = filedialog.getDirectory();
		try {
			FileOutputStream fileoutputstream = new FileOutputStream(file);
			BufferedOutputStream bufferedoutputstream = new BufferedOutputStream(fileoutputstream);
			GifEncoder gifencoder = new GifEncoder(image, fileoutputstream);
			gifencoder.encode();
			fileoutputstream.close();
		}
		catch (IOException _ex) { }
	}

	public void write(int ai[][], int ai1[], int ai2[][], Label label, int i, int j, gen agen[], 
			boolean aflag[]) {
		Status = label;
		try {
			File file = new File("out");
			File file1 = new File("out2");
			ostream = new FileOutputStream(file);
			ostream2 = new FileOutputStream(file1);
			dataout = new DataOutputStream(ostream);
			writemeta(ai, ai1, ai2, agen, aflag);
			dataout.writeInt(i);
			dataout.writeInt(j);
			dataout.flush();
			ostream.close();
			ostream2.close();
			Status.setText("Done saving");
		}
		catch (IOException ioexception) {
			System.out.println("IO Exception:");
			System.out.println(ioexception.getMessage());
			ioexception.printStackTrace();
		}
	}

	public void writegen(gen gen1) {
		try {
			if (gen1.name != null)
				dataout.writeUTF(gen1.name);
			else
				dataout.writeUTF("nn");
			dataout.writeBoolean(gen1.hasleft);
			dataout.writeBoolean(gen1.hasright);
			dataout.writeBoolean(gen1.merged);
			dataout.writeDouble(gen1.value);
			dataout.writeInt(gen1.nme);
		}
		catch (IOException ioexception) {
			System.out.println("IO Exception:");
			System.out.println(ioexception.getMessage());
			ioexception.printStackTrace();
		}
		if (gen1.hasright)
			writegen(gen1.right);
		if (gen1.hasleft)
			writegen(gen1.left);
	}

	public void writemeta(int ai[][], int ai1[], int ai2[][], gen agen[], boolean aflag[]) {
		try {
			ObjectOutputStream objectoutputstream = new ObjectOutputStream(ostream2);
			objectoutputstream.writeObject(ai);
			Status.setText("Maindata saved");
			objectoutputstream.writeObject(ai1);
			Status.setText("genvalues saved");
			objectoutputstream.writeObject(ai2);
			Status.setText("values save");
			objectoutputstream.writeObject(agen);
			Status.setText("gendata saved");
			objectoutputstream.writeObject(aflag);
			Status.setText("steplist saved");
		}
		catch (IOException ioexception) {
			System.out.println("IO Exception:");
			System.out.println(ioexception.getMessage());
			ioexception.printStackTrace();
		}
	}

	public void writetree(gen gen1) {
		System.out.print(" " + gen1.name);
		if (gen1.hasright)
			writetree(gen1.right);
		if (gen1.hasleft)
			writetree(gen1.left);
	}
}
