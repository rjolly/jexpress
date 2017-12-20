package JSci.maths;


// Referenced classes of package JSci.maths:
//			MathSet

public class DiscreteSet
	implements MathSet {

	private String label;
	protected Object elements[];

	protected DiscreteSet(String s) {
		label = s;
	}

	public DiscreteSet(String s, Object aobj[]) {
		this(s);
		elements = new Object[aobj.length];
		System.arraycopy(((Object) (aobj)), 0, ((Object) (elements)), 0, aobj.length);
	}

	public boolean equals(Object obj) {
		return obj != null && (obj instanceof DiscreteSet) && label.equals(obj.toString());
	}

	public final String toString() {
		return label;
	}

	public final int size() {
		return elements.length;
	}

	public final Object getElement(int i) {
		return elements[i];
	}

	public MathSet union(MathSet mathset) {
		if (!(mathset instanceof DiscreteSet))
			throw new IllegalArgumentException("Must be a discrete set.");
		DiscreteSet discreteset = (DiscreteSet)mathset;
		Object aobj1[] = new Object[elements.length + discreteset.size()];
		int i;
		if (elements.length > discreteset.size()) {
			for (i = 0; i < discreteset.size(); i++)
				aobj1[i] = discreteset.getElement(i);

			for (int j = 0; j < elements.length; j++) {
				boolean flag = true;
				for (int l = 0; l < discreteset.size(); l++)
					if (elements[j].equals(aobj1[l]))
						flag = false;

				if (flag)
					aobj1[i++] = elements[j];
			}

		} else {
			for (i = 0; i < elements.length; i++)
				aobj1[i] = elements[i];

			for (int k = 0; k < discreteset.size(); k++) {
				boolean flag1 = true;
				for (int i1 = 0; i1 < elements.length; i1++)
					if (discreteset.getElement(k).equals(aobj1[i1]))
						flag1 = false;

				if (flag1)
					aobj1[i++] = discreteset.getElement(k);
			}

		}
		Object aobj[] = new Object[i];
		System.arraycopy(((Object) (aobj1)), 0, ((Object) (aobj)), 0, aobj.length);
		return new DiscreteSet("(" + toString() + ") U (" + discreteset.toString() + ")", aobj);
	}

	public MathSet intersect(MathSet mathset) {
		if (!(mathset instanceof DiscreteSet))
			throw new IllegalArgumentException("Must be a discrete set.");
		DiscreteSet discreteset = (DiscreteSet)mathset;
		int i = 0;
		Object aobj[];
		if (elements.length > discreteset.size()) {
			aobj = new Object[elements.length];
			for (int j = 0; j < elements.length; j++) {
				boolean flag = true;
				for (int l = 0; l < discreteset.size() && flag; l++)
					if (elements[j].equals(discreteset.getElement(l))) {
						aobj[i++] = elements[j];
						flag = false;
					}

			}

		} else {
			aobj = new Object[discreteset.size()];
			for (int k = 0; k < discreteset.size(); k++) {
				boolean flag1 = true;
				for (int i1 = 0; i1 < elements.length && flag1; i1++)
					if (discreteset.getElement(k).equals(elements[i1])) {
						aobj[i++] = elements[i1];
						flag1 = false;
					}

			}

		}
		Object aobj1[] = new Object[i];
		System.arraycopy(((Object) (aobj)), 0, ((Object) (aobj1)), 0, aobj1.length);
		return new DiscreteSet("(" + toString() + ") I (" + discreteset.toString() + ")", aobj1);
	}
}
