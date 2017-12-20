package JSci.maths;

import java.io.Serializable;

public abstract class MathNumber
	implements Serializable {

	public MathNumber() {
	}

	public abstract MathNumber add(MathNumber mathnumber);

	public abstract MathNumber subtract(MathNumber mathnumber);

	public abstract MathNumber multiply(MathNumber mathnumber);

	public abstract MathNumber divide(MathNumber mathnumber);
}
