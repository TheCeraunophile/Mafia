import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;
public class God {
    private static final God god = new God();
    private int numberOfDayOrNight =1;
    private boolean isGameCreated=false;
    private boolean rolesPushed=false;
    private boolean gameStarted=false;
    private final String[] roles = {"Joker", "villager", "detective", "doctor", "bulletproof", "mafia", "godfather", "silencer"};
    private String[] nameOfPlayers;
    private int lengthOfPlayersNames=0;
    private Player[] players ;         /// enhance of being final
    static Scanner scanner = new Scanner(System.in);

    private God(){}

    public God getGod(){
        return god;
    }

    public void creatingGame(){
        isGameCreated=true;
        String names=scanner.nextLine();
        Pattern pattern1 = Pattern.compile("[a-z,A-Z]{1,}");
        Matcher matcher1 = pattern1.matcher(names);
        while (matcher1.find()){
            nameOfPlayers[lengthOfPlayersNames]=names.substring(matcher1.start(), matcher1.end());
            lengthOfPlayersNames++;
        }
        if (lengthOfPlayersNames<9){
            System.out.println("you must atlaste input 8 player you should input " + (8 - lengthOfPlayersNames) + " player more");
            System.out.println("waiting to input remained player");
            creatingGame();
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
        switch (role) {
            case "joker" -> players[counter] = new Joker(name, role);
            case "bulletproof" -> players[counter] = new BulletProof(name, role);
            case "villager" -> players[counter] = new Villager(name, role);
            case "mafia" -> players[counter] = new Mafia(name, role);
            case "godfather" -> players[counter] = new GodFather(name, role);
            case "doctoe" -> players[counter] = new Doctor(name, role);
            case "detector" -> players[counter] = new Detector(name, role);
            case "silencer" -> players[counter] = new Silencer(name, role);
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

            while (counter< nameOfPlayers.length){
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
                        if (counter== nameOfPlayers.length-1){
                            rolesPushed=true;
                        }
                    }
                }catch (Exception exception){
                    switch (line){
                        case "start_game": System.out.println((nameOfPlayers.length - counter) +
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
                if (!player.isSilent && player.isLive){
                    System.out.print(player);
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
                    if (isTheName(voterName)){
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

        for (Player player:players){
            player.isSilent=false;
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
        String command=scanner.nextLine();
        Player[] killedByMafiInNigth=new Player[20];
        Player middleOfDeadAndLive;
        boolean isDetectorChose=false;
        while (!command.equals("nigth_end")) {
            int firstSpace = command.indexOf(" ");
            try {
                String name1 = command.substring(0, firstSpace);
                String name2 = command.substring(++firstSpace);
                if (isTheName(name1) && isTheName(name2)) {
                    Player comander = findingThePlayer(name1);
                    Player detected = findingThePlayer(name2);
                    if (comander==null || detected==null){
                        if (comander==null)
                            System.out.println("the player whith name " + name1 + " not found");
                        if (detected==null)
                            System.out.println("the player whith name " + name2 + " not found");
                    }else {
                        if (comander.isLive && detected.isLive){
                            if (comander instanceof AsleepedPlayer){
                                System.out.println("user can not wake up during night");
                            }
                            if (comander instanceof Detector){
                                if (!isDetectorChose) {
                                    if (detected instanceof Mafia) {
                                        System.out.println("YES");
                                    } else {
                                        System.out.println("NO");
                                    }
                                    isDetectorChose=true;
                                }else {
                                    System.out.println("detective has already asked");
                                }
                            }
                            if (comander instanceof Silencer){
                                detected.isSilent=true;
                            }
                            if (comander instanceof Mafia || comander instanceof GodFather){
                                for (Player player:killedByMafiInNigth){
                                    if (player==null){
                                        player=detected;
                                    }
                                }
                            }
                            if (comander instanceof Doctor){
                                middleOfDeadAndLive=detected;
                            }
                        }else {
                            if (!comander.isLive){
                                System.out.println("the player can't wakeup during the nigth");
                            }
                            if (!detected.isLive){
                                System.out.println("votee already dead");
                            }
                        }
                    }
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
            command= scanner.nextLine();
        }


        // TODO: 25/03/2021 a way for detecting killed or not

        numberOfDayOrNight++;
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
}