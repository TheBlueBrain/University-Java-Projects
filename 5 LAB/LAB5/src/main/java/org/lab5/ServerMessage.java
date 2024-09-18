package org.lab5;
import java.io.*;
import java.util.Scanner;
public class ServerMessage{
	static synchronized String getMessages(String Sender, String Recipient, boolean isRoom){
		try{
			Scanner scanner;
			if(isRoom){
				scanner = new Scanner(new File("src/main/RoomMessages.csv"));
			}else{
				scanner = new Scanner(new File("src/main/PersonalMessages.csv"));
			}
			String rez = "";
			while(scanner.hasNextLine()){
				String message = scanner.nextLine();
				String[] tok = message.replaceAll("\n","").split(", ");
				if(Recipient.equals(tok[0]) && isRoom){
					rez += tok[1] + ", ";
					for(int i = 2; i < tok.length; i++){
						rez += tok[i];
						if(i!=tok.length-1){
							rez+= ", ";
						}
					}
					rez +=  "/";
				}else if(!isRoom && ((Recipient.equals(tok[0]) && Sender.equals(tok[1])) || (Recipient.equals(tok[1]) && Sender.equals(tok[0])))){
					rez+= Sender + ", ";
					for(int i = 2; i < tok.length; i++){
						rez += tok[i];
						if(i!=tok.length-1){
							rez+= ", ";
						}
					}
					rez += "/";
				}
			}
			return rez;
		}catch(FileNotFoundException e){
			throw new RuntimeException(e);
		}
	}
	public static synchronized void addMessage(String Sender, String Recipient, boolean isRoom, String message){
		BufferedWriter wr;
		try{
			if(isRoom){
				wr = new BufferedWriter(new FileWriter("src/main/RoomMessages.csv", true));
			}else{
				wr = new BufferedWriter(new FileWriter("src/main/PersonalMessages.csv", true));
			}
			wr.write(Recipient + ", " + Sender + ", " + message +"\n");
			wr.close();
		}catch(IOException e){
			throw new RuntimeException(e);
		}

	}
}
