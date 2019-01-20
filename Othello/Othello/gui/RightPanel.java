package gui;

import model.data_model.GameBoard;
import model.player.Player;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.Font;

public class RightPanel extends JPanel {

	private JLabel playerTurn;
	private JLabel discs1;
	private JLabel discs2;
	private JLabel discs3;
	private JLabel discs4;
	private JLabel memory;

	private Player[] playerList;
	private String[] colorArray ={"White", "Black", "Red", "Green", "Blue", "Yellow"};

	public RightPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	public void changeTurn(GameBoard gameBoard){

		//System.out.print(colorArray[gameBoard.getTurn()] + "'s Turn");
		playerTurn.setText(colorArray[gameBoard.getTurn()] + "'s Turn\n");

		discs1.setText(playerList[0] + " " + colorArray[playerList[0].getColor()] + " = " + gameBoard.count1);
		discs2.setText(playerList[1] + " " + colorArray[playerList[1].getColor()] + " = " + gameBoard.count2);
		if (playerList.length >= 3) {discs3.setText(playerList[2] + " " + colorArray[playerList[2].getColor()] + " = " + gameBoard.count3);}
		if (playerList.length >= 4) {discs4.setText(playerList[3] + " " + colorArray[playerList[3].getColor()] + " = " + gameBoard.count4);}
		double used = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		double kb = used/1024.0;
		double mb = kb/1024.0;
		memory.setText((int)mb + " MB memory used");
		System.gc();
		System.runFinalization();
	}

	public void setPlayerList(Player[] playerList) {
		this.playerList = playerList;

		playerTurn = new JLabel(colorArray[playerList[0].getColor()] + "'s Turn\n");
		playerTurn.setFont(new Font("Arial", Font.BOLD, 20));
		playerTurn.setPreferredSize(new Dimension(200, 50));



		this.add(playerTurn);
		discs1 = new JLabel(playerList[0] + " " + colorArray[playerList[0].getColor()] + " = ");
		this.add(discs1);
		discs2 = new JLabel(playerList[1] + " " +colorArray[playerList[1].getColor()] + " = ");
		this.add(discs2);
		if (playerList.length > 2) {
			discs3 = new JLabel(playerList[2] + " " +colorArray[playerList[2].getColor()] + " = 1");
			this.add(discs3);
		}
		if (playerList.length > 3) {
			discs4 = new JLabel(playerList[3] + " " +colorArray[playerList[3].getColor()] + " = 1");
			this.add(discs4);
		}
		memory = new JLabel("Memory Usage");
		this.add(memory);
	}
}
