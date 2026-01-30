package Model;

public class Guide {
    private int id;
    private String name;
    private String speciality;

    public Guide (String name, String speciality, int id){
        this.name = name;
        this.speciality = speciality;
        this.id = id;
    }
    public Guide (String name, String speciality){
        this.name = name;
        this.speciality = speciality;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return name + " - " + speciality;
    }
}
