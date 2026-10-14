import java.util.ArrayList;
import java.util.List;

import interfaces.TourInterface;
import models.Tour;
import tours.LandTour;
import tours.SeaTour;
import tours.ShowTicket;

/**
 * Console demo: shows every OOP concept with REAL output.
 * Run with:  java DemoConcepts
 */
public class DemoConcepts {

    public static void main(String[] args) {

        // =====================================================
        System.out.println("===== 1. PRIMITIVE vs REFERENCE DATA TYPES =====");
        // Primitive: the variable holds the VALUE itself. Copy = independent copy.
        int a = 5;
        int b = a;          // copies the value
        b = 99;
        System.out.println("a = " + a + ", b = " + b + "   -> primitives are copied by value");

        // Reference: the variable holds the ADDRESS of an object. Copy = same object.
        SeaTour s1 = new SeaTour("S001", "John", "Phi Phi-Khai 1 Day Trip", 1500, 2, "Speedboat", 4);
        SeaTour s2 = s1;    // copies the reference, NOT the object
        s2.setNumGuests(10);
        System.out.println("s1 guests = " + s1.getNumGuests()
                + "   -> changing via s2 changed s1 (same object)");

        // == compares references, equals() compares content (as defined in Tour)
        SeaTour s3 = new SeaTour("S001", "John", "Phi Phi-Khai 1 Day Trip", 1500, 2, "Speedboat", 4);
        System.out.println("s1 == s3      : " + (s1 == s3) + "   (different objects in memory)");
        System.out.println("s1.equals(s3) : " + s1.equals(s3) + "    (same Tour ID)");

        // null = reference pointing to nothing; primitives can never be null
        Tour nothing = null;
        System.out.println("reference can be null: " + (nothing == null));

        // Wrapper classes: primitive <-> object (autoboxing)
        Integer boxed = 7;          // int -> Integer
        int unboxed = boxed;        // Integer -> int
        double parsed = Double.parseDouble("1500.5");   // String -> double
        System.out.println("boxed = " + boxed + ", unboxed = " + unboxed + ", parsed = " + parsed);
        System.out.println("In this project: double, int, boolean = primitives (price, guests, hotelPickup);"
                + " String, SeaTour, ArrayList = references.");

        // =====================================================
        System.out.println("\n===== 2. ENCAPSULATION =====");
        // s1.pricePerPerson = -5;    // would NOT compile: attribute is private
        try {
            s1.setPricePerPerson(-5);   // setter validates
        } catch (IllegalArgumentException ex) {
            System.out.println("Setter rejected bad value: " + ex.getMessage());
        }
        try {
            new SeaTour("S002", "Anna", "Jetski", 765, 0, "None", 0);   // constructor uses setters
        } catch (IllegalArgumentException ex) {
            System.out.println("Constructor rejected bad object: " + ex.getMessage());
        }

        // =====================================================
        System.out.println("\n===== 3. INHERITANCE =====");
        System.out.println("s1.getTourId() comes from Tour: " + s1.getTourId());
        System.out.println("s1 instanceof Tour          : " + (s1 instanceof Tour));
        System.out.println("s1 instanceof TourInterface : " + (s1 instanceof TourInterface));
        System.out.println("s1 instanceof Object        : " + (s1 instanceof Object));

        // =====================================================
        System.out.println("\n===== 4. ABSTRACTION =====");
        // Tour t = new Tour(...);    // would NOT compile: Tour is abstract
        System.out.println("Tour is abstract: it cannot be created. "
                + "Subclasses MUST implement calculateTotalPrice() and toString().");

        // =====================================================
        System.out.println("\n===== 5. POLYMORPHISM (upcasting, dynamic binding, downcasting) =====");
        List<TourInterface> list = new ArrayList<>();          // INTERFACE type
        list.add(new SeaTour("S001", "John", "Phi Phi-Khai 1 Day Trip", 1500, 2, "Speedboat", 4));
        list.add(new LandTour("L001", "Anna", "ATV", 1000, 3, "ATV", true));
        list.add(new ShowTicket("H001", "Mike", "Simon Show", 1000, 2, "18:00", "VIP"));

        for (TourInterface t : list) {
            // SAME call, DIFFERENT behaviour decided at run time
            System.out.printf("%-11s -> total = %,.2f THB%n",
                    t.getClass().getSimpleName(), t.calculateTotalPrice());

            // Downcast safely to use a UNIQUE method
            if (t instanceof SeaTour) {
                System.out.println("             unique to SeaTour: islands = "
                        + ((SeaTour) t).getNumIslands());
            } else if (t instanceof LandTour) {
                System.out.println("             unique to LandTour: hotel pickup = "
                        + ((LandTour) t).hasHotelPickup());
            } else if (t instanceof ShowTicket) {
                System.out.println("             unique to ShowTicket: seat = "
                        + ((ShowTicket) t).getSeatType());
            }
        }

        // =====================================================
        System.out.println("\n===== 6. OBJECT CLASS (parent of every class) =====");
        Object o = list.get(0);                 // a SeaTour stored in an Object variable
        System.out.println("o.toString() runs SeaTour's version:\n   " + o.toString());
        System.out.println("getClass()   : " + o.getClass().getName());
        System.out.println("hashCode() of s1 and s3 equal (equals/hashCode contract): "
                + (s1.hashCode() == s3.hashCode()));
        System.out.println("Overridden from Object: toString(), equals(), hashCode() (see Tour.java).");
    }
}