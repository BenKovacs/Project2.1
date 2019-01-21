package gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

public class Launcher {
	
	public static final int MAX = 8;

	public static void main(String[] args) {
		int start_i = Integer.parseInt(args[0]);
		int start_j = Integer.parseInt(args[1]);
		System.out.println("Starting othello launcher");
		for(int i = start_i; i <= MAX; i++) {
			for(int j = start_j; j <= MAX; j++) {
				try {
					String cmd = "java -jar othello.jar " + i + " " + j;
					System.out.println(cmd);;
					Process process = Runtime.getRuntime().exec(cmd);
					
					
					BufferedReader in = new BufferedReader(new InputStreamReader(process.getErrorStream()));
				    String line;
				    while ((line = in.readLine()) != null) {
				        System.out.println(line);
				    }
				    process.waitFor();
				    System.out.println("ok!");

				    in.close();
				    
				    
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}


}
