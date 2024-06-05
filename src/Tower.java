import java.awt.*;
import java.awt.image.*;
import java.util.ArrayList;

public abstract class Tower {

    protected int hitPoints;
    protected int lastFired = 1000000000;
    protected static ArrayList<Weapon> projectiles = new ArrayList<Weapon>();
    protected int x, y; // store its position as a pixel coordinate
    protected int range = 10;
    protected int[] coordinate;
    protected BufferedImage image = null;
    protected BufferedImage image2 = null;
    protected int scaledWidth, scaledHeight;
    protected double scale;

    public Tower(int[] coordinate) {
        this.coordinate = coordinate;
        this.x = coordinate[0] * Grid.getWidth() - Grid.getWidth() + Map.getMapStartX();
        this.y = coordinate[1] * Grid.getWidth() - Grid.getWidth() + Map.getMapStartY();
    }

    public static void moveProjectiles() {
        for (int i = 0; i < projectiles.size(); i++){
            projectiles.get(i).behave();
        }
    }

    public static void deletionCheck() {
        int i = 0;
        while (i < projectiles.size()) {
            Weapon p = projectiles.get(i);
            if (p.getDelete() || p.outOfBounds()) {
                projectiles.remove(i);
            } else {
                i += 1;
            }
        }
    }

    public abstract void attack();

    public void behave() {
        // empty method so that it can be used by the WaterBottle class while not used by others
    }

    public void takeHit(int damage) {
        hitPoints -= damage;
    }

    public static ArrayList<Weapon> getProjectiles() {
        return projectiles;
    }

    public int getRange() {
        return this.range;
    }

    public int getHitPoints() {
        return this.hitPoints;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getScaledWidth() {
        return this.scaledWidth;
    }

    public int[] getCoordinate() {
        return coordinate;
    }

    public void incFiredCounter() {
        this.lastFired += 1;
    }

    public abstract void paint(Graphics2D g2d);
}
