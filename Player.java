public class Player {
    String name;
    String role;
    boolean isSilent=false;
    boolean isLive=true;
    int conjectureMafiVote=0;
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
