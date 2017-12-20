package JSci.maths;


// Referenced classes of package JSci.maths:
//			AbstractMath, ArrayMath

public final class EngineerMath extends AbstractMath {

	private EngineerMath() {
	}

	public static double[] runningAverage(double ad[], int i) {
		if (ad.length < i)
			throw new IllegalArgumentException("Array must be at least as long as the required width : " + ad.length + " < " + i);
		if ((i & 1) == 0)
			throw new IllegalArgumentException("The parameter 'width' must be odd : " + i);
		if (ad.length == 0)
			throw new IllegalArgumentException("Empty array, nothing to compute!");
		int j = i >> 1;
		double ad1[] = new double[ad.length];
		ad1[0] = ArrayMath.mean(ArrayMath.extract(0, i - 1, ad));
		for (int k = 1; k < j; k++)
			ad1[k] = ad1[0];

		for (int l = j; l < ad.length - j - 1; l++)
			ad1[l] = ArrayMath.mean(ArrayMath.extract(l - j, l + j, ad));

		ad1[ad.length - j - 1] = ArrayMath.mean(ArrayMath.extract((ad.length - 1 - i) + 1, ad.length - 1, ad));
		for (int i1 = ad.length - j; i1 < ad.length; i1++)
			ad1[i1] = ad1[ad.length - j - 1];

		return ad1;
	}

	public static double[] runningMedian(double ad[], int i) {
		if (ad.length < i)
			throw new IllegalArgumentException("Array must be at least as long as the required width : " + ad.length + " < " + i);
		if ((i & 1) == 0)
			throw new IllegalArgumentException("The parameter 'width' must be odd : " + i);
		if (ad.length == 0)
			throw new IllegalArgumentException("Empty array, nothing to compute!");
		int j = i >> 1;
		double ad1[] = new double[ad.length];
		ad1[0] = ArrayMath.median(ArrayMath.extract(0, i - 1, ad));
		for (int k = 1; k < j; k++)
			ad1[k] = ad1[0];

		for (int l = j; l < ad.length - j - 1; l++)
			ad1[l] = ArrayMath.median(ArrayMath.extract(l - j, l + j, ad));

		ad1[ad.length - j - 1] = ArrayMath.median(ArrayMath.extract((ad.length - 1 - i) + 1, ad.length - 1, ad));
		for (int i1 = ad.length - j; i1 < ad.length; i1++)
			ad1[i1] = ad1[ad.length - j - 1];

		return ad1;
	}

	public static double icf(double ad[]) {
		double d = 0.0D;
		for (int i = 0; i < ad.length; i++)
			if (ad[i] != 0.0D) {
				if (ad[i] < 0.0D)
					throw new IllegalArgumentException("You cannot take the ICF of a array having negative components : v [" + i + "] = " + ad[i] + " < 0");
				d -= ad[i] * Math.log(ad[i]);
			}

		return d;
	}

	public static double entropy(double ad[]) {
		double ad1[] = ArrayMath.abs(ad);
		double d = ArrayMath.mass(ad1);
		if (d == 0.0D) {
			return 0.0D;
		} else {
			ad1 = ArrayMath.scalarMultiply(1.0D / d, ad1);
			return icf(ad1);
		}
	}

	public static double entropy(int ai[]) {
		int ai1[] = ArrayMath.abs(ai);
		int i = ArrayMath.mass(ai1);
		if (i == 0) {
			return 0.0D;
		} else {
			double ad[] = ArrayMath.scalarMultiply(1.0D / (double)i, ai1);
			return icf(ad);
		}
	}

	public static double[] resample(double ad[], int i) {
		if (i <= 0)
			throw new IllegalArgumentException("New length must be strictly positive : " + i + " <=0 ");
		double ad1[] = new double[i];
		for (int l = 0; l < i; l++) {
			double d = ((double)l / (double)(i - 1)) * (double)(ad.length - 1);
			int j = (int)Math.floor(d);
			int k = (int)Math.ceil(d);
			double d1 = d - (double)j;
			ad1[l] = ad[j] * d1 + ad[k] * (1.0D - d1);
		}

		return ad1;
	}
}
