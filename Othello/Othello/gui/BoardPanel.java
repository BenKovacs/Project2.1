package gui;


import model.GameBoard;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static model.Constants.*;

public class BoardPanel extends JPanel {

	private GameBoard gameBoard;

	//copy of the right panel
	private RightPanel rightPanel;

	private SquarePanel[][] sp;

	/**
	 * Create the panel.
	 * @param gameB
	 */
	public BoardPanel(GameBoard gameB, RightPanel rPanel) {
		this.gameBoard = gameB;
		this.rightPanel = rPanel;
		int h = gameBoard.getHeight(); int w = gameBoard.getWidth();
		setLayout(new GridLayout(w, h, 0, 0));

		sp = new SquarePanel[w][h];
		
		for (int y=0; y<h; y++){
			for (int x=0; x<w; x++){
				//create a new squarePanel panel and add to the array and the gridLayout
				sp[x][y] = new SquarePanel(x,y, gameBoard.getSquareType(x,y));
				add(sp[x][y]);
			}
		}
	}
	
	@Override
	public void paint(Graphics g) { super.paint(g); }

	public void showBoard() {
		revalidate();
		//repaint();
	}

	public void setGameBoard(GameBoard gb) {
		this.gameBoard = gb;

		this.removeAll();

		for (int y=0; y<gameBoard.getHeight(); y++) {
			for (int x = 0; x < gameBoard.getWidth(); x++) {
				//create a new squarePanel panel and add to the array and the gridLayout
				sp[x][y] = new SquarePanel(x,y, gameBoard.getSquareType(x,y));
				add(sp[x][y]);
			}
		}

		showBoard();
	}

	//the square composing the grid panels
	private class SquarePanel extends JPanel implements MouseListener {

		private int type;
		private int x;
		private int y;

		private int w,h;

		public SquarePanel(int x, int y, int type) {
			this.type = type;
			this.x = x;
			this.y = y;
			setLayout(new BorderLayout());
			setBorder(new LineBorder(Color.lightGray));

			addMouseListener(this);

			w = this.getWidth();
			h = this.getHeight();
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);

			//set background to gray
			Color c;

			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			if(type == WHITE){
				//draw a black circle
				c = new Color(255, 255, 255);
				g2.setColor(c);
				g2.fillOval(5,5,45, 45 );

			}else if (type == BLACK){
				//draw a white circle
				c = new Color(0, 0, 0);
				g2.setColor(c);
				g2.fillOval(5,5,45, 45 );

			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			//if the player can flip the disc
//			if(gameBoard.flipDisc(this.x, this.y)){
//				//update the gameBoard
//				setGameBoard(gameBoard);
//				//change the turn
//				//change the right panel label
//				rightPanel.changeTurn();
//			}
			gameBoard.flipDisc(this.x, this.y);
			setGameBoard(gameBoard);
			rightPanel.changeTurn();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}
}
