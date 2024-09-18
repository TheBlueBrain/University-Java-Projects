package org.lab6;

import java.util.ArrayList;
import java.util.stream.Stream;

public class Data{
	public volatile static boolean newData = false;
	volatile private static ArrayList<ArrayList<String>> raw = new ArrayList<>();
	synchronized static void add(ArrayList<String> row){
		raw.add(row);
	}
	Stream<ArrayList<String>> getStream(){
		return raw.stream();
	}
}
