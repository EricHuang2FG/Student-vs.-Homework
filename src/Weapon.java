import java.awt.*;

public class Weapon {
    private int damage;
    private boolean canPenetrate = false;
    private int x,y;
    private int vx;
    private int range = Grid.getWidth() * 9;
    private String imagepath;

    public Weapon(String type){
        if (type.equals("pencil")){
            damage = 40;
        }
        else if (type.equals("mechanical_pencil")){
            damage = 40;
        }
        else if (type.equals("pen")){
            damage = 40;
            range = Grid.getWidth() * 3;
            canPenetrate = true;
        }
        else if (type.equals("shredder")){
            damage = 1000000000;
//            canPenetrate = ;
        }
    }
    public void behave(){
        x += vx;
    }

    public boolean outOfBounds(){
        if (this.x > this.range){
            return true;
        }
        else{return false;}
    }

    public void paint(Graphics2D g2d){

    }
}
