package JSci.maths;

import JSci.GlobalSettings;
import java.io.PrintStream;

// Referenced classes of package JSci.maths:
//			AbstractMath, Complex, Mapping

public final class ArrayMath extends AbstractMath {

	private ArrayMath() {
	}

	public static double[] apply(Mapping mapping, double ad[]) {
		double ad1[] = new double[ad.length];
		for (int i = 0; i < ad.length; i++)
			ad1[i] = mapping.map(ad[i]);

		return ad1;
	}

	public static double[][] apply(Mapping mapping, double ad[][]) {
		double ad1[][] = new double[ad.length][];
		for (int i = 0; i < ad.length; i++)
			ad1[i] = apply(mapping, ad[i]);

		return ad1;
	}

	public static Complex[] apply(Mapping mapping, Complex acomplex[]) {
		Complex acomplex1[] = new Complex[acomplex.length];
		for (int i = 0; i < acomplex.length; i++)
			acomplex1[i] = mapping.map(acomplex[i]);

		return acomplex1;
	}

	public static Complex[][] apply(Mapping mapping, Complex acomplex[][]) {
		Complex acomplex1[][] = new Complex[acomplex.length][];
		for (int i = 0; i < acomplex.length; i++)
			acomplex1[i] = apply(mapping, acomplex[i]);

		return acomplex1;
	}

	public static double[] normalize(double ad[]) {
		return scalarMultiply(1.0D / norm(ad), ad);
	}

	public static double[] setLengthFromEnd(double ad[], int i) {
		double ad1[] = new double[i];
		int j;
		if (i - ad.length < 0)
			j = ad.length - i;
		else
			j = 0;
		System.arraycopy(ad, j, ad1, -ad.length + i + j, ad.length - j);
		return ad1;
	}

	public static double[] setLengthFromBeginning(double ad[], int i) {
		double ad1[] = new double[i];
		int j;
		if (i - ad.length < 0)
			j = ad.length - i;
		else
			j = 0;
		System.arraycopy(ad, 0, ad1, 0, ad.length - j);
		return ad1;
	}

	public static double[] copy(double ad[]) {
		double ad1[] = new double[ad.length];
		System.arraycopy(ad, 0, ad1, 0, ad.length);
		return ad1;
	}

	public static double[][] copy(double ad[][]) {
		double ad1[][] = new double[ad.length][];
		for (int i = 0; i < ad.length; i++)
			ad1[i] = copy(ad[i]);

		return ad1;
	}

	public static double variance(double ad[]) {
		double d = mean(ad);
		double d1 = sumSquares(ad) / (double)ad.length - d * d;
		return d1;
	}

	public static double covariance(double ad[], double ad1[]) {
		double d = mean(ad);
		double d1 = mean(ad1);
		double d2 = scalarProduct(ad, ad1) / (double)ad.length - d * d1;
		return d2;
	}

	public static double correlation(double ad[], double ad1[]) {
		double d = Math.sqrt(variance(ad) * variance(ad1));
		if (d != 0.0D)
			return covariance(ad, ad1) / d;
		return variance(ad) != 0.0D || variance(ad1) != 0.0D ? 0.0D : 1.0D;
	}

	public static double mean(double ad[]) {
		if (ad.length == 0)
			throw new IllegalArgumentException("Nothing to compute! The array must have at least one element.");
		else
			return mass(ad) / (double)ad.length;
	}

	public static double standardDeviation(double ad[]) {
		return Math.sqrt(variance(ad));
	}

	public static double[] sortMinToMax(double ad[]) {
		double ad1[] = copy(ad);
		QuickSortMinToMax(ad1, 0, ad1.length - 1);
		return ad1;
	}

	public static double[] sortMaxToMin(double ad[]) {
		double ad1[] = copy(ad);
		QuickSortMaxToMin(ad1, 0, ad1.length - 1);
		return ad1;
	}

	public static double percentile(double ad[], double d) {
		if (d < 0.0D || d > 1.0D)
			throw new IllegalArgumentException("Percentile must be between 0 and 1 : " + d);
		double ad1[] = sortMinToMax(ad);
		int i = (int)Math.floor(d * (double)(ad1.length - 1));
		double d1 = d * (double)(ad1.length - 1) - Math.floor(d * (double)(ad1.length - 1));
		if (i == ad1.length - 1)
			return ad1[ad1.length - 1];
		else
			return ad1[i] * d1 + ad1[i + 1] * (1.0D - d1);
	}

	public static double median(double ad[]) {
		return percentile(ad, 0.5D);
	}

	public static boolean equals(double ad[], double ad1[]) {
		if (ad.length != ad1.length)
			return false;
		for (int i = 0; i < ad.length; i++)
			if (Math.abs(ad[i] - ad1[i]) > GlobalSettings.ZERO_TOL)
				return false;

		return true;
	}

	public static double[] abs(double ad[]) {
		double ad1[] = new double[ad.length];
		for (int i = 0; i < ad.length; i++)
			ad1[i] = Math.abs(ad[i]);

		return ad1;
	}

	public static double[][] abs(double ad[][]) {
		double ad1[][] = new double[ad.length][];
		for (int i = 0; i < ad.length; i++)
			ad1[i] = abs(ad[i]);

		return ad1;
	}

	public static double max(double ad[]) {
		double d = ad[0];
		for (int i = 1; i < ad.length; i++)
			if (d < ad[i])
				d = ad[i];

		return d;
	}

	public static double max(double ad[][]) {
		double d = max(ad[0]);
		for (int i = 1; i < ad.length; i++)
			if (d < max(ad[i]))
				d = max(ad[i]);

		return d;
	}

	public static double min(double ad[]) {
		double d = ad[0];
		for (int i = 1; i < ad.length; i++)
			if (d > ad[i])
				d = ad[i];

		return d;
	}

	public static double min(double ad[][]) {
		double d = min(ad[0]);
		for (int i = 1; i < ad.length; i++)
			if (d > min(ad[i]))
				d = min(ad[i]);

		return d;
	}

	public static double[] mod(Complex acomplex[]) {
		double ad[] = new double[acomplex.length];
		for (int i = 0; i < acomplex.length; i++)
			ad[i] = acomplex[i].mod();

		return ad;
	}

	public static double[][] mod(Complex acomplex[][]) {
		double ad[][] = new double[acomplex.length][];
		for (int i = 0; i < acomplex.length; i++)
			ad[i] = mod(acomplex[i]);

		return ad;
	}

	public static double norm(double ad[]) {
		return Math.sqrt(sumSquares(ad));
	}

	public static double sumSquares(double ad[]) {
		double d = 0.0D;
		for (int i = 0; i < ad.length; i++)
			d += ad[i] * ad[i];

		return d;
	}

	public static double sumSquares(double ad[][]) {
		double d = 0.0D;
		for (int i = 0; i < ad.length; i++) {
			for (int j = 0; j < ad[i].length; j++)
				d += ad[i][j] * ad[i][j];

		}

		return d;
	}

	public static double scalarProduct(double ad[], double ad1[]) {
		if (ad.length != ad1.length)
			throw new IllegalArgumentException("Arrays must have the same length : " + ad.length + ", " + ad1.length);
		if (ad.length == 0)
			throw new IllegalArgumentException("Nothing to compute! Arrays must have at least one element.");
		double d = 0.0D;
		for (int i = 0; i < ad.length; i++)
			d += ad[i] * ad1[i];

		return d;
	}

	public static double scalarProduct(double ad[][], double ad1[][]) {
		if (ad.length != ad1.length)
			throw new IllegalArgumentException("Arrays must have the same length : " + ad.length + ", " + ad1.length);
		if (ad.length == 0)
			throw new IllegalArgumentException("Nothing to compute! Arrays must have at least one element.");
		double d = 0.0D;
		for (int i = 0; i < ad.length; i++)
			d += scalarProduct(ad[i], ad1[i]);

		return d;
	}

	public static double[] extract(int i, int j, double ad[]) {
		if (i < 0 || j < 0 || i > ad.length - 1 || j > ad.length - 1)
			throw new IllegalArgumentException("The parameters are incorrect : " + i + ", " + j + ", " + ad.length);
		if (j > i) {
			double ad1[] = new double[(j - i) + 1];
			System.arraycopy(ad, i, ad1, 0, (j - i) + 1);
			return ad1;
		}
		double ad2[] = new double[-j + i + 1];
		for (int k = j; k <= i; k++)
			ad2[i - k] = ad[k];

		return ad2;
	}

	public static double[] invert(double ad[]) {
		double ad1[] = new double[ad.length];
		for (int i = 0; i < ad.length; i++)
			ad1[ad.length - 1 - i] = ad[i];

		return ad1;
	}

	public static double[] padding(int i, int j, double ad[]) {
		if (ad.length + j > i || j < 0) {
			throw new IllegalArgumentException("Array is to long for this : " + i + ", " + j + ", " + ad.length);
		} else {
			double ad1[] = new double[i];
			System.arraycopy(ad, 0, ad1, j, ad.length);
			return ad1;
		}
	}

	public static double[] add(double ad[], double d, double ad1[], int i) {
		if (ad1.length > ad.length)
			throw new IllegalArgumentException("Second array must be shorter or equal to the first one : " + ad.length + ", " + ad1.length);
		double ad2[] = copy(ad);
		for (int j = i; j < i + ad1.length; j++)
			ad2[j] += d * ad1[j - i];

		return ad2;
	}

	public static double[] add(double ad[], double d) {
		double ad1[] = copy(ad);
		for (int i = 0; i < ad1.length; i++)
			ad1[i] += d;

		return ad1;
	}

	public static double[][] transpose(double ad[][]) {
		double ad1[][] = new double[ad[0].length][ad.length];
		for (int i = 0; i < ad.length; i++) {
			if (ad[i].length != ad[0].length)
				throw new IllegalArgumentException("The array is not a matrix.");
			for (int j = 0; j < ad[0].length; j++)
				ad1[j][i] = ad[i][j];

		}

		return ad1;
	}

	public static double[] range(double d, double d1, double d2) {
		if (d2 <= 0.0D)
			throw new IllegalArgumentException("The argument step should be positive: " + d2 + " < 0");
		if (d == d1) {
			double ad[] = new double[1];
			ad[0] = d;
			return ad;
		}
		int i = (new Double(Math.abs(d - d1) / d2)).intValue() + 1;
		double ad1[] = new double[i];
		ad1[0] = d;
		if (d > d1)
			d2 *= -1D;
		for (int j = 1; j < i; j++)
			ad1[j] = ad1[j - 1] + d2;

		return ad1;
	}

	public static double[] range(double d, double d1) {
		return range(d, d1, 1.0D);
	}

	public static double[] range(double d) {
		return range(0.0D, d);
	}

	public static int[] toIntArray(double ad[]) {
		int ai[] = new int[ad.length];
		for (int i = 0; i < ad.length; i++)
			ai[i] = (new Double(ad[i])).intValue();

		return ai;
	}

	public static int[][] toIntArray(double ad[][]) {
		int ai[][] = new int[ad.length][];
		for (int i = 0; i < ad.length; i++)
			ai[i] = toIntArray(ad[i]);

		return ai;
	}

	public static double[] add(double ad[], double ad1[]) {
		if (ad.length != ad1.length)
			throw new IllegalArgumentException("To add two arrays, they must have the same length : " + ad.length + ", " + ad1.length);
		double ad2[] = copy(ad);
		for (int i = 0; i < ad.length; i++)
			ad2[i] += ad1[i];

		return ad2;
	}

	public static double[] subtract(double ad[], double ad1[]) {
		if (ad.length != ad1.length)
			throw new IllegalArgumentException("To add two arrays, they must have the same length : " + ad.length + ", " + ad1.length);
		double ad2[] = copy(ad);
		for (int i = 0; i < ad.length; i++)
			ad2[i] -= ad1[i];

		return ad2;
	}

	public static double[] scalarMultiply(double d, double ad[]) {
		double ad1[] = new double[ad.length];
		for (int i = 0; i < ad.length; i++)
			ad1[i] = d * ad[i];

		return ad1;
	}

	public static String toString(double ad[]) {
		StringBuffer stringbuffer = new StringBuffer(ad.length);
		int i;
		for (i = 0; i < ad.length - 1; i++) {
			stringbuffer.append(ad[i]);
			stringbuffer.append(',');
		}

		stringbuffer.append(ad[i]);
		return stringbuffer.toString();
	}

	public static String toString(double ad[][]) {
		StringBuffer stringbuffer = new StringBuffer();
		for (int i = 0; i < ad.length; i++) {
			stringbuffer.append(toString(ad[i]));
			stringbuffer.append(System.getProperty("line.separator"));
		}

		return stringbuffer.toString();
	}

	public static double mass(double ad[]) {
		double d = 0.0D;
		for (int i = 0; i < ad.length; i++)
			d += ad[i];

		return d;
	}

	public static double[] scalarMultiplyFast(double d, double ad[]) {
		if (d == 0.0D)
			return new double[ad.length];
		double ad1[] = new double[ad.length];
		for (int i = 0; i < ad.length; i++)
			if (d != 0.0D && ad[i] != 0.0D)
				ad1[i] = ad[i] * d;
			else
				ad1[i] = 0.0D;

		return ad1;
	}

	public static void print(double ad[]) {
		for (int i = 0; i < ad.length; i++)
			System.out.println("array [" + i + "] = " + ad[i]);

	}

	public static void print(double ad[][]) {
		for (int i = 0; i < ad.length; i++) {
			for (int j = 0; j < ad[i].length; j++)
				System.out.println("array [" + i + "][" + j + "] = " + ad[i][j]);

		}

	}

	public static int[] setLengthFromEnd(int ai[], int i) {
		int ai1[] = new int[i];
		int j;
		if (i - ai.length < 0)
			j = ai.length - i;
		else
			j = 0;
		System.arraycopy(ai, j, ai1, -ai.length + i + j, ai.length - j);
		return ai1;
	}

	public static int[] setLengthFromBeginning(int ai[], int i) {
		int ai1[] = new int[i];
		int j;
		if (i - ai.length < 0)
			j = ai.length - i;
		else
			j = 0;
		System.arraycopy(ai, 0, ai1, 0, ai.length - j);
		return ai1;
	}

	public static int[] copy(int ai[]) {
		int ai1[] = new int[ai.length];
		System.arraycopy(ai, 0, ai1, 0, ai.length);
		return ai1;
	}

	public static int[][] copy(int ai[][]) {
		int ai1[][] = new int[ai.length][];
		for (int i = 0; i < ai.length; i++)
			ai1[i] = copy(ai[i]);

		return ai1;
	}

	public static double variance(int ai[]) {
		double d = mean(ai);
		double d1 = (double)sumSquares(ai) / (double)ai.length - d * d;
		return d1;
	}

	public static double covariance(int ai[], int ai1[]) {
		double d = mean(ai);
		double d1 = mean(ai1);
		double d2 = (double)scalarProduct(ai, ai1) / (double)ai.length - d * d1;
		return d2;
	}

	public static double correlation(int ai[], int ai1[]) {
		double d = Math.sqrt(variance(ai) * variance(ai1));
		if (d != 0.0D)
			return covariance(ai, ai1) / d;
		return variance(ai) != 0.0D || variance(ai1) != 0.0D ? 0.0D : 1.0D;
	}

	public static double mean(int ai[]) {
		if (ai.length == 0)
			throw new IllegalArgumentException("Nothing to compute! The array must have at least one element.");
		else
			return (double)mass(ai) / (double)ai.length;
	}

	public static double standardDeviation(int ai[]) {
		return Math.sqrt(variance(ai));
	}

	public static int[] sortMinToMax(int ai[]) {
		int ai1[] = copy(ai);
		QuickSortMinToMax(ai1, 0, ai1.length - 1);
		return ai1;
	}

	public static int[] sortMaxToMin(int ai[]) {
		int ai1[] = copy(ai);
		QuickSortMaxToMin(ai1, 0, ai1.length - 1);
		return ai1;
	}

	public static String toString(int ai[]) {
		StringBuffer stringbuffer = new StringBuffer(ai.length);
		int i;
		for (i = 0; i < ai.length - 1; i++) {
			stringbuffer.append(ai[i]);
			stringbuffer.append(',');
		}

		stringbuffer.append(ai[i]);
		return stringbuffer.toString();
	}

	public static String toString(int ai[][]) {
		StringBuffer stringbuffer = new StringBuffer();
		for (int i = 0; i < ai.length; i++) {
			stringbuffer.append(toString(ai[i]));
			stringbuffer.append(System.getProperty("line.separator"));
		}

		return stringbuffer.toString();
	}

	public static double percentile(int ai[], double d) {
		if (d < 0.0D || d > 1.0D)
			throw new IllegalArgumentException("Percentile must be between 0 and 1 : " + d);
		int ai1[] = sortMinToMax(ai);
		int i = (int)Math.floor(d * (double)(ai1.length - 1));
		double d1 = d * (double)(ai1.length - 1) - Math.floor(d * (double)(ai1.length - 1));
		if (i == ai1.length - 1)
			return (double)ai1[ai1.length - 1];
		else
			return (double)ai1[i] * d1 + (double)ai1[i + 1] * (1.0D - d1);
	}

	public static double median(int ai[]) {
		return percentile(ai, 0.5D);
	}

	public static boolean equals(int ai[], int ai1[]) {
		if (ai.length != ai1.length)
			return false;
		for (int i = 0; i < ai.length; i++)
			if (ai[i] != ai1[i])
				return false;

		return true;
	}

	public static int[] abs(int ai[]) {
		int ai1[] = new int[ai.length];
		for (int i = 0; i < ai.length; i++)
			ai1[i] = Math.abs(ai[i]);

		return ai1;
	}

	public static int[][] abs(int ai[][]) {
		int ai1[][] = new int[ai.length][];
		for (int i = 0; i < ai.length; i++)
			ai1[i] = abs(ai[i]);

		return ai1;
	}

	public static int max(int ai[]) {
		int i = ai[0];
		for (int j = 1; j < ai.length; j++)
			if (i < ai[j])
				i = ai[j];

		return i;
	}

	public static int max(int ai[][]) {
		int i = max(ai[0]);
		for (int j = 1; j < ai.length; j++)
			if (i < max(ai[j]))
				i = max(ai[j]);

		return i;
	}

	public static int min(int ai[]) {
		int i = ai[0];
		for (int j = 1; j < ai.length; j++)
			if (i > ai[j])
				i = ai[j];

		return i;
	}

	public static int min(int ai[][]) {
		int i = min(ai[0]);
		for (int j = 1; j < ai.length; j++)
			if (i > min(ai[j]))
				i = min(ai[j]);

		return i;
	}

	public static double norm(int ai[]) {
		return Math.sqrt(sumSquares(ai));
	}

	public static int sumSquares(int ai[]) {
		int i = 0;
		for (int j = 0; j < ai.length; j++)
			i += ai[j] * ai[j];

		return i;
	}

	public static int sumSquares(int ai[][]) {
		int i = 0;
		for (int j = 0; j < ai.length; j++) {
			for (int k = 0; k < ai[j].length; k++)
				i += ai[j][k] * ai[j][k];

		}

		return i;
	}

	public static int scalarProduct(int ai[], int ai1[]) {
		if (ai.length != ai1.length)
			throw new IllegalArgumentException("Arrays must have the same length : " + ai.length + ", " + ai1.length);
		if (ai.length == 0)
			throw new IllegalArgumentException("Nothing to compute! Arrays must have at least one element.");
		int i = 0;
		for (int j = 0; j < ai.length; j++)
			i += ai[j] * ai1[j];

		return i;
	}

	public static double scalarProduct(int ai[][], int ai1[][]) {
		if (ai.length != ai1.length)
			throw new IllegalArgumentException("Arrays must have the same length : " + ai.length + ", " + ai1.length);
		if (ai.length == 0)
			throw new IllegalArgumentException("Nothing to compute! Arrays must have at least one element.");
		double d = 0.0D;
		for (int i = 0; i < ai.length; i++)
			d += scalarProduct(ai[i], ai1[i]);

		return d;
	}

	public static int[] extract(int i, int j, int ai[]) {
		if (i < 0 || j < 0 || i > ai.length - 1 || j > ai.length - 1)
			throw new IllegalArgumentException("The parameters are incorrect : " + i + ", " + j + ", " + ai.length);
		if (j > i) {
			int ai1[] = new int[(j - i) + 1];
			System.arraycopy(ai, i, ai1, 0, (j - i) + 1);
			return ai1;
		}
		int ai2[] = new int[-j + i + 1];
		for (int k = j; k <= i; k++)
			ai2[i - k] = ai[k];

		return ai2;
	}

	public static int[] invert(int ai[]) {
		int ai1[] = new int[ai.length];
		for (int i = 0; i < ai.length; i++)
			ai1[ai.length - 1 - i] = ai[i];

		return ai1;
	}

	public static int[] padding(int i, int j, int ai[]) {
		if (ai.length + j > i || j < 0) {
			throw new IllegalArgumentException("The array is too long for this : " + i + ", " + j + ", " + ai.length);
		} else {
			int ai1[] = new int[i];
			System.arraycopy(ai, 0, ai1, j, ai.length);
			return ai1;
		}
	}

	public static int[] add(int ai[], double d, int ai1[], int i) {
		if (ai1.length > ai.length)
			throw new IllegalArgumentException("Second array must be shorter or equal to the first one : " + ai.length + ", " + ai1.length);
		int ai2[] = copy(ai);
		for (int j = i; j < i + ai1.length; j++)
			ai2[j] += d * (double)ai1[j - i];

		return ai2;
	}

	public static int[] add(int ai[], int i) {
		int ai1[] = copy(ai);
		for (int j = 0; j < ai1.length; j++)
			ai1[j] += i;

		return ai1;
	}

	public static int[] range(int i, int j) {
		return range(i, j, 1);
	}

	public static int[] range(int i) {
		return range(0, i);
	}

	public static int[] range(int i, int j, int k) {
		if (k <= 0)
			throw new IllegalArgumentException("The argument step should be positive: " + k + " < 0");
		if (i == j) {
			int ai[] = new int[1];
			ai[0] = i;
			return ai;
		}
		int l = (new Double(Math.abs(i - j) / k)).intValue();
		int ai1[] = new int[l];
		ai1[0] = i;
		if (i > j)
			k *= -1;
		for (int i1 = 1; i1 < l; i1++)
			ai1[i1] = ai1[i1 - 1] + k;

		return ai1;
	}

	public static int[][] transpose(int ai[][]) {
		int ai1[][] = new int[ai[0].length][ai.length];
		for (int i = 0; i < ai.length; i++) {
			if (ai[i].length != ai[0].length)
				throw new IllegalArgumentException("The array is not a matrix.");
			for (int j = 0; j < ai[0].length; j++)
				ai1[j][i] = ai[i][j];

		}

		return ai1;
	}

	public static double[] toDoubleArray(int ai[]) {
		double ad[] = new double[ai.length];
		for (int i = 0; i < ai.length; i++)
			ad[i] = ai[i];

		return ad;
	}

	public static double[][] toDoubleArray(int ai[][]) {
		double ad[][] = new double[ai.length][];
		for (int i = 0; i < ai.length; i++)
			ad[i] = toDoubleArray(ai[i]);

		return ad;
	}

	public static int[] add(int ai[], int ai1[]) {
		if (ai.length != ai1.length)
			throw new IllegalArgumentException("To add two arrays, they must have the same length : " + ai.length + ", " + ai1.length);
		int ai2[] = copy(ai);
		for (int i = 0; i < ai.length; i++)
			ai2[i] += ai1[i];

		return ai2;
	}

	public static int[] subtract(int ai[], int ai1[]) {
		if (ai.length != ai1.length)
			throw new IllegalArgumentException("To add two arrays, they must have the same length : " + ai.length + ", " + ai1.length);
		int ai2[] = copy(ai);
		for (int i = 0; i < ai.length; i++)
			ai2[i] -= ai1[i];

		return ai2;
	}

	public static int mass(int ai[]) {
		int i = 0;
		for (int j = 0; j < ai.length; j++)
			i += ai[j];

		return i;
	}

	public static double[] scalarMultiply(double d, int ai[]) {
		double ad[] = new double[ai.length];
		for (int i = 0; i < ai.length; i++)
			ad[i] = (double)ai[i] * d;

		return ad;
	}

	public static double[] scalarMultiplyFast(double d, int ai[]) {
		if (d == 0.0D)
			return new double[ai.length];
		double ad[] = new double[ai.length];
		for (int i = 0; i < ai.length; i++)
			if (d != 0.0D && ai[i] != 0)
				ad[i] = (double)ai[i] * d;
			else
				ad[i] = 0.0D;

		return ad;
	}

	public static void print(int ai[]) {
		for (int i = 0; i < ai.length; i++)
			System.out.println("array [" + i + "] = " + ai[i]);

	}

	public static void print(int ai[][]) {
		for (int i = 0; i < ai.length; i++) {
			for (int j = 0; j < ai[i].length; j++)
				System.out.println("array [" + i + "][" + j + "] = " + ai[i][j]);

		}

	}

	private static void QuickSortMinToMax(int ai[], int i, int j) {
		int k = i;
		int l = j;
		if (j > i) {
			int i1 = ai[(int)Math.round((double)(i + j) / 2D)];
			while (k <= l)  {
				while (k < j && ai[k] < i1) 
					k++;
				for (; l > i && ai[l] > i1; l--);
				if (k <= l) {
					swap(ai, k, l);
					k++;
					l--;
				}
			}
			if (i < l)
				QuickSortMinToMax(ai, i, l);
			if (k < j)
				QuickSortMinToMax(ai, k, j);
		}
	}

	private static void QuickSortMaxToMin(int ai[], int i, int j) {
		int k = i;
		int l = j;
		if (j > i) {
			int i1 = ai[(int)Math.round((double)(i + j) / 2D)];
			while (k <= l)  {
				while (k < j && ai[k] > i1) 
					k++;
				for (; l > i && ai[l] < i1; l--);
				if (k <= l) {
					swap(ai, k, l);
					k++;
					l--;
				}
			}
			if (i < l)
				QuickSortMaxToMin(ai, i, l);
			if (k < j)
				QuickSortMaxToMin(ai, k, j);
		}
	}

	private static void QuickSortMinToMax(double ad[], int i, int j) {
		int k = i;
		int l = j;
		if (j > i) {
			double d = ad[(int)Math.round((double)(i + j) / 2D)];
			while (k <= l)  {
				while (k < j && ad[k] < d) 
					k++;
				for (; l > i && ad[l] > d; l--);
				if (k <= l) {
					swap(ad, k, l);
					k++;
					l--;
				}
			}
			if (i < l)
				QuickSortMinToMax(ad, i, l);
			if (k < j)
				QuickSortMinToMax(ad, k, j);
		}
	}

	private static void QuickSortMaxToMin(double ad[], int i, int j) {
		int k = i;
		int l = j;
		if (j > i) {
			double d = ad[(int)Math.round((double)(i + j) / 2D)];
			while (k <= l)  {
				while (k < j && ad[k] > d) 
					k++;
				for (; l > i && ad[l] < d; l--);
				if (k <= l) {
					swap(ad, k, l);
					k++;
					l--;
				}
			}
			if (i < l)
				QuickSortMaxToMin(ad, i, l);
			if (k < j)
				QuickSortMaxToMin(ad, k, j);
		}
	}

	private static void swap(int ai[], int i, int j) {
		int k = ai[i];
		ai[i] = ai[j];
		ai[j] = k;
	}

	private static void swap(double ad[], int i, int j) {
		double d = ad[i];
		ad[i] = ad[j];
		ad[j] = d;
	}
}
