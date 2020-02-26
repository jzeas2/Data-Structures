/**
 * @author jonathanzeas
 * CSCI 1450-001
 * Assignment 4
 * Due: Sep 27 @ 12:15 PM
 * 
 * This program reads plane and truck data from a file. It then inputs the data into a cargo terminal.
 * Once all data is entered into the cargo dock, docks and stands as well as tarmac status are output to the console.
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.io.IOException;
import java.util.Scanner;

public class ZeasJonathanAssignment4 {
	
	/**public static void main (String[] args) throws IOException {
	
		String txtPlanes = "FedExPlanes.txt"; //define text file names
		String txtTrucks = "FedExTrucks.txt";
		
		File inputFile = new File (txtPlanes); //define as files
		File inputFile2 = new File (txtTrucks);
		
		Scanner inputPlanes = new Scanner (inputFile); //instantiate scanners
		Scanner inputTrucks = new Scanner (inputFile2);
		
		String dataRead = inputPlanes.next(); //get data from first line
		inputPlanes.nextLine();
		
		int numOfStands = Integer.parseInt(dataRead); //get number of stands from data
		
		dataRead = inputTrucks.next(); //get data from first line
		inputTrucks.nextLine();
		
		int numOfDocks = Integer.parseInt(dataRead); //get number of docks from data
		
		System.out.println("\nLoading semi-trucks and planes into cargo terminal...\n");
		
		CargoTerminal cargoTerminal = new CargoTerminal (numOfDocks, numOfStands); //create CargoTerminal object
		
		int numOfPlanes = 0;//count num of planes
		
		while (inputPlanes.hasNextLine()) {
			dataRead = inputPlanes.nextLine();
			numOfPlanes++;
		}
		
		
		int numOfTrucks = 0;//count num of trucks
		while (inputTrucks.hasNextLine()) {
			dataRead = inputTrucks.nextLine();
			numOfTrucks++;
		}
		
		inputPlanes.close();//close and reopen scanners to go back to top
		inputTrucks.close();
		
		Scanner inputPlanes2 = new Scanner (inputFile);
		Scanner inputTrucks2 = new Scanner (inputFile2);

		inputPlanes2.nextLine();//go to second line
		inputTrucks2.nextLine();
		
		String readPlaneArr[] = new String [5];//arrays to split info from files
		String readTruckArr[] = new String [4];
		
		int stand; int flightNum; String type; double capacity; String destination;
		
		
		for (int i = 0; i < numOfPlanes; i++) {//create CargoPlane object for each line in file
			readPlaneArr = inputPlanes2.nextLine().split(" ");
			stand = Integer.parseInt(readPlaneArr[0]);
			flightNum = Integer.parseInt(readPlaneArr[1]);
			type = readPlaneArr[2];
			capacity = Double.parseDouble(readPlaneArr[3]);
			destination = readPlaneArr[4];
			cargoTerminal.addCargoPlane(stand,new CargoPlane (flightNum,type,capacity,destination))	;
		}
		
		int truckNum; int dock;
		
		for (int i = 0; i < numOfTrucks; i++) {//create SemiTruck object for each line in file
			readTruckArr = inputTrucks2.nextLine().split(" ");
			dock = Integer.parseInt(readTruckArr[0]);
			truckNum = Integer.parseInt(readTruckArr[1]);
			destination = readTruckArr[2];
			cargoTerminal.addSemiTruck(dock,new SemiTruck(truckNum,destination));
		}
		
		inputPlanes2.close();//close files
		inputTrucks2.close();
		
		cargoTerminal.displayTrucksAndPlanes();//call displayTrucksAndPlanes function
		System.out.println();
		System.out.println();
		System.out.println();
		printTarmacStatus(cargoTerminal);//call printTarmacStatus function

	} 
	
	public static void printTarmacStatus (CargoTerminal terminal)  {

		  ArrayList <PlaneReport> planes = new ArrayList<PlaneReport>();
		 //For each cargo plane 
		  for (int i = 0; i < terminal.getNumStands(); i++) {
			  String type; int flightNumber; double capacity; String destinationCity; int stand;
			  if (terminal.getCargoPlane(i) != null) {
				  //Place all PlaneReports into an ArrayList
				  stand = i;
				  type = terminal.getCargoPlane(i).getType();
				  flightNumber = terminal.getCargoPlane(i).getFlightNumber(); 
				  capacity = terminal.getCargoPlane(i).getCapacity(); 
				  destinationCity = terminal.getCargoPlane(i).getDestinationCity();
				  planes.add(new PlaneReport(stand,flightNumber,type,capacity,destinationCity));
		  }
		  }
		 
		 //Sort all PlaneReports in the ArrayList using Collections.sort()
		  Collections.sort(planes);
		  //o	Print all PlaneReports in the ArrayList (now sorted by capacity)
		  System.out.println("****************************************************************************************");
			System.out.println("                              Tarmac Status \n                         (Lowest to Highest Capacity)");
		  System.out.println("****************************************************************************************");
		  System.out.println("Flight		Stand		Departing To		Type		Capacity(lbs)");
		  System.out.println("----------------------------------------------------------------------------------------");

		  for (PlaneReport p : planes) {
			  System.out.println(p.print());
		  } 
	  }
	
	}
	


interface printable {
	
	public String print();
	
}


class CargoTerminal {
	private int numberDocks; private int numberStands; private SemiTruck[] loadingDock; private CargoPlane[] tarmac;
	
	public CargoTerminal (int numberDocks, int numberStands) {//constructor
		
		this.numberDocks = numberDocks;
		this.numberStands = numberStands;
		
		this.loadingDock = new SemiTruck[numberDocks];
		this.tarmac = new CargoPlane[numberStands]; 
		
	}
	
	public int getNumDocs() {//getter
		return numberDocks;
	}
	
	public int getNumStands() {//getter
		return numberStands;
	}
	
	public void addSemiTruck (int dock, SemiTruck semiTruck) {//setter
		//Places incoming semi-truck object in loading dock array in specified dock (slot) 
		//Simulates parking a semi-truck at a specific loading dock at the cargo terminal
		loadingDock[dock] = semiTruck;
	}
	
	public void addCargoPlane (int stand, CargoPlane plane) {//setter
	//Places incoming plane object in tarmac array in specified stand (slot)
	//Simulates parking a plane at a specific stand at the cargo terminal
		tarmac[stand] = plane;
	
	}
	
	public SemiTruck getSemiTruck (int dock) {//getter
		//Returns semi-truck stored in the loading dock (array) in the specified dock (slot)
		//If there is no semi-truck in the slot (i.e. empty array location), returns null
		return loadingDock[dock];
	}

	public CargoPlane getCargoPlane (int stand) {//getter
	//Returns the plane stored on the tarmac (array) in a specified stand (slot)
	//If there is no plane in the slot (i.e. empty array location), returns null
		return tarmac[stand];
	}
	
	public void displayTrucksAndPlanes() {//Displays a nicely formatted version of cargo terminal’s loading dock and tarmac 
		
		for (int i = 0; i < loadingDock.length; i++) {//for each dock
			System.out.printf("%-15s","Dock #"+Integer.toString(i));//print dock #
		}
		System.out.println();
		
		for (int i = 0; i < loadingDock.length; i++) {//for each dock
			if (loadingDock[i] == null)	//if no truck print - - - - -
				System.out.printf("%-15s", "- - - - -");
		 else //otherwise print truck number
			System.out.printf("%-15s",loadingDock[i].getTruckNumber());
		

			}
			System.out.println();
			System.out.println();
		//Print stand # and plane’s flight number
		for (int i = 0; i < tarmac.length; i++) {//for each tarmac slot
			System.out.printf("%-15s","Stand #"+Integer.toString(i));//print tarmac number
		}
		System.out.println();
		for (int i = 0; i < tarmac.length; i++) {//for each tarmac slot
			if (tarmac[i] == null)	//if no plane, print - - - - -
				System.out.printf("%-15s", "- - - - -");
		 else //otherwise print flight number
			System.out.printf("%-15s",tarmac[i].getFlightNumber());
		

			}
		
	}
	
}	
	
class SemiTruck {
	private int truckNumber; private String destinationCity;
	
	public SemiTruck (int truckNumber, String destinationCity) {//constructor
		
		this.truckNumber = truckNumber;
		this.destinationCity = destinationCity;
		
	}
	
	public int getTruckNumber() {//getter
		return truckNumber;
	}
	
	public String getDestinationCity() {//getter
		return destinationCity;
	}
	
}

class CargoPlane {
	
	private int flightNumber; private String type; private double capacity; private String destinationCity;

	
	public CargoPlane (int flightNumber, String type, double capacity, String destinationCity) {//constructor
		this.flightNumber = flightNumber;
		this.type = type;
		this.capacity = capacity;
		this.destinationCity = destinationCity;
	}
	
	public int getFlightNumber() {//getter
		return flightNumber;
	}
	
	public String getType() {//getter
		return type;
	}
	
	public double getCapacity() {//getter
		return capacity;
	}
	
	public String getDestinationCity() {//getter
		return destinationCity;
	}
	
}

class PlaneReport implements printable, Comparable<PlaneReport> {
	
	private int stand; private int flightNumber; private String type; private double capacity; private String destinationCity; 
	
	public PlaneReport (int stand, int flightNumber, String type, double capacity, String destinationCity) {//constructor
		this.stand = stand;
		this.flightNumber = flightNumber;
		this.type = type;
		this.capacity = capacity;
		this.destinationCity = destinationCity;
	}
	
	@Override
	public String print() {//returns string value representing the flight, for ready for printing
		return String.format("%4d\t\t%2d\t\t%-15s\t\t%-10s\t%.2f",  flightNumber,  stand,  destinationCity,  type, capacity);


	}
	
	@Override
	public int compareTo (PlaneReport otherReport) {//returns 0, 1, or -1 depending on relationship between capacities of planes
		double capThis = capacity;//gets data for main report
		String last10Digits = otherReport.print().substring(otherReport.print().length()-10);//gets last 10 digits of print function for otherReport
		Scanner scanner = new Scanner(last10Digits);//scanner for string
		double capOther = 0;
		if (scanner.hasNextDouble())//if there's a double in the string (should be 1 and only 1 unless textfile isn't correct)
			capOther = scanner.nextDouble();//capacity of other is the value of the double
		scanner.close();
		if (capThis > capOther) {
			return 1;
		} else if (capThis < capOther) {
			return -1;
		} else {
			return 0;
		}

		
}
	**/
}

