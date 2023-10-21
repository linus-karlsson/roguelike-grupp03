package com.rougelike;

import java.util.List;
import java.util.Random;

import com.rougelike.dungeon.DungeonGenerator;
import com.rougelike.dungeon.Grid;
import com.rougelike.dungeon.GridIndex;
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

        double minWidth = 10.0;
        double maxWidth = 60.0;
        double minHeight = 10.0;
        double maxHeight = 60.0;
        int columnCount = 61;
        int rowCount = 61;
        double tileSize = DungeonGenerator.MIN_TILE_SIZE;

        DungeonGenerator dungeonGenerator = new DungeonGenerator();
        int extraForBorder = 2;
        int roomsInX = rowCount / ((int) (minWidth / DungeonGenerator.MIN_TILE_SIZE) + extraForBorder);
        int roomsInY = columnCount / ((int) (minHeight / DungeonGenerator.MIN_TILE_SIZE) + extraForBorder);

        double randomMultiplier = 0.0;
        dungeonGenerator.setRandom(new RandomInternal(randomMultiplier));
        int roomCount = roomsInX * roomsInY;
        List<Room> rooms = dungeonGenerator.generateListOfRooms(roomCount, minWidth, maxWidth,
                minHeight, maxHeight);
        dungeonGenerator.setRandom(new RandomInternal(randomMultipliersForAreaIsFilled(roomCount, roomsInX, roomsInY)));

        List<Room> placedRooms = dungeonGenerator.placeRoomsInArea(rooms, 1, rowCount, columnCount,
                DungeonGenerator.MIN_TILE_SIZE);

        grid = dungeonGenerator.getCopyOfGrid();

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
                int tileValue = grid.getTile(new GridIndex(row, column));
                if (tileValue >= 0) {
                    if (tileValue < placedRooms.size()) {
                        rect.setFill(roomColors[tileValue]);
                    } else {
                        rect.setFill(Color.WHITE);
                    }
                } else if (tileValue == Grid.BORDER_VALUE) {
                    // rect.setFill(Color.BLUE);
                }
                rect.setStroke(Color.RED);
                center.getChildren().add(rect);
            }
        }
        Point2D pos = placedRooms.get(0)
                .getPosition();
        player = new Rectangle(pos.getX(), pos.getY(), 6.0, 6.0);
        player.setFill(Color.RED);
        player.setStroke(Color.BLACK);
        // center.getChildren().add(player);
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
            GridIndex index = grid.getGriddIndexBasedOnPosition(point);
            GridIndex index2 = grid.getGriddIndexBasedOnPosition(
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

    private double[] randomMultipliersForAreaIsFilled(int roomCount, int roomsInX, int roomsInY) {
        double[] randomMultipliers = new double[roomCount * 2]; // (x, y) till varje rum därför * 2
        int index = 0;
        for (int y = 0; y < roomsInY; y++) {
            for (int x = 0; x < roomsInX; x++) {
                // roomsIn* - 1.0 så det går från 0.0 till 1.0 inclusive
                randomMultipliers[index++] = (double) x / ((double) roomsInX - 1.0);
                randomMultipliers[index++] = (double) y / ((double) roomsInY - 1.0);
            }
        }
        return randomMultipliers;
    }
}
