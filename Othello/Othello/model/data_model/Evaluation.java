package model.data_model;

import java.util.Random;

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

    public static double staticWeightsHeuristic(int x, int y, int[][] board, int turn){
        int[][] weights ={{200 , -100, 100,  50,  50, 100, -100,  200},
                        {-100, -200, -50, -50, -50, -50, -200, -100},
                        {100 ,  -50, 100,   0,   0, 100,  -50,  100},
                        {50  ,  -50,   0,   0,   0,   0,  -50,   50},
                        {50  ,  -50,   0,   0,   0,   0,  -50,   50},
                        {100 ,  -50, 100,   0,   0, 100,  -50,  100},
                        {-100, -200, -50, -50, -50, -50, -200, -100},
                        {200 , -100, 100,  50,  50, 100, -100,  200}};
        int total = 0;

        //COMMENTED OUT BECAUSE RESULTED IN POOR MINIMAX PERFORMING VS GREEDY
/*
        //if corners are taken, that corner of the board looses all value.
        if(board[0][0] != 0){
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j <= 3; j++) {
                    weights[i][j] = 0;
                }
            }
        }

        if(board[0][7] != 0){
            for (int i = 0; i < 3; i++) {
                for (int j = 4; j <= 7; j++) {
                    weights[i][j] = 0;
                }
            }
        }

        if(board[7][0] != 0){
            for (int i = 5; i < 8; i++) {
                for (int j = 0; j <= 3; j++) {
                    weights[i][j] = 0;
                }
            }
        }

        if(board[7][7] != 0){
            for (int i = 5; i < 8; i++) {
                for (int j = 4; j <= 7; j++) {
                    weights[i][j] = 0;
                }
            }
        }*/

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
        Random random = new Random();
        return total * (random.nextDouble() / 50 + 0.98);
    }
}
