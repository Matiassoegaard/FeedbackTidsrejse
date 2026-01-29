package UI;

import Data.DB;
import Logic.Kundeadministration;
import Model.Kunder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TidsrejseagenturetController implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    private Button bookingButton;
    @FXML
    private Button createCustomerButton;
    @FXML
    private TextField kundeNavn;
    @FXML
    private ComboBox<Kunder> customerComboBox;

    private DB db;
    private Kundeadministration kundeAdmin;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
           db = new DB();
           kundeAdmin = new Kundeadministration(db);
           loadCustomer();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadCustomer(){
        try {
            ObservableList<Kunder> kunder = kundeAdmin.getCustomerInformation();
            customerComboBox.setItems(kunder);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   // ObservableList<String> timePeriods = FXCollections.observableArrayList("Dinosaurernes æra", "Middelalderen", "Fremtiden");
    // Binding til en ComboBox
    //timePeriodComboBox.setItems(timePeriods);

    @FXML
    protected void onBookingButtonClick(ActionEvent event) throws IOException {
        try{
            SceneSwitcher.switchScene(event,"Customer.fxml");
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    protected void onCreateCustomerButtonClick(ActionEvent event) throws IOException {
        try{
            SceneSwitcher.switchScene(event,"Booking-view.fxml");
        }catch(IOException e){
            e.printStackTrace();
        }
    }



}