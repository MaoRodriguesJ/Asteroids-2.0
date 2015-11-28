package GameState;

import Interface.GameState;
import Main.Panel;
import Utility.GUI;
import Utility.Media;
import Utility.SaveLoad;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class StateMenu extends GUI implements GameState {
	
	private int index = 0;
	
	private boolean start = true;
	private boolean highscores = false;
	private boolean loadgame = false;
	private boolean exit = false;
	
	private ImageIcon img = Media.getImageMenu();
	
	public StateMenu() {
		//Menu principal do jogo, com opcoes clicaveis de iniciar novo jogo,
		//ir para o menu de pontuacoes, iniciar a partir de um jogo salvo e sair
	}

	@Override
	public void draw(Graphics2D g) {
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Panel.WIDTH, Panel.HEIGHT);
		
		g.drawImage(img.getImage(), 0, -280, img.getImageObserver());
		
		createImage("ASTEROIDS 2.0", 55, Panel.WIDTH/2, Panel.HEIGHT/4, g, false);	
		createImage("Start", 40, Panel.WIDTH/2, 2*Panel.HEIGHT/3+50, g, start);
		createImage("Highscores", 40, Panel.WIDTH/2, 2*Panel.HEIGHT/3 + 2*50, g, highscores);
		createImage("Load Game", 40, Panel.WIDTH/2, 2*Panel.HEIGHT/3 + 3*50, g, loadgame);
		createImage("Exit", 40, Panel.WIDTH/2, 2*Panel.HEIGHT/3 + 4*50, g, exit);
		
	}

	@Override
	public void update() {
	
		if(index == 0) {
			start = true;
			highscores = false;
			loadgame = false;
			exit = false;
		}
		if(index == 1) {
			start = false;
			highscores = true;
			loadgame = false;
			exit = false;
		} 
		if(index == 2) {
			start = false;
			highscores = false;
			loadgame = true;
			exit = false;
		}
		if(index == 3) {
			start = false;
			highscores = false;
			loadgame = false;
			exit = true;
		}
		
	}
	
	@Override
	public void reset() {

		index = 0;
		start = true;
		highscores = false;
		loadgame = false;
		exit = false;
		
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
			if(index == 3) {
				index = 0;
			} else {
				index++;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			if(index == 0) {
				index = 3;
			} else {
				index--;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE) {
			if(index == 0) {
				Panel.instance.arrayStates[1] = new StatePlay();
				Panel.instance.currentState = Panel.instance.arrayStates[1]; //inicia novo jogo
				reset();
			}
			if(index == 1) {
				Panel.instance.currentState = Panel.instance.arrayStates[3];//menu de pontuaoes
				reset();
			} 
			if(index == 2) {
				SaveLoad.loadGame();
				Panel.instance.currentState = Panel.instance.arrayStates[1];//iniciar a partir de um jogo salvo
				reset();
			}
			if(index == 3) {
				SaveLoad.SaveScore(Panel.instance.scores); //sair do jogo
				Panel.instance.stopGame();
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			Panel.instance.stopGame();
		}
		
	}
	
}
