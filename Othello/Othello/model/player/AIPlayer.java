package model.player;

import gui.BoardPanel;
import gui.MainApp;
import javafx.geometry.Point3D;
import model.GameBoard;

import java.util.ArrayList;
import java.util.Random;



public class AIPlayer  extends Thread implements Player {
    private BoardPanel boardPanel;
    private Random random = new Random();

    public AIPlayer(BoardPanel boardPanel){
       this.boardPanel = boardPanel;
       setName("AIBot");

    }
    public void play(){
        ArrayList<Point3D> validMoves = boardPanel.getGameBoard().getValidMoves();
        if(validMoves.size() > 0){
            int randInt = random.nextInt(validMoves.size());
            //try {Thread.sleep(300);}catch(Exception e){}
            boardPanel.play((int)validMoves.get(randInt).getX() , (int)validMoves.get(randInt).getY());
            //boardPanel.getGameBoard().flipDisc((int)validMoves.get(randInt).getX() , (int)validMoves.get(randInt).getY());
        }

    }
    
    public void run() {
    	while(true) {
    		try {
        		
    			sleep(1000);
    		} catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		//System.out.println("AI Tick");
        	if(MainApp.getSingleton() == null)
        		continue;
        	//System.out.println("Check 2");
        	//System.out.println("GB:" + boardPanel.getGameBoard());
        	if(boardPanel.getGameBoard() == null)
        		continue;
        	//System.out.println("GB not null");
        	//System.out.println(Player.TYPE_BOT);
        	//System.out.println("vs "  + boardPanel.getGameBoard().getPlayer().getPlayerType());
        	if(Player.TYPE_BOT == boardPanel.getGameBoard().getPlayer().getPlayerType()) {
        		try {
        			sleep(2000);
        		} catch (InterruptedException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		play();
        	}
        	
    	}
    	
    }

    public int getPlayerType() {
        return Player.TYPE_BOT;
    }
}
