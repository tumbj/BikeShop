package Model;
public class OrderDetail {
    private String OrderID;
    private String tel;
    private int amount;
    private double price;
    private String productID;
    private String prices;
    private String amounts;
    private String ProductName;
    private String CustomerName;

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

    public String getPrices() {
        return prices;
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
    public OrderDetail(String productID,String orderID, String price, String amount,String tel ) {
        this.productID = productID;
        this.tel = tel;
        this.amounts = amount;
        this.prices = price;
        this.OrderID=orderID;
    }
    public OrderDetail(String productID,String productName,String c,String tel ,String price,String amount ) {
        this.productID = productID;
        this.tel = tel;
        this.amounts = amount;
        this.prices = price;
        this.ProductName=productName;
        this.CustomerName=c;
    }


    public String getAmounts() {
        return amounts;
    }

    public String getProductName() {
        return ProductName;
    }

    public String getCustomerName() {
        return CustomerName;
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
