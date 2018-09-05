package oth.gui;


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import oth.model.GameBoard;



public class BoardPanel extends JPanel {

	private GameBoard gameBoard;
	
	public GameBoard getGameBoard() {
		return gameBoard;
	}

	public void setGameBoard(GameBoard gb) {
		this.gameBoard = gb;
		System.out.println("panel adjusted to new gb");
	}

	private SquarePanel[][] sp;

	/**
	 * Create the panel.
	 * @param gb 
	 * @param j 
	 * @param i 
	 */
	
	
	public BoardPanel(GameBoard gb) {
		this.gameBoard = gb;
		setLayout(new GridLayout(gb.getHeight(), gb.getWidth(), 0, 0));

		sp = new SquarePanel[gb.getWidth()][gb.getHeight()];
		
		for (int y=0; y<gb.getHeight(); y++){
			for (int x=0; x<gb.getWidth(); x++){
				SquarePanel spp = new SquarePanel(x, y);
				sp[x][y] = spp;
				add(spp);
			}
		}

	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}
	
	private class SquarePanel extends JPanel{

		private JLabel label;
		int x;
		int y;
		public SquarePanel(int x, int y) {
			this.x = x;
			this.y = y;
			setLayout(new BorderLayout());
			label = new JLabel();
//			label.setText(x+", "+y);
			add(label, BorderLayout.CENTER);
			setBorder(new LineBorder(Color.lightGray));
			}
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);
				double w = gameBoard.getBoardSquare(x, y).getWeight();
				float fw = (float) ((360-w)/360);
				Color c = new Color(0, fw, 0);
				setBackground(c);
//			if (gameBoard.getBoardSquare(x, y).isOccupied()){
//				System.out.println("panel coords occupied are x " + x + " and y " + y);
//				Color col = gameBoard.getBoardSquare(x, y).getOccupant().getTeam().getColor();
//				g.setColor(col);
//				int width = this.getWidth()*3/4;
//				int height = this.getHeight()*3/4;
//				int dim = Math.min(width, height);
//				g.fillOval(getWidth()/2-dim/2, getHeight()/2-dim/2, dim, dim);
//			}
			if (y>7 && x ==7){
				g.setColor(Color.blue);
				((Graphics2D)g).setStroke(new BasicStroke(20));
				g.drawLine(getWidth(), 0, getWidth(), getHeight());
			}
			if (y>7 && x ==8){
				g.setColor(Color.blue);
				((Graphics2D)g).setStroke(new BasicStroke(20));
				g.drawLine(0, 0, 0, getHeight());
			}
		}
	}

	public void showBoard(GameBoard board) {
		this.gameBoard = board;
		repaint();
	}

}
