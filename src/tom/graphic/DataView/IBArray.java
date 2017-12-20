package tom.graphic.DataView;

import java.io.PrintStream;
import javax.infobus.ArrayAccess;
import javax.infobus.DataItemAddedEvent;
import javax.infobus.DataItemChangeEvent;
import javax.infobus.DataItemChangeListener;
import javax.infobus.DataItemChangeManager;
import javax.infobus.DataItemDeletedEvent;
import javax.infobus.DataItemRevokedEvent;
import javax.infobus.DataItemValueChangedEvent;
import javax.infobus.ImmediateAccess;
import javax.infobus.RowsetCursorMovedEvent;

// Referenced classes of package tom.graphic.DataView:
//			Array

public class IBArray extends Array
	implements DataItemChangeListener {

	Object theDataItem;

	public void dataItemAdded(DataItemAddedEvent evt) {
		System.out.println("Data item addedd" + evt);
	}

	public void dataItemDeleted(DataItemDeletedEvent evt) {
		System.out.println("Data item addedd" + evt);
	}

	public void dataItemRevoked(DataItemRevokedEvent evt) {
		System.out.println("Data item revoked" + evt);
	}

	public void dataItemValueChanged(DataItemValueChangedEvent evt) {
		System.out.println("Data item value changed" + evt.getChangedItem());
		dataItemAvailable(theDataItem, evt.getChangedItem());
	}

	public void rowsetCursorMoved(RowsetCursorMovedEvent evt) {
		System.out.println("rowset cursor moved" + evt);
	}

	public void dataItemAvailable(Object inDataItem, Object objectChanged) {
		if (inDataItem instanceof ArrayAccess) {
			int level = 0;
			ArrayAccess aa = (ArrayAccess)inDataItem;
			int coords[] = aa.getDimensions();
			System.out.println(level + "ArrayAccess.getDimensions()->=" + coords.length);
			for (int i = 0; i < coords.length; i++)
				System.out.println(level + "dim[" + i + "]=" + coords[i]);

			try {
				if (coords.length == 1) {
					int xCoord[] = new int[1];
					dimI(coords[0]);
					dimJ(1);
					for (int x = 0; x < coords[0]; x++) {
						xCoord[0] = x;
						Object subitem = aa.getItemByCoordinates(xCoord);
					}

				} else
				if (coords.length == 2) {
					int xyCoord[] = new int[2];
					dimI(coords[0]);
					dimJ(coords[1]);
					for (int x = 0; x < coords[0]; x++) {
						for (int y = 0; y < coords[1]; y++) {
							xyCoord[0] = x;
							xyCoord[1] = y;
							Object subitem = aa.getItemByCoordinates(xyCoord);
							ImmediateAccess ia = (ImmediateAccess)subitem;
							Object obj = ia.getValueAsObject();
							if (obj instanceof Float) {
								float f = ((Float)obj).floatValue();
								System.out.println(" ij:" + x + " " + y + " " + f);
								setIJ(x, y, f);
							} else
							if (obj instanceof Double) {
								float f = ((Double)obj).floatValue();
								System.out.println(" ij:" + x + " " + y + " " + f);
								setIJ(x, y, f);
							}
						}

					}

				} else
				if (coords.length == 3) {
					int xyzCoord[] = new int[3];
					if (coords[2] == 0) {
						int z = 0;
						for (int x = 0; x < coords[0]; x++) {
							for (int y = 0; y < coords[1]; y++) {
								xyzCoord[0] = x;
								xyzCoord[1] = y;
								xyzCoord[2] = z;
								Object subitem = aa.getItemByCoordinates(xyzCoord);
								ImmediateAccess ia = (ImmediateAccess)subitem;
							}

						}

					} else {
						for (int x = 0; x < coords[0]; x++) {
							for (int y = 0; y < coords[1]; y++) {
								for (int z = 0; z < coords[2]; z++) {
									xyzCoord[0] = x;
									xyzCoord[1] = y;
									xyzCoord[2] = z;
									Object subitem = aa.getItemByCoordinates(xyzCoord);
								}

							}

						}

					}
				} else {
					System.out.println((level + coords.length) + " dimensional array not supported");
				}
			}
			catch (Exception _ex) { }
		}
		if ((inDataItem instanceof DataItemChangeManager) && theDataItem == null)
			((DataItemChangeManager)inDataItem).addDataItemChangeListener(this);
		theDataItem = inDataItem;
	}

	public IBArray() {
	}
}
