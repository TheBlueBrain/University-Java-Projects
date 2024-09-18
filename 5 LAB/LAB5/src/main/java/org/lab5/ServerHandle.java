package org.lab5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class ServerHandle implements Runnable{
	private Socket socket;
	String inRoom;
	boolean room;
	public ServerHandle(Socket s){
		socket = s;
		try{
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}

	PrintWriter out;
	void register(String n, String p){
		if(!ServerCheckUser.isRegistered(n, p)){
			ServerCheckUser.register(n, p);
			out.println("REGISTER Success");
		}else{
			out.println("REGISTER Fail");
		}
	}
	void sendMessage(String msg){
		out.println(msg);
	}
	void login(String n, String p){

		if(ServerCheckUser.isRegistered(n, p)){
			out.println("LOGIN Success");
			ServerMain.onl.put( n, this);
		}else{
			out.println("LOGIN Fail");
		}
		name = n;
		for(ServerHandle h : ServerMain.l){
			h.sendMessage("NEWUSER " + n);
		}
	}
	void getR(){
		String res = "";
		for(Iterator<String> i = ServerMain.room.keySet().iterator(); i.hasNext();){
			res += i.next();
			if(i.hasNext()){
				res += ", ";
			}
		}
		out.println("ROOMLIST " + res);
	}
	void getP(){
		String res = "";
		for(Iterator<String> iterator = ServerMain.onl.keySet().iterator(); iterator.hasNext(); ){
			String u = iterator.next();
			res += u;
			if(iterator.hasNext()){
				res += ", ";
			}
		}
		//res = res.substring(0, res.length()-2);
		out.println("PEOPLELIST " + res);
	}
	void sendP(String[] data){
		String d="", n = data[1];
		ServerHandle h = ServerMain.onl.get(n);
		for(int i = 3; i < data.length; i++){
			d+=data[i];
			if(i!=data.length-1){
				d+=" ";
			}
		}
		ServerMessage.addMessage(name, data[1], false, d);
		h.sendMessage("DIRMESSAGE " + data[2] + " " + d);
	}
	String name;
	void sendR(String[] data){
		String d="", n = data[1];
		ArrayList<ServerHandle> h = ServerMain.room.get(n);
		for(int i = 3; i < data.length; i++){
			d+=data[i];
			if(i!=data.length-1){
				d+=" ";
			}
		}
		ServerMessage.addMessage(name, inRoom, true, d);
		for(ServerHandle h2 : h){
			h2.sendMessage("ROOMMESSAGE " + name + " "+d);
		}
	}
	void newR(String n){
		ServerMain.room.put(n, new ArrayList<>());
		ServerRooms.addRoom(n);
		for(ServerHandle h : ServerMain.onl.values()){
			h.sendMessage("NEWROOM " + n);
		}
	}

	void joinRoom(String n){
		if(inRoom != null && !inRoom.equals("_") ){
			ServerMain.room.get(inRoom).remove(this);
		}
		if(n.equals("_")){
			inRoom= n;
		}else{
			if(ServerMain.room.get(n) == null){
				ServerMain.room.put(n, new ArrayList<>());
			}
			ServerMain.room.get(n).add(this);
			inRoom = n;
		}
	}
	BufferedReader in;
	@Override
	public void run(){
		try{
			while(true){

				//PrintWriter out =
				String line = in.readLine();
				if(line == null){
					continue;
				}
				String[] tokens = line.split(" ");
				if(tokens[0].equals("Register")){
					register(tokens[1], tokens[2]);
				}else if(tokens[0].equals("Login")){
					login(tokens[1], tokens[2]);
				}else if(tokens[0].equals("SendR")){
					sendR(tokens);
				}else if(tokens[0].equals("SendP")){
					sendP(tokens);
				}else if(tokens[0].equals("NewR")){
					newR(tokens[1]);
				}else if(tokens[0].equals("GetR")){
					getR();
				}else if(tokens[0].equals("GetP")){
					getP();
				}else if(tokens[0].equals("Close")){
					socket.close();
					return;
				}else if(tokens[0].equals("JoinR")){
					joinRoom(tokens[1]);
				}else if(tokens[0].equals("GetRM")){
					sendMessage("LOADMSG " + ServerMessage.getMessages(name, inRoom, true));
				}else if(tokens[0].equals("GetPM")){
					sendMessage("LOADMSG " + ServerMessage.getMessages(name, tokens[1], false));
				}
				Thread.sleep(250);
			}
		}catch(IOException e){
			throw new RuntimeException(e);
		}catch(InterruptedException e){
			throw new RuntimeException(e);
		}finally{
			try{
				socket.close();
			}catch(IOException e){
				throw new RuntimeException(e);
			}
		}
	}
}
