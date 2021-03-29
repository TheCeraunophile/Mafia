public class AsleepedPlayer extends Player{

    public AsleepedPlayer(String name, Roles role) {
        super(name, role);
    }
}
class Joker extends AsleepedPlayer{

    public Joker(String name, Roles role) {
        super(name, role);
    }
}
class Villager extends AsleepedPlayer{

    public Villager(String name, Roles role) {
        super(name, role);
    }
}
class BulletProof extends AsleepedPlayer{

    boolean hasProof=true;

    public BulletProof(String name, Roles role) {
        super(name, role);
    }

    @Override
    protected void die(){
        if (this.hasProof || super.rescue) {
            this.hasProof=false;
            super.rescue=false;
            System.out.println("no player kiled ");
        }else {
            super.isLive=false;
            System.out.println("the player with name " + super.name + " killed");
        }
    }
}