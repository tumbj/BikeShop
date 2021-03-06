package Controller;

import ConnectDatabase.ProductDB;
import Model.Cart;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

import static ConnectDatabase.CustomerDB.customerToken;
import static Controller.CartController.cart;
import static Controller.CartController.cartForShow;


public class ShowProductController {
    @FXML
    private Button loginBtn;

    @FXML
    private Button registerBtn;

    @FXML
    private TextField searchFill;

    @FXML
    private Button searchBtn;

    @FXML
    private Button bikeBtn;

    @FXML
    private Button bikeAccessBtn;

    @FXML
    private Button accessoriesBtn;

    @FXML
    private TableView<Product> tableView;

    @FXML
    private TableColumn nameCol;

    @FXML
    private TableColumn quantityCol;

    @FXML
    private Button cartBtn;

    @FXML
    private Button managementBtn;
    @FXML
    private Label numOrderLabel;

    @FXML
    private Label showUserLabel;
    @FXML
    private Button logoutBtn;

    @FXML
    private Label dateLabel;

    public static String PRODUCT_ID="";

    ObservableList<Product> products = FXCollections.observableArrayList();

    ArrayList<Product> allProducts = new ArrayList<>();

    @FXML
    public void initialize() {
        nameCol.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));
        quantityCol.setStyle("-fx-alignment: center-right;");
        DateTime jodaTime = new DateTime();
//        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/YYYY");
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/YYYY hh:mm");

//        dateLabel.setOnInputMethodTextChanged(event -> {
//            dateLabel.setText(formatter.print(jodaTime));
//        });
        dateLabel.setText(formatter.print(jodaTime));
        System.out.println(formatter.print(jodaTime));

        if(customerToken!=null) {
            showUserLabel.setText("user:  "+customerToken.getUsername());
        }
        else{
            showUserLabel.setText("");
        }
        if(checkRole()){
            managementBtn.setDisable(false);
            managementBtn.setOpacity(1);
        }else{
            managementBtn.setDisable(true);
            managementBtn.setOpacity(0);
        }

        if(customerToken !=null){
            cartBtn.setDisable(false);
            cartBtn.setOpacity(1);
            //enable logoutBtn
            logoutBtn.setDisable(false);
            logoutBtn.setOpacity(1);
            //disable loginBtn
            loginBtn.setDisable(true);
            loginBtn.setOpacity(0);
        }else{
            cartBtn.setDisable(true);
            cartBtn.setOpacity(0);
            //enable loginBtn
            loginBtn.setDisable(false);
            loginBtn.setOpacity(1);
            //disable logoutBtn
            logoutBtn.setDisable(true);
            logoutBtn.setOpacity(0);
        }
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        quantityCol.setCellFactory(TextFieldTableCell.<Product, Integer>forTableColumn(new IntegerStringConverter()));
        tableView.setItems(products);
        if (customerToken != null) {
            loginBtn.setOpacity(0);
            loginBtn.setDisable(true);
            if(customerToken.getUsername().equals("admin")){
                cartForShow.getProducts().clear();
                System.out.println("clearrrrr");
            }
        }
        setAllData();

    }

    void setAllData() {
        allProducts.clear();
        allProducts.addAll(ProductDB.getAllProduct());
        mergeQuantity();
        products.setAll(allProducts);
    }

    void mergeQuantity(){
//        System.out.println(cartForShow.getProducts().size());
        for(Product product : cartForShow.getProducts()){
            for (Product product1: allProducts) {
                 if(product.getId().equals(product1.getId())){
                     product1.setQuantity(product1.getQuantity()-product.getQuantity());
//                     System.out.println(product1.getName()+" "+
//                             product1.getQuantity() + product.getQuantity());
                 }
            }
        }
    }


    public boolean checkRole(){
        if(customerToken!=null){
            if(customerToken.getUsername().equals("admin")){
                return true;
            }
        }
        return false;
    }

    @FXML
    void onActionManagementBtn(ActionEvent event){
        Button b = (Button) event.getSource();

        Stage stage = (Stage) b.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProductList.fxml"));
        try {
            stage.setScene(new Scene((Parent) loader.load()));

            stage.show();

        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
    @FXML
    void handleLoginBtn(ActionEvent event) {
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleRegisterBtn(ActionEvent actionEvent) {
        Button b = (Button) actionEvent.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Register.fxml"));

        try {
            stage.setScene(new Scene((Parent) loader.load(), 760, 654));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onActionGoBtn(ActionEvent event) {

        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/product.fxml"));

        try {
            if(tableView.getSelectionModel().getSelectedItem()!=null) {


                    PRODUCT_ID = tableView.getSelectionModel().getSelectedItem().getId();

                    stage.setScene(new Scene((Parent) loader.load(), 927, 527));
                    stage.show();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(tableView.getSelectionModel().getSelectedItem().getId());

    }
    @FXML
    void onActionHandleLogoutBtn(ActionEvent event) {

        customerToken = null;

        initialize();
    }


    @FXML
    void onActionCartBtn(ActionEvent event) {
            Button b = (Button) event.getSource();
            Stage stage = (Stage) b.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cart.fxml"));

            try {
                stage.setScene(new Scene((Parent) loader.load(), 771, 515));
                stage.show();


            } catch (IOException e) {
                e.printStackTrace();
            }


    }

    @FXML
    void onActionBikeCategoryBtn(ActionEvent event) {
        products.clear();
        for (Product product:allProducts) {
            if(product.getId().substring(0,1).equals("1")){
               products.add(product);
            }
        }
    }

    @FXML
    void onActionBikeAccessCategoryBtn(ActionEvent event) {
        products.clear();
        for (Product product:allProducts) {
            if(product.getId().substring(0,1).equals("2")){
                products.add(product);
            }
        }
    }

    @FXML
    void onActionAccessCategoryBtn(ActionEvent event) {
        products.clear();
        for (Product product:allProducts) {
            if(product.getId().substring(0,1).equals("3")){
                products.add(product);
            }
        }
    }

    @FXML
    public void onActionHandleSearchBtn(ActionEvent event) {
        boolean found =false;
        ArrayList<Product>tmpProducts = new ArrayList<>();
        if(searchFill!=null){

            for (Product product:allProducts) {
//                found = Arrays.asList(product.getName().split(" ")).contains(searchFill.getText());
                found = product.getName().contains(searchFill.getText());

                if(found){
                    tmpProducts.add(product);
                }
            }
            if(tmpProducts.size()!=0){
                products.clear();
                products.addAll(tmpProducts);
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "Not found product by name.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    searchFill.clear();
                    products.setAll(allProducts);

                }
            }
        }
    }

}

