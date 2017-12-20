package tom.graphic.ThreeD;


public class Point3D {

	public float x;
	public float y;
	public float z;

	public Point3D() {
	}

	public Point3D(float f, float f1, float f2) {
		x = f;
		y = f1;
		z = f2;
	}

	public void add(Point3D point3d) {
		x += point3d.x;
		y += point3d.y;
		z += point3d.z;
	}

	public float getLength() {
		return (float)Math.sqrt(x * x + y * y + z * z);
	}

	void normalize() {
		float f = getLength();
		x = x / f;
		y = y / f;
		z = z / f;
	}

	public void prodVect(Point3D point3d, Point3D point3d1) {
		x = point3d.z * point3d1.y - point3d.y * point3d1.z;
		y = point3d.x * point3d1.z - point3d.z * point3d1.x;
		z = point3d.y * point3d1.x - point3d.x * point3d1.y;
	}

	public void sub(Point3D point3d) {
		x -= point3d.x;
		y -= point3d.y;
		z -= point3d.z;
	}

	public static Point3D sub(Point3D point3d, Point3D point3d1) {
		Point3D point3d2 = new Point3D();
		point3d2.x = point3d1.x - point3d.x;
		point3d2.y = point3d1.y - point3d.y;
		point3d2.z = point3d1.z - point3d.z;
		return point3d2;
	}

	public synchronized String toString() {
		return "x:" + x + ";y:" + y + ";z:" + z;
	}
}
