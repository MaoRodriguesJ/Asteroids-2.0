package Interface;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Main.Panel;

public class Listener implements KeyListener {

	public Listener() {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (Panel.instance.currentState != null)
			Panel.instance.currentState.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (Panel.instance.currentState != null)
			Panel.instance.currentState.keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (Panel.instance.currentState != null)
			Panel.instance.currentState.keyTyped(e);
	}

}
