package model;

import gui.BoardPanel;
import gui.RightPanel;
import model.data_model.Node;
import model.player.MonteCarloTreeSearch;
import model.player.SuperMonteCarloTreeSearch;
import model.player.SuperMonteCarloTreeSearch2;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class OthelloBoard implements Cloneable {

    public static final int BLACK = -1;
    public static final int EMPTY = 0;
    public static final int WHITE = 1;

    private int width;
    private int height;

    private int[][] board;

    private int turn;
    private ArrayList<Point> previousMoves; //Maybe change the var name.
    private Point lastMove;

    public OthelloBoard(int width, int height) {
        this.width = width;
        this.height = height;
        board = new int[height][width];
        previousMoves = new ArrayList<Point>();
        restart();
    }

    public void restart() {
        clearBoard();
        board[(int)Math.floor((height-1)/2.0)][(int)Math.floor((width-1)/2.0)] = WHITE;
        board[(int)Math.floor((height-1)/2.0)][(int)Math.ceil((width-1)/2.0)] = BLACK;
        board[(int)Math.ceil((height-1)/2.0)][(int)Math.floor((width-1)/2.0)] = BLACK;
        board[(int)Math.ceil((height-1)/2.0)][(int)Math.ceil((width-1)/2.0)] = WHITE;
        turn = BLACK;
        previousMoves.clear();
        lastMove = null;
    }

    public void clearBoard() {
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                board[row][column] = EMPTY;
            }
        }
    }

    //play using a board from a GameBoard
    public void useGameBoard(GameBoard gameBoard){
        this.width = gameBoard.getWidth();
        this.height = gameBoard.getHeight();
        board = new int[height][width];
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if (gameBoard.getboard()[row][column] == Constants.BLACK)
                    board[row][column] = BLACK;
                if (gameBoard.getboard()[row][column] == Constants.EMPTY)
                    board[row][column] = EMPTY;
                if (gameBoard.getboard()[row][column] == Constants.WHITE)
                    board[row][column] = WHITE;
            }
        }
        if (gameBoard.getTurn() == Constants.BLACK)
            turn = BLACK;
        if (gameBoard.getTurn() == Constants.WHITE)
            turn = WHITE;
        lastMove = gameBoard.getLastMove();
    }

    //return an list of valid moves as points.
    public ArrayList<Point> getValidMoves() {
        ArrayList<Point> validMoves = new ArrayList<Point>();
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if (isValidMove(row, column)) {
                    Point validMove = new Point(row, column);
                    validMoves.add(validMove);
                }
            }
        }
        return validMoves;
    }

    public boolean isValidMove(int targetRow, int targetColumn) { //perhaps better params name?
        if (isInsideBoard(targetRow, targetColumn) && board[targetRow][targetColumn] == EMPTY) {
            for (int row = targetRow-1; row <= targetRow+1; row++) {
                for (int column = targetColumn-1; column <= targetColumn+1; column++) {
                    if (isInsideBoard(row, column) && board[row][column] == -turn) {
                        int rowDelta = row - targetRow;
                        int columnDelta = column - targetColumn;
                        int searchRow = row + rowDelta;
                        int searchColumn = column + columnDelta;
                        while(isInsideBoard(searchRow, searchColumn)) {
                            if (board[searchRow][searchColumn] == -turn) {
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

    public boolean isInsideBoard(int row, int column) {
        return row >= 0 && row < height && column >= 0 && column < width;
    }

    public void play(int targetRow, int targetColumn) {
        if (isValidMove(targetRow,targetColumn)) {
            board[targetRow][targetColumn] = turn;
            lastMove = new Point(targetRow, targetColumn);
            previousMoves.add(lastMove);
            for (int row = targetRow-1; row <= targetRow+1; row++) {
                for (int column = targetColumn-1; column <= targetColumn+1; column++) {
                    if (isInsideBoard(row, column) && board[row][column] == -turn) {
                        ArrayList<Point> exploredCells = new ArrayList<Point>();
                        Point exploredCell = new Point(row, column);
                        exploredCells.add(exploredCell);
                        int rowDelta = row - targetRow;
                        int columnDelta = column - targetColumn;
                        int exploringRow = row + rowDelta;
                        int exploringColumn = column + columnDelta;
                        while(isInsideBoard(exploringRow, exploringColumn) && !exploredCells.isEmpty()) {
                            if (board[exploringRow][exploringColumn] == -turn) {
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
            switchTurn();
        } else {
            System.out.println("INVALID MOVE");
        }
    }

    //switch player turn
    public void switchTurn() {
        turn = -turn;
        if (getValidMoves().isEmpty()) {
            turn = -turn;
            if (getValidMoves().isEmpty()) {
                turn = -turn;
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

    public String gameResult(){
        if (isGameOver()) {
            if (countCellState(BLACK) > countCellState(WHITE)) {
                return "BLACK WIN";
            } else if (countCellState(BLACK) < countCellState(WHITE)) {
                return "WHITE WIN";
            } else {
                return "DRAW";
            }
        } else {
            return "GAME NOT OVER";
        }
    }

    public String playerResult(int player){
        if (isGameOver()) {
            if (countCellState(player) > countCellState(-player)) {
                return "WON";
            } else if (countCellState(player) < countCellState(-player)) {
                return "LOSE";
            } else {
                return "DRAW";
            }
        } else {
            return "GAME NOT OVER";
        }
    }

    public boolean isGameOver() {
        if (getValidMoves().isEmpty())
            return true;
        else
            return false;
    }

    //NEED A BETTER WAY TO COPY THE ARRAY !!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public Object clone() {
        OthelloBoard clone = null;
        try {
            clone = (OthelloBoard) super.clone();
            int[][] cloneBoard = new int[height][width]; //Perhaps a better name?
            for (int row = 0; row < height; row++) {
                for (int column = 0; column < width; column++) {
                    cloneBoard[row][column] = board[row][column];
                }
            }
            clone.setBoard(cloneBoard);
            clone.setPreviousMoves(new ArrayList<Point>(previousMoves));
            if (lastMove != null)
                clone.setLastMove(new Point((int) lastMove.getX(), (int) lastMove.getY())); //TRY remove this line and see what happen
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
                if (showBoard[row][column] == BLACK)
                    System.out.print("B ");
                if (showBoard[row][column] == WHITE)
                    System.out.print("W ");
                if (showBoard[row][column] == EMPTY)
                    System.out.print("O ");
                if (showBoard[row][column] == 9)
                    System.out.print("X ");
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
    public Point getLastMove() { return lastMove; }

    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }
    public void setBoard(int[][] board) { this.board = board; }
    public void setTurn(int turn) { this.turn = turn; }
    public void setPreviousMoves(ArrayList<Point> previousMoves) { this.previousMoves = previousMoves; }
    public void setLastMove(Point lastMove) { this.lastMove = lastMove; }

}
