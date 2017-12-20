package JSci.maths;


// Referenced classes of package JSci.maths:
//			ComplexVector, Complex, Double3Vector, DoubleVector, 
//			Integer3Vector, IntegerVector, Mapping, VectorDimensionException, 
//			MathVector

public class Complex3Vector extends ComplexVector {

	public Complex3Vector() {
		super(3);
	}

	public Complex3Vector(Complex complex, Complex complex1, Complex complex2) {
		super(3);
		super.vector[0] = complex;
		super.vector[1] = complex1;
		super.vector[2] = complex2;
	}

	public boolean equals(Object obj) {
		if (obj != null && (obj instanceof Complex3Vector)) {
			Complex3Vector complex3vector = (Complex3Vector)obj;
			return super.vector[0].equals(((ComplexVector) (complex3vector)).vector[0]) && super.vector[1].equals(((ComplexVector) (complex3vector)).vector[1]) && super.vector[2].equals(((ComplexVector) (complex3vector)).vector[2]);
		} else {
			return false;
		}
	}

	public String toString() {
		StringBuffer stringbuffer = new StringBuffer(5);
		stringbuffer.append(super.vector[0]).append(',').append(super.vector[1]).append(',').append(super.vector[2]);
		return stringbuffer.toString();
	}

	public int hashCode() {
		return (int)Math.exp(norm());
	}

	public DoubleVector real() {
		return new Double3Vector(super.vector[0].real(), super.vector[1].real(), super.vector[2].real());
	}

	public DoubleVector imag() {
		return new Double3Vector(super.vector[0].imag(), super.vector[1].imag(), super.vector[2].imag());
	}

	public Complex getComponent(int i) {
		if (i >= 0 && i < 3)
			return super.vector[i];
		else
			throw new VectorDimensionException("Invalid component.");
	}

	public void setComponent(int i, Complex complex) {
		if (i >= 0 && i < 3) {
			super.vector[i] = complex;
			return;
		} else {
			throw new VectorDimensionException("Invalid component.");
		}
	}

	public int dimension() {
		return 3;
	}

	public double norm() {
		return Math.sqrt(super.vector[0].real() * super.vector[0].real() + super.vector[0].imag() * super.vector[0].imag() + super.vector[1].real() * super.vector[1].real() + super.vector[1].imag() * super.vector[1].imag() + super.vector[2].real() * super.vector[2].real() + super.vector[2].imag() * super.vector[2].imag());
	}

	public double infNorm() {
		double d = super.vector[0].mod();
		if (super.vector[1].mod() > d)
			d = super.vector[1].mod();
		if (super.vector[2].mod() > d)
			d = super.vector[2].mod();
		return d;
	}

	public ComplexVector conjugate() {
		return new Complex3Vector(super.vector[0].conjugate(), super.vector[1].conjugate(), super.vector[2].conjugate());
	}

	public MathVector add(MathVector mathvector) {
		if (mathvector instanceof Complex3Vector)
			return add((Complex3Vector)mathvector);
		if (mathvector instanceof Double3Vector)
			return add((Double3Vector)mathvector);
		if (mathvector instanceof Integer3Vector)
			return add((Integer3Vector)mathvector);
		else
			throw new IllegalArgumentException("Vector class not recognised by this method.");
	}

	public Complex3Vector add(Complex3Vector complex3vector) {
		return new Complex3Vector(super.vector[0].add(((ComplexVector) (complex3vector)).vector[0]), super.vector[1].add(((ComplexVector) (complex3vector)).vector[1]), super.vector[2].add(((ComplexVector) (complex3vector)).vector[2]));
	}

	public Complex3Vector add(Double3Vector double3vector) {
		return new Complex3Vector(super.vector[0].addReal(((DoubleVector) (double3vector)).vector[0]), super.vector[1].addReal(((DoubleVector) (double3vector)).vector[1]), super.vector[2].addReal(((DoubleVector) (double3vector)).vector[2]));
	}

	public Complex3Vector add(Integer3Vector integer3vector) {
		return new Complex3Vector(super.vector[0].addReal(((IntegerVector) (integer3vector)).vector[0]), super.vector[1].addReal(((IntegerVector) (integer3vector)).vector[1]), super.vector[2].addReal(((IntegerVector) (integer3vector)).vector[2]));
	}

	public MathVector subtract(MathVector mathvector) {
		if (mathvector instanceof Complex3Vector)
			return subtract((Complex3Vector)mathvector);
		if (mathvector instanceof Double3Vector)
			return subtract((Double3Vector)mathvector);
		if (mathvector instanceof Integer3Vector)
			return subtract((Integer3Vector)mathvector);
		else
			throw new IllegalArgumentException("Vector class not recognised by this method.");
	}

	public Complex3Vector subtract(Complex3Vector complex3vector) {
		return new Complex3Vector(super.vector[0].subtract(((ComplexVector) (complex3vector)).vector[0]), super.vector[1].subtract(((ComplexVector) (complex3vector)).vector[1]), super.vector[2].subtract(((ComplexVector) (complex3vector)).vector[2]));
	}

	public Complex3Vector subtract(Double3Vector double3vector) {
		return new Complex3Vector(super.vector[0].subtractReal(((DoubleVector) (double3vector)).vector[0]), super.vector[1].subtractReal(((DoubleVector) (double3vector)).vector[1]), super.vector[2].subtractReal(((DoubleVector) (double3vector)).vector[2]));
	}

	public Complex3Vector subtract(Integer3Vector integer3vector) {
		return new Complex3Vector(super.vector[0].subtractReal(((IntegerVector) (integer3vector)).vector[0]), super.vector[1].subtractReal(((IntegerVector) (integer3vector)).vector[1]), super.vector[2].subtractReal(((IntegerVector) (integer3vector)).vector[2]));
	}

	public ComplexVector scalarMultiply(Complex complex) {
		return new Complex3Vector(super.vector[0].multiply(complex), super.vector[1].multiply(complex), super.vector[2].multiply(complex));
	}

	public ComplexVector scalarMultiply(double d) {
		return new Complex3Vector(super.vector[0].multiply(d), super.vector[1].multiply(d), super.vector[2].multiply(d));
	}

	public ComplexVector scalarDivide(Complex complex) {
		return new Complex3Vector(super.vector[0].divide(complex), super.vector[1].divide(complex), super.vector[2].divide(complex));
	}

	public ComplexVector scalarDivide(double d) {
		return new Complex3Vector(super.vector[0].divide(d), super.vector[1].divide(d), super.vector[2].divide(d));
	}

	public Complex scalarProduct(Complex3Vector complex3vector) {
		return new Complex(super.vector[0].real() * ((ComplexVector) (complex3vector)).vector[0].real() + super.vector[0].imag() * ((ComplexVector) (complex3vector)).vector[0].imag() + super.vector[1].real() * ((ComplexVector) (complex3vector)).vector[1].real() + super.vector[1].imag() * ((ComplexVector) (complex3vector)).vector[1].imag() + super.vector[2].real() * ((ComplexVector) (complex3vector)).vector[2].real() + super.vector[2].imag() * ((ComplexVector) (complex3vector)).vector[2].imag(), ((((super.vector[0].imag() * ((ComplexVector) (complex3vector)).vector[0].real() - super.vector[0].real() * ((ComplexVector) (complex3vector)).vector[0].imag()) + super.vector[1].imag() * ((ComplexVector) (complex3vector)).vector[1].real()) - super.vector[1].real() * ((ComplexVector) (complex3vector)).vector[1].imag()) + super.vector[2].imag() * ((ComplexVector) (complex3vector)).vector[2].real()) - super.vector[2].real() * ((ComplexVector) (complex3vector)).vector[2].imag());
	}

	public Complex3Vector multiply(Complex3Vector complex3vector) {
		return new Complex3Vector(super.vector[1].multiply(((ComplexVector) (complex3vector)).vector[2]).subtract(((ComplexVector) (complex3vector)).vector[1].multiply(super.vector[2])), super.vector[2].multiply(((ComplexVector) (complex3vector)).vector[0]).subtract(((ComplexVector) (complex3vector)).vector[2].multiply(super.vector[0])), super.vector[0].multiply(((ComplexVector) (complex3vector)).vector[1]).subtract(((ComplexVector) (complex3vector)).vector[0].multiply(super.vector[1])));
	}

	public ComplexVector mapComponents(Mapping mapping) {
		return new Complex3Vector(mapping.map(super.vector[0]), mapping.map(super.vector[1]), mapping.map(super.vector[2]));
	}
}
