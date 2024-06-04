import java.awt.*;
import java.util.Random;

public class Donald extends Enemy {

    private int[] targetCoordinate = new int[2];
    private double targetY;
    private long hoverStartTime;
    private boolean timeSet = false;
    private long timeForSpawningEnemies = 2;
    private boolean enemySpawned = false;
    private int stage = 1;
    private final double verticalMovementVelocity = 9;

    public Donald(String type, Map map) {
        super(type, map);
        Random random = new Random();
        this.targetCoordinate[0] = random.nextInt(5, 9);
        this.targetCoordinate[1] = random.nextInt(2, 5);
        super.x = Map.getMapStartX() + (Grid.getWidth() * (this.targetCoordinate[0] - 1)) + (Grid.getWidth() / 2.0) - (super.scaledWidth / 2.0);
        super.y = -super.scaledHeight;
        this.targetY = (Map.getMapStartY() + (Grid.getWidth() * (this.targetCoordinate[1] - 1))) + (Grid.getWidth() / 2.0) - super.scaledHeight;
        super.vx = 0.0;
        super.vy = this.verticalMovementVelocity;
        System.out.println("Mr. Donald spawned ");
    }

    private void spawnEnemies() {
        int[] enemyCoordinate1 = {this.targetCoordinate[0], this.targetCoordinate[1] - 1};
        Level.spawnEnemy("donald_enemy", super.map, enemyCoordinate1);
        int[] enemyCoordinate2 = {this.targetCoordinate[0] + 1, this.targetCoordinate[1]};
        Level.spawnEnemy("donald_enemy", super.map, enemyCoordinate2);
        int[] enemyCoordinate3 = {this.targetCoordinate[0], this.targetCoordinate[1] + 1};
        Level.spawnEnemy("donald_enemy", super.map, enemyCoordinate3);
    }

    private void behaveStageActivity() {
        if (this.stage == 1) {
            super.vy = this.verticalMovementVelocity;
            if (super.y >= this.targetY) {
                this.stage++;
            }
        } else if (this.stage == 2) {
            if (!this.timeSet) {
                this.hoverStartTime = (long) (System.nanoTime() / (Math.pow(10, 9)));
                this.timeSet = true;
            }
            super.vy = 0.0;
            long currentTime = (long) (System.nanoTime() / (Math.pow(10, 9)));
            long difference = currentTime - this.hoverStartTime;
            if (difference >= this.timeForSpawningEnemies / 2 && !this.enemySpawned) {
                spawnEnemies();
                this.enemySpawned = true;
            }
            if (difference >= this.timeForSpawningEnemies) {
                this.stage++;
            }
        } else if (this.stage == 3) {
            super.vy = -this.verticalMovementVelocity;
            if (super.y < -super.scaledHeight) {
                super.hitPoints = 0; // this will activate the deletion of Donald from the list
            }
        }
    }

    @Override
    public void behave() {
        behaveStageActivity();
        move();
    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(2));
        int lineX = (int) (super.x + (super.scaledWidth / 2));
        int lineY = (int) super.y;
        g2d.drawLine(lineX, 0, lineX, lineY);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawImage(super.images[0], (int) super.x, (int) super.y, super.scaledWidth, super.scaledHeight, null);
    }

}