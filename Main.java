import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        God god = God.getGod();
        while (scanner.hasNext()) {
            String act = scanner.next();
            switch (act) {
                case "create_game" -> god.creatingGame();
                case "assign_role" -> god.assignRole();
                case "start_game" -> god.day();
                case "get_game_state" -> god.getGameStatus();
                default -> System.out.println("your command is undifined");
            }
        }
    }
}