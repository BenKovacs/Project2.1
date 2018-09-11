package model;

import gui.RightPanel;

import static model.Constants.*;

public class GameBoard {
	private int width;
	private int height;
	private int[][] bs;
	private int enemy;
	//Game state
	public static int turn;
	private int INVERSE_COLOUR;

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
	}


	public void flipDisc(int x, int y){

		if(isValidMove(x,y)){
			bs[x][y] = turn;
			changeTurn();
			//return true;
		} else{
			System.out.println("invalid move");
			//return false;
		}
	}



	public boolean isInsideBoard(int x, int y) {
		return x >= 0 && x <= width && y >= 0 && y <= height;
	}


	public boolean isValidMove(int x, int y) {
		return bs[x][y] == EMPTY && checkFlip(x,y);
	}

	public boolean checkFlip(int x, int y) {
	    boolean valid = false;
	    try{
	        for (int i=x-1; i<=x+1; i++)
                for (int j=y-1; j<=y+1; j++)
                    if (isInsideBoard(i,j) && bs[i][j] == enemy)
                        if(checkDirection(x,y,i,j)) {
                            valid = true;
                        }
        } catch(Exception e){}
	    return valid;
	}

	public boolean checkDirection(int x, int y, int i, int j) {
		if (bs[x+(i-x)][y+(j-y)] == EMPTY)
			return false;
		else if (bs[x+(i-x)][y+(j-y)] == turn)
			return true;
		else if (isInsideBoard(x+(i-x)*2,y+(j-y)*2))
			if (checkDirection(x+(i-x),y+(j-y),x+(i-x)*2,y+(j-y)*2)) {
				bs[x + (i - x)][y + (j - y)] = turn;
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

	}
}

