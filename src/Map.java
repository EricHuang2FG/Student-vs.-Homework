import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;

public class Map {

    private BufferedImage map = null;
    private int mapWidth = StudentVsHomework.getScreenWidth();
    private int mapHeight = StudentVsHomework.getScreenHeight();
    
    public Map() {
        try {
            this.map = ImageIO.read(new File("res\\map.png"));
            // this.map = ImageIO.read(new File("..\res\map.png"));
        } catch (IOException e) {
            System.out.println("Error loading image: \n" + e);
        }
    }

    public void paint(Graphics2D g2d) {
        g2d.drawImage(this.map, 0, 0, mapWidth, mapHeight, null);
    }
    
}