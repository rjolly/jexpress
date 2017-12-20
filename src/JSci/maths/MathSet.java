package JSci.maths;


public interface MathSet {

	public abstract MathSet union(MathSet mathset);

	public abstract MathSet intersect(MathSet mathset);
}
