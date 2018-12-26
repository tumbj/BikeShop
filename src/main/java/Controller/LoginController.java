package Controller;

import ConnectDatabase.CustomerDB;

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

import static ConnectDatabase.CustomerDB.customerToken;

public class LoginController {

    @FXML
    Button loginBtn;

    @FXML
    Button registerBtn;

    @FXML
    Label showStatus;

    @FXML
    TextField usernameInput;

    @FXML
    PasswordField pwdInput;




    @FXML
    protected void handleNextPageRegisterButtonAction(ActionEvent e) {

        Button b = (Button) e.getSource();

        Stage stage = (Stage) b.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Register.fxml"));
        try {
            stage.setScene(new Scene((Parent) loader.load(), 799, 654));

            stage.show();

        } catch (IOException e1) {
            e1.printStackTrace();
        }


    }


    @FXML
    protected void handleLoginButtonAction(MouseEvent e) throws SQLException, IOException {
        String userID = usernameInput.getText();
        String passID = pwdInput.getText();

        CustomerDB.login(userID,passID);
        System.out.println(customerToken.getUsername());
        navigateTo("/ShowProduct.fxml",e,929, 592);

    }

    public void navigateTo(String name,MouseEvent event,int width,int height){
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
