package lk.ijse.project.coffeeshop.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lk.ijse.project.coffeeshop.dto.UserDto;
import lk.ijse.project.coffeeshop.model.UserModel;
import lk.ijse.project.coffeeshop.util.Navigation;


import javax.mail.MessagingException;
import java.sql.SQLException;
import java.util.Random;

public class ForgotPasswordController {

    static int otp;

    @FXML
    private JFXButton btnreset;

    @FXML
    private TextField txtUserName;
    @FXML
    private JFXButton btnBack;
    static String username;

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException, MessagingException {


        username = txtUserName.getText();
        UserModel userModel = new UserModel();
        Random random = new Random();
        otp = random.nextInt(9000);
        otp += 1000;
        UserDto userDto = userModel.getEmail(username);
        System.out.println(userDto.getE_mail());
        EmailController.sendEmail(userDto.getE_mail(), "Fruity fizz verification", otp + "");

        btnreset.getScene().getWindow().hide();
        Navigation.changeStage("/view/otpForm.fxml","OTP Form");

    }
    @FXML
    void btnBackOnAction(ActionEvent event) {
        btnBack.getScene().getWindow().hide();
        Navigation.changeStage("/view/loginPageForm.fxml","Login Form");
    }



}
