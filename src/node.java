
class node {

	double xcor;
	double ycor;
	int memberindexes[];
	double vector[];
	node right;
	node left;
	node up;
	node down;
	double addedvector[];
	double additions;
	int mapx;
	int mapy;
	int mapz;

	public node(double ad[]) {
		additions = 1.0D;
		vector = ad;
	}

	public node(double ad[], double d, double d1) {
		additions = 1.0D;
		xcor = d;
		ycor = d1;
		addedvector = new double[ad.length];
		for (int i = 0; i < addedvector.length; i++)
			addedvector[i] = 0.0D;

		vector = ad;
	}

	public void addout() {
		for (int i = 0; i < addedvector.length; i++)
			addedvector[i] = addedvector[i] / additions;

	}

	public void clear() {
		additions = 1.0D;
		clearmembers();
		for (int i = 0; i < addedvector.length; i++)
			addedvector[i] = 0.0D;

	}

	public void clearmembers() {
		memberindexes = null;
	}

	public double dist(node node1) {
		double d = 0.0D;
		for (int i = 0; i < vector.length; i++)
			d += Math.pow(vector[i] - node1.vector[i], 2D);

		return Math.sqrt(d);
	}

	public void newmember(int i) {
		removemember(i);
		if (memberindexes == null) {
			memberindexes = new int[1];
			memberindexes[0] = i;
		} else {
			int ai[] = new int[memberindexes.length + 1];
			for (int j = 0; j < memberindexes.length; j++)
				ai[j] = memberindexes[j];

			ai[memberindexes.length] = i;
			memberindexes = ai;
		}
	}

	public void removemember(int i) {
		int j = -1;
		if (memberindexes != null) {
			for (int k = 0; k < memberindexes.length; k++)
				if (memberindexes[k] == i)
					j = k;

			if (j != -1) {
				int ai[] = new int[memberindexes.length - 1];
				for (int l = 0; l < j; l++)
					ai[l] = memberindexes[l];

				for (int i1 = j + 1; i1 < memberindexes.length; i1++)
					ai[i1 - 1] = memberindexes[i1];

				memberindexes = ai;
			}
		}
	}
}
