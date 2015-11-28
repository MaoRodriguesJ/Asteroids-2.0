package Utility;

import GameState.StatePlay;
import Interface.GameState;
import Main.Panel;
import Objects.Asteroid;
import Objects.Bullet;
import Objects.SpaceShip;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SaveLoad {
	
	private static String OS = System.getProperty("os.name").toLowerCase();
	private static String local;
	
	public static void setLocal() {
		if(OS.indexOf("nux") >= 0) {
			local = "/";
		}
		else {
			local = "\\";
		}
	}
	
	public static void saveGame(GameState statePlay, ArrayList<Bullet> arrayBullets, 
								ArrayList<Asteroid> arrayAsteroids, SpaceShip spaceShip) {
		setLocal();
		String fileGameState = "files"+local+"GameState.txt";
		String fileArrayBullets = "files"+local+"ArrayBullets.txt";
		String fileArrayAsteroids = "files"+local+"ArrayAsteroids.txt";
		String fileSpaceShip = "files"+local+"SpaceShip.txt";
		
		try {
			FileOutputStream fileOutput = new FileOutputStream(fileGameState);
			ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
			objectOutput.writeObject(statePlay);
			objectOutput.close();
			fileOutput.close();
			
			FileOutputStream fileOutput2 = new FileOutputStream(fileArrayBullets);
			ObjectOutputStream objectOutput2 = new ObjectOutputStream(fileOutput2);
			objectOutput2.writeObject(arrayBullets);
			objectOutput2.close();
			fileOutput2.close();
			
			FileOutputStream fileOutput3 = new FileOutputStream(fileArrayAsteroids);
			ObjectOutputStream objectOutput3 = new ObjectOutputStream(fileOutput3);
			objectOutput3.writeObject(arrayAsteroids);
			objectOutput3.close();
			fileOutput3.close();
			
			FileOutputStream fileOutput4 = new FileOutputStream(fileSpaceShip);
			ObjectOutputStream objectOutput4 = new ObjectOutputStream(fileOutput4);
			objectOutput4.writeObject(spaceShip);
			objectOutput4.close();
			fileOutput4.close();
			
			System.out.println("Arquivos salvos");
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	public static void loadGame() {
		setLocal();
		
		GameState statePlay = null;
		ArrayList<Bullet> arrayBullets = null;
		ArrayList<Asteroid> arrayAsteroids = null;
		SpaceShip spaceShip = null;
		
		String fileGameState = "files"+local+"GameState.txt";
		String fileArrayBullets = "files"+local+"ArrayBullets.txt";
		String fileArrayAsteroids = "files"+local+"ArrayAsteroids.txt";
		String fileSpaceShip = "files"+local+"SpaceShip.txt";
		
		try {
			FileInputStream fileInput = new FileInputStream(fileGameState);
			ObjectInputStream objectInput = new ObjectInputStream(fileInput);
			statePlay = (StatePlay) objectInput.readObject();
			objectInput.close();
			fileInput.close();
			
			FileInputStream fileInput2 = new FileInputStream(fileArrayBullets);
			ObjectInputStream objectInput2 = new ObjectInputStream(fileInput2);
			arrayBullets = (ArrayList<Bullet>) objectInput2.readObject();
			objectInput2.close();
			fileInput2.close();
			
			FileInputStream fileInput3 = new FileInputStream(fileArrayAsteroids);
			ObjectInputStream objectInput3 = new ObjectInputStream(fileInput3);
			arrayAsteroids = (ArrayList<Asteroid>) objectInput3.readObject();
			objectInput3.close();
			fileInput3.close();
			
			FileInputStream fileInput4 = new FileInputStream(fileSpaceShip);
			ObjectInputStream objectInput4 = new ObjectInputStream(fileInput4);
			spaceShip = (SpaceShip) objectInput4.readObject();
			objectInput4.close();
			fileInput4.close();
			
			Panel.instance.arrayStates[1] = statePlay;
			StatePlay.arrayAsteroids = arrayAsteroids;
			StatePlay.arrayBullets = arrayBullets;
			StatePlay.spaceShip = spaceShip;
			
			System.out.println("Arquivos importados");
		} catch (Exception ex) {
			Panel.instance.arrayStates[1] = new StatePlay();
			System.out.println("Nenhum jogo salvo encontrado, iniciando um jogo novo!");
		}
		
	}
	
	public static void SaveScore(Scores score) {
		setLocal();
		String fileScore = "files"+local+"Score.txt";
		
		try {
			FileOutputStream fileOutput = new FileOutputStream(fileScore);
			ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
			objectOutput.writeObject(score);
			objectOutput.close();
			fileOutput.close();
			System.out.println("Score salvo");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void LoadScore() {
		setLocal();
		String fileScore = "files"+local+"Score.txt";
		Scores score = null;
		
		try {
			FileInputStream fileInput = new FileInputStream(fileScore);
			ObjectInputStream objectInput = new ObjectInputStream(fileInput);
			score = (Scores) objectInput.readObject();
			objectInput.close();
			fileInput.close();
			Panel.instance.scores = score;
			System.out.println("Score importado");
		} catch(Exception ex) {
			System.out.println("Nenhum HighScore encontrado, tente jogar um pouco!");
		}
	}

}
