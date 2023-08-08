package main;

import obj_data.Flower;
import obj_data.Order;
import sys_data.FlowerSet;
import sys_data.OrderSet;
import ult.Input;
import ult.Menu;

import java.io.File;

public class Store {
    public static void main(String[] args) {
        Input input = new Input();
        //Init menu
        Menu storeMenu = new Menu();
        storeMenu.addOption("1.Manage flower");
        storeMenu.addOption("2.Manage Order");
        storeMenu.addOption("0.Exit");

        Menu flowerMenu = new Menu();
        flowerMenu.addOption("1.Add a flower");
        flowerMenu.addOption("2.Find a flower");
        flowerMenu.addOption("3.Update a flower");
        flowerMenu.addOption("4.Delete a flower");
        flowerMenu.addOption("0.Back");

        Menu orderMenu = new Menu();
        orderMenu.addOption("1.Add an order");
        orderMenu.addOption("2.Display orders");
        orderMenu.addOption("3.Sort orders");
        orderMenu.addOption("4.Save data");
        orderMenu.addOption("5.Load data");
        orderMenu.addOption("0.Back");

        int mainKey;
        int subKey;

        FlowerSet flowerSet = new FlowerSet();
        OrderSet orderSet = new OrderSet(flowerSet);
        File fs = new File("data/flowers.dat");
        File os = new File("data/orders.dat");

        //Main program
        do {


            storeMenu.showOption("---[Welcome to Flower Store Management]---");
            mainKey = storeMenu.getOption("Enter your option:", "Please enter a number!");
            switch (mainKey) {


                // Flower management ---------------------------------------------------------------------------------//


                case 1: {

                    do {

                        flowerMenu.showOption("---[Welcome to Flower's Management]---");
                        subKey = flowerMenu.getOption("Enter your option:", "Please enter a number!");

                        switch (subKey) {

                            // Add new flower ------------------------------------------------------------------------//

                            case 1: {

                                if (flowerSet.isEmpty()) {
                                    if (!flowerSet.readFile(fs)) {
                                        System.out.println("No data to load FlowerFile!");
                                    } else {
                                        System.out.println("[Load Flower's data atomatic!]");
                                    }
                                }
                                if (orderSet.isEmpty()) {
                                    if (!orderSet.readFile(os)) {
                                        System.out.println("No data to load OrderFile!");
                                    } else {
                                        System.out.println("[Load Order's data automatic!]");
                                    }
                                }

                                String choiceYN = "N";
                                do {
                                    Flower flower = new Flower();
                                    if (flowerSet.addFlower(flower)) {
                                        System.out.println("Add new flower successfully!");
                                    } else {
                                        System.out.println("Add new flower fail!");
                                    }
                                    choiceYN = input.getString("Do you want to creat more [Y/N]:", "Invalid option!").toUpperCase();

                                } while (choiceYN.equals("Y"));
                                break;
                            }

                            // Find a flower -------------------------------------------------------------------------//

                            case 2: {

                                if (orderSet.isEmpty() || flowerSet.isEmpty()) {
                                    System.out.println("Data must be load first!");
                                    break;
                                }

                                String choiceYN = "N";
                                do {
                                    String flowerFindingKeyWord;
                                    flowerFindingKeyWord = input.getString("Enter flower name or flower id to find:", "Do not enter empty").toUpperCase().trim();
                                    if (flowerSet.findFlowerByID(flowerFindingKeyWord) == null) {
                                        if (flowerSet.findFlowerByName(flowerFindingKeyWord) == null) {
                                            System.out.println("The flower does not exist!");
                                        } else {
                                            for (Flower flower : flowerSet.findFlowerByName(flowerFindingKeyWord)) {
                                                System.out.println(flower.toString());
                                            }
                                        }
                                    } else {
                                        System.out.println(flowerSet.findFlowerByID(flowerFindingKeyWord).toString());
                                    }
                                    choiceYN = input.getString("Do you want to find again [Y/N]:", "Invalid option!").toUpperCase().trim();
                                } while (choiceYN.equals("Y"));
                                break;
                            }

                            // Update a flower -----------------------------------------------------------------------//

                            case 3: {
                                while (true) {
                                    if (flowerSet.isEmpty()) {
                                        System.out.println("There are no Flower's data loaded!");
                                        break;
                                    }
                                    try {
                                        String choiceYN = "Y";
                                        String flowerFindingKeyWord;
                                        do {
                                            flowerSet.displayAllFlower();
                                            flowerFindingKeyWord = input.getString("Enter flower name or flower id to update:", "Do not enter empty").toUpperCase().trim();
                                            if (flowerSet.findFlowerByID(flowerFindingKeyWord) == null) {
                                                if (flowerSet.findFlowerByName(flowerFindingKeyWord).isEmpty()) {
                                                    throw new Exception();
                                                } else {
                                                    for (Flower flower : flowerSet.findFlowerByName(flowerFindingKeyWord)) {
                                                        System.out.println(flower.toString());
                                                    }
                                                    while (true) {
                                                        try {
                                                            flowerFindingKeyWord = input.getString("Enter a flower id to choose:", "Do not enter empty").toUpperCase().trim();
                                                            if (!input.checkFlowerID(flowerFindingKeyWord)) {
                                                                throw new Exception();
                                                            }
                                                            flowerSet.addFlower(flowerSet.findFlowerByID(flowerFindingKeyWord));
                                                            break;
                                                        } catch (Exception e) {
                                                            System.out.println("Invalid staffID!");
                                                        }
                                                    }
                                                }
                                            } else {
                                                System.out.println(flowerSet.findFlowerByID(flowerFindingKeyWord).toString());
                                                flowerSet.addFlower(flowerSet.findFlowerByID(flowerFindingKeyWord));
                                            }
                                            choiceYN = input.getString("Do you want to update new Flower [Y/N]:", "Invalid option").toUpperCase();
                                        } while (choiceYN.equals("Y"));
                                        break;
                                    } catch (Exception e) {
                                        System.out.println("“The Flower does not exist!");
                                        String choiceYN = input.getString("Do you want to find again [Y/N]:", "Invalid option!").toUpperCase();
                                        if (!choiceYN.equals("Y")) {
                                            break;
                                        }
                                    }
                                }
                                break;
                            }

                            // Remove a flower -------------------------------------------------------------------------//

                            case 4: {

                                if (orderSet.isEmpty() || flowerSet.isEmpty()) {
                                    System.out.println("Data must be load first!");
                                    break;
                                }

                                while (true) {
                                    try {
                                        String flowerID = input.getString("Enter flowerID to remove:", "Do not enter empty!").toUpperCase().trim();
                                        if (!input.checkFlowerID(flowerID)) {
                                            System.out.println("Invalid flowerID!");
                                            throw new Exception();
                                        }
                                        if (flowerSet.findFlowerByID(flowerID) == null) {
                                            System.out.println("Flower does not exist!");
                                            throw new Exception();
                                        }
                                        if (flowerSet.checkFlowerInOrder(flowerID, orderSet)) {
                                            if (flowerSet.remove(flowerSet.findFlowerByID(flowerID))) {
                                                System.out.println("The flower removed successfully!");
                                                throw new Exception();
                                            }
                                        } else {
                                            System.out.println("The flower already in orderSet, cancel remove!");
                                            throw new Exception();
                                        }
                                    } catch (Exception e) {
                                        String choiceYN = input.getString("Do you want to continue [Y/N]", "Invalid option!");
                                        if (choiceYN.equalsIgnoreCase("N")) {
                                            break;
                                        }
                                    }
                                }
                                break;
                            }
                        }
                    } while (subKey != 0);
                    break;
                }


                // Order management ----------------------------------------------------------------------------------//


                case 2: {

                    do {

                        orderMenu.showOption("Well come to Order's Management ");
                        subKey = orderMenu.getOption("Enter your option:", "Please enter a number!");

                        switch (subKey) {

                            // Add an order --------------------------------------------------------------------------//

                            case 1: {

                                if (flowerSet.isEmpty()) {
                                    if (!flowerSet.readFile(fs)) {
                                        System.out.println("No data to load FlowerFile!");
                                    } else {
                                        System.out.println("[Load Flower's data atomatic!]");
                                    }
                                }
                                if (orderSet.isEmpty()) {
                                    if (!orderSet.readFile(os)) {
                                        System.out.println("No data to load OrderFile!");
                                    } else {
                                        System.out.println("[Load Order's data automatic!]");
                                    }
                                }

                                String choiceYN = "N";
                                do {
                                    Order order = new Order();
                                    if (orderSet.addOrder(order)) {
                                        System.out.println("Add new order successfully!");
                                    } else {
                                        System.out.println("Add new order fail!");
                                    }
                                    choiceYN = input.getString("Do you want to create more order [Y/N]:", "Invalid option!").toUpperCase();

                                } while (choiceYN.equals("Y"));
                                break;
                            }

                            // Display orders -------------------------------------------------------------------------//

                            case 2: {

                                if (orderSet.isEmpty() || flowerSet.isEmpty()) {
                                    System.out.println("Data must be load first!");
                                    break;
                                }

                                while (true) {
                                    try {
                                        String startDate = input.getString("Enter startDate:", "Do not enter empty!");
                                        if (!input.checkDateFormat(startDate)) {
                                            System.out.println("Invalid startDate!");
                                            throw new Exception();
                                        }
                                        String endDate = input.getString("Enter endDate:", "Do not enter empty!");
                                        if (!input.checkDateFormat(endDate)) {
                                            System.out.println("Invalid endDate!");
                                            throw new Exception();
                                        }
                                        orderSet.displayByDate(startDate, endDate);
                                        break;
                                    } catch (Exception e) {
                                        String choiceYN = input.getString("Do you want to continue [Y/N]", "Invalid option!").toUpperCase();
                                        if (choiceYN.equalsIgnoreCase("N")) {
                                            break;
                                        }
                                    }
                                }
                                break;
                            }

                            // Sort orders ---------------------------------------------------------------------------//

                            case 3: {
                                while (true) {

                                    if (orderSet.isEmpty()) {
                                        System.out.println("There are no Order's data loaded!");
                                        break;
                                    }

                                    try {
                                        String typeSort;
                                        String sortChoice = input.getString("Which ones do you want to sort by [Name/Id/Date/Cost]:", "Do not enter empty!");
                                        if (sortChoice.equalsIgnoreCase("Name")) {
                                            typeSort = input.getString("Enter your type of sort [ASC/DESC]: ", "Do not enter empty!");
                                            if (!typeSort.equalsIgnoreCase("ASC") && !typeSort.equalsIgnoreCase("DESC")) {
                                                throw new Exception();
                                            }
                                            orderSet.sortByName(typeSort);
                                        } else if (sortChoice.equalsIgnoreCase("ID")) {
                                            typeSort = input.getString("Enter your type of sort [ASC/DESC]: ", "Do not enter empty!");
                                            if (!typeSort.equalsIgnoreCase("ASC") && !typeSort.equalsIgnoreCase("DESC")) {
                                                throw new Exception();
                                            }
                                            orderSet.sortByID(typeSort);
                                        } else if (sortChoice.equalsIgnoreCase("Date")) {
                                            typeSort = input.getString("Enter your type of sort [ASC/DESC]: ", "Do not enter empty!");
                                            if (!typeSort.equalsIgnoreCase("ASC") && !typeSort.equalsIgnoreCase("DESC")) {
                                                throw new Exception();
                                            }
                                            orderSet.sortByDate(typeSort);
                                        } else if (sortChoice.equalsIgnoreCase("Cost")) {
                                            typeSort = input.getString("Enter your type of sort [ASC/DESC]: ", "Do not enter empty!");
                                            if (!typeSort.equalsIgnoreCase("ASC") && !typeSort.equalsIgnoreCase("DESC")) {
                                                throw new Exception();
                                            }
                                            orderSet.sortByTotalCost(typeSort);
                                        } else {
                                            throw new Exception();
                                        }
                                        break;
                                    } catch (Exception e) {
                                        System.out.println("Invalid option!");
                                    }
                                }
                                break;
                            }

                            // Save file  ----------------------------------------------------------------------------//

                            case 4: {
                                if (!flowerSet.isEmpty()) {
                                    if (!flowerSet.saveFile(fs)) {
                                        System.out.println("Fail to save FlowerFile!");
                                    } else {
                                        System.out.println("Save FlowerFile Successfully!");
                                    }
                                } else {
                                    System.out.println("There are no Flower's data to save!");
                                }
                                if (!orderSet.isEmpty()) {
                                    if (!orderSet.saveFile(os)) {
                                        System.out.println("Fail to save OrderFile!");
                                    } else {
                                        System.out.println("Save OrderFile Successfully!");
                                    }
                                } else {
                                    System.out.println("There are no Order's data to save!");
                                }
                                break;
                            }

                            // Load file -----------------------------------------------------------------------------//

                            case 5: {
                                if (flowerSet.isEmpty()) {
                                    if (!flowerSet.readFile(fs)) {
                                        System.out.println("No data to load FlowerFile!");
                                    } else {
                                        System.out.println("[Load Flower's data Successfully!]");
                                    }
                                }
                                if (orderSet.isEmpty()) {
                                    if (!orderSet.readFile(os)) {
                                        System.out.println("No data to load OrderFile!");
                                    } else {
                                        System.out.println("[Load Order's data Successfully!]");
                                    }
                                }
                                break;
                            }
                        }
                    } while (subKey != 0);
                    break;
                }

                // Exit ----------------------------------------------------------------------------------------------//

                case 0: {
                    String choiceYN = input.getString("Do you want to continue [Y/N]", "Invalid option!").toUpperCase();
                    if (choiceYN.equalsIgnoreCase("Y")) {
                        if (!flowerSet.isEmpty()) {
                            if (!flowerSet.saveFile(fs)) {
                                System.out.println("Fail to save FlowerFile!");
                            } else {
                                System.out.println("Save FlowerFile Successfully!");
                            }
                        } else {
                            System.out.println("There are no Flower's data to save!");
                        }
                        if (!orderSet.isEmpty()) {
                            if (!orderSet.saveFile(os)) {
                                System.out.println("Fail to save OrderFile!");
                            } else {
                                System.out.println("Save OrderFile Successfully!");
                            }
                        } else {
                            System.out.println("There are no Order's data to save!");
                        }
                        System.out.println("---[System exist]---");
                    } else {
                        mainKey = storeMenu.size() + 1;
                    }
                }
            }
        } while (mainKey != 0);
    }
}