package org.lab5;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerMain{
	static ArrayList<ServerHandle> l = new ArrayList<>();
	static Map<String, ServerHandle> onl = new HashMap<>();;
	public static Map<String, ArrayList<ServerHandle>> room = new HashMap<>();
	public static void main(String[] args){
		String raw = ServerRooms.getRooms();
		String[] rooms = raw.replaceAll("\n","").split(", ");
		for(String r : rooms){
			room.put(r, new ArrayList<>());
		}
		try{
			ServerSocket serverSocket = new ServerSocket(65535, 0, InetAddress.getByName(null));
			serverSocket.setSoTimeout(0);
			while(true){
				Socket socket = serverSocket.accept();
				ServerHandle s = new ServerHandle(socket);
				l.add(s);
				new Thread(s).start();
			}
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}
}
