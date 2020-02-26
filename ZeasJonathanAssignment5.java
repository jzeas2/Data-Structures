import java.io.*;
import java.util.*;

/**
 * @author jonathanzeas
 * CSCI 1450-001
 * Assignment 5
 * Due: Oct 3 @ 12:15 PM
 * 
 * This program reads data from text files and stores it in a stack. It then performs various tasks on the data,
 * including sorting in alphabetical/numerical order and replacing objects.
 * 
 */



public class ZeasJonathanAssignment5 {
	
	public static void main (String[] args) throws IOException {
		/***
		 * This method takes data from a file and creates stacks depending on the data type.
		 * It then performs functions such as sort,print, and find/replace on the stack.
		 */
	
		GenericStack<Integer> genStackInt = new GenericStack<Integer>();
		
		String txtNums = "numbersFall19.txt"; //define text file names
		String txtStrings = "stringsFall19.txt";
		
		File inputFile = new File (txtNums); //define as files
		File inputFile2 = new File (txtStrings);
		
		Scanner inputNums = new Scanner (inputFile); //instantiate scanners
		Scanner inputStrings = new Scanner (inputFile2);
		
		while (inputNums.hasNextInt()) {//put data into stack
			
			genStackInt.push(inputNums.nextInt());
			
		}
		
		inputNums.close();//close file
		
		System.out.println("\n\nValues read from file and pushed onto number stack: \n" + 
				"----------------------------------------------------");
		
		printStack(genStackInt);
		
		sortStack(genStackInt);//sort stack
		
		System.out.println("\nNumber Stack sorted - smallest to highest: \n" + 
				"----------------------------------------------");
		
		printStack(genStackInt);//print stack
		
		Scanner input = new Scanner (System.in);
		
		System.out.println("\nEnter value to replace on integer stack.");
		
		Integer replaceInt = input.nextInt();//user input
		
		System.out.println("Enter replacement value.");
		
		Integer replaceValue = input.nextInt();
					
		boolean intRep = searchAndReplace(genStackInt,replaceInt, replaceValue);//replace item if exists
		
		if (intRep == true) {//if item was replaced
			
			System.out.println("\nNumber Stack - replaced "+Integer.toString(replaceInt)+" with "+Integer.toString(replaceValue)+": \n" + 
					"------------------------------------");
			printStack(genStackInt);
			
		} else {//otherwise
			
			System.out.println("\nWas not able to replace the value.");
			System.out.println("\nNumber Stack\n" + 
					"----------------------------------------------");
			printStack(genStackInt);//print stack
			
		}
		
		
		
		GenericStack<String> genStackString = new GenericStack<String>();//create string stack
		while (inputStrings.hasNext()) {//put data into stack
			genStackString.push(inputStrings.next());
		}
		
		inputStrings.close();
		
		System.out.println("\nValues read from file and pushed onto string Stack: \n" + 
				"-----------------------------------------------------");
		
		printStack(genStackString);
		
		sortStack(genStackString);
		
		System.out.println("\nString Stack sorted - alphabetical order: \n" + 
				"----------------------------------------------");
		
		printStack(genStackString);
		
		System.out.println("\nEnter value to replace on string stack.");
		
		String replaceString = input.next();//user input
		
		System.out.println("Enter replacement value.");
		
		String replaceStrValue = input.next();
	    
		boolean strRep = searchAndReplace(genStackString,replaceString, replaceStrValue);//try and replace
		
		input.close();
		
		if (strRep == true) {//if item was replaced
			
			System.out.println("\nString Stack - replaced "+replaceString+" with "+replaceStrValue+": \n" + 
					"------------------------------------");
			printStack(genStackString);
			
		} else {//otherwise
			
			System.out.println("\nWas not able to replace the value.");
			System.out.println("\nString Stack\n" + 
					"----------------------------------------------");
			printStack(genStackString);//print stack
			
		}
		
		
	}
	
	public static <E> void printStack (GenericStack <E> genStack ) {
		
		/***
		 * Prints each value in the stack, returns stack in original form
		 */
		
		GenericStack<E> newStack = new GenericStack<E>();
		while(genStack.isEmpty() == false) {//for each element
			
			E popValue = genStack.pop();//pop the value and print it
			System.out.println(popValue);
			newStack.push(popValue);//add to newStack
			
		}
		
		while (newStack.isEmpty() == false) {
			genStack.push(newStack.pop());
		}
	}
	
	public static <E extends Comparable<E>> void sortStack (GenericStack<E> genStack) {

		GenericStack<E> newStack = new GenericStack<E>();
		
		E smalEle;
			while (genStack.isEmpty() == false) {
				
				smalEle = removeSmallestElement(genStack);
				
				newStack.push(smalEle);
				
			}
			
			while (newStack.isEmpty() == false) {
				
				genStack.push(newStack.pop());
				
			}
		
			
		}
		
		

	
	
	public static <E extends Comparable<E>> E removeSmallestElement (GenericStack<E> genStack) {
		
		/***
		 * Removes smallest element from the stack and returns it
		 */
		
		GenericStack<E> tempStack = new GenericStack<E>();//def starting variables
		E currentEle = genStack.peek();
		E lowEle = genStack.peek();
		int lowLoc = genStack.getSize()-1;

		while (genStack.isEmpty() == false) {//while there's still an item in the generic stack
			
			currentEle = genStack.pop();//pop item and set equal to E currentELe
			tempStack.push(currentEle);//add value to new stack
			
			if (currentEle.compareTo(lowEle) < 0) {//if it's lower than current lowest element
				
				lowLoc = genStack.getSize();//set lowest location
				lowEle = currentEle;//lowest element is now the current element
				
			}
			
		}
			
		int currentLoc= 0;//start at index 0
		
		while (tempStack.isEmpty() == false) {//count up adding values back
			
			if(Integer.compare(currentLoc,lowLoc) != 0) {//once you reach lowest element location, don't add. add the rest.
			genStack.push(tempStack.pop());
			currentLoc++;
			
		} else {
			
			tempStack.pop();
			currentLoc++;
	
		}
		}
		
		return lowEle;//return lowest element
	}


	


public static <E> boolean searchAndReplace (GenericStack<E> stack, E valueToReplace, E replacementValue) {
	/***
	 * This method searches for a value and replaces it with a new value
	 * Returns true if it finds the value and false if it doesn't
	 */
	
	GenericStack<E> tempStack = new GenericStack<E>();
	boolean valueFound = false;
	E currentEle = stack.peek();
	
	while ((valueFound==false) && (stack.isEmpty()==false)) {//until value is found or end is reached
		
		currentEle = stack.pop();

		if (currentEle.equals(valueToReplace)) {
			valueFound = true;
			currentEle = replacementValue;
		}
		
		tempStack.push(currentEle);
		
	}
		
	while (tempStack.isEmpty()==false) {//push values back to stack
		stack.push(tempStack.pop());
	}
	
	if (valueFound == true)
			return true;
	else
		return false;
	
	
	
}
}


class GenericStack<E> {
	/***
	 * This class represents a generic stack
	 */

	private ArrayList<E> list = new ArrayList <E>();
	
	public GenericStack() {
		
		this.list = new ArrayList<E>();
		
	}
	
	public int getSize() {
	//Returns the number of objects currently on the stack	
		return list.size();
		
	}
	
	public boolean isEmpty() {
	//Returns boolean indicating if the stack is currently empty
		try {
			list.get(0);
			return false;
		} catch (Exception e) {
			return true;
		}
	}
	
	public E peek() {
	//Returns the object on the top of the stack, does NOT remove that object
		int size = list.size();
		return list.get(size-1);
		
	}
	
	public void push(E value) {
	//Adds an object to the top of the stack (i.e. end of the ArrayList)	
		list.add(value);
		
	}
	
	public E pop() {//remove and return item
		int size = list.size();
		E element = list.get(size-1);
		list.remove(size-1);
		return element;
	}

}
