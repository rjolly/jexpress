package JSci.maths;

import JSci.GlobalSettings;

// Referenced classes of package JSci.maths:
//			DoubleTridiagonalMatrix, Complex, ComplexDiagonalMatrix, DimensionException, 
//			DoubleMatrix, DoubleSquareMatrix, DoubleVector, IntegerDiagonalMatrix, 
//			Mapping, MatrixDimensionException, IntegerMatrix, ComplexMatrix, 
//			Matrix

public final class DoubleDiagonalMatrix extends DoubleTridiagonalMatrix {

	protected DoubleDiagonalMatrix() {
	}

	public DoubleDiagonalMatrix(int i) {
		this();
		super.matrix = new double[1][i];
	}

	public DoubleDiagonalMatrix(double ad[][]) {
		this(ad.length);
		if (ad.length == ad[0].length) {
			for (int i = 0; i < super.matrix[0].length; i++)
				super.matrix[0][i] = ad[i][i];

			return;
		} else {
			super.matrix = null;
			throw new MatrixDimensionException("Array must be square.");
		}
	}

	public DoubleDiagonalMatrix(double ad[]) {
		this();
		super.matrix = new double[1][];
		super.matrix[0] = ad;
	}

	public static DoubleDiagonalMatrix identity(int i) {
		double ad[] = new double[i];
		for (int j = 0; j < i; j++)
			ad[j] = 1.0D;

		return new DoubleDiagonalMatrix(ad);
	}

	public boolean equals(Object obj) {
		if (obj != null && (obj instanceof DoubleDiagonalMatrix) && super.matrix[0].length == ((DoubleDiagonalMatrix)obj).rows()) {
			DoubleDiagonalMatrix doublediagonalmatrix = (DoubleDiagonalMatrix)obj;
			for (int i = 0; i < super.matrix[0].length; i++)
				if (Math.abs(super.matrix[0][i] - doublediagonalmatrix.getElement(i, i)) > GlobalSettings.ZERO_TOL)
					return false;

			return true;
		} else {
			return false;
		}
	}

	public IntegerMatrix toIntegerMatrix() {
		int ai[] = new int[super.matrix[0].length];
		for (int i = 0; i < super.matrix[0].length; i++)
			ai[i] = Math.round((float)super.matrix[0][i]);

		return new IntegerDiagonalMatrix(ai);
	}

	public ComplexMatrix toComplexMatrix() {
		Complex acomplex[] = new Complex[super.matrix[0].length];
		for (int i = 0; i < super.matrix[0].length; i++)
			acomplex[i] = new Complex(super.matrix[0][i], 0.0D);

		return new ComplexDiagonalMatrix(acomplex);
	}

	public double getElement(int i, int j) {
		if (i >= 0 && i < super.matrix[0].length && j >= 0 && j < super.matrix[0].length) {
			if (i == j)
				return super.matrix[0][i];
			else
				return 0.0D;
		} else {
			throw new MatrixDimensionException("Invalid element.");
		}
	}

	public void setElement(int i, int j, double d) {
		if (i >= 0 && i < super.matrix[0].length && j >= 0 && j < super.matrix[0].length && i == j) {
			super.matrix[0][i] = d;
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

	public double det() {
		double d = super.matrix[0][0];
		for (int i = 1; i < super.matrix[0].length; i++)
			d *= super.matrix[0][i];

		return d;
	}

	public double trace() {
		double d = super.matrix[0][0];
		for (int i = 1; i < super.matrix[0].length; i++)
			d += super.matrix[0][i];

		return d;
	}

	public double infNorm() {
		double d = Math.abs(super.matrix[0][0]);
		for (int i = 1; i < super.matrix[0].length; i++) {
			double d1 = Math.abs(super.matrix[0][i]);
			if (d1 > d)
				d = d1;
		}

		return d;
	}

	public double frobeniusNorm() {
		double d = super.matrix[0][0] * super.matrix[0][0];
		for (int i = 1; i < super.matrix[0].length; i++)
			d += super.matrix[0][i] * super.matrix[0][i];

		return Math.sqrt(d);
	}

	public int rows() {
		return super.matrix[0].length;
	}

	public int columns() {
		return super.matrix[0].length;
	}

	public Matrix add(Matrix matrix) {
		if (matrix instanceof DoubleDiagonalMatrix)
			return add((DoubleDiagonalMatrix)matrix);
		if (matrix instanceof DoubleTridiagonalMatrix)
			return rawAdd((DoubleTridiagonalMatrix)matrix);
		if (matrix instanceof DoubleSquareMatrix)
			return rawAdd((DoubleSquareMatrix)matrix);
		if (matrix instanceof DoubleMatrix)
			return rawAdd((DoubleMatrix)matrix);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public DoubleMatrix add(DoubleMatrix doublematrix) {
		if (doublematrix instanceof DoubleDiagonalMatrix)
			return add((DoubleDiagonalMatrix)doublematrix);
		if (doublematrix instanceof DoubleTridiagonalMatrix)
			return rawAdd((DoubleTridiagonalMatrix)doublematrix);
		if (doublematrix instanceof DoubleSquareMatrix)
			return rawAdd((DoubleSquareMatrix)doublematrix);
		else
			return rawAdd(doublematrix);
	}

	private DoubleMatrix rawAdd(DoubleMatrix doublematrix) {
		if (super.matrix[0].length == doublematrix.rows() && super.matrix[0].length == doublematrix.columns()) {
			double ad[][] = new double[super.matrix[0].length][super.matrix[0].length];
			for (int i = 0; i < ad.length; i++)
				System.arraycopy(doublematrix.matrix[i], 0, ad[i], 0, ad.length);

			for (int j = 0; j < ad.length; j++)
				ad[j][j] += super.matrix[0][j];

			return new DoubleSquareMatrix(ad);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public DoubleSquareMatrix add(DoubleSquareMatrix doublesquarematrix) {
		if (doublesquarematrix instanceof DoubleDiagonalMatrix)
			return add((DoubleDiagonalMatrix)doublesquarematrix);
		if (doublesquarematrix instanceof DoubleTridiagonalMatrix)
			return rawAdd((DoubleTridiagonalMatrix)doublesquarematrix);
		else
			return rawAdd(doublesquarematrix);
	}

	private DoubleSquareMatrix rawAdd(DoubleSquareMatrix doublesquarematrix) {
		if (super.matrix[0].length == doublesquarematrix.rows()) {
			double ad[][] = new double[super.matrix[0].length][super.matrix[0].length];
			for (int i = 0; i < ad.length; i++)
				System.arraycopy(((DoubleMatrix) (doublesquarematrix)).matrix[i], 0, ad[i], 0, ad.length);

			for (int j = 0; j < ad.length; j++)
				ad[j][j] += super.matrix[0][j];

			return new DoubleSquareMatrix(ad);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public DoubleTridiagonalMatrix add(DoubleTridiagonalMatrix doubletridiagonalmatrix) {
		if (doubletridiagonalmatrix instanceof DoubleDiagonalMatrix)
			return add((DoubleDiagonalMatrix)doubletridiagonalmatrix);
		else
			return rawAdd(doubletridiagonalmatrix);
	}

	private DoubleTridiagonalMatrix rawAdd(DoubleTridiagonalMatrix doubletridiagonalmatrix) {
		if (super.matrix[0].length == doubletridiagonalmatrix.rows()) {
			DoubleTridiagonalMatrix doubletridiagonalmatrix1 = new DoubleTridiagonalMatrix(super.matrix[0].length);
			System.arraycopy(((DoubleMatrix) (doubletridiagonalmatrix)).matrix[0], 0, ((DoubleMatrix) (doubletridiagonalmatrix1)).matrix[0], 0, super.matrix[0].length);
			System.arraycopy(((DoubleMatrix) (doubletridiagonalmatrix)).matrix[2], 0, ((DoubleMatrix) (doubletridiagonalmatrix1)).matrix[2], 0, super.matrix[2].length);
			((DoubleMatrix) (doubletridiagonalmatrix1)).matrix[1][0] = super.matrix[0][0] + ((DoubleMatrix) (doubletridiagonalmatrix)).matrix[1][0];
			for (int i = 1; i < super.matrix[0].length; i++)
				((DoubleMatrix) (doubletridiagonalmatrix1)).matrix[1][i] = super.matrix[0][i] + ((DoubleMatrix) (doubletridiagonalmatrix)).matrix[1][i];

			return doubletridiagonalmatrix1;
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public DoubleDiagonalMatrix add(DoubleDiagonalMatrix doublediagonalmatrix) {
		if (super.matrix[0].length == doublediagonalmatrix.rows()) {
			double ad[] = new double[super.matrix[0].length];
			ad[0] = super.matrix[0][0] + ((DoubleMatrix) (doublediagonalmatrix)).matrix[0][0];
			for (int i = 1; i < ad.length; i++)
				ad[i] = super.matrix[0][i] + ((DoubleMatrix) (doublediagonalmatrix)).matrix[0][i];

			return new DoubleDiagonalMatrix(ad);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public Matrix subtract(Matrix matrix) {
		if (matrix instanceof DoubleDiagonalMatrix)
			return subtract((DoubleDiagonalMatrix)matrix);
		if (matrix instanceof DoubleTridiagonalMatrix)
			return rawSubtract((DoubleTridiagonalMatrix)matrix);
		if (matrix instanceof DoubleSquareMatrix)
			return rawSubtract((DoubleSquareMatrix)matrix);
		if (matrix instanceof DoubleMatrix)
			return rawSubtract((DoubleMatrix)matrix);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public DoubleMatrix subtract(DoubleMatrix doublematrix) {
		if (doublematrix instanceof DoubleDiagonalMatrix)
			return subtract((DoubleDiagonalMatrix)doublematrix);
		if (doublematrix instanceof DoubleTridiagonalMatrix)
			return rawSubtract((DoubleTridiagonalMatrix)doublematrix);
		if (doublematrix instanceof DoubleSquareMatrix)
			return rawSubtract((DoubleSquareMatrix)doublematrix);
		else
			return rawSubtract(doublematrix);
	}

	private DoubleMatrix rawSubtract(DoubleMatrix doublematrix) {
		if (super.matrix[0].length == doublematrix.rows() && super.matrix[0].length == doublematrix.columns()) {
			double ad[][] = new double[super.matrix[0].length][super.matrix[0].length];
			for (int j = 0; j < ad.length; j++) {
				ad[j][0] = -doublematrix.matrix[j][0];
				for (int i = 1; i < ad.length; i++)
					ad[j][i] = -doublematrix.matrix[j][i];

			}

			for (int k = 0; k < ad.length; k++)
				ad[k][k] += super.matrix[0][k];

			return new DoubleSquareMatrix(ad);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public DoubleSquareMatrix subtract(DoubleSquareMatrix doublesquarematrix) {
		if (doublesquarematrix instanceof DoubleDiagonalMatrix)
			return subtract((DoubleDiagonalMatrix)doublesquarematrix);
		if (doublesquarematrix instanceof DoubleTridiagonalMatrix)
			return rawSubtract((DoubleTridiagonalMatrix)doublesquarematrix);
		else
			return rawSubtract(doublesquarematrix);
	}

	private DoubleSquareMatrix rawSubtract(DoubleSquareMatrix doublesquarematrix) {
		if (super.matrix[0].length == doublesquarematrix.rows()) {
			double ad[][] = new double[super.matrix[0].length][super.matrix[0].length];
			for (int j = 0; j < ad.length; j++) {
				ad[j][0] = -((DoubleMatrix) (doublesquarematrix)).matrix[j][0];
				for (int i = 1; i < ad.length; i++)
					ad[j][i] = -((DoubleMatrix) (doublesquarematrix)).matrix[j][i];

			}

			for (int k = 0; k < ad.length; k++)
				ad[k][k] += super.matrix[0][k];

			return new DoubleSquareMatrix(ad);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public DoubleTridiagonalMatrix subtract(DoubleTridiagonalMatrix doubletridiagonalmatrix) {
		if (doubletridiagonalmatrix instanceof DoubleDiagonalMatrix)
			return subtract((DoubleDiagonalMatrix)doubletridiagonalmatrix);
		else
			return rawSubtract(doubletridiagonalmatrix);
	}

	private DoubleTridiagonalMatrix rawSubtract(DoubleTridiagonalMatrix doubletridiagonalmatrix) {
		if (super.matrix[0].length == doubletridiagonalmatrix.rows()) {
			int i = super.matrix[0].length;
			DoubleTridiagonalMatrix doubletridiagonalmatrix1 = new DoubleTridiagonalMatrix(i);
			doubletridiagonalmatrix1.setElement(0, 0, super.matrix[0][0] - doubletridiagonalmatrix.getElement(0, 0));
			doubletridiagonalmatrix1.setElement(1, 0, -doubletridiagonalmatrix.getElement(1, 0));
			i--;
			for (int j = 1; j < i; j++) {
				doubletridiagonalmatrix1.setElement(j - 1, j, -doubletridiagonalmatrix.getElement(j - 1, j));
				doubletridiagonalmatrix1.setElement(j, j, super.matrix[0][j] - doubletridiagonalmatrix.getElement(j, j));
				doubletridiagonalmatrix1.setElement(j + 1, j, -doubletridiagonalmatrix.getElement(j + 1, j));
			}

			doubletridiagonalmatrix1.setElement(i - 1, i, -doubletridiagonalmatrix.getElement(i - 1, i));
			doubletridiagonalmatrix1.setElement(i, i, super.matrix[0][i] - doubletridiagonalmatrix.getElement(i, i));
			return doubletridiagonalmatrix1;
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public DoubleDiagonalMatrix subtract(DoubleDiagonalMatrix doublediagonalmatrix) {
		if (super.matrix[0].length == doublediagonalmatrix.rows()) {
			double ad[] = new double[super.matrix[0].length];
			ad[0] = super.matrix[0][0] - ((DoubleMatrix) (doublediagonalmatrix)).matrix[0][0];
			for (int i = 1; i < ad.length; i++)
				ad[i] = super.matrix[0][i] - ((DoubleMatrix) (doublediagonalmatrix)).matrix[0][i];

			return new DoubleDiagonalMatrix(ad);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public DoubleMatrix scalarMultiply(double d) {
		double ad[] = new double[super.matrix[0].length];
		ad[0] = d * super.matrix[0][0];
		for (int i = 1; i < ad.length; i++)
			ad[i] = d * super.matrix[0][i];

		return new DoubleDiagonalMatrix(ad);
	}

	public DoubleVector multiply(DoubleVector doublevector) {
		if (super.matrix[0].length == doublevector.dimension()) {
			double ad[] = new double[super.matrix[0].length];
			ad[0] = super.matrix[0][0] * doublevector.getComponent(0);
			for (int i = 1; i < ad.length; i++)
				ad[i] = super.matrix[0][i] * doublevector.getComponent(i);

			return new DoubleVector(ad);
		} else {
			throw new DimensionException("Matrix and vector are incompatible.");
		}
	}

	public Matrix multiply(Matrix matrix) {
		if (matrix instanceof DoubleDiagonalMatrix)
			return multiply((DoubleDiagonalMatrix)matrix);
		if (matrix instanceof DoubleTridiagonalMatrix)
			return rawMultiply((DoubleTridiagonalMatrix)matrix);
		if (matrix instanceof DoubleSquareMatrix)
			return rawMultiply((DoubleSquareMatrix)matrix);
		if (matrix instanceof DoubleMatrix)
			return rawMultiply((DoubleMatrix)matrix);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public DoubleMatrix multiply(DoubleMatrix doublematrix) {
		if (doublematrix instanceof DoubleDiagonalMatrix)
			return multiply((DoubleDiagonalMatrix)doublematrix);
		if (doublematrix instanceof DoubleTridiagonalMatrix)
			return rawMultiply((DoubleTridiagonalMatrix)doublematrix);
		if (doublematrix instanceof DoubleSquareMatrix)
			return rawMultiply((DoubleSquareMatrix)doublematrix);
		else
			return rawMultiply(doublematrix);
	}

	private DoubleMatrix rawMultiply(DoubleMatrix doublematrix) {
		if (super.matrix[0].length == doublematrix.rows()) {
			double ad[][] = new double[super.matrix[0].length][doublematrix.columns()];
			for (int j = 0; j < ad.length; j++) {
				ad[j][0] = super.matrix[0][j] * doublematrix.getElement(j, 0);
				for (int i = 1; i < ad[0].length; i++)
					ad[j][i] = super.matrix[0][j] * doublematrix.getElement(j, i);

			}

			return new DoubleMatrix(ad);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public DoubleSquareMatrix multiply(DoubleSquareMatrix doublesquarematrix) {
		if (doublesquarematrix instanceof DoubleDiagonalMatrix)
			return multiply((DoubleDiagonalMatrix)doublesquarematrix);
		if (doublesquarematrix instanceof DoubleTridiagonalMatrix)
			return rawMultiply((DoubleTridiagonalMatrix)doublesquarematrix);
		else
			return rawMultiply(doublesquarematrix);
	}

	private DoubleSquareMatrix rawMultiply(DoubleSquareMatrix doublesquarematrix) {
		if (super.matrix[0].length == doublesquarematrix.rows()) {
			double ad[][] = new double[super.matrix[0].length][super.matrix[0].length];
			for (int j = 0; j < ad.length; j++) {
				ad[j][0] = super.matrix[0][j] * doublesquarematrix.getElement(j, 0);
				for (int i = 1; i < ad.length; i++)
					ad[j][i] = super.matrix[0][j] * doublesquarematrix.getElement(j, i);

			}

			return new DoubleSquareMatrix(ad);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public DoubleSquareMatrix multiply(DoubleTridiagonalMatrix doubletridiagonalmatrix) {
		if (doubletridiagonalmatrix instanceof DoubleDiagonalMatrix)
			return multiply((DoubleDiagonalMatrix)doubletridiagonalmatrix);
		else
			return rawMultiply(doubletridiagonalmatrix);
	}

	private DoubleSquareMatrix rawMultiply(DoubleTridiagonalMatrix doubletridiagonalmatrix) {
		int i = super.matrix[0].length;
		if (i == doubletridiagonalmatrix.rows()) {
			DoubleTridiagonalMatrix doubletridiagonalmatrix1 = new DoubleTridiagonalMatrix(i);
			doubletridiagonalmatrix1.setElement(0, 0, super.matrix[0][0] * doubletridiagonalmatrix.getElement(0, 0));
			doubletridiagonalmatrix1.setElement(0, 1, super.matrix[0][0] * doubletridiagonalmatrix.getElement(0, 1));
			i--;
			for (int j = 1; j < i; j++) {
				doubletridiagonalmatrix1.setElement(j, j - 1, super.matrix[0][j] * doubletridiagonalmatrix.getElement(j, j - 1));
				doubletridiagonalmatrix1.setElement(j, j, super.matrix[0][j] * doubletridiagonalmatrix.getElement(j, j));
				doubletridiagonalmatrix1.setElement(j, j + 1, super.matrix[0][j] * doubletridiagonalmatrix.getElement(j, j + 1));
			}

			doubletridiagonalmatrix1.setElement(i, i - 1, super.matrix[0][i] * doubletridiagonalmatrix.getElement(i, i - 1));
			doubletridiagonalmatrix1.setElement(i, i, super.matrix[0][i] * doubletridiagonalmatrix.getElement(i, i));
			return doubletridiagonalmatrix1;
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public DoubleDiagonalMatrix multiply(DoubleDiagonalMatrix doublediagonalmatrix) {
		if (super.matrix[0].length == doublediagonalmatrix.rows()) {
			double ad[] = new double[super.matrix[0].length];
			ad[0] = super.matrix[0][0] * doublediagonalmatrix.getElement(0, 0);
			for (int i = 1; i < ad.length; i++)
				ad[i] = super.matrix[0][i] * doublediagonalmatrix.getElement(i, i);

			return new DoubleDiagonalMatrix(ad);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public DoubleSquareMatrix inverse() {
		double ad[] = new double[super.matrix[0].length];
		ad[0] = 1.0D / super.matrix[0][0];
		for (int i = 1; i < ad.length; i++)
			ad[i] = 1.0D / super.matrix[0][i];

		return new DoubleDiagonalMatrix(ad);
	}

	public Matrix transpose() {
		return this;
	}

	public DoubleSquareMatrix[] luDecompose() {
		DoubleDiagonalMatrix adoublediagonalmatrix[] = new DoubleDiagonalMatrix[2];
		adoublediagonalmatrix[0] = identity(super.matrix[0].length);
		adoublediagonalmatrix[1] = this;
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

	public DoubleMatrix mapElements(Mapping mapping) {
		double ad[] = new double[super.matrix[0].length];
		ad[0] = mapping.map(super.matrix[0][0]);
		for (int i = 1; i < ad.length; i++)
			ad[i] = mapping.map(super.matrix[0][i]);

		return new DoubleDiagonalMatrix(ad);
	}
}
