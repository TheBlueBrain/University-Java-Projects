package com.lab4v2;

import java.io.File;

public abstract class FileWriter{
	String fileName;
	File f;
	public FileWriter(String name){
		fileName = name;
		f = new File(fileName);
	}
	abstract int write(Collection g, Collection s);
	abstract void read(Collection g, Collection s);
}
