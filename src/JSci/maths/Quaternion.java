package JSci.maths;

import JSci.GlobalSettings;

// Referenced classes of package JSci.maths:
//			MathNumber, Double3Vector, DoubleVector, MathDouble, 
//			MathInteger

public final class Quaternion extends MathNumber {

	private double re;
	private Double3Vector im;

	public Quaternion(double d, Double3Vector double3vector) {
		re = d;
		im = double3vector;
	}

	public Quaternion(double d, double d1, double d2, double d3) {
		re = d;
		im = new Double3Vector(d1, d2, d3);
	}

	public boolean equals(Object obj) {
		if (obj != null && (obj instanceof Quaternion)) {
			Quaternion quaternion = (Quaternion)obj;
			return Math.abs(re - quaternion.re) <= GlobalSettings.ZERO_TOL && im.equals(quaternion.im);
		} else {
			return false;
		}
	}

	public String toString() {
		StringBuffer stringbuffer = new StringBuffer();
		stringbuffer.append(re);
		if (((DoubleVector) (im)).vector[0] >= 0.0D)
			stringbuffer.append("+");
		stringbuffer.append(((DoubleVector) (im)).vector[0]);
		stringbuffer.append("i");
		if (((DoubleVector) (im)).vector[1] >= 0.0D)
			stringbuffer.append("+");
		stringbuffer.append(((DoubleVector) (im)).vector[1]);
		stringbuffer.append("j");
		if (((DoubleVector) (im)).vector[2] >= 0.0D)
			stringbuffer.append("+");
		stringbuffer.append(((DoubleVector) (im)).vector[2]);
		stringbuffer.append("k");
		return stringbuffer.toString();
	}

	public int hashCode() {
		return (int)Math.exp(mod());
	}

	public boolean isNaN() {
		return re == (0.0D / 0.0D) || ((DoubleVector) (im)).vector[0] == (0.0D / 0.0D) || ((DoubleVector) (im)).vector[1] == (0.0D / 0.0D) || ((DoubleVector) (im)).vector[2] == (0.0D / 0.0D);
	}

	public boolean isInfinite() {
		return re == (1.0D / 0.0D) || re == (-1.0D / 0.0D) || ((DoubleVector) (im)).vector[0] == (1.0D / 0.0D) || ((DoubleVector) (im)).vector[0] == (-1.0D / 0.0D) || ((DoubleVector) (im)).vector[1] == (1.0D / 0.0D) || ((DoubleVector) (im)).vector[1] == (-1.0D / 0.0D) || ((DoubleVector) (im)).vector[2] == (1.0D / 0.0D) || ((DoubleVector) (im)).vector[2] == (-1.0D / 0.0D);
	}

	public double real() {
		return re;
	}

	public Double3Vector imag() {
		return im;
	}

	public double mod() {
		double d = im.norm();
		if (re == 0.0D && d == 0.0D)
			return 0.0D;
		double d1 = Math.abs(re);
		double d2 = Math.abs(d);
		if (d1 < d2)
			return d2 * Math.sqrt(1.0D + (re / d) * (re / d));
		else
			return d1 * Math.sqrt(1.0D + (d / re) * (d / re));
	}

	public Quaternion conjugate() {
		return new Quaternion(re, -((DoubleVector) (im)).vector[0], -((DoubleVector) (im)).vector[1], -((DoubleVector) (im)).vector[2]);
	}

	public MathNumber add(MathNumber mathnumber) {
		if (mathnumber instanceof Quaternion)
			return add((Quaternion)mathnumber);
		if (mathnumber instanceof MathDouble)
			return addReal(((MathDouble)mathnumber).value());
		if (mathnumber instanceof MathInteger)
			return addReal(((MathInteger)mathnumber).value());
		else
			throw new IllegalArgumentException("Number class not recognised by this method.");
	}

	public Quaternion add(Quaternion quaternion) {
		return new Quaternion(re + quaternion.re, im.add(quaternion.im));
	}

	public Quaternion addReal(double d) {
		return new Quaternion(re + d, im);
	}

	public Quaternion addImag(Double3Vector double3vector) {
		return new Quaternion(re, im.add(double3vector));
	}

	public MathNumber subtract(MathNumber mathnumber) {
		if (mathnumber instanceof Quaternion)
			return subtract((Quaternion)mathnumber);
		if (mathnumber instanceof MathDouble)
			return subtractReal(((MathDouble)mathnumber).value());
		if (mathnumber instanceof MathInteger)
			return subtractReal(((MathInteger)mathnumber).value());
		else
			throw new IllegalArgumentException("Number class not recognised by this method.");
	}

	public Quaternion subtract(Quaternion quaternion) {
		return new Quaternion(re - quaternion.re, im.subtract(quaternion.im));
	}

	public Quaternion subtractReal(double d) {
		return new Quaternion(re - d, im);
	}

	public Quaternion subtractImag(Double3Vector double3vector) {
		return new Quaternion(re, im.subtract(double3vector));
	}

	public MathNumber multiply(MathNumber mathnumber) {
		if (mathnumber instanceof Quaternion)
			return multiply((Quaternion)mathnumber);
		if (mathnumber instanceof MathDouble)
			return multiply(((MathDouble)mathnumber).value());
		if (mathnumber instanceof MathInteger)
			return multiply(((MathInteger)mathnumber).value());
		else
			throw new IllegalArgumentException("Number class not recognised by this method.");
	}

	public Quaternion multiply(Quaternion quaternion) {
		return new Quaternion(re * quaternion.re - im.scalarProduct(quaternion.im), (Double3Vector)quaternion.im.scalarMultiply(re).add(im.scalarMultiply(quaternion.re)).add(im.multiply(quaternion.im)));
	}

	public Quaternion multiply(double d) {
		return new Quaternion(d * re, (Double3Vector)im.scalarMultiply(d));
	}

	public MathNumber divide(MathNumber mathnumber) {
		if (mathnumber instanceof Quaternion)
			return divide((Quaternion)mathnumber);
		if (mathnumber instanceof MathDouble)
			return divide(((MathDouble)mathnumber).value());
		if (mathnumber instanceof MathInteger)
			return divide(((MathInteger)mathnumber).value());
		else
			throw new IllegalArgumentException("Number class not recognised by this method.");
	}

	public Quaternion divide(Quaternion quaternion) {
		double d = quaternion.mod();
		return multiply(quaternion.conjugate()).divide(d * d);
	}

	public Quaternion divide(double d) {
		return new Quaternion(re / d, (Double3Vector)im.scalarDivide(d));
	}
}
