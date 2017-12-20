package JSci.maths;

import JSci.GlobalSettings;

// Referenced classes of package JSci.maths:
//			DoubleSquareMatrix, Complex, ComplexMatrix, ComplexTridiagonalMatrix, 
//			DimensionException, DoubleMatrix, DoubleVector, IntegerMatrix, 
//			IntegerTridiagonalMatrix, Mapping, MatrixDimensionException, Matrix

public class DoubleTridiagonalMatrix extends DoubleSquareMatrix {

	protected DoubleTridiagonalMatrix() {
	}

	public DoubleTridiagonalMatrix(int i) {
		this();
		super.matrix = new double[3][i];
	}

	public DoubleTridiagonalMatrix(double ad[][]) {
		this(ad.length);
		if (ad.length == ad[0].length) {
			super.matrix[1][0] = ad[0][0];
			super.matrix[2][0] = ad[0][1];
			int i;
			for (i = 1; i < ad.length - 1; i++) {
				super.matrix[0][i] = ad[i][i - 1];
				super.matrix[1][i] = ad[i][i];
				super.matrix[2][i] = ad[i][i + 1];
			}

			super.matrix[0][i] = ad[i][i - 1];
			super.matrix[1][i] = ad[i][i];
			return;
		} else {
			super.matrix = null;
			throw new MatrixDimensionException("The array is not square.");
		}
	}

	public boolean equals(Object obj) {
		if (obj != null && (obj instanceof DoubleTridiagonalMatrix) && super.matrix[1].length == ((DoubleTridiagonalMatrix)obj).rows()) {
			DoubleTridiagonalMatrix doubletridiagonalmatrix = (DoubleTridiagonalMatrix)obj;
			if (Math.abs(super.matrix[1][0] - doubletridiagonalmatrix.getElement(0, 0)) > GlobalSettings.ZERO_TOL)
				return false;
			if (Math.abs(super.matrix[2][0] - doubletridiagonalmatrix.getElement(0, 1)) > GlobalSettings.ZERO_TOL)
				return false;
			int i;
			for (i = 1; i < super.matrix[1].length - 1; i++) {
				if (Math.abs(super.matrix[0][i] - doubletridiagonalmatrix.getElement(i, i - 1)) > GlobalSettings.ZERO_TOL)
					return false;
				if (Math.abs(super.matrix[1][i] - doubletridiagonalmatrix.getElement(i, i)) > GlobalSettings.ZERO_TOL)
					return false;
				if (Math.abs(super.matrix[2][i] - doubletridiagonalmatrix.getElement(i, i + 1)) > GlobalSettings.ZERO_TOL)
					return false;
			}

			if (Math.abs(super.matrix[0][i] - doubletridiagonalmatrix.getElement(i, i - 1)) > GlobalSettings.ZERO_TOL)
				return false;
			return Math.abs(super.matrix[1][i] - doubletridiagonalmatrix.getElement(i, i)) <= GlobalSettings.ZERO_TOL;
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

	public IntegerMatrix toIntegerMatrix() {
		IntegerTridiagonalMatrix integertridiagonalmatrix = new IntegerTridiagonalMatrix(super.matrix[1].length);
		((IntegerMatrix) (integertridiagonalmatrix)).matrix[1][0] = Math.round((float)super.matrix[1][0]);
		((IntegerMatrix) (integertridiagonalmatrix)).matrix[2][0] = Math.round((float)super.matrix[2][0]);
		int i;
		for (i = 1; i < super.matrix[1].length - 1; i++) {
			((IntegerMatrix) (integertridiagonalmatrix)).matrix[0][i] = Math.round((float)super.matrix[0][i]);
			((IntegerMatrix) (integertridiagonalmatrix)).matrix[1][i] = Math.round((float)super.matrix[1][i]);
			((IntegerMatrix) (integertridiagonalmatrix)).matrix[2][i] = Math.round((float)super.matrix[2][i]);
		}

		((IntegerMatrix) (integertridiagonalmatrix)).matrix[0][i] = Math.round((float)super.matrix[0][i]);
		((IntegerMatrix) (integertridiagonalmatrix)).matrix[1][i] = Math.round((float)super.matrix[1][i]);
		return integertridiagonalmatrix;
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

	public double getElement(int i, int j) {
		if (i >= 0 && i < super.matrix[1].length && j >= 0 && j < super.matrix[1].length) {
			if (j == i - 1)
				return super.matrix[0][i];
			if (j == i)
				return super.matrix[1][i];
			if (j == i + 1)
				return super.matrix[2][i];
			else
				return 0.0D;
		} else {
			throw new MatrixDimensionException("Invalid element.");
		}
	}

	public void setElement(int i, int j, double d) {
		if (i >= 0 && i < super.matrix[1].length && j >= 0 && j < super.matrix[1].length) {
			if (j == i - 1) {
				super.matrix[0][i] = d;
				return;
			}
			if (j == i) {
				super.matrix[1][i] = d;
				return;
			}
			if (j == i + 1) {
				super.matrix[2][i] = d;
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

	public double trace() {
		double d = super.matrix[1][0];
		for (int i = 1; i < super.matrix[1].length; i++)
			d += super.matrix[1][i];

		return d;
	}

	public double infNorm() {
		double d = Math.abs(super.matrix[1][0]) + Math.abs(super.matrix[2][0]);
		int i;
		for (i = 1; i < super.matrix[1].length - 1; i++) {
			double d1 = Math.abs(super.matrix[0][i]) + Math.abs(super.matrix[1][i]) + Math.abs(super.matrix[2][i]);
			if (d1 > d)
				d = d1;
		}

		double d2 = Math.abs(super.matrix[0][i]) + Math.abs(super.matrix[1][i]);
		if (d2 > d)
			d = d2;
		return d;
	}

	public double frobeniusNorm() {
		double d = super.matrix[1][0] * super.matrix[1][0] + super.matrix[2][0] * super.matrix[2][0];
		int i;
		for (i = 1; i < super.matrix[1].length - 1; i++)
			d += super.matrix[0][i] * super.matrix[0][i] + super.matrix[1][i] * super.matrix[1][i] + super.matrix[2][i] * super.matrix[2][i];

		d += super.matrix[0][i] * super.matrix[0][i] + super.matrix[1][i] * super.matrix[1][i];
		return Math.sqrt(d);
	}

	public int rows() {
		return super.matrix[1].length;
	}

	public int columns() {
		return super.matrix[1].length;
	}

	public Matrix add(Matrix matrix) {
		if (matrix instanceof DoubleTridiagonalMatrix)
			return add((DoubleTridiagonalMatrix)matrix);
		if (matrix instanceof DoubleSquareMatrix)
			return rawAdd((DoubleSquareMatrix)matrix);
		if (matrix instanceof DoubleMatrix)
			return rawAdd((DoubleMatrix)matrix);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public DoubleMatrix add(DoubleMatrix doublematrix) {
		if (doublematrix instanceof DoubleTridiagonalMatrix)
			return add((DoubleTridiagonalMatrix)doublematrix);
		if (doublematrix instanceof DoubleSquareMatrix)
			return rawAdd((DoubleSquareMatrix)doublematrix);
		else
			return rawAdd(doublematrix);
	}

	private DoubleMatrix rawAdd(DoubleMatrix doublematrix) {
		if (super.matrix[1].length == doublematrix.rows() && super.matrix[1].length == doublematrix.columns()) {
			double ad[][] = new double[super.matrix[1].length][super.matrix[1].length];
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
		if (doublesquarematrix instanceof DoubleTridiagonalMatrix)
			return add((DoubleTridiagonalMatrix)doublesquarematrix);
		else
			return rawAdd(doublesquarematrix);
	}

	private DoubleSquareMatrix rawAdd(DoubleSquareMatrix doublesquarematrix) {
		if (super.matrix[1].length == doublesquarematrix.rows()) {
			double ad[][] = new double[super.matrix[1].length][super.matrix[1].length];
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

	public DoubleTridiagonalMatrix add(DoubleTridiagonalMatrix doubletridiagonalmatrix) {
		int i = super.matrix[1].length;
		if (i == doubletridiagonalmatrix.rows()) {
			DoubleTridiagonalMatrix doubletridiagonalmatrix1 = new DoubleTridiagonalMatrix(i);
			((DoubleMatrix) (doubletridiagonalmatrix1)).matrix[1][0] = super.matrix[1][0] + doubletridiagonalmatrix.getElement(0, 0);
			((DoubleMatrix) (doubletridiagonalmatrix1)).matrix[2][0] = super.matrix[2][0] + doubletridiagonalmatrix.getElement(0, 1);
			i--;
			for (int j = 1; j < i; j++) {
				((DoubleMatrix) (doubletridiagonalmatrix1)).matrix[0][j] = super.matrix[0][j] + doubletridiagonalmatrix.getElement(j, j - 1);
				((DoubleMatrix) (doubletridiagonalmatrix1)).matrix[1][j] = super.matrix[1][j] + doubletridiagonalmatrix.getElement(j, j);
				((DoubleMatrix) (doubletridiagonalmatrix1)).matrix[2][j] = super.matrix[2][j] + doubletridiagonalmatrix.getElement(j, j + 1);
			}

			((DoubleMatrix) (doubletridiagonalmatrix1)).matrix[0][i] = super.matrix[0][i] + doubletridiagonalmatrix.getElement(i, i - 1);
			((DoubleMatrix) (doubletridiagonalmatrix1)).matrix[1][i] = super.matrix[1][i] + doubletridiagonalmatrix.getElement(i, i);
			return doubletridiagonalmatrix1;
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public Matrix subtract(Matrix matrix) {
		if (matrix instanceof DoubleTridiagonalMatrix)
			return subtract((DoubleTridiagonalMatrix)matrix);
		if (matrix instanceof DoubleSquareMatrix)
			return rawSubtract((DoubleSquareMatrix)matrix);
		if (matrix instanceof DoubleMatrix)
			return rawSubtract((DoubleMatrix)matrix);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public DoubleMatrix subtract(DoubleMatrix doublematrix) {
		if (doublematrix instanceof DoubleTridiagonalMatrix)
			return subtract((DoubleTridiagonalMatrix)doublematrix);
		if (doublematrix instanceof DoubleSquareMatrix)
			return rawSubtract((DoubleSquareMatrix)doublematrix);
		else
			return rawSubtract(doublematrix);
	}

	private DoubleMatrix rawSubtract(DoubleMatrix doublematrix) {
		if (super.matrix[1].length == doublematrix.rows() && super.matrix[1].length == doublematrix.columns()) {
			double ad[][] = new double[super.matrix[1].length][super.matrix[1].length];
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
		if (doublesquarematrix instanceof DoubleTridiagonalMatrix)
			return subtract((DoubleTridiagonalMatrix)doublesquarematrix);
		else
			return rawSubtract(doublesquarematrix);
	}

	private DoubleSquareMatrix rawSubtract(DoubleSquareMatrix doublesquarematrix) {
		if (super.matrix[1].length == doublesquarematrix.rows()) {
			double ad[][] = new double[super.matrix[1].length][super.matrix[1].length];
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

	public DoubleTridiagonalMatrix subtract(DoubleTridiagonalMatrix doubletridiagonalmatrix) {
		int i = super.matrix[1].length;
		if (i == doubletridiagonalmatrix.rows()) {
			DoubleTridiagonalMatrix doubletridiagonalmatrix1 = new DoubleTridiagonalMatrix(i);
			((DoubleMatrix) (doubletridiagonalmatrix1)).matrix[1][0] = super.matrix[1][0] - doubletridiagonalmatrix.getElement(0, 0);
			((DoubleMatrix) (doubletridiagonalmatrix1)).matrix[2][0] = super.matrix[2][0] - doubletridiagonalmatrix.getElement(0, 1);
			i--;
			for (int j = 1; j < i; j++) {
				((DoubleMatrix) (doubletridiagonalmatrix1)).matrix[0][j] = super.matrix[0][j] - doubletridiagonalmatrix.getElement(j, j - 1);
				((DoubleMatrix) (doubletridiagonalmatrix1)).matrix[1][j] = super.matrix[1][j] - doubletridiagonalmatrix.getElement(j, j);
				((DoubleMatrix) (doubletridiagonalmatrix1)).matrix[2][j] = super.matrix[2][j] - doubletridiagonalmatrix.getElement(j, j + 1);
			}

			((DoubleMatrix) (doubletridiagonalmatrix1)).matrix[0][i] = super.matrix[0][i] - doubletridiagonalmatrix.getElement(i, i - 1);
			((DoubleMatrix) (doubletridiagonalmatrix1)).matrix[1][i] = super.matrix[1][i] - doubletridiagonalmatrix.getElement(i, i);
			return doubletridiagonalmatrix1;
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public DoubleMatrix scalarMultiply(double d) {
		int i = super.matrix[1].length;
		DoubleTridiagonalMatrix doubletridiagonalmatrix = new DoubleTridiagonalMatrix(i);
		((DoubleMatrix) (doubletridiagonalmatrix)).matrix[1][0] = d * super.matrix[1][0];
		((DoubleMatrix) (doubletridiagonalmatrix)).matrix[2][0] = d * super.matrix[2][0];
		i--;
		for (int j = 1; j < i; j++) {
			((DoubleMatrix) (doubletridiagonalmatrix)).matrix[0][j] = d * super.matrix[0][j];
			((DoubleMatrix) (doubletridiagonalmatrix)).matrix[1][j] = d * super.matrix[1][j];
			((DoubleMatrix) (doubletridiagonalmatrix)).matrix[2][j] = d * super.matrix[2][j];
		}

		((DoubleMatrix) (doubletridiagonalmatrix)).matrix[0][i] = d * super.matrix[0][i];
		((DoubleMatrix) (doubletridiagonalmatrix)).matrix[1][i] = d * super.matrix[1][i];
		return doubletridiagonalmatrix;
	}

	public DoubleVector multiply(DoubleVector doublevector) {
		int i = super.matrix[1].length;
		if (i == doublevector.dimension()) {
			double ad[] = new double[i];
			ad[0] = super.matrix[1][0] * doublevector.getComponent(0) + super.matrix[2][0] * doublevector.getComponent(1);
			i--;
			for (int j = 1; j < i; j++)
				ad[j] = super.matrix[0][j] * doublevector.getComponent(j - 1) + super.matrix[1][j] * doublevector.getComponent(j) + super.matrix[2][j] * doublevector.getComponent(j + 1);

			ad[i] = super.matrix[0][i] * doublevector.getComponent(i - 1) + super.matrix[1][i] * doublevector.getComponent(i);
			return new DoubleVector(ad);
		} else {
			throw new DimensionException("Matrix and vector are incompatible.");
		}
	}

	public Matrix multiply(Matrix matrix) {
		if (matrix instanceof DoubleTridiagonalMatrix)
			return multiply((DoubleTridiagonalMatrix)matrix);
		if (matrix instanceof DoubleSquareMatrix)
			return rawMultiply((DoubleSquareMatrix)matrix);
		if (matrix instanceof DoubleMatrix)
			return rawMultiply((DoubleMatrix)matrix);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public DoubleMatrix multiply(DoubleMatrix doublematrix) {
		if (doublematrix instanceof DoubleTridiagonalMatrix)
			return multiply((DoubleTridiagonalMatrix)doublematrix);
		if (doublematrix instanceof DoubleSquareMatrix)
			return rawMultiply((DoubleSquareMatrix)doublematrix);
		else
			return rawMultiply(doublematrix);
	}

	private DoubleMatrix rawMultiply(DoubleMatrix doublematrix) {
		if (super.matrix[1].length == doublematrix.rows()) {
			double ad[][] = new double[super.matrix[1].length][doublematrix.columns()];
			for (int k = 0; k < ad.length; k++) {
				for (int j = 0; j < ad[0].length; j++) {
					ad[k][j] = getElement(k, 0) * doublematrix.getElement(0, j);
					for (int i = 1; i < super.matrix[1].length; i++)
						ad[k][j] += getElement(k, i) * doublematrix.getElement(i, j);

				}

			}

			return new DoubleMatrix(ad);
		} else {
			throw new MatrixDimensionException("Incompatible matrices.");
		}
	}

	public DoubleSquareMatrix multiply(DoubleSquareMatrix doublesquarematrix) {
		if (doublesquarematrix instanceof DoubleTridiagonalMatrix)
			return multiply((DoubleTridiagonalMatrix)doublesquarematrix);
		else
			return rawMultiply(doublesquarematrix);
	}

	private DoubleSquareMatrix rawMultiply(DoubleSquareMatrix doublesquarematrix) {
		if (super.matrix[1].length == doublesquarematrix.rows()) {
			double ad[][] = new double[super.matrix[1].length][super.matrix[1].length];
			for (int k = 0; k < ad.length; k++) {
				for (int j = 0; j < ad.length; j++) {
					ad[k][j] = getElement(k, 0) * doublesquarematrix.getElement(0, j);
					for (int i = 1; i < ad.length; i++)
						ad[k][j] += getElement(k, i) * doublesquarematrix.getElement(i, j);

				}

			}

			return new DoubleSquareMatrix(ad);
		} else {
			throw new MatrixDimensionException("Incompatible matrices.");
		}
	}

	public DoubleSquareMatrix multiply(DoubleTridiagonalMatrix doubletridiagonalmatrix) {
		int i = super.matrix[1].length;
		if (i == doubletridiagonalmatrix.rows()) {
			double ad[][] = new double[i][i];
			ad[0][0] = super.matrix[1][0] * doubletridiagonalmatrix.getElement(0, 0) + super.matrix[2][0] * doubletridiagonalmatrix.getElement(1, 0);
			ad[0][1] = super.matrix[1][0] * doubletridiagonalmatrix.getElement(0, 1) + super.matrix[2][0] * doubletridiagonalmatrix.getElement(1, 1);
			ad[0][2] = super.matrix[2][0] * doubletridiagonalmatrix.getElement(1, 2);
			if (i > 3) {
				ad[1][0] = super.matrix[0][1] * doubletridiagonalmatrix.getElement(0, 0) + super.matrix[1][1] * doubletridiagonalmatrix.getElement(1, 0);
				ad[1][1] = super.matrix[0][1] * doubletridiagonalmatrix.getElement(0, 1) + super.matrix[1][1] * doubletridiagonalmatrix.getElement(1, 1) + super.matrix[2][1] * doubletridiagonalmatrix.getElement(2, 1);
				ad[1][2] = super.matrix[1][1] * doubletridiagonalmatrix.getElement(1, 2) + super.matrix[2][1] * doubletridiagonalmatrix.getElement(2, 2);
				ad[1][3] = super.matrix[2][1] * doubletridiagonalmatrix.getElement(2, 3);
			}
			if (i == 3) {
				ad[1][0] = super.matrix[0][1] * doubletridiagonalmatrix.getElement(0, 0) + super.matrix[1][1] * doubletridiagonalmatrix.getElement(1, 0);
				ad[1][1] = super.matrix[0][1] * doubletridiagonalmatrix.getElement(0, 1) + super.matrix[1][1] * doubletridiagonalmatrix.getElement(1, 1) + super.matrix[2][1] * doubletridiagonalmatrix.getElement(2, 1);
				ad[1][2] = super.matrix[1][1] * doubletridiagonalmatrix.getElement(1, 2) + super.matrix[2][1] * doubletridiagonalmatrix.getElement(2, 2);
			} else
			if (i > 4) {
				for (int j = 2; j < i - 2; j++) {
					ad[j][j - 2] = super.matrix[0][j] * doubletridiagonalmatrix.getElement(j - 1, j - 2);
					ad[j][j - 1] = super.matrix[0][j] * doubletridiagonalmatrix.getElement(j - 1, j - 1) + super.matrix[1][j] * doubletridiagonalmatrix.getElement(j, j - 1);
					ad[j][j] = super.matrix[0][j] * doubletridiagonalmatrix.getElement(j - 1, j) + super.matrix[1][j] * doubletridiagonalmatrix.getElement(j, j) + super.matrix[2][j] * doubletridiagonalmatrix.getElement(j + 1, j);
					ad[j][j + 1] = super.matrix[1][j] * doubletridiagonalmatrix.getElement(j, j + 1) + super.matrix[2][j] * doubletridiagonalmatrix.getElement(j + 1, j + 1);
					ad[j][j + 2] = super.matrix[2][j] * doubletridiagonalmatrix.getElement(j + 1, j + 2);
				}

			}
			if (i > 3) {
				ad[i - 2][i - 4] = super.matrix[0][i - 2] * doubletridiagonalmatrix.getElement(i - 3, i - 4);
				ad[i - 2][i - 3] = super.matrix[0][i - 2] * doubletridiagonalmatrix.getElement(i - 3, i - 3) + super.matrix[1][i - 2] * doubletridiagonalmatrix.getElement(i - 2, i - 3);
				ad[i - 2][i - 2] = super.matrix[0][i - 2] * doubletridiagonalmatrix.getElement(i - 3, i - 2) + super.matrix[1][i - 2] * doubletridiagonalmatrix.getElement(i - 2, i - 2) + super.matrix[2][i - 2] * doubletridiagonalmatrix.getElement(i - 1, i - 2);
				ad[i - 2][i - 1] = super.matrix[1][i - 2] * doubletridiagonalmatrix.getElement(i - 2, i - 1) + super.matrix[2][i - 2] * doubletridiagonalmatrix.getElement(i - 1, i - 1);
			}
			i--;
			ad[i][i - 2] = super.matrix[0][i] * doubletridiagonalmatrix.getElement(i - 1, i - 2);
			ad[i][i - 1] = super.matrix[0][i] * doubletridiagonalmatrix.getElement(i - 1, i - 1) + super.matrix[1][i] * doubletridiagonalmatrix.getElement(i, i - 1);
			ad[i][i] = super.matrix[0][i] * doubletridiagonalmatrix.getElement(i - 1, i) + super.matrix[1][i] * doubletridiagonalmatrix.getElement(i, i);
			return new DoubleSquareMatrix(ad);
		} else {
			throw new MatrixDimensionException("Incompatible matrices.");
		}
	}

	public Matrix transpose() {
		int i = super.matrix[1].length;
		DoubleTridiagonalMatrix doubletridiagonalmatrix = new DoubleTridiagonalMatrix(i);
		((DoubleMatrix) (doubletridiagonalmatrix)).matrix[1][0] = super.matrix[1][0];
		doubletridiagonalmatrix.setElement(1, 0, super.matrix[2][0]);
		i--;
		for (int j = 1; j < i; j++) {
			doubletridiagonalmatrix.setElement(j - 1, j, super.matrix[0][j]);
			((DoubleMatrix) (doubletridiagonalmatrix)).matrix[1][j] = super.matrix[1][j];
			doubletridiagonalmatrix.setElement(j + 1, j, super.matrix[2][j]);
		}

		doubletridiagonalmatrix.setElement(i - 1, i, super.matrix[0][i]);
		((DoubleMatrix) (doubletridiagonalmatrix)).matrix[1][i] = super.matrix[1][i];
		return doubletridiagonalmatrix;
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
			ad[0][i][0] = ad[1][0][i] = getElement(i, 0) / ad[0][0][0];

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

	public DoubleMatrix mapElements(Mapping mapping) {
		int i = super.matrix[1].length;
		DoubleTridiagonalMatrix doubletridiagonalmatrix = new DoubleTridiagonalMatrix(i);
		((DoubleMatrix) (doubletridiagonalmatrix)).matrix[1][0] = mapping.map(super.matrix[1][0]);
		((DoubleMatrix) (doubletridiagonalmatrix)).matrix[2][0] = mapping.map(super.matrix[2][0]);
		i--;
		for (int j = 1; j < i; j++) {
			((DoubleMatrix) (doubletridiagonalmatrix)).matrix[0][j] = mapping.map(super.matrix[0][j]);
			((DoubleMatrix) (doubletridiagonalmatrix)).matrix[1][j] = mapping.map(super.matrix[1][j]);
			((DoubleMatrix) (doubletridiagonalmatrix)).matrix[2][j] = mapping.map(super.matrix[2][j]);
		}

		((DoubleMatrix) (doubletridiagonalmatrix)).matrix[0][i] = mapping.map(super.matrix[0][i]);
		((DoubleMatrix) (doubletridiagonalmatrix)).matrix[1][i] = mapping.map(super.matrix[1][i]);
		return doubletridiagonalmatrix;
	}
}
