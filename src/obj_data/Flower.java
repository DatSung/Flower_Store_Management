package obj_data;

import java.io.Serializable;

public class Flower implements Serializable {

    private static final long serialVersionUID = -6140363508500781797L;

    private String flowerID;
    private String flowerDescription;
    private String flowerImportDate;
    private double flowerUnitPrice = 0;
    private String flowerCategory;

    public Flower() {

    }

    public Flower(String flowerID, String flowerDescription, String flowerImportDate, double flowerUnitPrice, String flowerCategory) {
        this.flowerID = flowerID;
        this.flowerDescription = flowerDescription;
        this.flowerImportDate = flowerImportDate;
        this.flowerUnitPrice = flowerUnitPrice;
        this.flowerCategory = flowerCategory;
    }


    public String getFlowerID() {
        return flowerID;
    }

    public void setFlowerID(String flowerID) {
        this.flowerID = flowerID;
    }

    public String getFlowerDescription() {
        return flowerDescription;
    }

    public void setFlowerDescription(String flowerDescription) {
        this.flowerDescription = flowerDescription;
    }

    public String getFlowerImportDate() {
        return flowerImportDate;
    }

    public void setFlowerImportDate(String flowerImportDate) {
        this.flowerImportDate = flowerImportDate;
    }

    public double getFlowerUnitPrice() {
        return flowerUnitPrice;
    }

    public void setFlowerUnitPrice(double flowerUnitPrice) {
        this.flowerUnitPrice = flowerUnitPrice;
    }

    public String getFlowerCategory() {
        return flowerCategory;
    }

    public void setFlowerCategory(String flowerCategory) {
        this.flowerCategory = flowerCategory;
    }


    @Override
    public String toString() {
        return String.format("FlowerID: %s | FlowerDescription: %s | FlowerImportDate: %s | FlowerUnitPrice: %f | flowerCategory: %s |", flowerID, flowerDescription, flowerImportDate, flowerUnitPrice, flowerCategory);
    }
}
