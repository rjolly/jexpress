package JSci.maths;

import JSci.maths.groups.AbelianGroup;

public interface Ring
	extends AbelianGroup {

	public abstract Object multiply(Object obj, Object obj1);
}
