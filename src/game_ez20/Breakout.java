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

	private final String PADDLE_IMAGE = "paddle.gif";
	private final String BALL_IMAGE = "ball.gif";
	private final int XSIZE = 500;
	private final int YSIZE = 500;
	private int BALL_SPEED = 300;
	private int BALL_SIZE = 15;
	private int PADDLE_SPEED = 40;
	private int PADDLE_SIZE = 500;
	private final int FRAMES_PER_SECOND = 60;
	private final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

	private Scene myScene, startScene, winScene, lossScene;
	private Paddle paddle;
	private Ball ball;
	private BrickController brickController;
	private PowerController powerController;
	private Timeline animation;
	private Group root = new Group();
	private Group root2 = new Group();
	private Group root3 = new Group();
	private Group root4 = new Group();
	private Image ballImage = new Image(getClass().getClassLoader().getResourceAsStream(BALL_IMAGE));
	private Image paddleImage = new Image(getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
	private int LIVES = 3;
	private int SCORE = 0;
	private Label lifeLabel;
	private Label scoreLabel;
	private Label finalScoreLabel;
	private Label finalWinScoreLabel;

	public void restart(Stage firstStage) {
		root.getChildren().clear();

		lifeLabel = new Label(String.format("LIVES LEFT: %d", LIVES));
		scoreLabel = new Label(String.format("SCORE: %d", SCORE));
		scoreLabel.setLayoutY(15);
		paddle = new Paddle(paddleImage, XSIZE / 2 - 25, YSIZE - 50, PADDLE_SPEED, PADDLE_SIZE);
		ball = new Ball(ballImage, paddle.xCoord + (int) paddle.imageview.getBoundsInParent().getWidth() / 2,
				paddle.yCoord - BALL_SIZE, BALL_SPEED, BALL_SIZE);
		brickController = new BrickController(1, SCORE);
		powerController = new PowerController(root, YSIZE);

		brickController.drawBricks(root);
		root.getChildren().add(scoreLabel);
		root.getChildren().add(lifeLabel);
		root.getChildren().add(ball.imageview);
		root.getChildren().add(paddle.imageview);

		myScene.setOnKeyPressed(e -> keyInput(e.getCode()));
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY, firstStage));
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	public void welcome(Stage firstStage) {
		firstStage.setScene(startScene);
		firstStage.show();

		Label welcome = new Label("Welcome to Edward Zhuang's Breakout!");
		welcome.setLayoutX(80);
		welcome.setFont(new Font("Helvetica", 20));

		Image gameDemo = new Image(getClass().getClassLoader().getResourceAsStream("gamedemo.gif"));
		ImageView demo = new ImageView(gameDemo);
		demo.setFitHeight(YSIZE);
		demo.setFitWidth(XSIZE);

		Image gameRules = new Image(getClass().getClassLoader().getResourceAsStream("rules.PNG"));
		ImageView rules = new ImageView(gameRules);
		rules.setFitWidth(XSIZE * .75);
		rules.setFitHeight(YSIZE * .4);
		rules.setLayoutX(XSIZE / 8);
		rules.setLayoutY(YSIZE * .4);

		Image powerguide = new Image(getClass().getClassLoader().getResourceAsStream("powerguide.PNG"));
		ImageView powerGuide = new ImageView(powerguide);
		powerGuide.setFitWidth(XSIZE * .25);
		powerGuide.setFitHeight(YSIZE * .25);
		powerGuide.setLayoutY(370);

		Button startGame = new Button("Play Game!");
		startGame.setLayoutY(400);
		startGame.setLayoutX(220);
		startGame.setOnAction(e -> firstStage.setScene(myScene));

		root2.getChildren().add(demo);
		root2.getChildren().add(startGame);
		root2.getChildren().add(powerGuide);
		root2.getChildren().add(welcome);
		root2.getChildren().add(rules);

	}

	public void victory(Stage firstStage) {
		Image win = new Image(getClass().getClassLoader().getResourceAsStream("congrats.PNG"));
		ImageView gameWin = new ImageView(win);
		gameWin.setFitWidth(XSIZE * .9);
		gameWin.setFitHeight(YSIZE * .3);
		gameWin.setLayoutX(XSIZE / 20);
		gameWin.setLayoutY(YSIZE * .25);

		Button returnToStart2 = new Button("Return to Main Menu");
		returnToStart2.setLayoutY(320);
		returnToStart2.setLayoutX(185);
		returnToStart2.setOnAction(e -> firstStage.setScene(startScene));

		finalWinScoreLabel = new Label(String.format("SCORE: %d", SCORE));
		finalWinScoreLabel.setLayoutX(220);
		finalWinScoreLabel.setLayoutY(280);

		root3.getChildren().add(gameWin);
		root3.getChildren().add(returnToStart2);
		root3.getChildren().add(finalWinScoreLabel);

	}

	public void failure(Stage firstStage) {
		Image loss = new Image(getClass().getClassLoader().getResourceAsStream("failure.PNG"));
		ImageView gameLoss = new ImageView(loss);
		gameLoss.setFitWidth(XSIZE * .9);
		gameLoss.setFitHeight(YSIZE * .3);
		gameLoss.setLayoutX(XSIZE / 20);
		gameLoss.setLayoutY(YSIZE * .25);

		Button returnToStart = new Button("Return to Main Menu");
		returnToStart.setLayoutY(270);
		returnToStart.setLayoutX(185);
		returnToStart.setOnAction(e -> firstStage.setScene(startScene));

		finalScoreLabel = new Label(String.format("SCORE: %d", SCORE));
		finalScoreLabel.setLayoutX(220);
		finalScoreLabel.setLayoutY(240);

		root4.getChildren().add(gameLoss);
		root4.getChildren().add(returnToStart);
		root4.getChildren().add(finalScoreLabel);
	}

	@Override
	public void start(Stage firstStage) {
		firstStage.setTitle("Breakout");

		myScene = new Scene(root, XSIZE, YSIZE, Color.CORAL);
		startScene = new Scene(root2, XSIZE, YSIZE, Color.WHITE);
		winScene = new Scene(root3, XSIZE, YSIZE, Color.SALMON);
		lossScene = new Scene(root4, XSIZE, YSIZE, Color.SALMON);

		restart(firstStage);
		welcome(firstStage);
		victory(firstStage);
		failure(firstStage);
	}

	private void keyInput(KeyCode code) {

		paddle.movePaddle(code, ball, XSIZE, YSIZE);

		if (code == KeyCode.DIGIT1) {
			for (int x = 0; x < brickController.bricklist.size(); x++) {
				root.getChildren().remove(brickController.bricklist.get(x).imageview);
			}
			brickController = new BrickController(1, SCORE);
			brickController.drawBricks(root);
			powerController.clear(root);
			ball.reset(XSIZE, YSIZE, paddle, LIVES);
			ball.lives++;
		}
		if (code == KeyCode.DIGIT2) {
			for (int x = 0; x < brickController.bricklist.size(); x++) {
				root.getChildren().remove(brickController.bricklist.get(x).imageview);
			}
			brickController = new BrickController(2, SCORE);
			brickController.drawBricks(root);
			powerController.clear(root);
			ball.reset(XSIZE, YSIZE, paddle, LIVES);
			ball.lives++;
		}
		if (code == KeyCode.DIGIT3) {
			for (int x = 0; x < brickController.bricklist.size(); x++) {
				root.getChildren().remove(brickController.bricklist.get(x).imageview);
			}
			brickController = new BrickController(3, SCORE);
			brickController.drawBricks(root);
			powerController.clear(root);
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
		if (code == KeyCode.P) {
			ball.movable = false;
		}
		if (code == KeyCode.B) {
			ball.powerBall = 1;
		}
	}

	private void step(double elapsedTime, Stage firstStage) {
		ball.update(elapsedTime, XSIZE, YSIZE, paddle, LIVES);

		LIVES = ball.lives;
		lifeLabel.setText(String.format("LIVES LEFT: %d", LIVES));

		powerController.update(elapsedTime);
		powerController.CollisionChecker(root, paddle, ball, YSIZE);
		brickController.CollisionChecker(ball, root, powerController);

		SCORE = brickController.score;
		scoreLabel.setText(String.format("CURRENT SCORE: %d", SCORE));

		if (brickController.nextLevel() && ((brickController.level + 1) <= 3)) {
			brickController = new BrickController(brickController.level + 1, SCORE);
			brickController.drawBricks(root);
			ball.reset(XSIZE, YSIZE, paddle, LIVES);
			powerController.clear(root);
			ball.lives++;
			LIVES = LIVES + 1;
		}

		if (brickController.nextLevel() && ((brickController.level + 1) == 4)) {
			animation.pause();
			finalWinScoreLabel.setText(String.format("SCORE: %d", SCORE));
			ball.lives = 3;
			brickController.score = 0;
			SCORE = 0;
			scoreLabel.setText(String.format("CURRENT SCORE: %d", SCORE));
			lifeLabel.setText(String.format("LIVES LEFT: %d", LIVES));
			firstStage.setScene(winScene);
			restart(firstStage);
		}

		if (LIVES == 0) {
			animation.pause();
			finalScoreLabel.setText(String.format("SCORE: %d", SCORE));
			ball.lives = 3;
			brickController.score = 0;
			SCORE = 0;
			scoreLabel.setText(String.format("CURRENT SCORE: %d", SCORE));
			lifeLabel.setText(String.format("LIVES LEFT: %d", LIVES));
			firstStage.setScene(lossScene);
			restart(firstStage);
		}
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
