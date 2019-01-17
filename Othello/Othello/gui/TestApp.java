package gui;

import model.data_model.Constants;
import model.data_model.GameBoard;
import model.player.*;


public class TestApp {
    private static TestApp testApp;
    private BoardPanel boardPanel;

    private GameBoard gameBoard;
    private RightPanel rPanel;

    private String p1 = "minmax";
    private String p2 = "minmax";
    private String p3;
    private String p4;
    private int numPlayers = 2;
    private int depthLevel = 4;

    public static void main(String[] args){
        testApp = new TestApp();

    }

    TestApp(){
        initialize();
    }


    public static TestApp getSingleton() {
        return testApp;
    }

    private void initialize() {


        // The settings Dialog is Modal so this thread will pause after setting
        // it to be visible until it is set invisible by the dialog.

        // Create AIs or human players
        int playerCount = numPlayers;
        Player[] playerList = new Player[playerCount];

        // create the gameBoard/panel
        gameBoard = new GameBoard(8, 8);

        rPanel = new RightPanel();
        boardPanel = new BoardPanel(gameBoard, rPanel);

        int turn;
        if (playerCount == 2){
            turn = Constants.WHITE;
        } else {
            turn = Constants.RED;
        }
        if (p1.equalsIgnoreCase("human")) {
            playerList[0] = new HumanPlayer(boardPanel, turn);
        } else if (p1.equalsIgnoreCase("minmax")) {
            MinMaxPlayer ai = new MinMaxPlayer(boardPanel, turn, depthLevel);
            playerList[0] = ai;
            ai.start();
        } else if (p1.equalsIgnoreCase("greedy")) {
            GreedyPlayer ai = new GreedyPlayer(boardPanel, turn);
            ai.start();
            playerList[0] = ai;
        } else if (p1.equalsIgnoreCase("random")) {
            RandomPlayer ai = new RandomPlayer(boardPanel, turn);
            ai.start();
            playerList[0] = ai;
        } else if (p1.equalsIgnoreCase("mcts")) {
            int runtime = 2000; //min runtime in millisecs
            int iterations = 0; //min iterations
            MonteCarloTreeSearch ai = new MonteCarloTreeSearch(boardPanel, turn, runtime, iterations);
            playerList[0] = ai;
            ai.start();
        } else if (p1.equalsIgnoreCase("smcts")) {
            int runtime = 3000; //min runtime in millisecs
            int iterations = 0; //min iterations
            SuperMonteCarloTreeSearch ai = new SuperMonteCarloTreeSearch(boardPanel, turn, runtime, iterations);
            playerList[0] = ai;
            ai.start();
        }

        if (playerCount == 2){
            turn = Constants.BLACK;
        } else {
            turn = Constants.GREEN;
        }
        if (p2.equalsIgnoreCase("human")) {
            playerList[1] = new HumanPlayer(boardPanel, turn);
        } else if (p2.equalsIgnoreCase("minmax")) {
            MinMaxPlayer ai = new MinMaxPlayer(boardPanel, turn, depthLevel);
            playerList[1] = ai;
            ai.start();
        } else if (p2.equalsIgnoreCase("greedy")) {
            GreedyPlayer ai = new GreedyPlayer(boardPanel, turn);
            playerList[1]  = ai;
            ai.start();
        } else if (p2.equalsIgnoreCase("random")) {
            RandomPlayer ai = new RandomPlayer(boardPanel, turn);
            playerList[1] = ai;
            ai.start();
        } else if (p2.equalsIgnoreCase("mcts")) {
            int runtime = 2000; //min runtime in millisecs
            int iterations = 0; //min iterations
            MonteCarloTreeSearch ai = new MonteCarloTreeSearch(boardPanel, turn, runtime, iterations);
            ai.start();
            playerList[1] = ai;
        } else if (p2.equalsIgnoreCase("smcts")) {
            int runtime = 3000; //min runtime in millisecs
            int iterations = 0; //min iterations
            SuperMonteCarloTreeSearch ai = new SuperMonteCarloTreeSearch(boardPanel, turn, runtime, iterations);
            playerList[1] = ai;
            ai.start();
        }

        if (playerCount > 2){
            turn = Constants.BLUE;
            if (p3.equalsIgnoreCase("human")) {
                playerList[2] = new HumanPlayer(boardPanel, turn);
            } else if (p3.equalsIgnoreCase("minmax")) {
                MinMaxPlayer ai = new MinMaxPlayer(boardPanel, turn, depthLevel);
                playerList[2] = ai;
                ai.start();
            } else if (p3.equalsIgnoreCase("greedy")) {
                GreedyPlayer ai = new GreedyPlayer(boardPanel, turn);
                playerList[2]  = ai;
                ai.start();
            } else if (p3.equalsIgnoreCase("random")) {
                RandomPlayer ai = new RandomPlayer(boardPanel, turn);
                playerList[2] = ai;
                ai.start();
            } else if (p3.equalsIgnoreCase("mcts")) {
                int runtime = 2000; //min runtime in millisecs
                int iterations = 0; //min iterations
                MonteCarloTreeSearch ai = new MonteCarloTreeSearch(boardPanel, turn, runtime, iterations);
                ai.start();
                playerList[2] = ai;
            } else if (p3.equalsIgnoreCase("smcts")) {
                int runtime = 3000; //min runtime in millisecs
                int iterations = 0; //min iterations
                SuperMonteCarloTreeSearch ai = new SuperMonteCarloTreeSearch(boardPanel, turn, runtime, iterations);
                playerList[2] = ai;
                ai.start();
            }
        }

        if (playerCount > 3){
            turn = Constants.YELLOW;
            if (p4.equalsIgnoreCase("human")) {
                playerList[3] = new HumanPlayer(boardPanel, turn);
            } else if (p4.equalsIgnoreCase("minmax")) {
                MinMaxPlayer ai = new MinMaxPlayer(boardPanel, turn, depthLevel);
                playerList[3] = ai;
                ai.start();
            } else if (p4.equalsIgnoreCase("greedy")) {
                GreedyPlayer ai = new GreedyPlayer(boardPanel, turn);
                playerList[3]  = ai;
                ai.start();
            } else if (p4.equalsIgnoreCase("random")) {
                RandomPlayer ai = new RandomPlayer(boardPanel, turn);
                playerList[3] = ai;
                ai.start();
            } else if (p4.equalsIgnoreCase("mcts")) {
                int runtime = 2000; //min runtime in millisecs
                int iterations = 0; //min iterations
                MonteCarloTreeSearch ai = new MonteCarloTreeSearch(boardPanel, turn, runtime, iterations);
                ai.start();
                playerList[3] = ai;
            } else if (p4.equalsIgnoreCase("smcts")) {
                int runtime = 3000; //min runtime in millisecs
                int iterations = 0; //min iterations
                SuperMonteCarloTreeSearch ai = new SuperMonteCarloTreeSearch(boardPanel, turn, runtime, iterations);
                playerList[3] = ai;
                ai.start();
            }
        }


        for (int i = 0; i < playerList.length; i++) {
            System.out.println("Player " + i + " is: " + playerList[i] + playerList[i].getColor());

        }
        gameBoard.setPlayerList(playerList);
        rPanel.setPlayerList(playerList);
        boardPanel.setGameBoard(gameBoard);
    }

}
