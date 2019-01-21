package model.player;

import gui.BoardPanel;
import gui.MainApp;
import gui.TestApp;
import model.data_model.MCTSBoard;
import model.data_model.Node;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MonteCarloTreeSearch extends Thread implements Player {

    private Node<HashMap<String,Object>> rootNode;
    private Node<HashMap<String,Object>> currentNode;

    private int runtime;
    private int iterations;
    private int exploreParam;

    private BoardPanel boardPanel;
    private int color;

    public MonteCarloTreeSearch() {
        this.runtime = 3000;
        this.iterations = 10000;
        this.exploreParam = 2;
    }

    public MonteCarloTreeSearch(BoardPanel boardPanel, int color, int runtime, int iterations) {
        this.boardPanel = boardPanel;
        this.color = color;
        this.runtime = runtime;
        this.iterations = iterations;
        this.exploreParam = 2;
        setName("MCTS Bot");
    }

    public Point getMove(MCTSBoard board) {
        initialization(board);

        long endTime = System.currentTimeMillis() + runtime;
        int iterationCount = 0;
        while (System.currentTimeMillis() < endTime || iterationCount < iterations) {
            selection();
            iterationCount++;
        }

        ArrayList<Node<HashMap<String,Object>>> validMoves = (ArrayList<Node<HashMap<String,Object>>>) rootNode.getChildren();
        if (!validMoves.isEmpty()) {
            Node<HashMap<String, Object>> bestMove = validMoves.get(0);
            for (Node<HashMap<String, Object>> move : validMoves) {
                if (winRate(move) > winRate(bestMove)) {
                    bestMove = move;
                }
            }
            return new Point((int) getBoard(bestMove).getLastMove().getX(), (int) getBoard(bestMove).getLastMove().getY());
        } else {
            return null;
        }
    }

    private void initialization(MCTSBoard board) {
        HashMap<String,Object> data = new HashMap<String, Object>();
        data.put("BOARD", board);
        data.put("WINS", 0.0);
        data.put("PLAYOUTS", 0);
        rootNode = new Node<HashMap<String,Object>>(data);

        ArrayList<Point> validMoves = getBoard(rootNode).getValidMoves();
        for (Point move : validMoves) {
            MCTSBoard clonedBoard = (MCTSBoard) getBoard(rootNode).clone();
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
            backpropagation(getResult(getBoard(currentNode)));
        }
    }

    private void expansion() {
        ArrayList<Point> validMoves = getBoard(currentNode).getValidMoves();
        for (Point move : validMoves) {
            MCTSBoard clonedBoard = (MCTSBoard) getBoard(currentNode).clone();
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
        MCTSBoard clonedBoard = (MCTSBoard) getBoard(currentNode).clone();
        Random random = new Random();
        while (!clonedBoard.getValidMoves().isEmpty()) {
            ArrayList<Point> validMoves = clonedBoard.getValidMoves();
            Point move = validMoves.get(random.nextInt(validMoves.size()));
            int row = (int) move.getX();
            int column = (int) move.getY();
            clonedBoard.play(row, column);
        }
        double[] result = getResult(clonedBoard);
        backpropagation(result);
    }

    private double[] getResult(MCTSBoard simulatedBoard) {
        int currentPlayer = (int) getBoard(currentNode).getLastMove().getZ();
        int max = 0;
        for (int i = 0; i < simulatedBoard.getPlayerList().length; i++) {
            if (simulatedBoard.countCellState(simulatedBoard.getPlayerList()[i]) > max) {
                max = simulatedBoard.countCellState(simulatedBoard.getPlayerList()[i]);
            }
        }
        double[] result = new double[7];
        for (int i = 0; i < simulatedBoard.getPlayerList().length; i++) {
            if (simulatedBoard.countCellState(simulatedBoard.getPlayerList()[i]) == max) {
                result[simulatedBoard.getPlayerList()[i]] = 1.0;
            } else {
                result[simulatedBoard.getPlayerList()[i]] = 0.0;
            }
        }
        return result;
//        Still need to implement the case of draw
    }

    private void backpropagation(double[] result){
        while (currentNode != rootNode) {
            int currentNodePlayer = (int) getBoard(currentNode).getLastMove().getZ();
            currentNode.getData().put("WINS", getWins(currentNode) + result[currentNodePlayer]);
            currentNode.getData().put("PLAYOUTS", getPlayouts(currentNode) + 1);
            currentNode = currentNode.getParent();
        }
        currentNode.getData().put("PLAYOUTS", getPlayouts(currentNode) + 1);
    }

    private double uct(Node<HashMap<String,Object>> node) {
        return getWins(node) / getPlayouts(node) + Math.sqrt(exploreParam * Math.log(getPlayouts(rootNode)) / getPlayouts(node));
    }

    private double winRate(Node<HashMap<String, Object>> node) {
        return getWins(node) / getPlayouts(node);
    }

    private MCTSBoard getBoard(Node<HashMap<String,Object>> node) {
        return (MCTSBoard) node.getData().get("BOARD");
    }

    private double getWins(Node<HashMap<String,Object>> node) {
        return (double) node.getData().get("WINS");
    }

    private int getPlayouts(Node<HashMap<String,Object>> node) {
        return (int) node.getData().get("PLAYOUTS");
    }

    //Not sure if this change could result in possible problems
    //Using last turn instead of last played color to identify players
//    private boolean isSamePlayer(Node<HashMap<String,Object>> node1, Node<HashMap<String,Object>> node2) {
//        MCTSBoard board1 = getBoard(node1);
//        Point lastMove1 = board1.getLastMove();
//        int player1;
//        if (lastMove1 != null) {
//            int row = (int) lastMove1.getX();
//            int column = (int) lastMove1.getY();
//            player1 = board1.getBoard()[row][column];
//        } else {
//            player1 = -getBoard(rootNode).getTurn();
//        }
//
//        MCTSBoard board2 = getBoard(node2);
//        Point lastMove2 = board2.getLastMove();
//        int player2;
//        if (lastMove2 != null) {
//            int row = (int) lastMove2.getX();
//            int column = (int) lastMove2.getY();
//            player2 = board2.getBoard()[row][column];
//        } else {
//            player2 = -getBoard(rootNode).getTurn();
//        }
//
//        if (getBoard(node1).getTurn() == getBoard(node2).getTurn()) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    public int getRuntime() { return runtime; }
    public int getIterations() { return iterations; }
    public int getExploreParam() { return this.exploreParam; }

    public void setRuntime(int runtime) { this.runtime = runtime; }
    public void setIterations(int iterations) { this.iterations = iterations; }
    public void setExploreParam(int exploreParam) { this.exploreParam = exploreParam; }

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
        MCTSBoard board = getBoard(rootNode);
        System.out.println("MCTS PLAYER: " + board.toColor(board.getTurn()));
        ArrayList<Node<HashMap<String,Object>>> validMoves = (ArrayList<Node<HashMap<String,Object>>>) rootNode.getChildren();
        for (Node<HashMap<String,Object>> move : validMoves) {
            System.out.println("[COORD: " + getBoard(move).getLastMove().getX() + ", " + getBoard(move).getLastMove().getY() + "] "
                    + "[WINS/PLAYOUTS: " + move.getData().get("WINS") + "/" + move.getData().get("PLAYOUTS") + "] "
                    + "[WINS%: " + String.format("%.2f", (double) move.getData().get("WINS") / (int) move.getData().get("PLAYOUTS") * 100) + "%]");
        }
        System.out.println("TOTAL ITERATIONS: " + rootNode.getData().get("PLAYOUTS"));
        System.out.println("TOTAL NODES: " + (countChildren(rootNode) + 1));
        for (int player = 0; player < board.getPlayerList().length; player++) {
            System.out.println(board.toColor(board.getPlayerList()[player]) + " COUNTS: " + board.countCellState(board.getPlayerList()[player]));
        }
        System.out.println();
    }

    public void run() {
        while(true) {
            if (boardPanel.getGameBoard().isGameFinished()){
                break;
            }
            try {
                sleep(200);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //System.out.println("AI Tick");
            if(MainApp.getSingleton() == null && TestApp.getSingleton() == null)
                continue;
            //System.out.println("Check 2");
            //System.out.println("GB:" + boardPanel.getGameBoard());
            if(boardPanel.getGameBoard() == null)
                continue;
            //System.out.println("GB not null");
            //System.out.println(Player.TYPE_BOT);
            //System.out.println("vs "  + boardPanel.getGameBoard().getPlayer().getPlayerType());
            if(getColor() == boardPanel.getGameBoard().getTurn()) {
                play();
            }
        }
    }

    public void play() {
        MCTSBoard board = new MCTSBoard(8,8);
        board.useGameBoard(boardPanel.getGameBoard());
        Point bestMove = getMove(board);
        if (bestMove != null) {
            board.printBoard();
            //printData();
            boardPanel.play((int)bestMove.getX(),(int)bestMove.getY());
        }
    }

    public int getPlayerType() {
        return Player.TYPE_BOT;
    }
    
    public int getColor() {
    	return color;
    }

    public String toString() {
        return "MCTS Player";
    }
    
}