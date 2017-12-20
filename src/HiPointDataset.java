import java.awt.TextField;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StreamTokenizer;
import java.util.Vector;

class HiPointDataset extends Vector {

	public double mins[];
	public double maxs[];
	public double normalized_averages[];
	public double normalized_variances[];
	public double averages[];
	public double variances[];
	public int dimensions;
	public int best_one;
	public int second_one;
	double D2data[];
	double distXi[];
	int N;
	int nrPoints;
	double sumDistXi;

	HiPointDataset(String s) throws IOException, IllegalFormatException {
		sumDistXi = 0.0D;
		FileInputStream fileinputstream = new FileInputStream(s);
		read_dataset(fileinputstream);
		fileinputstream.close();
	}

	HiPointDataset(double ad[][]) throws IOException, IllegalFormatException {
		sumDistXi = 0.0D;
		read_dataset(ad);
	}

	HiPointDataset(int ai[][]) throws IOException, IllegalFormatException {
		sumDistXi = 0.0D;
		read_dataset(ai);
	}

	void compute_dimension_stats() throws IllegalFormatException {
		dimensions = myElementAt(0).hicoords.length;
		mins = new double[dimensions];
		maxs = new double[dimensions];
		normalized_averages = new double[dimensions];
		normalized_variances = new double[dimensions];
		averages = new double[dimensions];
		variances = new double[dimensions];
		for (int i = 0; i != dimensions; i++) {
			mins[i] = myElementAt(0).hicoords[i];
			maxs[i] = myElementAt(0).hicoords[i];
			averages[i] = myElementAt(0).hicoords[i];
			variances[i] = 0.0D;
			normalized_variances[i] = 0.0D;
		}

		for (int j = 1; j < size(); j++) {
			if (dimensions != myElementAt(j).hicoords.length)
				throw new IllegalFormatException();
			for (int i1 = 0; i1 != dimensions; i1++) {
				double d = myElementAt(j).hicoords[i1];
				if (d < mins[i1])
					mins[i1] = d;
				if (d > maxs[i1])
					maxs[i1] = d;
				averages[i1] += d;
			}

		}

		best_one = 0;
		second_one = 0;
		for (int j1 = 0; j1 != dimensions; j1++) {
			averages[j1] = averages[j1] / (double)size();
			normalized_averages[j1] = (averages[j1] - mins[j1]) / (maxs[j1] - mins[j1]);
		}

		for (int k1 = 0; k1 != dimensions; k1++) {
			for (int k = 0; k < size(); k++) {
				double d1 = myElementAt(k).hicoords[k1] - averages[k1];
				variances[k1] += d1 * d1;
				d1 = (myElementAt(k).hicoords[k1] - mins[k1]) / (maxs[k1] - mins[k1]) - normalized_averages[k1];
				normalized_variances[k1] += d1 * d1;
			}

			variances[k1] = variances[k1] / (double)size();
			normalized_variances[k1] = normalized_variances[k1] / (double)size();
		}

		for (int l1 = 0; l1 != dimensions; l1++)
			if (normalized_variances[l1] >= normalized_variances[best_one]) {
				second_one = best_one;
				best_one = l1;
			} else
			if (normalized_variances[l1] >= normalized_variances[second_one])
				second_one = l1;

		for (int l = 0; l != size(); l++) {
			myElementAt(l).x = myElementAt(l).hicoords[best_one];
			myElementAt(l).y = myElementAt(l).hicoords[second_one];
		}

	}

	double dist(double ad[], double ad1[]) {
		boolean flag = false;
		double d = 0.0D;
		for (int i = 0; i < ad.length; i++)
			d += Math.pow(ad[i] - ad1[i], 2D);

		return Math.sqrt(d);
	}

	int dx_ij(int i, int j, int k) {
		int l = 0;
		if (i < j) {
			for (int j1 = 1; j1 < i + 1; j1++)
				l += k - j1;

			l += j - i - 1;
			return l;
		}
		if (i > j) {
			int i1 = dx_ij(j, i, k);
			return i1;
		} else {
			return -1;
		}
	}

	public double iterate_sammon(TextField textfield, TextField textfield1, TextField textfield2, TextField textfield3) {
		double ad[] = new double[2];
		double ad1[] = new double[2];
		double d = 0.0D;
		double d2 = 0.0D;
		double d3 = 0.29999999999999999D;
		double d4 = 0.0D;
		double d6 = 0.0D;
		double d8 = 0.0D;
		double d10 = 0.0D;
		if (D2data == null) {
			D2data = new double[2 * nrPoints];
			N = 0;
			for (int i = 1; i <= nrPoints; i++)
				N += i - 1;

			distXi = new double[N];
			StringBuffer stringbuffer = new StringBuffer();
			int i5 = 0;
			for (int j = 0; j < nrPoints - 1; j++) {
				boolean _tmp = nrPoints <= 80 || j % (1 + nrPoints / 80) == 0;
				for (int i2 = j + 1; i2 < nrPoints; i2++) {
					distXi[i5] = dist(myElementAt(j).hicoords, myElementAt(i2).hicoords);
					if (distXi[i5] == 0.0D)
						distXi[i5] = 9.9999999999999995E-008D;
					sumDistXi += distXi[i5];
					i5++;
				}

			}

		}
		double ad2[] = new double[N];
		for (int k = 0; k < D2data.length; k++)
			D2data[k] = k % 2 != 0 ? myElementAt(k / 2).y : myElementAt(k / 2).x;

		int j5 = 0;
		StringBuffer stringbuffer1 = new StringBuffer();
		for (int l = 0; l < nrPoints - 1; l++) {
			boolean _tmp1 = nrPoints <= 80 || l % (1 + nrPoints / 80) == 0;
			int i3 = posIntwoDData(l);
			int i4 = 0;
			for (; i3 < posIntwoDData(l) + 2; i3++) {
				ad[i4] = D2data[i3];
				i4++;
			}

			for (int j2 = l + 1; j2 < nrPoints; j2++) {
				int j3 = posIntwoDData(j2);
				int j4 = 0;
				for (; j3 < posIntwoDData(j2) + 2; j3++) {
					ad1[j4] = D2data[j3];
					j4++;
				}

				ad2[j5] = dist(ad, ad1);
				if (ad2[j5] == 0.0D)
					ad2[j5] = 9.9999999999999995E-008D;
				j5++;
			}

		}

		stringbuffer1 = new StringBuffer();
		for (int i1 = 0; i1 < distXi.length; i1++) {
			int _tmp2 = i1 % (distXi.length / 80);
			d2 += Math.pow(distXi[i1] - ad2[i1], 2D) / distXi[i1];
		}

		double d1 = d = (1.0D / sumDistXi) * d2;
		d10 = -2D / sumDistXi;
		stringbuffer1 = new StringBuffer();
		d6 = 0.0D;
		d8 = 0.0D;
		d4 = 0.0D;
		for (int k5 = 0; k5 < nrPoints; k5++) {
			boolean _tmp3 = nrPoints <= 80 || k5 % (1 + nrPoints / 80) == 0;
			for (int l5 = 0; l5 < 2; l5++) {
				double d7 = 0.0D;
				double d9 = 0.0D;
				for (int k2 = 0; k2 < nrPoints; k2++)
					if (k2 != k5) {
						d7 += ((distXi[dx_ij(k5, k2, nrPoints)] - ad2[dx_ij(k5, k2, nrPoints)]) / (distXi[dx_ij(k5, k2, nrPoints)] * ad2[dx_ij(k5, k2, nrPoints)])) * (D2data[matrix2arrayPos(k5, l5, 2)] - D2data[matrix2arrayPos(k2, l5, 2)]);
						d9 += (1.0D / (distXi[dx_ij(k5, k2, nrPoints)] * ad2[dx_ij(k5, k2, nrPoints)])) * (distXi[dx_ij(k5, k2, nrPoints)] - ad2[dx_ij(k5, k2, nrPoints)] - (Math.pow(D2data[matrix2arrayPos(k5, l5, 2)] - D2data[matrix2arrayPos(k2, l5, 2)], 2D) / ad2[dx_ij(k5, k2, nrPoints)]) * (1.0D + (distXi[dx_ij(k5, k2, nrPoints)] - ad2[dx_ij(k5, k2, nrPoints)]) / ad2[dx_ij(k5, k2, nrPoints)]));
					}

				double d5 = (d10 * d7) / Math.abs(d10 * d9);
				D2data[matrix2arrayPos(k5, l5, 2)] -= d3 * d5;
			}

		}

		j5 = 0;
		for (int j1 = 0; j1 < nrPoints - 1; j1++) {
			int k3 = posIntwoDData(j1);
			int k4 = 0;
			for (; k3 < posIntwoDData(j1) + 2; k3++) {
				ad[k4] = D2data[k3];
				k4++;
			}

			for (int l2 = j1 + 1; l2 < nrPoints; l2++) {
				int l3 = posIntwoDData(l2);
				int l4 = 0;
				for (; l3 < posIntwoDData(l2) + 2; l3++) {
					ad1[l4] = D2data[l3];
					l4++;
				}

				ad2[j5] = dist(ad, ad1);
				j5++;
			}

		}

		d2 = 0.0D;
		for (int k1 = 0; k1 < distXi.length; k1++)
			d2 += Math.pow(distXi[k1] - ad2[k1], 2D) / distXi[k1];

		d = (1.0D / sumDistXi) * d2;
		for (int l1 = 0; l1 < D2data.length; l1 += 2) {
			myElementAt(l1 / 2).x = D2data[l1];
			myElementAt(l1 / 2).y = D2data[l1 + 1];
		}

		return d;
	}

	int matrix2arrayPos(int i, int j, int k) {
		return i * k + j;
	}

	HiPoint myElementAt(int i) {
		return (HiPoint)super.elementAt(i);
	}

	int posIntwoDData(int i) {
		return i * 2;
	}

	void read_dataset(InputStream inputstream) throws IOException, IllegalFormatException {
		Vector vector = new Vector();
		StreamTokenizer streamtokenizer = new StreamTokenizer(inputstream);
		streamtokenizer.parseNumbers();
		streamtokenizer.slashStarComments(true);
		streamtokenizer.eolIsSignificant(true);
		while (streamtokenizer.nextToken() != -1) 
			if (streamtokenizer.ttype == 10) {
				addElement(new HiPoint(vector));
				vector = new Vector();
			} else
			if (streamtokenizer.ttype == -2)
				vector.addElement(new Double(streamtokenizer.nval));
		D2data = null;
		N = -1;
		nrPoints = size();
		compute_dimension_stats();
	}

	void read_dataset(double ad[][]) throws IOException, IllegalFormatException {
		Vector vector = new Vector();
		for (int i = 0; i < ad.length; i++) {
			for (int j = 0; j < ad[i].length; j++)
				vector.addElement(new Double(ad[i][j]));

			addElement(new HiPoint(vector));
			vector = new Vector();
		}

		D2data = null;
		N = -1;
		nrPoints = size();
		compute_dimension_stats();
	}

	void read_dataset(int ai[][]) throws IOException, IllegalFormatException {
		Vector vector = new Vector();
		for (int i = 0; i < ai.length; i++) {
			addElement(new HiPoint(vector));
			vector = new Vector();
			for (int j = 0; j < ai[i].length; j++)
				vector.addElement(new Double(ai[i][j]));

		}

		D2data = null;
		N = -1;
		nrPoints = size();
		compute_dimension_stats();
	}
}
