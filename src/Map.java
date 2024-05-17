import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;

public class Map {

    private BufferedImage map = null;
    private int mapWidth = StudentVsHomework.getScreenWidth();
    private int mapHeight = StudentVsHomework.getScreenHeight();
    private Grid[][] grids = new Grid[5][9];
    
    public Map() {
        try {
            this.map = ImageIO.read(new File("res\\map.png"));
            // this.map = ImageIO.read(new File("..\res\map.png"));
        } catch (IOException e) {
            System.out.println("Error loading image: \n" + e);
        }
        int x = 120;
        int y = 70;
        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids[i].length; j++) {
                if (i % 2 == 0) {
                    grids[i][j] = new Grid(x, y, j % 2 == 0);
                } else {
                    grids[i][j] = new Grid(x, y, j % 2 == 1);
                }
                x += Grid.getWidth();
            }
            x = 120;
            y += Grid.getWidth();
        }
    }

    public void paint(Graphics2D g2d) {
        g2d.drawImage(this.map, 0, 0, mapWidth, mapHeight, null);
        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids[i].length; j++) {
                grids[i][j].paint(g2d);
            }
        }
    }
    
}