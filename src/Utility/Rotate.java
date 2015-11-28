package Utility;

import java.awt.Point;
import java.awt.Polygon;
import java.lang.Math;

public abstract class Rotate {
	
	private int xteste;
	private int yteste;
	
	public Point rotate(int x, int y, int x0, int y0, double angle) {
		
		xteste = (int) (((Math.cos(Math.toRadians(angle)) * (x-x0)) - (Math.sin(Math.toRadians(angle)) * (y-y0)))+0.5) + x0;
		
		yteste = (int) (((Math.sin(Math.toRadians(angle)) * (x-x0)) + (Math.cos(Math.toRadians(angle)) * (y-y0)))+0.5) + y0;
		
		return new Point(xteste, yteste);

	}
	
	public Polygon rotatePolygon(Polygon polygon, Point p, double angle) {
		
		int[] x = new int[polygon.npoints];
		int[] y = new int[polygon.npoints];
		
		int[] xteste = new int[polygon.npoints];
		int[] yteste = new int[polygon.npoints];
		
		for(int i = 0; i < polygon.npoints; i++) {
			x[i] = polygon.xpoints[i];
			y[i] = polygon.ypoints[i];
			xteste[i] = rotate(x[i], y[i], p.x, p.y, angle).x;
			yteste[i] = rotate(x[i], y[i], p.x, p.y, angle).y;
		}
		
		x = xteste;
		y = yteste;
		
		Polygon polygonRotated = new Polygon(x, y, polygon.npoints);
		
		return polygonRotated;
	}

}
