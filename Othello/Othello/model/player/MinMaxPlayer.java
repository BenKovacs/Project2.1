package model.player;

import gui.BoardPanel;
import javafx.geometry.Point3D;
import model.data_model.BoardTree;
import model.data_model.Node;


/**
 * This class need to be reviewed but the tree building works
 */
public class MinMaxPlayer implements Player {
	private BoardPanel boardPanel;

	private BoardTree bTree;

	public MinMaxPlayer(BoardPanel boardPanel) {
		this.boardPanel = boardPanel;
	}

	public void play() {

		//construct the tree
		bTree = new BoardTree(boardPanel.getGameBoard().getboard(), boardPanel.getGameBoard().getTurn(), 3);

		//get the minimax move
		//Node<Point3D> bestmove = minimax(bTree.getRootT(), bTree.getDepth(), false);
		Node<Point3D> bestmove = alphaBeta(bTree.getRootT(), bTree.getDepth(),-999, 999, false);
		System.out.println(bestmove.getData().toString());

		//select the move and play
		boardPanel.play((int)bestmove.getData().getX(), (int)bestmove.getData().getY());

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 *MINIMAX ALGORITHM STill something to test !!!!
	 */
	private Node<Point3D> minimax(Node<Point3D> node, int depth, boolean maxPlayer){
		//condition of end
		if(depth == 0) return node;

		//act for the MAX
		if(maxPlayer){
			Node<Point3D> maxEval = new Node(new Point3D(0,0,-999));
			for(Node<Point3D> n: node.getChildren()){
				double eval = minimax(n, depth-1, false).getData().getZ();
				maxEval = new Node(new Point3D( n.getData().getX(),n.getData().getY(), Math.max(maxEval.getData().getZ(), eval)));
			}
			return maxEval;

		}else{ // act for the MIN
			Node<Point3D>  minEval = new Node(new Point3D(0,0,999));
			for(Node<Point3D> n: node.getChildren()){
				double eval =minimax(n, depth-1, true).getData().getZ();
				minEval = new Node(new Point3D(n.getData().getX(),n.getData().getY(), Math.min(minEval.getData().getZ(), eval)));
			}
			return minEval;
		}
	}


	/**
	 *AlphaBeta ALGORITHM STill something to test !!!!
	 */
	private Node<Point3D> alphaBeta(Node<Point3D> node, int depth, int alpha, int beta, boolean maxPlayer){
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
	}

	public int getPlayerType() {
		return Player.TYPE_BOT;
	}
}
