package org.lab5;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class LAB5Application extends Application {
    Socket s;
    @Override
    public void start(Stage stage) throws IOException {
        s = new Socket(InetAddress.getByName(null),65535);
        s.setSoTimeout(0);
        LoginController.s = s;
        MainWindowController.s = s;
        RegisterController.s = s;
        ClientIO.socket = s;
        ClientIO.out = new PrintWriter(s.getOutputStream(), true);
        ClientIO.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        /*Task<Void> t = new Task<Void>() {
            @Override
            protected Void call() throws Exception{
                ClientIO.run();
                return null;
            }

        };
        new Thread(t).start();*/
        new Thread(new ClientIO()).start();
        FXMLLoader fxmlLoader = new FXMLLoader(LAB5Application.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("5 lab");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        //Platform.runLater(new ClientIO());
    }
    void closeSocket(){
        try{
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            out.println("Close");
            s.close();
        }catch(IOException e){
	        throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        launch();
    }
}