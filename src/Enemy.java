import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;
import java.util.Random;

public class Enemy {

    protected int hitPoints;
    protected int damage;
    protected int lastAttackTime = 0;
    protected double x = StudentVsHomework.getScreenWidth(), y;
    protected double vx, vy = 0; // pixels per second
    protected double scale;
    protected int scaledWidth, scaledHeight;
    protected BufferedImage image = null;

    public Enemy(String type) {
        Random random = new Random();
        String imagePath = "res\\monsters\\" + type + ".png";
        // String imagePath = "../res/monsters/" + type + ".png";
        int row = random.nextInt(1, 6);
        if (type.equals("paper")) {
            this.hitPoints = 200;
            this.damage = 30;
            this.vx = -0.3;
            this.scale = 0.18;
        } else if (type.equals("notebook")){
            this.hitPoints = 600;
            this.damage = 30;
            this.vx = -0.3;
            this.scale = 0.9;
        } else if (type.equals("textbook")) {
            this.hitPoints = 1200;
            this.damage = 30;
            this.vx = -0.3;
            this.scale = 0.9;
        } else if (type.equals("notepad")) {
            this.hitPoints = 50;
            this.damage = 20;
            this.vx = -0.5;
            this.scale = 0.9;
        } else if (type.equals("test")) {

        }
        try {
            //
            this.image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.out.println("Error loading image: \n" + e);
        }
        this.scaledWidth = (int) (this.image.getWidth() * this.scale);
        this.scaledHeight = (int) (this.image.getHeight() * this.scale);
        this.y = (int) (Map.getMapStartY() + (((row - 1) * Grid.getWidth()) + (Grid.getWidth() / 2) - (this.scaledHeight / 2)));
    }

    public void move() {
        this.x += this.vx;
        this.y += this.vy;
    }

    public void paint(Graphics2D g2d) {
        g2d.drawImage(this.image, (int) this.x, (int) this.y, this.scaledWidth, this.scaledHeight, null);
    }
    
}