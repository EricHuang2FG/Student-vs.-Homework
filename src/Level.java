import java.awt.*;
import java.util.ArrayList;
import java.lang.*;
import java.util.Random;

public class Level {

    private int levelNumber = 1;
    private boolean wave = false;
    private int waveCount = 0;
    private int totalEnemies = 5 * (this.levelNumber * this.levelNumber) + 10 * this.levelNumber;
//    private int enemiesPerWave = (int) ((totalEnemies - 6) / 3);
    private int enemiesSpawnedBetweenWaves = 0;
    private int enemiesSpawnedDuringWave = 0;
    private long startTime = (long) (System.nanoTime() / (Math.pow(10, 9)));
    private Map map = new Map();
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private boolean spawn = true;
    private int betweenWavesSpawnCoolDown = 20;

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
        this.waveCount = 0;
        this.enemiesSpawnedBetweenWaves = 0;
        this.enemiesSpawnedDuringWave = 0;
        this.startTime = (long) (System.nanoTime() / (Math.pow(10, 9)));
        this.enemies.clear();
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

    private double waveFactor(){
        if (waveCount == 1){
            return 0.17;
        }
        else if (waveCount == 2){
            return 0.33;
        }
        else{
            return 0.5;
        }
    }

    private void executeLevelLogic() {
        long currentTime = (long) (System.nanoTime() / (Math.pow(10, 9)));
        long timeElapsed = currentTime - startTime;
        if (this.totalEnemies > 0) {
            if (!this.wave) {
                if (timeElapsed != 0 && timeElapsed % this.betweenWavesSpawnCoolDown == 0 && this.spawn) {
                    if (this.enemiesSpawnedBetweenWaves < 2) {
                        enemies.add(new Enemy(chooseEnemy()));
                        this.spawn = false;
                        this.enemiesSpawnedBetweenWaves++;
                    } else {
                        this.enemiesSpawnedBetweenWaves++; // adds a cooldown before the wave starts
                    }
                } else if ((timeElapsed - 1) % this.betweenWavesSpawnCoolDown == 0 && !this.spawn) {
                    this.spawn = true;
                }
                if (this.enemiesSpawnedBetweenWaves == 3 && this.waveCount != 3) {
                    this.wave = true;
                    this.spawn = true;
                    this.enemiesSpawnedDuringWave = 0;
                    this.waveCount++;
                    System.out.println("Wave " + this.waveCount);
                }
            } else {
                if (timeElapsed % 3 == 0 && this.spawn) {
                    enemies.add(new Enemy(chooseEnemy()));
                    this.spawn = false;
                    this.enemiesSpawnedDuringWave++;
                    this.totalEnemies--;
                } else if ((timeElapsed - 1) % 3 == 0 && !this.spawn) {
                    this.spawn = true;
                }
                if (this.enemiesSpawnedDuringWave >= (int) (this.totalEnemies * waveFactor())) {
                    this.enemiesSpawnedBetweenWaves = 0;
                    this.wave = false;
                    this.spawn = true;
                }
            }
        }
        if (this.waveCount == 3 && this.enemiesSpawnedDuringWave == this.totalEnemies * waveFactor() && this.enemies.size() == 0) {
            // you win
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