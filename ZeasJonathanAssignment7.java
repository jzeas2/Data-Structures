/**
 * @author jonathanzeas
 * CSCI 1450-001
 * Assignment 7
 * Due: Oct 29 @ 12:15 PM
 * 
 * This program reads plane and truck data from a file. It then inputs the data into a cargo terminal.
 * Once all data is entered into the cargo terminal, the planes are queued on the taxiways and cleared for takeoff.
 */

import java.io.File;
import java.util.*;
import java.io.IOException;

public class ZeasJonathanAssignment7 {
	

	public static void main(String[] args) throws IOException {
		
		// Name of files to read from
		final String PLANES_FILE_NAME = "FedExPlanes7.txt";	
		final String TRUCKS_FILE_NAME = "FedExTrucks7.txt";	

		// Setup a file reference variable to refer to text file
		File planesFileName = new File(PLANES_FILE_NAME);
		File trucksFileName = new File(TRUCKS_FILE_NAME);

		// Open the planes file for reading by creating a scanner for the file
		// First value in the file tells how many stands the terminal's tarmac contains
		Scanner planesFile = new Scanner (planesFileName);
		int numberStands = planesFile.nextInt();

		// Open the trucks file for reading by creating a scanner for the file
		// First value in the file tells how many docks the terminal's loading dock contains
		Scanner trucksFile = new Scanner (trucksFileName);
		int numberDocks = trucksFile.nextInt();

		// Create cargo terminal with the number of loading docks and stands specified in file.  
		CargoTerminal memphis = new CargoTerminal(numberDocks, numberStands);

		// Load cargo terminal with semi-trucks and planes
		System.out.println("Loading semi-trucks and planes into cargo terminal...");
		System.out.println();
		
		// Create the semi-truck objects
		while (trucksFile.hasNext()) {
			
			// Read information from the line in the file into separate variables
			// The last value on the line is the name of the destination city so use a  
			// nextLine to consume the entire string from the file.
			int dockNumber = trucksFile.nextInt();
			int truckNumber = trucksFile.nextInt();
			String destCity = (trucksFile.nextLine()).trim();

			// Create a semi-truck object
			// Move semi-truck to its correct dock
			SemiTruck aTruck = new SemiTruck(truckNumber, destCity);		
			memphis.addSemiTruck(dockNumber, aTruck);
		
		} // while more semi-trucks
		
		// Close the semi-trucks file
		trucksFile.close();

		// Next setup the tarmac with the planes in the file
		// Create the plane objects
		while (planesFile.hasNext()) {
			
			// Read information from the line in the file into separate variables
			// The last value on the line is the name of the destination city so use a  
			// nextLine to consume the entire string from the file.
			int standNumber = planesFile.nextInt();
			String cargoType = planesFile.next();
			int flightNumber = planesFile.nextInt();
			String type = planesFile.next();
			double capacity = planesFile.nextDouble();
			String destCity = (planesFile.nextLine()).trim();

			// Create a plane object
			// Move the plane to its correct spot on the tarmac 
			CargoPlane aPlane = new CargoPlane(flightNumber, type, capacity, destCity, cargoType);
			memphis.addCargoPlane(standNumber, aPlane);

		} // while more planes

		// Close the cargo planes file
		planesFile.close();
		
		// Now that the semi-trucks and planes are loaded display the cargo terminal
		memphis.displayTrucksAndPlanes();
		
		//Create an air traffic controller object, a taxiways object, and a runaway object
		Taxiways taxiways = new Taxiways();
		AirTrafficController controller = new AirTrafficController();
		Runway runway = new Runway();
		
		//Air traffic controller moves all planes in cargo terminal’s tarmac to taxiways:
		System.out.print("\n\n\nControl Tower: Moving planes from cargo terminal to taxiways: \n--------------------------------------------------------------------");

		controller.movePlanesToTaxiways(memphis,taxiways);
		
		//After all planes are moved, show that the cargo terminal’s tarmac is empty.
		System.out.println("\n\nCargo Terminal after Move - ");
		memphis.displayTrucksAndPlanes();
		
		//Air traffic controller moves all planes waiting in taxiways to runway:
		System.out.print("\n\n\nControl Tower: Moving cargo planes from taxiways to runway\n--------------------------------------------------------------------");
		controller.movePlanesToRunway(taxiways,runway);
		
		//Air traffic controller simulate planes taking off:
		System.out.print("\n\n\nControl Tower: Ready for takeoff! \n--------------------------------------------------------------------");
		controller.clearedForTakeoff(runway);
		
	} // main
	
	
} // Assignment 4




// Represents the Cargo Terminal.  
// The cargo terminal contains a loading dock and a tarmac.  Both are modeled with an array
// and each contains a specific number of slots.
class CargoTerminal {
	
	private int numberDocks;			// Number of docks where semi-trucks can be loaded
	private int numberStands;			// Number of stands (parking locations) for planes
	private SemiTruck[] loadingDock;	// Array representing loading docks that hold semi-trucks
	private CargoPlane[] tarmac;		// Array representing parking spots that hold planes
	
	
	public CargoTerminal (int numberDocks, int numberStands) {
		this.numberDocks = numberDocks;
		this.numberStands = numberStands;
		
		// Create the loading dock and tarmac for the cargo terminal
		loadingDock = new SemiTruck[numberDocks];
		tarmac = new CargoPlane[numberStands];
	}

	public CargoPlane removeCargoPlane (int stand) {
		CargoPlane returnValue = tarmac[stand];
		tarmac[stand] = null;
		return returnValue;
	}
	
	// Returns the number of docks in the loading dock the cargo terminal contains 
	public int getNumberDocks() {
		return numberDocks;
	}

	// Returns the number of stands in the tarmac the cargo terminal contains 
	public int getNumberStands() {
		return numberStands;
	}
		
	// Add a truck to the semi-truck array in a specific location
	public void addSemiTruck (int dock, SemiTruck semiTruck) {
		loadingDock[dock] = semiTruck; 
	}

	// Add a plane to the plane array in a specific location
	public void addCargoPlane (int stand, CargoPlane plane) {
		tarmac[stand] = plane; 
	}

	// Returns the semi-truck in the specified loading dock in the array.  
	// When a dock does NOT contain a semi-truck return null.
	public SemiTruck getSemiTruck(int dock) {
		
		// Return the semi-truck which could be null if slot is empty
		// Student Note: When you created the array, Java initialized the array with null values
		return loadingDock[dock];
	}

	// Returns the cargo plane in the specified location in the array.  
	// When a stand does NOT contain a cargo plane return null.
	public CargoPlane getCargoPlane(int stand) {
		
		// Return the plane which could be null if slot is empty
		// Student Note: When you created the array, Java initialized the array with null values
		return tarmac[stand];
	}

	// Displays a nice layout of the semi-trucks and planes in the cargo terminal
	public void displayTrucksAndPlanes() {
		
		System.out.println();

		// Create the header text for the semi-trucks
		for (int i = 1; i < numberDocks; i++) {
			System.out.printf("%s %d\t", "Dock #", i);
		}
		System.out.println();
		
		// Now print the semi-trucks in the loading docks
		for (int i = 1; i < numberDocks; i++) {
			SemiTruck semiTruck = loadingDock[i];
			if (semiTruck != null) {
				System.out.printf("%d\t\t", semiTruck.getTruckNumber());
			}
			else {
				System.out.printf ("%s\t\t", "------");				
			}
		}

		// Create the header text for the cargo planes
		System.out.println();
		System.out.println();
		for (int i = 1; i < numberStands; i++) {
			System.out.printf("%s %d\t", "Stand #", i);
		}
		System.out.println();
		
		// Now print the cargo planes in the tarmac
		for (int i = 1; i < numberStands; i++) {
			CargoPlane plane = tarmac[i];
			if (plane != null) {
				System.out.printf("%d\t\t", plane.getFlightNumber());
			}
			else {
				System.out.printf ("%s\t\t", "------");				
			}
		}
		
	} // displayTrucksAndPlanes
				
} // CargoTerminal

//Represents a cargo plane
class CargoPlane implements Comparable <CargoPlane> { 
	
	private int flightNumber;
	private String type;
	private double capacity;
	private String destinationCity;		// Where plane will depart to
	private String cargoType;
			
	// Create a plane
	public CargoPlane (int flightNumber, String type, double capacity, String destinationCity, String cargoType) {
		this.flightNumber= flightNumber;
		this.type = type;
		this.capacity = capacity;
		this.destinationCity = destinationCity;
		this.cargoType = cargoType;
	}
		
	public int getFlightNumber() {
		return flightNumber;
	}
	
	public String getDestinationCity() {
		return destinationCity;
	}

	public String getType() {
		return type;
	}
	
	public double getCapacity() {
		return capacity;
	}
	
	public String getCargoType() {
		return cargoType;
	}
	
	//if medical or animals, return true
	public boolean isUrgent() {
		if (cargoType.equals("medical") || cargoType.equals("animals")) {
			return true;
		} else {
			return false;
		}
	}
	
	//if packages, return true
	public boolean isStandard() {
		if (cargoType.equals("packages")) {
			return true;
		} else {
			return false;
		}
	}
	
	//print flight #, destination city, cargo type
	public void print () {
		System.out.printf(" %-6s%-12s%-12s",Integer.toString(flightNumber),destinationCity,cargoType);
	}
	
	//compare urgent planes to each other (medical before animals)
	public int compareTo (CargoPlane otherPlane) {
		if(otherPlane.getCargoType() == cargoType) {
			return 0;
		} else if ((otherPlane.getCargoType() == "medical") && (cargoType == "animals")) {
			return 1;
		} else {
			return -1;
		}
	}
			
} // CargoPlane

// Represents a semi-truck
class SemiTruck { 

	private int truckNumber;			// Semi-truck's number
	private String destinationCity;		// City semi-truck will take goods

	public SemiTruck(int truckNumber, String destinationCity) {
		this.truckNumber = truckNumber;
		this.destinationCity = destinationCity;
	}
	
	public int getTruckNumber() {
		return truckNumber;
	}
	
	public String getDestinationCity() {
		return destinationCity;
	}

} // SemiTruck

//Represents the 2 queues that make up the taxiways
class Taxiways {
	
	//starting variables
	private PriorityQueue<CargoPlane> urgentTaxiway;
	private Queue <CargoPlane> standardTaxiway;
	
	//constructor
	public Taxiways () {
		this.urgentTaxiway = new PriorityQueue<CargoPlane>();
		this.standardTaxiway = new LinkedList<CargoPlane>();
	}
	
	// returns true when urgent taxiway queue is empty
	public boolean isUrgentTaxiwayEmpty() {

		CargoPlane temp = urgentTaxiway.peek();
		if (temp == null) {
			return true;
		} else {
			return false;
		}
		
	}
	
	//adds (offers) plane to urgent queue
	public void addPlaneToUrgentTaxiway (CargoPlane p) { 
		urgentTaxiway.offer(p);
	}
	
	//removes plane from urgent queue
	public CargoPlane removePlaneFromUrgentTaxiway() { 
		return urgentTaxiway.remove();	
	}
	
	// returns true when standard taxiway queue is empty
	public boolean isStandardTaxiwayEmpty() {	
		CargoPlane temp = standardTaxiway.peek();
		if (temp == null) {
			return true;
		} else {
			return false;
		}
	}
	
	//adds (offers) plane to standard queue
	public void addPlaneToStandardTaxiway (CargoPlane p) { 
		standardTaxiway.offer(p);
	}
	
	//removes plane from standard queue
	public CargoPlane removePlaneFromStandardTaxiway() { 
		return standardTaxiway.remove();	
	}
	
}//taxiways


//Represents the 1 queue that makes up the runway
class Runway {

	//starting variables
	private LinkedList<CargoPlane> runway;
	
	//constructor
	Runway() {
		
		this.runway = new LinkedList<CargoPlane>();
		
	}
	
	// returns true when standard runway queue is empty
	public boolean isRunwayEmpty() {	
		CargoPlane temp = runway.peek();
		if (temp == null) {
			return true;
		} else {
			return false;
		}
	}
	
	//adds (offers) plane to standard queue
	public void addPlaneToRunway (CargoPlane p) { 
		runway.offer(p);
	}
	
	//removes plane from standard queue
	public CargoPlane removePlaneFromRunway() { 
		return runway.remove();	
	}
	
}//runway

//o	Contains the business logic for moving cargo planes from tarmac to taxiways then to runway
class AirTrafficController {
	
	public void movePlanesToTaxiways (CargoTerminal cargoTerminal, Taxiways taxiways) {
		
		//go through stands in order, adding planes to correct taxiways
		int index = 0;
		while (index < cargoTerminal.getNumberStands()) {
			CargoPlane tempPlane = cargoTerminal.getCargoPlane(index);
			if (tempPlane == null) {
				//do nothing if no plane
			} else if (tempPlane.isUrgent()) {//if urgent, add to urgent taxiway
				taxiways.addPlaneToUrgentTaxiway(tempPlane);
				System.out.print("\n\nMoved to taxiway Urgent -   ");
				tempPlane.print();
			} else {//otherwise, add to standard taxiway
				taxiways.addPlaneToStandardTaxiway(tempPlane);
				System.out.print("\n\nMoved to taxiway Standard - ");
				tempPlane.print();
			}
			
			cargoTerminal.addCargoPlane(index,null);//remove plane from cargo terminal
			index++;

		}
	}
	
	public void movePlanesToRunway (Taxiways taxiways, Runway runway) {
		
		//for each plane in urgent taxiway
		while (!taxiways.isUrgentTaxiwayEmpty()) {
			//take plane from taxiway
			CargoPlane tempPlane = taxiways.removePlaneFromUrgentTaxiway();
			//add it to runway
			runway.addPlaneToRunway(tempPlane);
			System.out.print("\n\nMoved to runway - ");
			tempPlane.print();
		}
		
		//for each plane in standard taxiway
		while (!taxiways.isStandardTaxiwayEmpty()) {
			//remove plane from taxiway
			CargoPlane tempPlane = taxiways.removePlaneFromStandardTaxiway();
			//add it to runway
			runway.addPlaneToRunway(tempPlane);
			System.out.print("\n\nMoved to runway - ");
			tempPlane.print();
		}
		
	}
	
	public void clearedForTakeoff (Runway runway)  {
		
		//for each plane on runway
		while (!runway.isRunwayEmpty()) {
			//remove plane from runway
			CargoPlane tempPlane = runway.removePlaneFromRunway();
			//clear plane for takeoff
			System.out.print("\n\nCleared for takeoff - ");
			tempPlane.print();
		}
		
	}
		
	}
	




