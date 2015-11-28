package Objects;

import Main.Panel;
import Utility.Direction;
import GameState.StatePlay;
import Interface.Drawable;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;

public class Bullet implements Drawable, Serializable {

	public static final int diameter = 10, vel = 10;;

	private Direction direction = null;

	private int x = 0, y = 0, velBol;

	private Rectangle rect = new Rectangle(x, y, diameter, diameter);

	public Bullet(int x, int y, int vel, Direction direction) {

		this.direction = direction;
		this.x = x;
		this.y = y;
		this.velBol = vel;

	}

	@Override
	public void draw(Graphics2D g) {

		g.setColor(Color.CYAN);
		g.fillOval(x, y, diameter, diameter);

	}

	@Override
	public void simulate() {

		switch (direction) {
		case N:
			y = y - velBol - vel;
			break;

		case S:
			y = y + velBol + vel;
			break;

		case E:
			x = x + velBol + vel;
			break;

		case W:
			x = x - velBol - vel;
			break;

		case NE:
			y = y - velBol - vel;
			x = x + velBol + vel;
			break;

		case NW:
			y = y - velBol - vel;
			x = x - velBol - vel;
			break;

		case SW:
			x = x - velBol - vel;
			y = y + velBol + vel;
			break;

		case SE:
			x = x + velBol + vel;
			y = y + velBol + vel;
			break;

		default:
			throw new IllegalArgumentException("direction not found");
		}


		if (!rect.intersects(Panel.instance.getRekt())) {
			StatePlay.arrayBullets.remove(this);
		}

		rect.x = x;
		rect.y = y;

	}

	public Rectangle getRekt() {
		return rect;
	}

}
