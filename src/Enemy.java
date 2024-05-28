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
    protected Grid occupiedGrid = null;
    protected BufferedImage image = null;
    protected Map map;
    protected Grid[][] grids;

    public Enemy(String type, Map map) {
        Random random = new Random();
        String imagePath = "res\\monsters\\" + type + ".png";
        // String imagePath = "../res/monsters/" + type + ".png";
        int row = random.nextInt(1, 6);
        if (type.equals("paper")) {
            this.hitPoints = 200;
            this.damage = 30;
            this.vx = -0.3;
            this.scale = 0.16;
        } else if (type.equals("notebook")){
            this.hitPoints = 600;
            this.damage = 30;
            this.vx = -0.3;
            this.scale = 0.9;
        } else if (type.equals("textbook")) {
            this.hitPoints = 1200;
            this.damage = 30;
            this.vx = -0.3;
            this.scale = 1.0;
        } else if (type.equals("notepad")) {
            this.hitPoints = 50;
            this.damage = 20;
            this.vx = -0.5;
            this.scale = 0.9;
        } else if (type.equals("test")) {
            this.hitPoints = 600;
            this.damage = 100;
            this.vx = -0.75;
            this.scale = 0.9;
        } else if (type.equals("exam")) {
            this.hitPoints = 1200;
            this.damage = 300;
            this.vx = -0.75;
            this.scale = 1.0;
        }
        try {
            this.image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.out.println("Error loading image: \n" + e);
        }
        this.scaledWidth = (int) (this.image.getWidth() * this.scale);
        this.scaledHeight = (int) (this.image.getHeight() * this.scale);
        this.y = (int) (Map.getMapStartY() + (((row - 1) * Grid.getWidth()) + (Grid.getWidth() / 2) - (this.scaledHeight / 2)));
        this.map = map;
        this.grids = map.getGrids();
        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids[i].length; j++) {
                System.out.println((j + 1) + ", " + (i + 1));
            }
        }
    }

    public int getX() {
        return (int) (this.x);
    }

    public int getY() {
        return (int) (this.y);
    }

    public int getWidth() {
        return this.scaledWidth;
    }

    public int getHeight() {
        return this.scaledHeight;
    }

    private void setOccupiedGrid() {
        boolean found = false;
        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids[i].length; j++) {
                if (grids[i][j].enemyIsOnGrid(this)) {
                    found = true;
                    this.occupiedGrid = grids[i][j];
                    System.out.println(grids[i][j].getCoordinate()[0] + ", " + grids[i][j].getCoordinate()[1]);
                    break;
                }
            }
            if (found) {
                break;
            }
        }
        if (!found) {
            this.occupiedGrid = null;
        }
    }

    public void takeHit(int damage) {
        this.hitPoints -= damage;
    }

    private void move() {
        this.x += this.vx;
        this.y += this.vy;
    }

    public void behave() {
        move();
        setOccupiedGrid();
    }

    public void paint(Graphics2D g2d) {
        g2d.drawImage(this.image, (int) this.x, (int) this.y, this.scaledWidth, this.scaledHeight, null);
    }
    
}