package model.data_model;

import javafx.geometry.Point3D;
import model.Constants;
import java.util.ArrayList;
import static model.Constants.EMPTY;

/**
 * Encapsulate some of the features of the board but easier to manage for AI
 */
public class AIBoard{

    private int[][] board;
    private int turn;

    public AIBoard(int[][] b, int turn){
        this.turn = turn;
        board = new int[8][8];

        for(int i=0; i<board.length; i++){
            for(int f=0; f<board[0].length; f++){
                this.board[i][f] = b[i][f];
                if (board[i][f] < 0) board[i][f] = EMPTY;
            }
        }
    }

    /**
     * - check valid moves for that player
     * - for each valid move, flip the disc and develop the next state
     */
    public ArrayList<Point3D> getValidM(){
        ArrayList<Point3D> validMoves = new ArrayList<>();


        // Find possible moves for new player turn
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {

                int flips = countFlips(x, y);
                if (flips > 0) validMoves.add(new Point3D(x, y, heuristicPosition(x,y) + flips));

            }
        }
        return validMoves;
    }

    public int countFlips(int x, int y) {
        if (board[x][y] == EMPTY || board[x][y] < 0) {
            return checkFlip(x, y, false);
        } else {
            return 0;
        }
    }

    private int checkFlip(int x, int y, boolean executeMove) {
        int flips = 0;

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (isInsideBoard(i, j)) {
                    if (board[i][j] == (turn==0?1:0)) {
                        int flipsDirection = checkDirection(x, y, i, j, executeMove);
                        if (flipsDirection > 0) {
                            flips += flipsDirection;
                        }
                    }
                }
            }
        }
        return flips;
    }


    private int checkDirection(int x, int y, int i, int j, boolean executeMove) {
        int flips;
        if (board[x + (i - x)][y + (j - y)] == EMPTY || board[x + (i - x)][y + (j - y)] < 0) {
            return -1;
        } else if (board[x + (i - x)][y + (j - y)] == turn) {
            return 0;
        } else if (isInsideBoard(x + (i - x) * 2, y + (j - y) * 2)) {
            flips = checkDirection(x + (i - x), y + (j - y), x + (i - x) * 2, y + (j - y) * 2, executeMove);
            if (flips >= 0) {
                if (executeMove) {
                    board[x + (i - x)][y + (j - y)] = turn;
                }
                return flips + 1;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    public void flipDisc(int x, int y){
        //if x,y is in the valid moves than do this

        if (isValidMove(x, y)) {
            checkFlip(x, y, true);
            board[x][y] = turn;
        } else {
            System.out.println("invalid move");
        }
    }

    public boolean isValidMove(int x, int y) {
        return (board[x][y] == EMPTY || board[x][y] < 0);
    }

    private boolean isInsideBoard(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    public void changeTurn(){
        if(turn == Constants.WHITE) turn = Constants.BLACK;
        else
            turn = Constants.WHITE;
    }

    public int getTurn() {
        return turn;
    }

    public int[][] getBoard() {
        return board;
    }

    public void printBoard(){
        for(int i=0; i<board.length; i++){
            for(int f=0; f<board[0].length; f++){

                if(this.board[i][f] == 0)System.out.print("W ");
                if(this.board[i][f] == 1)System.out.print("B ");
                if(this.board[i][f] == 9)System.out.print(0 + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    

    private int heuristicPosition(int x, int y){
        //heuristic that gives amount based on the position of the flipped disks on the table
        if(x>=3 && x<= 4 && y>=3 && y<= 4) return 1;
        else{
            if(x>=2 && x<= 5 && y>=2 && y<= 5) return 3;
            else{
                if(x>=1 && x<= 6 && y>=1 && y<= 6) return 8;
                else{
                    if(x>=0 && x<= 7 && y>=0 && y<= 7)return 16;
                }
            }
        }
        return 1;
    }
    
    
    private int stabilityHeuristic() {
    	return 0;
    }
}


