package game_ez20;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ExtraLife extends Power {
	
	private Image extraLife = new Image(getClass().getClassLoader().getResourceAsStream("extraballpower.gif"));
		
	public ExtraLife (int startX, int startY, int startSpeed) {	
		super(startX, startY, startSpeed);
		imageview = new ImageView(extraLife);
		imageview.setX(xCoord);
		imageview.setY(yCoord);
	}
	
	@Override
	public void activatePower(Ball ball, Paddle paddle, Group root, Label label) {
		ball.extraLife();
		label.setText("Extra life earned!");
		remove(root);
	}
	

}
