package game_ez20;

import java.util.*;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileNotFoundException;



public class BrickController {
	public ArrayList<Brick> bricklist = new ArrayList<Brick>();
	private boolean usedPowerBall = false;
	
	Image brick1 = new Image(getClass().getClassLoader().getResourceAsStream("brick1.gif"));
	Image brick2 = new Image(getClass().getClassLoader().getResourceAsStream("brick2.gif"));
	Image brick3 = new Image(getClass().getClassLoader().getResourceAsStream("brick3.gif"));
	Image brick4 = new Image(getClass().getClassLoader().getResourceAsStream("brick4.gif"));
	Image brick5 = new Image(getClass().getClassLoader().getResourceAsStream("brick5.gif"));
	Image brick6 = new Image(getClass().getClassLoader().getResourceAsStream("brick6.gif"));
	Image brick7 = new Image(getClass().getClassLoader().getResourceAsStream("brick7.gif"));
	Image brick8 = new Image(getClass().getClassLoader().getResourceAsStream("brick8.gif"));
	Image brick9 = new Image(getClass().getClassLoader().getResourceAsStream("brick9.gif"));

	Map<Integer, Image> brickmap = new HashMap<Integer, Image>();
	File file;
	int level;
	int score = 0;
	Map<Integer, String> levelmap = new HashMap<Integer, String>();

	public BrickController(int startLevel, int startScore) {
		score = startScore;
		level = startLevel;
		brickmap.put(1, brick1);
		brickmap.put(2, brick2);
		brickmap.put(3, brick3);
		brickmap.put(4, brick4);
		brickmap.put(5, brick5);
		brickmap.put(6, brick6);
		brickmap.put(7, brick7);
		brickmap.put(8, brick8);
		brickmap.put(9, brick9);
		levelmap.put(1, "bricklist1.txt");
		levelmap.put(2, "bricklist2.txt");
		levelmap.put(3, "bricklist3.txt");
		file = new File(levelmap.get(level));

		try {
			Scanner scan = new Scanner(file);
			while (scan.hasNextLine()) {
				int type = scan.nextInt();
				int xCoord = scan.nextInt();
				int yCoord = scan.nextInt();

				Brick brick = new Brick(brickmap.get(type), xCoord, yCoord, type);

				bricklist.add(brick);

			}

			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void drawBricks(Group root) {
		for (int x = 0; x < bricklist.size(); x++) {
			root.getChildren().add(bricklist.get(x).imageview);
		}
	}

	public void scoreAdd() {
		score = score + 1000;
	}
	
	public boolean getUsedPowerBall() {
		return usedPowerBall;
	}
	
	public void CollisionChecker(Ball ball, Group root, PowerController powerController) {

		for (int y = 0; y < bricklist.size(); y++) {
			if (ball.imageview.getBoundsInParent().intersects(bricklist.get(y).imageview.getBoundsInParent())) {
				
				scoreAdd();
				if (ball.getPowerBall() > 0) {
					bricklist.get(y).ReleasePower(powerController, root);
					root.getChildren().remove(bricklist.get(y).imageview);
					bricklist.remove(y);
					usedPowerBall = true;
				} else {
					if (ball.imageview.getX() >= (bricklist.get(y).imageview.getX()
							+ bricklist.get(y).imageview.getBoundsInParent().getWidth())) {
						
						ball.goRight();
					}
					if (ball.imageview.getX() <= (bricklist.get(y).imageview.getX())) {
						ball.goLeft();
					}
					if (ball.imageview.getY() <= (bricklist.get(y).imageview.getY())) {
						ball.goUp();
					}
					if (ball.imageview.getY() >= (bricklist.get(y).imageview.getY()
							+ bricklist.get(y).imageview.getBoundsInParent().getHeight())) {
						ball.goDown();
					}

					bricklist.get(y).loseHealth();
					
					
					if (bricklist.get(y).getHealth() == 0) {
						bricklist.get(y).ReleasePower(powerController, root);
						root.getChildren().remove(bricklist.get(y).imageview);
						bricklist.remove(y);
					} else {
						root.getChildren().remove(bricklist.get(y).imageview);
						bricklist.get(y).imageview = new ImageView(brickmap.get(bricklist.get(y).getHealth()));
						bricklist.get(y).imageview.setX(bricklist.get(y).xCoord);
						bricklist.get(y).imageview.setY(bricklist.get(y).yCoord);
						root.getChildren().add(bricklist.get(y).imageview);

					}
					usedPowerBall = false;
				}

			}

		}
		
	}

	public boolean nextLevel() {
		return bricklist.isEmpty();
	}

}
