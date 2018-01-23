package game_ez20;

import java.util.Random;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// this class creates a brick object. Bricks are organized through a brickcontroller object. 
// bricks are not movable, are located somewhere in the top of the screen, and have health (number of hits required to break brick)

public class Brick extends Item {

	private int health;

	public Brick(Image image, int startX, int startY, int startHealth) {
		imageview = new ImageView(image);
		health = startHealth;
		xCoord = startX;
		yCoord = startY;
		movable = false;
		isOn = true;
		imageview.setX(xCoord);
		imageview.setY(yCoord);
	}

	// a brick will randomly drop a powerup or powerdown when broken
	
	public void ReleasePower(PowerController powerController, Group root) {
		Random rand = new Random();

		if (rand.nextInt(10) > 6) {
			powerController.AddPower(rand.nextInt(8)+1, xCoord, yCoord, rand.nextInt(100) + 50, root);
		}

	}
	
	public int getHealth() {
		return health;
	}
	
	public void loseHealth() {
		health--;
	}
}
