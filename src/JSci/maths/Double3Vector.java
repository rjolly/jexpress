package JSci.maths;

import JSci.GlobalSettings;

// Referenced classes of package JSci.maths:
//			DoubleVector, Complex, Complex3Vector, Integer3Vector, 
//			Mapping, VectorDimensionException, IntegerVector, ComplexVector, 
//			MathVector

public class Double3Vector extends DoubleVector {

	public Double3Vector() {
		super(3);
	}

	public Double3Vector(double d, double d1, double d2) {
		super(3);
		super.vector[0] = d;
		super.vector[1] = d1;
		super.vector[2] = d2;
	}

	public boolean equals(Object obj) {
		if (obj != null && (obj instanceof Double3Vector)) {
			Double3Vector double3vector = (Double3Vector)obj;
			return Math.abs(super.vector[0] - ((DoubleVector) (double3vector)).vector[0]) <= GlobalSettings.ZERO_TOL && Math.abs(super.vector[1] - ((DoubleVector) (double3vector)).vector[1]) <= GlobalSettings.ZERO_TOL && Math.abs(super.vector[2] - ((DoubleVector) (double3vector)).vector[2]) <= GlobalSettings.ZERO_TOL;
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

	public IntegerVector toIntegerVector() {
		return new Integer3Vector(Math.round((float)super.vector[0]), Math.round((float)super.vector[1]), Math.round((float)super.vector[2]));
	}

	public ComplexVector toComplexVector() {
		return new Complex3Vector(new Complex(super.vector[0], 0.0D), new Complex(super.vector[1], 0.0D), new Complex(super.vector[2], 0.0D));
	}

	public double getComponent(int i) {
		if (i >= 0 && i < 3)
			return super.vector[i];
		else
			throw new VectorDimensionException("Invalid component.");
	}

	public void setComponent(int i, double d) {
		if (i >= 0 && i < 3) {
			super.vector[i] = d;
			return;
		} else {
			throw new VectorDimensionException("Invalid component.");
		}
	}

	public int dimension() {
		return 3;
	}

	public double norm(int i) {
		double d = Math.pow(super.vector[0], i) + Math.pow(super.vector[1], i) + Math.pow(super.vector[2], i);
		return Math.pow(d, 1.0D / (double)i);
	}

	public double norm() {
		double d = super.vector[0] * super.vector[0] + super.vector[1] * super.vector[1] + super.vector[2] * super.vector[2];
		return Math.sqrt(d);
	}

	public double infNorm() {
		double d = super.vector[0];
		if (super.vector[1] > d)
			d = super.vector[1];
		if (super.vector[2] > d)
			d = super.vector[2];
		return d;
	}

	public MathVector add(MathVector mathvector) {
		if (mathvector instanceof Double3Vector)
			return add((Double3Vector)mathvector);
		else
			throw new IllegalArgumentException("Vector class not recognised by this method.");
	}

	public Double3Vector add(Double3Vector double3vector) {
		return new Double3Vector(super.vector[0] + ((DoubleVector) (double3vector)).vector[0], super.vector[1] + ((DoubleVector) (double3vector)).vector[1], super.vector[2] + ((DoubleVector) (double3vector)).vector[2]);
	}

	public MathVector subtract(MathVector mathvector) {
		if (mathvector instanceof Double3Vector)
			return subtract((Double3Vector)mathvector);
		else
			throw new IllegalArgumentException("Vector class not recognised by this method.");
	}

	public Double3Vector subtract(Double3Vector double3vector) {
		return new Double3Vector(super.vector[0] - ((DoubleVector) (double3vector)).vector[0], super.vector[1] - ((DoubleVector) (double3vector)).vector[1], super.vector[2] - ((DoubleVector) (double3vector)).vector[2]);
	}

	public DoubleVector scalarMultiply(double d) {
		return new Double3Vector(d * super.vector[0], d * super.vector[1], d * super.vector[2]);
	}

	public DoubleVector scalarDivide(double d) {
		return new Double3Vector(super.vector[0] / d, super.vector[1] / d, super.vector[2] / d);
	}

	public double scalarProduct(Double3Vector double3vector) {
		return super.vector[0] * ((DoubleVector) (double3vector)).vector[0] + super.vector[1] * ((DoubleVector) (double3vector)).vector[1] + super.vector[2] * ((DoubleVector) (double3vector)).vector[2];
	}

	public Double3Vector multiply(Double3Vector double3vector) {
		return new Double3Vector(super.vector[1] * ((DoubleVector) (double3vector)).vector[2] - ((DoubleVector) (double3vector)).vector[1] * super.vector[2], super.vector[2] * ((DoubleVector) (double3vector)).vector[0] - ((DoubleVector) (double3vector)).vector[2] * super.vector[0], super.vector[0] * ((DoubleVector) (double3vector)).vector[1] - ((DoubleVector) (double3vector)).vector[0] * super.vector[1]);
	}

	public DoubleVector mapComponents(Mapping mapping) {
		return new Double3Vector(mapping.map(super.vector[0]), mapping.map(super.vector[1]), mapping.map(super.vector[2]));
	}
}
