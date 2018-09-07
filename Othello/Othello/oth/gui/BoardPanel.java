package oth.gui;


import oth.model.GameBoard;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BoardPanel extends JPanel {

	private GameBoard gameBoard;

	private SquarePanel[][] sp;

	/**
	 * Create the panel.
	 * @param gameB
	 */
	public BoardPanel(GameBoard gameB) {
		this.gameBoard = gameB;
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
		repaint();
	}

	public void setGameBoard(GameBoard gb) {
		this.gameBoard = gb;

		this.removeAll();

		for (int y=0; y<10; y++) {
			for (int x = 0; x < 10; x++) {
				//create a new squarePanel panel and add to the array and the gridLayout
				sp[x][y] = new SquarePanel(x,y, gameBoard.getSquareType(x,y));
				add(sp[x][y]);
			}
		}

		showBoard();
	}

	//the square composing the grid panels
	private class SquarePanel extends JPanel implements MouseListener {

		public final int EMPTY = -1;
		public final int BLACK = 0;
		public final int WHITE = 1;

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
				g2.fillOval(3,3,39, 39 );

			}else if (type == BLACK){
				//draw a white circle
				c = new Color(0, 0, 0);
				g2.setColor(c);
				g2.fillOval(3,3,39, 39 );

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
			gameBoard.changeSquareType(this.x,this.y);
			setGameBoard(gameBoard);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}
}
