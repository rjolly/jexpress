package Jama;

import Jama.util.Maths;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StreamTokenizer;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Vector;

// Referenced classes of package Jama:
//			CholeskyDecomposition, EigenvalueDecomposition, LUDecomposition, QRDecomposition, 
//			SingularValueDecomposition

public class Matrix
	implements Cloneable, Serializable {

	private double A[][];
	private int m;
	private int n;

	public Matrix(int i, int j) {
		m = i;
		n = j;
		A = new double[i][j];
	}

	public Matrix(int i, int j, double d) {
		m = i;
		n = j;
		A = new double[i][j];
		for (int k = 0; k < i; k++) {
			for (int l = 0; l < j; l++)
				A[k][l] = d;

		}

	}

	public Matrix(double ad[], int i) {
		m = i;
		n = i == 0 ? 0 : ad.length / i;
		if (i * n != ad.length)
			throw new IllegalArgumentException("Array length must be a multiple of m.");
		A = new double[i][n];
		for (int j = 0; j < i; j++) {
			for (int k = 0; k < n; k++)
				A[j][k] = ad[j + k * i];

		}

	}

	public Matrix(double ad[][]) {
		m = ad.length;
		n = ad[0].length;
		for (int i = 0; i < m; i++)
			if (ad[i].length != n)
				throw new IllegalArgumentException("All rows must have the same length.");

		A = ad;
	}

	public Matrix(double ad[][], int i, int j) {
		A = ad;
		m = i;
		n = j;
	}

	public Matrix arrayLeftDivide(Matrix matrix) {
		checkMatrixDimensions(matrix);
		Matrix matrix1 = new Matrix(m, n);
		double ad[][] = matrix1.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				ad[i][j] = matrix.A[i][j] / A[i][j];

		}

		return matrix1;
	}

	public Matrix arrayLeftDivideEquals(Matrix matrix) {
		checkMatrixDimensions(matrix);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				A[i][j] = matrix.A[i][j] / A[i][j];

		}

		return this;
	}

	public Matrix arrayRightDivide(Matrix matrix) {
		checkMatrixDimensions(matrix);
		Matrix matrix1 = new Matrix(m, n);
		double ad[][] = matrix1.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				ad[i][j] = A[i][j] / matrix.A[i][j];

		}

		return matrix1;
	}

	public Matrix arrayRightDivideEquals(Matrix matrix) {
		checkMatrixDimensions(matrix);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				A[i][j] = A[i][j] / matrix.A[i][j];

		}

		return this;
	}

	public Matrix arrayTimes(Matrix matrix) {
		checkMatrixDimensions(matrix);
		Matrix matrix1 = new Matrix(m, n);
		double ad[][] = matrix1.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				ad[i][j] = A[i][j] * matrix.A[i][j];

		}

		return matrix1;
	}

	public Matrix arrayTimesEquals(Matrix matrix) {
		checkMatrixDimensions(matrix);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				A[i][j] = A[i][j] * matrix.A[i][j];

		}

		return this;
	}

	private void checkMatrixDimensions(Matrix matrix) {
		if (matrix.m != m || matrix.n != n)
			throw new IllegalArgumentException("Matrix dimensions must agree.");
		else
			return;
	}

	public CholeskyDecomposition chol() {
		return new CholeskyDecomposition(this);
	}

	public Object clone() {
		return copy();
	}

	public double cond() {
		return (new SingularValueDecomposition(this)).cond();
	}

	public static Matrix constructWithCopy(double ad[][]) {
		int i = ad.length;
		int j = ad[0].length;
		Matrix matrix = new Matrix(i, j);
		double ad1[][] = matrix.getArray();
		for (int k = 0; k < i; k++) {
			if (ad[k].length != j)
				throw new IllegalArgumentException("All rows must have the same length.");
			for (int l = 0; l < j; l++)
				ad1[k][l] = ad[k][l];

		}

		return matrix;
	}

	public Matrix copy() {
		Matrix matrix = new Matrix(m, n);
		double ad[][] = matrix.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				ad[i][j] = A[i][j];

		}

		return matrix;
	}

	public double det() {
		return (new LUDecomposition(this)).det();
	}

	public EigenvalueDecomposition eig() {
		return new EigenvalueDecomposition(this);
	}

	public double get(int i, int j) {
		return A[i][j];
	}

	public double[][] getArray() {
		return A;
	}

	public double[][] getArrayCopy() {
		double ad[][] = new double[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				ad[i][j] = A[i][j];

		}

		return ad;
	}

	public int getColumnDimension() {
		return n;
	}

	public double[] getColumnPackedCopy() {
		double ad[] = new double[m * n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				ad[i + j * m] = A[i][j];

		}

		return ad;
	}

	public Matrix getMatrix(int i, int j, int k, int l) {
		Matrix matrix = new Matrix((j - i) + 1, (l - k) + 1);
		double ad[][] = matrix.getArray();
		try {
			for (int i1 = i; i1 <= j; i1++) {
				for (int j1 = k; j1 <= l; j1++)
					ad[i1 - i][j1 - k] = A[i1][j1];

			}

		}
		catch (ArrayIndexOutOfBoundsException _ex) {
			throw new ArrayIndexOutOfBoundsException("Submatrix indices");
		}
		return matrix;
	}

	public Matrix getMatrix(int i, int j, int ai[]) {
		Matrix matrix = new Matrix((j - i) + 1, ai.length);
		double ad[][] = matrix.getArray();
		try {
			for (int k = i; k <= j; k++) {
				for (int l = 0; l < ai.length; l++)
					ad[k - i][l] = A[k][ai[l]];

			}

		}
		catch (ArrayIndexOutOfBoundsException _ex) {
			throw new ArrayIndexOutOfBoundsException("Submatrix indices");
		}
		return matrix;
	}

	public Matrix getMatrix(int ai[], int i, int j) {
		Matrix matrix = new Matrix(ai.length, (j - i) + 1);
		double ad[][] = matrix.getArray();
		try {
			for (int k = 0; k < ai.length; k++) {
				for (int l = i; l <= j; l++)
					ad[k][l - i] = A[ai[k]][l];

			}

		}
		catch (ArrayIndexOutOfBoundsException _ex) {
			throw new ArrayIndexOutOfBoundsException("Submatrix indices");
		}
		return matrix;
	}

	public Matrix getMatrix(int ai[], int ai1[]) {
		Matrix matrix = new Matrix(ai.length, ai1.length);
		double ad[][] = matrix.getArray();
		try {
			for (int i = 0; i < ai.length; i++) {
				for (int j = 0; j < ai1.length; j++)
					ad[i][j] = A[ai[i]][ai1[j]];

			}

		}
		catch (ArrayIndexOutOfBoundsException _ex) {
			throw new ArrayIndexOutOfBoundsException("Submatrix indices");
		}
		return matrix;
	}

	public int getRowDimension() {
		return m;
	}

	public double[] getRowPackedCopy() {
		double ad[] = new double[m * n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				ad[i * n + j] = A[i][j];

		}

		return ad;
	}

	public static Matrix identity(int i, int j) {
		Matrix matrix = new Matrix(i, j);
		double ad[][] = matrix.getArray();
		for (int k = 0; k < i; k++) {
			for (int l = 0; l < j; l++)
				ad[k][l] = k != l ? 0.0D : 1.0D;

		}

		return matrix;
	}

	public Matrix inverse() {
		return solve(identity(m, m));
	}

	public LUDecomposition lu() {
		return new LUDecomposition(this);
	}

	public Matrix minus(Matrix matrix) {
		checkMatrixDimensions(matrix);
		Matrix matrix1 = new Matrix(m, n);
		double ad[][] = matrix1.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				ad[i][j] = A[i][j] - matrix.A[i][j];

		}

		return matrix1;
	}

	public Matrix minusEquals(Matrix matrix) {
		checkMatrixDimensions(matrix);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				A[i][j] = A[i][j] - matrix.A[i][j];

		}

		return this;
	}

	public double norm1() {
		double d = 0.0D;
		for (int i = 0; i < n; i++) {
			double d1 = 0.0D;
			for (int j = 0; j < m; j++)
				d1 += Math.abs(A[j][i]);

			d = Math.max(d, d1);
		}

		return d;
	}

	public double norm2() {
		return (new SingularValueDecomposition(this)).norm2();
	}

	public double normF() {
		double d = 0.0D;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				d = Maths.hypot(d, A[i][j]);

		}

		return d;
	}

	public double normInf() {
		double d = 0.0D;
		for (int i = 0; i < m; i++) {
			double d1 = 0.0D;
			for (int j = 0; j < n; j++)
				d1 += Math.abs(A[i][j]);

			d = Math.max(d, d1);
		}

		return d;
	}

	public Matrix plus(Matrix matrix) {
		checkMatrixDimensions(matrix);
		Matrix matrix1 = new Matrix(m, n);
		double ad[][] = matrix1.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				ad[i][j] = A[i][j] + matrix.A[i][j];

		}

		return matrix1;
	}

	public Matrix plusEquals(Matrix matrix) {
		checkMatrixDimensions(matrix);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				A[i][j] = A[i][j] + matrix.A[i][j];

		}

		return this;
	}

	public void print(int i, int j) {
		print(new PrintWriter(System.out, true), i, j);
	}

	public void print(PrintWriter printwriter, int i, int j) {
		DecimalFormat decimalformat = new DecimalFormat();
		decimalformat.setMinimumIntegerDigits(1);
		decimalformat.setMaximumFractionDigits(j);
		decimalformat.setMinimumFractionDigits(j);
		decimalformat.setGroupingUsed(false);
		print(printwriter, ((NumberFormat) (decimalformat)), i + 2);
	}

	public void print(PrintWriter printwriter, NumberFormat numberformat, int i) {
		printwriter.println();
		for (int j = 0; j < m; j++) {
			for (int k = 0; k < n; k++) {
				String s = numberformat.format(A[j][k]);
				int l = Math.max(1, i - s.length());
				for (int i1 = 0; i1 < l; i1++)
					printwriter.print(' ');

				printwriter.print(s);
			}

			printwriter.println();
		}

		printwriter.println();
	}

	public void print(NumberFormat numberformat, int i) {
		print(new PrintWriter(System.out, true), numberformat, i);
	}

	public QRDecomposition qr() {
		return new QRDecomposition(this);
	}

	public static Matrix random(int i, int j) {
		Matrix matrix = new Matrix(i, j);
		double ad[][] = matrix.getArray();
		for (int k = 0; k < i; k++) {
			for (int l = 0; l < j; l++)
				ad[k][l] = Math.random();

		}

		return matrix;
	}

	public int rank() {
		return (new SingularValueDecomposition(this)).rank();
	}

	public static Matrix read(BufferedReader bufferedreader) throws IOException {
		StreamTokenizer streamtokenizer = new StreamTokenizer(bufferedreader);
		streamtokenizer.resetSyntax();
		streamtokenizer.wordChars(0, 255);
		streamtokenizer.whitespaceChars(0, 32);
		streamtokenizer.eolIsSignificant(true);
		Vector vector = new Vector();
		while (streamtokenizer.nextToken() == 10) ;
		if (streamtokenizer.ttype == -1)
			throw new IOException("Unexpected EOF on matrix read.");
		do
			vector.addElement(Double.valueOf(streamtokenizer.sval));
		while (streamtokenizer.nextToken() == -3);
		int i = vector.size();
		double ad[] = new double[i];
		for (int j = 0; j < i; j++)
			ad[j] = ((Double)vector.elementAt(j)).doubleValue();

		vector.removeAllElements();
		vector.addElement(ad);
		while (streamtokenizer.nextToken() == -3)  {
			double ad1[];
			vector.addElement(ad1 = new double[i]);
			int k = 0;
			do {
				if (k >= i)
					throw new IOException("Row " + vector.size() + " is too long.");
				ad1[k++] = Double.valueOf(streamtokenizer.sval).doubleValue();
			} while (streamtokenizer.nextToken() == -3);
			if (k < i)
				throw new IOException("Row " + vector.size() + " is too short.");
		}
		int l = vector.size();
		double ad2[][] = new double[l][];
		vector.copyInto(ad2);
		return new Matrix(ad2);
	}

	public void set(int i, int j, double d) {
		A[i][j] = d;
	}

	public void setMatrix(int i, int j, int k, int l, Matrix matrix) {
		try {
			for (int i1 = i; i1 <= j; i1++) {
				for (int j1 = k; j1 <= l; j1++)
					A[i1][j1] = matrix.get(i1 - i, j1 - k);

			}

		}
		catch (ArrayIndexOutOfBoundsException _ex) {
			throw new ArrayIndexOutOfBoundsException("Submatrix indices");
		}
	}

	public void setMatrix(int i, int j, int ai[], Matrix matrix) {
		try {
			for (int k = i; k <= j; k++) {
				for (int l = 0; l < ai.length; l++)
					A[k][ai[l]] = matrix.get(k - i, l);

			}

		}
		catch (ArrayIndexOutOfBoundsException _ex) {
			throw new ArrayIndexOutOfBoundsException("Submatrix indices");
		}
	}

	public void setMatrix(int ai[], int i, int j, Matrix matrix) {
		try {
			for (int k = 0; k < ai.length; k++) {
				for (int l = i; l <= j; l++)
					A[ai[k]][l] = matrix.get(k, l - i);

			}

		}
		catch (ArrayIndexOutOfBoundsException _ex) {
			throw new ArrayIndexOutOfBoundsException("Submatrix indices");
		}
	}

	public void setMatrix(int ai[], int ai1[], Matrix matrix) {
		try {
			for (int i = 0; i < ai.length; i++) {
				for (int j = 0; j < ai1.length; j++)
					A[ai[i]][ai1[j]] = matrix.get(i, j);

			}

		}
		catch (ArrayIndexOutOfBoundsException _ex) {
			throw new ArrayIndexOutOfBoundsException("Submatrix indices");
		}
	}

	public Matrix solve(Matrix matrix) {
		return m != n ? (new QRDecomposition(this)).solve(matrix) : (new LUDecomposition(this)).solve(matrix);
	}

	public Matrix solveTranspose(Matrix matrix) {
		return transpose().solve(matrix.transpose());
	}

	public SingularValueDecomposition svd() {
		return new SingularValueDecomposition(this);
	}

	public Matrix times(double d) {
		Matrix matrix = new Matrix(m, n);
		double ad[][] = matrix.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				ad[i][j] = d * A[i][j];

		}

		return matrix;
	}

	public Matrix times(Matrix matrix) {
		if (matrix.m != n)
			throw new IllegalArgumentException("Matrix inner dimensions must agree.");
		Matrix matrix1 = new Matrix(m, matrix.n);
		double ad[][] = matrix1.getArray();
		double ad1[] = new double[n];
		for (int i = 0; i < matrix.n; i++) {
			for (int j = 0; j < n; j++)
				ad1[j] = matrix.A[j][i];

			for (int k = 0; k < m; k++) {
				double ad2[] = A[k];
				double d = 0.0D;
				for (int l = 0; l < n; l++)
					d += ad2[l] * ad1[l];

				ad[k][i] = d;
			}

		}

		return matrix1;
	}

	public Matrix timesEquals(double d) {
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				A[i][j] = d * A[i][j];

		}

		return this;
	}

	public double trace() {
		double d = 0.0D;
		for (int i = 0; i < Math.min(m, n); i++)
			d += A[i][i];

		return d;
	}

	public Matrix transpose() {
		Matrix matrix = new Matrix(n, m);
		double ad[][] = matrix.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				ad[j][i] = A[i][j];

		}

		return matrix;
	}

	public Matrix uminus() {
		Matrix matrix = new Matrix(m, n);
		double ad[][] = matrix.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				ad[i][j] = -A[i][j];

		}

		return matrix;
	}
}
