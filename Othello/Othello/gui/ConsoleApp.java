package gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.Random;

import model.data_model.Constants;
import model.data_model.GameBoard;
import model.player.GreedyPlayer;
import model.player.HumanPlayer;
import model.player.MinMaxPlayer;
import model.player.MonteCarloTreeSearch;
import model.player.Player;
import model.player.RandomPlayer;
import model.player.SuperMonteCarloTreeSearch;

public class ConsoleApp {
	
	public static Player getBot(int index, BoardPanel boardPanel, int turn, int depth) {
		switch(index) {
		case 0:
			 return new MinMaxPlayer(boardPanel, turn, depth);
		case 1:
			return new GreedyPlayer(boardPanel, turn);
		case 2:
			return new RandomPlayer(boardPanel, turn);
		case 3:
			int runtime = 2000; //min runtime in millisecs
			int iterations = 0; //min iterations
			return new MonteCarloTreeSearch(boardPanel, turn, runtime, iterations);
		case 4:
			runtime = 3000; //min runtime in millisecs
			iterations = 0; //min iterations
			return new SuperMonteCarloTreeSearch(boardPanel, turn, runtime, iterations);
			
		}
		return new HumanPlayer(boardPanel, turn);
	}

	public static void main(String[] args) {
		
		/*
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				*/
				Random rand = new Random();
				int i = 1; //rand.nextInt(5);
				int j = 2;//rand.nextInt(5);
				GameBoard gameBoard = new GameBoard(8, 8);
				RightPanel rPanel = new RightPanel();
				BoardPanel boardPanel = new TestingPanel(gameBoard, rPanel);
				Player first = getBot(i,boardPanel,Constants.WHITE,5);
				Player second = getBot(j,boardPanel,Constants.WHITE,5);
				Player[] playerList = {first,second};
				gameBoard.setPlayerList(playerList);
				rPanel.setPlayerList(playerList);
				boardPanel.setGameBoard(gameBoard);
				rPanel.setPreferredSize(new Dimension(10, 30));
//				frame.getContentPane().add(rPanel, 0, 1);

				for(Player ai: playerList){
					System.out.println("Starting " + ai);;
					ai.start();
				}
				/*
			}
		}
		*/
		
	}
	
	

}
