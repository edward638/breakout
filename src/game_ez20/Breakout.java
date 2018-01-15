package game_ez20;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
	public static int BALL_SIZE = 15;
	public static int PADDLE_SPEED = 50;
	public static int PADDLE_SIZE = 500;
	public static final int FRAMES_PER_SECOND = 60;
	public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	private Scene myScene;
	private Paddle paddle;
	private Ball ball;
	private BrickController brickController;
	private PowerController powerController;
	private Timeline animation;
	Group root = new Group();
	public static int LIVES = 3;
	Label lifeLabel;
	private ImageView finish;
	
	@Override
	public void start(Stage firstStage) {
		firstStage.setTitle("Breakout");

		myScene = new Scene(root, XSIZE, YSIZE, Color.SALMON);
		lifeLabel = new Label(String.format("LIVES LEFT: %d", LIVES));

		root.getChildren().add(lifeLabel);

		Image ballImage = new Image(getClass().getClassLoader().getResourceAsStream(BALL_IMAGE));
		Image paddleImage = new Image(getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
		Image congrats = new Image(getClass().getClassLoader().getResourceAsStream("congrats.gif"));
		finish = new ImageView(congrats);
		finish.setFitWidth(YSIZE);
		
		
		paddle = new Paddle(paddleImage, XSIZE / 2 - 25, YSIZE - 50, PADDLE_SPEED, PADDLE_SIZE);

		ball = new Ball(ballImage, paddle.xCoord + (int) paddle.imageview.getBoundsInParent().getWidth() / 2,
				paddle.yCoord - BALL_SIZE, BALL_SPEED, BALL_SIZE);
		ball.movable = false;
		brickController = new BrickController(3);

		powerController = new PowerController();

		brickController.drawBricks(root);

		root.getChildren().add(ball.imageview);
		root.getChildren().add(paddle.imageview);
		myScene.setOnKeyPressed(e -> keyInput(e.getCode()));
		firstStage.setScene(myScene);
		firstStage.show();

		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();

	}

	private void keyInput(KeyCode code) {

		paddle.movePaddle(code, ball);

	}

	private void step(double elapsedTime) {
		ball.update(elapsedTime, XSIZE, YSIZE, paddle, LIVES);

		LIVES = ball.lives;

		lifeLabel.setText(String.format("LIVES LEFT: %d", LIVES));

		powerController.update(elapsedTime);
		powerController.CollisionChecker(root, paddle, ball, YSIZE);

		brickController.CollisionChecker(ball, root, powerController);
		if (brickController.nextLevel() && ((brickController.level + 1) <= 3)) {
			brickController = new BrickController(brickController.level + 1);
			brickController.drawBricks(root);
			ball.reset(XSIZE, YSIZE, paddle, LIVES);
			LIVES = LIVES + 1;

		}
		if (brickController.nextLevel() && ((brickController.level + 1) == 4)) {
			root.getChildren().add(finish);
			animation.pause();
		}

		// check to see if there is collision between ball and paddle (need to make
		// method for this in item manager)

	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
