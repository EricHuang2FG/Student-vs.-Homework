import java.awt.*;
import java.util.Random;

public class Donald extends Enemy {

    private int[] targetCoordinate = new int[2];
    private long timeSpawned = (long) (System.nanoTime() / (Math.pow(10, 9)));
    private long timeForDescend = 2;
    private long timeForSpawningEnemies = 1;
    private long timeForAscend = this.timeForDescend;
    private final double verticalMovementVelocity;

    public Donald(String type, Map map) {
        super(type, map);
        Random random = new Random();
        this.targetCoordinate[0] = random.nextInt(4, 9);
        this.targetCoordinate[1] = random.nextInt(2, 5);
        super.x = Map.getMapStartX() + (Grid.getWidth() * (this.targetCoordinate[0] - 1)) + (Grid.getWidth() / 2.0) - (super.scaledWidth / 2.0);
        super.y = -super.scaledHeight;
        this.verticalMovementVelocity = ((Map.getMapStartY() + (Grid.getWidth() * (this.targetCoordinate[1] - 1))) - super.y) / (this.timeForDescend * 1.0);
        super.vx = 0.0;
        super.vy = this.verticalMovementVelocity;
        System.out.println("Mr. Donald spawned ");
    }

    private void setVelocities() {
        long currentTime = (long) (System.nanoTime() / (Math.pow(10, 9)));
        long difference = currentTime - this.timeSpawned;
        if (difference <= this.timeForDescend) {
            super.vy = this.verticalMovementVelocity;
        } else if (difference <= this.timeForDescend + this.timeForSpawningEnemies) {
            super.vy = 0.0;
        } else if (difference <= this.timeForDescend + this.timeForSpawningEnemies + this.timeForAscend) {
            super.vy = -this.verticalMovementVelocity;
        } else {
            super.hitPoints = 0; // this will cause the enemy to be deleted
        }
    }

    @Override
    public void behave() {
        setVelocities();
        move();
    }

    @Override
    public void paint(Graphics2D g2d) {
        // add the string drawing
        g2d.drawImage(super.images[0], (int) super.x, (int) super.y, super.scaledWidth, super.scaledHeight, null);
    }

}