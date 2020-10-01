/*
 * Jerry Soong
 * Started: May 18, 2020
 * Ended: June 12, 2020
 */

//Package statement. Remove this to run?
package game;


//Import statement for color.
import java.awt.Color;


//Class declaration. I make this an abstract class because the class is not to be instanced (has no distinct characteristics
//itself, unlike its subclasses), as well as access to abstract methods (for the same reason). The entity is the base for all
//"entities" (individual elements), holding all basic variables for them.
public abstract class Entity{	
	
//The instance variables. Private for convention.
//x, y as coordinates for location on the GUI (JPanel).
	private int x;
	private int y;
	
//Speeds for the respective coordinate axes. Dictate how far the entity will move in one "tick". Allows for smoother movement.
	private int speedx;
	private int speedy;
	
//Boundary values for the x and y coordinate axes. Will take on the GUI size.
	private int boundx;
	private int boundy;
	
//Color and radius, characteristics of the how the entity looks on the GUI. Radius also plays into the size of the entity,
//which is also used in gameplay to an extent.
	private Color color;
	private int r;
	
	
	
//Default constructor with default values. Abstract classes can have constructors, just that they cannot be called.
public Entity(){
	x = y = speedx = speedy = boundx = boundy = 0;
	color = Color.BLACK;
	this.r = 10;
}


//Constructor with variables. Excludes coordinate location and speed, as those are used in gameplay only (location will
//immediately updated as a start location anyways, depending on entity subclass).
public Entity(int bx, int by, Color c, int r){
	x = y = speedx = speedy = 0;
	boundx = bx;
	boundy = by;
	color = c;
	this.r = r;
}
	

//Move method, inherited and unchanged for all subclasses. Moves the entity based on the current speed, and calls "oob()", a
//method which checks primarily for if the entity reached Out Of Bounds (hence the short name) among other things.
public void move(){
	x += speedx;
	y += speedy;
	this.oob();
}


//The Out Of Bounds method. Abstract, as it polymorphic, based on the subclass (very different in "Enemy" and "Player").
public abstract void oob();





//All getter methods. Public for global access (no real harm).
public int getX(){
	return x;
}

public int getY(){
	return y;
}

public int getSpeedx(){
	return speedx;
}

public int getSpeedy(){
	return speedy;
}

public int getBoundx(){
	return boundx;
}

public int getBoundy(){
	return boundy;
}

public Color getColor(){
	return color;
}

public int getR(){
	return r;
}





//All setter (accessor) methods. Protected, so only the package has access. Only for variables which require changing for
//gameplay. Other variables do not require setters, and are thus unchangable.
protected void setX(int x){
	this.x = x;
}

protected void setY(int y){
	this.y = y;
}

protected void setSpeedx(int sx){
	this.speedx = sx;
}

protected void setSpeedy(int sy){
	this.speedy = sy;
}


}	//End of Entity class.