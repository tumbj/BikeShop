package Controller;


import ConnectDatabase.OrderDB;
import ConnectDatabase.ProductDataBase;
import Model.Customer;
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

import java.io.IOException;
import java.util.ArrayList;

public class ShowOrderDetailController {
    @FXML
    private TableView<OrderDetail> tableView;
    @FXML
    private TableColumn ID,name,quantity,price,CustomerName,ProductName;
    private OrderDB orderDB=new OrderDB();
    private ProductDataBase productDataBase =new ProductDataBase();
    String orderID;
    @FXML
    private Button checkstock,backBtn,subBtn,Menu;
    @FXML
    private Label messageSt;
    ArrayList<OrderDetail> orderDetails=orderDB.getOrderList(orderID);
    private int s=0;

    public void setDisplay(String OrderID){
        this.orderID=OrderID;
        orderDetails=orderDB.getOrderList(orderID);
        subBtn.setDisable(true);
        subBtn.setVisible(false);
        messageSt.setVisible(false);

        ID.setCellValueFactory(new PropertyValueFactory<OrderDetail,String>("productID"));
        ID.setStyle("-fx-alignment: CENTER;");
        name.setCellValueFactory(new PropertyValueFactory<OrderDetail,String>("tel"));
        name.setStyle("-fx-alignment: CENTER;");
        CustomerName.setCellValueFactory(new PropertyValueFactory<OrderDetail,String>("CustomerName"));
        CustomerName.setStyle("-fx-alignment: CENTER;");
        ProductName.setCellValueFactory(new PropertyValueFactory<OrderDetail,String>("ProductName"));
        ProductName.setStyle("-fx-alignment: CENTER;");
        quantity.setCellValueFactory(new PropertyValueFactory<OrderDetail,String>("amounts"));
        quantity.setStyle("-fx-alignment: center-right;");
        price.setCellValueFactory(new PropertyValueFactory<OrderDetail,String>("prices"));
        price.setStyle("-fx-alignment: center-right;");
        tableView.setItems(addData(orderDB.getOrderListCusPro(orderID)));

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
            if(temp.getQuantity()>=orderDetail.getAmount()){ }
            else {
                status++;
            }
        }
        String str="****Stock balance****\n";
        if(status==0) {
            subBtn.setDisable(false);
            subBtn.setVisible(true);
            messageSt.setVisible(true);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Can buy ",ButtonType.OK);
            alert.showAndWait();
            for (OrderDetail orderDetail : orderDetails) {
                String id=orderDetail.getProductID();
                Product temp;
                temp= productDataBase.getProduct(id);
                str+=temp.getName()+" : "+temp.getQuantity()+" - "+orderDetail.getAmount()+" -> ";
                str+=temp.getQuantity()-orderDetail.getAmount()+"\n";
            }
            messageSt.setText(str);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "can not buy product …not enough",ButtonType.OK);
            alert.showAndWait();
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
    @FXML
    public void handleMenuBtn(ActionEvent event) throws IOException {
        Menu= (Button) event.getSource();
        Stage stage = (Stage)Menu.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowProduct.fxml"));
        stage.setScene(new Scene((Parent) loader.load()));
        stage.show();
    }


}
