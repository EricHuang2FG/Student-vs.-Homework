import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Eraser extends Tower {

    private static int lastPlaced = 10000000;
//    private double attackDelay = 2;
    private String imagePath = "res\\towers\\eraser.png";
    private String type = "eraser";
    public Eraser(int[] coordinate){
        super(coordinate);
        hitPoints = 1000;

        scaledWidth = 100;
        scaledHeight = 100;
        String imagePath = "res\\towers\\"+type+".png";
        try {
            this.image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.out.println("Error loading image: \n" + e);
        }
    }

    public void attack(){
//        if (lastFired >= 50*attackDelay){
//            Weapon weapon = new Weapon("pencil");
//            projectiles.add(weapon);
//            lastFired = 0;
//        }
//        else{
//            lastFired += 1;
//        }
    }

    public void paint(Graphics2D g2d){
        g2d.drawImage(this.image, x, y, this.scaledWidth, this.scaledHeight, null);
    }
}
