import java.io.Serializable;

public class gen
	implements Serializable {

	gen right;
	gen left;
	double value;
	String name;
	int nme;
	int xcor;
	int ycor;
	boolean hasright;
	boolean hasleft;
	boolean merged;
	boolean last;

	public gen(double d, gen gen1, gen gen2) {
		hasright = false;
		hasleft = false;
		merged = false;
		last = true;
		value = d;
		merged = true;
		hasright = hasleft = true;
		last = false;
		right = gen1;
		left = gen2;
	}

	public gen(double d, String s) {
		hasright = false;
		hasleft = false;
		merged = false;
		last = true;
		value = d;
		name = s;
		merged = false;
	}

	public gen(double d, String s, boolean flag) {
		hasright = false;
		hasleft = false;
		merged = false;
		last = true;
		value = d;
		name = s;
		merged = flag;
	}

	public gen(int i) {
		hasright = false;
		hasleft = false;
		merged = false;
		last = true;
		value = i;
		merged = true;
	}

	public gen(int i, int j) {
		hasright = false;
		hasleft = false;
		merged = false;
		last = true;
		value = i;
		nme = j;
		name = String.valueOf(j);
	}

	public gen(gen gen1, gen gen2) {
		hasright = false;
		hasleft = false;
		merged = false;
		last = true;
		merged = true;
		last = false;
		right = gen1;
		left = gen2;
	}

	public gen(String s) {
		hasright = false;
		hasleft = false;
		merged = false;
		last = true;
		name = s;
		merged = false;
	}

	public double getval() {
		return value / 1000D;
	}
}
