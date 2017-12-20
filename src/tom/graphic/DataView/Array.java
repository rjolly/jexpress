package tom.graphic.DataView;

import java.util.Vector;

// Referenced classes of package tom.graphic.DataView:
//			ArrayChangeEvent, ArrayChangeListener

public class Array {

	Vector Listeners;
	int i;
	int j;
	int k;
	int ndim;
	int size;
	float max;
	float min;
	float tab[];

	public Array() {
		i = 10;
		j = 10;
		k = 1;
		size = 0;
		recomputeSize();
	}

	public Array(int l, int i1) {
		i = 10;
		j = 10;
		k = 1;
		size = 0;
		i = l;
		j = i1;
		recomputeSize();
	}

	public void addChangeListener(ArrayChangeListener arraychangelistener) {
		if (Listeners == null)
			Listeners = new Vector();
		Listeners.addElement(arraychangelistener);
	}

	public void dimI(int l) {
		i = l;
		recomputeSize();
	}

	public void dimJ(int l) {
		j = l;
		recomputeSize();
	}

	public float getIJ(int l, int i1) {
		if (l >= 0 && l < i && i1 >= 0 && i1 < j)
			return tab[i1 * i + l];
		else
			return 0.0F;
	}

	float getMax() {
		float f = tab[0];
		for (int l = 1; l < size; l++)
			if (tab[l] > f)
				f = tab[l];

		return f;
	}

	float getMin() {
		float f = tab[0];
		for (int l = 1; l < size; l++)
			if (tab[l] < f)
				f = tab[l];

		return f;
	}

	private void notifyChange(int ai[], float f) {
		ArrayChangeEvent arraychangeevent = new ArrayChangeEvent(this, ai, f);
		Vector vector;
		synchronized (this) {
			vector = (Vector)Listeners.clone();
		}
		if (Listeners != null) {
			for (int l = 0; l < vector.size(); l++) {
				ArrayChangeListener arraychangelistener = (ArrayChangeListener)vector.elementAt(l);
				arraychangelistener.elementChanged(arraychangeevent);
			}

		}
	}

	void recomputeSize() {
		tab = null;
		size = i * j * k;
		tab = new float[size];
	}

	public void removeChangeListener(ArrayChangeListener arraychangelistener) {
		Listeners.removeElement(arraychangelistener);
	}

	public void setIJ(int l, int i1, float f) {
		if (l >= 0 && l < i && i1 >= 0 && i1 < j)
			tab[i1 * i + l] = f;
		if (Listeners != null) {
			int ai[] = new int[2];
			ai[0] = l;
			ai[1] = i1;
			notifyChange(ai, f);
		}
	}
}
