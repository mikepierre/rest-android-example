package model;

/**
 * Created by michaelpierre on 10/26/15.
 */
public class Flower {
    private int productId;
    private String name;
    private String catergory;
    private String instructions;
    private double price;
    private String photo;


    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCatergory(String catergory) {
        this.catergory = catergory;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getCatergory() {
        return catergory;
    }

    public String getInstructions() {
        return instructions;
    }

    public double getPrice() {
        return price;
    }

    public String getPhoto() {
        return photo;
    }

}
