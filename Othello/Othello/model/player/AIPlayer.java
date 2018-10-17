package model.player;

import gui.BoardPanel;
import model.GameBoard;

import java.awt.*;
import java.util.Random;
import java.util.ArrayList;

import static model.Constants.*;



public class AIPlayer implements Player{
    private BoardPanel boardPanel;
    private Random random = new Random();

    public AIPlayer(BoardPanel boardPanel){
       this.boardPanel = boardPanel;

    }
    public void play(){
        ArrayList<Point> validMoves = boardPanel.getGameBoard().getValidMoves();
        if(validMoves.size() > 0){
            int randInt = random.nextInt(validMoves.size());
            //try {Thread.sleep(300);}catch(Exception e){}
            boardPanel.play((int)validMoves.get(randInt).getX() , (int)validMoves.get(randInt).getY());
            //boardPanel.getGameBoard().flipDisc((int)validMoves.get(randInt).getX() , (int)validMoves.get(randInt).getY());
        }

    }
    public String getPlayerType(){
        return "bot";
    }
}
