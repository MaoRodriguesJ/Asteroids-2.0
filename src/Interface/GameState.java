package Interface;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.NotSerializableException;

public interface GameState {

	public void draw(Graphics2D g);
	
	public void update();
	
	public void reset();
	
	public void keyTyped(KeyEvent e);
	
	public void keyReleased(KeyEvent e);
	
	public void keyPressed(KeyEvent e);
}
