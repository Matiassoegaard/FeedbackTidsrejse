package Model;

public class Kunder {
    private String navn;
    private String email;
    private int id;

    public Kunder(String navn, String email, int id) {
        this.navn = navn;
        this.email = email;
        this.id = id;
    }

    public String getNavn() {
        return navn;
    }
    public void setNavn(String navn) {
        this.navn = navn;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return navn + " " + email;
    }
}
