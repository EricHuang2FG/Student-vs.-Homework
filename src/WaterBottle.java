import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.io.IOException;

public class WaterBottle extends Tower {

    private long lastMotivationSpawnTime = (long) (System.nanoTime() / (Math.pow(10, 9)));
    private long motivationSpawnCoolDown = 15;
    private static String cost = "50";
    private static int spawnCoolDown = 5;

    public WaterBottle(int[] coordinate) {
        super(coordinate);
        super.hitPoints = 100;
        super.range = 0;
        super.scale = 0.9;
        super.type = "water_bottle";
        try {
            super.image = ImageIO.read(new File("res\\towers\\water_bottle.png"));
        } catch (IOException e) {
            System.out.println("Error loading image: \n" + e);
        }
        super.scaledWidth = (int) (super.image.getWidth() * super.scale);
        super.scaledHeight = (int) (super.image.getHeight() * super.scale);
    }

    public static String getCost() {
        return cost;
    }

    public static int getSpawnCoolDown() {
        return spawnCoolDown;
    }

    public void setLastMotivationSpawnTime(long time) {
        this.lastMotivationSpawnTime = time;
    }

    public void attack() {
        // empty method to satisfy abstract requirements since it doesn't attack
    }

    @Override
    public void behave() {
        long currentTime = (long) (System.nanoTime() / (Math.pow(10, 9)));
        long difference = currentTime - this.lastMotivationSpawnTime;
        if (difference >= this.motivationSpawnCoolDown) {
            Level.spawnMotivation("water_bottle", this);
            this.lastMotivationSpawnTime = currentTime;
        }
    }

    public void paint(Graphics2D g2d) {
        g2d.drawImage(super.image, super.x, super.y, super.scaledWidth, super.scaledHeight, null);
    }

}