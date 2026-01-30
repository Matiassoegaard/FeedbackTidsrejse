package Logic;

import Data.DB;
import Model.Guide;
import Model.Kunder;
import Model.TidsPeriode;
import Model.Tidsresjemaskiner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class Kundeadministration {
private final DB db;


public  Kundeadministration(DB db) {
    this.db = db;
}
    //Metode til at få hentede kunde information fra mysql
    public ObservableList<Kunder> getCustomerInformation() throws Exception {
        ObservableList<Kunder> kunderList = FXCollections.observableArrayList();
        String sql = "Select * from kunder";

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
    //Metode til at få hentede guide information fra mysql
    public ObservableList<Guide> getGuideInformation() throws Exception {
        ObservableList<Guide> guideList = FXCollections.observableArrayList();
        String sql = "Select * from guide";

        try(Connection c = db.get();
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                String navn = rs.getString("navn");
                String speciality = rs.getString("specialitet");
                int id = rs.getInt("id");

                Guide guide = new Guide(navn,speciality,id);
                guideList.add(guide);

            }
        }
        return guideList;
    }
    //Metode til at få hentede tidsmaskine information fra mysql
    public ObservableList<Tidsresjemaskiner> getTimeMachineInformation() throws Exception {
        ObservableList<Tidsresjemaskiner> machineList = FXCollections.observableArrayList();
        String sql = "Select * from tidsmaskine where kapacitet > 0";

        try(Connection c = db.get();
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                String navn = rs.getString("navn");
                int kapacitet = rs.getInt("kapacitet");
                String status = rs.getString("status");
                int id = rs.getInt("id");


                Tidsresjemaskiner tidsresjemaskiner = new Tidsresjemaskiner(navn,kapacitet,status,id);
                machineList.add(tidsresjemaskiner);
            }
        }
        return machineList;
    }
    //Metode til at få hentede tidsperiode information fra mysql
    public ObservableList<TidsPeriode> getTimePeriodInformation() throws Exception {
        ObservableList<TidsPeriode> timePeriod = FXCollections.observableArrayList();
        String sql = "Select * from tidsperiode";

        try(Connection c = db.get();
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                String navn = rs.getString("navn");
                String beskrivelse = rs.getString("beskrivelse");
                int id = rs.getInt("id");

                TidsPeriode tidsPeriode = new TidsPeriode(navn,beskrivelse,id);
                timePeriod.add(tidsPeriode);
            }
        }
        return timePeriod;
    }


    //Medtode til at oprette en kunde i mysql
    public int opretKunde (String name, String email) throws Exception {
        String sql = "INSERT INTO kunder (navn,email) VALUES (?, ?)";
        try (Connection c = db.get();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, name);
            ps.setString(2, email);


            int rows = ps.executeUpdate();

            return rows;
        }
    }
    //Metode til at oprette booking i mysql
    public int opretBooking (int kunderId, int guideId, int maskineId, int periodeId) throws Exception {
        String sql = "INSERT INTO booking (kundeID,tidsmaskineID,tidsperiodeID, guideID) VALUES (?,?,?,?)";
        String updateKapacitet = "UPDATE tidsmaskine SET kapacitet = kapacitet - 1 WHERE id = ?";
        String updateStatus = "UPDATE tidsmaskine set status = 'Ikke tilgængelig' WHERE id = ? AND kapacitet = 0";

        try (Connection c = db.get();
            PreparedStatement psBooking = c.prepareStatement(sql);
            PreparedStatement psKapacitet = c.prepareStatement(updateKapacitet);
            PreparedStatement psStatus = c.prepareStatement(updateStatus)){

               //indsætter i booking table
                psBooking.setInt(1, kunderId);
                psBooking.setInt(2, maskineId);
                psBooking.setInt(3, periodeId);
                psBooking.setInt(4, guideId);
                int rows = psBooking.executeUpdate();

                // Decrease kapacitet
                psKapacitet.setInt(1, maskineId);
                psKapacitet.executeUpdate();

                // Update status
                psStatus.setInt(1,maskineId);
                psStatus.executeUpdate();

                return rows;
        }
    }
    //Metode til at slette en kunde fra mysql
    public int sletKunde(Kunder kunder) throws Exception {
        String sql = "DELETE FROM kunder WHERE id = ?";
        try (Connection c = db.get();
        PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, kunder.getId());

            int rows = ps.executeUpdate();
            return rows;
        }
    }
    //Metode til at kunne redigere kundes email i mysql
    public int redigerKundeEmail(Kunder kunder, String newEmail) throws Exception {
        String sql = "update kunder set email = ? where id = ?";
        try (Connection c = db.get();
        PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, newEmail);
            ps.setInt(2, kunder.getId());
            int rows = ps.executeUpdate();
            return rows;
        }
    }
    //Metode til at kunne redigere kundes navn i mysql
    public int redigerKundeNavn(Kunder kunder, String newName) throws Exception {
        String sql = "Update kunder set navn =  ? where id = ?";

        try (Connection c = db.get();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, newName);
            ps.setInt(2, kunder.getId());
            int rows = ps.executeUpdate();
            return rows;
        }
    }

}
