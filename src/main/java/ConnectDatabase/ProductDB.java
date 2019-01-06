package ConnectDatabase;



import Model.Product;

import java.sql.*;
import java.util.ArrayList;

public class ProductDB {
    private static String dbURL = "jdbc:sqlite:Database.db";
    private static String dbName = "org.sqlite.JDBC";

    public static ArrayList<Product> getAllProduct(){
        ArrayList<Product>products=new ArrayList<>();
        try{
            Class.forName(dbName);
            Connection connection = DriverManager.getConnection(dbURL);
            if(connection != null){
                String query = "select * from Product";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()){
                    String id =resultSet.getString("id");
                    String name=resultSet.getString("name");
                    double price = resultSet.getDouble("price");
                    int quantity = resultSet.getInt("quantity");
                    String url = resultSet.getString("urlImage");
                    products.add(new Product(id,name,price,quantity,url));
                }
                connection.close();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return products;
    }

    public static Product getProduct(String id){
        try{
            Class.forName(dbName);
            Connection connection = DriverManager .getConnection(dbURL);
            if(connection != null){
                String query = "select * from Product WHERE Product.id ='"+id+"'";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                resultSet.next();
                String id01 =resultSet.getString("id");
                String name= resultSet.getString("name");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                String url = resultSet.getString("urlImage");
                connection.close();
                return new Product(id01,name,price,quantity,url);
            }

        }  catch (SQLException e) {
            e.printStackTrace();
         } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public void addProductToDB(Product product){
//        try {
//            Class.forName(dbName);
//            Connection connection = DriverManager.getConnection(dbURL);
//            if(connection != null){
//                String query = "insert into Product (ID,Name,Amount,Price) values " +
//                        "('"+product.getId()+"','"+product.getName()+"',"+product.getAmount()+","+product.getPrice()+")";
//                Statement p = connection.createStatement();
//                p.executeUpdate(query);
//                connection.close();
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    public static void deleteProduct(Product product){
//        try{
//            Class.forName(dbName);
//            Connection connection = DriverManager .getConnection(dbURL);
//            if(connection != null){
//                String query  = "Delete from Product where ID == '" + product.getId() + "'";
//                PreparedStatement p = connection.prepareStatement(query);
//                p.executeUpdate();
//                connection.close();
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    public static void update(Product product){
//        try {
//            Class.forName(dbName);
//            Connection connection = DriverManager .getConnection(dbURL);
//            if(connection != null){
//                String query  = " UPDATE Product SET Name= '"+product.getName()+"',Amount = "+product.getAmount()
//                        +",Price = "+product.getPrice()+" WHERE ID = "+product.getId()+";";
//                PreparedStatement p = connection.prepareStatement(query);
//                p.executeUpdate();
//                connection.close();
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

}
