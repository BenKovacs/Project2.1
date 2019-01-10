package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Rolit implements Cloneable {

    public static final int EMPTY = 0;
    public static final int RED = 1;
    public static final int YELLOW = 2;
    public static final int GREEN = 3;
    public static final int BLUE = 4;

    private int width;
    private int height;

    private int[][] board;

    private int[] players;
    private int turn;
    private ArrayList<Point> previousMoves; //Maybe change the var name.
//    private Point lastMove;

    public Rolit(int width, int height, int[] players) {
        this.width = width;
        this.height = height;
        board = new int[height][width];
        this.players = players;
        previousMoves = new ArrayList<Point>();
        restart();
    }

    public void restart() {
        clearBoard();
        board[(int)Math.floor((height-1)/2.0)][(int)Math.floor((width-1)/2.0)] = RED;
        board[(int)Math.floor((height-1)/2.0)][(int)Math.ceil((width-1)/2.0)] = YELLOW;
        board[(int)Math.ceil((height-1)/2.0)][(int)Math.floor((width-1)/2.0)] = BLUE;
        board[(int)Math.ceil((height-1)/2.0)][(int)Math.ceil((width-1)/2.0)] = GREEN;
        Random random = new Random();
        turn = random.nextInt(4) + 1;
        previousMoves.clear();
//        lastMove = null;
    }

    public void clearBoard() {
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                board[row][column] = EMPTY;
            }
        }
    }

//    //play using a board from a GameBoard
//    public void useGameBoard(GameBoard gameBoard){
//        this.width = gameBoard.getWidth();
//        this.height = gameBoard.getHeight();
//        board = new int[height][width];
//        for (int row = 0; row < height; row++) {
//            for (int column = 0; column < width; column++) {
//                if (gameBoard.getboard()[row][column] == Constants.BLACK)
//                    board[row][column] = BLACK;
//                if (gameBoard.getboard()[row][column] == Constants.EMPTY)
//                    board[row][column] = EMPTY;
//                if (gameBoard.getboard()[row][column] == Constants.WHITE)
//                    board[row][column] = WHITE;
//            }
//        }
//        if (gameBoard.getTurn() == Constants.BLACK)
//            turn = BLACK;
//        if (gameBoard.getTurn() == Constants.WHITE)
//            turn = WHITE;
//        lastMove = gameBoard.getLastMove();
//    }

    //return a list of valid moves as points.
    public ArrayList<Point> getValidMoves() {
        ArrayList<Point> validMoves = new ArrayList<Point>();
        validMoves.addAll(getCapturableCells());
        if (validMoves.isEmpty()) {
            for (int row = 0; row < height; row++) {
                for (int column = 0; column < width; column++) {
                    if (board[row][column] == EMPTY && hasOpponentNeighbor(row, column)) {
                        Point validMove = new Point(row, column);
                        validMoves.add(validMove);
                    }
                }
            }
        }
        return validMoves;
    }

    private boolean isCellCapturable(int targetRow, int targetColumn) {
        if (isInsideBoard(targetRow, targetColumn) && board[targetRow][targetColumn] == EMPTY) {
            for (int row = targetRow-1; row <= targetRow+1; row++) {
                for (int column = targetColumn-1; column <= targetColumn+1; column++) {
                    if (isInsideBoard(row, column) && isOtherPlayer(row, column)) {
                        int rowDelta = row - targetRow;
                        int columnDelta = column - targetColumn;
                        int searchRow = row + rowDelta;
                        int searchColumn = column + columnDelta;
                        while(isInsideBoard(searchRow, searchColumn)) {
                            if (isOtherPlayer(searchRow, searchColumn)) {
                                searchRow = searchRow + rowDelta;
                                searchColumn = searchColumn + columnDelta;
                            } else if (board[searchRow][searchColumn] == turn) {
                                return true;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean hasOpponentNeighbor(int targetRow, int targetColumn) {
        for (int row = targetRow-1; row <= targetRow+1; row++) {
            for (int column = targetColumn-1; column <= targetColumn+1; column++) {
                if (isInsideBoard(row, column) && isOtherPlayer(row, column)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isOtherPlayer(int row, int column) {
        return board[row][column] != turn && board[row][column] != EMPTY;
    }

    private boolean isInsideBoard(int row, int column) {
        return row >= 0 && row < height && column >= 0 && column < width;
    }

    private ArrayList<Point> getCapturableCells () {
        ArrayList<Point> capturableCells = new ArrayList<Point>();
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if (isCellCapturable(row, column)) {
                    Point capturableCell = new Point(row, column);
                    capturableCells.add(capturableCell);
                }
            }
        }
        return capturableCells;
    }

    private boolean isValidMove(int targetRow, int targetColumn) {
        if (isCellCapturable(targetRow, targetColumn)) {
            return true;
        } else {
            return getCapturableCells().isEmpty() && hasOpponentNeighbor(targetRow, targetColumn);
        }
    }

    public void play(int targetRow, int targetColumn) {
        if (isValidMove(targetRow,targetColumn)) {
            board[targetRow][targetColumn] = turn;
            Point lastMove = new Point(targetRow, targetColumn);
            previousMoves.add(lastMove);
            for (int row = targetRow-1; row <= targetRow+1; row++) {
                for (int column = targetColumn-1; column <= targetColumn+1; column++) {
                    if (isInsideBoard(row, column) && isOtherPlayer(row, column)) {
                        ArrayList<Point> exploredCells = new ArrayList<Point>();
                        Point exploredCell = new Point(row, column);
                        exploredCells.add(exploredCell);
                        int rowDelta = row - targetRow;
                        int columnDelta = column - targetColumn;
                        int exploringRow = row + rowDelta;
                        int exploringColumn = column + columnDelta;
                        while(isInsideBoard(exploringRow, exploringColumn) && !exploredCells.isEmpty()) {
                            if (isOtherPlayer(exploringRow, exploringColumn)) {
                                exploredCell = new Point(exploringRow, exploringColumn);
                                exploredCells.add(exploredCell);
                                exploringRow = exploringRow + rowDelta;
                                exploringColumn = exploringColumn + columnDelta;
                            } else if (board[exploringRow][exploringColumn] == turn) {
                                for (Point cell : exploredCells) //perhaps better var name that make more sense?
                                    board[(int)cell.getX()][(int)cell.getY()] = turn;
                                exploredCells.clear();
                            } else {
                                exploredCells.clear();
                            }
                        }
                    }
                }
            }
            nextTurn();
        } else {
            System.out.println("INVALID MOVE");
        }
    }

    //switch player turn
    public void nextTurn() {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == turn) {
                if (i < players.length - 1) {
                    turn = players[i+1];
                    break;
                } else {
                    turn = players[0];
                    break;
                }
            }
        }
    }

    //count the number of cells which have the given cell state.
    public int countCellState(int cellState){
        int counter = 0;
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if (board[row][column] == cellState)
                    counter++;
            }
        }
        return counter;
    }

    private String toColor(int value) {
        switch (value) {
            case 1 : return "RED";
            case 2 : return "YELLOW";
            case 3 : return "GREEN";
            case 4 : return "BLUE";
            case 9 : return "VALID";
            default: return "EMPTY";
        }
    }

    private String toChar(int value) {
        switch (value) {
            case 1 : return "R";
            case 2 : return "Y";
            case 3 : return "G";
            case 4 : return "B";
            case 9 : return "X";
            default: return "O";
        }
    }

    public String gameResult(){
        if (isGameOver()) {
            String result = "";
            for (int i = 0; i < players.length; i++) {
                result += "PLAYER " + players[i] + ": " + countCellState(players[i]) + "\n";
            }
            return result;
        } else {
            return "GAME NOT OVER";
        }
    }

//    public String playerResult(int player){
//        if (isGameOver()) {
//            if (countCellState(player) > countCellState(-player)) {
//                return "WON";
//            } else if (countCellState(player) < countCellState(-player)) {
//                return "LOSE";
//            } else {
//                return "DRAW";
//            }
//        } else {
//            return "GAME NOT OVER";
//        }
//    }

    public boolean isGameOver() {
        return getValidMoves().isEmpty();
    }

    //NEED A BETTER WAY TO COPY THE ARRAY !!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public Object clone() {
        Rolit clone = null;
        try {
            clone = (Rolit) super.clone();
            int[][] cloneBoard = new int[height][width]; //Perhaps a better name?
            for (int row = 0; row < height; row++) {
                for (int column = 0; column < width; column++) {
                    cloneBoard[row][column] = board[row][column];
                }
            }
            clone.setBoard(cloneBoard);

            int[] clonePlayers = new int[players.length]; //Perhaps a better name?
            for (int i = 0; i < players.length; i++) {
                clonePlayers[i] = players[i];
            }
            clone.setPlayers(clonePlayers);

            clone.setPreviousMoves(new ArrayList<Point>(previousMoves));
//            if (lastMove != null)
//                clone.setLastMove(new Point((int) lastMove.getX(), (int) lastMove.getY())); //TRY remove this line and see what happen
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }

    public void printBoard(){
        int[][] showBoard = new int[height][width];
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                showBoard[row][column] = board[row][column];
            }
        }
        ArrayList<Point> validMoves = getValidMoves();
        for (Point move : validMoves) {
            showBoard[(int)move.getX()][(int)move.getY()] = 9;
        }
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                System.out.print(toChar(showBoard[row][column]) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int[][] getBoard() { return board; }
    public int getTurn() { return turn; }
    public ArrayList<Point> getPreviousMoves() { return previousMoves; }
    public Point getLastMove() { return previousMoves.get(previousMoves.size()-1); }
    public int[] getPlayers() { return players; }

    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }
    public void setBoard(int[][] board) { this.board = board; }
    public void setTurn(int turn) { this.turn = turn; }
    public void setPreviousMoves(ArrayList<Point> previousMoves) { this.previousMoves = previousMoves; }
//    public void setLastMove(Point lastMove) { this.lastMove = lastMove; }
    public void setPlayers(int[] players) { this.players = players; }

}
