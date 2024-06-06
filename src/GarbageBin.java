import java.awt.*;
import javax.imageio.*;
import java.io.*;

public class GarbageBin extends Card {

    private boolean deleteTower = false;

    public GarbageBin(int x, int y) {
        super("garbage_bin", x, y);
        super.type = "garbage_bin";
        String image1Path = "res\\cards\\garbage_bin.png";
        String image2Path = "res\\cards\\clicked_garbage_bin.png";
        super.scale = 0.3;
        try {
            super.image1 = ImageIO.read(new File(image1Path));
            super.image2 = ImageIO.read(new File(image2Path));
        } catch (IOException e) {
            System.out.println("Error loading image: \n" + e);
        }
        super.scaledWidth = (int) (super.image1.getWidth() * super.scale);
        super.scaledHeight = (int) (super.image1.getHeight() * super.scale);
    }

    public void setDeleteTower(boolean value) {
        this.deleteTower = value;
    }

    public void paint(Graphics2D g2d) {
        if (!this.deleteTower) {
            g2d.drawImage(super.image1, super.x, super.y, super.scaledWidth, super.scaledHeight, null);
        } else {
            g2d.drawImage(super.image2, super.x, super.y, super.scaledWidth, super.scaledHeight, null);
        }
    }

}