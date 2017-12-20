package JSci.maths;


// Referenced classes of package JSci.maths:
//			Matrix, Complex, ComplexMatrix, DimensionException, 
//			DoubleMatrix, IntegerSquareMatrix, IntegerVector, MathInteger, 
//			MatrixDimensionException, MathNumber

public class IntegerMatrix extends Matrix {

	protected int matrix[][];

	protected IntegerMatrix() {
	}

	public IntegerMatrix(int i, int j) {
		matrix = new int[i][j];
	}

	public IntegerMatrix(int ai[][]) {
		matrix = ai;
	}

	public IntegerMatrix(IntegerVector aintegervector[]) {
		this(aintegervector[0].dimension(), aintegervector.length);
		for (int j = 0; j < matrix.length; j++) {
			for (int i = 0; i < matrix[0].length; i++)
				matrix[j][i] = aintegervector[i].getComponent(j);

		}

	}

	protected void finalize() throws Throwable {
		matrix = null;
		super.finalize();
	}

	public boolean equals(Object obj) {
		if (obj != null && (obj instanceof IntegerMatrix) && matrix.length == ((IntegerMatrix)obj).rows() && matrix[0].length == ((IntegerMatrix)obj).columns()) {
			IntegerMatrix integermatrix = (IntegerMatrix)obj;
			for (int j = 0; j < matrix.length; j++) {
				for (int i = 0; i < matrix[0].length; i++)
					if (matrix[j][i] != integermatrix.getElement(j, i))
						return false;

			}

			return true;
		} else {
			return false;
		}
	}

	public String toString() {
		StringBuffer stringbuffer = new StringBuffer(matrix.length * matrix[0].length);
		for (int j = 0; j < matrix.length; j++) {
			for (int i = 0; i < matrix[0].length; i++) {
				stringbuffer.append(matrix[j][i]);
				stringbuffer.append(' ');
			}

			stringbuffer.append('\n');
		}

		return stringbuffer.toString();
	}

	public int hashCode() {
		return (int)Math.exp(infNorm());
	}

	public DoubleMatrix toDoubleMatrix() {
		double ad[][] = new double[matrix.length][matrix[0].length];
		for (int j = 0; j < matrix.length; j++) {
			for (int i = 0; i < matrix[0].length; i++)
				ad[j][i] = matrix[j][i];

		}

		return new DoubleMatrix(ad);
	}

	public ComplexMatrix toComplexMatrix() {
		Complex acomplex[][] = new Complex[matrix.length][matrix[0].length];
		for (int j = 0; j < matrix.length; j++) {
			for (int i = 0; i < matrix[0].length; i++)
				acomplex[j][i] = new Complex(matrix[j][i], 0.0D);

		}

		return new ComplexMatrix(acomplex);
	}

	public int getElement(int i, int j) {
		if (i >= 0 && i < matrix.length && j >= 0 && j < matrix[0].length)
			return matrix[i][j];
		else
			throw new MatrixDimensionException("Invalid element.");
	}

	public void setElement(int i, int j, int k) {
		if (i >= 0 && i < matrix.length && j >= 0 && j < matrix[0].length) {
			matrix[i][j] = k;
			return;
		} else {
			throw new MatrixDimensionException("Invalid element.");
		}
	}

	public int infNorm() {
		int i = 0;
		for (int l = 0; l < matrix.length; l++) {
			int j = 0;
			for (int k = 0; k < matrix[0].length; k++)
				j += Math.abs(matrix[l][k]);

			if (j > i)
				i = j;
		}

		return i;
	}

	public double frobeniusNorm() {
		int i = 0;
		for (int k = 0; k < matrix.length; k++) {
			for (int j = 0; j < matrix[0].length; j++)
				i += matrix[k][j] * matrix[k][j];

		}

		return Math.sqrt(i);
	}

	public int rows() {
		return matrix.length;
	}

	public int columns() {
		return matrix[0].length;
	}

	public Matrix add(Matrix matrix1) {
		if (matrix1 instanceof IntegerMatrix)
			return add((IntegerMatrix)matrix1);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public IntegerMatrix add(IntegerMatrix integermatrix) {
		if (matrix.length == integermatrix.rows() && matrix[0].length == integermatrix.columns()) {
			int ai[][] = new int[matrix.length][matrix[0].length];
			for (int j = 0; j < ai.length; j++) {
				ai[j][0] = matrix[j][0] + integermatrix.getElement(j, 0);
				for (int i = 1; i < ai[0].length; i++)
					ai[j][i] = matrix[j][i] + integermatrix.getElement(j, i);

			}

			return new IntegerMatrix(ai);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public Matrix subtract(Matrix matrix1) {
		if (matrix1 instanceof IntegerMatrix)
			return subtract((IntegerMatrix)matrix1);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public IntegerMatrix subtract(IntegerMatrix integermatrix) {
		if (matrix.length == integermatrix.rows() && matrix[0].length == integermatrix.columns()) {
			int ai[][] = new int[matrix.length][matrix[0].length];
			for (int j = 0; j < ai.length; j++) {
				ai[j][0] = matrix[j][0] - integermatrix.getElement(j, 0);
				for (int i = 1; i < ai[0].length; i++)
					ai[j][i] = matrix[j][i] - integermatrix.getElement(j, i);

			}

			return new IntegerMatrix(ai);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public Matrix scalarMultiply(MathNumber mathnumber) {
		if (mathnumber instanceof MathInteger)
			return scalarMultiply(((MathInteger)mathnumber).value());
		else
			throw new IllegalArgumentException("Number class not recognised by this method.");
	}

	public IntegerMatrix scalarMultiply(int i) {
		int ai[][] = new int[matrix.length][matrix[0].length];
		for (int k = 0; k < ai.length; k++) {
			ai[k][0] = i * matrix[k][0];
			for (int j = 1; j < ai[0].length; j++)
				ai[k][j] = i * matrix[k][j];

		}

		return new IntegerMatrix(ai);
	}

	public IntegerVector multiply(IntegerVector integervector) {
		if (matrix[0].length == integervector.dimension()) {
			int ai[] = new int[matrix.length];
			for (int j = 0; j < ai.length; j++) {
				ai[j] = matrix[j][0] * integervector.getComponent(0);
				for (int i = 1; i < matrix[0].length; i++)
					ai[j] += matrix[j][i] * integervector.getComponent(i);

			}

			return new IntegerVector(ai);
		} else {
			throw new DimensionException("Matrix and vector are incompatible.");
		}
	}

	public Matrix multiply(Matrix matrix1) {
		if (matrix1 instanceof IntegerMatrix)
			return multiply((IntegerMatrix)matrix1);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public IntegerMatrix multiply(IntegerMatrix integermatrix) {
		if (matrix[0].length == integermatrix.rows()) {
			int ai[][] = new int[matrix.length][integermatrix.columns()];
			for (int k = 0; k < ai.length; k++) {
				for (int j = 0; j < ai[0].length; j++) {
					ai[k][j] = matrix[k][0] * integermatrix.getElement(0, j);
					for (int i = 1; i < matrix[0].length; i++)
						ai[k][j] += matrix[k][i] * integermatrix.getElement(i, j);

				}

			}

			if (ai.length == ai[0].length)
				return new IntegerSquareMatrix(ai);
			else
				return new IntegerMatrix(ai);
		} else {
			throw new MatrixDimensionException("Incompatible matrices.");
		}
	}

	public Matrix transpose() {
		int ai[][] = new int[matrix[0].length][matrix.length];
		for (int j = 0; j < ai[0].length; j++) {
			ai[0][j] = matrix[j][0];
			for (int i = 1; i < ai.length; i++)
				ai[i][j] = matrix[j][i];

		}

		return new IntegerMatrix(ai);
	}
}
