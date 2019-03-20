package ui.mainAppWindow;

import ad.LDAPParser;
import ad.UserHolder;
import db.DbCon;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import properties.Cfg;
import pwSafe.PHolder;
import ui.mainAppWindow.popups.CityEdit;
import ui.mainAppWindow.popups.UserEdit;
import ui.signWindow.SignWindow;
import ui.mailerWindow.InfoWindow;
import ui.mailerWindow.MailerWindow;
import ui.crmWindow.CrmWindow;
import ui.gSuiteWindow.GSuiteWindow;
import ui.sceneControl.ScenePicker;
import ui.settingsWindow.SettingsWindow;
import uiUtils.StatusBar;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;

public class MainWindow extends AnchorPane {

    private ScenePicker scenePicker;

    private FXMLLoader fxmlLoader;

    private UserEdit userEditPop;
    private CityEdit cityEditPop;


    @FXML
    private HBox hBoxUserInfo;
    @FXML
    private Label labelUsername;
    @FXML
    private Label labelCity;
    @FXML
    private ImageView imgPrev;
    @FXML
    private ImageView imgNext;
    @FXML
    private HBox hBoxQty;
    @FXML
    private Label labelCurrentCnt;
    @FXML
    private Label labelMaxCnt;
    @FXML
    public Button tabTest;
    @FXML
    public TabPane tabPane;
    @FXML
    public Pane infoPane;
    @FXML
    public Button scene1;
    @FXML
    public Button scene2;
    @FXML
    public Button scene3;
    @FXML
    public Button scene4;
    @FXML
    public AnchorPane contentPane;
    @FXML
    public StatusBar statusBar;
    @FXML
    private ImageView imgUserEdit;
    @FXML
    private ImageView imgCityEdit;

    public MainWindow(){
        fxmlLoader = new FXMLLoader(getClass().getResource("mainWindow.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public FXMLLoader getFxmlLoader() {
        return fxmlLoader;
    }

    @FXML
    public void initialize(){
        scenePicker = new ScenePicker();
        scenePicker.addAll(new MailerWindow(this), new SignWindow(), new CrmWindow(), new GSuiteWindow(), new SettingsWindow());
        labelCity.setText("");
        labelUsername.setText("");
        infoBarAssetsVisible(false);

        userEditPop = new UserEdit();
        cityEditPop = new CityEdit();

        loadOnStart();
    }



    public void goScn1(ActionEvent actionEvent) {
        selectScene(0);
        statusBar.setVanishingText("Mailing - wysyłanie szablonów wiadomości z danymi dostępowymi");
    }
    @FXML
    public void goScn2(ActionEvent actionEvent) {
        selectScene(1);
        statusBar.setVanishingText("Selected Scene 2");
    }
    @FXML
    public void goScn3(ActionEvent actionEvent) {
        selectScene(2);
        statusBar.setVanishingText("Selected Scene 3");
    }
    @FXML
    public void goScn4(ActionEvent actionEvent) {
        selectScene(3);
        statusBar.setVanishingText("Selected Scene 4");
    }
    @FXML
    public void goScn5(ActionEvent actionEvent) {
        selectScene(4);
        statusBar.setVanishingText("Ustawienia połączeń z serwerem poczty, katalogiem LDAP, bazą danych itp.");
    }

    private void selectScene(int index){

        Pane paneToSet = scenePicker.getScene(index);
        this.contentPane.getChildren().setAll(paneToSet);
        AnchorPane.setRightAnchor(paneToSet,0.);
        AnchorPane.setLeftAnchor(paneToSet, 0.);
        AnchorPane.setTopAnchor(paneToSet, 0.);
        AnchorPane.setBottomAnchor(paneToSet, 0.);

        this.scenePicker.setActiveScene(index);
    }

    private void loadOnStart(){
        int active = scenePicker.getActiveScene();
        if(active >= 0) {
            selectScene(active);
        }
    }
    @FXML
    public void showMailSett(ActionEvent actionEvent) {
        InfoWindow infoWindow = new InfoWindow();
        infoWindow.getStage().show();
    }

    @FXML
    void showDbToolsWindow(ActionEvent event) {

    }

    @FXML //not used currently
    void loadNewestUser(ActionEvent event) throws NamingException, SQLException {
        LDAPParser ldapParser = new LDAPParser();
        ldapParser.loadCfgCredentials();
        ldapParser.initializeLdapContext();

        DbCon dbCon = new DbCon(ldapParser);
        dbCon.loadCfgCredentials();
        dbCon.setDbPass(PHolder.db);
        dbCon.initConnection();

        dbCon.updateUsersTable();

        UserHolder.setCurrentUser(dbCon.getNewestUser());

        changeUser();
    }

    @FXML
    void loadNewUsers() throws NamingException, SQLException {
        infoBarAssetsVisible(true);

        int userQtyToLoad = Integer.parseInt(Cfg.getInstance().retrieveProp(Cfg.DB_USER_QTY));
        LDAPParser ldapParser = new LDAPParser();
        ldapParser.loadCfgCredentials();
        ldapParser.initializeLdapContext();

        DbCon dbCon = new DbCon(ldapParser);
        dbCon.loadCfgCredentials();
        dbCon.setDbPass(PHolder.db);
        dbCon.initConnection();

        dbCon.updateUsersTable();

        dbCon.getNewUsers(userQtyToLoad);

        changeUser();

        this.labelMaxCnt.setText(Integer.toString(UserHolder.getMaxCount()));
    }

    @FXML
    void imgBtnNextUser(MouseEvent event) {
        UserHolder.next();
        changeUser();
    }

    @FXML
    void imgBtnPrevUser(MouseEvent event) {
        UserHolder.previous();
        changeUser();
    }

    @FXML
    void exitAction(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void findAction(ActionEvent event) {
        //todo
    }

    @FXML
    void imgCityEditAction(MouseEvent event) {
        cityEditPop.showAt(event.getScreenX(), event.getScreenY());
    }

    @FXML
    void imgUserEditAction(MouseEvent event) {
        userEditPop.showAt(event.getScreenX(), event.getScreenY());
    }



    private void infoBarAssetsVisible(boolean b){
        this.hBoxQty.setVisible(b);
        this.hBoxUserInfo.setVisible(b);
        this.imgNext.setVisible(b);
        this.imgPrev.setVisible(b);
    }

    private void changeUser(){
        this.labelUsername.setText(UserHolder.getCurrentUser().getDisplayName());
        this.labelCity.setText(UserHolder.getCurrentUser().getCity());
        this.labelCurrentCnt.setText(Integer.toString(UserHolder.getCurrentIndex() + 1));

        MailerWindow mw = (MailerWindow) scenePicker.getScene(0);
        mw.setUserName(UserHolder.getCurrentUser().getDisplayName());

        SignWindow sw = (SignWindow) scenePicker.getScene(1);
        sw.setTxtfName(UserHolder.getCurrentUser().getDisplayName());
        sw.setTxtfCity(UserHolder.getCurrentUser().getCity());
        sw.setTxtfCityPhone(UserHolder.getCurrentUser().getCityPhone());
        sw.setTxtfCityFax(UserHolder.getCurrentUser().getCityFax());
        sw.setTxtfPos(UserHolder.getCurrentUser().getPosition());
        sw.setTxtfPhone(UserHolder.getCurrentUser().getUserPhone());
        sw.setTxtfMPhone(UserHolder.getCurrentUser().getUserMPhone());
        String cType = UserHolder.getCurrentUser().getCityType();
        if(cType.equals("Filia")){
            sw.selectComboxVal(1);
        } else if (cType.equals("Centrala")) {
            sw.selectComboxVal(0);
        }
         sw.reload();
    }



    public void setStatusBarText(String text){
        this.statusBar.setVanishingText(text);
    }

}
