package com.lab4v2;

import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;
import java.util.HashMap;

public class Student implements Data{
	String fname, lname, address,birth,start, end, programme, personalCode;;
	boolean isMale, financed;
	HashMap<String, Boolean> attendance;
	ArrayList<Group> groups;
	public String toString(){
		return fname + " " + lname;
	}
	private Student(StudentBuilder b){
		groups = new ArrayList<>();
		fname = b.fname;
		lname = b.lname;
		address = b.address;
		programme = b.programme;
		isMale = b.isMale;
		financed = b.financed;
		birth = b.birth;
		personalCode = b.personalCode;
		start = b.start;
		end = b.end;
		attendance = new HashMap<String, Boolean>();
	}
	public static class StudentBuilder{
		String fname, lname, address,birth, start, end, programme, personalCode;;
		boolean isMale, financed;


		public StudentBuilder setFname(String fname){
			this.fname = fname;
			return this;
		}

		public StudentBuilder setLname(String lname){
			this.lname = lname;
			return this;
		}

		public StudentBuilder setAddress(String address){
			this.address = address;
			return this;
		}

		public StudentBuilder setProgramme(String programme){
			this.programme = programme;
			return this;
		}

		public StudentBuilder setMale(boolean male){
			isMale = male;
			return this;
		}

		public StudentBuilder setFinanced(boolean financed){
			this.financed = financed;
			return this;
		}

		public StudentBuilder setBirth(String birth){
			this.birth = birth;
			return this;
		}

		public StudentBuilder setPersonalCode(String personalCode){
			this.personalCode = personalCode;
			return this;
		}

		public StudentBuilder setStart(String start){
			this.start = start;
			return this;
		}

		public StudentBuilder setEnd(String end){
			this.end = end;
			return this;
		}
		public Student build(){
			return new Student(this);
		}
	}
}
