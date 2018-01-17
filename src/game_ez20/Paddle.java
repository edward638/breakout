package game_ez20;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class Paddle extends Item {

	public int speed;
	public int size;
	public int stretchPaddleTime = 0;
	public boolean vertical = false;
	public double ogWidth;

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
		ogWidth = imageview.getBoundsInParent().getWidth();
	}

	public void update(double elapsedTime) {

	}

	public void movePaddle(KeyCode code, Ball ball, int XSIZE, int YSIZE) {
		if (code == KeyCode.RIGHT) {
			if ((imageview.getX() + imageview.getBoundsInParent().getWidth()) < XSIZE)
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

		if (vertical) {
			if (code == KeyCode.DOWN) {
				if ((imageview.getY() + imageview.getBoundsInParent().getWidth()) < YSIZE)
					imageview.setY(imageview.getY() + speed);
				if (ball.movable == false) {
					ball.movable = true;
					ball.xDirect = -1;
				}
			}
			if (code == KeyCode.UP) {
				if (imageview.getY() > YSIZE * 2 / 3)
					imageview.setY(imageview.getY() - speed);
				if (ball.movable == false) {
					ball.movable = true;
					ball.xDirect = 1;
				}
			}
		}

	}
}
