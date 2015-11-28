package Main;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Asteroids 2.0");

		Panel panel = new Panel(frame);

		frame.getContentPane().add(panel, BoxLayout.X_AXIS);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setResizable(false);

		frame.setBackground(Color.WHITE);

		frame.pack();

		frame.setLocationRelativeTo(null);

		frame.setVisible(true);

		panel.startGame();    
		
	}

}
