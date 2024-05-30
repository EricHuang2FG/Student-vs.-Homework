import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Pencil extends Tower {

    private static int lastPlaced = 10000000;
    private double attackDelay = 6;

    private String imagePath = "res\\towers\\pencil.png";
    // private String imagePath = "../res/towers/pencil.png";

    public Pencil(int[] coordinate) {
        super(coordinate);
        super.hitPoints = 100;
        super.range = 10;
        super.scale = 0.9;
        try {
            super.image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.out.println("Error loading image: \n" + e);
        }
        super.scaledWidth = (int) (image.getWidth() * scale);
        super.scaledHeight = (int) (image.getHeight() * scale);
    }

    public void attack(){
        if (lastFired >= 50 * attackDelay){
            Weapon weapon = new Weapon("pencil", x, y, this);
            projectiles.add(weapon);
            lastFired = 0;
        } else {
            lastFired += 1;
        }
    }
    public void paint(Graphics2D g2d){
        g2d.drawImage(this.image, x, y, this.scaledWidth, this.scaledHeight, null);
    }
}


//100 HP
//Shoots lead in a straight line every 2 seconds with infinite range
//Each lead breaks once it hits a zombie
//The lead does 40 HP of damage
//Costs 100 motivation
//Cooldown time is 5 seconds