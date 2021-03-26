import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;
public class God {
    private static final God god = new God();
    private static int numberOfDayOrNight =1;
    private boolean isGameCreated=false;
    private boolean rolesPushed=false;
    private boolean gameStarted=false;
    private final String[] nameOfPlayers = new String[15];
    private int lengthOfPlayersNames=0;
    private final Player[] players = new Player[15];         /// enhance of being final
    static Scanner scanner = new Scanner(System.in);

    private God(){}

    public static God getGod(){
        return god;
    }

    public void creatingGame(){
        System.out.println("inpute name of players");
        isGameCreated=true;
        String names=scanner.nextLine();
        Pattern pattern1 = Pattern.compile("[a-z,A-Z]{1,}");
        Matcher matcher1 = pattern1.matcher(names);
        while (matcher1.find()){
            nameOfPlayers[lengthOfPlayersNames]=names.substring(matcher1.start(), matcher1.end());
            lengthOfPlayersNames++;
        }
        if (lengthOfPlayersNames<8){
            System.out.println("you must atlaste input 8 player you should input "
                    + (8 - lengthOfPlayersNames) + " player more");
            System.out.println("waiting to input remained player");
            creatingGame();
        }else {
            System.out.println("player's names added succesfuly");
        }
    }

    private boolean isTheName(String name){
        for (String nameOfPlayer : nameOfPlayers){
            if (nameOfPlayer!=null)
            if (nameOfPlayer.equals(name)){
                return true;
            }

        }
        System.out.println("The name ' " + name + " ' Not found");
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

    private void pullingTheRoleAndName(String name,String nameOfRole,int counter){
        Roles[] arrayOfRoles=Roles.values();
        for (Roles role:arrayOfRoles) {
            if (role.toString().equals(nameOfRole)) {
                switch (role) {
                    case joker -> players[counter] = new Joker(name, role);
                    case bulletproof -> players[counter] = new BulletProof(name, role);
                    case villager -> players[counter] = new Villager(name, role);
                    case mafia -> players[counter] = new Mafia(name, role);
                    case godfather -> players[counter] = new GodFather(name, role);
                    case doctor -> players[counter] = new Doctor(name, role);
                    case detective -> players[counter] = new Detective(name, role);
                    case silencer -> players[counter] = new Silencer(name, role);
                }
                System.out.println("the player whith name "+name+" and role of "+nameOfRole+" is add succesfuly");
                break;
            }
        }
        if (players[counter]==null)
            System.out.println("The Role ' " + nameOfRole + " ' Not found");
    }

    public void assignRole(){
        if (!isGameCreated || gameStarted){
            if (gameStarted) {
                System.out.println("game already started and all of the players has role");
            }else {
                System.out.println("you should first create a game then assign role for players");
            }
        }else {
            System.out.println("wayting to assign");
            int counter=0;
            while (counter< lengthOfPlayersNames){
                String line = scanner.nextLine();
                int firstSpace=line.indexOf(" ");
                try {
                    String name = line.substring(0, firstSpace);
                    String role = line.substring(++firstSpace);
                    if (isTheName(name)) {
                        pullingTheRoleAndName(name, role, counter);
                        if (players[counter]!=null) {
                            counter++;
                        }
                        if (counter == lengthOfPlayersNames-1){
                            rolesPushed=true;
                        }
                    }
                }catch (Exception exception){
                    switch (line){
                        case "start_game": System.out.println((lengthOfPlayersNames - counter) +
                                " player whitout a role you should first detect their role");break;
                        case "create_game": System.out.println("you already create your game");break;
                    }
                }
            }
        }
    }

    public void day(){
        if (isGameCreated && rolesPushed){
            gameStarted=true;
            for (Player player : players) {
                if (player!=null)
                if (!player.isSilent && player.isLive){
                    System.out.println(player);
                }
            }
            System.out.println("Day " + numberOfDayOrNight);
            System.out.println("waited for vote");
            String vote=scanner.nextLine();
            while (!vote.equals("end_vote")){
                int firstSpace=vote.indexOf(" ");
                try {
                    String voterName =vote.substring(0,firstSpace);
                    String voted = vote.substring(++firstSpace);
                    if (isTheName(voterName) && isTheName(voted)){
                        if (!findingThePlayer(voterName).isSilent && findingThePlayer(voterName).isLive && findingThePlayer(voted).isLive){
                            findingThePlayer(voted).conjectureMafiVote++;
                        }else {
                            if (findingThePlayer(voterName).isSilent)
                                System.out.println("voter is silencer");
                            if (!findingThePlayer(voterName).isLive)
                                System.out.println("The voter already dead");
                            if (!findingThePlayer(voted).isLive)
                                System.out.println("votee already dead");
                        }
                    }
                }catch (Exception e) {
                    switch (vote) {
                        case "create_game":
                            System.out.println("the game already created");
                            break;
                        case "assign_role":
                            System.out.println("you already assign the role of all players");
                            break;
                        case "start_game":
                            System.out.println("the game already started");
                            break;
                        case "get_game_state":
                            getGameStatus();
                            break;
                    }
                }
                vote=scanner.nextLine();
            }
            Player temp = players[0];
            boolean shouldKill=true;
            for (Player player:players){
                if (player!=null)
                if (temp.conjectureMafiVote<player.conjectureMafiVote){
                    temp=player;
                }
            }
            int numberOFVoted= temp.conjectureMafiVote;
            for (Player player : players) {
                if (player!=null)
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
            for (Player player:players){
                if (player!=null)
                player.isSilent=false;
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
        int numberOfVilagers=0;
        for (Player player : players) {
            if (player!=null) {
                if (player instanceof Joker && !player.isLive) {
                    System.out.println(" joker whit name " + player.name + " wone the Game");
                    System.exit(0);
                }
                if ((player instanceof Mafia && player.isLive) || (player instanceof GodFather && player.isLive)) {
                    numberOfMafis++;
                }
                if (player.isLive && !(player instanceof Mafia) && !(player instanceof GodFather)) {
                    numberOfVilagers++;
                }
            }
        }
        if (numberOfMafis==0){
            System.out.println("The vilagers wone the Game");
            System.exit(0);
        }
        if (numberOfVilagers==numberOfMafis){
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
            if (player!=null)
            if (player instanceof GettedUpPlayer && player.isLive) {
                System.out.println(player);
            }
        }
        String command = scanner.nextLine();
        Player[] killedByMafiInNigth = new Player[20];
        boolean isDetectorChose=false;
        while (!command.equals("nigth_end")) {
            int firstSpace = command.indexOf(" ");
            try {
                String name1 = command.substring(0, firstSpace);
                String name2 = command.substring(++firstSpace);
                if (isTheName(name1) && isTheName(name2)) {
                    Player voter = findingThePlayer(name1);
                    Player votee = findingThePlayer(name2);

                    if (voter.isLive && votee.isLive) {
                        if (voter instanceof AsleepedPlayer) {
                            System.out.println("user can not wake up during night");
                        }
                        if (voter instanceof Detective) {
                            if (!isDetectorChose) {
                                if (votee instanceof Mafia) {
                                    System.out.println("YES");
                                } else {
                                    System.out.println("NO");
                                }
                                isDetectorChose = true;
                            } else {
                                System.out.println("detective has already asked");
                            }
                        }
                        if (voter instanceof Silencer) {
                            votee.isSilent = true;
                        }
                        if (voter instanceof Mafia || voter instanceof GodFather) {
                            for (Player player : killedByMafiInNigth) {
                                if (player == null) {
                                    player = votee;
                                }
                            }
                        }
                        if (voter instanceof Doctor) {
                            votee.rescue=true;
                        }
                    } else {
                        if (!voter.isLive) {
                            System.out.println("the player can't wakeup during the nigth");
                        }
                        if (!votee.isLive) {
                            System.out.println("votee already dead");
                        }
                    }
                }else {
                    if (isTheName(name1))
                        System.out.println("the player whith name " + name1 + " not found");
                    if (isTheName(name2))
                        System.out.println("the player whith name " + name2 + " not found");
                }
            } catch (Exception exception) {
                switch (command) {
                    case "create_game":
                        System.out.println("the game already created");
                        break;
                    case "assign_role":
                        System.out.println("you already assign the role of all players");
                        break;
                    case "start_game":
                        System.out.println("the game already started");
                        break;
                    case "get_game_state":
                        getGameStatus();
                        break;
                }
            }
            command = scanner.nextLine();
        }


        // TODO: 25/03/2021 a way for detecting killed or not

        numberOfDayOrNight++;
        middleOFNightAndDay("day");
    }

    public void getGameStatus(){
        int numberOfLivedPlayers=0;
        for (Player player : players){
            if (player!=null)
            if (player.isLive && !(player instanceof GodFather) && !(player instanceof Mafia)){
                numberOfLivedPlayers++;
            }
        }
        int numberOfMafia=0;
        for (Player player : players){
            if (player!=null)
            if (player instanceof Mafia || player instanceof GodFather){
                numberOfMafia++;
            }
        }
        System.out.println("we have now " + numberOfMafia + " mafia & " + numberOfLivedPlayers + " lived vilager");
    }
}