package game_ez20;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// class used for ball object. The ball is used to bounce around and break bricks. 

public class Ball extends Item {
	private int speed;
	private int size;
	private int lives = 3;
	private int xDirect = 1;
	private int yDirect = -1;
	private int powerBall = 0;
	private int ogSpeed;
	private int speedTime = 0;
	
	public Ball(Image image, int startX, int startY, int startSpeed, int startSize) {
		imageview = new ImageView(image);
		xCoord = startX;
		yCoord = startY;
		speed = startSpeed;
		ogSpeed = startSpeed;
		size = startSize;
		movable = false;
		isOn = true;
		imageview.setFitHeight(size);
		imageview.setFitWidth(size);
		imageview.setX(xCoord);
		imageview.setY(yCoord);
	}

	// these three methods are used to take care of the powerful ball powerup
	
	public int getPowerBall() {
		return powerBall;
	}
	
	public void increasePowerBall() {
		powerBall = 1;
	}
	
	public void reducePowerBall() {
		if (powerBall >0) powerBall = 0;
	}
	
	// the next four methods change the direction of the ball
	
	public void goLeft() {
		xDirect = -1;
	}
	public void goRight() {
		xDirect = 1;
	}
	public void goDown() {
		yDirect = 1;
	}
	public void goUp() {
		yDirect = -1;
	}
	
	// these three methods 
	
	public void addSpeedTime() {
		speedTime = 5;
	}
	public void increaseSpeed() {
		speed += 20;
	}
	public void decreaseSpeed() {
		if (speed > 10) speed -= 20;
	}
	
	// a ball is reset when it goes to the bottom of the screen, or when a level is beat.
	
	public void reset(int XSIZE, int YSIZE, Paddle paddle, int LIVES) {
		movable = false;
		imageview.setX(XSIZE / 2);
		paddle.imageview.setX(XSIZE / 2 - 25);
		imageview.setY(YSIZE - 65);
		paddle.imageview.setY(YSIZE - 50);
		lives = LIVES;

		yDirect = -1;
	}
	
	// deals with the lives in game
	
	public void extraLife() {
		lives++;
	}

	public int getLives() {
		return lives;
	}
	
	// updates ball's position and power ups 
	
	public void update(double elapsedTime, int XSIZE, int YSIZE, Paddle paddle, int LIVES, BrickController brickController) {
		if (movable) {
			imageview.setX(imageview.getX() + speed * elapsedTime * xDirect);
			imageview.setY(imageview.getY() + speed * elapsedTime * yDirect);
		}
		// check out of bounds?
		// check if bouncer hits walls
		if (imageview.getX() >= XSIZE) {
			xDirect = -1;
		}
		if (imageview.getX() <= 0) {
			xDirect = 1;
		}
	
		if (imageview.getY() >= YSIZE) {
			reset(XSIZE, YSIZE, paddle, LIVES);
			lives--;
		}
		if (imageview.getY() <= 0) {
			yDirect = 1;
		}
		if (imageview.getBoundsInParent().intersects(paddle.imageview.getBoundsInParent())) {
			yDirect = -1;

			paddle.reduceStretch();
			
			if (speedTime > 0) {
				speedTime--;
				if (speedTime == 0)
					speed = ogSpeed; 
			}
			
			if (brickController.getUsedPowerBall()) {
				powerBall = 0;
			}

			// right side of paddle bounces ball to the right, left bounces ball to the left
			if (imageview.getX() >= paddle.imageview.getX() + paddle.imageview.getBoundsInParent().getWidth() / 2)
				xDirect = 1;
			else
				xDirect = -1;
		}
	}
}
