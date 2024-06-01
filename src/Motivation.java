import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.util.Random;

public class Motivation {

    private int value = 25;
    private int vx, vy;
    private int x, y;
    private double scale = 0.3;
    private int scaledWidth, scaledHeight;
    private BufferedImage image  = null;

    public Motivation(String type) {
        try {
            this.image = ImageIO.read(new File("res\\motivation.png"));
        } catch (IOException e) {
            System.out.println("Error loading image: \n" + e);
        }
        this.scaledWidth = (int) (this.image.getWidth() * this.scale);
        this.scaledHeight = (int) (this.image.getHeight() * this.scale);
        if (type.equals("air_drop")) {
            Random random = new Random();
            this.x = random.nextInt(10, StudentVsHomework.getScreenWidth() - this.scaledWidth - 19);
            this.y = -this.scaledHeight;
            this.vx = 0;
            this.vy = 10;
        } else if (type.equals("water_bottle")) {

        }
    }

    public int getValue() {
        return this.value;
    }

    private void move() {
        this.x += this.vx;
        this.y += this.vy;
    }

    public void behave() { // INCOMPLETE
        move();
    }

    public void paint(Graphics2D g2d) {
        g2d.drawImage(this.image, this.x, this.y, this.scaledWidth, this.scaledHeight, null);
    }

}