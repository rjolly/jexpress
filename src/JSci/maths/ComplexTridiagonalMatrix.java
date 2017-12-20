package JSci.maths;


// Referenced classes of package JSci.maths:
//			ComplexSquareMatrix, Complex, ComplexMatrix, ComplexVector, 
//			DimensionException, DoubleMatrix, DoubleTridiagonalMatrix, Mapping, 
//			MatrixDimensionException, Matrix

public class ComplexTridiagonalMatrix extends ComplexSquareMatrix {

	protected ComplexTridiagonalMatrix() {
	}

	public ComplexTridiagonalMatrix(int i) {
		this();
		super.matrix = new Complex[3][i];
	}

	public ComplexTridiagonalMatrix(Complex acomplex[][]) {
		this(acomplex.length);
		if (acomplex.length == acomplex[0].length) {
			super.matrix[1][0] = acomplex[0][0];
			super.matrix[2][0] = acomplex[0][1];
			int i;
			for (i = 1; i < acomplex.length - 1; i++) {
				super.matrix[0][i] = acomplex[i][i - 1];
				super.matrix[1][i] = acomplex[i][i];
				super.matrix[2][i] = acomplex[i][i + 1];
			}

			super.matrix[0][i] = acomplex[i][i - 1];
			super.matrix[1][i] = acomplex[i][i];
			return;
		} else {
			super.matrix = null;
			throw new MatrixDimensionException("The array is not square.");
		}
	}

	public boolean equals(Object obj) {
		if (obj != null && (obj instanceof ComplexTridiagonalMatrix) && super.matrix[1].length == ((ComplexTridiagonalMatrix)obj).rows()) {
			ComplexTridiagonalMatrix complextridiagonalmatrix = (ComplexTridiagonalMatrix)obj;
			if (!super.matrix[1][0].equals(complextridiagonalmatrix.getElement(0, 0)))
				return false;
			if (!super.matrix[2][0].equals(complextridiagonalmatrix.getElement(0, 1)))
				return false;
			int i;
			for (i = 1; i < super.matrix[1].length - 1; i++) {
				if (!super.matrix[0][i].equals(complextridiagonalmatrix.getElement(i, i - 1)))
					return false;
				if (!super.matrix[1][i].equals(complextridiagonalmatrix.getElement(i, i)))
					return false;
				if (!super.matrix[2][i].equals(complextridiagonalmatrix.getElement(i, i + 1)))
					return false;
			}

			if (!super.matrix[0][i].equals(complextridiagonalmatrix.getElement(i, i - 1)))
				return false;
			return super.matrix[1][i].equals(complextridiagonalmatrix.getElement(i, i));
		} else {
			return false;
		}
	}

	public String toString() {
		StringBuffer stringbuffer = new StringBuffer(super.matrix.length * super.matrix[1].length);
		for (int j = 0; j < rows(); j++) {
			for (int i = 0; i < columns(); i++) {
				stringbuffer.append(getElement(j, i).toString());
				stringbuffer.append(' ');
			}

			stringbuffer.append('\n');
		}

		return stringbuffer.toString();
	}

	public DoubleMatrix real() {
		DoubleTridiagonalMatrix doubletridiagonalmatrix = new DoubleTridiagonalMatrix(super.matrix[1].length);
		((DoubleMatrix) (doubletridiagonalmatrix)).matrix[1][0] = super.matrix[1][0].real();
		((DoubleMatrix) (doubletridiagonalmatrix)).matrix[2][0] = super.matrix[2][0].real();
		int i;
		for (i = 1; i < super.matrix[1].length - 1; i++) {
			((DoubleMatrix) (doubletridiagonalmatrix)).matrix[0][i] = super.matrix[0][i].real();
			((DoubleMatrix) (doubletridiagonalmatrix)).matrix[1][i] = super.matrix[1][i].real();
			((DoubleMatrix) (doubletridiagonalmatrix)).matrix[2][i] = super.matrix[2][i].real();
		}

		((DoubleMatrix) (doubletridiagonalmatrix)).matrix[0][i] = super.matrix[0][i].real();
		((DoubleMatrix) (doubletridiagonalmatrix)).matrix[1][i] = super.matrix[1][i].real();
		return doubletridiagonalmatrix;
	}

	public DoubleMatrix imag() {
		DoubleTridiagonalMatrix doubletridiagonalmatrix = new DoubleTridiagonalMatrix(super.matrix[1].length);
		((DoubleMatrix) (doubletridiagonalmatrix)).matrix[1][0] = super.matrix[1][0].imag();
		((DoubleMatrix) (doubletridiagonalmatrix)).matrix[2][0] = super.matrix[2][0].imag();
		int i;
		for (i = 1; i < super.matrix[1].length - 1; i++) {
			((DoubleMatrix) (doubletridiagonalmatrix)).matrix[0][i] = super.matrix[0][i].imag();
			((DoubleMatrix) (doubletridiagonalmatrix)).matrix[1][i] = super.matrix[1][i].imag();
			((DoubleMatrix) (doubletridiagonalmatrix)).matrix[2][i] = super.matrix[2][i].imag();
		}

		((DoubleMatrix) (doubletridiagonalmatrix)).matrix[0][i] = super.matrix[0][i].imag();
		((DoubleMatrix) (doubletridiagonalmatrix)).matrix[1][i] = super.matrix[1][i].imag();
		return doubletridiagonalmatrix;
	}

	public Complex getElement(int i, int j) {
		if (i >= 0 && i < super.matrix[1].length && j >= 0 && j < super.matrix[1].length) {
			if (j == i - 1)
				return super.matrix[0][i];
			if (j == i)
				return super.matrix[1][i];
			if (j == i + 1)
				return super.matrix[2][i];
			else
				return Complex.ZERO;
		} else {
			throw new MatrixDimensionException("Invalid element.");
		}
	}

	public void setElement(int i, int j, Complex complex) {
		if (i >= 0 && i < super.matrix[1].length && j >= 0 && j < super.matrix[1].length) {
			if (j == i - 1) {
				super.matrix[0][i] = complex;
				return;
			}
			if (j == i) {
				super.matrix[1][i] = complex;
				return;
			}
			if (j == i + 1) {
				super.matrix[2][i] = complex;
				return;
			} else {
				throw new MatrixDimensionException("Invalid element.");
			}
		} else {
			throw new MatrixDimensionException("Invalid element.");
		}
	}

	public Complex trace() {
		Complex complex = super.matrix[1][0];
		for (int i = 1; i < super.matrix[1].length; i++)
			complex = complex.add(super.matrix[1][i]);

		return complex;
	}

	public double infNorm() {
		double d = super.matrix[1][0].mod() + super.matrix[2][0].mod();
		int i;
		for (i = 1; i < super.matrix[1].length - 1; i++) {
			double d1 = super.matrix[0][i].mod() + super.matrix[1][i].mod() + super.matrix[2][i].mod();
			if (d1 > d)
				d = d1;
		}

		double d2 = super.matrix[0][i].mod() + super.matrix[1][i].mod();
		if (d2 > d)
			d = d2;
		return d;
	}

	public double frobeniusNorm() {
		double d = super.matrix[1][0].mod() * super.matrix[1][0].mod() + super.matrix[2][0].mod() * super.matrix[2][0].mod();
		int i;
		for (i = 1; i < super.matrix[1].length - 1; i++)
			d += super.matrix[0][i].mod() * super.matrix[0][i].mod() + super.matrix[1][i].mod() * super.matrix[1][i].mod() + super.matrix[2][i].mod() * super.matrix[2][i].mod();

		d += super.matrix[0][i].mod() * super.matrix[0][i].mod() + super.matrix[1][i].mod() * super.matrix[1][i].mod();
		return Math.sqrt(d);
	}

	public int rows() {
		return super.matrix[1].length;
	}

	public int columns() {
		return super.matrix[1].length;
	}

	public Matrix add(Matrix matrix) {
		if (matrix instanceof ComplexTridiagonalMatrix)
			return add((ComplexTridiagonalMatrix)matrix);
		if (matrix instanceof ComplexSquareMatrix)
			return rawAdd((ComplexSquareMatrix)matrix);
		if (matrix instanceof ComplexMatrix)
			return rawAdd((ComplexMatrix)matrix);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public ComplexMatrix add(ComplexMatrix complexmatrix) {
		if (complexmatrix instanceof ComplexTridiagonalMatrix)
			return add((ComplexTridiagonalMatrix)complexmatrix);
		if (complexmatrix instanceof ComplexSquareMatrix)
			return rawAdd((ComplexSquareMatrix)complexmatrix);
		else
			return rawAdd(complexmatrix);
	}

	private ComplexMatrix rawAdd(ComplexMatrix complexmatrix) {
		if (super.matrix[1].length == complexmatrix.rows() && super.matrix[1].length == complexmatrix.columns()) {
			Complex acomplex[][] = new Complex[super.matrix[1].length][super.matrix[1].length];
			for (int j = 0; j < acomplex.length; j++) {
				acomplex[j][0] = getElement(j, 0).add(complexmatrix.getElement(j, 0));
				for (int i = 1; i < acomplex.length; i++)
					acomplex[j][i] = getElement(j, i).add(complexmatrix.getElement(j, i));

			}

			return new ComplexSquareMatrix(acomplex);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public ComplexSquareMatrix add(ComplexSquareMatrix complexsquarematrix) {
		if (complexsquarematrix instanceof ComplexTridiagonalMatrix)
			return add((ComplexTridiagonalMatrix)complexsquarematrix);
		else
			return rawAdd(complexsquarematrix);
	}

	private ComplexSquareMatrix rawAdd(ComplexSquareMatrix complexsquarematrix) {
		if (super.matrix[1].length == complexsquarematrix.rows()) {
			Complex acomplex[][] = new Complex[super.matrix[1].length][super.matrix[1].length];
			for (int j = 0; j < acomplex.length; j++) {
				acomplex[j][0] = getElement(j, 0).add(complexsquarematrix.getElement(j, 0));
				for (int i = 1; i < acomplex.length; i++)
					acomplex[j][i] = getElement(j, i).add(complexsquarematrix.getElement(j, i));

			}

			return new ComplexSquareMatrix(acomplex);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public ComplexTridiagonalMatrix add(ComplexTridiagonalMatrix complextridiagonalmatrix) {
		int i = super.matrix[1].length;
		if (i == complextridiagonalmatrix.rows()) {
			ComplexTridiagonalMatrix complextridiagonalmatrix1 = new ComplexTridiagonalMatrix(i);
			((ComplexMatrix) (complextridiagonalmatrix1)).matrix[1][0] = super.matrix[1][0].add(complextridiagonalmatrix.getElement(0, 0));
			((ComplexMatrix) (complextridiagonalmatrix1)).matrix[2][0] = super.matrix[2][0].add(complextridiagonalmatrix.getElement(0, 1));
			i--;
			for (int j = 1; j < i; j++) {
				((ComplexMatrix) (complextridiagonalmatrix1)).matrix[0][j] = super.matrix[0][j].add(complextridiagonalmatrix.getElement(j, j - 1));
				((ComplexMatrix) (complextridiagonalmatrix1)).matrix[1][j] = super.matrix[1][j].add(complextridiagonalmatrix.getElement(j, j));
				((ComplexMatrix) (complextridiagonalmatrix1)).matrix[2][j] = super.matrix[2][j].add(complextridiagonalmatrix.getElement(j, j + 1));
			}

			((ComplexMatrix) (complextridiagonalmatrix1)).matrix[0][i] = super.matrix[0][i].add(complextridiagonalmatrix.getElement(i, i - 1));
			((ComplexMatrix) (complextridiagonalmatrix1)).matrix[1][i] = super.matrix[1][i].add(complextridiagonalmatrix.getElement(i, i));
			return complextridiagonalmatrix1;
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public Matrix subtract(Matrix matrix) {
		if (matrix instanceof ComplexTridiagonalMatrix)
			return subtract((ComplexTridiagonalMatrix)matrix);
		if (matrix instanceof ComplexSquareMatrix)
			return rawSubtract((ComplexSquareMatrix)matrix);
		if (matrix instanceof ComplexMatrix)
			return rawSubtract((ComplexMatrix)matrix);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public ComplexMatrix subtract(ComplexMatrix complexmatrix) {
		if (complexmatrix instanceof ComplexTridiagonalMatrix)
			return subtract((ComplexTridiagonalMatrix)complexmatrix);
		if (complexmatrix instanceof ComplexSquareMatrix)
			return rawSubtract((ComplexSquareMatrix)complexmatrix);
		else
			return rawSubtract(complexmatrix);
	}

	private ComplexMatrix rawSubtract(ComplexMatrix complexmatrix) {
		if (super.matrix[1].length == complexmatrix.rows() && super.matrix[1].length == complexmatrix.columns()) {
			Complex acomplex[][] = new Complex[super.matrix[1].length][super.matrix[1].length];
			for (int j = 0; j < acomplex.length; j++) {
				acomplex[j][0] = getElement(j, 0).subtract(complexmatrix.getElement(j, 0));
				for (int i = 1; i < acomplex.length; i++)
					acomplex[j][i] = getElement(j, i).subtract(complexmatrix.getElement(j, i));

			}

			return new ComplexSquareMatrix(acomplex);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public ComplexSquareMatrix subtract(ComplexSquareMatrix complexsquarematrix) {
		if (complexsquarematrix instanceof ComplexTridiagonalMatrix)
			return subtract((ComplexTridiagonalMatrix)complexsquarematrix);
		else
			return rawSubtract(complexsquarematrix);
	}

	private ComplexSquareMatrix rawSubtract(ComplexSquareMatrix complexsquarematrix) {
		if (super.matrix[1].length == complexsquarematrix.rows()) {
			Complex acomplex[][] = new Complex[super.matrix[1].length][super.matrix[1].length];
			for (int j = 0; j < acomplex.length; j++) {
				acomplex[j][0] = getElement(j, 0).subtract(complexsquarematrix.getElement(j, 0));
				for (int i = 1; i < acomplex.length; i++)
					acomplex[j][i] = getElement(j, i).subtract(complexsquarematrix.getElement(j, i));

			}

			return new ComplexSquareMatrix(acomplex);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public ComplexTridiagonalMatrix subtract(ComplexTridiagonalMatrix complextridiagonalmatrix) {
		int i = super.matrix[1].length;
		if (i == complextridiagonalmatrix.rows()) {
			ComplexTridiagonalMatrix complextridiagonalmatrix1 = new ComplexTridiagonalMatrix(i);
			((ComplexMatrix) (complextridiagonalmatrix1)).matrix[1][0] = super.matrix[1][0].subtract(complextridiagonalmatrix.getElement(0, 0));
			((ComplexMatrix) (complextridiagonalmatrix1)).matrix[2][0] = super.matrix[2][0].subtract(complextridiagonalmatrix.getElement(0, 1));
			i--;
			for (int j = 1; j < i; j++) {
				((ComplexMatrix) (complextridiagonalmatrix1)).matrix[0][j] = super.matrix[0][j].subtract(complextridiagonalmatrix.getElement(j, j - 1));
				((ComplexMatrix) (complextridiagonalmatrix1)).matrix[1][j] = super.matrix[1][j].subtract(complextridiagonalmatrix.getElement(j, j));
				((ComplexMatrix) (complextridiagonalmatrix1)).matrix[2][j] = super.matrix[2][j].subtract(complextridiagonalmatrix.getElement(j, j + 1));
			}

			((ComplexMatrix) (complextridiagonalmatrix1)).matrix[0][i] = super.matrix[0][i].subtract(complextridiagonalmatrix.getElement(i, i - 1));
			((ComplexMatrix) (complextridiagonalmatrix1)).matrix[1][i] = super.matrix[1][i].subtract(complextridiagonalmatrix.getElement(i, i));
			return complextridiagonalmatrix1;
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public ComplexMatrix scalarMultiply(Complex complex) {
		int i = super.matrix[1].length;
		ComplexTridiagonalMatrix complextridiagonalmatrix = new ComplexTridiagonalMatrix(i);
		((ComplexMatrix) (complextridiagonalmatrix)).matrix[1][0] = complex.multiply(super.matrix[1][0]);
		((ComplexMatrix) (complextridiagonalmatrix)).matrix[2][0] = complex.multiply(super.matrix[2][0]);
		i--;
		for (int j = 1; j < i; j++) {
			((ComplexMatrix) (complextridiagonalmatrix)).matrix[0][j] = complex.multiply(super.matrix[0][j]);
			((ComplexMatrix) (complextridiagonalmatrix)).matrix[1][j] = complex.multiply(super.matrix[1][j]);
			((ComplexMatrix) (complextridiagonalmatrix)).matrix[2][j] = complex.multiply(super.matrix[2][j]);
		}

		((ComplexMatrix) (complextridiagonalmatrix)).matrix[0][i] = complex.multiply(super.matrix[0][i]);
		((ComplexMatrix) (complextridiagonalmatrix)).matrix[1][i] = complex.multiply(super.matrix[1][i]);
		return complextridiagonalmatrix;
	}

	public ComplexMatrix scalarMultiply(double d) {
		int i = super.matrix[1].length;
		ComplexTridiagonalMatrix complextridiagonalmatrix = new ComplexTridiagonalMatrix(i);
		((ComplexMatrix) (complextridiagonalmatrix)).matrix[1][0] = super.matrix[1][0].multiply(d);
		((ComplexMatrix) (complextridiagonalmatrix)).matrix[2][0] = super.matrix[2][0].multiply(d);
		i--;
		for (int j = 1; j < i; j++) {
			((ComplexMatrix) (complextridiagonalmatrix)).matrix[0][j] = super.matrix[0][j].multiply(d);
			((ComplexMatrix) (complextridiagonalmatrix)).matrix[1][j] = super.matrix[1][j].multiply(d);
			((ComplexMatrix) (complextridiagonalmatrix)).matrix[2][j] = super.matrix[2][j].multiply(d);
		}

		((ComplexMatrix) (complextridiagonalmatrix)).matrix[0][i] = super.matrix[0][i].multiply(d);
		((ComplexMatrix) (complextridiagonalmatrix)).matrix[1][i] = super.matrix[1][i].multiply(d);
		return complextridiagonalmatrix;
	}

	public ComplexVector multiply(ComplexVector complexvector) {
		int i = super.matrix[1].length;
		if (i == complexvector.dimension()) {
			Complex acomplex[] = new Complex[i];
			acomplex[0] = super.matrix[1][0].multiply(complexvector.getComponent(0)).add(super.matrix[2][0].multiply(complexvector.getComponent(1)));
			i--;
			for (int j = 1; j < i; j++)
				acomplex[j] = super.matrix[0][j].multiply(complexvector.getComponent(j - 1)).add(super.matrix[1][j].multiply(complexvector.getComponent(j))).add(super.matrix[2][j].multiply(complexvector.getComponent(j + 1)));

			acomplex[i] = super.matrix[0][i].multiply(complexvector.getComponent(i - 1)).add(super.matrix[1][i].multiply(complexvector.getComponent(i)));
			return new ComplexVector(acomplex);
		} else {
			throw new DimensionException("Matrix and vector are incompatible.");
		}
	}

	public Matrix multiply(Matrix matrix) {
		if (matrix instanceof ComplexTridiagonalMatrix)
			return multiply((ComplexTridiagonalMatrix)matrix);
		if (matrix instanceof ComplexSquareMatrix)
			return rawMultiply((ComplexSquareMatrix)matrix);
		if (matrix instanceof ComplexMatrix)
			return rawMultiply((ComplexMatrix)matrix);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public ComplexMatrix multiply(ComplexMatrix complexmatrix) {
		if (complexmatrix instanceof ComplexTridiagonalMatrix)
			return multiply((ComplexTridiagonalMatrix)complexmatrix);
		if (complexmatrix instanceof ComplexSquareMatrix)
			return rawMultiply((ComplexSquareMatrix)complexmatrix);
		else
			return rawMultiply(complexmatrix);
	}

	private ComplexMatrix rawMultiply(ComplexMatrix complexmatrix) {
		if (super.matrix[1].length == complexmatrix.rows()) {
			Complex acomplex[][] = new Complex[super.matrix[1].length][complexmatrix.columns()];
			for (int k = 0; k < acomplex.length; k++) {
				for (int j = 0; j < acomplex[0].length; j++) {
					acomplex[k][j] = getElement(k, 0).multiply(complexmatrix.getElement(0, j));
					for (int i = 1; i < super.matrix[1].length; i++)
						acomplex[k][j] = acomplex[k][j].add(getElement(k, i).multiply(complexmatrix.getElement(i, j)));

				}

			}

			return new ComplexMatrix(acomplex);
		} else {
			throw new MatrixDimensionException("Incompatible matrices.");
		}
	}

	public ComplexSquareMatrix multiply(ComplexSquareMatrix complexsquarematrix) {
		if (complexsquarematrix instanceof ComplexTridiagonalMatrix)
			return multiply((ComplexTridiagonalMatrix)complexsquarematrix);
		else
			return rawMultiply(complexsquarematrix);
	}

	private ComplexSquareMatrix rawMultiply(ComplexSquareMatrix complexsquarematrix) {
		if (super.matrix[1].length == complexsquarematrix.rows()) {
			Complex acomplex[][] = new Complex[super.matrix[1].length][super.matrix[1].length];
			for (int k = 0; k < acomplex.length; k++) {
				for (int j = 0; j < acomplex.length; j++) {
					acomplex[k][j] = getElement(k, 0).multiply(complexsquarematrix.getElement(0, j));
					for (int i = 1; i < acomplex.length; i++)
						acomplex[k][j] = acomplex[k][j].add(getElement(k, i).multiply(complexsquarematrix.getElement(i, j)));

				}

			}

			return new ComplexSquareMatrix(acomplex);
		} else {
			throw new MatrixDimensionException("Incompatible matrices.");
		}
	}

	public ComplexSquareMatrix multiply(ComplexTridiagonalMatrix complextridiagonalmatrix) {
		int i = super.matrix[1].length;
		if (i == complextridiagonalmatrix.rows()) {
			Complex acomplex[][] = new Complex[i][i];
			acomplex[0][0] = super.matrix[1][0].multiply(complextridiagonalmatrix.getElement(0, 0)).add(super.matrix[2][0].multiply(complextridiagonalmatrix.getElement(1, 0)));
			acomplex[0][1] = super.matrix[1][0].multiply(complextridiagonalmatrix.getElement(0, 1)).add(super.matrix[2][0].multiply(complextridiagonalmatrix.getElement(1, 1)));
			acomplex[0][2] = super.matrix[2][0].multiply(complextridiagonalmatrix.getElement(1, 2));
			if (i > 3) {
				acomplex[1][0] = super.matrix[0][1].multiply(complextridiagonalmatrix.getElement(0, 0)).add(super.matrix[1][1].multiply(complextridiagonalmatrix.getElement(1, 0)));
				acomplex[1][1] = super.matrix[0][1].multiply(complextridiagonalmatrix.getElement(0, 1)).add(super.matrix[1][1].multiply(complextridiagonalmatrix.getElement(1, 1))).add(super.matrix[2][1].multiply(complextridiagonalmatrix.getElement(2, 1)));
				acomplex[1][2] = super.matrix[1][1].multiply(complextridiagonalmatrix.getElement(1, 2)).add(super.matrix[2][1].multiply(complextridiagonalmatrix.getElement(2, 2)));
				acomplex[1][3] = super.matrix[2][1].multiply(complextridiagonalmatrix.getElement(2, 3));
			}
			if (i == 3) {
				acomplex[1][0] = super.matrix[0][1].multiply(complextridiagonalmatrix.getElement(0, 0)).add(super.matrix[1][1].multiply(complextridiagonalmatrix.getElement(1, 0)));
				acomplex[1][1] = super.matrix[0][1].multiply(complextridiagonalmatrix.getElement(0, 1)).add(super.matrix[1][1].multiply(complextridiagonalmatrix.getElement(1, 1))).add(super.matrix[2][1].multiply(complextridiagonalmatrix.getElement(2, 1)));
				acomplex[1][2] = super.matrix[1][1].multiply(complextridiagonalmatrix.getElement(1, 2)).add(super.matrix[2][1].multiply(complextridiagonalmatrix.getElement(2, 2)));
			} else
			if (i > 4) {
				for (int j = 2; j < i - 2; j++) {
					acomplex[j][j - 2] = super.matrix[0][j].multiply(complextridiagonalmatrix.getElement(j - 1, j - 2));
					acomplex[j][j - 1] = super.matrix[0][j].multiply(complextridiagonalmatrix.getElement(j - 1, j - 1)).add(super.matrix[1][j].multiply(complextridiagonalmatrix.getElement(j, j - 1)));
					acomplex[j][j] = super.matrix[0][j].multiply(complextridiagonalmatrix.getElement(j - 1, j)).add(super.matrix[1][j].multiply(complextridiagonalmatrix.getElement(j, j))).add(super.matrix[2][j].multiply(complextridiagonalmatrix.getElement(j + 1, j)));
					acomplex[j][j + 1] = super.matrix[1][j].multiply(complextridiagonalmatrix.getElement(j, j + 1)).add(super.matrix[2][j].multiply(complextridiagonalmatrix.getElement(j + 1, j + 1)));
					acomplex[j][j + 2] = super.matrix[2][j].multiply(complextridiagonalmatrix.getElement(j + 1, j + 2));
				}

			}
			if (i > 3) {
				acomplex[i - 2][i - 4] = super.matrix[0][i - 2].multiply(complextridiagonalmatrix.getElement(i - 3, i - 4));
				acomplex[i - 2][i - 3] = super.matrix[0][i - 2].multiply(complextridiagonalmatrix.getElement(i - 3, i - 3)).add(super.matrix[1][i - 2].multiply(complextridiagonalmatrix.getElement(i - 2, i - 3)));
				acomplex[i - 2][i - 2] = super.matrix[0][i - 2].multiply(complextridiagonalmatrix.getElement(i - 3, i - 2)).add(super.matrix[1][i - 2].multiply(complextridiagonalmatrix.getElement(i - 2, i - 2))).add(super.matrix[2][i - 2].multiply(complextridiagonalmatrix.getElement(i - 1, i - 2)));
				acomplex[i - 2][i - 1] = super.matrix[1][i - 2].multiply(complextridiagonalmatrix.getElement(i - 2, i - 1)).add(super.matrix[2][i - 2].multiply(complextridiagonalmatrix.getElement(i - 1, i - 1)));
			}
			i--;
			acomplex[i][i - 2] = super.matrix[0][i].multiply(complextridiagonalmatrix.getElement(i - 1, i - 2));
			acomplex[i][i - 1] = super.matrix[0][i].multiply(complextridiagonalmatrix.getElement(i - 1, i - 1)).add(super.matrix[1][i].multiply(complextridiagonalmatrix.getElement(i, i - 1)));
			acomplex[i][i] = super.matrix[0][i].multiply(complextridiagonalmatrix.getElement(i - 1, i)).add(super.matrix[1][i].multiply(complextridiagonalmatrix.getElement(i, i)));
			return new ComplexSquareMatrix(acomplex);
		} else {
			throw new MatrixDimensionException("Incompatible matrices.");
		}
	}

	public ComplexMatrix hermitianAdjoint() {
		int i = super.matrix[1].length;
		ComplexTridiagonalMatrix complextridiagonalmatrix = new ComplexTridiagonalMatrix(i);
		((ComplexMatrix) (complextridiagonalmatrix)).matrix[1][0] = super.matrix[1][0].conjugate();
		complextridiagonalmatrix.setElement(1, 0, super.matrix[2][0].conjugate());
		i--;
		for (int j = 1; j < i; j++) {
			complextridiagonalmatrix.setElement(j - 1, j, super.matrix[0][j].conjugate());
			((ComplexMatrix) (complextridiagonalmatrix)).matrix[1][j] = super.matrix[1][j].conjugate();
			complextridiagonalmatrix.setElement(j + 1, j, super.matrix[2][j].conjugate());
		}

		complextridiagonalmatrix.setElement(i - 1, i, super.matrix[0][i].conjugate());
		((ComplexMatrix) (complextridiagonalmatrix)).matrix[1][i] = super.matrix[1][i].conjugate();
		return complextridiagonalmatrix;
	}

	public ComplexMatrix conjugate() {
		int i = super.matrix[1].length;
		ComplexTridiagonalMatrix complextridiagonalmatrix = new ComplexTridiagonalMatrix(i);
		((ComplexMatrix) (complextridiagonalmatrix)).matrix[1][0] = super.matrix[1][0].conjugate();
		((ComplexMatrix) (complextridiagonalmatrix)).matrix[2][0] = super.matrix[2][0].conjugate();
		i--;
		for (int j = 1; j < i; j++) {
			((ComplexMatrix) (complextridiagonalmatrix)).matrix[0][j] = super.matrix[0][j].conjugate();
			((ComplexMatrix) (complextridiagonalmatrix)).matrix[1][j] = super.matrix[1][j].conjugate();
			((ComplexMatrix) (complextridiagonalmatrix)).matrix[2][j] = super.matrix[2][j].conjugate();
		}

		((ComplexMatrix) (complextridiagonalmatrix)).matrix[0][i] = super.matrix[0][i].conjugate();
		((ComplexMatrix) (complextridiagonalmatrix)).matrix[1][i] = super.matrix[1][i].conjugate();
		return complextridiagonalmatrix;
	}

	public Matrix transpose() {
		int i = super.matrix[1].length;
		ComplexTridiagonalMatrix complextridiagonalmatrix = new ComplexTridiagonalMatrix(i);
		((ComplexMatrix) (complextridiagonalmatrix)).matrix[1][0] = super.matrix[1][0];
		complextridiagonalmatrix.setElement(1, 0, super.matrix[2][0]);
		i--;
		for (int j = 1; j < i; j++) {
			complextridiagonalmatrix.setElement(j - 1, j, super.matrix[0][j]);
			((ComplexMatrix) (complextridiagonalmatrix)).matrix[1][j] = super.matrix[1][j];
			complextridiagonalmatrix.setElement(j + 1, j, super.matrix[2][j]);
		}

		complextridiagonalmatrix.setElement(i - 1, i, super.matrix[0][i]);
		((ComplexMatrix) (complextridiagonalmatrix)).matrix[1][i] = super.matrix[1][i];
		return complextridiagonalmatrix;
	}

	public ComplexSquareMatrix[] luDecompose() {
		Complex acomplex[][][] = new Complex[2][super.matrix[1].length][super.matrix[1].length];
		acomplex[0][0][0] = Complex.ONE;
		for (int i = 1; i < super.matrix[1].length; i++)
			acomplex[0][i][i] = Complex.ONE;

		for (int l = 0; l < super.matrix[1].length; l++) {
			for (int j = 0; j <= l; j++) {
				Complex complex = getElement(j, l);
				for (int i1 = 0; i1 < j; i1++)
					complex = complex.subtract(acomplex[0][j][i1].multiply(acomplex[1][i1][l]));

				acomplex[1][j][l] = complex;
			}

			for (int k = l + 1; k < super.matrix[1].length; k++) {
				Complex complex1 = getElement(k, l);
				for (int j1 = 0; j1 < l; j1++)
					complex1 = complex1.subtract(acomplex[0][k][j1].multiply(acomplex[1][j1][l]));

				acomplex[0][k][l] = complex1.divide(acomplex[1][l][l]);
			}

		}

		ComplexSquareMatrix acomplexsquarematrix[] = new ComplexSquareMatrix[2];
		acomplexsquarematrix[0] = new ComplexSquareMatrix(acomplex[0]);
		acomplexsquarematrix[1] = new ComplexSquareMatrix(acomplex[1]);
		return acomplexsquarematrix;
	}

	public ComplexMatrix mapElements(Mapping mapping) {
		int i = super.matrix[1].length;
		ComplexTridiagonalMatrix complextridiagonalmatrix = new ComplexTridiagonalMatrix(i);
		((ComplexMatrix) (complextridiagonalmatrix)).matrix[1][0] = mapping.map(super.matrix[1][0]);
		((ComplexMatrix) (complextridiagonalmatrix)).matrix[2][0] = mapping.map(super.matrix[2][0]);
		i--;
		for (int j = 1; j < i; j++) {
			((ComplexMatrix) (complextridiagonalmatrix)).matrix[0][j] = mapping.map(super.matrix[0][j]);
			((ComplexMatrix) (complextridiagonalmatrix)).matrix[1][j] = mapping.map(super.matrix[1][j]);
			((ComplexMatrix) (complextridiagonalmatrix)).matrix[2][j] = mapping.map(super.matrix[2][j]);
		}

		((ComplexMatrix) (complextridiagonalmatrix)).matrix[0][i] = mapping.map(super.matrix[0][i]);
		((ComplexMatrix) (complextridiagonalmatrix)).matrix[1][i] = mapping.map(super.matrix[1][i]);
		return complextridiagonalmatrix;
	}
}
