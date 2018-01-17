package game_ez20;

import java.util.Random;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Brick extends Item {

	int health;

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

	public void ReleasePower(PowerController powerController, Group root) {
		Random rand = new Random();
		// 10% of dropping a power up

		if (rand.nextInt(10) > 7) {
			powerController.AddPower(4, xCoord, yCoord, rand.nextInt(100) + 50, root);
		}

	}

}
