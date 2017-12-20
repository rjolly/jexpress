package JSci.maths;

import JSci.GlobalSettings;

// Referenced classes of package JSci.maths:
//			MathNumber, ExtraMath, MathDouble, MathInteger

public final class Complex extends MathNumber {

	private double re;
	private double im;
	public static final Complex I = new Complex(0.0D, 1.0D);
	public static final Complex ONE = new Complex(1.0D, 0.0D);
	public static final Complex ZERO = new Complex(0.0D, 0.0D);
	public static final Complex MINUS_ONE = new Complex(-1D, 0.0D);
	public static final Complex MINUS_I = new Complex(0.0D, -1D);
	private static final Complex PI = new Complex(3.1415926535897931D, 0.0D);
	private static final Complex PI_I = new Complex(0.0D, 3.1415926535897931D);
	private static final Complex PI_2 = new Complex(1.5707963267948966D, 0.0D);
	private static final Complex MINUS_PI_2 = new Complex(-1.5707963267948966D, 0.0D);
	private static final Complex PI_2_I = new Complex(0.0D, 1.5707963267948966D);
	private static final Complex MINUS_PI_2_I = new Complex(0.0D, -1.5707963267948966D);

	public Complex(double d, double d1) {
		re = d;
		im = d1;
	}

	public Complex(String s) throws NumberFormatException {
		int i = s.indexOf('i');
		if (i == -1) {
			re = Double.valueOf(s).doubleValue();
			im = 0.0D;
			return;
		}
		int j = s.indexOf('+');
		if (j == -1)
			j = s.indexOf('-');
		String s1;
		if (j == -1) {
			re = 0.0D;
			s1 = s;
		} else
		if (i < j) {
			s1 = s.substring(0, j);
			re = Double.valueOf(s.substring(j + 1)).doubleValue();
		} else {
			re = Double.valueOf(s.substring(0, j)).doubleValue();
			s1 = s.substring(j + 1);
		}
		if (s1.startsWith("i")) {
			im = Double.valueOf(s1.substring(1)).doubleValue();
			return;
		}
		if (s1.endsWith("i")) {
			im = Double.valueOf(s1.substring(0, s1.length() - 1)).doubleValue();
			return;
		} else {
			throw new NumberFormatException("The imaginary part should have 'i' as a prefix or suffix.");
		}
	}

	public static Complex polar(double d, double d1) {
		return new Complex(d * Math.cos(d1), d * Math.sin(d1));
	}

	public boolean equals(Object obj) {
		if (obj != null && (obj instanceof Complex)) {
			Complex complex = (Complex)obj;
			return Math.abs(re - complex.re) <= GlobalSettings.ZERO_TOL && Math.abs(im - complex.im) <= GlobalSettings.ZERO_TOL;
		} else {
			return false;
		}
	}

	public String toString() {
		StringBuffer stringbuffer = new StringBuffer();
		stringbuffer.append(re);
		if (im >= 0.0D)
			stringbuffer.append("+");
		stringbuffer.append(im);
		stringbuffer.append("i");
		return stringbuffer.toString();
	}

	public int hashCode() {
		return (int)Math.exp(mod());
	}

	public boolean isNaN() {
		return re == (0.0D / 0.0D) || im == (0.0D / 0.0D);
	}

	public boolean isInfinite() {
		return re == (1.0D / 0.0D) || re == (-1.0D / 0.0D) || im == (1.0D / 0.0D) || im == (-1.0D / 0.0D);
	}

	public double real() {
		return re;
	}

	public double imag() {
		return im;
	}

	public double mod() {
		if (re == 0.0D && im == 0.0D)
			return 0.0D;
		double d = Math.abs(re);
		double d1 = Math.abs(im);
		if (d < d1)
			return d1 * Math.sqrt(1.0D + (re / im) * (re / im));
		else
			return d * Math.sqrt(1.0D + (im / re) * (im / re));
	}

	public double arg() {
		if (re == 0.0D) {
			if (im > 0.0D)
				return 1.5707963267948966D;
			return im >= 0.0D ? 0.0D : -1.5707963267948966D;
		}
		if (re < 0.0D) {
			if (im >= 0.0D)
				return Math.atan(im / re) + 3.1415926535897931D;
			else
				return Math.atan(im / re) - 3.1415926535897931D;
		} else {
			return Math.atan(im / re);
		}
	}

	public Complex negate() {
		return new Complex(-re, -im);
	}

	public Complex conjugate() {
		return new Complex(re, -im);
	}

	public MathNumber add(MathNumber mathnumber) {
		if (mathnumber instanceof Complex)
			return add((Complex)mathnumber);
		if (mathnumber instanceof MathDouble)
			return addReal(((MathDouble)mathnumber).value());
		if (mathnumber instanceof MathInteger)
			return addReal(((MathInteger)mathnumber).value());
		else
			throw new IllegalArgumentException("Number class not recognised by this method.");
	}

	public Complex add(Complex complex) {
		return new Complex(re + complex.re, im + complex.im);
	}

	public Complex addReal(double d) {
		return new Complex(re + d, im);
	}

	public Complex addImag(double d) {
		return new Complex(re, im + d);
	}

	public MathNumber subtract(MathNumber mathnumber) {
		if (mathnumber instanceof Complex)
			return subtract((Complex)mathnumber);
		if (mathnumber instanceof MathDouble)
			return subtractReal(((MathDouble)mathnumber).value());
		if (mathnumber instanceof MathInteger)
			return subtractReal(((MathInteger)mathnumber).value());
		else
			throw new IllegalArgumentException("Number class not recognised by this method.");
	}

	public Complex subtract(Complex complex) {
		return new Complex(re - complex.re, im - complex.im);
	}

	public Complex subtractReal(double d) {
		return new Complex(re - d, im);
	}

	public Complex subtractImag(double d) {
		return new Complex(re, im - d);
	}

	public MathNumber multiply(MathNumber mathnumber) {
		if (mathnumber instanceof Complex)
			return multiply((Complex)mathnumber);
		if (mathnumber instanceof MathDouble)
			return multiply(((MathDouble)mathnumber).value());
		if (mathnumber instanceof MathInteger)
			return multiply(((MathInteger)mathnumber).value());
		else
			throw new IllegalArgumentException("Number class not recognised by this method.");
	}

	public Complex multiply(Complex complex) {
		return new Complex(re * complex.re - im * complex.im, re * complex.im + im * complex.re);
	}

	public Complex multiply(double d) {
		return new Complex(d * re, d * im);
	}

	public MathNumber divide(MathNumber mathnumber) {
		if (mathnumber instanceof Complex)
			return divide((Complex)mathnumber);
		if (mathnumber instanceof MathDouble)
			return divide(((MathDouble)mathnumber).value());
		if (mathnumber instanceof MathInteger)
			return divide(((MathInteger)mathnumber).value());
		else
			throw new IllegalArgumentException("Number class not recognised by this method.");
	}

	public Complex divide(Complex complex) {
		double d;
		double d1;
		double d2;
		if (Math.abs(complex.re) < Math.abs(complex.im)) {
			double d3 = complex.re / complex.im;
			d = complex.re * d3 + complex.im;
			d1 = re * d3 + im;
			d2 = im * d3 - re;
		} else {
			double d4 = complex.im / complex.re;
			d = complex.re + complex.im * d4;
			d1 = re + im * d4;
			d2 = im - re * d4;
		}
		return new Complex(d1 / d, d2 / d);
	}

	public Complex divide(double d) {
		return new Complex(re / d, im / d);
	}

	public Complex pow(Complex complex) {
		double d = mod();
		double d1 = arg();
		double d2 = Math.pow(d, complex.re) / Math.exp(d1 * complex.im);
		double d3 = d1 * complex.re + Math.log(d) * complex.im;
		return polar(d2, d3);
	}

	public Complex pow(double d) {
		return polar(Math.pow(mod(), d), arg() * d);
	}

	public Complex sqr() {
		return new Complex(re * re - im * im, 2D * re * im);
	}

	public Complex sqrt() {
		return polar(Math.pow(mod(), 0.5D), arg() * 0.5D);
	}

	public static Complex exp(Complex complex) {
		return new Complex(Math.exp(complex.re) * Math.cos(complex.im), Math.exp(complex.re) * Math.sin(complex.im));
	}

	public static Complex log(Complex complex) {
		return new Complex(Math.log(complex.mod()), complex.arg());
	}

	public static Complex sin(Complex complex) {
		return new Complex(Math.sin(complex.re) * ExtraMath.cosh(complex.im), Math.cos(complex.re) * ExtraMath.sinh(complex.im));
	}

	public static Complex cos(Complex complex) {
		return new Complex(Math.cos(complex.re) * ExtraMath.cosh(complex.im), -Math.sin(complex.re) * ExtraMath.sinh(complex.im));
	}

	public static Complex tan(Complex complex) {
		return sin(complex).divide(cos(complex));
	}

	public static Complex sinh(Complex complex) {
		return new Complex(ExtraMath.sinh(complex.re) * Math.cos(complex.im), ExtraMath.cosh(complex.re) * Math.sin(complex.im));
	}

	public static Complex cosh(Complex complex) {
		return new Complex(ExtraMath.cosh(complex.re) * Math.cos(complex.im), ExtraMath.sinh(complex.re) * Math.sin(complex.im));
	}

	public static Complex tanh(Complex complex) {
		return sinh(complex).divide(cosh(complex));
	}

	public static Complex asin(Complex complex) {
		if (complex.equals(ONE))
			return PI_2;
		if (complex.equals(MINUS_ONE))
			return MINUS_PI_2;
		else
			return atan(complex.divide(ONE.subtract(complex.sqr()).sqrt()));
	}

	public static Complex acos(Complex complex) {
		if (complex.equals(ONE))
			return ZERO;
		if (complex.equals(MINUS_ONE))
			return PI;
		else
			return atan(complex.negate().divide(ONE.subtract(complex.sqr()).sqrt())).addReal(1.5707963267948966D);
	}

	public static Complex atan(Complex complex) {
		return MINUS_I.multiply(atanh(I.multiply(complex)));
	}

	public static Complex asinh(Complex complex) {
		if (complex.equals(I))
			return PI_2_I;
		if (complex.equals(MINUS_I))
			return MINUS_PI_2_I;
		else
			return log(complex.add(complex.sqr().addReal(1.0D).sqrt()));
	}

	public static Complex acosh(Complex complex) {
		if (complex.equals(ONE))
			return ZERO;
		if (complex.equals(MINUS_ONE))
			return PI_I;
		else
			return log(complex.add(complex.sqr().subtractReal(1.0D).sqrt()));
	}

	public static Complex atanh(Complex complex) {
		return log(complex.addReal(1.0D).divide(complex.subtractReal(1.0D).negate())).divide(2D);
	}

}
