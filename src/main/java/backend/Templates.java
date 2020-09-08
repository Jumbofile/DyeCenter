package backend;

public class Templates {

    private static String welcome;


    public Templates(){
        this.welcome = "<h1>This is actual message</h1>";
    }

    public static String getWelcome(){
        return welcome;
    }
}
