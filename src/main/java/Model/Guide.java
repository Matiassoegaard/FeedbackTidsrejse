package Model;

public class Guide {
    private int id;
    private String navn;
    private String specialitet;

    public Guide (String navn, String specialitet, int id){
        this.navn = navn;
        this.specialitet = specialitet;
        this.id = id;
    }
    public Guide (String navn, String specialitet){
        this.navn = navn;
        this.specialitet = specialitet;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return navn + " - " +specialitet;
    }
}
