package JSci.maths;


// Referenced classes of package JSci.maths:
//			ComplexTridiagonalMatrix, Complex, ComplexMatrix, ComplexSquareMatrix, 
//			ComplexVector, DimensionException, DoubleDiagonalMatrix, Mapping, 
//			MatrixDimensionException, DoubleMatrix, Matrix

public final class ComplexDiagonalMatrix extends ComplexTridiagonalMatrix {

	protected ComplexDiagonalMatrix() {
	}

	public ComplexDiagonalMatrix(int i) {
		this();
		super.matrix = new Complex[1][i];
	}

	public ComplexDiagonalMatrix(Complex acomplex[][]) {
		this(acomplex.length);
		if (acomplex.length == acomplex[0].length) {
			for (int i = 0; i < super.matrix[0].length; i++)
				super.matrix[0][i] = acomplex[i][i];

			return;
		} else {
			super.matrix = null;
			throw new MatrixDimensionException("Array must be square.");
		}
	}

	public ComplexDiagonalMatrix(Complex acomplex[]) {
		this();
		super.matrix = new Complex[1][];
		super.matrix[0] = acomplex;
	}

	public static ComplexDiagonalMatrix identity(int i) {
		Complex acomplex[] = new Complex[i];
		for (int j = 0; j < i; j++)
			acomplex[j] = Complex.ONE;

		return new ComplexDiagonalMatrix(acomplex);
	}

	public boolean equals(Object obj) {
		if (obj != null && (obj instanceof ComplexDiagonalMatrix) && super.matrix[0].length == ((ComplexDiagonalMatrix)obj).rows()) {
			ComplexDiagonalMatrix complexdiagonalmatrix = (ComplexDiagonalMatrix)obj;
			for (int i = 0; i < super.matrix[0].length; i++)
				if (!super.matrix[0][i].equals(complexdiagonalmatrix.getElement(i, i)))
					return false;

			return true;
		} else {
			return false;
		}
	}

	public DoubleMatrix real() {
		double ad[] = new double[super.matrix[0].length];
		for (int i = 0; i < super.matrix[0].length; i++)
			ad[i] = super.matrix[0][i].real();

		return new DoubleDiagonalMatrix(ad);
	}

	public DoubleMatrix imag() {
		double ad[] = new double[super.matrix[0].length];
		for (int i = 0; i < super.matrix[0].length; i++)
			ad[i] = super.matrix[0][i].imag();

		return new DoubleDiagonalMatrix(ad);
	}

	public Complex getElement(int i, int j) {
		if (i >= 0 && i < super.matrix[0].length && j >= 0 && j < super.matrix[0].length) {
			if (i == j)
				return super.matrix[0][i];
			else
				return Complex.ZERO;
		} else {
			throw new MatrixDimensionException("Invalid element.");
		}
	}

	public void setElement(int i, int j, Complex complex) {
		if (i >= 0 && i < super.matrix[0].length && j >= 0 && j < super.matrix[0].length && i == j) {
			super.matrix[0][i] = complex;
			return;
		} else {
			throw new MatrixDimensionException("Invalid element.");
		}
	}

	public boolean isHermitian() {
		return equals(conjugate());
	}

	public boolean isUnitary() {
		return multiply(conjugate()).equals(identity(super.matrix[0].length));
	}

	public Complex det() {
		Complex complex = super.matrix[0][0];
		for (int i = 1; i < super.matrix[0].length; i++)
			complex = complex.multiply(super.matrix[0][i]);

		return complex;
	}

	public Complex trace() {
		Complex complex = super.matrix[0][0];
		for (int i = 1; i < super.matrix[0].length; i++)
			complex = complex.add(super.matrix[0][i]);

		return complex;
	}

	public double infNorm() {
		double d = super.matrix[0][0].mod();
		for (int i = 1; i < super.matrix[0].length; i++) {
			double d1 = super.matrix[0][i].mod();
			if (d1 > d)
				d = d1;
		}

		return d;
	}

	public double frobeniusNorm() {
		double d = super.matrix[0][0].mod() * super.matrix[0][0].mod();
		for (int i = 1; i < super.matrix[0].length; i++)
			d += super.matrix[0][i].mod() * super.matrix[0][i].mod();

		return Math.sqrt(d);
	}

	public int rows() {
		return super.matrix[0].length;
	}

	public int columns() {
		return super.matrix[0].length;
	}

	public Matrix add(Matrix matrix) {
		if (matrix instanceof ComplexDiagonalMatrix)
			return add((ComplexDiagonalMatrix)matrix);
		if (matrix instanceof ComplexTridiagonalMatrix)
			return rawAdd((ComplexTridiagonalMatrix)matrix);
		if (matrix instanceof ComplexSquareMatrix)
			return rawAdd((ComplexSquareMatrix)matrix);
		if (matrix instanceof ComplexMatrix)
			return rawAdd((ComplexMatrix)matrix);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public ComplexMatrix add(ComplexMatrix complexmatrix) {
		if (complexmatrix instanceof ComplexDiagonalMatrix)
			return add((ComplexDiagonalMatrix)complexmatrix);
		if (complexmatrix instanceof ComplexTridiagonalMatrix)
			return rawAdd((ComplexTridiagonalMatrix)complexmatrix);
		if (complexmatrix instanceof ComplexSquareMatrix)
			return rawAdd((ComplexSquareMatrix)complexmatrix);
		else
			return rawAdd(complexmatrix);
	}

	private ComplexMatrix rawAdd(ComplexMatrix complexmatrix) {
		if (super.matrix[0].length == complexmatrix.rows() && super.matrix[0].length == complexmatrix.columns()) {
			Complex acomplex[][] = new Complex[super.matrix[0].length][super.matrix[0].length];
			for (int i = 0; i < acomplex.length; i++)
				System.arraycopy(complexmatrix.matrix[i], 0, acomplex[i], 0, acomplex.length);

			for (int j = 0; j < acomplex.length; j++)
				acomplex[j][j] = acomplex[j][j].add(super.matrix[0][j]);

			return new ComplexSquareMatrix(acomplex);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public ComplexSquareMatrix add(ComplexSquareMatrix complexsquarematrix) {
		if (complexsquarematrix instanceof ComplexDiagonalMatrix)
			return add((ComplexDiagonalMatrix)complexsquarematrix);
		if (complexsquarematrix instanceof ComplexTridiagonalMatrix)
			return rawAdd((ComplexTridiagonalMatrix)complexsquarematrix);
		else
			return rawAdd(complexsquarematrix);
	}

	private ComplexSquareMatrix rawAdd(ComplexSquareMatrix complexsquarematrix) {
		if (super.matrix[0].length == complexsquarematrix.rows()) {
			Complex acomplex[][] = new Complex[super.matrix[0].length][super.matrix[0].length];
			for (int i = 0; i < acomplex.length; i++)
				System.arraycopy(((ComplexMatrix) (complexsquarematrix)).matrix[i], 0, acomplex[i], 0, acomplex.length);

			for (int j = 0; j < acomplex.length; j++)
				acomplex[j][j] = acomplex[j][j].add(super.matrix[0][j]);

			return new ComplexSquareMatrix(acomplex);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public ComplexTridiagonalMatrix add(ComplexTridiagonalMatrix complextridiagonalmatrix) {
		if (complextridiagonalmatrix instanceof ComplexDiagonalMatrix)
			return add((ComplexDiagonalMatrix)complextridiagonalmatrix);
		else
			return rawAdd(complextridiagonalmatrix);
	}

	private ComplexTridiagonalMatrix rawAdd(ComplexTridiagonalMatrix complextridiagonalmatrix) {
		if (super.matrix[0].length == complextridiagonalmatrix.rows()) {
			ComplexTridiagonalMatrix complextridiagonalmatrix1 = new ComplexTridiagonalMatrix(super.matrix[0].length);
			System.arraycopy(((ComplexMatrix) (complextridiagonalmatrix)).matrix[0], 0, ((ComplexMatrix) (complextridiagonalmatrix1)).matrix[0], 0, super.matrix[0].length);
			System.arraycopy(((ComplexMatrix) (complextridiagonalmatrix)).matrix[2], 0, ((ComplexMatrix) (complextridiagonalmatrix1)).matrix[2], 0, super.matrix[2].length);
			((ComplexMatrix) (complextridiagonalmatrix1)).matrix[1][0] = super.matrix[0][0].add(((ComplexMatrix) (complextridiagonalmatrix)).matrix[1][0]);
			for (int i = 1; i < super.matrix[0].length; i++)
				((ComplexMatrix) (complextridiagonalmatrix1)).matrix[1][i] = super.matrix[0][i].add(((ComplexMatrix) (complextridiagonalmatrix)).matrix[1][i]);

			return complextridiagonalmatrix1;
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public ComplexDiagonalMatrix add(ComplexDiagonalMatrix complexdiagonalmatrix) {
		if (super.matrix[0].length == complexdiagonalmatrix.rows()) {
			Complex acomplex[] = new Complex[super.matrix[0].length];
			acomplex[0] = super.matrix[0][0].add(complexdiagonalmatrix.getElement(0, 0));
			for (int i = 1; i < acomplex.length; i++)
				acomplex[i] = super.matrix[0][i].add(complexdiagonalmatrix.getElement(i, i));

			return new ComplexDiagonalMatrix(acomplex);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public Matrix subtract(Matrix matrix) {
		if (matrix instanceof ComplexDiagonalMatrix)
			return subtract((ComplexDiagonalMatrix)matrix);
		if (matrix instanceof ComplexTridiagonalMatrix)
			return rawSubtract((ComplexTridiagonalMatrix)matrix);
		if (matrix instanceof ComplexSquareMatrix)
			return rawSubtract((ComplexSquareMatrix)matrix);
		if (matrix instanceof ComplexMatrix)
			return rawSubtract((ComplexMatrix)matrix);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public ComplexMatrix subtract(ComplexMatrix complexmatrix) {
		if (complexmatrix instanceof ComplexDiagonalMatrix)
			return subtract((ComplexDiagonalMatrix)complexmatrix);
		if (complexmatrix instanceof ComplexTridiagonalMatrix)
			return rawSubtract((ComplexTridiagonalMatrix)complexmatrix);
		if (complexmatrix instanceof ComplexSquareMatrix)
			return rawSubtract((ComplexSquareMatrix)complexmatrix);
		else
			return rawSubtract(complexmatrix);
	}

	private ComplexMatrix rawSubtract(ComplexMatrix complexmatrix) {
		if (super.matrix[0].length == complexmatrix.rows() && super.matrix[0].length == complexmatrix.columns()) {
			Complex acomplex[][] = new Complex[super.matrix[0].length][super.matrix[0].length];
			for (int j = 0; j < acomplex.length; j++) {
				acomplex[j][0] = complexmatrix.matrix[j][0].negate();
				for (int i = 1; i < acomplex.length; i++)
					acomplex[j][i] = complexmatrix.matrix[j][i].negate();

			}

			for (int k = 0; k < acomplex.length; k++)
				acomplex[k][k] = acomplex[k][k].add(super.matrix[0][k]);

			return new ComplexSquareMatrix(acomplex);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public ComplexSquareMatrix subtract(ComplexSquareMatrix complexsquarematrix) {
		if (complexsquarematrix instanceof ComplexDiagonalMatrix)
			return subtract((ComplexDiagonalMatrix)complexsquarematrix);
		if (complexsquarematrix instanceof ComplexTridiagonalMatrix)
			return rawSubtract((ComplexTridiagonalMatrix)complexsquarematrix);
		else
			return rawSubtract(complexsquarematrix);
	}

	private ComplexSquareMatrix rawSubtract(ComplexSquareMatrix complexsquarematrix) {
		if (super.matrix[0].length == complexsquarematrix.rows()) {
			Complex acomplex[][] = new Complex[super.matrix[0].length][super.matrix[0].length];
			for (int j = 0; j < acomplex.length; j++) {
				acomplex[j][0] = ((ComplexMatrix) (complexsquarematrix)).matrix[j][0].negate();
				for (int i = 1; i < acomplex.length; i++)
					acomplex[j][i] = ((ComplexMatrix) (complexsquarematrix)).matrix[j][i].negate();

			}

			for (int k = 0; k < acomplex.length; k++)
				acomplex[k][k] = acomplex[k][k].add(super.matrix[0][k]);

			return new ComplexSquareMatrix(acomplex);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public ComplexTridiagonalMatrix subtract(ComplexTridiagonalMatrix complextridiagonalmatrix) {
		if (complextridiagonalmatrix instanceof ComplexDiagonalMatrix)
			return subtract((ComplexDiagonalMatrix)complextridiagonalmatrix);
		else
			return rawSubtract(complextridiagonalmatrix);
	}

	private ComplexTridiagonalMatrix rawSubtract(ComplexTridiagonalMatrix complextridiagonalmatrix) {
		if (super.matrix[0].length == complextridiagonalmatrix.rows()) {
			int i = super.matrix[0].length;
			ComplexTridiagonalMatrix complextridiagonalmatrix1 = new ComplexTridiagonalMatrix(i);
			complextridiagonalmatrix1.setElement(0, 0, super.matrix[0][0].subtract(complextridiagonalmatrix.getElement(0, 0)));
			complextridiagonalmatrix1.setElement(1, 0, complextridiagonalmatrix.getElement(1, 0).negate());
			i--;
			for (int j = 1; j < i; j++) {
				complextridiagonalmatrix1.setElement(j - 1, j, complextridiagonalmatrix.getElement(j - 1, j).negate());
				complextridiagonalmatrix1.setElement(j, j, super.matrix[0][j].subtract(complextridiagonalmatrix.getElement(j, j)));
				complextridiagonalmatrix1.setElement(j + 1, j, complextridiagonalmatrix.getElement(j + 1, j).negate());
			}

			complextridiagonalmatrix1.setElement(i - 1, i, complextridiagonalmatrix.getElement(i - 1, i).negate());
			complextridiagonalmatrix1.setElement(i, i, super.matrix[0][i].subtract(complextridiagonalmatrix.getElement(i, i)));
			return complextridiagonalmatrix1;
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public ComplexDiagonalMatrix subtract(ComplexDiagonalMatrix complexdiagonalmatrix) {
		if (super.matrix[0].length == complexdiagonalmatrix.rows()) {
			Complex acomplex[] = new Complex[super.matrix[0].length];
			acomplex[0] = super.matrix[0][0].subtract(((ComplexMatrix) (complexdiagonalmatrix)).matrix[0][0]);
			for (int i = 1; i < acomplex.length; i++)
				acomplex[i] = super.matrix[0][i].subtract(((ComplexMatrix) (complexdiagonalmatrix)).matrix[0][i]);

			return new ComplexDiagonalMatrix(acomplex);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public ComplexMatrix scalarMultiply(Complex complex) {
		Complex acomplex[] = new Complex[super.matrix[0].length];
		acomplex[0] = complex.multiply(super.matrix[0][0]);
		for (int i = 1; i < acomplex.length; i++)
			acomplex[i] = complex.multiply(super.matrix[0][i]);

		return new ComplexDiagonalMatrix(acomplex);
	}

	public ComplexMatrix scalarMultiply(double d) {
		Complex acomplex[] = new Complex[super.matrix[0].length];
		acomplex[0] = super.matrix[0][0].multiply(d);
		for (int i = 1; i < acomplex.length; i++)
			acomplex[i] = super.matrix[0][i].multiply(d);

		return new ComplexDiagonalMatrix(acomplex);
	}

	public ComplexVector multiply(ComplexVector complexvector) {
		if (super.matrix[0].length == complexvector.dimension()) {
			Complex acomplex[] = new Complex[super.matrix[0].length];
			acomplex[0] = super.matrix[0][0].multiply(complexvector.getComponent(0));
			for (int i = 1; i < acomplex.length; i++)
				acomplex[i] = super.matrix[0][i].multiply(complexvector.getComponent(i));

			return new ComplexVector(acomplex);
		} else {
			throw new DimensionException("Matrix and vector are incompatible.");
		}
	}

	public Matrix multiply(Matrix matrix) {
		if (matrix instanceof ComplexDiagonalMatrix)
			return multiply((ComplexDiagonalMatrix)matrix);
		if (matrix instanceof ComplexTridiagonalMatrix)
			return rawMultiply((ComplexTridiagonalMatrix)matrix);
		if (matrix instanceof ComplexSquareMatrix)
			return rawMultiply((ComplexSquareMatrix)matrix);
		if (matrix instanceof ComplexMatrix)
			return rawMultiply((ComplexMatrix)matrix);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public ComplexMatrix multiply(ComplexMatrix complexmatrix) {
		if (complexmatrix instanceof ComplexDiagonalMatrix)
			return multiply((ComplexDiagonalMatrix)complexmatrix);
		if (complexmatrix instanceof ComplexTridiagonalMatrix)
			return rawMultiply((ComplexTridiagonalMatrix)complexmatrix);
		if (complexmatrix instanceof ComplexSquareMatrix)
			return rawMultiply((ComplexSquareMatrix)complexmatrix);
		else
			return rawMultiply(complexmatrix);
	}

	private ComplexMatrix rawMultiply(ComplexMatrix complexmatrix) {
		if (super.matrix[0].length == complexmatrix.rows()) {
			Complex acomplex[][] = new Complex[super.matrix[0].length][complexmatrix.columns()];
			for (int j = 0; j < acomplex.length; j++) {
				acomplex[j][0] = super.matrix[0][j].multiply(complexmatrix.getElement(j, 0));
				for (int i = 1; i < acomplex[0].length; i++)
					acomplex[j][i] = super.matrix[0][j].multiply(complexmatrix.getElement(j, i));

			}

			return new ComplexMatrix(acomplex);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public ComplexSquareMatrix multiply(ComplexSquareMatrix complexsquarematrix) {
		if (complexsquarematrix instanceof ComplexDiagonalMatrix)
			return multiply((ComplexDiagonalMatrix)complexsquarematrix);
		if (complexsquarematrix instanceof ComplexTridiagonalMatrix)
			return rawMultiply((ComplexTridiagonalMatrix)complexsquarematrix);
		else
			return rawMultiply(complexsquarematrix);
	}

	private ComplexSquareMatrix rawMultiply(ComplexSquareMatrix complexsquarematrix) {
		if (super.matrix[0].length == complexsquarematrix.rows()) {
			Complex acomplex[][] = new Complex[super.matrix[0].length][super.matrix[0].length];
			for (int j = 0; j < acomplex.length; j++) {
				acomplex[j][0] = super.matrix[0][j].multiply(complexsquarematrix.getElement(j, 0));
				for (int i = 1; i < acomplex.length; i++)
					acomplex[j][i] = super.matrix[0][j].multiply(complexsquarematrix.getElement(j, i));

			}

			return new ComplexSquareMatrix(acomplex);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public ComplexSquareMatrix multiply(ComplexTridiagonalMatrix complextridiagonalmatrix) {
		if (complextridiagonalmatrix instanceof ComplexDiagonalMatrix)
			return multiply((ComplexDiagonalMatrix)complextridiagonalmatrix);
		else
			return rawMultiply(complextridiagonalmatrix);
	}

	private ComplexSquareMatrix rawMultiply(ComplexTridiagonalMatrix complextridiagonalmatrix) {
		int i = super.matrix[0].length;
		if (i == complextridiagonalmatrix.rows()) {
			ComplexTridiagonalMatrix complextridiagonalmatrix1 = new ComplexTridiagonalMatrix(i);
			complextridiagonalmatrix1.setElement(0, 0, super.matrix[0][0].multiply(complextridiagonalmatrix.getElement(0, 0)));
			complextridiagonalmatrix1.setElement(0, 1, super.matrix[0][0].multiply(complextridiagonalmatrix.getElement(0, 1)));
			i--;
			for (int j = 1; j < i; j++) {
				complextridiagonalmatrix1.setElement(j, j - 1, super.matrix[0][j].multiply(complextridiagonalmatrix.getElement(j, j - 1)));
				complextridiagonalmatrix1.setElement(j, j, super.matrix[0][j].multiply(complextridiagonalmatrix.getElement(j, j)));
				complextridiagonalmatrix1.setElement(j, j + 1, super.matrix[0][j].multiply(complextridiagonalmatrix.getElement(j, j + 1)));
			}

			complextridiagonalmatrix1.setElement(i, i - 1, super.matrix[0][i].multiply(complextridiagonalmatrix.getElement(i, i - 1)));
			complextridiagonalmatrix1.setElement(i, i, super.matrix[0][i].multiply(complextridiagonalmatrix.getElement(i, i)));
			return complextridiagonalmatrix1;
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public ComplexDiagonalMatrix multiply(ComplexDiagonalMatrix complexdiagonalmatrix) {
		if (super.matrix[0].length == complexdiagonalmatrix.rows()) {
			Complex acomplex[] = new Complex[super.matrix[0].length];
			acomplex[0] = super.matrix[0][0].multiply(complexdiagonalmatrix.getElement(0, 0));
			for (int i = 1; i < acomplex.length; i++)
				acomplex[i] = super.matrix[0][i].multiply(complexdiagonalmatrix.getElement(i, i));

			return new ComplexDiagonalMatrix(acomplex);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public ComplexSquareMatrix inverse() {
		Complex acomplex[] = new Complex[super.matrix[0].length];
		acomplex[0] = Complex.ONE.divide(super.matrix[0][0]);
		for (int i = 1; i < acomplex.length; i++)
			acomplex[i] = Complex.ONE.divide(super.matrix[0][i]);

		return new ComplexDiagonalMatrix(acomplex);
	}

	public ComplexMatrix conjugate() {
		Complex acomplex[] = new Complex[super.matrix[0].length];
		acomplex[0] = super.matrix[0][0].conjugate();
		for (int i = 1; i < acomplex.length; i++)
			acomplex[i] = super.matrix[0][i].conjugate();

		return new ComplexDiagonalMatrix(acomplex);
	}

	public Matrix transpose() {
		return this;
	}

	public ComplexSquareMatrix[] luDecompose() {
		ComplexDiagonalMatrix acomplexdiagonalmatrix[] = new ComplexDiagonalMatrix[2];
		acomplexdiagonalmatrix[0] = identity(super.matrix[0].length);
		acomplexdiagonalmatrix[1] = this;
		return acomplexdiagonalmatrix;
	}

	public ComplexMatrix mapElements(Mapping mapping) {
		Complex acomplex[] = new Complex[super.matrix[0].length];
		acomplex[0] = mapping.map(super.matrix[0][0]);
		for (int i = 1; i < acomplex.length; i++)
			acomplex[i] = mapping.map(super.matrix[0][i]);

		return new ComplexDiagonalMatrix(acomplex);
	}
}
