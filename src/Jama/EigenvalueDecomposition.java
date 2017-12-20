package Jama;

import Jama.util.Maths;
import java.io.Serializable;

// Referenced classes of package Jama:
//			Matrix

public class EigenvalueDecomposition
	implements Serializable {

	private int n;
	private boolean issymmetric;
	private double d[];
	private double e[];
	private double V[][];
	private double H[][];
	private double ort[];
	private transient double cdivr;
	private transient double cdivi;

	public EigenvalueDecomposition(Matrix matrix) {
		double ad[][] = matrix.getArray();
		n = matrix.getColumnDimension();
		V = new double[n][n];
		d = new double[n];
		e = new double[n];
		issymmetric = true;
		for (int i = 0; (i < n) & issymmetric; i++) {
			for (int j = 0; (j < n) & issymmetric; j++)
				issymmetric = ad[j][i] == ad[i][j];

		}

		if (issymmetric) {
			for (int k = 0; k < n; k++) {
				for (int i1 = 0; i1 < n; i1++)
					V[k][i1] = ad[k][i1];

			}

			tred2();
			tql2();
		} else {
			H = new double[n][n];
			ort = new double[n];
			for (int l = 0; l < n; l++) {
				for (int j1 = 0; j1 < n; j1++)
					H[j1][l] = ad[j1][l];

			}

			orthes();
			hqr2();
		}
	}

	private void cdiv(double d1, double d2, double d3, double d4) {
		if (Math.abs(d3) > Math.abs(d4)) {
			double d5 = d4 / d3;
			double d7 = d3 + d5 * d4;
			cdivr = (d1 + d5 * d2) / d7;
			cdivi = (d2 - d5 * d1) / d7;
		} else {
			double d6 = d3 / d4;
			double d8 = d4 + d6 * d3;
			cdivr = (d6 * d1 + d2) / d8;
			cdivi = (d6 * d2 - d1) / d8;
		}
	}

	public Matrix getD() {
		Matrix matrix = new Matrix(n, n);
		double ad[][] = matrix.getArray();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				ad[i][j] = 0.0D;

			ad[i][i] = d[i];
			if (e[i] > 0.0D)
				ad[i][i + 1] = e[i];
			else
			if (e[i] < 0.0D)
				ad[i][i - 1] = e[i];
		}

		return matrix;
	}

	public double[] getImagEigenvalues() {
		return e;
	}

	public double[] getRealEigenvalues() {
		return d;
	}

	public Matrix getV() {
		return new Matrix(V, n, n);
	}

	private void hqr2() {
		int i = n;
		int j = i - 1;
		int l = 0;
		int i1 = i - 1;
		double d1 = Math.pow(2D, -52D);
		double d2 = 0.0D;
		double d3 = 0.0D;
		double d5 = 0.0D;
		double d7 = 0.0D;
		double d8 = 0.0D;
		double d9 = 0.0D;
		double d26 = 0.0D;
		for (int j1 = 0; j1 < i; j1++) {
			if ((j1 < l) | (j1 > i1)) {
				d[j1] = H[j1][j1];
				e[j1] = 0.0D;
			}
			for (int k1 = Math.max(j1 - 1, 0); k1 < i; k1++)
				d26 += Math.abs(H[j1][k1]);

		}

		int l1 = 0;
		while (j >= l)  {
			int i2;
			for (i2 = j; i2 > l; i2--) {
				d8 = Math.abs(H[i2 - 1][i2 - 1]) + Math.abs(H[i2][i2]);
				if (d8 == 0.0D)
					d8 = d26;
				if (Math.abs(H[i2][i2 - 1]) < d1 * d8)
					break;
			}

			if (i2 == j) {
				H[j][j] = H[j][j] + d2;
				d[j] = H[j][j];
				e[j] = 0.0D;
				j--;
				l1 = 0;
			} else
			if (i2 == j - 1) {
				double d14 = H[j][j - 1] * H[j - 1][j];
				d3 = (H[j - 1][j - 1] - H[j][j]) / 2D;
				d5 = d3 * d3 + d14;
				d9 = Math.sqrt(Math.abs(d5));
				H[j][j] = H[j][j] + d2;
				H[j - 1][j - 1] = H[j - 1][j - 1] + d2;
				double d18 = H[j][j];
				if (d5 >= 0.0D) {
					if (d3 >= 0.0D)
						d9 = d3 + d9;
					else
						d9 = d3 - d9;
					d[j - 1] = d18 + d9;
					d[j] = d[j - 1];
					if (d9 != 0.0D)
						d[j] = d18 - d14 / d9;
					e[j - 1] = 0.0D;
					e[j] = 0.0D;
					d18 = H[j][j - 1];
					d8 = Math.abs(d18) + Math.abs(d9);
					d3 = d18 / d8;
					d5 = d9 / d8;
					d7 = Math.sqrt(d3 * d3 + d5 * d5);
					d3 /= d7;
					d5 /= d7;
					for (int i3 = j - 1; i3 < i; i3++) {
						d9 = H[j - 1][i3];
						H[j - 1][i3] = d5 * d9 + d3 * H[j][i3];
						H[j][i3] = d5 * H[j][i3] - d3 * d9;
					}

					for (int i5 = 0; i5 <= j; i5++) {
						d9 = H[i5][j - 1];
						H[i5][j - 1] = d5 * d9 + d3 * H[i5][j];
						H[i5][j] = d5 * H[i5][j] - d3 * d9;
					}

					for (int i6 = l; i6 <= i1; i6++) {
						d9 = V[i6][j - 1];
						V[i6][j - 1] = d5 * d9 + d3 * V[i6][j];
						V[i6][j] = d5 * V[i6][j] - d3 * d9;
					}

				} else {
					d[j - 1] = d18 + d3;
					d[j] = d18 + d3;
					e[j - 1] = d9;
					e[j] = -d9;
				}
				j -= 2;
				l1 = 0;
			} else {
				double d19 = H[j][j];
				double d22 = 0.0D;
				double d15 = 0.0D;
				if (i2 < j) {
					d22 = H[j - 1][j - 1];
					d15 = H[j][j - 1] * H[j - 1][j];
				}
				if (l1 == 10) {
					d2 += d19;
					for (int j3 = l; j3 <= j; j3++)
						H[j3][j3] -= d19;

					d8 = Math.abs(H[j][j - 1]) + Math.abs(H[j - 1][j - 2]);
					d19 = d22 = 0.75D * d8;
					d15 = -0.4375D * d8 * d8;
				}
				if (l1 == 30) {
					d8 = (d22 - d19) / 2D;
					d8 = d8 * d8 + d15;
					if (d8 > 0.0D) {
						d8 = Math.sqrt(d8);
						if (d22 < d19)
							d8 = -d8;
						d8 = d19 - d15 / ((d22 - d19) / 2D + d8);
						for (int k3 = l; k3 <= j; k3++)
							H[k3][k3] -= d8;

						d2 += d8;
						d19 = d22 = d15 = 0.96399999999999997D;
					}
				}
				l1++;
				int l3;
				for (l3 = j - 2; l3 >= i2; l3--) {
					d9 = H[l3][l3];
					d7 = d19 - d9;
					d8 = d22 - d9;
					d3 = (d7 * d8 - d15) / H[l3 + 1][l3] + H[l3][l3 + 1];
					d5 = H[l3 + 1][l3 + 1] - d9 - d7 - d8;
					d7 = H[l3 + 2][l3 + 1];
					d8 = Math.abs(d3) + Math.abs(d5) + Math.abs(d7);
					d3 /= d8;
					d5 /= d8;
					d7 /= d8;
					if (l3 == i2 || Math.abs(H[l3][l3 - 1]) * (Math.abs(d5) + Math.abs(d7)) < d1 * (Math.abs(d3) * (Math.abs(H[l3 - 1][l3 - 1]) + Math.abs(d9) + Math.abs(H[l3 + 1][l3 + 1]))))
						break;
				}

				for (int j5 = l3 + 2; j5 <= j; j5++) {
					H[j5][j5 - 2] = 0.0D;
					if (j5 > l3 + 2)
						H[j5][j5 - 3] = 0.0D;
				}

				for (int j6 = l3; j6 <= j - 1; j6++) {
					boolean flag = j6 != j - 1;
					if (j6 != l3) {
						d3 = H[j6][j6 - 1];
						d5 = H[j6 + 1][j6 - 1];
						d7 = flag ? H[j6 + 2][j6 - 1] : 0.0D;
						d19 = Math.abs(d3) + Math.abs(d5) + Math.abs(d7);
						if (d19 != 0.0D) {
							d3 /= d19;
							d5 /= d19;
							d7 /= d19;
						}
					}
					if (d19 == 0.0D)
						break;
					d8 = Math.sqrt(d3 * d3 + d5 * d5 + d7 * d7);
					if (d3 < 0.0D)
						d8 = -d8;
					if (d8 != 0.0D) {
						if (j6 != l3)
							H[j6][j6 - 1] = -d8 * d19;
						else
						if (i2 != l3)
							H[j6][j6 - 1] = -H[j6][j6 - 1];
						d3 += d8;
						d19 = d3 / d8;
						double d23 = d5 / d8;
						d9 = d7 / d8;
						d5 /= d3;
						d7 /= d3;
						for (int i7 = j6; i7 < i; i7++) {
							d3 = H[j6][i7] + d5 * H[j6 + 1][i7];
							if (flag) {
								d3 += d7 * H[j6 + 2][i7];
								H[j6 + 2][i7] = H[j6 + 2][i7] - d3 * d9;
							}
							H[j6][i7] = H[j6][i7] - d3 * d19;
							H[j6 + 1][i7] = H[j6 + 1][i7] - d3 * d23;
						}

						for (int j7 = 0; j7 <= Math.min(j, j6 + 3); j7++) {
							d3 = d19 * H[j7][j6] + d23 * H[j7][j6 + 1];
							if (flag) {
								d3 += d9 * H[j7][j6 + 2];
								H[j7][j6 + 2] = H[j7][j6 + 2] - d3 * d7;
							}
							H[j7][j6] = H[j7][j6] - d3;
							H[j7][j6 + 1] = H[j7][j6 + 1] - d3 * d5;
						}

						for (int k7 = l; k7 <= i1; k7++) {
							d3 = d19 * V[k7][j6] + d23 * V[k7][j6 + 1];
							if (flag) {
								d3 += d9 * V[k7][j6 + 2];
								V[k7][j6 + 2] = V[k7][j6 + 2] - d3 * d7;
							}
							V[k7][j6] = V[k7][j6] - d3;
							V[k7][j6 + 1] = V[k7][j6 + 1] - d3 * d5;
						}

					}
				}

			}
		}
		if (d26 == 0.0D)
			return;
		for (int k = i - 1; k >= 0; k--) {
			double d4 = d[k];
			double d6 = e[k];
			if (d6 == 0.0D) {
				int j2 = k;
				H[k][k] = 1.0D;
				for (int i4 = k - 1; i4 >= 0; i4--) {
					double d16 = H[i4][i4] - d4;
					d7 = 0.0D;
					for (int k5 = j2; k5 <= k; k5++)
						d7 += H[i4][k5] * H[k5][k];

					if (e[i4] < 0.0D) {
						d9 = d16;
						d8 = d7;
					} else {
						j2 = i4;
						if (e[i4] == 0.0D) {
							if (d16 != 0.0D)
								H[i4][k] = -d7 / d16;
							else
								H[i4][k] = -d7 / (d1 * d26);
						} else {
							double d20 = H[i4][i4 + 1];
							double d24 = H[i4 + 1][i4];
							d6 = (d[i4] - d4) * (d[i4] - d4) + e[i4] * e[i4];
							double d11 = (d20 * d8 - d9 * d7) / d6;
							H[i4][k] = d11;
							if (Math.abs(d20) > Math.abs(d9))
								H[i4 + 1][k] = (-d7 - d16 * d11) / d20;
							else
								H[i4 + 1][k] = (-d8 - d24 * d11) / d9;
						}
						double d12 = Math.abs(H[i4][k]);
						if (d1 * d12 * d12 > 1.0D) {
							for (int k6 = i4; k6 <= k; k6++)
								H[k6][k] = H[k6][k] / d12;

						}
					}
				}

			} else
			if (d6 < 0.0D) {
				int k2 = k - 1;
				if (Math.abs(H[k][k - 1]) > Math.abs(H[k - 1][k])) {
					H[k - 1][k - 1] = d6 / H[k][k - 1];
					H[k - 1][k] = -(H[k][k] - d4) / H[k][k - 1];
				} else {
					cdiv(0.0D, -H[k - 1][k], H[k - 1][k - 1] - d4, d6);
					H[k - 1][k - 1] = cdivr;
					H[k - 1][k] = cdivi;
				}
				H[k][k - 1] = 0.0D;
				H[k][k] = 1.0D;
				for (int j4 = k - 2; j4 >= 0; j4--) {
					double d27 = 0.0D;
					double d28 = 0.0D;
					for (int l7 = k2; l7 <= k; l7++) {
						d27 += H[j4][l7] * H[l7][k - 1];
						d28 += H[j4][l7] * H[l7][k];
					}

					double d17 = H[j4][j4] - d4;
					if (e[j4] < 0.0D) {
						d9 = d17;
						d7 = d27;
						d8 = d28;
					} else {
						k2 = j4;
						if (e[j4] == 0.0D) {
							cdiv(-d27, -d28, d17, d6);
							H[j4][k - 1] = cdivr;
							H[j4][k] = cdivi;
						} else {
							double d21 = H[j4][j4 + 1];
							double d25 = H[j4 + 1][j4];
							double d29 = ((d[j4] - d4) * (d[j4] - d4) + e[j4] * e[j4]) - d6 * d6;
							double d30 = (d[j4] - d4) * 2D * d6;
							if ((d29 == 0.0D) & (d30 == 0.0D))
								d29 = d1 * d26 * (Math.abs(d17) + Math.abs(d6) + Math.abs(d21) + Math.abs(d25) + Math.abs(d9));
							cdiv((d21 * d7 - d9 * d27) + d6 * d28, d21 * d8 - d9 * d28 - d6 * d27, d29, d30);
							H[j4][k - 1] = cdivr;
							H[j4][k] = cdivi;
							if (Math.abs(d21) > Math.abs(d9) + Math.abs(d6)) {
								H[j4 + 1][k - 1] = ((-d27 - d17 * H[j4][k - 1]) + d6 * H[j4][k]) / d21;
								H[j4 + 1][k] = (-d28 - d17 * H[j4][k] - d6 * H[j4][k - 1]) / d21;
							} else {
								cdiv(-d7 - d25 * H[j4][k - 1], -d8 - d25 * H[j4][k], d9, d6);
								H[j4 + 1][k - 1] = cdivr;
								H[j4 + 1][k] = cdivi;
							}
						}
						double d13 = Math.max(Math.abs(H[j4][k - 1]), Math.abs(H[j4][k]));
						if (d1 * d13 * d13 > 1.0D) {
							for (int i8 = j4; i8 <= k; i8++) {
								H[i8][k - 1] = H[i8][k - 1] / d13;
								H[i8][k] = H[i8][k] / d13;
							}

						}
					}
				}

			}
		}

		for (int l2 = 0; l2 < i; l2++)
			if ((l2 < l) | (l2 > i1)) {
				for (int k4 = l2; k4 < i; k4++)
					V[l2][k4] = H[l2][k4];

			}

		for (int l4 = i - 1; l4 >= l; l4--) {
			for (int l5 = l; l5 <= i1; l5++) {
				double d10 = 0.0D;
				for (int l6 = l; l6 <= Math.min(l4, i1); l6++)
					d10 += V[l5][l6] * H[l6][l4];

				V[l5][l4] = d10;
			}

		}

	}

	private void orthes() {
		int i = 0;
		int j = n - 1;
		for (int k = i + 1; k <= j - 1; k++) {
			double d1 = 0.0D;
			for (int k1 = k; k1 <= j; k1++)
				d1 += Math.abs(H[k1][k - 1]);

			if (d1 != 0.0D) {
				double d2 = 0.0D;
				for (int j2 = j; j2 >= k; j2--) {
					ort[j2] = H[j2][k - 1] / d1;
					d2 += ort[j2] * ort[j2];
				}

				double d4 = Math.sqrt(d2);
				if (ort[k] > 0.0D)
					d4 = -d4;
				d2 -= ort[k] * d4;
				ort[k] = ort[k] - d4;
				for (int i3 = k; i3 < n; i3++) {
					double d5 = 0.0D;
					for (int k3 = j; k3 >= k; k3--)
						d5 += ort[k3] * H[k3][i3];

					d5 /= d2;
					for (int l3 = k; l3 <= j; l3++)
						H[l3][i3] -= d5 * ort[l3];

				}

				for (int j3 = 0; j3 <= j; j3++) {
					double d6 = 0.0D;
					for (int i4 = j; i4 >= k; i4--)
						d6 += ort[i4] * H[j3][i4];

					d6 /= d2;
					for (int j4 = k; j4 <= j; j4++)
						H[j3][j4] -= d6 * ort[j4];

				}

				ort[k] = d1 * ort[k];
				H[k][k - 1] = d1 * d4;
			}
		}

		for (int l = 0; l < n; l++) {
			for (int i1 = 0; i1 < n; i1++)
				V[l][i1] = l != i1 ? 0.0D : 1.0D;

		}

		for (int j1 = j - 1; j1 >= i + 1; j1--)
			if (H[j1][j1 - 1] != 0.0D) {
				for (int l1 = j1 + 1; l1 <= j; l1++)
					ort[l1] = H[l1][j1 - 1];

				for (int i2 = j1; i2 <= j; i2++) {
					double d3 = 0.0D;
					for (int k2 = j1; k2 <= j; k2++)
						d3 += ort[k2] * V[k2][i2];

					d3 = d3 / ort[j1] / H[j1][j1 - 1];
					for (int l2 = j1; l2 <= j; l2++)
						V[l2][i2] += d3 * ort[l2];

				}

			}

	}

	private void tql2() {
		for (int i = 1; i < n; i++)
			e[i - 1] = e[i];

		e[n - 1] = 0.0D;
		double d1 = 0.0D;
		double d2 = 0.0D;
		double d3 = Math.pow(2D, -52D);
		for (int j = 0; j < n; j++) {
			d2 = Math.max(d2, Math.abs(d[j]) + Math.abs(e[j]));
			int k;
			for (k = j; k < n; k++)
				if (Math.abs(e[k]) <= d3 * d2)
					break;

			if (k > j) {
				int i1 = 0;
				do {
					i1++;
					double d4 = d[j];
					double d8 = (d[j + 1] - d4) / (2D * e[j]);
					double d9 = Maths.hypot(d8, 1.0D);
					if (d8 < 0.0D)
						d9 = -d9;
					d[j] = e[j] / (d8 + d9);
					d[j + 1] = e[j] * (d8 + d9);
					double d11 = d[j + 1];
					double d12 = d4 - d[j];
					for (int i2 = j + 2; i2 < n; i2++)
						d[i2] -= d12;

					d1 += d12;
					d8 = d[k];
					double d15 = 1.0D;
					double d16 = d15;
					double d17 = d15;
					double d18 = e[j + 1];
					double d19 = 0.0D;
					double d20 = 0.0D;
					for (int j2 = k - 1; j2 >= j; j2--) {
						d17 = d16;
						d16 = d15;
						d20 = d19;
						double d5 = d15 * e[j2];
						double d13 = d15 * d8;
						double d10 = Maths.hypot(d8, e[j2]);
						e[j2 + 1] = d19 * d10;
						d19 = e[j2] / d10;
						d15 = d8 / d10;
						d8 = d15 * d[j2] - d19 * d5;
						d[j2 + 1] = d13 + d19 * (d15 * d5 + d19 * d[j2]);
						for (int k2 = 0; k2 < n; k2++) {
							double d14 = V[k2][j2 + 1];
							V[k2][j2 + 1] = d19 * V[k2][j2] + d15 * d14;
							V[k2][j2] = d15 * V[k2][j2] - d19 * d14;
						}

					}

					d8 = (-d19 * d20 * d17 * d18 * e[j]) / d11;
					e[j] = d19 * d8;
					d[j] = d15 * d8;
				} while (Math.abs(e[j]) > d3 * d2);
			}
			d[j] = d[j] + d1;
			e[j] = 0.0D;
		}

		for (int l = 0; l < n - 1; l++) {
			int j1 = l;
			double d6 = d[l];
			for (int k1 = l + 1; k1 < n; k1++)
				if (d[k1] < d6) {
					j1 = k1;
					d6 = d[k1];
				}

			if (j1 != l) {
				d[j1] = d[l];
				d[l] = d6;
				for (int l1 = 0; l1 < n; l1++) {
					double d7 = V[l1][l];
					V[l1][l] = V[l1][j1];
					V[l1][j1] = d7;
				}

			}
		}

	}

	private void tred2() {
		for (int i = 0; i < n; i++)
			d[i] = V[n - 1][i];

		for (int j = n - 1; j > 0; j--) {
			double d1 = 0.0D;
			double d3 = 0.0D;
			for (int k1 = 0; k1 < j; k1++)
				d1 += Math.abs(d[k1]);

			if (d1 == 0.0D) {
				e[j] = d[j - 1];
				for (int i2 = 0; i2 < j; i2++) {
					d[i2] = V[j - 1][i2];
					V[j][i2] = 0.0D;
					V[i2][j] = 0.0D;
				}

			} else {
				for (int j2 = 0; j2 < j; j2++) {
					d[j2] /= d1;
					d3 += d[j2] * d[j2];
				}

				double d5 = d[j - 1];
				double d7 = Math.sqrt(d3);
				if (d5 > 0.0D)
					d7 = -d7;
				e[j] = d1 * d7;
				d3 -= d5 * d7;
				d[j - 1] = d5 - d7;
				for (int i3 = 0; i3 < j; i3++)
					e[i3] = 0.0D;

				for (int j3 = 0; j3 < j; j3++) {
					d5 = d[j3];
					V[j3][j] = d5;
					double d8 = e[j3] + V[j3][j3] * d5;
					for (int k3 = j3 + 1; k3 <= j - 1; k3++) {
						d8 += V[k3][j3] * d[k3];
						e[k3] += V[k3][j3] * d5;
					}

					e[j3] = d8;
				}

				d5 = 0.0D;
				for (int l3 = 0; l3 < j; l3++) {
					e[l3] /= d3;
					d5 += e[l3] * d[l3];
				}

				double d10 = d5 / (d3 + d3);
				for (int i4 = 0; i4 < j; i4++)
					e[i4] -= d10 * d[i4];

				for (int j4 = 0; j4 < j; j4++) {
					double d6 = d[j4];
					double d9 = e[j4];
					for (int k4 = j4; k4 <= j - 1; k4++)
						V[k4][j4] -= d6 * e[k4] + d9 * d[k4];

					d[j4] = V[j - 1][j4];
					V[j][j4] = 0.0D;
				}

			}
			d[j] = d3;
		}

		for (int k = 0; k < n - 1; k++) {
			V[n - 1][k] = V[k][k];
			V[k][k] = 1.0D;
			double d2 = d[k + 1];
			if (d2 != 0.0D) {
				for (int i1 = 0; i1 <= k; i1++)
					d[i1] = V[i1][k + 1] / d2;

				for (int l1 = 0; l1 <= k; l1++) {
					double d4 = 0.0D;
					for (int k2 = 0; k2 <= k; k2++)
						d4 += V[k2][k + 1] * V[k2][l1];

					for (int l2 = 0; l2 <= k; l2++)
						V[l2][l1] -= d4 * d[l2];

				}

			}
			for (int j1 = 0; j1 <= k; j1++)
				V[j1][k + 1] = 0.0D;

		}

		for (int l = 0; l < n; l++) {
			d[l] = V[n - 1][l];
			V[n - 1][l] = 0.0D;
		}

		V[n - 1][n - 1] = 1.0D;
		e[0] = 0.0D;
	}
}
