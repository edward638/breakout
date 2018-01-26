package game_ez20;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PowerDownSize extends Power {
	
	private Image powerDown = new Image(getClass().getClassLoader().getResourceAsStream("laserpower.gif"));
		
	public PowerDownSize (int startX, int startY, int startSpeed) {
		super(startX, startY, startSpeed);
		imageview = new ImageView(powerDown);
		imageview.setX(xCoord);
		imageview.setY(yCoord);
		
		imageview.setFitHeight(imageview.getBoundsInParent().getHeight()*5);
		imageview.setFitWidth(imageview.getBoundsInParent().getWidth()*5);
		
	}
	
	@Override
	public void activatePower(Ball ball, Paddle paddle, Group root, Label label) {
			paddle.imageview.setFitWidth(paddle.getOgWidth() / 2);
			
			root.getChildren().remove(paddle.imageview);
			root.getChildren().add(paddle.imageview);
			paddle.addStretch();			
			label.setText("POWER DOWN! PADDLE SIZE DECREASED!");
			remove(root);
	}
	
	
}
