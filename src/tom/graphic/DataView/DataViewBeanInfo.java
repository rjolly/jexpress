package tom.graphic.DataView;

import java.awt.Image;
import java.beans.SimpleBeanInfo;

public class DataViewBeanInfo extends SimpleBeanInfo {

	public DataViewBeanInfo() {
	}

	public Image getIcon(int i) {
		if (i == 1) {
			Image image = loadImage("DataViewIcon.gif");
			return image;
		}
		if (i == 2) {
			Image image1 = loadImage("DataViewIconBig.gif");
			return image1;
		} else {
			return null;
		}
	}
}
