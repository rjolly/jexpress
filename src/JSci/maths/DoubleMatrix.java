package JSci.maths;

import JSci.GlobalSettings;

// Referenced classes of package JSci.maths:
//			Matrix, Complex, ComplexMatrix, DimensionException, 
//			DoubleSquareMatrix, DoubleVector, IntegerMatrix, Mapping, 
//			MathDouble, MathInteger, MatrixDimensionException, MathNumber

public class DoubleMatrix extends Matrix {

	protected double matrix[][];

	protected DoubleMatrix() {
	}

	public DoubleMatrix(int i, int j) {
		this();
		matrix = new double[i][j];
	}

	public DoubleMatrix(double ad[][]) {
		this();
		matrix = ad;
	}

	public DoubleMatrix(DoubleVector adoublevector[]) {
		this(adoublevector[0].dimension(), adoublevector.length);
		for (int j = 0; j < matrix.length; j++) {
			for (int i = 0; i < matrix[0].length; i++)
				matrix[j][i] = adoublevector[i].getComponent(j);

		}

	}

	protected void finalize() throws Throwable {
		matrix = null;
		super.finalize();
	}

	public boolean equals(Object obj) {
		if (obj != null && (obj instanceof DoubleMatrix) && matrix.length == ((DoubleMatrix)obj).rows() && matrix[0].length == ((DoubleMatrix)obj).columns()) {
			DoubleMatrix doublematrix = (DoubleMatrix)obj;
			for (int j = 0; j < matrix.length; j++) {
				for (int i = 0; i < matrix[0].length; i++)
					if (Math.abs(matrix[j][i] - doublematrix.getElement(j, i)) > GlobalSettings.ZERO_TOL)
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

	public IntegerMatrix toIntegerMatrix() {
		int ai[][] = new int[matrix.length][matrix[0].length];
		for (int j = 0; j < matrix.length; j++) {
			for (int i = 0; i < matrix[0].length; i++)
				ai[j][i] = Math.round((float)matrix[j][i]);

		}

		return new IntegerMatrix(ai);
	}

	public ComplexMatrix toComplexMatrix() {
		Complex acomplex[][] = new Complex[matrix.length][matrix[0].length];
		for (int j = 0; j < matrix.length; j++) {
			for (int i = 0; i < matrix[0].length; i++)
				acomplex[j][i] = new Complex(matrix[j][i], 0.0D);

		}

		return new ComplexMatrix(acomplex);
	}

	public double getElement(int i, int j) {
		if (i >= 0 && i < matrix.length && j >= 0 && j < matrix[0].length)
			return matrix[i][j];
		else
			throw new MatrixDimensionException("Invalid element.");
	}

	public void setElement(int i, int j, double d) {
		if (i >= 0 && i < matrix.length && j >= 0 && j < matrix[0].length) {
			matrix[i][j] = d;
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
				d1 += Math.abs(matrix[j][i]);

			if (d1 > d)
				d = d1;
		}

		return d;
	}

	public double frobeniusNorm() {
		double d = 0.0D;
		for (int j = 0; j < matrix.length; j++) {
			for (int i = 0; i < matrix[0].length; i++)
				d += matrix[j][i] * matrix[j][i];

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
		if (matrix1 instanceof DoubleMatrix)
			return add((DoubleMatrix)matrix1);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public DoubleMatrix add(DoubleMatrix doublematrix) {
		if (matrix.length == doublematrix.rows() && matrix[0].length == doublematrix.columns()) {
			double ad[][] = new double[matrix.length][matrix[0].length];
			for (int j = 0; j < ad.length; j++) {
				ad[j][0] = matrix[j][0] + doublematrix.getElement(j, 0);
				for (int i = 1; i < ad[0].length; i++)
					ad[j][i] = matrix[j][i] + doublematrix.getElement(j, i);

			}

			return new DoubleMatrix(ad);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public Matrix subtract(Matrix matrix1) {
		if (matrix1 instanceof DoubleMatrix)
			return subtract((DoubleMatrix)matrix1);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public DoubleMatrix subtract(DoubleMatrix doublematrix) {
		if (matrix.length == doublematrix.rows() && matrix[0].length == doublematrix.columns()) {
			double ad[][] = new double[matrix.length][matrix[0].length];
			for (int j = 0; j < ad.length; j++) {
				ad[j][0] = matrix[j][0] - doublematrix.getElement(j, 0);
				for (int i = 1; i < ad[0].length; i++)
					ad[j][i] = matrix[j][i] - doublematrix.getElement(j, i);

			}

			return new DoubleMatrix(ad);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public Matrix scalarMultiply(MathNumber mathnumber) {
		if (mathnumber instanceof MathDouble)
			return scalarMultiply(((MathDouble)mathnumber).value());
		if (mathnumber instanceof MathInteger)
			return scalarMultiply(((MathInteger)mathnumber).value());
		else
			throw new IllegalArgumentException("Number class not recognised by this method.");
	}

	public DoubleMatrix scalarMultiply(double d) {
		double ad[][] = new double[matrix.length][matrix[0].length];
		for (int j = 0; j < ad.length; j++) {
			ad[j][0] = d * matrix[j][0];
			for (int i = 1; i < ad[0].length; i++)
				ad[j][i] = d * matrix[j][i];

		}

		return new DoubleMatrix(ad);
	}

	public DoubleVector multiply(DoubleVector doublevector) {
		if (matrix[0].length == doublevector.dimension()) {
			double ad[] = new double[matrix.length];
			for (int j = 0; j < ad.length; j++) {
				ad[j] = matrix[j][0] * doublevector.getComponent(0);
				for (int i = 1; i < matrix[0].length; i++)
					ad[j] += matrix[j][i] * doublevector.getComponent(i);

			}

			return new DoubleVector(ad);
		} else {
			throw new DimensionException("Matrix and vector are incompatible.");
		}
	}

	public Matrix multiply(Matrix matrix1) {
		if (matrix1 instanceof DoubleMatrix)
			return multiply((DoubleMatrix)matrix1);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public DoubleMatrix multiply(DoubleMatrix doublematrix) {
		if (matrix[0].length == doublematrix.rows()) {
			double ad[][] = new double[matrix.length][doublematrix.columns()];
			for (int k = 0; k < ad.length; k++) {
				for (int j = 0; j < ad[0].length; j++) {
					ad[k][j] = matrix[k][0] * doublematrix.getElement(0, j);
					for (int i = 1; i < matrix[0].length; i++)
						ad[k][j] += matrix[k][i] * doublematrix.getElement(i, j);

				}

			}

			if (ad.length == ad[0].length)
				return new DoubleSquareMatrix(ad);
			else
				return new DoubleMatrix(ad);
		} else {
			throw new MatrixDimensionException("Incompatible matrices.");
		}
	}

	public Matrix transpose() {
		double ad[][] = new double[matrix[0].length][matrix.length];
		for (int j = 0; j < ad[0].length; j++) {
			ad[0][j] = matrix[j][0];
			for (int i = 1; i < ad.length; i++)
				ad[i][j] = matrix[j][i];

		}

		return new DoubleMatrix(ad);
	}

	public DoubleMatrix mapElements(Mapping mapping) {
		double ad[][] = new double[matrix.length][matrix[0].length];
		for (int j = 0; j < ad.length; j++) {
			ad[j][0] = mapping.map(matrix[j][0]);
			for (int i = 1; i < ad[0].length; i++)
				ad[j][i] = mapping.map(matrix[j][i]);

		}

		return new DoubleMatrix(ad);
	}
}
