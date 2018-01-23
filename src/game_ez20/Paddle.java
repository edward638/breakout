package game_ez20;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

// this class is for the paddle object. The paddle is primarily controlled by the user to manipulate the ball.

public class Paddle extends Item {

	private int speed;
	private int size;
	private int stretchPaddleTime = 0;
	private boolean vertical = false;
	private double ogWidth;

	// allows for vertical movement of the paddle

	public void enableVertical() {
		vertical = (!vertical);
	}
	
	// when paddle stretch powerup is collected, the paddle is stretched for 5 bounces
	
	public void addStretch() {
		stretchPaddleTime = 5;
	}
	
	// ogWidth = original width
	
	public double getOgWidth() {
		return ogWidth;
	}
	
	public void reduceStretch() {
		if (stretchPaddleTime > 0) {
			stretchPaddleTime--;
			if (stretchPaddleTime == 0)
				imageview.setFitWidth(ogWidth);
		}
	}
	
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

	// controls movement of paddle
	
	public void movePaddle(KeyCode code, Ball ball, int XSIZE, int YSIZE) {
		if (code == KeyCode.RIGHT) {
			if ((imageview.getX() + imageview.getBoundsInParent().getWidth()) < XSIZE)
				imageview.setX(imageview.getX() + speed);
			else {
				imageview.setX(0); // allows for paddle wraparound
			}
			if (ball.movable == false) {
				ball.movable = true;
				ball.goLeft();
			}
		}
		if (code == KeyCode.LEFT) {
			if (imageview.getX() > 0)
				imageview.setX(imageview.getX() - speed);
			else {
				imageview.setX(XSIZE-imageview.getBoundsInParent().getWidth()); // allows for paddle wraparound
			}
			if (ball.movable == false) {
				ball.movable = true;
				ball.goRight();
			}
		}

		if (vertical) {
			if (code == KeyCode.DOWN) {
				if ((imageview.getY() + imageview.getBoundsInParent().getWidth()) < YSIZE)
					imageview.setY(imageview.getY() + speed);
				if (ball.movable == false) {
					ball.movable = true;
					ball.goLeft();
				}
			}
			if (code == KeyCode.UP) {
				if (imageview.getY() > YSIZE * 2 / 3)
					imageview.setY(imageview.getY() - speed);
				if (ball.movable == false) {
					ball.movable = true;
					ball.goRight();
				}
			}
		}

	}
}
