package model;

import gui.RightPanel;

import static model.Constants.*;

public class GameBoard {
	private static final boolean EXECUTE = true;
	private static final boolean SIMULATE = false;
	private int width;
	private int height;
	private int[][] bs;
	private int enemy;
	//Game state
	public static int turn;
	private int INVERSE_COLOUR;
	public static int countWhite;
	public static int countBlack;

	public GameBoard(int width, int height) {
		this.width = width;
		this.height = height;

		bs = new int[width][height];

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				bs[x][y] = -1; // default gray
			}
		}
		turn = WHITE;
		enemy = BLACK;
		bs[3][3] = WHITE;
		bs[3][4] = BLACK;
		bs[4][3] = BLACK;
		bs[4][4] = WHITE;
		//starting game position
		showValidMoves();
	}


	public void flipDisc(int x, int y){

		if(isValidMove(x,y, EXECUTE)){
			bs[x][y] = turn;
			countDiscs();
			changeTurn();
			//return true;
		} else{
			System.out.println("invalid move");
			//return false;
		}
	}



	private void countDiscs() {
		countWhite = 0;
		countBlack = 0;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if(bs[x][y] == WHITE)
				{
					countWhite++;
				}
				if(bs[x][y] == BLACK)
				{
					countBlack++;
				}
			}
		}
	}
	


	public boolean isInsideBoard(int x, int y) {
		return x >= 0 && x < width && y >= 0 && y < height;
	}


	public boolean isValidMove(int x, int y, boolean executeMove) {
//		return bs[x][y] == VALID;
		return (bs[x][y] == EMPTY || bs[x][y] == VALID) && checkFlip(x,y, executeMove);
	}

	public boolean checkFlip(int x, int y, boolean executeMove) {
	    boolean valid = false;

	    for (int i=x-1; i<=x+1; i++){
            for (int j=y-1; j<=y+1; j++) {
                try {
                    if (isInsideBoard(i, j) && bs[i][j] == enemy)
                        if (checkDirection(x, y, i, j, executeMove)) {
                            valid = true;
                        }
                } catch(Exception e) {}
            }
	    }
	    return valid;
	}

	public boolean checkDirection(int x, int y, int i, int j, boolean executeMove) {
		if (bs[x+(i-x)][y+(j-y)] == EMPTY)
			return false;
		else if (bs[x+(i-x)][y+(j-y)] == turn)
			return true;
		else if (isInsideBoard(x+(i-x)*2,y+(j-y)*2))
			if (checkDirection(x+(i-x),y+(j-y),x+(i-x)*2,y+(j-y)*2, executeMove)) {
				if(executeMove){
					bs[x + (i - x)][y + (j - y)] = turn;
				}
				return true;
			}
			else
				return false;
		else
			return false;
	}



	public int getSquareType(int x, int y) { return bs[x][y]; }

	public int[][] getboard(){ return bs ; }

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getTurn(){
		return turn;
	}

	public void changeTurn(){
		if(turn == WHITE) {
			turn = BLACK;
			enemy = WHITE;
		}
		else {
			turn = WHITE;
			enemy = BLACK;
		}
		showValidMoves();
	}
	public void showValidMoves(){
		// Reset possible moves
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int current = bs[x][y];
				if (current == VALID){
					bs[x][y] = EMPTY;
				}
			}
		}
		// Find possible moves for new player turn
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (isValidMove(x, y, SIMULATE)){
					bs[x][y] = VALID;
				}
			}
		}
		
	}


	public int getCountWhite() {
		return countWhite;
	}


	public int getCountBlack() {
		return countBlack;
	}


}

