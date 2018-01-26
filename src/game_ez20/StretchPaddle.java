package game_ez20;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class StretchPaddle extends Power {
	
	private Image stretchPaddle = new Image(getClass().getClassLoader().getResourceAsStream("sizepower.gif"));
		
	public StretchPaddle (int startX, int startY, int startSpeed) {
		super(startX, startY, startSpeed);
		imageview = new ImageView(stretchPaddle);
		imageview.setX(xCoord);
		imageview.setY(yCoord);
	}
	
	@Override
	public void activatePower(Ball ball, Paddle paddle, Group root, Label label) {
		paddle.imageview.setFitWidth(paddle.imageview.getBoundsInParent().getWidth() * 2);	
		root.getChildren().remove(paddle.imageview);
		root.getChildren().add(paddle.imageview);
		paddle.addStretch();
		label.setText("Paddle size stretched!");
		remove(root);
	}
	
	
	
}
