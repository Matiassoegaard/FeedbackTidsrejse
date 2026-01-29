package Logic;

import Data.DB;
import Model.Kunder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class Kundeadministration {
private final DB db;

public  Kundeadministration(DB db) {
    this.db = db;
}

    public ObservableList<Kunder> getCustomerInformation() throws Exception {
        ObservableList<Kunder> kunderList = FXCollections.observableArrayList();
        String sql = "Select* from kunder";

        try(Connection c = db.get();
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                String navn = rs.getString("navn");
                String email = rs.getString("email");
                int id = rs.getInt("id");

                Kunder kunde = new Kunder(navn,email,id);
                kunderList.add(kunde);
            }
        }
        return kunderList;
    }

    public int opretKunde (Kunder kunder) throws Exception {
        String sql = "INSERT INTO kunder (navn,email) VALUES (?, ?)";
        try (Connection c = db.get();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, kunder.getNavn());
            ps.setString(2, kunder.getEmail());


            int rows = ps.executeUpdate();

            return rows;
        }
    }

    public int sletKunde(Kunder kunder) throws Exception {
        String sql = "DELETE FROM kunder WHERE id = ?";
        try (Connection c = db.get();
        PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, kunder.getId());

            int rows = ps.executeUpdate();
            return rows;
        }
    }

    public int redigerKundeEmail(Kunder kunder) throws Exception {
        String sql = "update kunder set email = ? where id = ?";
        try (Connection c = db.get();
        PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, kunder.getEmail());
            ps.setInt(2, kunder.getId());
            int rows = ps.executeUpdate();
            return rows;
        }
    }

    public int redigerKundeNavn(Kunder kunder) throws Exception {
        String sql = "Update kunder set navn =  ? where id = ?";
        try (Connection c = db.get();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, kunder.getNavn());
            ps.setInt(2, kunder.getId());
            int rows = ps.executeUpdate();
            return rows;
        }
    }










}
