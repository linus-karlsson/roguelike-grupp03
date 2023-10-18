package com.rougelike;

import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    Rectangle player;
    Vector playerVelocity = new Vector(0, 0);

    @Override
    public void start(Stage primaryStage) {

        double minWidth = 30.0;
        double maxWidth = 60.0;
        double minHeight = 30.0;
        double maxHeight = 60.0;

        DungeonGenerator map = new DungeonGenerator();
        int roomCount = 30;
        ArrayList<Room> rooms = map.generateListOfRooms(roomCount, minWidth,
                maxWidth,
                minHeight, maxHeight);

        int rows = 60;
        int columns = 60;
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
        Point pos = placedRooms.get(0).getPosition();
        player = new Rectangle(pos.getX(), pos.getY(), 6.0, 6.0);
        player.setFill(Color.RED);
        player.setStroke(Color.BLACK);
        center.getChildren().add(player);
        Scene scene = new Scene(center);

        scene.setOnKeyPressed(event -> {
            String codeString = event.getCode().getChar();
            playerVelocity.setX(0);
            playerVelocity.setY(0);
            if (codeString.equals("A")) {
                playerVelocity.setX(-2.0);
            }
            if (codeString.equals("W")) {
                playerVelocity.setY(-2.0);
            }
            if (codeString.equals("D")) {
                playerVelocity.setX(2.0);
            }
            if (codeString.equals("S")) {
                playerVelocity.setY(2.0);
            }
            Point point = new Point(player.getX(), player.getY());
            point = point.plus(playerVelocity);
            Gridd.Index index = gridd.getGriddIndexBasedOnPosition(point);
            Gridd.Index index2 = gridd.getGriddIndexBasedOnPosition(
                    new Point(point.getX() + player.getWidth(), point.getY() + player.getHeight()));
            if (gridd.getTile(index) >= 0 && gridd.getTile(index2) >= 0) {
                player.setX(point.getX());
                player.setY(point.getY());
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
