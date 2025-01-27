module org.HuellaCarbono {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    opens org.HuellaCarbono to javafx.fxml;
    exports org.HuellaCarbono;
}
