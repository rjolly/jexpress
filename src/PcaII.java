import Jama.EigenvalueDecomposition;
import Jama.Matrix;

public class PcaII {

	double eigenvalues[];
	double projection[][];
	double eigenvectors[][];

	public PcaII(double ad[][]) {
		double ad1[][] = covar(ad);
		projection = PCA(ad, ad1);
	}

	public double ElementAt(int i, int j) {
		return projection[i][j];
	}

	public double[][] PCA(double ad[][], double ad1[][]) {
		Matrix matrix2 = new Matrix(ad1);
		EigenvalueDecomposition eigenvaluedecomposition = matrix2.eig();
		eigenvalues = eigenvaluedecomposition.getRealEigenvalues();
		double ad2[][] = eigenvaluedecomposition.getV().getArray();
		ad2 = sorteigenvectors(ad2);
		eigenvectors = ad2;
		Matrix matrix = new Matrix(ad);
		Matrix matrix1 = new Matrix(ad2);
		matrix = matrix.times(matrix1);
		return matrix.getArray();
	}

	public double[][] covar(double ad[][]) {
		double ad1[] = new double[ad[0].length];
		double ad2[] = new double[ad.length];
		double ad3[] = new double[ad.length];
		double ad4[][] = new double[ad[0].length][ad[0].length];
		double d = 0.0D;
		for (int i = 0; i < ad.length; i++) {
			for (int j = 0; j < ad[0].length; j++)
				ad1[j] = ad1[j] + ad[i][j];

		}

		for (int k = 0; k < ad1.length; k++)
			ad1[k] = ad1[k] / (double)ad.length;

		for (int l = 0; l < ad[0].length; l++) {
			for (int i1 = 0; i1 <= l; i1++) {
				for (int k1 = 0; k1 < ad.length; k1++) {
					ad2[k1] = ad[k1][l] - ad1[l];
					ad3[k1] = ad[k1][i1] - ad1[i1];
				}

				double d1 = 0.0D;
				for (int i2 = 0; i2 < ad.length; i2++)
					d1 += ad2[i2] * ad3[i2];

				d1 /= ad.length - 1;
				ad4[l][i1] = d1;
			}

		}

		for (int j1 = 1; j1 < ad4.length; j1++) {
			for (int l1 = 0; l1 < j1; l1++)
				ad4[l1][j1] = ad4[j1][l1];

		}

		return ad4;
	}

	public double[][] getlow(double ad[][]) {
		Matrix matrix = new Matrix(ad);
		Matrix matrix1 = new Matrix(eigenvectors);
		matrix = matrix.times(matrix1);
		return matrix.getArray();
	}

	public double[][] gettransposedeigen() {
		Matrix matrix = new Matrix(eigenvectors);
		return matrix.transpose().getArray();
	}

	public static void main(String args[]) {
		PcaII pcaii = new PcaII(null);
	}

	public double nrPoints() {
		return (double)projection.length;
	}

	public double[][] sorteigenvectors(double ad[][]) {
		double d = 0.0D;
		for (int i = 0; i < eigenvalues.length - 1; i++) {
			for (int j = i + 1; j < eigenvalues.length; j++)
				if (eigenvalues[j] > eigenvalues[i]) {
					double d1 = eigenvalues[i];
					eigenvalues[i] = eigenvalues[j];
					eigenvalues[j] = d1;
					for (int k = 0; k < ad.length; k++) {
						double d2 = ad[k][i];
						ad[k][i] = ad[k][j];
						ad[k][j] = d2;
					}

				}

		}

		return ad;
	}

	public double totalvariance() {
		double d = 0.0D;
		for (int i = 0; i < eigenvalues.length; i++)
			d += eigenvalues[i];

		return d;
	}

	public double varianceaccounted(int i) {
		double d = 0.0D;
		d = eigenvalues[i] / totalvariance();
		d *= 100D;
		return d;
	}

	public String varianceastr(int i) {
		String s = "";
		double d = varianceaccounted(i);
		s = String.valueOf(d);
		if (s.length() > 5)
			s = s.substring(0, 5);
		return s;
	}
}
