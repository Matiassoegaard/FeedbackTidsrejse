package UI;

import Data.DB;
import Logic.Kundeadministration;
import Model.Guide;
import Model.Kunder;
import Model.TidsPeriode;
import Model.Tidsresjemaskiner;
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

public class TidsrejseagenturetController implements Initializable {

    @FXML
    private Button bookingButton;

    @FXML
    private Button createCustomerButton;

    @FXML
    private TextField kundeNavn;

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
    private ComboBox<Kunder> customerComboBox;
    @FXML
    private ComboBox<Guide> guideComboBox;
    @FXML
    private ComboBox<TidsPeriode> periodeComboBox;
    @FXML
    private ComboBox<Tidsresjemaskiner> machineComboBox;


    private ObservableList<Kunder> kunderListe = FXCollections.observableArrayList();
    private ObservableList<Guide> guideListe = FXCollections.observableArrayList();
    private ObservableList<TidsPeriode> periodListe = FXCollections.observableArrayList();
    private ObservableList<Tidsresjemaskiner> machineListe = FXCollections.observableArrayList();

    private DB db;
    private Kundeadministration kundeAdmin;

    private Guide guide;
    private Period period;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
           db = new DB();
           kundeAdmin = new Kundeadministration(db);
           customerComboBox.setItems(kunderListe);
            loadCustomer();
           guideComboBox.setItems(guideListe);
            loadGuide();
           periodeComboBox.setItems(periodListe);
            loadPeriod();
           machineComboBox.setItems(machineListe);
           loadMaskine();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    // Kunde
    private void loadCustomer(){
        try {

            ObservableList<Kunder> kunder = kundeAdmin.getCustomerInformation();
            kunderListe.clear();
            kunderListe.addAll(kunder);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Guide
    private void loadGuide() {
        try {

            ObservableList<Guide> guideList = kundeAdmin.getGuideInformation();
            guideListe.clear();
            guideListe.addAll(guideList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Periode
    private void loadPeriod() {
        try {

            ObservableList<TidsPeriode> periodList = kundeAdmin.getTimePeriodInformation();
            periodListe.clear();
            periodListe.addAll(periodList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Maskine
    private void loadMaskine() {
        try {

            ObservableList<Tidsresjemaskiner> machineList = kundeAdmin.getTimeMachineInformation();
            machineListe.clear();
            machineListe.addAll(machineList);
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
            Kunder selectedKunde = customerComboBox.getSelectionModel().getSelectedItem();
            Guide selectedGuide = guideComboBox.getSelectionModel().getSelectedItem();
            Tidsresjemaskiner selectedMaskine = machineComboBox.getSelectionModel().getSelectedItem();
            TidsPeriode selectedPeriod = periodeComboBox.getSelectionModel().getSelectedItem();

            kundeAdmin.opretBooking(selectedKunde.getId(),selectedGuide.getId(),selectedMaskine.getId(),selectedPeriod.getId());
            loadMaskine();
            showAlert(Alert.AlertType.CONFIRMATION, "Booking Complete!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onCreateCustomerButtonClick(){
        String name = kundeNavn.getText();
        String email = customerEmail.getText();

        try{
            if(!name.isEmpty() && !email.isEmpty()){
                kundeAdmin.opretKunde(name,email);
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
            Kunder selectedKunde = customerComboBox.getSelectionModel().getSelectedItem();
            String newName = editCustomerName.getText();
            if(!newName.isEmpty()){
                kundeAdmin.redigerKundeNavn(selectedKunde,newName);
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
            Kunder selectedKunde = customerComboBox.getSelectionModel().getSelectedItem();
            String newEmail = editCustomerEmail.getText();
            if(!newEmail.isEmpty()){
                kundeAdmin.redigerKundeEmail(selectedKunde,newEmail);
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
            Kunder selectedKunde = customerComboBox.getSelectionModel().getSelectedItem();
            kundeAdmin.sletKunde(selectedKunde);

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
    private void showAlert(Alert.AlertType type, String besked) {
        Alert alert = new Alert(type, besked, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }




}