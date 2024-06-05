import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.util.Random;

public class Motivation {

    private static final int VALUE = 25;
    private final int collectedLocationX = 20, collectedLocationY = 0;
    private final double collectedMovementTime = 15.0;
    private String type;
    private double vx, vy;
    private double x, y;
    private double ay;
    private double scale = 0.07;
    private int scaledWidth, scaledHeight;
    private boolean isCollected = false;
    private boolean canDelete = false;
    private BufferedImage image  = null;

    public Motivation(String type, WaterBottle host) {
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
            this.vy = 3.0;
            this.ay = 0;
        } else if (this.type.equals("water_bottle")) {
            this.x = host.getX() + (host.getScaledWidth() / 2.0);
            this.y = host.getY();
            this.vx = 0.7;
            this.vy = -3.0;
            this.ay = 0.2;
        }
    }

    public static int getValue() {
        return VALUE;
    }

    public boolean getCanDelete() {
        return this.canDelete;
    }

    public boolean isClicked(int x, int y) {
        return ((x > this.x && x < this.x + this.scaledWidth) && (y > this.y && y < this.y + this.scaledHeight)) && !this.isCollected;
    }

    public void setCollectedVelocities() {
        this.isCollected = true;
        this.vx = -(this.x - this.collectedLocationX) / this.collectedMovementTime;
        this.vy = -(this.y - this.collectedLocationY) / this.collectedMovementTime;
    }

    private void judgeCanDelete() {
        this.canDelete = (this.x + this.scaledWidth < 0) && this.isCollected;
    }

    private void move() {
        this.x += this.vx;
        this.y += this.vy;
        this.vy += this.ay;
        if (this.type.equals("air_drop") && !this.isCollected) {
            if (this.y >= StudentVsHomework.getScreenHeight() - 100) {
                this.vy = 0.0;
            }
        } else if (this.type.equals("water_bottle") && !this.isCollected) {
            if (this.vy > 6.0) {
                this.vx = 0.0;
                this.vy = 0.0;
                this.ay = 0.0;
            }
        }
    }

    public void behave() { // INCOMPLETE
        judgeCanDelete();
        move();
    }

    public void paint(Graphics2D g2d) {
        g2d.drawImage(this.image, (int) this.x, (int) this.y, this.scaledWidth, this.scaledHeight, null);
    }

}