package JSci.maths;


// Referenced classes of package JSci.maths:
//			AbstractMath, ArrayMath, Complex

public final class WaveletMath extends AbstractMath {

	private WaveletMath() {
	}

	public static Complex[] downsample(Complex acomplex[], Complex acomplex1[]) {
		int i = acomplex.length;
		int j = Math.round((float)i / 2.0F - 0.5F);
		int k = acomplex1.length;
		int l = Math.round((float)k / 2.0F - 0.5F);
		Complex acomplex2[] = new Complex[l];
		for (int i1 = 0; i1 < l; i1++) {
			acomplex2[i1] = Complex.ZERO;
			for (int j1 = Math.max(0, (2 * i1 - j) + 1); j1 < Math.min(k, ((2 * i1 + i) - j) + 1); j1++)
				acomplex2[i1] = acomplex2[i1].add(acomplex1[j1].multiply(acomplex[((j1 - 2 * i1) + j) - 1]));

		}

		return acomplex2;
	}

	public static double[] downsample(double ad[], double ad1[]) {
		int i = ad.length;
		int j = Math.round((float)i / 2.0F - 0.5F);
		int k = ad1.length;
		int l = Math.round((float)k / 2.0F - 0.5F);
		double ad2[] = new double[l];
		for (int i1 = 0; i1 < l; i1++) {
			ad2[i1] = 0.0D;
			for (int j1 = Math.max(0, (2 * i1 - j) + 1); j1 < Math.min(k, ((2 * i1 + i) - j) + 1); j1++)
				ad2[i1] += ad1[j1] * ad[((j1 - 2 * i1) + j) - 1];

		}

		return ad2;
	}

	public static Complex[] upsample(Complex acomplex[]) {
		int i = acomplex.length;
		Complex acomplex1[] = new Complex[2 * i];
		for (int j = 0; j < i; j++) {
			acomplex1[2 * j] = acomplex[j];
			acomplex1[2 * j + 1] = Complex.ZERO;
		}

		return acomplex1;
	}

	public static double[] upsample(double ad[]) {
		int i = ad.length;
		double ad1[] = new double[2 * i];
		for (int j = 0; j < i; j++) {
			ad1[2 * j] = ad[j];
			ad1[2 * j + 1] = 0.0D;
		}

		return ad1;
	}

	public static Complex[] upsample(Complex acomplex[], Complex acomplex1[]) {
		int i = acomplex.length;
		int j = Math.round((float)i / 2.0F - 0.5F);
		int k = acomplex1.length;
		Complex acomplex2[] = new Complex[2 * k];
		Complex acomplex3[] = new Complex[2 * k];
		acomplex3 = upsample(acomplex1);
		for (int l = 0; l < 2 * k; l++) {
			acomplex2[l] = Complex.ZERO;
			for (int i1 = Math.max(0, l - j); i1 < Math.min(2 * k, (l + i) - j); i1++)
				acomplex2[l] = acomplex2[l].add(acomplex3[i1].multiply(acomplex[(l + i) - j - i1 - 1]));

		}

		return acomplex2;
	}

	public static double[] upsample(double ad[], double ad1[]) {
		int i = ad.length;
		int j = Math.round((float)i / 2.0F - 0.5F);
		int k = ad1.length;
		double ad2[] = new double[2 * k];
		double ad3[] = new double[2 * k];
		ad3 = upsample(ad1);
		for (int l = 0; l < 2 * k; l++) {
			ad2[l] = 0.0D;
			for (int i1 = Math.max(0, l - j); i1 < Math.min(2 * k, (l + i) - j); i1++)
				ad2[l] += ad3[i1] * ad[(l + i) - j - i1 - 1];

		}

		return ad2;
	}

	public static double[] lowToHigh(double ad[]) {
		double ad1[] = ArrayMath.invert(ad);
		int i = 1;
		for (int j = 0; j < ad1.length; j++) {
			ad1[j] = (double)i * ad1[j];
			i *= -1;
		}

		return ad1;
	}
}
