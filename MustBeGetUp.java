public class MustBeGetUp extends Player{

    protected boolean isVoted = false;
    public MustBeGetUp(String name, Roles role) {
        super(name, role);
    }

    @Override
    public String toString(){
        return "Player : " + name + " With role of " + role;
    }
}
class Detective extends MustBeGetUp {

    public Detective(String name, Roles role) {
        super(name, role);
    }
}
class Doctor extends MustBeGetUp {

    public Doctor(String name, Roles role) {
        super(name, role);
    }
}
class MafiaGroupe extends MustBeGetUp{

    public MafiaGroupe(String name, Roles role){
        super(name,role);
    }

}
class Mafia extends MafiaGroupe {

    public Mafia(String name, Roles role) {
        super(name, role);
    }
}
class GodFather extends MafiaGroupe {

    public GodFather(String name, Roles role) {
        super(name, role);
    }
}
class Silencer extends MafiaGroupe {

    public Silencer(String name, Roles role) {
        super(name, role);
    }
}