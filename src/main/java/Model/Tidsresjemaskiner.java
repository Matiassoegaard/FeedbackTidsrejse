package Model;

public class Tidsresjemaskiner {
    private int id;
    private String navn;
    private int kapacitet;
    private String status;

    public Tidsresjemaskiner(String navn, int kapacitet, String status) {
        this.navn = navn;
        this.kapacitet = kapacitet;
        this.status = status;
    }
    public Tidsresjemaskiner(String navn, int kapacitet, String status, int id) {
        this.navn = navn;
        this.kapacitet = kapacitet;
        this.status = status;
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public String getStatus(){
        return status;
    }

    public int getKapacitet() {
        return kapacitet;
    }

    @Override
    public String toString() {
        return navn + " - " +  kapacitet + " - " + status;
    }
}
