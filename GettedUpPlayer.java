public class GettedUpPlayer extends Player{

    public GettedUpPlayer(String name, String role) {
        super(name, role);
    }

    @Override
    public String toString(){
        String result =" The person : " + name + " Whit role of " + role;
        return result;
    }
}
class Detector extends GettedUpPlayer{

    public Detector(String name, String role) {
        super(name, role);
    }
}
class Doctor extends GettedUpPlayer{

    public Doctor(String name, String role) {
        super(name, role);
    }
}
class Mafia extends GettedUpPlayer{

    public Mafia(String name, String role) {
        super(name, role);
    }
}
class GodFather extends GettedUpPlayer{

    public GodFather(String name, String role) {
        super(name, role);
    }
}
class Silencer extends GettedUpPlayer{

    public Silencer(String name, String role) {
        super(name, role);
    }
}