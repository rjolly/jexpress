import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Scrollbar;
import java.awt.Toolkit;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class paintpanel extends Panel
	implements AdjustmentListener {

	Image image;
	Scrollbar rangerup;
	Scrollbar rangerhor;
	Toolkit toolkit;
	int scright;
	int scdown;
	boolean clearpanel;
	boolean noim;
	Graphics offgr;

	public paintpanel() {
		toolkit = Toolkit.getDefaultToolkit();
		clearpanel = true;
		noim = true;
		image = toolkit.getImage("im/front.jpg");
		setBackground(Color.white);
		setLayout(new BorderLayout());
		rangerup = new Scrollbar(1, 0, 50, 0, 50);
		rangerhor = new Scrollbar(0, 0, 50, 0, 50);
		rangerup.setBackground(Color.lightGray);
		rangerhor.setBackground(Color.lightGray);
		rangerup.addAdjustmentListener(this);
		rangerhor.addAdjustmentListener(this);
		add(rangerup, "East");
		add(rangerhor, "South");
	}

	public paintpanel(int i) {
		toolkit = Toolkit.getDefaultToolkit();
		clearpanel = true;
		noim = true;
		setBackground(Color.white);
		setLayout(new BorderLayout());
	}

	public paintpanel(Scrollbar scrollbar, Scrollbar scrollbar1) {
		toolkit = Toolkit.getDefaultToolkit();
		clearpanel = true;
		noim = true;
		setBackground(Color.white);
		setLayout(new BorderLayout());
		rangerup = scrollbar1;
		rangerhor = scrollbar;
		rangerup.setBackground(Color.lightGray);
		rangerhor.setBackground(Color.lightGray);
		add(rangerup, "East");
		add(rangerhor, "South");
	}

	public void adjustmentValueChanged(AdjustmentEvent adjustmentevent) {
		if (adjustmentevent.getAdjustable() == rangerup)
			scright = -adjustmentevent.getValue();
		else
			scdown = -adjustmentevent.getValue();
		repaint();
	}

	public void clear(Graphics g) {
		Dimension dimension = getSize();
		g.setColor(Color.white);
		g.fillRect(0, 0, dimension.width, dimension.height);
	}

	public int hgth() {
		Dimension dimension = getSize();
		return dimension.height;
	}

	public void paint(Graphics g) {
		update(g);
	}

	public void resetscbar() {
		Dimension dimension = getSize();
		if (image != null) {
			rangerup.setMaximum((image.getHeight(this) - dimension.height) + 50);
			rangerhor.setMaximum(image.getWidth(this) - dimension.width);
		}
		repaint();
	}

	public void setimage(Image image1) {
		noim = false;
		image = image1;
		repaint();
	}

	public void update(Graphics g) {
		if (clearpanel) {
			g.setColor(Color.white);
			Image image1 = createImage(wdth(), hgth());
			g.fillRect(0, 0, wdth(), hgth());
			if (rangerup != null) {
				rangerup.setValue(0);
				rangerhor.setValue(0);
			}
			scright = scdown = 0;
			g.drawImage(image1, 0, 0, this);
		}
		if (image != null) {
			if (clearpanel) {
				clear(g);
				clearpanel = false;
			}
			g.drawImage(image, scdown, scright, this);
		} else {
			clear(g);
		}
		clearpanel = false;
	}

	public int wdth() {
		Dimension dimension = getSize();
		return dimension.width;
	}
}
