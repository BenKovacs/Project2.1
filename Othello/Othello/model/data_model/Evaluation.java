package model.data_model;
import model.Constants;

import static model.Constants.BLACK;
import static model.Constants.WHITE;

public class Evaluation {


    public static int ringWeightsHeuristic(int x, int y, int[][] board, int turn){
        //heuristic that gives amount based on the position of the flipped disks on the table
        if(x>=3 && x<= 4 && y>=3 && y<= 4) return 1;
        else{
            if(x>=2 && x<= 5 && y>=2 && y<= 5) return 8;
            else{
                if(x>=1 && x<= 6 && y>=1 && y<= 6) return 3;
                else{
                    if(x>=0 && x<= 7 && y>=0 && y<= 7)return 16;
                }
            }
        }
        return 1;
    }

    public static int staticWeightsHeuristic(int x, int y, int[][] board, int turn){
        int[][] weights = {{4, -3, 2, 2, 2, 2, -3, 4},
                {-3, -4, -1, -1, -1, -1, -4, -3},
                {2, -1, 1, 0, 0, 1, -1, 2},
                {2, -1, 0, 1, 1, 0, -1, 2},
                {2, -1, 0, 1, 1, 0, -1, 2},
                {2, -1, 1, 0, 0, 1, -1, 2},
                {-3, -4, -1, -1, -1, -1, -4, -3},
                {4, -3, 2, 2, 2, 2, -3, 4}};
        int total = 0;
        //int turn = board[x][y];
        //if(turn == BLACK){turn=WHITE;}else{turn=BLACK;}
        int i;
        int j;
        for (i = 0; i < 8; i++){
            for (j = 0; j < 8; j++){
                if(board[i][j] == turn){
                    total += weights[i][j];
                }
            }

        }
        return total;
    }
}
