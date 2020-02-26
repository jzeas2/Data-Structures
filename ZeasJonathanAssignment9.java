/**
 * @author jonathanzeas
 * CSCI 1450-001
 * Assignment 8
 * Due: Nov 19 @ 12:15 PM
 * 
 * This program puts values from a text file into a linked list. It then performs various tasks on the 
 * linked list such as bubble sort and find & replace, printing it between actions.
 */

import java.io.File;
import java.util.*;
import java.io.IOException;

public class ZeasJonathanAssignment9 {


	public static void main(String[] args) throws IOException {
		
		//name of files
		String fileName = "concertTour.txt";
		String fileReplaceName = "concertTourReplace.txt";
		
		//define files
		File file = new File (fileName);
		File fileReplace = new File(fileReplaceName);
		
		//instantiate scanners
		Scanner regFile = new Scanner(file);
		Scanner repFile = new Scanner(fileReplace);
		
		//instantiate tour linked list
		TourLinkedList linkedList = new TourLinkedList ();
		
		//read each locale and place in linked list
		while (regFile.hasNextLine()) {
			int stop = regFile.nextInt();
			String type = regFile.next();
			String place = regFile.next();
			String activity = regFile.nextLine();
			
			Locale locale = new Locale (stop, type, place, activity);
			linkedList.addLocale(locale);
		}
		
		//print list unsorted
		System.out.println("\nUnsorted Concert Tour Route:\n");
		linkedList.printList();
		
		//sort list and print again
		linkedList.bubbleSort();
		System.out.println("\nSorted Concert Tour Route:\n");
		linkedList.printList();
		
		//replace certain locales
		while(repFile.hasNextLine()) {
			int stop = repFile.nextInt();
			String type = repFile.next();
			String place = repFile.next();
			String activity = repFile.nextLine();
			
			Locale locale = new Locale (stop, type, place, activity);
			linkedList.replaceLocale(locale);
			
		}
		
		//print list one last time
		System.out.println("\nUpdated Concert Tour Route - Replaced Certain Locales:\n");
		linkedList.printList();
		
		repFile.close();
		regFile.close();
		

	}
	
	
}

class Locale {
	/**
	 * Represents one stop during the concert tour.  
	 */
	
	private int stop; // The stop number indicates the order in which locales are visited during the concert tour. Locales are visited in numeric order.
	private String type; // string with no spaces. Describes type of locale (e.g. “start”, “hotel”, etc.).
	private String name; //string with no spaces.  Name of the locale (e.g., “Miami-Airport.”)
	private String activity; //string with spaces. Describes what the parrots do at this locale (e.g., “Perch on beach”).
	
	public Locale (int stop, String type, String name, String activity) {
		
		this.stop = stop;
		this.type = type;
		this.name = name;
		this.activity = activity;
		
	}//constructor
	
	
	//getters
	public int getStop() {
		return stop;
	}
	
	public String getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
	
	public String getActivity() {
		return activity;
	}
	
	//setters
	public void setStop(int stop) {
		this.stop = stop;
	}
	
	public void setType (String type) {
		this.type = type;
	}
	
	public void setName (String name) {
		this.name = name;
	}
	
	public void setActivity(String activity) {
		this.activity = activity;
	}
	
	//return string formatted to print
	public String print() {
		return (String.format("%-20s %-20s %-35s",name,type,activity));
	}
	
}


class TourLinkedList {
	/**
	 *The linked list of locales represents all the stops made during the concert tour.	 
	 **/
	
	//elements of a linked list
	private Node head;
	private Node tail;
	private int size;
	
	
	public TourLinkedList() {
		
		this.head = null;
		this.tail = null;
		this.size = 0;
		
	}//constructor
	
	
	//adds a locale node to the list
	public void addLocale (Locale locale) {
		
		//create new node
		Node node = new Node (locale);
		
		//if this is the first element, set the head and tail equal to it
		if (size == 0) {
			
			head = node;
			tail = node;
			
		//otherwise, add to the end of the list
		} else {
			
			while (tail.next != null) {
				tail = tail.next;
			}
			tail.next = node;
		}
		//increment the size
		size++;
		
	}
	
	//replace a node at a given location
	public void replaceLocale(Locale replacementLocale) {
		
		Node node = new Node(replacementLocale);
		
		//start at beginning
		tail = head;
		
		//increment through the entire list
		for (int i = 0; i < size; i++) {
			
			//if not found, increment
			if (tail.locale.getStop() != replacementLocale.getStop()) {
				
				tail = tail.next;
				
			//if found, set node's next value equal to tail's next value
			} else {

				node.next = tail.next;
				
				//if it's the first value, set head equal to it
				if (head == tail) {
					
					head = node;
					i = size;
					break;
					
				//otherwise, go back one space and set the next value of that node equal to the new node
				} else {
				
				tail = head;
				
				for (int j = 1; j < i; j++) {
					tail = tail.next;
				}
				i = size;
				tail.next = node;
				break;
				}
			}
			
		}
		
		
	}
	
	//sorts based on stop number
	public void bubbleSort () {
	
	//for each value in linked list
	for (int i = 1; i < size; i++) {
		
		//count up to last value minus i
		for (int j = 0; j < (size-i); j++) {
			
			tail = head;
			
			//for each value, count to that value 
			for (int k = 0; k < j; k++) {
				tail = tail.next;
			}
			//then if the value at this stop is greater than value after it, swap the values.
			if (tail.locale.getStop() > tail.next.locale.getStop()) {
				swapNodeData(tail, tail.next);
			}
			
			
		}
		
	}
	
	}
	
	
	//swaps the values of 2 nodes
	public void swapNodeData (Node node1, Node node2) {
		

		//increment to first node
		tail = head;
		while (tail != node1) {
			tail = tail.next;
		}
		
		//store values at this node
		int stop = tail.locale.getStop(); 
		String type = tail.locale.getType(); 
		String name = tail.locale.getName(); 
		String activity = tail.locale.getActivity();
		
		//increment to second node
		tail = head;
		while (tail != node2) {
			tail = tail.next;
		}
		//save values at this node, then set values in node equal to the values saved from first node
		int stop2 = tail.locale.getStop(); 
		String type2 = tail.locale.getType(); 
		String name2 = tail.locale.getName(); 
		String activity2 = tail.locale.getActivity();
		
		tail.locale.setStop(stop);
		tail.locale.setType(type);
		tail.locale.setName(name);
		tail.locale.setActivity(activity);
		
		//increment back to first node
		tail = head;
		
		while (tail != node1) {
			tail = tail.next;
		}
		
		//set first node values equal to values obtained from second node
		tail.locale.setStop(stop2);
		tail.locale.setType(type2);
		tail.locale.setName(name2);
		tail.locale.setActivity(activity2);
		
		
	}


	//get print value from locale to print each node
	public void printList() {
		
		tail = head;
		
		//print header
		System.out.println((String.format("%-20s %-21s %-35s","Locale","Type","Activity")));
		System.out.println("----------------------------------------------------------------------------------------------");
		
		//print each node except last one 
		while (tail.next != null) {
			System.out.println(tail.locale.print());
			tail = tail.next;
			
		}
		//print last node
		System.out.println(tail.locale.print());
	}
	
	
	//node class
	private class Node {
		
		//parts of a node
		private Locale locale;
		private Node next;
		
		//constructor
		public Node (Locale locale) {
			this.locale = locale;
			this.next = null;
		}
		
		
		
	}
	
	
	
}







