package gui;

import model.data_model.GameBoard;

import java.util.concurrent.TimeUnit;

public class TestingPanel extends BoardPanel {
	private static int gamesPlayed = 0;
	private int maxGames = 10;

	public TestingPanel(GameBoard gameB, RightPanel rPanel) {
		super(gameB, rPanel);
	}

	@Override
	public void play(int x, int y) {
		if (gameBoard.flipDisc(x, y)) {
			//setGameBoard(gameBoard);
			//rightPanel.changeTurn(gameBoard);
			if (gameBoard.isGameFinished()) {
				gamesPlayed++;
				System.out.println("Scores are " + gameBoard.getCount1() + " and " + gameBoard.getCount2());
				if(gamesPlayed <= maxGames){
					try{
						TimeUnit.SECONDS.sleep(4);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					TestApp.getSingleton().reset();
				} else {
					System.exit(-1);
				}
				System.out.println(gamesPlayed +  "/" + maxGames + " games played");
			}
		}
	}
}
