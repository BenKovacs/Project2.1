package model.data_model;

import javafx.geometry.Point3D;

import java.util.ArrayList;

/**
 * Class that compute the tree of possibilities from the current board status
 */
public class BoardTree {

    private Node<Point3D> rootT;
    private boolean debug = false;
    private int depth;

    public BoardTree(GameBoard gameBoard, int turn, int depth ){
        // create a temporary GameBoard

        AIBoard tmpBoard = new AIBoard(gameBoard.getboard(), turn, gameBoard.getPlayerList());

        this.depth = depth;

        // create a tree with a certain depth
        rootT = new Node<>(new Point3D(999, 999, 99));

        ArrayList<Point3D> validMoves = tmpBoard.getValidMoves();

        // Leave this part commented for testing purposes

        /*for(Point3D p: validMoves)System.out.println(p.toString());

        tmpBoard.printBoard();

		tmpBoard.flipDisc((int)validMoves.get(0).getX(), (int)validMoves.get(0).getY());
		System.out.println("move for " + tmpBoard.getTurn() + " " +validMoves.get(0).toString());
		tmpBoard.printBoard();
        System.out.println();

		validMoves.clear();
		validMoves = tmpBoard.getValidMoves();
		for(Point3D p: validMoves)System.out.println(p.toString());

		tmpBoard.flipDisc((int)validMoves.get(0).getX(), (int)validMoves.get(0).getY());
        System.out.println("move for " + tmpBoard.getTurn() + " " +validMoves.get(0).toString());
		tmpBoard.printBoard();
        System.out.println("endddd");*/


        //for each valid moves create a branch with a recursive algorithm
        //testing Threads!!!!
        ArrayList<Thread> threads = new ArrayList<>();

        for(Point3D p: validMoves){
            if(debug)System.out.println(p.toString());

            Node<Point3D> child = new Node<>(new Point3D(p.getX(), p.getY(), p.getZ()));
            child.setDepth(rootT.getDepth()+1);


            Thread t = new Thread(() -> rootT.addChild(buildTree(child, depth, new AIBoard(tmpBoard.getBoard(), tmpBoard.getTurn(), tmpBoard.getPlayerList()))));

            t.start();

            threads.add(t);
        }

        //check whether the thread are finished
        boolean going = true;
        while(going){

            int a = 0;
            for(Thread t: threads) if(t.isAlive())a++;

            if(a==0)going = false;
        }

        //at the end build the tree
        if(debug)printTree(rootT, " ");

    }

    private Node<Point3D> buildTree(Node<Point3D> root, int depth, AIBoard gmB) {
        if (root.getDepth() == depth) return root;

        gmB.flipDisc((int) root.getData().getX(),  (int) root.getData().getY());

        if(debug)gmB.printBoard();

        // check how many possibilities with this node
        ArrayList<Point3D> validMoves = gmB.getValidMoves();

        if(debug)System.out.println("Found valid moves for depth " + root.getData().toString());

        for (Point3D p : validMoves) {
            if(debug)System.out.println("Valid Move: " + p.toString());

            Node<Point3D> n = new Node<>(new Point3D(p.getX(), p.getY(), p.getZ()));
            n.setDepth(root.getDepth()+1);

            root.addChild(buildTree(n, depth, new AIBoard(gmB.getBoard(), gmB.getTurn(), gmB.getPlayerList())));
            //root.addChild(buildTree(n, depth, gmB));
        }

        return root;
    }

    public Node<Point3D> getRootT() {
        return rootT;
    }

    public int getDepth() {
        return depth;
    }

    // method to print the tree
    private static <T> void printTree(Node<T> node, String appender) {
        System.out.println();
        System.out.println(appender + node.getData());
        node.getChildren().forEach(each -> printTree(each, appender + appender));
    }
}