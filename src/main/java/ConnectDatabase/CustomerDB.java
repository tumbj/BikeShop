package ConnectDatabase;

import Model.Customer;

import java.sql.*;

public class CustomerDB {
    private static String dbURL = "jdbc:sqlite:Database.db";
    private static String dbName = "org.sqlite.JDBC";


    public static Customer customerToken;

    public static void login(String username, String password){
        try{
            Class.forName(dbName);
            Connection conn = DriverManager.getConnection(dbURL);

            Statement myStmt = conn.createStatement();

            ResultSet myRs  = myStmt.executeQuery("select * from Customer where username" +
                    " = '"  + username + "' and password = '" + password+ "'" );

            if(myRs.next()){
                String username1 =myRs.getString("username");
                String pass= myRs.getString("password");
                String firstname = myRs.getString("first_name");
                String lastname = myRs.getString("last_name");
                String address = myRs.getString("address");
                String tel_number = myRs.getString("tel_number");

                customerToken = new Customer(username1,pass,address,firstname,lastname,tel_number);
                conn.close();
            }

        }
        catch (Exception exc){
            exc.printStackTrace();
        }
    }

    public static void register(String userID,String passID,String repassID,String firstname,String lastname,
                                String address,String tel_number){
        try{
            Class.forName(dbName);
            Connection conn = DriverManager .getConnection(dbURL);
            if(passID.equals(repassID)){
                String query = " insert into customer (tel_number,username,password,first_name,last_name,address)"
                        + " values (?, ?, ?, ?,?, ?)";

                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString (1, tel_number);
                preparedStmt.setString (2, userID);
                preparedStmt.setString (3, passID);
                preparedStmt.setString (4, firstname);
                preparedStmt.setString (5, lastname);
                preparedStmt.setString (6, address);


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
