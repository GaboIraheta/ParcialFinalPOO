module org.parcialfinal_poo {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.parcialfinal_poo to javafx.fxml;
    exports org.parcialfinal_poo;
}