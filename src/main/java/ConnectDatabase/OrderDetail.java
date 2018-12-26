package ConnectDatabase;

import Model.Product;

import java.sql.*;
import java.util.ArrayList;

public class OrderDetail {
    private static String user = "root";
    private static String pass = "";
    private static String dbURL = "jdbc:mysql://localhost/bikeshop";


    public static ArrayList<Product> getAllProduct() {
        ArrayList<Product> products = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(dbURL, user, pass);
            if (connection != null) {
                String query = "select * from order";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String name = resultSet.getString("name");
                    double price = resultSet.getDouble("price");
                    int quantity = resultSet.getInt("quantity");
                    products.add(new Product(id, name, price, quantity));
                }
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public static Product getProduct(int id) {
        try {
            Connection connection = DriverManager.getConnection(dbURL, user, pass);
            if (connection != null) {
                String query = "select * from Product WHERE Product.id ='" + id + "'";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                resultSet.next();
                int id01 = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                connection.close();
                return new Product(id01 + "", name, price, quantity);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createOrderDetail(Product product,String order_id,String cus_tel) {
        try {
            Connection connection = DriverManager.getConnection(dbURL,user,pass);
            if (connection != null) {
                String query = "insert into OrderDetail (Order_ID,product_ID,Tel,Quantity,Price) values " +
                        "('" + order_id+ "','" +product.getId() + "','" + cus_tel + "'," + product.getQuantity() + "," + product.getPrice() + ")";
                Statement p = connection.createStatement();
                p.executeUpdate(query);
                connection.close();
            }
        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
