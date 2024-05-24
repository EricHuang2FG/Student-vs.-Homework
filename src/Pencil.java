public class Pencil extends Tower {
  private double attackDelay = 2;
//    private double coolDown =
//    private Weapon weapon;
  private String imagePath = "res\\towers\\pencil.png";
  public Pencil(){
      hitPoints = 100;
  }
}


//100 HP
//Shoots lead in a straight line every 2 seconds with infinite range
//Each lead breaks once it hits a zombie
//The lead does 40 HP of damage
//Costs 100 motivation
//Cooldown time is 5 seconds