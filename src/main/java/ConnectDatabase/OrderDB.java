package ConnectDatabase;
//panut
import Model.Order;
import Model.OrderDetail;
import Model.Product;

import java.sql.*;
import java.util.ArrayList;

public class OrderDB {
    private static String user = "root";
    private static String pass = "";
    private static String dbURL = "jdbc:mysql://localhost/bikeshop";

    public  ArrayList<Order> getAllOrder() {
        ArrayList<Order> orders = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(dbURL, user, pass);
            if (connection != null) {
                String query = "select * from OrderList";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    String id = resultSet.getString("Tel");
                    String name = resultSet.getString("OrderID");
                    boolean a = resultSet.getBoolean("Status");
                    orders.add(new Order(id, name, a));
                }
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
        public  ArrayList<OrderDetail>getOrderList(String orderID){
        ArrayList<OrderDetail>orders=new ArrayList<>();
        try{
            Connection connection = DriverManager.getConnection(dbURL,user,pass);
            if(connection != null){
                String query = "select * from OrderDetail WHERE OrderDetail.OrderID='"+orderID+"'";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()){
                    String Pid =resultSet.getString("ProductID");
                    String Oid=resultSet.getString("OrderID");
                    double p=resultSet.getDouble("Price");
                    int a=resultSet.getInt("Amount");
                    String tel=resultSet.getString("Tel");
                    orders.add(new OrderDetail(Pid,Oid,p,a,tel));
                }
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (OrderDetail order : orders) {
            System.out.println(order);
        }

        return orders;
    }
    public String getOrderByTel(String tel){
        String tel_no ="";
        try {
            Connection connection = DriverManager.getConnection(dbURL, user, pass);

            if (connection != null) {
                String query = "select * from OrderList WHERE OrderList.tel_number='" + tel + "'";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                tel_no= resultSet.getString("Tel_number");

                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tel_no;
    }
    public  void updateOrder (String oid){
        try {
            Connection connection = DriverManager.getConnection(dbURL,user,pass);

            if(connection != null){
                String query  = " UPDATE OrderList SET Status= '"+1+"' WHERE = '"+oid+"'";
                PreparedStatement p = connection.prepareStatement(query);
                p.executeUpdate();
                connection.close();
            }
        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createOrder(String date,String cus_tel){
        try {
            Connection connection = DriverManager.getConnection(dbURL,user,pass);
            if (connection != null) {
                String query = "insert into OrderList (Date,status,total,tel_number) values " +
                        "('" + date+ "','" +"false" + "','" +"100','"+ cus_tel  + "')";
                Statement p = connection.createStatement();
                p.executeUpdate(query);
                connection.close();
            }
        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
