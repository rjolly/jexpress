package tom.graphic.ThreeD;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.PixelGrabber;
import java.io.PrintStream;

// Referenced classes of package tom.graphic.ThreeD:
//			Graphics3D

public class PixImage extends Image
	implements ImageObserver, Runnable {

	public int width;
	public int height;
	public int pix[];
	private Graphics3D g3d;
	private int shiftWidth;
	private static Frame f = null;
	private Image srcIma;

	public PixImage() {
		pix = null;
		g3d = null;
		shiftWidth = 0;
		srcIma = null;
		initCommon(128, 128);
	}

	public PixImage(int i, int j) {
		pix = null;
		g3d = null;
		shiftWidth = 0;
		srcIma = null;
		initCommon(i, j);
	}

	public PixImage(Image image) {
		pix = null;
		g3d = null;
		shiftWidth = 0;
		srcIma = null;
		try {
			if (f == null)
				f = new Frame();
			MediaTracker mediatracker = new MediaTracker(f);
			mediatracker.addImage(image, 0);
			mediatracker.waitForID(0, 100L);
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
		width = image.getWidth(null);
		height = image.getHeight(null);
		initCommon(width, height);
		PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, width, height, pix, 0, width);
		try {
			pixelgrabber.grabPixels();
		}
		catch (InterruptedException _ex) {
			System.err.println("interrupted waiting for pixels!");
		}
		if ((pixelgrabber.status() & 0x80) != 0) {
			System.err.println("image fetch aborted or errored");
			pix = null;
		}
	}

	public PixImage(Image image, boolean flag) {
		pix = null;
		g3d = null;
		shiftWidth = 0;
		srcIma = null;
		width = image.getWidth(this);
		height = image.getHeight(this);
		if (width == -1 || height == -1) {
			width = 1;
			height = 1;
		}
	}

	public void flush() {
	}

	public Graphics getGraphics() {
		if (g3d == null)
			g3d = new Graphics3D(this);
		return g3d;
	}

	public int getHeight(ImageObserver imageobserver) {
		return height;
	}

	public int getPixel(int i, int j) {
		int k;
		if (shiftWidth == 0)
			k = pix[j * width + i];
		else
			k = pix[(j << shiftWidth) + i];
		return k;
	}

	public Object getProperty(String s, ImageObserver imageobserver) {
		return null;
	}

	public int getShiftWidth() {
		return shiftWidth;
	}

	public ImageProducer getSource() {
		return null;
	}

	public int getWidth(ImageObserver imageobserver) {
		return width;
	}

	public boolean imageUpdate(Image image, int i, int j, int k, int l, int i1) {
		boolean flag = true;
		if ((i & 1) == 1 && (i & 2) == 2) {
			l = image.getWidth(null);
			i1 = image.getHeight(null);
			initCommon(l, i1);
		}
		if ((i & 0x20) == 32) {
			srcIma = image;
			System.out.println("IMAGE UPDATE:OK " + i);
			(new Thread(this)).start();
			flag = false;
		}
		return flag;
	}

	private synchronized void initCommon(int i, int j) {
		width = i;
		height = j;
		pix = new int[i * j];
		int k = width;
		do
			if (k % 2 != 0) {
				k = 0;
				shiftWidth = 0;
			} else {
				shiftWidth++;
				k /= 2;
			}
		while (k > 1);
	}

	public void run() {
		PixelGrabber pixelgrabber = new PixelGrabber(srcIma, 0, 0, width, height, pix, 0, width);
		try {
			pixelgrabber.grabPixels();
		}
		catch (InterruptedException _ex) {
			System.err.println("interrupted waiting for pixels!");
		}
		if ((pixelgrabber.status() & 0x80) != 0) {
			System.err.println("image fetch aborted or errored");
			pix = null;
		}
	}

	public void setPixel(int i, int j, int k) {
		if (shiftWidth == 0)
			pix[j * width + i] = k;
		else
			pix[(j << shiftWidth) + i] = k;
	}

}
