package Controller;

import ConnectDatabase.ProductDB;
import Model.Cart;
import Model.Product;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

import static ConnectDatabase.CustomerDB.customerToken;
import static Controller.ShowProductController.PRODUCT_ID;

public class ProductController {
    @FXML
    private Button loginBtn;

    @FXML
    private Button registerBtn;

    @FXML
    private Label productNameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private ChoiceBox<Integer> quantityChoice;

    @FXML
    private Button addToCartBtn;

    private Product product;

    public static Cart cart = Cart.getInstance();
    @FXML
    void initialize(){
         int id = PRODUCT_ID;
        if(ProductDB.getProduct(id)!=null) {
            product = (ProductDB.getProduct(id));
            productNameLabel.setText(product.getName());
            for (int i = 1; i <= product.getQuantity(); i++) {
                quantityChoice.getItems().add(i);
                if(i == product.getQuantity()){
                    quantityChoice.setValue(i);
                }
            }
        }

    }




    @FXML
    void handleLoginBtn(ActionEvent event) {
//        Stage loginStage = new Stage();
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
//            loginStage.setTitle("login");
//            loginStage.setScene(new Scene((Parent) loader.load(), 596, 480));
//            loginStage.setX(0);
//            loginStage.setY(0);
//            loginStage.show();
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));

        try {
            stage.setScene(new Scene((Parent) loader.load(), 596, 480));
            stage.show();

            if (customerToken != null) {
                loginBtn.setOpacity(0);
                loginBtn.setDisable(true);
            }
//            System.out.println(isLogin);
//            if (isLogin) {
//                loginStage.close();
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleRegisterBtn(ActionEvent event) {

    }

    @FXML
    void onActionAddToCartBtn(ActionEvent event) {
        if(customerToken==null) {
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "You must login or register first.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                handleLoginBtn(event);
                System.out.println("115151");

            }
        }
        else {
            cart.addProduct(new Product(product.getId(), product.getName(), product.getPrice()
                    , quantityChoice.getValue()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "Add to cart complete, please check in cart",ButtonType.OK);
            Optional<ButtonType> result = alert.showAndWait();
            System.out.println("!@#123123");

        }
    }
    @FXML
    void onActionBackBtn(ActionEvent event){
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowProduct.fxml"));

        try {
            stage.setScene(new Scene((Parent) loader.load(), 929, 592));
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
