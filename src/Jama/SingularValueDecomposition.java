package Jama;

import Jama.util.Maths;
import java.io.Serializable;

// Referenced classes of package Jama:
//			Matrix

public class SingularValueDecomposition
	implements Serializable {

	private double U[][];
	private double V[][];
	private double s[];
	private int m;
	private int n;

	public SingularValueDecomposition(Matrix matrix) {
		double ad[][] = matrix.getArrayCopy();
		m = matrix.getRowDimension();
		n = matrix.getColumnDimension();
		int i = Math.min(m, n);
		s = new double[Math.min(m + 1, n)];
		U = new double[m][i];
		V = new double[n][n];
		double ad1[] = new double[n];
		double ad2[] = new double[m];
		boolean flag = true;
		boolean flag1 = true;
		int j = Math.min(m - 1, n);
		int k = Math.max(0, Math.min(n - 2, m));
		for (int l = 0; l < Math.max(j, k); l++) {
			if (l < j) {
				s[l] = 0.0D;
				for (int i1 = l; i1 < m; i1++)
					s[l] = Maths.hypot(s[l], ad[i1][l]);

				if (s[l] != 0.0D) {
					if (ad[l][l] < 0.0D)
						s[l] = -s[l];
					for (int l1 = l; l1 < m; l1++)
						ad[l1][l] /= s[l];

					ad[l][l]++;
				}
				s[l] = -s[l];
			}
			for (int j1 = l + 1; j1 < n; j1++) {
				if ((l < j) & (s[l] != 0.0D)) {
					double d = 0.0D;
					for (int j5 = l; j5 < m; j5++)
						d += ad[j5][l] * ad[j5][j1];

					d = -d / ad[l][l];
					for (int j6 = l; j6 < m; j6++)
						ad[j6][j1] += d * ad[j6][l];

				}
				ad1[j1] = ad[l][j1];
			}

			if (flag & (l < j)) {
				for (int i2 = l; i2 < m; i2++)
					U[i2][l] = ad[i2][l];

			}
			if (l < k) {
				ad1[l] = 0.0D;
				for (int j2 = l + 1; j2 < n; j2++)
					ad1[l] = Maths.hypot(ad1[l], ad1[j2]);

				if (ad1[l] != 0.0D) {
					if (ad1[l + 1] < 0.0D)
						ad1[l] = -ad1[l];
					for (int j3 = l + 1; j3 < n; j3++)
						ad1[j3] /= ad1[l];

					ad1[l + 1]++;
				}
				ad1[l] = -ad1[l];
				if ((l + 1 < m) & (ad1[l] != 0.0D)) {
					for (int k3 = l + 1; k3 < m; k3++)
						ad2[k3] = 0.0D;

					for (int k5 = l + 1; k5 < n; k5++) {
						for (int k6 = l + 1; k6 < m; k6++)
							ad2[k6] += ad1[k5] * ad[k6][k5];

					}

					for (int l6 = l + 1; l6 < n; l6++) {
						double d4 = -ad1[l6] / ad1[l + 1];
						for (int k8 = l + 1; k8 < m; k8++)
							ad[k8][l6] += d4 * ad2[k8];

					}

				}
				if (flag1) {
					for (int l3 = l + 1; l3 < n; l3++)
						V[l3][l] = ad1[l3];

				}
			}
		}

		int k1 = Math.min(n, m + 1);
		if (j < n)
			s[j] = ad[j][j];
		if (m < k1)
			s[k1 - 1] = 0.0D;
		if (k + 1 < k1)
			ad1[k] = ad[k][k1 - 1];
		ad1[k1 - 1] = 0.0D;
		if (flag) {
			for (int k2 = j; k2 < i; k2++) {
				for (int i4 = 0; i4 < m; i4++)
					U[i4][k2] = 0.0D;

				U[k2][k2] = 1.0D;
			}

			for (int j4 = j - 1; j4 >= 0; j4--)
				if (s[j4] != 0.0D) {
					for (int l5 = j4 + 1; l5 < i; l5++) {
						double d3 = 0.0D;
						for (int i8 = j4; i8 < m; i8++)
							d3 += U[i8][j4] * U[i8][l5];

						d3 = -d3 / U[j4][j4];
						for (int l8 = j4; l8 < m; l8++)
							U[l8][l5] += d3 * U[l8][j4];

					}

					for (int i7 = j4; i7 < m; i7++)
						U[i7][j4] = -U[i7][j4];

					U[j4][j4] = 1.0D + U[j4][j4];
					for (int j7 = 0; j7 < j4 - 1; j7++)
						U[j7][j4] = 0.0D;

				} else {
					for (int i6 = 0; i6 < m; i6++)
						U[i6][j4] = 0.0D;

					U[j4][j4] = 1.0D;
				}

		}
		if (flag1) {
			for (int l2 = n - 1; l2 >= 0; l2--) {
				if ((l2 < k) & (ad1[l2] != 0.0D)) {
					for (int k4 = l2 + 1; k4 < i; k4++) {
						double d1 = 0.0D;
						for (int k7 = l2 + 1; k7 < n; k7++)
							d1 += V[k7][l2] * V[k7][k4];

						d1 = -d1 / V[l2 + 1][l2];
						for (int j8 = l2 + 1; j8 < n; j8++)
							V[j8][k4] += d1 * V[j8][l2];

					}

				}
				for (int l4 = 0; l4 < n; l4++)
					V[l4][l2] = 0.0D;

				V[l2][l2] = 1.0D;
			}

		}
		int i3 = k1 - 1;
		int i5 = 0;
		double d2 = Math.pow(2D, -52D);
		while (k1 > 0)  {
			int l7;
			for (l7 = k1 - 2; l7 >= -1; l7--) {
				if (l7 == -1)
					break;
				if (Math.abs(ad1[l7]) > d2 * (Math.abs(s[l7]) + Math.abs(s[l7 + 1])))
					continue;
				ad1[l7] = 0.0D;
				break;
			}

			byte byte0;
			if (l7 == k1 - 2) {
				byte0 = 4;
			} else {
				int i9;
				for (i9 = k1 - 1; i9 >= l7; i9--) {
					if (i9 == l7)
						break;
					double d11 = (i9 == k1 ? 0.0D : Math.abs(ad1[i9])) + (i9 == l7 + 1 ? 0.0D : Math.abs(ad1[i9 - 1]));
					if (Math.abs(s[i9]) > d2 * d11)
						continue;
					s[i9] = 0.0D;
					break;
				}

				if (i9 == l7)
					byte0 = 3;
				else
				if (i9 == k1 - 1) {
					byte0 = 1;
				} else {
					byte0 = 2;
					l7 = i9;
				}
			}
			l7++;
			switch (byte0) {
			default:
				break;

			case 1: // '\001'
				double d5 = ad1[k1 - 2];
				ad1[k1 - 2] = 0.0D;
				for (int k9 = k1 - 2; k9 >= l7; k9--) {
					double d13 = Maths.hypot(s[k9], d5);
					double d18 = s[k9] / d13;
					double d21 = d5 / d13;
					s[k9] = d13;
					if (k9 != l7) {
						d5 = -d21 * ad1[k9 - 1];
						ad1[k9 - 1] = d18 * ad1[k9 - 1];
					}
					if (flag1) {
						for (int k10 = 0; k10 < n; k10++) {
							double d14 = d18 * V[k10][k9] + d21 * V[k10][k1 - 1];
							V[k10][k1 - 1] = -d21 * V[k10][k9] + d18 * V[k10][k1 - 1];
							V[k10][k9] = d14;
						}

					}
				}

				break;

			case 2: // '\002'
				double d6 = ad1[l7 - 1];
				ad1[l7 - 1] = 0.0D;
				for (int l9 = l7; l9 < k1; l9++) {
					double d15 = Maths.hypot(s[l9], d6);
					double d19 = s[l9] / d15;
					double d22 = d6 / d15;
					s[l9] = d15;
					d6 = -d22 * ad1[l9];
					ad1[l9] = d19 * ad1[l9];
					if (flag) {
						for (int l10 = 0; l10 < m; l10++) {
							double d16 = d19 * U[l10][l9] + d22 * U[l10][l7 - 1];
							U[l10][l7 - 1] = -d22 * U[l10][l9] + d19 * U[l10][l7 - 1];
							U[l10][l9] = d16;
						}

					}
				}

				break;

			case 3: // '\003'
				double d7 = Math.max(Math.max(Math.max(Math.max(Math.abs(s[k1 - 1]), Math.abs(s[k1 - 2])), Math.abs(ad1[k1 - 2])), Math.abs(s[l7])), Math.abs(ad1[l7]));
				double d12 = s[k1 - 1] / d7;
				double d17 = s[k1 - 2] / d7;
				double d20 = ad1[k1 - 2] / d7;
				double d23 = s[l7] / d7;
				double d24 = ad1[l7] / d7;
				double d25 = ((d17 + d12) * (d17 - d12) + d20 * d20) / 2D;
				double d26 = d12 * d20 * (d12 * d20);
				double d27 = 0.0D;
				if ((d25 != 0.0D) | (d26 != 0.0D)) {
					d27 = Math.sqrt(d25 * d25 + d26);
					if (d25 < 0.0D)
						d27 = -d27;
					d27 = d26 / (d25 + d27);
				}
				double d28 = (d23 + d12) * (d23 - d12) + d27;
				double d29 = d23 * d24;
				for (int i11 = l7; i11 < k1 - 1; i11++) {
					double d30 = Maths.hypot(d28, d29);
					double d32 = d28 / d30;
					double d33 = d29 / d30;
					if (i11 != l7)
						ad1[i11 - 1] = d30;
					d28 = d32 * s[i11] + d33 * ad1[i11];
					ad1[i11] = d32 * ad1[i11] - d33 * s[i11];
					d29 = d33 * s[i11 + 1];
					s[i11 + 1] = d32 * s[i11 + 1];
					if (flag1) {
						for (int j11 = 0; j11 < n; j11++) {
							d30 = d32 * V[j11][i11] + d33 * V[j11][i11 + 1];
							V[j11][i11 + 1] = -d33 * V[j11][i11] + d32 * V[j11][i11 + 1];
							V[j11][i11] = d30;
						}

					}
					d30 = Maths.hypot(d28, d29);
					d32 = d28 / d30;
					d33 = d29 / d30;
					s[i11] = d30;
					d28 = d32 * ad1[i11] + d33 * s[i11 + 1];
					s[i11 + 1] = -d33 * ad1[i11] + d32 * s[i11 + 1];
					d29 = d33 * ad1[i11 + 1];
					ad1[i11 + 1] = d32 * ad1[i11 + 1];
					if (flag && i11 < m - 1) {
						for (int k11 = 0; k11 < m; k11++) {
							double d31 = d32 * U[k11][i11] + d33 * U[k11][i11 + 1];
							U[k11][i11 + 1] = -d33 * U[k11][i11] + d32 * U[k11][i11 + 1];
							U[k11][i11] = d31;
						}

					}
				}

				ad1[k1 - 2] = d28;
				i5++;
				break;

			case 4: // '\004'
				if (s[l7] <= 0.0D) {
					s[l7] = s[l7] >= 0.0D ? 0.0D : -s[l7];
					if (flag1) {
						for (int j9 = 0; j9 <= i3; j9++)
							V[j9][l7] = -V[j9][l7];

					}
				}
				for (; l7 < i3; l7++) {
					if (s[l7] >= s[l7 + 1])
						break;
					double d8 = s[l7];
					s[l7] = s[l7 + 1];
					s[l7 + 1] = d8;
					if (flag1 && l7 < n - 1) {
						for (int i10 = 0; i10 < n; i10++) {
							double d9 = V[i10][l7 + 1];
							V[i10][l7 + 1] = V[i10][l7];
							V[i10][l7] = d9;
						}

					}
					if (flag && l7 < m - 1) {
						for (int j10 = 0; j10 < m; j10++) {
							double d10 = U[j10][l7 + 1];
							U[j10][l7 + 1] = U[j10][l7];
							U[j10][l7] = d10;
						}

					}
				}

				i5 = 0;
				k1--;
				break;
			}
		}
	}

	public double cond() {
		return s[0] / s[Math.min(m, n) - 1];
	}

	public Matrix getS() {
		Matrix matrix = new Matrix(n, n);
		double ad[][] = matrix.getArray();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				ad[i][j] = 0.0D;

			ad[i][i] = s[i];
		}

		return matrix;
	}

	public double[] getSingularValues() {
		return s;
	}

	public Matrix getU() {
		return new Matrix(U, m, Math.min(m + 1, n));
	}

	public Matrix getV() {
		return new Matrix(V, n, n);
	}

	public double norm2() {
		return s[0];
	}

	public int rank() {
		double d = Math.pow(2D, -52D);
		double d1 = (double)Math.max(m, n) * s[0] * d;
		int i = 0;
		for (int j = 0; j < s.length; j++)
			if (s[j] > d1)
				i++;

		return i;
	}
}
