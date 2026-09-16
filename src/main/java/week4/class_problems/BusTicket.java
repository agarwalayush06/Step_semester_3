package week4.class_problems;

import java.util.HashSet;
import java.util.Set;

public class BusTicket {
    private String passengerName;
    private String destination;
    private boolean checkedIn;

    public BusTicket(String passengerName, String destination) {
        if (passengerName == null || destination == null ||
            passengerName.trim().isEmpty() || destination.trim().isEmpty() ||
            !passengerName.matches("[A-Za-z ]+") ||
            !destination.matches("[A-Za-z ]+")) {
            throw new IllegalArgumentException("Invalid booking");
        }

        this.passengerName = passengerName.trim();
        this.destination = destination.trim();
        this.checkedIn = false;
    }

    public void markCheckedIn() {
        if (!checkedIn) {
            checkedIn = true;
        }
    }

    public static void processBatch(String[][] rawBookings) {
        Set<String> accepted = new HashSet<>();
        int valid = 0;
        int rejected = 0;
        int duplicates = 0;

        for (String[] booking : rawBookings) {
            if (booking == null || booking.length < 2) {
                rejected++;
                continue;
            }

            try {
                BusTicket ticket = new BusTicket(booking[0], booking[1]);
                String key = ticket.passengerName.toLowerCase() + "|" +
                             ticket.destination.toLowerCase();

                if (accepted.contains(key)) {
                    duplicates++;
                } else {
                    accepted.add(key);
                    valid++;
                }
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        System.out.println("Valid: " + valid +
                " | Rejected: " + rejected +
                " | Duplicates skipped: " + duplicates);
    }

    public static void main(String[] args) {
        String[][] bookings = {
            {"Divya", "Chennai"},
            {"", "Bangalore"},
            {"Ravi123", "Pune"},
            {"Divya", "Chennai"},
            {" ", " "}
        };

        processBatch(bookings);
    }
}
