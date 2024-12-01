package lk.ijse.project.coffeeshop.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lk.ijse.project.coffeeshop.model.UserModel;
import lk.ijse.project.coffeeshop.util.Navigation;

import java.sql.SQLException;

public class ResetPasswordFormController {

    @FXML
    private JFXButton btnReset;

    @FXML
    private TextField txtConPw;

    @FXML
    private TextField txtNewPw;
    @FXML
    private JFXButton btnLogin;

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        UserModel userModel = new UserModel();
        if(txtNewPw.getText().equals(txtConPw.getText())) {
            boolean isUpdated = userModel.updatePassword(ForgotPasswordController.username, txtNewPw.getText());
            if (isUpdated) {
                System.out.println("OK");
            } else {
                System.out.println("WRONG");
            }
        }else {
            System.out.println("CONFIRMATION NOT MATCHED!");
        }
    }
    @FXML
    void btnLoginOnAction(ActionEvent event) {
        btnLogin.getScene().getWindow().hide();
        Navigation.changeStage("/view/loginPageForm.fxml","Login Form");
    }
    public void initialize(){
        System.out.println(ForgotPasswordController.username);
    }
}
