package model;

import static model.Constants.*;

public class GameBoard {
	private int width;
	private int height;
	private int[][] bs;

	//Game state
	private int turn = WHITE;

	public GameBoard(int width, int height) {
		this.width = width;
		this.height = height;

		bs = new int[width][height];

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				bs[x][y] = -1; // default gray
			}
		}
	}

	public boolean flipDisc(int x, int y){

		if(bs[x][y] == EMPTY){
			// put the disc only if the place is empty
			bs[x][y] = turn;
			changeTurn();

			return true;
		} else{
			System.out.println("Spot already selected");
			return false;
		}
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
		if(turn == WHITE) turn = BLACK;
		else turn = WHITE;
	}
}

