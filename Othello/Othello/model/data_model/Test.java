package model.data_model;

import gui.BoardPanel;
import gui.RightPanel;
import model.Constants;
import model.GameBoard;
import model.OthelloBoard;
import model.Rolit;
import model.player.MonteCarloTreeSearch;
import model.player.RolitMonteCarloTreeSearch;

import java.awt.*;
import java.util.*;

public class Test {

    public static void main(String[] args) {
//        int testsPerValue = 10; //change this to how many runs you want, even 1 run will take pretty long.
//        int[] iterations = new int[]{5000, 20000, 50000};
//        int[] exploParams = new int[]{10, 20, 50};
//        test1(testsPerValue, iterations);
//        test2(testsPerValue, exploParams);

//        OthelloBoard game = new OthelloBoard(8,8);
//        Random random = new Random();
//        while (!game.isGameOver()) {
//            ArrayList<Point> validMoves = game.getValidMoves();
//            Point move = validMoves.get(random.nextInt(validMoves.size()));
//            game.play((int) move.getX(), (int) move.getY());
//            game.printBoard();
//        }

        RolitMonteCarloTreeSearch rmcts = new RolitMonteCarloTreeSearch();
        Rolit game = new Rolit(8,8, new int[]{Rolit.RED, Rolit.YELLOW, Rolit.GREEN, Rolit.BLUE});
        while (!game.isGameOver()) {
            Point move = rmcts.getMove(game);
            game.printBoard();
            rmcts.printData();
            game.play((int) move.getX(), (int) move.getY());
        }

//        RolitMonteCarloTreeSearch rmcts = new RolitMonteCarloTreeSearch();
//        Rolit game = new Rolit(8,8, new int[]{Rolit.BLACK, Rolit.WHITE});
//        while (!game.isGameOver()) {
//            Point move = rmcts.getMove(game);
//            game.printBoard();
//            rmcts.printData();
//            game.play((int) move.getX(), (int) move.getY());
//        }
    }

//    public static void test1(int testRuns, int[] iterations) {
//        OthelloBoard game = new OthelloBoard(8,8);
//        BoardPanel boardPanel = new BoardPanel(new GameBoard(8,8), new RightPanel());
//
//        MonteCarloTreeSearch mcts = new MonteCarloTreeSearch(boardPanel, Constants.BLACK, 0, 10000);
//        MonteCarloTreeSearch mcts2 = new MonteCarloTreeSearch(boardPanel, Constants.BLACK, 0, 10000);
//
//        for (int iteration : iterations) {
//            mcts.setIterations(iteration);
//            for (int i = 1; i <= testRuns; i++) {
//                while (!game.isGameOver()) {
//                    if (game.getTurn() == OthelloBoard.BLACK) {
////                        game.printBoard();
//                        Point move = mcts.getMove(game);
////                        mcts.printData();
//                        if (move != null) {
//                            game.play((int) move.getX(), (int) move.getY());
//                        }
//                    } else {
////                        game.printBoard();
//                        Point move = mcts2.getMove(game);
////                        mcts2.printData();
//                        if (move != null) {
//                            game.play((int) move.getX(), (int) move.getY());
//                        }
//                    }
//                }
//                String s1 = "[GAME#: " + String.format("%02d", i) + "]\t";
//                String s2 = "[ITERATIONS: " + mcts.getIterations() + "]\t";
//                String s3 = "[EXPLORATION PARAMETER: " + String.format("%02d", mcts.getExploreParam()) + "]\t";
//                String s4 = "[RESULT: " + game.playerResult(OthelloBoard.BLACK) + "]";
//                System.out.println(s1 + s2 + s3 + s4);
//                game.restart();
//            }
//            System.out.println();
//        }
//    }
//
//    public static void test2(int testRuns, int[] exploParams) {
//        OthelloBoard game = new OthelloBoard(8,8);
//        BoardPanel boardPanel = new BoardPanel(new GameBoard(8,8), new RightPanel());
//
//        MonteCarloTreeSearch mcts = new MonteCarloTreeSearch(boardPanel, Constants.BLACK, 0, 10000);
//        MonteCarloTreeSearch mcts2 = new MonteCarloTreeSearch(boardPanel, Constants.BLACK, 0, 10000);
//
//        for (int exploParam : exploParams) {
//            mcts.setExploreParam(exploParam);
//            for (int i = 1; i <= testRuns; i++) {
//                while (!game.isGameOver()) {
//                    if (game.getTurn() == OthelloBoard.BLACK) {
////                        game.printBoard();
//                        Point move = mcts.getMove(game);
////                        mcts.printData();
//                        if (move != null) {
//                            game.play((int) move.getX(), (int) move.getY());
//                        }
//                    } else {
////                        game.printBoard();
//                        Point move = mcts2.getMove(game);
////                        mcts2.printData();
//                        if (move != null) {
//                            game.play((int) move.getX(), (int) move.getY());
//                        }
//                    }
//                }
//                String s1 = "[GAME#: " + String.format("%02d", i) + "]\t";
//                String s2 = "[ITERATIONS: " + mcts.getIterations() + "]\t";
//                String s3 = "[EXPLORATION PARAMETER: " + String.format("%02d", mcts.getExploreParam()) + "]\t";
//                String s4 = "[RESULT: " + game.playerResult(OthelloBoard.BLACK) + "]";
//                System.out.println(s1 + s2 + s3 + s4);
//                game.restart();
//            }
//            System.out.println();
//        }
//    }

}
