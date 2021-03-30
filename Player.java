public class Player {
    protected String name;
    protected Roles role;
    protected boolean isSilent=false;
    protected boolean isLive=true;
    protected boolean rescue = false;
    protected int conjectureMafiVote=0;

    protected void die(){
        if (rescue) {
            rescue = false;
            System.out.println("no player kiled -__- Doctor saved");
        }else {
            System.out.println("player with name "+this.name+" kiled");
            isLive=false;
        }
    }

    protected void die(Player... players){
        if (rescue){
            rescue=false;
            System.out.println("no player kiled -__- Doctor saved");
        }else {
            System.out.println("player with name "+this.name+" kiled");
            isLive=false;
            if (this instanceof Informer) {
                ((Informer) this).informerTask(players);
            }
        }
    }

    protected Player(String name,Roles role) {
        this.name = name;
        this.role=role;
    }

    @Override
    public String toString(){
        return " The person : " + name + " With role of " + role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return name.equals(player.name);
    }

    protected String getRole() {
        return role.toString();
    }
}