package Utility;

import java.io.Serializable;

import javax.swing.JOptionPane;

public class Scores implements Serializable{
	
	public int score1 = 42;
	public int score2 = 42;
	public int score3 = 42;
	
	public String name1 = "Arthur Dent";
	public String name2 = "Ford Prefect";
	public String name3 = "Marvin, the Paranoid Android";
	
	public Scores() {
		
	}
	
	public boolean checkScores(int score) {
		if(score > score1) {
			score3 = score2;
			name3 = name2;	
			score2 = score1;
			name2 = name1;
			score1 = score;
			name1 = JOptionPane.showInputDialog("Qual seu nome aventureiro?");
			return true;
		}
		if(score > score2) {
			score3 = score2;
			name3 = name2;
			score2 = score;
			name2 = JOptionPane.showInputDialog("Qual seu nome aventureiro?");
			return true;
		}
		if(score > score3) {
			score3 = score;
			name3 = JOptionPane.showInputDialog("Qual seu nome aventureiro?");
			return true;
		}
		else {
			return false;
		}
	}

}
