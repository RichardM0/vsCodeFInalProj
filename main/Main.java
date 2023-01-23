package main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;


public class Main {

	public static JLabel scoreLabel = new JLabel();
	public static JPanel scorePanel = new JPanel();
	public static void main(String[] args) {
		
		JFrame window  = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Plane Dodger");

		
		scorePanel.setOpaque(false);
		scorePanel.setFocusable(false);
		scorePanel.setLayout(new BorderLayout());
		scoreLabel.setBorder(new EmptyBorder(30,60,0,0));
		scorePanel.add(scoreLabel,BorderLayout.NORTH);
		scoreLabel.setFont(scoreLabel.getFont().deriveFont(48f));


		

		window.setGlassPane(scorePanel);
		
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		window.pack();
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gamePanel.startGameThread();
		
	}

}