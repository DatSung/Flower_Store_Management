package obj_data;

import sys_data.FlowerSet;

import java.io.Serializable;


public class OrderDetail implements Serializable {

    private static final long serialVersionUID = -6140363508500781797L;

    private String orderDetailID;
    private String flowerID;
    private int flowerQuantity;
    private double flowerCost;


    public OrderDetail() {
    }

    public String getFlowerID() {
        return flowerID;
    }

    public void setFlowerID(String flowerID) {
        this.flowerID = flowerID;
    }

    public int getFlowerQuantity() {
        return flowerQuantity;
    }

    public void setFlowerQuantity(int flowerQuantity) {
        this.flowerQuantity = flowerQuantity;
    }

    public double getFlowerCost() {
        return flowerCost;
    }

    public void setFlowerCost(FlowerSet flowerSet) {
        this.flowerCost = flowerSet.findFlowerByID(this.flowerID).getFlowerUnitPrice() * this.flowerQuantity;
    }

    public String getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(String orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public void setFlowerCost(double flowerCost) {
        this.flowerCost = flowerCost;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderDetailID='" + orderDetailID + '\'' +
                ", flowerID='" + flowerID + '\'' +
                ", flowerQuantity=" + flowerQuantity +
                ", flowerCost=" + flowerCost +
                '}';
    }
}
