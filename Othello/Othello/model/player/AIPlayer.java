package model.player;

import model.GameBoard;

import java.awt.*;
import java.util.Random;
import java.util.ArrayList;

import static model.Constants.*;



public class AIPlayer implements Player{
    private GameBoard gameBoard;
    Random random = new Random();

    public AIPlayer(GameBoard gameBoard){
        this.gameBoard = gameBoard;

    }
    public void play(){
        ArrayList<Point> validMoves = gameBoard.getValidMoves();
        int tmp = validMoves.size();
        int randInt = random.nextInt(tmp);
        gameBoard.flipDisc((int)validMoves.get(randInt).getX() , (int)validMoves.get(randInt).getY());
    }
    public String getPlayerType(){
        return "bot";
    }
}
