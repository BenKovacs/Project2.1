package gui;



import model.GameBoard;

import javax.swing.*;

import static model.Constants.WHITE;

public class RightPanel extends JPanel {

	private JLabel playerTurn;
	private JLabel whiteDiscs;
	private JLabel blackDiscs;

	public RightPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		playerTurn = new JLabel("White turn");
		this.add(playerTurn);
		whiteDiscs = new JLabel("White = 2");
		this.add(whiteDiscs);
		blackDiscs = new JLabel("Black = 2");
		this.add(blackDiscs);

	}

	void changeTurn(){

		if(GameBoard.turn == WHITE) playerTurn.setText("White turn");
		else playerTurn.setText("Black turn");

		whiteDiscs.setText("White = "+ GameBoard.countWhite);
		blackDiscs.setText("Black = "+ GameBoard.countBlack);
	}
}
