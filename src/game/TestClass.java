/*
 * Jerry Soong
 * Started: May 18, 2020
 * Ended: June 12, 2020
 * Summative Dodge Game
 */

//Package statement. Remove to run?
package game;


//A test class to test the binary tree, Scores, intensively.
public class TestClass {

	
//static variable for the class, because why not.
	static Scores score = new Scores();
	
	
/*
 * The only method, the main. Tests the function of the binary tree class, Scores. Inputs the following 14 "scores" (with names),
 * and prints the tree (top 10, as the code will enforce). Thus, the lowest 4 scores (indicated) should be cut off. As a result,
 * the following should be printed:
 * 
 * 1. Name: Jed     Score: 110
 * 2. Name: Tom     Score: 102
 * 3. Name: Frank     Score: 100
 * 4. Name: Sap     Score: 100
 * 5. Name: Chip     Score: 94
 * 6. Name: Simon     Score: 90
 * 7. Name: Bill     Score: 80
 * 8. Name: Ellis     Score: 70
 * 9. Name: Patrick     Score: 60
 * 10. Name: John     Score: 50
 * 
 * (More nodes may be added by your own discretion, to test the top 10 capabilities)
 * 
 * Afterwards, the tree calls the printPaths method to show the paths of the tree. This is to demonstrate that the nodes are
 * set properly.
 */
public static void main(String args[]){

	
//Add the 14 "scores" (with names).
	score.add("Frank", 100);	//1
	score.add("Sap", 100);		//2		Lower than Frank, since this is inputed later.
	score.add("John", 50);		//3
	score.add("Chip", 94);		//4
	score.add("George", 10);	//5		Lowest
	score.add("Simon", 90);		//6
	score.add("Fred", 20);		//7		2nd lowest
	score.add("Tom", 102);		//8
	score.add("Wesker", 30);	//9		3rd lowest
	score.add("Patrick", 60);	//10
	score.add("Tony", 40);		//11	4th lowest
	score.add("Ellis", 70);		//12
	score.add("Bill", 80);		//13
	score.add("Jed", 110);		//14

	
//Print the top 10 nodes, in string fashion, shown in-game.
	System.out.println(score);
	
	
//Print the paths for demonstration of proper location setting.
	score.printPaths();
}
}
