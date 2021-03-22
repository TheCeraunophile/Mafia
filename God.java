import java.util.Scanner;
public class God {
    private int DayOrNight=1;
    boolean isGameCreated=false;
    String[] roles = {"Joker", "villager", "detective", "doctor", "bulletproof", "mafia", "godfather", "silencer"};
    String[] nameOfPlayers;
    Player[] players = new Player[8];
    static Scanner scanner = new Scanner(System.in);

    public void creatingGame(){
        isGameCreated=true;
        int counter=0;
        while (counter<8){
            String name = scanner.next();
            int remaining = 8-counter;
            switch (name){
                case "create_game":
                    System.out.println("the game already created");break;
                case "assign_role":
                    System.out.println("you must complet another " + remaining + " Player");
                case "start_game":
                    System.out.println("you not fill minimom of players");break;
                case "end_vote":
                    System.out.println("you not fill minimom of players");break;
                case "end night":
                    System.out.println("you not fill minimom of players");break;
                case "get_game_state":
                    System.out.println("you not fill minimom of players");break;
                default:nameOfPlayers[counter]=name;counter++;break;
            }
        }
    }

    private boolean isTheName(String name){
        for (int i=0;i<this.nameOfPlayers.length;i++){
            if (nameOfPlayers[i].equals(name)){
                return true;
            }
        }
        System.out.println("The name ' " + name + " ' Not found");
        return false;
    }
    private boolean isTheRole(String role){
        for (int i=0;i<this.roles.length;i++){
            if (this.roles[i].equals(role)){
                return true;
            }
        }
        System.out.println("The Role ' " + role + " ' Not found");
        return false;
    }

    private void pullingTheRoleAndName(String name,String role,int counter){
        // TODO: 22/03/2021  
    }

    public void assignRole(){
        if (!isGameCreated){
            System.out.println("you should first create a game then assign role for players");
        }else {
            int counter=0;
            while (counter<8){
                String line = scanner.nextLine();
                int firstSpace=line.indexOf(" ");
                String name=line.substring(0,firstSpace);
                String role=line.substring(++firstSpace);
                boolean nameStatus=false,roleStatus=false;
                if (isTheName(name)){
                    nameStatus=true;
                }
                if (isTheRole(role)){
                    roleStatus=true;
                }
                if (roleStatus && nameStatus) {
                    players[counter] = new Player(name, role);   // TODO: 22/03/2021  polimorphic for this line whit Method or Not
                    counter++;
                }
            }
        }
    }
    
    public void day(){
        for (Player player : players) {
            player.toString();
        }

        // TODO: 22/03/2021  
        
        middleOFNightAndDay("night");
    }
    
    private void middleOFNightAndDay(String DayOrNight){
        // TODO: 22/03/2021  
    }
    
    private void night(){
        for (Player player : players) {
            if (player instanceof GettedUpPlayer) {
                player.toString();
            }
        }

        // TODO: 22/03/2021  
        
        DayOrNight++;
        middleOFNightAndDay("day");
    }
    
    public static void main(String[] args) {
        while (scanner.hasNext()) {
            String act = scanner.next();
            switch (act) {
                case "create_game":
                case "assign_role":
                case "start_game":
//                case "end_vote":
                
                case "get_game_state":
            }
        }
    }
}