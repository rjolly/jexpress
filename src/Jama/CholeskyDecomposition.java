package Jama;

import java.io.Serializable;

// Referenced classes of package Jama:
//			Matrix

public class CholeskyDecomposition
	implements Serializable {

	private double L[][];
	private int n;
	private boolean isspd;

	public CholeskyDecomposition(Matrix matrix) {
		double ad[][] = matrix.getArray();
		n = matrix.getRowDimension();
		L = new double[n][n];
		isspd = matrix.getColumnDimension() == n;
		for (int i = 0; i < n; i++) {
			double ad1[] = L[i];
			double d = 0.0D;
			for (int j = 0; j < i; j++) {
				double ad2[] = L[j];
				double d1 = 0.0D;
				for (int l = 0; l < j; l++)
					d1 += ad2[l] * ad1[l];

				ad1[j] = d1 = (ad[i][j] - d1) / L[j][j];
				d += d1 * d1;
				isspd = isspd & (ad[j][i] == ad[i][j]);
			}

			d = ad[i][i] - d;
			isspd = isspd & (d > 0.0D);
			L[i][i] = Math.sqrt(Math.max(d, 0.0D));
			for (int k = i + 1; k < n; k++)
				L[i][k] = 0.0D;

		}

	}

	public Matrix getL() {
		return new Matrix(L, n, n);
	}

	public boolean isSPD() {
		return isspd;
	}

	public Matrix solve(Matrix matrix) {
		if (matrix.getRowDimension() != n)
			throw new IllegalArgumentException("Matrix row dimensions must agree.");
		if (!isspd)
			throw new RuntimeException("Matrix is not symmetric positive definite.");
		double ad[][] = matrix.getArrayCopy();
		int i = matrix.getColumnDimension();
		for (int j = 0; j < n; j++) {
			for (int k = j + 1; k < n; k++) {
				for (int i1 = 0; i1 < i; i1++)
					ad[k][i1] -= ad[j][i1] * L[k][j];

			}

			for (int j1 = 0; j1 < i; j1++)
				ad[j][j1] /= L[j][j];

		}

		for (int l = n - 1; l >= 0; l--) {
			for (int k1 = 0; k1 < i; k1++)
				ad[l][k1] /= L[l][l];

			for (int l1 = 0; l1 < l; l1++) {
				for (int i2 = 0; i2 < i; i2++)
					ad[l1][i2] -= ad[l][i2] * L[l][l1];

			}

		}

		return new Matrix(ad, n, i);
	}
}
