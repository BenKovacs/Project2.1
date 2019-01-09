package gui;

import model.GameBoard;
import model.player.Player;

import javax.swing.*;

import static model.Constants.WHITE;

import java.awt.Dimension;
import java.awt.Font;

public class RightPanel extends JPanel {

	private JLabel playerTurn;
	private JLabel discs1;
	private JLabel discs2;
	private JLabel discs3;
	private JLabel discs4;

	Player[] playerList;
	String[] colorArray ={"White", "Black", "Red", "Green", "Blue", "Yellow"};;

	public RightPanel(Player[] playerList) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.playerList = playerList;



	}

	void changeTurn(){
		playerTurn.setText(colorArray[GameBoard.turn] + " turn\n");

		discs1.setText(colorArray[playerList[0].getColor()] + " = " + GameBoard.count1);
		discs2.setText(colorArray[playerList[1].getColor()] + " = " + GameBoard.count2);
		if (playerList.length >= 3) {discs3.setText(colorArray[playerList[2].getColor()] + " = " + GameBoard.count3);}
		if (playerList.length >= 4) {discs4.setText(colorArray[playerList[3].getColor()] + " = " + GameBoard.count4);}
	}

	public void setPlayerList(Player[] playerList) {
		this.playerList = playerList;

		playerTurn = new JLabel(colorArray[playerList[0].getColor()] + " Turn");
		playerTurn.setFont(new Font("Arial", Font.BOLD, 20));
		playerTurn.setPreferredSize(new Dimension(200, 50));



		this.add(playerTurn);
		discs1 = new JLabel(colorArray[playerList[0].getColor()] + " = " + GameBoard.count1);
		this.add(discs1);
		discs2 = new JLabel(colorArray[playerList[1].getColor()] + " = " + GameBoard.count2);
		this.add(discs2);
		if (playerList.length > 2) {
			discs3 = new JLabel(colorArray[playerList[2].getColor()] + " = 1");
			this.add(discs3);
		}
		if (playerList.length > 3) {
			discs4 = new JLabel(colorArray[playerList[3].getColor()] + " = 1");
			this.add(discs4);
		}
	}
}
