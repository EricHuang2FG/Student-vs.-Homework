import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class SuperEraser extends Tower {

    private static String cost = "100";
    private static int spawnCoolDown = 15;
    private BufferedImage image3 = null;

    public SuperEraser(int[] coordinate) {
        super(coordinate);
        super.hitPoints = 3000;
        super.scaledWidth = 100;
        super.scaledHeight = 100;
        super.type = "super_eraser";
        String imagePath = "res\\towers\\" + super.type + ".png";
        String imagePath2 = "res\\towers\\" + "super_eraser_damaged" + ".png";
        try {
            super.image = ImageIO.read(new File(imagePath));
            image3 = ImageIO.read(new File(imagePath2));
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
        if (hitPoints <= 1000){
            g2d.drawImage(this.image3, x, y, this.scaledWidth, this.scaledHeight, null);
        }
        else{
            g2d.drawImage(this.image, x, y, this.scaledWidth, this.scaledHeight, null);
        }
    }
}
