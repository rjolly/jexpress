package tom.graphic.ThreeD;

import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.io.PrintStream;

public class View3DBeanArrInfo extends SimpleBeanInfo {

	public View3DBeanArrInfo() {
	}

	public PropertyDescriptor[] getPropertyDescriptors() {
		try {
			PropertyDescriptor propertydescriptor = new PropertyDescriptor("objFromFile", tom.graphic.ThreeD.View3DBean.class);
			propertydescriptor.setPropertyEditorClass(tom.graphic.BeanUtils.FileNameEditor.class);
			PropertyDescriptor propertydescriptor1 = new PropertyDescriptor("backgIma", tom.graphic.ThreeD.View3DBean.class);
			propertydescriptor1.setPropertyEditorClass(tom.graphic.BeanUtils.ImageEditor.class);
			PropertyDescriptor propertydescriptor2 = new PropertyDescriptor("backgMode", tom.graphic.ThreeD.View3DBean.class);
			propertydescriptor2.setPropertyEditorClass(tom.graphic.ThreeD.View3DBeanBackgEditor.class);
			PropertyDescriptor apropertydescriptor[] = {
				propertydescriptor, propertydescriptor1, propertydescriptor2
			};
			return apropertydescriptor;
		}
		catch (Exception exception) {
			System.err.println("View3DBeanInfo: unexpected exeption: " + exception);
		}
		return null;
	}
}
