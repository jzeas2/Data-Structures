/**
 * @author jonathanzeas
 * CSCI 1450-001
 * Assignment 10
 * Due: Dec 5 @ 12:15 PM
 * 
 * This project creates a binary tree of parrots. It then iterates through in levelOrder printing the parrot's lyrics as it passes them.
 * 
 */

import java.io.File;
import java.util.*;
import java.io.IOException;

public class ZeasJonathanAssignment10 {


	public static void main(String[] args) throws IOException {
		
		//name of file
		String fileName = "parrots.txt";
		
		//define file
		File file = new File (fileName);
		
		//instantiate scanner
		Scanner regFile = new Scanner(file);
		
		//new binarytree
		BinaryTree tree = new BinaryTree();
		
		//while there's a next element
		while (regFile.hasNextInt()) {
			
			//add the next element to a parrot, then insert the parrot to the tree
			int id = regFile.nextInt();
			String name = regFile.next();
			String songPhrase = regFile.nextLine();
			
			Parrot parrot = new Parrot (id, name, songPhrase);
			tree.insertParrot(parrot);	
		}	
		
		//sort tree level order
		tree.levelOrder();
		
		//close file
		regFile.close();
	}	
}


/*
 * Represents one parrot
 */
class Parrot {
	
	//initialize variables
	private int id;
	private String name;
	private String songPhrase;
	
	//constructor
	public Parrot (int id, String name, String songPhrase) {
		
		this.id = id;
		this.name = name;
		this.songPhrase = songPhrase;
		
	}
	
	//getter
	public String getName () {
		return name;
	}
	
	//getter
	public String sing () {
		return songPhrase;
	}
	
	//compares parrot to another parrot
	public int compareTo (Parrot p) {
		
		if (p.id > this.id) {
			return -1;
		} else if (this.id > p.id){
			return 1;
		} else {
			return 0;
		}
		
	}
	
	
}

/*
 * A binary search tree constructed from nodes
 */

class BinaryTree {
	
	
	private TreeNode root;
	
	//constructor
	public BinaryTree () {
		
		this.root = null;
		
	}
	
	//inserts a parrot into the tree
	public void insertParrot (Parrot parrotToAdd) {
		
		//if tree is empty, set root equal to first parrot
		if (this.root == null) {
			TreeNode firstNode = new TreeNode(parrotToAdd);
			this.root = firstNode;
		//otherwise
		} else {
			TreeNode currentNode = root;
			TreeNode nextNode = root;
			//compare to other nodes until a blank spot is reached, then add the parrot to that spot
			while (nextNode != null) {
				//move right if greater than
				if (parrotToAdd.compareTo(currentNode.parrot) > 0) {
					nextNode = currentNode.right;
					//if not empty, iterate again
					if (nextNode != null) {
						currentNode = nextNode;
					//otherwise if empty, add parrot
					} else {
						TreeNode addNode = new TreeNode (parrotToAdd);
						currentNode.right = addNode;
					}
				//move less if less than
				} else if (parrotToAdd.compareTo(currentNode.parrot) < 0) {
					nextNode = currentNode.left;
					//if not empty, iterate again
					if (nextNode != null) {
						currentNode = nextNode;
					//otherwise, add the parrot
					} else {
						TreeNode addNode = new TreeNode (parrotToAdd);
						currentNode.left = addNode;
					}
				}
			}
		}	
	}
	
	//prints the parrots in level order
	public void levelOrder () {
		
		LinkedList <TreeNode> list = new LinkedList<TreeNode>();
		list.add(root);
		
		//while there's still an item in the linked list
		while (list.peek() != null) {
			//print the current node
			TreeNode currentNode = list.pop();
			System.out.print(currentNode.parrot.sing());
			//add the leaves to the root, left first then right
			if (currentNode.left != null) 
				list.add(currentNode.left);
			if (currentNode.right != null)
				list.add(currentNode.right);
		}
		
	}
	
	//public method calls private method
	public void visitLeaves () {
	visitLeaves(root);
	}
	
	//visits leaves in order left-parent-right
	private void visitLeaves (TreeNode aNode) {
		
		//calls visitLeaves on left leaf
		if (aNode.left != null) {
			visitLeaves(aNode.left);
		}
		//prints parent
		System.out.println(aNode.parrot.getName());
		
		//calls visitLeaves on right leaf
		if (aNode.right !=null) {
			visitLeaves(aNode.right);
		}
		
	}
	
	/*
	 * Represents one node in the binary tree 
	 */
	class TreeNode {
		
		private Parrot parrot;
		private TreeNode left;
		private TreeNode right;
		
		//constructor
		public TreeNode (Parrot parrot) {
			
			this.parrot = parrot;
			this.left = null;
			this.right = null;
			
		}
		
	}
}



