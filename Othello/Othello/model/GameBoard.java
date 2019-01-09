package model;

import javafx.geometry.Point3D;
import model.player.Player;

import java.awt.*;
import java.util.ArrayList;

import static model.Constants.*;

public class GameBoard {
	private static final boolean EXECUTE = true;
	private static final boolean SIMULATE = false;

	private int width;
	private int height;
	private int[][] board;

	// Game staten
	public static int turn;
	private Point lastMove;
	private int INVERSE_COLOUR;
	private int countValid;
	public static int count1;
	public static int count2;
	public static int count3;
	public static int count4;

	private Player[] playerList;
	private Player player;
	private ArrayList<Point3D> validMoves = new ArrayList<>();

	public GameBoard(int width, int height) {
		this.width = width;
		this.height = height;

		board = new int[width][height];

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				board[x][y] = EMPTY; // default empty
			}
		}
		lastMove = null;
		turn = WHITE;
		board[3][3] = WHITE;
		board[3][4] = BLACK;
		board[4][3] = BLACK;
		board[4][4] = WHITE;

		// starting game position
		showValidMoves();
	}

	/**
	 * Flips a disc at square x,y
	 */
	public boolean flipDisc(int x, int y) {
		if (isValidMove(x, y, EXECUTE)) {
			board[x][y] = turn;
			lastMove = new Point(x, y);
			countDiscs();
			changeTurn();
			return true;
		} else {
			System.out.println("invalid move");
			return false;
		}
	}

	/**
	 * Recounts the disc counts
	 */
	private void countDiscs() {
		count1 = 0;
		count2 = 0;
		count3 = 0;
		count4 = 0;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (board[x][y] == WHITE)
					count1++;
				if (board[x][y] == BLACK)
					count2++;
				if (board[x][y] == RED)
					count1++;
				if (board[x][y] == GREEN)
					count2++;
				if (board[x][y] == BLUE)
					count3++;
				if (board[x][y] == YELLOW)
					count4++;
			}
		}
	}

	public boolean isInsideBoard(int x, int y) {
		return x >= 0 && x < width && y >= 0 && y < height;
	}

	public boolean isValidMove(int x, int y, boolean executeMove) {
		return (board[x][y] == EMPTY || board[x][y] < 0) && checkFlip(x, y, executeMove) > 0;
	}

	// returns -1 if no flips from that position that direction, otherwise return
	// the number of flips from that position
	public int countFlips(int x, int y) {
		if (board[x][y] == EMPTY || board[x][y] < 0) {
			return checkFlip(x, y, SIMULATE);
		} else {
			return 0;
		}
	}

	// returns -1 if no flips in that direction, otherwise return the number of
	// flips in that direction
	private int checkFlip(int x, int y, boolean executeMove) {
		int flips = 0;

		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				if (isInsideBoard(i, j)) {
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

	// returns -1 if no flips in that direction, otherwise return the number of
	// flips in that direction
	private int checkDirection(int x, int y, int i, int j, boolean executeMove) {
		int flips;
		if (board[x + (i - x)][y + (j - y)] == EMPTY || board[x + (i - x)][y + (j - y)] < 0) {
			return -1;
		} else if (board[x + (i - x)][y + (j - y)] == turn) {
			return 0;
		} else if (isInsideBoard(x + (i - x) * 2, y + (j - y) * 2)) {
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

	public void changeTurn() {
		swapPlayers();
		showValidMoves();
	}

	/**
	 * Sets who is making a turn now
	 */
	private void swapPlayers() {
//		if (turn == WHITE) {
//			turn = BLACK;
//		} else {
//			turn = WHITE;
//		}
		int oldturn = turn;
		int i = 0;
		while (oldturn == turn){
			if (playerList[i].getColor() == turn){
				if( i < playerList.length-1){
					turn = playerList[i+1].getColor();
					player = playerList[i+1];
				} else {
					turn = playerList[0].getColor();
					player = playerList[0];
				}
			}
			i++;
		}
	}

	/**
	 * Recalculates the list of possible moves
	 */
	public void showValidMoves() {
		validMoves.clear();
		// Reset possible moves
		countValid = 0;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int current = board[x][y];
				if (current < 0) {
					board[x][y] = EMPTY;
				}
			}
		}

		// Find possible moves for new player turn
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {

				int flips = countFlips(x, y);
				if (flips > 0) {
					board[x][y] = -flips;
					validMoves.add(new Point3D(x, y, flips));
					countValid++;
				}
				// System.out.print(" " + board[x][y]);
			}
			// System.out.println(" ");
		}
		// System.out.println(" ");
	}

	/**
	 * This method checks to see if the games has finished.<br>
	 * If it has it will return true. The game is finished when ....
	 *
	 * @return true or false
	 */
	public boolean isGameFinished() {
		if (count1 + count2 + count3 + count4 == 64) {
			return true;
		}
		// CHeck for the current player valid moves
		if (countValid == 0) {
			changeTurn();
			// check for the other player...
			if (countValid == 0) {
				return true;
			}
		}
		return false;
	}

	public void setPlayerList(Player[] playerList) {
		this.playerList = playerList;
		player = playerList[turn];

		if(playerList.length > 2){
			turn = RED;
			board[3][3] = RED;
			board[3][4] = GREEN;
			board[4][3] = BLUE;
			board[4][4] = YELLOW;
		}
		countDiscs();
		showValidMoves();
	}

	public ArrayList<Point3D> getValidMoves() {
		// showValidMoves();
		return validMoves;
	}

	public Player[] getPlayerList() {
		return playerList;
	}
	public Player getPlayer() {
		return player;
	}

	public int getCountWhite() {
		return count1;
	}

	public int getCountBlack() {
		return count2;
	}

	public static int getCount1() {
		return count1;
	}

	public static int getCount2() {
		return count2;
	}

	public static int getCount3() {
		return count3;
	}

	public static int getCount4() {
		return count4;
	}

	public int getSquareType(int x, int y) {
		return board[x][y];
	}

	public int[][] getboard() {
		return board;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getTurn() {
		return turn;
	}

	public Point getLastMove() { return lastMove; }
}