package tom.graphic.ThreeD;

import java.awt.Image;
import java.beans.BeanInfo;
import java.beans.SimpleBeanInfo;

// Referenced classes of package tom.graphic.ThreeD:
//			View3DBeanArrInfo

public class View3DBeanBeanInfo extends SimpleBeanInfo {

	public View3DBeanBeanInfo() {
	}

	public BeanInfo[] getAdditionalBeanInfo() {
		BeanInfo abeaninfo[] = new BeanInfo[1];
		abeaninfo[0] = new View3DBeanArrInfo();
		return abeaninfo;
	}

	public Image getIcon(int i) {
		if (i == 1) {
			Image image = loadImage("View3DIcon.gif");
			return image;
		}
		if (i == 2) {
			Image image1 = loadImage("View3DIconBig.gif");
			return image1;
		} else {
			return null;
		}
	}
}
