package org.lab5;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

import static org.lab5.RegisterController.e;

public class LoginController{

	static Socket s;
	public Button gotoRegisterB;

	@FXML
	private Button loginButton;

	@FXML
	private PasswordField password;

	@FXML
	private TextField username;


	@FXML
	void gotoRegister(ActionEvent event){
		FXMLLoader l =  new FXMLLoader(getClass().getResource("Register.fxml"));
		Node n = (Node) event.getSource();
		Stage s = (Stage) n.getScene().getWindow();
		try{
			s.setScene(new Scene(l.load()));
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}
	Node n;
	void loginrezult(String res){
		if(res.equals("Fail")){
			username.setText("");
			password.setText("");
			JOptionPane.showMessageDialog(null, "Login Failed");
		}else{
			MainWindowController.name = username.getText();
			Stage s = (Stage) gotoRegisterB.getScene().getWindow();
			Parent p = null;
			try{
				p = FXMLLoader.load(getClass().getResource("Main.fxml"));
			}catch(IOException ex){
				throw new RuntimeException(ex);
			}
			Scene sc = new Scene(p);
			s.setScene(sc);
			s.show();
		}
	}

	@FXML
	void login(ActionEvent event) {
		ClientIO.lg = this;
		n = (Node) event.getSource();
		ClientIO.write("Login " + username.getText() + " " + password.getText());
	}
}
