package org.lab6;

import javafx.application.Platform;
import javafx.scene.control.ProgressBar;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileRead implements Runnable{

	private static final int LINE_AMOUNT =1000;
	private int current = 0;
	ProgressBar bar;

	String filename;
	Data d;
	Scanner br;
	boolean readH = false;
	public FileRead(String name, Data dat, ProgressBar bar){
		filename = name;
		d = dat;
		this.bar = bar;
		try{
			br = new Scanner(new FileReader(filename));
		}catch(FileNotFoundException e){
			throw new RuntimeException(e);
		}
	}
	ArrayList<String> fields = new ArrayList<>();
	@Override
	public void run(){
		while(br.hasNextLine()){
			String line = br.nextLine();
			if(!readH){
				readH = true;
				String[] tokens = line.split(",");
				for(String x : tokens){
					fields.add(x);
				}
				continue;
			}
			String[] tokens = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1); //regex Mistika
			ArrayList<String> newFields = new ArrayList<>();
			for(String x : tokens){
				newFields.add(x);
			}
			d.add(newFields);
			current++;
			Platform.runLater(() -> bar.setProgress((double) current /LINE_AMOUNT));

			Data.newData = true;
			try{
				Thread.sleep(10);
			}catch(InterruptedException e){
				throw new RuntimeException(e);
			}
		}
	}
}
