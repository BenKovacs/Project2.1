package model;

import gui.RightPanel;
import static model.Constants.*;

public class GameBoard {
	private static final boolean EXECUTE = true;
	private static final boolean SIMULATE = false;
	private int width;
	private int height;
	private int[][] board;
	private int enemy;
	//Game state
	public static int turn;
	private int INVERSE_COLOUR;
	public int countValid;
	public static int countWhite;
	public static int countBlack;

	public GameBoard(int width, int height) {
		this.width = width;
		this.height = height;

		board = new int[width][height];

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				board[x][y] = 0; // default gray
			}
		}
		turn = WHITE;
		enemy = BLACK;
		board[3][3] = WHITE;
		board[3][4] = BLACK;
		board[4][3] = BLACK;
		board[4][4] = WHITE;
		//starting game position
		showValidMoves();
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean flipDisc(int x, int y){
	    if(isValidMove(x,y, EXECUTE)){
            board[x][y] = turn;
			countDiscs();
			changeTurn();
			return true;
		} else {
			System.out.println("invalid move");
			return false;
		}
	}

	private void countDiscs() {
		countWhite = 0;
		countBlack = 0;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if(board[x][y] == WHITE)countWhite++;
			
				if(board[x][y] == BLACK)countBlack++;
			}
		}
	}
	


	public boolean isInsideBoard(int x, int y) {
		return x >= 0 && x < width && y >= 0 && y < height;
	}

	public boolean isValidMove(int x, int y, boolean executeMove) {
		return (board[x][y] == EMPTY || board[x][y] < 0) && checkFlip(x,y, executeMove) > 0;
	}

	public int countFlips(int x, int y){
		if(board[x][y] == EMPTY || board[x][y] < 0){
			return checkFlip(x,y, SIMULATE);
		} else {
			return 0;
		}
	}

	public int checkFlip(int x, int y, boolean executeMove) {
	    int flips = 0;

	    for (int i=x-1; i<=x+1; i++){
            for (int j=y-1; j<=y+1; j++) {
                if (isInsideBoard(i, j)){
                    if (board[i][j] == enemy) {
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

	//returns -1 if no flips in that direction, otherwise return the number of flips
	public int checkDirection(int x, int y, int i, int j, boolean executeMove) {
		int flips;
		if (board[x+(i-x)][y+(j-y)] == EMPTY) {
            return -1;
        } else if (board[x+(i-x)][y+(j-y)] == turn) {
            return 0;
        } else if (isInsideBoard(x+(i-x)*2,y+(j-y)*2)) {
			flips = checkDirection(x+(i-x), y+(j-y), x+(i-x)*2, y+(j-y)*2, executeMove);
            if (flips >= 0) {
                if (executeMove) {
                    board[x+(i-x)][y+(j-y)] = turn;
                }
                return flips+1;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
	}

	public void changeTurn(){
		swapPlayers();
		showValidMoves();
	}

	private void swapPlayers() {
		if (turn == WHITE) {
			turn = BLACK;
			enemy = WHITE;
		}
		else {
			turn = WHITE;
			enemy = BLACK;
		}
	}

	public void showValidMoves(){
		// Reset possible moves
		countValid = 0;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int current = board[x][y];
				if (current < 0){
                    board[x][y] = EMPTY;
				}
			}
		}
		// Find possible moves for new player turn
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {

				int flips = countFlips(x, y);
				if (flips > 0){
                    board[x][y] = -flips;
                    countValid++;
				}
				System.out.print("	" + board[x][y]);
			}
			System.out.println(" ");
		}
		System.out.println(" ");
	}

	/**
	 * This method checks to see if the games has finished.<br>
	 * If it has it will return true.
	 * The game is finished when ....
	 * @return true or false
	 */
	public boolean isGameFinished() {
		if(countWhite + countBlack == 64){
			return true;
		}
		// CHeck for the current player valid moves
		if(countValid == 0){
			swapPlayers();
			// check for the other player...
			showValidMoves();
			if (countValid == 0){
				return true;
			}
		}
		return false;
	}
	
	public int getCountWhite() {
		return countWhite;
	}


	public int getCountBlack() {
		return countBlack;
	}
	
	public int getSquareType(int x, int y) {
	    return board[x][y];
	}

	public int[][] getboard(){
		return board ;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getTurn(){
		return turn;
	}
}
