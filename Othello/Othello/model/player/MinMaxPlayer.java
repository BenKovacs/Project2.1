package model.player;

import gui.BoardPanel;
import gui.MainApp;
import gui.TestApp;
import gui.TestAppV2;
import javafx.geometry.Point3D;
import model.data_model.BoardTree;
import model.data_model.Node;


/**
 * This class need to be reviewed but the tree building works
 */
public class MinMaxPlayer extends Thread implements Player {
	private BoardPanel boardPanel;

	private BoardTree bTree;
	private int depth;

	private int color;

	private int nPlayers;
	
	public MinMaxPlayer(BoardPanel boardPanel, int color, int depth) {
		this.boardPanel = boardPanel;
		this.color = color;
		this.depth = depth;
		setName("MinMax/AlphaBeta Bot");
	}

	public void play() {

		nPlayers = boardPanel.getGameBoard().getPlayerList().length;

		//construct the tree
		bTree = new BoardTree(boardPanel.getGameBoard(), boardPanel.getGameBoard().getTurn(), depth);

		//get the minimax move
		Node<Point3D> bestmove = minimax(bTree.getRootT(), bTree.getDepth(), true, nPlayers);
		//Node<Point3D> bestmove = alphaBeta(bTree.getRootT(), bTree.getDepth(),-999, 999, true);

		//set true to Maximize the result for the first player
		//System.out.println(bestmove.getData().toString());

		//select the move and play
		try{
			boardPanel.play((int)bestmove.getData().getX(), (int)bestmove.getData().getY());
		}catch (ArrayIndexOutOfBoundsException e){ } //To avoid the final OUTOFBOUNDS
	}



	/**
	 *MINIMAX ALGORITHM
	 */
	private Node<Point3D> minimax(Node<Point3D> node, int depth, boolean maxPlayer, int toMinimize){
		//end condition
		if(depth == 0) return node;

		if(node.getChildren().size()==0) return node;

		//act for the MAX
		if(maxPlayer){

			Node<Point3D> maxEval = new Node(new Point3D(0,0,-999));
			for(Node<Point3D> n: node.getChildren()){
				double eval = minimax(n, depth-1, false, toMinimize).getData().getZ();

				if(maxEval.getData().getZ()< eval)
					maxEval = new Node(new Point3D( n.getData().getX(), n.getData().getY(), eval));
			}
			return maxEval;

		}else{ // act for the MIN
			Node<Point3D>  minEval = new Node(new Point3D(0,0,999));
			for(Node<Point3D> n: node.getChildren()){

				double eval;


				if(toMinimize >=2)
					eval = minimax(n, depth-1, false, toMinimize-1).getData().getZ();
				else
					eval = minimax(n, depth-1, true, nPlayers).getData().getZ();


				if(minEval.getData().getZ()> eval)
					minEval = new Node(new Point3D(n.getData().getX(),n.getData().getY(), eval));
			}
			return minEval;
		}
	}


	/**
	 *AlphaBeta ALGORITHM not FIXED
	 */
	/*private Node<Point3D> alphaBeta(Node<Point3D> node, int depth, int alpha, int beta, boolean maxPlayer){
		//condition of end
		if(depth == 0) return node;

		//act for the MAX
		if(maxPlayer){
			Node<Point3D> maxEval = new Node(new Point3D(0,0,-999));
			for(Node<Point3D> n: node.getChildren()){
				double eval = minimax(n, depth-1, false).getData().getZ();
				maxEval = new Node(new Point3D( n.getData().getX(),n.getData().getY(), Math.max(maxEval.getData().getZ(), eval)));

				//alphabeta implementation
				alpha = Math.max(alpha, (int)eval);
				if(beta <= alpha)break;
			}
			return maxEval;

		}else{ // act for the MIN
			Node<Point3D>  minEval = new Node(new Point3D(0,0,999));
			for(Node<Point3D> n: node.getChildren()){
				double eval =minimax(n, depth-1, true).getData().getZ();
				minEval = new Node(new Point3D(n.getData().getX(),n.getData().getY(), Math.min(minEval.getData().getZ(), eval)));

				//alphabeta implementation
				beta = Math.min(beta, (int)eval);
				if(beta <= alpha)break;
			}
			return minEval;
		}
	}*/

	public void run() {
		while(true) {
			try {
				sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(MainApp.getSingleton() == null && TestAppV2.getSingleton() == null)
				continue;

			if(boardPanel.getGameBoard() == null)
				continue;

			if(getColor() == boardPanel.getGameBoard().getTurn()) {
				this.play();
			}
			if (boardPanel.getGameBoard().isGameFinished()){
				break;
			}
		}

	}

	public int getPlayerType() {
		return Player.TYPE_BOT;
	}
	
	public int getColor() {
		return color;
	}
}