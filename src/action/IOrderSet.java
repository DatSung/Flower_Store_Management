package action;

import obj_data.Order;

public interface IOrderSet {
    public boolean addOrder(Order order);

    public void displayByDate(String startDate, String endDate);

    public void displayAll();
}
