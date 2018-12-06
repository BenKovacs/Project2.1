package model.player;

import gui.BoardPanel;
import javafx.geometry.Point3D;
import java.util.ArrayList;
import java.util.Random;



public class GreedyPlayer extends Thread implements Player{
    private BoardPanel boardPanel;
    private Random random = new Random();
    
    private int color;

    public GreedyPlayer(BoardPanel boardPanel, int color) {
       this.boardPanel = boardPanel;
       this.color = color;

    }
    public void play(){
        ArrayList<Point3D> validMoves = boardPanel.getGameBoard().getValidMoves();
        if(validMoves.size() > 0){
            
        	Point3D max = null;
        	int maxFlips = 0;
        	for(int i = 0; i < validMoves.size(); i++) {
        		Point3D p = validMoves.get(i);
        		int type = boardPanel.getGameBoard().getSquareType((int)p.getX(), (int)p.getY());
        		if(type < 0) {
        			int flips = -type;
        			if(flips > maxFlips || max == null) {
        				maxFlips = flips;
        				max = p;
        			}
        		}
        	}
            //try {Thread.sleep(300);}catch(Exception e){}
            boardPanel.play((int)max.getX() , (int)max.getY());
            //boardPanel.getGameBoard().flipDisc((int)validMoves.get(randInt).getX() , (int)validMoves.get(randInt).getY());
        }

    }

    public int getPlayerType() {
        return Player.TYPE_BOT;
    }
    
    public int getColor() {
    	return color;
    }
}
