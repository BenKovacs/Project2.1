package model.player;

import gui.BoardPanel;
import model.GameBoard;

import java.awt.*;
import java.util.Random;
import java.util.ArrayList;

import static model.Constants.*;



public class GreedyPlayer implements Player{
    private BoardPanel boardPanel;
    private Random random = new Random();

    public GreedyPlayer(BoardPanel boardPanel){
       this.boardPanel = boardPanel;

    }
    public void play(){
        ArrayList<Point> validMoves = boardPanel.getGameBoard().getValidMoves();
        if(validMoves.size() > 0){
            
        	Point max = null;
        	int maxFlips = 0;
        	for(int i = 0; i < validMoves.size(); i++) {
        		Point p = validMoves.get(i);
        		int type = boardPanel.getGameBoard().getSquareType(p.x, p.y);
        		if(type < 0) {
        			int flips = -type;
        			if(flips > maxFlips || max == null) {
        				maxFlips = flips;
        				max = p;
        			}
        		}
        	}
            //try {Thread.sleep(300);}catch(Exception e){}
            boardPanel.play(max.x , max.y);
            //boardPanel.getGameBoard().flipDisc((int)validMoves.get(randInt).getX() , (int)validMoves.get(randInt).getY());
        }

    }
    public String getPlayerType(){
        return "bot";
    }
}
