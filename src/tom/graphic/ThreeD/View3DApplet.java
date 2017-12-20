package tom.graphic.ThreeD;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.MediaTracker;
import java.io.PrintStream;
import java.net.URL;
import java.util.StringTokenizer;

// Referenced classes of package tom.graphic.ThreeD:
//			View3DCanvas, View3DCommon

public class View3DApplet extends Applet {

	String paramName;
	public View3DCanvas view;

	public View3DApplet() {
	}

	public Integer getParameterInt(String s) {
		Integer integer = null;
		String s1;
		if ((s1 = getParameter(s)) != null)
			try {
				integer = new Integer(s1);
			}
			catch (Exception _ex) {
				System.out.println("Error, for paremeter " + s + " incorrect int value:" + s1);
			}
		return integer;
	}

	public void init() {
		setLayout(new BorderLayout());
		add("Center", view = new View3DCanvas());
		Integer integer;
		if ((integer = getParameterInt("angleX")) != null)
			view.setAngleX(integer.intValue());
		Integer integer1;
		if ((integer1 = getParameterInt("angleY")) != null)
			view.setAngleY(integer1.intValue());
		Integer integer2;
		if ((integer2 = getParameterInt("angleZ")) != null)
			view.setAngleZ(integer2.intValue());
		Integer integer3;
		if ((integer3 = getParameterInt("posX")) != null)
			view.setPosX(integer3.intValue());
		if ((integer3 = getParameterInt("posX")) != null)
			view.setPosX(integer3.intValue());
		Integer integer4;
		if ((integer4 = getParameterInt("posY")) != null)
			view.setPosY(integer4.intValue());
		Integer integer5;
		if ((integer5 = getParameterInt("posZ")) != null)
			view.setPosZ(integer5.intValue());
		if ((paramName = getParameter("backgIma")) != null)
			setBackgIma(paramName);
		Integer integer6;
		if ((integer6 = getParameterInt("backgMode")) != null)
			setBackgMode(integer6.intValue());
		if ((paramName = getParameter("model")) != null)
			setModel(paramName);
		String s = null;
		if ((s = getParameter("allowedMove")) != null)
			try {
				boolean aflag[] = new boolean[6];
				StringTokenizer stringtokenizer = new StringTokenizer(s, ",");
				for (int i = 0; i < 6; i++)
					aflag[i] = Boolean.valueOf(stringtokenizer.nextToken()).booleanValue();

				view.setAllowedMove(aflag[0], aflag[1], aflag[2], aflag[3], aflag[4], aflag[5]);
			}
			catch (Exception _ex) {
				System.out.println("erreur with parameter allowedMove");
			}
	}

	public void setBackgIma(String s) {
		MediaTracker mediatracker = new MediaTracker(this);
		java.awt.Image image;
		mediatracker.addImage(image = getImage(getDocumentBase(), s), 0);
		try {
			mediatracker.waitForID(0);
		}
		catch (Exception _ex) {
			System.out.println("Not all images loaded.");
		}
		view.setBackgIma(image);
	}

	public void setBackgMode(int i) {
		view.setBackgMode(i);
	}

	public void setModel(String s) {
		Object obj = null;
		try {
			java.io.InputStream inputstream = (new URL(getDocumentBase(), s)).openStream();
			view.setObj(inputstream, s);
		}
		catch (Exception exception) {
			System.out.println(exception);
		}
	}
}
