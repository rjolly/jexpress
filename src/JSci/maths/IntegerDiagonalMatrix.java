package JSci.maths;


// Referenced classes of package JSci.maths:
//			IntegerTridiagonalMatrix, Complex, ComplexDiagonalMatrix, DimensionException, 
//			DoubleDiagonalMatrix, IntegerMatrix, IntegerSquareMatrix, IntegerVector, 
//			MatrixDimensionException, DoubleMatrix, ComplexMatrix, Matrix, 
//			DoubleSquareMatrix

public final class IntegerDiagonalMatrix extends IntegerTridiagonalMatrix {

	protected IntegerDiagonalMatrix() {
	}

	public IntegerDiagonalMatrix(int i) {
		this();
		super.matrix = new int[1][i];
	}

	public IntegerDiagonalMatrix(int ai[][]) {
		this(ai.length);
		if (ai.length == ai[0].length) {
			for (int i = 0; i < super.matrix[0].length; i++)
				super.matrix[0][i] = ai[i][i];

			return;
		} else {
			super.matrix = null;
			throw new MatrixDimensionException("Array must be square.");
		}
	}

	public IntegerDiagonalMatrix(int ai[]) {
		this();
		super.matrix = new int[1][];
		super.matrix[0] = ai;
	}

	public static IntegerDiagonalMatrix identity(int i) {
		int ai[] = new int[i];
		for (int j = 0; j < i; j++)
			ai[j] = 1;

		return new IntegerDiagonalMatrix(ai);
	}

	public boolean equals(Object obj) {
		if (obj != null && (obj instanceof IntegerDiagonalMatrix) && super.matrix[0].length == ((IntegerDiagonalMatrix)obj).rows()) {
			IntegerDiagonalMatrix integerdiagonalmatrix = (IntegerDiagonalMatrix)obj;
			for (int i = 0; i < super.matrix[0].length; i++)
				if (super.matrix[0][i] != integerdiagonalmatrix.getElement(i, i))
					return false;

			return true;
		} else {
			return false;
		}
	}

	public DoubleMatrix toDoubleMatrix() {
		double ad[] = new double[super.matrix[0].length];
		for (int i = 0; i < super.matrix[0].length; i++)
			ad[i] = super.matrix[0][i];

		return new DoubleDiagonalMatrix(ad);
	}

	public ComplexMatrix toComplexMatrix() {
		Complex acomplex[] = new Complex[super.matrix[0].length];
		for (int i = 0; i < super.matrix[0].length; i++)
			acomplex[i] = new Complex(super.matrix[0][i], 0.0D);

		return new ComplexDiagonalMatrix(acomplex);
	}

	public int getElement(int i, int j) {
		if (i >= 0 && i < super.matrix[0].length && j >= 0 && j < super.matrix[0].length) {
			if (i == j)
				return super.matrix[0][i];
			else
				return 0;
		} else {
			throw new MatrixDimensionException("Invalid element.");
		}
	}

	public void setElement(int i, int j, int k) {
		if (i >= 0 && i < super.matrix[0].length && j >= 0 && j < super.matrix[0].length && i == j) {
			super.matrix[0][i] = k;
			return;
		} else {
			throw new MatrixDimensionException("Invalid element.");
		}
	}

	public boolean isSymmetric() {
		return true;
	}

	public boolean isUnitary() {
		return multiply(this).equals(identity(super.matrix[0].length));
	}

	public int det() {
		int i = super.matrix[0][0];
		for (int j = 1; j < super.matrix[0].length; j++)
			i *= super.matrix[0][j];

		return i;
	}

	public int trace() {
		int i = super.matrix[0][0];
		for (int j = 1; j < super.matrix[0].length; j++)
			i += super.matrix[0][j];

		return i;
	}

	public int infNorm() {
		int i = Math.abs(super.matrix[0][0]);
		for (int k = 1; k < super.matrix[0].length; k++) {
			int j = Math.abs(super.matrix[0][k]);
			if (j > i)
				i = j;
		}

		return i;
	}

	public double frobeniusNorm() {
		int i = super.matrix[0][0] * super.matrix[0][0];
		for (int j = 1; j < super.matrix[0].length; j++)
			i += super.matrix[0][j] * super.matrix[0][j];

		return Math.sqrt(i);
	}

	public int rows() {
		return super.matrix[0].length;
	}

	public int columns() {
		return super.matrix[0].length;
	}

	public Matrix add(Matrix matrix) {
		if (matrix instanceof IntegerDiagonalMatrix)
			return add((IntegerDiagonalMatrix)matrix);
		if (matrix instanceof IntegerTridiagonalMatrix)
			return rawAdd((IntegerTridiagonalMatrix)matrix);
		if (matrix instanceof IntegerSquareMatrix)
			return rawAdd((IntegerSquareMatrix)matrix);
		if (matrix instanceof IntegerMatrix)
			return rawAdd((IntegerMatrix)matrix);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public IntegerMatrix add(IntegerMatrix integermatrix) {
		if (integermatrix instanceof IntegerDiagonalMatrix)
			return add((IntegerDiagonalMatrix)integermatrix);
		if (integermatrix instanceof IntegerTridiagonalMatrix)
			return rawAdd((IntegerTridiagonalMatrix)integermatrix);
		if (integermatrix instanceof IntegerSquareMatrix)
			return rawAdd((IntegerSquareMatrix)integermatrix);
		else
			return rawAdd(integermatrix);
	}

	private IntegerMatrix rawAdd(IntegerMatrix integermatrix) {
		if (super.matrix[0].length == integermatrix.rows() && super.matrix[0].length == integermatrix.columns()) {
			int ai[][] = new int[super.matrix[0].length][super.matrix[0].length];
			for (int i = 0; i < ai.length; i++)
				System.arraycopy(integermatrix.matrix[i], 0, ai[i], 0, ai.length);

			for (int j = 0; j < ai.length; j++)
				ai[j][j] += super.matrix[0][j];

			return new IntegerSquareMatrix(ai);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public IntegerSquareMatrix add(IntegerSquareMatrix integersquarematrix) {
		if (integersquarematrix instanceof IntegerDiagonalMatrix)
			return add((IntegerDiagonalMatrix)integersquarematrix);
		if (integersquarematrix instanceof IntegerTridiagonalMatrix)
			return rawAdd((IntegerTridiagonalMatrix)integersquarematrix);
		else
			return rawAdd(integersquarematrix);
	}

	private IntegerSquareMatrix rawAdd(IntegerSquareMatrix integersquarematrix) {
		if (super.matrix[0].length == integersquarematrix.rows()) {
			int ai[][] = new int[super.matrix[0].length][super.matrix[0].length];
			for (int i = 0; i < ai.length; i++)
				System.arraycopy(((IntegerMatrix) (integersquarematrix)).matrix[i], 0, ai[i], 0, ai.length);

			for (int j = 0; j < ai.length; j++)
				ai[j][j] += super.matrix[0][j];

			return new IntegerSquareMatrix(ai);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public IntegerTridiagonalMatrix add(IntegerTridiagonalMatrix integertridiagonalmatrix) {
		if (integertridiagonalmatrix instanceof IntegerDiagonalMatrix)
			return add((IntegerDiagonalMatrix)integertridiagonalmatrix);
		else
			return rawAdd(integertridiagonalmatrix);
	}

	private IntegerTridiagonalMatrix rawAdd(IntegerTridiagonalMatrix integertridiagonalmatrix) {
		if (super.matrix[0].length == integertridiagonalmatrix.rows()) {
			IntegerTridiagonalMatrix integertridiagonalmatrix1 = new IntegerTridiagonalMatrix(super.matrix[0].length);
			System.arraycopy(((IntegerMatrix) (integertridiagonalmatrix)).matrix[0], 0, ((IntegerMatrix) (integertridiagonalmatrix1)).matrix[0], 0, super.matrix[0].length);
			System.arraycopy(((IntegerMatrix) (integertridiagonalmatrix)).matrix[2], 0, ((IntegerMatrix) (integertridiagonalmatrix1)).matrix[2], 0, super.matrix[2].length);
			((IntegerMatrix) (integertridiagonalmatrix1)).matrix[1][0] = super.matrix[0][0] + ((IntegerMatrix) (integertridiagonalmatrix)).matrix[1][0];
			for (int i = 1; i < super.matrix[0].length; i++)
				((IntegerMatrix) (integertridiagonalmatrix1)).matrix[1][i] = super.matrix[0][i] + ((IntegerMatrix) (integertridiagonalmatrix)).matrix[1][i];

			return integertridiagonalmatrix1;
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public IntegerDiagonalMatrix add(IntegerDiagonalMatrix integerdiagonalmatrix) {
		if (super.matrix[0].length == integerdiagonalmatrix.rows()) {
			int ai[] = new int[super.matrix[0].length];
			ai[0] = super.matrix[0][0] + integerdiagonalmatrix.getElement(0, 0);
			for (int i = 1; i < ai.length; i++)
				ai[i] = super.matrix[0][i] + integerdiagonalmatrix.getElement(i, i);

			return new IntegerDiagonalMatrix(ai);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public Matrix subtract(Matrix matrix) {
		if (matrix instanceof IntegerDiagonalMatrix)
			return subtract((IntegerDiagonalMatrix)matrix);
		if (matrix instanceof IntegerTridiagonalMatrix)
			return rawSubtract((IntegerTridiagonalMatrix)matrix);
		if (matrix instanceof IntegerSquareMatrix)
			return rawSubtract((IntegerSquareMatrix)matrix);
		if (matrix instanceof IntegerMatrix)
			return rawSubtract((IntegerMatrix)matrix);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public IntegerMatrix subtract(IntegerMatrix integermatrix) {
		if (integermatrix instanceof IntegerDiagonalMatrix)
			return subtract((IntegerDiagonalMatrix)integermatrix);
		if (integermatrix instanceof IntegerTridiagonalMatrix)
			return rawSubtract((IntegerTridiagonalMatrix)integermatrix);
		if (integermatrix instanceof IntegerSquareMatrix)
			return rawSubtract((IntegerSquareMatrix)integermatrix);
		else
			return rawSubtract(integermatrix);
	}

	private IntegerMatrix rawSubtract(IntegerMatrix integermatrix) {
		if (super.matrix[0].length == integermatrix.rows() && super.matrix[0].length == integermatrix.columns()) {
			int ai[][] = new int[super.matrix[0].length][super.matrix[0].length];
			for (int j = 0; j < ai.length; j++) {
				ai[j][0] = -integermatrix.matrix[j][0];
				for (int i = 1; i < ai.length; i++)
					ai[j][i] = -integermatrix.matrix[j][i];

			}

			for (int k = 0; k < ai.length; k++)
				ai[k][k] += super.matrix[0][k];

			return new IntegerSquareMatrix(ai);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public IntegerSquareMatrix subtract(IntegerSquareMatrix integersquarematrix) {
		if (integersquarematrix instanceof IntegerDiagonalMatrix)
			return subtract((IntegerDiagonalMatrix)integersquarematrix);
		if (integersquarematrix instanceof IntegerTridiagonalMatrix)
			return rawSubtract((IntegerTridiagonalMatrix)integersquarematrix);
		else
			return rawSubtract(integersquarematrix);
	}

	private IntegerSquareMatrix rawSubtract(IntegerSquareMatrix integersquarematrix) {
		if (super.matrix[0].length == integersquarematrix.rows()) {
			int ai[][] = new int[super.matrix[0].length][super.matrix[0].length];
			for (int j = 0; j < ai.length; j++) {
				ai[j][0] = -((IntegerMatrix) (integersquarematrix)).matrix[j][0];
				for (int i = 1; i < ai.length; i++)
					ai[j][i] = -((IntegerMatrix) (integersquarematrix)).matrix[j][i];

			}

			for (int k = 0; k < ai.length; k++)
				ai[k][k] += super.matrix[0][k];

			return new IntegerSquareMatrix(ai);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public IntegerTridiagonalMatrix subtract(IntegerTridiagonalMatrix integertridiagonalmatrix) {
		if (integertridiagonalmatrix instanceof IntegerDiagonalMatrix)
			return subtract((IntegerDiagonalMatrix)integertridiagonalmatrix);
		else
			return rawSubtract(integertridiagonalmatrix);
	}

	private IntegerTridiagonalMatrix rawSubtract(IntegerTridiagonalMatrix integertridiagonalmatrix) {
		if (super.matrix[0].length == integertridiagonalmatrix.rows()) {
			int i = super.matrix[0].length;
			IntegerTridiagonalMatrix integertridiagonalmatrix1 = new IntegerTridiagonalMatrix(i);
			integertridiagonalmatrix1.setElement(0, 0, super.matrix[0][0] - integertridiagonalmatrix.getElement(0, 0));
			integertridiagonalmatrix1.setElement(1, 0, -integertridiagonalmatrix.getElement(1, 0));
			i--;
			for (int j = 1; j < i; j++) {
				integertridiagonalmatrix1.setElement(j - 1, j, -integertridiagonalmatrix.getElement(j - 1, j));
				integertridiagonalmatrix1.setElement(j, j, super.matrix[0][j] - integertridiagonalmatrix.getElement(j, j));
				integertridiagonalmatrix1.setElement(j + 1, j, -integertridiagonalmatrix.getElement(j + 1, j));
			}

			integertridiagonalmatrix1.setElement(i - 1, i, -integertridiagonalmatrix.getElement(i - 1, i));
			integertridiagonalmatrix1.setElement(i, i, super.matrix[0][i] - integertridiagonalmatrix.getElement(i, i));
			return integertridiagonalmatrix1;
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public IntegerDiagonalMatrix subtract(IntegerDiagonalMatrix integerdiagonalmatrix) {
		if (super.matrix[0].length == integerdiagonalmatrix.rows()) {
			int ai[] = new int[super.matrix[0].length];
			ai[0] = super.matrix[0][0] - ((IntegerMatrix) (integerdiagonalmatrix)).matrix[0][0];
			for (int i = 1; i < ai.length; i++)
				ai[i] = super.matrix[0][i] - ((IntegerMatrix) (integerdiagonalmatrix)).matrix[0][i];

			return new IntegerDiagonalMatrix(ai);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public IntegerMatrix scalarMultiply(int i) {
		int ai[] = new int[super.matrix[0].length];
		ai[0] = i * super.matrix[0][0];
		for (int j = 1; j < ai.length; j++)
			ai[j] = i * super.matrix[0][j];

		return new IntegerDiagonalMatrix(ai);
	}

	public IntegerVector multiply(IntegerVector integervector) {
		if (super.matrix[0].length == integervector.dimension()) {
			int ai[] = new int[super.matrix[0].length];
			ai[0] = super.matrix[0][0] * integervector.getComponent(0);
			for (int i = 1; i < ai.length; i++)
				ai[i] = super.matrix[0][i] * integervector.getComponent(i);

			return new IntegerVector(ai);
		} else {
			throw new DimensionException("Matrix and vector are incompatible.");
		}
	}

	public Matrix multiply(Matrix matrix) {
		if (matrix instanceof IntegerDiagonalMatrix)
			return multiply((IntegerDiagonalMatrix)matrix);
		if (matrix instanceof IntegerTridiagonalMatrix)
			return rawMultiply((IntegerTridiagonalMatrix)matrix);
		if (matrix instanceof IntegerSquareMatrix)
			return rawMultiply((IntegerSquareMatrix)matrix);
		if (matrix instanceof IntegerMatrix)
			return rawMultiply((IntegerMatrix)matrix);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public IntegerMatrix multiply(IntegerMatrix integermatrix) {
		if (integermatrix instanceof IntegerDiagonalMatrix)
			return multiply((IntegerDiagonalMatrix)integermatrix);
		if (integermatrix instanceof IntegerTridiagonalMatrix)
			return rawMultiply((IntegerTridiagonalMatrix)integermatrix);
		if (integermatrix instanceof IntegerSquareMatrix)
			return rawMultiply((IntegerSquareMatrix)integermatrix);
		else
			return rawMultiply(integermatrix);
	}

	private IntegerMatrix rawMultiply(IntegerMatrix integermatrix) {
		if (super.matrix[0].length == integermatrix.rows()) {
			int ai[][] = new int[super.matrix[0].length][integermatrix.columns()];
			for (int j = 0; j < ai.length; j++) {
				ai[j][0] = super.matrix[0][j] * integermatrix.getElement(j, 0);
				for (int i = 1; i < ai[0].length; i++)
					ai[j][i] = super.matrix[0][j] * integermatrix.getElement(j, i);

			}

			return new IntegerMatrix(ai);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public IntegerSquareMatrix multiply(IntegerSquareMatrix integersquarematrix) {
		if (integersquarematrix instanceof IntegerDiagonalMatrix)
			return multiply((IntegerDiagonalMatrix)integersquarematrix);
		if (integersquarematrix instanceof IntegerTridiagonalMatrix)
			return rawMultiply((IntegerTridiagonalMatrix)integersquarematrix);
		else
			return rawMultiply(integersquarematrix);
	}

	private IntegerSquareMatrix rawMultiply(IntegerSquareMatrix integersquarematrix) {
		if (super.matrix[0].length == integersquarematrix.rows()) {
			int ai[][] = new int[super.matrix[0].length][super.matrix[0].length];
			for (int j = 0; j < ai.length; j++) {
				ai[j][0] = super.matrix[0][j] * integersquarematrix.getElement(j, 0);
				for (int i = 1; i < ai.length; i++)
					ai[j][i] = super.matrix[0][j] * integersquarematrix.getElement(j, i);

			}

			return new IntegerSquareMatrix(ai);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public IntegerSquareMatrix multiply(IntegerTridiagonalMatrix integertridiagonalmatrix) {
		if (integertridiagonalmatrix instanceof IntegerDiagonalMatrix)
			return multiply((IntegerDiagonalMatrix)integertridiagonalmatrix);
		else
			return rawMultiply(integertridiagonalmatrix);
	}

	private IntegerSquareMatrix rawMultiply(IntegerTridiagonalMatrix integertridiagonalmatrix) {
		int i = super.matrix[0].length;
		if (i == integertridiagonalmatrix.rows()) {
			IntegerTridiagonalMatrix integertridiagonalmatrix1 = new IntegerTridiagonalMatrix(i);
			integertridiagonalmatrix1.setElement(0, 0, super.matrix[0][0] * integertridiagonalmatrix.getElement(0, 0));
			integertridiagonalmatrix1.setElement(0, 1, super.matrix[0][0] * integertridiagonalmatrix.getElement(0, 1));
			i--;
			for (int j = 1; j < i; j++) {
				integertridiagonalmatrix1.setElement(j, j - 1, super.matrix[0][j] * integertridiagonalmatrix.getElement(j, j - 1));
				integertridiagonalmatrix1.setElement(j, j, super.matrix[0][j] * integertridiagonalmatrix.getElement(j, j));
				integertridiagonalmatrix1.setElement(j, j + 1, super.matrix[0][j] * integertridiagonalmatrix.getElement(j, j + 1));
			}

			integertridiagonalmatrix1.setElement(i, i - 1, super.matrix[0][i] * integertridiagonalmatrix.getElement(i, i - 1));
			integertridiagonalmatrix1.setElement(i, i, super.matrix[0][i] * integertridiagonalmatrix.getElement(i, i));
			return integertridiagonalmatrix1;
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public IntegerDiagonalMatrix multiply(IntegerDiagonalMatrix integerdiagonalmatrix) {
		if (super.matrix[0].length == integerdiagonalmatrix.rows()) {
			int ai[] = new int[super.matrix[0].length];
			ai[0] = super.matrix[0][0] * integerdiagonalmatrix.getElement(0, 0);
			for (int i = 1; i < ai.length; i++)
				ai[i] = super.matrix[0][i] * integerdiagonalmatrix.getElement(i, i);

			return new IntegerDiagonalMatrix(ai);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public Matrix transpose() {
		return this;
	}

	public DoubleSquareMatrix[] luDecompose() {
		DoubleDiagonalMatrix adoublediagonalmatrix[] = new DoubleDiagonalMatrix[2];
		adoublediagonalmatrix[0] = DoubleDiagonalMatrix.identity(super.matrix[0].length);
		adoublediagonalmatrix[1] = (DoubleDiagonalMatrix)toDoubleMatrix();
		return adoublediagonalmatrix;
	}

	public DoubleSquareMatrix[] choleskyDecompose() {
		DoubleDiagonalMatrix adoublediagonalmatrix[] = new DoubleDiagonalMatrix[2];
		double ad[] = new double[super.matrix[0].length];
		ad[0] = Math.sqrt(super.matrix[0][0]);
		for (int i = 1; i < ad.length; i++)
			ad[i] = Math.sqrt(super.matrix[0][i]);

		adoublediagonalmatrix[0] = new DoubleDiagonalMatrix(ad);
		adoublediagonalmatrix[1] = adoublediagonalmatrix[0];
		return adoublediagonalmatrix;
	}
}
