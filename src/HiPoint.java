import java.util.Vector;

class HiPoint {

	public int dimension;
	public double hicoords[];
	public double x;
	public double y;
	public int onscreen_x;
	public int onscreen_y;
	public int cls;

	HiPoint(Vector vector) {
		hicoords = new double[vector.size() - 1];
		for (dimension = 0; dimension < vector.size() - 1; dimension++)
			hicoords[dimension] = ((Double)vector.elementAt(dimension)).doubleValue();

		cls = ((Double)vector.elementAt(dimension)).intValue();
	}

	public String toString() {
		StringBuffer stringbuffer = new StringBuffer();
		stringbuffer.append("{");
		for (int i = 0; i != dimension; i++)
			stringbuffer.append(hicoords[i] + ",");

		stringbuffer.append(cls + "}");
		return stringbuffer.toString();
	}
}
