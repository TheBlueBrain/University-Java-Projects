package org.lab5;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javax.swing.*;
import java.net.Socket;

public class MainWindowController{
	static String name;
	static Socket s;
	Boolean room;
	String messageRecipient;

	@FXML
	private ListView<String> Users;

	@FXML
	private ListView<String> chatWindow;

	@FXML
	private Button createRoomB;

	@FXML
	private TextField messageInput;

	@FXML
	private Button refreshRoomsB;

	@FXML
	private Label roomName;

	@FXML
	private ListView<String> rooms;

	void addRoom(String Room){
		rooms.getItems().add(Room);
	}

	@FXML
	void createRoom(ActionEvent event) {
		ClientIO.write("NewR "+ JOptionPane.showInputDialog("Enter room name"));
	}
	@FXML
	void sendMessage(ActionEvent event) {

		if(room  == null){
			messageInput.setText("");
			return;
		}
		if(room){
			ClientIO.write("SendR " + Rec + " " + name + " " + messageInput.getText());
		}else{
			ClientIO.write("SendP " + Rec + " " + name + " " + messageInput.getText());
		}
		if(!room){
			chatWindow.getItems().add(name + ": " + messageInput.getText());
		}
		messageInput.setText("");
	}
	void setUsers(String res){
		//System.out.println(res);
		String[] users = res.split(", ");
		Users.getItems().clear();
		Users.getItems().addAll(users);
		Users.setOnMouseClicked(mouseEvent -> {
			room = false;
			messageRecipient = Users.getSelectionModel().getSelectedItem();
			roomName.setText("User: " + messageRecipient);
			chatWindow.getItems().clear();
			//getMessages(messageRecipient, true);
			Rec = messageRecipient;
			ClientIO.write("JoinR _");
			ClientIO.write("GetPM " + Rec);
		});
	}
	static String Rec;
	void setRooms(String res){
		String[] Rooms = res.split(", ");
		rooms.getItems().clear();
		rooms.getItems().addAll(Rooms);
		rooms.setOnMouseClicked(mouseEvent -> {
			room = true;
			messageRecipient = rooms.getSelectionModel().getSelectedItem();
			roomName.setText("Room: " + messageRecipient);
			chatWindow.getItems().clear();
			//getMessages(messageRecipient, false);
			Rec = messageRecipient;
			ClientIO.write("JoinR " + Rec);
			ClientIO.write("GetRM");
		});
	}
	public void initialize() {
		ClientIO.mw = this;
		ClientIO.write("GetR");
		ClientIO.write("GetP");
	}

	public void newPrivateMessage(String roommessage){
		if(room){
			return;
		}
		if(!roommessage.startsWith(Rec + " ")){
			return;
		}
		roommessage.replace(Rec + " ", "");

		chatWindow.getItems().add(roommessage.replace(" ", ": "));
	}

	public void newRoomMessage(String roommessage){
		if(!room){
			return;
		}
		chatWindow.getItems().add(roommessage.replace(" ", ": "));
	}
	public void getMessages(String name, boolean room){
		if(room){
			ClientIO.write("GetRM " + name);
		}else{
			ClientIO.write("GetPM " + name);
		}
	}
	public void loadMessages(String s){
		String[] Messages = s.split("/");
		chatWindow.getItems().clear();
		for(String m : Messages){
			m = m.replace(", ", ": ");
			if(!m.isEmpty()){
				chatWindow.getItems().add(m);
			}
		}
	}

	public void addUser(String newuser){
		Users.getItems().add(newuser);
	}
}
