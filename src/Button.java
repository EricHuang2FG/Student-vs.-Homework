import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class Button {

    private int x, y;
    private double scale = 0.3;
    private int scaledWidth, scaledHeight;
    private BufferedImage image = null;

    public Button(int x, int y, String imagePath, boolean center) {
        this.y = y;
        try {
            this.image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.out.println("Error loading image: \n" + e);
        }
        this.scaledWidth = (int) (this.image.getWidth() * this.scale);
        this.scaledHeight = (int) (this.image.getHeight() * this.scale);
        if (center) {
            this.x = (StudentVsHomework.getScreenWidth() / 2) - (this.scaledWidth / 2);
        } else {
            this.x = x;
        }
    }

    public boolean isClicked(int x, int y) {
        return ((x > this.x && x < this.x + this.scaledWidth) && (y > this.y && y < this.y + this.scaledHeight));
    }

    public void paint(Graphics2D g2d) {
        g2d.drawImage(this.image, this.x, this.y, this.scaledWidth, this.scaledHeight, null);
    }

}