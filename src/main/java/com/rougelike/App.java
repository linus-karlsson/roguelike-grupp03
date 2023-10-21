package com.rougelike;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.rougelike.dungeon.DungeonGenerator;
import com.rougelike.dungeon.Grid;
import com.rougelike.dungeon.Room;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        double minWidth = 30.0;
        double maxWidth = 60.0;
        double minHeight = 30.0;
        double maxHeight = 60.0;
        int columnCount = 1000;
        int rowCount = 1000;
        double tileSize = DungeonGenerator.MIN_TILE_SIZE;

        DungeonGenerator dungeonGenerator = new DungeonGenerator();
        int roomCount = 1000;
        dungeonGenerator.generateDungeon(roomCount, minWidth, maxWidth, minHeight, maxHeight, roomCount,
                rowCount, columnCount, tileSize);
        launch(args);
    }

    Rectangle player;
    Vector2D playerVelocity = new Vector2D(0, 0);
    Grid grid;
    boolean running = false;

    public void start(Stage primaryStage) {

        double minWidth = 30.0;
        double maxWidth = 60.0;
        double minHeight = 30.0;
        double maxHeight = 60.0;
        int columnCount = 80;
        int rowCount = 80;
        double tileSize = DungeonGenerator.MIN_TILE_SIZE;

        DungeonGenerator dungeonGenerator = new DungeonGenerator();
        int roomCount = 40;
        List<Room> placedRooms = dungeonGenerator.generateDungeon(roomCount, minWidth, maxWidth, minHeight, maxHeight,
                20, rowCount, columnCount, tileSize);
        grid = dungeonGenerator.getCopyOfGridd();

        Random rand = new Random();
        rand.nextInt(roomCount);

        Color[] roomColors = new Color[placedRooms.size()];
        for (int i = 0; i < placedRooms.size(); i++) {
            roomColors[i] = Color.color(rand.nextDouble(), rand.nextDouble(),
                    rand.nextDouble());
        }

        Pane center = new Pane();
        for (int row = 0; row < grid.getRowCount(); row++) {
            for (int column = 0; column < grid.getColumnCount(); column++) {
                Rectangle rect = new Rectangle(column * tileSize, row * tileSize, tileSize,
                        tileSize);
                int tileValue = grid.getTile(grid.new Index(row, column));
                if (tileValue >= 0) {
                    if (tileValue < placedRooms.size()) {
                        rect.setFill(roomColors[tileValue]);
                    } else {
                        rect.setFill(Color.WHITE);
                    }
                } else if (tileValue == Grid.BORDER_VALUE) {
                    rect.setFill(Color.BLUE);
                }
                rect.setStroke(Color.RED);
                center.getChildren().add(rect);
            }
        }
        Point2D pos = placedRooms.get(0).getPosition();
        player = new Rectangle(pos.getX(), pos.getY(), 6.0, 6.0);
        player.setFill(Color.RED);
        player.setStroke(Color.BLACK);
        center.getChildren().add(player);
        Scene scene = new Scene(center);

        scene.setOnKeyPressed(event -> {
            String codeString = event.getCode().getChar();
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
            Point2D point = new Point2D(player.getX(), player.getY());
            point = point.plus(playerVelocity);
            Grid.Index index = grid.getGriddIndexBasedOnPosition(point);
            Grid.Index index2 = grid.getGriddIndexBasedOnPosition(
                    new Point2D(point.getX() + player.getWidth(), point.getY() +
                            player.getHeight()));
            if (grid.getTile(index) >= 0 && grid.getTile(index2) >= 0) {
                player.setX(point.getX());
                player.setY(point.getY());
            }
        });
        scene.setOnKeyReleased(event -> {
            String codeString = event.getCode().getChar();
            if (codeString.equals("A")) {
                if (playerVelocity.getX() < 0.0) {
                    playerVelocity.setX(0.0);
                }
            }
            if (codeString.equals("W")) {
                if (playerVelocity.getY() < 0.0) {
                    playerVelocity.setY(0.0);
                }
            }
            if (codeString.equals("D")) {
                if (playerVelocity.getX() > 0.0) {
                    playerVelocity.setX(0.0);
                }
            }
            if (codeString.equals("S")) {
                if (playerVelocity.getY() > 0.0) {
                    playerVelocity.setY(0.0);
                }
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
