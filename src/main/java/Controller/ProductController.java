package Controller;

import ConnectDatabase.ProductDB;
import Model.Cart;
import Model.Product;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Optional;

import static ConnectDatabase.CustomerDB.customerToken;
import static Controller.CartController.cart;
import static Controller.CartController.cartForShow;
import static Controller.ShowProductController.PRODUCT_ID;
import static Controller.LoginController.FROMHEIGHT;
import static Controller.LoginController.FROMPAGE;
import static Controller.LoginController.FROMWIDTH;


public class ProductController {

    @FXML
    private Button loginBtn;
    @FXML
    private Button logoutBtn;

    @FXML
    private Button registerBtn;

    @FXML
    private Label productNameLabel;

    @FXML
    private Label priceLabel;

//    @FXML
//    private ChoiceBox<Integer> quantityChoice;

    @FXML
    private Button addToCartBtn;
    @FXML
    private ImageView showImage;

    private Product product;
    @FXML
    private Label showUserLabel;

    @FXML
    private Label maxQuantity;
    @FXML
    private TextField inputQuantity;
    @FXML
    void initialize(){
        if(customerToken!=null) {
            showUserLabel.setText("user:  "+customerToken.getUsername());
        }else{
            showUserLabel.setText("");
        }
        if(ProductDB.getProduct(PRODUCT_ID)!=null) {
            product = (ProductDB.getProduct(PRODUCT_ID));
            productNameLabel.setText(product.getName());
//            for (int i = 1; i <= product.getQuantity(); i++) {
//                quantityChoice.getItems().add(i);
//                if(i == product.getQuantity()){
//                    quantityChoice.setValue(i);
//                }
//            }
            showImage.setImage(new Image(product.getUrlImage()));
            mergeQuantity();
            maxQuantity.setText("/"+product.getQuantity());

            String number = product.getPrice()+"";
            double amount = Double.parseDouble(number);
            DecimalFormat formatter = new DecimalFormat("#,###.00");
            priceLabel.setText(formatter.format(amount)+"");
        }
        if(customerToken !=null){
            //enable logoutBtn
            logoutBtn.setDisable(false);
            logoutBtn.setOpacity(1);
            //disable loginBtn
            loginBtn.setDisable(true);
            loginBtn.setOpacity(0);
        }else{
            //enable loginBtn
            loginBtn.setDisable(false);
            loginBtn.setOpacity(1);
            //disable logoutBtn
            logoutBtn.setDisable(true);
            logoutBtn.setOpacity(0);
        }

        inputQuantity.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(inputQuantity.getText().matches("[0-9]+")) {
//                if(isAllNumber(inputQuantity)) {
                    if (Integer.parseInt(inputQuantity.getText()) > product.getQuantity()) {
                        inputQuantity.setText(product.getQuantity() + "");
                    }
                }else{
                    inputQuantity.setText("");
                }
            }
        });

    }

    void mergeQuantity(){
        if(cartForShow!=null){
            for (Product product1 : cartForShow.getProducts()) {

                if (product1.getId().equals(product.getId())) {
                    product.setQuantity(product.getQuantity() - product1.getQuantity());
                }

            }
        }else {
            for (Product product1 : cart.getProducts()) {

                if (product1.getId().equals(product.getId())) {
                    product.setQuantity(product.getQuantity() - product1.getQuantity());
//                     System.out.println(product1.getName()+" "+
//                             product1.getQuantity() + product.getQuantity());
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


            FROMPAGE= ("/product.fxml");
            FROMWIDTH=(927);
            FROMHEIGHT=(527);
            if (customerToken != null) {
                loginBtn.setOpacity(0);
                loginBtn.setDisable(true);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleRegisterBtn(ActionEvent event) {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Register.fxml"));

        try {
            stage.setScene(new Scene((Parent) loader.load(), 760, 654));
            stage.show();


            FROMPAGE= ("/product.fxml");
            FROMWIDTH=(927);
            FROMHEIGHT=(527);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
            if(Integer.parseInt(inputQuantity.getText())>0) {
                cart.addProduct(new Product(product.getId(), product.getName(), product.getPrice()
                        , Integer.parseInt(inputQuantity.getText()), product.getUrlImage()));
                cartForShow.addProduct(new Product(product.getId(), product.getName(), product.getPrice()
                        , Integer.parseInt(inputQuantity.getText()), product.getUrlImage()));
//            cart.addProduct(new Product(product.getId(), product.getName(), product.getPrice()
//                    , quantityChoice.getValue()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "Add to cart complete, please check in cart", ButtonType.OK);
                Optional<ButtonType> result = alert.showAndWait();
                System.out.println("!@#123123");
            }

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

    @FXML
    void onActionHandleLogoutBtn(ActionEvent event) {
            customerToken = null;
            initialize();

    }



}
