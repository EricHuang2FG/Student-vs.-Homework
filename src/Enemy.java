import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;

public class Enemy {

    protected int hitPoints;
    protected int damage;
    protected int lastAttackTime = 0;
    protected int x = StudentVsHomework.getScreenWidth(), y;
    protected int vx, vy = 0;
    protected double scale;
    protected int scaledWidth, scaledHeight;
    protected BufferedImage image = null;

    public Enemy(String type) {
        String imagePath = "res\\monsters\\" + type + ".png"; 
        // String imagePath = "../res/monsters/" + type + ".png";
        if (type.equals("paper")) {
            this.hitPoints = 200;
            this.damage = 30;
            this.y = 0; // temporary
            this.vx = -2;
            this.vy = 0;
            this.scale = 0.5;
        } else if (type.equals("notebook")){
            this.hitPoints = 600;
            this.damage = 30;
            this.y = 0; // temporary
            this.vx = -2;
            this.vy = 0;
            this.scale = 0.5;
        } 
        try {
            //
            this.image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.out.println("Error loading image: \n" + e);
        }
        this.scaledWidth = (int) (this.image.getWidth() * this.scale);
        this.scaledHeight = (int) (this.image.getHeight() * this.scale);
    }

    public void paint(Graphics2D g2d) {
        g2d.drawImage(this.image, this.x, this.y, this.scaledWidth, this.scaledHeight, null);
    }
    
}