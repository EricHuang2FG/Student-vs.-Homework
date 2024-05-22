import java.awt.*;
import java.util.ArrayList;
import java.lang.*;
import java.util.Random;

public class Level {

    private int levelNumber = 1;
    private boolean wave = false;
    private int totalEnemies = 5 * (this.levelNumber * this.levelNumber) + 10 * this.levelNumber;
    private long startTime = (long) (System.nanoTime() / (Math.pow(10, 9)));
    private Map map = new Map();
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private boolean spawn = true;
    private int betweenWavesSpawnCoolDown = 15;

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
        this.totalEnemies = 5 * (this.levelNumber * this.levelNumber) + 10 * this.levelNumber;
    }

    private String chooseEnemy() {
        Random random = new Random();
        int num = random.nextInt(1, 101);
        if (num >= 1 && num <= 20) {
            return "paper";
        } else if (num >= 21 && num <= 30) {
            return "notebook";
        } else if (num >= 31 && num <= 35) {
            return "textbook";
        } else if (num >= 36 && num <= 75) {
            return "notepad";
        } else {
            return "notepad"; // TEMPORARY
        }
    }

    private void executeLevelLogic() {
        long currentTime = (long) (System.nanoTime() / (Math.pow(10, 9)));
        long timeElapsed = currentTime - startTime;
        if ((timeElapsed == this.betweenWavesSpawnCoolDown || timeElapsed == 2 * this.betweenWavesSpawnCoolDown || timeElapsed == 3 * this.betweenWavesSpawnCoolDown) && this.spawn) {
            enemies.add(new Enemy(chooseEnemy()));
            this.spawn = false;
        } else if ((timeElapsed == this.betweenWavesSpawnCoolDown + 1 || timeElapsed == 2 * this.betweenWavesSpawnCoolDown + 1 || timeElapsed == 2 * this.betweenWavesSpawnCoolDown + 1) && !this.spawn)  {
            this.spawn = true;
        }
    }

    private void moveEnemies() {
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).move();
        }
    }

    public void behave() {
        executeLevelLogic();
        moveEnemies();
    }

    public void paint(Graphics2D g2d) {
        map.paint(g2d);
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).paint(g2d);
        }
    }

}