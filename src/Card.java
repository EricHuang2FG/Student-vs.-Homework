import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class Card {

    protected int x, y;
    protected int infoCardX, infoCardY;
    protected double scale = 0.2;
    protected int scaledWidth, scaledHeight;
    protected double infoCardScale = 0.4;
    protected int infoCardScaledWidth, infoCardScaledHeight;
    protected String type;
    protected boolean showTowerInfo = false;
    protected boolean spawnTower = false;
    protected boolean countCoolDown = false;
    protected long coolDownStartTime;
    protected long coolDownTimer;
    protected int coolDownRectangleHeight;
    protected BufferedImage image1 = null, image2 = null, infoCardImage = null;
    protected String cost;
    protected int spawnCoolDown;
    protected final int FONT_SIZE = 13;

    public Card(String type, int x, int y) {
        String image1Path = "res\\cards\\" + type + "_card.png";
        String image2Path = "res\\cards\\clicked_card.png";
        String image3path = "res\\tower_info_cards\\" + type + "_info.png";
        this.x = x;
        this.y = y;
        if (!type.equals("garbage_bin")) {
            try {
                this.image1 = ImageIO.read(new File(image1Path));
                this.image2 = ImageIO.read(new File(image2Path));
                this.infoCardImage = ImageIO.read(new File(image3path));
            } catch (IOException e) {
                System.out.println("Error loading image: \n" + e);
            }
            this.scaledWidth = (int) (this.image1.getWidth() * this.scale);
            this.scaledHeight = (int) (this.image1.getHeight() * this.scale);
            this.infoCardScaledWidth = (int) (this.infoCardImage.getWidth() * this.infoCardScale);
            this.infoCardScaledHeight = (int) (this.infoCardImage.getHeight() *  this.infoCardScale);
            this.infoCardX = this.x;
            this.infoCardY = this.y + (this.scaledHeight / 2);
        }
        if (type.equals("pencil")) {
            this.cost = Pencil.getCost();
            this.spawnCoolDown = Pencil.getSpawnCoolDown();
        } else if (type.equals("eraser")) {
            this.cost = Eraser.getCost();
            this.spawnCoolDown = Eraser.getSpawnCoolDown();
        } else if (type.equals("mechanical_pencil")) {
            this.cost = MechanicalPencil.getCost();
            this.spawnCoolDown = MechanicalPencil.getSpawnCoolDown();
        } else if (type.equals("pen")) {
            this.cost = Pen.getCost();
            this.spawnCoolDown = Pen.getSpawnCoolDown();
        } else if (type.equals("paper_shredder")) {
            this.cost = Shredder.getCost();
            this.spawnCoolDown = Shredder.getSpawnCoolDown();
        } else if (type.equals("water_bottle")) {
            this.cost = WaterBottle.getCost();
            this.spawnCoolDown = WaterBottle.getSpawnCoolDown();
        } else if (type.equals("robotic_pencil")) {
            this.cost = RoboticPencil.getCost();
            this.spawnCoolDown = RoboticPencil.getSpawnCoolDown();
        } else if (type.equals("super_eraser")) {
            this.cost = SuperEraser.getCost();
            this.spawnCoolDown = SuperEraser.getSpawnCoolDown();
        }
        this.type = type;
    }

    public boolean getCountCoolDown() {
        return this.countCoolDown;
    }

    public String getType() {
        return this.type;
    }

    public int getWidth() {
        return this.scaledWidth;
    }

    public int getHeight() {
        return this.scaledHeight;
    }

    public int getCost() {
        return Integer.parseInt(this.cost);
    }

    public void setSpawnTower(boolean value) {
        this.spawnTower = value;
    }

    public boolean canSpawn() {
        return (Level.getMotivationPoints() >= Integer.parseInt(this.cost));
    }

    public boolean isClicked(int x, int y) {
        return ((x > this.x && x < this.x + this.scaledWidth) && (y > this.y && y < this.y + this.scaledHeight));
    }

    public void checkMouseIsHoveredAbove(int x, int y) {
        this.showTowerInfo = ((x > this.x && x < this.x + this.scaledWidth) && (y > this.y && y < this.y + this.scaledHeight));
    }

    public void startCoolDown() {
        this.countCoolDown = true;
        this.coolDownStartTime = (long) (System.nanoTime() / (Math.pow(10, 9)));
    }

    public void behave() {
        if (this.countCoolDown) {
            long currentTime = (long) (System.nanoTime() / (Math.pow(10, 9)));
            this.coolDownTimer = currentTime - this.coolDownStartTime;
            this.coolDownRectangleHeight = (int) ((1.0 - ((this.coolDownTimer * 1.0) / (this.spawnCoolDown * 1.0))) * this.scaledHeight);
            if (this.coolDownTimer > this.spawnCoolDown) {
                this.countCoolDown = false;
            }
        }
    }

    public void paintTowerInfo(Graphics2D g2d) {
        if (this.showTowerInfo) {
            g2d.drawImage(this.infoCardImage, this.infoCardX, this.infoCardY, this.infoCardScaledWidth, this.infoCardScaledHeight, null);
        }
    }

    public void paint(Graphics2D g2d) {
        if (!this.spawnTower) {
            g2d.drawImage(this.image1, this.x, this.y, this.scaledWidth, this.scaledHeight, null);
        } else {
            g2d.drawImage(this.image2, this.x, this.y, this.scaledWidth, this.scaledHeight, null);
        }
        g2d.setFont(new Font("Century Schoolbook", Font.PLAIN, this.FONT_SIZE));
        if (!canSpawn()) {
            g2d.setColor(Color.RED);
        }
        g2d.drawString(this.cost, this.x + 30, this.y + this.scaledHeight - 7);
        g2d.setColor(Color.BLACK);
        if (this.countCoolDown) {
            g2d.setColor(Color.GRAY);
            g2d.fillRect(this.x, this.y, this.scaledWidth, this.coolDownRectangleHeight);
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(1));
            g2d.drawRect(this.x, this.y, this.scaledWidth, this.coolDownRectangleHeight);
        }
    }

}