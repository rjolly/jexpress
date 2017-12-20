package JSci.maths;


// Referenced classes of package JSci.maths:
//			DoubleSquareMatrix, DoubleVector, LinearMath, MaximumIterationsExceededException

public final class KarhunenLoeve {

	double data[][];

	public KarhunenLoeve(double ad[][]) {
		setData(ad);
	}

	public double[][] getProductMatrix() {
		return getProductMatrix(data);
	}

	private static double[][] vectorToSquare(double ad[]) {
		double ad1[][] = new double[ad.length][ad.length];
		for (int i = 0; i < ad.length; i++) {
			for (int j = 0; j < ad.length; j++)
				ad1[j][i] = ad[i] * ad[j];

		}

		return ad1;
	}

	private static void add(double ad[][], double d, double ad1[][]) {
		for (int i = 0; i < ad.length; i++) {
			for (int j = 0; j < ad[i].length; j++)
				ad[i][j] += ad1[i][j] * d;

		}

	}

	public static double[][] getProductMatrix(double ad[][]) {
		double ad1[][] = new double[ad[0].length][ad[0].length];
		for (int i = 0; i < ad.length; i++)
			add(ad1, 1.0D / (double)ad.length, vectorToSquare(ad[i]));

		return ad1;
	}

	public static double[][] getProductMatrix(double ad[]) {
		return vectorToSquare(ad);
	}

	public double[][] getData() {
		return data;
	}

	public void setData(double ad[][]) {
		data = ad;
	}

	public double[][] getEigenvectors() throws MaximumIterationsExceededException {
		double ad[][] = getProductMatrix(data);
		DoubleSquareMatrix doublesquarematrix = new DoubleSquareMatrix(ad);
		DoubleVector adoublevector[] = new DoubleVector[data[0].length];
		double ad1[] = LinearMath.eigenSolveSymmetric(doublesquarematrix, adoublevector);
		tri(ad1, adoublevector);
		double ad2[][] = new double[adoublevector.length][adoublevector[0].dimension()];
		for (int i = 0; i < adoublevector.length; i++) {
			for (int j = 0; j < adoublevector[i].dimension(); j++)
				ad2[i][j] = adoublevector[i].getComponent(j);

		}

		return ad2;
	}

	private static void tri(double ad[], DoubleVector adoublevector[]) {
		boolean flag = true;
		while (flag)  {
			flag = false;
			for (int i = 0; i < ad.length - 1; i++)
				if (ad[i] < ad[i + 1]) {
					double d = ad[i + 1];
					ad[i + 1] = ad[i];
					ad[i] = d;
					flag = true;
					DoubleVector doublevector = adoublevector[i + 1];
					adoublevector[i + 1] = adoublevector[i];
					adoublevector[i] = doublevector;
				}

		}
	}
}
