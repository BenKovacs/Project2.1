package model.data_model;

import javafx.geometry.Point3D;
import model.player.Player;
import java.util.ArrayList;

import static model.data_model.Constants.*;

/**
 * Encapsulate some of the features of the board but easier to manage for AI
 */
public class AIBoard{

	private int[][] board;
	private int turn;

	private Player[] playerList;
	private ArrayList<Point3D> validMoves;

	public AIBoard(int[][] b, int t, Player[] playerList) {

		this.turn = t;

		this.board = new int[b.length][b[0].length];

		for (int i = 0; i < b.length; i++) {
			for (int f = 0; f < b[0].length; f++) {
				board[i][f] = b[i][f];
				if (board[i][f] < 0)
					board[i][f] = Constants.EMPTY;
			}
		}

		this.playerList = playerList;

		this.validMoves = new ArrayList<>();
	}

	/**
	 * Flips a disc at square x,y
	 */
	public boolean flipDisc(int x, int y) {
		if (isValidMove(x, y, true)) {
			board[x][y] = turn;
			changeTurn();
			return true;
		} else {
			System.out.println("AIBOARD " + x + "," + y + " is invalid move");
			return false;
		}
	}

	protected boolean isValidMove(int x, int y, boolean executeMove) {
		if (!(board[x][y] == EMPTY || board[x][y] < 0)) return false;

		else if (checkFlip(x, y, executeMove) > 0){
			return true;
		} else if (playerList.length > 2 && !checkForValidMoves() && checkForNeighbours(x,y)){
			return true;
		}
		return false;
	}

	private boolean checkForValidMoves(){
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++) {
				if ((board[i][j] == EMPTY || board[i][j] < 0) && (checkFlip(i, j, false) > 0)){
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkForNeighbours(int x, int y){
		for(int i = -1; i <= 1; i++){
			for(int j = -1; j <= 1; j++) {
				if (x+i >= 0 && x+i < 8 && y+j >= 0 && y+j < 8){
					if(board[x+i][y+j] != EMPTY && board[x+i][y+j] >= 0){
						return true;
					}
				}
			}
		}
		return false;
	}

	private int checkFlip(int x, int y, boolean executeMove) {
		int flips = 0;

		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				if (i >= 0 && i < 8 && j >= 0 && j < 8) {
					if (board[i][j] != turn) {
						int flipsDirection = checkDirection(x, y, i, j, executeMove);
						if (flipsDirection > 0) {
							flips += flipsDirection;
						}
					}
				}
			}
		}
		return flips;
	}

	private int checkDirection(int x, int y, int i, int j, boolean executeMove) {
		int flips;
		if (board[x + (i - x)][y + (j - y)] == EMPTY || board[x + (i - x)][y + (j - y)] < 0) {
			return -1;
		} else if (board[x + (i - x)][y + (j - y)] == turn) {
			return 0;
		} else if ( x + (i - x) * 2 >= 0 && x + (i - x) * 2 < 8 && y + (j - y) * 2 >= 0 && y + (j - y) * 2 < 8) { // if inside the board

			flips = checkDirection(x + (i - x), y + (j - y), x + (i - x) * 2, y + (j - y) * 2, executeMove);
			if (flips >= 0) {
				if (executeMove) {
					board[x + (i - x)][y + (j - y)] = turn;
				}
				return flips + 1;
			} else {
				return -1;
			}
		} else {
			return -1;
		}
	}


	private void changeTurn() {
		swapPlayers();
		showValidMoves();
	}

	private void showValidMoves() {
		validMoves.clear();

		// Find possible moves for new player turn
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {

                // Reset possible moves
                if(board[x][y]<0) board[x][y] = EMPTY;


				int flips = countFlips(x, y);
				if (flips > 0) {
					//add here the evaluation function
					validMoves.add(new Point3D(x, y, Evaluation.getEvaluation(x, y , this.board, this.turn) + flips));

				} else if (isValidMove(x, y, false)){
					board[x][y] = VALID;
					validMoves.add(new Point3D(x, y, 0));

				}
			}
		}
	}

	private int countFlips(int x, int y) {
		if (board[x][y] == EMPTY || board[x][y] < 0) {
			return checkFlip(x, y, false);
		} else {
			return 0;
		}
	}

	private void swapPlayers() {
		int oldturn = turn;
		int i = 0;
		while (oldturn == turn){
			if (playerList[i].getColor() == turn){
				if( i < playerList.length-1){
					turn = playerList[i+1].getColor();

				} else {
					turn = playerList[0].getColor();
				}
			}
			i++;
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

	public int[][] getBoard() {
		return board;
	}

	public int getTurn() {
		return turn;
	}

	public ArrayList<Point3D> getValidMoves() {
		showValidMoves();
		return validMoves;
	}

	public Player[] getPlayerList() {
		return playerList;
	}
}


