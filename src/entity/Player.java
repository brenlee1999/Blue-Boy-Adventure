package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {

    GamePanel gamePanel;
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);

        this.worldX = gamePanel.tileSize * 23;
        this.worldY = gamePanel.tileSize * 21;

        solidArea = new Rectangle(8, 16, 32, 32);

        this.speed = 4;
        this.direction = "down";
        getPlayerImage();
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(new FileInputStream("res\\player\\boy_up_1.png"));
            up2 = ImageIO.read(new FileInputStream("res\\player\\boy_up_2.png"));
            down1 = ImageIO.read(new FileInputStream("res\\player\\boy_down_1.png"));
            down2 = ImageIO.read(new FileInputStream("res\\player\\boy_down_2.png"));
            left1 = ImageIO.read(new FileInputStream("res\\player\\boy_left_1.png"));
            left2 = ImageIO.read(new FileInputStream("res\\player\\boy_left_2.png"));
            right1 = ImageIO.read(new FileInputStream("res\\player\\boy_right_1.png"));
            right2 = ImageIO.read(new FileInputStream("res\\player\\boy_right_2.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public void setDefaultValues(){
        x = 100;
        y = 100;
        speed = 4;
    }*/
    public void update() {
        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed) {
            if (keyHandler.rightPressed)
                direction = "right";
            else if (keyHandler.downPressed)
                direction = "down";
            else if (keyHandler.upPressed)
                direction = "up";
            else
                direction = "left";
            //Check tile collision
            collisionOn = false;
            gamePanel.collisionDetection.checkTile(this);

            //If collision is false, player can move.
            if (!collisionOn) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            spriteCounter++;
            if (spriteCounter > 20) {
                if (spriteNumber == 1) {
                    spriteNumber = 2;
                } else if (spriteNumber == 2) {
                    spriteNumber = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    public void draw(Graphics2D graphics2D) {
        BufferedImage image = null;
        switch (direction) {
            case "up" -> {
                if (spriteNumber == 1)
                    image = up1;
                if (spriteNumber == 2)
                    image = up2;
            }
            case "down" -> {
                if (spriteNumber == 1)
                    image = down1;
                if (spriteNumber == 2)
                    image = down2;
            }
            case "left" -> {
                if (spriteNumber == 1)
                    image = left1;
                if (spriteNumber == 2)
                    image = left2;
            }
            case "right" -> {
                if (spriteNumber == 1)
                    image = right1;
                if (spriteNumber == 2)
                    image = right2;
            }
            default -> {
            }
        }
        graphics2D.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
