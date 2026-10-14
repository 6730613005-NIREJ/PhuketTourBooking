package tours;
// SUBCLASS 3: shows and entry tickets.
// Price = price x tickets (+ 500 x tickets if VIP)
import models.Tour;

public class ShowTicket extends Tour {

    private String showTime;   // "18:00" or "All day"
    private String seatType;   // Standard / VIP / Entry

    public ShowTicket(String tourId, String customerName, String tourName,
                      double pricePerPerson, int numGuests,
                      String showTime, String seatType) {
        super(tourId, customerName, tourName, pricePerPerson, numGuests);
        setShowTime(showTime);
        setSeatType(seatType);
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        if (showTime.equals("")) {
            throw new IllegalArgumentException("Show time cannot be empty.");
        }
        this.showTime = showTime;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    @Override
    public double calculateTotalPrice() {
        double total = getPricePerPerson() * getNumGuests();
        if (seatType.equals("VIP")) {
            total = total + 500 * getNumGuests();
        }
        return total;
    }

    @Override
    public String toString() {
        return "[Show Ticket] ID: " + getTourId()
                + " | Customer: " + getCustomerName()
                + " | Tour: " + getTourName()
                + " | Price/person: " + getPricePerPerson()
                + " | Tickets: " + getNumGuests()
 
                + " | Show time: " + showTime
                + " | Seat: " + seatType
                + " | TOTAL: " + calculateTotalPrice() + " THB";
    }
}