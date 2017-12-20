package JSci.maths;


// Referenced classes of package JSci.maths:
//			IntegerVector, Complex, Complex3Vector, Double3Vector, 
//			VectorDimensionException, DoubleVector, ComplexVector, MathVector

public class Integer3Vector extends IntegerVector {

	public Integer3Vector() {
		super(3);
	}

	public Integer3Vector(int i, int j, int k) {
		super(3);
		super.vector[0] = i;
		super.vector[1] = j;
		super.vector[2] = k;
	}

	public boolean equals(Object obj) {
		if (obj != null && (obj instanceof Integer3Vector)) {
			Integer3Vector integer3vector = (Integer3Vector)obj;
			return super.vector[0] == ((IntegerVector) (integer3vector)).vector[0] && super.vector[1] == ((IntegerVector) (integer3vector)).vector[1] && super.vector[2] == ((IntegerVector) (integer3vector)).vector[2];
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

	public DoubleVector toDoubleVector() {
		return new Double3Vector(super.vector[0], super.vector[1], super.vector[2]);
	}

	public ComplexVector toComplexVector() {
		return new Complex3Vector(new Complex(super.vector[0], 0.0D), new Complex(super.vector[1], 0.0D), new Complex(super.vector[2], 0.0D));
	}

	public int getComponent(int i) {
		if (i >= 0 && i < 3)
			return super.vector[i];
		else
			throw new VectorDimensionException("Invalid component.");
	}

	public void setComponent(int i, int j) {
		if (i >= 0 && i < 3) {
			super.vector[i] = j;
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
		int i = super.vector[0] * super.vector[0] + super.vector[1] * super.vector[1] + super.vector[2] * super.vector[2];
		return Math.sqrt(i);
	}

	public double infNorm() {
		int i = super.vector[0];
		if (super.vector[1] > i)
			i = super.vector[1];
		if (super.vector[2] > i)
			i = super.vector[2];
		return (double)i;
	}

	public MathVector add(MathVector mathvector) {
		if (mathvector instanceof Integer3Vector)
			return add((Integer3Vector)mathvector);
		else
			throw new IllegalArgumentException("Vector class not recognised by this method.");
	}

	public Integer3Vector add(Integer3Vector integer3vector) {
		return new Integer3Vector(super.vector[0] + ((IntegerVector) (integer3vector)).vector[0], super.vector[1] + ((IntegerVector) (integer3vector)).vector[1], super.vector[2] + ((IntegerVector) (integer3vector)).vector[2]);
	}

	public MathVector subtract(MathVector mathvector) {
		if (mathvector instanceof Integer3Vector)
			return subtract((Integer3Vector)mathvector);
		else
			throw new IllegalArgumentException("Vector class not recognised by this method.");
	}

	public Integer3Vector subtract(Integer3Vector integer3vector) {
		return new Integer3Vector(super.vector[0] - ((IntegerVector) (integer3vector)).vector[0], super.vector[1] - ((IntegerVector) (integer3vector)).vector[1], super.vector[2] - ((IntegerVector) (integer3vector)).vector[2]);
	}

	public IntegerVector scalarMultiply(int i) {
		return new Integer3Vector(i * super.vector[0], i * super.vector[1], i * super.vector[2]);
	}

	public int scalarProduct(Integer3Vector integer3vector) {
		return super.vector[0] * ((IntegerVector) (integer3vector)).vector[0] + super.vector[1] * ((IntegerVector) (integer3vector)).vector[1] + super.vector[2] * ((IntegerVector) (integer3vector)).vector[2];
	}

	public Integer3Vector multiply(Integer3Vector integer3vector) {
		return new Integer3Vector(super.vector[1] * ((IntegerVector) (integer3vector)).vector[2] - ((IntegerVector) (integer3vector)).vector[1] * super.vector[2], super.vector[2] * ((IntegerVector) (integer3vector)).vector[0] - ((IntegerVector) (integer3vector)).vector[2] * super.vector[0], super.vector[0] * ((IntegerVector) (integer3vector)).vector[1] - ((IntegerVector) (integer3vector)).vector[0] * super.vector[1]);
	}
}
