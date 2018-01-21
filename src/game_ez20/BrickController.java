package game_ez20;

import java.util.*;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileNotFoundException;

public class BrickController {
	public ArrayList<Brick> bricklist = new ArrayList<Brick>();

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
	int score;
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
	
	public void CollisionChecker(Ball ball, Group root, PowerController powerController) {

		for (int y = 0; y < bricklist.size(); y++) {
			if (ball.imageview.getBoundsInParent().intersects(bricklist.get(y).imageview.getBoundsInParent())) {
				// decide which way to bounce the ball -> check what side of the brick was hit?
				scoreAdd();
				if (ball.powerBall > 0) {
					bricklist.get(y).ReleasePower(powerController, root);
					root.getChildren().remove(bricklist.get(y).imageview);
					bricklist.remove(y);
					ball.powerBall++;
				} else {
					if (ball.imageview.getX() >= (bricklist.get(y).imageview.getX()
							+ bricklist.get(y).imageview.getBoundsInParent().getWidth())) {
						// System.out.println("ball's coordinates are " + ball.imageview.getX() + "," +
						// ball.imageview.getY() + " " + bricklist.get(y).imageview.getX() + " " +
						// bricklist.get(y).imageview.getBoundsInParent().getWidth());
						ball.xDirect = 1;
					}
					if (ball.imageview.getX() <= (bricklist.get(y).imageview.getX())) {
						ball.xDirect = -1;
					}
					if (ball.imageview.getY() <= (bricklist.get(y).imageview.getY())) {
						ball.yDirect = -1;
					}
					if (ball.imageview.getY() >= (bricklist.get(y).imageview.getY()
							+ bricklist.get(y).imageview.getBoundsInParent().getHeight())) {
						ball.yDirect = 1;
					}

					bricklist.get(y).health--;
					
					
					if (bricklist.get(y).health == 0) {
						bricklist.get(y).ReleasePower(powerController, root);
						root.getChildren().remove(bricklist.get(y).imageview);
						bricklist.remove(y);
					} else {
						root.getChildren().remove(bricklist.get(y).imageview);
						bricklist.get(y).imageview = new ImageView(brickmap.get(bricklist.get(y).health));
						bricklist.get(y).imageview.setX(bricklist.get(y).xCoord);
						bricklist.get(y).imageview.setY(bricklist.get(y).yCoord);
						root.getChildren().add(bricklist.get(y).imageview);

					}
				}

			}

		}

	}

	public boolean nextLevel() {
		return bricklist.isEmpty();
	}

}
