import java.util.Random;
public class MustBeSleep extends Player{

    public MustBeSleep(String name, Roles role) {
        super(name, role);
    }
}
class Joker extends MustBeSleep{

    public Joker(String name, Roles role) {
        super(name, role);
    }
}
class Villager extends MustBeSleep{

    public Villager(String name, Roles role) {
        super(name, role);
    }
}
class BulletProof extends MustBeSleep {

    boolean hasProof = true;

    public BulletProof(String name, Roles role) {
        super(name, role);
    }

    @Override
    protected void die() {
        if (this.hasProof || super.rescue) {
            this.hasProof = false;
            super.rescue = false;
            System.out.println("No players were killed");
        } else {
            super.isLive = false;
            System.out.println("Player with name " + super.name + " Killed");
        }
    }
}
class Informer extends MustBeSleep{

    Player[] player = new Player[20];

    public Informer(String name , Roles roles){
        super(name,roles);
    }

    protected void informerTask(Player... player){
        this.player=player;
        Random random = new Random();
        int floor = 3;
        int action = random.nextInt(floor);
        switch (action){
            case 0 -> firstWordOfMafia();
            case 1 -> firstWordOfJokerName();
            case 2 -> WVBK();
            case 3 ->  numberOfLivedMafia();
        }
    }

    private void WVBK(){
        for (Player player : this.player){
            if (player!=null){
                if (player.isLive && player.conjectureMafiVote!=0){
                    System.out.println(player.name+" was voted to be killed");
                    break;
                }
            }
        }
    }

    private void firstWordOfMafia(){
        for (Player player:this.player){
            if (player!=null){
                if (player.isLive && player instanceof MafiaGroupe){
                    System.out.println("There is a mafia who’s name starts with "+player.name.charAt(0));
                    break;
                }
            }
        }
    }

    private void firstWordOfJokerName(){
        for (Player player: this.player){
            if (player!=null){
                if (player instanceof Joker ){
                    System.out.println("There is a joker who’s name starts with " + player.name.charAt(0));
                    break;
                }
            }
        }
    }

    private void numberOfLivedMafia(){
        int counter=0;
        for (Player player : this.player){
            if (player!=null){
                if (player.isLive && player instanceof MafiaGroupe){
                    counter++;
                }
            }
        }
        System.out.println("Number of alive mafia : " + counter);
    }
}