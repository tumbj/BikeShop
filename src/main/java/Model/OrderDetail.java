package Model;
public class OrderDetail {
    private String OrderID;
    private String tel;
    private int amount;
    private double price;
    private String productID;

    public String getOrderID() {
        return OrderID;
    }

    public String getTel() {
        return tel;
    }
    public int getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public OrderDetail(String productID,String orderID, double price, int amount,String tel ) {
        this.productID = productID;
        this.tel = tel;
        this.amount = amount;
        this.price = price;
        this.OrderID=orderID;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "OrderID='" + OrderID + '\'' +
                ", tel='" + tel + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                ", productID='" + productID + '\'' +
                '}';
    }

    public String getProductID() {

        return productID;
    }
}
