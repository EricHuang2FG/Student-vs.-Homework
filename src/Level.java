import java.awt.*;
import java.util.ArrayList;
import java.lang.*;
import java.util.Random;

public class Level {

    private int levelNumber = 1;
    private boolean wave = false;
    private int waveCount = 0;
    private int totalEnemies = 5 * (this.levelNumber * this.levelNumber) + 10 * this.levelNumber;
    private int enemiesSpawnedBetweenWaves = 0;
    private int enemiesSpawnedDuringWave = 0;
    private long startTime = (long) (System.nanoTime() / (Math.pow(10, 9)));
    private boolean spawn = true;
    private int betweenWavesSpawnCoolDown = 20; // TEMPORARY. Normally this is 20
    private long lastMotivationSpawnTime = (long) (System.nanoTime() / (Math.pow(10, 9)));
    private long motivationSpawnCoolDown = 2; // TEMPORARY
    private Map map = new Map();
    private static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private static ArrayList<Tower> towers = new ArrayList<Tower>();
    private static ArrayList<Motivation> motivationCoins = new ArrayList<Motivation>();

    public Level() {
        int[] what = {8, 1};
        MechanicalPencil p = new MechanicalPencil(what);
        towers.add(p);

        int[] what2 = {8, 2};
        MechanicalPencil p2 = new MechanicalPencil(what2);
        towers.add(p2);

        int[] what3 = {8, 3};
        MechanicalPencil p3 = new MechanicalPencil(what3);
        towers.add(p3);

        int[] what4 = {8, 4};
        MechanicalPencil p4 = new MechanicalPencil(what4);
        towers.add(p4);

        int[] what5 = {8, 5};
        MechanicalPencil p5 = new MechanicalPencil(what5);
        towers.add(p5);

//        int[] what = {5,1};
//        MechanicalPencil p = new MechanicalPencil(what);
//        towers.add(p);
//
//        int[] what2 = {5,2};
//        Shredder p2 = new Shredder(what2);
//        towers.add(p2);
//
//        int[] what3 = {5,3};
//        Shredder p3 = new Shredder(what3);
//        towers.add(p3);
//
//        int[] what4 = {5,4};
//        Shredder p4 = new Shredder(what4);
//        towers.add(p4);
//
//        int[] what5 = {5,5};
//        Shredder p5 = new Shredder(what5);
//        towers.add(p5);
//
//        int[] what6 = {6 , 1};
//        Eraser p6 = new Eraser(what6);
//        towers.add(p6);
//
//        int[] what7 = {4 , 1};
//        Pencil p7 = new Pencil(what7);
//        towers.add(p7);
    }

    public void checkCollisions() {
        ArrayList<Weapon> projs = Tower.getProjectiles();
        for (int i = 0; i < projs.size(); i++) {
            Weapon proj = projs.get(i);
            for (int j = 0; j < enemies.size(); j++) {
                Enemy en = enemies.get(j);
                if (Geometry.isColliding(proj.getX(), proj.getY(), 20, 50, en.getX(), en.getY(), en.getWidth(), en.getHeight())) {
                    System.out.println("COLLISION DETECTED");
                    System.out.println(en.getHitPoints());
                    en.takeHit(proj.getDamage());
                    if (!proj.getPenetrate()) {
                        proj.setDelete();
                    }
                }
            }
        }
    }

    public void behaveTowers() {
        for (int i = 0; i < towers.size(); i++) {
            Tower tow = towers.get(i);
            for (int j = 0; j < enemies.size(); j++) {
                Enemy en = enemies.get(j);
                int[] towerCoordinate = tow.getCoordinate();
                int[] enemyCoordinate = en.getCoordinate();
//                tow.incFiredCounter();
                if (enemyCoordinate != null) {
                    if (towerCoordinate[1] == enemyCoordinate[1] && (enemyCoordinate[0] - towerCoordinate[0] <= tow.getRange() && enemyCoordinate[0] >= towerCoordinate[0])) {
                        tow.attack();
                    }
                }
            }
        }
    }

    public void garbageCleanup() { // this is run every tick to check which monsters died and to increase the tower cooldown counter
//        for (int i = 0; i < enemies.size(); i++){
//            Enemy en = enemies.get(i);
//
//        }
        int i = 0;
        while (i < enemies.size()) {
            if (enemies.get(i).getHitPoints() <= 0) {
                enemies.remove(i);
            } else {
                i++;
            }
        }

        for (int j = 0; j < towers.size(); j++) {
            Tower tow = towers.get(j);
            tow.incFiredCounter();
        }

        i = 0;
        while (i < towers.size()) {
            if (towers.get(i).getHitPoints() <= 0) {
                towers.remove(i);
            } else {
                i++;
            }
        }

//        ArrayList<Weapon> projs = Tower.getProjectiles();
//        i = 0;
//        while (i<projs.size()){
//            if projs.
//        }
    }

    public static ArrayList<Tower> getTowers() {
        return towers;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
        this.waveCount = 0;
        this.enemiesSpawnedBetweenWaves = 0;
        this.enemiesSpawnedDuringWave = 0;
        this.startTime = (long) (System.nanoTime() / (Math.pow(10, 9)));
        this.enemies.clear();
        this.totalEnemies = 5 * (this.levelNumber * this.levelNumber) + 10 * this.levelNumber;
    }

    private void spawnMotivation() {
        long currentTime = (long) (System.nanoTime() / (Math.pow(10, 9)));
        if (currentTime - this.lastMotivationSpawnTime >= this.motivationSpawnCoolDown) {
            motivationCoins.add(new Motivation("air_drop"));
            this.lastMotivationSpawnTime = currentTime;
        }
    }

    private String chooseEnemy() {
        Random random = new Random();
        int num = random.nextInt(1, 101);
        if (num >= 1 && num <= 30) {
            return "paper";
        } else if (num >= 31 && num <= 40) {
            return "notebook";
        } else if (num >= 41 && num <= 45) {
            return "textbook";
        } else if (num >= 46 && num <= 90) {
            return "notepad";
        } else if (num >= 91 && num <= 96) {
            return "test";
        } else if (num >= 97 && num <= 100) {
            return "exam";
        } else {
            return "notepad";
        }
    }

    private double waveFactor() {
        if (waveCount == 1){
            return 0.17;
        } else if (waveCount == 2) {
            return 0.33;
        } else {
            return 0.5;
        }
    }

    private void behaveEnemySpawnLogic() {
        long currentTime = (long) (System.nanoTime() / (Math.pow(10, 9)));
        long timeElapsed = currentTime - startTime;
        if (this.totalEnemies > 0) {
            if (!this.wave) {
                if (timeElapsed != 0 && timeElapsed % this.betweenWavesSpawnCoolDown == 0 && this.spawn) {
                    if (this.enemiesSpawnedBetweenWaves < 2) {
                        enemies.add(new Enemy(chooseEnemy(), this.map));
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
                    enemies.add(new Enemy(chooseEnemy(), this.map));
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
    }

    public boolean levelIsWon() {
        return (this.waveCount == 3 && this.enemiesSpawnedDuringWave == this.totalEnemies * waveFactor() && this.enemies.size() == 0);
    }

    public boolean levelIsLost() {
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).getX() + enemies.get(i).getWidth() <= Map.getMapStartX()) {
                return true;
            }
        }
        return false;
    }

    private void behaveEnemies() {
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).behave();
        }
    }

    public void behave() {
        behaveEnemySpawnLogic();
        behaveEnemies();
        checkCollisions();
        garbageCleanup();
        Tower.deletionCheck();
        behaveTowers();
        Tower.moveProjectiles();
        spawnMotivation();
    }

    public void paint(Graphics2D g2d) {
        map.paint(g2d);
        for (int i = 0; i < towers.size(); i++) {
            towers.get(i).paint(g2d);
        }
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).paint(g2d);
        }
        ArrayList<Weapon> projs = Tower.getProjectiles();
        for (int i = 0; i < projs.size(); i++) {
            projs.get(i).paint(g2d);
        }
        for (int i = 0; i < motivationCoins.size(); i++) {
            motivationCoins.get(i).paint(g2d);
        }
    }

}