package gui;


import model.GameBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainApp implements ActionListener {
	
	private static MainApp mainApp;
	
	public static MainApp getSingleton() {
		return mainApp;
	}

	private JFrame frame;
	private GameBoard gameBoard;
	
	public JFrame getFrame() {
		return frame;
	}

	//--PANELS
	//right RightPanel
	private RightPanel rPanel;
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
					mainApp = window;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	private MainApp(){

		frame = new JFrame();
		frame.setBounds(100, 100, 927, 473);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initialize();
	}
	
	public void reset() {
		frame = new JFrame();
		frame.setBounds(100, 100, 927, 473);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents.
	 */
	public void initialize() {
		//set the layout
		frame.getContentPane().setLayout(new GridLayout(1,2));

		rPanel = new RightPanel();

		//create the gameBoard/panel
		gameBoard = new GameBoard(8,8);
		boardPanel = new BoardPanel(gameBoard, rPanel);
		frame.getContentPane().add(boardPanel, 1,0);

		rPanel.setPreferredSize(new Dimension(10, 30));
		frame.getContentPane().add(rPanel, 0,1);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	}
	
	

}

