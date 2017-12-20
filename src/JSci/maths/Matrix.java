package JSci.maths;

import java.io.Serializable;

// Referenced classes of package JSci.maths:
//			MathNumber

public abstract class Matrix
	implements Serializable {

	protected static final int CLASS_SPECIFIC = 0;
	protected static int storageFormat;

	public Matrix() {
	}

	public abstract int rows();

	public abstract int columns();

	public abstract Matrix add(Matrix matrix);

	public abstract Matrix subtract(Matrix matrix);

	public abstract Matrix multiply(Matrix matrix);

	public abstract Matrix transpose();

	public abstract Matrix scalarMultiply(MathNumber mathnumber);
}
