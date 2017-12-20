package JSci.maths;

import JSci.GlobalSettings;

// Referenced classes of package JSci.maths:
//			DoubleSquareMatrix, Complex, ComplexSquareMatrix, DimensionException, 
//			DoubleMatrix, DoubleVector, IntegerSquareMatrix, Mapping, 
//			MatrixDimensionException, IntegerMatrix, ComplexMatrix, Matrix

public final class DoubleSparseMatrix extends DoubleSquareMatrix {

	private int colPos[];
	private int rows[];
	private int N;

	protected DoubleSparseMatrix() {
	}

	public DoubleSparseMatrix(int i) {
		this();
		N = i;
		super.matrix = new double[1][0];
		colPos = new int[0];
		rows = new int[N + 1];
	}

	protected void finalize() throws Throwable {
		colPos = null;
		rows = null;
		super.finalize();
	}

	public boolean equals(Object obj) {
		if (obj != null && (obj instanceof DoubleSparseMatrix) && N == ((DoubleSparseMatrix)obj).N) {
			DoubleSparseMatrix doublesparsematrix = (DoubleSparseMatrix)obj;
			if (colPos.length != doublesparsematrix.colPos.length)
				return false;
			for (int i = 1; i < rows.length; i++)
				if (rows[i] != doublesparsematrix.rows[i])
					return false;

			for (int j = 1; j < colPos.length; j++) {
				if (colPos[j] != doublesparsematrix.colPos[j])
					return false;
				if (Math.abs(super.matrix[0][j] - ((DoubleMatrix) (doublesparsematrix)).matrix[0][j]) > GlobalSettings.ZERO_TOL)
					return false;
			}

			return true;
		} else {
			return false;
		}
	}

	public String toString() {
		StringBuffer stringbuffer = new StringBuffer(N * N);
		for (int j = 0; j < N; j++) {
			for (int i = 0; i < N; i++) {
				stringbuffer.append(getElement(j, i));
				stringbuffer.append(' ');
			}

			stringbuffer.append('\n');
		}

		return stringbuffer.toString();
	}

	public IntegerMatrix toIntegerMatrix() {
		int ai[][] = new int[N][N];
		for (int j = 0; j < N; j++) {
			for (int i = 0; i < N; i++)
				ai[j][i] = Math.round((float)getElement(j, i));

		}

		return new IntegerSquareMatrix(ai);
	}

	public ComplexMatrix toComplexMatrix() {
		Complex acomplex[][] = new Complex[N][N];
		for (int j = 0; j < N; j++) {
			for (int i = 0; i < N; i++)
				acomplex[j][i] = new Complex(getElement(j, i), 0.0D);

		}

		return new ComplexSquareMatrix(acomplex);
	}

	public double getElement(int i, int j) {
		if (i >= 0 && i < N && j >= 0 && j < N) {
			for (int k = rows[i]; k < rows[i + 1]; k++)
				if (colPos[k] == j)
					return super.matrix[0][k];

			return 0.0D;
		} else {
			throw new MatrixDimensionException("Invalid element.");
		}
	}

	public void setElement(int i, int j, double d) {
		if (i >= 0 && i < N && j >= 0 && j < N) {
			if (Math.abs(d) <= GlobalSettings.ZERO_TOL)
				return;
			for (int k = rows[i]; k < rows[i + 1]; k++)
				if (colPos[k] == j) {
					super.matrix[0][k] = d;
					return;
				}

			double ad[] = super.matrix[0];
			int ai[] = colPos;
			super.matrix[0] = new double[ad.length + 1];
			colPos = new int[ai.length + 1];
			System.arraycopy(ad, 0, super.matrix[0], 0, rows[i]);
			System.arraycopy(ai, 0, colPos, 0, rows[i]);
			int l;
			for (l = rows[i]; l < rows[i + 1] && ai[l] < j; l++) {
				super.matrix[0][l] = ad[l];
				colPos[l] = ai[l];
			}

			super.matrix[0][l] = d;
			colPos[l] = j;
			System.arraycopy(ad, l, super.matrix[0], l + 1, ad.length - l);
			System.arraycopy(ai, l, colPos, l + 1, ai.length - l);
			for (int i1 = i + 1; i1 < rows.length; i1++)
				rows[i1]++;

			return;
		} else {
			throw new MatrixDimensionException("Invalid element.");
		}
	}

	public double det() {
		DoubleSquareMatrix adoublesquarematrix[] = luDecompose();
		double d = ((DoubleMatrix) (adoublesquarematrix[1])).matrix[0][0];
		for (int i = 1; i < N; i++)
			d *= ((DoubleMatrix) (adoublesquarematrix[1])).matrix[i][i];

		return d;
	}

	public double trace() {
		double d = getElement(0, 0);
		for (int i = 1; i < N; i++)
			d += getElement(i, i);

		return d;
	}

	public double infNorm() {
		double d = 0.0D;
		for (int j = 0; j < N; j++) {
			double d1 = 0.0D;
			for (int i = rows[j]; i < rows[j + 1]; i++)
				d1 += Math.abs(super.matrix[0][i]);

			if (d1 > d)
				d = d1;
		}

		return d;
	}

	public double frobeniusNorm() {
		double d = 0.0D;
		for (int i = 0; i < colPos.length; i++)
			d += super.matrix[0][i] * super.matrix[0][i];

		return Math.sqrt(d);
	}

	public int rows() {
		return N;
	}

	public int columns() {
		return N;
	}

	public Matrix add(Matrix matrix) {
		if (matrix instanceof DoubleSparseMatrix)
			return add((DoubleSparseMatrix)matrix);
		if (matrix instanceof DoubleSquareMatrix)
			return rawAdd((DoubleSquareMatrix)matrix);
		if (matrix instanceof DoubleMatrix)
			return rawAdd((DoubleMatrix)matrix);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public DoubleMatrix add(DoubleMatrix doublematrix) {
		if (doublematrix instanceof DoubleSparseMatrix)
			return add((DoubleSparseMatrix)doublematrix);
		if (doublematrix instanceof DoubleSquareMatrix)
			return rawAdd((DoubleSquareMatrix)doublematrix);
		else
			return rawAdd(doublematrix);
	}

	private DoubleMatrix rawAdd(DoubleMatrix doublematrix) {
		if (N == doublematrix.rows() && N == doublematrix.columns()) {
			double ad[][] = new double[N][N];
			for (int j = 0; j < ad.length; j++) {
				ad[j][0] = getElement(j, 0) + doublematrix.getElement(j, 0);
				for (int i = 1; i < ad.length; i++)
					ad[j][i] = getElement(j, i) + doublematrix.getElement(j, i);

			}

			return new DoubleSquareMatrix(ad);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public DoubleSquareMatrix add(DoubleSquareMatrix doublesquarematrix) {
		if (doublesquarematrix instanceof DoubleSparseMatrix)
			return add((DoubleSparseMatrix)doublesquarematrix);
		else
			return rawAdd(doublesquarematrix);
	}

	private DoubleSquareMatrix rawAdd(DoubleSquareMatrix doublesquarematrix) {
		if (N == doublesquarematrix.rows()) {
			double ad[][] = new double[N][N];
			for (int j = 0; j < ad.length; j++) {
				ad[j][0] = getElement(j, 0) + doublesquarematrix.getElement(j, 0);
				for (int i = 1; i < ad.length; i++)
					ad[j][i] = getElement(j, i) + doublesquarematrix.getElement(j, i);

			}

			return new DoubleSquareMatrix(ad);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public DoubleSparseMatrix add(DoubleSparseMatrix doublesparsematrix) {
		if (N == doublesparsematrix.rows()) {
			DoubleSparseMatrix doublesparsematrix1 = new DoubleSparseMatrix(N);
			for (int j = 0; j < N; j++) {
				doublesparsematrix1.setElement(j, 0, getElement(j, 0) + doublesparsematrix.getElement(j, 0));
				for (int i = 1; i < N; i++)
					doublesparsematrix1.setElement(j, i, getElement(j, i) + doublesparsematrix.getElement(j, i));

			}

			return doublesparsematrix1;
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public Matrix subtract(Matrix matrix) {
		if (matrix instanceof DoubleSparseMatrix)
			return subtract((DoubleSparseMatrix)matrix);
		if (matrix instanceof DoubleSquareMatrix)
			return rawSubtract((DoubleSquareMatrix)matrix);
		if (matrix instanceof DoubleMatrix)
			return rawSubtract((DoubleMatrix)matrix);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public DoubleMatrix subtract(DoubleMatrix doublematrix) {
		if (doublematrix instanceof DoubleSparseMatrix)
			return subtract((DoubleSparseMatrix)doublematrix);
		if (doublematrix instanceof DoubleSquareMatrix)
			return rawSubtract((DoubleSquareMatrix)doublematrix);
		else
			return rawSubtract(doublematrix);
	}

	private DoubleMatrix rawSubtract(DoubleMatrix doublematrix) {
		if (N == doublematrix.rows() && N == doublematrix.columns()) {
			double ad[][] = new double[N][N];
			for (int j = 0; j < ad.length; j++) {
				ad[j][0] = getElement(j, 0) - doublematrix.getElement(j, 0);
				for (int i = 1; i < ad.length; i++)
					ad[j][i] = getElement(j, i) - doublematrix.getElement(j, i);

			}

			return new DoubleSquareMatrix(ad);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public DoubleSquareMatrix subtract(DoubleSquareMatrix doublesquarematrix) {
		if (doublesquarematrix instanceof DoubleSparseMatrix)
			return subtract((DoubleSparseMatrix)doublesquarematrix);
		else
			return rawSubtract(doublesquarematrix);
	}

	private DoubleSquareMatrix rawSubtract(DoubleSquareMatrix doublesquarematrix) {
		if (N == doublesquarematrix.rows()) {
			double ad[][] = new double[N][N];
			for (int j = 0; j < ad.length; j++) {
				ad[j][0] = getElement(j, 0) - doublesquarematrix.getElement(j, 0);
				for (int i = 1; i < ad.length; i++)
					ad[j][i] = getElement(j, i) - doublesquarematrix.getElement(j, i);

			}

			return new DoubleSquareMatrix(ad);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public DoubleSparseMatrix subtract(DoubleSparseMatrix doublesparsematrix) {
		if (N == doublesparsematrix.rows()) {
			DoubleSparseMatrix doublesparsematrix1 = new DoubleSparseMatrix(N);
			for (int j = 0; j < N; j++) {
				doublesparsematrix1.setElement(j, 0, getElement(j, 0) - doublesparsematrix.getElement(j, 0));
				for (int i = 1; i < N; i++)
					doublesparsematrix1.setElement(j, i, getElement(j, i) - doublesparsematrix.getElement(j, i));

			}

			return doublesparsematrix1;
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public DoubleMatrix scalarMultiply(double d) {
		DoubleSparseMatrix doublesparsematrix = new DoubleSparseMatrix(N);
		((DoubleMatrix) (doublesparsematrix)).matrix[0] = new double[super.matrix[0].length];
		doublesparsematrix.colPos = new int[colPos.length];
		System.arraycopy(colPos, 0, doublesparsematrix.colPos, 0, colPos.length);
		System.arraycopy(rows, 0, doublesparsematrix.rows, 0, rows.length);
		for (int i = 0; i < colPos.length; i++)
			((DoubleMatrix) (doublesparsematrix)).matrix[0][i] = d * super.matrix[0][i];

		return doublesparsematrix;
	}

	public DoubleVector multiply(DoubleVector doublevector) {
		if (N == doublevector.dimension()) {
			double ad[] = new double[N];
			for (int j = 0; j < ad.length; j++) {
				for (int i = rows[j]; i < rows[j + 1]; i++)
					ad[j] += super.matrix[0][i] * doublevector.getComponent(colPos[i]);

			}

			return new DoubleVector(ad);
		} else {
			throw new DimensionException("Matrix and vector are incompatible.");
		}
	}

	public Matrix multiply(Matrix matrix) {
		if (matrix instanceof DoubleSparseMatrix)
			return multiply((DoubleSparseMatrix)matrix);
		if (matrix instanceof DoubleSquareMatrix)
			return rawMultiply((DoubleSquareMatrix)matrix);
		if (matrix instanceof DoubleMatrix)
			return rawMultiply((DoubleMatrix)matrix);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public DoubleMatrix multiply(DoubleMatrix doublematrix) {
		if (doublematrix instanceof DoubleSparseMatrix)
			return multiply((DoubleSparseMatrix)doublematrix);
		if (doublematrix instanceof DoubleSquareMatrix)
			return rawMultiply((DoubleSquareMatrix)doublematrix);
		else
			return rawMultiply(doublematrix);
	}

	private DoubleMatrix rawMultiply(DoubleMatrix doublematrix) {
		if (N == doublematrix.rows()) {
			double ad[][] = new double[N][doublematrix.columns()];
			for (int k = 0; k < ad.length; k++) {
				for (int j = 0; j < ad[0].length; j++) {
					ad[k][j] = super.matrix[k][0] * doublematrix.getElement(0, j);
					for (int i = 1; i < N; i++)
						ad[k][j] += super.matrix[k][i] * doublematrix.getElement(i, j);

				}

			}

			return new DoubleMatrix(ad);
		} else {
			throw new MatrixDimensionException("Incompatible matrices.");
		}
	}

	public DoubleSquareMatrix multiply(DoubleSquareMatrix doublesquarematrix) {
		if (doublesquarematrix instanceof DoubleSparseMatrix)
			return multiply((DoubleSparseMatrix)doublesquarematrix);
		else
			return rawMultiply(doublesquarematrix);
	}

	private DoubleSquareMatrix rawMultiply(DoubleSquareMatrix doublesquarematrix) {
		if (N == doublesquarematrix.rows()) {
			double ad[][] = new double[N][N];
			for (int k = 0; k < ad.length; k++) {
				for (int j = 0; j < ad.length; j++) {
					ad[k][j] = super.matrix[k][0] * doublesquarematrix.getElement(0, j);
					for (int i = 1; i < ad.length; i++)
						ad[k][j] += super.matrix[k][i] * doublesquarematrix.getElement(i, j);

				}

			}

			return new DoubleSquareMatrix(ad);
		} else {
			throw new MatrixDimensionException("Incompatible matrices.");
		}
	}

	public DoubleSparseMatrix multiply(DoubleSparseMatrix doublesparsematrix) {
		if (N == doublesparsematrix.rows()) {
			DoubleSparseMatrix doublesparsematrix1 = new DoubleSparseMatrix(N);
			for (int k = 0; k < N; k++) {
				for (int j = 0; j < N; j++) {
					double d = getElement(k, 0) * doublesparsematrix.getElement(0, j);
					for (int i = 1; i < N; i++)
						d += getElement(k, i) * doublesparsematrix.getElement(i, j);

					doublesparsematrix1.setElement(k, j, d);
				}

			}

			return doublesparsematrix1;
		} else {
			throw new MatrixDimensionException("Incompatible matrices.");
		}
	}

	public Matrix transpose() {
		DoubleSparseMatrix doublesparsematrix = new DoubleSparseMatrix(N);
		for (int j = 0; j < N; j++) {
			doublesparsematrix.setElement(0, j, getElement(j, 0));
			for (int i = 1; i < N; i++)
				doublesparsematrix.setElement(i, j, getElement(j, i));

		}

		return doublesparsematrix;
	}

	public DoubleSquareMatrix inverse() {
		double ad[][][] = new double[2][N][N];
		DoubleSquareMatrix adoublesquarematrix[] = luDecompose();
		ad[0][0][0] = 1.0D / ((DoubleMatrix) (adoublesquarematrix[0])).matrix[0][0];
		ad[1][0][0] = 1.0D / ((DoubleMatrix) (adoublesquarematrix[1])).matrix[0][0];
		for (int i = 1; i < N; i++) {
			ad[0][i][i] = 1.0D / ((DoubleMatrix) (adoublesquarematrix[0])).matrix[i][i];
			ad[1][i][i] = 1.0D / ((DoubleMatrix) (adoublesquarematrix[1])).matrix[i][i];
		}

		for (int j = 0; j < N - 1; j++) {
			for (int k = j + 1; k < N; k++) {
				double d1;
				double d = d1 = 0.0D;
				for (int l = j; l < k; l++) {
					d -= ((DoubleMatrix) (adoublesquarematrix[0])).matrix[k][l] * ad[0][l][j];
					d1 -= ad[1][j][l] * ((DoubleMatrix) (adoublesquarematrix[1])).matrix[l][k];
				}

				ad[0][k][j] = d / ((DoubleMatrix) (adoublesquarematrix[0])).matrix[k][k];
				ad[1][j][k] = d1 / ((DoubleMatrix) (adoublesquarematrix[1])).matrix[k][k];
			}

		}

		return (new DoubleSquareMatrix(ad[1])).multiply(new DoubleSquareMatrix(ad[0]));
	}

	public DoubleSquareMatrix[] luDecompose() {
		double ad[][][] = new double[2][N][N];
		ad[0][0][0] = 1.0D;
		for (int i = 1; i < ad[0].length; i++)
			ad[0][i][i] = 1.0D;

		for (int l = 0; l < ad[0].length; l++) {
			for (int j = 0; j <= l; j++) {
				double d = getElement(j, l);
				for (int i1 = 0; i1 < j; i1++)
					d -= ad[0][j][i1] * ad[1][i1][l];

				ad[1][j][l] = d;
			}

			for (int k = l + 1; k < ad[0].length; k++) {
				double d1 = getElement(k, l);
				for (int j1 = 0; j1 < l; j1++)
					d1 -= ad[0][k][j1] * ad[1][j1][l];

				ad[0][k][l] = d1 / ad[1][l][l];
			}

		}

		DoubleSquareMatrix adoublesquarematrix[] = new DoubleSquareMatrix[2];
		adoublesquarematrix[0] = new DoubleSquareMatrix(ad[0]);
		adoublesquarematrix[1] = new DoubleSquareMatrix(ad[1]);
		return adoublesquarematrix;
	}

	public DoubleSquareMatrix[] choleskyDecompose() {
		double ad[][][] = new double[2][N][N];
		ad[0][0][0] = ad[1][0][0] = Math.sqrt(getElement(0, 0));
		for (int i = 1; i < ad[0].length; i++)
			ad[0][i][0] = ad[1][0][i] = getElement(i, 0) / ad[0][0][0];

		for (int l = 1; l < ad[0].length; l++) {
			double d = getElement(l, l);
			for (int j = 0; j < l; j++)
				d -= ad[0][l][j] * ad[0][l][j];

			ad[0][l][l] = ad[1][l][l] = Math.sqrt(d);
			for (int k = l + 1; k < ad[0].length; k++) {
				double d1 = getElement(k, l);
				for (int i1 = 0; i1 < k; i1++)
					d1 -= ad[0][l][i1] * ad[1][i1][k];

				ad[0][k][l] = ad[1][l][k] = d1 / ad[1][l][l];
			}

		}

		DoubleSquareMatrix adoublesquarematrix[] = new DoubleSquareMatrix[2];
		adoublesquarematrix[0] = new DoubleSquareMatrix(ad[0]);
		adoublesquarematrix[1] = new DoubleSquareMatrix(ad[1]);
		return adoublesquarematrix;
	}

	public DoubleMatrix mapElements(Mapping mapping) {
		DoubleSparseMatrix doublesparsematrix = new DoubleSparseMatrix(N);
		((DoubleMatrix) (doublesparsematrix)).matrix[0] = new double[super.matrix[0].length];
		doublesparsematrix.colPos = new int[colPos.length];
		System.arraycopy(colPos, 0, doublesparsematrix.colPos, 0, colPos.length);
		System.arraycopy(rows, 0, doublesparsematrix.rows, 0, rows.length);
		for (int i = 0; i < colPos.length; i++)
			((DoubleMatrix) (doublesparsematrix)).matrix[0][i] = mapping.map(super.matrix[0][i]);

		return doublesparsematrix;
	}
}
