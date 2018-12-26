package ConnectDatabase;

import Model.Customer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class CustomerDB {
    private static String user = "root";
    private static String pass = "";
    private static String dbURL = "jdbc:mysql://localhost/bikeshop";


    public static Customer customerToken;

    public static void login(String username, String password){
        try{
            Connection conn = DriverManager.getConnection(dbURL,user,pass);


            Statement myStmt = conn.createStatement();

            ResultSet myRs  = myStmt.executeQuery("select * from users where username" +
                    " = '"  + username + "' and password = '" + password+ "'" );

            if(myRs.next()){
                String username1 =myRs.getString("username");
                String pass= myRs.getString("password");
                String firstname = myRs.getString("firstname");
                String lastname = myRs.getString("lastname");
                String address = myRs.getString("address");
                String tel_number = myRs.getString("tel_number");

                customerToken = new Customer(username1,pass,address,firstname,lastname,tel_number);
                conn.close();;
            }

        }
        catch (Exception exc){
            exc.printStackTrace();
        }
    }

    public static void register(String userID,String passID,String repassID,String firstname,String lastname,
                                String address,String tel_number){
        try{
            Connection conn = DriverManager.getConnection(dbURL,user,pass);
            if(passID.equals(repassID)){
                String query = " insert into users (username,password,firstname,lastname,address,tel_number)"
                        + " values (?, ?, ?, ?,?, ?)";

                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString (1, userID);
                preparedStmt.setString (2, passID);
                preparedStmt.setString (3, firstname);
                preparedStmt.setString (4, lastname);
                preparedStmt.setString (5, address);
                preparedStmt.setString (6, tel_number);

                preparedStmt.execute();
                login(userID,passID);
                conn.close();
            }



        }
        catch (Exception exc){
            exc.printStackTrace();
        }

    }

}
