package com.lab4v2;

import java.util.ArrayList;
import java.util.List;

public class Collection{

	public ArrayList<Data> data;
	private List<MyChoiceBox> obs;

	public Collection(){
		data = new ArrayList<>();//Student / Group
		obs = new ArrayList<>();
	}
	public void add(Data d){
		data.add(d);
		update(d);
	}
	public void addChoiseBox(MyChoiceBox b){
		obs.add(b);
	}
	private void update(Data d){
		for(MyChoiceBox x : obs){
			x.addSelection(d);
		}
	}
	public void changeTitle(Data d){
		for(MyChoiceBox x : obs){
			//studentSelectStudentOrCreateNew.getItems().set(studentSelectStudentOrCreateNew.getItems().indexOf(s), s);
			x.getItems().set(x.getItems().indexOf(d), d);
		}
	}

}
