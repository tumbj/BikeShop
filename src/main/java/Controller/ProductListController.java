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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import Model.Product;

import java.io.File;
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
    @FXML private ImageView  upload;
    private File file;
    private String srcImage="";
    public Button Menu;

    @FXML
    public void initialize(){
        ID.setCellValueFactory(new PropertyValueFactory<Product,String>("id"));
        ID.setStyle("-fx-alignment: CENTER;");
        name.setCellValueFactory(new PropertyValueFactory<Product,String>("name"));
        quantity.setCellValueFactory(new PropertyValueFactory<Product,Integer>("quantity"));
        price.setCellValueFactory(new PropertyValueFactory<Product,Double>("price"));
        price.setStyle("-fx-alignment: center-right;");
        quantity.setStyle("-fx-alignment: CENTER;");
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
        if((textID.getText().isEmpty()||textName.getText().isEmpty()||textAmount.getText().isEmpty()||textPrice.getText().isEmpty())||srcImage.isEmpty()){
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
            }if(srcImage.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Picture is Empty",ButtonType.OK);
                alert.showAndWait();
            }
        }else if(checkIDsame(textID.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR, "ID is Same",ButtonType.OK);
            alert.showAndWait();
           // eID.setText("ID is Same");
        }else if (checkNamesame(textName.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Name is Same",ButtonType.OK);
            alert.showAndWait();
            //eName.setText("ID is Same");
        }else if (!isAllNumber(textAmount)){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Amount is invaild",ButtonType.OK);
            alert.showAndWait();

        }else if (!isAllNumber(textPrice)){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Price is invaild",ButtonType.OK);
            alert.showAndWait();
        }else if(Integer.parseInt(textAmount.getText())<0){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Amount is invaild",ButtonType.OK);
            alert.showAndWait();
        }else if(Double.parseDouble(textPrice.getText())<=0){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Price is invaild",ButtonType.OK);
            alert.showAndWait();
        }
        else {
            String id=textID.getText();
            String name=textName.getText();
            int amonut=Integer.parseInt(textAmount.getText());
            double price=Double.parseDouble(textPrice.getText());
            Product product=new Product(id,name,price,amonut,srcImage);
            productDataBase.addProductToDB(product);
            textID.clear();
            textName.clear();
            textAmount.clear();
            textPrice.clear();
            srcImage="";
            upload.setImage(null);
            showTable();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Complete",ButtonType.OK);
            alert.showAndWait();
        }
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
        if(checkNamesame(cellEditEvent.getNewValue()+"")){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Name is Same",ButtonType.OK);
            alert.showAndWait();

        }else if ((cellEditEvent.getNewValue()+"").isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Name is Empty",ButtonType.OK);
            alert.showAndWait();
        }else{
            Product selectedItem = tableView.getSelectionModel().getSelectedItem();
            selectedItem.setName(cellEditEvent.getNewValue()+"");
            productDataBase.update(selectedItem);
            showTable();
        }

    }
    public void onEditAmount(TableColumn.CellEditEvent cellEditEvent) {
       if(isAllNumber(cellEditEvent.getNewValue()+"")){
           Product selectedItem = tableView.getSelectionModel().getSelectedItem();
           if((Double)cellEditEvent.getNewValue()<=0){
               Alert alert = new Alert(Alert.AlertType.ERROR, "Amount is invaild",ButtonType.OK);
               alert.showAndWait();
           }else{
               selectedItem.setQuantity((Integer)cellEditEvent.getNewValue());
               productDataBase.update(selectedItem);
               showTable();
           }
       }else{
           Alert alert = new Alert(Alert.AlertType.ERROR, "Amount is invaild",ButtonType.OK);
           alert.showAndWait();
       }

    }
    public void onEditPrice(TableColumn.CellEditEvent cellEditEvent) {
        if(isAllNumber(cellEditEvent.getNewValue()+"")){
         Product selectedItem = tableView.getSelectionModel().getSelectedItem();
         if((Double)cellEditEvent.getNewValue()<=0){
             Alert alert = new Alert(Alert.AlertType.ERROR, "Price is invaild",ButtonType.OK);
             alert.showAndWait();
         }else {
             selectedItem.setPrice((Double)cellEditEvent.getNewValue());
             productDataBase.update(selectedItem);
             showTable();
         }

        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Price is invaild",ButtonType.OK);
            alert.showAndWait();
        }

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
    @FXML
    public void loadPicture(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose your picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Choose \".png\" and \".jpg\" file", "*.png", "*.jpg"));
        file = fileChooser.showOpenDialog(upload.getScene().getWindow());
        String[] tmp = file.getAbsolutePath().split("\\\\");
        String path ="";
        for (int i = 0; i < tmp.length-1; i++) {
            path+=tmp[i]+"/";
            System.out.println(tmp[i]);
        }
        path+=tmp[tmp.length-1];
        System.out.println(path);
        upload.setImage(new Image("file:///"+path));
        System.out.println("file:///"+path);
        srcImage="file:///"+path;
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
    public static boolean isAllNumber(String textField) {
        boolean isCorrect = true;
        for (int i = 0; i < textField.length(); i++) {
            if (isCorrect) {
                if ((textField.charAt(i) + "").matches("[0-9.]+")) {
                } else {
                    isCorrect = false;
                    return isCorrect;
                }
            }
        }
        return isCorrect;
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
