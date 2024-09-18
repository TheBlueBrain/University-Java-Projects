package com.lab4v2;

import java.util.HashMap;

public class Attendance{
	HashMap<Group, HashMap<Student, HashMap<Integer, Boolean>>> atendees;
	HashMap<Student, HashMap<Integer, Boolean>> getByGroup (Group g){
		return atendees.get(g);
	}
	HashMap<Group, HashMap<Integer, Boolean>> getByStudent(Student s){
		HashMap<Group, HashMap<Integer, Boolean>> g = new HashMap<>();
		atendees.forEach((group, studentMapHashMap) ->{
			if(group.students.contains(s) && studentMapHashMap.containsKey(s)){
				g.put(group, studentMapHashMap.get(s));
			}
		});
		return g;
	}
	HashMap<Integer, Boolean> getByBoth(Group g, Student s){
		return atendees.get(g).get(s);
	}
	void setAtendance(Group g, Student s, Integer date, Boolean attended){
		if(atendees.containsKey(g)){
			if(atendees.get(g).containsKey(s)){
				if(atendees.get(g).get(s).containsKey(date)){
					atendees.get(g).get(s).remove(date);
					atendees.get(g).get(s).put(date, attended);
				}else{
					atendees.get(g).get(s).put(date, attended);
				}
			}else{
				atendees.get(g).put(s, (new HashMap<Integer, Boolean>()));
				atendees.get(g).get(s).put(date, attended);
			}
		}else{
			atendees.put(g, new HashMap<Student, HashMap<Integer, Boolean>>());
			atendees.get(g).put(s, (new HashMap<Integer, Boolean>()));
			atendees.get(g).get(s).put(date, attended);
		}
	}
}
