package game_ez20;

import javafx.scene.image.*;


// class which all items are based on (ball, paddle, powerup, brick)

public abstract class Item {
	public boolean movable = true;
	public boolean isOn;
	public int xCoord;
	public int yCoord;
	public ImageView imageview;
}
