import JSci.maths.DoubleMatrix;
import JSci.maths.KarhunenLoeve;
import JSci.maths.MaximumIterationsExceededException;
import java.io.PrintStream;

public class LowPointDataset {

	double dataset[][];
	double values[][];
	int nrPoints;
	KarhunenLoeve ka;
	DoubleMatrix eigenvl;

	public LowPointDataset(double ad[][]) {
		nrPoints = 0;
		geteigen(ad);
		dataset = dubval(getmatrix(ad));
		values = dubval(eigenvl);
		nrPoints = ad.length;
	}

	public double XElementAt(int i) {
		return dataset[i][0];
	}

	public double YElementAt(int i) {
		return dataset[i][1];
	}

	public double ZElementAt(int i) {
		return dataset[i][2];
	}

	public double[][] dubval(DoubleMatrix doublematrix) {
		double ad[][] = new double[doublematrix.rows()][doublematrix.columns()];
		for (int i = 0; i < doublematrix.rows(); i++) {
			for (int j = 0; j < doublematrix.columns(); j++)
				ad[i][j] = doublematrix.getElement(i, j);

		}

		return ad;
	}

	public void geteigen(double ad[][]) {
		DoubleMatrix doublematrix = null;
		ka = new KarhunenLoeve(ad);
		try {
			doublematrix = new DoubleMatrix(ka.getEigenvectors());
		}
		catch (MaximumIterationsExceededException _ex) {
			System.out.print("No eigenvectors found!");
		}
		doublematrix = (DoubleMatrix)doublematrix.transpose();
		eigenvl = doublematrix;
	}

	public double[][] getlowvalues(double ad[][]) {
		double ad1[][] = dubval(getmatrix(ad));
		return ad1;
	}

	public DoubleMatrix getmatrix(double ad[][]) {
		DoubleMatrix doublematrix = new DoubleMatrix(ad);
		DoubleMatrix doublematrix1 = doublematrix.multiply(eigenvl);
		return doublematrix1;
	}

	public double[] vals() {
		double ad[] = new double[values.length];
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values[0].length; j++)
				ad[j] = ad[j] + values[i][j];

		}

		return ad;
	}
}
