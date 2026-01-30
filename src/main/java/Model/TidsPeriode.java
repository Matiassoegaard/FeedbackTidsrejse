package Model;

public class TidsPeriode {
    private int id;
    private String navn;
    private String beskrivelse;

    public TidsPeriode(String navn, String beskrivelse, int id) {
        this.id = id;
        this.navn = navn;
        this.beskrivelse = beskrivelse;
    }
    public TidsPeriode(String navn, String beskrivelse) {
        this.navn = navn;
        this.beskrivelse = beskrivelse;
    }

    public  int getId() {
        return id;
    }

    @Override
    public String toString() {
        return navn + " - " +  beskrivelse;
    }
}
