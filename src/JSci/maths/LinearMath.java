package JSci.maths;


// Referenced classes of package JSci.maths:
//			AbstractMath, Complex, ComplexMatrix, ComplexVector, 
//			DoubleMatrix, DoubleSquareMatrix, DoubleTridiagonalMatrix, DoubleVector, 
//			MaximumIterationsExceededException, ComplexSquareMatrix

public final class LinearMath extends AbstractMath {

	private LinearMath() {
	}

	public static DoubleVector solve(DoubleSquareMatrix adoublesquarematrix[], DoubleVector doublevector) {
		int i = doublevector.dimension();
		double ad[] = new double[i];
		double ad1[] = new double[i];
		for (int j = 0; j < i; j++) {
			double d = doublevector.getComponent(j);
			for (int l = 0; l < j; l++)
				d -= adoublesquarematrix[0].getElement(j, l) * ad[l];

			ad[j] = d / adoublesquarematrix[0].getElement(j, j);
		}

		for (int k = i - 1; k >= 0; k--) {
			double d1 = ad[k];
			for (int i1 = k + 1; i1 < i; i1++)
				d1 -= adoublesquarematrix[1].getElement(k, i1) * ad1[i1];

			ad1[k] = d1 / adoublesquarematrix[1].getElement(k, k);
		}

		return new DoubleVector(ad1);
	}

	public static DoubleVector solveGMRes(DoubleMatrix doublematrix, DoubleVector doublevector, int i, double d) throws MaximumIterationsExceededException {
		if (i <= 0)
			throw new IllegalArgumentException("Number of allowed iterations must be a positive integer: " + i + " <= 0.");
		if (d < 0.0D)
			throw new IllegalArgumentException("Tolerance must be positive or zero: " + d + " < 0.");
		int j = doublematrix.rows();
		int i1 = 1;
		double ad[] = new double[j + 1];
		double ad1[][] = new double[j + 1][2];
		double ad2[] = new double[2];
		DoubleVector doublevector2 = new DoubleVector(doublematrix.rows());
		double d2 = doublevector.norm();
		DoubleVector doublevector3 = doublevector.subtract(doublematrix.multiply(doublevector2));
		double d3 = doublevector3.norm();
		if (d2 == 0.0D)
			d2 = 1.0D;
		double d1;
		if ((d1 = doublevector3.norm() / d2) <= d) {
			d = d1;
			i = 0;
			throw new IllegalArgumentException("There is a bug.");
		}
		DoubleVector adoublevector[] = new DoubleVector[j + 1];
		DoubleMatrix doublematrix1 = new DoubleMatrix(j + 1, j);
		while (i1 <= i)  {
			adoublevector[0] = doublevector3.scalarMultiply(1.0D / d3);
			for (int k = 0; k < j + 1; k++)
				ad[k] = 0.0D;

			ad[0] = d3;
			for (int l = 0; l < j && i1 <= i; i1++) {
				DoubleVector doublevector1 = doublematrix.multiply(adoublevector[l]);
				for (int j1 = 0; j1 <= l; j1++) {
					doublematrix1.matrix[j1][l] = doublevector1.scalarProduct(adoublevector[j1]);
					doublevector1 = doublevector1.subtract(adoublevector[j1].scalarMultiply(doublematrix1.matrix[j1][l]));
				}

				doublematrix1.matrix[l + 1][l] = doublevector1.norm();
				adoublevector[l + 1] = doublevector1.scalarMultiply(1.0D / doublematrix1.matrix[l + 1][l]);
				for (int k1 = 0; k1 < l; k1++) {
					double ad3[] = applyPlaneRotation(doublematrix1.matrix[k1][l], doublematrix1.matrix[k1 + 1][l], ad1[k1][0], ad1[k1][1]);
					doublematrix1.matrix[k1][l] = ad3[0];
					doublematrix1.matrix[k1 + 1][l] = ad3[1];
				}

				ad1[l] = generatePlaneRotation(doublematrix1.matrix[l][l], doublematrix1.matrix[l + 1][l]);
				double ad4[] = applyPlaneRotation(doublematrix1.matrix[l][l], doublematrix1.matrix[l + 1][l], ad1[l][0], ad1[l][1]);
				doublematrix1.matrix[l][l] = ad4[0];
				doublematrix1.matrix[l + 1][l] = ad4[1];
				ad4 = applyPlaneRotation(ad[l], ad[l + 1], ad1[l][0], ad1[l][1]);
				ad[l] = ad4[0];
				ad[l + 1] = ad4[1];
				if ((d1 = Math.abs(ad[l + 1]) / d2) < d) {
					doublevector2 = update(doublevector2, l, doublematrix1, ad, adoublevector);
					d = d1;
					i = i1;
					return doublevector2;
				}
				l++;
			}

			doublevector2 = update(doublevector2, j - 1, doublematrix1, ad, adoublevector);
			doublevector3 = doublevector.subtract(doublematrix.multiply(doublevector2));
			d3 = doublevector3.norm();
			if ((d1 = d3 / d2) < d) {
				d = d1;
				i = i1;
				return doublevector2;
			}
		}
		d = d1;
		throw new MaximumIterationsExceededException("(tol) " + d + ". It doesn't converge in " + i + "iterations. Try raising the number of allowed iterations or raising the tolerance.");
	}

	private static double[] generatePlaneRotation(double d, double d1) {
		double ad[] = new double[2];
		if (d1 == 0.0D) {
			ad[0] = 1.0D;
			ad[1] = 0.0D;
		} else
		if (Math.abs(d1) > Math.abs(d)) {
			double d2 = d / d1;
			ad[1] = 1.0D / Math.sqrt(1.0D + d2 * d2);
			ad[0] = d2 * ad[1];
		} else {
			double d3 = d1 / d;
			ad[0] = 1.0D / Math.sqrt(1.0D + d3 * d3);
			ad[1] = d3 * ad[0];
		}
		return ad;
	}

	private static double[] applyPlaneRotation(double d, double d1, double d2, double d3) {
		double ad[] = new double[2];
		ad[0] = d2 * d + d3 * d1;
		ad[1] = -d3 * d + d2 * d1;
		return ad;
	}

	private static DoubleVector update(DoubleVector doublevector, int i, DoubleMatrix doublematrix, double ad[], DoubleVector adoublevector[]) {
		DoubleVector doublevector1 = new DoubleVector(ad);
		for (int j = i; j >= 0; j--) {
			doublevector1.vector[j] = doublevector1.vector[j] / doublematrix.matrix[j][j];
			for (int k = j - 1; k >= 0; k--)
				doublevector1.vector[k] = doublevector1.vector[k] - doublematrix.matrix[k][j] * doublevector1.vector[j];

		}

		for (int l = 0; l <= i; l++)
			doublevector = doublevector.add(adoublevector[l].scalarMultiply(doublevector1.vector[l]));

		return doublevector;
	}

	public static DoubleVector leastSquaresFit(int i, double ad[][]) {
		int j1 = i++;
		double ad1[][] = new double[i][i];
		double ad2[] = new double[i];
		for (int j = 0; j < i; j++) {
			double d2;
			double d = d2 = 0.0D;
			for (int l = 0; l < ad[0].length; l++) {
				double d3 = Math.pow(ad[0][l], j);
				d += d3;
				d2 += d3 * ad[1][l];
			}

			ad1[0][j] = d;
			ad2[j] = d2;
		}

		for (int k = 1; k < i; k++) {
			System.arraycopy(ad1[k - 1], 1, ad1[k], 0, j1);
			double d1 = 0.0D;
			for (int i1 = 0; i1 < ad[0].length; i1++)
				d1 += Math.pow(ad[0][i1], j1 + k);

			ad1[k][j1] = d1;
		}

		return solve((new DoubleSquareMatrix(ad1)).choleskyDecompose(), new DoubleVector(ad2));
	}

	public static double[] eigenvalueSolveHermitian(ComplexSquareMatrix complexsquarematrix) throws MaximumIterationsExceededException {
		int i = complexsquarematrix.rows();
		double ad[][] = new double[2 * i][2 * i];
		for (int j = 0; j < i; j++) {
			for (int l = 0; l < i; l++)
				ad[j][l] = complexsquarematrix.getElement(j, l).real();

			for (int i1 = 0; i1 < i; i1++)
				ad[j][i + i1] = -complexsquarematrix.getElement(j, i1).imag();

		}

		for (int k = 0; k < i; k++) {
			for (int j1 = 0; j1 < i; j1++)
				ad[i + k][j1] = complexsquarematrix.getElement(k, j1).imag();

			for (int k1 = 0; k1 < i; k1++)
				ad[i + k][i + k1] = complexsquarematrix.getElement(k, k1).real();

		}

		double ad1[] = new double[2 * i];
		double ad2[] = new double[2 * i];
		reduceSymmetric1_SquareToTridiagonal(ad, ad1, ad2);
		System.arraycopy(ad2, 1, ad2, 0, i - 1);
		ad2[i - 1] = 0.0D;
		eigenvalueSolveSymmetricTridiagonalMatrix(ad1, ad2);
		double ad3[] = new double[i];
		System.arraycopy(ad1, 0, ad3, 0, i);
		return ad3;
	}

	public static double[] eigenSolveHermitian(ComplexSquareMatrix complexsquarematrix, ComplexVector acomplexvector[]) throws MaximumIterationsExceededException {
		int i = complexsquarematrix.rows();
		double ad[][] = new double[2 * i][2 * i];
		for (int j = 0; j < i; j++) {
			for (int i1 = 0; i1 < i; i1++)
				ad[j][i1] = complexsquarematrix.getElement(j, i1).real();

			for (int j1 = 0; j1 < i; j1++)
				ad[j][i + j1] = -complexsquarematrix.getElement(j, j1).imag();

		}

		for (int k = 0; k < i; k++) {
			for (int k1 = 0; k1 < i; k1++)
				ad[i + k][k1] = complexsquarematrix.getElement(k, k1).imag();

			for (int l1 = 0; l1 < i; l1++)
				ad[i + k][i + l1] = complexsquarematrix.getElement(k, l1).real();

		}

		double ad1[] = new double[2 * i];
		double ad2[] = new double[2 * i];
		reduceSymmetric2_SquareToTridiagonal(ad, ad1, ad2);
		System.arraycopy(ad2, 1, ad2, 0, i - 1);
		ad2[i - 1] = 0.0D;
		eigenSolveSymmetricTridiagonalMatrix(ad1, ad2, ad);
		double ad3[] = new double[i];
		for (int l = 0; l < i; l++) {
			ad3[l] = ad1[l];
			Complex acomplex[] = new Complex[i];
			for (int i2 = 0; i2 < i; i2++)
				acomplex[i2] = new Complex(ad[i2][l], ad[i2 + i][l]);

			acomplexvector[l] = new ComplexVector(acomplex);
		}

		return ad3;
	}

	public static double[] eigenvalueSolveSymmetric(DoubleTridiagonalMatrix doubletridiagonalmatrix) throws MaximumIterationsExceededException {
		int i = doubletridiagonalmatrix.rows();
		int j = i - 1;
		double ad[] = new double[i];
		double ad1[] = new double[i];
		for (int k = 0; k < j; k++) {
			ad[k] = doubletridiagonalmatrix.getElement(k, k);
			ad1[k] = doubletridiagonalmatrix.getElement(k, k + 1);
		}

		ad[j] = doubletridiagonalmatrix.getElement(j, j);
		ad1[j] = 0.0D;
		eigenvalueSolveSymmetricTridiagonalMatrix(ad, ad1);
		return ad;
	}

	public static double[] eigenSolveSymmetric(DoubleTridiagonalMatrix doubletridiagonalmatrix, DoubleVector adoublevector[]) throws MaximumIterationsExceededException {
		int i = doubletridiagonalmatrix.rows();
		int j = i - 1;
		double ad[] = new double[i];
		double ad1[] = new double[i];
		double ad2[][] = new double[i][i];
		for (int k = 0; k < j; k++) {
			ad2[k][k] = 1.0D;
			ad[k] = doubletridiagonalmatrix.getElement(k, k);
			ad1[k] = doubletridiagonalmatrix.getElement(k, k + 1);
		}

		ad2[j][j] = 1.0D;
		ad[j] = doubletridiagonalmatrix.getElement(j, j);
		ad1[j] = 0.0D;
		eigenSolveSymmetricTridiagonalMatrix(ad, ad1, ad2);
		for (int l = 0; l < i; l++) {
			adoublevector[l] = new DoubleVector(i);
			for (int i1 = 0; i1 < i; i1++)
				adoublevector[l].vector[i1] = ad2[i1][l];

		}

		return ad;
	}

	public static double[] eigenvalueSolveSymmetric(DoubleSquareMatrix doublesquarematrix) throws MaximumIterationsExceededException {
		int i = doublesquarematrix.rows();
		double ad[] = new double[i];
		double ad1[] = new double[i];
		double ad2[][] = new double[i][i];
		for (int j = 0; j < i; j++) {
			for (int k = 0; k < i; k++)
				ad2[j][k] = doublesquarematrix.getElement(j, k);

		}

		reduceSymmetric1_SquareToTridiagonal(ad2, ad, ad1);
		System.arraycopy(ad1, 1, ad1, 0, i - 1);
		ad1[i - 1] = 0.0D;
		eigenvalueSolveSymmetricTridiagonalMatrix(ad, ad1);
		return ad;
	}

	public static double[] eigenSolveSymmetric(DoubleSquareMatrix doublesquarematrix, DoubleVector adoublevector[]) throws MaximumIterationsExceededException {
		int i = doublesquarematrix.rows();
		double ad[] = new double[i];
		double ad1[] = new double[i];
		double ad2[][] = new double[i][i];
		for (int j = 0; j < i; j++) {
			for (int l = 0; l < i; l++)
				ad2[j][l] = doublesquarematrix.getElement(j, l);

		}

		reduceSymmetric2_SquareToTridiagonal(ad2, ad, ad1);
		System.arraycopy(ad1, 1, ad1, 0, i - 1);
		ad1[i - 1] = 0.0D;
		eigenSolveSymmetricTridiagonalMatrix(ad, ad1, ad2);
		for (int k = 0; k < i; k++) {
			adoublevector[k] = new DoubleVector(i);
			for (int i1 = 0; i1 < i; i1++)
				adoublevector[k].vector[i1] = ad2[i1][k];

		}

		return ad;
	}

	private static void eigenvalueSolveSymmetricTridiagonalMatrix(double ad[], double ad1[]) throws MaximumIterationsExceededException {
		int i = ad.length;
		int j = i - 1;
		for (int l = 0; l < i; l++) {
			int i1 = 0;
			int k;
			do {
				for (k = l; k < j; k++) {
					double d8 = Math.abs(ad[k]) + Math.abs(ad[k + 1]);
					if (Math.abs(ad1[k]) + d8 == d8)
						break;
				}

				if (k != l) {
					if (i1++ == 50)
						throw new MaximumIterationsExceededException("No convergence after 50 iterations.");
					double d6 = (ad[l + 1] - ad[l]) / (2D * ad1[l]);
					double d1 = Math.sqrt(d6 * d6 + 1.0D);
					d6 = (ad[k] - ad[l]) + ad1[l] / (d6 + (d6 >= 0.0D ? Math.abs(d1) : -Math.abs(d1)));
					double d9;
					double d = d9 = 1.0D;
					double d5 = 0.0D;
					for (int j1 = k - 1; j1 >= l; j1--) {
						double d7 = d * ad1[j1];
						double d10 = d9 * ad1[j1];
						if (Math.abs(d7) >= Math.abs(d6)) {
							d9 = d6 / d7;
							double d2 = Math.sqrt(d9 * d9 + 1.0D);
							ad1[j1 + 1] = d7 * d2;
							d = 1.0D / d2;
							d9 *= d;
						} else {
							d = d7 / d6;
							double d3 = Math.sqrt(d * d + 1.0D);
							ad1[j1 + 1] = d6 * d3;
							d9 = 1.0D / d3;
							d *= d9;
						}
						d6 = ad[j1 + 1] - d5;
						double d4 = (ad[j1] - d6) * d + 2D * d9 * d10;
						d5 = d * d4;
						ad[j1 + 1] = d6 + d5;
						d6 = d9 * d4 - d10;
					}

					ad[l] = ad[l] - d5;
					ad1[l] = d6;
					ad1[k] = 0.0D;
				}
			} while (k != l);
		}

	}

	private static void reduceSymmetric1_SquareToTridiagonal(double ad[][], double ad1[], double ad2[]) {
		int i = ad1.length;
		for (int j = i - 1; j > 0; j--) {
			int k2 = j - 1;
			double d7;
			double d5 = d7 = 0.0D;
			if (k2 > 0) {
				for (int j1 = 0; j1 <= k2; j1++)
					d7 += Math.abs(ad[j][j1]);

				if (d7 == 0.0D) {
					ad2[j] = ad[j][k2];
				} else {
					for (int k1 = 0; k1 <= k2; k1++) {
						ad[j][k1] /= d7;
						d5 += ad[j][k1] * ad[j][k1];
					}

					double d = ad[j][k2];
					double d2 = d < 0.0D ? Math.sqrt(d5) : -Math.sqrt(d5);
					ad2[j] = d7 * d2;
					d5 -= d * d2;
					ad[j][k2] = d - d2;
					d = 0.0D;
					for (int l = 0; l <= k2; l++) {
						double d3 = 0.0D;
						for (int l1 = 0; l1 <= l; l1++)
							d3 += ad[l][l1] * ad[j][l1];

						for (int i2 = l + 1; i2 <= k2; i2++)
							d3 += ad[i2][l] * ad[j][i2];

						ad2[l] = d3 / d5;
						d += ad2[l] * ad[j][l];
					}

					double d6 = d / (d5 + d5);
					for (int i1 = 0; i1 <= k2; i1++) {
						double d1 = ad[j][i1];
						double d4;
						ad2[i1] = d4 = ad2[i1] - d6 * d1;
						for (int j2 = 0; j2 <= i1; j2++)
							ad[i1][j2] -= d1 * ad2[j2] + d4 * ad[j][j2];

					}

				}
			} else {
				ad2[j] = ad[j][k2];
			}
			ad1[j] = d5;
		}

		ad2[0] = 0.0D;
		for (int k = 0; k < i; k++)
			ad1[k] = ad[k][k];

	}

	private static void eigenSolveSymmetricTridiagonalMatrix(double ad[], double ad1[], double ad2[][]) throws MaximumIterationsExceededException {
		int i = ad.length;
		int j = i - 1;
		for (int l = 0; l < i; l++) {
			int i1 = 0;
			int k;
			do {
				for (k = l; k < j; k++) {
					double d9 = Math.abs(ad[k]) + Math.abs(ad[k + 1]);
					if (Math.abs(ad1[k]) + d9 == d9)
						break;
				}

				if (k != l) {
					if (i1++ == 50)
						throw new MaximumIterationsExceededException("No convergence after 50 iterations.");
					double d6 = (ad[l + 1] - ad[l]) / (2D * ad1[l]);
					double d1 = Math.sqrt(d6 * d6 + 1.0D);
					d6 = (ad[k] - ad[l]) + ad1[l] / (d6 + (d6 >= 0.0D ? Math.abs(d1) : -Math.abs(d1)));
					double d10;
					double d = d10 = 1.0D;
					double d5 = 0.0D;
					for (int j1 = k - 1; j1 >= l; j1--) {
						double d7 = d * ad1[j1];
						double d11 = d10 * ad1[j1];
						if (Math.abs(d7) >= Math.abs(d6)) {
							d10 = d6 / d7;
							double d2 = Math.sqrt(d10 * d10 + 1.0D);
							ad1[j1 + 1] = d7 * d2;
							d = 1.0D / d2;
							d10 *= d;
						} else {
							d = d7 / d6;
							double d3 = Math.sqrt(d * d + 1.0D);
							ad1[j1 + 1] = d6 * d3;
							d10 = 1.0D / d3;
							d *= d10;
						}
						d6 = ad[j1 + 1] - d5;
						double d4 = (ad[j1] - d6) * d + 2D * d10 * d11;
						d5 = d * d4;
						ad[j1 + 1] = d6 + d5;
						d6 = d10 * d4 - d11;
						for (int k1 = 0; k1 < i; k1++) {
							double d8 = ad2[k1][j1 + 1];
							ad2[k1][j1 + 1] = d * ad2[k1][j1] + d10 * d8;
							ad2[k1][j1] = d10 * ad2[k1][j1] - d * d8;
						}

					}

					ad[l] = ad[l] - d5;
					ad1[l] = d6;
					ad1[k] = 0.0D;
				}
			} while (k != l);
		}

	}

	private static void reduceSymmetric2_SquareToTridiagonal(double ad[][], double ad1[], double ad2[]) {
		int i = ad1.length;
		for (int j = i - 1; j > 0; j--) {
			int k3 = j - 1;
			double d8;
			double d6 = d8 = 0.0D;
			if (k3 > 0) {
				for (int l1 = 0; l1 <= k3; l1++)
					d8 += Math.abs(ad[j][l1]);

				if (d8 == 0.0D) {
					ad2[j] = ad[j][k3];
				} else {
					for (int i2 = 0; i2 <= k3; i2++) {
						ad[j][i2] /= d8;
						d6 += ad[j][i2] * ad[j][i2];
					}

					double d = ad[j][k3];
					double d2 = d < 0.0D ? Math.sqrt(d6) : -Math.sqrt(d6);
					ad2[j] = d8 * d2;
					d6 -= d * d2;
					ad[j][k3] = d - d2;
					d = 0.0D;
					for (int l = 0; l <= k3; l++) {
						ad[l][j] = ad[j][l] / d6;
						double d3 = 0.0D;
						for (int j2 = 0; j2 <= l; j2++)
							d3 += ad[l][j2] * ad[j][j2];

						for (int k2 = l + 1; k2 <= k3; k2++)
							d3 += ad[k2][l] * ad[j][k2];

						ad2[l] = d3 / d6;
						d += ad2[l] * ad[j][l];
					}

					double d7 = d / (d6 + d6);
					for (int i1 = 0; i1 <= k3; i1++) {
						double d1 = ad[j][i1];
						double d4;
						ad2[i1] = d4 = ad2[i1] - d7 * d1;
						for (int l2 = 0; l2 <= i1; l2++)
							ad[i1][l2] -= d1 * ad2[l2] + d4 * ad[j][l2];

					}

				}
			} else {
				ad2[j] = ad[j][k3];
			}
			ad1[j] = d6;
		}

		ad1[0] = ad2[0] = 0.0D;
		for (int k = 0; k < i; k++) {
			int l3 = k - 1;
			if (ad1[k] != 0.0D) {
				for (int j1 = 0; j1 <= l3; j1++) {
					double d5 = 0.0D;
					for (int i3 = 0; i3 <= l3; i3++)
						d5 += ad[k][i3] * ad[i3][j1];

					for (int j3 = 0; j3 <= l3; j3++)
						ad[j3][j1] -= d5 * ad[j3][k];

				}

			}
			ad1[k] = ad[k][k];
			ad[k][k] = 1.0D;
			for (int k1 = 0; k1 <= l3; k1++)
				ad[k1][k] = ad[k][k1] = 0.0D;

		}

	}
}
