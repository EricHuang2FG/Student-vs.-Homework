import java.awt.*;

public abstract class Tower {
    protected int hitPoints;
    protected int cost;
    protected int x, y; //store it's position as a grid

    public void takeDamage(int amount){
        hitPoints -= amount;
    }

    public void paint(Graphics2D g2d){

    }
}
