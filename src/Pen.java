import java.awt.*;

public class Pen extends Tower {

    private double attackDelay = 2;
    private String imagePath = "res\\towers\\pen.png";
    // private String imagePath = "../res/towers/pen.png";

    public Pen(int[] coordinate) {
        super(coordinate);
        super.hitPoints = 100;
    }

    public void attack(){
        if (lastFired >= 50 * attackDelay){
            Weapon weapon = new Weapon("pen");
            projectiles.add(weapon);
            lastFired = 0;
        }
        else{
            lastFired += 1;
        }
    }

    public void paint(Graphics2D g2d){

    }
}
