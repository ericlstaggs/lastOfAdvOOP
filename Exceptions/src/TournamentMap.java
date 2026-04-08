/**
 * 
 */


import java.util.*;

import org.nit.Team;

import java.io.*;

/**
 * 
 */
public class TournamentMap {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		List<Team> list = new ArrayList<>();
		Map<String, Team> map = new HashMap<>();
		// read  the file and the team
		//for each line of the file
		// add each team to the list
		populateList(list , "2025_NIT.csv");
		populateMap(map, list);
		
		System.out.println(list);
		System.out.println(map);
		
		do {
			displayMenu();
		}while(true);

	}
	
	private static void populateMap(Map<String, Team> map, List<Team> list) {
//		for(Map m : list) {
//			map.put(m, null);
//		}
		map.putIfAbsent("UNA", list.get(0));
	}
	
	private static void populateList(List<Team> list, String filename) {
//		list.add(new Team());
		for(Team t : list) {
			
		}
		
		//open file
		// while ready
		// read line
		//parse line into columns
		//create a team object
		//add the object to the list
		//close the file
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
			
			if(br.ready()) {
				br.readLine();
			}
			while(br.ready()) {
				String line = br.readLine();
				System.out.println(line);
				
				String[] cols = line.split(",");
				
				System.out.println(cols[0]);
				Team team = new Team();
				
				list.add(team);
				break;
			}
			
		} catch (FileNotFoundException e) {
			
			// TODO Auto-generated catch block
			System.err.println(e);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void displayMenu() {
		System.out.println("start");
	}

}
