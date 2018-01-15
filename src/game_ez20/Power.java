package game_ez20;

import java.util.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Power extends Item {
	Map<Integer, Image> powermap = new HashMap<Integer, Image>();

	Image extraLife = new Image(getClass().getClassLoader().getResourceAsStream("extraballpower.gif"));
	Image speedPaddle = new Image(getClass().getClassLoader().getResourceAsStream("sizepower.gif"));
	Image powerBall = new Image(getClass().getClassLoader().getResourceAsStream("pointspower.gif"));

	int speed;
	int powerType;

	public Power(int startPowerType, int startX, int startY, int startSpeed) {

		powermap.put(1, extraLife);
		powermap.put(2, speedPaddle);
		powermap.put(3, powerBall);

		imageview = new ImageView(powermap.get(startPowerType));
		powerType = startPowerType;
		speed = startSpeed;

		xCoord = startX;
		yCoord = startY;
		
		imageview.setX(xCoord);
		imageview.setY(yCoord);
		
	}
	
	public void update(double elapsedTime){
		imageview.setY(imageview.getY() + speed * elapsedTime);
	}
	public void ActivatePower(Ball ball) {
		if (powerType == 1) ball.lives++;
	}
	
}
