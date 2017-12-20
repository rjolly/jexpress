package JSci.maths;


// Referenced classes of package JSci.maths:
//			IntegerSquareMatrix, Complex, ComplexMatrix, ComplexTridiagonalMatrix, 
//			DimensionException, DoubleMatrix, DoubleSquareMatrix, DoubleTridiagonalMatrix, 
//			IntegerMatrix, IntegerVector, MatrixDimensionException, Matrix

public class IntegerTridiagonalMatrix extends IntegerSquareMatrix {

	protected IntegerTridiagonalMatrix() {
	}

	public IntegerTridiagonalMatrix(int i) {
		this();
		super.matrix = new int[3][i];
	}

	public IntegerTridiagonalMatrix(int ai[][]) {
		this(ai.length);
		if (ai.length == ai[0].length) {
			super.matrix[1][0] = ai[0][0];
			super.matrix[2][0] = ai[0][1];
			int i;
			for (i = 1; i < ai.length - 1; i++) {
				super.matrix[0][i] = ai[i][i - 1];
				super.matrix[1][i] = ai[i][i];
				super.matrix[2][i] = ai[i][i + 1];
			}

			super.matrix[0][i] = ai[i][i - 1];
			super.matrix[1][i] = ai[i][i];
			return;
		} else {
			super.matrix = null;
			throw new MatrixDimensionException("The array is not square.");
		}
	}

	public boolean equals(Object obj) {
		if (obj != null && (obj instanceof IntegerTridiagonalMatrix) && super.matrix[1].length == ((IntegerTridiagonalMatrix)obj).rows()) {
			IntegerTridiagonalMatrix integertridiagonalmatrix = (IntegerTridiagonalMatrix)obj;
			if (super.matrix[1][0] != integertridiagonalmatrix.getElement(0, 0))
				return false;
			if (super.matrix[2][0] != integertridiagonalmatrix.getElement(0, 1))
				return false;
			int i;
			for (i = 1; i < super.matrix[1].length - 1; i++) {
				if (super.matrix[0][i] != integertridiagonalmatrix.getElement(i, i - 1))
					return false;
				if (super.matrix[1][i] != integertridiagonalmatrix.getElement(i, i))
					return false;
				if (super.matrix[2][i] != integertridiagonalmatrix.getElement(i, i + 1))
					return false;
			}

			if (super.matrix[0][i] != integertridiagonalmatrix.getElement(i, i - 1))
				return false;
			return super.matrix[1][i] == integertridiagonalmatrix.getElement(i, i);
		} else {
			return false;
		}
	}

	public String toString() {
		StringBuffer stringbuffer = new StringBuffer(super.matrix.length * super.matrix[1].length);
		for (int j = 0; j < rows(); j++) {
			for (int i = 0; i < columns(); i++) {
				stringbuffer.append(getElement(j, i));
				stringbuffer.append(' ');
			}

			stringbuffer.append('\n');
		}

		return stringbuffer.toString();
	}

	public DoubleMatrix toDoubleMatrix() {
		DoubleTridiagonalMatrix doubletridiagonalmatrix = new DoubleTridiagonalMatrix(super.matrix[1].length);
		((DoubleMatrix) (doubletridiagonalmatrix)).matrix[1][0] = super.matrix[1][0];
		((DoubleMatrix) (doubletridiagonalmatrix)).matrix[2][0] = super.matrix[2][0];
		int i;
		for (i = 1; i < super.matrix[1].length - 1; i++) {
			((DoubleMatrix) (doubletridiagonalmatrix)).matrix[0][i] = super.matrix[0][i];
			((DoubleMatrix) (doubletridiagonalmatrix)).matrix[1][i] = super.matrix[1][i];
			((DoubleMatrix) (doubletridiagonalmatrix)).matrix[2][i] = super.matrix[2][i];
		}

		((DoubleMatrix) (doubletridiagonalmatrix)).matrix[0][i] = super.matrix[0][i];
		((DoubleMatrix) (doubletridiagonalmatrix)).matrix[1][i] = super.matrix[1][i];
		return doubletridiagonalmatrix;
	}

	public ComplexMatrix toComplexMatrix() {
		ComplexTridiagonalMatrix complextridiagonalmatrix = new ComplexTridiagonalMatrix(super.matrix[1].length);
		((ComplexMatrix) (complextridiagonalmatrix)).matrix[1][0] = new Complex(super.matrix[1][0], 0.0D);
		((ComplexMatrix) (complextridiagonalmatrix)).matrix[2][0] = new Complex(super.matrix[2][0], 0.0D);
		int i;
		for (i = 1; i < super.matrix[1].length - 1; i++) {
			((ComplexMatrix) (complextridiagonalmatrix)).matrix[0][i] = new Complex(super.matrix[0][i], 0.0D);
			((ComplexMatrix) (complextridiagonalmatrix)).matrix[1][i] = new Complex(super.matrix[1][i], 0.0D);
			((ComplexMatrix) (complextridiagonalmatrix)).matrix[2][i] = new Complex(super.matrix[2][i], 0.0D);
		}

		((ComplexMatrix) (complextridiagonalmatrix)).matrix[0][i] = new Complex(super.matrix[0][i], 0.0D);
		((ComplexMatrix) (complextridiagonalmatrix)).matrix[1][i] = new Complex(super.matrix[1][i], 0.0D);
		return complextridiagonalmatrix;
	}

	public int getElement(int i, int j) {
		if (i >= 0 && i < super.matrix[1].length && j >= 0 && j < super.matrix[1].length) {
			if (j == i - 1)
				return super.matrix[0][i];
			if (j == i)
				return super.matrix[1][i];
			if (j == i + 1)
				return super.matrix[2][i];
			else
				return 0;
		} else {
			throw new MatrixDimensionException("Invalid element.");
		}
	}

	public void setElement(int i, int j, int k) {
		if (i >= 0 && i < super.matrix[1].length && j >= 0 && j < super.matrix[1].length) {
			if (j == i - 1) {
				super.matrix[0][i] = k;
				return;
			}
			if (j == i) {
				super.matrix[1][i] = k;
				return;
			}
			if (j == i + 1) {
				super.matrix[2][i] = k;
				return;
			} else {
				throw new MatrixDimensionException("Invalid element.");
			}
		} else {
			throw new MatrixDimensionException("Invalid element.");
		}
	}

	public boolean isSymmetric() {
		if (super.matrix[0][1] != super.matrix[2][0])
			return false;
		for (int i = 1; i < super.matrix[1].length - 1; i++)
			if (super.matrix[0][i + 1] != super.matrix[2][i])
				return false;

		return true;
	}

	public int trace() {
		int i = super.matrix[1][0];
		for (int j = 1; j < super.matrix[1].length; j++)
			i += super.matrix[1][j];

		return i;
	}

	public int infNorm() {
		int i = Math.abs(super.matrix[1][0]) + Math.abs(super.matrix[2][0]);
		int l;
		for (l = 1; l < super.matrix[1].length - 1; l++) {
			int j = Math.abs(super.matrix[0][l]) + Math.abs(super.matrix[1][l]) + Math.abs(super.matrix[2][l]);
			if (j > i)
				i = j;
		}

		int k = Math.abs(super.matrix[0][l]) + Math.abs(super.matrix[1][l]);
		if (k > i)
			i = k;
		return i;
	}

	public double frobeniusNorm() {
		int i = super.matrix[1][0] * super.matrix[1][0] + super.matrix[2][0] * super.matrix[2][0];
		int j;
		for (j = 1; j < super.matrix[1].length - 1; j++)
			i += super.matrix[0][j] * super.matrix[0][j] + super.matrix[1][j] * super.matrix[1][j] + super.matrix[2][j] * super.matrix[2][j];

		i += super.matrix[0][j] * super.matrix[0][j] + super.matrix[1][j] * super.matrix[1][j];
		return Math.sqrt(i);
	}

	public int rows() {
		return super.matrix[1].length;
	}

	public int columns() {
		return super.matrix[1].length;
	}

	public Matrix add(Matrix matrix) {
		if (matrix instanceof IntegerTridiagonalMatrix)
			return add((IntegerTridiagonalMatrix)matrix);
		if (matrix instanceof IntegerSquareMatrix)
			return rawAdd((IntegerSquareMatrix)matrix);
		if (matrix instanceof IntegerMatrix)
			return rawAdd((IntegerMatrix)matrix);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public IntegerMatrix add(IntegerMatrix integermatrix) {
		if (integermatrix instanceof IntegerTridiagonalMatrix)
			return add((IntegerTridiagonalMatrix)integermatrix);
		if (integermatrix instanceof IntegerSquareMatrix)
			return rawAdd((IntegerSquareMatrix)integermatrix);
		else
			return rawAdd(integermatrix);
	}

	private IntegerMatrix rawAdd(IntegerMatrix integermatrix) {
		if (super.matrix[1].length == integermatrix.rows() && super.matrix[1].length == integermatrix.columns()) {
			int ai[][] = new int[super.matrix[1].length][super.matrix[1].length];
			for (int j = 0; j < ai.length; j++) {
				ai[j][0] = getElement(j, 0) + integermatrix.getElement(j, 0);
				for (int i = 1; i < ai.length; i++)
					ai[j][i] = getElement(j, i) + integermatrix.getElement(j, i);

			}

			return new IntegerSquareMatrix(ai);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public IntegerSquareMatrix add(IntegerSquareMatrix integersquarematrix) {
		if (integersquarematrix instanceof IntegerTridiagonalMatrix)
			return add((IntegerTridiagonalMatrix)integersquarematrix);
		else
			return rawAdd(integersquarematrix);
	}

	private IntegerSquareMatrix rawAdd(IntegerSquareMatrix integersquarematrix) {
		if (super.matrix[1].length == integersquarematrix.rows()) {
			int ai[][] = new int[super.matrix[1].length][super.matrix[1].length];
			for (int j = 0; j < ai.length; j++) {
				ai[j][0] = getElement(j, 0) + integersquarematrix.getElement(j, 0);
				for (int i = 1; i < ai.length; i++)
					ai[j][i] = getElement(j, i) + integersquarematrix.getElement(j, i);

			}

			return new IntegerSquareMatrix(ai);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public IntegerTridiagonalMatrix add(IntegerTridiagonalMatrix integertridiagonalmatrix) {
		int i = super.matrix[1].length;
		if (i == integertridiagonalmatrix.rows()) {
			IntegerTridiagonalMatrix integertridiagonalmatrix1 = new IntegerTridiagonalMatrix(i);
			((IntegerMatrix) (integertridiagonalmatrix1)).matrix[1][0] = super.matrix[1][0] + integertridiagonalmatrix.getElement(0, 0);
			((IntegerMatrix) (integertridiagonalmatrix1)).matrix[2][0] = super.matrix[2][0] + integertridiagonalmatrix.getElement(0, 1);
			i--;
			for (int j = 1; j < i; j++) {
				((IntegerMatrix) (integertridiagonalmatrix1)).matrix[0][j] = super.matrix[0][j] + integertridiagonalmatrix.getElement(j, j - 1);
				((IntegerMatrix) (integertridiagonalmatrix1)).matrix[1][j] = super.matrix[1][j] + integertridiagonalmatrix.getElement(j, j);
				((IntegerMatrix) (integertridiagonalmatrix1)).matrix[2][j] = super.matrix[2][j] + integertridiagonalmatrix.getElement(j, j + 1);
			}

			((IntegerMatrix) (integertridiagonalmatrix1)).matrix[0][i] = super.matrix[0][i] + integertridiagonalmatrix.getElement(i, i - 1);
			((IntegerMatrix) (integertridiagonalmatrix1)).matrix[1][i] = super.matrix[1][i] + integertridiagonalmatrix.getElement(i, i);
			return integertridiagonalmatrix1;
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public Matrix subtract(Matrix matrix) {
		if (matrix instanceof IntegerTridiagonalMatrix)
			return subtract((IntegerTridiagonalMatrix)matrix);
		if (matrix instanceof IntegerSquareMatrix)
			return rawSubtract((IntegerSquareMatrix)matrix);
		if (matrix instanceof IntegerMatrix)
			return rawSubtract((IntegerMatrix)matrix);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public IntegerMatrix subtract(IntegerMatrix integermatrix) {
		if (integermatrix instanceof IntegerTridiagonalMatrix)
			return subtract((IntegerTridiagonalMatrix)integermatrix);
		if (integermatrix instanceof IntegerSquareMatrix)
			return rawSubtract((IntegerSquareMatrix)integermatrix);
		else
			return rawSubtract(integermatrix);
	}

	private IntegerMatrix rawSubtract(IntegerMatrix integermatrix) {
		if (super.matrix[1].length == integermatrix.rows() && super.matrix[1].length == integermatrix.columns()) {
			int ai[][] = new int[super.matrix[1].length][super.matrix[1].length];
			for (int j = 0; j < ai.length; j++) {
				ai[j][0] = getElement(j, 0) - integermatrix.getElement(j, 0);
				for (int i = 1; i < ai.length; i++)
					ai[j][i] = getElement(j, i) - integermatrix.getElement(j, i);

			}

			return new IntegerSquareMatrix(ai);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public IntegerSquareMatrix subtract(IntegerSquareMatrix integersquarematrix) {
		if (integersquarematrix instanceof IntegerTridiagonalMatrix)
			return subtract((IntegerTridiagonalMatrix)integersquarematrix);
		else
			return rawSubtract(integersquarematrix);
	}

	private IntegerSquareMatrix rawSubtract(IntegerSquareMatrix integersquarematrix) {
		if (super.matrix[1].length == integersquarematrix.rows()) {
			int ai[][] = new int[super.matrix[1].length][super.matrix[1].length];
			for (int j = 0; j < ai.length; j++) {
				ai[j][0] = getElement(j, 0) - integersquarematrix.getElement(j, 0);
				for (int i = 1; i < ai.length; i++)
					ai[j][i] = getElement(j, i) - integersquarematrix.getElement(j, i);

			}

			return new IntegerSquareMatrix(ai);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public IntegerTridiagonalMatrix subtract(IntegerTridiagonalMatrix integertridiagonalmatrix) {
		int i = super.matrix[1].length;
		if (i == integertridiagonalmatrix.rows()) {
			IntegerTridiagonalMatrix integertridiagonalmatrix1 = new IntegerTridiagonalMatrix(i);
			((IntegerMatrix) (integertridiagonalmatrix1)).matrix[1][0] = super.matrix[1][0] - integertridiagonalmatrix.getElement(0, 0);
			((IntegerMatrix) (integertridiagonalmatrix1)).matrix[2][0] = super.matrix[2][0] - integertridiagonalmatrix.getElement(0, 1);
			i--;
			for (int j = 1; j < i; j++) {
				((IntegerMatrix) (integertridiagonalmatrix1)).matrix[0][j] = super.matrix[0][j] - integertridiagonalmatrix.getElement(j, j - 1);
				((IntegerMatrix) (integertridiagonalmatrix1)).matrix[1][j] = super.matrix[1][j] - integertridiagonalmatrix.getElement(j, j);
				((IntegerMatrix) (integertridiagonalmatrix1)).matrix[2][j] = super.matrix[2][j] - integertridiagonalmatrix.getElement(j, j + 1);
			}

			((IntegerMatrix) (integertridiagonalmatrix1)).matrix[0][i] = super.matrix[0][i] - integertridiagonalmatrix.getElement(i, i - 1);
			((IntegerMatrix) (integertridiagonalmatrix1)).matrix[1][i] = super.matrix[1][i] - integertridiagonalmatrix.getElement(i, i);
			return integertridiagonalmatrix1;
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public IntegerMatrix scalarMultiply(int i) {
		int j = super.matrix[1].length;
		IntegerTridiagonalMatrix integertridiagonalmatrix = new IntegerTridiagonalMatrix(j);
		((IntegerMatrix) (integertridiagonalmatrix)).matrix[1][0] = i * super.matrix[1][0];
		((IntegerMatrix) (integertridiagonalmatrix)).matrix[2][0] = i * super.matrix[2][0];
		j--;
		for (int k = 1; k < j; k++) {
			((IntegerMatrix) (integertridiagonalmatrix)).matrix[0][k] = i * super.matrix[0][k];
			((IntegerMatrix) (integertridiagonalmatrix)).matrix[1][k] = i * super.matrix[1][k];
			((IntegerMatrix) (integertridiagonalmatrix)).matrix[2][k] = i * super.matrix[2][k];
		}

		((IntegerMatrix) (integertridiagonalmatrix)).matrix[0][j] = i * super.matrix[0][j];
		((IntegerMatrix) (integertridiagonalmatrix)).matrix[0][j] = i * super.matrix[1][j];
		return integertridiagonalmatrix;
	}

	public IntegerVector multiply(IntegerVector integervector) {
		int i = super.matrix[1].length;
		if (i == integervector.dimension()) {
			int ai[] = new int[i];
			ai[0] = super.matrix[1][0] * integervector.getComponent(0) + super.matrix[2][0] * integervector.getComponent(1);
			i--;
			for (int j = 1; j < i; j++)
				ai[j] = super.matrix[0][j] * integervector.getComponent(j - 1) + super.matrix[1][j] * integervector.getComponent(j) + super.matrix[2][j] * integervector.getComponent(j + 1);

			ai[i] = super.matrix[0][i] * integervector.getComponent(i - 1) + super.matrix[1][i] * integervector.getComponent(i);
			return new IntegerVector(ai);
		} else {
			throw new DimensionException("Matrix and vector are incompatible.");
		}
	}

	public Matrix multiply(Matrix matrix) {
		if (matrix instanceof IntegerTridiagonalMatrix)
			return multiply((IntegerTridiagonalMatrix)matrix);
		if (matrix instanceof IntegerSquareMatrix)
			return rawMultiply((IntegerSquareMatrix)matrix);
		if (matrix instanceof IntegerMatrix)
			return rawMultiply((IntegerMatrix)matrix);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public IntegerMatrix multiply(IntegerMatrix integermatrix) {
		if (integermatrix instanceof IntegerTridiagonalMatrix)
			return multiply((IntegerTridiagonalMatrix)integermatrix);
		if (integermatrix instanceof IntegerSquareMatrix)
			return rawMultiply((IntegerSquareMatrix)integermatrix);
		else
			return rawMultiply(integermatrix);
	}

	private IntegerMatrix rawMultiply(IntegerMatrix integermatrix) {
		if (super.matrix[1].length == integermatrix.rows()) {
			int ai[][] = new int[super.matrix[1].length][integermatrix.columns()];
			for (int k = 0; k < ai.length; k++) {
				for (int j = 0; j < ai[0].length; j++) {
					ai[k][j] = getElement(k, 0) * integermatrix.getElement(0, j);
					for (int i = 1; i < super.matrix[1].length; i++)
						ai[k][j] += getElement(k, i) * integermatrix.getElement(i, j);

				}

			}

			return new IntegerMatrix(ai);
		} else {
			throw new MatrixDimensionException("Incompatible matrices.");
		}
	}

	public IntegerSquareMatrix multiply(IntegerSquareMatrix integersquarematrix) {
		if (integersquarematrix instanceof IntegerTridiagonalMatrix)
			return multiply((IntegerTridiagonalMatrix)integersquarematrix);
		else
			return rawMultiply(integersquarematrix);
	}

	private IntegerSquareMatrix rawMultiply(IntegerSquareMatrix integersquarematrix) {
		if (super.matrix[1].length == integersquarematrix.rows()) {
			int ai[][] = new int[super.matrix[1].length][super.matrix[1].length];
			for (int k = 0; k < ai.length; k++) {
				for (int j = 0; j < ai.length; j++) {
					ai[k][j] = getElement(k, 0) * integersquarematrix.getElement(0, j);
					for (int i = 1; i < ai.length; i++)
						ai[k][j] += getElement(k, i) * integersquarematrix.getElement(i, j);

				}

			}

			return new IntegerSquareMatrix(ai);
		} else {
			throw new MatrixDimensionException("Incompatible matrices.");
		}
	}

	public IntegerSquareMatrix multiply(IntegerTridiagonalMatrix integertridiagonalmatrix) {
		int i = super.matrix[1].length;
		if (i == integertridiagonalmatrix.rows()) {
			int ai[][] = new int[i][i];
			ai[0][0] = super.matrix[1][0] * integertridiagonalmatrix.getElement(0, 0) + super.matrix[2][0] * integertridiagonalmatrix.getElement(1, 0);
			ai[0][1] = super.matrix[1][0] * integertridiagonalmatrix.getElement(0, 1) + super.matrix[2][0] * integertridiagonalmatrix.getElement(1, 1);
			ai[0][2] = super.matrix[2][0] * integertridiagonalmatrix.getElement(1, 2);
			if (i > 3) {
				ai[1][0] = super.matrix[0][1] * integertridiagonalmatrix.getElement(0, 0) + super.matrix[1][1] * integertridiagonalmatrix.getElement(1, 0);
				ai[1][1] = super.matrix[0][1] * integertridiagonalmatrix.getElement(0, 1) + super.matrix[1][1] * integertridiagonalmatrix.getElement(1, 1) + super.matrix[2][1] * integertridiagonalmatrix.getElement(2, 1);
				ai[1][2] = super.matrix[1][1] * integertridiagonalmatrix.getElement(1, 2) + super.matrix[2][1] * integertridiagonalmatrix.getElement(2, 2);
				ai[1][3] = super.matrix[2][1] * integertridiagonalmatrix.getElement(2, 3);
			}
			if (i == 3) {
				ai[1][0] = super.matrix[0][1] * integertridiagonalmatrix.getElement(0, 0) + super.matrix[1][1] * integertridiagonalmatrix.getElement(1, 0);
				ai[1][1] = super.matrix[0][1] * integertridiagonalmatrix.getElement(0, 1) + super.matrix[1][1] * integertridiagonalmatrix.getElement(1, 1) + super.matrix[2][1] * integertridiagonalmatrix.getElement(2, 1);
				ai[1][2] = super.matrix[1][1] * integertridiagonalmatrix.getElement(1, 2) + super.matrix[2][1] * integertridiagonalmatrix.getElement(2, 2);
			} else
			if (i > 4) {
				for (int j = 2; j < i - 2; j++) {
					ai[j][j - 2] = super.matrix[0][j] * integertridiagonalmatrix.getElement(j - 1, j - 2);
					ai[j][j - 1] = super.matrix[0][j] * integertridiagonalmatrix.getElement(j - 1, j - 1) + super.matrix[1][j] * integertridiagonalmatrix.getElement(j, j - 1);
					ai[j][j] = super.matrix[0][j] * integertridiagonalmatrix.getElement(j - 1, j) + super.matrix[1][j] * integertridiagonalmatrix.getElement(j, j) + super.matrix[2][j] * integertridiagonalmatrix.getElement(j + 1, j);
					ai[j][j + 1] = super.matrix[1][j] * integertridiagonalmatrix.getElement(j, j + 1) + super.matrix[2][j] * integertridiagonalmatrix.getElement(j + 1, j + 1);
					ai[j][j + 2] = super.matrix[2][j] * integertridiagonalmatrix.getElement(j + 1, j + 2);
				}

			}
			if (i > 3) {
				ai[i - 2][i - 4] = super.matrix[0][i - 2] * integertridiagonalmatrix.getElement(i - 3, i - 4);
				ai[i - 2][i - 3] = super.matrix[0][i - 2] * integertridiagonalmatrix.getElement(i - 3, i - 3) + super.matrix[1][i - 2] * integertridiagonalmatrix.getElement(i - 2, i - 3);
				ai[i - 2][i - 2] = super.matrix[0][i - 2] * integertridiagonalmatrix.getElement(i - 3, i - 2) + super.matrix[1][i - 2] * integertridiagonalmatrix.getElement(i - 2, i - 2) + super.matrix[2][i - 2] * integertridiagonalmatrix.getElement(i - 1, i - 2);
				ai[i - 2][i - 1] = super.matrix[1][i - 2] * integertridiagonalmatrix.getElement(i - 2, i - 1) + super.matrix[2][i - 2] * integertridiagonalmatrix.getElement(i - 1, i - 1);
			}
			i--;
			ai[i][i - 2] = super.matrix[0][i] * integertridiagonalmatrix.getElement(i - 1, i - 2);
			ai[i][i - 1] = super.matrix[0][i] * integertridiagonalmatrix.getElement(i - 1, i - 1) + super.matrix[1][i] * integertridiagonalmatrix.getElement(i, i - 1);
			ai[i][i] = super.matrix[0][i] * integertridiagonalmatrix.getElement(i - 1, i) + super.matrix[1][i] * integertridiagonalmatrix.getElement(i, i);
			return new IntegerSquareMatrix(ai);
		} else {
			throw new MatrixDimensionException("Incompatible matrices.");
		}
	}

	public Matrix transpose() {
		int i = super.matrix[1].length;
		IntegerTridiagonalMatrix integertridiagonalmatrix = new IntegerTridiagonalMatrix(i);
		((IntegerMatrix) (integertridiagonalmatrix)).matrix[1][0] = super.matrix[1][0];
		integertridiagonalmatrix.setElement(1, 0, super.matrix[2][0]);
		i--;
		for (int j = 1; j < i; j++) {
			integertridiagonalmatrix.setElement(j - 1, j, super.matrix[0][j]);
			((IntegerMatrix) (integertridiagonalmatrix)).matrix[1][j] = super.matrix[1][j];
			integertridiagonalmatrix.setElement(j + 1, j, super.matrix[2][j]);
		}

		integertridiagonalmatrix.setElement(i - 1, i, super.matrix[0][i]);
		((IntegerMatrix) (integertridiagonalmatrix)).matrix[1][i] = super.matrix[1][i];
		return integertridiagonalmatrix;
	}

	public DoubleSquareMatrix[] luDecompose() {
		double ad[][][] = new double[2][super.matrix[1].length][super.matrix[1].length];
		ad[0][0][0] = 1.0D;
		for (int i = 1; i < super.matrix[1].length; i++)
			ad[0][i][i] = 1.0D;

		for (int l = 0; l < super.matrix[1].length; l++) {
			for (int j = 0; j <= l; j++) {
				double d = getElement(j, l);
				for (int i1 = 0; i1 < j; i1++)
					d -= ad[0][j][i1] * ad[1][i1][l];

				ad[1][j][l] = d;
			}

			for (int k = l + 1; k < super.matrix[1].length; k++) {
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
		double ad[][][] = new double[2][super.matrix[1].length][super.matrix[1].length];
		ad[0][0][0] = ad[1][0][0] = Math.sqrt(getElement(0, 0));
		for (int i = 1; i < super.matrix[1].length; i++)
			ad[0][i][0] = ad[1][0][i] = (double)getElement(i, 0) / ad[0][0][0];

		for (int l = 1; l < super.matrix[1].length; l++) {
			double d = getElement(l, l);
			for (int j = 0; j < l; j++)
				d -= ad[0][l][j] * ad[0][l][j];

			ad[0][l][l] = ad[1][l][l] = Math.sqrt(d);
			for (int k = l + 1; k < super.matrix[1].length; k++) {
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
}
