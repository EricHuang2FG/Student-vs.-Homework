import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Shredder extends Tower {
    private double attackDelay = 30;
    private String imagePath = "res\\towers\\paper_shredder.png";
    // private String imagePath = "../res/towers/paper_shredder.png";

    public Shredder(int[] coordinate) {
        super(coordinate);
        super.hitPoints = 200;
        super.range = 2;
        super.scale = 0.9;
        try {
            super.image = ImageIO.read(new File(imagePath));
            super.image2 = ImageIO.read(new File("res\\towers\\paper_shredder_unready.png"));
        } catch (IOException e) {
            System.out.println("Error loading image: \n" + e);
        }
        super.scaledWidth = (int) (super.image.getWidth() * super.scale);
        super.scaledHeight = (int) (super.image.getHeight() * super.scale);
    }

    public void attack() {
        if (lastFired >= 50 * attackDelay) {
            Weapon weapon = new Weapon("shredder", x, y, this);
            projectiles.add(weapon);
            lastFired = 0;
        } else {
//            lastFired += 1;
        }
    }
    public void paint(Graphics2D g2d) {
        if (lastFired >= 50 * attackDelay - 10){ //ready to attack
            g2d.drawImage(this.image, x, y, this.scaledWidth, this.scaledHeight, null);
        } else { //not ready to attack
            g2d.drawImage(this.image2, x, y, this.scaledWidth, this.scaledHeight, null);
        }
    }
}
