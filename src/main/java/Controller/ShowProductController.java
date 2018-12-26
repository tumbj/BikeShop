package Controller;

import ConnectDatabase.ProductDB;
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

import java.io.IOException;

import static ConnectDatabase.CustomerDB.customerToken;



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
    private Label numOrderLabel;


    public static int PRODUCT_ID=0;

    ObservableList<Product> products = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        nameCol.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));

        setAllData();
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        quantityCol.setCellFactory(TextFieldTableCell.<Product, Integer>forTableColumn(new IntegerStringConverter()));
        tableView.setItems(products);
        if (customerToken != null) {
            loginBtn.setOpacity(0);
            loginBtn.setDisable(true);
        }

    }

    void setAllData() {
        products.addAll(ProductDB.getAllProduct());
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
//            System.out.println(isLogin);
//            if (isLogin) {
//                loginStage.close();
//            }

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
            stage.setScene(new Scene((Parent) loader.load(), 596, 480));
            stage.show();

//            System.out.println(isLogin);
//            if (isLogin) {
//                loginStage.close();
//            }

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
            if(tableView.getSelectionModel().getSelectedItem().getId()!=null) {
                int tmp = Integer.parseInt(tableView.getSelectionModel().getSelectedItem().getId());

                    PRODUCT_ID = tmp;

                    stage.setScene(new Scene((Parent) loader.load(), 927, 527));
                    stage.show();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(tableView.getSelectionModel().getSelectedItem().getId());
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

}

