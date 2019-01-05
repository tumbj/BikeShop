package ConnectDatabase;
//panut
import Model.Order;
import Model.OrderDetail;
import Model.Product;

import java.sql.*;
import java.util.ArrayList;

public class OrderDB {
    private static String dbURL = "jdbc:sqlite:Database.db";
    private static String dbName = "org.sqlite.JDBC";

    public  ArrayList<Order> getAllOrder() {
        ArrayList<Order> orders = new ArrayList<>();
        try {
            Class.forName(dbName);
            Connection connection = DriverManager.getConnection(dbURL);
            if (connection != null) {
                String query = "select * from OrderList";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    String id = resultSet.getString("tel_number");
                    String name = resultSet.getString("Order_ID");
                    boolean a = resultSet.getBoolean("Status");
                    orders.add(new Order(id, name, a));
                }
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return orders;
    }
        public  ArrayList<OrderDetail>getOrderList(String orderID){
        ArrayList<OrderDetail>orders=new ArrayList<>();
        try{
            Class.forName(dbName);
            Connection connection = DriverManager .getConnection(dbURL);
            if(connection != null){
                String query = "select * from OrderDetail WHERE OrderDetail.Order_ID='"+orderID+"'";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()){
                    String Pid =resultSet.getString("Product_ID");
                    String Oid=resultSet.getString("Order_ID");
                    double p=resultSet.getDouble("Price");
                    int a=resultSet.getInt("Amount");
                    String tel=resultSet.getString("Tel_number");
                    orders.add(new OrderDetail(Pid,Oid,p,a,tel));
                }
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
            for (OrderDetail order : orders) {
            System.out.println(order);
        }

        return orders;
    }
    public String getOrderByTel(String tel){
        String order_id ="";
        try {
            Class.forName(dbName);
            Connection connection = DriverManager .getConnection(dbURL);

            if (connection != null) {
                String query = "select * from OrderList WHERE OrderList.tel_number='" + tel + "'";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                order_id = resultSet.getString("Order_ID");

                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return order_id;
    }
    public  void updateOrder (String oid){ //แก้เป็นเบอร์โทร
        try {
            Class.forName(dbName);
            Connection connection = DriverManager .getConnection(dbURL);

            if(connection != null){
                String query  = " UPDATE OrderList SET Status= '"+1+"' WHERE = '"+oid+"'";
                PreparedStatement p = connection.prepareStatement(query);
                p.executeUpdate();
                connection.close();
            }
        }  catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createOrder(String date,String cus_tel){
        try {
            Class.forName(dbName);
            Connection connection = DriverManager .getConnection(dbURL);
            if (connection != null) {
                String query = "insert into OrderList (Date,status,total,tel_number) values " +
                        "('" + date+ "','" +"false" + "','" +"100','"+ cus_tel  + "')";
                Statement p = connection.createStatement();
                p.executeUpdate(query);
                connection.close();
            }
        }  catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }




}
