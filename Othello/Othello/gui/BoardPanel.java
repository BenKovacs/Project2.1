package gui;

import model.data_model.GameBoard;
import model.player.Player;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static model.data_model.Constants.*;
import static model.data_model.GameBoard.result;
import static model.data_model.GameBoard.result2;

public class BoardPanel extends JPanel {

	protected static GameBoard gameBoard;

	// copy of the right panel
	protected RightPanel rightPanel;

	private SquarePanel[][] sp;
	private static boolean gameInProgress = true;

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

	public void play(int x, int y) {
		//System.out.println(" " + x + "," + y);
		if (gameBoard.flipDisc(x, y)) {
			setGameBoard(gameBoard);
			rightPanel.changeTurn(gameBoard);
			;
			String message = "";
			if (gameBoard.isGameFinished()) {
				this.gameInProgress = false;
				if(result.equals(result2)) {
					message = "It is a tie, the winners are " + result + " and " + result2;
				} else {
					message = "The winner is the " + result + " player. " ;
				}
				try {
					BufferedWriter out = new BufferedWriter(new FileWriter("./results.txt", true));
					out.write(this.getGameBoard().getPlayerList()[0] + " vs " + this.getGameBoard().getPlayerList()[1]);
					out.write("result: " + this.getGameBoard().getCount1() + " " + this.getGameBoard().getCount2());
				
					out.newLine();
					out.write("==============================");
					out.newLine();
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.exit(-1);
				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog(this, message + " Would you like to play again?", "Game Ended", dialogButton);
				if (dialogResult == 0) {
					MainApp.getSingleton().reset();
				} else {
					System.exit(-1);
				}
			}
		}
	}


	// the square composing the grid panels
	private class SquarePanel extends JPanel implements MouseListener {

		private int type;
		private int x;
		private int y;

		private int w, h;
		private Color backGroundcolor;

		private SquarePanel(int x, int y, int type) {
			this.type = type;
			this.x = x;
			this.y = y;

			setLayout(new BorderLayout());
			setBorder(new LineBorder(Color.lightGray));

			// set background color to dark green
			backGroundcolor = new Color(0, 80, 0);
			setBackground(backGroundcolor);

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
            g2.setStroke(new BasicStroke(4f));
            Color black = new Color(0, 0, 0);
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
			} else if (type == RED) {
				// draw a black circle
				c = new Color(255, 0, 0);
				g2.setColor(c);
				g2.fillOval(5, 5, w - 10, h - 10);
                g2.setColor(black);
                g2.drawOval(5,5,w-10, h-10);
			} else if (type == GREEN) {
				// draw a black circle
				c = new Color(0, 255, 0);
				g2.setColor(c);
				g2.fillOval(5, 5, w - 10, h - 10);
                g2.setColor(black);
                g2.drawOval(5,5,w-10, h-10);
			} else if (type == BLUE) {
				// draw a black circle
				c = new Color(0, 0, 255);
				g2.setColor(c);
				g2.fillOval(5, 5, w - 10, h - 10);
                g2.setColor(black);
                g2.drawOval(5,5,w-10, h-10);
			} else if (type == YELLOW) {
				// draw a black circle
				c = new Color(255, 255, 0);
				g2.setColor(c);
				g2.fillOval(5, 5, w - 10, h - 10);
                g2.setColor(black);
                g2.drawOval(5,5,w-10, h-10);
			}  else if (type < 0) {
				String flips;
				if (type == -999){
					flips = Integer.toString(0);
				}else{
					flips = Integer.toString(-type);
				}
				// change background color to light green and draw number

				g2.setColor(black);
				g2.drawOval(5,5,w-10, h-10);

				g2.setFont(new Font("Arial", Font.BOLD, h / 2));
				g2.drawString(flips, w / 2 - g2.getFontMetrics().stringWidth(flips) / 2, h / 2 + g2.getFontMetrics().getHeight() / 4);

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
			setBackground(backGroundcolor);
		}
	}

	public GameBoard getGameBoard(){
		return gameBoard;
	}
	}