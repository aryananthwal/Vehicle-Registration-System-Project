package model;

import java.sql.Date;

public class Registration {
    private int registrationId, vehicleId;
    private Date registrationDate, expiryDate;

    public Registration() {}
    public Registration(int vehicleId, Date registrationDate, Date expiryDate) {
        this.vehicleId = vehicleId; this.registrationDate = registrationDate; this.expiryDate = expiryDate;
    }
    public Registration(int registrationId, int vehicleId, Date registrationDate, Date expiryDate) {
        this.registrationId = registrationId; this.vehicleId = vehicleId;
        this.registrationDate = registrationDate; this.expiryDate = expiryDate;
    }

    public int getRegistrationId() { return registrationId; }
    public void setRegistrationId(int registrationId) { this.registrationId = registrationId; }
    public int getVehicleId() { return vehicleId; }
    public void setVehicleId(int vehicleId) { this.vehicleId = vehicleId; }
    public Date getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(Date registrationDate) { this.registrationDate = registrationDate; }
    public Date getExpiryDate() { return expiryDate; }
    public void setExpiryDate(Date expiryDate) { this.expiryDate = expiryDate; }

    @Override
    public String toString() {
        return "Registration{id=" + registrationId + ", vehicleId=" + vehicleId + ", registrationDate=" + registrationDate + ", expiryDate=" + expiryDate + "}";
    }
	public void setCustomerId(int customerId) {
		// TODO Auto-generated method stub
		
	}
}
