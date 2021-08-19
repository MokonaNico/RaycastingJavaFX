package Raycast3D;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class GameMap {
    static final int SIZE = 50;

    int[][] gameMap = {
            {1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,3,0,0,1},
            {1,0,0,0,0,0,3,0,0,1},
            {1,0,0,0,0,0,3,3,0,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,2,2,0,1},
            {1,0,1,0,0,0,2,2,0,1},
            {1,0,1,1,0,0,0,0,0,1},
            {1,0,1,0,1,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1}
    };

    Pane pane;

    ArrayList<Rectangle> wallList = new ArrayList<>();

    public GameMap(Pane pane){
        this.pane = pane;
    }

    public void drawMap(){
        for(int i = 0; i < gameMap[0].length ; i++){
            for(int j = 0; j < gameMap.length ; j++){
                if (gameMap[j][i] == 0) continue;
                Rectangle wall = new Rectangle(SIZE,SIZE);
                wall.setFill(getColor(gameMap[j][i]));
                wallList.add(wall);
                wall.setX(i*SIZE);
                wall.setY(j*SIZE);
                pane.getChildren().add(wall);
            }
        }
    }

    public void drawMapLine(){
        for(int i = 0; i < gameMap.length ; i++){
            Line line = new Line(0,i * SIZE,gameMap.length * SIZE,i * SIZE);
            line.setStroke(Color.GRAY);
            pane.getChildren().add(line);
        }
        for(int i = 0; i < gameMap[0].length ; i++){
            Line line = new Line(i * SIZE,0,i * SIZE,gameMap.length * SIZE);
            line.setStroke(Color.GRAY);
            pane.getChildren().add(line);
        }
    }

    public Color getColor(int num){
        switch (num){
            case 1:
                return Color.BLUE;
            case 2:
                return Color.RED;
            case 3:
                return Color.YELLOW;
        }
        return null;
    }
}
