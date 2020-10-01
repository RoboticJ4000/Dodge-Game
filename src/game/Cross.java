/*
 * Jerry Soong
 * Started: May 18, 2020
 * Ended: June 12, 2020
 * Summative Dodge Game
 */

//Package statement. Remove this to run?
package game;

//Import statement for Color (to use its constants).
import java.awt.Color;


//Class statement. The Cross enemy type, the simplest type of enemy. Moves across the screen in a straight vertical or horizontal
//line, reappearing in a new location and trajectory when the pass out of bounds.
public class Cross extends Enemy{


//The only, with-variables constructor. Only requires the bounds of the game (GUI), making the Color and size (radius) consistent
//among all instanced Cross-type enemies for a consistent look. This type is slightly larger than the rest, to balance for their
//basic behaviour.
public Cross(int bx, int by){
	super(bx, by, Color.RED, 8);
}


//The only "new" method of the Cross class. All other methods are inherited.
//The setMove method, previously abstract, now non-abstract. Uses the Random object to determine a trajectory, and subsequently
//a starting location.
public void setMove() {
	

	
//If-else statements take advantage of the Random object generating a boolean. The first boolean decides if the Cross enemy
//will be traveling vertically or horizontally.
	if ((this.getRandom().nextBoolean())){

		
//As it is moving horizontally, reset the y (vertical) speed to 0.
		this.setSpeedy(0);
		
		
//Secondary if-else, deciding the horizontal direction: left or right.
//Sets the speed to the appropriate value, as well as setting the starting x value accordingly.
		if ((this.getRandom().nextBoolean())){
			this.setSpeedx(1);
			this.setX(0);
		} else {
			this.setSpeedx(-1);
			this.setX(this.getBoundx());;
		}
		
/*
 * Regardless of horizontal direction, the starting y position will dictate the location of its path, and is thus randomized.
 * 
 * The main reason why two booleans are used over a random number. Since both horizontal trajectories require a random y position
 * to start, it is more efficient to hold the position generation together with the second if-else, than to use an if-else
 * external to a switch-case.
 */
		this.setY(this.getRandom().nextInt(this.getBoundy()) + 1);
		
		
		
//If not horizontal, then the Cross enemy will travel vertically.
	} else {
		

//As it is moving vertically, reset the x (horizontal) speed to 0.
		this.setSpeedx(0);
		
//Another secondary if-else, this time deciding between the vertical directions, up or down.
//Sets the y speed and the starting x position accordingly.
		if ((this.getRandom().nextBoolean())){
			this.setSpeedy(1);
			this.setY(0);
			
		} else {
			this.setSpeedy(-1);
			this.setY(this.getBoundy());
		}
		
//Similar case with the horizontal directions. Both vertical directions require a random starting x position to dictate the
//location of its path.
		this.setX(this.getRandom().nextInt(this.getBoundx()) + 1);
	}	
	
}	//End of setMove method.


}	//End of Cross class.