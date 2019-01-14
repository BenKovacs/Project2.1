package model.data_model;

import model.GameBoard;
import model.player.Player;

/**
 * Encapsulate some of the features of the board but easier to manage for AI
 */
public class AIBoard extends GameBoard {

	public AIBoard(int[][] b, int t, Player[] playerList) {
		super(8, 8);

		turn = t;

		for (int i = 0; i < b.length; i++) {
			for (int f = 0; f < b[0].length; f++) {
				board[i][f] = b[i][f];
			}
		}

		super.playerList = new Player[playerList.length];

		for (int i = 0; i < playerList.length; i++) {
			super.playerList[i] = playerList[i];
		}

	}

	public void printBoard(){
		for (int i = 0; i < board.length; i++) {
			for (int f = 0; f < board[0].length; f++) {
				System.out.print(board[i][f] +" ");
			}
			System.out.println();
		}
	}
}


