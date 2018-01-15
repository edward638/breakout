package game_ez20;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ball extends Item {
	public int speed;
	public int size;
	public int lives = 3;
	public int xDirect = 1;
	public int yDirect = -1;

	public Ball(Image image, int startX, int startY, int startSpeed, int startSize) {
		imageview = new ImageView(image);
		xCoord = startX;
		yCoord = startY;
		speed = startSpeed;
		size = startSize;
		movable = true;
		isOn = true;
		imageview.setFitHeight(size);
		imageview.setFitWidth(size);
		imageview.setX(xCoord);
		imageview.setY(yCoord);

	}

	public void reset(int XSIZE, int YSIZE, Paddle paddle, int LIVES) {
		movable = false;
		imageview.setX(XSIZE / 2);
		paddle.imageview.setX(XSIZE / 2 - 25);
		imageview.setY(YSIZE - 65);
		paddle.imageview.setY(YSIZE - 50);
		lives = LIVES - 1;
		
		
		yDirect = -1;
	}

	public void update(double elapsedTime, int XSIZE, int YSIZE, Paddle paddle, int LIVES) {
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

		}
		if (imageview.getY() <= 0) {
			yDirect = 1;
		}
		if (imageview.getBoundsInParent().intersects(paddle.imageview.getBoundsInParent())) {
			yDirect = -1;

			// right side of paddle bounces ball to the right
			if (imageview.getX() >= paddle.imageview.getX() + paddle.imageview.getBoundsInParent().getWidth() / 2)
				xDirect = 1;
			else
				xDirect = -1;
		}
	}
}
