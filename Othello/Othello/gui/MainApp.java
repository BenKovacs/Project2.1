package gui;

import model.data_model.Evaluation;
import model.data_model.GameBoard;
import model.data_model.Constants;
import model.player.*;
import javax.swing.*;
import java.awt.*;

public class MainApp{
	//MCTS vs MinMax both ways
	//depth 5 to depth 7
	//10K iterations
	
	//MCTS vs itself, exploration param in 0.5, 1, 1.5 , 2
	
	public static Player getBot(int index, BoardPanel boardPanel, int turn, int depth) {
		switch(index) {
		case 0:
			return new MinMaxPlayer(boardPanel, turn, depth);
		case 1:
			return new GreedyPlayer(boardPanel, turn);
		case 2:
			return new RandomPlayer(boardPanel, turn);
		case 3:
			int runtime = 0; //min runtime in millisecs
			int iterations = 10000; //min iterations
			return new MonteCarloTreeSearch(boardPanel, turn, runtime, iterations);
		case 4:
			runtime = 0; //min runtime in millisecs
			iterations = 10000; //min iterations
			return new SuperMonteCarloTreeSearch(boardPanel, turn, runtime, iterations);
		case 9:
			Evaluation.Heuristic1 = 0;
			Evaluation.Heuristic2 = 1;
			return new MinMaxPlayer(boardPanel, turn, depth);
		case 10:
			Evaluation.Heuristic2 = 0.5;
			return new MinMaxPlayer(boardPanel, turn, depth);
		}
		if(index >= 5 && index <= 8) {
			int runtime = 0; //min runtime in millisecs
			int iterations = 10000; //min iterations
			MonteCarloTreeSearch mcts =  new MonteCarloTreeSearch(boardPanel, turn, runtime, iterations);
			mcts.setExploreParam((index - 4)*0.5);
			
			return mcts;
		}
		return new HumanPlayer(boardPanel, turn);
	}

	private static MainApp mainApp;

	public static MainApp getSingleton() {
		return mainApp;
	}

	private JFrame frame;

	public JFrame getFrame() {
		return frame;
	}
	
	private int j;
	private int i;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		if(args.length >= 2) {
			int i = Integer.parseInt(args[0]);
			int j = Integer.parseInt(args[1]);
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						MainApp window = new MainApp(i,j);
						window.frame.setVisible(true);
						mainApp = window;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} else {
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
		
	}

	private MainApp() {
		this(-1,-1);
	}
	/**
	 * Create the application.
	 */
	private MainApp(int i, int j) {
		frame = new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setBounds(50, 50, 927*2, 473*2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initialize(i, j);
	}
	

	public void reset() {
	    frame.dispose();
		frame = new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setBounds(50, 50, 927*2, 473*2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initialize(i,j);
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents.
	 */
	private void initialize(int i, int j) {
		System.out.println("Initialize()");
		// --PANELS
		// right RightPanel
		RightPanel rPanel;
		// left LeftPanel
		BoardPanel boardPanel;
		GameBoard gameBoard = new GameBoard(8, 8);
		Player[] playerList;
		rPanel = new RightPanel();
		boardPanel = new BoardPanel(gameBoard, rPanel);
		if(i >= 0 && j >= 0) {
			
			// set the layout
			frame.getContentPane().setLayout(new GridLayout(1, 2));

			
			
			frame.getContentPane().add(boardPanel, 1, 0);
			int depth = 4;
			Player first = getBot(i, boardPanel, Constants.WHITE, depth);
			Player second = getBot(j, boardPanel, Constants.BLACK, depth);
			playerList = new Player[2];
			playerList[0] = first;
			playerList[1] = second;
			
		} else {
			System.out.println("Before settings");
			Settings settings = new Settings();
			System.out.println("After settings");;
			// The settings Dialog is Modal so this thread will pause after setting
			// it to be visible until it is set invisible by the dialog.
			settings.setVisible(true);
			if (settings.isCancelSelected()) {
				System.exit(0);
			}
			// Create AIs or human players
			int playerCount = settings.getNumPlayers();
			playerList = new Player[playerCount];
			System.out.println("After playerlist");;
			// create the gameBoard/panel
			

			// set the layout
			frame.getContentPane().setLayout(new GridLayout(1, 2));

			
			
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
				//ai.start();
			} else if (settings.getPlayer1().equalsIgnoreCase("greedy")) {
				GreedyPlayer ai = new GreedyPlayer(boardPanel, turn);
				//ai.start();
				playerList[0] = ai;
			} else if (settings.getPlayer1().equalsIgnoreCase("random")) {
				RandomPlayer ai = new RandomPlayer(boardPanel, turn);
				//ai.start();
				playerList[0] = ai;
			} else if (settings.getPlayer1().equalsIgnoreCase("mcts")) {
				int runtime = 0; //min runtime in millisecs
				int iterations = 10000; //min iterations
				MonteCarloTreeSearch ai = new MonteCarloTreeSearch(boardPanel, turn, runtime, iterations);
				playerList[0] = ai;
				//ai.start();
			} else if (settings.getPlayer1().equalsIgnoreCase("smcts")) {
	            int runtime = 0; //min runtime in millisecs
	            int iterations = 10000; //min iterations
	            SuperMonteCarloTreeSearch ai = new SuperMonteCarloTreeSearch(boardPanel, turn, runtime, iterations);
	            playerList[0] = ai;
	            //ai.start();
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
				//ai.start();
			} else if (settings.getPlayer2().equalsIgnoreCase("greedy")) {
				GreedyPlayer ai = new GreedyPlayer(boardPanel, turn);
				playerList[1]  = ai;
				//ai.start();
			} else if (settings.getPlayer2().equalsIgnoreCase("random")) {
				RandomPlayer ai = new RandomPlayer(boardPanel, turn);
				playerList[1] = ai;
				//ai.start();
			} else if (settings.getPlayer2().equalsIgnoreCase("mcts")) {
				int runtime = 0; //min runtime in millisecs
				int iterations = 10000; //min iterations
				MonteCarloTreeSearch ai = new MonteCarloTreeSearch(boardPanel, turn, runtime, iterations);
				//ai.start();
				playerList[1] = ai;
			} else if (settings.getPlayer2().equalsIgnoreCase("smcts")) {
	            int runtime = 0; //min runtime in millisecs
	            int iterations = 10000; //min iterations
	            SuperMonteCarloTreeSearch ai = new SuperMonteCarloTreeSearch(boardPanel, turn, runtime, iterations);
	            playerList[1] = ai;
	            //ai.start();
	        }

			if (playerCount > 2){
				turn = Constants.BLUE;
				if (settings.getPlayer3().equalsIgnoreCase("human")) {
					playerList[2] = new HumanPlayer(boardPanel, turn);
				} else if (settings.getPlayer3().equalsIgnoreCase("minmax")) {
					MinMaxPlayer ai = new MinMaxPlayer(boardPanel, turn, settings.getDepthLevel());
					playerList[2] = ai;
					//ai.start();
				} else if (settings.getPlayer3().equalsIgnoreCase("greedy")) {
					GreedyPlayer ai = new GreedyPlayer(boardPanel, turn);
					playerList[2]  = ai;
					//ai.start();
				} else if (settings.getPlayer3().equalsIgnoreCase("random")) {
					RandomPlayer ai = new RandomPlayer(boardPanel, turn);
					playerList[2] = ai;
					//ai.start();
				} else if (settings.getPlayer3().equalsIgnoreCase("mcts")) {
					int runtime = 0; //min runtime in millisecs
					int iterations = 10000; //min iterations
					MonteCarloTreeSearch ai = new MonteCarloTreeSearch(boardPanel, turn, runtime, iterations);
					//ai.start();
					playerList[2] = ai;
				} else if (settings.getPlayer3().equalsIgnoreCase("smcts")) {
	                int runtime = 0; //min runtime in millisecs
	                int iterations = 10000; //min iterations
	                SuperMonteCarloTreeSearch ai = new SuperMonteCarloTreeSearch(boardPanel, turn, runtime, iterations);
	                playerList[2] = ai;
	                //ai.start();
	            }
			}

			if (playerCount > 3){
				turn = Constants.YELLOW;
				if (settings.getPlayer4().equalsIgnoreCase("human")) {
					playerList[3] = new HumanPlayer(boardPanel, turn);
				} else if (settings.getPlayer4().equalsIgnoreCase("minmax")) {
					MinMaxPlayer ai = new MinMaxPlayer(boardPanel, turn, settings.getDepthLevel());
					playerList[3] = ai;
					//ai.start();
				} else if (settings.getPlayer4().equalsIgnoreCase("greedy")) {
					GreedyPlayer ai = new GreedyPlayer(boardPanel, turn);
					playerList[3]  = ai;
					//ai.start();
				} else if (settings.getPlayer4().equalsIgnoreCase("random")) {
					RandomPlayer ai = new RandomPlayer(boardPanel, turn);
					playerList[3] = ai;
					//ai.start();
				} else if (settings.getPlayer4().equalsIgnoreCase("mcts")) {
					int runtime = 0; //min runtime in millisecs
					int iterations = 10000; //min iterations
					MonteCarloTreeSearch ai = new MonteCarloTreeSearch(boardPanel, turn, runtime, iterations);
					//ai.start();
					playerList[3] = ai;
				} else if (settings.getPlayer4().equalsIgnoreCase("smcts")) {
	                int runtime = 0; //min runtime in millisecs
	                int iterations = 10000; //min iterations
	                SuperMonteCarloTreeSearch ai = new SuperMonteCarloTreeSearch(boardPanel, turn, runtime, iterations);
	                playerList[3] = ai;
	                //ai.start();
	            }
			}
		}
		

		

		for (int a = 0; a < playerList.length; a++) {
			//System.out.println("Player " + a + " is: " + playerList[a] + playerList[a].getColor());
		}
		gameBoard.setPlayerList(playerList);
		rPanel.setPlayerList(playerList);
		boardPanel.setGameBoard(gameBoard);
		rPanel.setPreferredSize(new Dimension(10, 30));
		frame.getContentPane().add(rPanel, 0, 1);

        for(Player ai: playerList){
        	System.out.println("Starting ai: " + ai);
            ai.start();
        }
	}
}
