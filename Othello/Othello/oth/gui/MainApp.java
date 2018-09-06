package oth.gui;


import oth.model.GameBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;


public class MainApp implements ActionListener {

	private JFrame frame;
	private int numHuman;
//	private int numAI;
//	private ArrayList<Player> players;
	private GameBoard gb;
	private int currentPlayer = 0;
	private int depth = 0;
	private int startPlayer = 0;
	private int aiDepth;
	private int algoPlayer0 = 1;
	private int algoPlayer1 = 0;
	private int algoPlayer2 = 2;
	private int algoPlayer3 = 2;
	//You can select an algorithm for each player here. 0 = Greedy
	//													1 = MiniMax
	//													2 = MCTS
	
	private boolean debugMode;
	private JSplitPane splitPane;
	private JPanel panel;
	private JLabel lblStartPlayerCurrent;
	private BoardPanel bp;
	private LinkedList<Point> points  ;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainApp window = new MainApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainApp(){
		initialize();
		Settings settings = new Settings();
		// The settings Dialog is Modal so this thread will pause after setting it to be visible until it is set invisible by the dialog.
		settings.setVisible(true);
		if (settings.isCancelSelected()){
			System.exit(0);
		}
		// TODO add default Close Operation.(when we press exit the game opens, not good).
		numHuman = settings.getNumberOfHumanPlayers();
//		numAI = settings.getNumberOfAIPlayers();
//		numKangas = settings.getNumberOfKangaroos();
		aiDepth = settings.getDepthLevel();
		points = new LinkedList<>(); 
//		players = new ArrayList<Player>() ;
		debugMode = settings.isDebugMode();
		

		initializeBoard();
		
	}

	private void initializeBoard() {
		gb = new GameBoard(16,14);
//		createPlayers();

		frame.getContentPane().setLayout(new BorderLayout());
		
		splitPane = new JSplitPane();
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		bp = new BoardPanel(gb);
		splitPane.setLeftComponent(bp);
		
//		mp = new MiniMaxPanel(this);
//		splitPane.setRightComponent(mp);
		splitPane.setDividerLocation(300);
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 30));
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		lblStartPlayerCurrent = new JLabel("Start Player: Current Player:");
		panel.add(lblStartPlayerCurrent);
		
//		mp.setDebugMode(debugMode);

	}
		
		//placement
		private Point randomPoint(List<Point> points)
	    {
			
			
	        int x = (int)(Math.random()*8 + 8);
	        int y = (int)(Math.random()*6 + 8);
	        Point temp = new Point(x, y);
	        if(points.size()==0)
	            {
	                System.out.println(x + " " + y);
	                return temp;
	            }
	       
	        for(int j = 0; j < points.size(); j++)
	        {
	            if(temp.x == points.get(j).x && temp.y == points.get(j).y)
	                {
	            		x = (int)(Math.random()*8 + 8);
	            		y = (int)(Math.random()*6 + 8);
	            		temp = new Point(x, y);
	            		j = 0;
	            		
	                }
	        }
	        System.out.println(x + " " + y);
	        return temp;
	    }
	   
//	    private void createPlayers() {
//	       players = new ArrayList<Player>();
//	       
//	        List<Point> points = new ArrayList<Point>();
//	       
//	        for(int i = 0; i < 40; i++)
//	        {  
//	            Point temp = randomPoint(points);
//	            points.add(temp);  
//	        }
//		
//
//		for (int i=0;i<numHuman; i++){
//			Player p = new HumanPlayer();
//			players.add(p);
//			for (int j=0; j<numKangas; j++){
//				Point pt = (Point) points.get(points.size()-1);
//				points.remove(points.size()-1);
//				
//				new Kangaroo(p, pt.x, pt.y);
//			}
//			gb.addPlayer(p);
//		}
//		for (int i=0;i<numAI; i++){
//			Player p = new AIPlayer();
//			players.add(p);
//			for (int j=0; j<numKangas; j++){
//				Point pt = (Point) points.get(points.size()-1);
//				points.remove(points.size()-1);
//				
//				System.out.println("New kanga made for player "+ i + " at coords " + pt.x + ", " + pt.y);
//				new Kangaroo(p, pt.x, pt.y);
//			}
//			gb.addPlayer(p);
//		}
//
//		MiniMax.getInstance().setCurrentPlayerTurn(this.gb.getPlayers().get(currentPlayer));
//		MiniMax.getInstance().initialise(this.gb);
//	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				try{
					splitPane.setDividerLocation(frame.getWidth()-400);
				}catch(Exception e){}
			}
		});
		frame.setBounds(100, 100, 927, 473);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

//	@Override
//	public void actionPerformed(ActionEvent e) {
//		switch(e.getActionCommand()){
//		case "turn":
////			doExecuteTurn();
//			break;
//		case "calculate":
//			doCalculateMoves();
//			break;
//		/*case "move":
//			if(algoPlayer0 == 0 && currentPlayer == 0) doMoveG();
//			else if(algoPlayer0 == 1 && currentPlayer == 0) doMove();
//			else if(algoPlayer0 == 2 && currentPlayer == 0) doMov();
//			else if(algoPlayer1 == 0 && currentPlayer == 1) doMoveG();
//			else if(algoPlayer1 == 1 && currentPlayer == 1) doMove();
//			else if(algoPlayer1 == 2 && currentPlayer == 1) doMov();
//			else if(algoPlayer2 == 0 && currentPlayer == 2) doMoveG();
//			else if(algoPlayer2 == 1 && currentPlayer == 2) doMove();
//			else if(algoPlayer2 == 2 && currentPlayer == 2) doMov();
//			else if(algoPlayer3 == 0 && currentPlayer == 3) doMoveG();
//			else if(algoPlayer3 == 1 && currentPlayer == 3) doMove();
//			else if(algoPlayer3 == 2 && currentPlayer == 3) doMov();
//			
//			break;*/
//		}
//	}

//	private void doExecuteTurn() {
//
//
//		Runnable r = new Runnable() {
//			
//			@Override
//			public void run() {
////				MiniMax.getInstance().reset(gb);
////				MiniMax.getInstance().setCurrentPlayerTurn(gb.getPlayers().get(currentPlayer));

				long start = Calendar.getInstance().getTimeInMillis();
//				for (int i=0; i< aiDepth; i++){
//					try {
//						ProgressBarPanel.getInstance().initialise();
//						Player p = players.get(currentPlayer);
//						p.move();
//						depth ++;
//						currentPlayer ++;
//						if (currentPlayer == players.size()){
//							currentPlayer = 0;
//						}
//						lblStartPlayerCurrent.setText("Player Turn: "+startPlayer+" Current Player: "+currentPlayer);
//						long end = Calendar.getInstance().getTimeInMillis();
//						mp.setState(TurnState.MovesFound, end-start);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//				long end = Calendar.getInstance().getTimeInMillis();
//				System.out.println("Finished calcing moves in "+(end-start)+" ms");
//				mp.setState(TurnState.DepthComplete, end-start);
//				
//				if(algoPlayer0 == 0 && currentPlayer == 0) doMove();
//				else if(algoPlayer0 == 1 && currentPlayer == 0) doMove();
//				else if(algoPlayer0 == 2 && currentPlayer == 0) doMov();
//				else if(algoPlayer1 == 0 && currentPlayer == 1) doMove();
//				else if(algoPlayer1 == 1 && currentPlayer == 1) doMove();
//				else if(algoPlayer1 == 2 && currentPlayer == 1) doMov();
//				else if(algoPlayer2 == 0 && currentPlayer == 2) doMove();
//				else if(algoPlayer2 == 1 && currentPlayer == 2) doMove();
//				else if(algoPlayer2 == 2 && currentPlayer == 2) doMov();
//				else if(algoPlayer3 == 0 && currentPlayer == 3) doMove();
//				else if(algoPlayer3 == 1 && currentPlayer == 3) doMove();
//				else if(algoPlayer3 == 2 && currentPlayer == 3) doMov();
//			}
//		};
//		
//		new Thread(r).start();
//	}

//	private void doCalculateMoves() {
		
//		ProgressBarPanel.getInstance().initialise();
//		Player p = players.get(currentPlayer);
//
//		Runnable r = new Runnable() {
//
//			@Override
//			public void run() {
//				try {
//					long start = Calendar.getInstance().getTimeInMillis();
//					p.move();
//					long end = Calendar.getInstance().getTimeInMillis();
//					System.out.println("Finished calcing moves in "+(end-start)+" ms");
//					depth ++;
//					currentPlayer ++;
//					if (currentPlayer == players.size()){
//						currentPlayer = 0;
//					}
//					lblStartPlayerCurrent.setText("Player Turn: "+(startPlayer+1)+" Current Player: "+(currentPlayer+1));
//					if (currentPlayer == 0 && algoPlayer0 == 0 && depth == 1)
//					{
//						mp.setState(TurnState.DepthComplete, end-start);
//					}
//					if (currentPlayer == 1 && algoPlayer1 == 0 && depth == 1)
//					{
//						mp.setState(TurnState.DepthComplete, end-start);
//					}
//					if (currentPlayer == 2 && algoPlayer2 == 0 && depth == 1)
//					{
//						mp.setState(TurnState.DepthComplete, end-start);
//					}
//					if (currentPlayer == 3 && algoPlayer3 == 0 && depth == 1)
//					{
//						mp.setState(TurnState.DepthComplete, end-start);
//					}
//					
//					if (depth == aiDepth){
//						mp.setState(TurnState.DepthComplete, end-start);
//					}
//					
//					else{
//						mp.setState(TurnState.MovesFound, end-start);
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		};
//		new Thread(r).start();
//	}
//	
//	 
//	private void doMov(){
//		
//
//		MonteCarloTreeSearch mcts = new MonteCarloTreeSearch(2) ;
//		String x = " " ;
//		 for(int i = 0; i < gb.getKangaroos().size(); i++){
//				x = x + " pre MCTS  " + gb.getKangaroos().get(i).getX() + " x and y " + gb.getKangaroos().get(i).getY()  + "of the " + i + "th kanga";
//				System.lineSeparator();
//			}
//		
//		//this.gb = gb.clone(mcts.findNextMove(gb, currentPlayer));
//		this.gb = mcts.findNextMove(gb, currentPlayer);
//		currentPlayer++;
//		if (currentPlayer == players.size()){
//			System.out.println("currentplayer = 0");
//			currentPlayer = 0;
//		}
//		bp.setGameBoard(gb);
//		bp.repaint();
//		System.out.println(x);
//		for(int i = 0; i < gb.getKangaroos().size(); i++){
//			System.out.println( "post MCTS  " + gb.getKangaroos().get(i).getX() + " x and y " + gb.getKangaroos().get(i).getY()  + "of the " + i + "th kanga" );
//			System.lineSeparator();
//		}
//		
//		for(int i = 0; i < gb.getPlayers().size(); i++){
//			for(int j= 0 ; j < gb.getPlayers().get(i).getKangaroos().size(); j++){
//				System.out.println("kannga " + j  + " from player " + i + " at position " + gb.getPlayers().get(i).getKangaroos().get(j).getX() + "   " + gb.getPlayers().get(i).getKangaroos().get(j).getY());
//			}
//		}
//		System.out.println("repainted loool");
//		
//	}
//	       /* List<LegalMove> availablePositions = this.gb.getEmptyPositions(1);
//	        int totalPossibilities = availablePositions.size();
//	        int selectRandom = (int) (Math.random() * ((totalPossibilities - 1) + 1));
//	        this.gb.move(availablePositions.get(selectRandom).kangaroo,availablePositions.get(selectRandom).to.x,availablePositions.get(selectRandom).to.y);
//	        System.out.println("Random move positions " + availablePositions.get(selectRandom).from + " " + availablePositions.get(selectRandom).to);
//	        bp.repaint();
//	        if(lapping(availablePositions.get(selectRandom).from.x, availablePositions.get(selectRandom).from.y, availablePositions.get(selectRandom).to.x,availablePositions.get(selectRandom).to.y)== true){
//	        	availablePositions.get(selectRandom).kangaroo.incrementLapCounter();
//			}
//	        if(unLapping(availablePositions.get(selectRandom).kangaroo.getX(), availablePositions.get(selectRandom).kangaroo.getY(), availablePositions.get(selectRandom).to.x,availablePositions.get(selectRandom).to.y)== true){
//	        	availablePositions.get(selectRandom).kangaroo.decrementLapCounter();
//			}
//	        if (availablePositions.get(selectRandom).kangaroo.getLapCounter() > 2) {
//				// Done the laps
//				// Remove the kanga from the game
//				gb.removeKangarooFromPlay(availablePositions.get(selectRandom).kangaroo);
//				//bp.repaint();
//				
//			} 
//	        System.out.println("Random move positions " + availablePositions.get(selectRandom).from + " " + availablePositions.get(selectRandom).to);
//	        System.out.println("kanga lapcounter = " + availablePositions.get(selectRandom).kangaroo.getLapCounter());
//		
//	}
//	 public boolean lapping(int x, int y, int tx, int ty) {
//			if (y>7 && ty>7){
//				if (x>7 && tx<8){
//					return true;
//				}
//			}
//			return false;
//		}
//	    
//	    public boolean unLapping(int x, int y, int tx, int ty) {
//			if (y>7 && ty>7){
//				if (tx>7 && x<8){
//					return true;
//				}
//			}
//			return false;
//		}
//	*/
	
	private void doMove() {
		
//		MiniMaxNode bestMove = MiniMax.getInstance().getBestMove();
//		//MCTS mcts = new MCTS(gb.getboard(), currentPlayer) ;
//		//MCTSmove bestMove = mcts.selectAction(); 
//		
//		Kangaroo k = bestMove.move.kangaroo;
//		int tx = bestMove.move.to.x;
//		int ty = bestMove.move.to.y;
//		
//		if(MiniMax.getInstance().lapping(k.getX(), k.getY(), tx, ty)== true){
//			k.incrementLapCounter();
//		}
//		if(MiniMax.getInstance().unLapping(k.getX(), k.getY(), tx, ty)== true){
//			k.decrementLapCounter();
//		}

//		if (k.getLapCounter() > 2) {
//			// Done the laps
//			// Remove the kanga from the game
//			gb.removeKangarooFromPlay(k);
//			bp.repaint();
//			return;
//		} 
//		
//		gb.move(k, tx, ty);
//		
//		// TODO if the bestMove is a jump move then place a referee in the originating square
//		bp.repaint();
		
//		mp.setState(TurnState.Idle,0);
//		startPlayer ++;
//		depth = 0;
//		if (startPlayer == players.size()){
//			startPlayer = 0;
//		}
//		currentPlayer = startPlayer;
//		lblStartPlayerCurrent.setText("Player Turn: "+startPlayer+" Current Player: "+currentPlayer);
//		
//		MiniMax.getInstance().reset(this.gb);
//		MiniMax.getInstance().setCurrentPlayerTurn(this.gb.getPlayers().get(currentPlayer));
	}

	public void showBoard(GameBoard board) {
		bp.showBoard(board);
	}

	public void resetBoard() {
		bp.showBoard(gb);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

