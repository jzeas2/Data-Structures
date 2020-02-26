/**
 * @author jonathanzeas
 * CSCI 1450-001
 * Assignment 2
 * Due: Sep 13 @ 12:15 PM
 * 
 * This program reads sea turtle data from a file, then passes it to a class to create a Sea Turtle. Finally, it prints each value from seaturtle
 */


import java.io.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ZeasJonathanAssignment2 {
	
	public static void main (String[] args) {
	
	//open file
	File inputFile = new File ("SeaTurtles.txt");
	try {
		//scan file
		Scanner input = new Scanner (inputFile);
		//read first line
		String dataRead = input.next();
		//go to second line
		input.nextLine();
		//first line has num of turtles, set equal to constant
		int numOfTurtles = Integer.parseInt(dataRead);
		//create new Sea Turtle array
		SeaTurtle polyArray [] = new SeaTurtle [numOfTurtles];
		//create array to read data from line
		String[] dataReadArr = new String [4];
		//starting variable
		String type = "unknown";
		//parse through each turtle
		for (int i = 0; i < numOfTurtles; i++) {
			//read the line
			dataRead = input.nextLine();
			//split line by space
			dataReadArr = dataRead.split(" ");
			type = dataReadArr[0];
			//if first line is lh, create Loggerhead
			if (type.contentEquals("lh")) {
			polyArray[i] = new LoggerHead(dataReadArr[3], Integer.parseInt(dataReadArr[1]),Double.parseDouble(dataReadArr[2]));
			//if first line is gt, create green turtle
			} else if (type.contentEquals("gt")) {
			polyArray[i] = new Green_Turtle(dataReadArr[3], Integer.parseInt(dataReadArr[1]),Double.parseDouble(dataReadArr[2]));
			//if first line is hb create Hawksbill
			} else if (type.contentEquals("hb")) {
			polyArray[i] = new Hawksbill(dataReadArr[3], Integer.parseInt(dataReadArr[1]),Double.parseDouble(dataReadArr[2]));
			//if first line is lb create Leatherback
			} else if (type.contentEquals("lb")) {
			polyArray[i] = new Leatherback(dataReadArr[3], Integer.parseInt(dataReadArr[1]),Double.parseDouble(dataReadArr[2]));
				}
		}
		//print headers
		System.out.printf("%-12s","Name");
		System.out.printf("%-18s", "Type");
		System.out.printf("%-20s", "Days Tracked");
		System.out.printf("%-20s", "Miles Traveled");
		System.out.printf("%-20s", "Threats to Survival");
		System.out.println();
		System.out.println("------------------------------------------------------------------------------------------------------------");
		
		
		//print each seaturtle's data
		for (SeaTurtle t : polyArray) {
			
			System.out.printf("%-12s",t.getName());
			System.out.printf("%-23s", t.getType());
			System.out.printf("%-19s", t.getDaysTracked());
			System.out.printf("%-16s", t.getMilesTraveled());
			System.out.printf("%-20s", t.threatsToSurvival());
			System.out.println();
		}
		input.close();
	} catch (IOException e) {
		System.out.println(e);
	}
	}
	
		
		
	}
	




 class SeaTurtle {
	//starting variables
	private String name;
	private String type;
	private int daysTracked;
	private double milesTraveled;
	
	public SeaTurtle (String name, String type, int daysTracked, double milesTraveled) {
	//stores variables which are passed in 
	this.name = name;
	this.type = type;
	this.daysTracked = daysTracked;
	this.milesTraveled = milesTraveled;
	

	}
	
	//each of these make private variables accessible outside of the class (can get, not change)
	public String getName () {
		return name;
	}
	public String getType () {
		return type;
	}
	public int getDaysTracked() {
		return daysTracked;
	}
	public double getMilesTraveled() {
		return milesTraveled;
	}
	public String threatsToSurvival() {
		return "";	
	}
	
}
//creates loggerhead
class LoggerHead extends SeaTurtle {
	
	
	public LoggerHead (String name, int daysTracked, double milesTraveled) {

		super(name, "LoggerHead", daysTracked, milesTraveled);
		
	}
	
	
	
	//overrides threatsToSurvival() in superclass
	@Override
	public String threatsToSurvival() {
		return "Loss of nesting habitat";
	}
	
	
}
//creates green turtle
class Green_Turtle extends SeaTurtle {
	
	public Green_Turtle (String name, int daysTracked, double milesTraveled) {
		
		super(name, "Green Turtle", daysTracked, milesTraveled);
		
	}
	//overrides threatsToSurvival() in superclass
	@Override
	public String threatsToSurvival() {
		return "Commercial harvest for eggs & food";
	}
	
}
//creates Hawksbill
class Hawksbill extends SeaTurtle {
	
	public Hawksbill (String name, int daysTracked, double milesTraveled) {
		
		super(name, "Hawksbill", daysTracked, milesTraveled);
		
	}
	//overrides threatsToSurvival() in superclass
	@Override
	public String threatsToSurvival() {
		return "Harvesting of their shell";
	}
}
//creates Leatherback
class Leatherback extends SeaTurtle {
	
	public Leatherback (String name, int daysTracked, double milesTraveled) {
		
		super(name, "Leatherback", daysTracked, milesTraveled);
		
	}
	//overrides threatsToSurvival() in superclass
	@Override
	public String threatsToSurvival() {
		return "Plastic bag mistaken for jellyfish";
	}
	
}