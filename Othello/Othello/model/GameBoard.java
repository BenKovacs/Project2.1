package model;


public class GameBoard {
	int width;
	int height;
	private int[][] bs;

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

	public void changeSquareType(int x, int y){
		//default move as white
		if(bs[x][y] == -1) bs[x][y] = 1;
		else if(bs[x][y] == 1) bs[x][y] = 0;
		else if(bs[x][y] == 0) bs[x][y] = 1;

		System.out.println("changes "+ x + " "+ y);
	}

	public int getSquareType(int x, int y) { return bs[x][y]; }

	public int[][] getboard(){ return bs ; }

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
}

