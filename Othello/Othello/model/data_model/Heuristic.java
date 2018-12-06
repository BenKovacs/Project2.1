package model.data_model;

import model.GameBoard;

public interface Heuristic {

    //return a value between 0 and 1, or -1 and 1?
    public double getScore(GameBoard gameBoard, int x, int y);

}
