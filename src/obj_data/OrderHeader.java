package obj_data;

import java.io.Serializable;

public class OrderHeader implements Serializable {

    private static final long serialVersionUID = -6140363508500781797L;
    private String oderID;
    private String cusName;
    private String orderDate;


    public OrderHeader() {
    }

    public String getOderID() {
        return oderID;
    }

    public void setOderID(String oderID) {
        this.oderID = oderID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }


    @Override
    public String toString() {
        return "OrderHeader{" +
                "oderID='" + oderID + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", cosName='" + cusName + '\'' +
                '}';
    }
}
