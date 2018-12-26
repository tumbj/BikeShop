package Controller;

import ConnectDatabase.CustomerDB;
import Model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class RegisterController {

    @FXML
    Button backBtn;

    @FXML
    Button registerBtn;

    @FXML
    TextField userFill;
    @FXML
    PasswordField passwordFill;
    @FXML
    PasswordField repasswordFill;

    @FXML
    TextField firstnameFill;

    @FXML
    TextField lastnameFill;

    @FXML
    TextField addressFill;

    @FXML
    TextField tel_numberFill;

    @FXML
    Label label;
    @FXML
    protected void handleBackPageLoginButtonAction(ActionEvent e) {

        Button b = (Button) e.getSource();

        Stage stage = (Stage) b.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        try {
            stage.setScene(new Scene((Parent) loader.load(), 799, 654));

            stage.show();

        } catch (IOException e1) {
            e1.printStackTrace();
        }



    }
    @FXML
    protected void handleRegisterButtonAction(ActionEvent e)  {
        String userID = userFill.getText();
        String passID = passwordFill.getText();
        String repassID = repasswordFill.getText();
        String firstname = firstnameFill.getText();
        String lastname = lastnameFill.getText();
        String address = addressFill.getText();
        String tel_number = tel_numberFill.getText();

        CustomerDB.register(userID,passID,repassID,firstname,lastname,address,tel_number);
        navigateTo("/ShowProduct.fxml",e,929, 592);



    }
    public void navigateTo(String name, ActionEvent event, int width, int height){
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(name));

        try {
            stage.setScene(new Scene((Parent) loader.load(), width, height));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
