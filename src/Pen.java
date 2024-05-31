import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Pen extends Tower {

    private double attackDelay = 3;
    private String imagePath = "res\\towers\\pen.png";
//    private String type = "pen";
    // private String imagePath = "../res/towers/pen.png";

    public Pen(int[] coordinate) {
        super(coordinate);
        super.hitPoints = 100;
        super.range = 3;
        super.scale = 0.9;
        try {
            super.image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.out.println("Error loading image: \n" + e);
        }
        super.scaledWidth = (int) (super.image.getWidth() * super.scale);
        super.scaledHeight = (int) (super.image.getHeight() * super.scale);
    }

    public void attack() {
        if (lastFired >= 50 * attackDelay) {
            Weapon weapon = new Weapon("pen", x, y, this);
            projectiles.add(weapon);
            lastFired = 0;
        } else {
            lastFired += 1;
        }
    }

    public void paint(Graphics2D g2d) {
        g2d.drawImage(this.image, x, y, this.scaledWidth, this.scaledHeight, null);
    }
}
