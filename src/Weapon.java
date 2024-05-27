import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;

public class Weapon {
    private int damage;
    private boolean canPenetrate = false;
    private int x, y;
    private int vx;
    private int range = Grid.getWidth() * 9;
    private BufferedImage image = null;
    private String imagepath;

    public Weapon(String type) {
        String imagePath = "res\\weapons\\" + type + ".png";
        // String imagePath = "../res/weapons/" + type + ".png";
        if (type.equals("pencil")){
            damage = 40;
        } else if (type.equals("mechanical_pencil")) {
            damage = 40;
        } else if (type.equals("pen")) {
            damage = 40;
            range = Grid.getWidth() * 3;
            canPenetrate = true;
        } else if (type.equals("shredder")) {
            damage = 1000000000;
            range = Grid.getWidth() * 1;
//            canPenetrate = ;
        }
        try {
            this.image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.out.println("Error loading image: \n" + e);
        }
    }

    public void behave() {
        x += vx;
    }

    public boolean outOfBounds() {
        if (this.x > this.range){
            return true;
        }
        else{return false;}
    }

    public boolean isColliding(int x) {
        if (this.x >= x) {
            return true;
        } else {
            return false;
        }
    }

    public void paint(Graphics2D g2d){

    }
}
