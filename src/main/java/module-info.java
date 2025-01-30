module org.HuellaCarbono {
    requires java.naming;
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.desktop;

    opens org.HuellaCarbono to javafx.fxml;
    exports org.HuellaCarbono;

    opens org.HuellaCarbono.model.entity to org.hibernate.orm.core;
}
