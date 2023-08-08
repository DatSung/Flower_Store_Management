package obj_data;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {

    private static final long serialVersionUID = -6140363508500781797L;
    OrderHeader orderHeader = new OrderHeader();
    ArrayList<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
    private double totalCost;

    private int totalQuantity;

    public Order() {
    }

    public OrderHeader getOrderHeader() {
        return orderHeader;
    }

    public void setOrderHeader(OrderHeader orderHeader) {
        this.orderHeader = orderHeader;
    }

    public ArrayList<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(ArrayList<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity() {
        int totalQuantityTMP = 0;
        for (OrderDetail orderDetailTMP : orderDetails) {
            totalQuantityTMP += orderDetailTMP.getFlowerQuantity();
        }
        this.totalQuantity = totalQuantityTMP;
    }

    public void setTotalCost() {
        double totalCostTMP = 0;
        for (OrderDetail orderDetail : orderDetails) {
            totalCostTMP += orderDetail.getFlowerCost();
        }
        this.totalCost = totalCostTMP;
    }

    @Override
    public String toString() {
        return String.format("| %9s | %11s | %21s | %10d | %14.2f $ |", orderHeader.getOderID(), orderHeader.getOrderDate(), orderHeader.getCusName(), totalQuantity, totalCost);
    }
}
