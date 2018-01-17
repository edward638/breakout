package game_ez20;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
	private Scene myScene, startScene;
	private Paddle paddle;
	private Ball ball;
	private BrickController brickController;
	private PowerController powerController;
	private Timeline animation;
	Group root = new Group();
	Group root2 = new Group();
	public static int LIVES = 3;
	Label lifeLabel;
	private ImageView finish;
	private ImageView failure;

	@Override
	public void start(Stage firstStage) {
		firstStage.setTitle("Breakout");

		startScene = new Scene(root2, XSIZE, YSIZE, Color.WHITE);

		firstStage.setScene(startScene);
		firstStage.show();
		Label welcome = new Label("Welcome to Edward Zhuang's Breakout!");
		Image gameDemo = new Image(getClass().getClassLoader().getResourceAsStream("gamedemo.gif"));
		ImageView demo = new ImageView(gameDemo);
		demo.setFitHeight(YSIZE);
		demo.setFitWidth(XSIZE);
		root2.getChildren().add(demo);
		Image gameRules = new Image(getClass().getClassLoader().getResourceAsStream("rules.PNG"));

		ImageView rules = new ImageView(gameRules);
		rules.setFitWidth(XSIZE * .75);
		rules.setFitHeight(YSIZE * .4);
		rules.setLayoutX(XSIZE / 8);
		rules.setLayoutY(YSIZE * .4);
		root2.getChildren().add(rules);
		welcome.setLayoutX(80);
		// welcome.setLayoutX(XSIZE/2);
		welcome.setFont(new Font("Helvetica", 20));
		root2.getChildren().add(welcome);
		Button startGame = new Button("Play Game!");
		startGame.setLayoutY(400);
		startGame.setLayoutX(220);
		startGame.setOnAction(e -> firstStage.setScene(myScene));
		root2.getChildren().add(startGame);

		myScene = new Scene(root, XSIZE, YSIZE, Color.CORAL);
		lifeLabel = new Label(String.format("LIVES LEFT: %d", LIVES));

		root.getChildren().add(lifeLabel);

		Image ballImage = new Image(getClass().getClassLoader().getResourceAsStream(BALL_IMAGE));
		Image paddleImage = new Image(getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
		Image congrats = new Image(getClass().getClassLoader().getResourceAsStream("congrats.gif"));
		Image gameover = new Image(getClass().getClassLoader().getResourceAsStream("gameover.gif"));
		finish = new ImageView(congrats);
		failure = new ImageView(gameover);
		failure.setFitHeight(YSIZE);
		failure.setFitWidth(XSIZE);
		finish.setFitWidth(XSIZE);

		paddle = new Paddle(paddleImage, XSIZE / 2 - 25, YSIZE - 50, PADDLE_SPEED, PADDLE_SIZE);

		ball = new Ball(ballImage, paddle.xCoord + (int) paddle.imageview.getBoundsInParent().getWidth() / 2,
				paddle.yCoord - BALL_SIZE, BALL_SPEED, BALL_SIZE);
		ball.movable = false;
		brickController = new BrickController(1);

		powerController = new PowerController(root, YSIZE);

		brickController.drawBricks(root);

		root.getChildren().add(ball.imageview);
		root.getChildren().add(paddle.imageview);
		myScene.setOnKeyPressed(e -> keyInput(e.getCode()));

		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();

	}

	private void keyInput(KeyCode code) {

		paddle.movePaddle(code, ball, XSIZE, YSIZE);

		if (code == KeyCode.DIGIT1) {
			for (int x = 0; x < brickController.bricklist.size(); x++) {
				root.getChildren().remove(brickController.bricklist.get(x).imageview);
			}
			brickController = new BrickController(1);
			brickController.drawBricks(root);
			ball.reset(XSIZE, YSIZE, paddle, LIVES);
			ball.lives++;
		}
		if (code == KeyCode.DIGIT2) {
			for (int x = 0; x < brickController.bricklist.size(); x++) {
				root.getChildren().remove(brickController.bricklist.get(x).imageview);
			}
			brickController = new BrickController(2);
			brickController.drawBricks(root);
			ball.reset(XSIZE, YSIZE, paddle, LIVES);
			ball.lives++;
		}
		if (code == KeyCode.DIGIT3) {
			for (int x = 0; x < brickController.bricklist.size(); x++) {
				root.getChildren().remove(brickController.bricklist.get(x).imageview);
			}
			brickController = new BrickController(3);
			brickController.drawBricks(root);
			ball.reset(XSIZE, YSIZE, paddle, LIVES);
			ball.lives++;
		}
		if (code == KeyCode.Q && ball.speed > 10) {
			ball.speed = ball.speed - 20;
		}
		if (code == KeyCode.W) {
			ball.speed = ball.speed + 20;
		}
		if (code == KeyCode.L) {
			ball.lives++;
		}
		if (code == KeyCode.A) {
			paddle.vertical = (!paddle.vertical);
		}
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
			ball.lives++;
			LIVES = LIVES + 1;
			
		}
		if (brickController.nextLevel() && ((brickController.level + 1) == 4)) {
			root.getChildren().add(finish);
			animation.pause();
		}
		if (LIVES == 0) {
			root.getChildren().add(failure);
			animation.pause();
		}

	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
