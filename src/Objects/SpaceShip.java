package Objects;

import GameState.StatePlay;
import Interface.Drawable;
import Interface.LifeCounter;
import Main.Panel;
import Utility.Media;
import Utility.Direction;
import Utility.Rotate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.io.Serializable;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.ImageIcon;

public class SpaceShip extends Rotate implements Drawable, LifeCounter, Serializable {
	
	public static final int vel = 10, diameter = 50;
	
	public int imageCounter;
	
	public Direction direction;
	
	public Shape shape;
	
	private int[] x1;
	private int[] x2;
	private int[] x3;
	
	private int[] y1;
	private int[] y2;
	private int[] y3;
	
	private Point pNave;
	
	private Polygon p1;
	private Polygon p2;
	private Polygon p3;

	private int totalLife;
	private int stayCounter;
	
	private double angle;
	
	private boolean lifeLost;
	private boolean stayFlicking;
	private boolean startCounting;
	
	private ImageIcon[] img = Media.getAnimation();

	public SpaceShip(Polygon polygon1, Polygon polygon2, Polygon polygon3, Point pNave, int positionX, int positionY) {
		
		x1 = new int[polygon1.npoints];
		y1 = new int[polygon1.npoints];
		
		x2 = new int[polygon2.npoints];
		y2 = new int[polygon2.npoints];
		
		x3 = new int[polygon3.npoints];
		y3 = new int[polygon3.npoints];
		
		this.pNave = new Point(pNave.x + positionX, pNave.y + positionY);
		
		for(int i = 0; i < polygon1.npoints; i++) {
			x1[i] = polygon1.xpoints[i] + positionX;
			y1[i] = polygon1.ypoints[i] + positionY;
		}
		
		for(int i = 0; i < polygon2.npoints; i++) {
			x2[i] = polygon2.xpoints[i] + positionX;
			y2[i] = polygon2.ypoints[i] + positionY;
		}
		
		for(int i = 0; i < polygon3.npoints; i++) {
			x3[i] = polygon3.xpoints[i] + positionX;
			y3[i] = polygon3.ypoints[i] + positionY;
		}
		
		this.p1 = new Polygon(x1, y1, polygon1.npoints);
		this.p2 = new Polygon(x2, y2, polygon2.npoints);
		this.p3 = new Polygon(x3, y3, polygon3.npoints);
		
		this.shape = AffineTransform.getTranslateInstance(0,0).createTransformedShape(new Area(p1));
		
		this.totalLife = 3;
		this.lifeLost = false;
		this.stayFlicking = false;
		this.stayCounter = 0;
		this.imageCounter = 0;
		this.direction = Direction.S;
		
	}

	@Override
	public void draw(Graphics2D g) {
		
		g.setColor(Color.GRAY);
		g.fillPolygon(p1);
		g.setColor(Color.CYAN);
		g.fillPolygon(p2);
		g.fillPolygon(p3);
		g.setColor(Color.WHITE);
		g.drawPolygon(p1);
		
		if(totalLife == 0 || lifeLost) {
			startCounting = true;
			if(totalLife == 0) {
				g.drawImage(img[imageCounter].getImage(), pNave.x - 55, pNave.y - 55, 110, 110, img[imageCounter].getImageObserver());
					if(imageCounter == img.length) {
						lifeLost = false;
						imageCounter = 0;
					}
			} 
			if(imageCounter%25 == 0 || stayFlicking) {
				stayFlicking = true;
				stayCounter++;
				if(stayCounter == 10) {
					stayCounter = 0;
					stayFlicking = false;
				}
				if(imageCounter == 75) {
					lifeLost = false;
					stayFlicking = false;
					startCounting = false;
					imageCounter = 0;
					stayCounter = 0;
					StatePlay.arrayAsteroids.clear();
				}
				if(lifeLost) {
					g.setColor(Color.BLACK);
					g.fillRect(pNave.x - 55, pNave.y - 55, 110, 110);
				}
			}
			if(startCounting) {
				imageCounter++;
			}
		}	
	}

	@Override
	public void simulate() {

			if (pNave.x + diameter < 0) {
				
				pNave.translate(Panel.WIDTH + diameter*2, 0);
				p1.translate(Panel.WIDTH + diameter*2, 0);
				p2.translate(Panel.WIDTH + diameter*2, 0);
				p3.translate(Panel.WIDTH + diameter*2, 0);
				x1 = translateArray(x1, Panel.WIDTH + diameter*2);
				x2 = translateArray(x2, Panel.WIDTH + diameter*2);
				x3 = translateArray(x3, Panel.WIDTH + diameter*2);
				
				
			}	
			else if (pNave.y + diameter < 0) {
				
				pNave.translate(0, Panel.HEIGHT + diameter*2);
				p1.translate(0, Panel.HEIGHT + diameter*2);
				p2.translate(0, Panel.HEIGHT + diameter*2);
				p3.translate(0, Panel.HEIGHT + diameter*2);
				y1 = translateArray(y1, Panel.HEIGHT + diameter*2);
				y2 = translateArray(y2, Panel.HEIGHT + diameter*2);
				y3 = translateArray(y3, Panel.HEIGHT + diameter*2);
				
			}
			else if (pNave.x - diameter > Panel.WIDTH) {
				
				pNave.translate(-Panel.WIDTH - diameter*2, 0);
				p1.translate(-Panel.WIDTH - diameter*2, 0);
				p2.translate(-Panel.WIDTH - diameter*2, 0);
				p3.translate(-Panel.WIDTH - diameter*2, 0);
				x1 = translateArray(x1,-Panel.WIDTH - diameter*2);
				x2 = translateArray(x2, -Panel.WIDTH - diameter*2);
				x3 = translateArray(x3, -Panel.WIDTH - diameter*2);
				
			}
			else if (pNave.y - diameter > Panel.HEIGHT) {
				
				pNave.translate(0, -Panel.HEIGHT - diameter*2);
				p1.translate(0, -Panel.HEIGHT - diameter*2);
				p2.translate(0, -Panel.HEIGHT - diameter*2);
				p3.translate(0, -Panel.HEIGHT - diameter*2);
				y1 = translateArray(y1, -Panel.HEIGHT - diameter*2);
				y2 = translateArray(y2, -Panel.HEIGHT - diameter*2);
				y3 = translateArray(y3, -Panel.HEIGHT - diameter*2);
				
				
			}
		
		shape = AffineTransform.getTranslateInstance(0,0).createTransformedShape(new Area(p1));
		
	}
	
	@Override
	public int lifeValue() {
		return totalLife;
	}

	@Override
	public void lifeIncrease(int value) {
		totalLife += value;
	}

	@Override
	public void lifeDecrease(int value) {
		if(!lifeLost) {
			totalLife -= value;
			lifeLost = true;
		}
	}
	
	public void move(boolean up, boolean down, boolean left, boolean right, boolean shooting) {
		
		if(totalLife != 0) {
			
			if (up) {
				
				p1.translate(0, -vel);
				p2.translate(0, -vel);
				p3.translate(0, -vel);
				pNave.translate(0, -vel);
				y1 = translateArray(y1, -vel);
				y2 = translateArray(y2, -vel);
				y3 = translateArray(y3, -vel);
				
			}
	
			if (down) {
				
				p1.translate(0, vel);
				p2.translate(0, vel);
				p3.translate(0, vel);
				pNave.translate(0, vel);
				y1 = translateArray(y1, vel);
				y2 = translateArray(y2, vel);
				y3 = translateArray(y3, vel);
				
			}
	
			if (left) {
				
				p1.translate(-vel, 0);
				p2.translate(-vel, 0);
				p3.translate(-vel, 0);
				pNave.translate(-vel, 0);
				x1 = translateArray(x1, -vel);
				x2 = translateArray(x2, -vel);
				x3 = translateArray(x3, -vel);
				
			}
	
			if (right) {
				
				p1.translate(vel, 0);
				p2.translate(vel, 0);
				p3.translate(vel, 0);
				pNave.translate(vel, 0);
				x1 = translateArray(x1, vel);
				x2 = translateArray(x2, vel);
				x3 = translateArray(x3, vel);
			}
			
		}
		if(!shooting) {
			
			if (up && right) {
				direction = Direction.NE;
			}
	
			else if (up && left) {
				direction = Direction.NW;
			}
	
			else if (down && left) {
				direction = Direction.SW;
			}
	
			else if (down && right) {
				direction = Direction.SE;
			}
	
			else if (up) {
				direction = Direction.N;
			}
	
			else if (down) {
				direction = Direction.S;
			}
	
			else if (right) {
				direction = Direction.E;
			}
	
			else if (left) {
				direction = Direction.W;
			}
		}
		
		shape = AffineTransform.getTranslateInstance(0,0).createTransformedShape(new Area(p1));

	}
	
	public void rotate() {
			
			switch (direction) {
			case N:
				angle = 180;
				break;

			case S:
				angle = 0;
				break;

			case E:
				angle = 270;
				break;

			case W:
				angle = 90;
				break;

			case NE:
				angle = 225;
				break;

			case NW:
				angle = 135;
				break;

			case SW:
				angle = 45;
				break;

			case SE:
				angle = 315;
				break;

			default:
				throw new IllegalArgumentException("direction not found");
			}
			
			p1 = rotateBasePolygon(x1, y1, angle);
			p2 = rotateBasePolygon(x2, y2, angle);
			p3 = rotateBasePolygon(x3, y3, angle);
			
			shape = AffineTransform.getTranslateInstance(0,0).createTransformedShape(new Area(p1));
		
	}
	
	public Polygon rotateBasePolygon(int[] x, int[] y, double angle) {
		Polygon p = new Polygon(x, y, x.length);
		p = rotatePolygon(p, pNave, angle);
		return p;
	}
	
	public int[] translateArray(int[] x, int value) {
		int[] xtest = x;
		for(int i = 0; i < xtest.length; i++) {
			xtest[i] += value;
		}
		return xtest;
	}
	
	public int quadrant() {
		if(pNave.x >= Panel.WIDTH/2 && pNave.y < Panel.HEIGHT/2) {
			return 1;
		}
		if(pNave.x < Panel.WIDTH/2 && pNave.y <= Panel.HEIGHT/2) {
			return 2;
		}
		if(pNave.x <= Panel.WIDTH/2 && pNave.y > Panel.HEIGHT/2) {
			return 3;
		}
		else {
			return 4;
		}
	}
	
	public int getX() {
		return pNave.x;
	}
	
	public int getY() {
		return pNave.y;
	}

}
