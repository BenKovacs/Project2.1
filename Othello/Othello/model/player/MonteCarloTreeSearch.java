package model.player;

import gui.BoardPanel;
import model.OthelloBoard;
import model.data_model.Node;

import java.awt.*;
import java.util.*;

public class MonteCarloTreeSearch implements Player {

    private Node<HashMap<String,Object>> rootNode;
    private Node<HashMap<String,Object>> currentNode;

    private BoardPanel boardPanel;

    public MonteCarloTreeSearch(BoardPanel boardPanel) {
        this.boardPanel = boardPanel;
    }

    public Point getMove(OthelloBoard board, long runtime) {
        initialization(board);

        long endTime = System.currentTimeMillis() + runtime;
        while (System.currentTimeMillis() < endTime) {
            selection();
        }

        ArrayList<Node<HashMap<String,Object>>> validMoves = (ArrayList<Node<HashMap<String,Object>>>) rootNode.getChildren();
        if (!validMoves.isEmpty()) {
            Node<HashMap<String, Object>> bestMove = validMoves.get(0);
            for (Node<HashMap<String, Object>> move : validMoves) {
                if (winRate(move) > winRate(bestMove)) {
                    bestMove = move;
                }
            }
            return getBoard(bestMove).getLastMove();
        } else {
            return null;
        }
    }

    private void initialization(OthelloBoard board) {
        HashMap<String,Object> data = new HashMap<String, Object>();
        data.put("BOARD", board);
        data.put("WINS", 0.0);
        data.put("PLAYOUTS", 0);
        rootNode = new Node<HashMap<String,Object>>(data);

        ArrayList<Point> validMoves = getBoard(rootNode).getValidMoves();
        for (Point move : validMoves) {
            OthelloBoard clonedBoard = (OthelloBoard) getBoard(rootNode).clone();
            int row = (int) move.getX();
            int column = (int) move.getY();
            clonedBoard.play(row, column);
            data = new HashMap<String,Object>();
            data.put("BOARD", clonedBoard);
            data.put("WINS", 0.0);
            data.put("PLAYOUTS", 0);
            Node<HashMap<String, Object>> child = new Node<HashMap<String, Object>>(data);
            rootNode.addChild(child);
        }
    }

    private void selection(){
        currentNode = rootNode;
        whileLoop:
        while (!currentNode.getChildren().isEmpty()) {
            ArrayList<Node<HashMap<String,Object>>> children = (ArrayList<Node<HashMap<String,Object>>>) currentNode.getChildren();
            currentNode = children.get(0);
            for (Node<HashMap<String,Object>> child : children) {
                if (getPlayouts(child) == 0) {
                    currentNode = child;
                    break whileLoop;
                } else {
                    if (uct(child) > uct(currentNode)) {
                        currentNode = child;
                    }
                }
            }
        }

        if (getPlayouts(currentNode) == 0) {
            simulation();
        } else if (!getBoard(currentNode).getValidMoves().isEmpty()) {
            expansion();
        } else {
            updateResult(getBoard(currentNode));
        }
    }

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

    private void simulation(){
        OthelloBoard clonedBoard = (OthelloBoard) getBoard(currentNode).clone();
        Random random = new Random();
        while (!clonedBoard.getValidMoves().isEmpty()) {
            ArrayList<Point> validMoves = clonedBoard.getValidMoves();
            Point move = validMoves.get(random.nextInt(validMoves.size()));
            int row = (int) move.getX();
            int column = (int) move.getY();
            clonedBoard.play(row, column);
        }
        updateResult(clonedBoard);
        backpropagation();
    }

    private void updateResult(OthelloBoard simulatedBoard) {
        OthelloBoard currentBoard = getBoard(currentNode);
        Point currentLastMove = currentBoard.getLastMove();
        int row = (int) currentLastMove.getX();
        int column = (int) currentLastMove.getY();
        int currentPlayer = currentBoard.getBoard()[row][column];

        if (simulatedBoard.countCellState(currentPlayer) > simulatedBoard.countCellState(-currentPlayer)) {
            currentNode.getData().put("WINS", getWins(currentNode) + 1.0);
        } else if (simulatedBoard.countCellState(currentPlayer) == simulatedBoard.countCellState(-currentPlayer)) {
            currentNode.getData().put("WINS", getWins(currentNode) + 0.5);
        }
        currentNode.getData().put("PLAYOUTS", getPlayouts(currentNode) + 1);
    }

    private void backpropagation(){
        double result;
        if (getWins(currentNode) == getPlayouts(currentNode)) {
            result = 1.0;
        } else if (getWins(currentNode) == 0.0) {
            result = 0.0;
        } else {
            result = 0.5;
        }

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

    private double uct(Node<HashMap<String,Object>> node) {
        int c = 2;
        double uct;
        if (getBoard(node.getParent()).getTurn() == getBoard(rootNode).getTurn()) {
            uct = getWins(node) / getPlayouts(node) + Math.sqrt(c * Math.log(getPlayouts(rootNode)) / getPlayouts(node));
        } else {
            uct = (getPlayouts(node) - getWins(node)) / getPlayouts(node) + Math.sqrt(c * Math.log(getPlayouts(rootNode)) / getPlayouts(node));
        }
        return uct;
    }

    private double winRate(Node<HashMap<String, Object>> node) {
        return getWins(node) / getPlayouts(node);
    }

    private OthelloBoard getBoard(Node<HashMap<String,Object>> node) {
        return (OthelloBoard) node.getData().get("BOARD");
    }

    private double getWins(Node<HashMap<String,Object>> node) {
        return (double) node.getData().get("WINS");
    }

    private int getPlayouts(Node<HashMap<String,Object>> node) {
        return (int) node.getData().get("PLAYOUTS");
    }

    private boolean isSamePlayer(Node<HashMap<String,Object>> node1, Node<HashMap<String,Object>> node2) {
        OthelloBoard board1 = getBoard(node1);
        Point lastMove1 = board1.getLastMove();
        int player1;
        if (lastMove1 != null) {
            int row = (int) lastMove1.getX();
            int column = (int) lastMove1.getY();
            player1 = board1.getBoard()[row][column];
        } else {
            player1 = -getBoard(rootNode).getTurn();
        }

        OthelloBoard board2 = getBoard(node2);
        Point lastMove2 = board2.getLastMove();
        int player2;
        if (lastMove2 != null) {
            int row = (int) lastMove2.getX();
            int column = (int) lastMove2.getY();
            player2 = board2.getBoard()[row][column];
        } else {
            player2 = -getBoard(rootNode).getTurn();
        }

        if (player1 == player2) {
            return true;
        } else {
            return false;
        }
    }

//    private int countChildren(Node<HashMap<String,Object>> node) {
//        ArrayList<Node<HashMap<String,Object>>> children = (ArrayList<Node<HashMap<String,Object>>>) node.getChildren();
//        int total = 0;
//        for (Node<HashMap<String,Object>> child : children) {
//            total++;
//            total += countChildren(child);
//        }
//        return total;
//    }

//    public void printData() {
//        getBoard(gameTree.getRoot()).printBoard();
//        if (getBoard(gameTree.getRoot()).getTurn() == OthelloBoard.BLACK)
//            System.out.println("CURRENT PLAYER: BLACK");
//        else
//            System.out.println("CURRENT PLAYER: WHITE");
//        ArrayList<Node<HashMap<String,Object>>> validMoves = (ArrayList<Node<HashMap<String,Object>>>) gameTree.getRoot().getChildren();
//        Node<HashMap<String,Object>> bestMove = null;
//        double highestWinRate = 0;
//        for (Node<HashMap<String,Object>> move : validMoves) {
//            System.out.println("[Coord: " + getBoard(move).getLastMove().getX() + ", " + getBoard(move).getLastMove().getY() + "] "
//                    + "[Wins/Playouts: " + move.getData().get("WINS") + "/" + move.getData().get("PLAYOUTS") + "] "
//                    + "[Wins%: " + String.format("%.2f", (double) move.getData().get("WINS") / (int) move.getData().get("PLAYOUTS") * 100) + "%]");
//            double moveWinRate = (double) move.getData().get("WINS") / (int) move.getData().get("PLAYOUTS");
//            if (moveWinRate > highestWinRate) {
//                highestWinRate = moveWinRate;
//                bestMove = move;
//            }
//            if (highestWinRate == 0.0 && !validMoves.isEmpty())
//                bestMove = validMoves.get(0);
//        }
//        System.out.println("Best Move: " + "[Coord: " + getBoard(bestMove).getLastMove().getX() + ", " + getBoard(bestMove).getLastMove().getY() + "] "
//                + "[Wins%: " + String.format("%.2f", (double) bestMove.getData().get("WINS") / (int) bestMove.getData().get("PLAYOUTS") * 100) + "%]");
//        System.out.println("Total Iterations Completed: " + gameTree.getRoot().getData().get("PLAYOUTS"));
//        System.out.println("Total Nodes: " + countChildren(gameTree.getRoot()) + 1);
//        System.out.println("BLACK: " + getBoard(gameTree.getRoot()).countCellState(OthelloBoard.BLACK));
//        System.out.println("WHITE: " + getBoard(gameTree.getRoot()).countCellState(OthelloBoard.WHITE));
//        System.out.println("TREE HEIGHT: " + Node.rootHeight);
//        System.out.println();
//    }

    public void play() {
        OthelloBoard board = new OthelloBoard(8,8);
        board.useGameBoard(boardPanel.getGameBoard());
        Point bestMove = getMove(board, 3000);
        boardPanel.play((int)bestMove.getX(),(int)bestMove.getY());
    }

    public int getPlayerType() {
        return Player.TYPE_BOT;
    }

}