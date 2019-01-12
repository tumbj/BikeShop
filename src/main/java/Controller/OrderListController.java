package Controller;


import ConnectDatabase.OrderDB;
import Model.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.ArrayList;

public class OrderListController {
    @FXML
    private TableView<Order> tableView;
    @FXML
    private TableColumn OrderID,CustomerID,Status;
    private OrderDB orderDB=new OrderDB();
    private  Order o;
    @FXML
    private Label label;

    @FXML
    Button goBtn,backBtn;
    @FXML
    public void initialize() throws IOException {
        CustomerID.setCellValueFactory(new PropertyValueFactory<Order,String>("CustomerID"));
        OrderID.setCellValueFactory(new PropertyValueFactory<Order,String>("OrderID"));
        Status.setCellValueFactory(new PropertyValueFactory<Order,Boolean>("Status"));
        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                o = tableView.getSelectionModel().getSelectedItem();
            }
        });
        tableView.setItems(addData(orderDB.getAllOrder()));
        if(checkFalse(orderDB.getAllOrder())){
            label.setText("your Have Order");
        }else {
            label.setText("");
        }


    }
    public ObservableList<Order> addData(ArrayList<Order> data){
        ObservableList<Order> temp= FXCollections.observableArrayList();
        for (Order i:data){
            temp.add(i);
        }
        return temp;
    }
    public  void handleGoBtn(ActionEvent event)throws Exception {
        if(o!=null){
            Stage stage = (Stage) goBtn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/OrderDetail.fxml"));
            stage.setScene(new Scene(loader.load()));
            ShowOrderDetailController showOrderDeTailController = loader.getController();
            showOrderDeTailController.setDisplay(o.getOrderID());
        }
    }

    public Boolean checkFalse(ArrayList<Order>orders){
        for (Order order : orders) {
            if(!order.isStatus()){
                return true;
            }
        }
        return false;
    }
    @FXML
    public void handleBackBtn(ActionEvent event) throws Exception {
        backBtn= (Button) event.getSource();
        Stage stage = (Stage) backBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProductLIst.fxml"));
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }
    public static boolean isAllNumber(TextField textField) {
        boolean isCorrect = true;
        for (int i = 0; i < textField.getText().length(); i++) {
            if (isCorrect) {
                if ((textField.getText().charAt(i) + "").matches("[0-9.]+")) {
                } else {
                    isCorrect = false;
                    textField.setStyle("-fx-border-color: red");
                    return isCorrect;
                }
            }
        }
        textField.setStyle("");
        return isCorrect;
    }
}
