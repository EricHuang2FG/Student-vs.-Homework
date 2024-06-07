import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.io.IOException;

public class Pencil extends Tower {

    private double attackDelay = 3.0;
    private String imagePath = "res\\towers\\pencil.png";
    private static String cost = "100";
    private static int spawnCoolDown = 5;

    public Pencil(int[] coordinate) {
        super(coordinate);
        super.hitPoints = 100;
        super.range = 10;
        super.scale = 0.9;
        super.type = "pencil";
        try {
            super.image = ImageIO.read(new File(imagePath));
            super.image2 = ImageIO.read(new File("res\\towers\\pencil_unready.png"));
        } catch (IOException e) {
            System.out.println("Error loading image: \n" + e);
        }
        super.scaledWidth = (int) (image.getWidth() * scale);
        super.scaledHeight = (int) (image.getHeight() * scale);
    }

    public static String getCost() {
        return cost;
    }

    public static int getSpawnCoolDown() {
        return spawnCoolDown;
    }

    public void attack() {
        if (lastFired >= 50 * attackDelay) {
            Weapon weapon = new Weapon("pencil", x, y, this);
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


//100 HP
//Shoots lead in a straight line every 2 seconds with infinite range
//Each lead breaks once it hits a zombie
//The lead does 40 HP of damage
//Costs 100 motivation
//Cooldown time is 5 seconds