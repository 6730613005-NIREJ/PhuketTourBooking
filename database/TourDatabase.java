package database;

import java.util.ArrayList;
import java.util.List;

import interfaces.TourInterface;
// The ONE list shared by all pages.
// "static" = only one copy exists, so every page uses the same list.
// The list type is the INTERFACE, so it can hold SeaTour, LandTour and ShowTicket.
public class TourDatabase {

    private static ArrayList<TourInterface> tours = new ArrayList<>();

    public static List<TourInterface> getTours() {
        return tours;
    }

    public static void addTour(TourInterface t) {
        tours.add(t);
    }

    // true if this Tour ID is already used
    public static boolean idExists(String id) {
        for (TourInterface t : tours) {
            if (t.getTourId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }
}