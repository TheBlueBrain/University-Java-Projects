package org.lab5;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
public class ClientIO implements Runnable {
	public static Socket socket;
	static PrintWriter out;
	static LoginController lg;
	static RegisterController rg;
	static MainWindowController mw;
	static BufferedReader in;
	public ClientIO() {
		try{
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}
	static void write(String m){
		out.println(m);
	}
	public void run(){
		while(true){
			try{
				String sdata = null;
				while(sdata == null){
					sdata = in.readLine();
				}
				parse(sdata);
			}catch(IOException e){
				throw new RuntimeException(e);
			}
			try{
				Thread.sleep(250);
			}catch(InterruptedException e){
				throw new RuntimeException(e);
			}
		}
	}
	static void parse(String s){
		if(s.startsWith("LOGIN ")){
			Platform.runLater(() -> {
				lg.loginrezult(s.replace("LOGIN ", ""));
			});
		}else if(s.startsWith("REGISTER ")){
			Platform.runLater( () -> {
				rg.registerResult(s.replace("REGISTER ", ""));
			});
		}else if(s.startsWith("NEWROOM ")){
			Platform.runLater( () -> {
				mw.addRoom(s.replace("NEWROOM ", ""));
			});
		}else if(s.startsWith("ROOMMESSAGE ")){
			Platform.runLater(() -> {
				mw.newRoomMessage(s.replace("ROOMMESSAGE ",""));
			});
		}else if(s.startsWith("DIRMESSAGE ")){
			Platform.runLater(() -> {
				mw.newPrivateMessage(s.replace("DIRMESSAGE ",""));
			});
		}else if(s.startsWith("PEOPLELIST ")){
			Platform.runLater(() -> {
				mw.setUsers(s.replace("PEOPLELIST ", ""));
			});
		}else if(s.startsWith("ROOMLIST ")){
			Platform.runLater(() -> {
				mw.setRooms(s.replace("ROOMLIST ", ""));
			});
		}else if(s.startsWith("LOADMSG ")){
			Platform.runLater(() -> {
				mw.loadMessages(s.replace("LOADMSG ", ""));
			});
		}else if(s.startsWith("NEWUSER ")){
			if(mw!=null){
				Platform.runLater(() -> {
					mw.addUser(s.replace("NEWUSER ", ""));
				});
			}
		}else{
			System.out.println("ERROR");
		}
	}

	public void send(String s){
		out.println(s);
	}
}
