package model.data_model;

public class Evaluation {


    static int stabilityHeuristic(int x, int y){
        //heuristic that gives amount based on the position of the flipped disks on the table
        if(x>=3 && x<= 4 && y>=3 && y<= 4) return 1;
        else{
            if(x>=2 && x<= 5 && y>=2 && y<= 5) return 3;
            else{
                if(x>=1 && x<= 6 && y>=1 && y<= 6) return 8;
                else{
                    if(x>=0 && x<= 7 && y>=0 && y<= 7)return 16;
                }
            }
        }
        return 1;
    }
}
