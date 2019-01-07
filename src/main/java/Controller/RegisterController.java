package Controller;

import ConnectDatabase.CustomerDB;
import Model.Customer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.junit.platform.commons.util.StringUtils;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import static Controller.LoginController.FROMHEIGHT;
import static Controller.LoginController.FROMPAGE;
import static Controller.LoginController.FROMWIDTH;

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
    void initialize(){
        userFill.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                userFill.setStyle("");
            }
        });
        tel_numberFill.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                tel_numberFill.setStyle("");
            }
        });
        addressFill.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                addressFill.setStyle("");
            }
        });
        firstnameFill.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                firstnameFill.setStyle("");
            }
        });
        lastnameFill.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lastnameFill.setStyle("");
            }
        });

    }

    @FXML
    void onActionBackBtn(ActionEvent event){
            if(FROMPAGE==null) {
                navigateTo("/ShowProduct.fxml", event, 929, 592);
            }else {
                navigateTo(FROMPAGE,event, FROMWIDTH, FROMHEIGHT);
                FROMPAGE =null;
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

        if(!StringUtils.isBlank(userID)&&!StringUtils.isBlank(passID)&&!StringUtils.isBlank(repassID)&&!StringUtils.isBlank(firstname)
        &&!StringUtils.isBlank(lastname)&&!StringUtils.isBlank(address)&&!StringUtils.isBlank(tel_number)){
            ArrayList<String> arrs = new ArrayList<>();
            arrs.add(userID);
            arrs.add(passID);
            arrs.add(firstname);
            arrs.add(lastname);
            arrs.add(address);
            arrs.add(tel_number);
            boolean haveSign =false;
            boolean addrHaveSign = false;
            for (int i = 0 ;i<arrs.size();i++) {
                String str = arrs.get(i);
                int factAllNumber ;
                for (int j = 0; j < str.length(); j++) {
                    factAllNumber =0;
                    if(i!=4) {
                        if(i!=5) {
                            if ((str.charAt(j) < 48) || ((str.charAt(j) > 57) && (str.charAt(j) < 65)) || ((str.charAt(j) > 90) && (str.charAt(j) < 97)) || (str.charAt(j) > 122)) {
                                haveSign = true;
                                Alert alert = new Alert(Alert.AlertType.WARNING,
                                        "Input have special sign, please check again", ButtonType.OK);
                                alert.showAndWait();
                                if(i==0){
                                    userFill.setStyle("-fx-border-color: red");;
                                }else if(i==1){
                                    passwordFill.clear();
                                    repasswordFill.clear();
                                }else if(i==2){
                                    firstnameFill.setStyle("-fx-border-color: red");
                                }else if(i==3){
                                    lastnameFill.setStyle("-fx-border-color: red");;
                                }

                                break;
                            }
                        }
                        if(i==5){ //tel num
                            if((str.charAt(j) < 48) ||(str.charAt(j) > 57)){
                                haveSign = true;
                                Alert alert = new Alert(Alert.AlertType.WARNING,
                                        "Input have special sign, please check again", ButtonType.OK);
                                alert.showAndWait();
                                tel_numberFill.setStyle("-fx-border-color: red");
                                break;
                            }
                        }
                    }
                    if(i==4){
                        System.out.println(str);
                        if((((str.charAt(j) == ')') || (str.charAt(j) == '('))  || (str.charAt(j) == '.') || (str.charAt(j) == '/') || (str.charAt(j) == '\\') || (str.charAt(j) == ' '))
                            || ((str.charAt(j)  >= 48) && (str.charAt(j) <= 57)) || ((str.charAt(j) > 64) && (str.charAt(j) <= 90)) || ((str.charAt(j) >= 97) && (str.charAt(j) <= 122))) {
                            if (((str.charAt(j) >= 48) && (str.charAt(j)  <= 57)) || str.charAt(j)  == ' ') {
                                factAllNumber++;
                            }
                        }else{
                            addrHaveSign = true;
                            Alert alert = new Alert(Alert.AlertType.WARNING,
                                    "address can not have any sign or all number(example: !,@,#,$,%,^,&,*,-,+ ", ButtonType.OK);
                            alert.showAndWait();
                            addressFill.setStyle("-fx-border-color: red");
                            break;
                        }
                        if(factAllNumber== str.length()){
                                addrHaveSign = true;
                                Alert alert = new Alert(Alert.AlertType.WARNING,
                                        "address can not have any sign or all number(example: !,@,#,$,%,^,&,*,-,+ ", ButtonType.OK);
                                alert.showAndWait();
                                break;

                        }
                    }
                }
                if(haveSign || addrHaveSign){
                    break;
                }
            }
            if(!haveSign && !addrHaveSign){
                if(passID.equals(repassID)) {
                    CustomerDB.register(userID, passID, repassID, firstname, lastname, address, tel_number);
                    navigateTo("/ShowProduct.fxml", e, 929, 592);
                }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING,
                            "re-password not match\n", ButtonType.OK);
                    alert.showAndWait();
                }
            }

        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "please input in text field", ButtonType.OK);
            alert.showAndWait();
        }

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
