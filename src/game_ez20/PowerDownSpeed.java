/**
* I am submitting this subclass to show the power class in "action". In my constructor 
* method, I use the constructor included within the abstract class and also set up the ImageView 
* with an image unique to this specific power down. Furthermore, I also increase the size of the ImageView,
* as all my power downs are bigger in size than power ups.
* My activatePower method overrides the abstract method within the Power class. For each subclass, 
* the activatePower method will perform a different action. For this PowerDownSpeed class, activatePower
* will increase the speed of the ball four times, set how long this effect takes place, edit the label to indicate
* what power down was just activated, and remove the Power's ImageView from the root.
*/
package game_ez20;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PowerDownSpeed extends Power {
	private Image powerDown = new Image(getClass().getClassLoader().getResourceAsStream("laserpower.gif"));

	/**
	 * Constructs a power with a given location and speed. Sets up the ImageView of
	 * the power.
	 */
	public PowerDownSpeed(int startX, int startY, int startSpeed) {
		super(startX, startY, startSpeed);
		imageview = new ImageView(powerDown);
		imageview.setX(xCoord);
		imageview.setY(yCoord);
		imageview.setFitHeight(imageview.getBoundsInParent().getHeight() * 5);
		imageview.setFitWidth(imageview.getBoundsInParent().getWidth() * 5);
	}

	/**
	 * @see Power#activatePower(Ball, Paddle, Group, Label)
	 */
	@Override
	public void activatePower(Ball ball, Paddle paddle, Group root, Label label) {
		for (int x = 0; x < 4; x++) {
			ball.increaseSpeed();
		}
		ball.addSpeedTime();
		label.setText("POWER DOWN! BALL SPEED INCREASED!");
		remove(root);
	}
}
