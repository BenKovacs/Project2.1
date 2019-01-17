package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Settings extends JDialog {

		private final JPanel contentPanel = new JPanel();
		private String[] playersList = {"Human", "Greedy", "MinMax", "MCTS", "SMCTS", "SMCTS2", "Random"};
		protected boolean okSelected;
		protected boolean cancelSelected;
		private JTextField tfDepth;
		private JComboBox cbNumOfPlayers;
		private JComboBox cbPlayers1;
		private JComboBox cbPlayers2;
		private JComboBox cbPlayers3;
		private JComboBox cbPlayers4;
		private int numOfPlayers = 0;

		public JButton btn2player;
		public JButton btn3player;
		public JButton btn4player;

		public JButton okButton;

		public JButton btnreturn;

	/**
		 * Create the dialog.
		 */
		public Settings() {
			setModal(true);
			setBounds(100, 100, 450, 336);
			getContentPane().setLayout(new BorderLayout());
			contentPanel.setBorder(new EmptyBorder(50, 50, 50, 50));
			getContentPane().add(contentPanel, BorderLayout.CENTER);
			SpringLayout sl_contentPanel = new SpringLayout();
     		contentPanel.setLayout(sl_contentPanel);




			// Player 1
			JLabel lblPlayer1 = new JLabel("Player 1 : ");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, lblPlayer1, 10, SpringLayout.NORTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.WEST, lblPlayer1, 10, SpringLayout.WEST, contentPanel);
			contentPanel.add(lblPlayer1);
			lblPlayer1.setVisible(false);

			cbPlayers1 = new JComboBox(playersList);
			cbPlayers1.setSelectedIndex(0);
			cbPlayers1.addActionListener(cbPlayers1);
			sl_contentPanel.putConstraint(SpringLayout.NORTH, cbPlayers1, 10, SpringLayout.NORTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.WEST, cbPlayers1, 200, SpringLayout.WEST, contentPanel);
			contentPanel.add(cbPlayers1);
			cbPlayers1.setVisible(false);
			// Player 2
			JLabel lblPlayer2 = new JLabel("Player 2 : ");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, lblPlayer2, 40, SpringLayout.NORTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.WEST, lblPlayer2, 10, SpringLayout.WEST, contentPanel);
			contentPanel.add(lblPlayer2);
			lblPlayer2.setVisible(false);

			cbPlayers2 = new JComboBox(playersList);
			cbPlayers2.setSelectedIndex(0);
			cbPlayers2.addActionListener(cbPlayers2);
			sl_contentPanel.putConstraint(SpringLayout.NORTH, cbPlayers2, 40, SpringLayout.NORTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.WEST, cbPlayers2, 200, SpringLayout.WEST, contentPanel);
			contentPanel.add(cbPlayers2);
			cbPlayers2.setVisible(false);

			// Player 3
			JLabel lblPlayer3 = new JLabel("Player 3 : ");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, lblPlayer3, 70, SpringLayout.NORTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.WEST, lblPlayer3, 10, SpringLayout.WEST, contentPanel);
			contentPanel.add(lblPlayer3);
			lblPlayer3.setVisible(false);

			cbPlayers3 = new JComboBox(playersList);
			cbPlayers3.setSelectedIndex(0);
			cbPlayers3.addActionListener(cbPlayers3);
			sl_contentPanel.putConstraint(SpringLayout.NORTH, cbPlayers3, 70, SpringLayout.NORTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.WEST, cbPlayers3, 200, SpringLayout.WEST, contentPanel);
			contentPanel.add(cbPlayers3);
			cbPlayers3.setVisible(false);
			//Player 4
			JLabel lblPlayer4 = new JLabel("Player 4 : ");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, lblPlayer4, 100, SpringLayout.NORTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.WEST, lblPlayer4, 10, SpringLayout.WEST, contentPanel);
			contentPanel.add(lblPlayer4);
			lblPlayer4.setVisible(false);

			cbPlayers4 = new JComboBox(playersList);
			cbPlayers4.setSelectedIndex(0);
			cbPlayers4.addActionListener(cbPlayers4);
			sl_contentPanel.putConstraint(SpringLayout.NORTH, cbPlayers4, 100, SpringLayout.NORTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.WEST, cbPlayers4, 200, SpringLayout.WEST, contentPanel);
			contentPanel.add(cbPlayers4);
			cbPlayers4.setVisible(false);
			// Depth Level
			JLabel lblDepthLevel = new JLabel("Depth Level");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, lblDepthLevel, 150, SpringLayout.NORTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.WEST, lblDepthLevel, 10, SpringLayout.WEST, contentPanel);
			contentPanel.add(lblDepthLevel);
			lblDepthLevel.setVisible(false);

			tfDepth = new JTextField();
			tfDepth.setHorizontalAlignment(SwingConstants.RIGHT);
			tfDepth.setText("4");
			sl_contentPanel.putConstraint(SpringLayout.SOUTH, tfDepth, 160, SpringLayout.NORTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.WEST, tfDepth, 200, SpringLayout.WEST, contentPanel);
			contentPanel.add(tfDepth);
			tfDepth.setColumns(10);
			tfDepth.setVisible(false);


			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setVisible(false);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						okSelected = true;
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cancelSelected = true;
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}


			// return button to go back to the option of player number
			btnreturn = new JButton("return");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, btnreturn, 180, SpringLayout.NORTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.WEST, btnreturn, 10, SpringLayout.WEST, contentPanel);
			contentPanel.add(btnreturn);
			btnreturn.setVisible(false);
			btnreturn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					btn2player.setVisible(true);
					btn3player.setVisible(true);
					btn4player.setVisible(true);
					lblPlayer1.setVisible(false);
					cbPlayers1.setVisible(false);
					lblPlayer2.setVisible(false);
					cbPlayers2.setVisible(false);
					lblPlayer3.setVisible(false);
					cbPlayers3.setVisible(false);
					lblPlayer4.setVisible(false);
					cbPlayers4.setVisible(false);
					lblDepthLevel.setVisible(false);
					tfDepth.setVisible(false);
					btnreturn.setVisible(false);
					numOfPlayers = 0;
					okButton.setVisible(false);
				}
			});
			//button for 2 player game mode
			btn2player = new JButton(" 2 ");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, btn2player, 10, SpringLayout.NORTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.WEST, btn2player, 80, SpringLayout.WEST, contentPanel);
			contentPanel.add(btn2player);
			btn2player.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {


					btn2player.setVisible(false);
					btn3player.setVisible(false);
					btn4player.setVisible(false);
					lblPlayer1.setVisible(true);
					cbPlayers1.setVisible(true);
					lblPlayer2.setVisible(true);
					cbPlayers2.setVisible(true);
					lblDepthLevel.setVisible(true);
					tfDepth.setVisible(true);
					btnreturn.setVisible(true);
					numOfPlayers = 2;
					okButton.setVisible(true);
				}
			});

			//button for 3 player game mode
			btn3player = new JButton(" 3 ");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, btn3player, 10, SpringLayout.NORTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.WEST, btn3player, 140, SpringLayout.WEST, contentPanel);
			contentPanel.add(btn3player);
			btn3player.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {


					btn2player.setVisible(false);
					btn3player.setVisible(false);
					btn4player.setVisible(false);
					lblPlayer1.setVisible(true);
					cbPlayers1.setVisible(true);
					lblPlayer2.setVisible(true);
					cbPlayers2.setVisible(true);
					lblPlayer3.setVisible(true);
					cbPlayers3.setVisible(true);
					lblDepthLevel.setVisible(true);
					tfDepth.setVisible(true);
					btnreturn.setVisible(true);
					numOfPlayers = 3;
					okButton.setVisible(true);
				}
			});

			//button for 4 player game mode
			btn4player = new JButton(" 4 ");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, btn4player, 10, SpringLayout.NORTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.WEST, btn4player, 200, SpringLayout.WEST, contentPanel);
			contentPanel.add(btn4player);
			btn4player.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					btn2player.setVisible(false);
					btn3player.setVisible(false);
					btn4player.setVisible(false);
					lblPlayer1.setVisible(true);
					cbPlayers1.setVisible(true);
					lblPlayer2.setVisible(true);
					cbPlayers2.setVisible(true);
					lblPlayer3.setVisible(true);
					cbPlayers3.setVisible(true);
					lblPlayer4.setVisible(true);
					cbPlayers4.setVisible(true);
					lblDepthLevel.setVisible(true);
					tfDepth.setVisible(true);
					btnreturn.setVisible(true);
					numOfPlayers = 4;
					okButton.setVisible(true);
				}
			});
		}

		public boolean isOkSelected() {
			return okSelected;
		}

		public void setOkSelected(boolean okSelected) {
			this.okSelected = okSelected;
		}

		public boolean isCancelSelected() {
			return cancelSelected;
		}

		public void setCancelSelected(boolean cancelSelected) {
			this.cancelSelected = cancelSelected;
		}
		

		public int getDepthLevel() {
			try{
				return Integer.parseInt(tfDepth.getText());
			}catch(Exception e){
				e.printStackTrace();
			}
			return 0;
		}

		public int getNumPlayers() { return numOfPlayers; }

		public String getPlayer1() { return (String)(cbPlayers1.getSelectedItem()); }

		public String getPlayer2() { return (String)(cbPlayers2.getSelectedItem()); }

		public String getPlayer3() { return  (String)(cbPlayers3.getSelectedItem());}

		public String getPlayer4() { return  (String)(cbPlayers4.getSelectedItem());}

}
