package com.lab4v2;

import java.util.ArrayList;

public class Group implements Data{
	ArrayList<Student> students;
	public String name;
	public Group(String name){
		this.name = name;
		students = new ArrayList<>();
	}
	public String toString(){
		return name;
	}
}
