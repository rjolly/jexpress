package tom.graphic.ThreeD;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.PrintStream;
import java.net.URL;

// Referenced classes of package tom.graphic.ThreeD:
//			Point2D, PixImage

public class TextureMap {

	public Point2D pt[];
	private static URL base = null;
	public PixImage pixImage;

	public TextureMap() {
		pt = new Point2D[4];
	}

	public TextureMap(Image image) {
		pt = new Point2D[4];
		setIma(image);
	}

	public TextureMap(String s) {
		pt = new Point2D[4];
		pt = new Point2D[4];
		Image image = null;
		try {
			File file = new File(s);
			if (file.exists())
				image = Toolkit.getDefaultToolkit().getImage(s);
		}
		catch (Exception exception) {
			System.out.println(exception);
		}
		if (image == null)
			try {
				URL url;
				if (!s.startsWith("HTTP:")) {
					if (base != null)
						url = new URL(base, s);
					else
						url = new URL("http:" + s);
				} else {
					url = new URL(s);
				}
				System.out.println(url);
				image = Toolkit.getDefaultToolkit().getImage(url);
			}
			catch (Exception exception1) {
				exception1.printStackTrace();
				image = null;
			}
		setIma(image);
	}

	public int getPixel(int i, int j) {
		return pixImage.getPixel(i, j);
	}

	public boolean isOk() {
		return pixImage != null && pixImage.pix != null;
	}

	public static void setDocumentBase(URL url) {
		base = url;
	}

	public void setIma(Image image) {
		if (image != null) {
			if (image instanceof PixImage)
				pixImage = (PixImage)image;
			else
				pixImage = new PixImage(image);
			pt = new Point2D[4];
			pt[0] = new Point2D(0, 0);
			pt[1] = new Point2D(pixImage.width - 1, 0);
			pt[2] = new Point2D(pixImage.width - 1, pixImage.height - 1);
			pt[3] = new Point2D(0, pixImage.height - 1);
			if (pixImage.width == -1)
				pixImage = null;
		}
	}

}
