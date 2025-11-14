package model;

public class Vehicle {
    private int id;
    private int customerId;
    private String brand;
    private String model;
    private int year;

    // Default constructor
    public Vehicle() {}

    // Constructor without ID (used when inserting new vehicle)
    public Vehicle(int customerId, String brand, String model, int year) {
        this.customerId = customerId;
        this.brand = brand;
        this.model = model;
        this.year = year;
    }

    // Getter and Setter for ID
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    // Getter and Setter for customerId
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    // Getter and Setter for brand
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }

    // Getter and Setter for model
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    // Getter and Setter for year
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }

	public void setRegistrationNumber(String string) {
		// TODO Auto-generated method stub
		
	}
}
