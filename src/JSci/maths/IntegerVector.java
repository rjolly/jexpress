package JSci.maths;


// Referenced classes of package JSci.maths:
//			MathVector, Complex, ComplexVector, DoubleVector, 
//			MathInteger, VectorDimensionException, MathNumber

public class IntegerVector extends MathVector {

	protected static final int ARRAY_1D = 1;
	protected int vector[];

	public IntegerVector(int i) {
		vector = new int[i];
	}

	public IntegerVector(int ai[]) {
		vector = ai;
	}

	public boolean equals(Object obj) {
		if (obj != null && (obj instanceof IntegerVector) && vector.length == ((IntegerVector)obj).dimension()) {
			IntegerVector integervector = (IntegerVector)obj;
			for (int i = 0; i < vector.length; i++)
				if (vector[i] != integervector.getComponent(i))
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

	public DoubleVector toDoubleVector() {
		double ad[] = new double[vector.length];
		for (int i = 0; i < vector.length; i++)
			ad[i] = vector[i];

		return new DoubleVector(ad);
	}

	public ComplexVector toComplexVector() {
		Complex acomplex[] = new Complex[vector.length];
		for (int i = 0; i < vector.length; i++)
			acomplex[i] = new Complex(vector[i], 0.0D);

		return new ComplexVector(acomplex);
	}

	public int getComponent(int i) {
		if (i >= 0 && i < vector.length)
			return vector[i];
		else
			throw new VectorDimensionException("Invalid component.");
	}

	public void setComponent(int i, int j) {
		if (i >= 0 && i < vector.length) {
			vector[i] = j;
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
		int i = vector[0] * vector[0];
		for (int j = 1; j < vector.length; j++)
			i += vector[j] * vector[j];

		return Math.sqrt(i);
	}

	public double infNorm() {
		int i = vector[0];
		for (int j = 1; j < vector.length; j++)
			if (vector[j] > i)
				i = vector[j];

		return (double)i;
	}

	public MathVector add(MathVector mathvector) {
		if (mathvector instanceof IntegerVector)
			return add((IntegerVector)mathvector);
		else
			throw new IllegalArgumentException("Vector class not recognised by this method.");
	}

	public IntegerVector add(IntegerVector integervector) {
		switch (MathVector.storageFormat) {
		case 1: // '\001'
			return rawAdd(integervector);
		}
		if (vector.length == integervector.dimension()) {
			int ai[] = new int[vector.length];
			ai[0] = vector[0] + integervector.getComponent(0);
			for (int i = 1; i < ai.length; i++)
				ai[i] = vector[i] + integervector.getComponent(i);

			return new IntegerVector(ai);
		} else {
			throw new VectorDimensionException("Vectors are different sizes.");
		}
	}

	private IntegerVector rawAdd(IntegerVector integervector) {
		if (vector.length == integervector.vector.length) {
			int ai[] = new int[vector.length];
			ai[0] = vector[0] + integervector.vector[0];
			for (int i = 1; i < ai.length; i++)
				ai[i] = vector[i] + integervector.vector[i];

			return new IntegerVector(ai);
		} else {
			throw new VectorDimensionException("Vectors are different sizes.");
		}
	}

	public MathVector subtract(MathVector mathvector) {
		if (mathvector instanceof IntegerVector)
			return subtract((IntegerVector)mathvector);
		else
			throw new IllegalArgumentException("Vector class not recognised by this method.");
	}

	public IntegerVector subtract(IntegerVector integervector) {
		switch (MathVector.storageFormat) {
		case 1: // '\001'
			return rawSubtract(integervector);
		}
		if (vector.length == integervector.dimension()) {
			int ai[] = new int[vector.length];
			ai[0] = vector[0] - integervector.getComponent(0);
			for (int i = 1; i < ai.length; i++)
				ai[i] = vector[i] - integervector.getComponent(i);

			return new IntegerVector(ai);
		} else {
			throw new VectorDimensionException("Vectors are different sizes.");
		}
	}

	private IntegerVector rawSubtract(IntegerVector integervector) {
		if (vector.length == integervector.vector.length) {
			int ai[] = new int[vector.length];
			ai[0] = vector[0] - integervector.vector[0];
			for (int i = 1; i < ai.length; i++)
				ai[i] = vector[i] - integervector.vector[i];

			return new IntegerVector(ai);
		} else {
			throw new VectorDimensionException("Vectors are different sizes.");
		}
	}

	public MathVector scalarMultiply(MathNumber mathnumber) {
		if (mathnumber instanceof MathInteger)
			return scalarMultiply(((MathInteger)mathnumber).value());
		else
			throw new IllegalArgumentException("Number class not recognised by this method.");
	}

	public IntegerVector scalarMultiply(int i) {
		int ai[] = new int[vector.length];
		ai[0] = i * vector[0];
		for (int j = 1; j < ai.length; j++)
			ai[j] = i * vector[j];

		return new IntegerVector(ai);
	}

	public int scalarProduct(IntegerVector integervector) {
		switch (MathVector.storageFormat) {
		case 1: // '\001'
			return rawScalarProduct(integervector);
		}
		if (vector.length == integervector.dimension()) {
			int i = vector[0] * integervector.getComponent(0);
			for (int j = 1; j < vector.length; j++)
				i += vector[j] * integervector.getComponent(j);

			return i;
		} else {
			throw new VectorDimensionException("Vectors are different sizes.");
		}
	}

	private int rawScalarProduct(IntegerVector integervector) {
		if (vector.length == integervector.vector.length) {
			int i = vector[0] * integervector.vector[0];
			for (int j = 1; j < vector.length; j++)
				i += vector[j] * integervector.vector[j];

			return i;
		} else {
			throw new VectorDimensionException("Vectors are different sizes.");
		}
	}

	static  {
		MathVector.storageFormat = 1;
	}
}
