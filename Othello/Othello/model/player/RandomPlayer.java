package model.player;

import gui.BoardPanel;
import gui.MainApp;
import gui.TestAppV2;
import javafx.geometry.Point3D;

import java.util.ArrayList;
import java.util.Random;



public class RandomPlayer  extends Thread implements Player {
    private BoardPanel boardPanel;
    private Random random = new Random();
    
    private int color;
    

    public RandomPlayer(BoardPanel boardPanel, int color){
       this.boardPanel = boardPanel;
       this.color = color;
       setName("Random Bot");

    }
    public void play(){
        ArrayList<Point3D> validMoves = boardPanel.getGameBoard().getValidMoves();
        if(validMoves.size() > 0){
            int randInt = random.nextInt(validMoves.size());
            boardPanel.play((int)validMoves.get(randInt).getX() , (int)validMoves.get(randInt).getY());
            //boardPanel.getGameBoard().flipDisc((int)validMoves.get(randInt).getX() , (int)validMoves.get(randInt).getY());
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
    		//System.out.println("AI Tick");
			if(MainApp.getSingleton() == null && TestAppV2.getSingleton() == null)
				continue;
        	//System.out.println("Check 2");
        	//System.out.println("GB:" + boardPanel.getGameBoard());
        	if(boardPanel.getGameBoard() == null)
        		continue;
        	//System.out.println("GB not null");
        	//System.out.println(Player.TYPE_BOT);
        	//System.out.println("vs "  + boardPanel.getGameBoard().getPlayer().getPlayerType());
        	if(getColor() == boardPanel.getGameBoard().getTurn()) {
        		try {
        			sleep(100);
        		} catch (InterruptedException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		play();
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
