package Objects;

import GameState.StatePlay;
import Interface.Drawable;
import Interface.LifeCounter;
import Main.Panel;
import Utility.Rotate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.io.Serializable;
import java.util.ArrayList;

public class Asteroid extends Rotate implements Drawable, LifeCounter, Serializable {
	
	private int diameter;
	private int velX;
	private int velY;
	
	private int[] x;
	private int[] y;

	private int totalLife;
	private int actualLife;
	
	private Point p;
	private Polygon polygon;
	private Shape asteroidShape;
	
	public Asteroid(Polygon polygon, int scale, Point p, Point spaceShip, double angle, int positionX, int positionY) {
		
		x = new int[polygon.npoints];
		y = new int[polygon.npoints];
	
		this.p = new Point((p.x*scale) + positionX, (p.y*scale) + positionY);
		
		for(int i = 0; i < polygon.npoints; i++) {
			x[i] = polygon.xpoints[i]*scale + positionX;
			y[i] = polygon.ypoints[i]*scale + positionY;
		}
		
		Polygon polygonTeste = new Polygon(x, y, polygon.npoints);
		this.polygon = rotatePolygon(polygonTeste, this.p, angle);
		this.diameter = p.x;
		this.totalLife = scale;
		this.actualLife = scale-1;
		
		move(this.p, spaceShip);
		
		this.asteroidShape = AffineTransform.getTranslateInstance(0,0).createTransformedShape(new Area(polygon));
		
	}

	@Override
	public void draw(Graphics2D g) {

		g.setColor(Color.DARK_GRAY);
		g.fillPolygon(polygon);
		g.setColor(Color.LIGHT_GRAY);
		g.drawPolygon(polygon);

	}

	@Override
	public void simulate() {
		
		polygon.translate(velX, velY);
		p.translate(velX, velY);
		
		if (p.x + diameter < 0) {
					
			p.translate(Panel.WIDTH + diameter*2, 0);
			polygon.translate(Panel.WIDTH + diameter*2, 0);
					
		}	
		else if (p.y + diameter < 0) {
					
			p.translate(0, Panel.HEIGHT + diameter*2);
			polygon.translate(0, Panel.HEIGHT + diameter*2);
		
		}
		else if (p.x - diameter > Panel.WIDTH) {
					
			p.translate(-Panel.WIDTH - diameter*2, 0);
			polygon.translate(-Panel.WIDTH - diameter*2, 0);
					
		}
		else if (p.y - diameter > Panel.HEIGHT) {
					
			p.translate(0, -Panel.HEIGHT - diameter*2);
			polygon.translate(0, -Panel.HEIGHT - diameter*2);
					
		}
		
		asteroidShape = AffineTransform.getTranslateInstance(0,0).createTransformedShape(new Area(polygon));
		Area areaAsteroid = new Area(asteroidShape);
		Area areaSpaceShip = new Area(StatePlay.spaceShip.shape);
		areaAsteroid.intersect(areaSpaceShip);
		
		if(!areaAsteroid.isEmpty()) {
			
			StatePlay.arrayAsteroids.remove(this);	
			StatePlay.spaceShip.lifeDecrease(1);

		}
		
		if (actualLife <= 0) {
			StatePlay.arrayAsteroids.remove(this);	
		}
		
		for(int j = 0; j < StatePlay.arrayBullets.size(); j++) {
			if(polygon.intersects(StatePlay.arrayBullets.get(j).getRekt())) {
				StatePlay.arrayBullets.remove(j);
				lifeDecrease(1);
				((StatePlay)Panel.instance.arrayStates[1]).score += 5*totalLife*(((StatePlay)Panel.instance.arrayStates[1]).difficulty/5);
			}
		}
		
	}

	@Override
	public int lifeValue() {
		return actualLife;
	}

	@Override
	public void lifeIncrease(int value) {
		actualLife += value;
	
	}

	@Override
	public void lifeDecrease(int value) {
		actualLife -= value;
	}
	
	public void move(Point psource, Point ptarget) {
		
		double ptx = (double) ptarget.x - psource.x;
		double pty = (double) ptarget.y - psource.y;
		
		if(ptx > 0 && pty >= 0) {
			
			double dax = ptx;
			double day = pty;

			double angle = Math.atan2(day, dax);
			
			this.velX = (int) ((2*Math.cos(angle) + 0.5))*(5 - totalLife);
			this.velY = (int) ((2*Math.sin(angle) + 0.5))*(5 - totalLife);
			
		}
		if(ptx <= 0 && pty > 0) {
			
			double dax = -ptx;
			double day = pty;

			double angle = Math.atan2(day, dax);
			
			this.velX = -(int) ((2*Math.cos(angle) + 0.5))*(5 - totalLife);
			this.velY = (int) ((2*Math.sin(angle) + 0.5))*(5 - totalLife);
			
		}
		if(ptx < 0 && pty <= 0) {
			
			double dax = -ptx;
			double day = -pty;

			double angle = Math.atan2(day, dax);
			
			this.velX = -(int) ((2*Math.cos(angle) + 0.5))*(5 - totalLife);
			this.velY = -(int) ((2*Math.sin(angle) + 0.5))*(5 - totalLife);
			
		}
		if(ptx >= 0 && pty < 0) {
			
			double dax = ptx;
			double day = -pty;

			double angle = Math.atan2(day, dax);
			
			this.velX = (int) (((2*Math.cos(angle))*((4 - totalLife)+ 0.5)) + 0.5);
			this.velY = -(int) (((2*Math.sin(angle))*((4 - totalLife)+ 0.5)) + 0.5);
			
		}
		
	}
	
	public Polygon getPolygoned() {
		return polygon;
	}

}
