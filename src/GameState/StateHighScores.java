package GameState;

import Interface.GameState;
import Main.Panel;
import Utility.GUI;
import Utility.Media;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class StateHighScores extends GUI implements GameState {
	
	private ImageIcon img = Media.getImageMenu();
	
	public StateHighScores() {
		//Menu de pontuacoes, unica opcao clicavel e voltar ao menu principal
	}

	@Override
	public void draw(Graphics2D g) {

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Panel.WIDTH, Panel.HEIGHT);
		
		g.drawImage(img.getImage(), 0, -280, img.getImageObserver());
		
		createImage("HighScores", 55, Panel.WIDTH/2, Panel.HEIGHT/4, g, false);	
		createImage(Panel.instance.scores.name1+" == "+Panel.instance.scores.score1, 40, Panel.WIDTH/2, 2*Panel.HEIGHT/3+50, g, false);
		createImage(Panel.instance.scores.name2+" == "+Panel.instance.scores.score2, 40, Panel.WIDTH/2, 2*Panel.HEIGHT/3 + 2*50, g, false);
		createImage(Panel.instance.scores.name3+" == "+Panel.instance.scores.score3, 40, Panel.WIDTH/2, 2*Panel.HEIGHT/3 + 3*50, g, false);
		createImage("Go Back to Menu", 40, Panel.WIDTH/2, 2*Panel.HEIGHT/3 + 4*50, g, true);
		
	}

	@Override
	public void update() {

		
		
	}
	
	@Override
	public void reset() {

		
		
	}

	@Override
	public void keyTyped(KeyEvent e) {

		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {

		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE) {
			Panel.instance.currentState = Panel.instance.arrayStates[0]; //Volta ao menu
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			Panel.instance.currentState = Panel.instance.arrayStates[0];
		}
		
		
	}

}
