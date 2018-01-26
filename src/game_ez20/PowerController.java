package game_ez20;

import java.util.*;

import javafx.scene.Group;
import javafx.scene.control.Label;

// class used to make powercontroller, which is used to control all the power ups on the screen (using arraylist). 
// also displays power up information at the bottom

public class PowerController {
	private ArrayList<Power> powerlist = new ArrayList<Power>();
	private Label powerupInfo = new Label();
	
	public PowerController(Group root, int YSIZE) {
		powerupInfo.setLayoutY(YSIZE - 30);
		root.getChildren().add(powerupInfo);
	}

	public void AddPower(int startPowerType, int startX, int startY, int startSpeed, Group root) {
		if (startPowerType == 1) {
			Power tempPower = new ExtraLife(startX, startY, startSpeed);
			tempPower.draw(root);
			powerlist.add(tempPower);
		}
		else if (startPowerType == 2) {
			Power tempPower = new StretchPaddle(startX, startY, startSpeed);
			tempPower.draw(root);
			powerlist.add(tempPower);
		}
		else if (startPowerType == 3) {
			Power tempPower = new PowerBall(startX, startY, startSpeed);
			tempPower.draw(root);
			powerlist.add(tempPower);
		}
		else if (startPowerType == 4) {
			Power tempPower = new PowerDownSize(startX, startY, startSpeed);
			tempPower.draw(root);
			powerlist.add(tempPower);
		}
		else {
			Power tempPower = new PowerDownSpeed(startX, startY, startSpeed);
			tempPower.draw(root);
			powerlist.add(tempPower);
		}
	}

	// moves the power ups
	
	public void update(double elapsedTime) {
		for (int x = 0; x < powerlist.size(); x++)
			powerlist.get(x).update(elapsedTime);
	}

	// checks to see if power up is hit by paddle (activates power up) or hits ground (disappears)
	
	public void CollisionChecker(Group root, Paddle paddle, Ball ball, int YSIZE) {
		for (int y = 0; y < powerlist.size(); y++) {
			Power curr = powerlist.get(y);

			if (curr.imageview.getY() > YSIZE) {
				curr.remove(root);
				powerlist.remove(y);
			} 
			else if (curr.imageview.getBoundsInParent().intersects(paddle.imageview.getBoundsInParent())) {
				curr.activatePower(ball, paddle, root, powerupInfo);
				powerlist.remove(y);
			}

		}
	}
	
	// power ups are cleared when a level is cleared
	
	public void clear(Group root) {
		for (int y = 0; y < powerlist.size(); y++) {
			Power curr = powerlist.get(y);
			root.getChildren().remove(curr.imageview);
			powerlist.remove(y);
		}
	}

}
