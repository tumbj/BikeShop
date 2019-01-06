package Controller;


import ConnectDatabase.OrderDB;
import ConnectDatabase.ProductDataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import Model.OrderDetail;
import Model.Product;
import Model.Order;

import java.util.ArrayList;

public class ShowOrderDetailController {
    @FXML
    private TableView<OrderDetail> tableView;
    @FXML
    private TableColumn ID,name,quantity,price;
    private OrderDB orderDB=new OrderDB();
    private ProductDataBase productDataBase =new ProductDataBase();
    String orderID;
    @FXML
    private Button checkstock,backBtn,subBtn;
    @FXML
    private Label label;
    ArrayList<OrderDetail> orderDetails=orderDB.getOrderList(orderID);
    private int s=0;

    public void setDisplay(String OrderID){
        this.orderID=OrderID;
        System.out.println("done setdis");
        orderDetails=orderDB.getOrderList(orderID);
        subBtn.setDisable(true);
        subBtn.setVisible(false);

        ID.setCellValueFactory(new PropertyValueFactory<OrderDetail,String>("productID"));
        ID.setStyle("-fx-alignment: CENTER;");
        name.setCellValueFactory(new PropertyValueFactory<OrderDetail,String>("tel"));
        System.out.printf("");
        quantity.setCellValueFactory(new PropertyValueFactory<OrderDetail,Integer>("amount"));
        price.setCellValueFactory(new PropertyValueFactory<OrderDetail,Double>("price"));


        tableView.setItems(addData(orderDB.getOrderList(orderID)));

    }

    public ObservableList<OrderDetail> addData(ArrayList<OrderDetail> data){
        ObservableList<OrderDetail> temp= FXCollections.observableArrayList();
        for (OrderDetail i:data){
            temp.add(i);
        }
        return temp;
    }

    @FXML
    public void handlecheckstock(ActionEvent event) throws Exception {
        int status=0;
        subBtn.setDisable(false);
        for (OrderDetail orderDetail : orderDetails) {
            String id=orderDetail.getProductID();
            Product temp;
            temp= productDataBase.getProduct(id);
            if(temp.getQuantity()>=orderDetail.getAmount()){
                label.setText("can buy");
            }
            else {
                label.setText("can not buy");
                status++;
            }
        }
        if(status==0) {
            subBtn.setDisable(false);
            subBtn.setVisible(true);
        }
    }
    @FXML
    public void handleBackBtn(ActionEvent event) throws Exception {
        backBtn= (Button) event.getSource();
        Stage stage = (Stage) backBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/OrderLIst.fxml"));
        stage.setScene(new Scene((Parent) loader.load()));
        stage.show();
    }
    @FXML
    public void handleSubBtn(ActionEvent event) throws Exception {
        for (OrderDetail orderDetail : orderDetails) {
            Product temp = productDataBase.getProduct(orderDetail.getProductID());
            temp.setQuantity(temp.getQuantity()-orderDetail.getAmount());
            productDataBase.update(temp);
        }
        orderDB.updateOrder(orderID);
        subBtn= (Button) event.getSource();
        Stage stage = (Stage) subBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/OrderLIst.fxml"));
        stage.setScene(new Scene((Parent) loader.load()));
        stage.show();
    }


}
