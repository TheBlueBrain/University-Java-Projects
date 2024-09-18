package org.lab6;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Popup;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Stream;

public class HelloController {

	@FXML
	private TableView<ArrayList<String>> dataTable;

	@FXML
	private TableColumn<ArrayList<String>, String> birthCol;

	@FXML
	private TableColumn<ArrayList<String>, String> counCol;

	@FXML
	private TableColumn<ArrayList<String>, String> domCol;

	@FXML
	private TableColumn<ArrayList<String>, String> emailCol;

	@FXML
	private TableColumn<ArrayList<String>, String> fnCol;

	@FXML
	private TableColumn<ArrayList<String>, String> genderCol;

	@FXML
	private TableColumn<ArrayList<String>, String> idCol;

	@FXML
	private TableColumn<ArrayList<String>, String> lnCol;

	@FXML
	private ComboBox<String> errorList;

	@FXML
	private MenuItem filterDate;

	@FXML
	private MenuItem numberSort;

	@FXML
	private ProgressBar prgs1;

	@FXML
	private ProgressBar prgs2;

	@FXML
	private ProgressBar prgs3;

	static int selectedColumn;
	volatile static boolean isAsc = false;
	static String startss;
	@FXML
	private MenuItem textSort;
	Data data;
	FileRead f1, f2, f3;
	@FXML
	public void initialize() {

		data = new Data();
		f1 = new FileRead("MOCK_DATA1.csv", data, prgs1);
		f2 = new FileRead("MOCK_DATA2.csv", data, prgs2);
		f3 = new FileRead("MOCK_DATA3.csv", data, prgs3);
		idCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(0)));
		emailCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(3)));
		fnCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(1)));
		genderCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(4)));
		birthCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(7)));
		lnCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(2)));
		domCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(6)));
		counCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(5)));

		Platform.runLater(()-> {
			new Timer().schedule(new TimerTask(){
				@Override
				public void run(){
					if(Data.newData==true){
						dataTable.getItems().clear();
						Stream<ArrayList<String>> s = data.getStream();
						Platform.runLater(() -> {
							try{
								s.forEach(row -> dataTable.getItems().add(row));
							}catch(Exception e){
								errorList.getItems().add("Update error");
							}
						});
						Data.newData = false;
						//System.out.println("UPDATE");
					}
					try{
						Thread.sleep(500);
					}catch(InterruptedException e){
						throw new RuntimeException(e);
					}
					//System.out.println("TIMER");
				}
			}, 0, 500);
		});

		new Thread(f1).start();
		new Thread(f2).start();
		new Thread(f3).start();
	}
	Popup text = new Popup(), number = new Popup(), alpha = new Popup();
	@FXML
	void selectColumnForNumber(ActionEvent event) {
		FXMLLoader l = new FXMLLoader(getClass().getResource("NumberWindow.fxml"));
		//boolean err = false;
		//l.setController(c);
		try{
			number.getContent().add((Parent) l.load());
		}catch(IOException e){
			throw new RuntimeException(e);
		}
		if(!number.isShowing()){
			//DateController c = new DateController(from, to);
			HelloController.isAsc = false;
			number.show(dataTable.getScene().getWindow());
		}else{
			dataTable.getItems().clear();
			Stream<ArrayList<String>> s = data.getStream();

			s.sorted((c1, c2) -> {
				try{
					Integer i1 = Integer.parseInt(c1.get(selectedColumn));
					Integer i2 = Integer.parseInt(c2.get(selectedColumn));

				if(isAsc){
					return i1 - i2;
				}else{
					return i2 - i1;
				}
				}catch(Exception e){
					errorList.getItems().add(e.getMessage());
					return 0;
				}
			}).forEach(row -> {
				Platform.runLater(() -> {
					dataTable.getItems().add(row);
				});
			});

			number.hide();
		}
	}

	@FXML
	void sortByAlpha(ActionEvent event) {
		FXMLLoader l = new FXMLLoader(getClass().getResource("AlphaWindow.fxml"));
		//boolean err = false;
		//l.setController(c);
		try{
			alpha.getContent().add((Parent) l.load());
		}catch(IOException e){
			throw new RuntimeException(e);
		}
		if(!alpha.isShowing()){
			//DateController c = new DateController(from, to);
			alpha.show(dataTable.getScene().getWindow());
			isAsc = false;
		}else{
			//System.out.println("isAsc: " + isAsc);
			dataTable.getItems().clear();
			Stream<ArrayList<String>> s = data.getStream();

			s.sorted((c1, c2) -> {
				try{
					String s1 = c1.get(selectedColumn).toLowerCase();
					String s2 = c2.get(selectedColumn).toLowerCase();

					if(isAsc){
						return s1.compareTo(s2);
					}else{
						return s2.compareTo(s1);
					}
				}catch(Exception e){
					errorList.getItems().add(e.getMessage());
					return 0;
				}
			}).forEach(row -> {
				Platform.runLater(() -> {
					dataTable.getItems().add(row);
				});
			});

			alpha.hide();
		}
	}

	@FXML
	void selectColumnForText(ActionEvent event) {
		FXMLLoader l = new FXMLLoader(getClass().getResource("TextWindow.fxml"));
		//l.setController(c);
		try{
			text.getContent().add((Parent) l.load());
		}catch(IOException e){
			throw new RuntimeException(e);
		}
		if(!text.isShowing()){
			//DateController c = new DateController(from, to);
			text.show(dataTable.getScene().getWindow());
		}else{
			dataTable.getItems().clear();
			Stream<ArrayList<String>> s = data.getStream();
			s.filter((c) -> {
				if(startss == null || startss.isEmpty()){
					return true;
				}
				return c.get(selectedColumn).startsWith(startss);
			}).forEach(row -> {
				Platform.runLater(() -> {
					dataTable.getItems().add(row);
				});
			});
			text.hide();
		}
	}

	static LocalDate from;
	static LocalDate to;
	Popup p = new Popup();
	@FXML
	void showDateSelection(ActionEvent event) {
		//System.out.println("DATE SELECTED");
		FXMLLoader l = new FXMLLoader(getClass().getResource("DateWindow.fxml"));
		//l.setController(c);
		try{
			p.getContent().add((Parent) l.load());
		}catch(IOException e){
			throw new RuntimeException(e);
		}
		if(!p.isShowing()){
			//DateController c = new DateController(from, to);
			p.show(dataTable.getScene().getWindow());
		}else{
			/*System.out.println(from);
			System.out.println(to);*/
			dataTable.getItems().clear();
			Stream<ArrayList<String>> s = data.getStream();
			s.filter( (c) -> {
				String date = c.get(7);
				LocalDate test = null;
				try{
					test = LocalDate.parse(date);
				}catch(Exception e){
					errorList.getItems().add(e.getMessage());
				}
				if(to == null || from == null || to.isBefore(from) || test == null){
					return true;
				}
				if(test.isBefore(to) && test.isAfter(from)){
					return true;
				}
				return false;
			}).forEach(row -> {
				Platform.runLater(() -> {
					dataTable.getItems().add(row);
				});
			});
			p.hide();
		}
	}

}
