package org.lab5;

import java.io.*;
import java.util.Scanner;

public class ServerCheckUser{
	static boolean isRegistered(String User, String pass){
		try{
			Scanner sc = new Scanner(new FileInputStream("src/main/Users.csv"));
			while(sc.hasNextLine()){
				String[] data = sc.nextLine().split(", ");
				if(data[0].equals(User) && data[1].equals(pass)){
					return true;
				}
			}
		}catch(FileNotFoundException e){
			throw new RuntimeException(e);
		}
		return false;
	}
	static void register(String User, String pass){
		try{
			BufferedWriter pw = new BufferedWriter(new FileWriter("src/main/Users.csv", true));
			pw.write(User + ", " + pass + "\n");
			pw.close();
		}catch(FileNotFoundException e){
			throw new RuntimeException(e);
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}
}
