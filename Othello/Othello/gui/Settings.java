package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Settings extends JDialog {

		private final JPanel contentPanel = new JPanel();
		private String[] playersList = {"Human", "Greedy", "MinMax", "MCTS", "Random"};
		protected boolean okSelected;
		protected boolean cancelSelected;
		private JTextField tfDepth;
		private JCheckBox chckbxDebugMode;
		private JComboBox cbPlayers1;
		private JComboBox cbPlayers2;
		

//		/**
//		 * Launch the application.
//		 */
//		public static void main(String[] args) {
//			try {
//				Settings dialog = new Settings();
//				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//				dialog.setVisible(true);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}

		/**
		 * Create the dialog.
		 */
		public Settings() {
			setModal(true);
			setBounds(100, 100, 450, 336);
			getContentPane().setLayout(new BorderLayout());
			contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			getContentPane().add(contentPanel, BorderLayout.CENTER);
			SpringLayout sl_contentPanel = new SpringLayout();
			contentPanel.setLayout(sl_contentPanel);
			
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
			
			// Player 1
			JLabel lblPlayer2 = new JLabel("Player 2 : ");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, lblPlayer2, 50, SpringLayout.NORTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.WEST, lblPlayer2, 10, SpringLayout.WEST, contentPanel);
			contentPanel.add(lblPlayer2);
			
			cbPlayers2 = new JComboBox(playersList);
			cbPlayers2.setSelectedIndex(0);
			cbPlayers2.addActionListener(cbPlayers2);
			sl_contentPanel.putConstraint(SpringLayout.NORTH, cbPlayers2, 50, SpringLayout.NORTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.WEST, cbPlayers2, 200, SpringLayout.WEST, contentPanel);
			contentPanel.add(cbPlayers2);
			
			JLabel lblDepthLevel = new JLabel("Depth Level");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, lblDepthLevel, 16, SpringLayout.SOUTH, lblPlayer2);
			sl_contentPanel.putConstraint(SpringLayout.WEST, lblDepthLevel, 0, SpringLayout.WEST, lblPlayer1);
			sl_contentPanel.putConstraint(SpringLayout.EAST, lblDepthLevel, 50, SpringLayout.EAST, lblPlayer2);
			contentPanel.add(lblDepthLevel);
			
			tfDepth = new JTextField();
			tfDepth.setHorizontalAlignment(SwingConstants.RIGHT);
			tfDepth.setText(" 2 ");
			sl_contentPanel.putConstraint(SpringLayout.SOUTH, tfDepth, 5, SpringLayout.SOUTH, lblDepthLevel);
			sl_contentPanel.putConstraint(SpringLayout.EAST, tfDepth, 210, SpringLayout.EAST, lblPlayer2);
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
		
		public boolean isDebugMode() {
			return chckbxDebugMode.isSelected();
		}

		public int getDepthLevel() {
			try{
				return Integer.parseInt(tfDepth.getText());
			}catch(Exception e){
				
			}
			return 0;
		}

		public int getNumPlayers() {
			return 2;
		}

		public String getPlayer1() {
			return (String)(cbPlayers1.getSelectedItem());
		}

		public String getPlayer2() {
			return (String)(cbPlayers2.getSelectedItem());
		}
	}






/////


//tfDepth = new JTextField();
//tfDepth.setHorizontalAlignment(SwingConstants.RIGHT);
//tfDepth.setText("2");
//sl_contentPanel.putConstraint(SpringLayout.SOUTH, tfDepth, 0, SpringLayout.SOUTH, lblDepthLevel);
//sl_contentPanel.putConstraint(SpringLayout.EAST, tfDepth, 0, SpringLayout.EAST, tfHumanPlayers);
//contentPanel.add(tfDepth);
//tfDepth.setColumns(10);

//JRadioButton rdbtnSmall = new JRadioButton("Small");
//sl_contentPanel.putConstraint(SpringLayout.NORTH, rdbtnSmall, 0, SpringLayout.NORTH, contentPanel);
//contentPanel.add(rdbtnSmall);
//
//JRadioButton rdbtnMedium = new JRadioButton("Medium");
//sl_contentPanel.putConstraint(SpringLayout.NORTH, rdbtnMedium, 6, SpringLayout.SOUTH, rdbtnSmall);
//sl_contentPanel.putConstraint(SpringLayout.WEST, rdbtnMedium, 0, SpringLayout.WEST, rdbtnSmall);
//contentPanel.add(rdbtnMedium);
//
//JRadioButton rdbtnLarge = new JRadioButton("Large");
//sl_contentPanel.putConstraint(SpringLayout.NORTH, rdbtnLarge, 6, SpringLayout.SOUTH, rdbtnMedium);
//sl_contentPanel.putConstraint(SpringLayout.WEST, rdbtnLarge, 0, SpringLayout.WEST, rdbtnSmall);
//contentPanel.add(rdbtnLarge);
//
//tfHumanPlayers = new JTextField();
//tfHumanPlayers.setHorizontalAlignment(SwingConstants.RIGHT);
//tfHumanPlayers.setText("1");
//sl_contentPanel.putConstraint(SpringLayout.NORTH, tfHumanPlayers, 6, SpringLayout.SOUTH, rdbtnLarge);
//sl_contentPanel.putConstraint(SpringLayout.WEST, tfHumanPlayers, 0, SpringLayout.WEST, rdbtnSmall);
//contentPanel.add(tfHumanPlayers);
//tfHumanPlayers.setColumns(10);
//
//tfAiPlayers = new JTextField();
//tfAiPlayers.setHorizontalAlignment(SwingConstants.RIGHT);
//tfAiPlayers.setText("1");
//sl_contentPanel.putConstraint(SpringLayout.NORTH, tfAiPlayers, 6, SpringLayout.SOUTH, tfHumanPlayers);
//sl_contentPanel.putConstraint(SpringLayout.EAST, tfAiPlayers, 0, SpringLayout.EAST, tfHumanPlayers);
//contentPanel.add(tfAiPlayers);
//tfAiPlayers.setColumns(10);
//
//JLabel lblNoHumans = new JLabel("No. Humans");
//sl_contentPanel.putConstraint(SpringLayout.WEST, lblNoHumans, 0, SpringLayout.WEST, lblBoardSiye);
//contentPanel.add(lblNoHumans);
//
//JLabel lblNoAi = new JLabel("No. AI");
//sl_contentPanel.putConstraint(SpringLayout.NORTH, lblNoAi, 114, SpringLayout.NORTH, contentPanel);
//sl_contentPanel.putConstraint(SpringLayout.SOUTH, lblNoHumans, -12, SpringLayout.NORTH, lblNoAi);
//sl_contentPanel.putConstraint(SpringLayout.WEST, lblNoAi, 0, SpringLayout.WEST, lblBoardSiye);
//contentPanel.add(lblNoAi);
//
//tfKangaroos = new JTextField();
//tfKangaroos.setHorizontalAlignment(SwingConstants.RIGHT);
//tfKangaroos.setText("5");
//sl_contentPanel.putConstraint(SpringLayout.NORTH, tfKangaroos, 6, SpringLayout.SOUTH, tfAiPlayers);
//sl_contentPanel.putConstraint(SpringLayout.WEST, tfKangaroos, 0, SpringLayout.WEST, rdbtnSmall);
//contentPanel.add(tfKangaroos);
//tfKangaroos.setColumns(10);
//
//JLabel lblNoKangasPer = new JLabel("No. Kangas per player");
//sl_contentPanel.putConstraint(SpringLayout.NORTH, lblNoKangasPer, 18, SpringLayout.SOUTH, lblNoAi);
//sl_contentPanel.putConstraint(SpringLayout.WEST, rdbtnSmall, 18, SpringLayout.EAST, lblNoKangasPer);
//sl_contentPanel.putConstraint(SpringLayout.WEST, tfAiPlayers, 18, SpringLayout.EAST, lblNoKangasPer);
//sl_contentPanel.putConstraint(SpringLayout.WEST, lblNoKangasPer, 0, SpringLayout.WEST, lblBoardSiye);
//contentPanel.add(lblNoKangasPer);
//
//JLabel lblDepthLevel = new JLabel("Depth Level");
//sl_contentPanel.putConstraint(SpringLayout.NORTH, lblDepthLevel, 16, SpringLayout.SOUTH, lblNoKangasPer);
//sl_contentPanel.putConstraint(SpringLayout.WEST, lblDepthLevel, 0, SpringLayout.WEST, lblBoardSiye);
//sl_contentPanel.putConstraint(SpringLayout.EAST, lblDepthLevel, -50, SpringLayout.EAST, lblNoKangasPer);
//contentPanel.add(lblDepthLevel);
//
//tfDepth = new JTextField();
//tfDepth.setHorizontalAlignment(SwingConstants.RIGHT);
//tfDepth.setText("2");
//sl_contentPanel.putConstraint(SpringLayout.SOUTH, tfDepth, 0, SpringLayout.SOUTH, lblDepthLevel);
//sl_contentPanel.putConstraint(SpringLayout.EAST, tfDepth, 0, SpringLayout.EAST, tfHumanPlayers);
//contentPanel.add(tfDepth);
//tfDepth.setColumns(10);
//
//chckbxDebugMode = new JCheckBox("Debug Mode");
//chckbxDebugMode.setHorizontalAlignment(SwingConstants.LEFT);
//sl_contentPanel.putConstraint(SpringLayout.WEST, chckbxDebugMode, 0, SpringLayout.WEST, lblBoardSiye);
//sl_contentPanel.putConstraint(SpringLayout.SOUTH, chckbxDebugMode, 0, SpringLayout.SOUTH, contentPanel);
//contentPanel.add(chckbxDebugMode);


/////////////