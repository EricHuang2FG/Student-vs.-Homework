import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;

public class Weapon {
    private int damage;
    private boolean canPenetrate = false;
    private int x = 50, y = 100;
    private int vx = 5;
    private int range = 10;
    private BufferedImage image = null;
    private String imagePath;

    private int starting;

//    private String imagePath = "res\\towers\\pencil.png";

    private boolean willDelete = false;

    private int scaledWidth,scaledHeight;

    public boolean getPenetrate(){
        return canPenetrate;
    }
    public Weapon(String type, int x, int y, Tower tower) {
        // String imagePath = "../res/weapons/" + type + ".png";

        if (type.equals("pencil")){
            this.x = x + 65;
            this.y = y + 2;
            damage = 30;
            range = Grid.getWidth() * tower.getRange();
            starting = this.x;
            imagePath = "res\\projectiles\\pencil_projectile.png"; // pixel range
        } else if (type.equals("mechanical_pencil")) {
            this.x = x + 65;
            this.y = y + 2;
            damage = 30;
            range = Grid.getWidth() * tower.getRange();
            starting = this.x;
            imagePath = "res\\projectiles\\pencil_projectile.png"; // pixel range
        } else if (type.equals("pen")) {
            this.x = x + 65;
            this.y = y + 2;
            damage = 20;
            range = Grid.getWidth() * tower.getRange(); // pixel range
            starting = this.x;
            canPenetrate = true;
            imagePath = "res\\projectiles\\ink.png";
        } else if (type.equals("shredder")) {
            this.x = x + 60;
            this.y = y + 15;
            damage = 1000000000;
            range = Grid.getWidth() * tower.getRange();
            starting = this.x;
        }

//        imagePath = "res\\projectiles\\penil_projectile.png";
        scaledWidth = 50;
        scaledHeight = 50;

        try {
            this.image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.out.println("Error loading PROJECTILE image: \n" + e);
        }
    }

    public void behave() {
        x += vx;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDamage() {
        return damage;
    }

    public boolean getDelete() {
        return willDelete;
    }

    public void setDelete(){
        willDelete = true;
    }

    public boolean outOfBounds() {
        if (this.x > this.range + starting) {
            return true;
        } else {
            return false;
        }
    }

    public void paint(Graphics2D g2d){
//        System.out.println("THE PROJECTILE IS TRYING TO BE DRAWN");
        g2d.drawImage(this.image, x, y, this.scaledWidth, this.scaledHeight, null);
    }
}
