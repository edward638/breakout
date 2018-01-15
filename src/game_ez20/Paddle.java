package game_ez20;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class Paddle extends Item {

	public int speed;
	public int size;

	public Paddle(Image image, int startX, int startY, int startSpeed, int startSize) {
		imageview = new ImageView(image);
		xCoord = startX;
		yCoord = startY;
		speed = startSpeed;
		size = startSize;
		movable = true;
		isOn = true;
		imageview.setX(xCoord);
		imageview.setY(yCoord);
	}

	public void update(double elapsedTime) {

	}

	public void movePaddle(KeyCode code, Ball ball) {
		if (code == KeyCode.RIGHT) {
			if ((imageview.getX() + imageview.getBoundsInParent().getWidth()) < 500)
				imageview.setX(imageview.getX() + speed);
			if (ball.movable == false) {
				ball.movable = true;
				ball.xDirect = -1;
			}
		}
		if (code == KeyCode.LEFT) {
			if (imageview.getX() > 0)
				imageview.setX(imageview.getX() - speed);
			if (ball.movable == false) {
				ball.movable = true;
				ball.xDirect = 1;
			}
		}

	}
}
