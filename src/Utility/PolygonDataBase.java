package Utility;

import java.awt.Point;
import java.awt.Polygon;
import java.io.Serializable;
import java.util.Random;

import Main.Panel;
import Objects.Asteroid;
import Objects.SpaceShip;

public class PolygonDataBase implements Serializable{

	// Retorna os arrays necessarios para criacao dos poligonos

	// SpaceShip arrays

	private Point pNave = new Point(50, 50);

	private int[] x1 = new int[] { 50, 58, 64, 76, 76, 90, 76, 70, 58, 50, 42, 30, 24, 10, 24, 24, 36, 42 };
	private int[] y1 = new int[] { 0, 20, 4, 26, 8, 30, 60, 50, 100, 70, 100, 50, 60, 30, 8, 26, 4, 20 };
	private int npoints1 = 18;

	private int[] x2 = new int[] { 50, 68, 50, 32 };
	private int[] y2 = new int[] { 0, 38, 24, 38 };
	private int npoints2 = 4;

	private int[] x3 = new int[] { 50, 66, 58, 50, 42, 34 };
	private int[] y3 = new int[] { 30, 44, 76, 50, 76, 44 };
	private int npoints3 = 6;

	// Asteroids arrays

	private Point p4 = new Point(22, 22);
	private int[] x4 = new int[] { 31, 41, 44, 30, 23, 20, 10, 20, 4, 0, 15 };
	private int[] y4 = new int[] { 2, 12, 24, 39, 29, 41, 37, 24, 32, 22, 2 };
	private int npoints4 = 11;

	private Point p5 = new Point(19, 19);
	private int[] x5 = new int[] { 20, 33, 37, 31, 37, 27, 12, 10, 12, 0, 10, 20 };
	private int[] y5 = new int[] { 4, 10, 14, 20, 27, 33, 31, 28, 23, 18, 8, 16 };
	private int npoints5 = 12;

	private Point p6 = new Point(19, 19);
	private int[] x6 = new int[] { 9, 25, 29, 33, 38, 30, 28, 14, 10, 3, 0, 3 };
	private int[] y6 = new int[] { 0, 3, 12, 7, 20, 25, 33, 37, 27, 25, 10, 12 };
	private int npoints6 = 12;

	private Point p7 = new Point(16, 16);
	private int[] x7 = new int[] { 22, 31, 28, 19, 17, 14, 4, 0, 3, 15 };
	private int[] y7 = new int[] { 2, 8, 20, 25, 23, 29, 25, 9, 3, 9 };
	private int npoints7 = 10;

	private Point p8 = new Point(14, 14);
	private int[] x8 = new int[] { 27, 28, 22, 24, 17, 7, 9, 0, 4, 0, 5 };
	private int[] y8 = new int[] { 1, 8, 14, 19, 26, 16, 26, 22, 13, 6, 1 };
	private int npoints8 = 11;
	
	// Polygons

	public Polygon polygon1() {
		return new Polygon(x1, y1, npoints1);
	}

	public Polygon polygon2() {
		return new Polygon(x2, y2, npoints2);
	}

	public Polygon polygon3() {
		return new Polygon(x3, y3, npoints3);
	}

	public Polygon polygon4() {
		return new Polygon(x4, y4, npoints4);
	}

	public Polygon polygon5() {
		return new Polygon(x5, y5, npoints5);
	}

	public Polygon polygon6() {
		return new Polygon(x6, y6, npoints6);
	}

	public Polygon polygon7() {
		return new Polygon(x7, y7, npoints7);
	}

	public Polygon polygon8() {
		return new Polygon(x8, y8, npoints8);
	}

	// Points

	public Point pointNave() {
		return (Point) pNave.clone();
	}

	public Point point4() {
		return (Point) p4.clone();
	}

	public Point point5() {
		return (Point) p5.clone();
	}

	public Point point6() {
		return (Point) p6.clone();
	}

	public Point point7() {
		return (Point) p7.clone();
	}

	public Point point8() {
		return (Point) p8.clone();
	}
	
	public Polygon getPolygon(int index) {
		if(index == 1){
			return polygon1();
		}
		if(index == 2){
			return polygon2();
		}
		if(index == 3){
			return polygon3();
		}
		if(index == 4){
			return polygon4();
		}
		if(index == 5){
			return polygon5();
		}
		if(index == 6){
			return polygon6();
		}
		if(index == 7){
			return polygon7();
		}
		if(index == 8){
			return polygon8();
		}
		else {
			return null;
		}
	}
	
	public Point getPoint(int index) {
		if(index == 0 || index == 1 || index == 2 || index == 3){
			return pointNave();
		}
		if(index == 4){
			return point4();
		}
		if(index == 5){
			return point5();
		}
		if(index == 6){
			return point6();
		}
		if(index == 7){
			return point7();
		}
		if(index == 8){
			return point8();
		}
		else {
			return null;
		}
	}
	
	public Asteroid createRandom(Random rng, SpaceShip spaceShip) {
		
		int whichPolygon = rng.nextInt((8-4)+1)+4;
		int whatScale = rng.nextInt((4-2)+1)+2;
		int whatAngle = rng.nextInt((360-0)+1)+0;
		int whatQuadrant = rng.nextInt((4-1)+1)+1;
		int nextQuadrant = rng.nextInt((1-0)+1)+0;
		int positionX = 0;
		int positionY = 0;
		
		if(whatQuadrant == spaceShip.quadrant()) {
			if(nextQuadrant == 0) {
				whatQuadrant += 1;
			} else {
				whatQuadrant -= 1;
			}
		}
		else if(whatQuadrant == 1) {
			positionX = Panel.WIDTH - (getPoint(whichPolygon).x*whatScale);
			positionY = getPoint(whichPolygon).y*whatScale;
		}
		else if(whatQuadrant == 2) {
			positionX = getPoint(whichPolygon).x*whatScale;
			positionY = getPoint(whichPolygon).y*whatScale;
		}
		else if(whatQuadrant == 3) {
			positionX = getPoint(whichPolygon).x*whatScale;
			positionY = Panel.HEIGHT - (getPoint(whichPolygon).y*whatScale);
		}
		else if(whatQuadrant == 4) {
			positionX = Panel.WIDTH - (getPoint(whichPolygon).x*whatScale);
			positionY = Panel.HEIGHT - (getPoint(whichPolygon).y*whatScale);
		}
		
		return (new Asteroid(getPolygon(whichPolygon), whatScale, getPoint(whichPolygon), 
			    new Point(spaceShip.getX(), spaceShip.getY()), whatAngle, positionX, positionY));
	}

}
