package interfaces;
// package interfaces;
// INTERFACE: the list of methods every booking must have.
public interface TourInterface {
    String getTourId();
    String getCustomerName();
    String getTourName();
    double getPricePerPerson();
    int getNumGuests();
    double calculateTotalPrice();
    String toString();
}
