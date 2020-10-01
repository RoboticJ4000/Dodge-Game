/*
 * Jerry Soong
 * Started: May 18, 2020
 * Ended: June 12, 2020
 * Summative Dodge Game
 */

//Package statement. Remove to run?
package game;


//Import statements.
import java.awt.Color;					//For Color constant.
import java.awt.event.KeyEvent;			//For KeyEvents for the listener.
import java.awt.event.KeyListener;		//For its own KeyListener.



//The Player class. The Player controlled Entity (inherits from it), tailored to have easier implementation for any program.
public class Player extends Entity{

	
//Various booleans required from smoother movement, each corresponding to one of the four cardinal directions.
	private boolean upBol = false;
	private boolean leftBol = false;
	private boolean downBol = false;
	private boolean rightBol = false;
	
	
//For customization, I have char variables to hold the chars of the keys used for movement. This is to allow better
//implementation for potential multiplayer (using different keys per player) or customization (none of which is used :P).
	private char up;
	private char left;
	private char down;
	private char right;
	
	
	
	
//A KeyListener object, to give each Player object its own KeyListener. Could technically be set to final.
//Initialized outside of constructor, since it'll be the same regardless of constructor.
	private KeyListener keyMove = new KeyListener() {

		
//keyPressed method, called whenever a key is pressed. Closes the program if the Escape key is pressed, for a shortcut.
		@Override
		public void keyPressed(KeyEvent e) {
		
//Simple if statement for checking if the key is indeed the Escape key.
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
				System.exit(0);
			}
		}

		
//keyReleased method, called whenever a key is released (lift from press). "Resets" the speed based on the key released.
		@Override
		public void keyReleased(KeyEvent e) {
			
//The helper method for reseting the speed. Takes the char of the released key.
			resetSpeed(e.getKeyChar());
		}

		
//keyTyped method, called whenever a character is "typed". Sets the speed of the Player entity depending on the key pressed.
//Used over the keyPressed event since 1) both need to be written for the KeyListener, and 2) keyTyped only runs for character
//keys, while keyPressed runs for all keys.
		@Override
		public void keyTyped(KeyEvent e) {
			
//Helper method for setting the speed. Takes the char of the "typed" character key.
			setSpeed(e.getKeyChar());
			
		}
		
	};	//End of KeyListener initialization.
	
	
	
	
//First constructor, with bounds and color arguments (color argument to allow for different colors per player, in cases of
//multiplayer being implemented). Is more "default" than the other constructor.
public Player(int bx, int by, Color c){
	
//Calls the superconstructor (of Entity) with the arguments and size (radius) of 5 units.
	super(bx, by, c, 5);
		
//Sets the char instance variables to default chars, of the WASD pattern (classic keyboard keys for movement in video games).
	this.up = 'w';
	this.left = 'a';
	this.down = 's';
	this.right = 'd';
}
	

//Second constructor, featuring four more char arguments, on top of the three arguments of the first constructor
//(bounds and color).
public Player(int bx, int by, Color c, char up, char left, char down, char right){
	
//Again, calls the superconstructor to fill in the bounds, color, and size values.
	super(bx, by, c, 5);
	
//Set the char values to the corresponding char instance variables, in order of up, left, down, right.
	this.up = up;
	this.left = left;
	this.down = down;
	this.right = right;
}
	


//setSpeed helper method. Takes the char of the typed key from keyTyped and sets the speed accordingly.
public void setSpeed(char key){

	
//Compares the char of the typed key to the char instance variables. If equal, set the speed to the corresponding direction,
//and set the associated boolean to true. This boolean is used more in-depth in resetSpeed method.
	
//Up is negative y.
	if (key == up){
		this.setSpeedy(-1);
		upBol = true;
	
//Left is negative x.
	} else if (key == left){
		this.setSpeedx(-1);
		leftBol = true;

//Down is positive y.
	} else if (key == down){
		this.setSpeedy(1);
		downBol = true;
	
//Right is positive x.
	} else if (key == right){
		this.setSpeedx(1);
		rightBol = true;
	}
}



//resetSpeed helper method. Resets the speed to 0 or its opposite value depending on the key released, and the keys still held
//down.
public void resetSpeed(char key){

//Like the setSpeed method, compare the chars with the instance variable counterparts. Reset the speed depending on the char's
//direction's opposite boolean. If the boolean is true, reset the speed to the opposite direction. If false, reset the speed
//to 0. Regardless of outcome, reset the direction's boolean to false upon release of the key.
	
//Up's opposite is down.
	if (key == up){
		if (downBol == true){
			this.setSpeedy(1);
		} else {
			this.setSpeedy(0);
		}
		upBol = false;
		
//Left's opposite is right.
	} else if (key == left){
		if (rightBol == true){
			this.setSpeedx(1);
		} else {
			this.setSpeedx(0);
		}
		leftBol = false;
		
//Down's opposite is up.
	} else if (key == down){
		if (upBol == true){
			this.setSpeedy(-1);
		} else {
			this.setSpeedy(0);
		}
		downBol = false;
	
//Right's opposite is left.
	} else if (key == right){
		if (leftBol == true){
			this.setSpeedx(-1);
		} else {
			this.setSpeedx(0);
		}
		rightBol = false;
	}
}
	

//Out Of Bounds method. Overrides Entity's abstract. Moves the Player one tile back should they approach the walls.
public void oob(){
	
//For x = 0 or y = 0, set the corresponding position to 1.
	if (this.getX()< 0){
		this.setX(1);
		
	} else if (this.getY() < 0){
		this.setY(1);
		
//For x = bound of x or y = bound of y, set corresponding position to 1 less than the bound.
	} else if (this.getX() > this.getBoundx()){
		this.setX(this.getBoundx() - 1);

	} else if (this.getY() > this.getBoundy()){
		this.setY(this.getBoundy() - 1);
	}
}


//reset method. Resets the Player entity's values to default values for the start of a new game/round.
public void reset(){
	
//Reset position for the start of a new game/round.
	this.setX(this.getBoundx()/2);
	this.setY(this.getBoundy()/2);
	
//Reset speed to not carry over the speed (thus causing the Player entity to move on its own for the start of the game).
	this.setSpeedx(0);
	this.setSpeedy(0);
	
//Reset the boolean values, so movement doesn't become skewed.
	upBol = downBol = leftBol = rightBol = false;
}


//hitDetect helper method. Returns a boolean based on if the CENTER (based on how the entities are drawn on the GUI) of the Player
//entity is within a box around the argument Entity's center (will look more like within their circle).
public boolean hitDetect(Entity e){
	
//Simply return a boolean of if the Player's x and y values are within the Entity's diameter.
//Basically: if (e.x - e.r < p.x < e.x + e.r && e.y - e.r < p.y < e.y + e.r).
	return (this.getX() > e.getX() - e.getR() &&
			this.getX() < e.getX() + e.getR() &&
			this.getY() > e.getY() - e.getR() &&
			this.getY() < e.getY() + e.getR());
}


//Getter method for the KeyListener, so the JPanel of the Game class can add the listener to itself, since all instance variables
//are private.
public KeyListener getKeyListener(){
	return keyMove;
}
}
