import java.util.Scanner;
public class God {
    private int DayOrNight=1;
    private boolean isGameCreated=false;
    private boolean rolesPushed=false;
    private boolean gameStarted=false;
    private final String[] roles = {"Joker", "villager", "detective", "doctor", "bulletproof", "mafia", "godfather", "silencer"};
    String[] nameOfPlayers;
    private Player[] players ;         /// enhance of being final
    static Scanner scanner = new Scanner(System.in);

    public void creatingGame(){
        isGameCreated=true;
        int counter=0;
        String names=scanner.nextLine();
        // TODO: 24/03/2021 sending the names in it's String in to array nameOfPlayers
        while (counter<8){
            String name = scanner.next();
            int remaining = 8-counter;
            switch (name){
                // TODO: 24/03/2021 should be writed with REGEX
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
        for (String nameOfPlayer : this.nameOfPlayers) {
            if (nameOfPlayer.equals(name)) {
                return true;
            }
        }
        System.out.println("The name ' " + name + " ' Not found");
        return false;
    }
    private boolean isTheRole(String role){
        for (String s : this.roles) {
            if (s.equals(role)) {
                return true;
            }
        }
        System.out.println("The Role ' " + role + " ' Not found");
        return false;
    }

    private Player findingThePlayer(String name){
        for (Player player : this.players) {
            if (player.name.equals(name)) {
                return player;
            }
        }
        return null;
    }

    private void pullingTheRoleAndName(String name,String role,int counter){
        // TODO: 23/03/2021 should be writed whit REGEX 
        switch (role){
            case "joker":players[counter]=new Joker(name,role);break;
            case "bulletproof":players[counter]=new BulletProof(name,role);break;
            case "villager":players[counter]=new Villager(name,role);break;
            case "mafia":players[counter]=new Mafia(name,role);break;
            case "godfather":players[counter]=new GodFather(name,role);break;
            case "doctoe":players[counter]=new Doctor(name,role);break;
            case "detector":players[counter]=new Detector(name,role);break;
            case "silencer":players[counter]=new Silencer(name,role);break;
        }
    }

    public void assignRole(){
        if (!isGameCreated || gameStarted){
            if (gameStarted) {
                System.out.println("game already started and all of the players has role");
            }else {
                System.out.println("you should first create a game then assign role for players");
            }
        }else {
            int counter=0;
            while (counter<8){
                String line = scanner.nextLine();
                int firstSpace=line.indexOf(" ");
                try {
                    String name = line.substring(0, firstSpace);
                    String role = line.substring(++firstSpace);
                    boolean nameStatus = false, roleStatus = false;
                    if (isTheName(name)) {
                        nameStatus = true;
                    }
                    if (isTheRole(role)) {
                        roleStatus = true;
                    }
                    if (roleStatus && nameStatus) {
                        pullingTheRoleAndName(name, role, counter);
                        counter++;
                        if (counter==8){
                            rolesPushed=true;
                        }
                    }
                }catch (Exception exception){
                    switch (line){
                        case "start_game": System.out.println((8 - counter) + " player whitout a role you should first detect their role");break;
                        //todo we can pull more intonation and write whith REGEX
                    }
                }
            }
        }
    }
    
    public void day(){
        if (isGameCreated && rolesPushed){
            gameStarted=true;
            for (Player player : players) {
                if (!player.isSilent && player.isLive){
                    System.out.print(player);
                }
            }
            System.out.println("Day " + DayOrNight);
            System.out.println("waited for vote");
            String vote=scanner.nextLine();
            while (!vote.equals("end_vote")){
                int firstSpace=vote.indexOf(" ");
                try {
                    String voterName =vote.substring(0,firstSpace);
                    String voted = vote.substring(++firstSpace);
                    if (isTheName(voterName)){
                        if (!findingThePlayer(voterName).isSilent && findingThePlayer(voterName).isLive && findingThePlayer(voted).isLive){
                            findingThePlayer(voted).conjectureMafiVote++;
                        }else {
                            // TODO: 24/03/2021 switch
                        }
                }
                }catch (Exception e){
                    // TODO: 24/03/2021 Switch
                }
                vote=scanner.nextLine();
            }
            Player temp = players[0];
            for (Player player:players){
                if (temp.conjectureMafiVote<player.conjectureMafiVote){
                    temp=player;
                }
            }
            boolean shouldKill=true;
            int numberOFVoted= temp.conjectureMafiVote;
            for (Player player : players) {
                if (!player.equals(temp)) {
                    if (player.conjectureMafiVote == numberOFVoted) {
                        shouldKill = false;
                    }
                }
            }
            if (shouldKill){
                temp.isLive=false;
                System.out.println("The Player " + temp.name + " kiled");
            }

            middleOFNightAndDay("night");
        }else {
            if (!isGameCreated){
                System.out.println("no created Game yet");
            }else {
                System.out.println("one or more players has not a role yet");
            }
        }
    }
    
    private void middleOFNightAndDay(String DayOrNight){
        int numberOfMafis=0;
        for (Player player : players) {
            if (player instanceof Joker) {
                if (!player.isLive) {
                    System.out.println(" joker whit name " + player.name + " wone the Game");
                    System.exit(0);
                }
            }
            if (player instanceof Mafia || player instanceof GodFather) {
                numberOfMafis++;
            }
        }
        if (numberOfMafis==0){
            System.out.println("The vilagers wone the Game");
            System.exit(0);
        }
        int numberOfLivedPlayers=0;
        for (Player player : players){
            if (player.isLive && !(player instanceof GodFather) && !(player instanceof Mafia)){
                numberOfLivedPlayers++;
            }
        }
        if (numberOfLivedPlayers==numberOfMafis){
            System.out.println("The mafias wone the Game");
            System.exit(0);
        }
        switch (DayOrNight) {
            case "day" -> day();
            case "night" -> night();
        }
    }
    
    private void night(){
        for (Player player : players) {
            if (player instanceof GettedUpPlayer && player.isLive) {
                System.out.println(player);
            }
        }


        // TODO: 22/03/2021  for nigth
        Player haveToKil;      //doctor & mafia
        Player checkOut;       //detective & mafia & godfather
                               //silencer





        ///
        DayOrNight++;
        middleOFNightAndDay("day");
    }

    public void getGameStatus(){
        int numberOfLivedPlayers=0;
        for (Player player : players){
            if (player.isLive && !(player instanceof GodFather) && !(player instanceof Mafia)){
                numberOfLivedPlayers++;
            }
        }
        int numberOfMafia=0;
        for (Player player : players){
            if (player instanceof Mafia || player instanceof GodFather){
                numberOfMafia++;
            }
        }
        System.out.println("we have now " + numberOfMafia + " mafia & " + numberOfLivedPlayers + " lived vilager");
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