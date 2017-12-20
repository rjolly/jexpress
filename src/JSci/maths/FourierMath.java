package JSci.maths;


// Referenced classes of package JSci.maths:
//			AbstractMath, Complex, NumericalConstants

public final class FourierMath extends AbstractMath
	implements NumericalConstants {

	private FourierMath() {
	}

	public static Complex[] transform(Complex acomplex[]) {
		double ad[][] = new double[2][acomplex.length];
		if (!isPowerOf2(acomplex.length))
			throw new IllegalArgumentException("The number of samples must be a power of 2.");
		int l1 = numberOfBitsNeeded(acomplex.length);
		for (int i = 0; i < acomplex.length; i++) {
			int l = reverseBits(i, l1);
			ad[0][l] = acomplex[i].real();
			ad[1][l] = acomplex[i].imag();
		}

		int j2 = 1;
		for (int i2 = 2; i2 <= acomplex.length; i2 <<= 1) {
			double d = 6.2831853071795862D / (double)i2;
			double d5 = Math.sin(0.5D * d);
			d5 = 2D * d5 * d5;
			double d6 = Math.sin(d);
			for (int j = 0; j < acomplex.length; j += i2) {
				double d1 = 1.0D;
				double d2 = 0.0D;
				int i1 = j;
				for (int k1 = 0; k1 < j2; k1++) {
					int j1 = i1 + j2;
					double d3 = d1 * ad[0][j1] - d2 * ad[1][j1];
					double d4 = d1 * ad[1][j1] + d2 * ad[0][j1];
					ad[0][j1] = ad[0][i1] - d3;
					ad[1][j1] = ad[1][i1] - d4;
					ad[0][i1] += d3;
					ad[1][i1] += d4;
					d3 = d5 * d1 + d6 * d2;
					d4 = d5 * d2 - d6 * d1;
					d1 -= d3;
					d2 -= d4;
					i1++;
				}

			}

			j2 = i2;
		}

		Complex acomplex1[] = new Complex[acomplex.length];
		for (int k = 0; k < acomplex.length; k++)
			acomplex1[k] = new Complex(ad[0][k], ad[1][k]);

		return acomplex1;
	}

	public static Complex[] transform(double ad[]) {
		double ad1[][] = new double[2][ad.length];
		if (!isPowerOf2(ad.length))
			throw new IllegalArgumentException("The number of samples must be a power of 2.");
		int l1 = numberOfBitsNeeded(ad.length);
		for (int i = 0; i < ad.length; i++) {
			int l = reverseBits(i, l1);
			ad1[0][l] = ad[i];
			ad1[1][l] = 0.0D;
		}

		int j2 = 1;
		for (int i2 = 2; i2 <= ad.length; i2 <<= 1) {
			double d = 6.2831853071795862D / (double)i2;
			double d5 = Math.sin(0.5D * d);
			d5 = 2D * d5 * d5;
			double d6 = Math.sin(d);
			for (int j = 0; j < ad.length; j += i2) {
				double d1 = 1.0D;
				double d2 = 0.0D;
				int i1 = j;
				for (int k1 = 0; k1 < j2; k1++) {
					int j1 = i1 + j2;
					double d3 = d1 * ad1[0][j1] - d2 * ad1[1][j1];
					double d4 = d1 * ad1[1][j1] + d2 * ad1[0][j1];
					ad1[0][j1] = ad1[0][i1] - d3;
					ad1[1][j1] = ad1[1][i1] - d4;
					ad1[0][i1] += d3;
					ad1[1][i1] += d4;
					d3 = d5 * d1 + d6 * d2;
					d4 = d5 * d2 - d6 * d1;
					d1 -= d3;
					d2 -= d4;
					i1++;
				}

			}

			j2 = i2;
		}

		Complex acomplex[] = new Complex[ad.length];
		for (int k = 0; k < ad.length; k++)
			acomplex[k] = new Complex(ad1[0][k], ad1[1][k]);

		return acomplex;
	}

	public static Complex[] inverseTransform(Complex acomplex[]) {
		double ad[][] = new double[2][acomplex.length];
		if (!isPowerOf2(acomplex.length))
			throw new IllegalArgumentException("Data length must be a power of 2.");
		int l1 = numberOfBitsNeeded(acomplex.length);
		for (int i = 0; i < acomplex.length; i++) {
			int l = reverseBits(i, l1);
			ad[0][l] = acomplex[i].real();
			ad[1][l] = acomplex[i].imag();
		}

		int j2 = 1;
		for (int i2 = 2; i2 <= acomplex.length; i2 <<= 1) {
			double d = -6.2831853071795862D / (double)i2;
			double d5 = Math.sin(0.5D * d);
			d5 = 2D * d5 * d5;
			double d6 = Math.sin(d);
			for (int j = 0; j < acomplex.length; j += i2) {
				double d1 = 1.0D;
				double d2 = 0.0D;
				int i1 = j;
				for (int k1 = 0; k1 < j2; k1++) {
					int j1 = i1 + j2;
					double d3 = d1 * ad[0][j1] - d2 * ad[1][j1];
					double d4 = d1 * ad[1][j1] + d2 * ad[0][j1];
					ad[0][j1] = ad[0][i1] - d3;
					ad[1][j1] = ad[1][i1] - d4;
					ad[0][i1] += d3;
					ad[1][i1] += d4;
					d3 = d5 * d1 + d6 * d2;
					d4 = d5 * d2 - d6 * d1;
					d1 -= d3;
					d2 -= d4;
					i1++;
				}

			}

			j2 = i2;
		}

		Complex acomplex1[] = new Complex[acomplex.length];
		double d7 = acomplex.length;
		for (int k = 0; k < acomplex.length; k++)
			acomplex1[k] = new Complex(ad[0][k] / d7, ad[1][k] / d7);

		return acomplex1;
	}

	public static Complex[] inverseTransform(double ad[]) {
		double ad1[][] = new double[2][ad.length];
		if (!isPowerOf2(ad.length))
			throw new IllegalArgumentException("Data length must be a power of 2.");
		int l1 = numberOfBitsNeeded(ad.length);
		for (int i = 0; i < ad.length; i++) {
			int l = reverseBits(i, l1);
			ad1[0][l] = ad[i];
			ad1[1][l] = 0.0D;
		}

		int j2 = 1;
		for (int i2 = 2; i2 <= ad.length; i2 <<= 1) {
			double d = -6.2831853071795862D / (double)i2;
			double d5 = Math.sin(0.5D * d);
			d5 = 2D * d5 * d5;
			double d6 = Math.sin(d);
			for (int j = 0; j < ad.length; j += i2) {
				double d1 = 1.0D;
				double d2 = 0.0D;
				int i1 = j;
				for (int k1 = 0; k1 < j2; k1++) {
					int j1 = i1 + j2;
					double d3 = d1 * ad1[0][j1] - d2 * ad1[1][j1];
					double d4 = d1 * ad1[1][j1] + d2 * ad1[0][j1];
					ad1[0][j1] = ad1[0][i1] - d3;
					ad1[1][j1] = ad1[1][i1] - d4;
					ad1[0][i1] += d3;
					ad1[1][i1] += d4;
					d3 = d5 * d1 + d6 * d2;
					d4 = d5 * d2 - d6 * d1;
					d1 -= d3;
					d2 -= d4;
					i1++;
				}

			}

			j2 = i2;
		}

		Complex acomplex[] = new Complex[ad.length];
		double d7 = ad.length;
		for (int k = 0; k < ad.length; k++)
			acomplex[k] = new Complex(ad1[0][k] / d7, ad1[1][k] / d7);

		return acomplex;
	}

	private static boolean isPowerOf2(int i) {
		byte byte0 = 32;
		int j = 1;
		for (int k = 2; j < byte0; k <<= 1) {
			if (i == k)
				return true;
			j++;
		}

		return false;
	}

	private static int numberOfBitsNeeded(int i) {
		if (i < 2)
			throw new IllegalArgumentException();
		int j = 0;
		do {
			if ((i & 1 << j) > 0)
				return j;
			j++;
		} while (true);
	}

	private static int reverseBits(int i, int j) {
		int l;
		for (int k = l = 0; k < j; k++) {
			l = l << 1 | i & 1;
			i >>= 1;
		}

		return l;
	}

	public static Complex[] sort(Complex acomplex[]) {
		Complex acomplex1[] = new Complex[acomplex.length];
		int i = acomplex.length / 2;
		for (int j = 0; j < i; j++) {
			acomplex1[i + j] = acomplex[j];
			acomplex1[j] = acomplex[i + j];
		}

		return acomplex1;
	}
}
