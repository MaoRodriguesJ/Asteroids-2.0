package GameState;

import Main.Panel;
import Objects.Asteroid;
import Objects.Bullet;
import Objects.SpaceShip;
import Utility.Direction;
import Utility.GUI;
import Utility.Media;
import Utility.PolygonDataBase;
import Utility.SaveLoad;
import Interface.GameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class StatePlay extends GUI implements GameState, Serializable {

	private static final int delayBullets = 13;
	private static final int delayAsteroids = 150;
	private static final int maxContDifficulty = 400;


	public static ArrayList<Bullet> arrayBullets = new ArrayList<>();
	public static ArrayList<Asteroid> arrayAsteroids = new ArrayList<>();

	public static SpaceShip spaceShip;

	private boolean up = false, down = false, right = false, left = false, shootup = false, shootdown = false,
					shootright = false, shootleft = false, shooting = false;

	private int contBullets;
	private int contAsteroids;
	private int contDifficulty;
	private int numberOfAsteroids;
	private int delayBulletsCounter;
	
	public int difficulty;	
	public int score;
	
	private Random rng = new Random();
	
	private PolygonDataBase data = new PolygonDataBase();
	
	private static Clip clip;
	private static AudioInputStream audioInputStream;

	public StatePlay() {
		
		//Uma das principais classes do projeto, responsavel por simular os objetos, 
		//criar novos objetos e alterar a dificuldade de acordo com o tempo

		spaceShip = new SpaceShip(data.getPolygon(1), data.getPolygon(2), data.getPolygon(3), data.getPoint(0), 
								  ((Panel.WIDTH/2) - data.getPoint(0).x), ((Panel.HEIGHT/2) - data.getPoint(0).y));
		arrayAsteroids.clear();
		arrayBullets.clear();
		contBullets = 0;
		contAsteroids = 0;
		contDifficulty = 0;
		difficulty = 5;
		numberOfAsteroids = 1;
		delayBulletsCounter = 0;
		score = 0;
		
		try {
			clip = AudioSystem.getClip(); //pegar sistema de musica do computador
		} catch (Exception ex) {
			
		}
		reset();

	}

	@Override
	public void draw(Graphics2D g) {

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Panel.WIDTH, Panel.HEIGHT);

		for (int i = 0; i < arrayBullets.size(); i++) {
			arrayBullets.get(i).draw(g);
		}

		for (int i = 0; i < arrayAsteroids.size(); i++) {
			arrayAsteroids.get(i).draw(g);
		}

		spaceShip.draw(g);
		createImage("LIFE: "+spaceShip.lifeValue(), 30, 1205, 735, g, false);
		createScore("SCORE: "+score, 30, 15, 700, g);

	}

	@Override
	public void update() {
		
		if(clip != null) {//iniciar arquvio de musica, caso ele ainda nao esteja tocando
			
			if(Panel.instance.currentState instanceof StatePlay && !clip.isRunning()) {
				try {
					
					audioInputStream = AudioSystem.getAudioInputStream(Media.getInGameMusic()); 
					clip.open(audioInputStream);
					
				} catch (Exception ex) {
					System.out.println("Arquivo de musica nao encontrado");
				}
				clip.loop(0);
			}
		}

		contBullets++;
		contAsteroids++;
		contDifficulty++;
		
		if (spaceShip.lifeValue() <= 0)  { //Caso a nave perca todas as vidas, retorna para o menu
										   //e o jogo é resetado
			arrayAsteroids.clear();
			contAsteroids = 0;
			
			if(spaceShip.imageCounter == Media.getAnimation().length) {
				
				Panel.instance.scores.checkScores(score);
				SaveLoad.SaveScore(Panel.instance.scores);
				
				if(clip != null) {
					clip.stop();
					try {
						audioInputStream.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					clip.close();
				}
				
				Panel.instance.currentState = Panel.instance.arrayStates[4];
				Panel.instance.arrayStates[1] = new StatePlay();
				reset();
				
			}

		}
		
		if (contAsteroids > (delayAsteroids - difficulty)) {
			
			for(int i = 0; i < numberOfAsteroids; i++) {
				arrayAsteroids.add(data.createRandom(rng, spaceShip));
			}
			
			contAsteroids = 0;

		}
		
		else if(contDifficulty > maxContDifficulty) { //Algoritmo para aumento de dificuldade em varios aspectos
			contDifficulty = 0;
			if(delayAsteroids >= difficulty + 45) {
				difficulty += 5;
//				System.out.println(difficulty);
				if(difficulty%40 == 0 && numberOfAsteroids < 4) {
					numberOfAsteroids++;
//					System.out.println("Novo Asteroid");
				}
			}
			if(delayBullets > delayBulletsCounter + 2) {
				if(difficulty%3 == 0) {
					delayBulletsCounter += 2;
//					System.out.println(delayBullets - delayBulletsCounter);
				}

			}
		}
		
		else if (!(shootup || shootdown || shootright || shootleft)) {
			shooting = false;
		}
		
		else if (shootup || shootdown || shootright || shootleft) {
			
			shooting = true;
				
			int bulletX = spaceShip.getX() - Bullet.diameter / 2;
			int bulletY = spaceShip.getY() - Bullet.diameter / 2;
	
			if (shootup && shootright) {
				if(contBullets > delayBullets - delayBulletsCounter) {
					arrayBullets.add(new Bullet(bulletX, bulletY, SpaceShip.vel, Direction.NE));
					contBullets = 0;
				}
				spaceShip.direction = Direction.NE;
			}
	
			else if (shootup && shootleft) {
				if(contBullets > delayBullets - delayBulletsCounter) {
					arrayBullets.add(new Bullet(bulletX, bulletY, SpaceShip.vel, Direction.NW));
					contBullets = 0;
				}
				spaceShip.direction = Direction.NW;
			}
	
			else if (shootdown && shootleft) {
				if(contBullets > delayBullets - delayBulletsCounter) {
					arrayBullets.add(new Bullet(bulletX, bulletY, SpaceShip.vel, Direction.SW));
					contBullets = 0;
				}
				spaceShip.direction = Direction.SW;
			}
	
			else if (shootdown && shootright) {
				if(contBullets > delayBullets - delayBulletsCounter) {
					arrayBullets.add(new Bullet(bulletX, bulletY, SpaceShip.vel, Direction.SE));
					contBullets = 0;
				}
				spaceShip.direction = Direction.SE;
			}
	
			else if (shootup) {
				if(contBullets > delayBullets - delayBulletsCounter) {
					arrayBullets.add(new Bullet(bulletX, bulletY, SpaceShip.vel, Direction.N));
					contBullets = 0;
				};
				spaceShip.direction = Direction.N;
			}
	
			else if (shootdown) {
				if(contBullets > delayBullets - delayBulletsCounter) {
					arrayBullets.add(new Bullet(bulletX, bulletY, SpaceShip.vel, Direction.S));
					contBullets = 0;
				}
				spaceShip.direction = Direction.S;
			}
	
			else if (shootright) {
				if(contBullets > delayBullets - delayBulletsCounter) {
					arrayBullets.add(new Bullet(bulletX, bulletY, SpaceShip.vel, Direction.E));
					contBullets = 0;
				}
				spaceShip.direction = Direction.E;
			}
	
			else if (shootleft) {
				if(contBullets > delayBullets - delayBulletsCounter) {
					arrayBullets.add(new Bullet(bulletX, bulletY, SpaceShip.vel, Direction.W));
					contBullets = 0;
				}
				spaceShip.direction = Direction.W;
			}
		}

		spaceShip.simulate();
		spaceShip.move(up, down, left, right, shooting);
		spaceShip.rotate();

		for (int i = 0; i < arrayBullets.size(); i++) {
			arrayBullets.get(i).simulate();
		}

		for (int i = 0; i < arrayAsteroids.size(); i++) {
			arrayAsteroids.get(i).simulate();
		}

	}

	@Override
	public void reset() {

		up = false;
		down = false;
		right = false;
		left = false;
		shootup = false;
		shootdown = false;
		shootright = false;
		shootleft = false;
		shooting = false;

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

		int key = e.getKeyCode();

		up = (key == KeyEvent.VK_W) ? false : up;
		down = (key == KeyEvent.VK_S) ? false : down;
		right = (key == KeyEvent.VK_D) ? false : right;
		left = (key == KeyEvent.VK_A) ? false : left;
		shootup = (key == KeyEvent.VK_UP) ? false : shootup;
		shootdown = (key == KeyEvent.VK_DOWN) ? false : shootdown;
		shootright = (key == KeyEvent.VK_RIGHT) ? false : shootright;
		shootleft = (key == KeyEvent.VK_LEFT) ? false : shootleft;

	}

	@Override
	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		up = (key == KeyEvent.VK_W) ? true : up;
		down = (key == KeyEvent.VK_S) ? true : down;
		right = (key == KeyEvent.VK_D) ? true : right;
		left = (key == KeyEvent.VK_A) ? true : left;
		shootup = (key == KeyEvent.VK_UP) ? true : shootup;
		shootdown = (key == KeyEvent.VK_DOWN) ? true : shootdown;
		shootright = (key == KeyEvent.VK_RIGHT) ? true : shootright;
		shootleft = (key == KeyEvent.VK_LEFT) ? true : shootleft;

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

			Panel.instance.currentState = Panel.instance.arrayStates[2]; //Pausar o jogo
			
			if(clip != null) {
				clip.stop();
				try {
					audioInputStream.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				clip.close();
			}
			
			reset();

		}

	}

}
