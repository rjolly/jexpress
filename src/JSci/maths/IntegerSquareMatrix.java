package JSci.maths;


// Referenced classes of package JSci.maths:
//			IntegerMatrix, DimensionException, DoubleMatrix, DoubleSquareMatrix, 
//			IntegerDiagonalMatrix, IntegerVector, MatrixDimensionException, Matrix

public class IntegerSquareMatrix extends IntegerMatrix {

	protected IntegerSquareMatrix() {
	}

	public IntegerSquareMatrix(int i) {
		super(i, i);
	}

	public IntegerSquareMatrix(int ai[][]) {
		super(ai);
		if (ai.length != ai[0].length) {
			super.matrix = null;
			throw new MatrixDimensionException("The array is not square.");
		} else {
			return;
		}
	}

	public IntegerSquareMatrix(IntegerVector aintegervector[]) {
		super(aintegervector);
		if (aintegervector.length != aintegervector[0].dimension()) {
			super.matrix = null;
			throw new MatrixDimensionException("The array does not form a square matrix.");
		} else {
			return;
		}
	}

	public boolean isSymmetric() {
		return equals(transpose());
	}

	public boolean isUnitary() {
		return multiply(transpose()).equals(IntegerDiagonalMatrix.identity(super.matrix[0].length));
	}

	public int det() {
		if (super.matrix.length == 2)
			return super.matrix[0][0] * super.matrix[1][1] - super.matrix[0][1] * super.matrix[1][0];
		DoubleSquareMatrix adoublesquarematrix[] = luDecompose();
		double d = ((DoubleMatrix) (adoublesquarematrix[1])).matrix[0][0];
		for (int i = 1; i < super.matrix.length; i++)
			d *= ((DoubleMatrix) (adoublesquarematrix[1])).matrix[i][i];

		return (int)d;
	}

	public int trace() {
		int i = super.matrix[0][0];
		for (int j = 1; j < super.matrix.length; j++)
			i += super.matrix[j][j];

		return i;
	}

	public Matrix add(Matrix matrix) {
		if (matrix instanceof IntegerSquareMatrix)
			return add((IntegerSquareMatrix)matrix);
		if (matrix instanceof IntegerMatrix)
			return rawAdd((IntegerMatrix)matrix);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public IntegerMatrix add(IntegerMatrix integermatrix) {
		if (integermatrix instanceof IntegerSquareMatrix)
			return add((IntegerSquareMatrix)integermatrix);
		else
			return rawAdd(integermatrix);
	}

	private IntegerMatrix rawAdd(IntegerMatrix integermatrix) {
		if (super.matrix.length == integermatrix.rows() && super.matrix.length == integermatrix.columns()) {
			int ai[][] = new int[super.matrix.length][super.matrix.length];
			for (int j = 0; j < ai.length; j++) {
				ai[j][0] = super.matrix[j][0] + integermatrix.getElement(j, 0);
				for (int i = 1; i < ai.length; i++)
					ai[j][i] = super.matrix[j][i] + integermatrix.getElement(j, i);

			}

			return new IntegerSquareMatrix(ai);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public IntegerSquareMatrix add(IntegerSquareMatrix integersquarematrix) {
		if (super.matrix.length == integersquarematrix.rows()) {
			int ai[][] = new int[super.matrix.length][super.matrix.length];
			for (int j = 0; j < ai.length; j++) {
				ai[j][0] = super.matrix[j][0] + integersquarematrix.getElement(j, 0);
				for (int i = 1; i < ai.length; i++)
					ai[j][i] = super.matrix[j][i] + integersquarematrix.getElement(j, i);

			}

			return new IntegerSquareMatrix(ai);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public Matrix subtract(Matrix matrix) {
		if (matrix instanceof IntegerSquareMatrix)
			return subtract((IntegerSquareMatrix)matrix);
		if (matrix instanceof IntegerMatrix)
			return rawSubtract((IntegerMatrix)matrix);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public IntegerMatrix subtract(IntegerMatrix integermatrix) {
		if (integermatrix instanceof IntegerSquareMatrix)
			return subtract((IntegerSquareMatrix)integermatrix);
		else
			return rawSubtract(integermatrix);
	}

	private IntegerMatrix rawSubtract(IntegerMatrix integermatrix) {
		if (super.matrix.length == integermatrix.rows() && super.matrix.length == integermatrix.columns()) {
			int ai[][] = new int[super.matrix.length][super.matrix.length];
			for (int j = 0; j < ai.length; j++) {
				ai[j][0] = super.matrix[j][0] - integermatrix.getElement(j, 0);
				for (int i = 1; i < ai.length; i++)
					ai[j][i] = super.matrix[j][i] - integermatrix.getElement(j, i);

			}

			return new IntegerSquareMatrix(ai);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public IntegerSquareMatrix subtract(IntegerSquareMatrix integersquarematrix) {
		if (super.matrix.length == integersquarematrix.rows()) {
			int ai[][] = new int[super.matrix.length][super.matrix.length];
			for (int j = 0; j < ai.length; j++) {
				ai[j][0] = super.matrix[j][0] - integersquarematrix.getElement(j, 0);
				for (int i = 1; i < ai.length; i++)
					ai[j][i] = super.matrix[j][i] - integersquarematrix.getElement(j, i);

			}

			return new IntegerSquareMatrix(ai);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public IntegerMatrix scalarMultiply(int i) {
		int ai[][] = new int[super.matrix.length][super.matrix.length];
		for (int k = 0; k < ai.length; k++) {
			ai[k][0] = i * super.matrix[k][0];
			for (int j = 1; j < ai.length; j++)
				ai[k][j] = i * super.matrix[k][j];

		}

		return new IntegerSquareMatrix(ai);
	}

	public IntegerVector multiply(IntegerVector integervector) {
		if (super.matrix.length == integervector.dimension()) {
			int ai[] = new int[super.matrix.length];
			for (int j = 0; j < ai.length; j++) {
				ai[j] = super.matrix[j][0] * integervector.getComponent(0);
				for (int i = 1; i < super.matrix.length; i++)
					ai[j] += super.matrix[j][i] * integervector.getComponent(i);

			}

			return new IntegerVector(ai);
		} else {
			throw new DimensionException("Matrix and vector are incompatible.");
		}
	}

	public Matrix multiply(Matrix matrix) {
		if (matrix instanceof IntegerSquareMatrix)
			return multiply((IntegerSquareMatrix)matrix);
		if (matrix instanceof IntegerMatrix)
			return rawMultiply((IntegerMatrix)matrix);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public IntegerMatrix multiply(IntegerMatrix integermatrix) {
		if (integermatrix instanceof IntegerSquareMatrix)
			return multiply((IntegerSquareMatrix)integermatrix);
		else
			return rawMultiply(integermatrix);
	}

	private IntegerMatrix rawMultiply(IntegerMatrix integermatrix) {
		if (super.matrix[0].length == integermatrix.rows()) {
			int ai[][] = new int[super.matrix.length][integermatrix.columns()];
			for (int k = 0; k < ai.length; k++) {
				for (int j = 0; j < ai[0].length; j++) {
					ai[k][j] = super.matrix[k][0] * integermatrix.getElement(0, j);
					for (int i = 1; i < super.matrix[0].length; i++)
						ai[k][j] += super.matrix[k][i] * integermatrix.getElement(i, j);

				}

			}

			return new IntegerMatrix(ai);
		} else {
			throw new MatrixDimensionException("Incompatible matrices.");
		}
	}

	public IntegerSquareMatrix multiply(IntegerSquareMatrix integersquarematrix) {
		if (super.matrix.length == integersquarematrix.rows()) {
			int ai[][] = new int[super.matrix.length][super.matrix.length];
			for (int k = 0; k < ai.length; k++) {
				for (int j = 0; j < ai.length; j++) {
					ai[k][j] = super.matrix[k][0] * integersquarematrix.getElement(0, j);
					for (int i = 1; i < ai.length; i++)
						ai[k][j] += super.matrix[k][i] * integersquarematrix.getElement(i, j);

				}

			}

			return new IntegerSquareMatrix(ai);
		} else {
			throw new MatrixDimensionException("Incompatible matrices.");
		}
	}

	public Matrix transpose() {
		int ai[][] = new int[super.matrix.length][super.matrix.length];
		for (int j = 0; j < ai.length; j++) {
			ai[0][j] = super.matrix[j][0];
			for (int i = 1; i < ai.length; i++)
				ai[i][j] = super.matrix[j][i];

		}

		return new IntegerSquareMatrix(ai);
	}

	public DoubleSquareMatrix[] luDecompose() {
		double ad[][][] = new double[2][super.matrix.length][super.matrix.length];
		ad[0][0][0] = 1.0D;
		for (int i = 1; i < super.matrix.length; i++)
			ad[0][i][i] = 1.0D;

		for (int l = 0; l < super.matrix.length; l++) {
			for (int j = 0; j <= l; j++) {
				double d = super.matrix[j][l];
				for (int i1 = 0; i1 < j; i1++)
					d -= ad[0][j][i1] * ad[1][i1][l];

				ad[1][j][l] = d;
			}

			for (int k = l + 1; k < super.matrix.length; k++) {
				double d1 = super.matrix[k][l];
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

	public DoubleSquareMatrix[] choleskyDecomposition() {
		double ad[][][] = new double[2][super.matrix.length][super.matrix.length];
		ad[0][0][0] = ad[1][0][0] = Math.sqrt(super.matrix[0][0]);
		for (int i = 1; i < super.matrix.length; i++)
			ad[0][i][0] = ad[1][0][i] = (double)super.matrix[i][0] / ad[0][0][0];

		for (int l = 1; l < super.matrix.length; l++) {
			double d = super.matrix[l][l];
			for (int j = 0; j < l; j++)
				d -= ad[0][l][j] * ad[0][l][j];

			ad[0][l][l] = ad[1][l][l] = Math.sqrt(d);
			for (int k = l + 1; k < super.matrix.length; k++) {
				double d1 = super.matrix[k][l];
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
}
