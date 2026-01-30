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
    private Button deleteCustomerButton;
    @FXML
    private Button bookButton;

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
            throw new RuntimeException(e);
        }
    }

    // Periode
    private void loadPeriod() {
        try {

            ObservableList<TidsPeriode> periodList = kundeAdmin.getTimePeriodInformation();
            periodListe.clear();
            periodListe.addAll(periodList);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Maskine
    private void loadMaskine() {
        try {

            ObservableList<Tidsresjemaskiner> machineList = kundeAdmin.getTimeMachineInformation();
            machineListe.clear();
            machineListe.addAll(machineList);
        } catch (Exception e) {
            throw new RuntimeException(e);
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
    protected void onBookButtonClick(ActionEvent event) {
        try {
            Kunder selectedKunde = customerComboBox.getSelectionModel().getSelectedItem();
            Guide selectedGuide = guideComboBox.getSelectionModel().getSelectedItem();
            Tidsresjemaskiner selectedMaskine = machineComboBox.getSelectionModel().getSelectedItem();
            TidsPeriode selectedPeriod = periodeComboBox.getSelectionModel().getSelectedItem();

            kundeAdmin.opretBooking(selectedKunde.getId(),selectedGuide.getId(),selectedMaskine.getId(),selectedPeriod.getId());

            showAlert(Alert.AlertType.CONFIRMATION, "Booking Complete!");
            loadMaskine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onCreateCustomerButtonClick(ActionEvent event){
        String name = kundeNavn.getText();
        String email = customerEmail.getText();

        try{
            if(!name.isEmpty() && !email.isEmpty()){
                kundeAdmin.opretKunde(name,email);
//                Kunder newKunde = new Kunder(name, email);
                loadCustomer();
            } else if (name.isEmpty() || email.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Udfyld venligst felterne");
            }



        }catch(Exception e){
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
        }catch(Exception e){
            e.printStackTrace();
        }
    }


            //songList.remove(songListView.getSelectionModel().getSelectedIndex());
    // Hjælpe metode til visning af alert dialog.
    private void showAlert(Alert.AlertType type, String besked) {
        Alert alert = new Alert(type, besked, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }




}