package model.data_model;

import java.util.LinkedList;
import java.util.Random;

public class Evaluation {

    public static double Heuristic1 = 1;
    public static double Heuristic2 = 0;

    public static double getEvaluation(int x, int y, int[][] board, int turn){
        return Heuristic1 * staticWeightsHeuristic(x, y, board, turn) + Heuristic2 * stabilityHeuristic(x, y, board, turn);
    }

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
//        int[][] weights ={{200 , -100, 100,  50,  50, 100, -100,  200},
//                        {-100, -200, -50, -50, -50, -50, -200, -100},
//                        {100 ,  -50, 100,   0,   0, 100,  -50,  100},
//                        {50  ,  -50,   0,   0,   0,   0,  -50,   50},
//                        {50  ,  -50,   0,   0,   0,   0,  -50,   50},
//                        {100 ,  -50, 100,   0,   0, 100,  -50,  100},
//                        {-100, -200, -50, -50, -50, -50, -200, -100},
//                        {200 , -100, 100,  50,  50, 100, -100,  200}};
        int[][] weights =
            {{4, -3, 2, 2, 2, 2, -3, 4},
            {-3, -4, -1, -1, -1, -1, -4, -3},
            {2, -1, 1, 0, 0, 1, -1, 2},
            {2, -1, 0, 1, 1, 0, -1, 2},
            {2, -1, 0, 1, 1, 0, -1, 2},
            {2, -1, 1, 0, 0, 1, -1, 2},
            {-3, -4, -1, -1, -1, -1, -4, -3},
            {4, -3, 2, 2, 2, 2, -3, 4}};
        int total = 0;
        int turnCount = 0;

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
                    turnCount++;
                }
            }

        }
        Random random = new Random();
        return total * (random.nextDouble() / 50 + 0.98) / (turnCount * 2);
    }

    public static double stabilityHeuristic(int x, int y, int[][] board, int turn) {
        double counter = 0;
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if(isStable(j,i, board)) {
                    counter++;
                }
            }
        }
        Random random = new Random();
        return counter/64.0 * (random.nextDouble() / 50 + 0.98);
    }

    public static boolean isStable(final int x, final int y, int[][] board) {
        LinkedList<String> list = new LinkedList<String>();
        int uniqId = new Random().nextInt(100000);
        int color = board[y][x];

        if (color != Constants.BLACK && color != Constants.WHITE) {
            // System.out.println("Empty square");
            return false;
        }
        // check horizontal

        int operatingX = x; // go left

        boolean leftOpposite = false;
        boolean leftEmpty = false;
        while (operatingX > 0) {
            operatingX--;
            if (board[y][operatingX] != Constants.WHITE && board[y][operatingX] != Constants.BLACK) { // if empty on the left -> try to find opposite or
                // empty on the right side
                leftEmpty = true;
                break;
            } else {
                if (board[y][operatingX] != color) { // if opposite color on the left-> try to find
                    // empty on the right
                    // side
                    leftOpposite = true;
                    break;
                }
            }
        }

        // go right boolean
        boolean rightOpposite = false;
        boolean rightEmpty = false;
        operatingX = x;
        while (operatingX < board[0].length - 1) {
            operatingX++;
            if (board[y][operatingX] != Constants.WHITE && board[y][operatingX] != Constants.BLACK) {
                rightEmpty = true;
                list.add(uniqId + " Found right square: " + operatingX);
                break;
            } else if (board[y][operatingX] != color) {
                rightOpposite = true;
                break;
            }
        }
        if (leftOpposite && rightEmpty) {
            return false;
        }
        if (rightOpposite && leftEmpty) {
            return false;
        }
        if (leftEmpty && rightEmpty) {
            return false;
        }

        // check vertical
        int operatingY = y;
        // go top
        boolean topOpposite = false;
        boolean topEmpty = false;
        while (operatingY > 0) {
            operatingY--;
            if (board[operatingY][x] != Constants.BLACK && board[operatingY][x] != Constants.WHITE) {
                // if empty on the left -> try to find opposite or empty on the
                // right side
                list.add(uniqId + " top -> empty at Y:" + operatingY + " ");
                topEmpty = true;
                break;
            } else if (board[operatingY][x] != color) {
                // if opposite color on the left -> try to find empty on the
                // right side
                list.add(uniqId + " top -> opposite at Y:" + operatingY + " " + board[operatingY][x]);
                topOpposite = true;
                break;
            }
        }
        list.add(uniqId + " top -> stopping at Y:" + operatingY + " ");
        // go bottom
        boolean bottomOpposite = false;
        boolean bottomEmpty = false;
        operatingY = y;
        while (operatingY < board[0].length - 1) {
            operatingY++;
            if (board[operatingY][x] != Constants.WHITE && board[operatingY][x] != Constants.BLACK) {
                bottomEmpty = true;
                // System.out.println(uniqId + " Found right square: " +
                // operatingA);
                list.add(uniqId + " bottom -> empty at Y:" + operatingY + " ");
                break;
            } else if (board[operatingY][x] != color) {
                bottomOpposite = true;
                list.add(uniqId + " bottom -> opposite at Y:" + operatingY + " ");
                break;
            }
        }
        list.add(uniqId + " bottom -> stopping at Y:" + operatingY);

        if (topOpposite && bottomEmpty) {
            return false;
        }
        if (bottomOpposite && topEmpty) {
            return false;
        }
        if (topEmpty && bottomEmpty) {
            return false;
        }
        // check diagonal increase
        boolean bottomLeftOpposite = false;
        boolean bottomLeftEmpty = false;
        operatingY = y;
        operatingX = x;
        while (operatingY > 0 && operatingX > 0) {
            operatingY--;
            operatingX--;
            if (board[operatingY][operatingX] != Constants.BLACK && board[operatingY][operatingX] != Constants.WHITE) {
                // if empty on the left -> try to find opposite or empty on the
                // right side
                list.add(uniqId + " bottomLeft -> empty at x,y:" + operatingX + "," + operatingY);
                bottomLeftEmpty = true;
                break;
            } else if (board[operatingY][operatingX] != color) {
                // if opposite color on the left -> try to find empty on the
                // right side
                list.add(uniqId + " bottomLeft -> opposite at x,y:" + operatingX + "," + operatingY);
                bottomLeftOpposite = true;
                break;
            }
        }
        list.add(uniqId + " bottomLeft -> stopping at x,y " + operatingX + ", " + operatingY);
        boolean topRightOpposite = false;
        boolean topRightEmpty = false;
        operatingY = y;
        operatingX = x;
        while (operatingY < board[0].length - 1 && operatingX < board.length - 1) {
            operatingY++;
            operatingX++;
            if (board[operatingY][operatingX] != Constants.WHITE && board[operatingY][operatingX] != Constants.BLACK) {
                topRightEmpty = true;
                // System.out.println(uniqId + " Found right square: " +
                // operatingA);
                list.add(uniqId + " bottom -> empty at Y:" + operatingY + " ");
                break;
            } else if (board[operatingY][operatingX] != color) {
                topRightOpposite = true;
                list.add(uniqId + " bottom -> opposite at Y:" + operatingY + " ");
                break;
            }
        }
        list.add(uniqId + " bottom -> stopping at Y:" + operatingY);
        if (topRightOpposite && bottomLeftEmpty) {
            return false;
        }
        if (bottomLeftOpposite && topRightEmpty) {
            return false;
        }
        if (topRightEmpty && bottomLeftEmpty) {
            return false;
        }
        //
        boolean topLeftOpposite = false;
        boolean topLeftEmpty = false;
        operatingY = y;
        operatingX = x;
        while (operatingY < board.length - 1 && operatingX > 0) {
            operatingY++;
            operatingX--;
            if (board[operatingY][operatingX] != Constants.BLACK && board[operatingY][operatingX] != Constants.WHITE) {
                // if empty on the left -> try to find opposite or empty on the
                // right side
                list.add(uniqId + " topLeft -> empty at x,y:" + operatingX + "," + operatingY);
                topLeftEmpty = true;
                break;
            } else if (board[operatingY][operatingX] != color) {
                // if opposite color on the left -> try to find empty on the
                // right side
                list.add(uniqId + " topLeft -> opposite at x,y:" + operatingX + "," + operatingY);
                topLeftOpposite = true;
                break;
            }
        }
        list.add(uniqId + " bottomLeft -> stopping at x,y " + operatingX + ", " + operatingY);
        boolean bottomRightOpposite = false;
        boolean bottomRightEmpty = false;
        operatingY = y;
        operatingX = x;
        while (operatingX < board[0].length - 1 && operatingY > 0) {
            operatingY--;
            operatingX++;
            if (board[operatingY][operatingX] != Constants.WHITE && board[operatingY][operatingX] != Constants.BLACK) {
                bottomRightEmpty = true;
                // System.out.println(uniqId + " Found right square: " +
                // operatingA);
                list.add(uniqId + " bottomRight -> empty at Y:" + operatingY + " ");
                break;
            } else if (board[operatingY][operatingX] != color) {
                bottomRightOpposite = true;
                list.add(uniqId + " bottomRight -> opposite at Y:" + operatingY + " ");
                break;
            }
        }
        list.add(uniqId + " bottom -> stopping at Y:" + operatingY);
        if (bottomRightOpposite && topLeftEmpty) {
            return false;
        }
        if (topLeftOpposite && bottomRightEmpty) {
            return false;
        }
        if (bottomRightEmpty && topLeftEmpty) {
            return false;
        }
        // check diagonal decrease
        // System.out.println(uniqId + ": checking " + x + "," + y);
        for (String s : list) {
            //System.out.println(s);
        }
        //System.out.println(uniqId + " Found a stable square on: " + x + "," + y);
        return true;
    }
}
