package com.lab4v2;

import com.lab4v2.Collection;
import com.lab4v2.Group;
import com.lab4v2.MyChoiceBox;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.apache.pdfbox.contentstream.PDContentStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MenuController {

	@FXML
	private MyChoiceBox GroupSelection;

	@FXML
	private Button NewGroupCreation;

	@FXML
	private CheckBox OnlyFilled;

	@FXML
	private TableColumn<Data, ChoiceBox> attendance;

	@FXML
	private Button attendanceLog;

	@FXML
	private DatePicker attendanceLogLowerDate;

	@FXML
	private ListView<String> attendanceLogTable;

	@FXML
	private DatePicker attendanceLogUpperDate;

	@FXML
	private ListView<String> attendanceTable;

	@FXML
	private DatePicker entryDateSelection;

	@FXML
	private Button exportData;

	@FXML
	private TextField groupName;

	@FXML
	private Button importData;

	@FXML
	private MyChoiceBox pickGroupForAttendence;

	@FXML
	private MyChoiceBox pickStudentforAttendence;

	@FXML
	private TextField studentAddress;

	@FXML
	private DatePicker studentBirthDate;

	@FXML
	private Button studentCreation;

	@FXML
	private TextField studentFirstName;

	@FXML
	private TextField studentLastName;

	@FXML
	private TextField studentPersonalCode;

	@FXML
	private MyChoiceBox studentSelectStudentOrCreateNew;

	@FXML
	private MyChoiceBox studentSelectionForGroup;

	@FXML
	private DatePicker studentStudyEnd;

	@FXML
	private TextField studentStudyProgramme;

	@FXML
	private DatePicker studentStudyStart;

	@FXML
	private TableView<Data> studentTable;

	@FXML
	private Button studentToGroup;

	@FXML
	private TableColumn<Data, String> students;

	@FXML
	private ListView<String> studentsInGroupList;

	@FXML
	private Button toPDF;
	Student IGNORE, selected = null;
	Group sel = null;
	Collection studentscol = new Collection(), groupscol = new Collection();
	boolean setS, setG;
	String lowerdate, upperdate;
	boolean onlyKnown = false;

	//done
	@FXML
	void addStudentToGroup(ActionEvent event) {
		Group s = (Group) GroupSelection.getValue();
		Student p = (Student) studentSelectionForGroup.getValue();
		s.students.add(p);
		p.groups.add(s);
		showListOfStudents(event);
	}
	//done
	@FXML
	void createNewGroup(ActionEvent event) {
		Group g = null;
		if(!groupName.getText().equals("")){
			g = new Group(groupName.getText());
			if(!setG){
				groupscol.addChoiseBox(pickGroupForAttendence);
				groupscol.addChoiseBox(GroupSelection);
				setG = true;
			}
			groupscol.add(g);
		}
	}
	//done
	@FXML
	void updateStudentInfo(ActionEvent e){
		if(studentSelectStudentOrCreateNew.getValue() != IGNORE && studentSelectStudentOrCreateNew.getValue() != null){
			Student s = (Student) studentSelectStudentOrCreateNew.getValue();
			studentFirstName.setText(s.fname);
			studentLastName.setText(s.lname);
			studentAddress.setText(s.address);
			studentPersonalCode.setText(s.personalCode);
			studentStudyProgramme.setText(s.programme);
			studentBirthDate.setValue(LocalDate.parse(s.birth));
			studentStudyStart.setValue(LocalDate.parse(s.start));
			studentStudyEnd.setValue(LocalDate.parse(s.end));
		}
	}
	//done
	@FXML
	void createStudent(ActionEvent e){
		if(!setS){
			studentscol.addChoiseBox(studentSelectionForGroup);
			studentscol.addChoiseBox(studentSelectStudentOrCreateNew);
			IGNORE = new Student.StudentBuilder().setFname("Naujas").setLname("studentas").build();
			studentSelectStudentOrCreateNew.addSelection(IGNORE);
			studentSelectStudentOrCreateNew.getSelectionModel().select(IGNORE);
			studentscol.addChoiseBox(pickStudentforAttendence);
			setS = true;
		}
		if(studentSelectStudentOrCreateNew.getValue()==IGNORE || studentSelectStudentOrCreateNew.getValue()==null){
			studentscol.add(new Student.StudentBuilder().setFname(studentFirstName.getText()).setLname(studentLastName.getText()).setAddress(studentAddress.getText()).setPersonalCode(studentPersonalCode.getText())
					.setProgramme(studentStudyProgramme.getText()).setBirth(studentBirthDate.getValue().toString()).setStart(studentStudyStart.getValue().toString()).setEnd(studentStudyEnd.getValue().toString()).build());
		}else{
			Student s = (Student) studentSelectStudentOrCreateNew.getValue();
			s.fname = studentFirstName.getText();
			s.lname=studentLastName.getText();
			s.address=studentAddress.getText();
			s.personalCode=studentPersonalCode.getText();
			s.programme=studentStudyProgramme.getText();
			s.birth=studentBirthDate.getValue().toString();
			s.start=studentStudyStart.getValue().toString();
			s.end=studentStudyEnd.getValue().toString();
			studentscol.changeTitle(s);
		}
	}
	//done
	@FXML
	void showListOfStudents(ActionEvent e){
		Group choce = (Group) GroupSelection.getValue();
		studentsInGroupList.getItems().clear();
		if(choce.students!=null){
			for(Student s : choce.students){
				studentsInGroupList.getItems().add(s.toString());
			}
		}

	}
	//DONE
	@FXML
	void saveToPDF(ActionEvent event) {
		try(PDDocument d = new PDDocument()){
			PDPage p = new PDPage();
			d.addPage(p);
			PDPageContentStream st = new PDPageContentStream(d, p);
			float pageH = p.getMediaBox().getHeight()-40;
			float tmp = pageH;
			float indent = 20;
			float margin = 20;
			PDType0Font f = PDType0Font.load(d, new File("/usr/share/fonts/truetype/ancient-scripts/Symbola_hint.ttf"));
			st.setFont(f, 11);

			//st.moveTo(margin, pageH + margin);
			st.beginText();
			st.newLineAtOffset(margin, pageH+margin);
		    for(Data data : studentscol.data){
				st.newLineAtOffset(0, 0);
		        Student s = (Student) data;
				st.showText(s.toString() + ":");
				st.newLineAtOffset(indent, -12);
				tmp-=12;
				if(tmp<=13){
					st.endText();
					st.close();
					p = new PDPage();
					d.addPage(p);
					st = new PDPageContentStream(d, p);
					st.setFont(f, 11);
					st.beginText();
					st.moveTo(margin, pageH + margin);
				}
		        for(String e : s.attendance.keySet()){
					if(s.attendance.get(e)!=null){
						st.showText(e + " " + (s.attendance.get(e)? "Atvyko" : "Neatvyko"));
						st.newLineAtOffset(0, -12);
						tmp -= 12;
						if(tmp <= 13){
							st.endText();
							st.close();
							p = new PDPage();
							d.addPage(p);
							st = new PDPageContentStream(d, p);
							st.setFont(f, 11);
							st.beginText();
							st.moveTo(margin, pageH + margin);
						}
					}
				}
				st.newLineAtOffset(-indent, -12);
				tmp-=12;
			    if(tmp<=13){
				    st.endText();
				    st.close();
				    p = new PDPage();
				    d.addPage(p);
				    st = new PDPageContentStream(d, p);
				    st.setFont(f, 11);
				    st.beginText();
				    st.moveTo(margin, pageH + margin);
			    }
			}
			st.endText();
			st.close();
			d.save("Ataskaita.pdf");
			d.close();
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}
	//TODO
	@FXML
	void selectImportFile(ActionEvent event) {
		FileChooser ch = new FileChooser();
		File f = ch.showOpenDialog(null);
		if(f==null){
			return;
		}
		String name = f.getPath();
		FileWriter fw;
		if(name.endsWith(".csv")){
			fw = new CSVWriter(name);
		}else if(name.endsWith(".xlsx")){
			fw = new ExcelWriter(name);
		}else{
			return;
		}
		if(!setG){
			groupscol.addChoiseBox(pickGroupForAttendence);
			groupscol.addChoiseBox(GroupSelection);
			setG = true;
		}
		if(!setS){
			studentscol.addChoiseBox(studentSelectionForGroup);
			studentscol.addChoiseBox(studentSelectStudentOrCreateNew);
			IGNORE = new Student.StudentBuilder().setFname("Naujas").setLname("studentas").build();
			studentSelectStudentOrCreateNew.addSelection(IGNORE);
			studentSelectStudentOrCreateNew.getSelectionModel().select(IGNORE);
			studentscol.addChoiseBox(pickStudentforAttendence);
			setS = true;
		}
		fw.read(groupscol, studentscol);
	}
	//TODO
	@FXML
	void selectOutputFile(ActionEvent event) {
		FileChooser ch = new FileChooser();
		File f = ch.showSaveDialog(null);
		if(f==null){
			return;
		}
		String name = f.getPath();
		FileWriter fw;
		if(name.endsWith(".csv")){
			fw = new CSVWriter(name);
		}else if(name.endsWith(".xlsx")){
			fw = new ExcelWriter(name);
		}else{
			return;
		}
		fw.write(groupscol, studentscol);
	}
	//done
	@FXML
	void setAttendanceLowerDate(ActionEvent event) {
		lowerdate = attendanceLogLowerDate.getValue().toString();
		updateAttendanceList();
	}

	//done
	@FXML
	void setAttendanceUpperDate(ActionEvent event) {
		upperdate = attendanceLogUpperDate.getValue().toString();
		updateAttendanceList();
	}

	//done
	void updateAttendanceList(){
		attendanceLogTable.getItems().clear();
		if(upperdate != null && lowerdate != null){
			for(Data d : studentscol.data){
				Student s = (Student) d;
				for(String e : s.attendance.keySet()){
					if(inDateRange(upperdate, lowerdate, e)){
						if(s.attendance.get(e)!=null){
							attendanceLogTable.getItems().add(s.toString() + " " + e + " " + (s.attendance.get(e) ? "Atvyko" : "Neatvyko"));
						}else{
							attendanceLogTable.getItems().add(s.toString() + " " + e + " " +"Nera duomenu");
						}
					}
				}
			}
		}
	}

	/**
	 * Done
	 * @param U Upper date
	 * @param L Lower date
	 * @param C check date
	 * returns true if date c is between L and U
	 */

	boolean inDateRange(String U, String L, String C){
		LocalDate u = LocalDate.parse(U);
		LocalDate l = LocalDate.parse(L);
		LocalDate c = LocalDate.parse(C);

		if(u.isAfter(c)&&l.isBefore(c)){
			return true;
		}
		return false;
	}

	//done
	@FXML
	void setEntryDate(ActionEvent event) {
		students.getColumns().clear();
		attendance.getColumns().clear();
		students.setCellValueFactory(data -> {
			Student st = (Student) data.getValue();
			if(st!=null){
				return new SimpleStringProperty(st.toString());
			}else{
				return new SimpleStringProperty("");
			}
		});
		attendance.setCellValueFactory(data -> {
			Student st = (Student) data.getValue();
			ChoiceBox<String> at = new ChoiceBox<>();
			at.getItems().addAll("Atvyko", "Neatvyko", "Nera duomenu");
			at.setValue("Nera duomenu");
			String date = entryDateSelection.getValue().toString();
			if(st.attendance.get(date)==null){
				at.setValue("Nera duomenu");
			}else if(st.attendance.get(date) == false){
				at.setValue("Neatvyko");
			}else{
				at.setValue("Atvyko");
			}
			at.setOnAction(e ->{
				if(at.getValue().equals("Atvyko")){
					st.attendance.put(entryDateSelection.getValue().toString(), true);
				}else if(at.getValue().equals("Neatvyko")){
					st.attendance.put(entryDateSelection.getValue().toString(), false);
				}else{
					st.attendance.put(entryDateSelection.getValue().toString(), null);
				}
			});
			return new SimpleObjectProperty<>(at);
		});
		studentTable.getColumns().setAll(students, attendance);
		studentTable.setItems(FXCollections.observableList(studentscol.data));
	}
	//done
	@FXML
	void setOnlyFilled(ActionEvent event) {
		onlyKnown = OnlyFilled.isSelected();
		showLocalAttendance();
	}
	//done
	void showLocalAttendance(){
		attendanceTable.getItems().clear();
		if(selected!=null){
			if(sel!=null && selected.groups.contains(sel)){
				for(String s : selected.attendance.keySet()){
					if(selected.attendance.get(s)!=null){
						attendanceTable.getItems().add(selected.toString() + " " + s + " " + (selected.attendance.get(s) ? "Atvyko" : "Neatvyko"));
					}else if(!onlyKnown){
						attendanceTable.getItems().add(selected.toString() + " " + s + " " +"Nera duomenu");
					}
					//attendanceTable.getItems().add(selected.toString() + " " + s + " " + selected.attendance.get(s));
				}
			}else{
				for(String s : selected.attendance.keySet()){
					if(selected.attendance.get(s)!=null){
						attendanceTable.getItems().add(selected.toString() + " " + s + " " + (selected.attendance.get(s) ? "Atvyko" : "Neatvyko"));
					}else if(!onlyKnown){
						attendanceTable.getItems().add(selected.toString() + " " + s + " " +"Nera duomenu");
					}
					//attendanceTable.getItems().add(selected.toString() + " " + s + " " + selected.attendance.get(s));
				}
			}
		}else if(sel != null){
			for(Student s : sel.students){
				for(String d : s.attendance.keySet()){
					if(s.attendance.get(d)!=null){
						attendanceTable.getItems().add(s.toString() + " " + d + " " + (s.attendance.get(d) ? "Atvyko" : "Neatvyko"));
					}else if(!onlyKnown){
						attendanceTable.getItems().add(s.toString() + " " + d + " " +"Nera duomenu");
					}
					//attendanceTable.getItems().add(s.toString() + " " + d + " " + s.attendance.get(d));
				}
			}
		}
	}
	//done
	@FXML
	public void chooseStudentForAttendance(ActionEvent actionEvent){
		selected = (Student) pickStudentforAttendence.getValue();
		showLocalAttendance();
	}
	//done
	@FXML
	public void chooseGroupForAttendance(ActionEvent actionEvent){
		sel = (Group) pickGroupForAttendence.getValue();
		showLocalAttendance();
	}
}
