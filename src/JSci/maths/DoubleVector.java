package JSci.maths;

import JSci.GlobalSettings;

// Referenced classes of package JSci.maths:
//			MathVector, Complex, ComplexVector, IntegerVector, 
//			Mapping, MathDouble, MathInteger, VectorDimensionException, 
//			MathNumber

public class DoubleVector extends MathVector {

	protected static final int ARRAY_1D = 1;
	protected double vector[];

	public DoubleVector(int i) {
		vector = new double[i];
	}

	public DoubleVector(double ad[]) {
		vector = ad;
	}

	public boolean equals(Object obj) {
		if (obj != null && (obj instanceof DoubleVector) && vector.length == ((DoubleVector)obj).dimension()) {
			DoubleVector doublevector = (DoubleVector)obj;
			for (int i = 0; i < vector.length; i++)
				if (Math.abs(vector[i] - doublevector.getComponent(i)) > GlobalSettings.ZERO_TOL)
					return false;

			return true;
		} else {
			return false;
		}
	}

	public String toString() {
		StringBuffer stringbuffer = new StringBuffer(vector.length);
		int i;
		for (i = 0; i < vector.length - 1; i++) {
			stringbuffer.append(vector[i]);
			stringbuffer.append(',');
		}

		stringbuffer.append(vector[i]);
		return stringbuffer.toString();
	}

	public int hashCode() {
		return (int)Math.exp(norm());
	}

	public IntegerVector toIntegerVector() {
		int ai[] = new int[vector.length];
		for (int i = 0; i < vector.length; i++)
			ai[i] = Math.round((float)vector[i]);

		return new IntegerVector(ai);
	}

	public ComplexVector toComplexVector() {
		Complex acomplex[] = new Complex[vector.length];
		for (int i = 0; i < vector.length; i++)
			acomplex[i] = new Complex(vector[i], 0.0D);

		return new ComplexVector(acomplex);
	}

	public double getComponent(int i) {
		if (i >= 0 && i < vector.length)
			return vector[i];
		else
			throw new VectorDimensionException("Invalid component.");
	}

	public void setComponent(int i, double d) {
		if (i >= 0 && i < vector.length) {
			vector[i] = d;
			return;
		} else {
			throw new VectorDimensionException("Invalid component.");
		}
	}

	public int dimension() {
		return vector.length;
	}

	public double norm(int i) {
		double d = Math.pow(vector[0], i);
		for (int j = 1; j < vector.length; j++)
			d += Math.pow(vector[j], i);

		return Math.pow(d, 1.0D / (double)i);
	}

	public double norm() {
		double d = vector[0] * vector[0];
		for (int i = 1; i < vector.length; i++)
			d += vector[i] * vector[i];

		return Math.sqrt(d);
	}

	public double infNorm() {
		double d = vector[0];
		for (int i = 1; i < vector.length; i++)
			if (vector[i] > d)
				d = vector[i];

		return d;
	}

	public MathVector add(MathVector mathvector) {
		if (mathvector instanceof DoubleVector)
			return add((DoubleVector)mathvector);
		else
			throw new IllegalArgumentException("Vector class not recognised by this method.");
	}

	public DoubleVector add(DoubleVector doublevector) {
		switch (MathVector.storageFormat) {
		case 1: // '\001'
			return rawAdd(doublevector);
		}
		if (vector.length == doublevector.dimension()) {
			double ad[] = new double[vector.length];
			ad[0] = vector[0] + doublevector.getComponent(0);
			for (int i = 1; i < ad.length; i++)
				ad[i] = vector[i] + doublevector.getComponent(i);

			return new DoubleVector(ad);
		} else {
			throw new VectorDimensionException("Vectors are different sizes.");
		}
	}

	private DoubleVector rawAdd(DoubleVector doublevector) {
		if (vector.length == doublevector.vector.length) {
			double ad[] = new double[vector.length];
			ad[0] = vector[0] + doublevector.vector[0];
			for (int i = 1; i < ad.length; i++)
				ad[i] = vector[i] + doublevector.vector[i];

			return new DoubleVector(ad);
		} else {
			throw new VectorDimensionException("Vectors are different sizes.");
		}
	}

	public MathVector subtract(MathVector mathvector) {
		if (mathvector instanceof DoubleVector)
			return subtract((DoubleVector)mathvector);
		else
			throw new IllegalArgumentException("Vector class not recognised by this method.");
	}

	public DoubleVector subtract(DoubleVector doublevector) {
		switch (MathVector.storageFormat) {
		case 1: // '\001'
			return rawSubtract(doublevector);
		}
		if (vector.length == doublevector.dimension()) {
			double ad[] = new double[vector.length];
			ad[0] = vector[0] - doublevector.getComponent(0);
			for (int i = 1; i < ad.length; i++)
				ad[i] = vector[i] - doublevector.getComponent(i);

			return new DoubleVector(ad);
		} else {
			throw new VectorDimensionException("Vectors are different sizes.");
		}
	}

	private DoubleVector rawSubtract(DoubleVector doublevector) {
		if (vector.length == doublevector.vector.length) {
			double ad[] = new double[vector.length];
			ad[0] = vector[0] - doublevector.vector[0];
			for (int i = 1; i < ad.length; i++)
				ad[i] = vector[i] - doublevector.vector[i];

			return new DoubleVector(ad);
		} else {
			throw new VectorDimensionException("Vectors are different sizes.");
		}
	}

	public MathVector scalarMultiply(MathNumber mathnumber) {
		if (mathnumber instanceof MathDouble)
			return scalarMultiply(((MathDouble)mathnumber).value());
		if (mathnumber instanceof MathInteger)
			return scalarMultiply(((MathInteger)mathnumber).value());
		else
			throw new IllegalArgumentException("Number class not recognised by this method.");
	}

	public DoubleVector scalarMultiply(double d) {
		double ad[] = new double[vector.length];
		ad[0] = d * vector[0];
		for (int i = 1; i < ad.length; i++)
			ad[i] = d * vector[i];

		return new DoubleVector(ad);
	}

	public DoubleVector scalarDivide(double d) {
		double ad[] = new double[vector.length];
		ad[0] = vector[0] / d;
		for (int i = 1; i < ad.length; i++)
			ad[i] = vector[i] / d;

		return new DoubleVector(ad);
	}

	public double scalarProduct(DoubleVector doublevector) {
		switch (MathVector.storageFormat) {
		case 1: // '\001'
			return rawScalarProduct(doublevector);
		}
		if (vector.length == doublevector.dimension()) {
			double d = vector[0] * doublevector.getComponent(0);
			for (int i = 1; i < vector.length; i++)
				d += vector[i] * doublevector.getComponent(i);

			return d;
		} else {
			throw new VectorDimensionException("Vectors are different sizes.");
		}
	}

	private double rawScalarProduct(DoubleVector doublevector) {
		if (vector.length == doublevector.vector.length) {
			double d = vector[0] * doublevector.vector[0];
			for (int i = 1; i < vector.length; i++)
				d += vector[i] * doublevector.vector[i];

			return d;
		} else {
			throw new VectorDimensionException("Vectors are different sizes.");
		}
	}

	public DoubleVector mapComponents(Mapping mapping) {
		double ad[] = new double[vector.length];
		ad[0] = mapping.map(vector[0]);
		for (int i = 1; i < vector.length; i++)
			ad[i] = mapping.map(vector[i]);

		return new DoubleVector(ad);
	}

	static  {
		MathVector.storageFormat = 1;
	}
}
