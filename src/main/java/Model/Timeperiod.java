package Model;

public class Timeperiod {
    private int id;
    private String name;
    private String decription;

    public Timeperiod(String name, String decription, int id) {
        this.id = id;
        this.name = name;
        this.decription = decription;
    }
    public Timeperiod(String name, String decription) {
        this.name = name;
        this.decription = decription;
    }

    public  int getId() {
        return id;
    }

    @Override
    public String toString() {
        return name + " - " + decription;
    }
}
