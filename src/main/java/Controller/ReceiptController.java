package Controller;

import ConnectDatabase.OrderDB;
import Model.OrderDetail;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReceiptController {
    private OrderDB orderDB=new OrderDB();
    @FXML
    Button OrderList,Menu,ProductLIst;
    @FXML
    TextArea textReceipt;
    @FXML
    TextArea kuy;
    public void setDisplay(String order){
        double total=0;
        ArrayList<OrderDetail> orderDetails=orderDB.getOrderList(order);
        Date obj=new Date();
        SimpleDateFormat ft = new SimpleDateFormat (" E dd/MM/yyyy");
        String date=ft.format(obj);

        String output="";
        String s =String.format("%-15s*%5s*%10s\n", "****************", "*****", "**********");
        String s1=String.format("%-8s %5s %10s\n", "*", "bike shop Receipt","*");
        String s2=String.format("%-15s*%5s*%10s\n", "****************", "*****", "**********");
        String s3="Date: "+date+"\n";
        String s4= String.format("%-15s %5s %10s\n", "Item", "Qty", "Price");
        String s5= String.format("%-15s %5s %10s\n", "----", "---", "-----");
        for (OrderDetail orderDetail : orderDetails) {
            double d=orderDetail.getAmount()*orderDetail.getPrice();
            total+=d;
            String line = String.format("%-15s %5d %10.2f\n",orderDB.getNamebyID(orderDetail.getProductID()),orderDetail.getAmount(), orderDetail.getPrice());
            output+=line;
        }
        String s7= String.format("%-15s %5s %10s\n", "", "", "-----");
        String s6= String.format("%-15s %5s %10.2f\n", "Total", "",total);

        System.out.println(s+s1+s2+s3+s4+s5+output+s7+s6);
        textReceipt.setText(s+s1+s2+s3+s4+s5+output+s7+s6);
       kuy.setText(s+s1+s2+s3+s4+s5+output+s7+s6);
    }
    @FXML
    public void handleProductLIstBtn(ActionEvent event) throws IOException {
        ProductLIst= (Button) event.getSource();
        Stage stage = (Stage) ProductLIst.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProductList.fxml"));
        stage.setScene(new Scene((Parent) loader.load()));
        stage.show();
    }
    @FXML
    public void handleOrderBtn(ActionEvent event) throws IOException {
        OrderList= (Button) event.getSource();
        Stage stage = (Stage) OrderList.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/OrderList.fxml"));
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
