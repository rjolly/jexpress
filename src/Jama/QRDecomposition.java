package Jama;

import Jama.util.Maths;
import java.io.Serializable;

// Referenced classes of package Jama:
//			Matrix

public class QRDecomposition
	implements Serializable {

	private double QR[][];
	private int m;
	private int n;
	private double Rdiag[];

	public QRDecomposition(Matrix matrix) {
		QR = matrix.getArrayCopy();
		m = matrix.getRowDimension();
		n = matrix.getColumnDimension();
		Rdiag = new double[n];
		for (int i = 0; i < n; i++) {
			double d = 0.0D;
			for (int j = i; j < m; j++)
				d = Maths.hypot(d, QR[j][i]);

			if (d != 0.0D) {
				if (QR[i][i] < 0.0D)
					d = -d;
				for (int k = i; k < m; k++)
					QR[k][i] /= d;

				QR[i][i]++;
				for (int l = i + 1; l < n; l++) {
					double d1 = 0.0D;
					for (int i1 = i; i1 < m; i1++)
						d1 += QR[i1][i] * QR[i1][l];

					d1 = -d1 / QR[i][i];
					for (int j1 = i; j1 < m; j1++)
						QR[j1][l] += d1 * QR[j1][i];

				}

			}
			Rdiag[i] = -d;
		}

	}

	public Matrix getH() {
		Matrix matrix = new Matrix(m, n);
		double ad[][] = matrix.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				if (i >= j)
					ad[i][j] = QR[i][j];
				else
					ad[i][j] = 0.0D;

		}

		return matrix;
	}

	public Matrix getQ() {
		Matrix matrix = new Matrix(m, n);
		double ad[][] = matrix.getArray();
		for (int i = n - 1; i >= 0; i--) {
			for (int j = 0; j < m; j++)
				ad[j][i] = 0.0D;

			ad[i][i] = 1.0D;
			for (int k = i; k < n; k++)
				if (QR[i][i] != 0.0D) {
					double d = 0.0D;
					for (int l = i; l < m; l++)
						d += QR[l][i] * ad[l][k];

					d = -d / QR[i][i];
					for (int i1 = i; i1 < m; i1++)
						ad[i1][k] += d * QR[i1][i];

				}

		}

		return matrix;
	}

	public Matrix getR() {
		Matrix matrix = new Matrix(n, n);
		double ad[][] = matrix.getArray();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				if (i < j)
					ad[i][j] = QR[i][j];
				else
				if (i == j)
					ad[i][j] = Rdiag[i];
				else
					ad[i][j] = 0.0D;

		}

		return matrix;
	}

	public boolean isFullRank() {
		for (int i = 0; i < n; i++)
			if (Rdiag[i] == 0.0D)
				return false;

		return true;
	}

	public Matrix solve(Matrix matrix) {
		if (matrix.getRowDimension() != m)
			throw new IllegalArgumentException("Matrix row dimensions must agree.");
		if (!isFullRank())
			throw new RuntimeException("Matrix is rank deficient.");
		int i = matrix.getColumnDimension();
		double ad[][] = matrix.getArrayCopy();
		for (int j = 0; j < n; j++) {
			for (int k = 0; k < i; k++) {
				double d = 0.0D;
				for (int k1 = j; k1 < m; k1++)
					d += QR[k1][j] * ad[k1][k];

				d = -d / QR[j][j];
				for (int i2 = j; i2 < m; i2++)
					ad[i2][k] += d * QR[i2][j];

			}

		}

		for (int l = n - 1; l >= 0; l--) {
			for (int i1 = 0; i1 < i; i1++)
				ad[l][i1] /= Rdiag[l];

			for (int j1 = 0; j1 < l; j1++) {
				for (int l1 = 0; l1 < i; l1++)
					ad[j1][l1] -= ad[l][l1] * QR[j1][l];

			}

		}

		return (new Matrix(ad, n, i)).getMatrix(0, n - 1, 0, i - 1);
	}
}
