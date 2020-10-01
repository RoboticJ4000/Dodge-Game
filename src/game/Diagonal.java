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


//The Diagonal (enemy type) class statement. Starts in a random position along the top or bottom of the game (GUI), and begins
//moving in a diagonal direction. Bounces off the walls in the game in 90 degree angles, instead of reappearing like Cross enemies.
public class Diagonal extends Enemy {


//The single constructor with variables. Like the Cross enemy, only the bounds are required, giving all Diagonal enemies a
//consistent look.
public Diagonal(int bx, int by){
	super(bx, by, Color.ORANGE, 7);
}


//The setMove method. Called when the enemy hits the "walls" of the game (GUI). Takes less if-else statements containing
//all of the decisions within setMove, rather than overriding the Out Of Bounds method and passing a string based on the wall.
public void setMove(){

	
//If the Diagonal enemy hits the left wall (x < 0), then it "bounces" off by setting its speed of x to 1
//(speed of x > 0 means moving right; makes sense as the RoC becomes positive when the x position is negative).
	if (this.getX() < 0){
		this.setSpeedx(1);
	
		
//If it hits the right wall (x > bound of x), "bounce" off by setting x speed to -1 (-1 means moving left).
	} else if (this.getX() > this.getBoundx()){
		this.setSpeedx(-1);
		
		
//If it hits the top wall (y < 0), "bounce" off by setting y speed to 1 (moves down).
	} else if (this.getY() < 0){
		this.setSpeedy(1);

		
//If it hits the bottom wall (y > bound of y), "bounce" off by setting y speed to -1 (moves up).
	} else if (this.getY() > this.getBoundy()){
		this.setSpeedy(-1);
		
		
//If none of the above, then the Diagonal enemy is being initialized from scratch.
	} else {
		
		
//Using a randomly generated number, decide the starting diagonal direction.
//Used a switch-case statement because why not. Not that much different than if-else.
		switch (this.getRandom().nextInt(4) + 1){
		
		case 1: this.setSpeedx(1); this.setSpeedy(1); break;		//1st case: SE
		case 2: this.setSpeedx(1); this.setSpeedy(-1); break;		//2nd case: NE
		case 3: this.setSpeedx(-1); this.setSpeedy(1); break;		//3rd case: SW
		default: this.setSpeedx(-1); this.setSpeedy(-1); break;		//default: NW
																	//(in case the random number is 4 or none of the above).
		}
			
		
//Using the set speed of y, determine if the Diagonal enemy will start at the top or bottom wall.
//If 1 (positive), that means the enemy will be moving diagonally down. Therefore, start at the top wall.
		if (this.getSpeedy() == 1) this.setY(0);
		
//If -1 (negative), that means it will be moving diagonally up. Thus, start at the bottom wall.
		else if (this.getSpeedy() == -1) this.setY(this.getBoundy());
		
//Regardless of the starting y position, randomize for a random x value within the bounds (0, bound of x), non-inclusive.
		this.setX(this.getRandom().nextInt(this.getBoundx() - 1) + 1);

	}	//End of if-else statement.
			
}	//End of setMove method.

}	//End of Diagonal class.