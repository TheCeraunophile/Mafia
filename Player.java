public class Player {
    String name;
    String role;

    public Player(String name,String role) {
        this.name = name;
        this.role=role;
    }

    @Override
    public String toString(){
        String result =" The person : " + name + " Whit role of " + role;
        return result;
    }
}
