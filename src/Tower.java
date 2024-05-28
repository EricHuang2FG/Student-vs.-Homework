import java.awt.*;
import java.util.ArrayList;

public abstract class Tower {

    protected int hitPoints;
    protected int lastFired = 1000000000;
    protected static ArrayList<Weapon> projectiles = new ArrayList<Weapon>();
//    protected static ArrayList<Tower> towers = new ArrayList<Tower>();

    //        private static projectiles
    protected int cost;
    protected int x, y; //store its position as a grid
    protected int[] coordinate;

    public int[] getCoordinate() {
        return coordinate;
    }
    public void takeDamage(int amount){
        hitPoints -= amount;
    }

    public void behaveProjectiles(){
        int i = 0;
        while (i < projectiles.size()){
            if (projectiles.get(i).outOfBounds() || projectiles.get(i).getDelete()){
                projectiles.remove(i);
            }
            else{
                projectiles.get(i).behave();
                i += 1;
            }
        }
//        for (int i = 0; i < projectiles.toArray().length; i ++){
//            if (projectiles.get(i).outOfBounds()){
//                //KILL IT
//            }
//            else{
//                projectiles.get(i).behave();
//            }
//        }
    }

    public boolean isColliding(int x) {
        if (this.x >= x) {
            return true;
        } else {
            return false;
        }
    }

    public void takeHit(int damage){
        hitPoints -= damage;
    }
    public static ArrayList<Weapon> getProjectiles(){
        return projectiles;
    }

    public void paint(Graphics2D g2d){
        for (int i = 0; i < projectiles.toArray().length; i ++){
            projectiles.get(i).paint(g2d);
        }
    }
}
