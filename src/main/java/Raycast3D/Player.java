package Raycast3D;

import com.sun.scenario.effect.impl.hw.ShaderSource;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.lang.Math;

public class Player extends Circle {

    private static final int PLAYERSIZE = 5;

    private final int SCREENSIZE = Main.WIDTH/2;

    Vector2 pos;
    Vector2 dir;
    Vector2 plane;

    double turnSpeed = 1;
    double movementSpeed = 1;

    Line dirLine;
    double dirLineLength = 50;
    Line planeLine;

    Line[] playerRaycastLines = new Line[SCREENSIZE];
    Line[] screenLines = new Line[SCREENSIZE];

    public Player(double x, double y) {
        super(PLAYERSIZE);

        this.pos = new Vector2(x,y);
        this.dir = new Vector2(1,0);
        this.plane = new Vector2(0,0.6);

        this.setCenterX(x);
        this.setCenterY(y);

        for(int i = 0; i < playerRaycastLines.length; i++){
            playerRaycastLines[i] = new Line(0,0,0,0);
            playerRaycastLines[i].setStroke(Color.GREEN);
            Main.root.getChildren().add(playerRaycastLines[i]);
        }

        for(int i = 0; i < screenLines.length; i++){
            screenLines[i] = new Line(0,0,0,0);
            Main.root.getChildren().add(screenLines[i]);
        }

        this.dirLine = new Line(0, 0, 0, 0);
        this.dirLine.setStroke(Color.ORANGE);
        this.planeLine = new Line(0, 0, 0, 0);
        this.planeLine.setStroke(Color.RED);

        updateDirLine();
        Main.root.getChildren().add(dirLine);
        Main.root.getChildren().add(planeLine);
        this.setFill(Color.BLUEVIOLET);

    }

    public void turnRight(){
        turnPlayer(-turnSpeed);
    }

    public void turnLeft(){
        turnPlayer(turnSpeed);
    }

    private void turnPlayer(double angle){
        dir = dir.Rotate(Math.toRadians(angle));
        plane = plane.Rotate(Math.toRadians(angle));
        updateDirLine();
    }

    public void moveForward(){
        moveDirection(movementSpeed);
    }

    public void moveBackward(){
        moveDirection(-movementSpeed);
    }

    private void moveDirection(double movementDist){
        Vector2 newPos = pos.Add(dir.MultiplyConstant(movementDist));
        this.setCenterX(newPos.x);
        this.setCenterY(newPos.y);
        if(checkCollision()){
            this.setCenterX(pos.x);
            this.setCenterY(pos.y);
        } else {
            pos = newPos;
            updateDirLine();
        }

    }

    public boolean checkCollision(){
        boolean collisionDetected = false;
        for(Rectangle static_bloc : Main.gm.wallList){
            if (this.getBoundsInParent().intersects(static_bloc.getBoundsInParent())) {
                collisionDetected = true;
            }
        }
        return collisionDetected;
    }

    public void updateDirLine(){
        Vector2 dirLineVector = dir.MultiplyConstant(dirLineLength);
        Vector2 endLineVector = pos.Add(dirLineVector);
        dirLine.setStartX(pos.x);
        dirLine.setStartY(pos.y);
        dirLine.setEndX(endLineVector.x);
        dirLine.setEndY(endLineVector.y);

        computeRaycast();
    }

    public void computeRaycast(){
        for (int x = 0; x < SCREENSIZE; x++){
            double cameraX = 2 * x / (double) SCREENSIZE - 1;
            Vector2 rayDir = dir.Add(plane.MultiplyConstant(cameraX));
            Vector2 mapPos = pos.DivideConstant(50).Round();
            Vector2 sideDist = new Vector2(0,0);
            Vector2 deltaDist = new Vector2(Math.abs(1 / rayDir.x), Math.abs(1 / rayDir.y));
            double perpWallDist;

            Vector2 step = new Vector2(0,0);

            boolean hit = false;
            int side = 0;

            if (rayDir.x < 0)
            {
                step.x = -1;
                sideDist.x = (pos.x/50 - mapPos.x) * deltaDist.x;
            }
            else
            {
                step.x = 1;
                sideDist.x = (mapPos.x + 1.0 - pos.x/50) * deltaDist.x;
            }
            if (rayDir.y < 0)
            {
                step.y = -1;
                sideDist.y = (pos.y/50 - mapPos.y) * deltaDist.y;
            }
            else
            {
                step.y = 1;
                sideDist.y = (mapPos.y + 1.0 - pos.y/50) * deltaDist.y;
            }

            while(!hit){
                if(sideDist.x < sideDist.y){
                    sideDist.x += deltaDist.x;
                    mapPos.x += step.x;
                    side = 0;
                }
                else {
                    sideDist.y += deltaDist.y;
                    mapPos.y += step.y;
                    side = 1;
                }

                if (Main.gm.gameMap[(int) mapPos.y][(int) mapPos.x] > 0) {
                    hit = true;
                }
            }

            if (side == 0) perpWallDist = (mapPos.x - pos.x/50 + (1-step.x)/2) / rayDir.x;
            else perpWallDist = (mapPos.y - pos.y/50 + (1-step.y)/2) / rayDir.y;

            playerRaycastLines[x].setStartX(pos.x);
            playerRaycastLines[x].setStartY(pos.y);
            Vector2 endRayDir = pos.Add(rayDir.MultiplyConstant(perpWallDist*50));
            playerRaycastLines[x].setEndX(endRayDir.x);
            playerRaycastLines[x].setEndY(endRayDir.y);



            int lineHeight = (int) (Main.HEIGHT / perpWallDist);

            int drawStart = -lineHeight / 2 + Main.HEIGHT /2;
            if (drawStart < 0) drawStart = 0;
            int drawEnd = lineHeight / 2 + Main.HEIGHT / 2;
            if(drawEnd >= Main.HEIGHT)drawEnd = Main.HEIGHT - 1;

            screenLines[x].setStartX(SCREENSIZE + x);
            screenLines[x].setStartY(drawStart);
            screenLines[x].setEndX(SCREENSIZE + x);
            screenLines[x].setEndY(drawEnd);
            Color main = Main.gm.getColor(Main.gm.gameMap[(int) mapPos.y][(int) mapPos.x]);
            if(side == 0) screenLines[x].setStroke(main);
            else screenLines[x].setStroke(main.darker());

        }
    }
}
