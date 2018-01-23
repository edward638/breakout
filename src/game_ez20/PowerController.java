package game_ez20;

import java.util.*;

import javafx.scene.Group;
import javafx.scene.control.Label;

public class PowerController {
	private ArrayList<Power> powerlist = new ArrayList<Power>();
	private Label powerupInfo = new Label();

	public PowerController(Group root, int YSIZE) {
		powerupInfo.setLayoutY(YSIZE - 30);
		root.getChildren().add(powerupInfo);
	}

	public void AddPower(int startPowerType, int startX, int startY, int startSpeed, Group root) {
		Power tempPower = new Power(startPowerType, startX, startY, startSpeed);
		powerlist.add(tempPower);
		root.getChildren().add(tempPower.imageview);
	}

	public void update(double elapsedTime) {
		for (int x = 0; x < powerlist.size(); x++)
			powerlist.get(x).update(elapsedTime);
	}

	public void CollisionChecker(Group root, Paddle paddle, Ball ball, int YSIZE) {
		for (int y = 0; y < powerlist.size(); y++) {
			Power curr = powerlist.get(y);

			if (curr.imageview.getY() > YSIZE) {
				root.getChildren().remove(curr.imageview);
				powerlist.remove(y);
			} 
			else if (curr.imageview.getBoundsInParent().intersects(paddle.imageview.getBoundsInParent())) {
				if (curr.getPowerType() == 1)
					powerupInfo.setText("Extra life earned!");
				if (curr.getPowerType()  == 2)
					powerupInfo.setText("Paddle size stretched!");
				if (curr.getPowerType()  == 3)
					powerupInfo.setText("POWER BALL!");
				if (curr.getPowerType()  == 4) {
					if (curr.getPowerDownType() == 0) powerupInfo.setText("POWER DOWN! PADDLE SIZE DECREASED!");
					else powerupInfo.setText("POWER DOWN! BALL SPEED INCREASED!");
				}
				curr.ActivatePower(ball, paddle, root);
				root.getChildren().remove(curr.imageview);
				powerlist.remove(y);

			}

		}
	}
	
	public void clear(Group root) {
		for (int y = 0; y < powerlist.size(); y++) {
			Power curr = powerlist.get(y);
			root.getChildren().remove(curr.imageview);
			powerlist.remove(y);
		}
	}

}
