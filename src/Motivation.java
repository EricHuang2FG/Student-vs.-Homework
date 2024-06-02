import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.util.Random;

public class Motivation {

    private static final int VALUE = 25;
    private final int collectedLocationX = 20, collectedLocationY = 20;
    private final double collectedMovementTime = 13.0;
    private String type;
    private double vx, vy;
    private double x, y;
    private double scale = 0.07;
    private int scaledWidth, scaledHeight;
    private boolean isCollected = false;
    private BufferedImage image  = null;

    public Motivation(String type) {
        this.type = type;
        try {
            this.image = ImageIO.read(new File("res\\motivation.png"));
        } catch (IOException e) {
            System.out.println("Error loading image: \n" + e);
        }
        this.scaledWidth = (int) (this.image.getWidth() * this.scale);
        this.scaledHeight = (int) (this.image.getHeight() * this.scale);
        if (this.type.equals("air_drop")) {
            Random random = new Random();
            this.x = random.nextInt(20, StudentVsHomework.getScreenWidth() - this.scaledWidth - 29);
            this.y = -this.scaledHeight;
            this.vx = 0.0;
            this.vy = 4.0;
        } else if (this.type.equals("water_bottle")) {
            // finish this later
        }
    }

    public static int getValue() {
        return VALUE;
    }

    public boolean isClickedByMouse(int x, int y) {
        return ((x > this.x && x < this.x + this.scaledWidth) && (y > this.y && y < this.y + this.scaledHeight)) && !this.isCollected;
    }

    public void setCollectedVelocities() {
        this.isCollected = true;
        this.vx = -(this.x - this.collectedLocationX) / this.collectedMovementTime;
        this.vy = -(this.y - this.collectedLocationY) / this.collectedMovementTime;
    }

    private void move() {
        this.x += this.vx;
        this.y += this.vy;
        if (this.type.equals("air_drop") && !this.isCollected) {
            if (this.y >= StudentVsHomework.getScreenHeight() - 100) {
                this.vy = 0.0;
            }
        }
    }

    public void behave() { // INCOMPLETE
        move();
    }

    public void paint(Graphics2D g2d) {
        g2d.drawImage(this.image, (int) this.x, (int) this.y, this.scaledWidth, this.scaledHeight, null);
    }

}