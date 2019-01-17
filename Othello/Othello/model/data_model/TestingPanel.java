package model.data_model;

import gui.BoardPanel;
import gui.RightPanel;
import gui.TestAppV2;

import static model.data_model.GameBoard.result;
import static model.data_model.GameBoard.result2;

public class TestingPanel extends BoardPanel {
	private static int gamesPlayed = 0;
	private int maxGames = 10;

	public TestingPanel(GameBoard gameB, RightPanel rPanel) {
		super(gameB, rPanel);
	}

	@Override
	public void play(int x, int y) {
		if (gameBoard.flipDisc(x, y)) {
			setGameBoard(gameBoard);
			rightPanel.changeTurn(gameBoard);
			if (gameBoard.isGameFinished()) {
				gamesPlayed++;
				if(gamesPlayed <= maxGames){
					TestAppV2.getSingleton().reset();
				} else {
					System.exit(-1);
				}
				System.out.println(gamesPlayed +  "/" + maxGames + " games played");
			}
		}
	}
}
