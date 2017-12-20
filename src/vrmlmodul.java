import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class vrmlmodul {

	tools t;
	double matrix[][];
	double xmax;
	double ymin;
	double ymax;
	gen trunk;
	int matrixheight;
	int matrixindex;
	String names[];
	String selectednames[];
	int namecounter;
	cluster cl;

	public vrmlmodul() {
		t = new tools();
	}

	public vrmlmodul(gen gen1, int ai[][], String as[], cluster cluster1) {
		t = new tools();
		cl = cluster1;
		names = as;
		trunk = gen1;
		countgenes(gen1);
		matrix = new double[matrixheight][ai[0].length];
		selectednames = new String[ai.length];
		makematrix(gen1, ai);
		checkmatrix(matrix);
		write();
	}

	public void checkmatrix(double ad[][]) {
		xmax = ad[0].length;
		for (int i = 0; i < ad.length; i++) {
			for (int j = 0; (double)j < xmax; j++) {
				if (ad[i][j] < ymin)
					ymin = ad[i][j];
				if (ad[i][j] > ymax)
					ymax = ad[i][j];
			}

		}

	}

	public void countgenes(gen gen1) {
		if (gen1.merged) {
			countgenes(gen1.right);
			countgenes(gen1.left);
		} else {
			matrixheight++;
		}
	}

	public static void main(String args[]) {
		vrmlmodul vrmlmodul1 = new vrmlmodul();
	}

	public void makematrix(gen gen1, int ai[][]) {
		if (gen1.merged) {
			makematrix(gen1.right, ai);
			makematrix(gen1.left, ai);
		} else {
			selectednames[namecounter] = names[t.intval(gen1.name)];
			namecounter++;
			for (int i = 0; i < ai[0].length; i++)
				matrix[matrixindex][i] = ai[t.intval(gen1.name)][i];

			matrixindex++;
		}
	}

	public void write() {
		double d2 = ymax - ymin;
		double d3 = 3D;
		FileDialog filedialog = new FileDialog(new Frame("Enter filename"));
		filedialog.setFile("cluster.vrml");
		filedialog.setMode(1);
		filedialog.show();
		if (cl != null && cl.lastpath != null)
			filedialog.setDirectory(cl.lastpath);
		File file = new File(filedialog.getDirectory() + filedialog.getFile());
		if (cl != null && cl.lastpath != null)
			cl.lastpath = filedialog.getDirectory();
		try {
			FileOutputStream fileoutputstream = new FileOutputStream(file);
			BufferedOutputStream bufferedoutputstream = new BufferedOutputStream(fileoutputstream);
			DataOutputStream dataoutputstream = new DataOutputStream(bufferedoutputstream);
			dataoutputstream.writeBytes("#VRML V2.0 utf8\n");
			dataoutputstream.writeBytes("Background{skyColor 0.8 0.8 0.85}\n");
			dataoutputstream.writeBytes("PointLight {on TRUE intensity 1.0 color 1.0 1.0 1.0 location -20.7622 7.45829 11.0889}\n");
			dataoutputstream.writeBytes("Viewpoint {\n");
			dataoutputstream.writeBytes("position -20.7622 7.45829 11.0889\n\n");
			dataoutputstream.writeBytes("orientation 0.14549 0.987475 0.0610503 5.00553}\n");
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[0].length; j++) {
					dataoutputstream.writeBytes("Transform {\n");
					dataoutputstream.writeBytes("translation " + String.valueOf((double)i * 0.40000000000000002D) + " " + String.valueOf(((matrix[i][j] / d2) * d3) / 2D) + " " + String.valueOf((double)j * 0.40000000000000002D) + "\n");
					dataoutputstream.writeBytes("children Shape {\n");
					if (matrix[i][j] < 0.0D) {
						double d = Math.abs(matrix[i][j] / ymin);
						dataoutputstream.writeBytes("appearance Appearance {material Material {diffuseColor 0 " + String.valueOf(d) + " 0 }}" + "\n");
						dataoutputstream.writeBytes("geometry Box { size 0.4 " + String.valueOf(-(matrix[i][j] / d2) * d3) + " 0.4 }}}" + "\n");
					} else
					if (matrix[i][j] > 0.0D) {
						double d1 = Math.abs(matrix[i][j] / ymax);
						dataoutputstream.writeBytes("appearance Appearance {material Material {diffuseColor " + String.valueOf(d1) + " 0 0 }}" + "\n");
						dataoutputstream.writeBytes("geometry Box { size 0.4 " + String.valueOf((matrix[i][j] / d2) * d3) + " 0.4 }}}" + "\n");
					} else {
						dataoutputstream.writeBytes("appearance Appearance {material Material {diffuseColor 0 0 0 }}\n");
						dataoutputstream.writeBytes("geometry Box { size 0.4 0.0001 0.4 }}}\n");
					}
				}

				dataoutputstream.writeBytes("Transform {\n");
				dataoutputstream.writeBytes("translation " + String.valueOf((double)i * 0.40000000000000002D) + " 0 " + String.valueOf((double)matrix[0].length * 0.40000000000000002D) + "" + "\n");
				dataoutputstream.writeBytes("rotation 0 1 0 4.7 \n");
				dataoutputstream.writeBytes("children Shape {\n");
				dataoutputstream.writeBytes("appearance Appearance {material Material {diffuseColor 0 0 0 }}\n");
				dataoutputstream.writeBytes(" geometry Text { string \" " + selectednames[i] + " \" }}}" + "\n");
			}

			bufferedoutputstream.flush();
			fileoutputstream.close();
		}
		catch (IOException _ex) { }
	}
}
