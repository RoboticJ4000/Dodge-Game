/*
 * Jerry Soong
 * Started: May 18, 2020
 * Ended: June 12, 2020
 * Summative Dodge Game
 */

//Package statement. Remove to run?
package game;


//Import statement for Color (to use its constants).
import java.awt.Color;


/*
 * The Chase class, for Chase-type enemies. They "chase" the player, traveling in diagonals while occasionally changing their
 * trajectory to move towards the player, either along the x or y axis randomly. This split between the x and y, and only having
 * one of them updated per number of ticks gives them a layer of randomness, and also allows the update rate to be faster.
 * They may move far away from the player or chase the player closely. Always starts at the top-left corner.
 */
public class Chase extends Enemy {

	
//Instance variables exclusive to this (Chase) enemy type.
//A count variable to count for when the Chase enemy is able to change its trajectory.
	private int count;
	
/*
 * The Player variable. Since it's meant to change its trajectory according to its position relative to the player, and
 * that all variables are to be private (and I wanted classes to be reusable outside of this package), a Player variable is
 * required to process its x, y coordinates (also it acts as a pointer to the Player object of the Game class, actively
 * transferring the x,y coordinates automatically). The variable also does not need to be a Player, as an Entity object could
 * be used instead (which could make for some interesting behaviour).
 */
	private Player p;
	
	
//Like the other enemy types, the only constructor with variable, only taking the bounds as arguments, making the appearance
//consistent amongst all of the type.
public Chase(int bx, int by, Player p){
	super(bx, by, Color.MAGENTA, 7);
	count = 0;
	this.p = p;
}


//A Out Of Bounds override. Primarily to call setMove every amount of in-game ticks, to update the direction since the Out Of
//Bounds method is called every time the move method (in Entity class) is called. Alternatively, the move method (from the Entity
//grandparent superclass) could be overridden in a similar fashion.
public void oob(){
	
//Call the original super method (Enemy's version of oob), to update movement if the enemy hits the bounds of the game (GUI).
//Not truly necessary, though I thought it would be nice if the enemies didn't fall Out Of Bounds for an excessive amount of time.
	super.oob();
	
	
//Check if the counter has reached a total of 75 (or above), while incrementing up, all in one line. This makes it so that for
//every 75 ticks (in-game), the Chase enemy will modify its movement direction.
	if (count++ >= 75){
		setMove();
		
//Reset the counter to 0 for repeat.
		count = 0;
	}
	
}


//The setMove method, overridden from abstract. Randomly decides between the x and y directions to change towards the player.
public void setMove(){
	
	
//Using a random boolean, generated by the Random object, inherited from Enemy, decide between the x or y. If true, update x.
	if (this.getRandom().nextBoolean()){
		
//Compare the Chase enemy's x to the player's. If Chase's is greater than player's, set x speed to negative (one).
		if (this.getX() > p.getX()){
			this.setSpeedx(-1);
			
//If Chase's is less than player's, set x speed to positive (one).
		} else {
			this.setSpeedx(1);
		}
		
		
		
//If the boolean was false, update the y direction instead.
	} else {
		
//Same as the x: if the Chase's y position is greater than the player's, set y speed to negative (one).
		if (this.getY() > p.getY()){
			this.setSpeedy(-1);
			
//If Chase's y is less than the player's, set y speed to positive (one).
		} else {
			this.setSpeedy(1);;
		}
		
	}	//End of 1st if-else statement.
	
	
}	//End of setMove method.

}	//End of Chase class.