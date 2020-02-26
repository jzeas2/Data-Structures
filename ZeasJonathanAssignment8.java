/**
 * @author jonathanzeas
 * CSCI 1450-001
 * Assignment 8
 * Due: Nov 6 @ 12:15 PM
 * 
 * This program unscrambles data messages using both ArrayLists and Queues
 */

import java.io.File;
import java.util.*;
import java.io.IOException;
import java.util.ArrayList;

public class ZeasJonathanAssignment8 {
	

	public static void main(String[] args) throws IOException {
		
		//name of files
		String queueKeyFile =  "queueKey.txt";
		String queueMessageFile = "queueMessage.txt";
		String listKeyFile = "listKey.txt";
		String listMessageFile = "listMessage.txt";
		
		//define as files
		File inputFile = new File (queueKeyFile); 
		File inputFile2 = new File (queueMessageFile);		
		File inputFile3 = new File (listKeyFile); 
		File inputFile4 = new File (listMessageFile);
		
		//instantiate scanners
		Scanner queueKey = new Scanner (inputFile); 
		Scanner queueMessage = new Scanner (inputFile2);
		Scanner listKey = new Scanner (inputFile3); 
		Scanner listMessage = new Scanner (inputFile4);
		
		//instantiate arraylists to hold message and key 
		ArrayList <Character> secretMessage = new ArrayList <Character>();
		ArrayList <Character> secretKey = new ArrayList <Character>();
		
		//get key string value
		String strVal = listKey.next();
		
		//push each character in string to ArrayList
		for (int i = 0; i < strVal.length(); i++){
		    char c = strVal.charAt(i);        
			secretKey.add(c);
		}
		
		//get message string value
		strVal = listMessage.next();
		
		//print scrambled message
		System.out.println("The scrambled message is: "+strVal);
		
		//push each character in string to ArrayList
		for (int i = 0; i < strVal.length(); i++){
		    char c = strVal.charAt(i);        
			secretMessage.add(c);
		}
		
		//get rows & cols
		int arrRows = listKey.nextInt();
		int arrCols = listKey.nextInt();
		
		//get starting rows & cols
		int startRow = listKey.nextInt();
		int startCol = listKey.nextInt();
		
		//define iterators
		Iterator <Character> itM = secretMessage.iterator();
		Iterator <Character> itK = secretKey.iterator();
		
		//create decoder 
		Decoder aryList = new Decoder(arrRows, arrCols, startRow, startCol);
		//unscramble message & print value 
		System.out.println("The secret message is: "+aryList.unscramble(itM, itK));
		
		//close scanners
		listKey.close();
		listMessage.close();
		
		//define character queues
		Queue <Character> qSecretMessage = new LinkedList<Character>();
		Queue <Character> qSecretKey = new LinkedList<Character>();
		
		//get string value of key
		strVal = queueKey.next();
		
		//add all chars in string to queue
		for (int i = 0; i < strVal.length(); i++) {
		    char c = strVal.charAt(i);        
			qSecretKey.add(c);
		}
		
		//get string value of message
		strVal = queueMessage.next();
		
		//print scrambled message
		System.out.println("\nThe scrambled message is: "+strVal);
		
		//add all chars in string to queue
		for (int i = 0; i < strVal.length(); i++) {
		    char c = strVal.charAt(i);        
			qSecretMessage.add(c);
		}
		
		//get int values for rows & cols
		int qRows = queueKey.nextInt();
		int qCols = queueKey.nextInt();
		int qStartRow = queueKey.nextInt();
		int qStartCol = queueKey.nextInt();
		
		//create iterators
		Iterator <Character> itQM = qSecretMessage.iterator();
		Iterator <Character> itQK = qSecretKey.iterator();
		
		//create decoder 
		Decoder Q = new Decoder(qRows, qCols, qStartRow, qStartCol);
		//print unscrambled message
		System.out.println("The secret message is: "+Q.unscramble(itQM, itQK));
		
		//close scanners
		queueKey.close();
		queueMessage.close();	
		
		
		
	}
	
	
}

class Decoder {
	
	/**
	 * Class that unscrambles a secret message
	 */
	
	private char [][]messageArray; // – 2D array of characters used in the process to unscramble the message
	private int startingRow; //– integer for starting row of the key route
	private int startingCol; // – integer for starting column of the key route
	private Stack stack; // – used in the last unscrambling step
	

	//constructor
	public Decoder (int numRows, int numCols, int startingRow, int startingCol)  {
		
		this.messageArray = new char [numRows][numCols];
		this.startingRow = startingRow;
		this.startingCol = startingCol;
		stack = new Stack();
		
	}
	
	//Unscrambles the secret message that is stored in the two iterators.
	public String unscramble (Iterator<Character> msgIterator, Iterator<Character>keyIterator) {
		
		//Step 1: Fill 2D array with characters in message iterator - msgIterator
		for (int i = messageArray.length-1;i > -1; i--) {
			for (int j = 0; j < messageArray[0].length;j++) {
				messageArray[i][j] = msgIterator.next();
			}
		}
		
		//Step 2: Fill stack with characters from 2D array using route cipher which is in keyIterator
		
		//push first item
		stack.push(messageArray[startingRow][startingCol]);
		
		//move and push each item from key
		while (keyIterator.hasNext()){
			char iterVal = keyIterator.next();
			if (iterVal == 'u') {
				startingRow--;
			} else if (iterVal == 'b') {
				startingCol--;
			} else if (iterVal == 'f') {
				startingCol++;
			} else if (iterVal == 'd') {
				startingRow++;
			} else {
				System.out.println("Invalid Key!" + Character.toString(iterVal));
			}
			stack.push(messageArray[startingRow][startingCol]);
		}
		
		//Step 3: Undo the reversal 
		
		String rtVal = "";
		while (!stack.isEmpty()) {
			rtVal= rtVal + Character.toString(stack.pop());
		}
	return rtVal;
	}


	
}

class Stack {
	
	/**
	 * A stack is used in the final step to unscramble the secret message.
	 * The secret message is pushed onto the stack one character at a time as described in the Decoder’s unscramble method.  
	 * In the final step, you will pop the characters off the stack and place them into a string.  
	 * This will undo the reversing of the original string.
	 */
	
	
	
	private ArrayList <Character> list;
	
	Stack() {
		
		list = new ArrayList<Character>();
		
	}
	
	
	//Returns a boolean indicating if the stack is empty
	public boolean isEmpty() {
		
		try {
			list.get(0);
			return false;
		} catch (Exception e) {
			return true;
		}
		
	}
	
	//Returns the number of elements currently on the stack
	public int getSize() {
		
		return list.size();
		
	}
	
	//Adds a character to the top of the stack 
	public void push(char value) {
		
		list.add(value);
		
	}

	//Removes and returns the character on the top of the stack.  
	public char pop() {
		
		int size = list.size();
		char c = list.remove(size-1);
		return c;
		
	}
	
	
	
	
	
	
	
	
}
