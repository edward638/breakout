package game_ez20;


import javafx.scene.image.*;



public abstract class Item {
		// 1 = movable (paddle and ball) 0 = immovable
		public boolean movable = true;
		public boolean isOn;		
		public int xCoord;		
		public int yCoord;		
		public ImageView imageview;
		
}
