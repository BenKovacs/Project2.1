package oth.model;


public class GameBoard {
	int width;
	int height;
	private BoardSquare[][] bs;
	private int tx, ty, tnx, tny = -1;
	//private Player currentPlayer ;
	
	public BoardSquare[][] getboard(){
		
		return bs ;
	}
	
//	public GameBoard(GameBoard g){
//		clone(g) ;
//	}

	public GameBoard(int width, int height) {
		this.width = width;
		this.height = height;

		bs = new BoardSquare[width][height];

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				bs[x][y] = new BoardSquare(x, y);
			}
		}
		//currentPlayer = players.get(0);

	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public BoardSquare getBoardSquare(int x, int y) {
		return bs[x][y];
	}

	public BoardSquare[][] getBs() {
		return bs;
	}
	
	

	
	
	
	
//	public boolean isOver() {
//    	int one = 0;
//    	int two = 0;
//    	
//    	
//    	for(int i = 0; i < 16; i++)
//    	{
//    		for(int j = 0; j < 14; j++)
//    		{
//    			if(bs[i][j].isOccupied())
//    			{
//    				if(bs[i][j].getOccupant().getTeam().getTeamId() == 1)
//    				{
//    					one++;
//    				}
//    				if(bs[i][j].getOccupant().getTeam().getTeamId() == 2)
//    				{
//    					two++;
//    				}
//    			}
//    		}
//    	}
//    	if(one < 1 || two < 1){
//    		System.out.println("game is over yo !");
//    		return true;
//    	}
//    	else return false;
//    }
	
	/*public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = players.get(currentPlayer-1);
	}*/
	}

