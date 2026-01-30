module com.example.feedbacktidsrejse {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens UI to javafx.fxml;
    exports UI;
}