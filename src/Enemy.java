import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;
import java.util.Random;
import java.util.ArrayList;

public class Enemy {

    protected int hitPoints;
    protected int damage;
    protected double x = StudentVsHomework.getScreenWidth(), y;
    protected double vx, vy = 0; // pixels per second
    protected double scale;
    protected int scaledWidth, scaledHeight;
    protected long lastAttackTime = (long) (System.nanoTime() / (Math.pow(10, 9)));
    protected boolean attack = false;
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

    public int getHitPoints() {
        return this.hitPoints;
    }

    public int[] getCoordinate() {
        return this.occupiedGrid.getCoordinate();
    }

    public void takeHit(int damage) {
        this.hitPoints -= damage;
    }

    private void setOccupiedGrid() {
        boolean found = false;
        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids[i].length; j++) {
                if (grids[i][j].enemyIsOnGrid(this)) {
                    found = true;
                    this.occupiedGrid = grids[i][j];
                    break;
                }
            }
            if (found) {
                break;
            }
        }
        if (!found) {
            int[] t = {100,100};
            Grid tmp = new Grid(10000, 10000, false, t); // there was an error with the null so I added this to bandage it
//            this.occupiedGrid = null;
            this.occupiedGrid = tmp;
        }
    }

    private Tower chooseTarget() {
        ArrayList<Tower> towers = Level.getTowers();
        for (int i = 0; i < towers.size(); i++) {
            int[] enemyCoordinate = this.occupiedGrid.getCoordinate();
            int[] towerCoordinate = towers.get(i).getCoordinate();
            if (enemyCoordinate[0] == towerCoordinate[0] && enemyCoordinate[1] == towerCoordinate[1]) {
                return towers.get(i);
            }
        }
        return null;
    }

    private void attack() {
        Tower target = chooseTarget();
        long currentTime = (long) (System.nanoTime() / (Math.pow(10, 9)));
        long difference = currentTime - this.lastAttackTime;
        if (target != null) {
            if (difference >= 1) {
                this.attack = true;
                target.takeHit(this.damage);
                this.lastAttackTime = currentTime;
            }
        } else {
            this.attack = false;
        }
    }

    private void move() {
        this.x += this.vx;
        this.y += this.vy;
    }

    public void behave() {
        move();
        setOccupiedGrid();
        attack();
    }

    public void paint(Graphics2D g2d) {
        if (this.attack) {
            // figure out how to rotate images!
        } else {
            g2d.drawImage(this.image, (int) this.x, (int) this.y, this.scaledWidth, this.scaledHeight, null);
        }
    }

}