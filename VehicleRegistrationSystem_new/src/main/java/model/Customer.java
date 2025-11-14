package model;

public class Customer {
    private int customerId;
    private String name, email, phone;

    public Customer() {}
    public Customer(String name, String email, String phone) {
        this.name = name; this.email = email; this.phone = phone;
    }
    public Customer(int customerId, String name, String email, String phone) {
        this.customerId = customerId; this.name = name; this.email = email; this.phone = phone;
    }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    @Override
    public String toString() {
        return "Customer{id=" + customerId + ", name='" + name + "', email='" + email + "', phone='" + phone + "'}";
    }
}
