import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class Card {

    private int x, y;
    private double scale = 0.2;
    private int scaledWidth, scaledHeight;
    private BufferedImage image1 = null, image2 = null;
    private String cost;
    private int spawnCoolDown;
    private final int FONT_SIZE = 13;

    public Card(String type, int x, int y) {
        String image1Path = "res\\cards\\" + type + "_card.png";
        String image2Path = "res\\cards\\clicked_card.png";
        this.x = x;
        this.y = y;
        try {
            this.image1 = ImageIO.read(new File(image1Path));
            this.image2 = ImageIO.read(new File(image2Path));
        } catch (IOException e) {
            System.out.println("Error loading image: \n" + e);
        }
        this.scaledWidth = (int) (this.image1.getWidth() * this.scale);
        this.scaledHeight = (int) (this.image1.getHeight() * this.scale);
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
        }
    }

    public int getWidth() {
        return this.scaledWidth;
    }

    public int getHeight() {
        return this.scaledHeight;
    }

    public void paint(Graphics2D g2d) {
        g2d.drawImage(this.image1, this.x, this.y, this.scaledWidth, this.scaledHeight, null);
        g2d.setFont(new Font("Century Schoolbook", Font.PLAIN, this.FONT_SIZE));
        g2d.drawString(this.cost, this.x + 30, this.y + this.scaledHeight - 7);
    }

}