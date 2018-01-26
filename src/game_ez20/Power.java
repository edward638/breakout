/**
* The purpose of this class is to define some common behavior that can be inherited
* by specific power subclasses, such as ExtraLife (which grants an extra life) and 
* PowerDownSpeed (which speeds up the ball). Previously, this was not an abstract class, and contained a
* instance variable PowerType, which was compared against conditionals within the activatePower
* method to activate a specific power. I decided to refactor this code to better implement the "Tell, Don't Ask" 
* principle and to make powers more flexible. Thus, when someone wants to add a new power, they do not have
*  to edit the power class; instead, they just need to make an additional subclass.
* Another thing I added was methods which removed and added the ImageView of the power onto a root. 
* Previously, I had a separate class which accessed the power object's ImageView, which violated 
* the rules of object oriented programming. 
*/
package game_ez20;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public abstract class Power extends Item {
	private int speed;	
	protected ImageView imageview;
	
	/**
	 * Constructs a power with a given location and speed.
	 * 
	 * @param startX starting X position of this power
	 * @param startY starting Y position of this power
	 * @param startSpeed speed at which the power will fall
	 */
	public Power(int startX, int startY, int startSpeed) {
		speed = startSpeed;
		xCoord = startX;
		yCoord = startY;
	}
	
	/**
	 * Updates the position of the power over time.
	 * 
	 * @param elapsedTime time passed which affects how far the power moves
	 */
	public void update(double elapsedTime) {
		imageview.setY(imageview.getY() + speed * elapsedTime);
	}

	/**
	 * Activates a specific power.
	 * 
	 * @param ball ball affected by the powers
	 * @param paddle paddle affected by the powers
	 * @param root root where ImageViews are
	 * @param label label which is updated based on what power is activated
	 */
	public abstract void activatePower(Ball ball, Paddle paddle, Group root, Label label);
	
	/**
	 * Removes ImageView of power
	 * 
	 * @param root root where ImageViews are
	 */
	public void remove(Group root) {
		root.getChildren().remove(imageview);
	}
	
	/**
	 * Adds ImageView of power
	 * 
	 * @param root root where ImageViews are
	 */
	public void draw(Group root) {
		root.getChildren().add(imageview);
	}
}
