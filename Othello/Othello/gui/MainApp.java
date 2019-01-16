package gui;

import model.data_model.GameBoard;
import model.data_model.Constants;
import model.player.*;
import javax.swing.*;
import java.awt.*;

public class MainApp{

	private static MainApp mainApp;

	public static MainApp getSingleton() {
		return mainApp;
	}

	private JFrame frame;

	public JFrame getFrame() {
		return frame;
	}

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
	private MainApp() {
		frame = new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setBounds(50, 50, 927*2, 473*2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initialize();
	}

	public void reset() {
	    frame.dispose();
		frame = new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setBounds(50, 50, 927*2, 473*2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents.
	 */
	private void initialize() {
		// --PANELS
		// right RightPanel
		RightPanel rPanel;
		// left LeftPanel
		BoardPanel boardPanel;

		Settings settings = new Settings();
		// The settings Dialog is Modal so this thread will pause after setting
		// it to be visible until it is set invisible by the dialog.
		settings.setVisible(true);
		if (settings.isCancelSelected()) {
			System.exit(0);
		}

		// Create AIs or human players
		int playerCount = settings.getNumPlayers();
		Player[] playerList = new Player[playerCount];

		// create the gameBoard/panel
		GameBoard gameBoard = new GameBoard(8, 8);

		// set the layout
		frame.getContentPane().setLayout(new GridLayout(1, 2));

		rPanel = new RightPanel();
		boardPanel = new BoardPanel(gameBoard, rPanel);
		frame.getContentPane().add(boardPanel, 1, 0);

		int turn;
		if (playerCount == 2){
			turn = Constants.WHITE;
		} else {
			turn = Constants.RED;
		}
		if (settings.getPlayer1().equalsIgnoreCase("human")) {
			playerList[0] = new HumanPlayer(boardPanel, turn);
		} else if (settings.getPlayer1().equalsIgnoreCase("minmax")) {
			MinMaxPlayer ai = new MinMaxPlayer(boardPanel, turn, settings.getDepthLevel());
			playerList[0] = ai;
			ai.start();
		} else if (settings.getPlayer1().equalsIgnoreCase("greedy")) {
			GreedyPlayer ai = new GreedyPlayer(boardPanel, turn);
			ai.start();
			playerList[0] = ai;
		} else if (settings.getPlayer1().equalsIgnoreCase("random")) {
			RandomPlayer ai = new RandomPlayer(boardPanel, turn);
			ai.start();
			playerList[0] = ai;
		} else if (settings.getPlayer1().equalsIgnoreCase("mcts")) {
			int runtime = 2000; //min runtime in millisecs
			int iterations = 0; //min iterations
			MonteCarloTreeSearch ai = new MonteCarloTreeSearch(boardPanel, turn, runtime, iterations);
			playerList[0] = ai;
			ai.start();
		} else if (settings.getPlayer1().equalsIgnoreCase("smcts")) {
            int runtime = 3000; //min runtime in millisecs
            int iterations = 0; //min iterations
            SuperMonteCarloTreeSearch ai = new SuperMonteCarloTreeSearch(boardPanel, turn, runtime, iterations);
            playerList[0] = ai;
            ai.start();
        }

		if (playerCount == 2){
			turn = Constants.BLACK;
		} else {
			turn = Constants.GREEN;
		}
		if (settings.getPlayer2().equalsIgnoreCase("human")) {
			playerList[1] = new HumanPlayer(boardPanel, turn);
		} else if (settings.getPlayer2().equalsIgnoreCase("minmax")) {
			MinMaxPlayer ai = new MinMaxPlayer(boardPanel, turn, settings.getDepthLevel());
			playerList[1] = ai;
			ai.start();
		} else if (settings.getPlayer2().equalsIgnoreCase("greedy")) {
			GreedyPlayer ai = new GreedyPlayer(boardPanel, turn);
			playerList[1]  = ai;
			ai.start();
		} else if (settings.getPlayer2().equalsIgnoreCase("random")) {
			RandomPlayer ai = new RandomPlayer(boardPanel, turn);
			playerList[1] = ai;
			ai.start();
		} else if (settings.getPlayer2().equalsIgnoreCase("mcts")) {
			int runtime = 2000; //min runtime in millisecs
			int iterations = 0; //min iterations
			MonteCarloTreeSearch ai = new MonteCarloTreeSearch(boardPanel, turn, runtime, iterations);
			ai.start();
			playerList[1] = ai;
		} else if (settings.getPlayer2().equalsIgnoreCase("smcts")) {
            int runtime = 3000; //min runtime in millisecs
            int iterations = 0; //min iterations
            SuperMonteCarloTreeSearch ai = new SuperMonteCarloTreeSearch(boardPanel, turn, runtime, iterations);
            playerList[1] = ai;
            ai.start();
        }

		if (playerCount > 2){
			turn = Constants.BLUE;
			if (settings.getPlayer3().equalsIgnoreCase("human")) {
				playerList[2] = new HumanPlayer(boardPanel, turn);
			} else if (settings.getPlayer3().equalsIgnoreCase("minmax")) {
				MinMaxPlayer ai = new MinMaxPlayer(boardPanel, turn, settings.getDepthLevel());
				playerList[2] = ai;
				ai.start();
			} else if (settings.getPlayer3().equalsIgnoreCase("greedy")) {
				GreedyPlayer ai = new GreedyPlayer(boardPanel, turn);
				playerList[2]  = ai;
				ai.start();
			} else if (settings.getPlayer3().equalsIgnoreCase("random")) {
				RandomPlayer ai = new RandomPlayer(boardPanel, turn);
				playerList[2] = ai;
				ai.start();
			} else if (settings.getPlayer3().equalsIgnoreCase("mcts")) {
				int runtime = 2000; //min runtime in millisecs
				int iterations = 0; //min iterations
				MonteCarloTreeSearch ai = new MonteCarloTreeSearch(boardPanel, turn, runtime, iterations);
				ai.start();
				playerList[2] = ai;
			} else if (settings.getPlayer3().equalsIgnoreCase("smcts")) {
                int runtime = 3000; //min runtime in millisecs
                int iterations = 0; //min iterations
                SuperMonteCarloTreeSearch ai = new SuperMonteCarloTreeSearch(boardPanel, turn, runtime, iterations);
                playerList[2] = ai;
                ai.start();
            }
		}

		if (playerCount > 3){
			turn = Constants.YELLOW;
			if (settings.getPlayer4().equalsIgnoreCase("human")) {
				playerList[3] = new HumanPlayer(boardPanel, turn);
			} else if (settings.getPlayer4().equalsIgnoreCase("minmax")) {
				MinMaxPlayer ai = new MinMaxPlayer(boardPanel, turn, settings.getDepthLevel());
				playerList[3] = ai;
				ai.start();
			} else if (settings.getPlayer4().equalsIgnoreCase("greedy")) {
				GreedyPlayer ai = new GreedyPlayer(boardPanel, turn);
				playerList[3]  = ai;
				ai.start();
			} else if (settings.getPlayer4().equalsIgnoreCase("random")) {
				RandomPlayer ai = new RandomPlayer(boardPanel, turn);
				playerList[3] = ai;
				ai.start();
			} else if (settings.getPlayer4().equalsIgnoreCase("mcts")) {
				int runtime = 2000; //min runtime in millisecs
				int iterations = 0; //min iterations
				MonteCarloTreeSearch ai = new MonteCarloTreeSearch(boardPanel, turn, runtime, iterations);
				ai.start();
				playerList[3] = ai;
			} else if (settings.getPlayer4().equalsIgnoreCase("smcts")) {
                int runtime = 3000; //min runtime in millisecs
                int iterations = 0; //min iterations
                SuperMonteCarloTreeSearch ai = new SuperMonteCarloTreeSearch(boardPanel, turn, runtime, iterations);
                playerList[3] = ai;
                ai.start();
            }
		}


//		for (int i = 0; i < playerList.length; i++) {
//			System.out.println("Player " + i + " is: " + playerList[i] + playerList[i].getColor());
//		}
		gameBoard.setPlayerList(playerList);
		rPanel.setPlayerList(playerList);
		boardPanel.setGameBoard(gameBoard);
		rPanel.setPreferredSize(new Dimension(10, 30));
		frame.getContentPane().add(rPanel, 0, 1);
	}
}
