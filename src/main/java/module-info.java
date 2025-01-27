module org.HuellaCarbono {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.HuellaCarbono to javafx.fxml;
    exports org.HuellaCarbono;
}
