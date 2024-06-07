import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Eraser extends Tower {

    private static int lastPlaced = 10000000;
    private String imagePath = "res\\towers\\eraser.png";
    private String type = "eraser";
    private static String cost = "50";
    private static int spawnCoolDown = 15;

    public Eraser(int[] coordinate) {
        super(coordinate);
        super.hitPoints = 1500;
        super.scaledWidth = 100;
        super.scaledHeight = 100;
        super.type = "eraser";
        String imagePath = "res\\towers\\" + super.type + ".png";
        try {
            super.image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.out.println("Error loading image: \n" + e);
        }
    }

    public static String getCost() {
        return cost;
    }

    public static int getSpawnCoolDown() {
        return spawnCoolDown;
    }

    public void attack() {
        // empty method to satisfy abstract requirements
    }

    public void paint(Graphics2D g2d) {
        g2d.drawImage(this.image, x, y, this.scaledWidth, this.scaledHeight, null);
    }
}
