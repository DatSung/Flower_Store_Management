package sys_data;

import action.IFlowerSet;
import action.ISet;
import obj_data.Flower;
import obj_data.Order;
import obj_data.OrderDetail;
import ult.Input;

import java.io.*;
import java.util.*;

public class FlowerSet extends HashSet<Flower> implements IFlowerSet, ISet, Serializable {

    private static final long serialVersionUID = -6140363508500781797L;


    public FlowerSet() {
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
                        if (value instanceof Flower) {
                            this.add((Flower) value);
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

    public void sortByDate(String type) {

    }

    public void sortByTotalCost(String type) {

    }

    public void sortByID(String type) {

    }

    public void sortByName(String type) {

    }


    public boolean addFlower(Flower flower) {

        // Input FlowerID -------------------------------------------------------------------- //
        Input input = new Input();
        while (true) {
            String flowerID = "";
            try {
                while (true) {
                    try {
                        if (flower.getFlowerID() == null) {
                            flowerID = input.getString("Enter flowerID [F****]", "Do not enter empty").toUpperCase().trim();
                            if (!input.checkFlowerID(flowerID)) {
                                throw new Exception();
                            }
                        }
                        break;
                    } catch (Exception e) {
                        System.out.println("Invalid flowerID, please enter again!");
                    }
                }
                for (Flower flowerTMP : this) {
                    if (flowerTMP.getFlowerID().equals(flowerID)) {
                        throw new Exception();
                    }
                }
                if (!flowerID.equals("")) {
                    flower.setFlowerID(flowerID);
                }
                break;
            } catch (Exception e) {
                System.out.println("Duplicated flowerID, please enter again!");
            }
        }

        // Input FlowerDescription -------------------------------------------------------------------- //

        while (true) {
            String flowerDescription;
            try {
                if (flower.getFlowerDescription() == null) {
                    flowerDescription = input.getString("Enter flower description:", "Do not enter empty!").trim();
                    if (!flowerDescription.matches("^\\s*.{3,50}\\s*$")) {
                        throw new Exception();
                    }
                } else {
                    flowerDescription = input.getStringUpdate("Enter new flower description to update, blank to skip:");
                }

                if (!flowerDescription.equals("")) {
                    flower.setFlowerDescription(flowerDescription);
                }
                break;
            } catch (Exception e) {
                System.out.println("Description must be from 3 to 50 characters!");
            }
        }

        // Input FlowerImportDate -------------------------------------------------------------------- //

        while (true) {
            String importDate;
            try {
                if (flower.getFlowerImportDate() == null) {
                    importDate = input.getString("Enter import date [dd/MM/yyyy]:", "Do not enter empty").trim();
                    if (!input.checkDateFormat(importDate)) {
                        throw new Exception();
                    }
                } else {
                    importDate = input.getStringUpdate("Enter new import date [dd/MM/yyyy], blank to skip:");
                }

                if (!importDate.equals("")) {
                    flower.setFlowerImportDate(importDate);
                }
                break;
            } catch (Exception e) {
                System.out.println("Invalid import date!");
            }
        }

        // Input FlowerUnitPrice -------------------------------------------------------------------- //

        while (true) {
            double flowerUnitPrice = 0;
            try {
                if (flower.getFlowerUnitPrice() == 0) {
                    flowerUnitPrice = input.getDouble("Enter flower unit price:", "Price must be a number!");
                } else {
                    flowerUnitPrice = input.getDouble("Enter new flower unit price, enter 0 to skip:", "Price must be a number!");
                }

                if (!input.checkPrice(flowerUnitPrice)) {
                    throw new Exception();
                }
                if (flowerUnitPrice != 0) {
                    flower.setFlowerUnitPrice(flowerUnitPrice);
                }
                break;
            } catch (Exception e) {
                System.out.println("Price must be a positive number!");
            }
        }

        // Input FlowerCategory -------------------------------------------------------------------- //

        while (true) {
            String flowerCategory;
            try {
                if (flower.getFlowerCategory() == null) {
                    flowerCategory = input.getString("Enter flower category:", "Do not enter empty!").trim();
                } else {
                    flowerCategory = input.getStringUpdate("Enter new flower category, blank to skip:");
                }
                if (!flowerCategory.equals("")) {
                    flower.setFlowerCategory(flowerCategory);
                }
                break;
            } catch (Exception e) {
            }
        }

        // Add to the FlowerSet --------------------------------------------------------------------- //

        if (this.add(flower)) {
            return true;
        } else {
            return false;
        }
    }

    public Flower findFlowerByID(String flowerID) {
        for (Flower flower : this) {
            if (flower.getFlowerID().equals(flowerID)) {
                return flower;
            }
        }
        return null;
    }

    public ArrayList<Flower> findFlowerByName(String flowerName) {
        ArrayList<Flower> flowers = new ArrayList<Flower>();
        for (Flower flower : this) {
            if (flower.getFlowerDescription().toUpperCase().contains(flowerName.toUpperCase())) {
                flowers.add(flower);
            }
        }
        return flowers;
    }

    public boolean checkFlowerInOrder(String flowerID, HashSet<Order> orderSet) {
        for (Order orderTMP : orderSet) {
            for (OrderDetail orderDetailTMP : orderTMP.getOrderDetails()) {
                if (flowerID.equalsIgnoreCase(orderDetailTMP.getFlowerID())) {
                    return false;
                }
            }
        }
        return true;
    }


    public void displayAllFlower() {
        for (Flower flower : this) {
            System.out.println(flower.toString());
        }
    }

}
