/*
 * Jerry Soong
 * Started: May 18, 2020
 * Ended: June 12, 2020
 */

//Package statement. Remove to run?
package game;


//Import statements.
import javax.swing.*;			//For all GUI related items.

import java.awt.Color;			//For Color constants.
import java.awt.Dimension;		//For JPanel size setting (better than JPanel.setSize, in experience).
import java.awt.Graphics;		//For, well, graphics and drawing circles.
import java.util.Arrays;		//For array manipulation (mainly cloning).
import java.util.Timer;			//For the Timer object, for in-game "ticks" with the TimerTask.
import java.util.TimerTask;		//For code to be run every "tick".
import java.util.Random;		//For the Random object to decide which enemy to spawn.



//The Game class, which combines all of the other object classes to run a game.
public class Game{

	
//A frame to hold the game in a GUI.
	private static JFrame frame = new JFrame("Game");
	
	
//The x and y values for the boundary of the game. Constants, since they are not to be changed.
	private static final int X_LENGTH = 700;
	private static final int Y_LENGTH = 700;
	
	
//An array to hold all of the enemies of the game (Enemy array, which can hold its subclasses as well).
	private static Enemy[] enemies = new Enemy[0];
	
//The player entity. Color of the player is customizable here.
	private static Player player = new Player(X_LENGTH, Y_LENGTH, Color.BLUE);
	
//A count integer, to count for when a new enemy should spawn.
	private static int count;
	
//The Timer object, to start and stop for the game the run and stop respectively (essentially, the "ticks" to the game).
	private static Timer timer;
	
	
//A long to hold the current time when the game starts. Used to compare with the current time at the end of the game to
//determine how many seconds the player has survived.
	private static long startTime;
	
	
//The Scores binary tree. Initialized immediately to allow the scores of previous rounds to be loaded for the scoreboard.
	private static Scores scores = new Scores();
	
	
//Constant string arrays. To supply JOptionPane OptionDialogs with string for the options. One for the starting menu,
//and another for the ending menu.
	private static final String[] START_OPTION = {"Start game", "Instructions", "Check Leaderboard", "Exit"};
	private static final String[] END_OPTION = {"Replay", "Check Leaderboard", "Exit"};
	
	
	
//A private (static) nested class, to act as the JPanel and allow graphics to be drawn onto it.
//Done after most variables, since it needs to reference some.
	private static class Canvas extends JPanel{
		
		/*
		 * Created for extending the JPanel.
		 */
		private static final long serialVersionUID = 1L;

		
	//Default constructor. Sets the preferred size with the Dimension object (better than JPanel.setSize in testing, gives more
	//proper bounds for certain reasons).
		public Canvas(){
			this.setPreferredSize(new Dimension(X_LENGTH, Y_LENGTH));
		}
		
		
	//The paintComponent method, called through Canvas.repaint and Canvas.revalidate methods. Paints the graphics of each
	//Entity character.
		public void paintComponent(Graphics g){
			
			
	//Calls JPanel's paintComponent method, to paint things onto the actual panel object?
			super.paintComponent(g);
			
	//A white background for clear vision of the enemies.
			this.setBackground(Color.WHITE);
			
			
	//For each entity, set the Graphics' paint color to the Entity's color, and then draw a filled oval with the Entity's
	//variables (at appropriate location, with the appropriate size - radius * 2 for diameter, for length and width).
			
	//Player has its own variable, so drawn it by itself.
			g.setColor(player.getColor());
			g.fillOval(player.getX() - player.getR(),
					player.getY() - player.getR(),
					player.getR()*2,
					player.getR()*2);
			
	//Use a for loop to loop through the enemies array, to draw all enemies.
			for (int i = 0; i < enemies.length; i++){
				g.setColor(enemies[i].getColor());
				g.fillOval(enemies[i].getX() - enemies[i].getR(),
						enemies[i].getY() - enemies[i].getR(),
						enemies[i].getR()*2,
						enemies[i].getR()*2);
			}
			
		}	//End of paintComponent method.
		
		
	}	//End of Canvas class.
	
	
//Create the Canvas object after the class declaration.
	private static Canvas canvas = new Canvas();
	
	
	
//The main. Load the Scores scoreboard, and call startMenu to bring up the start menu. The code continues from there.
public static void main(String args[]){
	scores.load();
	startMenu();
}





//The startMenu method. Mostly a helper method to bring up a start menu and act based on the option selected.
public static void startMenu(){
	

//Create an integer to hold the selection made from the OptionDialog. Also brings said menu up, with the START_OPTION constant
//string array shown before, with options: "Start game", "Instructions", "Check Leaderboard", "Exit" corresponding to integers
//0, 1, 2, 3 respectively.
	int select = JOptionPane.showOptionDialog(null,
			"Welcome to Game",
			"Main Menu",
			JOptionPane.DEFAULT_OPTION,
			JOptionPane.PLAIN_MESSAGE,
			null,
			START_OPTION,
			START_OPTION[0]);
	
	
//Using a switch-case statement over a if-else, because it fits better (and differ in efficiency only slightly).
	switch (select){

	
//For the "Start game" option, set up the GUI and start the game (via setGame method).
	case 0:
		setGui();
		setGame();
		break;
		
		
//For the "Instructions" option, display a MessageDialog listing the instructions of the game.
	case 1:
		JOptionPane.showMessageDialog(null,
				"Welcome User, to the game of dodging!"
				+ "\nYou will be controlling the blue circle, with the WASD keys."
				+ "\n(W for up, S for down, A for left, and D for right)"
				+ "\n\nThe goal of the game is to dodge the enemies (colored red, pink, orange) for as long as possible."
				+ "\nEach type of enemy is in a different color. Their behaviours are based on the type."
				+ "\nOnce you get hit, the game will be over."
				+ "\nAfterwards, the amount of seconds you survived for will be shown."
				+ "\nThen, you'll be prompted to input your name, in the possible case you're in the Top 10."
				+ "\n\nYou can use the ESC key to exit the game during play."
				+ "\n\nGood luck!");
		
//Recur back to this method, for a simple and easy loop.
		startMenu();
		break;
	
		
//For the "Check Leaderboard" option, open a MessageDialog with the string representation of the Scores class, to show the top 10
//score (or the lack of).
	case 2:	
		JOptionPane.showMessageDialog(null, scores, "Leaderboard", JOptionPane.PLAIN_MESSAGE);
		
//Recursion for an easy loop.
		startMenu();
		break;
		
		
//For the "Exit" option, or if one closes the MessageDialog (outputs -1), exit the program.
	default: 
		System.exit(0);
		break;
		
	}	//End of switch-case statement.
	
}	//End of startMenu class.





//The endMenu method. Displays a menu after a round ends (you get hit).
public static void endMenu(){
	
	
//Same as the other menu, create an integer for the selected option. Options "Replay", "Check Leaderboard", "Exit" output
//0, 1, 2 respectively.
	int select = JOptionPane.showOptionDialog(null,
			"Try again?",
			"Replay game?",
			JOptionPane.DEFAULT_OPTION,
			JOptionPane.PLAIN_MESSAGE,
			null,
			END_OPTION,
			END_OPTION[0]);
	
	
//Switch statement like the startMenu.
	switch (select){
	
	
//For "Replay", call setGame to reset the game. The GUI will still be set from before.
	case 0:
		setGame();
		break;

		
//For "Check Leaderboard", display the same MessageDialog as startMenu.
	case 1:
		JOptionPane.showMessageDialog(null, scores, "Leaderboard", JOptionPane.PLAIN_MESSAGE);
		
//Recursion for easy loop.
		endMenu();
		break;
	
		
//For "Exit" and closing the OptionDialog, exit the program.
	default:
		System.exit(0);
		break;
	}

}	//End of endMenu method.





//The setGame method. Sets the game values for the start of the game.
public static void setGame(){
	
//Reset the player's values.
	player.reset();
	
//Clear out the enemy array.
	enemies = new Enemy[0];
	
//Redraw the JPanel (canvas object which inherits it) for safety.
	canvas.repaint();
	canvas.revalidate();
	
//Add a new enemy and start the timer.
	add();
	setTimer();
}





//The setGui method. Sets up the GUI.
public static void setGui(){


//Add the Canvas nested class (which is a JPanel through inheritance) to the frame.
	frame.add(canvas);
	
//Pack the frame to the JPanel for sizing.
	frame.pack();
	
//Move the frame to an okay location on the monitor (nobody likes playing a game in the top left corner).
	frame.setLocation(300, 150);
	
//setResizable to false, to avoid having the users resize the JFrame and see weird results (game is bounded to a static size).
	frame.setResizable(false);
	
//Layout manager to null to avoid weird issues.
	canvas.setLayout(null);
	
//Add the KeyListener of the player to the frame, for key actions.
	frame.addKeyListener(player.getKeyListener());
	
//Set the default close operation to exit the program when the frame closed.
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setVisible(true);
}





//The setTimer method. Sets up the Timer object to start the game's "ticks", executing code per "tick".
public static void setTimer(){
	
	
//Set the counter integer to 0.
	count = 0;
	
//Save the starting time for the score.
	startTime = System.currentTimeMillis();
	
//Create a new Timer object (for starting the timer reasons).
	timer = new Timer();	
	
	
//Set the timer to run the task every 5 milliseconds, with 0 delay.
	timer.scheduleAtFixedRate(new TimerTask(){
		
//The method that is executed every "tick".
		@Override
		public void run() {
			
			
//Move the entities of the game, via the move method (Player by itself, enemies via looping the array).
			player.move();
			for (int i = 0; i < enemies.length; i++){
				enemies[i].move();
			}
			

//After moving the entities, call the detection (helper) method to see if the player has collided with an enemy.
			detection();
			
			
//Assume the game does not stop, check if the counter has surpassed 300 (300 "ticks", therefore every 15 seconds), while
//incrementing the counter for efficiency.
			if (count++ >= 300){
				
//If true, add a new enemy and reset the counter to 0.
				add();
				count = 0;
			}
			
			
//Call repaint and revalidate to draw on the JPanel/Canvas class.
			canvas.revalidate();
			canvas.repaint();
			
			
		}	//End of run method of TimerTask.
		
	},	//End of TimerTask declaration.

//As stated before, delay 0 at a rate of 5 milliseconds.
	0, 5);
	
	
}	//End of setTimer method.





//The detection method. Checks if the player has collided with any enemies, and stops the game if so.
public static void detection(){
	

//For loop to loop through each enemy in the enemies array.
	for (int a = 0; a < enemies.length; a++){	
	
		
//Use the Player class' hitDetect method to check if it has collided with the enemy of the current iteration.
		if (player.hitDetect(enemies[a])){
			
		
//If so, immediately stop the timer to stop the game.
			timer.cancel();
			
			
//Calculate the amount of seconds the player has survived for by comparing the start time, with the current time.
//(Subtract the current time with start to get the difference in milliseconds * 10???, divide by 1000 to get the seconds.
			int seconds = (int)((System.currentTimeMillis() - startTime)/1000);
			
			
//Display a message to tell the user that they got hit and their score.
			JOptionPane.showMessageDialog(null, "You got hit!\nYour score is: " + (seconds));
			

//Prompt the player to enter their name and save it to a variable.
			String name = JOptionPane.showInputDialog(null, "Enter your name.");
			
//Check if the name is null (closed the InputDialog). If so, set the name to N/A. Prevents the line just after from crashing.
			if (name == null){
				name = "N/A";
			}
			
//Check if the name is just spaces or blank, and fill it with N/A if so. Otherwise, leave it as is.
//This is mostly to prevent blank names.
			name = name.trim().equals("") ? "N/A" : name; 
			

//Add the name with the score of seconds to the binary tree of Scores.
			scores.add(name, seconds);
			
//Promptly save the tree to the text file.
			scores.save();
			
			
//Show the Leaderboard, with potential changes with the new score.
			JOptionPane.showMessageDialog(null, scores, "Leaderboards", JOptionPane.PLAIN_MESSAGE);
			
		
//Call the endMenu method to display the ending menu for more actions.
			endMenu();
			
			
		}	//End of if statement.
		
	}	//End of for loop.
	
}	//End of detection method.





//The add method. Adds a new enemy to the game.
public static void add(){
	
//Expand the enemies array with the copyOf method from the Arrays class, which creates a copy (with all contents) of the array,
//with the described length, which in this case, is one greater than the enemies array.
	enemies = Arrays.copyOf(enemies, enemies.length + 1);
	
	
//Create and initialize an enemy to add to the array.
	Enemy e = new Cross(X_LENGTH, Y_LENGTH);
	
	
//Use a switch statement to determine if the enemy is to be which type. I use a switch-case statement over a if-else, since
//this allows for easier changes in chances per enemy type. There is barely a difference in efficiency.
	
//Generate a number between 1 and 10 inclusive, to decide.
	switch (new Random().nextInt(10) + 1){
	case 1:
	case 2: 
	case 3:	e = new Diagonal(X_LENGTH, Y_LENGTH); break;			//From 1-3 (30% chance), create a Diagonal enemy.
	case 4: e = new Chase(X_LENGTH, Y_LENGTH, player); break;		//For 4 (10% chance), create a Chase enemy.
	case 5:	
	case 6:
	case 7:
	case 8:
	case 9:
	case 10:
	default: break;		//For all other numbers and default (in case, 60% chance), do not change the enemy (thus Cross enemy).
	}
	

//Add the enemy to the array.
	enemies[enemies.length - 1] = e;

	
//Initialize their movement AI.
	enemies[enemies.length - 1].setMove();
	
}	//End of add method.


}	//End of Game class.