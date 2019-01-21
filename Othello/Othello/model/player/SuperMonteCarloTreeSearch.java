package model.player;

import gui.BoardPanel;
import javafx.geometry.Point3D;
import model.data_model.MCTSBoard;
import model.data_model.Node;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SuperMonteCarloTreeSearch extends Thread implements Player {

    private Node<HashMap<String,Object>> rootNode;
    private Node<HashMap<String,Object>> currentNode;

    private int runtime;
    private int iterations;
    private double exploreParam;

    private BoardPanel boardPanel;
    private int color;

    private long startTime;
    private long timer;

    public SuperMonteCarloTreeSearch() {
        this.runtime = 3000;
        this.iterations = 10000;
        this.exploreParam = 0.5;
    }

    public SuperMonteCarloTreeSearch(BoardPanel boardPanel, int color, int runtime, int iterations) {
        this.runtime = 3000;
        this.iterations = 0;
        this.exploreParam = 0.5;
        this.boardPanel = boardPanel;
        this.color = color;
        setName("SMCTS Bot");
    }

    private void initialization(MCTSBoard board) {
        HashMap<String,Object> data = new HashMap<String, Object>();
        data.put("BOARD", (MCTSBoard) board.clone());
        data.put("WINS", 0.0);
        data.put("PLAYOUTS", 0);
        data.put("SCORE", 0);
        rootNode = new Node<HashMap<String,Object>>(data);

        ArrayList<Point> validMoves = board.getValidMoves();
        for (Point move : validMoves) {
            MCTSBoard clonedBoard = (MCTSBoard) board.clone();
            int row = (int) move.getX();
            int column = (int) move.getY();
            clonedBoard.play(row, column);
            data = new HashMap<String,Object>();
            data.put("BOARD", clonedBoard);
            data.put("WINS", 0.0);
            data.put("PLAYOUTS", 0);
            data.put("SCORE", 0);
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
            backpropagation(getResult(getBoard(currentNode)), getScores(getBoard(currentNode)));
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
            data.put("SCORE", 0);
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
        int[] scores = getScores(clonedBoard);
        backpropagation(result, scores);
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

    private int[] getScores(MCTSBoard simulatedBoard) {
        int[] scores = new int[7];
        int[] playerList = simulatedBoard.getPlayerList();
        for (int player : playerList) {
            scores[player] = simulatedBoard.countCellState(player);
        }
        return scores;
//        Still need to implement the case of draw
    }

    private void backpropagation(double[] result, int[] scores){
        while (currentNode != rootNode) {
            int currentNodePlayer = (int) getBoard(currentNode).getLastMove().getZ();
            currentNode.getData().put("WINS", getWins(currentNode) + result[currentNodePlayer]);
            currentNode.getData().put("PLAYOUTS", getPlayouts(currentNode) + 1);
            currentNode.getData().put("SCORE", getScore(currentNode) + scores[currentNodePlayer]);
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

    private double avgScore(Node<HashMap<String, Object>> node) {
        return (double) getScore(node) / getPlayouts(node);
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

    private int getScore(Node<HashMap<String,Object>> node) {
        return (int) node.getData().get("SCORE");
    }

    public int getRuntime() { return runtime; }
    public int getIterations() { return iterations; }
    public double getExploreParam() { return this.exploreParam; }

    public void setRuntime(int runtime) { this.runtime = runtime; }
    public void setIterations(int iterations) { this.iterations = iterations; }
    public void setExploreParam(double exploreParam) { this.exploreParam = exploreParam; }

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
        System.out.println("SUPER MCTS PLAYER: " + board.toColor(board.getTurn()));
        ArrayList<Node<HashMap<String,Object>>> validMoves = (ArrayList<Node<HashMap<String,Object>>>) rootNode.getChildren();
        for (Node<HashMap<String,Object>> move : validMoves) {
            System.out.println("[COORD: " + getBoard(move).getLastMove().getX() + ", " + getBoard(move).getLastMove().getY() + "] "
                    + "[WINS/PLAYOUTS: " + move.getData().get("WINS") + "/" + move.getData().get("PLAYOUTS") + "] "
                    + "[WINS%: " + String.format("%.2f", winRate(move) * 100) + "%] "
                    + "[AVERAGE SCORE: " + String.format("%.2f", avgScore(move)) + "]");
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
            if (rootNode == null) {
                if (boardPanel.getGameBoard() != null) {
                    MCTSBoard board = new MCTSBoard(8,8);
                    board.useGameBoard(boardPanel.getGameBoard());
                    initialization(board);
                    startTime = System.currentTimeMillis();
                    timer = runtime;
                }
            } else {
                while (getBoard(rootNode).getPreviousMoves().size() < boardPanel.getGameBoard().getPreviousMoves().size()) {
                    Point3D rootNextMove = getBoard(rootNode).convertPreviousMove(boardPanel.getGameBoard().getPreviousMoves().get(getBoard(rootNode).getPreviousMoves().size()));
                    ArrayList<Node<HashMap<String,Object>>> children = (ArrayList<Node<HashMap<String,Object>>>) rootNode.getChildren();
                    for (Node<HashMap<String,Object>> child : children) {
                        if (getBoard(child).getLastMove().equals(rootNextMove)) {
                            rootNode = child;
                            rootNode.getParent().setChildren(null);
                            rootNode.setParent(null);
                            break;
                        }
                        if (child == children.get(children.size()-1)) {
                            MCTSBoard board = new MCTSBoard(8,8);
                            board.useGameBoard(boardPanel.getGameBoard());
                            initialization(board);
                        }
                    }
                }
                if(getColor() == boardPanel.getGameBoard().getTurn()) {
                    long endTime = System.currentTimeMillis();
                    long deltaTime = endTime - startTime;
                    timer = timer - deltaTime;
                    if (timer < 0) {
                        play();
                        timer = runtime;
                    }
                }
                startTime = System.currentTimeMillis();
                selection();
            }
        }
    }

    public void play() {
        ArrayList<Node<HashMap<String,Object>>> validMoves = (ArrayList<Node<HashMap<String,Object>>>) rootNode.getChildren();
        if (!validMoves.isEmpty()) {
            Node<HashMap<String, Object>> bestMove = validMoves.get(0);
            for (Node<HashMap<String, Object>> move : validMoves) {
                if (winRate(move) > winRate(bestMove) || (winRate(move) == winRate(bestMove) && avgScore(move) > avgScore(bestMove))) {
                    bestMove = move;
                }
            }
            getBoard(rootNode).printBoard();
            printData();
            boardPanel.play((int) getBoard(bestMove).getLastMove().getX(), (int) getBoard(bestMove).getLastMove().getY());
        }
    }

    public int getPlayerType() {
        return Player.TYPE_BOT;
    }

    public int getColor() {
        return color;
    }

}