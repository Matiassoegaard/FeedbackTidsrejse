package UI;

import Data.DB;
import Logic.CustomerAdminitration;
import Model.Guide;
import Model.Customer;
import Model.Timeperiod;
import Model.Timemachine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


import java.io.IOException;
import java.net.URL;
import java.time.Period;
import java.util.ResourceBundle;

public class TimeTravelAgencyController implements Initializable {

    @FXML
    private Button bookingButton;

    @FXML
    private Button createCustomerButton;

    @FXML
    private TextField customerNameTextField;

    @FXML
    private TextField customerEmail;

    @FXML
    private TextField editCustomerName;
    @FXML
    private TextField editCustomerEmail;
    @FXML
    private Button deleteCustomerButton;
    @FXML
    private Button bookButton;
    @FXML
    private Button editCustomerButton;
    @FXML
    private Button editNameButton;
    @FXML
    private Button editEmailButton;
    @FXML
    private Button doneEdittingButton;
    @FXML
    private ComboBox<Customer> customerComboBox;
    @FXML
    private ComboBox<Guide> guideComboBox;
    @FXML
    private ComboBox<Timeperiod> periodeComboBox;
    @FXML
    private ComboBox<Timemachine> machineComboBox;


    private ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private ObservableList<Guide> guideList = FXCollections.observableArrayList();
    private ObservableList<Timeperiod> periodList = FXCollections.observableArrayList();
    private ObservableList<Timemachine> machineList = FXCollections.observableArrayList();

    private DB db;
    private CustomerAdminitration CustomerAdminitration;

    private Guide guide;
    private Period period;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
           db = new DB();
           CustomerAdminitration = new CustomerAdminitration(db);
           customerComboBox.setItems(customerList);
            loadCustomer();
           guideComboBox.setItems(guideList);
            loadGuide();
           periodeComboBox.setItems(periodList);
            loadPeriod();
           machineComboBox.setItems(machineList);
           loadMachine();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    // Kunde
    private void loadCustomer(){
        try {

            ObservableList<Customer> customer = CustomerAdminitration.getCustomerInformation();
            customerList.clear();
            customerList.addAll(customer);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Guide
    private void loadGuide() {
        try {

            ObservableList<Guide> guideList = CustomerAdminitration.getGuideInformation();
            this.guideList.clear();
            this.guideList.addAll(guideList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Periode
    private void loadPeriod() {
        try {

            ObservableList<Timeperiod> periodList = CustomerAdminitration.getTimePeriodInformation();
            this.periodList.clear();
            this.periodList.addAll(periodList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Maskine
    private void loadMachine() {
        try {

            ObservableList<Timemachine> machineList = CustomerAdminitration.getTimeMachineInformation();
            this.machineList.clear();
            this.machineList.addAll(machineList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    protected void onBookingButtonClick(ActionEvent event){
        try{
            SceneSwitcher.switchScene(event,"Customer.fxml");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    protected void onBookButtonClick() {
        try {
            Customer selectedKunde = customerComboBox.getSelectionModel().getSelectedItem();
            Guide selectedGuide = guideComboBox.getSelectionModel().getSelectedItem();
            Timemachine selectedMaskine = machineComboBox.getSelectionModel().getSelectedItem();
            Timeperiod selectedPeriod = periodeComboBox.getSelectionModel().getSelectedItem();

            CustomerAdminitration.createBooking(selectedKunde.getId(),selectedGuide.getId(),selectedMaskine.getId(),selectedPeriod.getId());
            loadMachine();
            showAlert(Alert.AlertType.CONFIRMATION, "Booking Complete!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onCreateCustomerButtonClick(){
        String name = customerNameTextField.getText();
        String email = customerEmail.getText();

        try{
            if(!name.isEmpty() && !email.isEmpty()){
                CustomerAdminitration.createCustomer(name,email);
                loadCustomer();
                showAlert(Alert.AlertType.CONFIRMATION, "Creation Complete!");
            } else if (name.isEmpty() || email.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Udfyld venligst felterne");
            }



        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    protected void onEditNameButtonClick(){
        try{
            Customer selectedCustomer = customerComboBox.getSelectionModel().getSelectedItem();
            String newName = editCustomerName.getText();
            if(!newName.isEmpty()){
                CustomerAdminitration.editCustomerName(selectedCustomer,newName);
            }
            loadCustomer();
            if(newName.isEmpty()){
                showAlert(Alert.AlertType.WARNING,"Skriv venligst et navn");
            }else{
                showAlert(Alert.AlertType.CONFIRMATION, "Ændring Complete!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @FXML
    protected void onEditEmailButtonClick(){
        try{
            Customer selectedCustomer = customerComboBox.getSelectionModel().getSelectedItem();
            String newEmail = editCustomerEmail.getText();
            if(!newEmail.isEmpty()){
                CustomerAdminitration.editCustomerEmail(selectedCustomer,newEmail);
            }
            loadCustomer();
            if (newEmail.isEmpty()){
                showAlert(Alert.AlertType.WARNING,"Skriv venligst en email");
            } else {
                showAlert(Alert.AlertType.CONFIRMATION, "Ændring Complete!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    protected void onContinueButtonClick(ActionEvent event) {
        try {
            SceneSwitcher.switchScene(event,"Booking-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onReturnButtonClick(ActionEvent event) {
        try {
            SceneSwitcher.switchScene(event,"Customer.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onDeleteCustomerButtonClick() {
        try {
            Customer selectedCustomer = customerComboBox.getSelectionModel().getSelectedItem();
            CustomerAdminitration.deleteCustomer(selectedCustomer);

            loadCustomer();
            showAlert(Alert.AlertType.CONFIRMATION, "Delete Complete!");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    protected void onEditCustomerButtonClick(ActionEvent event) {
        try {
            SceneSwitcher.switchScene(event, "EditCustomer-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void doneEdittingButton(ActionEvent event) {
        try {
            SceneSwitcher.switchScene(event, "Customer.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    // Hjælpe metode til visning af alert dialog.
    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }




}