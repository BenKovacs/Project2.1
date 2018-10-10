package model.player;

import gui.BoardPanel;
import javafx.geometry.Point3D;
import model.GameBoard;
import model.data_model.Node;

import java.util.ArrayList;
import java.util.Random;

public class AIPlayer implements Player{
    private BoardPanel boardPanel;
    private Random random = new Random();

    private Node<Point3D> rootT;

    public AIPlayer(BoardPanel boardPanel){
       this.boardPanel = boardPanel;

    }
    public void play(){
        /*ArrayList<Point3D> validMoves = boardPanel.getGameBoard().getValidMoves();
        if(validMoves.size() > 0){
            int randInt = random.nextInt(validMoves.size());
            //try {Thread.sleep(300);}catch(Exception e){}
            boardPanel.play((int)validMoves.get(randInt).getX() , (int)validMoves.get(randInt).getY());
            //boardPanel.getGameBoard().flipDisc((int)validMoves.get(randInt).getX() , (int)validMoves.get(randInt).getY());
        }*/

        minMax(2, boardPanel.getGameBoard().getboard());
    }

    public void minMax(int depth, int[][] currBoard){

        //create a temporary GameBoard
        GameBoard tmpGB = new GameBoard(8,8);
        //set the board and player's turn
        tmpGB.setBoard(currBoard);
        if(tmpGB.getTurn() != boardPanel.getGameBoard().getTurn())
            tmpGB.changeTurn();

        //create a tree with a certain depth
        rootT = new Node<>(new Point3D(999,999,99));
        rootT.addChild(buildTree(rootT,1, tmpGB));

        //recursive algorithm

        printTree(rootT, " ");
    }


    private Node<Point3D> buildTree(Node<Point3D> root, int depth, GameBoard gmB){
        if(root.getDepth()==depth)return root;

        //if root decline, otherwise flip the disc to predict the next moves
        if(root.getData().getZ()!= (double)99){
            /**
             * TODO Here is an issue that needs solving
             */
            gmB.flipDisc((int)root.getData().getX(),(int)root.getData().getY());
        }

        //check how many possibilities with this node
        ArrayList<Point3D> validMoves = gmB.getValidMoves();
        for (Point3D p: validMoves){
            System.out.println(p.toString());
            root.addChild(buildTree(new Node<>(p), depth, gmB));
        }

        return null;
    }

    public String getPlayerType(){
        return "bot";
    }

    //method to print the tree
    private static <T> void printTree(Node<T> node, String appender) {
        System.out.println(appender + node.getData());
        node.getChildren().forEach(each ->  printTree(each, appender + appender));
    }
}
