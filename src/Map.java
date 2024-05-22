import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;

public class Map {

    private BufferedImage map = null;
    private int mapWidth = StudentVsHomework.getScreenWidth();
    private int mapHeight = StudentVsHomework.getScreenHeight();
    private static final int MAP_START_Y = 100, MAP_START_X = 100;
    private Grid[][] grids = new Grid[5][9];

    public Map() {
        try {
            this.map = ImageIO.read(new File("res\\map.png"));
            // this.map = ImageIO.read(new File("../res/map.png"));
        } catch (IOException e) {
            System.out.println("Error loading image: \n" + e);
        }
        int x = MAP_START_X;
        int y = MAP_START_Y;
        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids[i].length; j++) {
                if (i % 2 == 0) {
                    grids[i][j] = new Grid(x, y, j % 2 == 0);
                } else {
                    grids[i][j] = new Grid(x, y, j % 2 == 1);
                }
                x += Grid.getWidth();
            }
            x = MAP_START_X;
            y += Grid.getWidth();
        }
    }

    public static int getMapStartX() {
        return MAP_START_X;
    }

    public static int getMapStartY() {
        return MAP_START_Y;
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