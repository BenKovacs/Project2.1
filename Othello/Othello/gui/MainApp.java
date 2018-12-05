package gui;


import model.GameBoard;
import model.Constants;
import model.player.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainApp implements ActionListener {
	
	private static MainApp mainApp;
	
	public static MainApp getSingleton() {
		return mainApp;
	}

	private JFrame frame;
	public GameBoard gameBoard;
	
	public JFrame getFrame() {
		return frame;
	}

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
					mainApp = window;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	private MainApp(){

		frame = new JFrame();
		frame.setBounds(100, 100, 927, 473);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initialize();
	}
	
	public void reset() {
		frame = new JFrame();
		frame.setBounds(100, 100, 927, 473);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents.
	 */

	private void initialize() {
		

		settings = new Settings();
		// The settings Dialog is Modal so this thread will pause after setting it to be visible until it is set invisible by the dialog.
		settings.setVisible(true);
		if (settings.isCancelSelected()){
			System.exit(0);
		}

		//create the gameBoard/panel
		gameBoard = new GameBoard(8,8);

		//set the layout
		frame.getContentPane().setLayout(new GridLayout(1,2));

		rPanel = new RightPanel();

		boardPanel = new BoardPanel(gameBoard, rPanel);
		frame.getContentPane().add(boardPanel, 1,0);

		//Create AIs or human players
<<<<<<< HEAD
		int turn = Constants.WHITE;
		Player[] playerArray = new Player[settings.getNumberOfAIPlayers()+settings.getNumberOfHumanPlayers()];
		for (int i = 0; i < settings.getNumberOfHumanPlayers(); i++){
			playerArray[i] = new HumanPlayer(boardPanel, turn);
			//Change color
			if(turn == Constants.WHITE) {
				turn = Constants.BLACK;
			} else {
				turn = Constants.WHITE;
			}
//			playerArray[i] = new MonteCarloTreeSearch(boardPanel);
		}

		for (int i = settings.getNumberOfHumanPlayers(); i < playerArray.length; i++) {
			AIPlayer ai = new AIPlayer(boardPanel,turn);
			playerArray[i] = ai;
			ai.start();
			if(turn == Constants.WHITE) {
				turn = Constants.BLACK;
			} else {
				turn = Constants.WHITE;
			}
=======

		Player[] playerArray = new Player[settings.getNumPlayers()];
		if (settings.getPlayer1().equalsIgnoreCase("human")){
			playerArray[0] = new HumanPlayer(boardPanel);
		}else if(settings.getPlayer1().equalsIgnoreCase("minmax")){
			playerArray[0] = new MinMaxPlayer(boardPanel);
		}else if(settings.getPlayer1().equalsIgnoreCase("greedy")){
			playerArray[0] = new GreedyPlayer(boardPanel);
		}else if(settings.getPlayer1().equalsIgnoreCase("random")){
			playerArray[0] = new AIPlayer(boardPanel);
		}else if(settings.getPlayer1().equalsIgnoreCase("mcts")){
	//		playerArray[0]; //= new TODO add correct code : MCTS(boardPanel);
			}


		if (settings.getPlayer2().equalsIgnoreCase("human")){
			playerArray[1] = new HumanPlayer(boardPanel);
		}else if(settings.getPlayer2().equalsIgnoreCase("minmax")){
			playerArray[1] = new MinMaxPlayer(boardPanel);
		}else if(settings.getPlayer2().equalsIgnoreCase("greedy")){
			playerArray[1] = new GreedyPlayer(boardPanel);
		}else if(settings.getPlayer2().equalsIgnoreCase("random")){
			playerArray[1] = new AIPlayer(boardPanel);
		}else if(settings.getPlayer2().equalsIgnoreCase("mcts")){
			//playerArray[1] //= new TODO add correct code : MCTS(boardPanel);
>>>>>>> origin/master
		}


		for(int i = 0; i < playerArray.length; i++) {
			System.out.println("Player " + i + " is: " + playerArray[i]);
		}
		gameBoard.setPlayers(playerArray);

		rPanel.setPreferredSize(new Dimension(10, 30));
		frame.getContentPane().add(rPanel, 0,1);


	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	}
}

