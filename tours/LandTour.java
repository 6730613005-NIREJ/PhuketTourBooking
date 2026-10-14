package tours;
// SUBCLASS 2: land adventures and guided tours.
// Price = price x guests (+ 200 x guests if hotel pickup)
import models.Tour;
public class LandTour extends Tour {

    private String activityType;   // Elephant Care, ATV, Rafting ...
    private boolean hotelPickup;   // true or false

    public LandTour(String tourId, String customerName, String tourName,
                    double pricePerPerson, int numGuests,
                    String activityType, boolean hotelPickup) {
        super(tourId, customerName, tourName, pricePerPerson, numGuests);
        setActivityType(activityType);
        setHotelPickup(hotelPickup);
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public boolean hasHotelPickup() {
        return hotelPickup;
    }

    public void setHotelPickup(boolean hotelPickup) {
        this.hotelPickup = hotelPickup;
    }

    @Override
    public double calculateTotalPrice() {
        double total = getPricePerPerson() * getNumGuests();
        if (hotelPickup) {
            total = total + 200 * getNumGuests();
        }
        return total;
    }

    @Override
    public String toString() {
        String pickup = "No";
        if (hotelPickup) {
            pickup = "Yes";
        }
        return "[Land Tour] ID: " + getTourId()
                + " | Customer: " + getCustomerName()
                + " | Tour: " + getTourName()
                + " | Price/person: " + getPricePerPerson()
                + " | Guests: " + getNumGuests()
                + " | Activity: " + activityType
                + " | Hotel pickup: " + pickup
                + " | TOTAL: " + calculateTotalPrice() + " THB";
    }
}