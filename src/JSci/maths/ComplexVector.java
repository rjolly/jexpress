package JSci.maths;


// Referenced classes of package JSci.maths:
//			MathVector, Complex, DoubleVector, IntegerVector, 
//			Mapping, MathDouble, MathInteger, VectorDimensionException, 
//			MathNumber

public class ComplexVector extends MathVector {

	protected static final int ARRAY_1D = 1;
	protected Complex vector[];

	public ComplexVector(int i) {
		vector = new Complex[i];
	}

	public ComplexVector(Complex acomplex[]) {
		vector = acomplex;
	}

	public boolean equals(Object obj) {
		if (obj != null && (obj instanceof ComplexVector) && vector.length == ((ComplexVector)obj).dimension()) {
			ComplexVector complexvector = (ComplexVector)obj;
			for (int i = 0; i < vector.length; i++)
				if (!vector[i].equals(complexvector.getComponent(i)))
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
			stringbuffer.append(vector[i].toString());
			stringbuffer.append(',');
		}

		stringbuffer.append(vector[i].toString());
		return stringbuffer.toString();
	}

	public int hashCode() {
		return (int)Math.exp(norm());
	}

	public DoubleVector real() {
		double ad[] = new double[vector.length];
		for (int i = 0; i < vector.length; i++)
			ad[i] = vector[i].real();

		return new DoubleVector(ad);
	}

	public DoubleVector imag() {
		double ad[] = new double[vector.length];
		for (int i = 0; i < vector.length; i++)
			ad[i] = vector[i].imag();

		return new DoubleVector(ad);
	}

	public Complex getComponent(int i) {
		if (i >= 0 && i < vector.length)
			return vector[i];
		else
			throw new VectorDimensionException("Invalid component.");
	}

	public void setComponent(int i, Complex complex) {
		if (i >= 0 && i < vector.length) {
			vector[i] = complex;
			return;
		} else {
			throw new VectorDimensionException("Invalid component.");
		}
	}

	public int dimension() {
		return vector.length;
	}

	public double norm() {
		double d = vector[0].real() * vector[0].real() + vector[0].imag() * vector[0].imag();
		for (int i = 1; i < vector.length; i++)
			d += vector[i].real() * vector[i].real() + vector[i].imag() * vector[i].imag();

		return Math.sqrt(d);
	}

	public double infNorm() {
		double d = vector[0].mod();
		for (int i = 1; i < vector.length; i++)
			if (vector[i].mod() > d)
				d = vector[i].mod();

		return d;
	}

	public ComplexVector conjugate() {
		Complex acomplex[] = new Complex[vector.length];
		acomplex[0] = vector[0].conjugate();
		for (int i = 1; i < acomplex.length; i++)
			acomplex[i] = vector[i].conjugate();

		return new ComplexVector(acomplex);
	}

	public MathVector add(MathVector mathvector) {
		if (mathvector instanceof ComplexVector)
			return add((ComplexVector)mathvector);
		if (mathvector instanceof DoubleVector)
			return add((DoubleVector)mathvector);
		if (mathvector instanceof IntegerVector)
			return add((IntegerVector)mathvector);
		else
			throw new IllegalArgumentException("Vector class not recognised by this method.");
	}

	public ComplexVector add(ComplexVector complexvector) {
		switch (MathVector.storageFormat) {
		case 1: // '\001'
			return rawAdd(complexvector);
		}
		if (vector.length == complexvector.dimension()) {
			Complex acomplex[] = new Complex[vector.length];
			acomplex[0] = vector[0].add(complexvector.getComponent(0));
			for (int i = 1; i < acomplex.length; i++)
				acomplex[i] = vector[i].add(complexvector.getComponent(i));

			return new ComplexVector(acomplex);
		} else {
			throw new VectorDimensionException("Vectors are different sizes.");
		}
	}

	private ComplexVector rawAdd(ComplexVector complexvector) {
		if (vector.length == complexvector.vector.length) {
			Complex acomplex[] = new Complex[vector.length];
			acomplex[0] = vector[0].add(complexvector.vector[0]);
			for (int i = 1; i < acomplex.length; i++)
				acomplex[i] = vector[i].add(complexvector.vector[i]);

			return new ComplexVector(acomplex);
		} else {
			throw new VectorDimensionException("Vectors are different sizes.");
		}
	}

	public ComplexVector add(DoubleVector doublevector) {
		switch (MathVector.storageFormat) {
		case 1: // '\001'
			return rawAdd(doublevector);
		}
		if (vector.length == doublevector.dimension()) {
			Complex acomplex[] = new Complex[vector.length];
			acomplex[0] = vector[0].addReal(doublevector.getComponent(0));
			for (int i = 1; i < acomplex.length; i++)
				acomplex[i] = vector[i].addReal(doublevector.getComponent(i));

			return new ComplexVector(acomplex);
		} else {
			throw new VectorDimensionException("Vectors are different sizes.");
		}
	}

	private ComplexVector rawAdd(DoubleVector doublevector) {
		if (vector.length == doublevector.vector.length) {
			Complex acomplex[] = new Complex[vector.length];
			acomplex[0] = vector[0].addReal(doublevector.vector[0]);
			for (int i = 1; i < acomplex.length; i++)
				acomplex[i] = vector[i].addReal(doublevector.vector[i]);

			return new ComplexVector(acomplex);
		} else {
			throw new VectorDimensionException("Vectors are different sizes.");
		}
	}

	public ComplexVector add(IntegerVector integervector) {
		switch (MathVector.storageFormat) {
		case 1: // '\001'
			return rawAdd(integervector);
		}
		if (vector.length == integervector.dimension()) {
			Complex acomplex[] = new Complex[vector.length];
			acomplex[0] = vector[0].addReal(integervector.getComponent(0));
			for (int i = 1; i < acomplex.length; i++)
				acomplex[i] = vector[i].addReal(integervector.getComponent(i));

			return new ComplexVector(acomplex);
		} else {
			throw new VectorDimensionException("Vectors are different sizes.");
		}
	}

	private ComplexVector rawAdd(IntegerVector integervector) {
		if (vector.length == integervector.vector.length) {
			Complex acomplex[] = new Complex[vector.length];
			acomplex[0] = vector[0].addReal(integervector.vector[0]);
			for (int i = 1; i < acomplex.length; i++)
				acomplex[i] = vector[i].addReal(integervector.vector[i]);

			return new ComplexVector(acomplex);
		} else {
			throw new VectorDimensionException("Vectors are different sizes.");
		}
	}

	public MathVector subtract(MathVector mathvector) {
		if (mathvector instanceof ComplexVector)
			return subtract((ComplexVector)mathvector);
		if (mathvector instanceof DoubleVector)
			return subtract((DoubleVector)mathvector);
		if (mathvector instanceof IntegerVector)
			return subtract((IntegerVector)mathvector);
		else
			throw new IllegalArgumentException("Vector class not recognised by this method.");
	}

	public ComplexVector subtract(ComplexVector complexvector) {
		switch (MathVector.storageFormat) {
		case 1: // '\001'
			return rawSubtract(complexvector);
		}
		if (vector.length == complexvector.dimension()) {
			Complex acomplex[] = new Complex[vector.length];
			acomplex[0] = vector[0].subtract(complexvector.getComponent(0));
			for (int i = 1; i < acomplex.length; i++)
				acomplex[i] = vector[i].subtract(complexvector.getComponent(i));

			return new ComplexVector(acomplex);
		} else {
			throw new VectorDimensionException("Vectors are different sizes.");
		}
	}

	private ComplexVector rawSubtract(ComplexVector complexvector) {
		if (vector.length == complexvector.vector.length) {
			Complex acomplex[] = new Complex[vector.length];
			acomplex[0] = vector[0].subtract(complexvector.vector[0]);
			for (int i = 1; i < acomplex.length; i++)
				acomplex[i] = vector[i].subtract(complexvector.vector[i]);

			return new ComplexVector(acomplex);
		} else {
			throw new VectorDimensionException("Vectors are different sizes.");
		}
	}

	public ComplexVector subtract(DoubleVector doublevector) {
		switch (MathVector.storageFormat) {
		case 1: // '\001'
			return rawSubtract(doublevector);
		}
		if (vector.length == doublevector.dimension()) {
			Complex acomplex[] = new Complex[vector.length];
			acomplex[0] = vector[0].subtractReal(doublevector.getComponent(0));
			for (int i = 1; i < acomplex.length; i++)
				acomplex[i] = vector[i].subtractReal(doublevector.getComponent(i));

			return new ComplexVector(acomplex);
		} else {
			throw new VectorDimensionException("Vectors are different sizes.");
		}
	}

	private ComplexVector rawSubtract(DoubleVector doublevector) {
		if (vector.length == doublevector.vector.length) {
			Complex acomplex[] = new Complex[vector.length];
			acomplex[0] = vector[0].subtractReal(doublevector.vector[0]);
			for (int i = 1; i < acomplex.length; i++)
				acomplex[i] = vector[i].subtractReal(doublevector.vector[i]);

			return new ComplexVector(acomplex);
		} else {
			throw new VectorDimensionException("Vectors are different sizes.");
		}
	}

	public ComplexVector subtract(IntegerVector integervector) {
		switch (MathVector.storageFormat) {
		case 1: // '\001'
			return rawSubtract(integervector);
		}
		if (vector.length == integervector.dimension()) {
			Complex acomplex[] = new Complex[vector.length];
			acomplex[0] = vector[0].subtractReal(integervector.getComponent(0));
			for (int i = 1; i < acomplex.length; i++)
				acomplex[i] = vector[i].subtractReal(integervector.getComponent(i));

			return new ComplexVector(acomplex);
		} else {
			throw new VectorDimensionException("Vectors are different sizes.");
		}
	}

	private ComplexVector rawSubtract(IntegerVector integervector) {
		if (vector.length == integervector.vector.length) {
			Complex acomplex[] = new Complex[vector.length];
			acomplex[0] = vector[0].subtractReal(integervector.vector[0]);
			for (int i = 1; i < acomplex.length; i++)
				acomplex[i] = vector[i].subtractReal(integervector.vector[i]);

			return new ComplexVector(acomplex);
		} else {
			throw new VectorDimensionException("Vectors are different sizes.");
		}
	}

	public MathVector scalarMultiply(MathNumber mathnumber) {
		if (mathnumber instanceof Complex)
			return scalarMultiply((Complex)mathnumber);
		if (mathnumber instanceof MathDouble)
			return scalarMultiply(((MathDouble)mathnumber).value());
		if (mathnumber instanceof MathInteger)
			return scalarMultiply(((MathInteger)mathnumber).value());
		else
			throw new IllegalArgumentException("Number class not recognised by this method.");
	}

	public ComplexVector scalarMultiply(Complex complex) {
		Complex acomplex[] = new Complex[vector.length];
		acomplex[0] = vector[0].multiply(complex);
		for (int i = 1; i < acomplex.length; i++)
			acomplex[i] = vector[i].multiply(complex);

		return new ComplexVector(acomplex);
	}

	public ComplexVector scalarMultiply(double d) {
		Complex acomplex[] = new Complex[vector.length];
		acomplex[0] = vector[0].multiply(d);
		for (int i = 1; i < acomplex.length; i++)
			acomplex[i] = vector[i].multiply(d);

		return new ComplexVector(acomplex);
	}

	public ComplexVector scalarDivide(Complex complex) {
		Complex acomplex[] = new Complex[vector.length];
		acomplex[0] = vector[0].divide(complex);
		for (int i = 1; i < acomplex.length; i++)
			acomplex[i] = vector[i].divide(complex);

		return new ComplexVector(acomplex);
	}

	public ComplexVector scalarDivide(double d) {
		Complex acomplex[] = new Complex[vector.length];
		acomplex[0] = vector[0].divide(d);
		for (int i = 1; i < acomplex.length; i++)
			acomplex[i] = vector[i].divide(d);

		return new ComplexVector(acomplex);
	}

	public Complex scalarProduct(ComplexVector complexvector) {
		switch (MathVector.storageFormat) {
		case 1: // '\001'
			return rawScalarProduct(complexvector);
		}
		if (vector.length == complexvector.dimension()) {
			double d = complexvector.getComponent(0).real();
			double d2 = complexvector.getComponent(0).imag();
			double d4 = vector[0].real() * d + vector[0].imag() * d2;
			double d5 = vector[0].imag() * d - vector[0].real() * d2;
			for (int i = 1; i < vector.length; i++) {
				double d1 = complexvector.getComponent(i).real();
				double d3 = complexvector.getComponent(i).imag();
				d4 += vector[i].real() * d1 + vector[i].imag() * d3;
				d5 += vector[i].imag() * d1 - vector[i].real() * d3;
			}

			return new Complex(d4, d5);
		} else {
			throw new VectorDimensionException("Vectors are different sizes.");
		}
	}

	private Complex rawScalarProduct(ComplexVector complexvector) {
		if (vector.length == complexvector.vector.length) {
			double d = vector[0].real() * complexvector.vector[0].real() + vector[0].imag() * complexvector.vector[0].imag();
			double d1 = vector[0].imag() * complexvector.vector[0].real() - vector[0].real() * complexvector.vector[0].imag();
			for (int i = 1; i < vector.length; i++) {
				d += vector[i].real() * complexvector.vector[i].real() + vector[i].imag() * complexvector.vector[i].imag();
				d1 += vector[i].imag() * complexvector.vector[i].real() - vector[i].real() * complexvector.vector[i].imag();
			}

			return new Complex(d, d1);
		} else {
			throw new VectorDimensionException("Vectors are different sizes.");
		}
	}

	public ComplexVector mapComponents(Mapping mapping) {
		Complex acomplex[] = new Complex[vector.length];
		acomplex[0] = mapping.map(vector[0]);
		for (int i = 1; i < vector.length; i++)
			acomplex[i] = mapping.map(vector[i]);

		return new ComplexVector(acomplex);
	}

	static  {
		MathVector.storageFormat = 1;
	}
}
