package JSci.maths;

import JSci.GlobalSettings;

// Referenced classes of package JSci.maths:
//			MathNumber, MathInteger

public final class MathDouble extends MathNumber {

	private double x;

	public MathDouble(double d) {
		x = d;
	}

	public MathDouble(String s) throws NumberFormatException {
		x = Double.valueOf(s).doubleValue();
	}

	public boolean equals(Object obj) {
		if (obj != null && (obj instanceof MathDouble))
			return Math.abs(x - ((MathDouble)obj).value()) <= GlobalSettings.ZERO_TOL;
		else
			return false;
	}

	public String toString() {
		return Double.toString(x);
	}

	public double value() {
		return x;
	}

	public boolean isNaN() {
		return x == (0.0D / 0.0D);
	}

	public boolean isInfinite() {
		return x == (1.0D / 0.0D) || x == (-1.0D / 0.0D);
	}

	public MathNumber add(MathNumber mathnumber) {
		if (mathnumber instanceof MathDouble)
			return add((MathDouble)mathnumber);
		if (mathnumber instanceof MathInteger)
			return add(new MathDouble(((MathInteger)mathnumber).value()));
		else
			throw new IllegalArgumentException("Number class not recognised by this method.");
	}

	public MathDouble add(MathDouble mathdouble) {
		return new MathDouble(x + mathdouble.value());
	}

	public MathNumber subtract(MathNumber mathnumber) {
		if (mathnumber instanceof MathDouble)
			return subtract((MathDouble)mathnumber);
		if (mathnumber instanceof MathInteger)
			return subtract(new MathDouble(((MathInteger)mathnumber).value()));
		else
			throw new IllegalArgumentException("Number class not recognised by this method.");
	}

	public MathDouble subtract(MathDouble mathdouble) {
		return new MathDouble(x - mathdouble.value());
	}

	public MathNumber multiply(MathNumber mathnumber) {
		if (mathnumber instanceof MathDouble)
			return multiply((MathDouble)mathnumber);
		if (mathnumber instanceof MathInteger)
			return multiply(new MathDouble(((MathInteger)mathnumber).value()));
		else
			throw new IllegalArgumentException("Number class not recognised by this method.");
	}

	public MathDouble multiply(MathDouble mathdouble) {
		return new MathDouble(x * mathdouble.value());
	}

	public MathNumber divide(MathNumber mathnumber) {
		if (mathnumber instanceof MathDouble)
			return divide((MathDouble)mathnumber);
		if (mathnumber instanceof MathInteger)
			return divide(new MathDouble(((MathInteger)mathnumber).value()));
		else
			throw new IllegalArgumentException("Number class not recognised by this method.");
	}

	public MathDouble divide(MathDouble mathdouble) {
		return new MathDouble(x / mathdouble.value());
	}
}
