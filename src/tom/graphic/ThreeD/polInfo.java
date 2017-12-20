package tom.graphic.ThreeD;


class polInfo {

	int NB_PT_POLY;
	int numPt;
	int x[];
	int y[];
	int oldPt;
	long Dx;
	long Dy;
	long X;

	void findNext() {
		int j = 0;
		int i = numPt - oldPt;
		do {
			numPt = numPt % NB_PT_POLY;
			Dy = y[numPt] - y[oldPt];
			if (Dy <= 0L) {
				oldPt = numPt;
				numPt = (numPt + i) % NB_PT_POLY;
			}
			j++;
		} while (Dy <= 0L && j < 4);
		if (Dy != 0L)
			Dx = ((long)(x[numPt] - x[oldPt]) << 8) / Dy;
		else
			Dx = 0L;
		X = ((long)x[oldPt] << 8) + 127L;
		if (Math.abs(Dx) > 256L)
			X += Dx / 2L;
	}

	polInfo() {
		NB_PT_POLY = 4;
	}
}
