package lk.ijse.project.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.project.util.Navigation;


public class OtpFormController {
    private int otp;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnVerify;

    @FXML
    private TextField txtOTP;

    @FXML
    void btnBackOnAction(ActionEvent event) {
        btnBack.getScene().getWindow().hide();
        Navigation.changeStage("/view/forgotPassword.fxml","FORGOT PASSWORD FORM");
    }

    @FXML
    void btnVerifyOnAction(ActionEvent event) {
        System.out.println(ForgotPasswordController.otp);
        if(String.valueOf(otp).equals(txtOTP.getText())){
            btnVerify.getScene().getWindow().hide();
            Navigation.changeStage("/view/resetPasswordForm.fxml","RESET PASSWORD FORM");
        }else{
            new Alert(Alert.AlertType.ERROR,"OTP WRONG");
        }
    }

    public void initialize(){
        System.out.println(ForgotPasswordController.otp);
        this.otp = ForgotPasswordController.otp;
    }
}
