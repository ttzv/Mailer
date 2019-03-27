package com.ttzv.itmg.ui.mainAppWindow.popups;

import com.ttzv.itmg.ad.User;
import com.ttzv.itmg.ad.UserHolder;
import com.ttzv.itmg.db.DbCon;
import com.ttzv.itmg.db.PgStatement;
import com.ttzv.itmg.ui.mainAppWindow.MainWindow;
import com.ttzv.itmg.uiUtils.UiObjectsWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.SQLException;

public class CityEdit extends AnchorPane {

    private Stage stage;
    private UiObjectsWrapper uiObjectsWrapper;

    public CityEdit(UiObjectsWrapper uiObjectsWrapper) {
        this.uiObjectsWrapper = uiObjectsWrapper;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/cityedit.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            AnchorPane root = fxmlLoader.load();
            stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
        stage.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                stage.close();
            }
        });

    }

    public void showAt(double x, double y) {
        this.labelCityName.setText(UserHolder.getCurrentUser().getCity());
        preloadInfo(UserHolder.getCurrentUser());
        stage.setX(x);
        stage.setY(y);
        stage.show();
    }

    @FXML
    private void initialize() {
        this.txtCityFax.setText("");
        this.txtCityPhone.setText("");
    }

    @FXML
    private TextField txtCityPhone;

    @FXML
    private TextField txtCityFax;

    @FXML
    private Label labelCityName;

    @FXML //USED IN FXML
    void btnSaveAndClose(ActionEvent event) throws SQLException {
        updateDatabase(UserHolder.getCurrentUser());
        stage.close();
    }

    private void preloadInfo(User user) {
        this.txtCityPhone.setText(user.getCityPhone());
        this.txtCityFax.setText(user.getCityFax());
    }

    private void updateDatabase(User user) throws SQLException {
        DbCon dbCon = new DbCon();
        dbCon.loadCfgCredentials();
        dbCon.initConnection();


        String cityPhone = this.txtCityPhone.getText();
        String queryPhone = "";
        if (!cityPhone.isEmpty()) {
            queryPhone = PgStatement.update("city", "phone", PgStatement.apostrophied(cityPhone), "name='" + user.getCity() + "'");
        }

        String cityFax = this.txtCityFax.getText();
        String queryFax = "";
        if (!cityFax.isEmpty()) {
            queryFax = PgStatement.update("city", "fax", PgStatement.apostrophied(cityFax), "name='" + user.getCity() + "'");
        }

        //System.out.println(queryPhone + "\n" + queryFax);

        dbCon.customQuery(queryPhone, queryFax);

        User currentUser = UserHolder.getCurrentUser();
        UserHolder.clear();
        UserHolder.addUser(dbCon.reloadUser(currentUser));
        MainWindow mainWindow = (MainWindow) uiObjectsWrapper.retrieveObject(uiObjectsWrapper.MainWindow);
        mainWindow.changeUser();
    }


}
