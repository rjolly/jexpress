package JSci.maths;


// Referenced classes of package JSci.maths:
//			AbstractMath, ArrayMath, Mapping, NMapping

public final class NumericalMath extends AbstractMath {

	private NumericalMath() {
	}

	public static double[] solveQuadratic(double d, double d1, double d2) {
		double ad[] = new double[2];
		double d3 = -0.5D * (d1 + (d1 >= 0.0D ? 1.0D : -1D) * Math.sqrt(d1 * d1 - 4D * d * d2));
		ad[0] = d3 / d;
		ad[1] = d2 / d3;
		return ad;
	}

	public static double[] euler(double ad[], Mapping mapping, double d) {
		for (int i = 0; i < ad.length - 1; i++)
			ad[i + 1] = ad[i] + d * mapping.map(ad[i]);

		return ad;
	}

	public static double[] leapFrog(double ad[], Mapping mapping, double d) {
		for (int i = 1; i < ad.length - 1; i++)
			ad[i + 1] = ad[i - 1] + 2D * d * mapping.map(ad[i]);

		return ad;
	}

	public static double[] rungeKutta2(double ad[], Mapping mapping, double d) {
		for (int i = 0; i < ad.length - 1; i++)
			ad[i + 1] = ad[i] + d * mapping.map(ad[i] + (d / 2D) * mapping.map(ad[i]));

		return ad;
	}

	public static double[] rungeKutta4(double ad[], Mapping mapping, double d) {
		for (int i = 0; i < ad.length - 1; i++) {
			double d1 = d * mapping.map(ad[i]);
			double d2 = d * mapping.map(ad[i] + d1 / 2D);
			double d3 = d * mapping.map(ad[i] + d2 / 2D);
			double d4 = d * mapping.map(ad[i] + d3);
			ad[i + 1] = ad[i] + (d1 + d4) / 6D + (d2 + d3) / 3D;
		}

		return ad;
	}

	public static double trapezium(int i, Mapping mapping, double d, double d1) {
		double d2 = 0.0D;
		double d3 = d;
		double d4 = (d1 - d) / (double)i;
		for (int j = 0; j < i; j++) {
			d2 += mapping.map(d3) + mapping.map(d3 + d4);
			d3 += d4;
		}

		return (d2 * d4) / 2D;
	}

	public static double simpson(int i, Mapping mapping, double d, double d1) {
		double d2 = 0.0D;
		double d3 = 0.0D;
		double d4 = d;
		double d5 = (d1 - d) / (double)(2 * i);
		for (int j = 0; j < i - 1; j++) {
			d2 += mapping.map(d4 + d5);
			d3 += mapping.map(d4 + 2D * d5);
			d4 += 2D * d5;
		}

		d2 += mapping.map(d4 + d5);
		return (d5 / 3D) * (mapping.map(d) + 4D * d2 + 2D * d3 + mapping.map(d1));
	}

	public static double richardson(int i, Mapping mapping, double d, double d1) {
		double d3 = 0.0D;
		double d4 = 0.0D;
		double d6 = 0.0D;
		double d7 = 0.0D;
		double d8 = d;
		double d9 = (d1 - d) / (double)(2 * i);
		double d10 = d9 / 2D;
		for (int j = 0; j < i - 1; j++) {
			d3 += mapping.map(d8 + d9);
			d4 += mapping.map(d8 + 2D * d9);
			d6 += mapping.map(d8 + d10);
			d7 += mapping.map(d8 + 2D * d10);
			d6 += mapping.map(d8 + 3D * d10);
			d7 += mapping.map(d8 + 4D * d10);
			d8 += 2D * d9;
		}

		d3 += mapping.map(d8 + d9);
		d6 += mapping.map(d8 + d10);
		d7 += mapping.map(d8 + 2D * d10);
		d6 += mapping.map(d8 + 3D * d10);
		double d2 = (d9 / 3D) * (mapping.map(d) + 4D * d3 + 2D * d4 + mapping.map(d1));
		double d5 = (d10 / 3D) * (mapping.map(d) + 4D * d6 + 2D * d7 + mapping.map(d1));
		return (16D * d5 - d2) / 15D;
	}

	public static double gaussian4(int i, Mapping mapping, double d, double d1) {
		double d2 = 0.0D;
		double d3 = (d1 - d) / (double)i;
		double d4 = d3 / 2D;
		double ad[] = new double[4];
		double ad1[] = new double[4];
		ad[2] = 0.33998104358485626D;
		ad[3] = 0.86113631159405257D;
		ad[0] = -ad[3];
		ad[1] = -ad[2];
		ad1[0] = ad1[3] = 0.34785484513745385D;
		ad1[1] = ad1[2] = 0.65214515486254609D;
		for (int j = 0; j < i; j++) {
			for (int k = 0; k < ad.length; k++)
				d2 += ad1[k] * mapping.map(d + (ad[k] + 1.0D) * d4);

			d += d3;
		}

		return d2 * d4;
	}

	public static double gaussian8(int i, Mapping mapping, double d, double d1) {
		double d2 = 0.0D;
		double d3 = (d1 - d) / (double)i;
		double d4 = d3 / 2D;
		double ad[] = new double[8];
		double ad1[] = new double[8];
		ad[4] = 0.18343464249564981D;
		ad[5] = 0.52553240991632899D;
		ad[6] = 0.79666647741362673D;
		ad[7] = 0.96028985649753618D;
		ad[0] = -ad[7];
		ad[1] = -ad[6];
		ad[2] = -ad[5];
		ad[3] = -ad[4];
		ad1[0] = ad1[7] = 0.10122853629037626D;
		ad1[1] = ad1[6] = 0.22238103445337448D;
		ad1[2] = ad1[5] = 0.31370664587788727D;
		ad1[3] = ad1[4] = 0.36268378337836199D;
		for (int j = 0; j < i; j++) {
			for (int k = 0; k < ad.length; k++)
				d2 += ad1[k] * mapping.map(d + (ad[k] + 1.0D) * d4);

			d += d3;
		}

		return d2 * d4;
	}

	public static double[] differentiate(int i, Mapping mapping, double d, double d1) {
		double ad[] = new double[i];
		double d2 = d;
		double d3 = (d1 - d) / (double)i;
		double d4 = d3 / 2D;
		for (int j = 0; j < i; j++) {
			ad[j] = (mapping.map(d2 + d4) - mapping.map(d2 - d4)) / d3;
			d2 += d3;
		}

		return ad;
	}

	public static double[][] differentiate(NMapping nmapping, double ad[], double ad1[]) {
		double ad2[] = new double[ad.length];
		double ad3[] = new double[ad.length];
		System.arraycopy(ad, 0, ad2, 0, ad.length);
		System.arraycopy(ad, 0, ad3, 0, ad.length);
		ad2[0] += ad1[0];
		ad3[0] -= ad1[0];
		double ad4[] = ArrayMath.scalarMultiply(0.5D / ad1[0], ArrayMath.subtract(nmapping.map(ad2), nmapping.map(ad3)));
		double ad6[][] = new double[ad4.length][ad.length];
		for (int i = 0; i < ad4.length; i++)
			ad6[i][0] = ad4[i];

		for (int k = 1; k < ad.length; k++) {
			System.arraycopy(ad, 0, ad2, 0, ad.length);
			System.arraycopy(ad, 0, ad3, 0, ad.length);
			ad2[k] += ad1[k];
			ad3[k] -= ad1[k];
			double ad5[] = ArrayMath.scalarMultiply(0.5D / ad1[k], ArrayMath.subtract(nmapping.map(ad2), nmapping.map(ad3)));
			for (int j = 0; j < ad5.length; j++)
				ad6[j][k] = ad5[j];

		}

		return ad6;
	}

	public static double[] metropolis(double ad[], Mapping mapping, double d) {
		for (int i = 0; i < ad.length - 1; i++) {
			ad[i + 1] = ad[i] + d * (2D * Math.random() - 1.0D);
			if (mapping.map(ad[i + 1]) / mapping.map(ad[i]) < Math.random())
				ad[i + 1] = ad[i];
		}

		return ad;
	}
}
