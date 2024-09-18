package org.lab5;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RegisterController {

	static Socket s;

	@FXML
	private Button gotoLoginB;

	@FXML
	private Button registerButton;

	@FXML
	private PasswordField registerPassword;

	@FXML
	private PasswordField registerRepeat;

	@FXML
	private TextField registerUsername;

	@FXML
	void gotoLogin(ActionEvent event) {
		FXMLLoader l =  new FXMLLoader(getClass().getResource("Login.fxml"));
		Stage s = (Stage) registerButton.getScene().getWindow();
		try{
			s.setScene(new Scene(l.load()));
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}
	void registerResult(String res){
		if(res.equals("Success")){
			JOptionPane.showMessageDialog(null, "You have successfully registered!");
			gotoLogin(null);
		}else{
			JOptionPane.showMessageDialog(null, "Error!");
			registerPassword.setText("");
			registerRepeat.setText("");
			registerUsername.setText("");
		}
	}
	static ActionEvent e;
	Node n;
	@FXML
	void register(ActionEvent event) {
		ClientIO.rg = this;
		n = (Node) event.getSource();
		e = event;
		if(registerPassword.getText().equals(registerRepeat.getText())){
			ClientIO.write("Register " + registerUsername.getText() + " " + registerPassword.getText());
		}else{
			JOptionPane.showMessageDialog(null, "Passwords do not match!");
		}
		/*if(registerPassword.getText().equals(registerRepeat.getText())) {
			PrintWriter out = null;
			try{
				out = new PrintWriter(s.getOutputStream(),true);
				BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				out.println("Register "+registerUsername.getText()+" "+registerPassword.getText());
				String res = null;
				while(res == null){
					res = in.readLine();
				}
				if(res.equals("Success")){
					JOptionPane.showMessageDialog(null, "You have successfully registered!");
					gotoLogin(event);
				}else{
					JOptionPane.showMessageDialog(null, "Error!");
					registerPassword.setText("");
					registerRepeat.setText("");
					registerUsername.setText("");
				}
			}catch(IOException e){
				throw new RuntimeException(e);
			}
		}else{
			JOptionPane.showMessageDialog(null, "Passwords do not match!");
		}*/
	}

}
