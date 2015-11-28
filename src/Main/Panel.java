package Main;

import Interface.Listener;
import GameState.*;
import Interface.GameState;
import Utility.Media;
import Utility.SaveLoad;
import Utility.Scores;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Panel extends JPanel {

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;

	public static Panel instance;
	
	public GameState[] arrayStates = new GameState[]{new StateMenu(), new StatePlay(), new StatePause(), 
													 new StateHighScores(), new StateEnd()};
	
	public Scores scores = new Scores();

	public GameState currentState;

	private BufferedImage buffer;
	private Graphics2D graphics;

	private Rectangle rect = new Rectangle(0, 0, WIDTH, HEIGHT);

	private boolean running = false;

	private Listener listener;
	
	private Clip clip;
	private AudioInputStream audioInputStream;

	public Panel(JFrame frame) {

		instance = this;

		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		requestFocus();

		buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		graphics = buffer.createGraphics();

		currentState = arrayStates[0]; // Inicia no menu
		
		SaveLoad.LoadScore();

		listener = new Listener();
		addKeyListener(listener);
		frame.addKeyListener(listener);
		
		try {
			clip = AudioSystem.getClip();
		} catch (Exception ex) {
			System.out.println("Sistema de audio nao encontrado");
		} 
		
	}

	public void startGame() {
		running = true;
		update();
	}

	public void stopGame() {
		running = false;
		System.exit(0);
	}

	public void update() {		

		while (running) {
			
			if(clip != null) {
				if(!(currentState instanceof StatePlay) && !clip.isRunning()) {
					try {
						audioInputStream = AudioSystem.getAudioInputStream(Media.getThemeMusic());
						clip.open(audioInputStream);
					} catch (Exception ex) {
						System.out.println("Arquivo de musica nao encontrado");
					}
					clip.loop(0);
				}
				if(currentState instanceof StatePlay && clip.isRunning()) {
					clip.stop();
					try {
						audioInputStream.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					clip.close();
				}
			}

			currentState.draw(graphics);
			currentState.update();

			paintImmediately(0, 0, WIDTH, HEIGHT);

			try {
				Thread.sleep(17);
			} catch (InterruptedException ex) {
				System.out.println("Error no Thread");
			}

		}

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (buffer != null) {
			g.drawImage(buffer, 0, 0, null);
		}
	}

	public Rectangle getRekt() {
		return rect;
	}

}
