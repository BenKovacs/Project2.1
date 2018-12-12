package gui;

import model.GameBoard;
import model.data_model.AIBoard;
import model.player.Player;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static model.Constants.*;

public class BoardPanel extends JPanel {

	private static GameBoard gameBoard;

	// copy of the right panel
	private RightPanel rightPanel;

	private SquarePanel[][] sp;

	/**
	 * Create the panel.
	 * 
	 * @param gameB
	 */
	public BoardPanel(GameBoard gameB, RightPanel rPanel) {
		this.gameBoard = gameB;
		this.rightPanel = rPanel;
		int h = gameBoard.getHeight();
		int w = gameBoard.getWidth();
		setLayout(new GridLayout(w, h, 0, 0));

		sp = new SquarePanel[w][h];

		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				// create a new squarePanel panel and add to the array and the gridLayout
				sp[x][y] = new SquarePanel(x, y, gameBoard.getSquareType(x, y));
				add(sp[x][y]);
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}

	public void showBoard() {
		revalidate();
	}

	public void setGameBoard(GameBoard gb) {
		this.gameBoard = gb;

		this.removeAll();
		for (int x = 0; x < gameBoard.getWidth(); x++) {
			for (int y = 0; y < gameBoard.getHeight(); y++) {
				// create a new squarePanel panel and add to the array and the gridLayout
				sp[x][y] = new SquarePanel(x, y, gameBoard.getSquareType(x, y));
				add(sp[x][y]);
			}
		}

		showBoard();
	}

	public void play(int x, int y){
		if (gameBoard.flipDisc(x, y)) {
			setGameBoard(gameBoard);
			rightPanel.changeTurn();

			String message;
			if (gameBoard.isGameFinished()) {
				if (gameBoard.getCountWhite() - gameBoard.getCountBlack() > 0){
					message = "White wins!";
				} else if (gameBoard.getCountWhite() - gameBoard.getCountBlack() < 0){
					message = "Black wins!";
				} else {
					message = "It's a Tie!";
				}
				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog(this, message + " Would you like to play again?", "Game Ended", dialogButton);
				if(dialogResult == 0) {
					MainApp.getSingleton().reset();
				} else {
					System.exit(-1);
				}
			}
		}
		//System.out.println("About to check if stable");
		for(int i= 0;  i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				AIBoard.isStable(i, j, gameBoard.getboard());
			}
		}
		
		
	}

	// the square composing the grid panels
	private class SquarePanel extends JPanel implements MouseListener {

		private int type;
		private int x;
		private int y;

		private int w, h;

		public SquarePanel(int x, int y, int type) {
			this.type = type;
			this.x = x;
			this.y = y;

			setLayout(new BorderLayout());
			setBorder(new LineBorder(Color.lightGray));

			// set background color to dark green
			Color backgroundColor = new Color(0, 120, 0);
			setBackground(backgroundColor);

			addMouseListener(this);
		}

		@Override
		public void paint(Graphics g) {
			w = this.getWidth();
			h = this.getHeight();

			super.paint(g);

			// initiate tile color
			Color c;

			Graphics2D g2 = (Graphics2D) g;

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			if (type == WHITE) {
				// draw a white circle
				c = new Color(255, 255, 255);
				g2.setColor(c);
				g2.fillOval(5, 5, w - 10, h - 10);
			} else if (type == BLACK) {
				// draw a black circle
				c = new Color(0, 0, 0);
				g2.setColor(c);
				g2.fillOval(5, 5, w - 10, h - 10);
			} else if (type < 0) {
				String flips = Integer.toString(-type);
				// change background color to light green and draw number

				c = new Color(0, 0, 0);
				g2.setColor(c);
				g2.setStroke(new BasicStroke(3.5f));
				g2.drawOval(5,5,w-10, h-10);

				g2.setFont(new Font("Arial", Font.BOLD, h / 2));
				g2.drawString(flips, w / 2 - g2.getFontMetrics().stringWidth(flips) / 2,
						h / 2 + g2.getFontMetrics().getHeight() / 4);

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
			if (gameBoard.getPlayer().getPlayerType() == Player.TYPE_HUMAN) {
				play(this.x, this.y);
			}
			System.out.println("Pressed on: " + x + "," + y);
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			this.setBackground(Color.CYAN);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			Color backgroundColor = new Color(0, 120, 0);
			setBackground(backgroundColor);
		}
	}

	public GameBoard getGameBoard(){
		return gameBoard;
	}
}
