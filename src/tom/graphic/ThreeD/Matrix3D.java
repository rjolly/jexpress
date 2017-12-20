package tom.graphic.ThreeD;


// Referenced classes of package tom.graphic.ThreeD:
//			Point3D

public class Matrix3D {

	float xx;
	float xy;
	float xz;
	float xo;
	float yx;
	float yy;
	float yz;
	float yo;
	float zx;
	float zy;
	float zz;
	float zo;
	static final double pi = 3.1415926500000002D;

	public Matrix3D() {
		unit();
	}

	public Object clone() {
		Matrix3D matrix3d = new Matrix3D();
		matrix3d.xx = xx;
		matrix3d.xy = xy;
		matrix3d.xz = xz;
		matrix3d.xo = xo;
		matrix3d.yx = yx;
		matrix3d.yy = yy;
		matrix3d.yz = yz;
		matrix3d.yo = zo;
		matrix3d.zx = zx;
		matrix3d.zy = zy;
		matrix3d.zz = zz;
		matrix3d.zo = zo;
		return matrix3d;
	}

	public void mult(Matrix3D matrix3d) {
		float f = xx * matrix3d.xx + yx * matrix3d.xy + zx * matrix3d.xz;
		float f1 = xy * matrix3d.xx + yy * matrix3d.xy + zy * matrix3d.xz;
		float f2 = xz * matrix3d.xx + yz * matrix3d.xy + zz * matrix3d.xz;
		float f3 = xo * matrix3d.xx + yo * matrix3d.xy + zo * matrix3d.xz + matrix3d.xo;
		float f4 = xx * matrix3d.yx + yx * matrix3d.yy + zx * matrix3d.yz;
		float f5 = xy * matrix3d.yx + yy * matrix3d.yy + zy * matrix3d.yz;
		float f6 = xz * matrix3d.yx + yz * matrix3d.yy + zz * matrix3d.yz;
		float f7 = xo * matrix3d.yx + yo * matrix3d.yy + zo * matrix3d.yz + matrix3d.yo;
		float f8 = xx * matrix3d.zx + yx * matrix3d.zy + zx * matrix3d.zz;
		float f9 = xy * matrix3d.zx + yy * matrix3d.zy + zy * matrix3d.zz;
		float f10 = xz * matrix3d.zx + yz * matrix3d.zy + zz * matrix3d.zz;
		float f11 = xo * matrix3d.zx + yo * matrix3d.zy + zo * matrix3d.zz + matrix3d.zo;
		xx = f;
		xy = f1;
		xz = f2;
		xo = f3;
		yx = f4;
		yy = f5;
		yz = f6;
		yo = f7;
		zx = f8;
		zy = f9;
		zz = f10;
		zo = f11;
	}

	public void rotMat(Point3D point3d) {
		xrot(point3d.x);
		yrot(point3d.y);
		zrot(point3d.z);
	}

	public void scale(float f) {
		xx *= f;
		xy *= f;
		xz *= f;
		xo *= f;
		yx *= f;
		yy *= f;
		yz *= f;
		yo *= f;
		zx *= f;
		zy *= f;
		zz *= f;
		zo *= f;
	}

	public void scale(float f, float f1, float f2) {
		xx *= f;
		xy *= f;
		xz *= f;
		xo *= f;
		yx *= f1;
		yy *= f1;
		yz *= f1;
		yo *= f1;
		zx *= f2;
		zy *= f2;
		zz *= f2;
		zo *= f2;
	}

	public void setFromArray(float af[][]) {
		xx = af[0][0];
		xy = af[1][0];
		xz = af[2][0];
		xo = af[3][0];
		yx = af[0][1];
		yy = af[1][1];
		yz = af[2][1];
		yo = af[3][1];
		yx = af[0][2];
		yy = af[1][2];
		yz = af[2][2];
		yo = af[3][2];
	}

	public String toString() {
		return "[" + xo + "," + xx + "," + xy + "," + xz + ";" + yo + "," + yx + "," + yy + "," + yz + ";" + zo + "," + zx + "," + zy + "," + zz + "]";
	}

	public void transform(Point3D apoint3d[], Point3D apoint3d1[], int i) {
		float f = xx;
		float f1 = xy;
		float f2 = xz;
		float f3 = xo;
		float f4 = yx;
		float f5 = yy;
		float f6 = yz;
		float f7 = yo;
		float f8 = zx;
		float f9 = zy;
		float f10 = zz;
		float f11 = zo;
		for (int j = i - 1; j >= 0; j--) {
			float f12 = apoint3d[j].x;
			float f13 = apoint3d[j].y;
			float f14 = apoint3d[j].z;
			apoint3d1[j].x = f12 * f + f13 * f1 + f14 * f2 + f3;
			apoint3d1[j].y = f12 * f4 + f13 * f5 + f14 * f6 + f7;
			apoint3d1[j].z = f12 * f8 + f13 * f9 + f14 * f10 + f11;
		}

	}

	public void transform(Point3D point3d, Point3D point3d1) {
		float f = xx;
		float f1 = xy;
		float f2 = xz;
		float f3 = xo;
		float f4 = yx;
		float f5 = yy;
		float f6 = yz;
		float f7 = yo;
		float f8 = zx;
		float f9 = zy;
		float f10 = zz;
		float f11 = zo;
		float f12 = point3d.x;
		float f13 = point3d.y;
		float f14 = point3d.z;
		point3d1.x = f12 * f + f13 * f1 + f14 * f2 + f3;
		point3d1.y = f12 * f4 + f13 * f5 + f14 * f6 + f7;
		point3d1.z = f12 * f8 + f13 * f9 + f14 * f10 + f11;
	}

	public void translate(float f, float f1, float f2) {
		xo += f;
		yo += f1;
		zo += f2;
	}

	public void translate(Point3D point3d) {
		xo += point3d.x;
		yo += point3d.y;
		zo += point3d.z;
	}

	public void unit() {
		xo = 0.0F;
		xx = 1.0F;
		xy = 0.0F;
		xz = 0.0F;
		yo = 0.0F;
		yx = 0.0F;
		yy = 1.0F;
		yz = 0.0F;
		zo = 0.0F;
		zx = 0.0F;
		zy = 0.0F;
		zz = 1.0F;
	}

	public void xrot(double d) {
		d *= 0.017453292500000002D;
		double d1 = Math.cos(d);
		double d2 = Math.sin(d);
		float f = (float)((double)yx * d1 + (double)zx * d2);
		float f1 = (float)((double)yy * d1 + (double)zy * d2);
		float f2 = (float)((double)yz * d1 + (double)zz * d2);
		float f3 = (float)((double)yo * d1 + (double)zo * d2);
		float f4 = (float)((double)zx * d1 - (double)yx * d2);
		float f5 = (float)((double)zy * d1 - (double)yy * d2);
		float f6 = (float)((double)zz * d1 - (double)yz * d2);
		float f7 = (float)((double)zo * d1 - (double)yo * d2);
		yo = f3;
		yx = f;
		yy = f1;
		yz = f2;
		zo = f7;
		zx = f4;
		zy = f5;
		zz = f6;
	}

	public void yrot(double d) {
		d *= 0.017453292500000002D;
		double d1 = Math.cos(d);
		double d2 = Math.sin(d);
		float f = (float)((double)xx * d1 + (double)zx * d2);
		float f1 = (float)((double)xy * d1 + (double)zy * d2);
		float f2 = (float)((double)xz * d1 + (double)zz * d2);
		float f3 = (float)((double)xo * d1 + (double)zo * d2);
		float f4 = (float)((double)zx * d1 - (double)xx * d2);
		float f5 = (float)((double)zy * d1 - (double)xy * d2);
		float f6 = (float)((double)zz * d1 - (double)xz * d2);
		float f7 = (float)((double)zo * d1 - (double)xo * d2);
		xo = f3;
		xx = f;
		xy = f1;
		xz = f2;
		zo = f7;
		zx = f4;
		zy = f5;
		zz = f6;
	}

	public void zrot(double d) {
		d *= 0.017453292500000002D;
		double d1 = Math.cos(d);
		double d2 = Math.sin(d);
		float f = (float)((double)yx * d1 + (double)xx * d2);
		float f1 = (float)((double)yy * d1 + (double)xy * d2);
		float f2 = (float)((double)yz * d1 + (double)xz * d2);
		float f3 = (float)((double)yo * d1 + (double)xo * d2);
		float f4 = (float)((double)xx * d1 - (double)yx * d2);
		float f5 = (float)((double)xy * d1 - (double)yy * d2);
		float f6 = (float)((double)xz * d1 - (double)yz * d2);
		float f7 = (float)((double)xo * d1 - (double)yo * d2);
		yo = f3;
		yx = f;
		yy = f1;
		yz = f2;
		xo = f7;
		xx = f4;
		xy = f5;
		xz = f6;
	}
}
