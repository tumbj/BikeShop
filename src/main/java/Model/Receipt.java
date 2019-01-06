package Model;

public class Receipt {
    public Receipt(String recpitNo, String orderid) {
        RecpitNo = recpitNo;
        Orderid = orderid;
    }

    private String RecpitNo;
    private String Orderid;

    public String getRecpitNo() {
        return RecpitNo;
    }

    public String getOrderid() {
        return Orderid;
    }

    public void setRecpitNo(String recpitNo) {
        RecpitNo = recpitNo;
    }

    public void setOrderid(String orderid) {
        Orderid = orderid;
    }
}
