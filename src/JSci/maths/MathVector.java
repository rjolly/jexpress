package JSci.maths;

import java.io.Serializable;

// Referenced classes of package JSci.maths:
//			MathNumber

public abstract class MathVector
	implements Serializable {

	protected static final int CLASS_SPECIFIC = 0;
	protected static int storageFormat;

	public MathVector() {
	}

	public abstract double norm();

	public abstract int dimension();

	public abstract MathVector add(MathVector mathvector);

	public abstract MathVector subtract(MathVector mathvector);

	public abstract MathVector scalarMultiply(MathNumber mathnumber);
}
