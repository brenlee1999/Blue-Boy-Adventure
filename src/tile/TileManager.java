package tile;

import main.GamePanel;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

public class TileManager {

    GamePanel gamePanel;
    public Tile[] tiles;
    public int[][] mapTileNumber;

    public TileManager(@NotNull GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        tiles = new Tile[10];
        mapTileNumber = new int[gamePanel.maxWorldColumn][gamePanel.maxWorldRow];

        getTileImage();
        loadMap("/maps/world01.txt");
    }

    public void getTileImage() {
        try {
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(new FileInputStream("res\\tiles\\grass.png"));

            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(new FileInputStream("res\\tiles\\wall.png"));
            tiles[1].collision = true;

            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(new FileInputStream("res\\tiles\\water.png"));
            tiles[2].collision = true;

            tiles[3] = new Tile();
            tiles[3].image = ImageIO.read(new FileInputStream("res\\tiles\\earth.png"));

            tiles[4] = new Tile();
            tiles[4].image = ImageIO.read(new FileInputStream("res\\tiles\\tree.png"));
            tiles[4].collision = true;

            tiles[5] = new Tile();
            tiles[5].image = ImageIO.read(new FileInputStream("res\\tiles\\sand.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        System.out.println("Trying to load map...");
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            assert is != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));

            int column = 0;
            int row = 0;
            while (column < gamePanel.maxWorldColumn && row < gamePanel.maxWorldRow) {
                String line = bufferedReader.readLine();
                while (column < gamePanel.maxWorldColumn) {
                    String[] numbers = line.split(" ");

                    int number = Integer.parseInt(numbers[column]);

                    mapTileNumber[column][row] = number;
                    column++;
                }
                if (column == gamePanel.maxWorldColumn) {
                    column = 0;
                    row++;
                }
            }
            System.out.println("Map loaded successfully!");
            bufferedReader.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D graphics2D) {
        int worldColumn = 0;
        int worldRow = 0;

        while (worldColumn < gamePanel.maxWorldColumn && worldRow < gamePanel.maxWorldRow) {

            int tileNumber = mapTileNumber[worldColumn][worldRow];

            int worldX = worldColumn * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                    worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                    worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                    worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {
                graphics2D.drawImage(tiles[tileNumber].image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
            }
            worldColumn++;

            if (worldColumn == gamePanel.maxWorldColumn) {
                worldColumn = 0;
                worldRow++;
            }
        }
    }
}