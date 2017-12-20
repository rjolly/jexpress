package JSci.maths;


// Referenced classes of package JSci.maths:
//			MathNumber, MathDouble

public final class MathInteger extends MathNumber {

	private int x;

	public MathInteger(int i) {
		x = i;
	}

	public MathInteger(String s) throws NumberFormatException {
		x = Integer.valueOf(s).intValue();
	}

	public boolean equals(Object obj) {
		if (obj != null && (obj instanceof MathInteger))
			return x == ((MathInteger)obj).value();
		else
			return false;
	}

	public String toString() {
		return Integer.toString(x);
	}

	public int value() {
		return x;
	}

	public MathNumber add(MathNumber mathnumber) {
		if (mathnumber instanceof MathInteger)
			return add((MathInteger)mathnumber);
		else
			throw new IllegalArgumentException("Number class not recognised by this method.");
	}

	public MathInteger add(MathInteger mathinteger) {
		return new MathInteger(x + mathinteger.value());
	}

	public MathNumber subtract(MathNumber mathnumber) {
		if (mathnumber instanceof MathInteger)
			return subtract((MathInteger)mathnumber);
		else
			throw new IllegalArgumentException("Number class not recognised by this method.");
	}

	public MathInteger subtract(MathInteger mathinteger) {
		return new MathInteger(x - mathinteger.value());
	}

	public MathNumber multiply(MathNumber mathnumber) {
		if (mathnumber instanceof MathInteger)
			return multiply((MathInteger)mathnumber);
		else
			throw new IllegalArgumentException("Number class not recognised by this method.");
	}

	public MathInteger multiply(MathInteger mathinteger) {
		return new MathInteger(x * mathinteger.value());
	}

	public MathNumber divide(MathNumber mathnumber) {
		if (mathnumber instanceof MathInteger)
			return divide((MathInteger)mathnumber);
		else
			throw new IllegalArgumentException("Number class not recognised by this method.");
	}

	public MathDouble divide(MathInteger mathinteger) {
		return new MathDouble(x / mathinteger.value());
	}
}
