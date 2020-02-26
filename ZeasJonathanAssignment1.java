import java.io.*;
import java.util.Arrays;


public class ZeasJonathanAssignment1 {

	public static void main(String[] args) {
		//Define Starting Variables
		int [] arrayWrite = new int [25];
		int [] arrayRead = new int [25];
		int [] arrayRev = new int [25];
		int highestValue = 0;
		int secondHighest = 0;
		String toBeWritten = "";
		int randomInt;
		try {
			//open file for writing
			BufferedWriter writeToFile = new BufferedWriter (new FileWriter("assignment1.txt"));
			//for each item in the range 0-24
			System.out.println("Generating random numbers and writing to a file.\n");
			for (int i = 0; i < 25; i++) {
				//generate a random integer between 0-99, inclusive
				randomInt = (int)(Math.random()*100);
				//add 1 so it's between 1-100
				randomInt = randomInt + 1;
				//log value in array
				arrayWrite[i]=randomInt;
				//convert value to string to be written to file
				toBeWritten =  Integer.toString(randomInt);
				System.out.println("Writing to File: "+toBeWritten);
				//write value to file
				writeToFile.write(toBeWritten);
				//add a new line after each line that's not the last line
				if (i < 24) {
					writeToFile.write("\n");
				}
			}
			//close the file
			writeToFile.close();
			System.out.println("");
			
			//TASK 2
			//Open file for reading
			BufferedReader readFile = new BufferedReader(new FileReader("assignment1.txt")); 
			String line = "";
			int numOfLines = 0;
			//read each line in file
			System.out.println("Reading values from file and placing into array.\n");
			while ((line = readFile.readLine()) != null) {
			 	//find each number integer in the line, store in array
			 	arrayRead[numOfLines] = Integer.parseInt(line);
			 	//print the value in array
			    System.out.println("Value in array: "+arrayRead[numOfLines]); 
			    //move to next space in array
		 		numOfLines++;
			 }
			readFile.close();
		//find highest & secondHighest
			System.out.println("\nFinding largest 2 values in array.");
			for (int i = 0; i < 25; i++) {
				if (arrayRead[i] > highestValue)
					highestValue = arrayRead[i];
			}
			
			for (int i = 0; i < 25; i++) {

				//looks for the second highest value, excluding duplicates
				if ((arrayRead[i] < highestValue) & (arrayRead[i] > secondHighest))
						secondHighest = arrayRead[i];
			}
			//print highest and second highest
			System.out.println("\n1st Largest Value: "+highestValue);
			System.out.println("\n2nd Largest Value: "+secondHighest);
			System.out.println();
			
			//sorts array into ascending order
			System.out.println("Sorting the values in the array into descending order (largest to smallest).\n");
			Arrays.sort(arrayRead);
			//reverses the order of first array
			int revNum = 24;
			for (int i = 0; i < 25; i++) {
				arrayRev[revNum] = arrayRead[i];
				revNum--;
			}
			arrayRead = arrayRev;
			//Print numbers in array
			for (int i = 0; i < 25; i++) {
				System.out.println("arrayRead["+Integer.toString(i)+"] = "+arrayRead[i]);
			}
		
		} catch (IOException e) {
			
		}

	}

}
