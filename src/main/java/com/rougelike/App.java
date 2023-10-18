package com.rougelike;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.*;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        double minWidth = 10.0;
        double maxWidth = 30.0;
        double minHeight = 10.0;
        double maxHeight = 30.0;

        DungeonGenerator map = new DungeonGenerator();
        int roomCount = 30;
        ArrayList<Room> rooms = map.generateListOfRooms(roomCount, minWidth,
                maxWidth,
                minHeight, maxHeight);

        int rows = 80;
        int columns = 80;
        ArrayList<Room> placedRooms = map.placeRoomsInArea(rooms, 30, rows, columns);
        map.connectRooms(placedRooms);
        Gridd gridd = map.getCopyOfGridd();

        Random rand = new Random();

        Color[] roomColors = new Color[placedRooms.size()];
        for (int i = 0; i < placedRooms.size(); i++) {
            roomColors[i] = Color.color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble());
        }

        Pane center = new Pane();
        double tileSize = DungeonGenerator.TILE_SIZE;
        for (int row = 0; row < gridd.getRowCount(); row++) {
            for (int column = 0; column < gridd.getColumnCount(); column++) {
                Rectangle rect = new Rectangle(column * tileSize, row * tileSize, tileSize, tileSize);
                int tileValue = gridd.getTile(gridd.new Index(row, column));
                if (tileValue >= 0) {
                    if (tileValue < placedRooms.size()) {
                        rect.setFill(roomColors[tileValue]);
                    } else {
                        rect.setFill(Color.WHITE);
                    }
                }
                center.getChildren().add(rect);
            }
        }
        Scene scene = new Scene(center);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
