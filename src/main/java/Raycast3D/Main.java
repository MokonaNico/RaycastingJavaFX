package Raycast3D;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    static final int WIDTH = 1000;
    static final int HEIGHT = 500;

    static Pane root;
    static GameMap gm;

    boolean turnRight = false;
    boolean turnLeft = false;
    boolean movingForward = false;
    boolean movingBackward = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("RaycastFX");
        root = new Pane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        root.setStyle("-fx-background-color: #565656");

        gm = new GameMap(root);
        gm.drawMap();
        gm.drawMapLine();

        Player p = new Player(75,75);
        root.getChildren().add(p);

        scene.setOnKeyPressed(ke -> {
            if (ke.getCode() == KeyCode.Q) {
                turnLeft = true;
            }
            if (ke.getCode() == KeyCode.D) {
                turnRight = true;
            }
            if (ke.getCode() == KeyCode.Z) {
                movingForward = true;
            }
            if (ke.getCode() == KeyCode.S) {
                movingBackward = true;
            }
        });

        scene.setOnKeyReleased(ke -> {
            if(ke.getCode() == KeyCode.Q) {
                turnLeft = false;
            }
            if(ke.getCode() == KeyCode.D) {
                turnRight = false;
            }
            if(ke.getCode() == KeyCode.Z){
                movingForward = false;
            }
            if(ke.getCode() == KeyCode.S){
                movingBackward = false;
            }
        });

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                if(turnLeft) p.turnLeft();
                if(turnRight) p.turnRight();
                if(movingForward) p.moveForward();
                if(movingBackward) p.moveBackward();
            }
        }.start();
    }
}
