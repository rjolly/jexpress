package JSci.maths;


// Referenced classes of package JSci.maths:
//			Matrix, Complex, ComplexSquareMatrix, ComplexVector, 
//			DimensionException, DoubleMatrix, Mapping, MathDouble, 
//			MathInteger, MatrixDimensionException, MathNumber

public class ComplexMatrix extends Matrix {

	protected Complex matrix[][];

	protected ComplexMatrix() {
	}

	public ComplexMatrix(int i, int j) {
		matrix = new Complex[i][j];
	}

	public ComplexMatrix(Complex acomplex[][]) {
		matrix = acomplex;
	}

	public ComplexMatrix(ComplexVector acomplexvector[]) {
		this(acomplexvector[0].dimension(), acomplexvector.length);
		for (int j = 0; j < matrix.length; j++) {
			for (int i = 0; i < matrix[0].length; i++)
				matrix[j][i] = acomplexvector[i].getComponent(j);

		}

	}

	protected void finalize() throws Throwable {
		matrix = null;
		super.finalize();
	}

	public boolean equals(Object obj) {
		if (obj != null && (obj instanceof ComplexMatrix) && matrix.length == ((ComplexMatrix)obj).rows() && matrix[0].length == ((ComplexMatrix)obj).columns()) {
			ComplexMatrix complexmatrix = (ComplexMatrix)obj;
			for (int j = 0; j < matrix.length; j++) {
				for (int i = 0; i < matrix[0].length; i++)
					if (!matrix[j][i].equals(complexmatrix.getElement(j, i)))
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
				stringbuffer.append(matrix[j][i].toString());
				stringbuffer.append(' ');
			}

			stringbuffer.append('\n');
		}

		return stringbuffer.toString();
	}

	public int hashCode() {
		return (int)Math.exp(infNorm());
	}

	public DoubleMatrix real() {
		double ad[][] = new double[matrix.length][matrix[0].length];
		for (int j = 0; j < matrix.length; j++) {
			for (int i = 0; i < matrix[0].length; i++)
				ad[j][i] = matrix[j][i].real();

		}

		return new DoubleMatrix(ad);
	}

	public DoubleMatrix imag() {
		double ad[][] = new double[matrix.length][matrix[0].length];
		for (int j = 0; j < matrix.length; j++) {
			for (int i = 0; i < matrix[0].length; i++)
				ad[j][i] = matrix[j][i].imag();

		}

		return new DoubleMatrix(ad);
	}

	public Complex getElement(int i, int j) {
		if (i >= 0 && i < matrix.length && j >= 0 && j < matrix[0].length)
			return matrix[i][j];
		else
			throw new MatrixDimensionException("Invalid element.");
	}

	public void setElement(int i, int j, Complex complex) {
		if (i >= 0 && i < matrix.length && j >= 0 && j < matrix[0].length) {
			matrix[i][j] = complex;
			return;
		} else {
			throw new MatrixDimensionException("Invalid element.");
		}
	}

	public double infNorm() {
		double d = 0.0D;
		for (int j = 0; j < matrix.length; j++) {
			double d1 = 0.0D;
			for (int i = 0; i < matrix[0].length; i++)
				d1 += matrix[j][i].mod();

			if (d1 > d)
				d = d1;
		}

		return d;
	}

	public double frobeniusNorm() {
		double d = 0.0D;
		for (int j = 0; j < matrix.length; j++) {
			for (int i = 0; i < matrix[0].length; i++)
				d += matrix[j][i].mod() * matrix[j][i].mod();

		}

		return Math.sqrt(d);
	}

	public int rows() {
		return matrix.length;
	}

	public int columns() {
		return matrix[0].length;
	}

	public Matrix add(Matrix matrix1) {
		if (matrix1 instanceof ComplexMatrix)
			return add((ComplexMatrix)matrix1);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public ComplexMatrix add(ComplexMatrix complexmatrix) {
		if (matrix.length == complexmatrix.rows() && matrix[0].length == complexmatrix.columns()) {
			Complex acomplex[][] = new Complex[matrix.length][matrix[0].length];
			for (int j = 0; j < acomplex.length; j++) {
				acomplex[j][0] = matrix[j][0].add(complexmatrix.getElement(j, 0));
				for (int i = 1; i < acomplex[0].length; i++)
					acomplex[j][i] = matrix[j][i].add(complexmatrix.getElement(j, i));

			}

			return new ComplexMatrix(acomplex);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public Matrix subtract(Matrix matrix1) {
		if (matrix1 instanceof ComplexMatrix)
			return subtract((ComplexMatrix)matrix1);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public ComplexMatrix subtract(ComplexMatrix complexmatrix) {
		if (matrix.length == complexmatrix.rows() && matrix[0].length == complexmatrix.columns()) {
			Complex acomplex[][] = new Complex[matrix.length][matrix[0].length];
			for (int j = 0; j < acomplex.length; j++) {
				acomplex[j][0] = matrix[j][0].subtract(complexmatrix.getElement(j, 0));
				for (int i = 1; i < acomplex[0].length; i++)
					acomplex[j][i] = matrix[j][i].subtract(complexmatrix.getElement(j, i));

			}

			return new ComplexMatrix(acomplex);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public Matrix scalarMultiply(MathNumber mathnumber) {
		if (mathnumber instanceof Complex)
			return scalarMultiply((Complex)mathnumber);
		if (mathnumber instanceof MathDouble)
			return scalarMultiply(((MathDouble)mathnumber).value());
		if (mathnumber instanceof MathInteger)
			return scalarMultiply(((MathInteger)mathnumber).value());
		else
			throw new IllegalArgumentException("Number class not recognised by this method.");
	}

	public ComplexMatrix scalarMultiply(Complex complex) {
		Complex acomplex[][] = new Complex[matrix.length][matrix[0].length];
		for (int j = 0; j < acomplex.length; j++) {
			acomplex[j][0] = complex.multiply(matrix[j][0]);
			for (int i = 1; i < acomplex[0].length; i++)
				acomplex[j][i] = complex.multiply(matrix[j][i]);

		}

		return new ComplexMatrix(acomplex);
	}

	public ComplexMatrix scalarMultiply(double d) {
		Complex acomplex[][] = new Complex[matrix.length][matrix[0].length];
		for (int j = 0; j < acomplex.length; j++) {
			acomplex[j][0] = matrix[j][0].multiply(d);
			for (int i = 1; i < acomplex[0].length; i++)
				acomplex[j][i] = matrix[j][i].multiply(d);

		}

		return new ComplexMatrix(acomplex);
	}

	public ComplexVector multiply(ComplexVector complexvector) {
		if (matrix[0].length == complexvector.dimension()) {
			Complex acomplex[] = new Complex[matrix.length];
			for (int j = 0; j < acomplex.length; j++) {
				acomplex[j] = matrix[j][0].multiply(complexvector.getComponent(0));
				for (int i = 1; i < matrix[0].length; i++)
					acomplex[j] = acomplex[j].add(matrix[j][i].multiply(complexvector.getComponent(i)));

			}

			return new ComplexVector(acomplex);
		} else {
			throw new DimensionException("Matrix and vector are incompatible.");
		}
	}

	public Matrix multiply(Matrix matrix1) {
		if (matrix1 instanceof ComplexMatrix)
			return multiply((ComplexMatrix)matrix1);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public ComplexMatrix multiply(ComplexMatrix complexmatrix) {
		if (matrix[0].length == complexmatrix.rows()) {
			Complex acomplex[][] = new Complex[matrix.length][complexmatrix.columns()];
			for (int k = 0; k < acomplex.length; k++) {
				for (int j = 0; j < acomplex[0].length; j++) {
					acomplex[k][j] = matrix[k][0].multiply(complexmatrix.getElement(0, j));
					for (int i = 1; i < matrix[0].length; i++)
						acomplex[k][j] = acomplex[k][j].add(matrix[k][i].multiply(complexmatrix.getElement(i, j)));

				}

			}

			if (acomplex.length == acomplex[0].length)
				return new ComplexSquareMatrix(acomplex);
			else
				return new ComplexMatrix(acomplex);
		} else {
			throw new MatrixDimensionException("Incompatible matrices.");
		}
	}

	public ComplexMatrix hermitianAdjoint() {
		Complex acomplex[][] = new Complex[matrix[0].length][matrix.length];
		for (int j = 0; j < acomplex.length; j++) {
			acomplex[0][j] = matrix[j][0].conjugate();
			for (int i = 1; i < acomplex[0].length; i++)
				acomplex[i][j] = matrix[j][i].conjugate();

		}

		return new ComplexMatrix(acomplex);
	}

	public ComplexMatrix conjugate() {
		Complex acomplex[][] = new Complex[matrix.length][matrix[0].length];
		for (int j = 0; j < acomplex.length; j++) {
			acomplex[j][0] = matrix[j][0].conjugate();
			for (int i = 1; i < acomplex[0].length; i++)
				acomplex[j][i] = matrix[j][i].conjugate();

		}

		return new ComplexMatrix(acomplex);
	}

	public Matrix transpose() {
		Complex acomplex[][] = new Complex[matrix[0].length][matrix.length];
		for (int j = 0; j < acomplex[0].length; j++) {
			acomplex[0][j] = matrix[j][0];
			for (int i = 1; i < acomplex.length; i++)
				acomplex[i][j] = matrix[j][i];

		}

		return new ComplexMatrix(acomplex);
	}

	public ComplexMatrix mapElements(Mapping mapping) {
		Complex acomplex[][] = new Complex[matrix.length][matrix[0].length];
		for (int j = 0; j < acomplex.length; j++) {
			acomplex[j][0] = mapping.map(matrix[j][0]);
			for (int i = 1; i < acomplex[0].length; i++)
				acomplex[j][i] = mapping.map(matrix[j][i]);

		}

		return new ComplexMatrix(acomplex);
	}
}
