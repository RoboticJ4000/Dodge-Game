/*
 * Jerry Soong
 * Started: May 18, 2020
 * Ended: June 12, 2020
 * Summative Dodge Game
 */

//Package statement. Remove to run?
package game;


//Import statements for Color and the Random class (Java class for generating (psuedo) random numbers/values).
import java.awt.Color;
import java.util.Random;


//Class statement. Also abstract for abstract methods. Is also not to be instanced, as it is without distinct characteristics
//and instead describes characters for most (if not all) of its subclasses.
public abstract class Enemy extends Entity{	
	
//One unique variable to enemies, over players. A Random object for random value generation for the enemies to use to dictate
//movement or start positions. Initialized outside of constructor, since it will be a new Random object regardless of constructor.
	private Random rdm = new Random();

	
//Default constructor. Call the default super constructor and instance a new Random object.
public Enemy(){
	super();
}


//Constructor with variables. Simply call the super constructor with variables, and instance a new Random object.
public Enemy(int bx, int by, Color c, int r){
	super(bx, by, c, r);
}


//Creating an actual Out Of Bounds method, for computer-controlled enemies. 2/3 enemies would use a system of checking if
//the enemy has left the bounds of the game (GUI), and called the "setMove" method if so.
public void oob(){
	if (this.getX() < 0 || this.getX() > this.getBoundx()|| this.getY() < 0 || this.getY() > this.getBoundy()){
		this.setMove();
	}
}


//Setting the movement of the enemies is used for all enemy types, while also being different for each. Therefore create
//and abstract method so that it can be called for type Enemy, while being polymorphic (also not needed for a base enemy with no
//specific characteristics).
public abstract void setMove();


//Getter method for the Random object instance variable, mainly for its subclasses.
public Random getRandom(){
	return rdm;
}


}	//End of Enemy class.