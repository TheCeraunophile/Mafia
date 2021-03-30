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
    private final Player[] players = new Player[15];
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
                System.out.println("the player with name "+name+" and role of "+nameOfRole+" is add succesfuly");
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
            while (counter < lengthOfPlayersNames){
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
                    switch (line) {
                        case "start_game" -> System.out.println((lengthOfPlayersNames - counter) +
                                " player whitout a role you should first detect their role");
                        case "create_game" -> System.out.println("you already create your game");
                        default -> System.out.println("command not found");
                    }
                }
            }
        }
    }

    public void day(){
        System.out.println("Day " + numberOfDayOrNight);
        if (isGameCreated && rolesPushed){
            gameStarted=true;
            for (Player player : players) {
                if (player!=null)
                if (!player.isSilent && player.isLive){
                    System.out.println(player);
                }
            }
            System.out.println("waited for votee & voter");
            String voteAndVoter=scanner.nextLine();
            while (!voteAndVoter.equals("end_vote")){
                int firstSpace=voteAndVoter.indexOf(" ");
                try {
                    String name1 =voteAndVoter.substring(0,firstSpace);
                    String name2 = voteAndVoter.substring(++firstSpace);
                    if (isTheName(name1) && isTheName(name2)){
                        Player voter = findingThePlayer(name1);
                        Player votee = findingThePlayer(name2);
                        if (votee.isLive && !voter.isSilent && voter.isLive){
                            votee.conjectureMafiVote++;
                        }else {
                            if (!votee.isLive)
                                System.out.println( votee.name+" already dead");
                            if (voter.isSilent)
                                System.out.println("name1 is silencer");
                            if (!voter.isLive)
                                System.out.println(voter.name+" already dead");
                        }
                    }
                }catch (Exception e) {
                    switch (voteAndVoter) {
                        case "create_game" -> System.out.println("the game already created");
                        case "assign_role" -> System.out.println("you already assign the role of all players");
                        case "start_game" -> System.out.println("the game already started");
                        case "get_game_state" -> getGameStatus();
                        default -> System.out.println("command not found");
                    }
                }
                voteAndVoter=scanner.nextLine();
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
            }else {
                System.out.println("no player kiled ");
            }
            for (Player player:players){
                if (player!=null) {
                    player.isSilent = false;
                    player.conjectureMafiVote = 0;
                }
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
                if (player.isLive && (player instanceof Mafia  || player instanceof GodFather  || player instanceof Silencer )) {
                    numberOfMafis++;
                }
                if (player.isLive && !(player instanceof Mafia) && !(player instanceof GodFather) && !(player instanceof Silencer)) {
                    numberOfVilagers++;
                }
            }
        }
        if (numberOfMafis==0 ){
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
        System.out.println("nigth "+numberOfDayOrNight);
        for (Player player : players) {
            if (player!=null)
            if (player instanceof MustBeGetUp && player.isLive) {
                System.out.println(player);
            }
        }
        String command = scanner.nextLine();
        Player[] parr = new Player[20];
        boolean firstChoseOfSilencer=true;
        while (!command.equals("end_nigth")) {
            int firstSpace = command.indexOf(" ");
            try {
                String name1 = command.substring(0, firstSpace);
                String name2 = command.substring(++firstSpace);
                if (isTheName(name1) && isTheName(name2)) {
                    Player voter = findingThePlayer(name1);
                    Player votee = findingThePlayer(name2);
                    if (voter.isLive && votee.isLive) {
                        if (voter instanceof MustBeSleep) {
                            System.out.println("user can not wake up during night");
                        }
                        if (voter instanceof Detective) {
                            if (!((Detective) voter).isVoted) {
                                if (votee instanceof Mafia || votee instanceof Silencer) {
                                    System.out.println("YES");
                                } else {
                                    System.out.println("NO");
                                }
                                ((Detective) voter).isVoted = true;
                            } else {
                                System.out.println("detective has already asked");
                            }
                        }
                        if (firstChoseOfSilencer) {
                            if (voter instanceof Silencer) {
                                if (!((Silencer) voter).isVoted) {
                                    votee.isSilent = true;
                                    ((Silencer) voter).isVoted = true;
                                    firstChoseOfSilencer=false;
                                } else {
                                    System.out.println("silencer already voted for silence");
                                }
                            }
                        }
                        if (voter instanceof Doctor) {
                            if (!((Doctor) voter).isVoted) {
                                votee.rescue = true;
                                ((Doctor) voter).isVoted = true;
                            }else {
                                System.out.println("doctor already voted");
                            }
                        }
                        if ((voter instanceof Mafia || voter instanceof GodFather || (voter instanceof Silencer &&
                                ((Silencer) voter).isVoted)) && !(votee instanceof Joker) ) {
                            for (int i=0,j=1;i<parr.length;i+=2,j+=2){
                                if (parr[i]==null){
                                    parr[i]=voter;
                                    parr[j]=votee ;
                                    break;
                                }else {
                                    if (parr[i].equals(voter)){
                                        parr[j]=votee;
                                        break;
                                    }
                                }
                            }
                        }
                    } else {
                        if (!voter.isLive) {
                            System.out.println("the player can't wakeup during the nigth");
                        }
                        if (!votee.isLive) {
                            System.out.println("votee already dead");
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
                    default:
                        System.out.println("command not found");
                }
            }
            command = scanner.nextLine();
        }

        for (int i=1;parr[i]!=null;i+=2){
            for (Player player:players){
                if (player!=null){
                    if (parr[i].equals(player)){
                        player.conjectureMafiVote++;
                        break;
                    }
                }
            }
        }

        Player mostVote=players[0];
        int repete = 0;
        Player[] outed = new Player[20];
        outed[repete] = mostVote;
        for (Player player:players){
            if (player!=null){
                if (!player.equals(mostVote) && player.conjectureMafiVote!=0) {
                    if (mostVote.conjectureMafiVote == player.conjectureMafiVote) {
                        repete++;
                        outed[repete] = player;
                    }
                    if (mostVote.conjectureMafiVote < player.conjectureMafiVote) {
                        mostVote = player;
                        repete = 0;
                        for (int i=0;outed[i]!=null;i++){
                            outed[i]=null;
                        }
                        outed[repete] = mostVote;
                    }
                }
            }
        }

        repete++;
        if (repete==1) {
            for (Player player:players){
                if (player!=null){
                    if (player.conjectureMafiVote!=0){
                        mostVote.die();
                        break;
                    }
                }
            }
        }else {
            if (repete - getSaved(outed) == 0 || repete - getSaved(outed) > 1) {
                System.out.println("no player killed ");
            }
            if (repete-getSaved(outed)==1){
                for (Player player : outed){
                    if (player!=null){
                        if (!player.rescue){
                            player.die();
                            break;
                        }
                    }
                }
            }
        }

        for (Player player : players){
            if (player!=null){
                if (player instanceof MustBeGetUp) {
                    ((MustBeGetUp) player).isVoted=false;
                }
                player.conjectureMafiVote = 0;
                player.rescue = false;
            }
        }
        numberOfDayOrNight++;
	swap();
        middleOFNightAndDay("day");
    }

    public void getGameStatus(){
        int numberOfVilagers=0;
        int numberOfMafia=0;
        for (Player player : players){
            if (player!=null) {
                if (player.isLive && !(player instanceof GodFather) && !(player instanceof Mafia) && !(player instanceof Silencer)) {
                    numberOfVilagers++;
                }
                if (player.isLive && (player instanceof GodFather || player instanceof Mafia || player instanceof Silencer)){
                    numberOfMafia++;
                }
            }
        }
        System.out.println("we have now " + numberOfMafia + " mafia & " + numberOfVilagers + " lived vilager");
    }

    private int getSaved(Player[] outed){
        int saved=0;
        for (Player player : outed){
            if (player!=null){
                if (player.rescue){
                    saved++;
                }
            }
        }
        return saved;
    }
    private void swap(){
        System.out.println("if you wan swap two character write their name or not press any key");
        String names = scanner.nextLine();
        boolean swaped = false;
        int firstSpace = names.indexOf(" ");
        while (!swaped) {
            try {
                String name1 = names.substring(0, firstSpace);
                String name2 = names.substring(++firstSpace);
                if (isTheName(name1) && isTheName(name2)) {
                    Player character1 = findingThePlayer(name1);
                    Player character2 = findingThePlayer(name2);
                    if (character1.isLive && character2.isLive) {
                        String name = character1.name;
                        character1.name = character2.name;
                        character2.name = name;
                        swaped=true;
                        System.out.println("the swap doned successfuly");
                    } else {
                        if (!character1.isLive)
                            System.out.println("the player " + character1 + " already died");
                        if (!character2.isLive)
                            System.out.println("the player " + character2 + " already died");
                    }
                }
            } catch (Exception exception) {
                swaped = true;
            }
        }
    }
}