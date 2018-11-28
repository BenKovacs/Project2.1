package model.player;

import gui.BoardPanel;
import model.OthelloBoard;
import model.data_model.Node;
import model.data_model.Tree;

import java.awt.*;
import java.util.*;

//Important question here: How does the algorithm continue after it reach the final leaf? So no more expansion possible.

public class MonteCarloTreeSearch implements Player {

    private Tree<HashMap<String,Object>> gameTree;
    private Node<HashMap<String,Object>> currentNode;

    private BoardPanel boardPanel;

    public MonteCarloTreeSearch(BoardPanel boardPanel) {
        this.boardPanel = boardPanel;
    }

    //Done, perfect!
    public Point getMove(OthelloBoard board, long runtime) {
        initialization(board);

        long endTime = System.currentTimeMillis() + runtime;
        while (System.currentTimeMillis() < endTime) {
            selection();
        }

        ArrayList<Node<HashMap<String,Object>>> validMoves = (ArrayList<Node<HashMap<String,Object>>>) gameTree.getRoot().getChildren();
        Node<HashMap<String,Object>> bestMove = null;
        double highestWinRate = -1.0;

        if (gameTree.getRoot().getChildren().isEmpty())
            System.out.println("gameTree has no children.");
        else
            for (Node<HashMap<String,Object>> validMove : validMoves) {
                System.out.println("Valid Move board: \n");
                getBoard(validMove).printBoard();
                System.out.println("Valid Move wins #: " + getWins(validMove) + "\n");
                System.out.println("Valid Move playouts # " + getPlayouts(validMove) + "\n");
            }

        for (Node<HashMap<String,Object>> move : validMoves) {
            double moveWinRate = getWins(move) / getPlayouts(move);
            if (moveWinRate > highestWinRate) {
                highestWinRate = moveWinRate;
                bestMove = move;
            }
        }
        if (bestMove == null) {
            System.out.println("Best Move is null");
            System.out.println("Possible Moves: " + getBoard(gameTree.getRoot()).getValidMoves());
            getBoard(gameTree.getRoot()).printBoard();
        } else {
            System.out.println("Best Move not null");
        }

        return getBoard(bestMove).getLastMove();
    }

    //Done perf!
    private void initialization(OthelloBoard board) {
        HashMap<String,Object> data = new HashMap<String, Object>();
        data.put("BOARD", board);
        data.put("WINS", 0.0);
        data.put("PLAYOUTS", 0);

        gameTree = new Tree<HashMap<String,Object>>(data);

        ArrayList<Point> validMoves = getBoard(gameTree.getRoot()).getValidMoves();
        for (Point move : validMoves) {
            OthelloBoard clonedBoard = (OthelloBoard) getBoard(gameTree.getRoot()).clone();
            int row = (int) move.getX();
            int column = (int) move.getY();
            clonedBoard.play(row, column);
            data = new HashMap<String,Object>();
            data.put("BOARD", clonedBoard);
            data.put("WINS", 0.0);
            data.put("PLAYOUTS", 0);
            Node<HashMap<String, Object>> child = new Node<HashMap<String, Object>>(data);
            gameTree.getRoot().addChild(child);
        }
    }

    //Done perf!
    private void selection(){
        System.out.println("selection");
        //What happen here is that the children of root all has 0 wins for some reason, so no child get choosen
        currentNode = gameTree.getRoot();
        whileLoop:
        while (!currentNode.getChildren().isEmpty()) {
            ArrayList<Node<HashMap<String,Object>>> children = (ArrayList<Node<HashMap<String,Object>>>) currentNode.getChildren();
            double bestUCT = 0;
            for (Node<HashMap<String,Object>> child : children) {
                if (getPlayouts(child) == 0) {
                    currentNode = child;
                    break whileLoop;
                } else {
                    if (uct(child) > bestUCT) {
                        bestUCT = uct(child);
                        currentNode = child;
                    }
                }
            }
        }
        //here it works just fine
//        if (getPlayouts(currentNode) == 0) {
//            simulation();
//        } else if (!getBoard(currentNode).getValidMoves().isEmpty()) {
//            expansion();
//        } else {
//            backpropagation();
//        }
        //this is where the no print message issue happen, something wrong with the loop
//        if (getPlayouts(currentNode) == 0 || getBoard(currentNode).getValidMoves().isEmpty()) {
//            simulation();
//        } else {
//            expansion();
//        }
//        this also works, now current node also get updated instead of 1/1 all the time
        if (getPlayouts(currentNode) == 0) {
            //So the code called simulation, finish till backpropagate, and get stuck after the next select call
            simulation();
        } else if (!getBoard(currentNode).getValidMoves().isEmpty()) {
            expansion();
        } else {
            updateResult(getBoard(currentNode));
        }
    }

    //DOne perf!
    private void expansion() {
        ArrayList<Point> validMoves = getBoard(currentNode).getValidMoves();
        for (Point move : validMoves) {
            OthelloBoard clonedBoard = (OthelloBoard) getBoard(currentNode).clone();
            int row = (int) move.getX();
            int column = (int) move.getY();
            clonedBoard.play(row, column);
            HashMap<String, Object> data = new HashMap<String, Object>();
            data.put("BOARD", clonedBoard);
            data.put("WINS", 0.0);
            data.put("PLAYOUTS", 0);
            Node<HashMap<String, Object>> child = new Node<HashMap<String, Object>>(data);
            currentNode.addChild(child);
        }
        currentNode = currentNode.getChildren().get(0);
        simulation();
    }

    //Done, Perf.
    private void simulation(){
        OthelloBoard clonedBoard = (OthelloBoard) getBoard(currentNode).clone();
        //just to test what is going on. Ok so there is valid moves
        Random random = new Random();
        while (!clonedBoard.getValidMoves().isEmpty()) {
            ArrayList<Point> validMoves = clonedBoard.getValidMoves();
            Point move = validMoves.get(random.nextInt(validMoves.size()));
            int row = (int) move.getX();
            int column = (int) move.getY();
            clonedBoard.play(row, column);
        }
        //Cloned manage to play till the end
        updateResult(clonedBoard);
        backpropagation();
    }

    //DONE! perhaps change var names.
    private void updateResult(OthelloBoard simulatedBoard) {
        OthelloBoard currentBoard = getBoard(currentNode);
        Point currentLastMove = currentBoard.getLastMove();
        int row = (int) currentLastMove.getX();
        int column = (int) currentLastMove.getY();
        int currentPlayer = currentBoard.getBoard()[row][column];


        simulatedBoard.printBoard();
        System.out.println("current player color: " + currentPlayer);
        System.out.println("current player pieces: " + simulatedBoard.countCellState(currentPlayer));
        System.out.println("current enemy pieces: " + simulatedBoard.countCellState(-currentPlayer));

        if (simulatedBoard.countCellState(currentPlayer) > simulatedBoard.countCellState(-currentPlayer)) {
            currentNode.getData().put("WINS", getWins(currentNode) + 1.0);
        } else if (simulatedBoard.countCellState(currentPlayer) == simulatedBoard.countCellState(-currentPlayer)) {
            currentNode.getData().put("WINS", getWins(currentNode) + 0.5);
        }

        //ok so he can win but chance is very small
        //but why does nullpointer error occurs when he win?
        //basically even if he wins, the data wont be propagate to the top, so wins # stay 0;

        currentNode.getData().put("PLAYOUTS", getPlayouts(currentNode) + 1);
    }

    //DONE! Perf!
    private void backpropagation(){
        double result;
        if (getWins(currentNode) == getPlayouts(currentNode)) {
            result = 1.0;
        } else if (getWins(currentNode) == 0.0) {
            result = 0.0;
        } else {
            result = 0.5;
        }
//        result = 0.5; //Need to fix this aswell, some time the node win # not get updated
        while (currentNode.getParent() != null) {
            if (!isSamePlayer(currentNode, currentNode.getParent())) {
                if (result == 1.0)
                    result = 0.0;
                else if (result == 0.0)
                    result = 1.0;
            }
            currentNode = currentNode.getParent();
            currentNode.getData().put("WINS", getWins(currentNode) + result);
            currentNode.getData().put("PLAYOUTS", getPlayouts(currentNode) + 1);
        }
    }

    //DONE
    private double uct(Node<HashMap<String,Object>> node) {
        int c = 2;
        double uct;
        if (getBoard(node.getParent()).getTurn() == getBoard(gameTree.getRoot()).getTurn()) {
            uct = getWins(node) / getPlayouts(node) + Math.sqrt(c * Math.log(getPlayouts(gameTree.getRoot())) / getPlayouts(node));
        } else {
            uct = (getPlayouts(node) - getWins(node)) / getPlayouts(node) + Math.sqrt(2 * Math.log(getPlayouts(gameTree.getRoot())) / getPlayouts(node));
        }
        return uct;
    }

    //Done
    private OthelloBoard getBoard(Node<HashMap<String,Object>> node) {
        if (node.getData() == null)
            System.out.println("getBoard null");
        return (OthelloBoard) node.getData().get("BOARD");
    }

    //Done
    private double getWins(Node<HashMap<String,Object>> node) {
        return (double) node.getData().get("WINS");
    }

    //Done
    private int getPlayouts(Node<HashMap<String,Object>> node) {
        return (int) node.getData().get("PLAYOUTS");
    }

    //Done
    //maybe change method name
    private boolean isSamePlayer(Node<HashMap<String,Object>> node1, Node<HashMap<String,Object>> node2) {
        OthelloBoard board1 = getBoard(node1);
        Point lastMove1 = board1.getLastMove();
        int player1;
        if (lastMove1 != null) {
            int row = (int) lastMove1.getX();
            int column = (int) lastMove1.getY();
            player1 = board1.getBoard()[row][column];
        } else {
            player1 = -getBoard(gameTree.getRoot()).getTurn();
        }

        OthelloBoard board2 = getBoard(node2);
        Point lastMove2 = board2.getLastMove();
        int player2;
        if (lastMove2 != null) {
            int row = (int) lastMove2.getX();
            int column = (int) lastMove2.getY();
            player2 = board2.getBoard()[row][column];
        } else {
            player2 = -getBoard(gameTree.getRoot()).getTurn();
        }

        if (player1 == player2) {
            return true;
        } else {
            return false;
        }
    }

    //Done perf!
    private int countChildren(Node<HashMap<String,Object>> node) {
        ArrayList<Node<HashMap<String,Object>>> children = (ArrayList<Node<HashMap<String,Object>>>) node.getChildren();
        int total = 0;
        for (Node<HashMap<String,Object>> child : children) {
            total++;
            total += countChildren(child);
        }
        return total;
    }

    public void printData() {
        getBoard(gameTree.getRoot()).printBoard();
        if (getBoard(gameTree.getRoot()).getTurn() == OthelloBoard.BLACK)
            System.out.println("CURRENT PLAYER: BLACK");
        else
            System.out.println("CURRENT PLAYER: WHITE");
        ArrayList<Node<HashMap<String,Object>>> validMoves = (ArrayList<Node<HashMap<String,Object>>>) gameTree.getRoot().getChildren();
        Node<HashMap<String,Object>> bestMove = null;
        double highestWinRate = 0;
        for (Node<HashMap<String,Object>> move : validMoves) {
            System.out.println("[Coord: " + getBoard(move).getLastMove().getX() + ", " + getBoard(move).getLastMove().getY() + "] "
                    + "[Wins/Playouts: " + move.getData().get("WINS") + "/" + move.getData().get("PLAYOUTS") + "] "
                    + "[Wins%: " + String.format("%.2f", (double) move.getData().get("WINS") / (int) move.getData().get("PLAYOUTS") * 100) + "%]");
            double moveWinRate = (double) move.getData().get("WINS") / (int) move.getData().get("PLAYOUTS");
            if (moveWinRate > highestWinRate) {
                highestWinRate = moveWinRate;
                bestMove = move;
            }
        }
        System.out.println("Best Move: " + "[Coord: " + getBoard(bestMove).getLastMove().getX() + ", " + getBoard(bestMove).getLastMove().getY() + "] "
                + "[Wins%: " + String.format("%.2f", (double) bestMove.getData().get("WINS") / (int) bestMove.getData().get("PLAYOUTS") * 100) + "%]");
        System.out.println("Total Iterations Completed: " + gameTree.getRoot().getData().get("PLAYOUTS"));
        System.out.println("Total Nodes: " + countChildren(gameTree.getRoot()) + 1);
        System.out.println("BLACK: " + getBoard(gameTree.getRoot()).countCellState(OthelloBoard.BLACK));
        System.out.println("WHITE: " + getBoard(gameTree.getRoot()).countCellState(OthelloBoard.WHITE));
        System.out.println("TREE DEPTH: " + gameTree.getRoot().getDepth());
        System.out.println();
    }

    public void play() {
        OthelloBoard board = new OthelloBoard(8,8);
        board.useGameBoard(boardPanel.getGameBoard());
        Point bestMove = getMove(board, 3000000);
        boardPanel.play((int)bestMove.getX(),(int)bestMove.getY());
    }

    public String getPlayerType() {
        return "bot";
    }

}