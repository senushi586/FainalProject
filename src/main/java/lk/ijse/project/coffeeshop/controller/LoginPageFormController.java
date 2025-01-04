package lk.ijse.project.coffeeshop.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.ijse.project.coffeeshop.model.UserModel;
import lk.ijse.project.coffeeshop.util.Navigation;

import java.io.IOException;

public class LoginPageFormController {

    public AnchorPane AncLogin;
    @FXML
    private JFXButton btnBooking;

    @FXML
    private JFXButton btnCustomer;

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnDelivery;

    @FXML
    private JFXButton btnEmployee;

    @FXML
    private JFXButton btnItem;

    @FXML
    private JFXButton btnLogOut;

    @FXML
    private JFXButton btnOrder;

    @FXML
    private JFXButton btnReport;

    @FXML
    private JFXButton btnSupplier;

    @FXML
    private TableColumn<?, ?> colBookId;

    @FXML
    private TableColumn<?, ?> colCusId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colNic;

    @FXML
    private TableColumn<?, ?> colTblName;

    @FXML
    private TableView<?> tblBooking;

    @FXML
    private TextField txtBookiId;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtNic;

    @FXML
    private TextField txtTblName;


    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;
    private ActionEvent actionEvent;
    @FXML
    private PasswordField passwordfield1;

    @FXML
    private Hyperlink txtForgetPassword;



    @FXML
    private TextField usernametxt;


    public void hlinkForgotPassword(ActionEvent actionEvent) {
    }

    public void txtPasswordOnAction(ActionEvent actionEvent) {
    }

//    public void hyperFogotPasswordOnAction(ActionEvent actionEvent) {
//    }

    @FXML
    void hyperFogotPasswordOnAction(ActionEvent event) {
        txtForgetPassword.getScene().getWindow().hide();
        Navigation.changeStage("/view/forgotPassword.fxml","FORGOT PASSWORD FORM");
    }



    public void btncheckpasswordbtnonaction(ActionEvent actionEvent) {
    }

    public void btninvisiblebtnonaction(ActionEvent actionEvent) {
    }

    public void btnOnActionLogin(ActionEvent actionEvent) throws IOException {
        this.actionEvent = actionEvent;
        AncLogin.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/dashboardForm.fxml"));
        AncLogin.getChildren().add(load);
        if (UserModel.verifyCredentials(usernametxt.getText(),passwordfield1.getText())){
            try {
                Navigation.switchNavigation("dashboardForm.fxml",actionEvent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void hyperCreateAccOnAction(ActionEvent actionEvent) {
    }
}
