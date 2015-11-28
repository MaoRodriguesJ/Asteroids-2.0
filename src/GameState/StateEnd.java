package GameState;

import Interface.GameState;
import Main.Panel;
import Utility.GUI;
import Utility.Media;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class StateEnd extends GUI implements GameState {
	
	private int index = 0;
	
	private boolean newGame = true;
	private boolean backMenu = false;
	private boolean exitGame = false;
	
	private ImageIcon img = Media.getImageEnd();
	
	public StateEnd() {
		//Menu de final de jogo, com opceos clicaveis de iniar novo jogo, 
		//voltar ao menu e sair
	}

	@Override
	public void draw(Graphics2D g) {

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Panel.WIDTH, Panel.HEIGHT);
		
		g.drawImage(img.getImage(), -100, 0, img.getImageObserver());
		
		createImage("You're DEAD", 55, Panel.WIDTH/2, Panel.HEIGHT/4, g, false);	
		createImage("New Game", 40, Panel.WIDTH/2, 2*Panel.HEIGHT/3+50, g, newGame);
		createImage("Go Back To Menu", 40, Panel.WIDTH/2, 2*Panel.HEIGHT/3 + 2*50, g, backMenu);
		createImage("Exit", 40, Panel.WIDTH/2, 2*Panel.HEIGHT/3 + 3*50, g, exitGame);
		
		
	}

	@Override
	public void update() {

		if(index == 0) {
			newGame = true;
			backMenu = false;
			exitGame = false;
		}
		if(index == 1) {
			newGame = false;
			backMenu = true;
			exitGame = false;
		} 
		if(index == 2) {
			newGame = false;
			backMenu = false;
			exitGame = true;
		}
		
	}
	
	@Override
	public void reset() {

		index = 0;
		newGame = true;
		backMenu = false;
		exitGame = false;
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {

		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			if(index == 2) {
				index = 0;
			} else {
				index++;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			if(index == 0) {
				index = 2;
			} else {
				index--;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE) {
			if(index == 0) {
				Panel.instance.currentState = Panel.instance.arrayStates[1]; //Inicia novo jogo
				reset();
			}
			if(index == 1) {
				Panel.instance.currentState = Panel.instance.arrayStates[0]; //Volta pra o menu
				reset();
			} 
			if(index == 2) {
				Panel.instance.stopGame(); //Sai do jogo
				reset();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			Panel.instance.stopGame(); //Sai do jogo
			reset();
		}
		
	}

}
