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
//        MCTSvsMCTS();

        ArrayList<String> testData = MCTS1(30);
        for (String s : testData)
            System.out.println(s);
    }

    //Standard mcts vs mcts
    public static void MCTSvsMCTS() {
        OthelloBoard game = new OthelloBoard(8,8);
        BoardPanel boardPanel = new BoardPanel(new GameBoard(8,8), new RightPanel());

        int runtime = 0; //Min runtime in milliseconds
        int iterations = 10000; //Min iterations

        MonteCarloTreeSearch mcts = new MonteCarloTreeSearch(boardPanel, Constants.BLACK, runtime, iterations);
        MonteCarloTreeSearch mcts2 = new MonteCarloTreeSearch(boardPanel, Constants.WHITE, runtime, iterations);

        while (true) {
            if (game.getTurn() == OthelloBoard.BLACK) {
                game.printBoard();
                Point move = mcts.getMove(game);
                mcts.printData();
                if (move != null) {
                    game.play((int) move.getX(), (int) move.getY());
                } else {
                    break;
                }
            } else {
                game.printBoard();
                Point move = mcts2.getMove(game);
                mcts2.printData();
                if (move != null) {
                    game.play((int) move.getX(), (int) move.getY());
                } else {
                    break;
                }
            }
        }
    }

    //Exploration param test, exploration param of BLACK keep increasing
    public static ArrayList<String> MCTS1(int testRuns) {
        ArrayList<String> testData = new ArrayList<String>();
        OthelloBoard game = new OthelloBoard(8,8);
        BoardPanel boardPanel = new BoardPanel(new GameBoard(8,8), new RightPanel());

        int runtime = 0; //Min runtime in milliseconds
        int iterations = 10000; //Min iterations

        MonteCarloTreeSearch mcts = new MonteCarloTreeSearch(boardPanel, Constants.BLACK, runtime, iterations);
        MonteCarloTreeSearch mcts2 = new MonteCarloTreeSearch(boardPanel, Constants.WHITE, runtime, iterations);

        for (int i = 1; i <= testRuns; i++) {
            while (true) {
                if (game.getTurn() == OthelloBoard.BLACK) {
                    game.printBoard();
                    Point move = mcts.getMove(game);
                    mcts.printData();
                    if (move != null) {
                        game.play((int) move.getX(), (int) move.getY());
                    } else {
                        break;
                    }
                } else {
                    game.printBoard();
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
            testData.add(s1 + s2 + s3 + s4);

            mcts.setExploreParam(mcts.getExploreParam() + 1);
            game.restart();
        }

        return testData;
    }

}
