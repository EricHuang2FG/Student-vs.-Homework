public class Shredder extends Tower {
    private double attackDelay = 10;
    private String imagePath = "res\\towers\\paper_shredder.png";
    public Shredder(){
        hitPoints = 200;
    }

    public void attack(){
        if (lastFired >= 50*attackDelay){
            Weapon weapon = new Weapon("shredder");
            projectiles.add(weapon);
            lastFired = 0;
        }
        else{
            lastFired += 1;
        }
    }
}
