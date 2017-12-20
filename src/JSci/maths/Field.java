package JSci.maths;


// Referenced classes of package JSci.maths:
//			Ring

public interface Field
	extends Ring {

	public abstract Object unit();

	public abstract Object inverse(Object obj);

	public abstract boolean isUnit(Object obj);

	public abstract boolean isInverse(Object obj, Object obj1);
}
