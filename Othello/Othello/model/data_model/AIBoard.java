package model.data_model;

import javafx.geometry.Point3D;
import model.player.Player;

import java.util.ArrayList;
import static model.data_model.Constants.*;

/**
 * Encapsulate some of the features of the board but easier to manage for AI
 */
public class AIBoard extends GameBoard{

	public AIBoard(int[][] b, int t, Player[] playerList) {
		super(8, 8);

		turn = t;

		for (int i = 0; i < b.length; i++) {
			for (int f = 0; f < b[0].length; f++) {
				board[i][f] = b[i][f];
				if (board[i][f] < 0)
					board[i][f] = Constants.EMPTY;
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

	@Override
	public ArrayList<Point3D> getValidMoves() {
		ArrayList<Point3D> validMoves = new ArrayList<>();

		// Find possible moves for new player turn
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if(isValidMove(x,y,false)){
					validMoves.add(new Point3D(x, y, Evaluation.staticWeightsHeuristic(x, y, board, turn)));
				}
				// System.out.print(" " + board[x][y]);
			}
			// System.out.println(" ");
		}
		// System.out.println(" ");
		return validMoves;
	}

}


