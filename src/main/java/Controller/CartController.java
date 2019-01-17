package Controller;

import ConnectDatabase.CustomerDB;
import ConnectDatabase.OrderDB;
import ConnectDatabase.OrderDetail;
import Model.Cart;
import Model.Customer;
import Model.Product;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.util.Optional;

import static ConnectDatabase.CustomerDB.customerToken;
import static Controller.ProductController.cart;

public class CartController {
    @FXML
    private ChoiceBox<String> addressChoiceBox;

    @FXML
    private TextArea newAddressTextArea;

    @FXML
    private TableView<Product> tableView;

    @FXML
    private TableColumn nameCol;

    @FXML
    private TableColumn quantityCol;
    @FXML
    private Label oldAddressLabel;

    @FXML

    private  Label newAddressLabel;

    ObservableList<Product> carts = FXCollections.observableArrayList();
    OrderDB orderDB = new OrderDB();
    OrderDetail orderDetail = new OrderDetail();


    @FXML
    void initialize(){

        nameCol.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        quantityCol.setCellFactory(TextFieldTableCell.<Product, Integer>forTableColumn(new IntegerStringConverter()));
        setAllData();
        tableView.setItems(carts);
        addressChoiceBox.getItems().add(customerToken.getAddress());
        addressChoiceBox.getItems().add("new address");
        addressChoiceBox.setValue(customerToken.getAddress());
        oldAddressLabel.setDisable(false);
        newAddressLabel.setOpacity(0);
        newAddressTextArea.setDisable(true);
        newAddressTextArea.setOpacity(0);
        addressChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                System.out.println(addressChoiceBox.getItems().get((Integer) number2));
                if (addressChoiceBox.getItems().get((Integer) number2).equals("new address")) {
                    oldAddressLabel.setText(" ");
                    oldAddressLabel.setDisable(true);
                    newAddressLabel.setOpacity(1);
                    newAddressTextArea.setDisable(false);
                    newAddressTextArea.setOpacity(1);

                } else {
                    oldAddressLabel.setText("address :"+customerToken.getAddress());
                    oldAddressLabel.setDisable(false);
                    newAddressLabel.setOpacity(0);
                    newAddressTextArea.setDisable(true);
                    newAddressTextArea.setOpacity(0);
                }
            }
        });

    }
    void setAllData(){
        carts.addAll(cart.getProducts());
    }
    @FXML
    void handleBackBtn(ActionEvent event) {
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
    void handleBuyBtn(ActionEvent event) {
        if(carts.size()!=0) {
            if (addressChoiceBox.getValue().equals("new address")) {
                boolean textHaveSign = false;
                int factAllNumber = 0;
                for (int i = 0; i < newAddressTextArea.getText().length(); i++) {
                    char tmp = newAddressTextArea.getText().charAt(i);
                    if (((tmp == ')') || (tmp == '(')) || ((tmp >= 48) && (tmp <= 57)) || (tmp == '.') || (tmp == '/') || (tmp == '\\') || ((tmp > 64) && (tmp <= 90)) || ((tmp >= 97) && (tmp <= 122)) || (tmp == ' ')) {
//                        System.out.println(tmp);
//                        System.out.println(tmp + 0);
                        if (((tmp >= 48) && (tmp <= 57)) || tmp == ' ') {
                            factAllNumber++;
//                        System.out.println(factAllNumber+"ssssssssssssss");
//                        System.out.println(newAddressTextArea.getText().length());
                        }
                    } else {
                        textHaveSign = true;
                        break;
                    }
                    if (factAllNumber == newAddressTextArea.getText().length()) {
                        textHaveSign = true;
                    }
                }
                if (textHaveSign) {
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "new address can not have any sign or all number(example: !,@,#,$,%,^,&,*,-,+)", ButtonType.OK);
                    alert.showAndWait();
                } else {
                    customerToken.setAddress(newAddressTextArea.getText());
                    CustomerDB.updateAddress(customerToken.getAddress());
                    System.out.println("new address " + customerToken.getAddress());
                }
            }
            DateTime jodaTime = new DateTime();

            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/YYYY");
            orderDB.createOrder(formatter.print(jodaTime), customerToken.getTel_number());
            double total  = 0;

            for (Product product : carts) {
                orderDetail.createOrderDetail(product, orderDB.getLateOrder_ID()+"", customerToken.getTel_number());
                total+= product.getPrice()*product.getQuantity();
            }
            orderDB.setTotal(orderDB.getLateOrder_ID()+"",total);
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "complete.", ButtonType.OK);
            alert.showAndWait();
//            System.out.println(carts.size());
            cart.removeAll();
//            carts.clear();
//            System.out.println(carts.size());
            handleBackBtn(event);
        }

    }

    @FXML
    void handleDeleteBtn(ActionEvent event) {
        carts.remove(tableView.getSelectionModel().getSelectedItem());
        cart.removeProduct(tableView.getSelectionModel().getSelectedItem().getId());

    }

}
