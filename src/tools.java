import java.io.PrintStream;

public class tools {

	public tools() {
	}

	public double abs(double d) {
		return Math.abs(d);
	}

	public int abs(int i) {
		return Math.abs(i);
	}

	public int calcnextdist(double d, int i, int j, int k) {
		int l = 0;
		double d1 = d;
		double d2 = i;
		double d3 = j;
		double d4 = k;
		double d5 = round(d1 / d2);
		l = intval((d5 / d4) * d3);
		return l;
	}

	public int calcnextstep(int i, int j, int k, int l) {
		int i1 = 0;
		double d = i;
		double d1 = j;
		double d2 = k;
		double d3 = l;
		double d4 = round(d1) / d3;
		i1 = intval(d + d4 * d2);
		return i1;
	}

	public double divideint(int i, int j) {
		double d = i;
		double d1 = j;
		return d / d1;
	}

	public int idivideint(int i, int j) {
		return intval(divideint(i, j));
	}

	public int imid(double d, double d1) {
		double d2 = 0.0D;
		d2 = d + d1;
		d2 /= 2D;
		return intval(d2);
	}

	public int intval(double d) {
		Long long1 = new Long(Math.round(d));
		return long1.intValue();
	}

	public int intval(String s) {
		Integer integer = new Integer(s);
		return integer.intValue();
	}

	public static void main(String args[]) {
		tools tools1 = new tools();
	}

	public double mid(double d, double d1) {
		double d2 = 0.0D;
		d2 = d + d1;
		d2 /= 2D;
		return d2;
	}

	public int norm(int i, int j, int k) {
		double d = i;
		double d1 = k;
		double d2 = j;
		return intval((d / d1) * d2);
	}

	public void print(double d) {
		System.out.print(String.valueOf(d));
	}

	public void print(int i) {
		System.out.print(String.valueOf(i));
	}

	public void print(String s) {
		System.out.print(s);
	}

	public void println(double d) {
		System.out.print(String.valueOf(d) + "\n");
	}

	public void println(int i) {
		System.out.print(String.valueOf(i) + "\n");
	}

	public void println(String s) {
		System.out.print(s + "\n");
	}

	public double round(double d) {
		Long long1 = new Long(Math.round(d));
		return long1.doubleValue();
	}

	public double trunkdist(double d, double d1) {
		double d2 = 0.0D;
		if (d < d1) {
			double d3 = d;
			d = d1;
			d1 = d3;
		}
		return abs(d - d1);
	}
}
