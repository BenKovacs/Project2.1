package model.player;

import gui.BoardPanel;

public class HumanPlayer implements Player{
	
	private int color;
	
    public HumanPlayer(BoardPanel boardPanel, int color){
    	this.color = color;
    }

    public void play(){

    }

    public int getPlayerType(){
        return Player.TYPE_HUMAN;
    }
    
    public int getColor() {
    	return color;
    }

    @Override
    public String toString() {
        return "Human Player";
    }
}
