/**
 * @author jonathanzeas
 * CSCI 1450-001
 * Assignment 3
 * Due: Sep 19 @ 12:15 PM
 * 
 * This program reads athlete data from a file, then passes it to a class to create a Athlete. It then retrieves the data from the class to determine which 
 * athletes can't swim, which can bike, and which is the slowest runner, and output the data to the console.
 */

import java.io.File;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Scanner;

public class ZeasJonathanAssignment3 {
	
	public static void main (String[] args) {
		//open file
		File inputFile = new File ("athletes.txt");
		try {
		//scan file
		Scanner input = new Scanner (inputFile);
		//read first line
		String dataRead = input.next();
		//go to second line
		input.nextLine();
		//get integer from first line
		int numOfAthletes = Integer.parseInt(dataRead);
		//create array of that length
		Athlete athleteArr [] = new Athlete [numOfAthletes];
		//create array to read all athlete data
		String dataReadArr [] = new String[5];
		//define variables
		double runSpeed;
		double bikeSpeed;
		double swimSpeed;
		String name;
		//for each athlete
		for (int i = 0; i < numOfAthletes; i++) {
			//read data
			dataRead = input.nextLine();
			dataReadArr = dataRead.split(" ");
			//assign variables
			runSpeed = Double.parseDouble(dataReadArr[1]);
			bikeSpeed = Double.parseDouble((dataReadArr[3]));
			swimSpeed = Double.parseDouble((dataReadArr[2]));
			name = dataReadArr[4] + " "+ dataReadArr[5];
			//create item based on type of athlete
			if (dataReadArr[0].contentEquals("t"))
				//pass correct data to create type
				athleteArr[i] = new Triathlete(runSpeed, bikeSpeed, swimSpeed, name);
			else if (dataReadArr[0].contentEquals("d"))
				athleteArr[i] = new Duathlete(runSpeed, bikeSpeed, name);
			else if (dataReadArr[0].contentEquals("a"))
				athleteArr[i] = new Aquathlete(runSpeed, swimSpeed, name);
			else if (dataReadArr[0].contentEquals("m"))
				athleteArr[i] = new Marathoner(runSpeed, name);
			else
				//if unknown data type, print error in console
				System.out.println("Error! Not a known data type.");
		}
		//close file
		input.close();


		//create array list of all bikers
		ArrayList <Athlete> bikerArr = findBikers(athleteArr);
		//print starting line
		System.out.println("\nATHLETES THAT BIKE!\n-------------------"+ "");
		//for each biker, print info in console
		for (Athlete item : bikerArr) {
			System.out.println("\n"+item.getName()+" is a "+item.getType()+" and is an athlete that bikes "+((Biker) item).bike()+" mph.\n"+item.disciplines());
		}
		//create array list of all athletes who don't swim
		ArrayList <Athlete> doNotSwim = findDoNotSwim(athleteArr);
		//print starting line
		System.out.println("\n\nATHLETES THAT DO NOT SWIM!\n-----------"+ "----------------");
		//print info in console for each item
		for (Athlete item : doNotSwim) {
			System.out.println("\n"+ item.getName()+" is a "+item.getType()+" and does not swim.\n"+item.disciplines());
		}
		//find slowest runner
		Athlete slowestRunner = findSlowestRunner(athleteArr);
		//print info in console
		System.out.println("\n"+ "SLOWEST RUNNER!\n---------------\n\nSlowest runner is "+slowestRunner.getName()+" who is a "+slowestRunner.getType()+" with a running speed of "+((Runner)slowestRunner).run()+" mph.");
		
		
			
		
		} catch (IOException e) {
			System.out.println(e);
		}
		
		
		
		
		
	}
	

	
	public static ArrayList<Athlete> findBikers(Athlete [] athletes) {
		//create array list of Athletes named findBikers
		ArrayList<Athlete> findBikers = new ArrayList <Athlete>();
		//for each athlete
		for (Athlete a : athletes) {
			//if Triathlete or Duathlete, add to arrayList
			if (a.getType() == "Triathlete") {
				findBikers.add(a);
			} else if (a.getType() == "Duathlete"){ 
				findBikers.add(a);
			}
		}
		//return arrayList
		return findBikers;
	}
	
	public static ArrayList<Athlete> findDoNotSwim(Athlete [] athletes) {
		//create ArrayList of Athletes named findDoNotSwim
		ArrayList<Athlete> findDoNotSwim = new ArrayList <Athlete>();
		//for each athlete
		for (Athlete a : athletes) {
			//if Marathoner or Duathlete, add to ArrayList
			if (a.getType() == "Marathoner") {
					findDoNotSwim.add(a);
			} else if (a.getType() == "Duathlete") {
				findDoNotSwim.add(a);
			}
		}
		//return ArrayList
		return findDoNotSwim;
		}
	
	public static Athlete findSlowestRunner(Athlete [] athletes) {
		//starting varaibles
		Athlete slowestRunner = athletes[0];
		//not humanly possible to run over 1000 MPH
		double smallestMph = 1000.0;
		//for each athlete
		for (Athlete a : athletes) {
			//if speed less than smallest, make that athlete the slowest runner
			if (((Runner) a).run() < smallestMph) {
				slowestRunner = a;
				smallestMph = ((Runner) a).run();
			}
		}
		//return the slowest runner
		return slowestRunner;
	}
	
}
interface Runner {
//abstract method
double run();
}

interface Swimmer {
//abstract method
double swim();
}

interface Biker {
//abstract method
double bike();
}


abstract class Athlete {
	//starting variables
	private String name;
	private String type;
	
	public Athlete () {
		
	}
	//constructor
	public Athlete (String name, String type) {
		
		this.name = name;
		this.type = type;
		
	}
	//setters
	public void setName(String name) {
		this.name = name;
	}
	
	public void setType (String type) {
		this.type = type;
	}
	//getters
	public String getName () {
		return this.name;
	}
	
	public String getType() {
		return this.type;
	}
	//abstract method
	public abstract String disciplines();
	
	
}
class Triathlete extends Athlete implements Runner,Biker,Swimmer {
	//starting variables
	private double runSpeed;
	private double bikeSpeed;
	private double swimSpeed;
	
	
	public Triathlete (double runSpeed, double bikeSpeed, double swimSpeed, String name) {
		//pass to superclass
		super(name, "Triathlete");
		//set variables
		this.runSpeed = runSpeed;
		this.bikeSpeed = bikeSpeed;
		this.swimSpeed = swimSpeed;
	}
	//overriding abstract methods
	@Override
	public String disciplines() {
		return ("During the Ironman triathlon, I swim 2.4 miles, bike 112 miles, then run 26.2 miles.");
	}

	@Override
	public double swim() {
		
		return swimSpeed;
	}

	@Override
	public double bike() {
		return bikeSpeed;
	}

	@Override
	public double run() {
		return runSpeed;
	}
}

class Duathlete extends Athlete implements Runner,Biker {
	//starting variables
	private double runSpeed;
	private double bikeSpeed;
	
	public Duathlete (double runSpeed, double bikeSpeed, String name) {
		//pass to superclass
		super(name, "Duathlete");
		//set variables
		this.runSpeed = runSpeed;
		this.bikeSpeed = bikeSpeed;
	}
	//override abstract methods
	@Override
	public String disciplines() {
		return "I run, bike, then sometimes run again. In a long-distance duathlon, I run 6.2 miles, bike 93 miles, then run 18.6 miles.";
	}

	@Override
	public double bike() {
		return bikeSpeed;
	}

	@Override
	public double run() {
		return runSpeed;
	}
}
class Aquathlete extends Athlete implements Runner, Swimmer {
	//starting variables
	private double runSpeed;
	private double swimSpeed;
	
	public Aquathlete (double runSpeed, double swimSpeed, String name) {
		//pass to superclass
		super(name, "Aquathlete");
		//set variables
		this.runSpeed = runSpeed;
		this.swimSpeed = swimSpeed;
	}
	//Override abstract methods
	@Override
	public String disciplines() {
		return "I run, swim, then run again. In the Swedish OTILLO Championship, the race takes place over 24 islands and requires 6 miles of swimming between the islands and 40 miles of trail running. ";
	}

	@Override
	public double swim() {
		
		return swimSpeed;
		
	}

	@Override
	public double run() {
		
		return runSpeed;
		
	}
}

class Marathoner extends Athlete implements Runner {
	//define variable
	private double runSpeed;
	
	public Marathoner (double runSpeed, String name) {
		//pass to super
		super(name, "Marathoner");
		//set variable
		this.runSpeed = runSpeed;
	}
	//Override abstract methods 
	@Override
	public String disciplines() {
		return "During a full marathon I run 26.2 miles!";
	}

	@Override
	public double run() {
		return runSpeed;
	}
}