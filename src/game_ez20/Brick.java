package game_ez20;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Brick extends Item{
	
	
	public Brick(Image image, int startX, int startY) {
		imageview = new ImageView(image);
		xCoord = startX;
		yCoord = startY;
		movable = false;
		isOn = true;
		imageview.setX(xCoord);
		imageview.setY(yCoord);
	}
	
	
	public void update(double elapsedTime) {
		
	}
	
}
