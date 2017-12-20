package JSci.maths;

import JSci.GlobalSettings;

// Referenced classes of package JSci.maths:
//			DoubleVector, Mapping, MathVector, VectorDimensionException

public final class DoubleSparseVector extends DoubleVector {

	protected static final int SPARSE = 2;
	private int pos[];
	private int N;

	public DoubleSparseVector(int i) {
		super(0);
		pos = new int[0];
		N = i;
	}

	public DoubleSparseVector(double ad[]) {
		super(0);
		N = ad.length;
		int i = 0;
		for (int j = 0; j < ad.length; j++)
			if (ad[j] != 0.0D)
				i++;

		super.vector = new double[i];
		pos = new int[i];
		i = 0;
		for (int k = 0; k < ad.length; k++)
			if (ad[k] != 0.0D) {
				pos[i] = k;
				super.vector[i] = ad[k];
				i++;
			}

	}

	protected void finalize() throws Throwable {
		pos = null;
		super.finalize();
	}

	public boolean equals(Object obj) {
		if (obj != null && (obj instanceof DoubleSparseVector) && N == ((DoubleSparseVector)obj).N) {
			DoubleSparseVector doublesparsevector = (DoubleSparseVector)obj;
			if (pos.length != doublesparsevector.pos.length)
				return false;
			if (Math.abs(getComponent(0) - doublesparsevector.getComponent(0)) > GlobalSettings.ZERO_TOL)
				return false;
			for (int i = 1; i < N; i++)
				if (Math.abs(getComponent(i) - doublesparsevector.getComponent(i)) > GlobalSettings.ZERO_TOL)
					return false;

			return true;
		} else {
			return false;
		}
	}

	public String toString() {
		StringBuffer stringbuffer = new StringBuffer(N);
		int i;
		for (i = 0; i < N - 1; i++) {
			stringbuffer.append(getComponent(i));
			stringbuffer.append(',');
		}

		stringbuffer.append(getComponent(i));
		return stringbuffer.toString();
	}

	public double getComponent(int i) {
		if (i < 0 || i >= N)
			throw new VectorDimensionException("Invalid component.");
		for (int j = 0; j < pos.length; j++)
			if (pos[j] == i)
				return super.vector[j];

		return 0.0D;
	}

	public void setComponent(int i, double d) {
		if (i < 0 || i >= N)
			throw new VectorDimensionException("Invalid component.");
		if (Math.abs(d) <= GlobalSettings.ZERO_TOL)
			return;
		for (int j = 0; j < pos.length; j++)
			if (i == pos[j]) {
				super.vector[j] = d;
				return;
			}

		int ai[] = new int[pos.length + 1];
		double ad[] = new double[super.vector.length + 1];
		System.arraycopy(pos, 0, ai, 0, pos.length);
		System.arraycopy(super.vector, 0, ad, 0, pos.length);
		ai[pos.length] = i;
		ad[super.vector.length] = d;
		pos = ai;
		super.vector = ad;
	}

	public int dimension() {
		return N;
	}

	public void normalize() {
		double d = norm();
		for (int i = 0; i < pos.length; i++)
			super.vector[i] /= d;

	}

	public double norm() {
		return Math.sqrt(sumSquares());
	}

	public double sumSquares() {
		double d = 0.0D;
		for (int i = 0; i < pos.length; i++)
			d += super.vector[i] * super.vector[i];

		return d;
	}

	public double mass() {
		double d = 0.0D;
		for (int i = 0; i < pos.length; i++)
			d += super.vector[i];

		return d;
	}

	public MathVector add(MathVector mathvector) {
		if (mathvector instanceof DoubleVector)
			return add((DoubleVector)mathvector);
		else
			throw new IllegalArgumentException("Vector class not recognised by this method.");
	}

	public DoubleVector add(DoubleVector doublevector) {
		switch (MathVector.storageFormat) {
		case 2: // '\002'
			return add((DoubleSparseVector)doublevector);

		case 1: // '\001'
			return rawAdd(doublevector);
		}
		if (N != doublevector.dimension())
			throw new VectorDimensionException("Vectors are different sizes.");
		double ad[] = new double[N];
		ad[0] = doublevector.getComponent(0);
		for (int i = 1; i < N; i++)
			ad[i] = doublevector.getComponent(i);

		for (int j = 0; j < pos.length; j++)
			ad[pos[j]] += super.vector[j];

		return new DoubleVector(ad);
	}

	private DoubleVector rawAdd(DoubleVector doublevector) {
		if (N != doublevector.dimension())
			throw new VectorDimensionException("Vectors are different sizes.");
		double ad[] = new double[N];
		System.arraycopy(doublevector.vector, 0, ad, 0, N);
		for (int i = 0; i < pos.length; i++)
			ad[pos[i]] += super.vector[i];

		return new DoubleVector(ad);
	}

	public DoubleSparseVector add(DoubleSparseVector doublesparsevector) {
		if (N != doublesparsevector.N)
			throw new VectorDimensionException("Vectors are different sizes.");
		double ad[] = new double[N];
		for (int i = 0; i < pos.length; i++)
			ad[pos[i]] = super.vector[i] + doublesparsevector.getComponent(pos[i]);

		for (int k = 0; k < doublesparsevector.pos.length; k++) {
			int j = doublesparsevector.pos[k];
			ad[j] = getComponent(j) + ((DoubleVector) (doublesparsevector)).vector[k];
		}

		return new DoubleSparseVector(ad);
	}

	public MathVector subtract(MathVector mathvector) {
		if (mathvector instanceof DoubleVector)
			return subtract((DoubleVector)mathvector);
		else
			throw new IllegalArgumentException("Vector class not recognised by this method.");
	}

	public DoubleVector subtract(DoubleVector doublevector) {
		switch (MathVector.storageFormat) {
		case 2: // '\002'
			return subtract((DoubleSparseVector)doublevector);

		case 1: // '\001'
			return rawSubtract(doublevector);
		}
		if (N != doublevector.dimension())
			throw new VectorDimensionException("Vectors are different sizes.");
		double ad[] = new double[N];
		ad[0] = -doublevector.getComponent(0);
		for (int i = 1; i < N; i++)
			ad[i] = -doublevector.getComponent(i);

		for (int j = 0; j < pos.length; j++)
			ad[pos[j]] += super.vector[j];

		return new DoubleVector(ad);
	}

	private DoubleVector rawSubtract(DoubleVector doublevector) {
		if (N != doublevector.dimension())
			throw new VectorDimensionException("Vectors are different sizes.");
		double ad[] = new double[N];
		ad[0] = -doublevector.vector[0];
		for (int i = 1; i < N; i++)
			ad[i] = -doublevector.vector[i];

		for (int j = 0; j < pos.length; j++)
			ad[pos[j]] += super.vector[j];

		return new DoubleVector(ad);
	}

	public DoubleSparseVector subtract(DoubleSparseVector doublesparsevector) {
		if (N != doublesparsevector.N)
			throw new VectorDimensionException("Vectors are different sizes.");
		double ad[] = new double[N];
		for (int i = 0; i < pos.length; i++)
			ad[pos[i]] = super.vector[i] - doublesparsevector.getComponent(pos[i]);

		for (int k = 0; k < doublesparsevector.pos.length; k++) {
			int j = doublesparsevector.pos[k];
			ad[j] = getComponent(j) - ((DoubleVector) (doublesparsevector)).vector[k];
		}

		return new DoubleSparseVector(ad);
	}

	public DoubleVector scalarMultiply(double d) {
		DoubleSparseVector doublesparsevector = new DoubleSparseVector(N);
		doublesparsevector.vector = new double[super.vector.length];
		doublesparsevector.pos = new int[pos.length];
		System.arraycopy(pos, 0, doublesparsevector.pos, 0, pos.length);
		for (int i = 0; i < pos.length; i++)
			((DoubleVector) (doublesparsevector)).vector[i] = d * super.vector[i];

		return doublesparsevector;
	}

	public DoubleVector scalarDivide(double d) {
		DoubleSparseVector doublesparsevector = new DoubleSparseVector(N);
		doublesparsevector.vector = new double[super.vector.length];
		doublesparsevector.pos = new int[pos.length];
		System.arraycopy(pos, 0, doublesparsevector.pos, 0, pos.length);
		for (int i = 0; i < pos.length; i++)
			((DoubleVector) (doublesparsevector)).vector[i] = super.vector[i] / d;

		return doublesparsevector;
	}

	public double scalarProduct(DoubleVector doublevector) {
		switch (MathVector.storageFormat) {
		case 2: // '\002'
			return scalarProduct((DoubleSparseVector)doublevector);

		case 1: // '\001'
			return rawScalarProduct(doublevector);
		}
		if (N != doublevector.dimension())
			throw new VectorDimensionException("Vectors are different sizes.");
		double d = 0.0D;
		for (int i = 0; i < pos.length; i++)
			d += super.vector[i] * doublevector.getComponent(pos[i]);

		return d;
	}

	private double rawScalarProduct(DoubleVector doublevector) {
		if (N != doublevector.dimension())
			throw new VectorDimensionException("Vectors are different sizes.");
		double d = 0.0D;
		for (int i = 0; i < pos.length; i++)
			d += super.vector[i] * doublevector.vector[pos[i]];

		return d;
	}

	public double scalarProduct(DoubleSparseVector doublesparsevector) {
		if (N != doublesparsevector.N)
			throw new VectorDimensionException("Vectors are different sizes.");
		double d = 0.0D;
		if (pos.length <= doublesparsevector.pos.length) {
			for (int i = 0; i < pos.length; i++)
				d += super.vector[i] * doublesparsevector.getComponent(pos[i]);

		} else {
			for (int j = 0; j < doublesparsevector.pos.length; j++)
				d += getComponent(doublesparsevector.pos[j]) * ((DoubleVector) (doublesparsevector)).vector[j];

		}
		return d;
	}

	public DoubleVector mapComponents(Mapping mapping) {
		DoubleSparseVector doublesparsevector = new DoubleSparseVector(N);
		doublesparsevector.vector = new double[super.vector.length];
		doublesparsevector.pos = new int[pos.length];
		System.arraycopy(pos, 0, doublesparsevector.pos, 0, pos.length);
		for (int i = 0; i < pos.length; i++)
			((DoubleVector) (doublesparsevector)).vector[i] = mapping.map(super.vector[i]);

		return doublesparsevector;
	}

	static  {
		MathVector.storageFormat = 2;
	}
}
