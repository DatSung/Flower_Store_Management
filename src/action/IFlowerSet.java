package action;

import obj_data.Flower;
import obj_data.Order;

import java.util.ArrayList;
import java.util.HashSet;

public interface IFlowerSet {
    public boolean addFlower(Flower flower);

    public Flower findFlowerByID(String flowerID);

    public ArrayList<Flower> findFlowerByName(String flowerName);

    public boolean checkFlowerInOrder(String flowerID, HashSet<Order> orderSet);

    public void displayAllFlower();
}
