import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MechanicalPencil extends Tower {

    private double attackDelay = 1.5;
    private String imagePath = "res\\towers\\mechanic_pencil.png";
    private static String cost = "225";
    private static int spawnCoolDown = 5;

    public MechanicalPencil(int[] coordinate) {
        super(coordinate);
        super.hitPoints = 100;
        super.range = 10;
        super.scale = 0.9;
        super.type = "mechanical_pencil";
        try {
            super.image = ImageIO.read(new File(this.imagePath));
            super.image2 = ImageIO.read(new File("res\\towers\\mechanical_pencil_unready.png"));
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

    public void attack() {
        if (lastFired >= 50 * attackDelay) {
            Weapon weapon = new Weapon("mechanical_pencil", x, y, this);
            projectiles.add(weapon);
            lastFired = 0;
        } else {
//            lastFired += 1;
        }
    }

    public void paint(Graphics2D g2d) {
        if (lastFired >= 50 * attackDelay - 10) { //ready to attack
            g2d.drawImage(this.image, x, y, this.scaledWidth, this.scaledHeight, null);
        } else { //not ready to attack
            g2d.drawImage(this.image2, x, y, this.scaledWidth, this.scaledHeight, null);
        }
    }
}
