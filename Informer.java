import java.util.Random;
public class Informer extends Player{
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
            case 1 -> numberOfLivedMafia();
            case 2 -> firstWordOfJokerName();
            case 3 -> System.out.println(":/");
        }
    }

    private void firstWordOfMafia(){
        for (Player player:this.player){
            if (player!=null){
                if (player.isLive && player instanceof MafiaGroupe){
                    System.out.println("first word of ones mafia's name is "+player.name.charAt(0));
                    break;
                }
            }
        }
    }

    private void firstWordOfJokerName(){
        for (Player player: this.player){
            if (player!=null){
                if (player instanceof Joker ){
                    System.out.println("first word of joker name is " + player.name.charAt(0));
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
        System.out.println("number of lived mafia is " + counter);
    }
}