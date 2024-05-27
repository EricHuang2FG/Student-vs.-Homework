public class MechanicalPencil extends Tower {
    private double attackDelay = 1;
    private String imagePath = "res\\towers\\mechanic_pencil.png";
    public MechanicalPencil(){
        hitPoints = 100;
    }

    public void attack(){
        if (lastFired >= 50 * attackDelay){
            Weapon weapon = new Weapon("mechanical_pencil");
            projectiles.add(weapon);
            lastFired = 0;
        }
        else{
            lastFired += 1;
        }
    }
}
