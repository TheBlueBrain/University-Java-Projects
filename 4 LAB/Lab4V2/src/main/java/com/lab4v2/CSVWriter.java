package com.lab4v2;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class CSVWriter extends FileWriter{

	public CSVWriter(String file){
		super(file);
	}
	@Override
	int write(Collection g, Collection st){
		try{
			BufferedWriter w = new BufferedWriter(new java.io.FileWriter(f));
			w.write("STUDENT NAME, LNAME, CODE, BDATE, SDATE, EDATE, LIVIN, PROGRAM, GROUPS, ATTENDANCE\n");
			for(Data d : st.data){
				Student s = (Student) d;
				w.write(s.fname + ", " + s.lname + ", " + (s.personalCode==null?"NUL":s.personalCode) +", " +  (s.birth==null?"NUL":s.birth) + ", "
						+ (s.start==null?"NUL":s.start) + ", "+ (s.end==null?"NUL":s.end) + ", "+ (s.address==null?"NUL":s.address) + ", "
						+ (s.programme==null?"NUL":s.programme) + ", ");
				w.write("[");
				for(Iterator<Group> iterator = s.groups.iterator(); iterator.hasNext(); ){
					Group gr = iterator.next();
					w.write(gr.name);
					if(iterator.hasNext()){
						w.write("; ");
					}
				}
				w.write("], [");
				for(Iterator<String> iterator = s.attendance.keySet().iterator(); iterator.hasNext(); ){
					String da = iterator.next();
					w.write("[" + da + "; " + (s.attendance.get(da)==null?"?":(s.attendance.get(da)==true?"Atvyko":"Neatvyko")));
					if(iterator.hasNext()){
						w.write("]; ");
					}else{
						w.write("]");
					}
				}
				w.write("]\n");
			}
			w.write("\n");
			w.close();
		}catch(IOException e){
			throw new RuntimeException(e);
		}
		return 1;
	}

	@Override
	void read(Collection g, Collection s){
		g.data.clear();
		s.data.clear();
		ArrayList<Group> grlist = new ArrayList<>();
		try{
			Scanner r = new Scanner(f);
			String st = r.nextLine();
			while(r.hasNextLine()){
				st = r.nextLine().replace("[", "").replace("]", "");
				if(st.equals("")){
					continue;
				}
				Student.StudentBuilder studentBuilder = new Student.StudentBuilder();
				String[] stri = st.split(", ");
				studentBuilder = studentBuilder.setFname(stri[0]);
				studentBuilder = studentBuilder.setLname(stri[1]);
				if(!stri[2].equals("Nul")){
					studentBuilder = studentBuilder.setPersonalCode(stri[2]);
				}
				if(!stri[3].equals("Nul")){
					studentBuilder = studentBuilder.setBirth(stri[3]);
				}
				if(!stri[4].equals("Nul")){
					studentBuilder = studentBuilder.setStart(stri[4]);
				}
				if(!stri[5].equals("Nul")){
					studentBuilder = studentBuilder.setEnd(stri[5]);
				}
				if(!stri[6].equals("Nul")){
					studentBuilder = studentBuilder.setAddress(stri[6]);
				}
				if(!stri[7].equals("Nul")){
					studentBuilder = studentBuilder.setProgramme(stri[7]);
				}
				Student student = studentBuilder.build();
				String[] groups = stri[8].split("; ");
				for(String gr : groups){
					boolean eg = false;
					for(Group group : grlist){
						if(group.name.equals(gr)){
							group.students.add(student);
							student.groups.add(group);
							eg = true;
						}
					}
					if(!eg){
						Group tmp = new Group(gr);
						tmp.students.add(student);
						student.groups.add(tmp);
						grlist.add(tmp);
						g.add(tmp);
					}
				}
				String[] dates = stri[9].split("; ");
				for(int i = 0; i<dates.length; i+=2){
					student.attendance.put(dates[i], (dates[i+1].equals("Atvyko")?true:false));
				}
				s.add(student);
			}
		}catch(FileNotFoundException e){
			throw new RuntimeException(e);
		}
	}
}
