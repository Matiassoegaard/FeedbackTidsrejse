module com.example.feedbacktidsrejse {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens UI to javafx.fxml;
    exports UI;
}