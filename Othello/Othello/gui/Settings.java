package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Settings extends JDialog {

		private final JPanel contentPanel = new JPanel();
		private String[] playersList = {"Human", "Greedy", "MinMax", "MCTS", "Random", "None"};
		private String[] numberOfPlayers = {"2","3","4"};
		protected boolean okSelected;
		protected boolean cancelSelected;
		private JTextField tfDepth;
		private JComboBox cbNumOfPlayers;
		private JComboBox cbPlayers1;
		private JComboBox cbPlayers2;
		private JComboBox cbPlayers3;
		private JComboBox cbPlayers4;

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


			// Number Of Players: 2-4 players
			JLabel lblNumOfPlayers = new JLabel(" Number of Players : ");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, lblNumOfPlayers, -30, SpringLayout.NORTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.WEST, lblNumOfPlayers, 10, SpringLayout.WEST, contentPanel);
			contentPanel.add(lblNumOfPlayers);

			cbNumOfPlayers = new JComboBox(numberOfPlayers);
			cbNumOfPlayers.setSelectedIndex(0);
			cbNumOfPlayers.addActionListener(cbNumOfPlayers);
			sl_contentPanel.putConstraint(SpringLayout.NORTH, cbNumOfPlayers, -30, SpringLayout.NORTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.WEST, cbNumOfPlayers, 200, SpringLayout.WEST, contentPanel);
			contentPanel.add(cbNumOfPlayers);

			// Player 1
			JLabel lblPlayer1 = new JLabel("Player 1 : ");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, lblPlayer1, 10, SpringLayout.NORTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.WEST, lblPlayer1, 10, SpringLayout.WEST, contentPanel);
			contentPanel.add(lblPlayer1);
			
			cbPlayers1 = new JComboBox(playersList);
			cbPlayers1.setSelectedIndex(0);
			cbPlayers1.addActionListener(cbPlayers1);
			sl_contentPanel.putConstraint(SpringLayout.NORTH, cbPlayers1, 10, SpringLayout.NORTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.WEST, cbPlayers1, 200, SpringLayout.WEST, contentPanel);
			contentPanel.add(cbPlayers1);
			
			// Player 2
			JLabel lblPlayer2 = new JLabel("Player 2 : ");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, lblPlayer2, 40, SpringLayout.NORTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.WEST, lblPlayer2, 10, SpringLayout.WEST, contentPanel);
			contentPanel.add(lblPlayer2);

			cbPlayers2 = new JComboBox(playersList);
			cbPlayers2.setSelectedIndex(0);
			cbPlayers2.addActionListener(cbPlayers2);
			sl_contentPanel.putConstraint(SpringLayout.NORTH, cbPlayers2, 40, SpringLayout.NORTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.WEST, cbPlayers2, 200, SpringLayout.WEST, contentPanel);
			contentPanel.add(cbPlayers2);

			// Player 3
			JLabel lblPlayer3 = new JLabel("Player 3 : ");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, lblPlayer3, 70, SpringLayout.NORTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.WEST, lblPlayer3, 10, SpringLayout.WEST, contentPanel);
			contentPanel.add(lblPlayer3);

			cbPlayers3 = new JComboBox(playersList);
			cbPlayers3.setSelectedIndex(0);
			cbPlayers3.addActionListener(cbPlayers3);
			sl_contentPanel.putConstraint(SpringLayout.NORTH, cbPlayers3, 70, SpringLayout.NORTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.WEST, cbPlayers3, 200, SpringLayout.WEST, contentPanel);
			contentPanel.add(cbPlayers3);

			//Player 4
			JLabel lblPlayer4 = new JLabel("Player 4 : ");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, lblPlayer4, 100, SpringLayout.NORTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.WEST, lblPlayer4, 10, SpringLayout.WEST, contentPanel);
			contentPanel.add(lblPlayer4);

			cbPlayers4 = new JComboBox(playersList);
			cbPlayers4.setSelectedIndex(0);
			cbPlayers4.addActionListener(cbPlayers4);
			sl_contentPanel.putConstraint(SpringLayout.NORTH, cbPlayers4, 100, SpringLayout.NORTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.WEST, cbPlayers4, 200, SpringLayout.WEST, contentPanel);
			contentPanel.add(cbPlayers4);


			// Depth Level
			JLabel lblDepthLevel = new JLabel("Depth Level");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, lblDepthLevel, 150, SpringLayout.NORTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.WEST, lblDepthLevel, 10, SpringLayout.WEST, contentPanel);
			contentPanel.add(lblDepthLevel);
			
			tfDepth = new JTextField();
			tfDepth.setHorizontalAlignment(SwingConstants.RIGHT);
			tfDepth.setText("4");
			sl_contentPanel.putConstraint(SpringLayout.SOUTH, tfDepth, 160, SpringLayout.NORTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.WEST, tfDepth, 200, SpringLayout.WEST, contentPanel);
			contentPanel.add(tfDepth);
			tfDepth.setColumns(10);
			{
				JPanel buttonPane = new JPanel();
				buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
				getContentPane().add(buttonPane, BorderLayout.SOUTH);
				{
					JButton okButton = new JButton("OK");
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
			}
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

		public int getNumPlayers() {
			return (int)(cbNumOfPlayers.getSelectedItem());
		}

		public String getPlayer1() {
			return (String)(cbPlayers1.getSelectedItem());
		}

		public String getPlayer2() {
			return (String)(cbPlayers2.getSelectedItem());
		}

		public  String getPlayer3() { return  (String)(cbPlayers3.getSelectedItem());}

		public  String getPlayer4() { return  (String)(cbPlayers4.getSelectedItem());}


}
