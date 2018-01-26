package game_ez20;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PowerBall extends Power {
	
	private Image powerBall = new Image(getClass().getClassLoader().getResourceAsStream("pointspower.gif"));
		
	public PowerBall (int startX, int startY, int startSpeed) {
		super(startX, startY, startSpeed);
		imageview = new ImageView(powerBall);
		imageview.setX(xCoord);
		imageview.setY(yCoord);
	}
	
	
	@Override
	public void activatePower(Ball ball, Paddle paddle, Group root, Label label) {
		ball.increasePowerBall();
		label.setText("POWER BALL!");
		remove(root);
	}

}
