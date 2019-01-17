package gui;

import model.data_model.GameBoard;

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
				if(gamesPlayed <= maxGames){
					TestApp.getSingleton().reset();
				} else {
					System.exit(-1);
				}
				System.out.println(gamesPlayed +  "/" + maxGames + " games played");
			}
		}
	}
}
