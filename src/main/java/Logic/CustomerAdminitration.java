package Logic;

import Data.DB;
import Model.Guide;
import Model.Customer;
import Model.Timeperiod;
import Model.Timemachine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class CustomerAdminitration {
private final DB db;


public CustomerAdminitration(DB db) {
    this.db = db;
}
    //Metode til at få hentede kunde information fra mysql
    public ObservableList<Customer> getCustomerInformation() throws Exception {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        String sql = "Select * from kunder";

        try(Connection c = db.get();
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                String name = rs.getString("navn");
                String email = rs.getString("email");
                int id = rs.getInt("id");

                Customer customer = new Customer(name,email,id);
                customerList.add(customer);
            }
        }
        return customerList;
    }
    //Metode til at få hentede guide information fra mysql
    public ObservableList<Guide> getGuideInformation() throws Exception {
        ObservableList<Guide> guideList = FXCollections.observableArrayList();
        String sql = "Select * from guide";

        try(Connection c = db.get();
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                String name = rs.getString("navn");
                String speciality = rs.getString("specialitet");
                int id = rs.getInt("id");

                Guide guide = new Guide(name,speciality,id);
                guideList.add(guide);

            }
        }
        return guideList;
    }
    //Metode til at få hentede tidsmaskine information fra mysql
    public ObservableList<Timemachine> getTimeMachineInformation() throws Exception {
        ObservableList<Timemachine> timemachineList = FXCollections.observableArrayList();
        String sql = "Select * from tidsmaskine where kapacitet > 0";

        try(Connection c = db.get();
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                String name = rs.getString("navn");
                int capacity = rs.getInt("kapacitet");
                String status = rs.getString("status");
                int id = rs.getInt("id");


                Timemachine timemachine = new Timemachine(name,capacity,status,id);
                timemachineList.add(timemachine);
            }
        }
        return timemachineList;
    }
    //Metode til at få hentede tidsperiode information fra mysql
    public ObservableList<Timeperiod> getTimePeriodInformation() throws Exception {
        ObservableList<Timeperiod> timePeriod = FXCollections.observableArrayList();
        String sql = "Select * from tidsperiode";

        try(Connection c = db.get();
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                String name = rs.getString("navn");
                String decription = rs.getString("beskrivelse");
                int id = rs.getInt("id");

                Timeperiod timeperiod = new Timeperiod(name,decription,id);
                timePeriod.add(timeperiod);
            }
        }
        return timePeriod;
    }


    //Medtode til at oprette en kunde i mysql
    public int createCustomer(String name, String email) throws Exception {
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
    public int createBooking(int customerId, int guideId, int machineId, int periodId) throws Exception {
        String sql = "INSERT INTO booking (kundeID,tidsmaskineID,tidsperiodeID, guideID) VALUES (?,?,?,?)";
        String updateCapacity = "UPDATE tidsmaskine SET kapacitet = kapacitet - 1 WHERE id = ?";
        String updateStatus = "UPDATE tidsmaskine set status = 'Ikke tilgængelig' WHERE id = ? AND kapacitet = 0";

        try (Connection c = db.get();
            PreparedStatement psBooking = c.prepareStatement(sql);
            PreparedStatement psCapacity = c.prepareStatement(updateCapacity);
            PreparedStatement psStatus = c.prepareStatement(updateStatus)){

               //indsætter i booking table
                psBooking.setInt(1, customerId);
                psBooking.setInt(2, machineId);
                psBooking.setInt(3, periodId);
                psBooking.setInt(4, guideId);
                int rows = psBooking.executeUpdate();

                // Decrease kapacitet
                psCapacity.setInt(1, machineId);
                psCapacity.executeUpdate();

                // Update status
                psStatus.setInt(1,machineId);
                psStatus.executeUpdate();

                return rows;
        }
    }
    //Metode til at slette en kunde fra mysql
    public int deleteCustomer(Customer customer) throws Exception {
        String sql = "DELETE FROM kunder WHERE id = ?";
        try (Connection c = db.get();
        PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, customer.getId());

            int rows = ps.executeUpdate();
            return rows;
        }
    }
    //Metode til at kunne redigere kundes email i mysql
    public int editCustomerEmail(Customer customer, String newEmail) throws Exception {
        String sql = "update kunder set email = ? where id = ?";
        try (Connection c = db.get();
        PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, newEmail);
            ps.setInt(2, customer.getId());
            int rows = ps.executeUpdate();
            return rows;
        }
    }
    //Metode til at kunne redigere kundes navn i mysql
    public int editCustomerName(Customer customer, String newName) throws Exception {
        String sql = "Update kunder set navn =  ? where id = ?";

        try (Connection c = db.get();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, newName);
            ps.setInt(2, customer.getId());
            int rows = ps.executeUpdate();
            return rows;
        }
    }

}
