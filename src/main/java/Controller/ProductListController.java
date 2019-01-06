package Controller;

import ConnectDatabase.ProductDataBase;
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
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import Model.Product;

import java.io.IOException;
import java.util.ArrayList;

public class ProductListController {
    @FXML
    private TableView<Product> tableView;
    @FXML
    private TableColumn ID,name,quantity,price;
    private ProductDataBase productDataBase =new ProductDataBase();
    @FXML
    private TextField textID,textName,textAmount, textPrice;
    @FXML
    private Button addBtn,deletBtn,OrderBtn;
    @FXML
    private Label eID,eName,eAmount,ePrice;
    @FXML
    public void initialize(){
        ID.setCellValueFactory(new PropertyValueFactory<Product,String>("id"));
        ID.setStyle("-fx-alignment: CENTER;");
        name.setCellValueFactory(new PropertyValueFactory<Product,String>("name"));
        quantity.setCellValueFactory(new PropertyValueFactory<Product,Integer>("quantity"));
        price.setCellValueFactory(new PropertyValueFactory<Product,Double>("price"));
        showTable();
        tableView.setEditable(true);
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        quantity.setCellFactory(TextFieldTableCell.<Product, Integer>forTableColumn(new IntegerStringConverter()));
        price.setCellFactory(TextFieldTableCell.<Product, Double>forTableColumn(new DoubleStringConverter()));
    }

    public ObservableList<Product> addData(ArrayList<Product> data){
        ObservableList<Product> temp= FXCollections.observableArrayList();
        for (Product i:data){
            temp.add(i);
        }
        return temp;
    }
    @FXML
    public void handleAddbtn(ActionEvent event) throws Exception {
        if((textID.getText().isEmpty()||textName.getText().isEmpty()||textAmount.getText().isEmpty()||textPrice.getText().isEmpty())){
           if(textID.getText().isEmpty()){
               Alert alert = new Alert(Alert.AlertType.ERROR, "ID is Empty",ButtonType.OK);
               alert.showAndWait();
//               eID.setText("ID is Empty");
           } if (textName.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Name is Empty",ButtonType.OK);
                alert.showAndWait();
               //eName.setText("Amount is Empty");
            }if(textAmount.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Amount is Empty",ButtonType.OK);
                alert.showAndWait();
              //  eAmount.setText("Amount is Empty");
            }if(textPrice.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Price is Empty",ButtonType.OK);
                alert.showAndWait();
             //  ePrice.setText("Price is Empty");
            }
        }else if(checkIDsame(textID.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR, "ID is Same",ButtonType.OK);
            alert.showAndWait();
           // eID.setText("ID is Same");
        }else if (checkNamesame(textName.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Name is Same",ButtonType.OK);
            alert.showAndWait();
            //eName.setText("ID is Same");
        }else {
            String id=textID.getText();
            String name=textName.getText();
            int amonut=Integer.parseInt(textAmount.getText());
            double price=Double.parseDouble(textPrice.getText());
            Product product=new Product(id,name,price,amonut);
            productDataBase.addProductToDB(product);
            textID.clear();
            textName.clear();
            textAmount.clear();
            textPrice.clear();
            eID.setText("");
            eAmount.setText("");
            ePrice.setText("");
            eName.setText("");

            showTable();
        }
    }
    private boolean CheckTextEmpty(){

        return false;
    }
    void showTable(){
        tableView.setItems(addData(productDataBase.getAllProduct()));
    }

    public void deletHandle(ActionEvent event)throws Exception{
        Product selectedItem = tableView.getSelectionModel().getSelectedItem();
        productDataBase.deleteProduct(selectedItem);
        tableView.getItems().remove(selectedItem);
        showTable();
    }


    public void onEditName(TableColumn.CellEditEvent cellEditEvent) {
        Product selectedItem = tableView.getSelectionModel().getSelectedItem();
        selectedItem.setName(cellEditEvent.getNewValue()+"");
        productDataBase.update(selectedItem);
        showTable();
    }
    public void onEditAmount(TableColumn.CellEditEvent cellEditEvent) {
        Product selectedItem = tableView.getSelectionModel().getSelectedItem();
        selectedItem.setQuantity((Integer)cellEditEvent.getNewValue());
        productDataBase.update(selectedItem);
        showTable();
    }
    public void onEditPrice(TableColumn.CellEditEvent cellEditEvent) {
        Product selectedItem = tableView.getSelectionModel().getSelectedItem();
        selectedItem.setPrice((Double)cellEditEvent.getNewValue());
        productDataBase.update(selectedItem);
        showTable();
    }
    @FXML
    public void handleOrderBtn(ActionEvent event) throws IOException {
        OrderBtn= (Button) event.getSource();
        Stage stage = (Stage) OrderBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/OrderLIst.fxml"));
        stage.setScene(new Scene((Parent) loader.load()));
        stage.show();

    }
    Boolean checkNamesame(String name){
       ArrayList<Product>a= productDataBase.getAllProduct();
        for (Product product : a) {
            if(product.getName().equals(name)){
                return true;
            }
        }
        return false;
    }
    Boolean checkIDsame(String id){
        ArrayList<Product>a= productDataBase.getAllProduct();
        for (Product product : a) {
            if(product.getId().equals(id)){
                return true;
            }
        }
        return false;
    }

}
