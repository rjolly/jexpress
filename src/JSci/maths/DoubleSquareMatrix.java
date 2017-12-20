package JSci.maths;


// Referenced classes of package JSci.maths:
//			DoubleMatrix, DimensionException, DoubleDiagonalMatrix, DoubleVector, 
//			Mapping, MatrixDimensionException, Matrix

public class DoubleSquareMatrix extends DoubleMatrix {

	protected DoubleSquareMatrix() {
	}

	public DoubleSquareMatrix(int i) {
		super(i, i);
	}

	public DoubleSquareMatrix(double ad[][]) {
		super(ad);
		if (ad.length != ad[0].length) {
			super.matrix = null;
			throw new MatrixDimensionException("The array is not square.");
		} else {
			return;
		}
	}

	public DoubleSquareMatrix(DoubleVector adoublevector[]) {
		super(adoublevector);
		if (adoublevector.length != adoublevector[0].dimension()) {
			super.matrix = null;
			throw new MatrixDimensionException("The array does not form a square matrix.");
		} else {
			return;
		}
	}

	public boolean isSymmetric() {
		return equals(transpose());
	}

	public boolean isUnitary() {
		return multiply(transpose()).equals(DoubleDiagonalMatrix.identity(super.matrix[0].length));
	}

	public double det() {
		if (super.matrix.length == 2)
			return super.matrix[0][0] * super.matrix[1][1] - super.matrix[0][1] * super.matrix[1][0];
		DoubleSquareMatrix adoublesquarematrix[] = luDecompose();
		double d = ((DoubleMatrix) (adoublesquarematrix[1])).matrix[0][0];
		for (int i = 1; i < super.matrix.length; i++)
			d *= ((DoubleMatrix) (adoublesquarematrix[1])).matrix[i][i];

		return d;
	}

	public double trace() {
		double d = super.matrix[0][0];
		for (int i = 1; i < super.matrix.length; i++)
			d += super.matrix[i][i];

		return d;
	}

	public Matrix add(Matrix matrix) {
		if (matrix instanceof DoubleSquareMatrix)
			return add((DoubleSquareMatrix)matrix);
		if (matrix instanceof DoubleMatrix)
			return rawAdd((DoubleMatrix)matrix);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public DoubleMatrix add(DoubleMatrix doublematrix) {
		if (doublematrix instanceof DoubleSquareMatrix)
			return add((DoubleSquareMatrix)doublematrix);
		else
			return rawAdd(doublematrix);
	}

	private DoubleMatrix rawAdd(DoubleMatrix doublematrix) {
		if (super.matrix.length == doublematrix.rows() && super.matrix.length == doublematrix.columns()) {
			double ad[][] = new double[super.matrix.length][super.matrix.length];
			for (int j = 0; j < ad.length; j++) {
				ad[j][0] = super.matrix[j][0] + doublematrix.getElement(j, 0);
				for (int i = 1; i < ad.length; i++)
					ad[j][i] = super.matrix[j][i] + doublematrix.getElement(j, i);

			}

			return new DoubleSquareMatrix(ad);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public DoubleSquareMatrix add(DoubleSquareMatrix doublesquarematrix) {
		if (super.matrix.length == doublesquarematrix.rows()) {
			double ad[][] = new double[super.matrix.length][super.matrix.length];
			for (int j = 0; j < ad.length; j++) {
				ad[j][0] = super.matrix[j][0] + doublesquarematrix.getElement(j, 0);
				for (int i = 1; i < ad.length; i++)
					ad[j][i] = super.matrix[j][i] + doublesquarematrix.getElement(j, i);

			}

			return new DoubleSquareMatrix(ad);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public Matrix subtract(Matrix matrix) {
		if (matrix instanceof DoubleSquareMatrix)
			return subtract((DoubleSquareMatrix)matrix);
		if (matrix instanceof DoubleMatrix)
			return rawSubtract((DoubleMatrix)matrix);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public DoubleMatrix subtract(DoubleMatrix doublematrix) {
		if (doublematrix instanceof DoubleSquareMatrix)
			return subtract((DoubleSquareMatrix)doublematrix);
		else
			return rawSubtract(doublematrix);
	}

	private DoubleMatrix rawSubtract(DoubleMatrix doublematrix) {
		if (super.matrix.length == doublematrix.rows() && super.matrix.length == doublematrix.columns()) {
			double ad[][] = new double[super.matrix.length][super.matrix.length];
			for (int j = 0; j < ad.length; j++) {
				ad[j][0] = super.matrix[j][0] - doublematrix.getElement(j, 0);
				for (int i = 1; i < ad.length; i++)
					ad[j][i] = super.matrix[j][i] - doublematrix.getElement(j, i);

			}

			return new DoubleSquareMatrix(ad);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public DoubleSquareMatrix subtract(DoubleSquareMatrix doublesquarematrix) {
		if (super.matrix.length == doublesquarematrix.rows()) {
			double ad[][] = new double[super.matrix.length][super.matrix.length];
			for (int j = 0; j < ad.length; j++) {
				ad[j][0] = super.matrix[j][0] - doublesquarematrix.getElement(j, 0);
				for (int i = 1; i < ad.length; i++)
					ad[j][i] = super.matrix[j][i] - doublesquarematrix.getElement(j, i);

			}

			return new DoubleSquareMatrix(ad);
		} else {
			throw new MatrixDimensionException("Matrices are different sizes.");
		}
	}

	public DoubleMatrix scalarMultiply(double d) {
		double ad[][] = new double[super.matrix.length][super.matrix.length];
		for (int j = 0; j < ad.length; j++) {
			ad[j][0] = d * super.matrix[j][0];
			for (int i = 1; i < ad.length; i++)
				ad[j][i] = d * super.matrix[j][i];

		}

		return new DoubleSquareMatrix(ad);
	}

	public DoubleVector multiply(DoubleVector doublevector) {
		if (super.matrix.length == doublevector.dimension()) {
			double ad[] = new double[super.matrix.length];
			for (int j = 0; j < ad.length; j++) {
				ad[j] = super.matrix[j][0] * doublevector.getComponent(0);
				for (int i = 1; i < super.matrix.length; i++)
					ad[j] += super.matrix[j][i] * doublevector.getComponent(i);

			}

			return new DoubleVector(ad);
		} else {
			throw new DimensionException("Matrix and vector are incompatible.");
		}
	}

	public Matrix multiply(Matrix matrix) {
		if (matrix instanceof DoubleSquareMatrix)
			return multiply((DoubleSquareMatrix)matrix);
		if (matrix instanceof DoubleMatrix)
			return rawMultiply((DoubleMatrix)matrix);
		else
			throw new IllegalArgumentException("Matrix class not recognised by this method.");
	}

	public DoubleMatrix multiply(DoubleMatrix doublematrix) {
		if (doublematrix instanceof DoubleSquareMatrix)
			return multiply((DoubleSquareMatrix)doublematrix);
		else
			return rawMultiply(doublematrix);
	}

	private DoubleMatrix rawMultiply(DoubleMatrix doublematrix) {
		if (super.matrix[0].length == doublematrix.rows()) {
			double ad[][] = new double[super.matrix.length][doublematrix.columns()];
			for (int k = 0; k < ad.length; k++) {
				for (int j = 0; j < ad[0].length; j++) {
					ad[k][j] = super.matrix[k][0] * doublematrix.getElement(0, j);
					for (int i = 1; i < super.matrix[0].length; i++)
						ad[k][j] += super.matrix[k][i] * doublematrix.getElement(i, j);

				}

			}

			return new DoubleMatrix(ad);
		} else {
			throw new MatrixDimensionException("Incompatible matrices.");
		}
	}

	public DoubleSquareMatrix multiply(DoubleSquareMatrix doublesquarematrix) {
		if (super.matrix.length == doublesquarematrix.rows()) {
			double ad[][] = new double[super.matrix.length][super.matrix.length];
			for (int k = 0; k < ad.length; k++) {
				for (int j = 0; j < ad.length; j++) {
					ad[k][j] = super.matrix[k][0] * doublesquarematrix.getElement(0, j);
					for (int i = 1; i < ad.length; i++)
						ad[k][j] += super.matrix[k][i] * doublesquarematrix.getElement(i, j);

				}

			}

			return new DoubleSquareMatrix(ad);
		} else {
			throw new MatrixDimensionException("Incompatible matrices.");
		}
	}

	public Matrix transpose() {
		double ad[][] = new double[super.matrix.length][super.matrix.length];
		for (int j = 0; j < ad.length; j++) {
			ad[0][j] = super.matrix[j][0];
			for (int i = 1; i < ad.length; i++)
				ad[i][j] = super.matrix[j][i];

		}

		return new DoubleSquareMatrix(ad);
	}

	public DoubleSquareMatrix inverse() {
		double ad[][][] = new double[2][super.matrix.length][super.matrix.length];
		DoubleSquareMatrix adoublesquarematrix[] = luDecompose();
		ad[0][0][0] = 1.0D / ((DoubleMatrix) (adoublesquarematrix[0])).matrix[0][0];
		ad[1][0][0] = 1.0D / ((DoubleMatrix) (adoublesquarematrix[1])).matrix[0][0];
		for (int i = 1; i < super.matrix.length; i++) {
			ad[0][i][i] = 1.0D / ((DoubleMatrix) (adoublesquarematrix[0])).matrix[i][i];
			ad[1][i][i] = 1.0D / ((DoubleMatrix) (adoublesquarematrix[1])).matrix[i][i];
		}

		for (int j = 0; j < super.matrix.length - 1; j++) {
			for (int k = j + 1; k < super.matrix.length; k++) {
				double d1;
				double d = d1 = 0.0D;
				for (int l = j; l < k; l++) {
					d -= ((DoubleMatrix) (adoublesquarematrix[0])).matrix[k][l] * ad[0][l][j];
					d1 -= ad[1][j][l] * ((DoubleMatrix) (adoublesquarematrix[1])).matrix[l][k];
				}

				ad[0][k][j] = d / ((DoubleMatrix) (adoublesquarematrix[0])).matrix[k][k];
				ad[1][j][k] = d1 / ((DoubleMatrix) (adoublesquarematrix[1])).matrix[k][k];
			}

		}

		return (new DoubleSquareMatrix(ad[1])).multiply(new DoubleSquareMatrix(ad[0]));
	}

	public DoubleSquareMatrix[] luDecompose() {
		double ad[][][] = new double[2][super.matrix.length][super.matrix.length];
		ad[0][0][0] = 1.0D;
		for (int i = 1; i < super.matrix.length; i++)
			ad[0][i][i] = 1.0D;

		for (int l = 0; l < super.matrix.length; l++) {
			for (int j = 0; j <= l; j++) {
				double d = super.matrix[j][l];
				for (int i1 = 0; i1 < j; i1++)
					d -= ad[0][j][i1] * ad[1][i1][l];

				ad[1][j][l] = d;
			}

			for (int k = l + 1; k < super.matrix.length; k++) {
				double d1 = super.matrix[k][l];
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
		double ad[][][] = new double[2][super.matrix.length][super.matrix.length];
		ad[0][0][0] = ad[1][0][0] = Math.sqrt(super.matrix[0][0]);
		for (int i = 1; i < super.matrix.length; i++)
			ad[0][i][0] = ad[1][0][i] = super.matrix[i][0] / ad[0][0][0];

		for (int l = 1; l < super.matrix.length; l++) {
			double d = super.matrix[l][l];
			for (int j = 0; j < l; j++)
				d -= ad[0][l][j] * ad[0][l][j];

			ad[0][l][l] = ad[1][l][l] = Math.sqrt(d);
			for (int k = l + 1; k < super.matrix.length; k++) {
				double d1 = super.matrix[k][l];
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
		double ad[][] = new double[super.matrix.length][super.matrix.length];
		for (int j = 0; j < ad.length; j++) {
			ad[j][0] = mapping.map(super.matrix[j][0]);
			for (int i = 1; i < ad.length; i++)
				ad[j][i] = mapping.map(super.matrix[j][i]);

		}

		return new DoubleSquareMatrix(ad);
	}
}
