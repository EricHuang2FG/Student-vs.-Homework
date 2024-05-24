import java.awt.*;
import java.util.ArrayList;

public abstract class Tower {

    protected int hitPoints;
    protected static ArrayList<Weapon> projectiles = new ArrayList<Weapon>();
//    private static ArrayList<Tower> towers = new ArrayList<Tower>();

    //    private static projectiles
    protected int cost;
    protected int x, y; //store it's position as a grid

    public void takeDamage(int amount){
        hitPoints -= amount;
    }

    public void behaveProjectiles(){
        for (int i = 0; i < projectiles.toArray().length; i ++){
            projectiles.get(i).behave();
        }
    }

    public void paint(Graphics2D g2d){
        for (int i = 0; i < projectiles.toArray().length; i ++){
            projectiles.get(i).behave();
        }
    }
}
