package tom.graphic.ThreeD;

import java.beans.PropertyEditorSupport;

public class View3DBeanBackgEditor extends PropertyEditorSupport {

	static String result[] = {
		"None", "Backg Centered", "Backg Tiled", "Backg Stretched", "Backg Directed"
	};

	public String getAsText() {
		return result[((Integer)getValue()).intValue()];
	}

	public String[] getTags() {
		return result;
	}

	public void setAsText(String s) {
		int i;
		for (i = 0; i < result.length && !result[i].equals(s); i++);
		if (i == result.length)
			i = 0;
		setValue(new Integer(i));
	}

	public View3DBeanBackgEditor() {
	}

}
