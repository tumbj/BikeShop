package Model;

public class Order {
    private String CustomerID;
    private String CustomerName;
    private String OrderID;
    private boolean Status;

    public Order(String customerID, String orderID, boolean status) {
        CustomerID = customerID;
        OrderID = orderID;
        Status = status;

    }
    public Order(String customerID,String customerName, String orderID, boolean status) {
        CustomerID = customerID;
        OrderID = orderID;
        Status = status;
        CustomerName=customerName;

    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public String getOrderID() {
        return OrderID;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public void setStatus(boolean status) {
        Status = status;
    }
}
