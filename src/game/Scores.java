/*
 * Jerry Soong
 * Started: May 18, 2020
 * Ended: June 12, 2020
 * Summative Dodge Game
 */

//Package statement. Remove to run?
package game;


//Import statement to import java.io.
import java.io.*;



//The Scores class. A binary tree to hold the scores of the players of the game. Is saveable through File IO.
public class Scores implements java.io.Serializable{

	
//A recommended, generated variable, due to java.io.Serializable for File IO. In basic terms, ensures the class is inputed,
//outputted consistently, so no errors occur.

	private static final long serialVersionUID = 1L;

	
//The Node, private nested class. Also serializable, so that the binary tree can be complete serializable (has Node variable).
	private class Node implements java.io.Serializable{
		
		
//Same reason as the binary tree's (Scores') serialVersionUID, above.
		private static final long serialVersionUID = 1L;
			
//As with binary tree nodes, a left and right pointer instance variable, to point to child nodes.
		private Node left;
		private Node right;
		
//The cargoes. A name of the player, as well as the score they got (like traditional arcade machines).
		private String name;
		private int score;
		
		
//The only constructor, with variables. Takes in a string for the name, and an integer for the score. Also sets the left, right
//children as null, as a placeholder (since there are no cases of immediate children nodes on construction).
	public Node(String n, int s){
		this.name = n;
		this.score = s;
		this.left = this.right = null;
	}
	
	
//The only method of the Node objects, toString, for automatic string output.
	public String toString(){
		
//Output the name, followed by the score, spaced after (before with indicators).
		return "Name: " + this.name + "     Score: " + this.score;
	}
	
	} //End of Node nested class.
	
	
	
	
	
//The binary tree's (Scores') own instance variables.
	private Node root;			//The traditional root variable, to have reference to the root node.
	private int count;			//A counting variable, used for outputting only a select number of nodes.
	
	
	
//Default constructor. Creates an empty binary tree (primarily for creating new save data).
public Scores(){
	this.root = null;
	this.count = 0;
}



//The add method. Adds a new node with the given arguments, in a sorted position (left as greater, right as less).
public void add(String name, int score){
	
	
//Create the new node to be added to the tree (to avoid rewriting things). It'll be added regardless.
	Node newNode = new Node(name, score);
	
	
//If the root is null (ie tree empty), add the node as the root and end the method with the return keyword (adding an else to
//the if statement could've also worked).
	if (this.root == null){
		this.root = newNode;
		return;
	}
	
	
//If the root was not null, create a node pointer variable of n, to reference various nodes, starting at the root as usual.
	Node n = this.root;
	
	
	
//Use iteration, since recursion is overrated. I jest. I didn't want to write another helper method was the main reason.
//Infinite for, to keep looping until the new node is set.
	for(;;){
		
		
//Should the new node's score (checked as the argument) be larger than the score of the reference node, check the left child.
		if (score > n.score){
			
//If the left child is null (no node there), place the new node there. Use return to exit the method to avoid the other code.
			if (n.left == null){
				n.left = newNode;
				return;
				
//If the left child is not null, move to said node and repeat the comparisons.
			} else {
				n = n.left;
			}
	
			
//Should the new node's score be equal to or smaller than the referenced node, check the right child.
//(Note: this means for any tie in score, the early score is placed higher.
		} else if (score <= n.score){
			
//Same as the left node - if null, add the new node as the right child. Use return to exit the method, for efficiency.
			if (n.right == null){
				n.right = newNode;
				return;
				
//If the right child is not null, move the reference to said node and repeat.
			} else {
				n = n.right;
			}
			
		}	//End of if-else (comparing new node to reference node).	
	
	
	}	//End of for loop.
	
}	//End of add method.





//toString method, to output string representation of the tree.
public String toString(){
	
//Set the counter instance variable to 10, for limiting reasons.
	count = 10;
	
	
//For a case where the toString is called while empty (ie new save is created, and the leaderboard is checked), output a
//message stating the fact. Encapsulation exmaple!
	if (this.root == null){
		return "No scores available. Go play to make some!";
		
		
//Otherwise, return the string returned by the inOrder helper method. It'll output the top 10 scores with names.
	} else {
		return inOrder(this.root);	
	}
	
	
}



/*
 * inOrder helper method (for toString).
 * Uses in-order traversal (left, node, right) to output string of the top 10 scores. It does this by simply printing in order,
 * with a counter which stops the method after 10 have been printed. As the tree is already structured to hold the values from
 * greatest to least, left to right, simply traversing in-order (thus greatest to least) with a counter is enough to get only
 * the top 10 scores w/ names. Note that the tree is not actively balanced as values are added, the printing of the tree
 * may get slower and slower if consistently greater values are added to the tree (takes more recursions to get to the bottom).
 * 
 * Uses recursion to traverse. Private, since it is only to be accessed through toString.
 */
private String inOrder(Node n){
	
	
//To output all of the string at once, use a temporary string.
	String temp = "";
	
	
//As in-order traversal would go, move to the left node immediately, given that the left node is not null, and that the counter
//is still above 0 (10 nodes have not been printed yet). Add its return to the temporary string to return it all at once.
	if (n.left != null && count > 0){
		temp += inOrder(n.left);
	}
	
	
//A preceding check to check if counter has reach 0 (or below?), and to return the string if so (skipping the current node).
//Since it is in-order and sorted, there is no way the right node has a higher score than the parent. Therefore, a return is
//slightly more efficient, by skipping the next if statement (does the comparison if not skipped).
	if (count <= 0) return temp;
	
	
//Should the code pass the check, print the current node to the temporary string (decrement the counter while at it).
//Also prints a number, indicating place.
	temp += (10 - count-- + 1) + ". " + n.toString() + "\n";
	
	
//Same as the left child, the right child is accessed as the new reference node should it not be null, and the counter is 
//still above 0. Add the returned string to the temporary string.
	if (n.right != null && count > 0){
		temp += inOrder(n.right);
	}
	
	
//After both left, the node itself, and right children have been checked, return the temporary string to the preceding recursion.
	return temp;
	
}	//End of inOrder helper method.






//printPaths tester method. Used to print the paths for testing.
public void printPaths(){
	
//If the root is null (tree empty), state so.
	if (this.root == null){
		System.out.println("No tree, therefore no paths");
		
//Otherwise, call the "helper" method to output the actual strings of the path.
	} else {
		printPathRecur(this.root, new int[0]);
	}
}


//printPathRecur "helper" method. The actual bulk of the path printing. Prints the paths of the binary tree in string (only the
//scores to make it simple). Private, as this is not to be called outside of the class.
private void printPathRecur(Node n, int[] path){
	
	
//Per recursion, expand the array (shown above to start at 0) to add the score of the currently referenced node.
//Essentially, the new array to replace the argument array.
	int[] expand = new int[path.length + 1];
	
//Copy all of the old array's contents to the new array.
	for (int j = 0; j < path.length; j++){
		expand[j] = path[j];
	}
	
//Add the currently referenced node's score to the last index of the array.
	expand[expand.length - 1] = n.score;
	
	
	
//If both children are null (leaf, dead end node), print the path.
	if (n.left == null && n.right == null){
		
//To print the path, loop through the array, printing the scores (contents of the array) with spaces after.
		for (int i = 0; i < expand.length; i++){
			System.out.print(expand[i] + " ");
		}
		
//Move to a new line for the other paths.
		System.out.println();
		
//Return to exit the method on this recursion (efficient in skipping the other if statements).
		return;
	}
	
	
	
//If the left node is not null, move to it via recursion and the reference node argument.
	if (n.left != null){
		printPathRecur(n.left, expand);
	}
	
	
//If the right node is not null, move to it via recursion and the reference argument. In a separate if to avoid mutual exclusion.
	if (n.right != null){
		printPathRecur(n.right, expand);
	}
	
}	//End of printPathRecur method.





//The save method. Saves (outputs) the binary tree to the text file "scores.txt".
public void save(){
	
//Encapsulate to avoid errors and crashing.
	try{
		
		
//Create the output stream (path?) to the text file.
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("scores.txt"));
		
//Using the stream, write the object which calls the save method into the text file.
		out.writeObject(this);
		
//Close the stream to avoid leaks.
		out.close();
		
		
//Catch the error of the file being missing.
	} catch (FileNotFoundException e) {
		System.err.println("Error: file not found");	//Seems to create the "scores.txt" file if not found.
		e.printStackTrace();
		
//Catch if the output action cannot be performed for some reason.
	} catch (IOException e) {
		System.err.println("Error: cannot output to file");
		e.printStackTrace();
	}
	
}



//The load method. Loads the saved tree in the "scores.txt" text file to the object which calls the method.
public void load() {
	
//Encapsulation to avoid crashing due to errors.
	try{
	
		
//Create the input stream (path?) from the text file.
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("scores.txt"));

//Overwrite the tree's root with the root from the tree found in the text file, to completely overwrite the current tree with
//the one loaded from the text file (count integer is not necessary - only used and is set within the toString method,
//and is initialized within the constructor).
		this.root = ((Scores)in.readObject()).root;
		
//Close the stream to avoid leaks.
		in.close();
		
		
//Catch the error of being unable to find the text file.
	} catch (FileNotFoundException e) {
		System.err.println("Error: file not found");
		e.printStackTrace();

		
//Catches an exception of if nothing can be loaded from the save.
	} catch (IOException e) {
		
//Notify the player of no save being found, and inform them of how a new one will be created.
		System.err.println("Error: existing save not found\nA new save will be created once a score is entered.");
		
		
//In the case that the saved object is not of this (Scores) class (which should not happen within the Game code, unless another
//program created uses the same text file), catch the error and output the error message.
	} catch (ClassNotFoundException e) {
		System.err.println("Error: class not found");
		e.printStackTrace();
		
	}	//End of try-catch method.
	
}	//End of load method.

}	//End of Scores class.