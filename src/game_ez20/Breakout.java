package game_ez20;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Breakout extends Application {

	public static final String PADDLE_IMAGE = "paddle.gif";
	public static final String BALL_IMAGE = "ball.gif";
	public static final int XSIZE = 500;
	public static final int YSIZE = 500;
	public static int BALL_SPEED = 300;
	public static int BALL_SIZE = 30;
	public static int PADDLE_SPEED = 50;
	public static int PADDLE_SIZE = 500;
	public static final int FRAMES_PER_SECOND = 60;
	public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	private Scene myScene;
	private Paddle paddle;
	private Ball ball;
	private BrickController brickController;
	Group root = new Group();

	@Override
	public void start(Stage firstStage) {
		firstStage.setTitle("Breakout");

		myScene = new Scene(root, XSIZE, YSIZE, Color.SALMON);

		Image ballImage = new Image(getClass().getClassLoader().getResourceAsStream(BALL_IMAGE));
		Image paddleImage = new Image(getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
		paddle = new Paddle(paddleImage, XSIZE / 2 - 25, YSIZE - 50, PADDLE_SPEED, PADDLE_SIZE);

		ball = new Ball(ballImage, paddle.xCoord + (int) paddle.imageview.getBoundsInParent().getWidth() / 2,
				paddle.yCoord - BALL_SIZE, BALL_SPEED, BALL_SIZE);
		ball.movable = false;
		brickController = new BrickController(1);

		brickController.drawBricks(root);
		
		root.getChildren().add(ball.imageview);
		root.getChildren().add(paddle.imageview);
		myScene.setOnKeyPressed(e -> keyInput(e.getCode()));
		firstStage.setScene(myScene);
		firstStage.show();

		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();

	}

	private void keyInput(KeyCode code) {
		// if (code == KeyCode.RIGHT) {
		// if (paddle.imageview.getX() < 450)
		// paddle.imageview.setX(paddle.imageview.getX() + paddle.speed);
		// if (ball.movable == false) {
		// ball.movable = true;
		// ball.xDirect = -1;
		// }
		// }
		// if (code == KeyCode.LEFT) {
		// if (paddle.imageview.getX() > 0)
		// paddle.imageview.setX(paddle.imageview.getX() - paddle.speed);
		// if (ball.movable == false) {
		// ball.movable = true;
		// ball.xDirect = 1;
		// }
		// }
		paddle.movePaddle(code, ball);

	}

	private void step(double elapsedTime) {
		ball.update(elapsedTime, XSIZE, YSIZE, paddle);
		brickController.CollisionChecker(ball, root);
		if (brickController.nextLevel() && ((brickController.level + 1) < 3)) {
			brickController = new BrickController(brickController.level + 1);
			brickController.drawBricks(root);
		}
		if (brickController.nextLevel() && ((brickController.level + 1) > 3)) {
			
		}

		

		// check to see if there is collision between ball and paddle (need to make
		// method for this in item manager)
		

	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
