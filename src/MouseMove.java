import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMove extends Frame
	implements MouseMotionListener, ComponentListener {
	class Line3d
		implements Cloneable {

		int to;
		int from;

		public Object clone() {
			return new Line3d(to, from);
		}

		public Line3d(int i, int j) {
			to = i;
			from = j;
		}
	}

	class Object3d
		implements Cloneable {

		Color color;
		double xo;
		double yo;
		double zo;
		Line3d lines[];
		point3d points[];
		Vertex3d vertices[];
		Color colors[];
		Color colors2[];
		double ci;

		public void addpoints(double ad[][]) {
			double d = 99999999D;
			double d1 = 99999999D;
			double d2 = 99999999D;
			double d3 = -99999999D;
			double d4 = -99999999D;
			double d5 = -99999999D;
			double d8 = 0.0D;
			double d9 = 0.0D;
			for (int i = 0; i < ad.length; i++) {
				if (d > ad[i][0])
					d = ad[i][0];
				if (d1 > ad[i][1])
					d1 = ad[i][1];
				if (d2 > ad[i][2])
					d2 = ad[i][2];
				if (d3 < ad[i][0])
					d3 = ad[i][0];
				if (d4 < ad[i][1])
					d4 = ad[i][1];
				if (d5 < ad[i][2])
					d5 = ad[i][2];
			}

			double d6 = d3 - d;
			double d7 = d4 - d1;
			d8 = d5 - d2;
			d6 /= 100D;
			d7 /= 100D;
			d8 /= 100D;
			points = new point3d[ad.length];
			for (int j = 0; j < ad.length; j++)
				points[j] = new point3d((ad[j][0] + d) / d6, (ad[j][1] + d1) / d7, (ad[j][2] + d2) / d8);

		}

		public void translate(double d, double d1, double d2) {
			xo += d;
			yo += d1;
			zo += d2;
		}

		public void scale(double d) {
			for (int i = 0; i < vertices.length; i++) {
				vertices[i].x *= d;
				vertices[i].y *= d;
				vertices[i].z *= d;
			}

			for (int j = 0; j < points.length; j++) {
				points[j].x *= d;
				points[j].y *= d;
				points[j].z *= d;
			}

		}

		public void rotateX(double d) {
			double d5 = Math.cos(d);
			double d6 = Math.sin(d);
			for (int i = 0; i < vertices.length; i++) {
				double d3 = vertices[i].z * d5 - vertices[i].y * d6;
				double d1 = vertices[i].y * d5 + vertices[i].z * d6;
				vertices[i].z = d3;
				vertices[i].y = d1;
			}

			for (int j = 0; j < points.length; j++) {
				double d4 = points[j].z * d5 - points[j].y * d6;
				double d2 = points[j].y * d5 + points[j].z * d6;
				points[j].z = d4;
				points[j].y = d2;
			}

		}

		public void rotateY(double d) {
			double d5 = Math.cos(d);
			double d6 = Math.sin(d);
			for (int i = 0; i < vertices.length; i++) {
				double d1 = vertices[i].x * d5 - vertices[i].z * d6;
				double d3 = vertices[i].z * d5 + vertices[i].x * d6;
				vertices[i].x = d1;
				vertices[i].z = d3;
			}

			for (int j = 0; j < points.length; j++) {
				double d2 = points[j].x * d5 - points[j].z * d6;
				double d4 = points[j].z * d5 + points[j].x * d6;
				points[j].x = d2;
				points[j].z = d4;
			}

		}

		public void rotateZ(double d) {
			double d5 = Math.cos(d);
			double d6 = Math.sin(d);
			for (int i = 0; i < vertices.length; i++) {
				double d1 = vertices[i].x * d5 - vertices[i].y * d6;
				double d3 = vertices[i].y * d5 + vertices[i].x * d6;
				vertices[i].x = d1;
				vertices[i].y = d3;
			}

			for (int j = 0; j < points.length; j++) {
				double d2 = points[j].x * d5 - points[j].y * d6;
				double d4 = points[j].y * d5 + points[j].x * d6;
				points[j].x = d2;
				points[j].y = d4;
			}

		}

		public void draw(Graphics g, Dimension dimension, double d) {
			g.setColor(color);
			for (int i = 0; i < lines.length; i++) {
				double d3 = vertices[lines[i].from].x;
				double d4 = vertices[lines[i].from].y;
				double d6 = vertices[lines[i].from].z;
				double d1 = vertices[lines[i].to].x;
				double d2 = vertices[lines[i].to].y;
				double d5 = vertices[lines[i].to].z;
				ci = (d6 + d5 + 280D) / 600D;
				if (ci > 1.0D)
					ci = 1.0D;
				if (ci < 0.0D)
					ci = 0.0D;
				g.setColor(colors[(int)(ci * 15D)]);
				if (projection) {
					d6 += 650D;
					d5 += 650D;
					d3 += xo;
					d4 += yo;
					d6 += zo;
					d1 += xo;
					d2 += yo;
					d5 += zo;
					d3 = distance * (d3 / d6);
					d4 = distance * (d4 / d6);
					d1 = distance * (d1 / d5);
					d2 = distance * (d2 / d5);
					g.drawLine((int)(d3 + (double)sxo), (int)(d4 + (double)syo), (int)(d1 + (double)sxo), (int)(d2 + (double)syo));
				} else {
					g.drawLine((int)(d3 + xo + (double)sxo), (int)(d4 + yo + (double)syo), (int)(d1 + xo + (double)sxo), (int)(d2 + yo + (double)syo));
				}
			}

			for (int j = 0; j < points.length; j++) {
				double d7 = points[j].x;
				double d8 = points[j].y;
				double d9 = points[j].z;
				ci = (d9 + 280D) / 600D;
				if (ci > 1.0D)
					ci = 1.0D;
				if (ci < 0.0D)
					ci = 0.0D;
				g.setColor(colors2[(int)(ci * 15D)]);
				if (projection) {
					d9 += 650D;
					d7 += xo;
					d8 += yo;
					d9 += zo;
					d7 = distance * (d7 / d9);
					d8 = distance * (d8 / d9);
					g.drawRect((int)(d7 + (double)sxo), (int)(d8 + (double)syo), 1, 1);
				} else {
					g.drawRect((int)(d7 + xo + (double)sxo), (int)(d8 + yo + (double)syo), 1, 1);
				}
			}

		}

		public Object clone() {
			Object3d object3d = new Object3d(color, xo, yo, zo, (Vertex3d[])vertices.clone(), (Line3d[])lines.clone());
			return object3d;
		}

		public Object3d(Color color1, double d, double d1, double d2, Vertex3d avertex3d[], Line3d aline3d[]) {
			points = new point3d[2];
			points[0] = new point3d(50D, 54D, 3D);
			points[1] = new point3d(52D, 5D, 48D);
			color = color1;
			xo = d;
			yo = d1;
			zo = d2;
			vertices = avertex3d;
			lines = aline3d;
			colors = new Color[16];
			for (int i = 0; i < 16; i++)
				colors[i] = new Color(255 - i * 10, 255 - i * 10, 255 - i * 10);

			colors2 = new Color[16];
			for (int j = 0; j < 16; j++)
				colors2[j] = new Color(205 - j * 10, 205 - j * 10, 255);

		}
	}

	class point3d
		implements Cloneable {

		double x;
		double y;
		double z;

		public Object clone() {
			return new point3d(x, y, z);
		}

		public point3d(double d, double d1, double d2) {
			x = d;
			y = d1;
			z = d2;
		}
	}

	class Vertex3d
		implements Cloneable {

		double x;
		double y;
		double z;

		public Object clone() {
			return new Vertex3d(x, y, z);
		}

		public Vertex3d(double d, double d1, double d2) {
			x = d;
			y = d1;
			z = d2;
		}
	}


	Thread thread;
	Canvas canvas;
	Panel panel;
	int sxo;
	int syo;
	boolean projection;
	boolean resize;
	double distance;
	int mouseX;
	int mouseY;
	Vertex3d vertices[] = {
		new Vertex3d(-100D, 100D, -100D), new Vertex3d(100D, 100D, -100D), new Vertex3d(100D, -100D, -100D), new Vertex3d(-100D, -100D, -100D), new Vertex3d(-100D, 100D, 100D), new Vertex3d(100D, 100D, 100D), new Vertex3d(100D, -100D, 100D), new Vertex3d(-100D, -100D, 100D)
	};
	Line3d lines[] = {
		new Line3d(0, 1), new Line3d(1, 2), new Line3d(2, 3), new Line3d(3, 0), new Line3d(4, 5), new Line3d(5, 6), new Line3d(6, 7), new Line3d(7, 4), new Line3d(0, 4), new Line3d(1, 5), 
		new Line3d(2, 6), new Line3d(3, 7)
	};
	Object3d cube1;
	Image buffer;
	Graphics bg;

	public void componentHidden(ComponentEvent componentevent) {
	}

	public void componentMoved(ComponentEvent componentevent) {
	}

	public void componentResized(ComponentEvent componentevent) {
		resize = true;
		repaint();
	}

	public void componentShown(ComponentEvent componentevent) {
	}

	public MouseMove(double ad[][]) {
		canvas = new Canvas();
		panel = new Panel();
		projection = true;
		resize = true;
		distance = 370D;
		cube1 = new Object3d(Color.white, 0.0D, 0.0D, 0.0D, vertices, lines);
		setLayout(new BorderLayout());
		add(canvas, "Center");
		addComponentListener(this);
		setSize(500, 500);
		cube1.addpoints(ad);
		cube1.scale(2D);
		cube1.translate(0.0D, 0.0D, 0.0D);
		sxo = getSize().width / 2;
		syo = getSize().height / 2;
		canvas.addMouseMotionListener(this);
		repaint();
		setVisible(true);
	}

	public void update(Graphics g) {
		if (buffer != null && !resize) {
			g = canvas.getGraphics();
			g.drawImage(buffer, 0, 0, this);
		} else
		if (resize) {
			buffer = createImage(getSize().width, getSize().height);
			bg = buffer.getGraphics();
			resize = false;
			repaint();
		}
		bg.setColor(Color.black);
		bg.fillRect(0, 0, getSize().width, getSize().height);
		bg.setColor(Color.yellow);
		cube1.draw(bg, getSize(), distance);
	}

	public void paint(Graphics g) {
		update(g);
	}

	public void mouseDragged(MouseEvent mouseevent) {
		cube1.rotateX((double)(mouseevent.getY() - mouseY) / -50D);
		cube1.rotateY((double)(mouseevent.getX() - mouseX) / 50D);
		mouseX = mouseevent.getX();
		mouseY = mouseevent.getY();
		repaint();
	}

	public void mouseMoved(MouseEvent mouseevent) {
	}
}
