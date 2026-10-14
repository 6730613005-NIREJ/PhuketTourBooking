package models;

// ABSTRACT CLASS: the shared data of all bookings.
// You cannot write "new Tour(...)". Only the subclasses can be created.

// import interfaces.TourInterface;

import interfaces.TourInterface;
public abstract class Tour implements TourInterface {

    // private = ENCAPSULATION (only this class can touch them directly)
    private String tourId;
    private String customerName;
    private String tourName;
    private double pricePerPerson;
    private int numGuests;

    public Tour(String tourId, String customerName, String tourName,
                double pricePerPerson, int numGuests) {
        if (tourId.equals("")) {
            throw new IllegalArgumentException("Tour ID cannot be empty.");
        }
        if (customerName.equals("")) {
            throw new IllegalArgumentException("Customer name cannot be empty.");
        }
        this.tourId = tourId;
        this.customerName = customerName;
        // the constructor calls the setters, so the checks always happen
        setTourName(tourName);
        setPricePerPerson(pricePerPerson);
        setNumGuests(numGuests);
    }

    // final = a subclass cannot change these two methods
    public final String getTourId() {
        return tourId;
    }

    public final String getCustomerName() {
        return customerName;
    }

    public String getTourName() {
        return tourName;
    }

    public double getPricePerPerson() {
        return pricePerPerson;
    }

    public int getNumGuests() {
        return numGuests;
    }

    // setters check the value before saving it
    public void setTourName(String tourName) {
        if (tourName.equals("")) {
            throw new IllegalArgumentException("Tour name cannot be empty.");
        }
        this.tourName = tourName;
    }

    public void setPricePerPerson(double pricePerPerson) {
        if (pricePerPerson < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.pricePerPerson = pricePerPerson;
    }

    public void setNumGuests(int numGuests) {
        if (numGuests < 1) {
            throw new IllegalArgumentException("Guests must be at least 1.");
        }
        this.numGuests = numGuests;
    }

    // abstract = no body here. Each subclass MUST write its own version.
    public abstract double calculateTotalPrice();

    public abstract String toString();
}