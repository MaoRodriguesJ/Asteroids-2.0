package GameState;

import Interface.GameState;
import Main.Panel;
import Utility.GUI;
import Utility.Media;
import Utility.SaveLoad;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.NotSerializableException;

import javax.swing.ImageIcon;

public class StatePause extends GUI implements GameState {
	
	private int index = 0;
	
	private boolean resumeGame = true;
	private boolean saveGame = false;
	private boolean backMenu = false;
	
	private ImageIcon img = Media.getImagePause(); 
	
	public StatePause() {
		//Menu de pausa do jogo, com opcoes clicaveis de voltar ao jogo, 
		//salvar o jogo atual e voltar ao menu principal
	}

	@Override
	public void draw(Graphics2D g) {

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Panel.WIDTH, Panel.HEIGHT);
		
		g.drawImage(img.getImage(), -610, 15, img.getImageObserver());
		
		createImage("GAME PAUSED", 55, Panel.WIDTH/2, Panel.HEIGHT/4, g, false);	
		createImage("Resume Game", 40, Panel.WIDTH/2, 2*Panel.HEIGHT/3+50, g, resumeGame);
		createImage("Save Game", 40, Panel.WIDTH/2, 2*Panel.HEIGHT/3 + 2*50, g, saveGame);
		createImage("Go Back To Menu", 40, Panel.WIDTH/2, 2*Panel.HEIGHT/3 + 3*50, g, backMenu);
		
	}

	@Override
	public void update() {

		if(index == 0) {
			resumeGame = true;
			saveGame = false;
			backMenu = false;
		}
		if(index == 1) {
			resumeGame = false;
			saveGame = true;
			backMenu = false;
		} 
		if(index == 2) {
			resumeGame = false;
			saveGame = false;
			backMenu = true;
		}
		
	}
	
	@Override
	public void reset() {

		index = 0;
		resumeGame = true;
		saveGame = false;
		backMenu = false;
		
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
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(index == 0) {
				Panel.instance.currentState = Panel.instance.arrayStates[1]; //Volta para o jogo
				reset();
			}
			if(index == 1) {
				SaveLoad.saveGame(Panel.instance.arrayStates[1], StatePlay.arrayBullets, //Salva o jogo
								  StatePlay.arrayAsteroids, StatePlay.spaceShip);
				reset();
			} 
			if(index == 2) {
				Panel.instance.currentState = Panel.instance.arrayStates[0];//Volta para o menu
				reset();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			Panel.instance.currentState = Panel.instance.arrayStates[1]; //Volta para o jogo
			reset();
		}
		
	}

}
