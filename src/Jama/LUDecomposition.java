package Jama;

import java.io.Serializable;

// Referenced classes of package Jama:
//			Matrix

public class LUDecomposition
	implements Serializable {

	private double LU[][];
	private int m;
	private int n;
	private int pivsign;
	private int piv[];

	public LUDecomposition(Matrix matrix) {
		LU = matrix.getArrayCopy();
		m = matrix.getRowDimension();
		n = matrix.getColumnDimension();
		piv = new int[m];
		for (int i = 0; i < m; i++)
			piv[i] = i;

		pivsign = 1;
		double ad1[] = new double[m];
		for (int j = 0; j < n; j++) {
			for (int k = 0; k < m; k++)
				ad1[k] = LU[k][j];

			for (int l = 0; l < m; l++) {
				double ad[] = LU[l];
				int i1 = Math.min(l, j);
				double d = 0.0D;
				for (int j2 = 0; j2 < i1; j2++)
					d += ad[j2] * ad1[j2];

				ad[j] = ad1[l] -= d;
			}

			int j1 = j;
			for (int k1 = j + 1; k1 < m; k1++)
				if (Math.abs(ad1[k1]) > Math.abs(ad1[j1]))
					j1 = k1;

			if (j1 != j) {
				for (int l1 = 0; l1 < n; l1++) {
					double d1 = LU[j1][l1];
					LU[j1][l1] = LU[j][l1];
					LU[j][l1] = d1;
				}

				int k2 = piv[j1];
				piv[j1] = piv[j];
				piv[j] = k2;
				pivsign = -pivsign;
			}
			if ((j < m) & (LU[j][j] != 0.0D)) {
				for (int i2 = j + 1; i2 < m; i2++)
					LU[i2][j] /= LU[j][j];

			}
		}

	}

	public double det() {
		if (m != n)
			throw new IllegalArgumentException("Matrix must be square.");
		double d = pivsign;
		for (int i = 0; i < n; i++)
			d *= LU[i][i];

		return d;
	}

	public double[] getDoublePivot() {
		double ad[] = new double[m];
		for (int i = 0; i < m; i++)
			ad[i] = piv[i];

		return ad;
	}

	public Matrix getL() {
		Matrix matrix = new Matrix(m, n);
		double ad[][] = matrix.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				if (i > j)
					ad[i][j] = LU[i][j];
				else
				if (i == j)
					ad[i][j] = 1.0D;
				else
					ad[i][j] = 0.0D;

		}

		return matrix;
	}

	public int[] getPivot() {
		int ai[] = new int[m];
		for (int i = 0; i < m; i++)
			ai[i] = piv[i];

		return ai;
	}

	public Matrix getU() {
		Matrix matrix = new Matrix(n, n);
		double ad[][] = matrix.getArray();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				if (i <= j)
					ad[i][j] = LU[i][j];
				else
					ad[i][j] = 0.0D;

		}

		return matrix;
	}

	public boolean isNonsingular() {
		for (int i = 0; i < n; i++)
			if (LU[i][i] == 0.0D)
				return false;

		return true;
	}

	public Matrix solve(Matrix matrix) {
		if (matrix.getRowDimension() != m)
			throw new IllegalArgumentException("Matrix row dimensions must agree.");
		if (!isNonsingular())
			throw new RuntimeException("Matrix is singular.");
		int i = matrix.getColumnDimension();
		Matrix matrix1 = matrix.getMatrix(piv, 0, i - 1);
		double ad[][] = matrix1.getArray();
		for (int j = 0; j < n; j++) {
			for (int k = j + 1; k < n; k++) {
				for (int i1 = 0; i1 < i; i1++)
					ad[k][i1] -= ad[j][i1] * LU[k][j];

			}

		}

		for (int l = n - 1; l >= 0; l--) {
			for (int j1 = 0; j1 < i; j1++)
				ad[l][j1] /= LU[l][l];

			for (int k1 = 0; k1 < l; k1++) {
				for (int l1 = 0; l1 < i; l1++)
					ad[k1][l1] -= ad[l][l1] * LU[k1][l];

			}

		}

		return matrix1;
	}
}
