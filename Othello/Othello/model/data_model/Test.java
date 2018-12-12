package model.data_model;

import gui.BoardPanel;
import gui.RightPanel;
import model.Constants;
import model.GameBoard;
import model.OthelloBoard;
import model.player.MonteCarloTreeSearch;
//import model.player.SuperMonteCarloTreeSearch2;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Test {

    public static void main(String[] args) {
        MCTSvsMCTS();
//        MCTS1(30);
    }

    //Standard mcts vs mcts
    public static void MCTSvsMCTS() {
        OthelloBoard game = new OthelloBoard(8,8);
        BoardPanel boardPanel = new BoardPanel(new GameBoard(8,8), new RightPanel());

        int width = 8;
        int height = 8;
        //Paste your result here to continue
        int[][] scoreBoard = new int[][]{
            {5029, 5017, 4991, 5009, 5027, 5011, 4991, 5035},
            {5027, 4979, 5019, 4981, 4995, 4994, 4975, 5003},
            {4987, 5021, 4975, 5020, 4987, 5000, 4995, 5003},
            {5017, 4973, 5025, 5000, 5000, 4988, 4989, 5011},
            {5007, 5005, 4968, 5000, 5000, 5016, 4988, 5009},
            {5011, 5008, 4995, 4975, 5018, 4986, 5007, 5005},
            {4995, 4986, 4989, 5009, 4975, 4992, 4975, 5007},
            {5028, 5004, 4999, 5019, 5006, 5007, 5002, 5037}
        };
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                scoreBoard[row][column] = 5000;
            }
        }
        int gamePlayed = 0;
        ArrayList<Point> blackMoves = new ArrayList<Point>();
        ArrayList<Point> whiteMoves = new ArrayList<Point>();

        int runtime = 0; //Min runtime in milliseconds
        int iterations = 5000; //Min iterations

        MonteCarloTreeSearch mcts = new MonteCarloTreeSearch(boardPanel, Constants.BLACK, runtime, iterations);
        MonteCarloTreeSearch mcts2 = new MonteCarloTreeSearch(boardPanel, Constants.WHITE, runtime, iterations);

        while (true) {
            while (true) {
                if (game.getTurn() == OthelloBoard.BLACK) {
//                game.printBoard();
                    Point move = mcts.getMove(game);
//                mcts.printData();
                    if (move != null) {
                        game.play((int) move.getX(), (int) move.getY());
                        blackMoves.add(move);
                    } else {
                        break;
                    }
                } else {
//                game.printBoard();
                    Point move = mcts2.getMove(game);
//                mcts2.printData();
                    if (move != null) {
                        game.play((int) move.getX(), (int) move.getY());
                        whiteMoves.add(move);
                    } else {
                        break;
                    }
                }
            }
            if (game.gameResult() == "BLACK WIN") {
                for (Point move : blackMoves)
                    scoreBoard[(int)move.getX()][(int)move.getY()]++;
                for (Point move : whiteMoves)
                    scoreBoard[(int)move.getX()][(int)move.getY()]--;
            } else if (game.gameResult() == "WHITE WIN") {
                for (Point move : blackMoves)
                    scoreBoard[(int)move.getX()][(int)move.getY()]--;
                for (Point move : whiteMoves)
                    scoreBoard[(int)move.getX()][(int)move.getY()]++;
            }
            gamePlayed++;
            System.out.println("GAME NO.: " +gamePlayed);
            for (int row = 0; row < height; row++) {
                for (int column = 0; column < width; column++) {
                    System.out.print(scoreBoard[row][column] + " ");
                }
                System.out.println();
            }
            System.out.println();
            blackMoves.clear();
            whiteMoves.clear();
            game.restart();
        }
    }

    //Exploration param test, exploration param of BLACK keep increasing
    public static void MCTS1(int testRuns) {
        OthelloBoard game = new OthelloBoard(8,8);
        BoardPanel boardPanel = new BoardPanel(new GameBoard(8,8), new RightPanel());

        int runtime = 0; //Min runtime in milliseconds
        int iterations = 10000; //Min iterations

        MonteCarloTreeSearch mcts = new MonteCarloTreeSearch(boardPanel, Constants.BLACK, runtime, iterations);
        MonteCarloTreeSearch mcts2 = new MonteCarloTreeSearch(boardPanel, Constants.WHITE, runtime, iterations);

        for (int i = 1; i <= testRuns; i++) {
            while (true) {
                if (game.getTurn() == OthelloBoard.BLACK) {
//                    game.printBoard();
                    Point move = mcts.getMove(game);
                    mcts.printData();
                    if (move != null) {
                        game.play((int) move.getX(), (int) move.getY());
                    } else {
                        break;
                    }
                } else {
//                    game.printBoard();
                    Point move = mcts2.getMove(game);
                    mcts2.printData();
                    if (move != null) {
                        game.play((int) move.getX(), (int) move.getY());
                    } else {
                        break;
                    }
                }
            }
            String s1 = "[GAME#: " + String.format("%02d", i) + "]\t";
            String s2 = "[ITERATIONS: " + mcts.getIterations() + "]\t";
            String s3 = "[EXPLORATION PARAMETER: " + String.format("%02d", mcts.getExploreParam()) + "]\t";
            String s4 = "[RESULT: " + game.playerResult(OthelloBoard.BLACK) + "]";
            System.out.println(s1 + s2 + s3 + s4);

//            mcts.setExploreParam(mcts.getExploreParam() + 1);
            game.restart();
        }
    }

}
