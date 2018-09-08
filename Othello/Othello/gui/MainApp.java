package gui;


import model.GameBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainApp implements ActionListener {

	private JFrame frame;
	private GameBoard gameBoard;

	//--PANELS
	//right RightPanel
	private SettingsPanel rPanel;
	//left LeftPanel
	private BoardPanel boardPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainApp window = new MainApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainApp(){

		frame = new JFrame();
		frame.setBounds(100, 100, 927, 473);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initialize();
	}

	/**
	 * Initialize the contents.
	 */
	private void initialize() {
		//set the layout
		frame.getContentPane().setLayout(new GridLayout(1,2));

		//create the gameBoard/panel
		gameBoard = new GameBoard(10,10);
		boardPanel = new BoardPanel(gameBoard);
		frame.getContentPane().add(boardPanel, 1,0);

		rPanel = new SettingsPanel();
		rPanel.setPreferredSize(new Dimension(10, 30));
		frame.getContentPane().add(rPanel, 0,1);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	}

}

