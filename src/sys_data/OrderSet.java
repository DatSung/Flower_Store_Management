package sys_data;

import action.IOrderSet;
import action.ISet;
import com.sun.org.apache.xpath.internal.operations.Or;
import obj_data.Flower;
import obj_data.Order;
import obj_data.OrderDetail;
import obj_data.OrderHeader;
import ult.Input;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class OrderSet extends HashSet<Order> implements IOrderSet, ISet, Serializable {

    private static final long serialVersionUID = -6140363508500781797L;

    FlowerSet flowerSet = new FlowerSet();

    private double totalOrderSetCost;
    private int totalOrderSetQuantity;


    public OrderSet(FlowerSet flowerSet) {
        this.flowerSet = flowerSet;
    }

    public double getTotalOrderSetCost() {
        return totalOrderSetCost;
    }

    public void setTotalOrderSetCost() {
        double totalOrderSetCostTMP = 0;
        for (Order orderTMP : this) {
            totalOrderSetCostTMP += orderTMP.getTotalCost();
        }
        this.totalOrderSetCost = totalOrderSetCostTMP;
    }

    public int getTotalOrderSetQuantity() {
        return totalOrderSetQuantity;
    }

    public void setTotalOrderSetQuantity() {
        int totalOrderSetQuantityTMP = 0;
        for (Order orderTMP : this) {
            totalOrderSetQuantityTMP += orderTMP.getTotalQuantity();
        }
        this.totalOrderSetQuantity = totalOrderSetQuantityTMP;
    }

    public boolean saveFile(File fileName) {

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(fileName);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public boolean readFile(File fileName) {

        FileInputStream fis = null;
        ObjectInputStream ois = null;

        if (!fileName.exists()) {
            try {
                fileName.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        try {
            fis = new FileInputStream(fileName);

            if (fis.available() > 0) {
                ois = new ObjectInputStream(fis);
                Object obj = ois.readObject();
                if (obj instanceof HashSet<?>) {
                    HashSet<?> set = (HashSet<?>) obj;
                    set.forEach((value) -> {
                        if (value instanceof Order) {
                            this.add((Order) value);
                        }
                    });
                }
                return true;
            } else {
                return false;
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ois != null) ois.close();
                if (fis != null) fis.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public boolean addOrder(Order order) {

        if (!flowerSet.isEmpty()) {
            Input input = new Input();
            ArrayList<OrderDetail> orderDetailArrayList = new ArrayList<OrderDetail>();
            OrderHeader orderHeader = new OrderHeader();
            String subChoiceYN = "N";

            // Enter orderID ---------------------------------------------------------------------------------------------//

            while (true) {
                String orderID = "";
                try {
                    while (true) {
                        try {
                            if (orderHeader.getOderID() == null) {
                                orderID = input.getString("Enter orderID [O****]", "Do not enter empty").toUpperCase().trim();
                                if (!input.checkOrderID(orderID)) {
                                    throw new Exception();
                                }
                            }
                            break;
                        } catch (Exception e) {
                            System.out.println("Invalid orderID, please enter again!");
                        }
                    }
                    for (Order orderTMP : this) {
                        if (orderTMP.getOrderHeader().getOderID().equals(orderID)) {
                            throw new Exception();
                        }
                    }
                    if (!orderID.equals("")) {
                        orderHeader.setOderID(orderID);
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Duplicated orderID, please enter again!");
                }
            }

            // Enter orderName -------------------------------------------------------------------------------------------//

            while (true) {
                try {
                    String cusName = input.getString("Enter costumer's name:", "Do not enter empty!").trim();
                    if (cusName.matches(".*[0-9].*")) {
                        throw new Exception();
                    }
                    orderHeader.setCusName(cusName);
                    break;
                } catch (Exception e) {
                    System.out.println("The name do not contains a number!");
                }
            }

            // Enter orderDate -------------------------------------------------------------------------------------------//

            while (true) {
                String orderDate;
                try {
                    if (orderHeader.getOrderDate() == null) {
                        orderDate = input.getString("Enter order date [dd/MM/yyyy]:", "Do not enter empty").trim();
                        if (!input.checkDateFormat(orderDate)) {
                            throw new Exception();
                        }
                    } else {
                        orderDate = input.getStringUpdate("Enter new order date [dd/MM/yyyy], blank to skip:");
                    }

                    if (!orderDate.equals("")) {
                        orderHeader.setOrderDate(orderDate);
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid order date!");
                }
            }

            do {

                OrderDetail orderDetail = new OrderDetail();

                //  Enter order detail id to buy ---------------------------------------------------------------------//

                while (true) {
                    try {
                        String orderDetailID = input.getString("Enter orderDetailID [#****]", " Do not enter empty").trim();
                        if (!input.checkOrderDetailID(orderDetailID)) {
                            System.out.println("Invalid orderDetailID!");
                            throw new Exception();
                        }
                        for (OrderDetail orderDetailTMP : orderDetailArrayList) {
                            if (orderDetailTMP.getOrderDetailID().equalsIgnoreCase(orderDetailID)) {
                                System.out.println("Duplicated orderDetailID!");
                                throw new Exception();
                            }
                        }
                        orderDetail.setOrderDetailID(orderDetailID);
                        break;
                    } catch (Exception e) {
                    }
                }

                // Enter flower id to buy ----------------------------------------------------------------------------//

                flowerSet.displayAllFlower();
                String flowerFindingKeyWord;
                flowerFindingKeyWord = input.getString("Enter flower name or flower id to buy:", "Do not enter empty").trim();
                if (flowerSet.findFlowerByID(flowerFindingKeyWord) == null) {
                    for (Flower flower : flowerSet.findFlowerByName(flowerFindingKeyWord)) {
                        System.out.println(flower.toString());
                    }
                    while (true) {
                        try {
                            flowerFindingKeyWord = input.getString("Enter a flower id to choose:", "Do not enter empty").toUpperCase().trim();
                            if (!input.checkFlowerID(flowerFindingKeyWord)) {
                                throw new Exception();
                            }
                            orderDetail.setFlowerID(flowerFindingKeyWord);
                            break;
                        } catch (Exception e) {
                            System.out.println("Invalid staffID!");
                        }
                    }

                } else {
                    orderDetail.setFlowerID(flowerFindingKeyWord);
                }


                while (true) {
                    try {
                        int flowerQuantity;
                        flowerQuantity = input.getInt("Enter quantity of this flower:", "Do not enter empty");
                        if (flowerQuantity > 0) {
                            orderDetail.setFlowerQuantity(flowerQuantity);
                        } else {
                            throw new Exception();
                        }
                        break;
                    } catch (Exception e) {
                        System.out.println("The quantity must be at least 1 Pcs!");
                    }
                }

                if (!flowerSet.isEmpty()) {
                    orderDetail.setFlowerCost(flowerSet);
                }


                orderDetailArrayList.add(orderDetail);

                subChoiceYN = input.getString("Do you want to buy more flower [Y/N]:", "Invalid option!").toUpperCase().trim();


            } while (subChoiceYN.equalsIgnoreCase("Y"));

            order.setOrderHeader(orderHeader);
            order.setOrderDetails(orderDetailArrayList);
            order.setTotalCost();
            order.setTotalQuantity();
            setTotalOrderSetQuantity();
            setTotalOrderSetCost();

            if (this.add(order)) {
                System.out.println("Create order successfully!");
                return true;
            } else {
                System.out.println("Create order fail!");
                return false;
            }
        } else {
            System.out.println("There are no flower in database!");
            return false;
        }
    }

    public void displayByDate(String startDate, String endDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            Date start = format.parse(startDate);
            Date end = format.parse(endDate);

            if (this.isEmpty()) {
                System.out.println("There are no Patients!");
                return;
            }

            System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
            System.out.println("|  OrderID  |  OrderDate  |     Customer Name     |  Quantity  |    Total Cost    |");
            for (Order orderTMP : this) {
                Date orderDate = format.parse(orderTMP.getOrderHeader().getOrderDate());
                if ((orderDate.after(start) || orderDate.equals(start)) && (orderDate.before(end) || orderDate.equals(end))) {
                    System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
                    System.out.println(orderTMP.toString());
                }
            }
            System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
            setTotalOrderSetQuantity();
            setTotalOrderSetCost();
            System.out.printf("|   Total   |-------------|-----------------------| %10d | %14.2f $ |\n", getTotalOrderSetQuantity(), getTotalOrderSetCost());
            System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
        } catch (Exception e) {
            System.out.println("Invalid date input!");
        }
    }

    @Override
    public void displayAll() {

    }

    public void sortByDate(String type) {
        if (this.isEmpty()) {
            System.out.println("There are no Orders!");
            return;
        }
        if (type.equalsIgnoreCase("ASC")) {

            List<Order> listByName = new ArrayList<>(this);

            Collections.sort(listByName, new Comparator<Order>() {
                public int compare(Order o1, Order o2) {
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date start = format.parse(o1.getOrderHeader().getOrderDate());
                        Date end = format.parse(o2.getOrderHeader().getOrderDate());
                        return start.compareTo(end);
                    } catch (Exception e) {
                        System.out.println("ERROR");
                    }
                    return 0;
                }
            });

            System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
            System.out.println("|  OrderID  |  OrderDate  |     Customer Name     |  Quantity  |    Total Cost    |");
            for (Order orderTMP : listByName) {
                System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
                System.out.println(orderTMP.toString());
            }
            System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
            setTotalOrderSetQuantity();
            setTotalOrderSetCost();
            System.out.printf("|   Total   |-------------|-----------------------| %10d | %14.2f $ |\n", getTotalOrderSetQuantity(), getTotalOrderSetCost());
            System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
        } else if (type.equalsIgnoreCase("DESC")) {

            List<Order> listByName = new ArrayList<>(this);

            Collections.sort(listByName, new Comparator<Order>() {
                public int compare(Order o1, Order o2) {
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date start = format.parse(o1.getOrderHeader().getOrderDate());
                        Date end = format.parse(o2.getOrderHeader().getOrderDate());
                        return end.compareTo(start);
                    } catch (Exception e) {
                        System.out.println("ERROR");
                    }
                    return 0;
                }
            });

            System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
            System.out.println("|  OrderID  |  OrderDate  |     Customer Name     |  Quantity  |    Total Cost    |");
            for (Order orderTMP : listByName) {
                System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
                System.out.println(orderTMP.toString());
            }
            System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
            setTotalOrderSetQuantity();
            setTotalOrderSetCost();
            System.out.printf("|   Total   |-------------|-----------------------| %10d | %14.2f $ |\n", getTotalOrderSetQuantity(), getTotalOrderSetCost());
            System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
        }
    }

    public void sortByTotalCost(String type) {
        if (this.isEmpty()) {
            System.out.println("There are no Orders!");
            return;
        }
        if (type.equalsIgnoreCase("ASC")) {

            List<Order> listByName = new ArrayList<>(this);

            Collections.sort(listByName, new Comparator<Order>() {
                public int compare(Order o1, Order o2) {
                    try {
                        return Double.compare(o1.getTotalCost(), o2.getTotalCost());
                    } catch (Exception e) {
                        System.out.println("ERROR");
                    }
                    return 0;
                }
            });
            System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
            System.out.println("|  OrderID  |  OrderDate  |     Customer Name     |  Quantity  |    Total Cost    |");
            for (Order orderTMP : listByName) {
                System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
                System.out.println(orderTMP.toString());
            }
            System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
            setTotalOrderSetQuantity();
            setTotalOrderSetCost();
            System.out.printf("|   Total   |-------------|-----------------------| %10d | %14.2f $ |\n", getTotalOrderSetQuantity(), getTotalOrderSetCost());
            System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
        } else if (type.equalsIgnoreCase("DESC")) {

            List<Order> listByName = new ArrayList<>(this);

            Collections.sort(listByName, new Comparator<Order>() {
                public int compare(Order o1, Order o2) {
                    try {
                        return Double.compare(o2.getTotalCost(), o1.getTotalCost());
                    } catch (Exception e) {
                        System.out.println("ERROR");
                    }
                    return 0;
                }
            });
            System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
            System.out.println("|  OrderID  |  OrderDate  |     Customer Name     |  Quantity  |    Total Cost    |");
            for (Order orderTMP : listByName) {
                System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
                System.out.println(orderTMP.toString());
            }
            System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
            setTotalOrderSetQuantity();
            setTotalOrderSetCost();
            System.out.printf("|   Total   |-------------|-----------------------| %10d | %14.2f $ |\n", getTotalOrderSetQuantity(), getTotalOrderSetCost());
            System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
        }
    }

    public void sortByID(String type) {
        if (this.isEmpty()) {
            System.out.println("There are no Orders!");
            return;
        }
        if (type.equalsIgnoreCase("ASC")) {

            List<Order> listByName = new ArrayList<>(this);

            Collections.sort(listByName, new Comparator<Order>() {
                public int compare(Order o1, Order o2) {
                    try {
                        return o1.getOrderHeader().getOderID().compareTo(o2.getOrderHeader().getOderID());
                    } catch (Exception e) {
                        System.out.println("ERROR");
                    }
                    return 0;
                }
            });
            System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
            System.out.println("|  OrderID  |  OrderDate  |     Customer Name     |  Quantity  |    Total Cost    |");
            for (Order orderTMP : listByName) {
                System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
                System.out.println(orderTMP.toString());
            }
            System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
            setTotalOrderSetQuantity();
            setTotalOrderSetCost();
            System.out.printf("|   Total   |-------------|-----------------------| %10d | %14.2f $ |\n", getTotalOrderSetQuantity(), getTotalOrderSetCost());
            System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
        } else if (type.equalsIgnoreCase("DESC")) {

            List<Order> listByName = new ArrayList<>(this);

            Collections.sort(listByName, new Comparator<Order>() {
                public int compare(Order o1, Order o2) {
                    try {
                        return o2.getOrderHeader().getOderID().compareTo(o1.getOrderHeader().getOderID());
                    } catch (Exception e) {
                        System.out.println("ERROR");
                    }
                    return 0;
                }
            });
            System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
            System.out.println("|  OrderID  |  OrderDate  |     Customer Name     |  Quantity  |    Total Cost    |");
            for (Order orderTMP : listByName) {
                System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
                System.out.println(orderTMP.toString());
            }
            System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
            setTotalOrderSetQuantity();
            setTotalOrderSetCost();
            System.out.printf("|   Total   |-------------|-----------------------| %10d | %14.2f $ |\n", getTotalOrderSetQuantity(), getTotalOrderSetCost());
            System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
        }
    }

    public void sortByName(String type) {
        if (this.isEmpty()) {
            System.out.println("There are no Orders!");
            return;
        }
        if (type.equalsIgnoreCase("ASC")) {

            List<Order> listByName = new ArrayList<>(this);

            Collections.sort(listByName, new Comparator<Order>() {
                public int compare(Order o1, Order o2) {
                    try {
                        return o1.getOrderHeader().getCusName().compareToIgnoreCase(o2.getOrderHeader().getCusName());
                    } catch (Exception e) {
                        System.out.println("ERROR");
                    }
                    return 0;
                }
            });
            System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
            System.out.println("|  OrderID  |  OrderDate  |     Customer Name     |  Quantity  |    Total Cost    |");
            for (Order orderTMP : listByName) {
                System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
                System.out.println(orderTMP.toString());
            }
            System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
            setTotalOrderSetQuantity();
            setTotalOrderSetCost();
            System.out.printf("|   Total   |-------------|-----------------------| %10d | %14.2f $ |\n", getTotalOrderSetQuantity(), getTotalOrderSetCost());
            System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
        } else if (type.equalsIgnoreCase("DESC")) {

            List<Order> listByName = new ArrayList<>(this);

            Collections.sort(listByName, new Comparator<Order>() {
                public int compare(Order o1, Order o2) {
                    try {
                        return o2.getOrderHeader().getCusName().compareToIgnoreCase(o1.getOrderHeader().getCusName());
                    } catch (Exception e) {
                        System.out.println("ERROR");
                    }
                    return 0;
                }
            });
            System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
            System.out.println("|  OrderID  |  OrderDate  |     Customer Name     |  Quantity  |    Total Cost    |");
            for (Order orderTMP : listByName) {
                System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
                System.out.println(orderTMP.toString());
            }
            System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
            setTotalOrderSetQuantity();
            setTotalOrderSetCost();
            System.out.printf("|   Total   |-------------|-----------------------| %10d | %14.2f $ |\n", getTotalOrderSetQuantity(), getTotalOrderSetCost());
            System.out.println("+-----------+-------------+-----------------------+------------+------------------+");
        }
    }
}
