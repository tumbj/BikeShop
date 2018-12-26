package ConnectDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Driver {
    public static void main(String[] args) {
        String user = "root";
        String pass = "";
        String url = "jdbc:mysql://localhost/bikeshop";

        try{
            Connection conn = DriverManager.getConnection(url,user,pass);

            Statement myStmt = conn.createStatement();


            String s = "mava";
//            String a = "isus";
//            String b = "eiei";
//            String c = "fuck";
//            String sql2 = "insert into users (username,password,firstname,lastname )"+
////                    "values('testza','5678','" + s + "','za')";
////            String sql3 = "insert into users (username,password,firstname,lastname )"+
////                    "values('"+ s + "','"+ a +"','" + b + "','"+c +"')";
//
//            myStmt.executeUpdate(sql3);

//            ResultSet myRs = myStmt.executeQuery("Select * from users");
//
//            while (myRs.next()){
//                System.out.println(myRs.getString("ID")+ " " + myRs.getString("username")+" "
//                        +  myRs.getString("firstname"));
//            }
        }
        catch (Exception exc){
            exc.printStackTrace();
        }
    }
}
