package tours;
// SUBCLASS 1: boat trips and water activities.
// Price = price x guests + 100 x islands x guests
import models.Tour;


public class SeaTour extends Tour {

    private String boatType;   // Speedboat / Catamaran / Yacht / None
    private int numIslands;    // 0 or more

    public SeaTour(String tourId, String customerName, String tourName,
                   double pricePerPerson, int numGuests,
                   String boatType, int numIslands) {
        super(tourId, customerName, tourName, pricePerPerson, numGuests);
        setBoatType(boatType);
        setNumIslands(numIslands);
    }

    public String getBoatType() {
        return boatType;
    }

    public void setBoatType(String boatType) {
        this.boatType = boatType;
    }

    public int getNumIslands() {
        return numIslands;
    }

    public void setNumIslands(int numIslands) {
        if (numIslands < 0) {
            throw new IllegalArgumentException("Islands cannot be negative.");
        }
        this.numIslands = numIslands;
    }

    @Override
    public double calculateTotalPrice() {
        return getPricePerPerson() * getNumGuests() + 100 * numIslands * getNumGuests();
    }

    @Override
    public String toString() {
        return "[Sea Tour] ID: " + getTourId()
                + " | Customer: " + getCustomerName()
                + " | Tour: " + getTourName()
                + " | Price/person: " + getPricePerPerson()
                + " | Guests: " + getNumGuests()
                + " | Boat: " + boatType
                + " | Islands: " + numIslands
                + " | TOTAL: " + calculateTotalPrice() + " THB";
    }
}