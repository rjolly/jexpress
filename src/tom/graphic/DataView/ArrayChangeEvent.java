package tom.graphic.DataView;

import java.util.EventObject;

// Referenced classes of package tom.graphic.DataView:
//			Array

public class ArrayChangeEvent extends EventObject {

	int pos[];
	float theValue;

	public ArrayChangeEvent(Array array, int ai[], float f) {
		super(array);
		pos = ai;
		theValue = f;
	}
}
