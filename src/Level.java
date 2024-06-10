import java.awt.*;
import java.util.ArrayList;
import java.lang.*;
import java.util.Random;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.io.IOException;

public class Level {

    private int levelNumber;
    private boolean wave = false;
    private int waveCount = 0;
    private int totalEnemies = 5 * this.levelNumber + 10 + ((this.levelNumber - 1) * (this.levelNumber - 1));
    private int enemiesSpawnedBetweenWaves = 0;
    private int enemiesSpawnedDuringWave = 0;
    private long startTime = (long) (System.nanoTime() / (Math.pow(10, 9)));
    private boolean spawn = true;
    private int betweenWavesSpawnCoolDown = 20;
    private int duringWavesSpawnCoolDown = 3;
    private long lastMotivationSpawnTime = (long) (System.nanoTime() / (Math.pow(10, 9)));
    private long motivationSpawnCoolDown = 20;
    private static int motivationPoints = 50;
    private BufferedImage motivationCountBlockImage = null;
    private int motivationCountBlockX = 0, motivationCountBlockY = 0;
    private double motivationCountBlockScale = 0.2;
    private int motivationCountBlockScaledWidth, motivationCountBlockScaledHeight;
    private int motivationCountFontSize = 40;
    private int cardBlockNPoints = 5;
    private int[] cardBlockXCoords = new int[this.cardBlockNPoints], cardBlockYCoords = new int[this.cardBlockNPoints];
    private int cardBlockX = 200, cardBlockY = 0;
    private int cardBlockWidth = 700, cardBlockHeight = Map.getMapStartY() - 10;
    private Polygon cardBlock;
    private int firstCardX = cardBlockX + 10, firstCardY = cardBlockY + (cardBlockHeight / 2) - ((new Card("pencil", 10000, 10000)).getHeight() / 2);
    private int cardSpacing = 10;
    private int levelAndWaveNumberFontSize = 15;
    private boolean toggleAwaitSpawnTowerClickResponse = false;
    private boolean toggleAwaitDeleteTowerClickResponse = false;
    private Card clickedCard = null;
    private final Color LIGHT_YELLOW = new Color(255, 250, 40);
    private Map map = new Map();
    private Grid[][] grids = this.map.getGrids();
    private String[] allCards = {"water_bottle", "pencil", "pen", "eraser", "mechanical_pencil", "paper_shredder", "robotic_pencil", "super_eraser"};
    private static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private static ArrayList<Tower> towers = new ArrayList<Tower>();
    private static ArrayList<Motivation> motivations = new ArrayList<Motivation>();
    private static ArrayList<Card> cards = new ArrayList<Card>();
    private GarbageBin garbageBin = new GarbageBin(this.cardBlockX + this.cardBlockWidth + 10, this.firstCardY);

    public Level() {
        try {
            this.motivationCountBlockImage = ImageIO.read(new File("res\\motivation_count_block.png"));
        } catch (IOException e) {
            System.out.println("Error loading image: \n" + e);
        }
        this.motivationCountBlockScaledWidth = (int) (this.motivationCountBlockImage.getWidth() * this.motivationCountBlockScale);
        this.motivationCountBlockScaledHeight = (int) (this.motivationCountBlockImage.getHeight() * this.motivationCountBlockScale);
        this.cardBlockXCoords[0] = this.cardBlockX;
        this.cardBlockXCoords[1] = this.cardBlockX + this.cardBlockWidth;
        this.cardBlockXCoords[2] = this.cardBlockX + this.cardBlockWidth;
        this.cardBlockXCoords[3] = this.cardBlockX;
        this.cardBlockXCoords[4] = this.cardBlockX;
        this.cardBlockYCoords[0] = this.cardBlockY;
        this.cardBlockYCoords[1] = this.cardBlockY;
        this.cardBlockYCoords[2] = this.cardBlockY + this.cardBlockHeight;
        this.cardBlockYCoords[3] = this.cardBlockY + this.cardBlockHeight;
        this.cardBlockYCoords[4] = this.cardBlockY;
        this.cardBlock = new Polygon(this.cardBlockXCoords, this.cardBlockYCoords, this.cardBlockNPoints);
    }

    public static ArrayList<Tower> getTowers() {
        return towers;
    }

    public static int getMotivationPoints() {
        return motivationPoints;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
        this.waveCount = 0;
        this.enemiesSpawnedBetweenWaves = 0;
        this.enemiesSpawnedDuringWave = 0;
        this.wave = false;
        this.startTime = (long) (System.nanoTime() / (Math.pow(10, 9)));
        this.lastMotivationSpawnTime = (long) (System.nanoTime() / (Math.pow(10, 9))); // fix this for the water bottles
        motivationPoints = 50;
        enemies.clear();
        motivations.clear();
        cards.clear();
        Tower.clearProjectiles();
        for (int i = 0; i < towers.size(); i++) {
            if (towers.get(i).getType().equals("water_bottle")) {
                WaterBottle currentWaterBottle = (WaterBottle) towers.get(i);
                currentWaterBottle.setLastMotivationSpawnTime((long) (System.nanoTime() / (Math.pow(10, 9))));
            }
        }
        this.totalEnemies = 5 * this.levelNumber + 10;
        int x = this.firstCardX;
        for (int i = 0; i < this.levelNumber + 1; i++) {
            if (i == this.allCards.length) {
                break;
            }
            Card nextCard = new Card(this.allCards[i], x, this.firstCardY);
            cards.add(nextCard);
            x += nextCard.getWidth() + this.cardSpacing;
        }
    }

    public static void setTowers(ArrayList<Tower> savedTowersSetUp) {
        towers = savedTowersSetUp;
    }

    public void collectMotivation(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            for (int i = 0; i < motivations.size(); i++) {
                Motivation motivation = motivations.get(i);
                if (motivation.isClicked(e.getX(), e.getY())) {
                    motivationPoints += Motivation.getValue();
                    motivation.setCollectedVelocities();
                }
            }
        }
    }

    public static void spawnMotivation(String type, WaterBottle host) {
        motivations.add(new Motivation(type, host));
    }

    private void spawnTower(String type, int[] coordinate) {
        if (type.equals("eraser")) {
            Eraser tower = new Eraser(coordinate);
            towers.add(tower);
        } else if (type.equals("mechanical_pencil")) {
            MechanicalPencil tower = new MechanicalPencil(coordinate);
            towers.add(tower);
        } else if (type.equals("pen")) {
            Pen tower = new Pen(coordinate);
            towers.add(tower);
        } else if (type.equals("pencil")) {
            Pencil tower = new Pencil(coordinate);
            towers.add(tower);
        } else if (type.equals("paper_shredder")) {
            Shredder tower = new Shredder(coordinate);
            towers.add(tower);
        } else if (type.equals("water_bottle")) {
            WaterBottle tower = new WaterBottle(coordinate);
            towers.add(tower);
        } else if (type.equals("robotic_pencil")) {
            RoboticPencil tower = new RoboticPencil(coordinate);
            towers.add(tower);
        } else if (type.equals("super_eraser")) {
            SuperEraser tower = new SuperEraser(coordinate);
            towers.add(tower);
        }
    }

    private void behaveMotivationSpawnLogic() {
        long currentTime = (long) (System.nanoTime() / (Math.pow(10, 9)));
        if (currentTime - this.lastMotivationSpawnTime >= this.motivationSpawnCoolDown) {
            spawnMotivation("air_drop", null);
            this.lastMotivationSpawnTime = currentTime;
        }
    }

    private void awaitDeleteTowerClickResponse(MouseEvent e, GarbageBin garbageBin) {
        if (this.garbageBin.isClicked(e.getX(), e.getY())) {
            this.toggleAwaitDeleteTowerClickResponse = false;
            this.garbageBin.setDeleteTower(false);
        } else {
            for (int i = 0; i < this.grids.length; i++) {
                for (int j = 0; j < this.grids[i].length; j++) {
                    Grid currentGrid = this.grids[i][j];
                    if (currentGrid.isClicked(e.getX(), e.getY()) && currentGrid.getIsOccupied()) {
                        int[] gridCoordinate = currentGrid.getCoordinate();
                        int index = 0;
                        for (int k = 0; k < towers.size(); k++) {
                            Tower currentTower = towers.get(k);
                            int[] towerCoordinate = currentTower.getCoordinate();
                            if (towerCoordinate[0] == gridCoordinate[0] && towerCoordinate[1] == gridCoordinate[1]) {
                                index = k;
                                break;
                            }
                        }
                        towers.remove(index);
                        this.toggleAwaitDeleteTowerClickResponse = false;
                        this.garbageBin.setDeleteTower(false);
                    }
                }
            }
        }
    }

    private void awaitSpawnTowerClickResponse(MouseEvent e, Card clickedCard) {
        if (clickedCard.isClicked(e.getX(), e.getY())) {
            this.toggleAwaitSpawnTowerClickResponse = false;
            this.clickedCard.setSpawnTower(false);
            this.clickedCard = null;
        } else {
            for (int i = 0; i < this.grids.length; i++) {
                for (int j = 0; j < this.grids[i].length; j++) {
                    Grid currentGrid = this.grids[i][j];
                    if (currentGrid.isClicked(e.getX(), e.getY()) && !currentGrid.getIsOccupied()) {
                        spawnTower(clickedCard.getType(), currentGrid.getCoordinate());
                        motivationPoints -= clickedCard.getCost();
                        this.toggleAwaitSpawnTowerClickResponse = false;
                        this.clickedCard.startCoolDown();
                        this.clickedCard.setSpawnTower(false);
                        this.clickedCard = null;
                    }
                }
            }
        }
    }

    public void clickCard(MouseEvent e) {
        if (this.toggleAwaitSpawnTowerClickResponse) {
            awaitSpawnTowerClickResponse(e, this.clickedCard);
        } else if (this.toggleAwaitDeleteTowerClickResponse) {
            awaitDeleteTowerClickResponse(e, this.garbageBin);
        } else {
            for (int i = 0; i < cards.size(); i++) {
                Card currentCard = cards.get(i);
                if (currentCard.isClicked(e.getX(), e.getY()) && !currentCard.getCountCoolDown() && currentCard.canSpawn()) {
                    this.toggleAwaitSpawnTowerClickResponse = true;
                    this.clickedCard = currentCard;
                    this.clickedCard.setSpawnTower(true);
                    break;
                }
            }
            if (this.garbageBin.isClicked(e.getX(), e.getY())) {
                this.toggleAwaitDeleteTowerClickResponse = true;
                this.garbageBin.setDeleteTower(true);
            }
        }
    }

    private void checkOccupiedGrids() {
        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids[i].length; j++) {
                for (int k = 0; k < towers.size(); k++) {
                    Grid currentGrid = grids[i][j];
                    int[] gridCoordinate = currentGrid.getCoordinate();
                    Tower currentTower = towers.get(k);
                    int[] towerCoordinate = currentTower.getCoordinate();
                    if (gridCoordinate[0] == towerCoordinate[0] && gridCoordinate[1] == towerCoordinate[1]) {
                        currentGrid.setIsOccupied(true);
                        break;
                    } else {
                        currentGrid.setIsOccupied(false);
                    }
                }
            }
        }
    }

    private void checkCollisions() {
        ArrayList<Weapon> projs = Tower.getProjectiles();
        for (int i = 0; i < projs.size(); i++) {
            Weapon proj = projs.get(i);
            for (int j = 0; j < enemies.size(); j++) {
                Enemy en = enemies.get(j);
                if (Geometry.isColliding(proj.getX(), proj.getY(), 20, 50, en.getX(), en.getY(), en.getWidth(), en.getHeight())) {
//                    System.out.println("COLLISION DETECTED");
//                    System.out.println(en.getHitPoints());
                    en.takeHit(proj.getDamage());
                    if (!proj.getPenetrate()) {
                        proj.setDelete();
                        break;
                    }
                }
            }
        }
    }

    public void behaveTowers() {
        for (int i = 0; i < towers.size(); i++) {
            Tower tow = towers.get(i);
            tow.behave();
            for (int j = 0; j < enemies.size(); j++) {
                Enemy en = enemies.get(j);
                if (en.getType().equals("donald")) {
                    continue;
                }
                int[] towerCoordinate = tow.getCoordinate();
                int[] enemyCoordinate = en.getCoordinate();
                if (enemyCoordinate != null) {
                    if (towerCoordinate[1] == enemyCoordinate[1] && (enemyCoordinate[0] - towerCoordinate[0] <= tow.getRange() && enemyCoordinate[0] >= towerCoordinate[0])) {
                        tow.attack();
                    }
                }
            }
        }
    }

    public void garbageCleanup() { // this is run every tick to check which monsters died and to increase the tower cooldown counter
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
        i = 0;
        while (i < motivations.size()) {
            if (motivations.get(i).getCanDelete()) {
                motivations.remove(i);
            } else {
                i++;
            }
        }
    }

    private String chooseEnemy() {
//        return "donald"; // for testing
        int paperValue = (int) 50;
        int notebookValue = (int) 10;
        int textbookValue = (int) (0.5 * this.levelNumber);
        int notepadValue = (int) 20;
        int testValue = (int) (0.4 * this.levelNumber);
        int donaldValue = (int) (0.5 * this.levelNumber); // 20
        int examValue = (int) (0.1 * this.levelNumber);

        Random random = new Random();
        int num = random.nextInt(1, paperValue + notebookValue + textbookValue + notepadValue + testValue + donaldValue+examValue);
//        System.out.println(num);
        if (num >= 1 && num <= paperValue) {
            return "paper";
        } else if (num >= paperValue + 1 && num <= paperValue + notebookValue) {
            return "notebook";
        } else if (num >= paperValue + notebookValue + 1 && num <= paperValue + notebookValue+textbookValue) {
            return "textbook";
        } else if (num >= paperValue + notebookValue + textbookValue + 1 && num <= paperValue + notebookValue + textbookValue + notepadValue) {
            return "notepad";
        } else if (num >= paperValue + notebookValue + textbookValue + notepadValue + 1 && num <= paperValue + notebookValue + textbookValue + notepadValue + testValue) {
            return "test";
        } else if (num >= paperValue + notebookValue + textbookValue + notepadValue + testValue + 1 && num <= paperValue + notebookValue + textbookValue + notepadValue + testValue + donaldValue) {
            return "donald";
        } else if (num >= paperValue + notebookValue + textbookValue + notepadValue + testValue + donaldValue + 1 && num <= paperValue + notebookValue + textbookValue + notepadValue + testValue + donaldValue + examValue) {
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

    public static void spawnEnemy(String type, Map map, int[] coordinate) {
        if (type.equals("donald")) {
            enemies.add(new Donald(type, map));
        } else if (type.equals("donald_enemy")) {
            enemies.add(new Enemy(map, coordinate));
        } else {
            enemies.add(new Enemy(type, map));
        }
    }

    private void behaveEnemySpawnLogic() {
        long currentTime = (long) (System.nanoTime() / (Math.pow(10, 9)));
        long timeElapsed = currentTime - startTime;
        if (this.totalEnemies > 0) {
            if (!this.wave) {
                if (timeElapsed > this.betweenWavesSpawnCoolDown && timeElapsed % this.betweenWavesSpawnCoolDown == 0 && this.spawn) {
                    if (this.enemiesSpawnedBetweenWaves < 2) {
                        spawnEnemy(chooseEnemy(), this.map, null);
                        this.spawn = false;
                    }
                    this.enemiesSpawnedBetweenWaves++; // adds a cooldown before the wave starts
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
                if (timeElapsed % this.duringWavesSpawnCoolDown == 0 && this.spawn) {
                    spawnEnemy(chooseEnemy(), this.map, null);
                    this.spawn = false;
                    this.enemiesSpawnedDuringWave++;
                    this.totalEnemies--;
                } else if ((timeElapsed - 1) % this.duringWavesSpawnCoolDown == 0 && !this.spawn) {
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
        return (this.waveCount == 3 && this.enemiesSpawnedDuringWave >= (int) (this.totalEnemies * waveFactor()) && this.enemies.isEmpty());
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

    private void behaveMotivations() {
        for (int i = 0; i < motivations.size(); i++) {
            motivations.get(i).behave();
        }
    }

    private void behaveCards() {
        PointerInfo pointer = MouseInfo.getPointerInfo();
        Point location = pointer.getLocation();
        int x = (int) location.getX();
        int y = (int) location.getY();
        for (int i = 0; i < cards.size(); i++) {
            Card currentCard = cards.get(i);
            currentCard.behave();
            currentCard.checkMouseIsHoveredAbove(x, y);
        }
        garbageBin.checkMouseIsHoveredAbove(x, y);
    }

    public void behave() {
        behaveEnemySpawnLogic();
        behaveEnemies();
        checkCollisions();
        garbageCleanup();
        Tower.deletionCheck();
        behaveTowers();
        Tower.moveProjectiles();
        behaveMotivationSpawnLogic();
        behaveMotivations();
        checkOccupiedGrids();
        behaveCards();
//        System.out.println(this.toggleAwaitClickResponse);
    }

    private void paintTowers(Graphics2D g2d) {
        for (int i = 0; i < towers.size(); i++) {
            towers.get(i).paint(g2d);
        }
    }

    private void paintEnemies(Graphics2D g2d) {
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).paint(g2d);
        }
    }

    private void paintProjectiles(Graphics2D g2d) {
        ArrayList<Weapon> projs = Tower.getProjectiles();
        for (int i = 0; i < projs.size(); i++) {
            projs.get(i).paint(g2d);
        }
    }

    private void paintMotivations(Graphics2D g2d) {
        for (int i = 0; i < motivations.size(); i++) {
            motivations.get(i).paint(g2d);
        }
    }

    private void paintMotivationCountBlock(Graphics2D g2d) {
        g2d.drawImage(this.motivationCountBlockImage, this.motivationCountBlockX, this.motivationCountBlockY, this.motivationCountBlockScaledWidth, this.motivationCountBlockScaledHeight, null);
        g2d.setFont(new Font("Century Schoolbook", Font.PLAIN, this.motivationCountFontSize));
        g2d.drawString(motivationPoints + "", this.motivationCountBlockX + 85, this.motivationCountBlockY + (this.motivationCountBlockScaledHeight / 2) + (this.motivationCountFontSize / 4));
    }

    private void paintCardBlock(Graphics2D g2d) {
        g2d.setColor(this.LIGHT_YELLOW);
        g2d.fillPolygon(this.cardBlock);
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(this.cardBlock);
        g2d.setStroke(new BasicStroke(1));
        for (int i = 0; i < cards.size(); i++) {
            cards.get(i).paint(g2d);
        }
    }

    private void paintLevelAndWaveNumber(Graphics2D g2d) {
        g2d.setFont(new Font("Century Schoolbook", Font.BOLD, this.levelAndWaveNumberFontSize));
        g2d.drawString("Level " + this.levelNumber + ", wave " + this.waveCount, StudentVsHomework.getScreenWidth() - 170, StudentVsHomework.getScreenHeight() - 15);
    }

    public void paint(Graphics2D g2d) {
        map.paint(g2d);
        paintLevelAndWaveNumber(g2d);
        paintTowers(g2d);
        paintEnemies(g2d);
        paintProjectiles(g2d);
        paintMotivations(g2d);
        paintMotivationCountBlock(g2d);
        paintCardBlock(g2d);
        garbageBin.paint(g2d);
    }

}