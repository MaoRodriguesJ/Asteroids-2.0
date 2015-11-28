package Utility;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public abstract class GUI {
	
	public void createImage (String name, int fontSize, int x, int y, Graphics2D g, boolean value) {
		if(value){
			g.setColor(Color.CYAN);
		} else {
			g.setColor(Color.LIGHT_GRAY);
		}
		g.setFont(new Font("Courier New", Font.BOLD, fontSize));
		g.drawString(name, x - (g.getFontMetrics().stringWidth(name)/2), y - g.getFontMetrics().getHeight());
	}
	
	public void createScore (String name, int fontSize, int x, int y, Graphics2D g) {
		g.setColor(Color.LIGHT_GRAY);
		g.setFont(new Font("Courier New", Font.BOLD, fontSize));
		g.drawString(name, x, y);
	}
}