/**
 * 
 */
package edu.univ.dept;

import java.util.*;
import java.io.*;


/**
 * 
 */
public class FileFactory implements InstructorFactory {
	
	List<Instructor> cc = new ArrayList<Instructor>();

	
	
	public FileFactory() {
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("courses.csv")));
			if(br.ready()) {
				br.readLine();
			}
			while(br.ready()) {
				Instructor course = new Instructor();
				String line = br.readLine();
				String[] cols = line.split(",");
				int id = Integer.parseInt(cols[0]);
				int instructorId = Integer.parseInt(cols[1]);
				String name = cols[2];
				int hours = Integer.parseInt(cols[3]);
				//TODO: fix this
//				course.setId(id);
//				course.setInstructorId(instructorId);
//				course.setName(name);
//				course.setHours(hours);
				cc.add(course);				
			}
		} catch (FileNotFoundException e) {
			System.err.println(e);
		} catch (NumberFormatException e) {
			System.err.println(e);
		} catch (IOException e) {
			System.err.println(e);
		}
	}
	

	public Instructor fetchClass(int id) {
		// TODO Auto-generated method stub
		Instructor c = new Instructor();
		for(Instructor cls : cc) {
			if(cls.getId() == id) {
				c = cls;
			}
		}
		return c;
	}


	public List<Instructor> fetchAllClasses() {		
		return cc;
	}


	@Override
	public Instructor fetchInstructorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Instructor> fetInstructors() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
