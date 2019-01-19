package model.data_model;

import java.io.*;

/**
 * Evaluation Learning Algorithm
 *
 * I'm trying to build an evaluation table based on the final scores of our plays
 *
 * It will work passively at the end of each game without problems.
 * REMEMBER TO PUSH YOUR FILE .TXT THAT IT WILL BE CREATED
 * If the table will work, we will implement it with success otherwise fck
 *
 */
public class ELA {
    static int calling;

    //just taking the name to gain data over the internet among all of us
    private String name;

    public ELA(){
        this.name = System.getProperty("user.name");
    }

    public void saveData(int[][] board){
        //just in case the method is called twice
        if(calling>0)return;

        int p1=0;

        for(int i=0;i<board.length; i++){
            for(int j=0;j<board[0].length; j++){
                if(board[i][j]== 0) p1++;
            }
        }

        //if both did the same amount than go out
        if(p1==32) return;

        //needed for the board of the txt file
        int[][] oldBoard = new int[board.length][board[0].length];

        //read the file if there is one
        File file = new File(name +".txt");
        boolean old = false;
        if(file.exists() && !file.isDirectory()) {
            BufferedReader reader = null;

            try{
                reader = new BufferedReader(new FileReader(file));
                String boardText = reader.readLine();
                String[] squares = boardText.split(",");

                for(int i=0;i<board.length; i++){
                    for(int j=0;j<board[0].length; j++){
                        oldBoard[i][j] = Integer.parseInt(squares[i+j*board.length]);
                    }
                }

                old = true;

            }catch (FileNotFoundException e) {
            } catch (IOException e) { }
            catch (NullPointerException e){}
            finally {
                try {
                    // Close the writer regardless of what happens...
                    reader.close();
                } catch (IOException e) { }
            }
        }


        //re-write the file otherwise summing the values
        BufferedWriter writer = null;
        try {
            file.createNewFile();
            String newBoard = "";

            for(int i=0;i<board.length; i++){
                for(int j=0;j<board[0].length; j++){
                    if( board[i][j]==0){
                        if(p1>32){
                            if(!old)newBoard +="1,";
                            else newBoard += (oldBoard[i][j] + 1) +",";
                        } else{
                            if(!old)newBoard += "-1,";
                            else newBoard += (oldBoard[i][j] - 1) +",";
                        }
                    }else{
                        if(p1<32){
                            if(!old)newBoard += "1,";
                            else newBoard += (oldBoard[i][j] + 1) +",";
                        } else{
                            if(!old)newBoard +="-1,";
                            else newBoard += (oldBoard[i][j] - 1) +",";
                        }
                    }
                }
            }

            writer = new BufferedWriter(new FileWriter(file));
            writer.write(newBoard);

            System.out.println("Ela updated");

        } catch (IOException e) { System.out.println("error writing file");}
        finally {
            try {
                // Close the writer regardless of what happens...
                writer.close();
            } catch (Exception e) { }
        }

        calling++;
    }
}