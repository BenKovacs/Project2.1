package gui;


import model.GameBoard;
import model.player.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainApp implements ActionListener {

	private JFrame frame;
	private GameBoard gameBoard;

	//--PANELS
	//right RightPanel
	private RightPanel rPanel;
	//left LeftPanel
	private BoardPanel boardPanel;
	private Settings settings;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainApp window = new MainApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainApp(){

		frame = new JFrame();
		frame.setBounds(100, 100, 927, 473);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		settings = new Settings();
		// The settings Dialog is Modal so this thread will pause after setting it to be visible until it is set invisible by the dialog.
		settings.setVisible(true);
		if (settings.isCancelSelected()){
			System.exit(0);
		}

		initialize();
	}

	/**
	 * Initialize the contents.
	 */
	private void initialize() {
		//create the gameBoard/panel
		gameBoard = new GameBoard(8,8);

		Player[] playerArray = new Player[settings.getNumberOfAIPlayers()+settings.getNumberOfHumanPlayers()];
		for (int i = 0; i < settings.getNumberOfHumanPlayers(); i++){
			playerArray[i] = new HumanPlayer(gameBoard);
		}
		for (int i = settings.getNumberOfHumanPlayers(); i < playerArray.length; i++){
			playerArray[i] = new AIPlayer(gameBoard);
		}

		gameBoard.setPlayers(playerArray);

		//set the layout
		frame.getContentPane().setLayout(new GridLayout(1,2));

		rPanel = new RightPanel();



		boardPanel = new BoardPanel(gameBoard, rPanel);
		frame.getContentPane().add(boardPanel, 1,0);

		rPanel.setPreferredSize(new Dimension(10, 30));
		frame.getContentPane().add(rPanel, 0,1);


	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	}

}

