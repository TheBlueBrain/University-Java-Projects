package com.lab4v2;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class ExcelWriter extends FileWriter{

	public ExcelWriter(String name){
		super(name);
	}

	@Override
	int write(Collection g, Collection s){
		XSSFWorkbook w = new XSSFWorkbook();
		XSSFSheet sheet = w.createSheet("Data");
		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		String groups = "";
		data.put("1", new Object[]{"S NAME", "LNAME", "CODE", "BDATE", "SDATE", "EDATE", "LIVIN", "PROGRAM", "GROUPS", "ATTENDANCE"});
		int i = 2;

		for(Data d : s.data){
			Student st = (Student) d;
			//s.fname + ", " + s.lname + ", " + (s.personalCode==null?"NUL":s.personalCode) +", " +  (st.birth==null?"NUL":st.birth) + ", "
			//						+ (st.start==null?"NUL":st.start) + ", "+ (st.end==null?"NUL":st.end) + ", "+ (st.address==null?"NUL":st.address) + ", "
			//						+ (st.programme==null?"NUL":st.programme)
			String grNames = "[";
			for(Iterator<Group> iterator = st.groups.iterator(); iterator.hasNext(); ){
				Group gr = iterator.next();
				grNames+=gr.name;
				if(iterator.hasNext()){
					grNames+=", ";
				}
			}
			grNames += "]";
			String Att = "[";
			for(Iterator<String> iterator = st.attendance.keySet().iterator(); iterator.hasNext(); ){
				String e = iterator.next();
				Att+="["+e+", "+(st.attendance.get(e)==null?"?":(st.attendance.get(e)?"Atvyko":"Neatvyko")) + "]";
				if(iterator.hasNext()){
					Att+=", ";
				}
			}
			Att+="]";
			data.put(String.valueOf(i++), new Object[]{st.fname, st.lname, (st.personalCode==null?"NUL":st.personalCode), (st.birth==null?"NUL":st.birth),
					(st.start==null?"NUL":st.start), (st.end==null?"NUL":st.end), (st.address==null?"NUL":st.address),(st.programme==null?"NUL":st.programme), grNames, Att
			});
		}

		Set<String> keyset = data.keySet();
		int rn = 0;
		for(String stri : keyset){
			Row r = sheet.createRow(rn++);
			Object[] arr = data.get(stri);
			int cn = 0;
			for(Object o : arr){
				Cell cell = r.createCell(cn++);
				cell.setCellValue((String) o);
			}
		}

		try{
			FileOutputStream o = new FileOutputStream(f);
			w.write(o);
			o.close();

		}catch(FileNotFoundException e){
			throw new RuntimeException(e);
		}catch(IOException e){
			throw new RuntimeException(e);
		}
		return 0;
	}

	@Override
	void read(Collection g, Collection s){
		g.data.clear();
		s.data.clear();
		ArrayList<Group> grlist = new ArrayList<>();
		try{
			FileInputStream in = new FileInputStream(f);
			XSSFWorkbook w = new XSSFWorkbook(in);
			XSSFSheet sheet = w.getSheetAt(0);

			Iterator<Row> it = sheet.iterator();
			Row row = it.next();
			while(it.hasNext()){
				row = it.next();
				Iterator<Cell> celit = row.iterator();
				int pos = 0;
				Student.StudentBuilder studentBuilder = new Student.StudentBuilder();
				Student student = null;
				while(celit.hasNext()){//visada 10 arg
					Cell cell = celit.next();
					switch(pos){
						case 0:
							studentBuilder = studentBuilder.setFname((!cell.getStringCellValue().equals("NUL")? cell.getStringCellValue():null));
							break;
						case 1:
							studentBuilder = studentBuilder.setLname((!cell.getStringCellValue().equals("NUL")? cell.getStringCellValue():null));
							break;
						case 2:
							studentBuilder = studentBuilder.setPersonalCode((!cell.getStringCellValue().equals("NUL")? cell.getStringCellValue():null));
							break;
						case 3:
							studentBuilder = studentBuilder.setBirth((!cell.getStringCellValue().equals("NUL")? cell.getStringCellValue():null));
							break;
						case 4:
							studentBuilder = studentBuilder.setStart((!cell.getStringCellValue().equals("NUL")? cell.getStringCellValue():null));
							break;
						case 5:
							studentBuilder = studentBuilder.setEnd((!cell.getStringCellValue().equals("NUL")? cell.getStringCellValue():null));
							break;
						case 6:
							studentBuilder = studentBuilder.setAddress((!cell.getStringCellValue().equals("NUL")? cell.getStringCellValue():null));
							break;
						case 7:
							studentBuilder = studentBuilder.setProgramme((!cell.getStringCellValue().equals("NUL")? cell.getStringCellValue():null));
							break;
						case 8:
							String[] groups = cell.getStringCellValue().replace("[", "").replace("]", "").split(", ");
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
							break;
						case 9:
							String[] dates = cell.getStringCellValue().replace("[", "").replace("]", "").split(", ");
							for(int i = 0; i<dates.length; i+=2){
								student.attendance.put(dates[i], (dates[i+1].equals("Atvyko")?true:false));
							}
							s.add(student);
							break;

					}
					if(pos==7){
						student = studentBuilder.build();
					}
					pos++;
				}
			}
		}catch(FileNotFoundException e){
			throw new RuntimeException(e);
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}
}
