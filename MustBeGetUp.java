public class MustBeGetUp extends Player{

    protected boolean isVoted = false;
    public MustBeGetUp(String name, Roles role) {
        super(name, role);
    }

    @Override
    public String toString(){
        String result =" The person : " + name + " With role of " + role;
        return result;
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
class Mafia extends MustBeGetUp {

    public Mafia(String name, Roles role) {
        super(name, role);
    }
}
class GodFather extends MustBeGetUp {

    public GodFather(String name, Roles role) {
        super(name, role);
    }
}
class Silencer extends MustBeGetUp {

    public Silencer(String name, Roles role) {
        super(name, role);
    }
}