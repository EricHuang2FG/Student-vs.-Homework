import java.awt.*;

public class Pencil extends Tower {

    private static int lastPlaced = 10000000;
    private double attackDelay = 2;
//    private static projectiles
//    private double coolDown =
//    private Weapon weapon;
    private String imagePath = "res\\towers\\pencil.png";
    // private String imagePath = "../res/towers/pencil.png";

    public Pencil(int[] coordinate) {
        super(coordinate);
        super.hitPoints = 100;
    }

    public void attack(){
        if (lastFired >= 50 * attackDelay){
            Weapon weapon = new Weapon("pencil");
            projectiles.add(weapon);
            lastFired = 0;
        } else {
            lastFired += 1;
        }
    }
    public void paint(Graphics2D g2d){

    }
}


//100 HP
//Shoots lead in a straight line every 2 seconds with infinite range
//Each lead breaks once it hits a zombie
//The lead does 40 HP of damage
//Costs 100 motivation
//Cooldown time is 5 seconds