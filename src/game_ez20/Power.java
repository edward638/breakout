package game_ez20;

import java.util.*;

import javafx.scene.Group;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Power extends Item {
	Map<Integer, Image> powermap = new HashMap<Integer, Image>();

	Image extraLife = new Image(getClass().getClassLoader().getResourceAsStream("extraballpower.gif"));
	Image stretchPaddle = new Image(getClass().getClassLoader().getResourceAsStream("sizepower.gif"));
	Image powerBall = new Image(getClass().getClassLoader().getResourceAsStream("pointspower.gif"));
	Image powerDown = new Image(getClass().getClassLoader().getResourceAsStream("laserpower.gif"));

	int speed;
	int powerType;

	public Power(int startPowerType, int startX, int startY, int startSpeed) {

		powermap.put(1, extraLife);
		powermap.put(2, stretchPaddle);
		powermap.put(3, powerBall);
		powermap.put(4, powerDown);

		imageview = new ImageView(powermap.get(startPowerType));
		powerType = startPowerType;
		speed = startSpeed;

		xCoord = startX;
		yCoord = startY;

		imageview.setX(xCoord);
		imageview.setY(yCoord);
		
		if (startPowerType == 4) {
			imageview.setFitHeight(imageview.getBoundsInParent().getHeight()*5);
			imageview.setFitWidth(imageview.getBoundsInParent().getWidth()*5);
		}
		System.out.println("big");
		
	}

	public void update(double elapsedTime) {
		imageview.setY(imageview.getY() + speed * elapsedTime);
	}

	public void ActivatePower(Ball ball, Paddle paddle, Group root) {
		if (powerType == 1) {
			ball.lives++;

		}

		if (powerType == 2) {
			paddle.imageview.setFitWidth(paddle.ogWidth * 1.5);
			root.getChildren().remove(paddle.imageview);
			root.getChildren().add(paddle.imageview);
			paddle.stretchPaddleTime = 5;
		}

		if (powerType == 3) {
			ball.powerBall = 1;
		}
		
		if (powerType == 4) {
			Random rand = new Random();
			if (rand.nextInt(2)+1>1) {
				paddle.imageview.setFitWidth(paddle.ogWidth / 2);
				root.getChildren().remove(paddle.imageview);
				root.getChildren().add(paddle.imageview);
				paddle.stretchPaddleTime = 5;
			}
			else {
				ball.speed = ball.ogSpeed + 80; 
				ball.speedTime = 5;
			}
		}
	}
}
