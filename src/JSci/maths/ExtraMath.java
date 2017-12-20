package JSci.maths;


// Referenced classes of package JSci.maths:
//			AbstractMath, SpecialMath

public final class ExtraMath extends AbstractMath {

	private ExtraMath() {
	}

	public static double factorial(double d) {
		return SpecialMath.gamma(d + 1.0D);
	}

	public static double logFactorial(double d) {
		return SpecialMath.logGamma(d + 1.0D);
	}

	public static double binomial(double d, double d1) {
		return Math.exp(SpecialMath.logGamma(d + 1.0D) - SpecialMath.logGamma(d1 + 1.0D) - SpecialMath.logGamma((d - d1) + 1.0D));
	}

	public static double sinh(double d) {
		return (Math.exp(d) - Math.exp(-d)) / 2D;
	}

	public static double cosh(double d) {
		return (Math.exp(d) + Math.exp(-d)) / 2D;
	}

	public static double tanh(double d) {
		return sinh(d) / cosh(d);
	}

	public static double asinh(double d) {
		return Math.log(d + Math.sqrt(d * d + 1.0D));
	}

	public static double acosh(double d) {
		return Math.log(d + Math.sqrt(d * d - 1.0D));
	}

	public static double atanh(double d) {
		return Math.log((1.0D + d) / (1.0D - d)) / 2D;
	}
}
