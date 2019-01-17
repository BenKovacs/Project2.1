package model.player;

import gui.BoardPanel;
import gui.MainApp;
import gui.TestApp;
import gui.TestAppV2;
import javafx.geometry.Point3D;
import java.util.ArrayList;



public class GreedyPlayer extends Thread implements Player{
    private BoardPanel boardPanel;

    private int color;

    public GreedyPlayer(BoardPanel boardPanel, int color) {
       this.boardPanel = boardPanel;
       this.color = color;
       setName("Greedy Bot");

    }
    public void play(){
        ArrayList<Point3D> validMoves = boardPanel.getGameBoard().getValidMoves();
        if(validMoves.size() > 0){
            
        	Point3D max = null;
        	int maxFlips = 0;
        	for(int i = 0; i < validMoves.size(); i++) {
        		Point3D p = validMoves.get(i);
        		int type = boardPanel.getGameBoard().getSquareType((int)p.getX(), (int)p.getY());

        		int x =(int) p.getX();
        		int y =(int) p.getY();

        		if((x==0 && y == 0)||(x==0 && y == 7)||(x==7 && y == 0)||(x==7 && y == 7))
        		{
                    boardPanel.play(x , y);
                }
        		if(type < 0) {
        			int flips = -type;
        			if(flips > maxFlips || max == null) {
        				maxFlips = flips;
        				max = p;
        			}
        		}
        	}
            boardPanel.play((int)max.getX() , (int)max.getY());

        }
    }

    public void run() {
        while(true) {
            try {
                sleep(200);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

           if(MainApp.getSingleton() == null && TestAppV2.getSingleton() == null)
                continue;

            if(boardPanel.getGameBoard() == null)
                continue;

            if(getColor() == boardPanel.getGameBoard().getTurn()) {
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                this.play();
            }

            if (boardPanel.getGameBoard().isGameFinished()){
                break;
            }

        }

    }

    public int getPlayerType() {
        return Player.TYPE_BOT;
    }
    
    public int getColor() {
    	return color;
    }
}
