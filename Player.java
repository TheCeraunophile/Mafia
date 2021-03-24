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
        String result =" The person : " + name + " Whit role of " + role+"  ";
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return name.equals(player.name);
    }

}