package org.lab5;
import java.io.*;
import java.util.Scanner;
public class ServerRooms{
	static synchronized String getRooms(){
		try{
			Scanner scanner = new Scanner(new File("src/main/Rooms.csv"));
			String rez = "";
			while(scanner.hasNextLine()){
				rez += scanner.nextLine();
			}
			return rez;
		}catch(FileNotFoundException e){
			throw new RuntimeException(e);
		}
	}
	static synchronized void addRoom(String room){
		try{
			FileWriter printWriter = new FileWriter("src/main/Rooms.csv", true);
			BufferedWriter bufferedWriter = new BufferedWriter(printWriter);
			bufferedWriter.write(", " + room);
			bufferedWriter.close();

		}catch(FileNotFoundException e){
			throw new RuntimeException(e);
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}
}
