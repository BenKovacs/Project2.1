package gui;



import model.GameBoard;

import javax.swing.*;

import static model.Constants.WHITE;

public class RightPanel extends JPanel {

	private JLabel playerTurn;

	public RightPanel() {
			/*setBounds(100, 100, 450, 336);
			this.setBorder(new EmptyBorder(5, 5, 5, 5));
			SpringLayout sl_contentPanel = new SpringLayout();
			this.setLayout(sl_contentPanel);

			*/
		playerTurn = new JLabel("White turn");
		this.add(playerTurn);
			/*

			JRadioButton rdbtnSmall = new JRadioButton("Small");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, rdbtnSmall, 0, SpringLayout.NORTH, this);
			this.add(rdbtnSmall);

			JRadioButton rdbtnMedium = new JRadioButton("Medium");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, rdbtnMedium, 6, SpringLayout.SOUTH, rdbtnSmall);
			sl_contentPanel.putConstraint(SpringLayout.WEST, rdbtnMedium, 0, SpringLayout.WEST, rdbtnSmall);
			this.add(rdbtnMedium);

			JRadioButton rdbtnLarge = new JRadioButton("Large");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, rdbtnLarge, 6, SpringLayout.SOUTH, rdbtnMedium);
			sl_contentPanel.putConstraint(SpringLayout.WEST, rdbtnLarge, 0, SpringLayout.WEST, rdbtnSmall);
			this.add(rdbtnLarge);

			tfHumanPlayers = new JTextField();
			tfHumanPlayers.setHorizontalAlignment(SwingConstants.RIGHT);
			tfHumanPlayers.setText("0");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, tfHumanPlayers, 6, SpringLayout.SOUTH, rdbtnLarge);
			sl_contentPanel.putConstraint(SpringLayout.WEST, tfHumanPlayers, 0, SpringLayout.WEST, rdbtnSmall);
			this.add(tfHumanPlayers);
			tfHumanPlayers.setColumns(10);

			tfAiPlayers = new JTextField();
			tfAiPlayers.setHorizontalAlignment(SwingConstants.RIGHT);
			tfAiPlayers.setText("2");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, tfAiPlayers, 6, SpringLayout.SOUTH, tfHumanPlayers);
			sl_contentPanel.putConstraint(SpringLayout.EAST, tfAiPlayers, 0, SpringLayout.EAST, tfHumanPlayers);
			this.add(tfAiPlayers);
			tfAiPlayers.setColumns(10);

			JLabel lblNoHumans = new JLabel("No. Humans");
			sl_contentPanel.putConstraint(SpringLayout.WEST, lblNoHumans, 0, SpringLayout.WEST, lblBoardSiye);
			this.add(lblNoHumans);

			JLabel lblNoAi = new JLabel("No. AI");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, lblNoAi, 114, SpringLayout.NORTH, this);
			sl_contentPanel.putConstraint(SpringLayout.SOUTH, lblNoHumans, -12, SpringLayout.NORTH, lblNoAi);
			sl_contentPanel.putConstraint(SpringLayout.WEST, lblNoAi, 0, SpringLayout.WEST, lblBoardSiye);
			this.add(lblNoAi);

			tfKangaroos = new JTextField();
			tfKangaroos.setHorizontalAlignment(SwingConstants.RIGHT);
			tfKangaroos.setText("5");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, tfKangaroos, 6, SpringLayout.SOUTH, tfAiPlayers);
			sl_contentPanel.putConstraint(SpringLayout.WEST, tfKangaroos, 0, SpringLayout.WEST, rdbtnSmall);
			this.add(tfKangaroos);
			tfKangaroos.setColumns(10);

			JLabel lblNoKangasPer = new JLabel("No. Kangas per player");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, lblNoKangasPer, 18, SpringLayout.SOUTH, lblNoAi);
			sl_contentPanel.putConstraint(SpringLayout.WEST, rdbtnSmall, 18, SpringLayout.EAST, lblNoKangasPer);
			sl_contentPanel.putConstraint(SpringLayout.WEST, tfAiPlayers, 18, SpringLayout.EAST, lblNoKangasPer);
			sl_contentPanel.putConstraint(SpringLayout.WEST, lblNoKangasPer, 0, SpringLayout.WEST, lblBoardSiye);
			this.add(lblNoKangasPer);

			JLabel lblDepthLevel = new JLabel("Depth Level");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, lblDepthLevel, 16, SpringLayout.SOUTH, lblNoKangasPer);
			sl_contentPanel.putConstraint(SpringLayout.WEST, lblDepthLevel, 0, SpringLayout.WEST, lblBoardSiye);
			sl_contentPanel.putConstraint(SpringLayout.EAST, lblDepthLevel, -50, SpringLayout.EAST, lblNoKangasPer);
			this.add(lblDepthLevel);

			tfDepth = new JTextField();
			tfDepth.setHorizontalAlignment(SwingConstants.RIGHT);
			tfDepth.setText("2");
			sl_contentPanel.putConstraint(SpringLayout.SOUTH, tfDepth, 0, SpringLayout.SOUTH, lblDepthLevel);
			sl_contentPanel.putConstraint(SpringLayout.EAST, tfDepth, 0, SpringLayout.EAST, tfHumanPlayers);
			this.add(tfDepth);
			tfDepth.setColumns(10);

			chckbxDebugMode = new JCheckBox("Debug Mode");
			chckbxDebugMode.setHorizontalAlignment(SwingConstants.LEFT);
			sl_contentPanel.putConstraint(SpringLayout.WEST, chckbxDebugMode, 0, SpringLayout.WEST, lblBoardSiye);
			sl_contentPanel.putConstraint(SpringLayout.SOUTH, chckbxDebugMode, 0, SpringLayout.SOUTH, this);*/
	}

	void changeTurn(){
//		if(playerTurn.getText().equals("White turn")) playerTurn.setText("Black turn");
//		else playerTurn.setText("White turn");
		if(GameBoard.turn == WHITE) playerTurn.setText("white turn");
		else playerTurn.setText("black turn");
	}
}