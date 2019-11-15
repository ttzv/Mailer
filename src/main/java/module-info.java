module com.ttzv.itmg {
    requires javafx.graphics;
    requires java.naming;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.web;
    requires java.desktop;
    requires jakarta.mail;
    requires org.update4j;
    requires ttzv.uiUtils;
    requires ttzv.propsicl;
    requires jdk.crypto.ec;//TODO: required for gmail's elliptic curve interface, remove this and bake into jlink image with system modules
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    opens com.ttzv.item to javafx.fxml;
    opens com.ttzv.item.ui.mainAppWindow to javafx.fxml;
   // opens com.ttzv.item.ui.mainAppWindow.popups to javafx.fxml;
    opens com.ttzv.item.ui.crmWindow to javafx.fxml;
    opens com.ttzv.item.ui.dbToolsWindow to javafx.fxml;
    opens com.ttzv.item.ui.mailerWindow to javafx.fxml;
    opens com.ttzv.item.ui.gSuiteWindow to javafx.fxml;
    opens com.ttzv.item.ui.settingsWindow to javafx.fxml;
   // opens com.ttzv.item.ui.signWindow to javafx.fxml;

    exports com.ttzv.item;
    exports com.ttzv.item.uiUtils;

}