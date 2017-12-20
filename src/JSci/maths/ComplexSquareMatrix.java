package JSci.maths;


// Referenced classes of package JSci.maths:
//			ComplexMatrix, Complex, ComplexDiagonalMatrix, ComplexVector, 
//			DimensionException, Mapping, MatrixDimensionException, Matrix

public class ComplexSquareMatrix extends ComplexMatrix {

	protected ComplexSquareMatrix() {
	}

	public ComplexSquareMatrix(int i) {
		super(i, i);
	}

	public ComplexSquareMatrix(Complex acomplex[][]) {
		super(acomplex);
		if (acomplex.length != acomplex[0].length) {
			super.matrix = null;
			throw new MatrixDimensionException("The array is not square.");
		} else {
			return;
		}
	}

	public ComplexSquareMatrix(ComplexVector acomplexvector[]) {
		super(acomplexvector);
		if (acomplexvector.length != acomplexvector[0].dimension()) {
			super.matrix = null;
			throw new MatrixDimensionException("The array does not form a square matrix.");
		} else {
			return;
		}
	}

	public boolean isHermitian() {
		return equals(hermitianAdjoint());
	}

	public boolean isUnitary() {
		return multiply(hermitianAdjoint()).equals(ComplexDiagonalMatrix.identity(super.matrix[0].length));
	}

	public Complex det() {
		if (super.matrix.length == 2)
			return super.matrix[0][0].multiply(super.matrix[1][1]).subtract(super.matrix[0][1].multiply(super.matrix[1][0]));
		ComplexSquareMatrix acomplexsquarematrix[] = luDecompose();
		Complex complex = ((ComplexMatrix) (acomplexsquarematrix[1])).matrix[0][0];
		for (int i = 1; i < super.matrix.length; i++)
			complex = complex.multiply(((ComplexMatrix) (acomplexsquarematrix[1])).matrix[i][i]);

		return complex;
	}

	public Complex trace() {
		Complex complex = super.matrix[0][0];
		for (int i = 1; i < super.matrix.length; i++)
			complex = complex.add(super.matrix[i][i]);

		return complex;
	}

	public Matrix add(Matrix matrix) {
		if (matrix instanceof ComplexSquareMatrix)
			return add((ComplexSquareMatrix)matrix);
		if (matrix instanceof ComplexMatrix)
			return rawAdd((ComplexMatrix)matrix);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public ComplexMatrix add(ComplexMatrix complexmatrix) {
		if (complexmatrix instanceof ComplexSquareMatrix)
			return add((ComplexSquareMatrix)complexmatrix);
		else
			return rawAdd(complexmatrix);
	}

	private ComplexMatrix rawAdd(ComplexMatrix complexmatrix) {
		if (super.matrix.length == complexmatrix.rows() && super.matrix.length == complexmatrix.columns()) {
			Complex acomplex[][] = new Complex[super.matrix.length][super.matrix.length];
			for (int j = 0; j < acomplex.length; j++) {
				acomplex[j][0] = super.matrix[j][0].add(complexmatrix.getElement(j, 0));
				for (int i = 1; i < acomplex.length; i++)
					acomplex[j][i] = super.matrix[j][i].add(complexmatrix.getElement(j, i));

			}

			return new ComplexSquareMatrix(acomplex);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public ComplexSquareMatrix add(ComplexSquareMatrix complexsquarematrix) {
		if (super.matrix.length == complexsquarematrix.rows()) {
			Complex acomplex[][] = new Complex[super.matrix.length][super.matrix.length];
			for (int j = 0; j < acomplex.length; j++) {
				acomplex[j][0] = super.matrix[j][0].add(complexsquarematrix.getElement(j, 0));
				for (int i = 1; i < acomplex.length; i++)
					acomplex[j][i] = super.matrix[j][i].add(complexsquarematrix.getElement(j, i));

			}

			return new ComplexSquareMatrix(acomplex);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public Matrix subtract(Matrix matrix) {
		if (matrix instanceof ComplexSquareMatrix)
			return subtract((ComplexSquareMatrix)matrix);
		if (matrix instanceof ComplexMatrix)
			return rawSubtract((ComplexMatrix)matrix);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public ComplexMatrix subtract(ComplexMatrix complexmatrix) {
		if (complexmatrix instanceof ComplexSquareMatrix)
			return subtract((ComplexSquareMatrix)complexmatrix);
		else
			return rawSubtract(complexmatrix);
	}

	private ComplexMatrix rawSubtract(ComplexMatrix complexmatrix) {
		if (super.matrix.length == complexmatrix.rows() && super.matrix.length == complexmatrix.columns()) {
			Complex acomplex[][] = new Complex[super.matrix.length][super.matrix.length];
			for (int j = 0; j < acomplex.length; j++) {
				acomplex[j][0] = super.matrix[j][0].subtract(complexmatrix.getElement(j, 0));
				for (int i = 1; i < acomplex.length; i++)
					acomplex[j][i] = super.matrix[j][i].subtract(complexmatrix.getElement(j, i));

			}

			return new ComplexSquareMatrix(acomplex);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public ComplexSquareMatrix subtract(ComplexSquareMatrix complexsquarematrix) {
		if (super.matrix.length == complexsquarematrix.rows()) {
			Complex acomplex[][] = new Complex[super.matrix.length][super.matrix.length];
			for (int j = 0; j < acomplex.length; j++) {
				acomplex[j][0] = super.matrix[j][0].subtract(complexsquarematrix.getElement(j, 0));
				for (int i = 1; i < acomplex.length; i++)
					acomplex[j][i] = super.matrix[j][i].subtract(complexsquarematrix.getElement(j, i));

			}

			return new ComplexSquareMatrix(acomplex);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public ComplexMatrix scalarMultiply(Complex complex) {
		Complex acomplex[][] = new Complex[super.matrix.length][super.matrix.length];
		for (int j = 0; j < acomplex.length; j++) {
			acomplex[j][0] = complex.multiply(super.matrix[j][0]);
			for (int i = 1; i < acomplex.length; i++)
				acomplex[j][i] = complex.multiply(super.matrix[j][i]);

		}

		return new ComplexSquareMatrix(acomplex);
	}

	public ComplexMatrix scalarMultiply(double d) {
		Complex acomplex[][] = new Complex[super.matrix.length][super.matrix.length];
		for (int j = 0; j < acomplex.length; j++) {
			acomplex[j][0] = super.matrix[j][0].multiply(d);
			for (int i = 1; i < acomplex.length; i++)
				acomplex[j][i] = super.matrix[j][i].multiply(d);

		}

		return new ComplexSquareMatrix(acomplex);
	}

	public ComplexVector multiply(ComplexVector complexvector) {
		if (super.matrix.length == complexvector.dimension()) {
			Complex acomplex[] = new Complex[super.matrix.length];
			for (int j = 0; j < acomplex.length; j++) {
				acomplex[j] = super.matrix[j][0].multiply(complexvector.getComponent(0));
				for (int i = 1; i < super.matrix.length; i++)
					acomplex[j] = acomplex[j].add(super.matrix[j][i].multiply(complexvector.getComponent(i)));

			}

			return new ComplexVector(acomplex);
		} else {
			throw new DimensionException("Matrix and vector are incompatible.");
		}
	}

	public Matrix multiply(Matrix matrix) {
		if (matrix instanceof ComplexSquareMatrix)
			return multiply((ComplexSquareMatrix)matrix);
		if (matrix instanceof ComplexMatrix)
			return rawMultiply((ComplexMatrix)matrix);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public ComplexMatrix multiply(ComplexMatrix complexmatrix) {
		if (complexmatrix instanceof ComplexSquareMatrix)
			return multiply((ComplexSquareMatrix)complexmatrix);
		else
			return rawMultiply(complexmatrix);
	}

	private ComplexMatrix rawMultiply(ComplexMatrix complexmatrix) {
		if (super.matrix[0].length == complexmatrix.rows()) {
			Complex acomplex[][] = new Complex[super.matrix.length][complexmatrix.columns()];
			for (int k = 0; k < acomplex.length; k++) {
				for (int j = 0; j < acomplex[0].length; j++) {
					acomplex[k][j] = super.matrix[k][0].multiply(complexmatrix.getElement(0, j));
					for (int i = 1; i < super.matrix[0].length; i++)
						acomplex[k][j] = acomplex[k][j].add(super.matrix[k][i].multiply(complexmatrix.getElement(i, j)));

				}

			}

			return new ComplexMatrix(acomplex);
		} else {
			throw new MatrixDimensionException("Incompatible matrices.");
		}
	}

	public ComplexSquareMatrix multiply(ComplexSquareMatrix complexsquarematrix) {
		if (super.matrix.length == complexsquarematrix.rows()) {
			Complex acomplex[][] = new Complex[super.matrix.length][super.matrix.length];
			for (int k = 0; k < acomplex.length; k++) {
				for (int j = 0; j < acomplex.length; j++) {
					acomplex[k][j] = super.matrix[k][0].multiply(complexsquarematrix.getElement(0, j));
					for (int i = 1; i < acomplex.length; i++)
						acomplex[k][j] = acomplex[k][j].add(super.matrix[k][i].multiply(complexsquarematrix.getElement(i, j)));

				}

			}

			return new ComplexSquareMatrix(acomplex);
		} else {
			throw new MatrixDimensionException("Incompatible matrices.");
		}
	}

	public ComplexMatrix hermitianAdjoint() {
		Complex acomplex[][] = new Complex[super.matrix.length][super.matrix.length];
		for (int j = 0; j < acomplex.length; j++) {
			acomplex[0][j] = super.matrix[j][0].conjugate();
			for (int i = 1; i < acomplex.length; i++)
				acomplex[i][j] = super.matrix[j][i].conjugate();

		}

		return new ComplexSquareMatrix(acomplex);
	}

	public ComplexMatrix conjugate() {
		Complex acomplex[][] = new Complex[super.matrix.length][super.matrix.length];
		for (int j = 0; j < acomplex.length; j++) {
			acomplex[j][0] = super.matrix[j][0].conjugate();
			for (int i = 1; i < acomplex.length; i++)
				acomplex[j][i] = super.matrix[j][i].conjugate();

		}

		return new ComplexSquareMatrix(acomplex);
	}

	public Matrix transpose() {
		Complex acomplex[][] = new Complex[super.matrix.length][super.matrix.length];
		for (int j = 0; j < acomplex.length; j++) {
			acomplex[0][j] = super.matrix[j][0];
			for (int i = 1; i < acomplex.length; i++)
				acomplex[i][j] = super.matrix[j][i];

		}

		return new ComplexSquareMatrix(acomplex);
	}

	public ComplexSquareMatrix inverse() {
		Complex acomplex[][][] = new Complex[2][super.matrix.length][super.matrix.length];
		ComplexSquareMatrix acomplexsquarematrix[] = luDecompose();
		acomplex[0][0][0] = Complex.ONE.divide(((ComplexMatrix) (acomplexsquarematrix[0])).matrix[0][0]);
		acomplex[1][0][0] = Complex.ONE.divide(((ComplexMatrix) (acomplexsquarematrix[1])).matrix[0][0]);
		for (int i = 1; i < super.matrix.length; i++) {
			acomplex[0][i][i] = Complex.ONE.divide(((ComplexMatrix) (acomplexsquarematrix[0])).matrix[i][i]);
			acomplex[1][i][i] = Complex.ONE.divide(((ComplexMatrix) (acomplexsquarematrix[1])).matrix[i][i]);
		}

		for (int j = 0; j < super.matrix.length - 1; j++) {
			for (int k = j + 1; k < super.matrix.length; k++) {
				Complex complex1;
				Complex complex = complex1 = Complex.ZERO;
				for (int l = j; l < k; l++) {
					complex = complex.subtract(((ComplexMatrix) (acomplexsquarematrix[0])).matrix[k][l].multiply(acomplex[0][l][j]));
					complex1 = complex1.subtract(acomplex[1][j][l].multiply(((ComplexMatrix) (acomplexsquarematrix[1])).matrix[l][k]));
				}

				acomplex[0][j][k] = Complex.ZERO;
				acomplex[0][k][j] = complex.divide(((ComplexMatrix) (acomplexsquarematrix[0])).matrix[k][k]);
				acomplex[1][k][j] = Complex.ZERO;
				acomplex[1][j][k] = complex1.divide(((ComplexMatrix) (acomplexsquarematrix[1])).matrix[k][k]);
			}

		}

		return (new ComplexSquareMatrix(acomplex[1])).multiply(new ComplexSquareMatrix(acomplex[0]));
	}

	public ComplexSquareMatrix[] luDecompose() {
		Complex acomplex[][][] = new Complex[2][super.matrix.length][super.matrix.length];
		acomplex[0][0][0] = Complex.ONE;
		for (int i = 1; i < super.matrix.length; i++)
			acomplex[0][i][i] = Complex.ONE;

		for (int l = 0; l < super.matrix.length; l++) {
			for (int j = 0; j <= l; j++) {
				Complex complex = super.matrix[j][l];
				for (int i1 = 0; i1 < j; i1++)
					complex = complex.subtract(acomplex[0][j][i1].multiply(acomplex[1][i1][l]));

				acomplex[1][l][j] = Complex.ZERO;
				acomplex[1][j][l] = complex;
			}

			for (int k = l + 1; k < super.matrix.length; k++) {
				Complex complex1 = super.matrix[k][l];
				for (int j1 = 0; j1 < l; j1++)
					complex1 = complex1.subtract(acomplex[0][k][j1].multiply(acomplex[1][j1][l]));

				acomplex[0][l][k] = Complex.ZERO;
				acomplex[0][k][l] = complex1.divide(acomplex[1][l][l]);
			}

		}

		ComplexSquareMatrix acomplexsquarematrix[] = new ComplexSquareMatrix[2];
		acomplexsquarematrix[0] = new ComplexSquareMatrix(acomplex[0]);
		acomplexsquarematrix[1] = new ComplexSquareMatrix(acomplex[1]);
		return acomplexsquarematrix;
	}

	public ComplexMatrix mapElements(Mapping mapping) {
		Complex acomplex[][] = new Complex[super.matrix.length][super.matrix.length];
		for (int j = 0; j < acomplex.length; j++) {
			acomplex[j][0] = mapping.map(super.matrix[j][0]);
			for (int i = 1; i < acomplex.length; i++)
				acomplex[j][i] = mapping.map(super.matrix[j][i]);

		}

		return new ComplexSquareMatrix(acomplex);
	}
}
