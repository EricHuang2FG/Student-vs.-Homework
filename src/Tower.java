import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Tower {

    protected int hitPoints;
    protected int lastFired = 1000000000;
    protected static ArrayList<Weapon> projectiles = new ArrayList<Weapon>();
//    protected static ArrayList<Tower> towers = new ArrayList<Tower>();

    //        private static projectiles
    protected int cost;
    protected int x, y; //store its position as a grid

    protected int range = 10;
    protected int[] coordinate;

    protected BufferedImage image = null;

    protected int scaledWidth, scaledHeight;
    protected double scale;

    public Tower(int[] coordinate) {
        this.coordinate = coordinate;
        this.x = coordinate[0] * 100 - 100 + Map.getMapStartX();
        this.y = coordinate[1] * 100 - 100 + Map.getMapStartY();

//        scaledWidth = 100;
//        scaledHeight = 100;
//        String imagePath = "res\\towers\\"+type+".png";
//        try {
//            this.image = ImageIO.read(new File(imagePath));
//        } catch (IOException e) {
//            System.out.println("Error loading image: \n" + e);
//        }
    }

    public int[] getCoordinate() {
        return coordinate;
    }

//    public static void behaveProjectiles(){
//        int i = 0;
//        while (i < projectiles.size()){
//            if (projectiles.get(i).outOfBounds() || projectiles.get(i).getDelete()){
//                projectiles.remove(i);
//            }
//            //The code below will make it so that the tower cooldown goes away twice the speed when monsters are near
////            else{
////                projectiles.get(i).behave();
////                i += 1;
////            }
//        }
////        for (int i = 0; i < projectiles.toArray().length; i ++){
////            if (projectiles.get(i).outOfBounds()){
////                //KILL IT
////            }
////            else{
////                projectiles.get(i).behave();
////            }
////        }
//    }

    public static void moveProjectiles(){
        for (int i = 0; i<projectiles.size();i++){
            projectiles.get(i).behave();
        }
    }

    public static void deletionCheck(){
        int i = 0;
        while (i<projectiles.size()){
            Weapon p = projectiles.get(i);
            if (p.getDelete() || p.outOfBounds()){
                projectiles.remove(i);
            }
            else{
                i += 1;
            }
        }
    }


    public abstract void attack();

    public void takeHit(int damage){
        hitPoints -= damage;
    }

    public static ArrayList<Weapon> getProjectiles() {
        return projectiles;
    }

    public int getRange(){
        return range;
    }

    public void incFiredCounter(){
        lastFired += 1;
    }

    public void paint(Graphics2D g2d) {
        for (int i = 0; i < projectiles.toArray().length; i++) {
            projectiles.get(i).paint(g2d);
        }
    }
}
