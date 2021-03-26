public class Player {
    protected String name;
    protected Roles role;
    protected boolean isSilent=false;
    protected boolean isLive=true;
    protected boolean rescue = false;
    protected int conjectureMafiVote=0;

    protected void die(String str){
        if (rescue) {
            rescue = false;
        }else {
            isLive=false;
        }

    }

    protected Player(String name,Roles role) {
        this.name = name;
        this.role=role;
    }

    @Override
    public String toString(){
        return " The person : " + name + " Whit role of " + role;
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